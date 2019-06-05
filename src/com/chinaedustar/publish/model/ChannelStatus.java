package com.chinaedustar.publish.model;

/**
 * 表示频道的内存状态。
 * 
 * @author liujunxing
 */
public class ChannelStatus extends Status {
	public ChannelStatus(int status_code, String status_desc) {
		super(status_code, status_desc);
	}
	
	@Override public String toString() {
		return "{频道状态:" + super.status_desc + ", 状态代码:" + status_code + "}";
	}
}
