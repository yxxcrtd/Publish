package com.chinaedustar.publish.label;

import java.util.List;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.itfc.LabelHandler2;
import com.chinaedustar.publish.model.*;

/**
 * 作者相关的标签。
 * 
 * @author liujunxing
 *
 */
public final class GeneralAuthorLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralAuthorLabel(){
	}
	
	/**
	 * 注册LabelHandler
	 * 
	 */
	public static final void registerHandler(LabelHandlerMap map){
		// logger.debug("GeneralAuthorLabel注册了其包含的一组标签解释器");
		
		map.put("AuthorName", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorPhoto", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorSex", new AuthorPropertyHandler());		// 调试通过。
		map.put("AuthorBirthDay", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorCompany", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorDepartment", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorAddress", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorTel", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorFax", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorZipCode", new AuthorPropertyHandler());	// 调试通过
		map.put("AuthorHomePage", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorEmail", new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorQQ", new AuthorPropertyHandler());		// 调试通过。
		map.put("AuthorType",new AuthorPropertyHandler());	// 调试通过。
		map.put("AuthorIntro",new AuthorPropertyHandler());	// 调试通过。
		
		map.put("ShowAuthorList", new AuthorListLabelHander());	// 调试通过。
		map.put("AuthorItemList", new AuthorItemListLabelHandler2());
	}

	/**
	 * 显示作者属性的简单处理器。
	 * @author liujunxing
	 *
	 */
	private static final class AuthorPropertyHandler extends AbstractSimpleLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName(); 
			Author author = getCurrentAuthor(env);
			if (author == null) {
				env.getOut().write("{?? #" + label_name + " 当前页面中不存在作者对象(author)}");
				return PROCESS_DEFAULT;
			}
			
			String result = "";
			if ("AuthorName".equals(label_name))
				result = author.getName();
			else if ("AuthorPhoto".equals(label_name))
				result = pub_ctxt.getSite().resolveUrl(author.getPhoto());
			else if ("AuthorSex".equals(label_name))
				result = author.getSexValue();
			else if ("AuthorBirthDay".equals(label_name))
				result = String.valueOf(author.getBirthDay());
			else if ("AuthorCompany".equals(label_name))
				result = author.getCompany();
			else if ("AuthorDepartment".equals(label_name))
				result = author.getDepartment();
			else if ("AuthorAddress".equals(label_name))
				result = author.getAddress();
			else if ("AuthorTel".equals(label_name))
				result = author.getTel(); 
			else if ("AuthorFax".equals(label_name))
				result = author.getFax();
			else if ("AuthorZipCode".equals(label_name))
				result = author.getZipCode();
			else if ("AuthorHomePage".equals(label_name))
				result = author.getHomePage();
			else if ("AuthorEmail".equals(label_name))
				result = author.getEmail();
			else if ("AuthorQQ".equals(label_name))
				result = String.valueOf(author.getQq());
			else if ("AuthorType".equals(label_name))
				result = author.getAuthorTypeString();
			else if ("AuthorIntro".equals(label_name))
				result = author.getDescription();
				
			if (result != null)
				env.getOut().write(result);
			return PROCESS_DEFAULT;
		}

		/** 获得当前环境中 author 对象。 */
		private static Author getCurrentAuthor(InternalProcessEnvironment env) {
			Object author = env.findVariable("author");
			if (author != null && author instanceof Author)
				return (Author)author;
			
			author = env.findVariable("this");
			if (author != null && author instanceof Author)
				return (Author)author;
			
			return null;
		}
	}
	
	/**
	 * 显示作者列表。
	 * #{ShowAuthorList channelId="0" type="0" colNum="5" style="" showType="true"}
	 * 
	 * @param channelId - 频道的标识，-1：网站中所有的作者（包括所有频道的作者）；0：当前频道中的作者，如果频道对象不存在，则为全站作者；1：特定频道的作者。默认为 0 。
	 * @param type - 作者分类，0：所有分类；1 大陆作者； 2 港台作者； 3 海外作者； 4 本站特约； 5其他作者。默认为 0 。
	 * @param num - 显示多少个作者。缺省取前 5 个。
	 * @param dispType	- 显示方式。0：普通；1：显示图像及说明；2：显示图像不显示说明。默认为1。
	 * @param openType  - 打开方式。0：_self；1：_blank。默认为0。
	 * @param cols - 每行显示的列数，默认为 1 。
	 * @param style - 显示的样式表。
	 * @param imgWidth	- 头像宽度。默认为50px。
	 * @param imgHeight - 头像高度。默认为60px。
	 * @param picPos - 图片与文字位置。0：图左；1：图上；2：图右；3：图下。默认为左。
	 * @param moreText - ”更多“处显示的文字。如"more";
	 *  
	 *  影响获取数据的方法为： channelId, type, num
	 */
	static final class AuthorListLabelHander extends AbstractLabelHandler implements LabelHandler2 {
		public static final String DEFAULT_TEMPLATE = ".builtin.showauthorlist";
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		@Override public int handleLabel() {
			// 获得参数
			AttributeCollection coll = label.getAttributes();
			int channelId = coll.safeGetIntAttribute("channelId", 0);
			int type = coll.safeGetIntAttribute("type", 0);
			int cols = coll.safeGetIntAttribute("cols", 1);
			int num = coll.safeGetIntAttribute("num", 5);
			
			java.util.Map<String, Object> options = coll.attrToOptions();
			options.put("channelId", channelId);
			options.put("type", type);
			options.put("cols", cols);
			options.put("num", num);
			options.put("dispType", coll.safeGetIntAttribute("dispType", 0));
			OpenType openType = OpenType.fromString(coll.safeGetStringAttribute("openType", null));
			options.put("openType", openType);
			options.put("style", coll.safeGetStringAttribute("style", ""));
			int imgWidth = coll.safeGetIntAttribute("imgWidth", 50);
			if (imgWidth <= 0) imgWidth = 32;
			options.put("imgWidth", imgWidth);
			int imgHeight = coll.safeGetIntAttribute("imgHeight", 60);
			if (imgHeight <= 0) imgHeight = 32;
			options.put("imgHeight", imgHeight);
			options.put("picPos", coll.safeGetIntAttribute("picPos", 0));
			options.put("moreText", coll.safeGetStringAttribute("moreText", ""));	
			
			// 获取数据。
			List<Author> author_list = getAuthorList(channelId, num, type);
			
			// 执行模板。
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, PROCESS_DEFAULT);
				local_ctxt.setVariable("author_list", author_list);
				local_ctxt.setVariable("options", options);
				super.addRepeaterSupport(local_ctxt, "author", author_list);
				return PROCESS_DEFAULT;
			} else {
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtLabel = getBuiltinLabel(template_name);
				Object[] args = new Object[] {author_list, options};
				return builtLabel.process(env, args);
			}
		}

		/** 得到作者的集合。 */
		private List<Author> getAuthorList(int channelId, int num, int type) {
			// 根据频道属性获取作者。
			// 频道的标识   -1：网站中所有的作者（包括所有频道的作者）；
			//      (默认) 0：当前频道中的作者，如果频道对象不存在，则为全站作者；
			//           > 0：特定频道的作者。
			if (channelId == 0) {
				Channel channel = getCurrentChannel();
				channelId = channel == null ? 0 : channel.getId();
			}
			
			List<Author> list = pub_ctxt.getSite().getAuthorCollection().getAuthorList(channelId, num, type);
			
			return list;
		}
	}

	/**
	 * 显示作者的项目（包括文章、图片、软件等）列表 (版本 2)
	 *  <br>如果需要
	 *  <br>可自定义。能嵌套使用Repeater和PageNav标签。Repeater中能使用的变量是item。
	 * 
	 * @param moduleId 模块的标识。必须提供。（可通过在标签中给出，也可以通过设置环境变量给出）。
	 * @param pageSize 每页显示搜索结果的个数。默认为10。
	 * @author dengxiaolong, liujunxing
	 * 语法： 
	 *   #{AuthorItemList name='xxx'  : name - 作者名字，缺省为当前作者的名字
	 *     pageSize='20'              : pageSize - 分页大小
	 *     }
	 */
	@SuppressWarnings("rawtypes")
	static final class AuthorItemListLabelHandler2 extends AdvanceItemListHandler3 {
		public static final String DEFAULT_TEMPLATE = ".builtin.authorarticlelist";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AdvanceItemListHandler3#getData()
		 */
		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			
			List result = ibo.getAdvItemList(query_option, page_info);
			return result;
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler2#getBuiltinName()
		 */
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AdvanceItemListHandler3#makeQueryOption()
		 */
		@Override protected boolean makeQueryOption() {
			if (super.makeQueryOption() == false) return false;
			
			// 添加作者查询选项，限定项目是这个作者的。
			super.query_option.author = label.getAttributes().getNamedAttribute("author");
			if (query_option.author == null || query_option.author.length() == 0) {
				Author author = getCurrentAuthor();
				if (author != null)
					query_option.author = author.getName();
			}
			
			return true;
		}
	}
}
