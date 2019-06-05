package com.chinaedustar.publish;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.chinaedustar.publish.itfc.QueryCallback;

/**
 * 定义发布系统数据访问对象的接口。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public interface DataAccessObject {
	/**
	 * 装载一个对象。（可能 Hibernate 会延迟装载）
	 * @param clazz
	 * @param id
	 * @return
	 */
	// public Object load(Class clazz, Serializable id);

	/**
	 * 得到一个对象。
	 * @param clazz - 对象类。
	 * @param id - 对象唯一标识。
	 * @return
	 */
	public Object get(Class clazz, Serializable id);

	/**
	 * 保存一个对象。
	 * @param target - 目标对象。
	 */
	public void save(Object target);
	
	/**
	 * 增加一个对象。
	 * @param target - 目标对象。
	 */
	public void insert(Object target);
	
	/**
	 * 修改一个对象。
	 * @param target - 目标对象。
	 */
	public void update(Object target);
	
	/**
	 * 删除一个对象。
	 * @param target - 目标对象。
	 */
	public void delete(Object target);
	
	/**
	 * 执行指定的 HQL 语句并返回一个结果的集合。
	 * @param hql
	 * @return
	 */
	public java.util.List list(String hql);

	/**
	 * 强迫刷新当前 Session.
	 * Force the Session to flush. Must be called at the end of a unit of work, 
	 * before commiting the transaction and closing the session (Transaction.commit() 
	 * calls this method). Flushing is the process of synchronising the underlying 
	 * persistent store with persistable state held in memory. 
	 */
	public void flush();

	/**
	 * 在 Session 中清除指定对象。
	 *
	 */
	public void evict(Object target);
	
	/**
	 * 清除当前 Session 中的所有缓存。
	 * Completely clear the session. Evict all loaded instances and cancel all pending 
	 *  saves, updates and deletions. Do not close open iterators or instances of 
	 *  ScrollableResults. 
	 */
	public void clear();
	
	/**
	 * Execute a query for persistent instances, binding a number of values to ":" named parameters in the query string. 
	 * @param queryString - a query expressed in Hibernate's query language
	 * @param paramNames - the names of the parameters
	 * @param values - the values of the parameters
	 * @return - a List containing 0 or more persistent instances
	 */
	public List queryByNamedParam(final String queryString, final String[] paramNames, final Object[] values);

	/**
	 * 执行查询，回调一个查询接口。
	 * @param callback
	 */
	public Object query(final String queryString, final QueryCallback callback);

	/**
	 * 执行批量更新。
	 * @param queryString - 更新语句。
	 * @return - 返回更新数量。
	 */
	public int bulkUpdate(final String queryString);

	/**
	 * 获得内部包装起来的 Spring HibernateTemplate.
	 * @return
	 */
	public HibernateTemplate getHibernateTemplate();

	/**
	 * 获得内部包装起来的 Hibernate SessionFactory.
	 * @return
	 */
	// public org.hibernate.SessionFactory getSessionFactory();
	
	/**
	 * 关闭数据访问对象，释放其所有资源。
	 *
	 */
	public void close();
}
