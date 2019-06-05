package com.chinaedustar.publish.model;

import java.util.Date;

/**
 * 关键字对象。
 * 
 * @author wangyi
 *
 */
public class Keyword extends AbstractNamedModelBase {
	/** 频道的标识，默认为0：关键字属于整个网站。 */
	private int channelId;
	
	/** 最后使用时间。 */
	private Date lastUseTime;
	
	/** 关键字的点击次数。 */
	private int hits;
	
	/**
	 * 频道的标识，默认为0：关键字属于整个网站。
	 * @return
	 */
	public int getChannelId() {
		return channelId;
	}
	
	/**
	 * 频道的标识，默认为0：关键字属于整个网站。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 关键字的点击次数。
	 * @return
	 */
	public int getHits() {
		return hits;
	}
	
	/**
	 * 关键字的点击次数。
	 * @param hits
	 */
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	/**
	 * 最后使用时间。
	 * @return
	 */
	public Date getLastUseTime() {
		return lastUseTime;
	}
	
	/**
	 * 最后使用时间。
	 * @param lastUseTime
	 */
	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}
}
