package com.chinaedustar.publish.admin;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.itfc.ChannelContainer;
import com.chinaedustar.publish.model.*;

/**
 * 提供管理页面的抽象基类。
 * 
 * @author liujunxing
 *
 * @more
 * 用于管理的类的体系：
 *  AbstractAdminData - 管理类基类，提供 admin 对象。
 *    AdminData - 产生管理使用的数据。
 *      AdminManagerData - 管理员管理的数据提供者类。
 *      AbstractArticleAdminData - 文章管理数据的基类。
 *        AdminArticleAdd - admin_article_add.jsp 的数据提供类。
 *        
 * @more 
 *   其实 Manage, Action 有类似的地方，我们怎么样能使得两者更接近一些，以能够简化处理？
 */
@SuppressWarnings("rawtypes")
abstract class AbstractBaseManage {
	/** 页面环境对象。 */
	protected final PageContext page_ctxt;
	
	/** 参数获取器。 */
	protected final ParamUtil param_util;
	
	/** 发布系统环境对象。 */
	protected final PublishContext pub_ctxt;
	
	/** 数据访问对象。 */
	protected final DataAccessObject dao;
	
	/** 事务处理对象。 */
	protected final TransactionProxy tx_proxy;
	
	/** 当前网站对象。 */
	protected final Site site;
	
	/** 当前管理员对象。 */
	protected Admin admin;
	
	/** 当前页面请求参数映射。 */
	protected final java.util.HashMap<String, Object> request_param;
	
	/**
	 * 构造一个 AdminData 的缺省实例。
	 *
	 */
	AbstractBaseManage(PageContext pageContext)   {
		this.page_ctxt = pageContext;
		this.param_util = new ParamUtil(pageContext);
		this.pub_ctxt = param_util.getPublishContext();
		this.dao = pub_ctxt.getDao();
		this.tx_proxy = pub_ctxt.getTransactionProxy();
		this.site = pub_ctxt.getSite();
		this.request_param = builtRequestParam();
		// 公共对象 admin, 放在系统中为 "admin".
		this.admin = PublishUtil.getCurrentAdmin(pageContext.getSession());
		// FOR DEBUG
		if (admin == null) {
			admin = new Admin();
			admin.setAdminName("未登录测试用");
			admin.setAdminType(Admin.ADMIN_TYPE_SUPER);
		}

		// 这些常用变量放到 pageContext 里面.
		setTemplateVariable("site", pub_ctxt.getSite());
		setTemplateVariable("request", request_param);
		setTemplateVariable("admin", admin);
	}
	
	/**
	 * 判断管理员是否登录了。
	 * @return
	 */
	public boolean isAdminLogined() {
		if (admin == null) return false;
		if (admin.getId() == 0) return false;
		return true;
	}
	

	/**
	 * 根据 pageContext 建立一个 request 对象。
	 * @return
	 */
	private java.util.HashMap<String, Object> builtRequestParam() {
		java.util.HashMap<String, Object> req = new java.util.HashMap<String, Object>();
		ServletRequest serv_req = page_ctxt.getRequest();
		java.util.Enumeration enumer = serv_req.getParameterNames();
		if (enumer != null) {
			while (enumer.hasMoreElements()) {
				String key = enumer.nextElement().toString();
				req.put(key, serv_req.getParameter(key));
			}
		}
		return req;
	}

	// === 辅助函数 =========================================================
	
	/**
	 * 根据页面参数 page, pageSize 创建对应的 PaginationInfo 对象。
	 * @return 返回设置了 currPage, pageSize 的分页对象。
	 */
	protected PaginationInfo getPaginationInfo() {
		PaginationInfo page_info = new PaginationInfo();
		int page = safeGetIntParam("page", 1);
		if (page < 1) page = 1;
		page_info.setCurrPage(page);
		
		int page_size = safeGetIntParam("pageSize", 20);
		if (page_size < 1) page_size = 1;
		page_info.setPageSize(page_size);
		return page_info;
	}
	
	/**
	 * 返回一个 Channel 不存在的 PublishException.
	 * @return new PublishException("执行标识 xxx 的频道不存在")
	 */
	protected PublishException exChannelUnexist() {
		String ex_msg = "指定标识 '" + param_util.getRequestParam("channelId") + "' 的频道不存在，请确定您是从有效的链接进入的。";
		return new PublishException(ex_msg);
	}
	
	public final String getAccessDeniedDesc() {
		return "您对当前页面的访问因为权限不足被拒绝。";
	}
	
	/**
	 * 抛出一个异常提示用户访问被拒绝。
	 *
	 */
	protected PublishException accessDenied() {
		return accessDenied(getAccessDeniedDesc(), true, "enter_manage_page");
	}
	
	/**
	 * accessDenied() 的高级版本，提供记录日志和提示信息的选择。
	 * @param write_log
	 * @param desc
	 * @return
	 */
	protected PublishException accessDenied(String desc, boolean write_log, String operation) {
		PublishException ex = new PublishException(desc);
		if (write_log) {
			this.writeLog(operation, desc, Log.STATUS_ACCESS_DENIED);
		}
		return ex;
	}
	
