package com.chinaedustar.publish.model;

/**
 * 班级对象
 * 
 * @author Yang XinXin
 */
public class Classs extends AbstractModelBase {
	
	/**
	 * 班级Id
	 */
	private int cid;
	
	/**
	 * 班级名称
	 */
	private String cname;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
