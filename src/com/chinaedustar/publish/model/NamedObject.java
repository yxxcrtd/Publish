package com.chinaedustar.publish.model;

/**
 * 定义发布系统具有名字和UUID标识的对象，Site, Channel 等具有此种属性。
 * 
 * @author liujunxing
 *
 */
public interface NamedObject extends ModelObject {
	/**
	 * 获得此对象的唯一标识,实际返回为一个符合 UUID 规则的 String。
	 * @return
	 */
	public String getObjectUuid();
	
	/**
	 * 获得此对象的名字。
	 * @return
	 */
	public String getName();
}