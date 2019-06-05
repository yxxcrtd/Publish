package com.chinaedustar.publish.itfc;

import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Admin;

/**
 * 表示一个支持设置频道权限的模块接口。
 * 
 * @author liujunxing
 *
 */
public interface ChannelRightModule {
	/**
	 * 为使用所给权限界面模板所准备的数据。
	 * @param channel
	 * @return
	 */
	public Object createRightData(Channel channel, Admin admin);
	
	/**
	 * 为设置频道权限界面所使用的模板的名字。
	 * @return
	 */
	public String getTemplateName(Channel channel);
}
