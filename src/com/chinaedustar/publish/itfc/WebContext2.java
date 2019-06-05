package com.chinaedustar.publish.itfc;

import com.chinaedustar.publish.PublishContext;

/**
 * 定义页面的交互环境。
 * 
 * @author liujunxing
 */
public interface WebContext2 {
	/**
	 * 获得发布系统环境对象。
	 * @return
	 */
	public PublishContext getPublishContext();

	/**
	 * 获得指定范围内的变量。
	 * @param name - 变量名字。
	 * @param scope - 范围值，取值为 page, request, session, application
	 * @return
	 */
	public Object getAttribute(String name, String scope);
	
	/**
	 * 设置指定范围的变量值。
	 * @param name - 变量名字。
	 * @param value - 变量的值。
	 * @param scope - 范围值，取值为 page, request, session, application
	 */
	public void setAttribute(String name, Object value, String scope);

	/**
	 * 获得指定键的请求参数值。
	 * @param key
	 * @return
	 */
	public String getRequestParam(String key);
}
