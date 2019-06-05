package com.chinaedustar.publish.model;

import java.util.ArrayList;
import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.pjo.Special;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 专题集合对象。
 * 
 * @author liujunxing
 * 
 * TODO: 将所用的 hql 调用全部规范化。
 */
@SuppressWarnings("rawtypes")
public class SpecialCollection extends AbstractCollWithContainer {
	
	/**
	 * 缺省构造函数。
	 */
	public SpecialCollection() {
	}

	// === 业务方法 2 ====================================================
	
	/**
	 * 获得当前频道中的专题数据列表。其包括全站专题 + 本频道专题。
	 * @return 返回一个 DataTable, 其 schema 为 [id, name, channelId], 排序为 channelId, specialOrder
	 * @schema 为 [id, name, channelId]
	 */
	public DataTable getChannelSpecialDataTable(int channelId) {
		// 1. 查询数据。
		String select_fields = "id, name, channelId";
		String hql = "SELECT " + select_fields +
					" FROM Special " +
					" WHERE channelId = 0 OR channelId = " + channelId + 
					" ORDER BY channelId, specialOrder ";
		List list = pub_ctxt.getDao().list(hql);
		
		// 2. 构造 DataTable 并返回。
		DataTable data_table = new DataTable(PublishUtil.columnsToSchema(select_fields));
		PublishUtil.addToDataTable(list, data_table);
		
		return data_table;
	}
	
	// === SpecialCollection 业务 =======================================
	
	/**
	 * 得到指定的专题对象。
	 * @param specialId - 专题的标识。
	 * @return 返回一个专题对象。如果不存在，则返回 null.
	 */
	public SpecialWrapper getSpecial(int specialId) {
		Special special = (Special)_getPublishContext().getDao().get(Special.class, specialId);
		if (special != null)
			return new SpecialWrapper(special, pub_ctxt, this);

		return null;
	}
	
	/**
	 * 获得此容器实例下的专题列表。
	 * 
	 * @return
	 */
	public List<SpecialWrapper> getSpecialList() {
		return this.getSpecialList(this.container.getChannelId(), null, null);
	}
	
	/**
	 * 得到全站或某频道下所有的专题
	 * @return
	 */
	public List<SpecialWrapper> getAllSpecial() {
		int channelId = container.getChannelId();
		return getSpecialList(channelId, null, -1);
	}
	
	/**
	 * 查询专题时候使用的查询条件对象。
	 * @author liujunxing
	 *
	 */
	public static final class SpecialQueryOption {
		/** 所属的频道标识数组, 如果 == null or size() == 0 查询所有的。 */
		public List<Integer> channel_ids = new ArrayList<Integer>();
		
		/** 是否选择推荐的； = null 表示不考虑推荐条件；= true,false 表示获取推荐的。 */
		public Boolean isElite;
		
		/** 起始记录数，-1 表示从第一条记录开始(row = 0)。 */
		public int firstResult = -1;
		
		/** 查询数量, = -1 表示查询所有的。 */
		public int itemNum = -1;
	}
	
	/**
	 * 使用指定查询选项查询专题列表。
	 * @param query_option
	 * @return
	 */
	public List<SpecialWrapper> getSpecialList(SpecialQueryOption query_option) {
		if (query_option == null) return this.getSpecialList();
		// 构造查询。
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Special ";
		if (query_option.channel_ids != null)
			query.addAndWhere(" channelId IN (" + PublishUtil.toSqlInString(query_option.channel_ids) + ")");
		if (query_option.isElite != null)
			query.addAndWhere(" isElite = " + query_option.isElite);
		query.orderClause = " ORDER BY channelId, specialOrder ASC, id DESC ";
		
		// 查询列表，并初始化模型数据。
		@SuppressWarnings("unchecked")
		List<Special> list = query.queryData(pub_ctxt.getDao(), query_option.firstResult, query_option.itemNum);
		List<SpecialWrapper> wrapper_list = new java.util.ArrayList<SpecialWrapper>();
		for (int i = 0; i < list.size(); ++i) {
			wrapper_list.add(new SpecialWrapper(list.get(i), pub_ctxt, this));
		}
		return wrapper_list;
	}
	
	/**
	 * 得到某一频道或网站的专题对象
	 * @param channelId - 频道标识； = 0,-1 表示获取 Site 的专题，= null 表示获取所有专题，
	 *    其它值表示频道标识。
	 * @param isElite - 是否选择推荐的； = null 表示不考虑推荐条件；= true,false 表示获取推荐的。
	 * @param num - 获取的最大数量。 = null 表示获取所有。
	 * @return
	 */
	public List<SpecialWrapper> getSpecialList(Integer channelId, Boolean isElite, Integer num){
		SpecialQueryOption query_option = new SpecialQueryOption();
		if (channelId != null)
			query_option.channel_ids.add(channelId);
		query_option.isElite = isElite;
		query_option.itemNum = num == null ? -1 : num;
		return getSpecialList(query_option);
	}

