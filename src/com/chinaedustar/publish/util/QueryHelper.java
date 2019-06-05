package com.chinaedustar.publish.util;

import java.util.List;

import org.hibernate.Query;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.itfc.QueryCallback;
import com.chinaedustar.publish.model.PaginationInfo;

/**
 * 能够将查询条件记录下来的记忆器
 */
@SuppressWarnings("rawtypes")
public class QueryHelper extends AbstractQueryHelper {
			
	/**
	 * SELECT 子句
	 */
	public String selectClause = "";
	
	/**
	 * FROM 子句
	 */
	public String fromClause = "";
	
	/**
	 * WHERE 子句
	 */
	public String whereClause = "";
	
	/**
	 * ORDER BY 子句
	 */
	public String orderClause = "";
	
	/**
	 * 添加一个 whereClause 的与(and)条件
	 * 
	 * @param condition
	 */
	public void addAndWhere(String condition) {
		if (this.whereClause == "")
			this.whereClause = " WHERE (" + condition + ")";
		else
			this.whereClause += " AND (" + condition + ")";
	}
	
	/**
	 * 添加一个 orderClause 的排序方式
	 * 
	 * @param order - 排序的子条件，如 'id ASC'
	 */
	public void addOrder(String order) {
		if (this.orderClause == "")
			this.orderClause = " ORDER BY " + order;
		else
			this.orderClause += ", " + order;
	}
	
	/** 
	 * 查询项目总数。查询语句为 "SELECT COUNT(*) " + fromClause + whereClause
	 * 
	 * @return - 返回项目总数
	 */
	public long queryTotalCount(DataAccessObject dao) {
		String hql = "SELECT COUNT(*) " + this.fromClause + " " + this.whereClause;
		
		List result = (List) dao.query(hql, new QueryCallback() {
			public Object doInQuery(Query query) {
				initQuery(query);
				return query.list();
			}
		});
	
		if (result == null || result.size() == 0)
			return 0;
		return PublishUtil.safeGetLongVal(result.get(0));
	}
	
	/**
	 * 查询项目列表
	 */
	public List queryData(DataAccessObject dao) {
		return queryData(dao, -1, -1);
	}
	
	/**
	 * 执行当前查询，并返回第一条结果
	 * 
	 * @param dao
	 * @return
	 */
	public Object querySingleData(DataAccessObject dao) {
		List list = queryData(dao, 0, 1);
		if (list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	/** 
	 * 查询项目列表：查询语句为：selectClause + fromClause + whereClause + orderClause
	 * 
	 * @param dao
	 * @param first_result - Set the first row to retrieve. -1 means not set, rows will be retrieved beginnning from row 0. 
	 * @param max_results - Set the maximum number of rows to retrieve. -1 means not set, there is no limit to the number of rows retrieved. 
	 */
	public List queryData(DataAccessObject dao, final int first_result, final int max_results) {
		String hql = this.selectClause + " " + this.fromClause + " " + this.whereClause + " " + this.orderClause; 
		
		return (List)dao.query(hql, new QueryCallback() {
			public Object doInQuery(Query query) {
				initQuery(query);

				if (first_result != -1)
					query.setFirstResult(first_result);
				if (max_results != -1)
					query.setMaxResults(max_results);

				return query.list();
			}
		});
		
	}
	
	/**
	 * 查询项目列表
	 * 
	 * @param dao
	 * @param page_info
	 * @return
	 */
	public List queryData(DataAccessObject dao, final PaginationInfo page_info) {
		return queryData(dao, (page_info.getCurrPage() - 1) * page_info.getPageSize(), page_info.getPageSize());
	}

	/**
	 * 查询数量及数据
	 * 
	 * @param dao
	 * @param page_info
	 * @return
	 */
	public List queryDataAndCount(DataAccessObject dao, final PaginationInfo page_info) {
		long total_count = this.queryTotalCount(dao);
		page_info.setTotalCount(total_count);
		return this.queryData(dao, page_info);
	}
	
}
