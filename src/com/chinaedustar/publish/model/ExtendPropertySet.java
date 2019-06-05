package com.chinaedustar.publish.model;

import java.util.*;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.util.QueryHelper;
import com.chinaedustar.publish.util.UpdateHelper;

/**
 * 扩展属性集合包。
 * 
 * @author liujunxing
 */
public class ExtendPropertySet extends AbstractPublishModelBase implements Iterable<ExtendProperty> {
	/** 发布系统环境对象。 */
	private final PublishContext pub_ctxt;
	
	/** 属性装载状态，= 0 表示未装载过，= 1 表示已经装载了，= 2 表示装载了之后发生了改变。 */
	private int _prop_load_status = 0;
	
	/** 实际的属性集合。 */
	private Hashtable<String, ExtendProperty> prop_map = null;
	
	/** 空的集合。 */
	private static final ArrayList<ExtendProperty> EMPTY_PROP_MAP = 
		new ArrayList<ExtendProperty>();
	
	/**
	 * 使用指定的目标对象和发布系统环境对象构造一个 ExtendPropertySet 的新实例。
	 * @param targetObject
	 * @param pub_ctxt
	 */
	public ExtendPropertySet(ExtendPropertySupport targetObject, PublishContext pub_ctxt) {
		this.owner_obj = targetObject;
		this.pub_ctxt = pub_ctxt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<ExtendProperty> iterator() {
		this.ensureLoad();
		if (prop_map == null) return EMPTY_PROP_MAP.iterator();
		return prop_map.values().iterator();
	}
	
	/**
	 * 获得这个扩展属性集合的拥有者对象，其应该也是 owner_obj.
	 */
	public ExtendPropertySupport getTargetObject() {
		return (ExtendPropertySupport)this.owner_obj;
	}
	
	/**
	 * 确保扩展属性被加载进入内存。
	 */
	@SuppressWarnings("rawtypes")
	private final synchronized void ensureLoad() {
		// 如果已经加载了，则返回。
		if (this._prop_load_status != 0) return;
		
		// 查询 targetObject，其是否有扩展属性；返回 0 则不用加载，并设置已经加载了。
		if (this.getTargetObject().hintPropNum() == 0) {
			this._prop_load_status = 1;
			return;
		}
		
		// 从数据库加载所有扩展属性。
		String hql = "FROM ExtendProperty WHERE targetUuid = '" + this.getTargetObject().getObjectUuid() + 
					 "' ORDER BY id ASC";
		List prop_list = pub_ctxt.getDao().list(hql);
		this._prop_load_status = 1;
		if (prop_list == null || prop_list.size() == 0) return;
		
		// 将扩展属性放到 Hashtable 里面。
		if (this.prop_map == null)
			this.prop_map = new Hashtable<String, ExtendProperty>();
		for (int i = 0; i < prop_list.size(); ++i) {
			ExtendProperty prop = (ExtendProperty)prop_list.get(i);
			prop._init(pub_ctxt, this);
			prop_map.put(prop.getName(), prop);
		}
	}
	
	/**
	 * 获得指定名字的扩展属性。
	 * @param name
	 * @return
	 */
	public ExtendProperty get(String name) {
		ensureLoad();
		if (prop_map == null) return null;
		return prop_map.get(name);
	}
	
	/**
	 * 判断是否指定名字的扩展属性是否存在。
	 * @param name
	 * @return
	 */
	public boolean exist(String name) {
		ensureLoad();
		if (prop_map == null) return false;
		return prop_map.containsKey(name);
	}
	
	/**
	 * 获得扩展属性的数量。
	 * @return
	 */
	public int size() {
		ensureLoad();
		if (prop_map == null) return 0;
		return prop_map.size();
	}

	/**
	 * 获得扩展属性属性。
	 * @return
	 */
	public int getSize() {
		return this.size();
	}
	
	// === 支持动态属性 (dynamic attribute) ======================================
	
	/**
	 * 设置指定名字的属性。
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value) {
		ExtendProperty prop = new ExtendProperty();
		prop.setId(0);
		prop.setPropName(name);
		prop.setValue(value);
		prop.setStatus(-1);			// 临时属性不持久化。
		
		if (this.prop_map == null)
			this.prop_map = new Hashtable<String, ExtendProperty>();
		// 这里可能覆盖已加载进来的持久化属性，不过没关系，此情形应用中少见，并且要避免。
		// ?? 也许可以通过给名字有特定字符来避免这个问题，但现在是否需要？
		this.prop_map.put(name, prop);
	}
	
	/**
	 * 删除指定名字的属性。
	 * @param name
	 */
	public void removeAttribute(String name) {
		if (this.prop_map == null) return;
		this.prop_map.remove(name);
	}

	// === 业务方法 =============================================================
	
	/**
	 * 创建或更新这个对象的一个扩展属性。
	 */
	public void saveProperty(ExtendProperty prop) {
		// 1. 查找此属性名是否已经有了。
		ExtendProperty old_prop = getNamedProperty(prop.getPropName());
		if (old_prop == null) {
			// 新建一个。
			prop.setId(0);
			prop.setTargetUuid(getTargetObject().getObjectUuid());
			prop.setTargetClass(getTargetObject().getObjectClass());
			
			pub_ctxt.getDao().save(prop);
		} else {
			// 修改
			old_prop.setPropType(prop.getPropType());
			old_prop.setPropValue(prop.getPropValue());
			
			pub_ctxt.getDao().update(old_prop);
		}
		pub_ctxt.getDao().flush();
		
		// 重新计算对象的属性数量，并通告对象。
		recalcPropNum();
	}
	
	/**
	 * 删除指定名字的扩展属性。
	 * @param prop_name
	 */
	public void deleteProperty(String prop_name) {
		// 删除此属性。
		UpdateHelper updator = new UpdateHelper();
		updator.updateClause = "DELETE FROM ExtendProperty WHERE targetUuid = :targetUuid AND propName = :propName";
		updator.setString("targetUuid", this.getTargetObject().getObjectUuid());
		updator.setString("propName", prop_name);
		int update_num = updator.executeUpdate(pub_ctxt.getDao());
		
		// 重新计算属性数量。
		if (update_num != 0)
			recalcPropNum();
	}
	
	/**
	 * 删除所有扩展属性。
	 *
	 */
	public void deleteAllProperty() {
		// 删除此属性。
		UpdateHelper updator = new UpdateHelper();
		updator.updateClause = "DELETE FROM ExtendProperty WHERE targetUuid = :targetUuid";
		updator.setString("targetUuid", this.getTargetObject().getObjectUuid());
		int update_num = updator.executeUpdate(pub_ctxt.getDao());
		
		// 重新计算属性数量。
		if (update_num != 0)
			recalcPropNum();
	}
	
	/**
	 * 更新所有发生变化的扩展属性。
	 *
	 */
	public void update() {
		if (this.prop_map == null || this.prop_map.size() == 0) return;
		Iterator<ExtendProperty> iter = this.prop_map.values().iterator();
		while (iter.hasNext()) {
			ExtendProperty prop = iter.next();
			if (prop._getChanged() == false) continue;
			pub_ctxt.getDao().update(prop);
		}

		// 重新计算对象的属性数量，并通告对象。
		recalcPropNum();
		
		pub_ctxt.getDao().flush();
	}
	
	// 重新计算对象的属性数量，并通告对象。
	private void recalcPropNum() {
		String hql = "SELECT COUNT(*) FROM ExtendProperty WHERE targetUuid = '" + getTargetObject().getObjectUuid() + "'";
		int prop_num = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		getTargetObject().propNumChanged(prop_num);
	}
	
	// 查找指定属性名的属性。
	private ExtendProperty getNamedProperty(String propName) {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM ExtendProperty ";
		query.whereClause = " WHERE targetUuid = :uuid AND propName = :name";
		query.setString("uuid", getTargetObject().getObjectUuid());
		query.setString("name", propName);
		Object result = query.querySingleData(pub_ctxt.getDao());
		return (ExtendProperty)result;
	}

	/**
	 * 删除指定支持扩展属性的对象的所有扩展属性。
	 * @param dao
	 * @param obj
	 */
	public static final void deleteObjectExtends(DataAccessObject dao, ExtendPropertySupport obj) {
		String hql = "DELETE FROM ExtendProperty WHERE targetUuid = '" + obj.getObjectUuid() + "'";
		dao.bulkUpdate(hql);
	}
}