	/**
	 * 创建一个专题的信息。
	 * 注意：需要事务支持。
	 * @param special 专题对象。
	 */
	public void insertSpecial(SpecialWrapper special) {
		if (special.getId() != 0) {
			updateSpecial(special);
		} else {
			// 保存专题。
			pub_ctxt.getDao().save(special.getTargetObject());
			pub_ctxt.getDao().flush();

			// 创建静态化地址。
			special._init(pub_ctxt, this);
			special.rebuildStaticPageUrl();
			special.updateGenerateStatus();
		}
	}
	
	/**
	 * 更新一个专题的信息。
	 * 注意：需要事务支持。
	 * @param special 专题对象。
	 */
	public void updateSpecial(SpecialWrapper special) {
		if (special.getId() == 0) {
			insertSpecial(special);
		} else {
			
			pub_ctxt.getDao().update(special.getTargetObject());
			pub_ctxt.getDao().flush();

			// 重建静态化地址。
			special._init(_getPublishContext(), this);
			if (special.rebuildStaticPageUrl())
				special.updateGenerateStatus();
		}
	}
	
	/**
	 * 删除指定专题对象。
	 * 注意：需要事务支持。
	 * @param specialId 转体对象的标识。
	 * @return 更新的记录数。
	 */
	public int deleteSpecial(SpecialWrapper special) {
		// 从数据库中删除，需要事务支持。
		int num = clearItems(special);
		
		// 删除专题。
		pub_ctxt.getDao().delete(special.getTargetObject());
		pub_ctxt.getDao().flush();
		
		// 销毁专题。
		if (special != null) {
			special._destroy();
		}
		return num;
	}
	
	/**
	 * 清空指定专题的所有内容项。
	 * @return 更新的记录数。
	 */
	public int clearItems(SpecialWrapper special) {
		// 删除专题、内容项的关联。
		RefSpecialItem refObject = new RefSpecialItem(pub_ctxt);
		return refObject.deleteItems(special.getId());
	}
	
	/**
	 * 添加专题内容项的引用。
	 * @param specialId
	 * @param itemId
	 */
	public void insertRefSpecialItem(int specialId, int itemId) {
		RefSpecialItem rsi = new RefSpecialItem(pub_ctxt);
		rsi.setSpecialId(specialId);
		rsi.setItemId(itemId);
		pub_ctxt.getDao().save(rsi);
	}
	
	/**
	 * 删除指定的专题内容项引用对象。
	 * 注意：需要事务支持。
	 * @param specialId
	 * @param itemId
	 */
	public int deleteRefSpecialItems(List<Integer> refids) {
		if (refids == null || refids.size() == 0) return 0;
		// TODO: 这里没有验证要删除的是不是这个频道的。
		String hql = "DELETE FROM RefSpecialItem " +
				" WHERE id IN (" + PublishUtil.toSqlInString(refids) + ")";
		int count = pub_ctxt.getDao().bulkUpdate(hql);
		return count;
	}
	
