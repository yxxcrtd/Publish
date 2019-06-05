package com.chinaedustar.publish.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.ParamUtil;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.engine.PageGenerator;
import com.chinaedustar.publish.model.Announcement;
import com.chinaedustar.publish.model.Article;
import com.chinaedustar.publish.model.Author;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.ChannelSearch;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.Item;
import com.chinaedustar.publish.model.PageTemplate;
import com.chinaedustar.publish.model.PaginationInfo;
import com.chinaedustar.publish.model.Photo;
import com.chinaedustar.publish.model.Site;
import com.chinaedustar.publish.model.SiteSearch;
import com.chinaedustar.publish.model.Soft;
import com.chinaedustar.publish.model.SpecialWrapper;
import com.chinaedustar.publish.model.ThreadCurrentMap;
import com.chinaedustar.publish.model.User;
import com.chinaedustar.publish.model.VoteWrapper;
import com.chinaedustar.publish.model.WebPage;

/**
 * 网页初始化对象，用于支持 index.jsp, channel/index.jsp 等页面
 * 
 * @author liujunxing
 */
public class PageHandler {
	
	/** 页面环境对象 */
	protected final PageContext page_ctxt;
	
	/** 参数获取器 */
	protected final ParamUtil param_util;
	
	/** 发布系统环境对象 */
	protected final PublishContext pub_ctxt;
	
	/** 页面生成对象 */
	protected final PageGenerator page_gen;
	
	/** 当前网站对象 */
	protected Site site;
	
	/**
	 * 构造函数
	 * 
	 * @param pageContext
	 */
	public PageHandler(PageContext pageContext) {
		this.page_ctxt = pageContext;
		this.param_util = new ParamUtil(pageContext);
		this.pub_ctxt = param_util.getPublishContext();
		this.page_gen = new PageGenerator(pub_ctxt);
		this.site = pub_ctxt.getSite();
		ThreadCurrentMap.current();			// 强迫产生一个 Map, 用于页面缓存。
		
		this.setPageGenOptions(page_gen);
	}
	
	// === 主页 =================================================================
	
	/**
	 * 初始化网站主页 index.jsp 页面所需数据。
	 *
	 */
	public String createIndexJsp() {
		// 产生主页。
		return page_gen.generateIndexPage();
	}
	
	/**
	 * 显示网站用户登录页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showMemberLoginPage() {
		return page_gen.generateMemberLoginPage();
	}
	
	/**
	 * 显示网站公告页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showAnnouncePage() {
		int announceId = param_util.safeGetIntParam("announceId");
		Announcement announce = site.getAnnouncementCollection().getAnnouncement(announceId);

		return page_gen.generateAnnouncePage(announce);
	}
	
	/**
	 * 显示搜索页.
	 * @return
	 */
	public String showSearchPage() {
		// 构造页面所需查询对象。
		SiteSearch search = new SiteSearch();
		search.setModuleId(param_util.safeGetIntParam("moduleId"));
		search.setKeyword(param_util.safeGetChineseParameter("keyword", null));
		
		// 构造页面所需分页对象。
		PaginationInfo page_info = new PaginationInfo();
		int page = param_util.safeGetIntParam("page", 1);
		if (page <= 0) page = 1;
		page_info.setCurrPage(page);
		page_info.setPageSize(site.getSearchPageSize());	// 从 site 里面获取分页大小，缺省= 20。
		String url_pattern = site.resolveUrl("search.jsp");
		url_pattern += "?page={page}&moduleId=" + search.getModuleId();
		page_info.setUrlPattern(url_pattern);
		page_info.appendParam2Url(true, "keyword", search.getKeyword(), "GB2312");
		
		return page_gen.generateSearchPage(search, page_info);
	}
	
