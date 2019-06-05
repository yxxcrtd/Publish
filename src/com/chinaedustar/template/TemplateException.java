package com.chinaedustar.template;

/**
 * 定义模板处理的异常基类。此异常是非检查的。
 *  
 * @author liujunxing
 */
public class TemplateException extends java.lang.RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2113754132852381998L;

	public TemplateException() {
		
	}
	
	public TemplateException(String msg) {
		super(msg);
	}
	
	public TemplateException(Throwable cause) {
		super(cause);
	}
	
	public TemplateException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
