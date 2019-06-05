package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 用于获取项目的参数对象，。
 * 
 * @author liujunxing
 * 
 */
public class ItemQueryOption {
	
	/** 频道的标识，必须存在该频道。 (= 0 表示任何频道) */
	public int channelId = 0;
	
	/** 栏目的标识，= 0 表示所有栏目(等于不考虑栏目)。 */
	public int columnId = 0;
	
	// TODO: public int moduleId;
	
	/** 要查询的项目类型，如果不给出，则查询所有 'item', 否则假定 itemClass 就是对象(如 article, photo 等) */
	public String itemClass = null;
	
	/**  是否包含该栏目下的所有子栏目, 默认 = false 不包含。 */
	public boolean includeChildColumns = false;
	
	/** 专题标识, = 0 表示不考虑专题，> 0 为专题标识。 */
	public int specialId = 0;
	
	/** 缺省获得多少个项目，其规定了获取多少个项目。 */
	public int itemNum = 0;
	
	/** 文章的状态，null - 所有文章；0 - 待审核的文章；1 - 已审核的文章。默认为 1 为审核已经通过的。 */
	public Integer status = Item.STATUS_APPR;
	
	/** 固顶文章，默认为null，不设置此条件。 */
	public Boolean isTop = null;
	
	/** 推荐文章，默认为null，不设置此条件。 */
	public Boolean isElite = null;
	
	/** 热门文章，默认为null，不设置此条件。 */
	public Boolean isHot = null;
	
	/** 是否是推荐文章，默认为 null，不设置此条件。 */
	public Boolean isCommend = null;
	
	/** 是否生成。默认为null，不设置此条件。 */
	public Boolean isGenerated = null;

	/** 是否被删除标志。默认为 false，为正常项目； = true 表示是被删除项目。 = null 则获取的项目可能包含删除和未删除的。 */
	public Boolean isDeleted = false;
	
	/** 文章录入者，默认为null，不处理。 */
	public String inputer = null;
	
	/** 文章作者，默认为 null, 不处理。 */
	public String author = null;
	
	/** 文章搜索选项域。 */
	public String field = null;
	
	/** 文章搜索关键字。 */
	public String keyword = null;
	
	/** 缺省排序方式： 'id DESC' = 0 */
	public static final int ORDER_TYPE_DEFAULT = 0;
	/** 排序方式 'id DESC' 也就是缺省排序方式 = 0。 */
	public static final int ORDER_TYPE_ID_DESC = 0;
	/** 排序方式 'id ASC' = 1。 */
	public static final int ORDER_TYPE_ID_ASC = 1;
	/** 排序方式 'lastModified DESC' = 2。 */
	public static final int ORDER_TYPE_LASTMODIFIED_DESC = 2;
	/** 排序方式 'lastModified ASC' = 3。 */
	public static final int ORDER_TYPE_LASTMODIFIED_ASC = 3;
	/** 排序方式 'hits DESC' = 4。 */
	public static final int ORDER_TYPE_HITS_DESC = 4;
	/** 排序方式 'hits ASC' = 5。 */
	public static final int ORDER_TYPE_HITS_ASC = 5;
	
	/** 排序方式。 */
	public int orderType = 0;
	
	/** 要查询的项目的别名，在部分查询中通过别名要区分字段所属对象。 */
	public String alias = null;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString() {
		StringBuilder strbuf = new StringBuilder();
		strbuf.append("ItemQueryOption{")
			.append("channelId=").append(channelId)
			.append(",columnId=").append(columnId)
			.append(",includeChildColumns=").append(includeChildColumns)
			.append(",specialId=").append(specialId)
			.append(",itemClass=").append(itemClass)
			.append(",status=").append(status)
			.append(",isTop=").append(isTop)
			.append(",isElite=").append(isElite)
			.append(",isHot=").append(isHot)
			.append(",isCommend=").append(isCommend)
			.append(",isGenerated=").append(isGenerated)
			.append(",isDeleted=").append(isDeleted)
			.append(",inputer=").append(inputer)
			.append(",author=").append(author)
			.append(",field=").append(field)
			.append(",keyword=").append(keyword)
			.append("}");
		return strbuf.toString();
	}
	
	/**
	 * 使用此对象代表的选项创建 QueryHelper ，其具有正确的 whereClause 和 orderClause，select, from 子句不设置。
	 * @return TODO: columnId 未处理
	 */
	public QueryHelper initQueryHelper(QueryHelper query, PublishContext pub_ctxt) {
		// 创建适当的 where 条件。
		handleChannelParam(query);
		handleColumnParam(query, pub_ctxt);
		handleSpecialParam(query, pub_ctxt);
		handleStatusParam(query);
		handleTopEtcParam(query);		// isHot, isTop, isDeleted etc.
		handleInputerParam(query);
		handleAuthorParam(query);
		handleKeywordParam(query);
		
		// 根据 ordertype 设置适当的 order 条件。
		query.addOrder(getOrderString());

		return query;
	}
	
