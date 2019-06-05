package com.chinaedustar.template.expr;

/**
 * 表示函数调用时的参数列表。
 *
 * <p>最好是外面不得随意更改这个集合。但是写一堆接口比较累，暂时先不写。</p>
 * 
 * @author liujunxing
 */
public class ParameterList extends java.util.ArrayList<Parameter> {
	/** 无参数的良好替代对象。 */
	public static final ParameterList EMPTY_PARAMETER_LIST = new ParameterList();
	
	/**
	 * 序列化。
	 */
	private static final long serialVersionUID = 1083134746966831348L;
}
