package com.chinaedustar.template.core;

/**
 * break 指令节点。
 * 
 * @author liujunxing
 *
 */
public class BreakInstructionElement extends InstructionElement {
	public BreakInstructionElement() {
		super("break");
	}
	
	/** 转为字符串表示。 */
	public String toString() {
		return "BreakInstruction{}";
	}

	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		env.breakk();
		return INSTRU_BREAK;		// 实际上这里不会执行到，但是编译器不让。
	}
}
