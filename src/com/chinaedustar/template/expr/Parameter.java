package com.chinaedustar.template.expr;

/**
 * 表示一个参数。参数实际就是一个表达式，只是名字叫参数，更容易理解一些。
 * 
 * @author liujunxing
 */
public class Parameter extends Expression {
	/**
	 * 使用指定的根节点构造一个 Parameter 的实例。
	 * @param root_node
	 */
	public Parameter(AbstractExpressionNode root_node) {
		super(root_node);
	}
}
