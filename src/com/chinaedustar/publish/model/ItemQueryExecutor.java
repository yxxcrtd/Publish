package com.chinaedustar.publish.model;

import java.util.ArrayList;
import java.util.List;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * ItemBusinessObject 的高级版本，提供不限定项目(item, article, photo, soft...)类型的查询能力
 */
@SuppressWarnings("rawtypes")
public class ItemQueryExecutor {

	/**
	 * 环境对象
	 */
	private final PublishContext pub_ctxt;

	/**
	 * 构造函数
	 * 
	 * @param pub_ctxt
	 */
	public ItemQueryExecutor(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}

	public static final java.util.ArrayList<Object> EMPTY_ARRAY_LIST = new ArrayList<Object>();

	/**
	 * 使用指定的选项、分页信息查询项目。项目类型不限制, 可以分别在 Article, Photo, Soft 里面。 优化的实现能够在限定了项目类型时以更好的方式进行查询。
	 * 
	 * @return 返回项目集合，以及设置合适的 page_info
	 */
	public List getAdvItemList(ItemQueryOption option, PaginationInfo page_info) {
		// 第一步：查询项目的标识和类型
		QueryHelper query = new QueryHelper();
		option.initQueryHelper(query, pub_ctxt);

		this.debug_query = query;

		// this.param_util = new ParamUtil(page_ctxt);
		// int page=this.param_util.safeGetIntParam("page");
		// if(page>0)
		// page_info.setCurrPage(page);

		// 第二步：如果限定了查询项目类型，则直接查询
		if (option.itemClass != null && option.itemClass.length() > 0) {
			return limitItemClassQuery(query, option, page_info);
		}

		// 第三步：未限定类型的时候，使用复杂查询。
		return unlimitItemClassQuery(query, option, page_info);
	}

	/**
	 * 带有项目限定类型的查询，我们直接查询该类型项目，而不是多次查询
	 * 
	 * @param query
	 * @param page_info
	 * @return
	 */
	private List limitItemClassQuery(QueryHelper query, ItemQueryOption option, PaginationInfo page_info) {
		String temp = option.itemClass;
		
		if ("student".equals(option.itemClass) || "rstudent".equals(option.itemClass)) {
			option.itemClass = "student";
		}
		
		query.fromClause = " FROM " + StringHelper.capFirst(option.itemClass) + " ";
		
		// 判断是本科生还是研究生
		if ("student".equals(temp)) {
			query.whereClause = "WHERE S_IsFinishSchool = 0 AND S_Type = 'Student'";
		} else if ("rstudent".equals(temp)) {
			query.whereClause = "WHERE S_IsFinishSchool = 0 AND S_Type <> 'Student'";
			query.orderClause = "ORDER BY S_Number";
		}
		
		long total_count = query.queryTotalCount(pub_ctxt.getDao());
		page_info.setTotalCount(total_count);
		page_info.init();
		if (total_count == 0) {
			// 如果没有任何项目则直接返回这样比较快。
			return EMPTY_ARRAY_LIST;
		}

		// 直接查询项目并返回。
		List result = query.queryData(pub_ctxt.getDao(), page_info);
		PublishUtil.initModelList(result, pub_ctxt, pub_ctxt.getSite());

		return result;
	}

	/**
	 * 未限定项目类型的查询，会多次查询以获得结果
	 * 
	 * @param query
	 * @param page_info
	 * @return
	 */
	private List unlimitItemClassQuery(QueryHelper query, ItemQueryOption option, PaginationInfo page_info) {
		// 初始状态查询所有项目
		query.selectClause = " SELECT id, itemClass ";
		query.fromClause = " FROM Item ";

		
		page_info.setPageSize(option.itemNum);
		
		long total_count = query.queryTotalCount(pub_ctxt.getDao());
		page_info.setTotalCount(total_count);
		page_info.init();
		
		// 如果没有任何项目则直接返回这样比较快
		if (total_count == 0) {
			return EMPTY_ARRAY_LIST;
		}

		List list = query.queryData(pub_ctxt.getDao(), page_info);
		
		// 如果没有查到任何数据，则直接返回
		if (list.size() == 0)
			return EMPTY_ARRAY_LIST;

		// 第二步：根据 id, itemClass 查询子类项目, 每种类型进行一次子查询
		List<ItemIdClass> list_id_class = toItemIdClass(list);
		queryItemIdClass(list_id_class);

		// 第三步：组装 List<Item> 返回
		List<Object> list_item = combineItemList(list_id_class);

		return list_item;
	}

	// getAdvItemList 辅助类
	private static final class ItemIdClass {
		
		public ItemIdClass(Integer id, String itemClass) {
			this.id = id;
			this.itemClass = itemClass;
		}

		public Integer id;
		public String itemClass;
		public Object item;
	}

	private QueryHelper debug_query;

	/** DEBUG 方法： 获取在 getAdvItemList() 时候创建的 QueryHelper 对象。 */
	public QueryHelper getAdvItemQueryHelper() {
		return debug_query;
	}

