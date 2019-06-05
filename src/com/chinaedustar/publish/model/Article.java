package com.chinaedustar.publish.model;

import java.util.List;
import com.chinaedustar.publish.PublishUtil;

/**
 * 表示一个文章。
 * 
 * @author liujunxing
 */
public class Article extends Item {
	/** 文章的副标题。 */
	private String subheading;
	
	/** 文章内容。 */
	private String content;
	
	/** 文章的显示属性，0：空；1：[图文]；2：[组图]；3：[推荐]；4：[注意]。 */
	private int includePic;
	
	/** 缺省图片的链接地址，需要从文章中上传的图片中选择。 */
	private String defaultPicUrl;
	
	/** 所有上传的图片集合，图片的服务器端的相对地址，用“|”号分割。 */
	private String uploadFiles;
	
	/** 标题的颜色。 */
	private String titleFontColor;
	
	/** 标题字体类型。 */
	private int titleFontType;
	
	/** 分页用属性，每页多少字符。 */
	private int maxCharPerPage;
	
	/** 分页用属性，分页类型。 */
	private int paginationType;
	
	/**
	 * 缺省构造函数。
	 *
	 */
	public Article() {
		super.setItemClass("article");
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.Item#get(java.lang.String)
	 */
	@Override public Object get(String name) {
		if ("subheading".equalsIgnoreCase(name)) {
			return subheading;
		} else if ("content".equalsIgnoreCase(name)) {
			return content;
		} else if ("includePic".equalsIgnoreCase(name)) {
			return includePic;
		} else if ("defaultPicUrl".equalsIgnoreCase(name)) {
			return defaultPicUrl;
		} else if ("uploadFiles".equalsIgnoreCase(name)) {
			return uploadFiles;
		} else if ("titleFontColor".equalsIgnoreCase(name)) {
			return titleFontColor;
		} else if ("titleFontType".equalsIgnoreCase(name)) {
			return titleFontType;
		} else if ("maxCharPerPage".equalsIgnoreCase(name)) {
			return maxCharPerPage;
		} else if ("paginationType".equalsIgnoreCase(name)) {
			return paginationType;
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
			page_url = "showArticle.jsp?articleId=" + getId(); 
		}
		return channel.resolveUrl(page_url);
	}
	
	// === getter, setter ========================================================
	
	/**
	 * 设置文章的副标题。
	 * @return
	 */
	public String getSubheading() {
		return subheading;
	}
	
	/**
	 * 得到文章的副标题。
	 * @param subheading
	 */
	public void setSubheading(String subheading) {
		this.subheading = subheading;
	}
	
	/**
	 * 得到文章内容。
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * 设置文章内容。
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 得到是否包含图片。
	 * @return
	 */
	public int getIncludePic() {
		return this.includePic;
	}
	
	/**
	 * 设置是否包含图片。
	 * @param includePic
	 */
	public void setIncludePic(int includePic) {
		this.includePic = includePic;
	}
	
	/**
	 * 得到缺省图片的链接地址。
	 * @return
	 */
	public String getDefaultPicUrl() {
		return defaultPicUrl;
	}
	
	/**
	 * 得到绝对化处理过的缺省图片的链接地址。
	 * @return
	 */
	public String getDefaultPicUrlAbs() {
		if (this.defaultPicUrl == null || this.defaultPicUrl.length() == 0)
			return this.defaultPicUrl;
		
		return this.getUrlResolver().resolveUrl(this.defaultPicUrl);
	}
	
	/**
	 * 设置缺省图片的链接地址。
	 * @param defaultPicUrl
	 */
	public void setDefaultPicUrl(String defaultPicUrl) {
		this.defaultPicUrl = defaultPicUrl;
	}
	
	/**
	 * 得到分页用属性，每页多少字符。
	 * @return
	 */
	public int getMaxCharPerPage() {
		return maxCharPerPage;
	}
	
	/**
	 * 设置分页用属性，每页多少字符。
	 * @param maxCharPerPage
	 */
	public void setMaxCharPerPage(int maxCharPerPage) {
		this.maxCharPerPage = maxCharPerPage;
	}
	
	/**
	 * 得到分页用属性，分页类型。
	 * @return
	 */
	public int getPaginationType() {
		return paginationType;
	}
	
	/**
	 * 设置分页用属性，分页类型。
	 * @param paginationType
	 */
	public void setPaginationType(int paginationType) {
		this.paginationType = paginationType;
	}
	
	/**
	 * 得到标题的颜色。
	 * @return
	 */
	public String getTitleFontColor() {
		return titleFontColor;
	}
	
	/**
	 * 设置标题的颜色。
	 * @param titleFontColor
	 */
	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}
	
	/**
	 * 得到标题字体类型。
	 * @return
	 */
	public int getTitleFontType() {
		return titleFontType;
	}
	
	/**
	 * 设置标题字体类型。
	 * @param titleFontType
	 */
	public void setTitleFontType(int titleFontType) {
		this.titleFontType = titleFontType;
	}
	
	/**
	 * 得到所有上传的图片集合。
	 * @return
	 */
	public String getUploadFiles() {
		return uploadFiles;
	}
	
