package com.chinaedustar.rtda.oper;

import com.chinaedustar.rtda.model.DataModelException;

/**
 * 执行数学计算时发生的异常。
 * 
 * @author liujunxing
 */
public class CalculationException extends DataModelException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2338967040943665385L;

	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public CalculationException() {
		
	}
	
	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public CalculationException(String msg) {
		super(msg);
	}
	
	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public CalculationException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造一个 DataException 的实例。
	 *
	 */
	public CalculationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