	// 组装 List<Item> 返回。
	private List<Object> combineItemList(List<ItemIdClass> list_id_class) {
		List<Object> list_item = new java.util.ArrayList<Object>();
		for (int i = 0; i < list_id_class.size(); ++i) {
			if (list_id_class.get(i).item != null)
				list_item.add(list_id_class.get(i).item);
		}
		PublishUtil.initModelList(list_item, pub_ctxt, pub_ctxt.getSite());
		return list_item;
	}

	// getAdvItemList 辅助函数
	private List<ItemIdClass> toItemIdClass(List list) {
		List<ItemIdClass> list_id_class = new java.util.ArrayList<ItemIdClass>();
		for (int i = 0; i < list.size(); ++i) {
			Object[] data = (Object[]) list.get(i);
			list_id_class.add(new ItemIdClass((Integer) data[0],
					(String) data[1]));
		}
		return list_id_class;
	}

	/**
	 * getAdvItemList 辅助函数
	 * 
	 * @param list_id_class
	 */
	private void queryItemIdClass(List<ItemIdClass> list_id_class) {
		List<ItemIdClass> list_unhandled = new ArrayList<ItemIdClass>();
		list_unhandled.addAll(list_id_class);

		while (list_unhandled.size() > 0) {
			// 找到一组未处理的 id, itemClass
			List<Integer> ids = new ArrayList<Integer>();
			String itemClass = list_unhandled.get(0).itemClass;
			ids.add(list_unhandled.get(0).id);
			for (int i = list_unhandled.size() - 1; i > 0; --i) {
				if (itemClass.equals(list_unhandled.get(i).itemClass)) {
					ids.add(list_unhandled.get(i).id);
					list_unhandled.remove(i); // 从未处理队列中删除掉。
				}
			}
			list_unhandled.remove(0);
			
			// 查询该组对象
			String hql = " FROM " + StringHelper.capFirst(itemClass);
			
			hql += " WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
			
			List items = pub_ctxt.getDao().list(hql);
			

			// 将这组对象放到 list_id_class 里面，id 要匹配
			for (int i = 0; i < items.size(); ++i) {
				ModelObject model_obj = (ModelObject) items.get(i); // 至少要支持 ModelObject 接口
				for (int j = 0; j < list_id_class.size(); ++j) {
					ItemIdClass iic = list_id_class.get(j);
					if (iic.id == model_obj.getId()) {
						iic.item = model_obj;
						break;
					}
				}
			}
		}
	}

	// === getPicArticleList
	// ============================================================

	/**
	 * 获得指定选项的图片文章列表 (文章对象有缺省图片) 此方法取代了原来 ItemBusiness.getPicItemList() 方法。
	 * 
	 * @param option
	 *            - 查询选项。
	 * @param page_info
	 *            - 分页属性。
	 * @return 返回 List&lt;Article&gt; 集合。
	 */
	public List<Article> getPicArticleList(ItemQueryOption option,
			PaginationInfo page_info) {
		// 构造 QueryHelper.
		QueryHelper query = new QueryHelper();
		option.initQueryHelper(query, pub_ctxt);

		// 添加额外的条件, 此条件对于查询带有图片的文章适用。
		query.fromClause = " FROM Article ";
		query.addAndWhere("(defaultPicUrl != '' AND NOT defaultPicUrl IS NULL)");

		// 进行查询。
		long total_count = query.queryTotalCount(pub_ctxt.getDao());
		page_info.setTotalCount(total_count);
		if (total_count == 0)
			return new java.util.ArrayList<Article>();

		@SuppressWarnings("unchecked")
		List<Article> result = (List<Article>) query.queryData(
				pub_ctxt.getDao(), page_info);
		PublishUtil.initModelList(result, pub_ctxt, pub_ctxt.getSite());

		this.debug_query = query;
		return result;
	}

	/**
	 * 获得具有指定选项的带有缩略图的软件列表，软件对象有缩略图属性。 (也许以后我们应该为 Item 统一提供 thumbPic 属性)
	 * 
	 * @param option
	 * @param page_info
	 * @return
	 */
	public List<Soft> getPicSoftList(ItemQueryOption option,
			PaginationInfo page_info) {
		// 构造 QueryHelper.
		QueryHelper query = new QueryHelper();
		option.initQueryHelper(query, pub_ctxt);

		// 添加额外的条件, 此条件对于查询带有缩略图的软件适用。
		query.fromClause = " FROM Soft ";
		query.addAndWhere("(thumbPic != '' AND NOT thumbPic IS NULL)");

		// 进行查询。
		long total_count = query.queryTotalCount(pub_ctxt.getDao());
		page_info.setTotalCount(total_count);
		if (total_count == 0)
			return new java.util.ArrayList<Soft>();

		@SuppressWarnings("unchecked")
		List<Soft> result = (List<Soft>) query.queryData(pub_ctxt.getDao(),
				page_info);
		PublishUtil.initModelList(result, pub_ctxt, pub_ctxt.getSite());

		this.debug_query = query;
		return result;
	}
}
