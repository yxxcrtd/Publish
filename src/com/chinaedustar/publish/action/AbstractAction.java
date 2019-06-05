package com.chinaedustar.publish.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.ParamUtil;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.TransactionProxy;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.ExtendProperty;
import com.chinaedustar.publish.model.ExtendPropertySet;
import com.chinaedustar.publish.model.ExtendPropertySupport;
import com.chinaedustar.publish.model.Log;
import com.chinaedustar.publish.model.Site;


/**
 * 操作页面统一的执行对象。
 * 所有的操作页面都需要指定一个执行对象来执行相应的操作。
 * @author wangyi
 *
 */
public abstract class AbstractAction {

	/** 消息的空模板。 */
	public static final String TEMPLATE_MESSAGE_BLANK = "template_message_blank";
	
	/** 消息的默认模板。 */
	public static final String TEMPLATE_MESSAGE_DEFAULT = "template_message_default";
	
	/** 消息的出现内部错误的模板。 */
	public static final String TEMPLATE_MESSAGE_WRONG = "template_message_wrong";
	
	/** 异常的空模板。 */
	public static final String TEMPLATE_EXCEPTION_BLANK = "template_exception_blank";
	
	/** 异常的默认模板。 */
	public static final String TEMPLATE_EXCEPTION_DEFAULT = "template_exception_blank";
	
	/** JSP页面环境对象。 */
	protected PageContext page_ctxt = null;
	
	/** 发布系统环境对象。 */
	protected PublishContext pub_ctxt = null;

	/** 主站点对象。 */
	protected Site site = null;
	
	/** 管理员对象，用于验证权限。 */
	protected Admin admin = null;

	/** 数据库操作对象。 */
	protected DataAccessObject dao = null;
	
	/** 事务包装对象。 */
	protected TransactionProxy tx_proxy = null;
	
	/** 获取参数的工具类。 */
	protected ParamUtil param_util = null;
	
	/** 信息的集合。 */
	protected final ArrayList<String> messages = new ArrayList<String>();

	/** 信息的链接地址集合。 */
	protected final ArrayList<ActionLink> links = new ArrayList<ActionLink>();

	/**
	 * 初始化页面执行对象。
	 * @param page_ctxt 页面环境对象。
	 */
	public void initialize(PageContext page_ctxt) {
		this.page_ctxt = page_ctxt;
		this.pub_ctxt = PublishUtil.getPublishContext(page_ctxt);
		this.param_util = new ParamUtil(page_ctxt);
		this.dao = pub_ctxt.getDao();
		this.site = pub_ctxt.getSite();
		this.admin = PublishUtil.getCurrentAdmin(page_ctxt.getSession());
		if (this.admin == null) {
			this.admin = new Admin();
			this.admin.setName("(未登录)");
			this.admin.setAdminType(Admin.ADMIN_TYPE_NONE);
		}
		this.tx_proxy = pub_ctxt.getTransactionProxy();
	}
	
	/**
	 * 业务的执行方法。
	 *
	 */
	public abstract void execute() throws Exception;
	
	/**
	 * 销毁。
	 *
	 */
	public void terminate() {
		this.param_util = null;
		this.pub_ctxt = null;
		this.page_ctxt = null;
	}
	
	/**
	 * 得到消息的模板类型，返回字符串，默认返回 TEMPLATE_MESSAGE_BLANK 。
	 * @return
	 */
	public String getMessageTemplateType() {
		return TEMPLATE_MESSAGE_BLANK;
	}
	
	/**
	 * 得到异常的模板类型，返回字符串，默认返回 TEMPLATE_EXCEPTION_BLANK 。
	 * @return
	 */
	public String getExceptionTemplateType() {
		return TEMPLATE_EXCEPTION_BLANK;
	}
	
	/**
	 * 返回消息的数组。
	 * @return
	 */
	public final List<String> getActionMessages() {
		return this.messages;
	}
	
	/**
	 * 返回执行链接对象。
	 * @return
	 */
	public final List<ActionLink> getActionLinks() {
		return this.links;
	}
	
	// === 提供给派生类用的常用业务方法 ===============================================
		
	/**
	 * 页面请求转发。
	 * @param url 转发的URL。
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void forward(String url) throws ServletException, IOException {
		page_ctxt.getRequest().getRequestDispatcher(url).forward(page_ctxt.getRequest(), page_ctxt.getResponse());
	}
	
	/**
	 * 页面跳转。
	 * @param url 要跳转到的URL。
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void redirect(String url) throws ServletException, IOException {
		((HttpServletResponse)page_ctxt.getResponse()).sendRedirect(url);
	}

	/** 提示用户没有提供必要的参数。 */
	protected void noIdsParam() {
		messages.add("没有给出选择要操作的对象参数，请确定您选择了要操作的对象。");
		links.add(getBackActionLink());
	}

