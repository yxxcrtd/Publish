package com.chinaedustar.publish.engine;

import java.util.Map;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.itfc.LabelHandler;
import com.chinaedustar.publish.model.Announcement;
import com.chinaedustar.publish.model.Article;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.ChannelSearch;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.Item;
import com.chinaedustar.publish.model.PageAttrObject;
import com.chinaedustar.publish.model.PageTemplate;
import com.chinaedustar.publish.model.PaginationInfo;
import com.chinaedustar.publish.model.Photo;
import com.chinaedustar.publish.model.Site;
import com.chinaedustar.publish.model.SiteSearch;
import com.chinaedustar.publish.model.Soft;
import com.chinaedustar.publish.model.SpecialWrapper;
import com.chinaedustar.publish.model.TemplateTheme;
import com.chinaedustar.publish.model.TemplateThemeCollection;
import com.chinaedustar.publish.model.User;
import com.chinaedustar.publish.model.VoteWrapper;
import com.chinaedustar.publish.model.WebPage;

/**
 * 发布系统页面生成器。
 * 
 * @author liujunxing
 *
 */
public class PageGenerator {
	
	/** 发布系统环境。 */
	private final PublishContext pub_ctxt;
	
	/** 站点对象。 */
	protected Site site; 
	
	/** 当前在访问的用户。 */
	protected User user;
	
	/** 模板标识, = 0 表示使用缺省模板. */
	private int templateId;
	
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 */
	public PageGenerator(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
		this.site = pub_ctxt.getSite();
	}

	/** 模板标识, = 0 表示使用缺省模板. */
	public int getTemplateId() {
		return this.templateId;
	}
	
