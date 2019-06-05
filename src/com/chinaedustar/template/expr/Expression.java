package com.chinaedustar.template.expr;

/**
 * 表示一个表达式。
 * 
 * <p>表达式是一棵节点构成的树，节点类型包括：</p>
 * <pre>
 *   AbstractExpressionNode - 表达式节点抽象表示。
 *     AbstractLeftNode - 叶子节点抽象表示。
 *       ConstantNode - 表示一个常量，常量是叶子节点。
 *     AbstractContainerNode - 容器节点抽象表示。
 *       OperatorNode - 表示一个操作子，操作子一般有左右子节点。
 * </pre>
 * 
 * @author liujunxing
 */
public class Expression {
	/** 此表达式的根节点。 */
	private AbstractExpressionNode root_node;
	
	/**
	 * 使用指定的根节点构造一个 Expression 的实例。
	 * @param root_node
	 */
	public Expression(AbstractExpressionNode root_node) {
		this.root_node = root_node;
	}
	
	public String toString() {
		return "Expression{" + root_node + "}";
	}
	
	/**
	 * 获得表达式根节点。
	 * @return
	 */
	public AbstractExpressionNode getRootNode() {
		return this.root_node;
	}
	
	/**
	 * 使用指定的计算器计算这个表达式的值。
	 * 
	 * @return
	 */
	public Object eval(ExpressionCalculator calc) {
		return root_node.eval(calc);
	}
}
