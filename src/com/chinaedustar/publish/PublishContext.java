package com.chinaedustar.publish;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.chinaedustar.publish.engine.GenerateEngine;
import com.chinaedustar.publish.event.PublishContextEvents;
import com.chinaedustar.publish.label.LabelHandlerMap;
import com.chinaedustar.publish.model.Log;
import com.chinaedustar.publish.model.Site;

/**
 * 定义发布系统的上下文环境
 * 
 * @author liujunxing
 */
public interface PublishContext {
	
	/** 环境变量在 ServletContext 中存储的键值。 */
	public static final String PUBLISH_CONTEXT_KEY = "com.chinaedustar.publish.PublishContext";

	/**
	 * 获得发布系统的配置信息。
	 * @return
	 * @throws PublishExcpetion
	 */
	public Object getConfig() throws PublishException;
	
	/**
	 * 得到发布系统的根目录，如：d:\projects\PubWeb。
	 * @return
	 */
	public String getRootDir();
	
	/**
	 * 得到URI原始编码
	 * @return
	 */
	public String getUriOriginalEncoding();
	
	/**
	 * 得到URI目标编码
	 * @return
	 */
	public String getUriTargetEncoding();
	
	/**
	 * 获得 Spring 的 ApplicationContext 对象。
	 * @return
	 * @throws PublishException
	 */
	public ApplicationContext getSpringContext() throws PublishException;
	
	/**
	 * 获得数据访问对象。
	 * @return
	 * @throws PublishExcpetion
	 */
	public DataAccessObject getDao() throws PublishException;
	
	/**
	 * 得到发布系统的事务代理对象。
	 * @return
	 * @throws PublishException
	 */
	public TransactionProxy getTransactionProxy() throws PublishException;
	
	/**
	 * 获得站点模型对象。
	 * @return
	 * @throws PublishException
	 */
	public Site getSite() throws PublishException;
	
	/**
	 * 更新站点的信息，将导致整个网站重新加载。
	 *
	 */
	public void updateSite(Site site);
	
	/**
	 * 获得安装在此环境中的所有模块的集合。
	 * @return
	 * @throws PublishExcpetion
	 */
	public Object getModules() throws PublishException;

	/**
	 * 获得此发布系统环境下事件集合。
	 * @return
	 * @throws PublishException
	 */
	public PublishContextEvents getEvents() throws PublishException;

	/**
	 * 获得静态化页面生成引擎。
	 * @return
	 * @throws PublishException
	 */
	public GenerateEngine getGenerateEngine() throws PublishException;
	
	/**
	 * 获得标签解释器集合对象。
	 * @return
	 * @throws PublishException
	 */ 
	public LabelHandlerMap getLabelHandlers() throws PublishException;
	
	/**
	 * 关闭 PublishContext, 清理其占用的所有资源。
	 *
	 */
	public void close();
	
	/**
	 * 记录操作日志。
	 * @param log - 要记录的日志。
	 */
	public void log(Log log);
	
	/**
	 * 得到一个可存放全局数据的容器。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getMap();
}
