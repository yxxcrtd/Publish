package com.chinaedustar.template.comp;

import com.chinaedustar.template.VariableResolver;

/**
 * 全局变量容器。其使用同步 Hashtalbe 而非 HashMap 实现同步访问。
 * 
 * @author liujunxing
 */
public class GlobalVariableResovler implements VariableResolver {
	/** 实际的变量集合。 */
	private java.util.Hashtable<String, Object> var_map;
	
	/** 单一实例。 */
	public static final GlobalVariableResovler INSTANCE = new GlobalVariableResovler();
	
	/**
	 * 构造一个 GlobalVariableResovler 的实例。
	 *
	 */
	private GlobalVariableResovler() {
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
		return null;
	}
	
	/**
	 * 设置父变量查找器。
	 * @param vr
	 */
	public void setParentResolver(VariableResolver vr) {
		throw new UnsupportedOperationException("GlobalVariableResovler 不支持具有父查找器。");
	}
	
}
