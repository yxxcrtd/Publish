package com.chinaedustar.publish.model;

/**
 * 研究所对象
 * 
 * @author Yang XinXin
 */
public class Institute extends AbstractModelBase {
	
	/**
	 * 研究所Id
	 */
	private int cid;
	
	/**
	 * 研究所名称
	 */
	private String title;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
