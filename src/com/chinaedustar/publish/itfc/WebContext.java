package com.chinaedustar.publish.itfc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.PublishModelObject;
import com.chinaedustar.publish.model.Site;

/**
 * 定义在一个页面处理期间的环境信息。通过此环境能够访问到 PublishContext, 
 *   PageContext, ServletContext 环境。
 * 
 * @author liujunxing
 */
public class WebContext implements WebContext2 {
	public static final String PAGE_SCOPE = "page";
	
	public static final String REQUEST_SCOPE = "request";
	
	public static final String SESSION_SCOPE = "session";
	
	public static final String PUBLISH_SCOPE = "publish";
	
	public static final String APPLICATION_SCOPE = "application";
	
	protected final PublishContext pub_ctxt;
	protected final PageContext page_ctxt;
	protected final ServletContext app_ctxt;

	/**
	 * 使用指定的页面环境变量构造 StandardWebContext 的实例。
	 * @param page_ctxt - 页面环境。
	 */
	public WebContext(PageContext page_ctxt) {
		this(page_ctxt, PublishUtil.getPublishContext(page_ctxt));
	}

	/**
	 * 使用指定的页面环境变量构造 StandardWebContext 的实例。
	 * @param page_ctxt - 页面环境。
	 * @param pub_ctxt - 发布系统环境。
	 */
	public WebContext(PageContext page_ctxt, PublishContext pub_ctxt) {
		this.page_ctxt = page_ctxt;
		this.app_ctxt = page_ctxt.getServletContext();
		this.pub_ctxt = pub_ctxt;
	}
	
	/**
	 * 获得发布系统环境对象。
	 * @return
	 */
	public PublishContext getPublishContext() {
		return this.pub_ctxt;
	}
	
	/**
	 * 获得页面环境对象。
	 * @return
	 */
	public PageContext getPageContext() {
		return this.page_ctxt;
	}
	
	/**
	 * 获得 WebApp 环境对象。
	 * @return
	 */
	public ServletContext getApplicationContext() {
		return this.app_ctxt;
	}
	
	/**
	 * 获得当前页面的 Request 对象。
	 * @return
	 */
	public ServletRequest getRequest() {
		return this.page_ctxt.getRequest();
	}
	
	/**
	 * 获得当前页面的 Response 对象。
	 * @return
	 */
	public ServletResponse getResponse() {
		return this.page_ctxt.getResponse();
	}
	
	/**
	 * 获得当前页面的 HttpSession 对象。
	 * @return
	 */
	public HttpSession getSession() {
		return this.page_ctxt.getSession();
	}
	
	/**
	 * 获得页面操作 action, 先从 request.Parameter() 中获取，如果没有
	 *   给出，则从 pageContext.getAttribute()。如果没有则返回 null.
	 * @return
	 */
	public String getAction(String default_action) {
		// 先从 request.parameter 中获取。
		String action = this.page_ctxt.getRequest().getParameter("action");
		if (action != null) return action;
		
		// 如果没有从 page.attributes 里面获取。
		Object obj_action = this.page_ctxt.getAttribute("action");
		if (obj_action == null) return default_action; // 没有找到则返回缺省的。
		
		if (obj_action instanceof String) return (String)obj_action;
		return obj_action.toString();
	}

	/**
	 * 获得指定范围的变量值。
	 * @param name - 变量名字。
	 * @param scope - 范围值，取值为 page, request, session, application
	 * @return
	 */
	public Object getAttribute(String name, String scope) {
		int scope_code = PublishUtil.fromScopeValue(scope);
		return page_ctxt.getAttribute(name, scope_code);
	}

	/**
	 * 设置指定名字的变量值到页面级变量中。
	 * @param name - 变量名字。
	 * @param value - 变量的值。
	 */
	public void setAttribute(String name, Object value) {
		page_ctxt.setAttribute(name, value);
	}

	/**
	 * 设置指定范围的变量值。
	 * @param name - 变量名字。
	 * @param value - 变量的值。
	 * @param scope - 范围值，取值为 page, request, session, application
	 */
	public void setAttribute(String name, Object value, String scope) {
		int scope_code = PublishUtil.fromScopeValue(scope);
		page_ctxt.setAttribute(name, value, scope_code);
	}

	/**
	 * 获得指定键的请求参数值。
	 * @param key
	 * @return
	 */
	public String getRequestParam(String key) {
		return page_ctxt.getRequest().getParameter(key);
	}

	/**
	 * 获得当前站点对象。
	 * @return
	 */
	public Site getSite() {
		return this.pub_ctxt.getSite();
	}
	
	/**
	 * 获得这个页面的 this 对象 - 页面当前模型对象。
	 * @return
	 */
	public PublishModelObject getThis() {
		Object this_o = page_ctxt.getAttribute("this");
		if (this_o == null) return null;
		if (this_o instanceof PublishModelObject) return (PublishModelObject)this_o;
		// 有这么一个变量，但是不是 PublishModelObject 类型的。
		return null;
	}
}
