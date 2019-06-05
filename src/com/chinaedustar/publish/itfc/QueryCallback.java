package com.chinaedustar.publish.itfc;

import org.hibernate.Query;

/**
 * Hibernate 查询回调接口。
 * 
 * @author liujunxing
 *
 */
public interface QueryCallback {
	public Object doInQuery(Query query);
}
