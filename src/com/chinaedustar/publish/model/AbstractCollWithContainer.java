package com.chinaedustar.publish.model;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.itfc.ChannelContainer;

/**
 * 具有容器对象的集合类实现。
 * 
 * @author liujunxing
 * 提供给 AuthorCollection, KeywordCollection 等做为基类。
 */
public abstract class AbstractCollWithContainer extends AbstractPublishModelBase {
	/**
	 * 构造。
	 */
	protected AbstractCollWithContainer() {
		
	}
	
	/** 此对象的容器对象。 */
	protected ChannelContainer container;
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.model.PublishModelObject)
	 */
	@Override public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super._init(pub_ctxt, owner_obj);
		if (owner_obj instanceof ChannelContainer)
			this.container = (ChannelContainer)owner_obj;
	}
	
	/**
	 * 获得容器对象。
	 * @return
	 */
	public ChannelContainer getContainer() {
		return this.container;
	}
	
	/**
	 * 设置容器对象。
	 * @param container
	 */
	public void setContainer(ChannelContainer container) {
		this.container = container;
	}
}
