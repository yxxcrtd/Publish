package com.chinaedustar.publish.tag;

/**
 * 定义模板标签使用的模板容器类。
 * 
 * @author liujunxing
 */
public class TemplateContainer {
	/** 模板的 map. */
	private final java.util.Hashtable<String, TemplateWrapper> templ_map = 
		new java.util.Hashtable<String, TemplateWrapper>();
	
	/**
	 * 获得指定名字的模板。
	 * @param name - 模板的名字。
	 * @return - 返回为一个模板包装器。
	 */
	public TemplateWrapper getTemplateWrapper(String name) {
		return templ_map.get(name);
	}
	
	/**
	 * 给这个模板容器中添加一个模板。
	 * @param tw
	 */
	public void putTemplate(TemplateWrapper tw) {
		this.templ_map.put(tw.getName(), tw);
	}
	
	/**
	 * 返回项目数量。
	 * @return
	 */
	public int size() {
		return this.templ_map.size();
	}
	
	/**
	 * 返回模板的枚举器。
	 * @return
	 */
	public java.util.Iterator<TemplateWrapper> iterator() {
		return this.templ_map.values().iterator();
	}
}
