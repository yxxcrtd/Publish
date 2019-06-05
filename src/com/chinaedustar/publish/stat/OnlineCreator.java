package com.chinaedustar.publish.stat;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.util.DateUtil;


/**
 * StatOnline 信息的创建者。
 * @author liujunxing
 *
 */
public class OnlineCreator {
	/** 数据访问者对象。 */
	private StatDao stat_dao;
	
	/** 发布系统环境对象。 */
	private PublishContext pub_ctxt;
	
	/** 数据访问者对象。 */
	public StatDao getStatDao() {
		return this.stat_dao;
	}
	
	/** 数据访问者对象。 */
	public void setStatDao(StatDao stat_dao) {
		this.stat_dao = stat_dao;
	}
	
	/** 发布系统环境对象。 */
	public PublishContext getPublishContext() {
		return this.pub_ctxt;
	}

	/** 发布系统环境对象。 */
	public void setPublishContext(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	/** 用户 IP */
	private String ip;
	
	/** 用户使用的浏览器。 */
	private String userAgent;
	
	/** 用户来自的页面。 */
	private String referer;
	
	/** 用户 IP */
	public String getIp() {
		return this.ip;
	}
	
	/** 用户 IP */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/** 用户使用的浏览器。 */
	public String getUserAgent() {
		return this.userAgent;
	}
	
	/** 用户使用的浏览器。 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	/** 用户来自的页面。 */
	public String getReferer() {
		return this.referer;
	}
	
	/** 用户来自的页面。 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * 记录用户在线信息。
	 *
	 */
	public void doStatOnline() {
		// 0. 数据防止 null
		if (this.ip == null) this.ip = "";
		if (this.referer == null) this.referer = "";
		if (this.userAgent == null) this.userAgent = "";
		
		// 1. 得到配置。
		int onlineTime = getOnlineTime();
		java.util.Date now = DateUtil.getNow();
		java.util.Date onl_time = DateUtil.addSeconds(now, -onlineTime);
		
		// 2. 得到该用户(ip)，在有效在线时间以内的记录 id。
		int id = stat_dao.getUserOnlineRecordId(onl_time, ip);
		if (id != 0) {
			// 如果有该记录，则更新该记录。
			stat_dao.updateUserOnlineRecord(id, now, this.referer);
		} else {
			// 否则要新建一条记录。这里会选择覆盖过期的一条，或新建一条。
			// 得到某个过期的记录标识。
			id = stat_dao.getOuttimeUserOnlineRecord(onl_time);
			if (id != 0) {
				// 覆盖此记录。
				stat_dao.overwriteUserOnlineRecord(id, ip, userAgent, referer, onl_time);
			} else {
				// 插入一条新的。
				stat_dao.insertUserOnlineRecord(ip, userAgent, referer, onl_time);
			}
		}
	}
	
	/**
	 * 得到配置中 OnlineTime (在线用户保留时间)，缺省为 150。
	 * @return
	 */
	private int getOnlineTime() {
		StatMain stat_main = StatHelper.getCacheStatMain(pub_ctxt, stat_dao);
		return stat_main.getOnlineTime();
	}
}
