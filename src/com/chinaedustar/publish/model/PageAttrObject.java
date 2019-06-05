package com.chinaedustar.publish.model;

/**
 * 定义具有页面产生属性的模型对象的接口。
 * 
 * <p>Site, Channel, Column, Special 都以 WebPage 来表现，从而实现此接口。</p>
 * 
 * @author liujunxing
 */
public interface PageAttrObject extends NamedObject, StaticSupportObject {
	/**
	 * 获得此页面的 Logo.
	 * @return
	 */
	public String getLogo();
	
	/**
	 * 获得此页面的 Banner.
	 * @return
	 */
	public String getBanner();
	
	/**
	 * 获得此页面的 Copyright.
	 * @return
	 */
	public String getCopyright();
	
	/**
	 * 获得此页面的 MetaKey.
	 * @return
	 */
	public String getMetaKey();
	
	/**
	 * 获得此页面的 MetaDesc.
	 * @return
	 */
	public String getMetaDesc();
	
	/**
	 * 获得此页面使用的模板标识。
	 * @return
	 */
	public int getTemplateId();
	
	/**
	 * 获得样式标识。
	 * @return
	 */
	public int getSkinId();
}