	/** 提示用户未知的命令，可能是进入链接不正确。 */
	protected void unknownCommand(String command) {
		messages.add("未知页面命令: " + command + ", 请确定您是从有效的页面提交的操作。");
		links.add(this.getBackActionLink());
	}

	/** 获得提交的前一页面，可能没有。 */
	protected String getRefererUrl() {
		return ((HttpServletRequest)page_ctxt.getRequest()).getHeader("referer");
	}
	
	/** 
	 * 获得返回的 ActionLink 对象。
	 * AbstractAction 的实现使用从页面提交获得的 'referer' 来做为返回地址，
	 * 如果该地址为 null, 则使用 javascript:history.back() 做为缺省返回。派生类可以
	 *   自己使用其它方式，如一个具体链接做为返回。 
	 */
	protected ActionLink getBackActionLink() {
		try {
			String backUrl = getRefererUrl();
			if (backUrl != null && backUrl.length() > 0)
				return new ActionLink("[返回]", backUrl);
		} catch (Exception ex) {
			// ignore
		}
		return new ActionLink("[返回]", "javascript:window.history.back();");
	}
	
	/**
	 * 获得返回的 ActionLink 对象，使用 window.history.back() 方式实现的。
	 * @return
	 */
	protected ActionLink getHistoryBack() {
		return new ActionLink("[返回]", "javascript:window.history.back();");
	}
	
	/**
	 * 获得当前频道对象。
	 */
	protected Channel getChannel() {
		int channelId = param_util.safeGetIntParam("channelId");
		Channel channel = pub_ctxt.getSite().getChannel(channelId);
		return channel;
	}
	
	/**
	 * 获得当前频道对象。如果未找到则返回 null, 而且会添加未找到频道的信息。
	 */
	protected Channel getChannelNeed() {
		int channelId = param_util.safeGetIntParam("channelId");
		if (channelId != 0) {
			Channel channel = pub_ctxt.getSite().getChannel(channelId);
			if (channel != null) return channel;
		}
		
		messages.add(getStringChannelUnexist(channelId));
		links.add(getBackActionLink());
		return null;
	}

	/**
	 * 判断是否有管理员登录，并且当没有管理员登录的时候添加必须的信息。
	 * @return true 表示有管理员；false 表示没有管理员。
	 */
	protected boolean isHasAdmin() {
		if (this.admin == null || this.admin.getId() == 0) {
			messages.add("当前未登录，或登录超时，请登录之后进行操作。");
			links.add(new ActionLink("[登录]", site.resolveUrl("admin/admin_login.jsp")));
			links.add(new ActionLink("[返回主页]", site.getUrl()));
			
			this.createWriteLog("security", "验证登录", Log.STATUS_NOT_LOGIN); // createLog("security", Log.STATUS_ACCESS_DENIED);
			return false;
		}
		return true;
	}
	
	/**
	 * 创建一个一般日志记录项，其填写了用户名、IP、PostData、URL、时间等信息。
	 * @return
	 */
	protected Log createLog(String operation, int status) {
		Log log = new Log();
		log.setUserName(admin == null ? "(未登录)" : admin.getAdminName());
		log.setOperation(operation);
		log.setStatus(status);
		log.setIPUrlPostData(page_ctxt);
		return log;
	}
	
	/**
	 * 创建一个一般日志记录项，其填写了用户名、IP、PostData、URL、时间等信息。
	 *   并且根据 messages 填写了 description 部分。
	 * @return
	 */
	protected Log createWriteLog(String operation, String desc, int status) {
		Log log = createLog(operation, status);
		String description = desc;
		for (int i = 0; i < messages.size(); ++i)
			description += "\r\n" + messages.get(i);
		log.setDescription(description);
		pub_ctxt.log(log);

		return log;
	}

	// === 字符串函数 ==================================================================
	
	// 获得 channelId 频道未找到时候的提示信息。
	protected String getStringChannelUnexist(int channelId) {
		String str = "无法获得指定标识为 " + channelId + " 的频道，请确定您是从有效链接进入的。";
		return str;
	}

	// === 扩展属性支持 ================================================================
	
	/**
	 * 收集扩展属性。
	 */
	protected void collectExtendsProp(ExtendPropertySupport object) {
		// 得到原始数值。
		String[] prop_names = page_ctxt.getRequest().getParameterValues("_propName");
		String[] prop_values = page_ctxt.getRequest().getParameterValues("_propValue");
		if (prop_names == null || prop_names.length == 0) return;
		if (prop_values == null || prop_values.length == 0) return;
		
		// 为每个属性设置到集合中。
		ExtendPropertySet prop_set = object.getExtends();
		for (int i = 0; i < prop_names.length; ++i) {
			ExtendProperty prop = prop_set.get(prop_names[i]);
			if (prop == null) continue;		// 属性不存在。
			prop._setPropValue(prop_values[i]);
		}
	}
}
