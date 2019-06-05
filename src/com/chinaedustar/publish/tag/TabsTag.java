package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;

import com.chinaedustar.publish.comp.Tabs;

/**
 * 标签页集合对象的页面标签对象。
 * 
 * 语法：<pub:tabs var="构造好了之后标签页集合在 PageContext 环境中的变量名"
 *    scope="标签页集合的作用范围"
 *    purpose="标签页集合的注释" >
 *    
 * @author wangyi
 *
 */
public class TabsTag extends ComponentTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8949219084883033751L;
	
	/** 在内存中的变量名。 */
	private String var;
	
	/** 标签页集合在程序中的作用范围。page, request, session, application 。 */
	private String scope;
	
	/** 注释，不输出。 */
	private String purpose;
	
	/** 标签页集合对象。 */
	private Tabs tabs = new Tabs();
	
	// === getter, setter =================================================
	
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	/** 得到属性页集合对象。 */
	public Tabs getTabs() {
		return this.tabs;
	}
	
	// === doStartTag, doEndTag 实现 ============================================
	
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		super.pageContext.setAttribute(var, tabs, super.fromScopeString(this.scope));
		tabs = new Tabs();
		return EVAL_PAGE;
	}
}
