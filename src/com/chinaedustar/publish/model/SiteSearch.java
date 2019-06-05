package com.chinaedustar.publish.model;

/**
 * 表示一个搜索对象。
 * 
 * @author liujunxing
 *
 */
public class SiteSearch {
	/** 要搜索的模块类型。 */
	private int moduleId = 0;
	
	/** 要搜索的关键字。 */
	private String keyword = "";
	
	/**
	 * 构造函数。
	 *
	 */
	public SiteSearch() {
		
	}
	
	/** 要搜索的模块类型。 */
	public int getModuleId() {
		return this.moduleId;
	}
	
	/** 要搜索的模块类型。 */
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	
	/** 要搜索的关键字。 */
	public String getKeyword() {
		return this.keyword;
	}
	
	/** 要搜索的关键字。 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
