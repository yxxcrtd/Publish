package com.chinaedustar.template.core;

import com.chinaedustar.template.VariableResolver;
import com.chinaedustar.template.expr.Expression;

/**
 * param 指令。
 * 
 * @author liujunxing
 *
 */
public class ParamInstructionElement extends InstructionElement {
	/** 参数名。 */
	private final String param_name;
	
	/** 缺省值。(当前没有使用，TemplateCompiler 中没有处理) */
	private Expression def_value; 
	
	public ParamInstructionElement(String param_name, Expression def_value) {
		super("param");
		this.param_name = param_name;
		this.def_value = def_value;
	}

	/** 获得字符串表示。 */
	@Override public String toString() {
		return "#param {" + this.param_name + "}";
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		// 获得这个参数。
		Object param_value = env.popParameter();
		if (param_value == null && def_value != null) {
			// 使用缺省值。
			param_value = env.calc(def_value);
		}
		
		// 在模板中定义这个参数变量。
		env.declareVariable(param_name, param_value, VariableResolver.SCOPE_TEMPLATE);
		return PROCESS_SIBLING;
	}
}
