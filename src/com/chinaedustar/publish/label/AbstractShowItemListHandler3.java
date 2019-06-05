package com.chinaedustar.publish.label;

import java.util.*;

import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.itfc.LabelHandler2;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.publish.util.QueryHelper;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * ShowAriticleListHandler3 等类的抽象基类。
 * 
 * 注意：这里要和 ShowArticleList 编辑页面联系起来一起改。
 * 
 * @param channelId - 频道标识, 默认为 0
 *       0: 当前频道, 如果没有当前频道则仍然为0；
 *     (*)  -1: 和当前频道相同的同类频道，如果没有当前频道，作用等同于 0；
 *     (*)  -2: 不属于任何频道（全站专题时）；
 *       > 0：频道标识为该值
 * @param columnId - 栏目标识, 默认为 0
 *       0: 当前栏目，如果当前没有栏目则由于限定了频道而获取频道下所有信息；
 *      -1: 不考虑栏目参数（显示所有栏目）；
 *      > 0：栏目标识为该栏目。  (以后考虑支持多栏目)
 * @param includeChild - 是否包含子栏目（true, 1：包含；false, 0：不包含。默认为 true 。）
 *        (此参数通常和 columnId 配合使用)
 * @param specialId - 所属专题, 默认为 0 
 *       0: 当前专题，如果没有当前专题则不考虑专题
 *      -1：当前频道的所有专题，如果没有频道则不考虑专题
 *      -2: 不对专题考虑；
 *      > 0：专题标识为该值。 (以后考虑支持多专题)
 * @param itemNum - 文章数目
 * @param isTop
 * @param isCommend - 是否推荐文章
 * @param isElite - 是否精华文章（如果为空，不处理。默认为空。）
 * @param isHot - 是否热门文章（如果为空，不处理。默认为空。）
 * @param author - 作者姓名（如果为空，不处理。默认为空。）
 * @param inputer - 录入者（默认为空）
 * @param orderBy;	排序方法（id asc, hits asc, ... 默认为 id desc 。）
 *      
 * 分页参数：      

 * 数据显示部分的参数。
 * @param labelDesc;	标签描述。
 * @param colNum;	每行列数（1：每一列就换行。默认为 1 。）
 * @param lastModified;	更新日期格式（默认为 ''。取值为 'yyyy-MM-dd hh:mm:ss'）
 * @param showColumn;	是否显示所属栏目（0：否；1：是。默认为 0 。）
 * @param showPicArticle;	是否显示“图文”标志（0：否；1：是。默认为 0 。）
 * @param showAuthor;	是否显示作者（0：否；1：是。默认为 0 。）
 * @param showHits;	是否显示点击次数（0：否；1：是。默认为 0 。）
 * @param showHot;	是否显示热点文章标志（0：否；1：是。默认为 0 。）
 * @param showNew;	是否显示最新文章标志（0：否；1：是。默认为 0 。）
 * @param showDescription;	是否显示提示信息（0：否；1：是。默认为 0 。）
 * @param showComment;	是否显示评论链接（0：不显示；1：显示。默认为 0 。）
 * @param usePage;	是否显示分页链接（0：否；1：是。默认为 0 。）
 * @param openType;	打开方式（0：在原窗口打开；1：在新窗口打开。默认为 0 。）
 * @param showType(以 template 属性替代);	显示样式（0：普通列表, 缺省的。；1：表格式 table ；2：各项独立式 p ；3：输出DIV格式。）
 * @param picType;	文章属性图片样式，普通，推荐，固顶三种图片样式（0：不显示；1：符号；2：小图片1；3：小图片2；4：小图片3；5：小图片4；6：小图片5；7：小图片6；8：小图片7；9：小图片8；10：小图片9。）
 * @param style;	样式表（显示的更多风格、样式。）
 * @param class1;	奇数行的样式。
 * @param class2;	偶数行的样式。
 *
 * @author liujunxing
 *
 * (*) 表示当前暂时不支持, 作为 0 处理。
 */
public abstract class AbstractShowItemListHandler3 extends AbstractLabelHandler2 implements LabelHandler2 {
	
	/**
	 * 查询选项
	 */
	protected ItemQueryOption query_option;
	
	/** 提供给标签中使用的选项对象。 */
	protected Map<String, Object> options;
	
	/** 分页信息。*/
	protected PaginationInfo page_info;
	
	/** 实际数据，其是 getData() 查询出来的结果，一般是一个 List。 */
	protected Object item_list;

	/** 当前频道。 */
	protected Channel channel;
	
	/** 是否显示分页。 */
	protected boolean usePage = false; 
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
	 */
	@Override public int handleLabel() {
		// 第一步：构造查询选项，标签选项，构造分页对象。
		buildQueryOption();
		initPageInfo();
		buildLabelOption();
		
		// 查询数据。
		this.item_list = getData();
		
		// 执行输出。
		return process();
	}

