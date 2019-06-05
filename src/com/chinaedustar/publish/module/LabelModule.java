package com.chinaedustar.publish.module;

import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Item;

/**
 * 标签中心使用的模块
 */
public class LabelModule extends AbstractItemModule {

	/**
	 * 缺省构造
	 */
	public LabelModule() {
		super();
	}
	
	/**
	 * 获得指定频道的菜单。
	 * @param channel
	 * @return
	 */
	public Menu getChannelMenu(Channel channel, Admin admin) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PublishModule#getModuleName()
	 */
	public String getModuleName() {
		return "LabelModule";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PublishModule#getModuleTitle()
	 */
	public String getModuleTitle() {
		return "标签模块";
	}

	// === ChannelModule 实现 ====================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getItemClassName()
	 */
	public String getItemClass() {
		return "Label";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getItemGeneralName()
	 */
	public String getDefaultItemName() {
		return "标签";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getDefaultItemUnit()
	 */
	public String getDefaultItemUnit() {
		return "个";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#loadItem(int)
	 */
	public Item loadItem(int itemId) {
		throw new UnsupportedOperationException();
	}
}


