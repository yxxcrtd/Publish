package com.chinaedustar.publish.model;

public class UserSimpleInfo {
	/** 项目名称 */
	private String itemName;
	/** 项目单位 */
	private String itemUnit;
	/** 项目的值 */
	private String itemValue;
	
	/**
	 * 获取项目名称
	 * @return
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * 设置项目名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * 设置项目单位
	 * @return
	 */
	public String getItemUnit() {
		return itemUnit;
	}
	/**
	 * 获取项目单位
	 * @param itemUnit
	 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	/**
	 * 获取项目的值
	 * @return
	 */
	public String getItemValue() {
		return itemValue;
	}
	/**
	 * 设置项目的值
	 * @param itemValue
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
}
