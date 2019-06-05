package com.chinaedustar.publish.model;

/**
 * 管理员权限类型对象，不属于发布系统业务模型对象，只是用来进行配置管理员权限使用。
 * @author wangyi
 *
 */
public class AdminRightType {
	/** 管理员权限的类型标识。 */
	private int id;
	
	/** 管理员权限的类型名称。 */
	private String rightType;
	
	/** 管理员权限类型所属的权限模块。 */
	private String rightModuleName;
	
	/** 管理员权限类型对应的页面。 */
	private String actionTypes;
	
	/** 管理员权限类型的描述信息。 */
	private String description;
	
	/**
	 * 管理员权限类型的描述信息。
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 管理员权限类型的描述信息。
	 * @param description 要设置的 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 管理员权限的类型标识。
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 管理员权限的类型标识。
	 * @param id 要设置的 id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 管理员权限类型对应的页面。
	 * @return pages
	 */
	public String getActionTypes() {
		return actionTypes;
	}
	/**
	 * 管理员权限类型对应的页面。
	 * @param actionType 要设置的 actionType
	 */
	public void setActionTypes(String actionTypes) {
		this.actionTypes = actionTypes;
	}
	/**
	 * 管理员权限类型所属的权限模块。
	 * @return rightModuleName
	 */
	public String getRightModuleName() {
		return rightModuleName;
	}
	/**
	 * 管理员权限类型所属的权限模块。
	 * @param rightModuleName 要设置的 rightModuleName
	 */
	public void setRightModuleName(String rightModuleName) {
		this.rightModuleName = rightModuleName;
	}
	/**
	 * 管理员权限的类型名称。
	 * @return rightType
	 */
	public String getRightType() {
		return rightType;
	}
	/**
	 * 管理员权限的类型名称。
	 * @param rightType 要设置的 rightType
	 */
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	
}
