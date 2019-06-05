package com.chinaedustar.publish.tag;

import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;
import com.chinaedustar.template.TemplateFactory;

/**
 * 提供访问位于 PageContext 模板标签使用的变量的辅助函数。
 * 
 * @author liujunxing
 */
public class PageContextTemplate {
	/** 模板工厂在 Application 环境中存储的键值。 */
	public static final String TEMPLATE_FACTORY_KEYNAME = "com.chinaedustar.publish.TemplateFactory";
	
	/** 模板容器在 Reqeust 环境中存储的键值。 */
	public static final String TEMPLATE_CONTAINER_KEYNAME = "com.chinaedustar.publish.TemplateContainer";

	private PageContextTemplate() {
	}
	
	/**
	 * 获得 Web-App 级的模板工厂，如果不存在，则创建一个缺省的。
	 * @return
	 */
	public static final TemplateFactory getTemplateFactory(PageContext pageContext) {
		// 尝试从现有环境中查找。
		ServletContext app = pageContext.getServletContext();
		TemplateFactory factory = (TemplateFactory)app.getAttribute(TEMPLATE_FACTORY_KEYNAME);

		// 如果还没有创建则现在创建。
		// TODO: 缺省属性描述在 /WEB-INF/template_factory.properties 文件中。
		Properties props = new Properties();
		if (factory == null) {
			factory = createAndSetTemplateFactory(app, props);
		}
		
		return factory;
	}
	
	/**
	 * 获得页面级模板容器对象，如果没有则创建一个。
	 * @return
	 */
	public static final TemplateContainer getPageTemplateContainer(PageContext pageContext) {
		// 尝试在现有请求中找。
		ServletRequest request = pageContext.getRequest();
		TemplateContainer template_container = (TemplateContainer)request.getAttribute(TEMPLATE_CONTAINER_KEYNAME);
		
		if (template_container == null) {
			
			// 如果没有则创建一个。
			template_container = new TemplateContainer();
			// modified by liujunxing: 当前模板容器放在 request 中，这样模板能够跨越 
			//   pageContext 在一次 request 中使用了。这是为了支持 jsp:include 指令
			//   包含其它页面时候也能使用前面的模板而改变的。
			request.setAttribute(TEMPLATE_CONTAINER_KEYNAME, template_container);
		}
		return template_container;
	}
	
	/**
	 * 使用指定参数创建一个模板工厂。
	 * @param application - 网站应用程序对象。
	 * @return
	 */
	private static final TemplateFactory createAndSetTemplateFactory(ServletContext application, Properties props) {
		synchronized (application) {
			TemplateFactory factory = (TemplateFactory)application.getAttribute(TEMPLATE_FACTORY_KEYNAME);
			if (factory != null) return factory;
			
			factory = TemplateFactory.createTemplateFactory(props);
			application.setAttribute(TEMPLATE_FACTORY_KEYNAME, factory);
			return factory;
		}
	}
}
