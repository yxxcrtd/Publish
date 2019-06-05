package com.chinaedustar.publish.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.TransactionProxy;
import com.chinaedustar.publish.engine.GenerateEngine;
import com.chinaedustar.publish.event.PublishContextEvents;
import com.chinaedustar.publish.label.AdvanceItemLabel;
import com.chinaedustar.publish.label.CommonItemLabel;
import com.chinaedustar.publish.label.CommonModelLabel;
import com.chinaedustar.publish.label.FunctionSiteLabel;
import com.chinaedustar.publish.label.GeneralAnnounceLabel;
import com.chinaedustar.publish.label.GeneralArticleLabel;
import com.chinaedustar.publish.label.GeneralAuthorLabel;
import com.chinaedustar.publish.label.GeneralChannelLabel;
import com.chinaedustar.publish.label.GeneralColumnLabel;
import com.chinaedustar.publish.label.GeneralCommentLabel;
import com.chinaedustar.publish.label.GeneralFeedbackLabel;
import com.chinaedustar.publish.label.GeneralFriendSiteLabel;
import com.chinaedustar.publish.label.GeneralMemberLabel;
import com.chinaedustar.publish.label.GeneralPhotoLabel;
import com.chinaedustar.publish.label.GeneralSearchLabel;
import com.chinaedustar.publish.label.GeneralSiteLabel;
import com.chinaedustar.publish.label.GeneralSoftLabel;
import com.chinaedustar.publish.label.GeneralSourceLabel;
import com.chinaedustar.publish.label.GeneralStudentLabel;
import com.chinaedustar.publish.label.LabelHandlerMap;
import com.chinaedustar.publish.label.SpecialLabel;
import com.chinaedustar.publish.model.Log;
import com.chinaedustar.publish.model.Site;

/**
 * 发布系统模型环境的实现，其提供发布系统业务模型的访问根。这个类支持多线程的调用
 */
@SuppressWarnings("rawtypes")
public class StandardPublishContext implements PublishContext {
	
    /** 应用的根目录。 */
    private String root_dir;
    
    /** URI原始编码 */
    private String uriOriginalEncoding = "ISO8859-1";
    
    /** URI目标编码 */
    private String uriTargetEncoding = "UTF-8";
    
    /** 配置对象，类型尚待确定。 */
    private Object config;
    
    /** Spring 的 ApplicationContext 对象。 */
    private ApplicationContext spring_ctxt;
    
    /** 事件集合对象。 */
    private PublishContextEvents events = new PublishContextEvents();
    
	/** 数据访问对象。 */
	private DataAccessObject dao;
	
	/** 发布系统的事务代理对象。 */
	private TransactionProxy tx_proxy;

	/** Site 模型对象。 */
	private Site site;

	/** 标签解释器集合。 */
	private LabelHandlerMap labelh_map = new LabelHandlerMap();

	/** 发布系统静态化引擎。 */
	private GenerateEngine gen_engine; 

	/** 可以用来存放全局缓存的容器。 */
	private Map map = new java.util.Hashtable();

	/**
	 * 构造一个发布系统的环境，此方式用于测试。
	 *
	 */
	public StandardPublishContext() {
		this(System.getProperty("user.dir"), false);
	}

	/**
	 * 使用指定的根目录构造一个发布系统的环境。
	 * @param root_dir - 发布系统站点所在物理目录，如 'C:\Publish\WebSite'
	 * @param start_engine - 是否启动静态化引擎和注册系统内建标签。用于测试。
	 */
	public StandardPublishContext(String root_dir, boolean start_engine) {
		this.root_dir = root_dir;
		this.gen_engine = new GenerateEngine(this);

		if (start_engine) {
			// 注册 LabelHandler, 我们有什么好办法将注册延迟并且让类自己注册呢???
			registerLabelHandler();
			
			// 初始化静态化引擎并自动启动。
			this.gen_engine.startEngine();
		}
	}
	
	/**
	 * 注册系统缺省的标签处理器
	 */
	public void registerLabelHandler() {
		AdvanceItemLabel.registerHandler(labelh_map);
		GeneralColumnLabel.registerHandler(labelh_map);
		CommonItemLabel.registerHandler(labelh_map);
		CommonModelLabel.registerHandler(labelh_map);
		FunctionSiteLabel.registerHandler(labelh_map);
		GeneralAnnounceLabel.registerHandler(labelh_map);
		GeneralArticleLabel.registerHandler(labelh_map);
		GeneralAuthorLabel.registerHandler(labelh_map);
		GeneralChannelLabel.registerHandler(labelh_map);
		GeneralCommentLabel.registerHandler(labelh_map);
		GeneralFriendSiteLabel.registerHandler(labelh_map);
		GeneralMemberLabel.registerHandler(labelh_map);
		GeneralPhotoLabel.registerHandler(labelh_map);
		GeneralSearchLabel.registerHandler(labelh_map);
		GeneralSiteLabel.registerHandler(labelh_map);
		GeneralSoftLabel.registerHandler(labelh_map);
		GeneralSourceLabel.registerHandler(labelh_map);
		GeneralFeedbackLabel.registerHandler(labelh_map);
		SpecialLabel.registerHandler(labelh_map);
		GeneralStudentLabel.registerHandler(labelh_map);
	}
	
