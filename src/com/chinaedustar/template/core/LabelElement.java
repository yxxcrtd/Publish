package com.chinaedustar.template.core;

/**
 * 标签节点
 */
public final class LabelElement extends AbstractLabelElement {
	
	/**
	 * 使用指定的标签名字构造一个 LabelElement 元素
	 * 
	 * @param label_name
	 */
	public LabelElement(String label_name) {
		super(label_name, null);
	}
	
	/**
	 * 使用指定的标签名字和命名空间构造一个 LabelElement 元素。
	 * @param label_name
	 * @param label_namespace
	 */
	public LabelElement(String label_name, String label_namespace) {
		super(label_name, label_namespace, null);
	}

	/**
	 * 使用指定的标签名、命名空间、属性构造一个 LabelElement 元素。
	 * @param label_name
	 * @param label_namespace
	 * @param attr_list
	 */
	public LabelElement(String label_name, String label_namespace, AttributeCollection attr_list) {
		super(label_name, label_namespace, attr_list);
	}

	/** 变成字符串表示。 */
	@Override public String toString() {
		return "{标签名称：" + this.getLabelName() 
			+ "，标签的属性集合：" + this.getAttributes() 
			+ "，标签开始点在原始模板中的行号：" + this.getStartLine() 
			+ "，标签开始点在原始模板中的列号：" + this.getStartCol() + "}";
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		// 请求 env 解释这个标签。
		return env.interpreteLabel(this);
	}
}
