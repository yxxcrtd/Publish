package com.chinaedustar.publish.model;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.itfc.ShowPathSupport;

/**
 * Site 模型对象。
 * 
 * @author liujunxing
 * 
 * (未来应该考虑将 site, channel 合并)
 */
public class Site extends AbstractChannelBase implements UrlResolver, ShowPathSupport, ExtendPropertySupport {	
	/**
	 * 缺省构造函数。
	 *
	 */
	public Site() {
		
	}
	
	/**
	 * 复制构造函数。
	 * @param src
	 */
	public Site(Site src) {
		super(src);
		this.title = src.title;
		this.url = src.url;
		this.installDir = src.installDir;
		this.webmaster = src.webmaster;
		this.webmasterEmail = src.webmasterEmail;
	}
	
	// === 通用 getter, setter ================================================
	
	/**
	 * 获取指定名字的属性值。支持 title, url 等, 其它都委托给了父类实现。
	 * @param name - 要获取的属性名。
	 * @see AbstractNamedModelBase#get(String)
	 */
	@Override public Object get(String name) {
		if ("title".equals(name))
			return this.getTitle();
		else if ("url".equals(name))
			return this.getUrl();
		else if ("installDir".equals(name))
			return this.getInstallDir();
		else if ("webmaster".equals(name))
			return this.getWebmaster();
		else if ("webmasterEmail".equals(name))
			return this.getWebmasterEmail();
		else
			return super.get(name);
	}
	
	/**
	 * 通用 set 方法。
	 * @param name - 属性名，支持 logo, banner, copyright 等, 其它都委托给了父类实现。
	 * @param value 
	 * @see AbstractNamedModelBase#set(String, Object)
	 */
	@Override public void set(String name, Object value) {
		if ("title".equals(name))
			this.setTitle((String)value);
		else if ("url".equals(name))
			this.setUrl((String)value);
		else if ("installDir".equals(name))
			this.setInstallDir((String)value);
		else if ("webmaster".equals(name))
			this.setWebmaster((String)value);
		else if ("webmasterEmail".equals(name))
			this.setWebmasterEmail((String)value);
		else
			super.set(name, value);
	}

	// === 属性定义 ===========================================================
	
	/** 网站的名字，实现在基类中。 */
	
	/** 网站的标题。 */
	private String title;
	
	/** 网站的URL地址，其后面必须有 '/', 如 'http://localhost/PubWeb/' 。 */
	private String url;
	
	/** 此网站安装的目录位置，其前后都必须有 '/' ，如 '/PubWeb/' 。 */
	private String installDir;
	
	/** 管理员名字。 */
	private String webmaster;
	
	/** 管理员的邮件地址。 */
	private String webmasterEmail;
	
	/** 权限标志。 */
	private int privilege_flag;
	
	/** 计费标志。 */
	private int charge_flag;
	
	/** 自定义标志。 */
	private int custom_flag;
	
	/**网站热点的点击数最小值。*/
	private int hitsOfHot;
	
	/**弹出公告窗口的间隔时间。 */
	private int announceCookieTime;
	
	/**是否显示网站频道。*/
	private int showSiteChannel;
	
	/**是否显示管理登陆链接。*/
	private int showAdminLogin;

	/**是否保存远程图片到本地。*/
	private int enableSaveRemote;
	
	/**是否开放友情链接申请。*/
	private int enableLinkReg;
	
	/**是否统计友情链接点击数。*/
	private int enableCountFriendSiteHits;
	
	/**是否使用频道、栏目、专题自设内容。*/
	private int enableSoftKey;

	/** 页面扩展名 = 0 为 '.html' */
	public static final int PAGE_URL_TYPE_HTML = 0;
	/** 页面扩展名 = 1 为 '.htm' */
	public static final int PAGE_URL_TYPE_HTM = 1; 
	/** 页面扩展名 = 2 为 '.shtml' */
	public static final int PAGE_URL_TYPE_SHTML = 2; 
	/** 页面扩展名 = 3 为 '.shtm' */
	public static final int PAGE_URL_TYPE_SHTM = 3; 
	/** 页面扩展名 = 4 为 '.jsp' */
	public static final int PAGE_URL_TYPE_JSP = 4; 
	
