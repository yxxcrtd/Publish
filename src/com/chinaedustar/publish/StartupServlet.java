package com.chinaedustar.publish;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.chinaedustar.publish.impl.StandardPublishContext;

/**
 * 发布系统启动初始化的 Servlet.
 * 
 * <p>这个 Servlet 必须配置在 web.xml 中，其在网站初始化的时候负责初始化必要的数据。</p>
 * 
 * @author liujunxing
 */
public class StartupServlet implements javax.servlet.Servlet {

	private ServletConfig config;

	/**
	 * 构造函数。
	 *
	 */
	public StartupServlet() {
	}
	
	/**
	 * Called by the servlet container to indicate to a servlet that 
	 *   the servlet is being placed into service. 
	 */
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		
		// 加载 log4j 的配置。
		ServletContext app = this.config.getServletContext();
		
//		// String logPath = config.getServletContext().getRealPath("") + "\\WEB-INF\\conf\\log4j.property";
//		String logPath = config.getServletContext().getRealPath("") + File.separator + "WEB-INF" + File.separator + "conf" + File.separator + "log4j.property";
//		logger.info("开始加载 Log4j 配置，路径：" + logPath);
//		
//		PropertyConfigurator.configure(logPath);
//		logger.info("init() config=" +  config);
		
		// 构造一个发布系统的运行环境，并放置在 app 中。
		String root_dir = config.getServletContext().getRealPath("/");
		StandardPublishContext pub_ctxt = new StandardPublishContext(root_dir, true);
		pub_ctxt.initUriEncoding(config.getInitParameter("uriOriginalEncoding"), config.getInitParameter("uriTargetEncoding"));
		app.setAttribute(PublishContext.PUBLISH_CONTEXT_KEY, pub_ctxt);
	}
	
	/**
	 * Returns a ServletConfig object, which contains initialization 
	 *   and startup parameters for this servlet. The ServletConfig 
	 *   object returned is the one passed to the init method.
	 */
	public ServletConfig getServletConfig() {
		return this.config;
	}
	
	
	/**
	 * Called by the servlet container to allow the servlet to respond 
	 *  to a request. 
	 */
	public void service(ServletRequest req, ServletResponse resp) {
	}
            
	/**
	 * Returns information about the servlet, such as author, version, 
	 *   and copyright.
	 */
	public String getServletInfo() {
		return "Publish System StartupServlet 1.0.0, (c)Copyright by www.chinaedustar.com";
	}
	
	/**
	 * Called by the servlet container to indicate to a servlet that the 
	 *   servlet is being taken out of service. This method is only called 
	 *   once all threads within the servlet's service method have exited 
	 *   or after a timeout period has passed. After the servlet container
	 *   calls this method, it will not call the service method again on 
	 *   this servlet. 
	 */
	public void destroy() {
	}
}