	/**
	 * 创建并写入一个日志。
	 * @param operation
	 * @param desc
	 * @param status
	 * @return 返回写入的日志对象。
	 */
	protected Log writeLog(String operation, String desc, int status) {
		Log log = new Log();
		log.setUserName(admin == null ? "(未登录)" : admin.getAdminName());
		log.setOperation(operation);
		log.setStatus(status);
		log.setIPUrlPostData(page_ctxt);
		log.setDescription(desc);
		pub_ctxt.log(log);
		return log;
	}
	
	/**
	 * 向 response 中写入 pragma: no-cache 以阻止页面缓存。
	 *
	 */
	protected void pragmaNocache() {
		HttpServletResponse response = (HttpServletResponse)page_ctxt.getResponse();
		response.addHeader("Pragma", "no-cache");
	}
	
	/**
	 * 得到Cookie
	 * @param key Cookie的名称
	 * @param defvar 默认值
	 * @return
	 */
	protected String safeGetCookie(String key, String defvar) {
		return param_util.safeGetCookie(key, defvar);
	}
	
	/**
	 * 安全的获取请求值，如果没有或非法则返回缺省值。
	 * @param key
	 * @param defval
	 * @return
	 */
	protected int safeGetIntParam(String key, int defval) {
		return param_util.safeGetIntParam(key, defval);
	}

	/**
	 * 安全的获取请求值，如果没有或非法则返回缺省值。
	 * @param key
	 * @param defval
	 * @return
	 */
	protected Integer safeGetIntParam2(String key, Integer defval) {
		return param_util.safeGetIntParam(key, defval);
	}

	/**
	 * 获得指定键的字符串参数，如果没有则使用缺省值 defval。
	 * @param key
	 * @return
	 */
	protected String safeGetStringParam(String key, String defval) {
		return param_util.safeGetStringParam(key, defval);
	}

	/**
	 * 安全的获取boolean型的请求值，如果没有或者非法返回给定的默认值省。
	 * @param key 
	 * @param defval
	 * @return
	 */
	protected Boolean safeGetBooleanParam(String key, Boolean defval) {
		return param_util.safeGetBooleanParam(key, defval);
	}

	/**
	 * 得到URL中中文的参数值
	 * @param key 参数名称
	 * @param defvar 默认值
	 * @return
	 */
	protected String safeGetChineseParameter(String key, String defvar) {
		return param_util.safeGetChineseParameter(key, defvar);
	}

	// === Column 数据 ================================================================
	
	/**
	 * 获得指定频道下，为支持显示 &lt;select&gt; 下拉列表使用的数据。
	 * @return 返回一个 DataTable，其 schema 为 [id, name, parentId, parentPath, enableAdd]
	 */
	protected DataTable getColumnsDropDownData(Channel channel) {
		DataTable data = channel.getColumnTree()
				.getAdvColumnListDataTable(ColumnTree.COLUMN_DROPDOWN_FIELDS);
		// 不在需要：TreeUtil.addSelectPrefix(data, "&nbsp;&nbsp;");
		return data;
	}
	
	// === Template/Skin 数据 =========================================================
	
	/**
	 * 获得当前缺省方案下指定频道的指定模板类别的所有可用模板列表。返回较少的数据，主要用于支持
	 *  页面上面选择可用模板时候的下拉列表框。
	 * <p>
	 * 使用例子：
	 *  获得频道 channel 中 '内容页(content)' 所有可用模板。 
	 *  <code> getAvailableTemplateDataTable(channel, "content"); </code>
	 * </p>
	 * @param themeId - 模板方案标识，如果 = 0 表示使用缺省模板方案。
	 * @param chanenlId - 频道标识，如果 = 0 表示网站通用模板。
	 * @param type_name - 模板类别名字，对应 TemplateType.name 属性。
	 * 
	 * @return 返回一个 DataTable, 其 schema 为 [id, name, isDefault].
	 * @see TemplateThemeCollection.getAvailableTemplateDataTable()
	 */
	protected DataTable getAvailableTemplateDataTable(Channel channel, String type_name) {
		TemplateThemeCollection theme_coll = site.getTemplateThemeCollection();
		int channelId = (channel == null) ? 0 : channel.getId();
		return theme_coll.getAvailableTemplateDataTable(0, channelId, type_name);
	}
	
	/**
	 * 获得当前方案下所有可用皮肤列表。
	 * @return 返回一个 DataTable, 其 schema 为 [id, name, isDefault].
	 * @see TemplateThemeCollection.getAvailableSkinDataTable(int)
	 */
	protected DataTable getAvailableSkinDataTable() {
		return pub_ctxt.getSite().getTemplateThemeCollection().getAvailableSkinDataTable(0);
	}
	
	// === Author 数据 ================================================================
	
	/**
	 * 得到最近使用过的作者集合。
	 * @param channel - 频道对象，可以为 null。
	 * @return 返回一个 List&lt;String&gt;, 其中每一项为一个作者名。
	 */
	protected List<String> getAuthorsLatestUsed(Channel channel) {
		List<String> list = new java.util.ArrayList<String>();
		list.add("未知");
		list.add("佚名");
		list.add(admin.getAdminName());
		String cookieKey = "LUA_" + (channel == null ? 0 : channel.getId());
		String latestAuthor = safeGetCookie(cookieKey, null);
		if (latestAuthor != null && "".equals(latestAuthor) == false && !list.contains(latestAuthor)) {
			list.add(latestAuthor);
		}

		return list;
	}
	
