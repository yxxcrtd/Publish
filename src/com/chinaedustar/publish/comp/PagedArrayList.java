package com.chinaedustar.publish.comp;

import java.util.ArrayList;

/**
 * 带有分页信息的 ArrayList，用于支持在页面上面实现分页处理。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class PagedArrayList extends ArrayList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 267339599631539948L;

	/** 记录总数。 */
	private int totalCount = 0;
	
	/** 当前页编号。 */
	private int currPage = 1;
	
	/** 页大小。 */
	private int pageSize = 20;
	
	public PagedArrayList() {
		
	}
	
	@SuppressWarnings("unchecked")
	public PagedArrayList(java.util.List list) {
		super.addAll(list);
	}

	@SuppressWarnings("unchecked")
	public PagedArrayList(java.util.List list, int totalCount, int currPage, int pageSize) {
		super.addAll(list);
		this.totalCount = totalCount;
		this.currPage = currPage;
		this.pageSize = pageSize;
	}

	/**
	 * 获得记录总数。
	 * @return
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 获得当前页编号，编号从 1 开始计算。
	 * @return
	 */
	public int getCurrPage() {
		return this.currPage;
	}
	
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	/**
	 * 获得每页记录数量。
	 * @return
	 */
	public int getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 获得总的页面数量。
	 * @return
	 */
	public int getPageCount() {
		int pageCount = this.totalCount / this.pageSize;
		return (this.totalCount % this.pageSize) == 0 ? pageCount : pageCount + 1;
	}
	
	/**
	 * 获得是否是第一页。
	 * @return
	 */
	public boolean getIsFirstPage() {
		return (this.currPage == 1);
	}
	
	/**
	 * 获得是否是最后一页。
	 * @return
	 */
	public boolean getIsLastPage() {
		return (this.currPage == getPageCount());
	}
}
