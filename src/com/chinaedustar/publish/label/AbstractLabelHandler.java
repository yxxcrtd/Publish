package com.chinaedustar.publish.label;

import java.util.ArrayList;
import java.util.List;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.itfc.LabelHandler;
import com.chinaedustar.publish.model.Announcement;
import com.chinaedustar.publish.model.Article;
import com.chinaedustar.publish.model.Author;
import com.chinaedustar.publish.model.BuiltinLabel;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.ChannelSearch;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.Feedback;
import com.chinaedustar.publish.model.Item;
import com.chinaedustar.publish.model.ModelObject;
import com.chinaedustar.publish.model.PageAttrObject;
import com.chinaedustar.publish.model.PaginationInfo;
import com.chinaedustar.publish.model.Photo;
import com.chinaedustar.publish.model.SiteSearch;
import com.chinaedustar.publish.model.Soft;
import com.chinaedustar.publish.model.Source;
import com.chinaedustar.publish.model.SpecialWrapper;
import com.chinaedustar.publish.model.Student;
import com.chinaedustar.publish.model.User;
import com.chinaedustar.template.TemplateConstant;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * LabelHandler 的抽象实现，提供 Label 处理的一些辅助方法和缺省实现。
 *   派生类必须提供 public 的缺省构造函数。
 * @author liujunxing
 */
public abstract class AbstractLabelHandler implements LabelHandler, TemplateConstant {
	/** 当前发布系统环境对象。 */
	protected PublishContext pub_ctxt = null;
	
	/** 当前模板执行环境。 */
	protected InternalProcessEnvironment env = null;
	
	/** 当前标签。 */
	protected AbstractLabelElement label = null;
	
	protected AbstractLabelHandler() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.LabelHandler#getLabelHandler(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
	 */
	public LabelHandler getLabelHandler(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
		AbstractLabelHandler new_handler = newInstance();
		new_handler.pub_ctxt = pub_ctxt;
		new_handler.env = env;
		new_handler.label = label;
		return new_handler;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
	 */
	public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
		return handleLabel();
	}

	/**
	 * 构造一个新的实例，如果派生类实现并返回, 则缺省实现就不用使用复杂的 Clazz.newInstance() 方法了。
	 * @return 构造此类的新实例。
	 */
	protected AbstractLabelHandler newInstance() {
		try {
			// 使用 newInstance() 也许比 clone() 简单一点，但是派生类构造函数必须要可访问。
			//    方法是提供 public 缺省构造，或者类设置为 internal or public 的。
			AbstractLabelHandler new_handler = this.getClass().newInstance();
			return new_handler;
		} catch (InstantiationException e) {
			throw new PublishException("不支持 newInstance() 操作的 Handler: " + this, e);
		} catch (IllegalAccessException e) {
			throw new PublishException("不支持 newInstance() 操作的 Handler: " + this, e);
		}
	}
	
	/**
	 * 解析一个简单标签并返回结果。
	 * @param pub_ctxt - 当前发布系统环境。
	 * @param env - 模板执行环境。
	 * @param label - 标签元素。
	 * @return 返回 LABEL_PROCESS 常量，一般为 PROCESS_DEFAULT.
	 */
	protected int handleLabel() {
		// 缺省返回此 label 未知。
		out("#{?? 未知" + label + "}");
		
		return PROCESS_DEFAULT;
	}
	
	// === 辅助函数 ===============================================================
	
	/**
	 * 输出内容。
	 * @param env
	 * @param val
	 */
	protected final void out(String val) {
		env.getOut().write(val);
	}

	/**
	 * 获得具有指定名字的内建 Label
	 * 
	 * @param name
	 * @return
	 */
	protected final BuiltinLabel getBuiltinLabel(String name) {
		BuiltinLabel label = pub_ctxt.getSite().getLabelCollection().findLabel(name);
		return label;
	}

	/**
	 * 获得标签属性中设置的模板名字，如果标签属性未设置模板名字，则返回缺省的
	 * 
	 * @param default_template_name
	 * @return 标签中模板属性的名字是 "template", 例如： #{ShowLogo template=".new_show_logo"}
	 */
	protected final String getTemplateName(String default_template_name) {
		String result = this.label.getAttributes().getNamedAttribute("template");
		if (result == null) return default_template_name;
		result = result.trim();
		if (result.length() == 0) return default_template_name;
		return result;
	}
	
