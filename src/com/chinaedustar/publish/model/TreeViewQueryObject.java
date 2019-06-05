package com.chinaedustar.publish.model;

/**
 * 树形结构的查询语句对象。
 * 保存了从树形结构的表中查询出结果的 where 与 order 子句。
 * @author wangyi
 *
 */
public class TreeViewQueryObject {
	private String where;
	private String order;
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	
	
}