	// 初始化 ItemQueryOption 的 channelId 参数
	private void initQueryChannelId() {
		ChannelParam channel_param = super.getChannelAttrib();
		this.channel = channel_param.channel;
		query_option.channelId = channel_param.channelId;
		/*
		int channelId = coll.safeGetIntAttribute("channelId", 0);
		String channelId_string = coll.safeGetStringAttribute("channelId", "");
		if (channelId_string == null || channelId_string.length() == 0 ||  "current".equals(channelId_string)) {
			// 使用当前频道
			this.channel = getCurrentChannel();
			channelId = 0;
		} else {
			if (channelId > 0)
				this.channel = pub_ctxt.getSite().getChannel(channelId);
			else if (channelId == 0)
				this.channel = getCurrentChannel();
		}
		// TODO: 处理更多种类的 channelId 参数
		channelId = (this.channel == null) ? 0 : channel.getId();
		query_option.channelId = channelId;
		*/
	}
	
	// 初始化 ItemQueryOption 的 columnId 参数。
	private void initQueryColumnId(AttributeCollection coll) {
		// 获得栏目参数，但是当前只用第一个。
		ColumnParam column_param = super.getColumnAttrib();
		query_option.columnId = column_param.column_ids.get(0);
		
		/*
		int columnId = coll.safeGetIntAttribute("columnId", 0);
		String columnId_string = coll.safeGetStringAttribute("columnId", "");
		// 'current' - 缺省，取当前频道。
		if (columnId_string == null || columnId_string.length() == 0 ||
				"current".equals(columnId_string)) {
			Column column = getCurrentColumn();
			// 如果没有当前频道，则忽略频道设置。
			columnId = (column == null) ? 0 : column.getId();
		} else if ("ignore".equals(columnId_string)) {
			// 'ignore' - 忽略栏目设置，等同于 columnId='-1'。
			columnId = 0;
		} else if (columnId == 0) {
			Column column = getCurrentColumn();
			columnId = (column == null) ? 0 : column.getId();
		} else if (columnId == -1)
			columnId = 0;
		// else - 指定的栏目标识, 不验证栏目是否存在, 也不验证栏目是否属于指定频道。
		
		query_option.columnId = columnId;
		*/
	}
	
	// 初始化 ItemQueryOption 的 specialId 参数。
	private void initQuerySpecialId(AttributeCollection coll) {
		int specialId = coll.safeGetIntAttribute("specialId", 0);
		String specialId_string = coll.safeGetStringAttribute("specialId", "");
		if (specialId_string == null || specialId_string.length() == 0 || "current".equals(specialId_string)) {
			// 'current' - 当前专题，获取当前专题的。
			SpecialWrapper special = super.getCurrentSpecial();
			specialId = (special == null) ? 0 : special.getId();
		} else if ("ignore".equals(specialId_string)) {
			// 'ignore' - 忽略当前专题。
			specialId = 0;
		} else if (specialId == 0) {		// 当前专题
			SpecialWrapper special = super.getCurrentSpecial();
			specialId = (special == null) ? 0 : special.getId();
		} else if (specialId == -1) {	// 当前频道下所有专题 
			// TODO:
			if (channel == null)
				specialId = 0;
		} else if (specialId == -2)	// 不考虑专题
			specialId = 0;
		query_option.specialId = specialId;
	}
	
	/**
	 * 构造查询选项。派生类可以重载以构造适合自己的特定选项，但必须先调用基类的函数。
	 */
	protected void buildQueryOption() {
		AttributeCollection coll = label.getAttributes();
		this.options = coll.attrToOptions();
		this.query_option = new ItemQueryOption();
		
		// channelId
		initQueryChannelId();
		
		// columnId
		initQueryColumnId(coll);
		
		// includeChild
		boolean includeChild = coll.safeGetBoolAttribute("includeChild", true);
		query_option.includeChildColumns = includeChild;

		// specialId
		initQuerySpecialId(coll);
		
		// itemNum
		int itemNum = coll.safeGetIntAttribute("itemNum", 0);
		if (itemNum == 0 && query_option.channelId > 0 && query_option.columnId > 0) {
			Channel channel = pub_ctxt.getSite().getChannel(query_option.channelId);
			if (query_option.columnId != channel.getRootColumnId()) {
				Column column = channel.getColumnTree().getColumn(query_option.columnId);
				if (column != null)
					itemNum = column.getMaxPerPage();
			}
		}
		if (itemNum <= 0) itemNum = 20;
		options.put("itemNum", itemNum);
		query_option.itemNum = itemNum;
		
		// isTop
		query_option.isTop = coll.safeGetBoolAttribute("isTop", null);
		// isCommend
		query_option.isCommend = coll.safeGetBoolAttribute("isCommend", null);
		// isElite
		query_option.isElite = coll.safeGetBoolAttribute("isElite", null);
		// isHot
		query_option.isHot = coll.safeGetBoolAttribute("isHot", null);
		
		// author
		query_option.author = coll.safeGetStringAttribute("author", null);
		// inputer
		query_option.author = coll.safeGetStringAttribute("inputer", null);
		
		// status
		query_option.status = Item.STATUS_APPR;		// 获取审核通过的。
		// deleted
		query_option.isDeleted = false;				// 未删除的正常项目。
		
		// dateScope
		// int dateScope = coll.safeGetIntAttribute("dateScope", 0);
		// options.put("dateScope", dateScope);
		// orderBy
		// options.put("orderBy", coll.safeGetStringAttribute("orderType", "id desc").trim());
		query_option.orderType = coll.safeGetIntAttribute("orderType", 0);
	}
	
