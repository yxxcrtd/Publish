package com.chinaedustar.template.core;

import com.chinaedustar.template.expr.Expression;

/**
 * 表示一个条件执行元素。
 * 
 * @author liujunxing
 */
public class ConditionElement extends InstructionElement {
	/** 指令名字，可能是 if, elseif, else 几种。 */
	// private final String instruction;
	
	/** 条件判断表达式。 */
	private Expression cond_expr;
	
	/**
	 * 使用指定的指令名字和条件表达式构造一个 ConditionElement 的实例。
	 * @param instruction
	 * @param cond_expr
	 */
	public ConditionElement(String instruction, Expression cond_expr) {
		super(instruction);
		this.cond_expr = cond_expr;
	}
	
	/**
	 * 
	 */
	public String toString() {
		return "ConditionElement{#" + super.getInstruction() + ",expr=" + cond_expr + "}";
	}
	
	/**
	 * 返回条件表达式。
	 * @return
	 */
	public Expression getConditionExpression() {
		return this.cond_expr;
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		throw new UnsupportedOperationException("ConditionElement accept 未实现。");
	}
}
