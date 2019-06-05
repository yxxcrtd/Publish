package com.chinaedustar.template.core;

/**
 * 表示一个模板中的简单文字元素节点。
 * 
 * @author liujunxing
 */
public final class TextElement extends AbstractTemplateElement {
	/** 文字内容。 */
	private final String text;
	
	/**
	 * 使用指定的父元素和文字构造一个 TextElement 的实例。
	 * @param parent
	 * @param text
	 */
	public TextElement(String text) {
		this.text = text;
	}
	
	public String toString() {
		return "TextElement{text=" + this.text + "}";
	}
	
	/**
	 * 获得文字内容。
	 * @return
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return
	 */
	public int accept(InternalProcessEnvironment env) {
		env.getOut().write(this.text);
		return 0;
	}
}
