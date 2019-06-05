package com.chinaedustar.publish.engine;

import com.chinaedustar.publish.PublishException;

/**
 * 生成异常。
 * 
 * @author liujunxing
 *
 */
public class GenerateException extends PublishException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1990977105628521338L;

	/**
	 * 构造一个 GenerateException 的实例。
	 *
	 */
	public GenerateException() {
	}
	
	/**
	 * 构造一个 GenerateException 的实例。
	 *
	 */
	public GenerateException(String message) {
		super(message);
	}
	
	/**
	 * 构造一个 GenerateException 的实例。
	 *
	 */
	public GenerateException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	
	/**
	 * 构造一个 GenerateException 的实例。
	 *
	 */
	public GenerateException(Throwable rootCause) {
		super(rootCause);
	}
}
