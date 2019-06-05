package com.chinaedustar.publish.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.itfc.QueryCallback;

/**
 * 发布系统的标准 DAO 实现类。
 * 
 * <p>此类在一个 WebApp 里面只需要一个实例。</p>
 * 
 * @author liujunxing
 *
 */
@SuppressWarnings("rawtypes")
public class StandardPublishDao implements DataAccessObject {
    private SessionFactory session_factory;
    
    private HibernateTemplate hibernate_template;
    
	/**
	 * 用于测试的，建立一个缺省的开发环境用的 DAO。
	 *
	 */
	public StandardPublishDao() {
		/*
		boolean debug = logger.isDebugEnabled();
		
		// instance Configuration
		if (debug)
			logger.debug("[Publish] 开始实例化 Hibernate Configuration 对象。");
		this.hibernate_config = new org.hibernate.cfg.Configuration();
		
		// config.configuration()
		if (debug) 
			logger.debug("[Publish] 配置 Hibernate Configuration 对象: " + this.hibernate_config);
		this.hibernate_config.configure();
		
		// create SessionFactory.
		if (debug)
			logger.debug("[Publish] 准备创建 SessionFactory.");
		this.session_factory = this.hibernate_config.buildSessionFactory();
		
		// OK.
		if (logger.isInfoEnabled()) 
			logger.info("成功创建了 SessionFactory: " + this.session_factory);
		*/
	}
	
	/**
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return this.session_factory;
	}
	
	/**
	 * setter.
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.session_factory = sessionFactory;
		this.hibernate_template = new HibernateTemplate(sessionFactory);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString() {
		return "StandardPublishDao{}";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#get(java.lang.Class, java.io.Serializable)
	 */
	public Object get(Class clazz, Serializable id) {
		return hibernate_template.get(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#save(java.lang.Object)
	 */
	public void save(Object target) {
		hibernate_template.saveOrUpdate(target);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#insert(java.lang.Object)
	 */
	public void insert(Object target) {
		hibernate_template.save(target);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#update(java.lang.Object)
	 */
	public void update(Object target) {
		hibernate_template.update(target);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#delete(java.lang.Object)
	 */
	public void delete(Object target) {
		hibernate_template.delete(target);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#list(java.lang.String)
	 */
	public java.util.List list(String hql) {
		return hibernate_template.find(hql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#getHibernateTemplate()
	 */
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernate_template;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#queryByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List queryByNamedParam(final String queryString, final String[] paramNames, final Object[] values) {
		return this.hibernate_template.findByNamedParam(queryString, paramNames, values);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#bulkUpdate(java.lang.String)
	 */
	public int bulkUpdate(final String queryString) {
		return this.hibernate_template.bulkUpdate(queryString, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#query(com.chinaedustar.publish.QueryCallback)
	 */
	public Object query(final String queryString, final QueryCallback callback) {
		return this.hibernate_template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				return callback.doInQuery(query);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#flush()
	 */
	public void flush() {
		hibernate_template.flush();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#evict(java.lang.Object)
	 */
	public void evict(Object target) {
		hibernate_template.evict(target);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#clear()
	 */
	public void clear() {
		hibernate_template.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.DataAccessObject#close()
	 */
	public void close() {
		
	}
}