	/**网站首页的扩展名, = 0 为 '.html', = 1 为 '.htm', = 2 为 '.shtml', = 3 为 '.shtm', = 4 为 '.jsp' 。*/
	private int fileExt_SiteIndex = PAGE_URL_TYPE_JSP;
	
	/**全站专题的扩展名, = 0 为 '.html', = 1 为 '.htm', = 2 为 '.shtml', = 3 为 '.shtm', = 4 为 '.jsp' 。*/
	private int fileExt_SiteSpecial = PAGE_URL_TYPE_JSP;
	
	/** 链接地址方式, = 0 表示使用 相对路径 */
	public static final int SITE_URL_TYPE_REL = 0;
	/** 链接地址方式, = 1 表示使用绝对路径 */
	public static final int SITE_URL_TYPE_ABS = 1;
	
	/** 链接地址方式, = 0 表示使用 相对路径, = 1 表示使用绝对路径 */
	private int siteUrlType = SITE_URL_TYPE_REL;
	
	/** 是否是主站点标志。 */
	private boolean mainSite = true;
	
	// === PublishModelObject 实现 ================================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.model.PublishModelObject)
	 */
	@Override public synchronized void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super._init(pub_ctxt, owner_obj);
	}
	
	/**
	 * 实际初始化站点对象。
	 * @param pub_ctxt
	 * @param owner_obj
	 */
	public synchronized void _initSite(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		this._init(pub_ctxt, owner_obj);
		
		// 初始化 Module 集合。
		ModuleCollection modules = new ModuleCollection();
		modules._init(pub_ctxt, this);
		this.modules = modules;
		
		// 初始化 Channel 集合。
		ChannelCollection channels = new ChannelCollection();
		channels._init(pub_ctxt, this);
		this.channels = channels;
		
		// 初始化 Label 集合。
		LabelCollection labels = new LabelCollection();
		labels._init(pub_ctxt, this);
		this.labels = labels;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_destroy()
	 */
	@Override public synchronized void _destroy() {
		
		// _destroy channels
		if (this.channels != null) {
			try {
				this.channels._destroy();
			} catch (Exception ex) {
			}
		}
		
		// _destroy modules
		if (this.modules != null) {
			try {
				this.modules._destroy();
			} catch (Exception ex) {
			}
		}
		
		this.modules = null;
		this.channels = null;
		
		super._destroy();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractChannelBase#_getContainerId()
	 */
	@Override public int getChannelId() {
		// 当做为 special, webpage 等容器时我们的标识为 0。
		return 0;
	}
	
	// === 基本属性的 getter, setter ============================================
	
	/**
	 * 获得此网站的标题。
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置此网站的标题。
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获得站点地址。是一个完整的url，例子：'http://www.chinaedustar.com/publish/'
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 设置站点地址。是一个完整的url，例子：'http://www.chinaedustar.com/publish/'
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 此网站安装的目录位置，例子：/School/
	 * @return
	 */
	public String getInstallDir() {
		return this.installDir; 
	}
	
	/**
	 * 设置此网站安装的目录位置。
	 * @param installUrl
	 */
	public void setInstallDir(String installDir) {
		this.installDir = installDir;
	}

	/**
	 * 网站管理员的名字。
	 * @return
	 */
	public String getWebmaster() {
		return this.webmaster;
	}

	/**
	 * 网站管理员的名字。
	 * @return
	 */
	public void setWebmaster(String val) {
		this.webmaster = val;
	}

	/**
	 * 网站管理员的电子邮件地址。
	 * @return
	 */
	public String getWebmasterEmail() {
		return this.webmasterEmail;
	}

	/**
	 * 网站管理员的电子邮件地址。
	 * @return
	 */
	public void setWebmasterEmail(String val) {
		this.webmasterEmail = val;
	}
	
	/**
	 * 获得权限标志。
	 * @return
	 */
	public int getPrivilegeFlag() {
		return this.privilege_flag;
	}
	
	/**
	 * 设置权限标志。
	 * @param privilege_flag
	 */
	protected void setPrivilegeFlag(int privilege_flag) {
		this.privilege_flag = privilege_flag;
	}
	
	/**
	 * 获得计费标志。
	 * @return
	 */
	public int getChargeFlag() {
		return this.charge_flag;
	}
	
	/**
	 * 设置计费标志。
	 * @param charge_flag
	 */
	protected void setChargeFlag(int charge_flag) {
		this.charge_flag = charge_flag;
	}

	/**
	 * 获得自定义信息标志。
	 * @return
	 */
	public int getCustomFlag() {
		return this.custom_flag;
	}
	
	/**
	 * 设置自定义信息标志。
	 * @param custom_flag
	 */
	protected void setCustomFlag(int custom_flag) {
		this.custom_flag = custom_flag;
	}
	
	/**
	 * 获得网站热点的点击数最小值。
	 * @return
	 */
	public int getHitsOfHot(){
		return this.hitsOfHot;
	}
	
	/**
	 * 设置网站热点的点击数最小值。
	 * @param hitOfHot
	 */
	public void setHitsOfHot(int hitsOfHot){
		this.hitsOfHot = hitsOfHot;
	}

	/**
	 * 获得弹出公告窗口的间隔时间。
	 */
	public int getAnnounceCookieTime(){
		return this.announceCookieTime;
	}
	
	/**
	 * 设置弹出公告窗口的间隔时间。
	 * @param announceCookieTime
	 */
	public void setAnnounceCookieTime(int announceCookieTime){
		this.announceCookieTime = announceCookieTime;
	}

	/**
	 * 获取是否显示网站频道。
	 * @return
	 */
	public int getShowSiteChannel(){
		return this.showSiteChannel;
	}
	
	/**
	 * 设置是否显示网站频道。
	 * @param showSiteChannel
	 */
	public void setShowSiteChannel(int showSiteChannel){
		this.showSiteChannel = showSiteChannel;
	}
	
	/**
	 * 获取是否显示管理登陆链接。
	 * @return
	 */
	public int getShowAdminLogin(){
		return this.showAdminLogin;
	}	
	
	/**
	 * 设置是否显示管理登陆链接。
	 * @param showAdminLogin
	 */
	public void setShowAdminLogin(int showAdminLogin){
		this.showAdminLogin = showAdminLogin;
	}
	
	/**
	 * 获取是否保存远程图片到本地。
	 * @return
	 */
	public int getEnableSaveRemote(){
		return this.enableSaveRemote;
	}	
	
	/**
	 * 设置是否保存远程图片到本地。
	 * @param  enableSaveRemote
	 */
	public void setEnableSaveRemote(int enableSaveRemote){
		this.enableSaveRemote = enableSaveRemote;
	}
	
	/**
	 * 获取是否开放友情链接申请。
	 * @return
	 */
	public int getEnableLinkReg(){
		return this.enableLinkReg;
	}	
	
	/**
	 * 设置是否开放友情链接申请。
	 * @param enableLinkReg
	 */
	public void setEnableLinkReg(int enableLinkReg){
		this.enableLinkReg = enableLinkReg;
	}
	
	/**
	 * 获取是否统计友情链接点击数。
	 * @return
	 */
	public int getEnableCountFriendSiteHits(){
		return this.enableCountFriendSiteHits;
	}	
	
	/**
	 * 设置是否统计友情链接点击数。
	 * @param enableCountFriendSiteHits
	 */
	public void setEnableCountFriendSiteHits(int enableCountFriendSiteHits){
		this.enableCountFriendSiteHits = enableCountFriendSiteHits;
	}
	
	/**
	 * 获取是否使用频道、栏目、专题自设内容。
	 * @return
	 */
	public int getEnableSoftKey(){
		return this.enableSoftKey;
	}	
	
	/**
	 * 设置是否使用频道、栏目、专题自设内容。
	 * @param enableSoftKey
	 */
	public void setEnableSoftKey(int enableSoftKey){
		this.enableSoftKey = enableSoftKey;
	}
	
	/**
	 * 获取网站首页的扩展名。
	 * @return 网站首页的扩展名, = 0 为 '.html', = 1 为 '.htm', = 2 为 '.shtml', = 3 为 '.shtm', = 4 为 '.jsp'
	 */
	public int getFileExt_SiteIndex(){
		return this.fileExt_SiteIndex;
	}
	
	/**
	 * 判定网站主页是否需要生成。
	 * @return
	 */
	public boolean getNeedGenerate() {
		// 不等于 jsp 就意味着要生成。
		return this.fileExt_SiteIndex != Site.PAGE_URL_TYPE_JSP;
	}
	
	/**
	 * 判定是否需要生成全站专题。
	 * @return
	 */
	public boolean getNeedGenerateSpecial() {
		// 不等于 jsp 就意味着要生成。
		return this.fileExt_SiteSpecial != Site.PAGE_URL_TYPE_JSP;
	}
	
	/**
	 * 设置网站首页的扩展名。
	 * @param fileExt_SiteIndex - 网站首页的扩展名, = 0 为 '.html', = 1 为 '.htm', = 2 为 '.shtml', = 3 为 '.shtm', = 4 为 '.jsp'
	 */
	public void setFileExt_SiteIndex(int fileExt_SiteIndex){
		this.fileExt_SiteIndex = fileExt_SiteIndex;
	}
	
	/**
	 * 获取全站专题的扩展名。
	 * @return 全站专题的扩展名, = 0 为 '.html', = 1 为 '.htm', = 2 为 '.shtml', = 3 为 '.shtm', = 4 为 '.jsp'
	 */
	public int getFileExt_SiteSpecial(){
		return this.fileExt_SiteSpecial;
	}	
	
	/**
	 * 设置全站专题的扩展名。
	 * @param fileExt_SiteSpecial - 全站专题的扩展名, = 0 为 '.html', = 1 为 '.htm', = 2 为 '.shtml', = 3 为 '.shtm', = 4 为 '.jsp'
	 */
	public void setFileExt_SiteSpecial(int fileExt_SiteSpecial){
		this.fileExt_SiteSpecial = fileExt_SiteSpecial;
	}
	
	/**
	 * 获取链接地址方式。
	 * @return
	 */
	public int getSiteUrlType(){
		return this.siteUrlType;
	}	
	
	/**
	 * 设置链接地址方式。
	 * @param siteUrlType
	 */
	public void setSiteUrlType(int siteUrlType){
		this.siteUrlType = siteUrlType;
	}

	/** 是否是主站点标志。 */
	public boolean getMainSite() {
		return this.mainSite;
	}
	
	/** 是否是主站点标志。 */
	public void setMainSite(boolean value) {
		this.mainSite = value;
	}
	
	// === 集合 ===================================================================
	
	/** 此网站加载的模块集合。 */
	private ModuleCollection modules;
	
	/** 此网站频道的集合。 */
	private ChannelCollection channels;
	
	/** 子对象，模板集合对象。 */
	private TemplateThemeCollection theme_coll;

	/** 此网站的 Label 集合。 */
	private LabelCollection labels;
	
	/**
	 * 获得此站点下加载的模块集合。
	 * @return
	 */
	public ModuleCollection getModules() {
		// Modules 总是在 Site 装载的时候就被装载。
		return this.modules;
	}
	
	/**
	 * 获得指定标识的频道对象。
	 * @param channelId - 频道标识。
	 * @return 频道对象，如果不存在则返回 null.
	 */
	public Channel getChannel(int channelId) {
		return this.getChannels().getChannel(channelId);
	}
	
	/**
	 * 获得此站点下频道集合。
	 * @return
	 */
	public ChannelCollection getChannels() {
		if (this.channels != null) return this.channels;
		synchronized (this) {
			if (this.channels != null) return this.channels;
			
			// 构造 channels 集合并进行初始化。
			ChannelCollection channels = new ChannelCollection();
			channels._init(super._getPublishContext(), this);
			
			this.channels = channels;
		}
		
		return this.channels;
	}

	/**
	 * 得到网站中的管理员集合对象。
	 * @return 返回管理员集合对象。
	 */
	public AdminCollection getAdminCollection() {
		AdminCollection admin_coll = new AdminCollection();
		admin_coll._init(pub_ctxt, this);
		return admin_coll;
	}
	
	/**
	 * 得到模板方案的集合对象。
	 * 模板方案对象一旦创建，将持久缓存在 Site 对象里面。
	 * @return
	 */
	public TemplateThemeCollection getTemplateThemeCollection() {
		if (theme_coll == null) {
			synchronized (this) {
				theme_coll = new TemplateThemeCollection();
				theme_coll._init(super._getPublishContext(), this);
			}
		}
		return theme_coll;
	}

	/**
	 * 得到模板方案的集合对象。
	 * @return
	 */
	public FeedbackCollection getFeedbackCollection() {
		FeedbackCollection feedback_coll = new FeedbackCollection();
		feedback_coll._init(pub_ctxt, this);
		return feedback_coll;
	}
	
	/**
	 * 获得自定义标签的集合。
	 * @return
	 */
	public LabelCollection getLabelCollection() {
		return this.labels;
	}
	
	/**
	 * 获得公告集合对象。
	 * @return
	 */
	public AnnouncementCollection getAnnouncementCollection() {
		AnnouncementCollection ac = new AnnouncementCollection();
		ac._init(super._getPublishContext(), this);
		return ac;
	}

	/**
	 * 获得会员对象的集合对象。
	 * @return
	 */
	public UserCollection getUserCollection() {
		UserCollection user_coll = new UserCollection();
		user_coll._init(pub_ctxt, this);
		return user_coll;
	}

	/**
	 * 获得友情链接集合对象
	 * @return
	 */
	public FriendSiteCollection getFriendSiteCollection() {
		FriendSiteCollection fsc = new FriendSiteCollection();
		fsc._init(super._getPublishContext(), this);
		return fsc;
	}
	
	/**
	 * 获得调查集合对象
	 * @return
	 */
	public VoteCollection getVoteCollection(){
		VoteCollection vc = new VoteCollection();
		vc._init(super._getPublishContext(), this);
		return vc;
	}

	/**
	 * 获得日志集合对象。
	 * @return
	 */
	public LogCollection getLogCollection() {
		LogCollection log_coll = new LogCollection();
		log_coll._init(pub_ctxt, this);
		return log_coll;
	}
	
	/**
	 * 获得网站信息统计对象。
	 * @return
	 */
	public SiteStatistics getSiteStatistics() {
		return new SiteStatistics(_getPublishContext());
	}

	// === 模板 ========================================================================
	
	/**
	 * 得到当前显示模板方案，也即缺省模板方案。
	 * @return
	 */
	public TemplateTheme getDefaultTheme() {
		return this.getTemplateThemeCollection().getDefaultTemplateTheme();
	}

	/**
	 * 刷新发布系统模板缓存。
	 * 模板所有信息都放在 TemplateThemeCollection 里面，所以更新它就可以了。
	 */
	public synchronized void refreshTemplateCache() {
		this.theme_coll = null;
	}
	
	// === StaticSupportObject 接口 ====================================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#calcPageUrl()
	 */
	@Override public String calcPageUrl() {
		// 如果设置为 jsp 的，返回 'index.jsp' 的地址。
		if (this.fileExt_SiteIndex == Site.PAGE_URL_TYPE_JSP)
			return resolveUrl("index.jsp");
		
		// 返回静态页面地址。
		return resolveUrl(getStaticPageUrl());
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#getNewStaticPageUrl(com.chinaedustar.publish.PublishContext)
	 */
	protected String getNewStaticPageUrl(PublishContext pub_ctxt) {
		if (this.getNeedGenerate() == false) return "";
		
		return "index" + getFileExtName(fileExt_SiteIndex);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractModelBase#getUrlResolver()
	 */
	@Override public UrlResolver getUrlResolver() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ModelObject#resolvePath(java.lang.String)
	 */
	public String resolvePath(String child_obj_path) {
		java.io.File file = null;
		String root_dir = pub_ctxt.getRootDir();
		if (root_dir.endsWith("\\"))
			file = new java.io.File(root_dir + child_obj_path);
		else
			file = new java.io.File(root_dir + "\\" + child_obj_path);
		return file.getAbsolutePath();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ModelObject#resolveUrl(java.lang.String)
	 */
	public String resolveUrl(String rel_url) {
		if (rel_url == null || rel_url.length() == 0) return null;
		// 如果地址已经是绝对地址了，则不再处理，节省一些时间。
		if (rel_url.startsWith("http://") || rel_url.startsWith("https://"))
			return rel_url;
		
		// 基准地址。
		String base_url = (siteUrlType == SITE_URL_TYPE_ABS) ? this.url : this.installDir;		
		
		try {
			java.net.URI base_uri = new java.net.URI(base_url);
			return String.valueOf(base_uri.resolve(rel_url));
		} catch (java.net.URISyntaxException ex) {
			return rel_url;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.UrlResolver#relativizeUrl(java.lang.String)
	 */
	public String relativizeUrl(String abs_url) {
		if (abs_url == null || abs_url.length() == 0) return null;
		String base_url = this.url;
		
		try {
			java.net.URI base_uri = new java.net.URI(base_url);
			java.net.URI abs_uri = new java.net.URI(abs_url);
			return String.valueOf(base_uri.relativize(abs_uri));
		} catch (java.net.URISyntaxException ex) {
			// 如果链接语法不对，则返回原来地址。
			return abs_url;
		}
	}

	// === ShowPathSupport 接口实现 ====================================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#isShowInPath()
	 */
	public boolean isShowInPath() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#getPathTitle()
	 */
	public String getPathTitle() {
		return this.getTitle();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#getPathTarget()
	 */
	public String getPathTarget() {
		return "";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#getPathPageUrl()
	 */
	public String getPathPageUrl() {
		return this.getUrl();
	}

	// === ExtendPropertySupport 支持 =================================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.ExtendPropertySupport#hintPropNum()
	 */
	@Override public int hintPropNum() {
		return -1;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.ExtendPropertySupport#propNumChanged(int)
	 */
	@Override public void propNumChanged(int num) {
		super.propNumChanged(num);
		this.custom_flag = num;
		
		// 也许应该重新加载一次。
		Site site = pub_ctxt.getSite();
		if (site != this)
			site.propNumChanged(num);
	}

	// === 业务方法 ====================================================================
	
	/**
	 * 加载一个栏目对象，也许不应该放在这里。
	 * @param owner
	 * @return
	 */
	public Column loadColumn(int columnId, PublishModelObject owner_obj) {
		Column column = (Column)pub_ctxt.getDao().get(Column.class, columnId);
		if (column == null) return null;
		column._init(pub_ctxt, owner_obj == null ? this : owner_obj);
		return column;
	}

	// === 希望添加的其它属性 =============================================================
	
	/**
	 * 返回站点文件使用的编码格式。
	 * @return 当前返回 'GB2312'. 也许在 linux 上面我们被迫使用 UTF-8?
	 */
	public String getCharset() {
		return "GB2312";
	}

	/**
	 * 获得搜索分页数量，其配置在网站扩展属性中名为 'searchPageSize' 中，类型为 int. 缺省为 20。
	 * @return
	 */
	public int getSearchPageSize() {
		int page_size = 20;
		try {
			ExtendProperty prop = this.getExtends().get("searchPageSize");
			if (prop == null) return page_size;
			if ("int".equals(prop.getPropType())) {
				page_size = (Integer)prop.getValue();
			} else if ("string".equals(prop.getPropType())) {
				page_size = Integer.parseInt(prop.getPropValue());
			}
			if (page_size <= 1) page_size = 20;
			return page_size;
		} catch (Exception ex) {
			return page_size;
		}
	}
	
	/**
	 * 获得是否是调试模式。
	 * @return
	 */
	public boolean getDebugMode() {
		try {
			ExtendProperty prop = this.getExtends().get("debugMode");
			if (prop == null) return false;
			if ("int".equals(prop.getPropType())) {
				return ((Integer)prop.getValue()).equals(1);
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
}