	/**
	 * 提示用户内建模板找不到，并返回 PROCESS_DEFAULT
	 * 
	 * @return
	 */
	protected final int unexistBuiltinLabel(String template_name) {
		return unexistBuiltinLabel(template_name, PROCESS_DEFAULT);
	}

	/**
	 * 提示用户内建模板找不到，并返回 return_result
	 * 
	 * @param return_result
	 * @return
	 */
	protected final int unexistBuiltinLabel(String template_name, int return_result) {
		out("#{?? " + label.getLabelName() + " 找不到内建标签 '" + template_name + "'}");
		return return_result;
	}

	/**
	 * 获得指定参数集合中指定名字的参数，如果没有则使用缺省值
	 * 
	 * @param params
	 * @param key
	 * @param defval
	 * @return
	 */
	protected String getNamedAttribute(AttributeCollection params, String key, String defval) {
		if (params == null) return defval;
		String value = params.getNamedAttribute(key);
		if (StringHelper.isEmpty(value)) return defval;
		return value;
	}
	
	/**
	 * 得到表格化的List。主要用于那些需要在设置每行显示多少列的标签
	 * 
	 * @param source 原来的List
	 * @param cols 每行显示多少列
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static List<List> getTableList(List source, int cols) {
		List<List> tableList = new ArrayList<List>();
		if (cols <= 0) {
			cols = 1;
		}
		if (source != null && source.size() > 0) {
			int rows = source.size() / cols;
			if (source.size() % cols > 0) {
				rows ++;
			}
			
			for (int i = 0; i< rows; i++) {
				int begin = i * cols;
				int end = (i + 1) * cols;
				if (end > source.size()) {
					end = source.size();
				}
				List rowList = new ArrayList();
				for (int j = begin; j < end; j++) {
					rowList.add(source.get(j));
				}
				tableList.add(rowList);
			}
		}
		
		return tableList;
	}
	
	// === Repeater/PageNav 支持 =======================================================
	
	/**
	 * 增加对 Reapeater 标签的支持。
	 * @param local_ctxt 
	 * @param var_name 循环时放入的变量。
	 * @param coll_obj 用来支持循环的集合数据。
	 */
	public static void addRepeaterSupport(LocalContext local_ctxt, String var_name, Object coll_obj) {
		local_ctxt.setVariable(LabelHandler.REPEATER_VAR_NAME, var_name);
		local_ctxt.setVariable(LabelHandler.REPEATER_DATA_PROVIDER_NAME, coll_obj);
	}
	
	/**
	 * 增加对 PageNav 标签的支持。
	 * @param local_ctxt
	 * @param page_info 分页信息对象。
	 */
	public static void addPageNavSupport(LocalContext local_ctxt, PaginationInfo page_info) {
		local_ctxt.setVariable(LabelHandler.PAGINATION_INFO_NAME, page_info);
	}
	
	// === get 当前对象方法 =============================================================
	
	/**
	 * 获得这个页面的 this 对象 - 页面当前模型对象。
	 * @return
	 */
	protected final ModelObject getThis() {
		Object this_o = env.findVariable("this");
		if (this_o == null) return null;
		if (this_o instanceof ModelObject) return (ModelObject)this_o;
		
		// 有这么一个变量，但是不是 PublishModelObject 类型的。
		return null;
	}

	/**
	 * 得到当前的频道
	 * @return 返回当前频道，如果没有频道则返回 null.
	 */
	protected final Channel getCurrentChannel() {
		return getCurrentChannel(this.env);
	}
	
	/**
	 * 得到当前的栏目
	 * @return
	 */
	protected final Column getCurrentColumn() {
		return getCurrentColumn(env);
	}
	
	/**
	 * 获得当前专题。
	 * @return
	 */
	protected final SpecialWrapper getCurrentSpecial() {
		return getCurrentSpecial(env);
	}

	/**
	 * 得到当前的ANNOUNCE对象。
	 * @return
	 */
	protected final Announcement getCurrentAnnounce() {
		return getCurrentAnnounce(env);
	}