	/**
	 * 显示友情链接页面。
	 * @return
	 */
	public String showFriendSitePage() {
		// 处理页面参数。
		int linkType = param_util.safeGetIntParam("linkType", 0);
		int kindId = param_util.safeGetIntParam("kindId", 0);
		int specialId = param_util.safeGetIntParam("specialId", 0);
		int kindType = param_util.safeGetIntParam("kindType", 0);

		return page_gen.generateFriendSitePage(linkType, kindId, specialId,	kindType);
	}
	
	/**
	 * 显示版权声明页.
	 * @return
	 */
	public String showCopyRightPage() {
		return page_gen.generateHomeGeneralPage(PageTemplate.COPY_RIGHT, null);
	}
	
	/**
	 * 显示作者介绍页面.
	 * @return
	 */
	public String showAuthorPage() {
		int authorId = param_util.safeGetIntParam("authorId");
		Author author = site.getAuthorCollection().getAuthor(authorId);
		java.util.Map<String, Object> vars = new java.util.HashMap<String, Object>();
		vars.put("author", author);

		return page_gen.generateHomeGeneralPage("author_content", vars);
	}
	
	/**
	 * 显示通用的主页级别的指定模板类别的页面, 附加变量放在 vars 里面。
	 */
	public String showHomeGeneralPage(String template_type_name, java.util.Map<String, Object> vars) {
		return page_gen.generateHomeGeneralPage(template_type_name, null);
	}
	
	// === 频道 =================================================================
	
	/**
	 * 创建栏目主页 index.jsp
	 * @return
	 * @throws java.io.IOException
	 */
	public String createChannelIndexJsp() {
		// 创建栏目主页。
		// 1. 根据请求 uri 找到栏目, url 格式 '/PubWeb/news/index.jsp'。
		//   如果频道做子站点，则可能 uri 为 '/index.jsp', 这时候该怎么找？
		//Channel channel = getChannelFromUri();
		//if (channel == null)
		//	return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		//return page_gen.generateChannelIndexPage(channel);
		return createChannelIndexJspPage();
	}
	
