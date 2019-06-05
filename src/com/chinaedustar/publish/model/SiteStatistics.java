package com.chinaedustar.publish.model;

import java.util.*;
import com.chinaedustar.publish.*;

/**
 * 网站的统计信息对象，其能够计算所需的所有统计信息。
 * 
 * @author liujunxing
 *
 */
public class SiteStatistics {
	private final PublishContext pub_ctxt;
	/**
	 * 使用指定的发布系统环境构造一个 SiteStatistics 的实例。
	 * @param pub_ctxt
	 */
	public SiteStatistics(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	public PublishContext getPublishContext() {
		return this.pub_ctxt;
	}
	
	/** 网站的会员数量， = -1 表示未统计过，其它数值表示统计结果。 */
	private int user_num = -1;
	
	/**
	 * 获得网站会员用户数量。
	 * @return 网站的用户数量。
	 */
	public int getUserNum() {
		if (user_num == -1) {
			String hql = "SELECT COUNT(*) FROM User";
			user_num = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		}
		return user_num;
	}
	
	/** 频道数量， = -1 表示未统计过，其它数值表示统计结果。 */
	private int channel_num = -1;
	
	/**
	 * 获得网站的频道数量。
	 * @return 返回网站频道数量。
	 */
	public int getChannelNum() {
		if (channel_num == -1) {
			String hql = "SELECT COUNT(*) FROM Channel WHERE status = 0";
			channel_num = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		}
		return channel_num;
	}

	/** 网站统计对象集合。 */
	private ArrayList<ChannelStatistics> channel_stat = null;
	
	/**
	 * 获得频道统计对象集合。
	 * @return
	 */
	public ArrayList<ChannelStatistics> getChannelStats() {
		if (channel_stat == null) {
			channel_stat = createChannelStats();
		}
		return channel_stat;
	}
	
	/** 创建频道统计对象。 */
	private final ArrayList<ChannelStatistics> createChannelStats() {
		ArrayList<ChannelStatistics> stats = new ArrayList<ChannelStatistics>();
		Iterator<Channel> iter = pub_ctxt.getSite().getChannels().iterator();
		while (iter.hasNext()) {
			Channel channel = iter.next();
			ChannelStatistics channel_stat = new ChannelStatistics(pub_ctxt, channel);
			stats.add(channel_stat);
		}
		return stats;
	}
}
