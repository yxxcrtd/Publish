package com.chinaedustar.publish;

/**
 * 定义数据访问组件产生的异常基类。此异常是非检查的。
 * 
 * @author liujunxing
 *
 */
public class PublishException extends java.lang.RuntimeException {
	/**
	 * 序列化编号。
	 */
	private static final long serialVersionUID = 7400245378753028680L;

	/**
	 * 构造一个 PublishException 的实例。
	 *
	 */
	public PublishException() {
	}
	
	/**
	 * 构造一个 PublishException 的实例。
	 *
	 */
	public PublishException(String message) {
		super(message);
	}
	
	/**
	 * 构造一个 PublishException 的实例。
	 *
	 */
	public PublishException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	
	/**
	 * 构造一个 PublishException 的实例。
	 *
	 */
	public PublishException(Throwable rootCause) {
		super(rootCause);
	}
}
