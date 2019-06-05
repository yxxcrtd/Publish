package com.chinaedustar.publish.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * 表示获取数据的分页信息
 */
public class PaginationInfo implements Cloneable {
	
	/** 当前所在的页数 */
	private int currPage;
	
	/** 当前每页显示的项目数目 */
	private int pageSize;
	
	/** 当前的项目名称 */
	private String itemName;
	
	/** 当前的项目单位 */
	private String itemUnit;
	
	/** 总记录数 */
	private long totalCount;
	
	/** 是否支持分页标签 #PageNav */
	private boolean usePage;

	/** 页面的Url Pattern, 内部的 '{page}' 将被替换为当时的页次。 */
	private String url_pattern = "";
	
	private String firstPageUrl;
	private String prevPageUrl;
	private String nextPageUrl;
	private String lastPageUrl;
	
	private String digitalPageUrl;
	/**
	 * 默认构造函数。
	 */
	public PaginationInfo() {
		this.currPage = 1;
		this.pageSize = 20;
	}
	
	/**
	 * 此方法主要用于在静态化引擎生成列表页面时使用。
	 * @param currPage 当前所在页面。
	 */
	public PaginationInfo(int currPage) {
		this.currPage = currPage;
		this.pageSize = 20;
	}
	
	public PaginationInfo(int currPage, int pageSize) {
		this.currPage = currPage <= 0 ? 1 : currPage;
		this.pageSize = pageSize <= 0 ? 20 : pageSize;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString() {
		return "PaginationInfo{currPage = " + currPage + ", pageSize = " + pageSize + ", totalCount = " + totalCount + "}";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override public PaginationInfo clone() {
		PaginationInfo page_info = new PaginationInfo();
		page_info.currPage = this.currPage;
		page_info.pageSize = this.pageSize;
		page_info.itemName = this.itemName;
		page_info.itemUnit = this.itemUnit;
		page_info.totalCount = this.totalCount;
		page_info.usePage = this.usePage;
		page_info.url_pattern = this.url_pattern;
		page_info.firstPageUrl = this.firstPageUrl;
		page_info.prevPageUrl = this.prevPageUrl;
		page_info.nextPageUrl = this.nextPageUrl;
		page_info.lastPageUrl = this.lastPageUrl;
		page_info.digitalPageUrl=this.digitalPageUrl; 
		return page_info;
	}
	
	
	/**
	 * 获取当前所在的页数
	 * @return 
	 */
	public int getCurrPage() {
		return currPage;
	}
	
	
	/**
	 * 设置当前所在的页数
	 * @param currPage
	 */
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	public String getDigitalPageUrl(){
		String showPrePage="...&nbsp;";
		String showNextPage="...";
		int showMaxPage=1;
		int showMinPage=1;
		String sUrlPage="";
		int i=0;
		
		if(this.pageSize<=0) {
			this.digitalPageUrl="";
			return "";
		}
		int totalPage = (int)(this.totalCount / this.pageSize);
		if (this.totalCount % this.pageSize > 0)
			++totalPage;
		
		if(totalPage<=5)
		{
			for(i=1;i<=totalPage;i++)
			{
				sUrlPage=sUrlPage+"<a href=?page="+ i +">"+i+"</a>&nbsp;&nbsp;";	
			}
			this.digitalPageUrl=sUrlPage;
		}
		else
		{
			if(currPage<4)
			{
				showMinPage=1;	
				showPrePage="";
			}
			else
			{
				showMinPage=currPage-3;
			}
			if((totalPage-currPage)>4)
			{
				showMaxPage=currPage+3;
			}
			else
			{
				showNextPage="";
				showMaxPage=totalPage;
			}
			for(i=showMinPage;i<=showMaxPage;i++)
			{
				sUrlPage=sUrlPage+"<a href=?page="+ i +">"+i+"</a>&nbsp;&nbsp;";	
			}
			this.digitalPageUrl=showPrePage+sUrlPage+showNextPage;
		}		
		return this.digitalPageUrl;
	}
	public void setDigitalPageUrl(String Nothing){
		//return this.digitalPageUrl=Nothing;
	}
	
	/**
	 * 获取当前的项目单位
	 * @return
	 */
	public String getItemUnit() {
		return itemUnit;
	}
	
	/**
	 * 设置当前的项目名称
	 * @param itemName
	 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
	/**
	 * 获取当前的项目名称
	 * @return
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * 设置当前的项目名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/**
	 * 获取当前每页显示的项目数目
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置当前每页显示的项目数目
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取总记录数
	 * @return
	 */
	public long getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 设置总记录数
	 * @param totalNum
	 */
	public void setTotalCount(long totalNum) {
		this.totalCount = totalNum;
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getTotalPage() {
		if (this.pageSize <= 0) return 0;
		int totalPage = (int)(this.totalCount / this.pageSize);
		if (this.totalCount % this.pageSize > 0)
			++totalPage;

		return totalPage;
	}
	
	/**
	 * 获取页面的UrlPattern, 例如 'list_{page}.html'
	 * @return
	 */
	public String getUrlPattern() {
		return url_pattern;
	}
	
	/**
	 * 设置页面的Url。比如：'list_{page}.html'
	 * @param url
	 */
	public void setUrlPattern(String url) {
		this.url_pattern = url;
	}
	
	/**
	 * 获取第一页的链接地址。
	 * @return
	 */
	public String getFirstPageUrl() {
		if (this.firstPageUrl == null || this.firstPageUrl.length() == 0)
			return this.internalGetPageUrl(1);
		return firstPageUrl;
	}
	
	/**
	 * 设置第一页的链接地址。
	 * @param firstPageUrl
	 */
	public void setFirstPageUrl(String firstPageUrl) {
		this.firstPageUrl = firstPageUrl;
	}
	
	/**
	 * 获取上一页的链接地址。
	 * @return
	 */
	public String getLastPageUrl() {
		if (this.lastPageUrl == null || this.lastPageUrl.length() == 0)
			return this.internalGetPageUrl(getTotalPage());
		return lastPageUrl;
	}
	
	/**
	 * 设置上一页的链接地址。
	 * @param firstPageUrl
	 */
	public void setLastPageUrl(String lastPageUrl) {
		this.lastPageUrl = lastPageUrl;
	}
	
	/**
	 * 获取下一页的链接地址。
	 * @return
	 */
	public String getNextPageUrl() {
		if (this.nextPageUrl == null || this.nextPageUrl.length() == 0)
			return this.internalGetPageUrl(currPage + 1);
		return nextPageUrl;
	}
	
	/**
	 * 获取下一页的链接地址。
	 * @param firstPageUrl
	 */
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	
	/**
	 * 获取最后一页的链接地址。
	 * @return
	 */
	public String getPrevPageUrl() {
		if (this.prevPageUrl == null || this.prevPageUrl.length() == 0)
			return this.internalGetPageUrl(currPage - 1);
		return prevPageUrl;
	}
	
	/**
	 * 获取最后一页的链接地址。
	 * @param firstPageUrl
	 */
	public void setPrevPageUrl(String prevPageUrl) {
		this.prevPageUrl = prevPageUrl;
	}
	
	//********************
	/**
	 * 完成初始化（包括链接地址和总页数的计算）
	 * 注意：此方法只有在参数（totalCount,currPage,pageSize）都已经初始化完毕才可以调用，否则会产生错误。
	 */
	public void init() {
		// 对每页显示个数、总页数、当前页数进行合法化处理。
		if (this.totalCount < 0) {
			this.totalCount = 0;
		}
		if (this.pageSize <= 0) {
			this.pageSize = 20;
		}
		if (this.currPage <= 0) {
			this.currPage = 1;
		}
		if (this.currPage > this.getTotalPage()) {
			this.currPage = this.getTotalPage();
		}
		
		// 对页面地址进行处理
		// TODO 列表页的地址问题尚需进一步考虑周全
		if (this.url_pattern == null || this.url_pattern.trim().length() < 1)
			this.url_pattern = "?page={page}";
	}
	
	/**
	 * 附加地址到URL
	 * @param addConnectChar 是否添加链接符号（&）
	 * @param key 参数名称
	 * @param value 参数值
	 * @param encoding 编码
	 */
	public void appendParam2Url(boolean addConnectChar, String key, String value, String encoding) {
		this.url_pattern += (addConnectChar ? "&" : "") + key + "=";
		if (null == encoding) {
			this.url_pattern += value;
		} else {
			try {
				this.url_pattern += URLEncoder.encode(value, encoding);
			} catch (UnsupportedEncodingException e) {
				this.url_pattern += value;
			}
		}
	}	

	/**
	 * 获得指定页号的地址。
	 * @param page
	 * @return
	 */
	protected String internalGetPageUrl(int page) {
		if (page <= 1) {
			if (this.firstPageUrl != null && this.firstPageUrl.length() > 0)
				return this.firstPageUrl;
			page = 1;
		}

		if (page >= getTotalPage())
			page = getTotalPage();

		if (this.url_pattern == null) return "";
		return this.url_pattern.replace("{page}", String.valueOf(page));
	}
	
	/** 表示一个页面项，其有页号、地址。 */
	public class PageItem {
		/** 当前页号。 */
		private int page;
		
		public PageItem(int page) { this.page = page; }
		
		/**
		 * 获得当前页号。
		 * @return
		 */
		public int getPage() {
			return this.page;
		}
		
		/**
		 * 获得当前页的 url 地址。
		 * @return
		 */
		public String getUrl() {
			return internalGetPageUrl(page);
		}
	
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override public String toString() {
			return "Page{page=" + page + ",url=" + getUrl() + "}";
		}
	}

	/**
	 * 获得这个分页对象的所有页面集合，该集合是一个只读集合。
	 * @return
	 */
	public Collection<PageItem> getPages() {
		return new PagesColl();
	}
	
	/** 实现只读 Collection 接口的 Page 集合。 */
	private class PagesColl extends AbstractCollection<PageItem> {
		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractCollection#size()
		 * 我们返回页面总数。
		 */
		@Override public int size() {
			return PaginationInfo.this.getTotalPage();
		}

		@Override public Iterator<PageItem> iterator() {
			return new IteratorImpl();
		}
		@Override public boolean add(PageItem o) {
			throw new UnsupportedOperationException("只读集合不能添加项目");
		}
		@Override public boolean remove(Object o) {
			throw new UnsupportedOperationException("只读集合不能删除项目");
		}
	}
	
	/** PagesColl 的枚举器实现。 */
	private class IteratorImpl implements Iterator<PageItem> {
		int page = 1;
		public boolean hasNext() {
			return (page <= getTotalPage());
		}

		public PageItem next() {
			return new PageItem(page++);
		}

		public void remove() {
			throw new UnsupportedOperationException("只读集合不能删除项目");
		}
	}

	/** 是否支持分页标签 #PageNav */
	public boolean getUsePage() {
		return usePage;
	}

	/** 是否支持分页标签 #PageNav */
	public void setUsePage(boolean usePage) {
		this.usePage = usePage;
	}
}
