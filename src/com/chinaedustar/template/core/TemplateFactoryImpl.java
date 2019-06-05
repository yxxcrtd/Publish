package com.chinaedustar.template.core;

import com.chinaedustar.common.Cache;
import com.chinaedustar.common.SimpleCache;
import com.chinaedustar.template.Template;
import com.chinaedustar.template.TemplateFactory;
import com.chinaedustar.template.ProcessEnvironment;

public class TemplateFactoryImpl extends TemplateFactory {
	/** 模板的缓存 */
	private final Cache template_cache; 
	
	public TemplateFactoryImpl(java.util.Properties prop) {
		super(prop);
		this.template_cache = new SimpleCache();
	}
	
	/**
	 * 获得具有指定名字的模板，可能从缓存中读取出来的。
	 * 
	 * @param name
	 * @return
	 */
	@Override public Template getTemplate(String name) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 根据内容直接编译一个模板。
	 * @param name - 此模板的名字。
	 * @param templateContent - 模板内容。
	 * @return
	 */
	@Override public Template compileTemplate(String name, String templateContent) {
		// 创建编译器，并进行编译。
		TemplateCompiler compiler = new TemplateCompiler(this);
		AbstractTemplateElement root_elem = compiler.compileTemplate(templateContent);
		return new TemplateImpl(name, root_elem);
	}
	
	/**
	 * 缓存指定的模板。
	 * @param template - 要缓存的模板。
	 * @param expired_ms - 过期时间，单位为毫秒。
	 */
	@Override public void cacheTemplate(Template template, long expired_ms) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 直接从缓存中获取一个模板，如果缓存中没有，则返回 null。
	 * @param name
	 */
	@Override public Template getCachedTemplate(String name) {
		Object result = this.template_cache.get(name);
		if (result == null) return null;
		
		return (Template)result;
	}
	
	/**
	 * 创建模板执行环境。
	 *
	 */
	@Override public ProcessEnvironment createProcessEnvironment() {
		DefaultProcessEnvironment env = new DefaultProcessEnvironment(this);
		env.initialize();
		return env;
	}

	/**
	 * 装载模板。
	 * @param name
	 * @return
	 */
	/*
	private Template loadTemplate(String name) {
		throw new UnsupportedOperationException();
	}
	*/
}
