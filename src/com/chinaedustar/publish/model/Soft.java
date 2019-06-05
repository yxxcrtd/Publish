package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.PublishUtil;

/**
 * 代表一个软件项目。
 * 
 * @author liujunxing
 */
public class Soft extends Item {
	/**
	 * 构造函数。
	 *
	 */
	public Soft() {
		super.setItemClass("soft");
	}
	
	/** 软件的版本。比如“2.0”。 */
	private String version;
	
	/** 软件的缩略图地址。 */
	private String thumbPic;	
	
	/** 软件类型。比如“国产软件”。 */
	private String type;
	
	/** 适用的操作系统。 */
	private String OS;
	
	/** 软件使用的语言。比如“简体中文”。 */
	private String language;
	
	/** 版权类型。比如“免费版”。 */
	private String copyrightType;
	
	/** 软件大小。以K为单位。 */
	private int size;
	
	/** 软件下载地址。 */
	private String downloadUrls;
	
	/** 测试版本所在的URL地址。 */
	private String demoUrl;
	
	/** 软件注册地址。 */
	private String registUrl;
	
	/** 解压密码。 */
	private String decompPwd;
	
	/** 最后一次点击的时间。 */
	private java.util.Date lastHitTime;
	
	/** 本日点击次数。 */
	private int dayHits;
	
	/** 本周点击次数。 */
	private int weekHits;
	