	public String createChannelIndexJspPage() {
		// 创建栏目主页。
		// 1. 根据请求 uri 找到栏目, url 格式 '/PubWeb/news/index.jsp'。
		//   如果频道做子站点，则可能 uri 为 '/index.jsp', 这时候该怎么找？
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		// 得到栏目特有的其它参数 page - 页次
		int page = param_util.safeGetIntParam("page", 1);
		if (page <= 0) page = 1;
		return page_gen.generateChannelIndexPage(channel, page);
	}
	/**
	 * 创建 showColumn.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String createShowColumnJsp() {
		// 简单检测。
		int columnId = param_util.safeGetIntParam("columnId");
		if (columnId == 0)
			return "没有给出栏目参数，请确定您访问的地址是有效的。";
		
		// 得到当前频道。
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		
		// 得到当前栏目。
		Column column = channel.getColumnTree().getColumnWithParent(columnId);
		if (column == null)
			return "您所访问的栏目不存在，可能参数错误或者该栏目已经过时被删除了。";
		if (column.getChannelId() != channel.getId())
			return "您所访问的栏目不属于所在栏目，请确定您访问的地址合法。";
		
		// 得到栏目特有的其它参数 page - 页次
		int page = param_util.safeGetIntParam("page", 1);
		if (page <= 0) page = 1;
		return page_gen.generateColumnPage(channel, column, page);
	}

	/**
	 * 显示频道 showSpecial.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showChannelSpecialPage() {
		// 简单检测。
		int specialId = param_util.safeGetIntParam("specialId");
		if (specialId == 0)
			return "没有给出专题参数，请确定您访问的地址是有效的。";
		
		// 得到当前频道。
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		
		// 得到专题对象。
		SpecialWrapper special = channel.getSpecialCollection().getSpecial(specialId);
		if (special == null /* || special.getIsDeleted()*/ )
			return "您所访问的专题不存在，可能参数错误或者该专题已经过时被删除了。";
		if (special.getChannelId() != channel.getId())
			return "您所访问的专题不属于此频道，请确定您访问的地址有效。";

		return page_gen.generateChannelSpecialPage(channel, special);
	}
	
	/**
	 * 创建 showElite.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showElitePage() {
		// 1. 根据请求 uri 找到栏目
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";

		return page_gen.generateChannelElitePage(channel);
	}
	
	/**
	 * 显示 showCommend.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showCommendPage() {
		// 1. 根据请求 uri 找到栏目
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";

		return page_gen.generateChannelCommendPage(channel);
	}
	
	/**
	 * 创建 showHot.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showHotPage() {
		// 1. 根据请求 uri 找到栏目
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";

		return page_gen.generateChannelHotPage(channel);
	}
	
	/**
	 * 创建 showNew.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showNewPage() {
		// 1. 根据请求 uri 找到栏目
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";

		return page_gen.generateChannelNewPage(channel);
	}
	
	/**
	 * 显示频道的专题列表页面。
	 * @return
	 */
	public String showChannelSpecialListPage() {
		// 1. 根据请求 uri 找到频道.
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		
		return page_gen.generateChannelSpecialListPage(channel);
	}
	
	/**
	 * 显示频道搜索页面.
	 * @return
	 */
	public String showChannelSearchPage() {
		// 1. 根据请求 uri 找到频道.
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";

		// 构造页面所需查询对象。
		// 页面参数有:
		// showAdSearch
		// keyword
		// columnId
		// field - title, content, author, inputer, keywords
		ChannelSearch search = ChannelSearch.getInstance(page_ctxt);
		PaginationInfo page_info = null;
		if (search != null) {
			// 设置搜索标题。
			search.setSearchResultTitle("搜索结果");
			
			// TODO: 根据频道项目类型选择不同方式初始化，也许以后应该消除这种代码：
			String itemClass = channel.getChannelModule().getItemClass();
			if ("article".equalsIgnoreCase(itemClass))
				search.init(1);	// 1 - article
			else if ("photo".equalsIgnoreCase(itemClass))
				search.init(2); // 2 - photo
			else if ("soft".equalsIgnoreCase(itemClass))
				search.init(3);	// 3 - soft
			else
				search.init(0);	// common
			
			// 构造页面所需分页对象。
			page_info = new PaginationInfo();
			int page = param_util.safeGetIntParam("page", 1);
			if (page <= 0) page = 1;
			page_info.setCurrPage(page);
			page_info.setPageSize(20);			// TODO: 从 channel 里面获取。
			//String url_pattern = channel.resolveUrl("search.jsp");
			//url_pattern += "?page={page}";
			//page_info.setUrlPattern(url_pattern);
			//page_info.appendParam2Url(true, "keyword", search.getKeyword(), "GB2312");
		}
		
		return page_gen.generateChannelSearchPage(channel, search, page_info);
	}
	
	// === 项目 =================================================================
	
	/**
	 * 创建 showArticle.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String createArticleJsp() {
		// 简单检测。
		int articleId = param_util.safeGetIntParam("articleId");
		if (articleId == 0)
			return "没有给出文章标识参数，请确定您访问的地址是有效的。";
		
		// 得到当前频道。
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		// 得到文章对象。
		Article article = channel.loadArticle(articleId);
		if (article == null)
			return "您所访问的文章不存在，可能参数错误或该文章已经被删除了。";
		
		return page_gen.generateArticlePage(channel, article);
	}
	
	/**
	 * 显示 showPhoto.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showPhotoJsp() {
		// 简单检测。
		int photoId = param_util.safeGetIntParam("photoId");
		if (photoId == 0)
			return "没有给出图片标识参数，请确定您访问的地址是有效的。";
		
		// 得到当前频道。
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		// 得到图片对象。
		Photo photo = channel.loadPhoto(photoId);
		if (photo == null)
			return "您所访问的图片不存在，可能参数错误或该文章已经被删除了。";
		
		return page_gen.generatePhotoPage(channel, photo);
	}

	/**
	 * 显示　showSoft.jsp 页面。
	 * @return
	 * @throws java.io.IOException
	 */
	public String showSoftJsp() {
		// 简单检测。
		int softId = param_util.safeGetIntParam("softId");
		if (softId == 0)
			return "没有给出软件标识参数，请确定您访问的地址是有效的。";
		
		// 得到当前频道。
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		// 得到图片对象。
		Soft soft = channel.loadSoft(softId);
		if (soft == null)
			return "您所访问的软件不存在，可能参数错误或该文章已经被删除了。";
		
		return page_gen.generateSoftPage(channel, soft);
	}
	
	// === 项目评论 ================================================================

	/**
	 * 显示文章频道文章评论页面.
	 * @return
	 */
	public String showCommentsPage() {
		// 1. 根据请求 uri 找到频道.
		Channel channel = getChannelFromUri();
		if (channel == null)
			return "无法找到所访问地址对应的频道，请确定您访问的地址合法。";
		
		// 找到文章
		int itemId = param_util.safeGetIntParam("itemId");
		Item item = channel.loadItem(itemId);
		if (item == null) return "未能找到指定标识的文章项目";
		
		// 找到栏目
		Column column = channel.getColumnTree().getColumnWithParent(item.getColumnId());
		if (column != null) item.setColumn(column);
		
		// 产生页面表单的验证码，以防止重复提交。
		String formId = PublishUtil.getFormValidId(page_ctxt.getSession());
		
		// 产生页面.
		return page_gen.generateItemCommentsPage(channel, column, item, formId);
	}
	
	
	// === 自定义页面 ==============================================================
	
	/**
	 * 显示自定义页面。
	 */
	public String showWebPage() {
		// 1. 根据请求 uri 找到频道.
		Channel channel = getChannelFromUri();
		
		// 2. 找到该自定义页面。
		int webpageId = param_util.safeGetIntParam("id");
		WebPage webpage = site.getWebPageCollection().getWebPage(webpageId);
		
		// 产生页面。
		return page_gen.generateWebPage(channel, webpage);
	}
	
	// === 投票页面 ================================================================
	
	/**
	 * 显示投票页面。
	 */
	@SuppressWarnings("unused")
	public String showVotePage() {
		// 得到参数。
		int voteId = param_util.safeGetIntParam("voteId");
		String command = param_util.safeGetStringParam("command");
		List<Integer> voteOption = param_util.safeGetIntValues("voteOption");
		
		VoteWrapper vote = site.getVoteCollection().getVote(voteId);
		if (vote == null) return "没有找到指定投票，请确定您是从有效链接进入的。";
		
		// 处理投票 action.
		if ("vote".equalsIgnoreCase(command)) {
			// pub_ctxt.getTransactionProxy().doVote(vote, voteOption);
		}
		
		// 产生页面。
		return page_gen.generateVotePage(vote);
	}
	
	// === 辅助函数 ================================================================
	
	/**
	 * 根据访问的频道地址找到对应的频道对象.
	 * @return
	 */
	private Channel getChannelFromUri() {
		// 1. 根据请求 uri 找到栏目, url 格式 '/PubWeb/news/index.jsp'。
		//   如果频道做子站点，则可能 uri 为 '/index.jsp', 这时候该怎么找？
		String uri = ((HttpServletRequest)page_ctxt.getRequest()).getRequestURI();
		return page_gen.getChannelByUri(uri);
	}
	
	// 设置额外参数。
	private void setPageGenOptions(PageGenerator page_gen) {
		// 当前登录用户，大部分页面使用。
		User user = PublishUtil.getCurrentUser(page_ctxt.getSession());
		page_gen.setUser(user);

		// 模板标识，测试用。
		int templateId = this.param_util.safeGetIntParam("templateId");
		page_gen.setTemplateId(templateId);
	}
}
