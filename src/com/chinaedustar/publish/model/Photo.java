package com.chinaedustar.publish.model;

import java.util.*;
import com.chinaedustar.publish.util.QueryHelper;
	
/**
 * 表示一个(组)图片。
 * 
 * @author liujunxing
 * 
 * 关于图片链接地址问题，为了最大可移植性，图片地址最好相对于 site.installDir, 例如：
 *  一个图片地址 http://localhost/PubWeb/photo/UploadDirs/2007/06/20070601091038503.jpg
 *  则保存在数据库中 使用 'photo/UploadDirs/2007/06/20070601091038503.jpg'
 */
public class Photo extends Item {
	/**
	 * 构造函数。
	 *
	 */
	public Photo() {
		super.setItemClass("photo");
	}
	
	/** 图片的缩略图 */
	private String thumbPic;
	
	/** 
	 * 所有上传的图片集合,格式为“图片名称|图片地址”
	 * 例子： '图片地址1|/PubWeb/photo/UploadDirs/2007/06/20070601091038503.jpg$$$图片地址2|/PubWeb/photo/UploadDirs/2007/06/20070601091038504.jpg' 
	 */
	private String pictureUrls;
	
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
		if ("photoThumb".equalsIgnoreCase(name)) {
			return thumbPic;
		} else if ("pictureUrls".equalsIgnoreCase(name)) {
			return thumbPic;
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
	}
	
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
			page_url = "showPhoto.jsp?photoId=" + getId(); 
		}
		return channel.resolveUrl(page_url);
	}

	// === getter, setter ========================================================
	
	/**
	 * 得到图片的缩略图
	 */
	public String getThumbPic() {
		return this.thumbPic;
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
	 * 设置图片的缩略图
	 * @param photoThumb
	 */
	public void setThumbPic(String photoThumb) {
		this.thumbPic = photoThumb;
	}
	
	/**
	 * 获取所有上传的图片集合
	 * @return
	 */
	public String getPictureUrls() {
		return pictureUrls;
	}
	
	/**
	 * 获取所有上传的图片集合。
	 * @return 返回一个 List, 此集合每次返回都是新建的，也就是改变集合不会改变 photo 的内容。
	 */
	public List<UrlEntry> getPictureList() {
		if (this.pictureUrls == null) return null;
		
		String[] str_files = this.pictureUrls.split("\\$\\$\\$");
		if (str_files == null || str_files.length == 0) return null;
		
		UrlResolver url_resolver = this.getUrlResolver();
		ArrayList<UrlEntry> pic_entrys = new ArrayList<UrlEntry>();
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
			url = url_resolver.resolveUrl(url);
			pic_entrys.add(new UrlEntry(name, url));
		}
		
		return pic_entrys;
	}
	
	/**
	 * 设置所有上传的图片集合
	 * @param pictureUrls
	 */
	public void setPictureUrls(String photoUrl) {
		this.pictureUrls = photoUrl;
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

	// === 获得相关业务对象 =========================================================

	private static final Photo NULL_PHOTO = new Photo();
	
	/**
	 * 得到此图片的前一张图片对象，如果没有则返回 null.
	 *   前一张图片定义为同频道同栏目 id 在当前图片之前的图片。
	 * @return
	 */
	public Photo getPrevPhoto() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "PrevPhoto_" + this.getUuid();
		Photo prev = (Photo)thread_cache.getNamedObject(cache_key);
		if (prev == NULL_PHOTO) return null;
		if (prev != null) return prev;
		
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Photo ";
		query.whereClause = " WHERE channelId=" + this.getChannelId()
			+ " AND deleted=false AND status=1"
			+ " AND id < " + this.getId();
		query.orderClause = " ORDER BY id DESC";
		
		return queryAndInitReturn(query, thread_cache, cache_key);
	}
	
	/**
	 * 得到此图片的前一张此频道的图片对象，如果没有则返回 null.
	 *   此频道前一张图片定义为同频道 id 在当前图片之前的图片。
	 * @return
	 */
	public Photo getPrevColumnPhoto() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "PrevColumnPhoto_" + this.getUuid();
		Photo prev = (Photo)thread_cache.getNamedObject(cache_key);
		if (prev == NULL_PHOTO) return null;
		if (prev != null) return prev;
		
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Photo ";
		query.whereClause = " WHERE channelId=" + this.getChannelId()
			+ " AND columnId=" + this.getColumnId() + " AND deleted=false AND status=1"
			+ " AND id < " + this.getId();
		query.orderClause = " ORDER BY id DESC";
		
		return queryAndInitReturn(query, thread_cache, cache_key);
	}

	/**
	 * 得到此图片的后一张图片对象，如果没有则返回 null.
	 *   后一张图片定义为同频道同栏目 id 比当前图片大的最小的一个。
	 * @return
	 */
	public Photo getNextPhoto() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "NextPhoto_" + this.getUuid();
		Photo next = (Photo)thread_cache.getNamedObject(cache_key);
		if (next == NULL_PHOTO) return null;
		if (next != null) return next;
		
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Photo ";
		query.whereClause = " WHERE channelId=" + this.getChannelId()
			+ " AND deleted=false AND status=1"
			+ " AND id > " + this.getId();
		query.orderClause = " ORDER BY id ASC";
		
		return queryAndInitReturn(query, thread_cache, cache_key);
	}

	/**
	 * 得到此图片的后一张图片对象，如果没有则返回 null.
	 *   后一张图片定义为同频道 id 比当前图片大的最小的一个。
	 * @return
	 */
	public Photo getNextColumnPhoto() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "NextColumnPhoto_" + this.getUuid();
		Photo next = (Photo)thread_cache.getNamedObject(cache_key);
		if (next == NULL_PHOTO) return null;
		if (next != null) return next;
		
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Photo ";
		query.whereClause = " WHERE channelId=" + this.getChannelId()
			+ " AND columnId=" + this.getColumnId() + " AND deleted=false AND status=1"
			+ " AND id > " + this.getId();
		query.orderClause = " ORDER BY id ASC";
		
		return queryAndInitReturn(query, thread_cache, cache_key);
	}

	/**
	 * 执行指定查询并返回单一 Photo 对象，进行模型 _init() 之后返回。
	 * @param query
	 * @return
	 */
	private Photo queryAndInitReturn(QueryHelper query, ThreadCurrentMap thread_cache, String cache_key) {
		Photo photo = (Photo)query.querySingleData(pub_ctxt.getDao());
		if (photo != null)
			photo._init(pub_ctxt, owner_obj);
		thread_cache.putNamedObject(cache_key, photo == null ? NULL_PHOTO : photo);
		return photo;
	}
}
