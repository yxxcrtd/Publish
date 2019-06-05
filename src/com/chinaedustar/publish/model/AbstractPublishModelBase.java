package com.chinaedustar.publish.model;

import com.chinaedustar.publish.PublishContext;

/**
 * 辅助实现 PublishModelObject 的抽象基类。
 * 
 * @author liujunxing
 */
public abstract class AbstractPublishModelBase implements PublishModelObject {
	/** 用于 get(key) 函数的返回，表示不存在该键对应的值。 */
	public static final Object UNEXIST = com.chinaedustar.rtda.simple.NullData.UNEXIST;

	// === PublishModelObject 接口实现 ========================================
	
	/** 当前此模型对象绑定的环境对象。 */
	protected transient PublishContext pub_ctxt;
	
	/** 此对象的拥有者对象。 */
	protected transient PublishModelObject owner_obj;

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.PublishModelObject#_getPublishContext()
	 */
	public PublishContext _getPublishContext() {
		return this.pub_ctxt;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.PublishModelObject#_getOwnerObject()
	 */
	public PublishModelObject _getOwnerObject() {
		return this.owner_obj;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.PublishModelObject#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.itfc.PublishModelObject)
	 */
	public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		this.pub_ctxt = pub_ctxt;
		this.owner_obj = owner_obj;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.PublishModelObject#_destroy()
	 */
	public void _destroy() {
		this.owner_obj = null;
		this.pub_ctxt = null;
	}
}
