package com.chinaedustar.template.core;

import com.chinaedustar.template.expr.Expression;

/**
 * return 指令节点。
 * 
 * @author liujunxing
 */
public class ReturnInstructionElement extends InstructionElement {
	private Expression ret_expr;
	/**
	 * 
	 * @param expr
	 */
	public ReturnInstructionElement(Expression ret_expr) {
		super("return");
		this.ret_expr = ret_expr;
	}
	
	/** 转为字符串表示。 */
	public String toString() {
		return "ReturnInstruction{ret=" + ret_expr + "}";
	}

	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		throw new UnsupportedOperationException("return not implement");
	}
}