	// === Keyword 数据 ===============================================================
	
	/**
	 * 得到最近使用过的关键字集合。
	 * @param channel - 频道对象。
	 * @param count - 获取的数量，如果 = 0 则使用缺省值 4。
	 * @return List&lt;String&gt;, 其中每一项是一个关键字。
	 * @see KeywordCollection.getLatestUsedKeywords(int)
	 */
	protected List<String> getKeywordsLatestUsed(Channel channel, int count) {
		if (count <= 0) count = 4;
		KeywordCollection keyword_coll;
		if (channel == null) 
			keyword_coll = pub_ctxt.getSite().getKeywordCollection();
		else
			keyword_coll = channel.getKeywordCollection();

		return keyword_coll.getLatestUsedKeywords(count);
	}
	
	// === Source 数据 ================================================================
	
	/**
	 * 得到最近使用过的来源集合
	 * @param channel - 频道对象，可以为 null。
	 * @return 返回一个 List&lt;String&gt;, 其中每一项为一个来源。
	 */
	protected List<String> getSourcesLatestUsed(Channel channel) {
		List<String> list = new ArrayList<String>();
		list.add("本站原创");
		
		String cookieKey = "LUS_" + (channel == null ? 0 : channel.getId());
		String latestSource = safeGetCookie(cookieKey, null);
		if (latestSource != null && "".equals(latestSource) == false && !list.contains(latestSource))
			list.add(latestSource);
		
		return list;
	}
	
	// === 旧的 ===
	
	/**
	 * 根据页面 channelId 参数，获取 site 对象或 channel 对象。
	 * @return 如果 channelId = 0 则返回 site 对象，否则返回 channel 对象。
	 *   如果 channel 不存在则返回 null.
	 */
	protected ChannelContainer getChannelOrSite() {
		int channelId = safeGetIntParam("channelId", 0);
		Site site = pub_ctxt.getSite();
		if (channelId == 0) return site;
		return site.getChannels().getChannel(channelId);
	}
	
	/**
	 * 根据页面 channelId 参数，获得其所属频道对象。
	 * @return 所属频道对象，如果没有该频道则返回 null.
	 */
	protected Channel getChannelData() {
		int channelId = safeGetIntParam("channelId", 0);
		request_param.put("channelId", channelId);
		return pub_ctxt.getSite().getChannel(channelId);
	}
	
	/**
	 * 获得频道列表数据。
	 * @return 频道列表(内存集合的复制品)。 
	 */
	protected java.util.List<Channel> getChannelListData() {
		return pub_ctxt.getSite().getChannels().getChannelList();
	}
	
	/**
	 * 获得可管理的频道列表。可管理的频道指具有后台管理项，非外部频道，状态正常。
	 * @return List&lt;Channel&gt; 频道的集合。
	 */
	protected java.util.List<Channel> getManagedChannelList() {
		java.util.List<Channel> list = getChannelListData();
		for (int i = list.size()-1; i >= 0; --i) {
			Channel channel = list.get(i);
			if (channel.getIsManagedChannel() == false)
				list.remove(i);
		}
		return list;
	}
	
	public static final String[] EMPTY_STRING_ARRAY = new String[] {};
	
	/**
	 * 获得指定标识的 Item 对象的类型 (itemClass)
	 * @param itemId
	 * @return
	 */
	protected String getItemClass(int itemId) {
		String hql = "SELECT itemClass FROM Item WHERE id = " + itemId;
		
		java.util.List result = pub_ctxt.getDao().queryByNamedParam(hql, EMPTY_STRING_ARRAY, EMPTY_STRING_ARRAY);
		if (result == null) return null;
		if (result.size() <= 0) return null;
		return result.get(0).toString();
	}
	
	/**
	 * 获得指定标识的 Item 对象，根据类型不同可能有 Article, Soft, Photo 数种。
	 * @param itemId
	 * @return
	 */
	protected Item getItem(int itemId) {
		String itemClass = getItemClass(itemId);
		if (itemClass == null) return null;			// 未知或标识不正确
		if (itemClass.equalsIgnoreCase("news"))
			return (Article)pub_ctxt.getDao().get(Article.class, itemId);
		else if (itemClass.equalsIgnoreCase("soft"))
			return (Soft)pub_ctxt.getDao().get(Soft.class, itemId);
		else if (itemClass.equalsIgnoreCase("photo"))
			return (Photo)pub_ctxt.getDao().get(Photo.class, itemId);
		else
			return (Item)pub_ctxt.getDao().get(Item.class, itemId);
	}

	/**
	 * 设置模板使用的变量，当前设置在 pageContext，也许以后调整为 request.setAttribute()
	 * @param name
	 * @param value
	 */
	protected void setTemplateVariable(String name, Object value) {
		page_ctxt.setAttribute(name, value);
	}
}
