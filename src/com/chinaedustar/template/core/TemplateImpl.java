package com.chinaedustar.template.core;

import com.chinaedustar.template.Template;

/**
 * 一个简单的模板实现对象。
 * 
 * @author liujunxing
 */
final class TemplateImpl implements Template {
	/** 此模板的名字。 */
	private final String template_name;
	
	/** 根对象。 */
	private final AbstractTemplateElement root_elem;
	
	/**
	 * 使用指定的模板名字构造一个 TemplateImpl 的实例。
	 * @param template_name
	 */
	TemplateImpl(String template_name, AbstractTemplateElement root_elem) {
		if (root_elem == null) 
			throw new IllegalArgumentException("root_elem == null");
		this.template_name = template_name == null ? "" : template_name;
		this.root_elem = root_elem;
	}
	
	/** 获得字符串表示。 */
	@Override public String toString() {
		return "TemplateImpl{name=" + this.template_name + "}";
	}
	
	/**
	 * 获得此模板的名字。
	 * @return
	 */
	public String getName() {
		return this.template_name;
	}
		
	/**
	 * 获得此模板的根元素。
	 * @return
	 */
	public AbstractTemplateElement getRootElement() {
		return this.root_elem;
	}
}
