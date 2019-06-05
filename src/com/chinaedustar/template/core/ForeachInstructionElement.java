package com.chinaedustar.template.core;

import com.chinaedustar.template.expr.Expression;

/**
 * foreach 指令。
 * 
 * @author liujunxing
 */
public class ForeachInstructionElement extends InstructionElement {
	/** 循环变量的名字。 */
	private final String var_name;
	
	/** 集合表达式。 */
	private final Expression coll_expr;
	
	/**
	 * 构造函数。
	 *
	 */
	public ForeachInstructionElement(String var_name, Expression coll_expr) {
		super("foreach");
		this.var_name = var_name;
		this.coll_expr = coll_expr;
	}
	
	/** 转换为字符串表示。 */
	@Override public String toString() {
		return "ForeachInstruction{#foreach " + this.var_name + " in " + " " + coll_expr + "}";
	}


	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		// 计算 coll_expr, 从结果获得一个枚举接口，用于枚举这个集合。
		Object coll_result = env.calc(coll_expr);
		env.foreach(this, var_name, coll_result, null);
		
		// 返回让处理下一个兄弟元素。
		return PROCESS_SIBLING;
	}
}
