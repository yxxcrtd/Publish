package com.chinaedustar.template.expr;

/**
 * 表示在解析表达式时发生的异常。此异常是非检查的。
 * 
 * @author liujunxing
 */
public class ExpressionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1433845723820143788L;

	public ExpressionException() {
		
	}
	
	public ExpressionException(String msg) {
		super(msg);
	}
	
	public ExpressionException(Throwable cause) {
		super(cause);
	}
	
	public ExpressionException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