	/**
	 * 得到当前的文章对象。
	 * @return
	 */
	protected final Article getCurrentArticle() {
		return getCurrentArticle(env);
	}

	/**
	 * 得到当前作者对象
	 * @param env
	 * @return
	 */
	protected final Author getCurrentAuthor(){
		Object author = env.findVariable("author");
		if (author != null && author instanceof Author)
			return (Author)author;
		
		author = env.findVariable("this");
		if (author != null && author instanceof Author)
			return (Author)author;
		
		return null;
	}

	/**
	 * 得到当前的留言对象。
	 * @return
	 */
	protected final Feedback getCurrentFeedback() {
		Object object = env.findVariable("feedback");
		if (object == null) {
			object = env.findVariable("this");
		}
		Feedback feedback = null;
		if (object != null && object instanceof Feedback) {
			feedback = (Feedback)object;
		}
		return feedback;
	}

	/**
	 * 得到当前的图片对象。
	 * @return
	 */
	protected final Photo getCurrentPhoto() {
		return getCurrentPhoto(env);
	}	

	/**
	 * 得到当前的下载对象。
	 * @return
	 */
	protected Soft getCurrentSoft() {
		return getCurrentSoft(env);
	}

	/**
	 * 得到当前来源对象
	 * @return
	 */
	protected final Source getCurrentSource() {
		Object source = env.findVariable("source");
		if (source != null && source instanceof Source)
			return (Source)source;
		
		source = env.findVariable("this");
		if (source != null && source instanceof Source)
			return (Source)source;
		
		return null;
	}

	/**
	 * 得到当前作者对象
	 * @param env
	 * @return
	 */
	protected final User getCurrentUser(){
		Object user = env.findVariable("user");
		if (user != null && user instanceof User)
			return (User)user;
		
		user = env.findVariable("this");
		if (user != null && user instanceof User)
			return (User)user;
		
		return null;
	}

	/**
	 * 得到当前具有 PageAttrObject 接口的对象。
	 * @return
	 */
	protected PageAttrObject getCurrentPageAttrObject() {
		// 
		ModelObject this_ = getThis();
		while (this_ != null) {
			if (this_ instanceof PageAttrObject) {
				return (PageAttrObject)this_;
			}
			this_ = this_.getParent();
		}
		return pub_ctxt.getSite();
	}
	
	// === public static 可用函数 ======================================================

	/**
	 * 得到当前的频道
	 * @return 返回当前频道，如果没有频道则返回 null.
	 */
	public static final Channel getCurrentChannel(InternalProcessEnvironment env) {
		Object pub_obj = env.findVariable("channel");
		Channel channel = null;
		if (pub_obj != null && pub_obj instanceof Channel) {
			channel = (Channel)pub_obj;
		} else {
			pub_obj = env.findVariable("this");
			if (pub_obj == null || !(pub_obj instanceof ModelObject)) {
				return null;
			}
			ModelObject model_obj = (ModelObject)pub_obj;
			while (model_obj != null) {
				if (model_obj instanceof Channel) {
					channel = (Channel)model_obj;
					break;
				} else {
					model_obj = ((ModelObject)model_obj).getParent();
				}
			}
		}
		return channel;
	}

	/**
	 * 得到当前的栏目
	 * @return
	 */
	public static final Column getCurrentColumn(InternalProcessEnvironment env) {
		Object pub_obj = env.findVariable("column");
		Column column = null;
		if (pub_obj != null && pub_obj instanceof Column) {
			column = (Column)pub_obj;
		} else {
			pub_obj = env.findVariable("this");
			if (pub_obj == null || !(pub_obj instanceof ModelObject)) {
				return null;
			}
			ModelObject model_obj = (ModelObject)pub_obj;
			while (model_obj != null) {
				if (model_obj instanceof Column) {
					column = (Column)model_obj;
					break;
				} else {
					model_obj = ((ModelObject)model_obj).getParent();
				}
			}
		}
		return column;
	}

	/**
	 * 获得当前专题
	 * 
	 * @return
	 */
	public static final SpecialWrapper getCurrentSpecial(InternalProcessEnvironment env) {
		Object obj = env.findVariable("special");
		if (obj != null && obj instanceof SpecialWrapper)
			return (SpecialWrapper)obj;
		
		obj = env.findVariable("this");
		if (obj != null && obj instanceof SpecialWrapper)
			return (SpecialWrapper)obj;

		return null;
	}

