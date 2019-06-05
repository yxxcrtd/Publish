package com.chinaedustar.template.core;

import com.chinaedustar.template.expr.Expression;

/**
 * 赋值指令。
 * 
 * @author liujunxing
 */
public class AssignInstructionElement extends InstructionElement {
	/** 变量名。 */
	private String var_name;
	
	/** 表达式。 */
	private Expression assign_expr;
	
	public AssignInstructionElement(String var_name, Expression assign_expr) {
		super("assign");
		this.var_name = var_name;
		this.assign_expr = assign_expr;
	}
	
	@Override public int accept(InternalProcessEnvironment env) {
		// 1. 计算表达式。
		Object val = env.calc(assign_expr);
		
		// 赋值。
		env.assignVariable(var_name, val);
		
		return 0;
	}
}
