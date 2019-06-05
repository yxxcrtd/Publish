package com.chinaedustar.publish.impl;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.itfc.LabelHandler;
import com.chinaedustar.publish.model.BuiltinLabel;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LabelInterpreter;

/**
 * 发布系统的标签解释器实现
 */
public class PublishLabelInterpreter implements LabelInterpreter {
	
	/**
	 * 发布系统环境对象
	 */
	private final PublishContext pub_ctxt;
	
	/**
	 * 父标签解释器
	 */
	private final LabelInterpreter parent;

	/**
	 * 构造函数
	 * 
	 * @param inter
	 */
	public PublishLabelInterpreter(PublishContext pub_ctxt, LabelInterpreter inter) {
		this.pub_ctxt = pub_ctxt;
		this.parent = inter;
	}
	
	/**
	 * 解析一个标签
	 * 
	 * @param label
	 * @param context
	 * @return - 返回定义在 TemplateConstant 中的一个处理返回常量
	 */
	public int processLabel(AbstractLabelElement label, InternalProcessEnvironment env) {
		
		// 1，查找自定义标签
		int process_result = processCustomLabel(label, env);
		
		if (process_result != LABEL_UNKNOWN) {
			return process_result;
		}
		
		// 2，查找系统预定义的
		String label_name = label.getLabelName();
		
		LabelHandler handler = pub_ctxt.getLabelHandlers().get(label_name);
		if (null == handler) {
			return LABEL_UNKNOWN;
		}
		
		return handler.getLabelHandler(pub_ctxt, env, label).handleLabel(pub_ctxt, env, label);
	}
	
	// 处理一个用户自定义标签。返回 true 表示处理了此自定义标签，false 为没有这个标签。
	private int processCustomLabel(AbstractLabelElement label, InternalProcessEnvironment env) {
		BuiltinLabel custom_label = pub_ctxt.getSite().getLabelCollection().findLabel(label.getLabelName());
		if (custom_label == null) return LABEL_UNKNOWN;
		
		// 当前仅支持静态 custom_label，以后再支持动态的。
		return custom_label.process(env, null);
	}
	
	/**
	 * 获得父一级的标签解释器
	 * 
	 * @return
	 */
	public LabelInterpreter getParentInterpreter() {
		return this.parent;
	}
	
}