	public String getRootDir() {
		return this.root_dir;
	}

	public String getUriOriginalEncoding() {
		return this.uriOriginalEncoding;
	}

	public String getUriTargetEncoding() {
		return this.uriTargetEncoding;
	}
	
	/**
	 * 初始化URI编码
	 * 
	 * @param original 原始编码
	 * @param target 目标编码
	 */
	public void initUriEncoding(String original, String target) {
		this.uriOriginalEncoding = original;
		this.uriTargetEncoding = target;
	}

	public Object getConfig() throws PublishException {
		return this.config;
	}

	public ApplicationContext getSpringContext() throws PublishException {
		if (spring_ctxt != null) return spring_ctxt;
		synchronized (this) {
			if (spring_ctxt != null) return spring_ctxt;
			
			// 实际创建 SpringContext.
			String config_file = File.separator + this.root_dir + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "beans-config.xml";
			ApplicationContext spring_ctxt = new FileSystemXmlApplicationContext(config_file);
			this.spring_ctxt = spring_ctxt;
		}
		return spring_ctxt;
	}

	public DataAccessObject getDao() throws PublishException {
		if (this.dao != null)
			return this.dao;

		synchronized (this) {
			if (this.dao != null) return this.dao;
			
			this.dao = (DataAccessObject)this.getSpringContext().getBean("dao");
			
			if (this.dao == null)
				throw new PublishException("无法获得 DAO 对象，请确定您的 beans-config.xml 文件配置正确。"); 
			return this.dao;
		}
	}

	public TransactionProxy getTransactionProxy() throws PublishException {
		if (this.tx_proxy != null) return this.tx_proxy;

		synchronized (this) {
			if (this.tx_proxy != null) return this.tx_proxy;
			
			this.tx_proxy = (TransactionProxy)this.getSpringContext().getBean("proxy");
			//this.proxy.setPublishContext(this);
			
			if (this.tx_proxy == null)
				throw new PublishException("无法获得 TransactionProxy proxy 对象，请确定您的 beans-config.xml 文件配置正确。"); 
			return this.tx_proxy;
		}
	}

	public Site getSite() throws PublishException {
		// 如果当前对象存在，则直接返回。
		if (this.site != null) return this.site;
		
		// 装载此对象。
		synchronized (this) {
			if (this.site != null) return this.site;

			// 获取所有网站信息。
			List result = getDao().list("FROM Site s ORDER BY s.id ASC");
			if (result.size() <= 0)
				throw new PublishException("在数据库中没有找到任何网站的数据，请确定您进行了正确的初始化。");
			if (result.size() > 1) {
			}
			
			// 取第一个做为主站点信息。以后也许可以用多个，其中只有一个是激活的。
			Site site = (Site)result.get(0);
			
			site._initSite(this, null);
			
			this.site = site;
		}
		return this.site;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.PublishContext#updateSite(com.chinaedustar.publish.model.Site)
	 */
	public void updateSite(Site site) {
		this.getDao().update(site);
		this.getDao().flush();
		
		synchronized (this) {
			this.site._destroy();
			this.site = null;
		}
		
		// 重新加载站点信息。
		Site new_site = getSite();
		if (new_site.rebuildStaticPageUrl())
			new_site.updateGenerateStatus();
	}

	public Object getModules() throws PublishException {
		//ModuleCollection modules = this.getSite().getModules();
		//return modules;
		throw new UnsupportedOperationException();
	}

	public PublishContextEvents getEvents() throws PublishException {
		return this.events;
	}

	public GenerateEngine getGenerateEngine() throws PublishException {
		return gen_engine;
	}

	public LabelHandlerMap getLabelHandlers() throws PublishException {
		return this.labelh_map;
	}

	public void log(Log log) {
		try {
			// 直接写入日志表, 忽略错误
			getDao().save(log);
		} catch (Exception ex) {
		}
	}

	public synchronized void close() {
		
		try {
			// destroy events.
			if (this.events != null) {
				// TODO: this.events.close();
				this.events = null;
			}
			
			// destroy site.
			if (this.site != null) {
				this.site._destroy();
				this.site = null;
			}
			
			// destroy dao.
			if (this.dao != null) {
				this.dao.close();
				this.dao = null;
			}
			
			// destroy config.
			if (this.config != null) {
				// this.config.close();
				this.config = null;
			}
		} catch (Exception ex) {
		}
	}
	
	/**
	 * 得到一个可存放全局数据的容器
	 * 
	 * @return
	 */
	public Map getMap() {
		return this.map;
	}
	
}
