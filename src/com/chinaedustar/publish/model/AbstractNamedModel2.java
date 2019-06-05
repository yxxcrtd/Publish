package com.chinaedustar.publish.model;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.pjo.NamedModelBase;

/**
 * 具有命名属性的模型对象基类。
 * @author liujunxing
 *
 */
public class AbstractNamedModel2 <PJO extends NamedModelBase> extends AbstractPublishModelBase {
	/** 实际包装的 PJO 对象。 */
	protected PJO target;
	
	/**
	 * 构造函数。
	 *
	 */
	protected AbstractNamedModel2(PJO target) {
		this.target = target;
	}
	
	/**
	 * 构造函数。
	 * @param target
	 * @param pub_ctxt
	 * @param owner_obj
	 */
	protected AbstractNamedModel2(PJO target, PublishContext pub_ctxt, PublishModelObject owner_obj) {
		this.target = target;
		this._init(pub_ctxt, owner_obj);
	}
	
	/**
	 * 获得被包装的目标对象。
	 * @return
	 */
	public PJO getTargetObject() {
		return this.target;
	}
	
	// === getter/setter ===================================================
	
	/**
	 * 获得此对象的标识。
	 * @return
	 */
	public int getId() {
		return target.getId();
	}

	/**
	 * 等同于 getId, ObjectId 是数据库中的名字。
	 * @return
	 */
	public int getObjectId() {
		return target.getId();
	}

	/**
	 * 获得此对象的唯一标识。
	 * @return
	 */
	public String getUuid() {
		return target.getObjectUuid();
	}
	
	/**
	 * 获得此对象的唯一标识。
	 * @return
	 */
	public String getObjectUuid() {
		return target.getObjectUuid();
	}
	
	/**
	 * 获得此对象的名字。
	 * @return
	 */
	public String getName() {
		return target.getName();
	}

	// === 业务方法 ============================================================
	
	/**
	 * 获得此模型对象的父对象，缺省实现为返回 null，派生类必须自己返回合适的父对象。
	 * @return 返回此对象的父对象。
	 */
	public ModelObject getParent() {
		return null;
	}

	/**
	 * 获得此对象的类型 - 缺省返回类的非限定名字，派生类可以返回特定的名字。
	 * @return getClass().getSimpleName().
	 */
	public String getObjectClass() {
		return getClass().getSimpleName();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ModelObject#getUrlResolver()
	 */
	public UrlResolver getUrlResolver() {
		if (this.getParent() == null)
			return pub_ctxt.getSite();		// Site 一定是一个绝对化器。
		
		// 我们自己不支持，上交给父对象解析。
		return this.getParent().getUrlResolver();
	}
}
