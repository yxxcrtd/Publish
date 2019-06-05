package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 自定义标签的管理集合对象。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class FriendSiteCollection extends AbstractPublishModelBase {
	/**
	 * 构造一个 FriendSiteCollection 的实例。
	 *
	 */
	public FriendSiteCollection() {
		
	}
	
	// === 业务 ==============================================
	
	/**
	 * 获取指定标识的 友情链接 对象。
	 * @return - 从数据库装载的 Label 数据，如果没有则返回 null.
	 */
	public FriendSite getFriendSite(int friendSiteId) {
		FriendSite friendSite = (FriendSite)this.pub_ctxt.getDao().get(FriendSite.class, new Integer(friendSiteId));
		friendSite._init(pub_ctxt, this);
		return friendSite;
	}

	/**
	 * 创建或更新友情链接对象。
	 * @param friendSite
	 */
	public void createOrUpdateFriendSite(FriendSite friendSite) {
		if (friendSite.getObjectId() == 0)
			internalCreateFriendSite(friendSite);
		else
			internalUpdateFriendSite(friendSite);
	}
	
	/**
	 * 创建一个友情链接对象。
	 * @param friendSite
	 */
	public void createFriendSite(FriendSite friendSite) {
		createOrUpdateFriendSite(friendSite);
	}
	

	/**
	 * 更新一个友情链接对象。
	 * @param friendSite
	 */
	public void updateFriendSite(FriendSite friendSite) {
		createOrUpdateFriendSite(friendSite);
	}
	/**
	 * 对友情链接进行排序
	 * @param ids
	 */
	public void sortFriendSite(List<Integer> ids) {
		if (ids == null) return;
		DataAccessObject dao = pub_ctxt.getDao();
		for (int i = 0; i < ids.size(); i++) {
			String hql = "UPDATE FriendSite SET orderId = " + (i + 1) + " WHERE id = " + ids.get(i);
			dao.bulkUpdate(hql);
		}
	}
	
	// === 业务 ===================================================================
	
	/**
	 * 获得指定标识的友情链接类别对象。
	 */
	public FriendSiteKind getFriendSiteKind(int id) {
		FriendSiteKind kind = (FriendSiteKind)pub_ctxt.getDao()
			.get(FriendSiteKind.class, new Integer(id));
		if (kind != null)
			kind._init(pub_ctxt, this);
		return kind;
	}
	
	/**
	 * 获得指定标识的友情链接专题对象。
	 * @param id
	 * @return
	 */
	public FriendSiteSpecial getFriendSiteSpecial(int id) {
		FriendSiteSpecial special = (FriendSiteSpecial)pub_ctxt.getDao()
			.get(FriendSiteSpecial.class, new Integer(id));
		if (special != null)
			special._init(pub_ctxt, this);
		return special;
	}
	
	/**
	 * 获得所有的友情链接列表
	 * @return
	 */
	public List<FriendSite> getFriendSiteList(){
		String hql = "FROM FriendSite ORDER BY orderId ASC";
		@SuppressWarnings("unchecked")
		List<FriendSite> list = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(list, pub_ctxt, this);
		return list;
	}
	
	/**
	 * 获得所有友情链接类别, 带有对每个类别下 友情链接 数量的统计数据.
	 * @return 返回一个 DataTable, 其具有的列为 id, name, kindDesc, count
	 */
	public DataTable getFsKindWithCount() {
		String hql = "SELECT k.id, k.kindName, k.kindDesc, " +
					 "    (SELECT COUNT(*) FROM FriendSite f WHERE f.kind.id = k.id) " +
					 "FROM FriendSiteKind k ";
		List list = pub_ctxt.getDao().list(hql);
		
		DataTable data = new DataTable(PublishUtil.columnsToSchema("id, name, kindDesc, count"));
		PublishUtil.addToDataTable(list, data);
		return data;
	}
	
	/**
	 * 获得所有的友情链接类别，按照其 id 排序。
	 * @return 返回一个 FriendSiteKind 对象的 List.
	 */
	public List<FriendSiteKind> getFsKindList() {
		String hql = "FROM FriendSiteKind k ORDER BY k.id ASC";
		@SuppressWarnings("unchecked")
		List<FriendSiteKind> fs_kinds = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(fs_kinds, pub_ctxt, this);
		return fs_kinds;
	}
	
	/**
	 * 获得所有友情链接的专题列表，按照其 id 排序。
	 * @return 返回一个 FriendSiteSpecial 对象 List.
	 */
	public List<FriendSiteSpecial> getFsSpecialList() {
		String hql = "FROM FriendSiteSpecial s ORDER BY s.id ASC";
		@SuppressWarnings("unchecked")
		List<FriendSiteSpecial> fs_specials = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(fs_specials, pub_ctxt, this);
		return fs_specials;
	}

	/**
	 * 获得专题列表，并额外统计每个专题下有多少个友情链接。
	 * @return DataTable<id, name, desc, count>
	 */
	public DataTable getFsSpecialListWithCount() {
		String hql = "SELECT s.id, s.specialName, s.specialDesc, " +
		 			 "    (SELECT COUNT(*) FROM FriendSite f WHERE f.special.id = s.id) " +
		 			 "FROM FriendSiteSpecial s ";
		List list = pub_ctxt.getDao().list(hql);

		DataTable data = new DataTable(PublishUtil.columnsToSchema("id, name, desc, count"));
		PublishUtil.addToDataTable(list, data);
		return data;
	}
	
	/**
	 * 获得友情链接列表按链接类别
	 * @return
	 */
	public List getFriendSiteListByKind(Boolean approved, int kindId){
		String hql = "FROM FriendSite f where ";
		if (approved != null) {
			hql += " f.approved = " + (approved ? "true" : "false");
		} else {
			// hql += " 1 = 1";
		}
		if(kindId > 0){
			hql += " And f.kind = "+ kindId;
		}else{
			hql += " And f.kind > 0 ";
		}
		hql += " ORDER BY f.id ASC";
		return pub_ctxt.getDao().list(hql);
	}
	
	/**
	 * 获得友情链接列表按链接专题
	 * @return
	 */
	public List getFriendSiteListBySpecial(Boolean approved, int specialId){
		String hql = "FROM FriendSite f where ";
		if (approved != null) {
			hql += " f.approved = " + (approved ? "true" : "false");
		} else {
			hql += " 1 = 1";
		}
		if(specialId > 0){
			hql += " And f.special = "+ specialId;
		}else{
			hql += " And f.special > 0 ";
		}
		hql += " ORDER BY f.id ASC";
		return pub_ctxt.getDao().list(hql);
	}
	
	/**
	 * 获取指定审核状态、指定类别、指定专题下的友情链接列表。按照友情链接标识排序。
	 * @param approved - 审核状态标识。缺省 = null 表示全部。
	 * @param elite -是否为精华（推荐）的站点。缺省 = null 表示全部。
	 * @param linkType - 链接类型。缺省 = 0 表示全部。
	 * @param kindId - 友情链接类别标识。 缺省 = 0 表示不区分类别。
	 * @param specialId - 友情链接专题标识。 缺省 = 0 表示不区分专题。
	 * @param orderBy - 按什么排序。
	 * @param page_info - 分页信息。
	 * @return
	 */
	public List<FriendSite> getFriendSiteList(Boolean approved, Boolean elite, 
			int linkType, int kindId, int specialId, 
			int orderBy, PaginationInfo page_info) {
		// 构造查询对象。
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM FriendSite AS f ";
		query.whereClause = " WHERE ";
		
		if (approved != null)
			query.whereClause += " f.approved = " + approved;
		else
			query.whereClause += " (1 = 1)";
		
		if (elite != null)
			query.whereClause += " AND f.elite = " + elite;
		if (linkType > 0)
			query.whereClause += " AND f.linkType = " + linkType;
		if (kindId > 0)
			query.whereClause += " And f.kind = " + kindId;
		if (specialId > 0)
			query.whereClause += " And f.special = " + specialId;
		
		String orderByStr = "orderId asc";
		switch (orderBy) {
		case 1: orderByStr = "orderId desc"; break;
		case 2: orderByStr = "id asc"; break;
		case 3: orderByStr = "id desc"; break;
		case 4: orderByStr = "stars asc"; break;
		case 5: orderByStr = "stars desc"; break;
		case 0:
		default: orderByStr = "orderId asc";
		}
		query.orderClause = " Order By f." + orderByStr;
		
		// 查询数据。
		page_info.setTotalCount(query.queryTotalCount(pub_ctxt.getDao()));
		@SuppressWarnings("unchecked")
		List<FriendSite> result = query.queryData(pub_ctxt.getDao(), page_info);

		PublishUtil.initModelList(result, pub_ctxt, this);
		return result;
	}

	/**
	 * 删除指定的友情链接。
	 * @param ids
	 * @return
	 */
	public int deleteFriendSite(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return 0;
		String hql = "DELETE FROM FriendSite " +
					 " WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int result = pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		return result;
	}
	
	/**
	 * 设置指定的友情链接的状态。
	 * @param ids 友情链接标识的数组
	 * @param elite_status 是否为精华
	 * @return
	 */
	public int eliteFriendSite(List<Integer> ids, boolean elite_status) {
		if (ids == null || ids.size() == 0) return 0;
		String hql = "UPDATE FriendSite " +
					 " SET elite = " + elite_status +
		 			 " WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int result = pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		return result;
	}

	/**
	 * 设置指定的友情链接的审核状态。
	 * @param ids
	 * @param appr_status
	 * @return
	 */
	public int approveFriendSite(List<Integer> ids, boolean appr_status) {
		if (ids == null || ids.size() == 0) return 0;
		String hql = "UPDATE FriendSite " +
					 " SET approved = " + appr_status +
		 			 " WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int result = pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		return result;
	}
	
	/**
	 * 移动指定的友情链接到指定的类别。
	 * @param ids
	 * @param kindId
	 * @return
	 */
	public int moveFriendSiteKind(List<Integer> ids, int kindId) {
		if (ids == null || ids.size() == 0) return 0;
		
		FriendSiteKind kind = kindId == 0 ? null : getFriendSiteKind(kindId);
		String hql;
		if (kind == null)
			hql = "UPDATE FriendSite SET kind = NULL";
		else
			hql = "UPDATE FriendSite SET kind = " + kind.getId();
		hql += " WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		
		int result = pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		return result;
	}
	
	/**
	 * 移动指定的友情链接到指定的专题。
	 * @param ids
	 * @param specialId
	 * @return
	 */
	public int moveFriendSiteSpecial(List<Integer> ids, int specialId) {
		if (ids == null || ids.size() == 0) return 0;
		
		FriendSiteSpecial special = specialId == 0 ? null : getFriendSiteSpecial(specialId);
		String hql;
		if (special == null)
			hql = "UPDATE FriendSite SET special = NULL";
		else
			hql = "UPDATE FriendSite SET special = " + special.getId();
		hql += " WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		
		int result = pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		return result;
	}	
	
	/**
	 * 验证指定的网站名字和网站地址是否可以新注册。
	 * @param siteName
	 * @param siteUrl
	 * @return 返回 true 表示可以注册，返回 false 表示重复了不能注册。
	 */
	public boolean checkNewReg(String siteName, String siteUrl) {
		QueryHelper query = new QueryHelper();
		query.selectClause = " SELECT COUNT(*) ";
		query.fromClause = " FROM FriendSite ";
		query.whereClause = " WHERE siteName = :siteName OR siteUrl = :siteUrl ";
		query.setString("siteName", siteName);
		query.setString("siteUrl", siteUrl);
		
		int count = (Integer)query.querySingleData(pub_ctxt.getDao());
		return count == 0 ? true : false;
	}
	
	// === FriendSiteKind 业务 ====================================================
	
	/**
	 * 创建或更新一个类别。
	 */
	public void createOrUpdateFsKind(FriendSiteKind kind) {
		pub_ctxt.getDao().save(kind);
	}
	
	/**
	 * 删除指定的友情链接类别，属于该类别的友情链接将被设置为 null 类别。
	 * @return 返回被删除的这个友情链接类别对象。其可能为空。
	 */
	public FriendSiteKind deleteKind(int kindId) {
		FriendSiteKind kind = this.getFriendSiteKind(kindId);
		
		// 将该类别的友情链接修改为无类别的。
		String hql = "UPDATE FriendSite SET kind = NULL WHERE kind.id = " + kindId;
		pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		// 删除该类别。
		if (kind != null)
			pub_ctxt.getDao().delete(kind);

		return kind;
	}
	
	/**
	 * 清空指定的友情链接类别，属于该类别的友情链接将被设置为 null 类别。
	 * @return 返回友情链接类别对象。其可能为空。
	 */
	public FriendSiteKind emptyKind(int kindId) {
		FriendSiteKind kind = this.getFriendSiteKind(kindId);
		
		// 将该类别的友情链接修改为无类别的。
		String hql = "UPDATE FriendSite SET kind = NULL WHERE kind.id = " + kindId;
		pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);

		return kind;
	}
	
	// === FriendSiteSpecial 业务 =================================================
	
	/**
	 * 创建或更新一个专题。
	 */
	public void createOrUpdateFsSpecial(FriendSiteSpecial special) {
		pub_ctxt.getDao().save(special);
	}
	
	/**
	 * 删除指定的友情链接专题，属于该专题的友情链接将被设置为 null 专题。
	 * @return 返回被删除的这个友情专题对象。其可能为空。
	 */
	public FriendSiteSpecial deleteSpecial(int specialId) {
		FriendSiteSpecial special = this.getFriendSiteSpecial(specialId);
		
		// 将该专题的友情链接修改为无类别的。
		String hql = "UPDATE FriendSite SET special = NULL WHERE special.id = " + specialId;
		pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		// 删除该专题。
		if (special != null)
			pub_ctxt.getDao().delete(special);

		return special;
	}
	
	/**
	 * 清空指定的友情链接专题，属于该专题的友情链接被设置为 null 专题。
	 * @param specialId
	 * @return 返回被删除的这个友情专题对象。其可能为空。
	 */
	public FriendSiteSpecial emptySpecial(int specialId) {
		FriendSiteSpecial special = this.getFriendSiteSpecial(specialId);
		
		// 将该专题的友情链接修改为无类别的。
		String hql = "UPDATE FriendSite SET special = NULL WHERE special.id = " + specialId;
		pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql);
		
		return special;
	}

	// === 实现 ===================================================================
	
	// 内部创建一个友情链接。
	private final void internalCreateFriendSite(FriendSite friendSite) {
		// 计算最后的一个 OrderId
		String hql = "SELECT MAX(orderId) FROM FriendSite";
		int orderId = 1 + PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		friendSite.setOrderId(orderId);
		
		// 创建此标签到数据库.
		this.pub_ctxt.getDao().save(friendSite);
	}
	
	// 内部更新一个友情链接。
	private final void internalUpdateFriendSite(FriendSite friendSite) {
		// 更新到数据库。
		this.pub_ctxt.getDao().save(friendSite);
		
		// 如果给出了 sitePassword 则需要单独执行 password 的更新。
		String password = friendSite.getSitePassword();
		if (password != null && password.length() > 0) {
			String hql = "UPDATE FriendSite SET sitePassword = ?";
			pub_ctxt.getDao().getHibernateTemplate().bulkUpdate(hql, password);
		}
	}
}
