package com.chinaedustar.publish.model;

/**
 * 静态化支持的接口。
 * 
 * @author liujunxing
 *
 */
public interface StaticSupportObject {
	/**
	 * 获得对象的页面的地址，页面地址已经根据 site, channel 的配置计算为合适的绝对目标位置地址。
	 * @return 对象的页面的地址。
	 */
	public String getPageUrl();
	
	/**
	 * 获取是否已经生成静态化页面。
	 * @return
	 */
	public boolean getIsGenerated();
	
	/**
	 * 设置是否已经生成静态化页面。
	 * (由静态化引擎调用)
	 */
	public void setIsGenerated(boolean isGenerated);
	
	/**
	 * 获取静态化页面地址, 页面地址相对于其父对象，当前用作相对地址解析支持的有 Site, Channel。
	 * @return 返回此对象静态化页面地址，即使没有生成也返回该地址。
	 */
	public String getStaticPageUrl();
	
	/** 获得最后一次生成的时间。 */
	public java.sql.Timestamp getLastGenerated();

	/**
	 * 设置静态化页面地址。
	 * @param staticPageUrl
	 */
	// public void setStaticPageUrl(String staticPageUrl);
	
	/**
	 * 重建静态化页面地址，并返回静态地址是否更改过。
	 *   注意。如果重建地址和原地址不符合，需要将以前的文件清理掉。
	 * @return 静态地址是否更改过。
	 */
	public boolean rebuildStaticPageUrl();
	
	/**
	 * 更新生成状态及静态化页面地址。
	 */
	public void updateGenerateStatus();
	
	/**
	 * 删除一个对象的静态化页面，并重置其“isGenerated”为false。
	 */
	// public void deleteStaticPage();
}
