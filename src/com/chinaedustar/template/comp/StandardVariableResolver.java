package com.chinaedustar.template.comp;

import java.util.Map;

import com.chinaedustar.template.VariableResolver;

/**
 * 模板级变量的容器实现。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class StandardVariableResolver implements VariableResolver {
	/** 实际的变量集合。 */
	private java.util.Map var_map;
	
	/** 父级容器。 */
	private VariableResolver parent_var_resolver;
	
	/**
	 * 构造一个 TemplateVariableResolver 的实例。
	 *
	 */
	public StandardVariableResolver() {
		this.parent_var_resolver = null;
	}
	
	/**
	 * 使用指定 parent 构造一个 TemplateVariableResolver 的实例。
	 * @param parent
	 */
	public StandardVariableResolver(VariableResolver parent) {
		this.parent_var_resolver = parent;
	}
	
	/**
	 * 使用指定的变量容器构造一个 TemplateVariableResolver 的实例。
	 * @param var_map
	 */
	public StandardVariableResolver(Map var_map) {
		this.var_map = var_map;
		this.parent_var_resolver = null;
	}

	/**
	 * 使用指定的变量容器和父容器构造一个 TemplateVariableResolver 的实例。
	 * @param var_map
	 */
	public StandardVariableResolver(Map var_map, VariableResolver parent) {
		this.var_map = var_map;
		this.parent_var_resolver = parent;
	}

	/**
	 * 查找具有名字为 var_name 的变量。
	 * @param var_name
	 * @return - 返回 null 表示没有找到；返回
	 */
	public Object resolveVariable(String var_name) {
		if (this.var_map == null) return null;
		return this.var_map.get(var_name);
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
