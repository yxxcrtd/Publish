package com.chinaedustar.publish.module;

import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;

/**
 * 定义能够为频道提供管理菜单的模块接口，实现了此接口的模块能够为频道提供
 *  一个自己的管理菜单。
 * 
 * @author liujunxing
 */
public interface ManageMenuModule {
	/**
	 * 获得指定频道的菜单。
	 * @param channel
	 * @return
	 */
	public Menu getChannelMenu(Channel channel, Admin admin);
}
