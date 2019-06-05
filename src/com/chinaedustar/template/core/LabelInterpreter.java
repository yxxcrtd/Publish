package com.chinaedustar.template.core;

import com.chinaedustar.template.TemplateConstant;

/**
 * 定义一个标签解释器的接口
 */
public interface LabelInterpreter extends TemplateConstant {
	
	/**
	 * 解析一个标签
	 * 
	 * @param label
	 * @param context
	 * @return - 返回定义在 TemplateConstant 中的一个处理返回常量
	 */
	public int processLabel(AbstractLabelElement label, InternalProcessEnvironment env);
	
	/**
	 * 获得父一级的标签解释器
	 * 
	 * @return
	 */
	public LabelInterpreter getParentInterpreter();
	
}
