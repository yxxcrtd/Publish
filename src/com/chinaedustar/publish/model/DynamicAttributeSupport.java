package com.chinaedustar.publish.model;

/**
 * 定义对象为支持动态属性而实现的接口。动态属性以扩展属性方式实现，但不保存到 DB 中。
 * 
 * @author liujunxing
 */
public interface DynamicAttributeSupport extends ExtendPropertySupport {
	/**
	 * 设置指定名字的属性，此属性只在内存中存在，不保留到数据库中。设置的属性通过
	 *   getExtends() 访问。
	 * @param name - 属性名字。
	 * @param value - 属性值。
	 */
	public void setAttribute(String name, Object value);
	
	/**
	 * 删除指定名字的属性。
	 * @param name
	 */
	public void removeAttribute(String name);
}
