package com.chinaedustar.template.core;

/**
 * 对枚举器变量进行修饰的实现。修饰之后能够支持 builtin is_iter, 
 *   is_first, is_last, index 等功能了。
 * 
 * @author liujunxing
 */
public class LoopVarDecorator {
	/** 被修饰的目标对象。 */
	public Object target;
	
	/** 此循环变量的索引。 */
	public int index;
	
	/** 是否是集合中第一个循环变量。 */
	public boolean is_first;
	
	/** 是否是集合中最后一个循环变量。 */
	public boolean is_last;
}