	/**
	 * 初始化分页信息。
	 *
	 */
	protected void initPageInfo() {
		// 是否显示分页。
		this.usePage = label.getAttributes().safeGetBoolAttribute("usePage", false);

		// 如果分页，则尝试获取系统给定的分页信息。
		if (this.usePage) {
			this.page_info = (PaginationInfo)env.findVariable(PAGINATION_INFO_NAME);
			if (this.page_info != null)
			{
				this.page_info = this.page_info.clone();
			}
		}
		if (this.page_info == null) {
			// 如果系统未给定，则创建一个，其获取第一页，itemNum 个项目。
			page_info = new PaginationInfo();
			page_info.setCurrPage(1);
			page_info.setPageSize(query_option.itemNum);
		}
		this.page_info.setUsePage(usePage);
		
		// 尽量初始化必要的信息。
		if (this.channel != null) {
			page_info.setItemName(channel.getItemName());
			page_info.setItemUnit(channel.getItemUnit());
		}
	}

	/**
	 * 派生类必须实现的，查询数据。
	 * @return
	 */
	protected abstract Object getData();

	/**
	 * 执行数据模板组合。派生类可以重载，进行不同的数据处理。
	 * @return
	 */
	protected int process() {
		LocalContext local_ctxt = env.acquireLocalContext(label, 0);
		
		// 分页的 HTML 代码。
		if (label.hasChild()) {
			setLocalContextVariable(local_ctxt);
			return PROCESS_DEFAULT;
		} else {
			// 取得内建 Label。
			String template_name = super.getTemplateName(getBuiltinName());
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			// 执行这个内建标签。
			return builtin_label.process(env, new Object[]{item_list, options, page_info});
		}
	}

	/**
	 * 根据 label.attributes 创建传递给 label 的 option
	 */
	protected void buildLabelOption() {
		AttributeCollection coll = label.getAttributes();
		
		// ***** 显示相关部分。 ***********************
		// labelDesc
		String labelDesc = coll.getNamedAttribute("labelDesc");
		options.put("labelDesc", labelDesc);
		// colNum
		int colNum = coll.safeGetIntAttribute("colNum", 1);
		if (colNum < 1) {
			colNum = 1;
		}
		options.put("colNum", colNum);
		// showColumn
		options.put("showColumn", coll.safeGetBoolAttribute("showColumn", false));

		// showAuthor
		options.put("showAuthor", coll.safeGetBoolAttribute("showAuthor", false));
		// lastModified - 修改时间格式
		options.put("lastModified", coll.safeGetStringAttribute("lastModified", null));
		// showHits
		Boolean showHits = coll.safeGetBoolAttribute("showHits", false);
		options.put("showHits", showHits);
		// showHot
		options.put("showHot", coll.safeGetBoolAttribute("showHot", false));
		// showNew
		options.put("showNew", coll.safeGetBoolAttribute("showNew", false));
		// showDescription
		options.put("showDescription", coll.safeGetBoolAttribute("showDescription", false));
		
		// usePage
		options.put("usePage", coll.safeGetBoolAttribute("usePage", false));
		
		// openType
		options.put("openType", OpenType.fromString(coll.safeGetStringAttribute("openType", null)));
		// showType
		options.put("showType", coll.safeGetIntAttribute("showType", 0));
		// picType
		options.put("picType", coll.safeGetIntAttribute("picType", 0));
		// style
		options.put("style", coll.safeGetStringAttribute("style", "").trim());
		// showComment
		options.put("showComment", coll.safeGetBoolAttribute("showComment", false));
		// class1
		options.put("class1",  coll.safeGetStringAttribute("class1", "").trim());
		// class2
		options.put("class2", coll.safeGetStringAttribute("class2", "").trim());
	}

	/**
	 * 在有子节点的时候，为了支持循环而设置的变量映射。包括 Repeater, PageNav 支持的变量设置。
	 *   派生类重载时候必须先调用基类的设置，然后设置自己的特定支持。
	 * 缺省将设置如下三个变量：
	 *  <li>item_list - 查询出来的项目列表。
	 *  <li>options - 标签的属性参数，已经经过了类型强化处理。
	 *  <li>page_info - 分页信息。
	 * @param local_ctxt
	 */
	protected void setLocalContextVariable(LocalContext local_ctxt) {
		local_ctxt.setVariable("item_list", this.item_list);
		local_ctxt.setVariable("options", this.options);
		local_ctxt.setVariable("page_info", this.page_info);
		
		super.addPageNavSupport(local_ctxt, page_info);
		super.addRepeaterSupport(local_ctxt, "item", item_list);
	}

	/**
	 * 调试输出 Query 对象的信息到页面和 logger 中。
	 * @param query
	 */
	protected void debugOutputQuery(QueryHelper query) {
	}
}
