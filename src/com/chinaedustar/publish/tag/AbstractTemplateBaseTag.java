package com.chinaedustar.publish.tag;

import com.chinaedustar.template.TemplateFactory;

/**
 * 所有模板类标签的基类标签，其提供对模板引擎的创建和引用功能。
 * 
 * @author liujunxing
 *
 */
abstract class AbstractTemplateBaseTag extends ComponentTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4267767556841981462L;

	/**
	 * 获得 Web-App 级的模板工厂，如果不存在，则创建一个缺省的。
	 * @return
	 */
	public final TemplateFactory getTemplateFactory() {
		return PageContextTemplate.getTemplateFactory(super.pageContext);
	}

	/**
	 * 获得页面级模板容器对象，如果没有则创建一个。
	 * @return
	 */
	public final TemplateContainer getPageTemplateContainer() {
		return PageContextTemplate.getPageTemplateContainer(super.pageContext);
	}
}
