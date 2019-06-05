package com.chinaedustar.publish.model;

/**
 * 页面使用的模板对象。
 * 
 * @author liujunxing
 */
public class PageTemplate extends AbstractNamedModelBase {
	// === 网站级页面 ============================================================
	/** 网站首页模板类型名字, = 'home' */
	public static final String HOME_PAGE = "home";
	
	/** 网站搜索页模板, = 'search' */
	public static final String SEARCH_PAGE = "search";
	
	/** 网站用户登录页面模板, = 'login_window' */
	public static final String LOGIN_WINDOW = "login_window";
	
	/** 网站公告页模板, = 'announce' */
	public static final String SHOW_ANNOUNCE = "announce";
	
	/** 友情链接页面模板, = 'friend_site' */
	public static final String FRIEND_SITE = "friend_site";
	
	/** 版权声明页模板. */
	public static final String COPY_RIGHT = "copyright";
	
	/** 网站自定义页面模板, = 'webpage' */
	public static final String WEB_PAGE = "webpage";
	
	/** 投票页面， = 'vote' */
	public static final String VOTE = "vote";
	
	// === 频道级页面 ============================================================
	/** 频道主页模板类型名字, = 'index' */
	public static final String CHANNEL_INDEX_PAGE = "index";
	
	/** 频道栏目模板类型名字, = 'column' */
	public static final String CHANNEL_COLUMN_PAGE = "column";
	
	/** 频道专题栏目类型名字, = 'special' */
	public static final String CHANNEL_SPECIAL_PAGE = "special";
	
	/** 频道内容页模板类型名字, = 'content' */
	public static final String CHANNEL_CONTENT_PAGE = "content";
	
	/** 频道的精华页面模板, = 'elite' */
	public static final String CHANNEL_ELITE_PAGE = "elite";
	
	/** 频道的推荐页面模板, = 'commend' */
	public static final String CHANNEL_COMMEND_PAGE = "commend";
	
	/** 频道的热门页面模板, = 'hots' */
	public static final String CHANNEL_HOTS_PAGE = "hots";
	
	/** 频道最新项目模板, = 'newest' */
	public static final String CHANNEL_NEWEST_PAGE = "newest";
	
	/** 频道专题列表页面模板, = 'special_list' */
	public static final String CHANNEL_SPECIAL_LIST_PAGE = "special_list";
	
	/** 频道搜索页面模板, = 'search' */
	public static final String CHANNEL_SEARCH = "search";
	
	/** 频道项目评论页面模板, = 'comments' */
	public static final String CHANNEL_COMMENTS = "comments";
	
	
	/** 所应用到的频道。如果频道标识=0，表示这是该模板方案的原始模板，当应用到频道的时候会复制一个给该频道。 */
	private int channelId;
	
	/** 此模板所属的模板方案标识。FK 引用到Temp_Theme表格。 */
	private int themeId;

	/** 模板类型，对于特定的模板类型此指是固定的。  */
	private int typeId;
	
	/** 模板的实际内容。 */
	private String content;

	/** 是否是缺省的。 */
	private boolean isDefault;
		
	/** 是否是方案中缺省的。 */
	private boolean themeDefault;
	
	/** 是否已经被删除了。 */
	private boolean deleted;
	
	/**
	 * 缺省构造函数。
	 *
	 */
	public PageTemplate() {
		
	}
	

	/**
	 * 复制。
	 * @param src
	 */
	@Override protected void copy(AbstractModelBase src) {
		copy(src);
	}
	
	/**
	 * 复制。
	 * @param src
	 */
	protected void copy(PageTemplate src) {
		super.copy(src);
		this.themeId = src.themeId;
		this.channelId = src.channelId;
		this.typeId = src.typeId;
		this.content = src.content;
		this.themeDefault = src.themeDefault;
		this.isDefault = src.isDefault;
		this.deleted = src.deleted;
	}

	// === getter, setter ====================================================
	
	/**
	 * 获取所应用到的频道。如果频道标识=0，表示这是该模板方案的原始模板，当应用到频道的时候会复制一个给该频道。
	 * @return
	 */
	public int getChannelId() {
		return this.channelId;
	}
	
	/**
	 * 设置所应用到的频道。如果频道标识=0，表示这是该模板方案的原始模板，当应用到频道的时候会复制一个给该频道。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 获取此模板所属的模板方案标识。
	 * @return
	 */
	public int getThemeId() {
		return this.themeId;
	}
	
	/**
	 * 设置此模板所属的模板方案标识。
	 * @param themeId
	 */
	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}
	
	/**
	 * 获取模板类型，对于特定的模板类型此指是固定的。 
	 * @return
	 */
	public int getTypeId() {
		return this.typeId;
	}
	
	/**
	 * 设置模板类型，对于特定的模板类型此指是固定的。 
	 * @param type
	 */
	public void setTypeId(int type) {
		this.typeId = type;
	}

	/**
	 * 获取模板的实际内容。
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * 设置模板的实际内容。
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否是缺省的。
	 * @return
	 */
	public boolean getIsDefault() {
		return this.isDefault;
	}
	
	/**
	 * 设置是否是缺省的。
	 * @param isDefault
	 */
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	/**
	 * 获取是否是方案中缺省的。
	 * @return
	 */
	public boolean getThemeDefault() {
		return this.themeDefault;
	}
	
	/**
	 * 设置是否是方案中缺省的。
	 * @param themeDefault
	 */
	public void setThemeDefault(boolean themeDefault) {
		this.themeDefault = themeDefault;
	}
	
	/**
	 * 获取是否已经被删除了。
	 * @return
	 */
	public boolean getDeleted() {
		return this.deleted;
	}
	
	/**
	 * 设置是否已经被删除了。
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
