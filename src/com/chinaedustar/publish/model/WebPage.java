package com.chinaedustar.publish.model;

import com.chinaedustar.publish.*;

/**
 * 表示一个独立网站页面。
 * 
 * @author liujunxing
 *
 */
public class WebPage extends AbstractPageModelBase implements PublishModelObject, TreeItemInterface {
	/** 所属频道标识，= 0 表示属于整个网站。 */
	private int channelId;
	
	/** 父页面标识， = 0 表示没有父页面。 */
	private int parentId;
	
	/** 树形结构的父节点全路径，格式为 /1/12/ 。 */
	private String parentPath;
	
	/** 树形结构的排序全路径，各式为 ./0001/0010/ 。 */
	private String orderPath;
	
	/** 页面标题。 */
	private String title;
	
	/** 页面的简要提示，不支持html和标签。 */
	private String tips;
	
	/** 页面的详细说明，支持html和标签。 */
	private String description;

	/** 打开方式：1：在新窗口打开；0：在原窗口打开。 */
	private int openType;

	/** 自定义属性数量。 */
	private int customNum; 
	
	/** 模型对象中的父对象。 */
	private ModelObject parent;
	
	/**
	 * 使用缺省参数构造一个 WebPage 的新实例。
	 *
	 */
	public WebPage() {
		
	}

	// === getter, setter =====================================================
	
	/**
	 * 返回所属的频道标识。
	 */
	public int getChannelId () {
		return channelId;
	}

	/**
	 * Set 所属的频道标识。外键关联到Cor_Channel表。
	 * @param channelId the ChannelId value
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * Return 父页面的标识。如果没有父页面，则此值为 0 。
	 */
	public int getParentId () {
		return parentId;
	}

	/**
	 * Set 父页面的标识。如果没有父页面，则此值为 0 。
	 * @param parentId the ParentId value
	 */
	public void setParentId (int parentId) {
		this.parentId = parentId;
	}

	/**
	 * Return 树形结构的父节点全路径，格式为 /1/12/ 。
	 */
	public String getParentPath () {
		return this.parentPath;
	}
	
	/**
	 * 获得此页面的深度，通过 parentPath(/1/12/) 可以方便的计算出深度。根页面深度 = 0。
	 * @return
	 */
	public int getDepth() {
		return TreeViewModel.calcPathDepth(parentPath);
	}

	/**
	 * Set 树形结构的父节点全路径，格式为 /1/12/ 。
	 * @param parentPath the ParentPath value
	 */
	public void setParentPath (String parentPath) {
		this.parentPath = parentPath;
	}

	/**
	 * Return 树形结构的排序全路径，各式为 ./0001/0010/ 。
	 */
	public String getOrderPath () {
		return orderPath;
	}

	/**
	 * Set 树形结构的排序全路径，各式为 ./0001/0010/ 。
	 * @param orderPath the OrderPath value
	 */
	public void setOrderPath (String orderPath) {
		this.orderPath = orderPath;
	}


	/** 页面标题。 */
	public String getTitle() {
		return this.title;
	}
	
	/** 页面标题。 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return 页面的简要提示，不支持html和标签。
	 */
	public String getTips () {
		return tips;
	}

	/**
	 * Set 页面的简要提示，不支持html和标签。
	 * @param tips the Tips value
	 */
	public void setTips (java.lang.String tips) {
		this.tips = tips;
	}

	/**
	 * Return 页面的详细说明，支持html和标签。
	 */
	public String getDescription () {
		return description;
	}

	/**
	 * Set 页面的详细说明，支持html和标签。
	 * @param description the Description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}

	/**
	 * 打开方式：1：在新窗口打开；0：在原窗口打开。
	 * @return
	 */
	public int getOpenType() {
		return openType;
	}
	
	/**
	 * 打开方式：1：在新窗口打开；0：在原窗口打开。
	 * @param openType
	 */
	public void setOpenType(int openType) {
		this.openType = openType;
	}

	/** 自定义属性数量。 */
	public int getCustomNum() {
		return this.customNum;
	}
	
	/** 自定义属性数量。 */
	public void setCustomNum(int customNum) {
		this.customNum = customNum;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractModelBase#getParent()
	 */
	@Override public ModelObject getParent() {
		return this.parent;
	}
	
	/**
	 * 设置这个对象的父对象。
	 * @param parent
	 */
	public void setParent(ModelObject parent) {
		this.parent = parent;
	}
	
	// === override ==========================================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#getPageUrl()
	 */
	@Override public String calcPageUrl() {
		if (super.getIsGenerated())
			return pub_ctxt.getSite().resolveUrl(super.getStaticPageUrl());
		return pub_ctxt.getSite().resolveUrl("showWebpage.jsp?id=" + super.getId());
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#getNewStaticPageUrl(com.chinaedustar.publish.PublishContext)
	 */
	@Override protected String getNewStaticPageUrl(PublishContext pub_ctxt) {
		// TODO: 也许使用一个字段保存更好。
		return this.getName() + ".html";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.ExtendPropertySupport#hintPropNum()
	 */
	@Override public int hintPropNum() {
		return this.customNum;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#propNumChanged(int)
	 */
	@Override public void propNumChanged(int num) {
		this.customNum = num;
		String hql = "UPDATE WebPage SET customNum = " + customNum + " WHERE id = " + this.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
		super.propNumChanged(num);
	}
}