	/**
	 * 设置所有上传的图片集合。
	 * @param uploadFiles
	 */
	public void setUploadFiles(String uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	// === 业务方法 ==========================================================

	/** 替代 null 表示没有一个文章。 */
	private static final Article NULL_ARTICLE = new Article();
	
	/**
	 * 得到此文章对象在频道中的前一篇文章。
	 *   前一篇文章指同频道中 id 比当前文章大的最小 的一篇文章。
	 */
	public Article getPrevArticle() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "PrevArticle_" + this.getUuid();
		Article prev = (Article)thread_cache.getNamedObject(cache_key);
		if (prev == NULL_ARTICLE) return null;
		if (prev != null) return prev;
		
		String hql = " FROM Article " +
				" WHERE channelId=" + getChannelId() +
				"  AND deleted=false AND status=1 AND id > " + this.getId() + 
				" ORDER BY id ASC ";

		// 获得数据
		@SuppressWarnings("unchecked")
		List<Article> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);
		
		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_ARTICLE);
			return null;
		}
		prev = list.get(0);
		prev._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, prev);
		return prev;
		
	}

	/**
	 * 获得当前文章在该栏目的下一篇文章。
	 *  下一篇文章指同栏目中 id 比当前文章小的最大的 一篇文章。
	 * @return
	 */
	public Article getNextArticle() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "NextArticle_" + this.getUuid();
		Article next = (Article)thread_cache.getNamedObject(cache_key);
		if (next == NULL_ARTICLE) return null;
		if (next != null) return next;

		String hql = "FROM Article " +
				" WHERE channelId=" + getChannelId() + 
				"  AND deleted=false AND status=1 AND id < " + getId() + 
				" ORDER BY id DESC";
		// 获得数据
		@SuppressWarnings("unchecked")
		List<Article> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);

		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_ARTICLE);
			return null;
		}
		next = list.get(0);
		next._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, next);
		return next;
	}
	
	
	/**
	 * 得到此文章对象在栏目中的前一篇文章。
	 *   前一篇文章指同栏目中 id 比当前文章大的最小 的一篇文章。
	 */
	public Article getPrevColumnArticle() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "PrevColumnArticle_" + this.getUuid();
		Article prev = (Article)thread_cache.getNamedObject(cache_key);
		if (prev == NULL_ARTICLE) return null;
		if (prev != null) return prev;

		String hql = " FROM Article " +
				" WHERE channelId=" + getChannelId() +
				"  AND columnId=" + getColumnId() + " AND deleted=false AND status=1" +
				"  AND id > " + this.getId() + 
				" ORDER BY id ASC ";

		// 获得数据
		@SuppressWarnings("unchecked")
		List<Article> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);
		
		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_ARTICLE);
			return null;
		}
		prev = list.get(0);
		prev._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, prev);
		return prev;
	}
	
	/**
	 * 获得当前文章在该栏目的下一篇文章。
	 *  下一篇文章指同栏目中 id 比当前文章小的最大的 一篇文章。
	 * @return
	 */
	public Article getNextColumnArticle() {
		ThreadCurrentMap thread_cache = ThreadCurrentMap.default_current();
		String cache_key = "NextColumnArticle_" + this.getUuid();
		Article next = (Article)thread_cache.getNamedObject(cache_key);
		if (next == NULL_ARTICLE) return null;
		if (next != null) return next;

		String hql = "FROM Article " +
				" WHERE channelId=" + getChannelId() + 
				"  AND columnId=" + getColumnId() + 
				"  AND deleted=false AND status=1 " + " AND id < " + getId() + 
				" ORDER BY id DESC";
		// 获得数据
		@SuppressWarnings("unchecked")
		List<Article> list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 1);

		if (list == null || list.size() == 0) {
			thread_cache.putNamedObject(cache_key, NULL_ARTICLE);
			return null;
		}
		
		next = list.get(0);
		next._init(_getPublishContext(), owner_obj);
		thread_cache.putNamedObject(cache_key, next);
		return next;
	}
	
	private List<UpFile> upload_file_coll = null;
	
	/**
	 * 得到此文章的上载文件集合。
	 *  前提条件: 对象必须模型化 (_init())，对象已经持久化了，从而有一个 id.
	 * @return
	 */
	public Object getUploadFilesColl() {
		// 缓存了。
		if (this.upload_file_coll != null) return upload_file_coll;
		
		if (this.pub_ctxt == null || this.getId() == 0) return null;
		if (this.uploadFiles == null || this.uploadFiles.length() == 0) return null;
		
		// 尝试加载。
		List<Integer> ids = PublishUtil.splitIdList(uploadFiles, "|");
		if (ids == null) return null;
		
		String hql = "FROM UpFile WHERE id IN (" + PublishUtil.toSqlInString(ids) + ")";
		@SuppressWarnings("unchecked")
		List<UpFile> result = (List<UpFile>)pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(result, pub_ctxt, this);
		this.upload_file_coll = result;
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.Item#beforeSave(java.lang.Object)
	 */
	@Override public void beforeSave(Object sender) {
		super.beforeSave(sender);
		
		// 对缩略图地址进行反解析。
		UrlResolver url_resolver = this.getUrlResolver();
		if (this.defaultPicUrl != null && this.defaultPicUrl.length() != 0) {
			this.defaultPicUrl = url_resolver.relativizeUrl(this.defaultPicUrl);
		}
	}
}