	/** 本月点击次数。 */
	private int monthHits;
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.Item#get(java.lang.String)
	 */
	@Override public Object get(String name) {
		if ("version".equals(name)) {
			return this.version;
		} else if ("thumbPic".equals(name)) {
			return this.thumbPic;
		} else if ("type".equals(name)) {
			return this.type;
		} else if ("OS".equals(name)) {
			return this.OS;
		} else if ("language".equals(name)) {
			return this.language;
		} else if ("copyright".equals(name)) {
			return this.copyrightType;
		} else if ("size".equals(name)) {
			return this.size;
		} else if ("downloadUrls".equals(name)) {
			return this.downloadUrls;
		} else if ("demoUrl".equals(name)) {
			return this.demoUrl;
		} else if ("registUrl".equals(name)) {
			return this.registUrl;
		} else if ("decompPwd".equals(name)) {
			return this.decompPwd;
		} else if ("lastHitTime".equals(name)) {
			return this.lastHitTime;
		} else if ("dayHits".equals(name)) {
			return this.dayHits;
		} else if ("weekHits".equals(name)) {
			return this.weekHits;
		} else if ("monthHits".equals(name)) {
			return this.monthHits;
		} else {
			return super.get(name);
		}
			
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.Item#calcPageUrl()
	 */
	@Override public String calcPageUrl() {
		Channel channel = this.getChannel();
		if (channel == null) return "";
		String page_url = "";
		if (channel.getNeedGenerateItem()) {
			page_url = getStaticPageUrl();
		} else {
			page_url = "showSoft.jsp?softId=" + getId(); 
		}
		return channel.resolveUrl(page_url);
	}

	/**
	 * 获取软件的缩略图地址。
	 * @return
	 */
	public String getThumbPic() {
		return thumbPic;
	}
	
	/**
	 * 得到绝对化过的图片地址。
	 * @return
	 */
	public String getThumbPicAbs() {
		if (this.thumbPic == null || this.thumbPic.length() == 0)
			return this.thumbPic;
		
		return this.getUrlResolver().resolveUrl(this.thumbPic);
	}

	/**
	 * 设置软件的缩略图地址。
	 * @param thumbPic
	 */
	public void setThumbPic(String thumbPic) {
		this.thumbPic = thumbPic;
	}
	
	/**
	 * 获取版权类型。比如“免费版”。
	 * @return
	 */
	public String getCopyrightType() {
		return copyrightType;
	}
	
	/**
	 * 设置版权类型。比如“免费版”。
	 * @param copyrightType
	 */
	public void setCopyrightType(String copyrightType) {
		this.copyrightType = copyrightType;
	}

	/** 
	 * 获取解压密码。
	 * @return
	 */
	public String getDecompPwd() {
		return decompPwd;
	}
	
	/**
	 * 设置解压密码。
	 * @param decompPwd
	 */
	public void setDecompPwd(String decompPwd) {
		this.decompPwd = decompPwd;
	}
	
	/**
	 * 获取用来演示软件的URL地址。
	 * @return
	 */
	public String getDemoUrl() {
		return demoUrl;
	}
	
	/**
	 * 设置用来演示软件的URL地址。
	 * @param demoUrl
	 */
	public void setDemoUrl(String demoUrl) {
		this.demoUrl = demoUrl;
	}
	
	/**
	 * 获取软件下载地址。
	 * @return
	 */
	public String getDownloadUrls() {
		return downloadUrls;
	}
	
	/**
	 * 设置软件下载地址。
	 * @param downloadUrl
	 */
	public void setDownloadUrls(String downloadUrls) {
		this.downloadUrls = downloadUrls;
	}
	
	/**
	 * 
	 * 设置软件使用的语言。比如“简体中文”。
	 * @return
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * 设置软件使用的语言。比如“简体中文”。
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * 获取适用的操作系统。
	 * @return
	 */
	public String getOS() {
		return OS;
	}
	
	/**
	 * 设置适用的操作系统。
	 * @param os
	 */
	public void setOS(String os) {
		this.OS = os;
	}
	
	/**
	 * 获取软件注册地址。
	 * @return
	 */
	public String getRegistUrl() {
		return registUrl;
	}
	
	/**
	 * 设置软件注册地址。
	 * @param registUrl
	 */
	public void setRegistUrl(String registUrl) {
		this.registUrl = registUrl;
	}
	
	/**
	 * 获取软件大小。以K为单位。
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * 设置软件大小。以K为单位。
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * 获取软件类型。比如“国产软件”。
	 * @return
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 设置软件类型。比如“国产软件”。
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 获取软件的版本。比如“2.0”。
	 * @return
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * 设置软件的版本。比如“2.0”。
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * 获取本日点击次数。
	 * @return
	 */
	public int getDayHits() {
		return dayHits;
	}
	
	/**
	 * 设置本日点击次数。
	 * @param dayHits
	 */
	public void setDayHits(int dayHits) {
		this.dayHits = dayHits;
	}
	
	/**
	 * 获取最后一次点击的时间。
	 * @return
	 */
	public java.util.Date getLastHitTime() {
		return lastHitTime;
	}
	
	/**
	 * 设置最后一次点击的时间。
	 * @param lastHitTime
	 */
	public void setLastHitTime(java.util.Date lastHitTime) {
		this.lastHitTime = lastHitTime;
	}
	
	/**
	 * 获取本月点击次数。
	 * @return
	 */
	public int getMonthHits() {
		return monthHits;
	}
	
	/**
	 * 设置本月点击次数。
	 * @param monthHits
	 */
	public void setMonthHits(int monthHits) {
		this.monthHits = monthHits;
	}
	
	/**
	 * 获取本周点击次数。
	 * @return
	 */
	public int getWeekHits() {
		return weekHits;
	}
	
	/**
	 * 设置本周点击次数。
	 * @param weekHits
	 */
	public void setWeekHits(int weekHits) {
		this.weekHits = weekHits;
	}

	// === 扩展对象方法 ==============================================================
	
	/**
	 * 获得下载地址列表的对象表达方式。
	 */
	public List<UrlEntry> getDownloadUrlList() {
		if (this.downloadUrls == null) return null;
		
		String[] str_files = this.downloadUrls.split("\\$\\$\\$");
		if (str_files == null || str_files.length == 0) return null;
		
		UrlResolver url_resovler = this.getUrlResolver();
		List<UrlEntry> download_url_entrys = new java.util.ArrayList<UrlEntry>();
		for (int i = 0; i < str_files.length; ++i) {
			int pos = str_files[i].indexOf('|');
			String name, url;
			if (pos >= 0) {
				name = str_files[i].substring(0, pos);
				url = str_files[i].substring(pos + 1);
			} else {
				name = str_files[i];
				url = "";
			}
			url = url_resovler.resolveUrl(url);
			download_url_entrys.add(new UrlEntry(name, url));
		}
		
		return download_url_entrys;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.Item#beforeSave(java.lang.Object)
	 */
	@Override public void beforeSave(Object sender) {
		super.beforeSave(sender);
		
		// 对缩略图地址进行反解析。
		UrlResolver url_resolver = this.getUrlResolver();
		if (this.thumbPic != null && this.thumbPic.length() > 0)
			this.thumbPic = url_resolver.relativizeUrl(this.thumbPic);
		
		// 软件下载地址进行反解析
		List<UrlEntry> url_entrys = getDownloadUrlList();
		if (url_entrys != null && url_entrys.size() > 0) {
			StringBuilder strbuf = new StringBuilder();
			for (int i = 0; i < url_entrys.size(); ++i) {
				UrlEntry entry = url_entrys.get(i);
				entry.setUrl(url_resolver.relativizeUrl(entry.getUrl()));
				if (i > 0) strbuf.append("$$$");
				strbuf.append(entry.toString());
			}
			this.downloadUrls = strbuf.toString();
		}
	}

	/** 替代 null 表示没有一个软件。 */
	private static final Soft NULL_SOFT = new Soft();
	
	/**
	 * 得到此文章对象在频道中的前一篇软件。
	 *   前一篇软件指同频道中 id 比当前软件大的最小 的一篇软件。
	 */
	public Soft getPrevSoft() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "PrevSoft_" + this.getUuid();
		Soft prev = (Soft)thread_cache.getNamedObject(cache_key);
		if (prev == NULL_SOFT) return null;
		if (prev != null) return prev;
		
		String hql = " FROM Soft " +
				" WHERE channelId=" + getChannelId() +
				"  AND deleted=false AND status=1 AND id > " + this.getId() + 
				" ORDER BY id ASC ";

		// 获得数据
		@SuppressWarnings("unchecked")
		List<Soft> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);
		
		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_SOFT);
			return null;
		}
		prev = list.get(0);
		prev._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, prev);
		return prev;
		
	}

	/**
	 * 获得当前软件在该栏目的下一篇软件。
	 *  下一篇软件指同栏目中 id 比当前软件小的最大的 一篇软件。
	 * @return
	 */
	public Soft getNextSoft() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "NextSoft_" + this.getUuid();
		Soft next = (Soft)thread_cache.getNamedObject(cache_key);
		if (next == NULL_SOFT) return null;
		if (next != null) return next;

		String hql = "FROM Soft " +
				" WHERE channelId=" + getChannelId() + 
				"  AND deleted=false AND status=1 AND id < " + getId() + 
				" ORDER BY id DESC";
		// 获得数据
		@SuppressWarnings("unchecked")
		List<Soft> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);

		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_SOFT);
			return null;
		}
		next = list.get(0);
		next._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, next);
		return next;
	}
	
	
	/**
	 * 得到此软件对象在栏目中的前一篇软件。
	 *   前一篇软件指同栏目中 id 比当前软件大的最小 的一篇软件。
	 */
	public Soft getPrevColumnSoft() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "PrevColumnSoft_" + this.getUuid();
		Soft prev = (Soft)thread_cache.getNamedObject(cache_key);
		if (prev == NULL_SOFT) return null;
		if (prev != null) return prev;

		String hql = " FROM Soft " +
				" WHERE channelId=" + getChannelId() +
				"  AND columnId=" + getColumnId() + " AND deleted=false AND status=1" +
				"  AND id > " + this.getId() + 
				" ORDER BY id ASC ";

		// 获得数据
		@SuppressWarnings("unchecked")
		List<Soft> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);
		
		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_SOFT);
			return null;
		}
		prev = list.get(0);
		prev._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, prev);
		return prev;
	}
	
	/**
	 * 获得当前软件在该栏目的下一篇软件。
	 *  下一篇软件指同栏目中 id 比当前软件小的最大的 一篇软件。
	 * @return
	 */
	public Soft getNextColumnSoft() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "NextColumnSoft_" + this.getUuid();
		Soft next = (Soft)thread_cache.getNamedObject(cache_key);
		if (next == NULL_SOFT) return null;
		if (next != null) return next;

		String hql = "FROM Soft " +
				" WHERE channelId=" + getChannelId() + 
				"  AND columnId=" + getColumnId() + 
				"  AND deleted=false AND status=1 " + " AND id < " + getId() + 
				" ORDER BY id DESC";
		// 获得数据
		@SuppressWarnings("unchecked")
		List<Soft> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);

		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_SOFT);
			return null;
		}
		
		next = list.get(0);
		next._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, next);
		return next;
	}
}
