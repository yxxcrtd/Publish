package com.chinaedustar.publish;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 发布系统使用的网站过滤器，当前实现如下功能：
 *   1、编码转换，在 request 进入处理之前设置其 encoding 为 UTF-8.(可配置的)
 * 
 * @author liujunxing
 */
public class DefaultFilter implements Filter {
	private static final Log log = LogFactory.getLog(DefaultFilter.class);

	/** 缺省的 Request 解码值。 */
    protected String encoding = "UTF-8";
        
    /** 过滤器的配置信息。 */
    protected FilterConfig filterConfig;
    
    /** 全局 ServletContext 变量。 */
    @SuppressWarnings("unused")
	private ServletContext context;

    /**
     * 初始化过滤器。
     */
	public void init(FilterConfig config) throws ServletException {
		log.info("====================Space DefaultFilter init() config=" + config);
		
		this.filterConfig = config;
		encoding = filterConfig.getInitParameter("encoding");
		//System.out.println("encoding = " + encoding);
		context = config.getServletContext();
	}

	/**
	 * 过滤执行。
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			// do prehandler.
			request.setCharacterEncoding(encoding);		
			
			chain.doFilter(request, response);
			
		} finally {
			// posthandler - 清理 ThreadCurrentMap.
			//ThreadCurrentMap.clear();
		}
	}

	/**
	 * 释放。
	 */
	public void destroy() {
		System.out.println("Publish DefaultFilter destroy()");
		//logger.info("Publish DefaultFilter destroy()");
	}

	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}
}
