package com.chinaedustar.rtda.wrapper;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.bean.BeanObjectWrapper;


/**
 * 缺省对象包装器的实现。
 * 
 * @author liujunxing
 *
 */
public class DefaultObjectWrapper extends BeanObjectWrapper implements ObjectWrapper {
	/** 缺省的实例。 */
	private static final DefaultObjectWrapper instance = new DefaultObjectWrapper();
	
	/**
	 * 获得 DefaultObjectWrapper 的缺省实例。
	 * @return
	 */
	public static DefaultObjectWrapper getInstance() { return instance; }
}
