package com.chinaedustar.publish.model;

import com.chinaedustar.publish.*;

/**
 * 频道统计信息对象。
 * 
 * @author liujunxing
 *
 */
public class ChannelStatistics {
	private final PublishContext pub_ctxt;
	private final Channel channel;
	
	/**
	 * 使用指定的发布系统环境对象和频道对象构造一个 ChannelStatistics 的实例。
	 * @param pub_ctxt
	 * @param channel
	 */
	public ChannelStatistics(PublishContext pub_ctxt, Channel channel) {
		this.pub_ctxt = pub_ctxt;
		this.channel = channel;
	}
	
	/**
	 * 获得发布系统环境对象。
	 * @return
	 */
	public PublishContext getPublishContext() {
		return this.pub_ctxt;
	}
	
	/**
	 * 获得频道对象。
	 * @return
	 */
	public Channel getChannel() {
		return this.channel;
	}
	
	/** 此频道中项目数量，不含已经删除的。 -1 表示未统计过。 */
	private int item_num = -1;
	
	/**
	 * 此频道中项目数量，不含已经删除的。
	 * @return
	 */
	public int getItemNum() {
		if (item_num == -1) {
			item_num = (int)channel.getTotalItemCount();
		}
		return item_num;
	}
	
	/** 此频道中未审核的项目数量。 -1 表示未统计过。 */
	private int unappr_num = -1;
	
	/**
	 * 获得未审核的项目数量。
	 * @return
	 */
	public int getUnapprNum() {
		if (unappr_num == -1)
			unappr_num = (int)channel.getUnapprItemCount(); 
		return unappr_num;
	}
	
	public String getItemName() {
		return channel.getItemName();
	}
	
	/**
	 * 获得项目的单位用字。如：篇，个。
	 * @return
	 */
	public String getItemUnit() {
		return channel.getItemUnit();
	}
	
	/**
	 * 获得此频道的名字。
	 * @return
	 */
	public String getChannelName() {
		return channel.getName();
	}
	
	/**
	 * 获得此频道的链接地址。
	 * @return
	 */
	public String getUrl() {
		return channel.getPageUrl();
	}
}
