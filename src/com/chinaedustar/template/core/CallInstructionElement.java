package com.chinaedustar.template.core;

import com.chinaedustar.template.TemplateException;
import com.chinaedustar.template.expr.ParameterList;

/**
 * call 指令，语法 #{call template_name (parameter-list) }
 *
 * 当 template_name == "dynamic" 的时候，使用第一个参数做为要执行的模板的名字。
 * 
 * @author liujunxing
 *
 */
public class CallInstructionElement extends InstructionElement {
	/** 要调用的子模板名字。 */
	private String child_template_name;
	
	/** 调用参数列表。 */
	private ParameterList params;
	
	/**
	 * 
	 *
	 */
	public CallInstructionElement(String child_template_name, ParameterList params) {
		super("call");
		this.child_template_name = child_template_name;
		this.params = params;
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		if (this.child_template_name.equals("dynamic")) {
			return dynamicNamedCall(env);
		} else {
			// 计算参数。
			Object[] p = calcParameterList(env);
			
			// 调用子模板，并将输出写到当前环境。
			String result = env.call(child_template_name, p);
			env.getOut().write(result);
			return PROCESS_SIBLING;
		}
	}
	
	private int dynamicNamedCall(InternalProcessEnvironment env) {
		// 1. 计算参数。
		Object[] p = calcParameterList(env);
		if (p.length < 1 || p[0] == null) 
			throw new TemplateException("#call dynamic 动态模板名字必须有至少一个模板名字的参数。");
		
		String child_template = p[0].toString();
		String result = env.call(child_template, p);
		env.getOut().write(result);
		return PROCESS_SIBLING;
	}
	
	/**
	 * 计算参数列表。
	 * @return
	 */
	private Object[] calcParameterList(InternalProcessEnvironment env) {
		if (this.params == null) return new Object[0];
		Object[] p = new Object[this.params.size()];
		for (int index = 0; index < p.length; ++index) {
			p[index] = env.calc(this.params.get(index));
		}
		return p;
	}
}

