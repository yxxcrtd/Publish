package com.chinaedustar.template;

import com.chinaedustar.template.core.TemplateFactoryImpl;

/**
 * 模板的工厂类。
 * 
 * @author liujunxing
 */
public abstract class TemplateFactory {
	/**
	 * 使用指定配置创建一个模板工厂。
	 * 一般在全局只需要有一个模板工厂就可以了。
	 * @return
	 */
	public static TemplateFactory createTemplateFactory(java.util.Properties prop) {
		return new TemplateFactoryImpl(prop);
	}
	
	/**
	 * 使用指定配置构造一个 TemplateFactory 的实例。
	 * @param prop
	 */
	protected TemplateFactory(java.util.Properties prop) {
	}
	
	/**
	 * 获得具有指定名字的模板，可能从缓存中读取出来的。
	 * 
	 * @param name
	 * @return
	 */
	public abstract Template getTemplate(String name);

	/**
	 * 根据内容直接编译一个模板。
	 * @param name - 此模板的名字。
	 * @param templateContent - 模板内容。
	 * @return
	 */
	public abstract Template compileTemplate(String name, String templateContent);
	
	/**
	 * 缓存指定的模板。
	 * @param template - 要缓存的模板。
	 * @param expired_ms - 过期时间，单位为毫秒。
	 */
	public abstract void cacheTemplate(Template template, long expired_ms);
	
	/**
	 * 直接从缓存中获取一个模板，如果缓存中没有，则返回 null。
	 * @param name
	 */
	public abstract Template getCachedTemplate(String name);

	/**
	 * 创建模板执行环境。
	 *
	 */
	public abstract ProcessEnvironment createProcessEnvironment();
}