	/** 模板标识, = 0 表示使用缺省模板. */
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	/**
	 * 获得用户对象。
	 * @return
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * 设置用户对象。
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	// === 站点主页 ==================================================================
	
	/**
	 * 生成网站主页。 'index.jsp'
	 * @return
	 * 主页支持的对象有 'site', 'this=site', 'user(TODO)'
	 */
	public String generateIndexPage() {
		// 准备变量。
		Map<String, Object> vars = getSiteVars(null);
		
		// 找到主页的模板。
		PageTemplate template = getSitePageTemplate(site);
		if (template == null) return "(找不到主页的模板)";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成用户登录页面。 'memberLogin.jsp'
	 * @return
	 * 该页面支持的对象有: 'site', 'user'
	 */
	public String generateMemberLoginPage() {
		// 准备变量。
		Map<String, Object> vars = this.getSiteVars(null);
		
		// 找到主页登录的模板。
		PageTemplate template = getSiteOtherPageTemplate(PageTemplate.LOGIN_WINDOW);
		if (template == null) return "找不到登录页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成公告页面。 'showAnnounce.jsp'
	 * @return
	 */
	public String generateAnnouncePage(Announcement announce) {
		// 准备变量。
		java.util.Map<String, Object> vars = this.getSiteVars(null);
		vars.put("announce", announce);
		vars.put("this", announce);

		// 找到主页公告的模板。
		PageTemplate template = getSiteOtherPageTemplate(PageTemplate.SHOW_ANNOUNCE);
		if (template == null) return "找不到公告页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成搜索页.
	 * @return
	 */
	public String generateSearchPage(SiteSearch search, PaginationInfo page_info) {
		// 准备变量。
		java.util.Map<String, Object> vars = this.getSiteVars(null);
		vars.put("search", search);
		vars.put(LabelHandler.PAGINATION_INFO_NAME, page_info);
		
		// 找到主页搜索页的模板。
		PageTemplate template = getSiteOtherPageTemplate(PageTemplate.SEARCH_PAGE);
		if (template == null) return "找不到搜索页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成友情链接页面。
	 * @return
	 */
	public String generateFriendSitePage(int linkType, int kindId, int specialId, int kindType) {
		// 准备变量。
		Map<String, Object> vars = getSiteVars(null);
		vars.put("linkType", linkType);
		vars.put("kindId", kindId);
		vars.put("specialId", specialId);
		vars.put("kindType", kindType);
		
		// 找到模板。
		PageTemplate template = getSiteOtherPageTemplate(PageTemplate.FRIEND_SITE);
		if (template == null) return "找不到友情链接页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成网站级通用页面.
	 * @return
	 */
	public String generateHomeGeneralPage(String type_name, Map<String, Object> vars) {
		// 准备变量.
		if (vars == null)
			vars = getSiteVars(vars);
		else {
			vars.put("site", site);
			if (vars.containsKey("this") == false)
				vars.put("this", site);
		}
		
		// 找到模板。
		PageTemplate template = getSiteOtherPageTemplate(type_name);
		if (template == null) return "找不到页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}

	// === 频道页面 ==================================================================
	
	/**
	 * 生成频道主页。 'channel/index.jsp'
	 * @return
	 */
	public String generateChannelIndexPage(Channel channel) {
		// 创建频道主页的数据。
		Map<String, Object> vars = getChannelVars(channel);
		
		// 找到对应的模板。
		PageTemplate template = getChannelIndexPageTemplate(channel);
		if (template == null) return "没有找到频道主页的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	 
	public String generateChannelIndexPage(Channel channel, int page) {
		// 准备分页对象
		PaginationInfo page_info = new PaginationInfo(page, 20);
		
		// 创建频道主页的数据。
		Map<String, Object> vars = getChannelVars(channel);
		vars.put(LabelHandler.PAGINATION_INFO_NAME, page_info);
		
		// 找到对应的模板
		PageTemplate template = getChannelIndexPageTemplate(channel);
		if (template == null) return "没有找到频道主页的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成栏目页面。 'channel/showColumn.jsp'
	 * @param channel - 频道对象。
	 * @param column - 栏目对象。
	 * @return
	 */
	public String generateColumnPage(Channel channel, Column column) {
		return generateColumnPage(channel, column, 1);
	}
	
	/**
	 * 生成栏目页面。 'channel/showColumn.jsp'
	 * @param channel - 频道对象。
	 * @param column - 栏目对象。
	 * @param page - 页次，页次=1 表示是栏目首页。
	 * @return
	 */
	public String generateColumnPage(Channel channel, Column column, int page) {
		// 准备分页对象。
		PaginationInfo page_info = new PaginationInfo(page, column.getMaxPerPage());
		page_info.setFirstPageUrl(column.getPageUrl());
		String url_pattern = column.getListUrlPattern();
		String resolved_url_pattern = channel.resolveUrl(url_pattern);
		page_info.setUrlPattern(resolved_url_pattern);
		
		// 生成该页面。
		return generateColumnListPage(channel, column, page_info);
	}
	
	/**
	 * 生成指定栏目的项目列表页面。
	 * @param channel
	 * @param column
	 * @return
	 */
	public String generateColumnListPage(Channel channel, Column column, PaginationInfo page_info) {
		Map<String, Object> vars = getColumnVars(channel, column);
		vars.put(LabelHandler.PAGINATION_INFO_NAME, page_info);

		// 找到对应的模板。
		PageTemplate template = getColumnPageTemplate(channel, column);
		if (template == null) return "没有找到栏目的模板";
		String template_content = template.getContent();
	
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成频道专题页面。 'channel/showSpecial.jsp'
	 * @param channel
	 * @param special
	 * @return
	 */
	public String generateChannelSpecialPage(Channel channel, SpecialWrapper special) {
		Map<String, Object> vars = getChannelSpecialVars(channel, special);

		// 找到对应的模板。
		PageTemplate template = getSpecialPageTemplate(channel, special);
		if (template == null) return "没有找到专题的模板";
		String template_content = template.getContent();
	
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	/**
	 * 生成频道精华项目页面。 'channel/showElite.jsp'
	 * @param channel
	 * @return
	 */
	public String generateChannelElitePage(Channel channel) {
		return generateChannelTypedPage(channel, PageTemplate.CHANNEL_ELITE_PAGE);
	}
	
	/**
	 * 生成频道推荐项目页面。 'channel/showCommend.jsp'
	 * @param channel
	 * @return
	 */
	public String generateChannelCommendPage(Channel channel) {
		return generateChannelTypedPage(channel, PageTemplate.CHANNEL_COMMEND_PAGE);
	}
	
	/**
	 * 生成频道热点项目页面。 'channel/showHots.jsp'
	 * @param channel
	 * @return
	 */
	public String generateChannelHotPage(Channel channel) {
		return generateChannelTypedPage(channel, PageTemplate.CHANNEL_HOTS_PAGE);
	}
	
	/**
	 * 生成频道最新新闻页面。 'channel/showNew.jsp'
	 * @param channel
	 * @return
	 */
	public String generateChannelNewPage(Channel channel) {
		return generateChannelTypedPage(channel, PageTemplate.CHANNEL_NEWEST_PAGE);
	}
	
	/**
	 * 生成频道专题列表页面。 'channel/showSpecialList.jsp'
	 * @param channel
	 * @return
	 */
	public String generateChannelSpecialListPage(Channel channel) {
		return generateChannelTypedPage(channel, PageTemplate.CHANNEL_SPECIAL_LIST_PAGE);
	}
	
	/**
	 * 生成频道搜索页面。 'channel/search.jsp'
	 * @param channel
	 * @param search
	 * @param page_info
	 * @return
	 */
	public String generateChannelSearchPage(Channel channel, ChannelSearch search, PaginationInfo page_info) {
		// 创建搜索页面的数据。
		Map<String, Object> vars = getChannelVars(channel);
		vars.put("search", search);
		vars.put("ShowAdvanceSearch", (search == null) ? true : false);
		vars.put(LabelHandler.PAGINATION_INFO_NAME, page_info);
		
		// 找到对应的模板。
		PageTemplate template = getChannelOtherPageTemplate(channel, PageTemplate.CHANNEL_SEARCH);
		if (template == null) return "没有找到搜索页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	// === 项目页面 =================================================================
	
	/**
	 * 生成文章页面。 'news/showArticle.jsp?articleId=6'
	 * @param channel
	 * @param article
	 * @return
	 */
	public String generateArticlePage(Channel channel, Article article) {
		return this.generateItemPage(channel, article);
	}
	
	/**
	 * 生成图片页面，如 'photo/showPhoto.jsp?photoId=3'
	 * @param channel
	 * @param photo
	 * @return
	 */
	public String generatePhotoPage(Channel channel, Photo photo) {
		return this.generateItemPage(channel, photo);
	}
	
	/**
	 * 生成软件页面，如 'soft/showSoft.jsp?softId=10'
	 * @param channel
	 * @param soft
	 * @return
	 */
	public String generateSoftPage(Channel channel, Soft soft) {
		return this.generateItemPage(channel, soft);
	}
	
	/**
	 * (通用) 生成项目页面。包括支持 article, photo, soft.
	 * @param channel
	 * @param item
	 * @return
	 */
	public String generateItemPage(Channel channel, Item item) {
		// 得到文章所在栏目树。
		Column column = channel.getColumnTree().getColumnWithParent(item.getColumnId());
		if (column != null)
			item.setColumn(column);
		
		Map<String, Object> vars = getItemVars(channel, column, item);
		
		// 找到对应的模板。
		PageTemplate template = getItemPageTemplate(channel, item);
		if (template == null) return "没有找到项目的模板";
		String template_content = template.getContent();
	
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	// === 
	
	/**
	 * 生成指定频道,指定栏目,指定项目的评论页面.
	 */
	public String generateItemCommentsPage(Channel channel, Column column, Item item, String formId) {
		// 准备变量.
		Map<String, Object> vars = getItemVars(channel, column, item);
		vars.put("formId", formId);
		
		// 找到对应的模板。
		PageTemplate template = getChannelOtherPageTemplate(channel, PageTemplate.CHANNEL_COMMENTS);
		if (template == null) return "没有找到评论页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}
	
	// === 生成自定义页面 =========================================================
	
	/**
	 * 生成自定义页面内容。
	 */
	public String generateWebPage(Channel channel, WebPage webpage) {
		// 准备变量.
		Map<String, Object> vars = getSiteVars(null);
		if (channel != null)
			vars.put("channel", channel);
		vars.put("webpage", webpage);
		vars.put("this", webpage);
		
		// 找到对应的模板。
		PageTemplate template = getWebPageTemplate(webpage);
		if (template == null) return "没有找到自定义页面的模板";
		
		// 编译输出。
		String template_content = template.getContent();
		return executeTemplate(template_content, vars);
	}
	
	// === 生成投票页面 ===========================================================
	
	/**
	 * 生成投票页面。
	 */
	public String generateVotePage(VoteWrapper vote) {
		Map<String, Object> vars = getSiteVars(null);
		vars.put("vote", vote);
		vars.put("this", vote);
		
		// 找到对应的模板。
		PageTemplate template = getSiteOtherPageTemplate(PageTemplate.VOTE);
		if (template == null) return "没有找到投票页面的模板";
		
		// 编译输出。
		String template_content = template.getContent();
		return executeTemplate(template_content, vars);
	}
	
	// === 支持函数 ==============================================================

	/**
	 * 获得标准站点变量集合。
	 */
	private Map<String, Object> getSiteVars(Map<String, Object> vars) {
		if (vars == null)
			vars = new java.util.HashMap<String, Object>();
		vars.put("site", site);
		vars.put("user", user);
		// add other standard variable
		
		if (vars.containsKey("this") == false)
			vars.put("this", site);
		return vars;
	}
	
	/**
	 * 获得标准频道对象变量集合。
	 * @param channel
	 * @return
	 */
	private Map<String, Object> getChannelVars(Channel channel) {
		Map<String, Object> vars = getSiteVars(null);
		vars.put("channel", channel);
		vars.put("this", channel);
		return vars;
	}
	
	/**
	 * 获得标准栏目对象变量集合。
	 * @param channel
	 * @param column
	 * @return
	 */
	private Map<String, Object> getColumnVars(Channel channel, Column column) {
		Map<String, Object> vars = getChannelVars(channel);
		vars.put("column", column);
		vars.put("this", column);
		return vars;
	}
	
	/**
	 * 获得标准频道专题对象变量集合。
	 * @param channel
	 * @param special
	 * @return
	 */
	private Map<String, Object> getChannelSpecialVars(Channel channel, SpecialWrapper special) {
		Map<String, Object> vars = getChannelVars(channel);
		vars.put("special", special);
		vars.put("this", special);
		return vars;
	}
	
	/**
	 * 获得项目页面对象变量集合。
	 * @param channel
	 * @param column
	 * @param item
	 * @return
	 */
	private Map<String, Object> getItemVars(Channel channel, Column column, Item item) {
		Map<String, Object> vars = getColumnVars(channel, column);
		vars.put("this", item);
		String[] names = item.getRepeatItemNames();	// 一般返回： 'article', 'item', 'object'
		for (int i = 0; i < names.length; ++i)
			vars.put(names[i], item);
		return vars;
	}
	
	/**
	 * 通过访问 uri 地址获得对应的频道对象。
	 * @param uri - 访问地址，如 '/PubWeb/news/index.jsp', '/PubWeb/soft', 
	 *   '/PubWeb/photo/col1/col2/index.jsp'
	 * @return 返回对应的频道对象，如果不存在则返回 null. 
	 */
	public Channel getChannelByUri(String uri) {
		if (uri == null || uri.length() == 0) return null;
		// 前面必须是 '/PubWeb/' - site.installDir
		if (uri.startsWith(site.getInstallDir()) == false)
			return null;
		// -- 变成 'news/index.jsp'
		uri = uri.substring(site.getInstallDir().length());
		if (uri.length() == 0) return null;
		
		int slash_pos = uri.indexOf('/');
		if (slash_pos >= 0)
			uri = uri.substring(0, slash_pos);
		if (uri.length() == 0) return null;
		
		// uri -- channeName 通过名字找到此频道。
		Channel channel = site.getChannels().getChannelByDir(uri);
		return channel;
	}

	/** 得到某个对象根据页面参数以及对象本身模板设置，来获得模板。 */
	private PageTemplate getCommonPageTemplate(PageAttrObject page_obj) {
		TemplateThemeCollection theme_coll = this.site.getTemplateThemeCollection();
		PageTemplate template = null;
		
		// 如果指定了模板标识，则使用指定的模板标识的模板。
		if (this.templateId != 0) {
			template = theme_coll.getPageTemplateMayCache(templateId);
			if (template != null) return template;
		}
		
		// 如果对象指定了模板标识，则使用该对象指定的模板标识。
		if (page_obj.getTemplateId() != 0) {
			template = theme_coll.getPageTemplateMayCache(page_obj.getTemplateId());
			if (template != null) return template;
		}
		
		return null;
	}
	
	/** 得到指定类型的网站模板. */
	private PageTemplate getSitePageTemplate(Site site) {
		PageTemplate template = getCommonPageTemplate(site);
		if (template != null) return template;
		
		// 使用缺省的.
		TemplateTheme curr_theme = site.getDefaultTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getSitePageTemplate(PageTemplate.HOME_PAGE);
		return template;
	}
	
	/** 得到网站组指定名字的模板。 */
	private PageTemplate getSiteOtherPageTemplate(String type_name) {
		TemplateThemeCollection theme_coll = this.site.getTemplateThemeCollection();
		PageTemplate template = null;
		
		// 如果指定了模板标识，则使用指定的模板标识的模板。
		if (this.templateId != 0) {
			template = theme_coll.getPageTemplateMayCache(templateId);
			if (template != null) return template;
		}

		// 得到该分组的缺省模板。
		TemplateTheme curr_theme = site.getDefaultTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getSitePageTemplate(type_name);
		return template;
	}
	
	/**
	 * 得到指定自定义页面的模板。
	 * @param webpage
	 * @return
	 */
	private PageTemplate getWebPageTemplate(WebPage webpage) {
		PageTemplate template = getCommonPageTemplate(webpage);
		if (template != null) return template;
		
		// 使用缺省的.
		TemplateTheme curr_theme = site.getDefaultTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getSitePageTemplate(PageTemplate.WEB_PAGE);
		return template;
	}
	
	/** 得到指定频道的频道主页模板。 */
	private PageTemplate getChannelIndexPageTemplate(Channel channel) {
		PageTemplate template = getCommonPageTemplate(channel);
		if (template != null) return template;
		
		// 没有给出参数, 用户也没有指定 channel 使用特定模板标识, 则使用缺省的.
		TemplateTheme curr_theme = site.getDefaultTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getChannelPageTemplate(channel, PageTemplate.CHANNEL_INDEX_PAGE);
		return template;
	}

	/** 得到指定频道指定类型模板。 */
	private PageTemplate getChannelOtherPageTemplate(Channel channel, String template_type) {
		TemplateThemeCollection theme_coll = this.site.getTemplateThemeCollection();
		PageTemplate template = null;
		
		// 如果页面参数中指定了模板标识，则使用指定的模板标识的模板。
		if (this.templateId != 0) {
			template = theme_coll.getPageTemplateMayCache(templateId);
			if (template != null) return template;
		}

		// 没有给出参数, 则使用缺省的.
		TemplateTheme curr_theme = theme_coll.getDefaultTemplateTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getChannelPageTemplate(channel, template_type);
		return template;
	}
	
	/** 得到指定栏目的栏目模板。 */
	private PageTemplate getColumnPageTemplate(Channel channel, Column column) {
		PageTemplate template = getCommonPageTemplate(column);
		if (template != null) return template;

		// 使用缺省的。
		TemplateTheme curr_theme = site.getDefaultTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getChannelPageTemplate(channel, PageTemplate.CHANNEL_COLUMN_PAGE);
		return template;
	}
	
	/** 得到指定专题的页面模板。 */
	private PageTemplate getSpecialPageTemplate(Channel channel, SpecialWrapper special) {
		PageTemplate template = getCommonPageTemplate(special);
		if (template != null) return template;
		
		// 使用缺省的。
		TemplateTheme curr_theme = site.getDefaultTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getChannelPageTemplate(channel, PageTemplate.CHANNEL_SPECIAL_PAGE);
		return template;
	}
	
	/** 得到指定文章、图片、软件的页面模板，其 type_name 都是 'content'。 */
	private PageTemplate getItemPageTemplate(Channel channel, Item item) {
		PageTemplate template = getCommonPageTemplate(item);
		if (template != null) return template;
		
		// 使用其所在栏目设置的项目模板。
		template = getColumnItemTemplate(item);
		if (template != null) return template;
		
		// 使用缺省的。
		TemplateTheme curr_theme = site.getDefaultTheme();
		if (curr_theme == null) return null;
		template = curr_theme.getChannelPageTemplate(channel, PageTemplate.CHANNEL_CONTENT_PAGE);
		return template;
	}

	/** 生成频道指定类型的页面, 如 showElite.jsp, showHots.jsp 。 */
	private String generateChannelTypedPage(Channel channel, String page_type_name) {
		// 创建频道主页的数据。
		Map<String, Object> vars = getChannelVars(channel);
		
		// 找到对应的模板。
		PageTemplate template = getChannelOtherPageTemplate(channel, page_type_name);
		if (template == null) return "没有找到此页面的模板";
		String template_content = template.getContent();
		
		// 编译输出。
		return executeTemplate(template_content, vars);
	}

	/**
	 * 生成模板页，并加上我们的版权声明。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String executeTemplate(String template_content, Map vars) {
		String page_content = PublishUtil.showTemplatePage(pub_ctxt, template_content, vars);
		if (page_content == null || page_content.length() == 0) 
			return "<!-- Powered by www.chinaedustar.com JPublish 1.0 -->";
		if (page_content.endsWith("\r\n"))
			return page_content + "<!-- Powered by www.chinaedustar.com JPublish 1.0 -->";
		else
			return page_content + "\r\n<!-- Powered by www.chinaedustar.com JPublish 1.0 -->";
	}

	// 查找指定项目所在栏目指定的项目模板。
	private PageTemplate getColumnItemTemplate(Item item) {
		TemplateThemeCollection theme_coll = this.site.getTemplateThemeCollection();
		PageTemplate template = null;
		
		Column column = item.getColumn();
		while (column != null) {
			// 使用该栏目设置的项目模板。
			int tid = column.getDefaultItemTemplate();
			if (tid != 0) {
				template = theme_coll.getPageTemplateMayCache(tid);
				if (template != null) return template;
			}
			// 未设置或未找到则继续向上查找父栏目。
			column = column.getParentColumn();
		}
		return null;
	}
}
