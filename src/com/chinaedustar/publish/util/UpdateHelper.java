package com.chinaedustar.publish.util;

import org.hibernate.Query;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.itfc.QueryCallback;

/**
 * 更新语句的辅助类。
 * 
 * @author liujunxing
 *
 */
public class UpdateHelper extends AbstractQueryHelper {
	/** 更新语句。 */
	public String updateClause = "";
	
	/**
	 * 缺省构造。
	 *
	 */
	public UpdateHelper() {
		
	}
	
	/**
	 * 使用指定的更新语句构造。
	 * @param updateClause
	 */
	public UpdateHelper(String updateClause) {
		this.updateClause = updateClause;
	}
	
	/**
	 * 执行更新语句，并返回更新的记录数量。
	 * @param dao
	 * @return 更新的记录数量。
	 */
	public int executeUpdate(DataAccessObject dao) {
		return (Integer)dao.query(updateClause, new QueryCallback() {
			public Object doInQuery(Query query) {
				initQuery(query);
				return query.executeUpdate();
			}
		});
	}
}
