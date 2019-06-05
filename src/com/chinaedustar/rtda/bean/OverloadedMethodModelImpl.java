package com.chinaedustar.rtda.bean;

import java.lang.reflect.InvocationTargetException;
import com.chinaedustar.common.bean.MethodDelegate;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.DataModelException;
import com.chinaedustar.rtda.model.MethodDataModel;

/**
 * 对具有多种重载形式的函数调用提供支持的实现类。
 * 
 * @author liujunxing
 */
public class OverloadedMethodModelImpl extends ModelImplBase<Object> 
		implements MethodDataModel {
	/** 方法委托。 */
	private final MethodDelegate method;
	
	/**
	 * 使用指定的目标对象、对象包装器、方法委托构造一个 OverloadedMethodModelImpl 的实例。
	 * @param target
	 * @param wrapper
	 * @param method
	 */
	public OverloadedMethodModelImpl(Object target, ObjectWrapper wrapper, MethodDelegate method) {
		super(target, wrapper);
		this.method = method;
	}
	
    /**
     * 能够执行一个方法调用。参数在传递前已经计算出来了。
     * @return - 返回计算的结果, 或者空。
     */
    public Object functor(Object[] args) {
    	try {
    		return method.invoke(target, args);
    	} catch (InvocationTargetException ex) {
    		// TODO: 异常的更好的包装。
    		throw new DataModelException("property method invoke InvocationTarget ex", ex);
    	} catch (IllegalAccessException ex) {
    		throw new DataModelException("property method invoke IllegalAccess ex", ex);
    	}
    }
    
    /**
     * 获得此方法的名字。此方法是可选实现的。
     * @return
     */
	public String getMethodName() {
		return this.method.getName();
	}
	
	/**
	 * 获得此方法的返回类型。此方法是可选实现的。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getReturnType() {
		return this.method.getReturnType();
	}
}
