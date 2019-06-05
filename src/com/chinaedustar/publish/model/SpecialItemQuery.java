package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.*;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 专题内项目查询器。
 * 
 * @author liujunxing
 *
 */
public class SpecialItemQuery {
	/** 系统环境。 */
	private PublishContext pub_ctxt;
	
	/**
	 * 
	 * @param pub_ctxt
	 */
	public SpecialItemQuery(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	/**
	 * 得到含有专题信息的文章列表信息，支持分页，用于专题文章管理中。
	 * @param option - 查询选项，其中支持选项 channelId, specialId, isHot, isElite, isTop, status, keyword, field。
	 * @param page_info - 分页信息。
	 * @return 返回以 DataTable 形式组装的数据，同时包括 page_info 数据。
	 * @schema
	 *  <li>"refid" - 专题引用标识 (RefSpecialItem.id)
	 *  <li>"id" - 项目(文章,软件,图片)标识 (Item.id)
	 *  <li>"specialId" - 专题标识(Special.id)
	 *  <li>"specialName" - 专题名称(Special.name)
	 *  <li>"columnId" - 项目所属栏目(Item.columnId)
	 *  其它字段请参见 ARTICLE_PROP_LIST 
	 */
	public DataTable getSpecialArticles(ItemQueryOption option, PaginationInfo page_info) {
		// 构造查询。
		SpecialQueryHelper query = initArticleQuery(option);
		
		return getItemList(query, page_info);
	}

	/**
	 * 得到含有专题信息的图片列表信息，支持分页，用于专题图片管理中。
	 * @param option - 查询选项，其中支持选项 channelId, specialId, isHot, isElite, isTop, status, keyword, field。
	 * @param page_info - 分页信息。
	 * @return 返回以 DataTable 形式组装的数据，同时包括 page_info 数据。
	 * @schema
	 *  <li>"refid" - 专题引用标识 (RefSpecialItem.id)
	 *  <li>"id" - 项目(文章,软件,图片)标识 (Item.id)
	 *  <li>"specialId" - 专题标识(Special.id)
	 *  <li>"specialName" - 专题名称(Special.name)
	 *  <li>"columnId" - 项目所属栏目(Item.columnId)
	 *  其它字段请参见 PHOTO_PROP_LIST 
	 */
	public DataTable getSpecialPhotos(ItemQueryOption option, PaginationInfo page_info) {
		// 构造查询。
		SpecialQueryHelper query = initPhotoQuery(option);

		return getItemList(query, page_info);
	}
	
	/**
	 * 得到含有专题信息的软件列表信息，支持分页，用于专题软件管理中。
	 * @param option - 查询选项，其中支持选项 channelId, specialId, isHot, isElite, isTop, status, keyword, field。
	 * @param page_info - 分页信息。
	 * @return 返回以 DataTable 形式组装的数据，同时包括 page_info 数据。
	 * @schema
	 *  <li>"refid" - 专题引用标识 (RefSpecialItem.id)
	 *  <li>"id" - 项目(文章,软件,图片)标识 (Item.id)
	 *  <li>"specialId" - 专题标识(Special.id)
	 *  <li>"specialName" - 专题名称(Special.name)
	 *  <li>"columnId" - 项目所属栏目(Item.columnId)
	 *  其它字段请参见 SOFT_PROP_LIST 
	 */
	public DataTable getSpecialSofts(ItemQueryOption option, PaginationInfo page_info) {
		// 构造查询。
		SpecialQueryHelper query = initSoftQuery(option);

		return getItemList(query, page_info);
	}
	
	
	// 根据 Query 对象查询数据，并组装为 DataTable.
	@SuppressWarnings("rawtypes")
	private DataTable getItemList(SpecialQueryHelper query, PaginationInfo page_info) {
		// 查询总数。
		page_info.setTotalCount(query.queryTotalCount(pub_ctxt.getDao()));

		// 构造数据表。
		DataSchema schema = new DataSchema(query.field_string);
		DataTable data_table = new DataTable(schema);
		if (page_info.getTotalCount() == 0) return data_table;
		
		// 查询真正数据。
		List list = query.queryData(pub_ctxt.getDao(), page_info);
		PublishUtil.addToDataTable(list, data_table);
		return data_table;
	}
	
	// 辅助查询对象，用于承载更多信息。
	private static final class SpecialQueryHelper extends QueryHelper {
		public String field_string;
	}
	
	// RefSpecialItem 一般查询的字段列表。
	public static final String RSI_PROP_LIST = 
		"R.id AS refid, R.itemId AS id, R.specialId, S.name AS specialName";
	
	// Item 公共字段列表。
	public static final String ITEM_PROP_LIST = 
		"I.columnId, I.channelId, I.lastModified, I.title, I.author, I.inputer, " +
		"I.editor, I.source, I.hits, I.stars, I.top, I.elite, I.status, " +
		"I.keywords, I.isGenerated, I.staticPageUrl";
	
	// 文章对象查询的字段列表。
	public static final String ARTICLE_PROP_LIST =
		RSI_PROP_LIST + ", " + ITEM_PROP_LIST +
		", I.includePic, I.defaultPicUrl";

	// 图片对象查询的字段列表。
	public static final String PHOTO_PROP_LIST =
		RSI_PROP_LIST + ", " + ITEM_PROP_LIST +
		", I.thumbPic";
	
	// 软件对象查询的字段列表。
	public static final String SOFT_PROP_LIST =
		RSI_PROP_LIST + ", " + ITEM_PROP_LIST +
		", I.thumbPic, I.size";

	// 构造查询专题文章所需 QueryHelper 并初始化。
	private SpecialQueryHelper initArticleQuery(ItemQueryOption option) {
		return initItemQuery(option, ARTICLE_PROP_LIST, "Article");
	}
	
	// 构造查询专题图片所需 QueryHelper 并初始化。
	private SpecialQueryHelper initPhotoQuery(ItemQueryOption option) {
		return initItemQuery(option, PHOTO_PROP_LIST, "Photo");
	}

	// 构造查询专题软件所需 QueryHelper 并初始化。
	private SpecialQueryHelper initSoftQuery(ItemQueryOption option) {
		return initItemQuery(option, SOFT_PROP_LIST, "Soft");
	}

	private SpecialQueryHelper initItemQuery(ItemQueryOption option, String prop_list, String itemClass) {
		// 构造我们所需 QueryHelper 并初始化。
		SpecialQueryHelper query = new SpecialQueryHelper();
		query.field_string = prop_list;
		int specialId = option.specialId;
		option.specialId = 0;			// option 不要按照缺省方式处理 specialId
		option.alias = "I";
		query.addAndWhere("R.itemId = I.id");
		query.addAndWhere("R.specialId = S.id");
		
		// 初始化查询条件。
		option.initQueryHelper(query, pub_ctxt);
		query.selectClause = "SELECT " + query.field_string;
		query.fromClause = " FROM RefSpecialItem R, Special S, " + itemClass + " I ";
		if (specialId != 0)
			query.addAndWhere("R.specialId = " + specialId);
		query.orderClause = "ORDER BY R.id DESC";		// ?? 有更好的选择吗??
		
		return query;
	}
}
