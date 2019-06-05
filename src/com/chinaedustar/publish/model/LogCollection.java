package com.chinaedustar.publish.model;

import java.util.List;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 日志集合对象，用于管理。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class LogCollection extends AbstractPublishModelBase {
	/**
	 * 获得管理用的日志列表信息。
	 * @param page_info
	 * @return
	 * @schema [id, operation, status, operTime, userName, userIP, url, description] 
	 */
	public DataTable getLogDataTable(PaginationInfo page_info) {
		// 构造查询。
		String fields = "id, operation, status, operTime, userName, userIP, url, description";
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + fields;
		query.fromClause = " FROM Log ";
		query.orderClause = " ORDER BY id DESC ";
		
		// 查询数量。
		DataAccessObject dao = pub_ctxt.getDao();
		page_info.setTotalCount(query.queryTotalCount(dao));
		// 按页查询。
		List result = query.queryData(dao, page_info);
		
		// 组装为 DataTable
		DataTable data_table = new DataTable(new DataSchema(fields));
		PublishUtil.addToDataTable(result, data_table);
		
		return data_table;
	}
	
	/**
	 * 获得指定标识的日志。
	 * @param id
	 * @return
	 */
	public Log getLog(int id) {
		Log log = (Log)pub_ctxt.getDao().get(Log.class, id);
		
		return log;
	}
	
	/**
	 * 批量删除日志。
	 * @param ids
	 */
	public void batchDeleteLogs(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return;
		
		String hql = "DELETE FROM Log WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 清空日志。
	 *
	 */
	public void clearLogs() {
		String hql = "DELETE FROM Log";
		pub_ctxt.getDao().bulkUpdate(hql);
	}

	/**
	 * 得到指定标识的日志项的前一个日志。
	 * @param log_id
	 * @return
	 */
	public Log getPrevLog(int log_id) {
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Log ";
		query.whereClause = " WHERE id > " + log_id ;
		query.orderClause = " ORDER BY id ASC ";
		return (Log)query.querySingleData(pub_ctxt.getDao());
	}
	
	/**
	 * 得到指定标识的日志项的后一个日志。
	 * @param log_id
	 * @return
	 */
	public Log getNextLog(int log_id) {
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Log ";
		query.whereClause = " WHERE id < " + log_id ;
		query.orderClause = " ORDER BY id DESC ";
		return (Log)query.querySingleData(pub_ctxt.getDao());
	}
}
