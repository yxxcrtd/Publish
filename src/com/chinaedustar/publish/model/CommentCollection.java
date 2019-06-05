package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 评论对象
 * @author dengxiaolong
 * TODO: 不要从 AbstractModelCollection 派生。
 */
@SuppressWarnings("rawtypes")
public class CommentCollection extends AbstractPublishModelBase	{
	/**
	 * 通过标识找到对应的评论对象。
	 * @param commentId 评论的标识。
	 * @return 评论对象。
	 */
	public Comment getComment(int commentId) {
		Comment comment = (Comment)pub_ctxt.getDao().get(Comment.class, commentId);
		if (comment != null)
			comment._init(pub_ctxt, this);
		return comment;
	}
	
	/**
	 * 插入一个评论对象。
	 * @param comment 评论对象。
	 */
	public void saveComment(Comment comment) {
		pub_ctxt.getDao().save(comment);
	}
	
	/**
	 * 删除一个评论对象。
	 * @param id 评论对象的标识。
	 */
	public void deleteComment(int id) {
		String hql = "DELETE FROM Comment WHERE id = " + id;
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 删除一组评论对象。
	 * @param ids 评论对象的标识的数组。
	 */
	public void deleteComment(List<Integer> ids) {
		String hql = "DELETE FROM Comment WHERE id IN(" + PublishUtil.toSqlInString(ids) +")";
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 给评论对象创建回复。
	 * @param commentId 评论对象的标识。
	 * @param replyName 回复人的帐号。
	 * @param replyContent 回复的内容。
	 */
	public void insertReply(int commentId, String replyName, String replyContent) {
		if (commentId == 0) return;
		
		Comment comment = getComment(commentId);
		if (comment == null) return;
		
		comment.setReplyName(replyName);
		comment.setReplyTime(new java.util.Date());
		comment.setReplyContent(replyContent);
		
		saveComment(comment);
	}
	
	/**
	 * 删除评论对象的回复。
	 * 注意：检查回复是否有评论时只要看replyName字段是否为空，因此只需要将replyName设为空即可。
	 * @param commentId 评论对象的标识。
	 */
	public void deleteReply(int commentId) {
		if (commentId == 0) return;
		
		Comment comment = getComment(commentId);
		if (comment == null) return;
		
		comment.setReplyName(null);
		comment.setReplyContent(null);
		
		saveComment(comment);		
	}
	
	/**
	 * 删除项目中的所有评论。
	 *
	 */
	public void clearReplys() {
		if (super._getOwnerObject() != null && super._getOwnerObject() instanceof Item) {
			Item item = (Item)super._getOwnerObject();
			int itemId = item.getId();
			
			String hql = "DELETE FROM Comment WHERE itemId = " + itemId;
			super._getPublishContext().getDao().bulkUpdate(hql);
		}
	}
	
	/**
	 * 更新评论对象的审核状态。
	 * @param commentId 评论对象的标识。
	 * @param passed 是否通过审核。
	 */
	public void updatePassed(List<Integer> ids, boolean passed) {
		String hql = "UPDATE Comment SET passed = " + passed
			+ " WHERE id In(" + PublishUtil.toSqlInString(ids) + ")";
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 得到评论对象列表。
	 * @param page 当前页数。
	 * @param maxPerPage 最大显示评论数目。
	 * @return
	 */
	public List<Comment> getCommentList(PaginationInfo page_info) {
		if (owner_obj != null && owner_obj instanceof Item) {
			Item item = (Item)owner_obj;
			// String hql = "From Comment where itemId = " + item.getId() + " AND Passed = true Order by writeTime desc";
			// 构造查询。
			QueryHelper query = new QueryHelper();
			query.fromClause = " FROM Comment ";
			query.whereClause = " WHERE itemId = " + item.getId() + " AND Passed = true ";
			query.orderClause = " ORDER BY id DESC ";
			
			// 查询数量及评论。
			long total_count = query.queryTotalCount(pub_ctxt.getDao());
			page_info.setTotalCount(total_count);
			if (total_count == 0)
				return new java.util.ArrayList<Comment>();
			@SuppressWarnings("unchecked")
			List<Comment> comment_list = query.queryData(pub_ctxt.getDao(), page_info);
			return comment_list;
		}
		
		return null;
	}
	
	/**
	 * 得到评论列表的数目
	 * 注意：这里只得到已经通过审核的评论
	 * @return
	 */
	public long getCommentCount() {
		long count = 0;
		if (super._getOwnerObject() != null && super._getOwnerObject() instanceof Item) {
			Item item = (Item)super._getOwnerObject();
			
			String hql ="Select count(*) From Comment where itemId = " + item.getId() + " AND Passed = true";
			java.util.List list = super._getPublishContext().getDao().list(hql);
			if (list != null && list.size() > 0) {
				count = Long.parseLong(list.get(0).toString());
			}			
		}
		return count;
	}

	/**
	 * 得到指定分页条件下评论信息数据表。
	 * @param passed - = null 表示不限制是否审核通过，= true 表示显示审核通过的，= false 表示显示审核未通过的。
	 * @param page_info
	 * @schema id, userName, itemId, writeTime, content, passed, title(item的)
	 */
	public DataTable getCommentDataTable(int channelId, Boolean passed, PaginationInfo page_info) {
		// 构造查询。
		String select_fields = "C.id, C.userName, C.itemId, C.writeTime, C.content, C.passed, I.title";
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + select_fields;
		query.fromClause = " FROM Comment AS C, Item AS I ";
		query.whereClause = " WHERE C.itemId = I.id AND I.channelId = " + channelId;
		query.orderClause = " ORDER BY C.id DESC ";
		if (passed != null)
			query.addAndWhere("C.passed = " + passed);
		
		// 查询数据。
		List list = query.queryDataAndCount(pub_ctxt.getDao(), page_info);
		
		// 组装为 DataTable
		DataTable data_table = new DataTable(new DataSchema(select_fields));
		PublishUtil.addToDataTable(list, data_table);
		return data_table;
	}

	/**
	 * 删除指定频道下所有评论。
	 * @param dao
	 * @param channelId
	 */
	public static void deleteChannelComments(DataAccessObject dao, int channelId) {
		// 这里使用的嵌套查询，也许有些 db 不支持??
		String hql = "DELETE FROM Comment WHERE itemId IN " +
			"(SELECT id FROM Item WHERE channelId = " + channelId + ")";
		dao.bulkUpdate(hql);
	}
}