	/**
	 * 得到当前环境中的文章对象
	 * 
	 * @param env
	 * @return
	 */
	public static final Article getCurrentArticle(InternalProcessEnvironment env) {
		Object article = env.findVariable("article");
		if (article != null && article instanceof Article)
			return (Article)article;
		
		article = env.findVariable("item");
		if (article != null && article instanceof Article)
			return (Article)article;
		
		article = env.findVariable("this");
		if (article != null && article instanceof Article)
			return (Article)article;

		return null;
	}

	/**
	 * 得到当前环境中的本科生对象
	 * 
	 * @param env
	 * @return
	 */
	public static final Student getCurrentStudent(InternalProcessEnvironment env) {
		Object student = env.findVariable("student");
		if (student != null && student instanceof Student)
			return (Student) student;

		student = env.findVariable("item");
		if (student != null && student instanceof Student)
			return (Student) student;

		student = env.findVariable("this");
		if (student != null && student instanceof Student)
			return (Student) student;

		return null;
	}

	/**
	 * 得到当前环境中的图片对象
	 * 
	 * @param env
	 * @return
	 */
	public static final Photo getCurrentPhoto(InternalProcessEnvironment env) {
		Object photo = env.findVariable("photo");
		if (photo != null && photo instanceof Photo)
			return (Photo)photo;
		
		photo = env.findVariable("item");
		if (photo != null && photo instanceof Photo)
			return (Photo)photo;
		
		photo = env.findVariable("this");
		if (photo != null && photo instanceof Photo)
			return (Photo)photo;

		return null;
	}

	/**
	 * 得到当前的下载对象。
	 * @return
	 */
	public static final Soft getCurrentSoft(InternalProcessEnvironment env) {
		Object soft = env.findVariable("soft");
		if (soft != null && soft instanceof Soft)
			return (Soft)soft;
		
		soft = env.findVariable("item");
		if (soft != null && soft instanceof Soft)
			return (Soft)soft;
		
		soft = env.findVariable("this");
		if (soft != null && soft instanceof Soft)
			return (Soft)soft;
		
		return null;
	}

	/**
	 * 得到当前的 Item 对象。
	 * @param env
	 * @return
	 */
	public static final Item getCurrentItem(InternalProcessEnvironment env) {
		Object item = env.findVariable("item");
		if (item != null && (item instanceof Item))
			return (Item)item;
		
		item = env.findVariable("this");
		if (item != null && (item instanceof Item)) 
			return (Item)item;
		
		item = env.findVariable("article");
		if (item == null) {
			item = env.findVariable("photo");
			if (item == null) {
				item = env.findVariable("soft");
			}
		}
		
		if (item != null && (item instanceof Item))
			return (Item)item;
		
		if (item != null && (item instanceof Item))
			return (Item)item;
		
		return null;
	}

	/**
	 * 得到当前的 Announce 对象。
	 * @param env
	 * @return
	 */
	public static final Announcement getCurrentAnnounce(InternalProcessEnvironment env) {
		Object announce = env.findVariable("announce");
		if (announce != null || announce instanceof Announcement) 
			return (Announcement)announce;
		
		announce = env.findVariable("this");
		if (announce != null || announce instanceof Announcement) 
			return (Announcement)announce;
		
		return null;
	}

	/**
	 * 得到当前的 Search 对象。
	 * @param env
	 * @return
	 */
	public static final SiteSearch getCurrentSearch(InternalProcessEnvironment env) {
		 Object search = env.findVariable("search");
		 if (search != null && search instanceof SiteSearch)
			 return (SiteSearch)search;
		 return null;
	}

	/**
	 * 得到当前频道搜索 (ChannelSearch) 对象。
	 * @param env
	 * @return
	 */
	public static final ChannelSearch getCurrentChannelSearch(InternalProcessEnvironment env) {
		Object search = env.findVariable("search");
		if (search != null && search instanceof ChannelSearch)
			return (ChannelSearch)search;
		return null;
	}

	// === 高级获取参数支持函数 =========================================================
	
