package com.chinaedustar.template.core;

/**
 * 内建模板标签解释器。
 * 
 * <p>内建的模板标签解释器负责对 if, switch, for, return, break, call 等核心
 *   标签进行解释。</p> 
 * 
 * @author liujunxing
 */
public class BuiltinLabelInterpreter implements LabelInterpreter {
	
	/**
	 * 解释一个标签
	 * 
	 * @param label
	 * @param context
	 */
	public int processLabel(AbstractLabelElement label, InternalProcessEnvironment env) {
		// 当前没有内建 label 需要解释。if,switch 等都实现为指令了		
		return LABEL_UNKNOWN;
	}

	/**
	 * 获得父一级的标签解释器。缺省的标签解释器没有父一级
	 * 
	 * @return
	 */
	public LabelInterpreter getParentInterpreter() {
		return null;
	}
	
}
