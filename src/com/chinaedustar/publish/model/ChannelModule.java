package com.chinaedustar.publish.model;


/**
 * 定义能够制作频道的模块必须实现的接口。
 * 
 * @author liujunxing
 */
public interface ChannelModule extends PublishModule {
	/**
	 * 获得此模块项目类型的名字，如 'Article', 'Soft', 'Photo' 等。
	 * @return 返回对象类的名字，如 'Article', 'Soft', 'Photo' 等。
	 */
	public String getItemClass();
	
	/**
	 * 获得此模块项目的一般性名字，如文章、下载、图片。
	 * @return
	 */
	public String getDefaultItemName();

	/**
	 * 获得此模块项目的一般性单位，如篇、个、幅。
	 * @return
	 */
	public String getDefaultItemUnit();

	/**
	 * 加载指定标识的项目对象。
	 * @param itemId
	 * @return
	 */
	public Item loadItem(int itemId);
}
