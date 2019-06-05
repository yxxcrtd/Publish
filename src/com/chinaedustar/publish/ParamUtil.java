package com.chinaedustar.publish;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.action.AbstractParamUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 提供对网页提交参数的便利访问功能。
 * @author liujunxing
 *
 */
public class ParamUtil extends AbstractParamUtil {
	protected final PageContext pageContext;
	protected final PublishContext pub_ctxt;
	
	public ParamUtil(PageContext pageContext) {
		this.pageContext = pageContext;
		this.pub_ctxt = PublishUtil.getPublishContext(pageContext);
	}
	
	/**
	 * 得到指定键的请求值。
	 * @param key
	 * @return
	 */
	@Override public String getRequestParam(String key) {
		return pageContext.getRequest().getParameter(key); 
	}

	/**
	 * 得到指定键的请求值，返回为数组形态。
	 * @param key
	 * @return
	 */
	@Override public String[] getRequestParamValues(String key) {
		return pageContext.getRequest().getParameterValues(key);
	}

	/**
	 * 获得发布系统环境。
	 * @return
	 */
	@Override public PublishContext getPublishContext() {
		return pub_ctxt;
	}
	
	/**
	 * 把一个属性放置到 pageContext 里面，可选实现。
	 * @param key
	 * @param val
	 */
	@Override public void setAttribute(String key, Object val) {
		pageContext.setAttribute(key, val);
	}
	
	/**
	 * 得到URL中中文的参数值
	 * @param key 参数名称
	 * @return
	 */
	public String safeGetChineseParameter(String key) {
		return safeGetChineseParameter(key, "");
	}
	
	/**
	 * 得到URL中中文的参数值
	 * @param key 参数名称
	 * @param defvar 默认值
	 * @return
	 */
	public String safeGetChineseParameter(String key, String defvar) {
		// 如果是post方法发送的数据，直接调用safeGetStringParam方法。
		if (((HttpServletRequest)pageContext.getRequest()).getMethod().equalsIgnoreCase("post")) {
			return safeGetStringParam(key, defvar);
		}
		String value = getRequestParam(key);
		if (null == value) {
			return defvar;
		} else {
			try {
				return new String(value.getBytes(pub_ctxt.getUriOriginalEncoding()), 
						pub_ctxt.getSite().getCharset());
						// pub_ctxt.getUriTargetEncoding()
			} catch (UnsupportedEncodingException e) {
				return value;
			}
		}
	}
	
	/**
	 * 添加Cookie
	 * @param key
	 * @param value
	 */
	public void addCookie(String key, Object value) {
		((HttpServletResponse)pageContext.getResponse()).addCookie(new Cookie(key, value.toString()));
	}
	
	/**
	 * 得到Cookie
	 * @param key Cookie的名称
	 * @return
	 */
	public String safeGetCookie(String key) {
		return safeGetCookie(key, "");
	}
	
	/**
	 * 得到Cookie
	 * @param key Cookie的名称
	 * @param defvar 默认值
	 * @return
	 */
	public String safeGetCookie(String key, String defvar) {
		if (null == key || "".equals(key.trim())) return defvar;
		
		Cookie[] cookies = ((HttpServletRequest)pageContext.getRequest()).getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (key.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		
		return defvar;
	}

	/**
	 * 得到标识组成的数组。比如从排序时的字符串2,4,3,1，得到int[]{2,4,3,1}
	 * @param key
	 * @return
	 */
	public List<Integer> safeGetIds(String key) {
		return this.safeGetIntValues(key);
	}
}
