package com.chinaedustar.publish.model;

/**
 * 树形结构中节点的接口。
 * @author wangyi
 *
 */
public interface TreeItemInterface {
	/**
	 * 得到节点的标识。
	 * @return
	 */
	public int getId();
	
	/**
	 * 得到节点的父节点标识，如果已经是根节点，父节点标识为 0 。
	 * @return
	 */
	public int getParentId();
	
	/**
	 * 得到父节点标识的全路径。
	 * @return 父节点组成的完整路径，根节点的路径为：‘/’ 
	 */
	public String getParentPath();
	
	/**
	 * 得到排序的全路径。
	 * @return 父节点到当前节点的排序组成的完整排序路径，根节点的排序路径为：‘./0001/’ 。
	 */
	public String getOrderPath();
}
