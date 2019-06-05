package com.chinaedustar.publish.model;

/**
 * 用户自定义标签。
 * 
 * @author liujunxing
 */
public class Label extends AbstractNamedModelBase {
	/**
	 * 缺省构造函数。
	 *
	 */
	public Label() {
		
	}
	
	/** 标签类型，0 为用户，1 为系统内建，2 为学习。。 */
	private int labelType;
	
	/** 标签类型。当前动易有 0-简单文字；1-静态；2-动态；3-函数；4-采集。我们当前先仅支持0，1静态的。 */
	// private int type;
	
	/** 标签的详细说明。 */
	private String description;
	
	/** 标签优先级，暂时未使用。 */
	private int priority;
	
	/** 标签的内容。 */
	private String content;
	
	/** 是否删除标志。 */
	private boolean deleted;
	
	// === getter, setter =======================================================
	
	/** 标签类型，0 为用户，1 为系统内建，2 为学习。。 */
	public int getLabelType() {
		return this.labelType;
	}
	
	/** 标签类型，0 为用户，1 为系统内建，2 为学习。。 */
	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}
	
	/** 标签的详细说明。 */
	public String getDescription() {
		return this.description;
	}
	
	/** 标签的详细说明。 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 标签优先级，暂时未使用。 */
	public int getPriority() {
		return this.priority;
	}
	
	/** 标签优先级，暂时未使用。 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/** 标签的内容。 */
	public String getContent() {
		return this.content;
	}
	
	/** 标签的内容。 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/** 是否删除标志。 */
	public boolean getDeleted() {
		return this.deleted;
	}
	
	/** 是否删除标志。 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
