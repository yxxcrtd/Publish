package com.chinaedustar.publish.comp;

import java.util.Iterator;

import com.chinaedustar.publish.model.DataSchema;
import com.chinaedustar.publish.model.DataTable;

@SuppressWarnings("rawtypes")
public class PagedDataTable extends DataTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6913447900512786002L;

	public PagedDataTable(DataSchema schema) {
		super(schema);
	}
	
	public PagedDataTable(DataSchema schema, PagedArrayList list) {
		super(schema, list);
		this.totalCount = list.getTotalCount();
		this.currPage = list.getCurrPage();
		this.pageSize = list.getPageSize();
	}
	
	/** 记录总数。 */
	private int totalCount = 0;
	
	/** 当前页编号。 */
	private int currPage = 1;
	
	/** 页大小。 */
	private int pageSize = 20;

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

	/**
	 * 获得所有页面的枚举范围。
	 * @return
	 */
	public Iterator getPages() {
		return new PageRangeIterator();
	}
	
	// 
	private final class PageRangeIterator implements Iterator {
		private int this_page = 1;
		private int page_count;
		public PageRangeIterator() {
			page_count = getPageCount();
		}
		public boolean hasNext() { return (this_page <= page_count); }
		public Integer next() { return this_page++; }
		public void remove() { throw new UnsupportedOperationException(); }
	}
}
