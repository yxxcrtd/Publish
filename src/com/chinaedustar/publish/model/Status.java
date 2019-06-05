package com.chinaedustar.publish.model;

/**
 * 一般状态类，这个类用于提供用户友好的状态显示，而不是简单显示一个数字。
 * 
 * @author liujunxing
 */
public class Status {
	protected final int status_code;
	protected final String status_desc;
	
	public Status(int status_code, String status_desc) {
		this.status_code = status_code;
		this.status_desc = status_desc;
	}
	
	public String toString() {
		return "Status{" + status_desc + ",code=" + this.status_code + "}";
	}
	
	public int getCode() {
		return this.status_code;
	}
	
	public String getDesc() {
		return this.status_desc;
	}
}
