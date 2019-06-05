package com.chinaedustar.publish.itfc;

/**
 * 指明对象支持在 #ShowPath 标签中使用。
 * 
 * @author liujunxing
 *
 */
public interface ShowPathSupport {
	/**
	 * 是否显示在 ShowPath 中。
	 * @return
	 */
	public boolean isShowInPath();
	
	/**
	 * 得到显示在 ShowPath 中的标题。
	 * @return
	 */
	public String getPathTitle();
	
	/**
	 * 得到链接目标属性，用于 a target='xxx'
	 * @return
	 */
	public String getPathTarget();
	
	/**
	 * 得到链接地址。
	 * @return
	 */
	public String getPathPageUrl();
}
