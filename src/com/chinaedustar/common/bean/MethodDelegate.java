package com.chinaedustar.common.bean;

import java.lang.reflect.InvocationTargetException;

/**
 * 定义能够封装 generic-get, property, index-property, method, overloaded-method
 * 的统一调用接口。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public interface MethodDelegate {
	/** 类型定义： generic-get */
	public static final int METHOD_TYPE_GENERIC_GET = 0;
	
	/** 类型定义： property */
	public static final int METHOD_TYPE_PROPERTY = 1;
	
	/** 类型定义： indexed-property */
	public static final int METHOD_TYPE_INDEXED_PROPERTY = 2;
	
	/** 类型定义： method */
	public static final int METHOD_TYPE_METHOD = 3;
	
	/** 类型定义： overloaded-method */
	public static final int METHOD_TYPE_OVERLOADED_METHOD = 4;

	/**
	 * 返回方法类型，为上面定义的几种类型之一。
	 * @return
	 */
	public int getMethodType();
	
	/** 
	 * 使用指定的参数在这个方法上产生 get 调用，对于 generic-get, property, 
	 *   indexed-property, method, overloaded-method 都适用。
	 * @param target - 调用目标对象。
	 * @param args - 参数。
	 * @exception InvocationTargetException - 请参考 Method.invoke() 的异常说明。
	 * @exception IllegalAccessException - 请参考 Method.invoke() 的异常说明。
	 * @exception RuntimeException - 如果找不到合适的重载函数形式会抛出这种异常。
	 */
	public Object invoke(Object target, Object... args) 
		throws InvocationTargetException, IllegalAccessException;
	
	/**
	 * 获得此方法或属性的名字。某些类型的方法可能没有名字。
	 * @return
	 */
	public String getName();
	
	/**
	 * 获得方法调用的返回类型。
	 * @return
	 */
	public Class getReturnType();
	
	/**
	 * 使用指定的参数在这个方法上产生 set 调用，对于 property, indexed-property
	 *   适用。其它类型方法不能用。
	 * @param target - 调用目标对象。
	 * @param args - 参数。
	 * @return
	 */
	/* public Object invokeSet(Object target, Object... args) 
		throws InvocationTargetException, IllegalAccessException; */
}