	/**
	 * 复制指定引用到指定专题中。
	 * TODO: 当前的实现太简单，以至于性能很不好。但是如果用的不多，可以不考虑性能。
	 * TODO: 没有验证目标专题是否在当前频道，没有验证要移动的项目是否在当前频道。
	 * @param refids
	 * @param specialIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int copyRefSpecialItems(final List<Integer> refids, final List<Integer> specialIds) {
		if (refids == null || refids.size() == 0) return 0;
		if (specialIds == null || specialIds.size() == 0) return 0;
		
		DataAccessObject dao = pub_ctxt.getDao();
		// 获得实际的来源项目信息。
		String hql = "SELECT DISTINCT itemId FROM RefSpecialItem " +
				" WHERE id IN (" + PublishUtil.toSqlInString(refids) + ")";
		List<Integer> item_list = dao.list(hql);
		if (item_list == null || item_list.size() == 0) return 0;
		
		// 获得实际的专题，某些专题标识可能从前端提交的不对。
		hql = "SELECT id FROM Special " +
				" WHERE id IN (" + PublishUtil.toSqlInString(specialIds) + ")";
		List<Integer> special_ids = dao.list(hql);
		if (special_ids == null || special_ids.size() == 0) return 0;
		
		int insert_count = 0;
		// 组装所有 item_list.itemId, special_ids.id ，插入到 RefSpecialItem 中。
		for (int i = 0; i < item_list.size(); ++i) {
			// 为每个 itemId
			int itemId = (Integer)item_list.get(i);
			for (int j = 0; j < special_ids.size(); ++j) {
				// 为每个 specialId
				int specialId = (Integer)special_ids.get(j);
				insert_count += tryInsertRsi(dao, specialId, itemId);
			}
		}
		dao.flush();
		return insert_count;
	}
	
	/**
	 * 移动指定引用到指定专题中。
	 * @param refids
	 * @param specialIds
	 * TODO: 当前的实现太简单，以至于性能很不好。但是如果用的不多，可以不考虑性能。
	 * TODO: 没有验证目标专题是否在当前频道，没有验证要移动的项目是否在当前频道。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int moveRefSpecialItems(List<Integer> refids, List<Integer> specialIds) {
		if (refids == null || refids.size() == 0) return 0;
		if (specialIds == null || specialIds.size() == 0) return 0;
		
		DataAccessObject dao = pub_ctxt.getDao();
		// 获得实际的来源项目信息。
		String hql = "SELECT DISTINCT itemId FROM RefSpecialItem " +
				" WHERE id IN (" + PublishUtil.toSqlInString(refids) + ")";
		List<Integer> item_list = dao.list(hql);
		if (item_list == null || item_list.size() == 0) return 0;
		
		// 获得实际的专题，某些专题标识可能从前端提交的不对。
		hql = "SELECT id FROM Special " +
				" WHERE id IN (" + PublishUtil.toSqlInString(specialIds) + ")";
		List<Integer> special_ids = dao.list(hql);
		if (special_ids == null || special_ids.size() == 0) return 0;
		
		int update_count = 0;
		// 删除之前的所有引用。
		hql = "DELETE FROM RefSpecialItem WHERE id IN (" + PublishUtil.toSqlInString(refids) + ")";
		update_count += dao.bulkUpdate(hql);
		
		// 新增新的引用。
		// 组装所有 item_list.itemId, special_ids.id ，插入到 RefSpecialItem 中。
		for (int i = 0; i < item_list.size(); ++i) {
			// 为每个 itemId
			int itemId = (Integer)item_list.get(i);
			for (int j = 0; j < special_ids.size(); ++j) {
				// 为每个 specialId
				int specialId = (Integer)special_ids.get(j);
				update_count += tryInsertRsi(dao, specialId, itemId);
			}
		}
		dao.flush();
		return update_count;
	}
	
	private int tryInsertRsi(DataAccessObject dao, int specialId, int itemId) {
		// 看看是否已经有了
		String hql = "SELECT COUNT(id) FROM RefSpecialItem WHERE specialId = " + specialId + " AND itemId = " + itemId;
		int id = PublishUtil.executeIntScalar(dao, hql);
		if (id > 0) return 0;		// 已经存在则不插入了。
		
		// 插入。
		RefSpecialItem rsi = new RefSpecialItem();
		rsi.setSpecialId(specialId);
		rsi.setItemId(itemId);
		dao.save(rsi);
		return 1;
	}
	
	
	/**
	 * 对专题进行重新排序。
	 * 注意：需要事务支持。
	 * @param specialIds 专题的标识数组，该数组的顺序决定了专题的顺序。
	 * @return 更新的记录数。
	 */
	public int reorder(List<Integer> special_ids) {
		if (special_ids == null || special_ids.size() == 0) return 0;
		
		DataAccessObject dao = pub_ctxt.getDao();
		int total_num = 0;
		for (int i = 0; i < special_ids.size(); ++i) {
			String hql = "UPDATE Special SET specialOrder = " + (i+1) + 
					" WHERE id = " + special_ids.get(i);
			total_num += dao.bulkUpdate(hql);
		}
		return total_num;
	}
	
	/**
	 * 将第一个专题合并到第二个专题中，所有的文章会转移到第二个专题中，第一个专题将会被删除。
	 * 注意：需要事务支持。
	 * @param specialId 第一个专题的标识。
	 * @param targetSpecialId 第二个专题的标识，也是目标专题。
	 * @return 返回更新的记录数。
	 */
	public void uniteSpecial(SpecialWrapper source_special, SpecialWrapper target_special) {
		// 1. 合并专题项目。
		String hql = "UPDATE RefSpecialItem SET specialId = " + 
			target_special.getId() + " WHERE specialId = " + source_special.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
		
		// 2. 删除 source_special
		deleteSpecial(source_special);
		pub_ctxt.getDao().flush();
	}
	
	/**
	 * 得到指定频道下最大的一个专题编号。
	 * @param channelId 频道的标识，如果为0，说明是全站专题。
	 * @return
	 */
	public int getLastOrderId() {
		int id = 0;	// 全站的专题。
		if (super._getOwnerObject() instanceof Channel) {
			Channel channel = (Channel)super._getOwnerObject();
			id = channel.getId();
		}
		String hql = "SELECT max(specialOrder) FROM Special where channelId=" + id;
		List list = super._getPublishContext().getDao().list(hql);
		if (list == null || list.isEmpty()) {
			return 0;
		} else {
			Object order = list.get(0);
			if (order == null) {
				return 0;
			} else {
				return (Integer)order;
			}
		}
	}
}
