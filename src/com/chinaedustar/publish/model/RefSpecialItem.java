package com.chinaedustar.publish.model;

import java.util.List;
import com.chinaedustar.publish.PublishContext;

/**
 * 简单的对象关系表（专题与项目的关系表）。
 * 专题－项目是多对多的关系，所以创建了这个简单的关系对象。
 * @author wangyi
 *
 */
public class RefSpecialItem {
	/** 发布系统环境对象。 */
	private PublishContext pub_ctxt;

	/** 对象标识。 */
	private int id;
	
	/** 专题的标识。 */
	private int specialId;
	
	/** 项目的标识。 */
	private int itemId;
	
	public RefSpecialItem() {
	}

	public RefSpecialItem(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	public void setPublishContext(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	/** 对象标识。 */
	public int getId() {
		return this.id;
	}
	
	/** 对象标识。 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 得到项目的标识。
	 * @return
	 */
	public int getItemId() {
		return itemId;
	}
	

	/**
	 * 设置项目的标识。
	 * @param itemId
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * 得到专题的标识。
	 * @return
	 */
	public int getSpecialId() {
		return specialId;
	}

	/**
	 * 设置专题的标识。
	 * @param specialId
	 */
	public void setSpecialId(int specialId) {
		this.specialId = specialId;
	}
	
	/**
	 * 根据专题的标识得到关联的对象标识集合。
	 * @param specialId
	 * @return
	 */
	public List<Integer> getItemIds2(int specialId) {
		String hql = "SELECT itemId FROM RefSpecialItem WHERE specialId = " + specialId;
		@SuppressWarnings("unchecked")
		List<Integer> result = pub_ctxt.getDao().list(hql);
		return result;
	}
	
	/**
	 * 删除指定专题的所有专题－项目关联对象。
	 * @param specialId 专题的标识。
	 * @return 
	 */
	public int deleteItems(int specialId) {
		// 删除与项目的关联。
		String hql = "DELETE FROM RefSpecialItem WHERE specialId = " + specialId;
		return pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 得到所有的关联中的所有内容项标识的 distinct 数组。
	 * ?? 给什么地方用 ??, 这样获取数据量很大，不应该要这个需求。
	 * @return
	 */
	public List<Integer> getAllItemIds() {
		String hql = "SELECT distinct itemId FROM RefSpecialItem";
		@SuppressWarnings("unchecked")
		List<Integer> list = pub_ctxt.getDao().list(hql);
		return list;
	}
}
