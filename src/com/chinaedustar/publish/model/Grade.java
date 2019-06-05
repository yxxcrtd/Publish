package com.chinaedustar.publish.model;

/**
 * 年级对象
 * 
 * @author Yang XinXin
 */
public class Grade extends AbstractModelBase {
	
	/**
	 * 年级Id
	 */
	private int gid;
	
	/**
	 * 年级名称
	 */
	private String gname;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}
	
}
