package com.chinaedustar.publish.model;

/**
 * 模板类型对象。
 * 
 * @author liujunxing
 */
public class TemplateType extends AbstractNamedModelBase {
	/** 此模板类型的显示名，如内容页模板。 */
	private String title;
	
	/** 此模板类型所属的组的标识。引用到 TemplateGroup 对象。 */
	private int groupId;
	
	/** 在这个组中的排序。 */
	private int groupOrder;	
	
	/**
	 * 缺省构造函数。
	 *
	 */
	public TemplateType() {
		
	}

	// === 业务方法 =============================================================
	
	
	
	// === getter, setter =====================================================
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getGroupId() {
		return this.groupId;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public int getGroupOrder() {
		return this.groupOrder;
	}
	
	public void setGroupOrder(int groupOrder) {
		this.groupOrder = groupOrder;
	}
}
