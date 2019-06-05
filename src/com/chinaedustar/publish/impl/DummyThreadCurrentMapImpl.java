package com.chinaedustar.publish.impl;

import com.chinaedustar.publish.itfc.ObjectDestroyer;
import com.chinaedustar.publish.model.ThreadCurrentMap;

/**
 * 实现一个不存放任何内容的 ThreadCurrentMap.
 * 
 * @author liujunxing
 *
 */
public class DummyThreadCurrentMapImpl extends ThreadCurrentMap {
	/** 唯一实例。 */
	public static final DummyThreadCurrentMapImpl DEFAULT = new DummyThreadCurrentMapImpl();
	
	/** 不从外部实例化。 */
	private DummyThreadCurrentMapImpl() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ThreadCurrentMap#getNamedObject(java.lang.String)
	 */
	@Override public Object getNamedObject(String name) {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ThreadCurrentMap#putNamedObject(java.lang.String, java.lang.Object)
	 */
	@Override public void putNamedObject(String name, Object target) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ThreadCurrentMap#putNamedObject(java.lang.String, java.lang.Object, com.chinaedustar.publish.itfc.ObjectDestroyer)
	 */
	@Override public void putNamedObject(String name, Object target, ObjectDestroyer destroyer) {
		// do nothing
	}

}
