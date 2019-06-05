package com.chinaedustar.publish.model;

import com.chinaedustar.publish.PublishContext;

/**
 * 定义发布系统模块的接口，所有发布系统的模块都必须实现此接口。
 * 
 * @author liujunxing
 */
public interface PublishModule {
	/**
	 * 模块被加载进入内存的时候的初始化，调用且仅调用一次。
	 * @param pub_ctxt - 发布系统环境对象。
	 */
	public void initialize(PublishContext pub_ctxt);
	
	/**
	 * 获得模块的唯一名字。
	 * @return
	 */
	public String getModuleName();
	
	/**
	 * 获得模块的中文名字（标题）。
	 * @return
	 */
	public String getModuleTitle();
	
	/**
	 * 模块从内存卸载的时候，被调用且仅调用一次。
	 *
	 */
	public void destroy();
}
