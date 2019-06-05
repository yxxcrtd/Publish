package com.chinaedustar.publish.comp;

import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;

/**
 * ChannelRightWrapper, ColumnRightWrapper 的基类。
 * 
 * @author liujunxing
 *
 */
public abstract class BaseRightWrapper {
	/** 频道对象。 */
	protected final Channel channel;
	
	/** 站点对象。 */
	protected final Admin admin;
	
	/**
	 * 构造。
	 * @param channel
	 * @param admin
	 */
	public BaseRightWrapper(Channel channel, Admin admin) {
		this.channel = channel;
		this.admin = admin;
	}
	
	/**
	 * 获得管理员对象。
	 * @return
	 */
	public Admin getAdmin() {
		return this.admin;
	}
	
	/**
	 * 获得频道。
	 * @return
	 */
	public Channel getChannel() {
		return this.channel;
	}
	
	/** 获得频道标识。 */
	public int getChannelId() {
		return this.channel.getId();
	}
	
	/**
	 * 获得这个频道的一个唯一名，我们通过 'channelDir_id' 来生成唯一名字。
	 * @return
	 */
	public String getUniqueName() {
		return getUniqueName(channel);
	}
	
	/**
	 * 获得这个频道的一个唯一名，我们通过 'channelDir_id' 来生成唯一名字。
	 * @return
	 */
	public static String getUniqueName(Channel channel) {
		return channel.getChannelDir() + "_" + channel.getId();
	}
}
