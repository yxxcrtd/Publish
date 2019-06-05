package com.chinaedustar.template.core;

import com.chinaedustar.template.expr.Expression;

/**
 * 一个表达式元素，表达式元素类似于正文元素，其没有子元素。
 * 
 * @author liujunxing
 *
 */
public class ExpressionElement extends AbstractTemplateElement {
	/** 实际的表达式。 */
	private final Expression expr;
	
	/**
	 * 使用指定的父节点构造一个 ExpressionElement 的节点。
	 * @param parent
	 */
	public ExpressionElement(Expression expr) {
		this.expr = expr;
	}
	
	/**
	 * 获得表达式字符串。
	 * @return
	 */
	public Expression getExpression() {
		return this.expr;
	}
	
	/** 转换为字符串表示。 */
	@Override public String toString() {
		return "#Expr{" + expr + "}";
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return
	 */
	@Override public int accept(InternalProcessEnvironment env) {
		// 使用环境计算这个表达式，并输出结果。
		Object result = env.calc(this.expr);
		env.getOut().write(result == null ? "" : result.toString());
		
		// 继续下一个节点的处理。
		return PROCESS_DEFAULT;
	}
}
