package com.chinaedustar.publish.tag;

import com.chinaedustar.template.Template;
import com.chinaedustar.template.TemplateFactory;

/**
 * 定义一个模板的包装器。
 * 
 * <p>模板包装器的最主要用途是延迟模板的编译时间。</p>
 * 
 * @author liujunxing
 */
public class TemplateWrapper {
	/** 已经编译过的模板。 */
	private Template compiled_template;
	
	/** 模板的类工厂对象。 */
	private TemplateFactory factory;
	
	/** 模板的名字。 */
	private String name;
	
	/** 模板的内容。 */
	private String template_content;
	
	/**
	 * 使用指定的模板类工厂、名字、模板内容构造一个模板包装器。
	 * @param factory
	 */
	public TemplateWrapper(TemplateFactory factory, String name, String template_content) {
		this.factory = factory;
		this.name = name;
		this.template_content = template_content;
	}

	/**
	 * 使用指定的名字、编译过的模板构造一个模板包装器。
	 * @param factory
	 */
	public TemplateWrapper(String name, Template template) {
		this.compiled_template = template;
		this.factory = null;
		this.name = template.getName();
		this.template_content = "";
	}

	/**
	 * 获得模板。
	 * @return
	 */
	public Template getTemplate() {
		if (this.compiled_template != null) return this.compiled_template; 

		// 还未编译过，则现在编译。
		Template template = this.factory.compileTemplate(name, template_content);
		synchronized (this) {
			if (this.compiled_template != null) return this.compiled_template;
			this.compiled_template = template;
		}
		
		return this.compiled_template;
	}
	
	/**
	 * 获得模板的名字。
	 * @return
	 */
	public String getName() {
		return this.name;
	}
}
