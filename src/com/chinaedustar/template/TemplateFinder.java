package com.chinaedustar.template;


/**
 * 定义模板的查找接口。
 *
 * 此接口定义根据指定的名字查找到模板的实现。
 * 
 * @author liujunxing
 */
public interface TemplateFinder {
	/**
	 * 查找具有指定名字的模板。
	 * @param template_name - 模板的名字。
	 * @return - 如果有此模板则返回该模板；否则返回 null。
	 */
	public Template findTemplate(String template_name);
	
	/**
	 * 获得父一级的模板装载器。
	 * @return
	 */
	public TemplateFinder getParentFinder();
}
