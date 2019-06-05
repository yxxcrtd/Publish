package com.chinaedustar.template.core;

/**
 * 标签元素的基类
 */
public abstract class AbstractLabelElement extends AbstractContainerElement {
	
	/**
	 * 标签名字
	 */
	private final String label;
	
	/**
	 * 获得标签的命名空间
	 */
	private final String label_namespace;
	
	/**
	 * 是否是一个结束标签
	 */
	private final boolean is_end_label;
	
	/** 这个节点是否已经有了匹配的结束标签。 */
	private boolean has_match_end_label;
	
	/** 属性集合, 从来都非空。 */
	private final AttributeCollection attr_list;
	
	/** 此标签的开始行、列。 */
	private int startLine, startCol;
	
	/** 此标签的结束行、列。 */
	private int endLine, endCol;
	
	/**
	 * 构造一个 AbstractLabelElement 的实例
	 * 
	 * @param name_r
	 * @param attr_list
	 */
	protected AbstractLabelElement(String label_name, AttributeCollection attr_list) {
		this.label = label_name;
		this.label_namespace = null;
		this.is_end_label = false;
		this.attr_list = AttributeCollection.optimizedCollection(attr_list);
	}
	
	/**
	 * 构造一个 AbstractLabelElement 的实例。
	 * @param name_r
	 * @param attr_list
	 */
	protected AbstractLabelElement(String label_name, String label_namespace, AttributeCollection attr_list) {
		this.label = label_name;
		this.label_namespace = label_namespace;
		this.is_end_label = false;
		this.attr_list = AttributeCollection.optimizedCollection(attr_list);
	}

	/**
	 * 构造一个 AbstractLabelElement 的实例。
	 * @param name_r
	 * @param attr_list
	 */
	protected AbstractLabelElement(String label_name, String label_namespace, boolean is_end_label, AttributeCollection attr_list) {
		this.label = label_name;
		this.label_namespace = label_namespace;
		this.is_end_label = is_end_label;
		this.attr_list = AttributeCollection.optimizedCollection(attr_list);
	}

	/** 设置开始位置的行和列。 */
	void setStartLineCol(int line, int col) {
		this.startLine = line;
		this.startCol = col;
	}
	
	/** 设置结束位置的行和列。 */
	void setEndLineCol(int line, int col) {
		this.endLine = line;
		this.endCol = col;
	}
	
	/**
	 * 获得此标签开始点在原始模板中的行号
	 */
	public int getStartLine() { return this.startLine; }
	
	/**
	 * 获得此标签开始点在原始模板中的列号
	 */
	public int getStartCol() { return this.startCol; }

	/** 获得此标签结束点在原始模板中的行号。 */
	public int getEndLine() { return this.endLine; }
	
	/** 获得此标签结束点在原始模板中的列号。 */
	public int getEndCol() { return this.endCol; }

	/**
	 * 判定是否是一个 LabelElement。
	 * @return
	 */
	@Override public boolean isLabelElement() {
		return true;
	}

	/**
	 * 获得此元素是否已经有了匹配的结束节点。
	 * @return
	 */
	public final boolean hasMatchEndLabel() {
		return this.has_match_end_label;
	}
	
	/**
	 * 获得标签的名字，标签的名字不区分大小写，但总是给出用户所写的大小写模式。
	 * 处理的时候要注意大小写。
	 * 另外如果有命名空间，则总是返回 '标签命名空间:标签' 的格式。
	 * @return
	 */
	public final String getLabelName() {
		if (this.label_namespace == null)
			return this.label;
		else
			return this.label_namespace + ":" + this.label;
	}
	
	/**
	 * 获得标签的命名空间。
	 * @return
	 */
	public final String getLabelNamespace() {
		return this.label_namespace;
	}
	
	/**
	 * 返回不带命名空间的标签名字。
	 * @return
	 */
	public final String getLabelWithoutNamespace() {
		return this.label;
	}
	
	/**
	 * 是否是一个开始标签。开始标签语法为 ${xxx attr-list}。
	 * @return
	 */
	public final boolean isStartLabel() { 
		return !isEndLabel(); 
	}
	
	/**
	 * 是否是一个结束标签。结束标签语法为 ${/xxx}，结束标签也可以有属性列表。
	 * @return
	 */
	public final boolean isEndLabel() { 
		return this.is_end_label;
	}
	
	/**
	 * 获得此标签的属性集合，返回永远不会为 null
	 * 
	 * @return
	 */
	public final AttributeCollection getAttributes() {
		return this.attr_list;
	}

	/**
	 * 将这个标签的所有兄弟元素都转为自己的子元素。
	 * @param match_end_label - 设置是否有了匹配结束标签。
	 */
	final void xferSiblingToChild(boolean match_end_label) {
		super._internalXferSiblingToChild();
		this.has_match_end_label = match_end_label;
	}
}
