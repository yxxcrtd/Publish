package com.chinaedustar.publish.impl;

import javax.servlet.jsp.PageContext;
import com.chinaedustar.template.VariableResolver;

/**
 * 页面环境的变量查找器实现。
 * 
 * @author liujunxing
 *
 */
public class PageVariableResolver implements VariableResolver {
	/** 父级变量查找器。 */
	private VariableResolver parent_var_resolver;
	private final PageContext page_ctxt;
	
	public PageVariableResolver(VariableResolver parent_var_resolver, PageContext page_ctxt) {
		this.parent_var_resolver = parent_var_resolver;
		this.page_ctxt = page_ctxt;
	}
	
	/**
	 * 查找具有名字为 name 的变量。
	 * @param name
	 * @return - 返回 null 表示没有找到；返回
	 */
	public Object resolveVariable(String name) {
		Object result = page_ctxt.findAttribute(name);
		return result;
	}
	
	/**
	 * 获得父变量查找器。
	 * @return
	 */
	public VariableResolver getParentResolver() {
		return this.parent_var_resolver;
	}
	
	/**
	 * 设置父变量查找器。
	 * @param vr
	 */
	public void setParentResolver(VariableResolver vr) {
		this.parent_var_resolver = vr;
	}
}