	private String getOrderString() {
		switch (this.orderType) {
		/** 排序方式 'id ASC' = 1。 */
		case ORDER_TYPE_ID_ASC: return "id ASC";
		/** 排序方式 'lastModified DESC' = 2。 */
		case ORDER_TYPE_LASTMODIFIED_DESC: return "lastModified DESC";
		/** 排序方式 'lastModified ASC' = 3。 */
		case ORDER_TYPE_LASTMODIFIED_ASC: return "lastModified ASC";
		/** 排序方式 'hits DESC' = 4。 */
		case ORDER_TYPE_HITS_DESC: return "hits DESC";
		/** 排序方式 'hits ASC' = 5。 */
		case ORDER_TYPE_HITS_ASC: return "hits ASC";
		/** 缺省排序方式： 'id DESC' = 0 */
		case ORDER_TYPE_DEFAULT: 
		default: return "id DESC";
		}
	}

	// 获得指定字段的全限定名，通过给定 alias 值可能不同。
	private String fsqn(String field) {
		if (alias == null || alias.length() == 0) return field;
		return alias + "." + field;
	}
	
	/** 处理频道参数。 */
	private void handleChannelParam(QueryHelper query) {
		if (this.channelId > 0)
			query.addAndWhere(fsqn("channelId") + " = " + this.channelId);
	}
	
	/** 处理 columnId, includeChildColumns 参数 */
	private void handleColumnParam(QueryHelper query, PublishContext pub_ctxt) {
		// columnId == 0 时候不考虑栏目参数。
		if (this.columnId == 0) return;
		
		int columnId = this.columnId;
		// 如果不含子栏目。
		if (this.includeChildColumns == false) {
			query.addAndWhere(fsqn("columnId") + " = " + columnId);
			return;
		}

		// 尝试加载此栏目。
		Column column = pub_ctxt.getSite().loadColumn(columnId, null);
		if (column == null) return;			// 栏目不存在。
		
		// 我们需要一个频道来操作 Column
		Channel channel = column.getChannel();
		if (channel == null) return;		// 无法处理，无法获取频道。
		
		ColumnTree column_tree = channel.getColumnTree();
		List<Integer> ids = column_tree.getAllChildColumnIds(column);
		if (ids == null || ids.size() == 0) {
			query.addAndWhere(fsqn("columnId") + " = " + columnId);
			return;
		}
		// 含有子栏目，示例：  columnId IN (1,3,47)
		query.addAndWhere(fsqn("columnId") + " IN (" + columnId + 
			"," + PublishUtil.toSqlInString(ids) + ") ");
	}

	/** 处理 specialId 参数。 */
	private void handleSpecialParam(QueryHelper query, PublishContext pub_ctxt) {
		if (specialId == 0) return;
		query.addAndWhere(fsqn("id") + " IN (" +
		  " SELECT itemId FROM RefSpecialItem WHERE specialId = " + specialId + ")");
	}
	
	/** 处理 status 参数 */
	private void handleStatusParam(QueryHelper query) {
		if (this.status != null)
			query.addAndWhere(fsqn("status") + " = " + this.status);
	}
	
	/** 处理 isTop, isElite, isHot, isRecommend, isGenerated, isDeleted 参数。 */
	private void handleTopEtcParam(QueryHelper query) {
		// isTop
		if (this.isTop != null)
			query.addAndWhere(fsqn("top") + " = " + this.isTop);
		// isElite
		if (this.isElite != null)
			query.addAndWhere(fsqn("elite") + " = " + this.isElite);
		// isHot
		if (this.isHot != null)
			query.addAndWhere(fsqn("hot") + " = " + this.isHot);
		// isRecoomend
		if (this.isCommend != null)
			query.addAndWhere(fsqn("commend") + " = " + this.isCommend);
		// isGenerated
		if (this.isGenerated != null)
			query.addAndWhere(fsqn("isGenerated") + " = " + this.isGenerated);
		// isDeleted
		if (this.isDeleted != null)
			query.addAndWhere(fsqn("deleted") + " = " + this.isDeleted);
	}
	
	/** 处理 inputer 参数 */
	private void handleInputerParam(QueryHelper query) {
		if (this.inputer != null && this.inputer.length() > 0) {
			query.addAndWhere(fsqn("inputer") + " = :inputer");
			query.setString("inputer", this.inputer);
		}
	}
	
	/** 处理 author 参数。 */
	private void handleAuthorParam(QueryHelper query) {
		if (this.author != null && this.author.length() > 0) {
			query.addAndWhere(fsqn("author") + " = :author");
			query.setString("author", this.author);
		}
	}
	
	/** 处理 field 搜索 */
	private void handleKeywordParam(QueryHelper query) {
		if (this.field == null || this.field.length() == 0) return;
		if (this.keyword == null || this.keyword.length() == 0) return;
		
		// 校验 field 只能是有限字段—— title, content, author, inputer
		if ("title".equals(this.field) ||
				"content".equals(this.field) ||
				"author".equals(this.field) ||
				"inputer".equals(this.field)) {
			query.addAndWhere(fsqn(this.field) + " LIKE :keyword"); 
			query.setString("keyword", "%" + this.keyword + "%");
		}
	}

}
