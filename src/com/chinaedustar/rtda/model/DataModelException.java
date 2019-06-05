package com.chinaedustar.rtda.model;

/**
 * 定义数据模型使用的异常基类。此异常在所有 DataModel 中都可能抛出，但非检查的。
 * 
 * @author liujunxing
 */
public class DataModelException extends RuntimeException {
	/**
	 * 序列化。
	 */
	private static final long serialVersionUID = 7616160545978736530L;

	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public DataModelException() {
		
	}
	
	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public DataModelException(String msg) {
		super(msg);
	}
	
	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public DataModelException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public DataModelException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
