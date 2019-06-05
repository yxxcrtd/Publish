package com.chinaedustar.rtda.model;

import com.chinaedustar.rtda.ObjectWrapper;

/**
 * 定义对一个数据产生修饰的接口，真实对象包装在此对象里面。
 * 
 * 当访问这个数据的时候，实际使用原始数据。而当调用函数等操作时，如果原对象
 *   没有提供访问功能，则修饰器代为提供。
 * 
 * @author liujunxing
 */
public interface DataDecorator  {
	/**
	 * 获得被包装的原始对象。
	 * @return
	 */
	public Object _getOriginObject();
	
	/**
	 * 使用指定的对象包装器包装内部原始对象并返回，修饰器有机会缓存被包装之后的对象。
	 * @param wrapper - 对象包装器。
	 * @return
	 */
	public DataModel _wrapObject(ObjectWrapper wrapper);
}
