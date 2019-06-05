package com.chinaedustar.publish.model;

import java.util.List;

import org.hibernate.Query;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.itfc.QueryCallback;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 表示公告的集合对象。
 * 
 * @author liujunxing
 *
 */
@SuppressWarnings({"rawtypes", "unused"})
public class AnnouncementCollection extends AbstractPublishModelBase implements PublishModelObject {

	/**
	 * 获得指定标识的公告。
	 */
	public Announcement getAnnouncement(int id) {
		Announcement announce = (Announcement)pub_ctxt.getDao().get(Announcement.class, id);
		if (announce != null)
			announce._init(pub_ctxt, this);
		return announce;
	}
	
	/**
	 * 获得指定频道的公告列表。
	 * @param channelId - 频道标识。
	 * @return 该频道的所有公告列表，类型为 DataTable. 包含的列
	 *   id, title, isSelected, showType, author, createDate, outTime
	 */
	public DataTable getAnnounceList(int channelId) {
		String hql = "SELECT id, title, isSelected, showType, author, createDate, outTime " +
			"FROM Announcement A WHERE A.channelId = " + channelId;
		List list = pub_ctxt.getDao().list(hql);
		DataTable dt = new DataTable(PublishUtil.columnsToSchema("id, title, isSelected, showType, author, createDate, outTime"));
		PublishUtil.addToDataTable(list, dt);
		return dt;
	}
	
	/**
	 * 获得所有频道的公告列表,包含分页(需要时实现)
	 * 主要用于ShowAnnounceList
	 * @return
	 */
	public List getAnnounceList(){
		String hql = "FROM Announcement WHERE isSelected = true And (offDate is null Or offDate >= :now) Order by id ASC";
		List list = (List) pub_ctxt.getDao().query(hql, new QueryCallback(){
			public Object doInQuery(Query query) {
				query.setDate("now", new java.util.Date());
				return query.list();
			}});
		return list;
	}
	
	/**
	 * 获得公告列表。
	 * 主要用于 ShowAnnounceList 标签
	 * @param channelId - 频道的标识, 0 表示全站公告，-1表示所有公告。
	 * @param num - 要得到的公告条数, <= 0 表示不限制
	 * @return
	 */
	public List<Announcement> getAnnounceList(int channelId, int num) {
		// 构造查询，只查询被选中，显示类型正确，未过期的。反序排列。
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Announcement ";
		query.whereClause = " WHERE isSelected = true " +
			" AND (showType = 0 OR showType = 1) " +
			" AND (offDate IS NULL OR offDate >= :now)";
		if (channelId >= 0)
			query.whereClause += " AND channelId = " + channelId;
		query.orderClause = " ORDER BY id DESC ";
		query.setTimestamp("now", new java.util.Date());
		
		// 获得数据，模型化之后返回。
		if (num <= 0) num = -1;
		@SuppressWarnings("unchecked")
		List<Announcement> result = query.queryData(pub_ctxt.getDao(), -1, num);
		
		PublishUtil.initModelList(result, pub_ctxt, this);
		return result;
	}
	
	/**
	 * 添加/修改公告。
	 * @param announce
	 */
	public void createOrUpdateAnnounce(Announcement announce) {
		pub_ctxt.getDao().save(announce);
	}
	
	/**
	 * 设置指定的公告为选中状态。
	 * @param id
	 * @param isSelected
	 */
	public void setAnnounceSelected(List<Integer> ids, boolean isSelected) {
		if (ids == null || ids.size() == 0) return;
		String hql = "UPDATE Announcement SET isSelected = " + isSelected + 
			" WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int updateNum = pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 删除指定标识的公告。
	 * @param id
	 */
	public void deleteAnnounce(int id) {
		List<Integer> ids = new java.util.ArrayList<Integer>();
		ids.add(id);
		deleteAnnounce(ids);
	}
	
	/**
	 * 删除一组公告。
	 * @param ids
	 */
	public void deleteAnnounce(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return;
		String hql = "DELETE FROM Announcement WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int deleteNum = pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 设置一组公告的显示类型。
	 * @param ids
	 * @param showType
	 */
	public void setAnnounceShowType(List<Integer> ids, int showType) {
		if (ids == null || ids.size() == 0) return;
		String hql = "UPDATE Announcement SET showType=" + showType + 
			" WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int updateNum = pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 移动一组公告到指定频道。
	 * @param ids
	 * @param channelId
	 */
	public void moveAnnounceToChannel(List<Integer> ids, int channelId) {
		if (ids == null || ids.size() == 0) return;
		String hql = "UPDATE Announcement SET channelId=" + channelId + 
			" WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int updateNum = pub_ctxt.getDao().bulkUpdate(hql);
	}
}