	/**
	 * getChannelAttrib() 返回的结果对象包装。
	 */
	protected static final class ChannelParam {
		public final int channelId;
		public final Channel channel;
		public ChannelParam(int channelId, Channel channel) {
			this.channelId = channelId;
			this.channel = channel;
		}
		public ChannelParam(Channel channel) {
			this.channelId = channel == null ? 0 : channel.getId();
			this.channel = channel;
		}
	}
	
	/**
	 * 获得当前频道参数, channelId='xxx'
	 * @return 返回根据 channelId 参数指定的频道标识、频道对象的包装对象。
	 *   如果 channelId >= 0 则同时返回频道对象，< 0 则仅返回标识。
	 */
	protected ChannelParam getChannelAttrib() {
		// 得到频道参数，字符串形式。
		AttributeCollection attrs = label.getAttributes();
		String channelId_string = attrs.safeGetStringAttribute("channelId", "");
		
		// 如果 == 'current' 或者未给出，则使用当前频道，其可能为 null.
		if (channelId_string == null || channelId_string.length() == 0 || 
				"current".equals(channelId_string) || "0".equals(channelId_string)) {
			// 使用当前频道。
			return new ChannelParam(getCurrentChannel());
		}
		
		// 获得数字形式。 == 0 使用当前频道。
		int channelId = PublishUtil.safeParseInt(channelId_string, 0);
		if (channelId == 0) return new ChannelParam(getCurrentChannel());
		
		// > 0 返回该指定频道，< 0 仅返回标识。
		if (channelId > 0)
			return new ChannelParam(pub_ctxt.getSite().getChannel(channelId));
		else
			return new ChannelParam(channelId, null);
	}

	/**
	 * getColumnAttrib() 返回的结果对象包装。
	 * @author liujunxing
	 *
	 */
	protected static final class ColumnParam {
		public final List<Integer> column_ids = new java.util.ArrayList<Integer>();
		public Column column;
		public ColumnParam() {}
		public ColumnParam(Column column) {
			this.column = column;
			if (column == null)
				column_ids.add(0);
			else
				column_ids.add(column.getId());
		}
		public void addColumn(Column column) {
			int id = column == null ? 0 : column.getId();
			if (column_ids.contains(id) == false)
				column_ids.add(id);
		}
		public void addColumnId(int id) {
			if (column_ids.contains(id) == false)
				column_ids.add(id);
		}
		public int getFirstColumnId() {
			if (column_ids == null || column_ids.size() == 0) return 0;
			return column_ids.get(0);
		}
	}
	
	/**
	 * 获得当前栏目参数, columnId='xxx', 支持提供多个参数。
	 * 多个参数时例子： columnId='3,5,current,parent,first_child,last_child'
	 * 字符串： 'current', 'ignore', 'ancestor', 'parent', 'first_child', 'last_child'
	 * @return 返回 ColumnParam 对象，其保证至少有一个 id. 
	 * 'first_child', 'last_child' 当前不支持。
	 */
	protected ColumnParam getColumnAttrib() {
		// 得到栏目参数，字符串形式。
		AttributeCollection attrs = label.getAttributes();
		String columnId_string = attrs.getNamedAttribute("columnId");
		
		Column current_column = getCurrentColumn();
		if (columnId_string == null || columnId_string.length() == 0 ||
				"current".equals(columnId_string) || "0".equals(columnId_string))
			return new ColumnParam(current_column);
		
		// 分解这个字符串。
		String[] ids = StringHelper.split(columnId_string, ",", false);
		if (ids == null || ids.length == 0) return new ColumnParam(current_column);
		
		// 为每个栏目执行添加。
		ColumnParam column_param = new ColumnParam();
		for (int i = 0; i < ids.length; ++i) {
			columnId_string = ids[i];
			if ("current".equals(columnId_string))
				column_param.addColumn(current_column);
			else if ("ignore".equals(columnId_string))
				column_param.addColumn(null);
			else if ("ancestor".equals(columnId_string))
				column_param.addColumn(current_column == null ? null : current_column.getAncestorColumn());
			else if ("parent".equals(columnId_string))
				column_param.addColumn(current_column == null ? null : current_column.getParentColumn());
			else
				column_param.addColumnId(PublishUtil.safeParseInt(columnId_string, 0));
		}
		
		return column_param;
	}
}

