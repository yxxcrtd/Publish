package com.chinaedustar.publish.model;

import java.util.Date;
import java.util.List;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.pjo.Vote;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("rawtypes")
public class VoteCollection extends AbstractPublishModelBase {
	public VoteCollection() {
	}
	
	// ==== 业务方法 ==========================================================

	/**
	 * 获得指定标识的调查
	 */
	public VoteWrapper getVote(int id){
		Vote vote = (Vote)pub_ctxt.getDao().get(Vote.class, id);
		if (vote == null) return null;
		
		return new VoteWrapper(pub_ctxt, this, vote);
	}
	
	/**
	 * 得到当前网站的调查。
	 * @return
	 */
	public VoteWrapper getSiteVote() {
		// 构造查询。
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Vote ";
		query.whereClause = " WHERE channelId = 0 AND isSelected = true AND isItem = false";
		Date now = new Date();
		query.addAndWhere("beginTime <= :now");
		query.addAndWhere("endTime > :now");
		query.setTimestamp("now", now);
		query.orderClause = " ORDER BY id DESC ";
		Vote vote = (Vote)query.querySingleData(pub_ctxt.getDao());
		if (vote == null) return null;
		
		// 返回包装的投票对象。
		return new VoteWrapper(pub_ctxt, this, vote);
	}
	
	/**
	 * 获得指定频道的调查列表.
	 * @param channelId
	 * @return
	 */
	public DataTable getVoteDataTable(int channelId, PaginationInfo page_info) {
		// 构造查询。
		String fields = "id, title, isSelected, voteType, beginTime, endTime";
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + fields;
		query.fromClause = " FROM Vote ";
		query.addAndWhere(" isItem = false AND channelId = " + channelId);
		query.orderClause = " ORDER BY id DESC ";
		
		// 查询总数。
		page_info.setTotalCount(query.queryTotalCount(pub_ctxt.getDao()));
		
		// 查询数据。
		List list = query.queryData(pub_ctxt.getDao(), page_info);
		
		// 构造 DataTable 返回。
		DataTable data_table = new DataTable(new DataSchema(fields));
		PublishUtil.addToDataTable(list, data_table);
		return data_table;
	}

	/**
	 * 添加或修改一个调查
	 * @param vote_wrapper
	 */
	public void createOrUpdateVote(VoteWrapper vote_wrapper) {
		// 获得实际操作数据，然后保存。
		Vote vote = vote_wrapper.getTargetObject();
		pub_ctxt.getDao().save(vote);
	}
	
	/**
	 * 设置指定的调查为开启状态
	 * @param ids
	 * @param isSelected
	 */
	public void setVoteSelected(int[] ids, boolean isSelected){
		if (ids == null) throw new java.lang.IllegalArgumentException("ids == null");
		
		if (ids.length == 0) return;
		String hql = "UPDATE Vote SET isSelected = " + isSelected + " WHERE id IN (" +PublishUtil.toSqlInString(ids) + ")";
		int updateNum = pub_ctxt.getDao().bulkUpdate(hql);
		System.out.println("setVoteSelected hql = " + hql + ", return updateNum = " + updateNum);
	}
	
	/**
	 * 删除一个指定标识的调查
	 * @param id
	 */
	public void deleteVote(int id){
		deleteVote(new int[] {id});
	}
	
	@SuppressWarnings("unused")
	public void deleteVote(int[] ids){
		if (ids == null) throw new java.lang.IllegalArgumentException("ids == null");
		if (ids.length == 0) return;
		String hql = "DELETE FROM Vote WHERE id IN (" +PublishUtil.toSqlInString(ids) +")";
		int updateNum = pub_ctxt.getDao().bulkUpdate(hql);
	}
}
