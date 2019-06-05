package com.chinaedustar.publish.model;

/**
 * 模块状态。
 * 
 * @author liujunxing
 */
public final class ModuleStatus extends Status {
	public ModuleStatus(int status_code, String status_desc) {
		super(status_code, status_desc);
	}
	
	public String toString() {
		return "{模块状态: " + super.status_desc + ", 状态代码: " + super.status_code + "}";
	}
}
