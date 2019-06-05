package com.chinaedustar.publish.model;


/**
 * 树形结构必须的一些数据。
 * 节点路径与排序路径都是36进制表示（0-9,a-z）。
 * 
 * @author wangyi
 *
 */
public class TreeItemObject implements TreeItemInterface {
	/** 树节点的标识。 */
	private int id;
	
	/** 父节点的标识。 */
	private int parentId;
	
	/** 父节点组成的完整路径，根节点的路径为：‘/’ 。 */
	private String parentPath;
	
	/** 父节点到当前节点的排序组成的完整排序路径，根节点的排序路径为：‘./0001/’ 。 */
	private String orderPath;
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.TreeItemInterface#getId()
	 */
	public int getId() {
		return id;
	}
	
	/** 树节点的标识。 */
	public void setId(int id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.TreeItemInterface#getOrderPath()
	 */
	public String getOrderPath() {
		if (orderPath != null) {
			return orderPath.trim();
		} else {
			return null;
		}
	}
	
	public void setOrderPath(String orderPath) {
		this.orderPath = orderPath;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.TreeItemInterface#getParentId()
	 */
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.TreeItemInterface#getParentPath()
	 */
	public String getParentPath() {
		if (parentPath != null) {
			return parentPath.trim();
		} else {
			return null;
		}
	}
	
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
}
