package com.chinaedustar.rtda.bean;

import java.lang.reflect.InvocationTargetException;
import com.chinaedustar.common.bean.ClassInfo;
import com.chinaedustar.common.bean.MethodDelegate;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.DataModel;
import com.chinaedustar.rtda.model.DataModelException;
import com.chinaedustar.rtda.model.HashDataModel;
import com.chinaedustar.rtda.model.BuiltinDataModel;

/**
 * 包装一个 Object 对象，使用标准的 bean Introspector 得到此对象的可访问方法
 *   和函数的信息。
 * 
 * <p>
 * 从而能够通过 object.foo 访问这个对象的属性，对于支持索引属性 (indexed properties) 
 *   的对象，还可以使用 object.foo[index] 语法访问索引属性。
 * HashDataModel 用于支持 object.foo, object.foo[index] 语法。
 * </p>
 * 
 * @author Attila Szegedi 原作者, 我们根据需要进行了调整。
 */
public class BeanModelImpl<T> extends ModelImplBase<T> implements HashDataModel, BuiltinDataModel {
	/**
	 * 使用指定的对象构造一个 BeanData 的实例。
	 * @param target
	 * @param wrapper
	 */
	public BeanModelImpl(T target, ObjectWrapper wrapper) {
		super(target, wrapper);
	}
	
	/**
	 * 尝试用 generic get 方法获得指定 key 的值。
	 * @param key
	 * @return
	 */
	public Object tryGenericGet(String key) {
		MethodDelegate generic_get = super.getClassInfo().getGenericGetMethod();
		if (generic_get == null) return wrapper.wrap(null);
		
		try {
			// 调用这个 generic-get 方法。
			return generic_get.invoke(target, new Object[] { key });
		} catch (InvocationTargetException ex) {
			throw new DataModelException("generic-get method invoke InvocationTarget ex", ex);
		} catch (IllegalAccessException ex) {
			throw new DataModelException("generic-get method invoke InvocationTarget ex", ex);
		}
	}
	
    /**
     * 获得指定键的数据。实现者尽量返回 DataModel 接口的数据，这将使数据访问更便利。
     *
     * @param key - 要访问的属性名称。
     * @return - 返回 null 表示没有；否则为找到的属性值。
     */
    public Object get(String key) {
    	// 获取这个对象的可访问属性信息。
    	ClassInfo ci = super.getClassInfo();
    	
    	// 找到具有指定名字的方法委托。
    	MethodDelegate method = ci.get(key);
    	if (method == null) {
    		return tryGenericGet(key);
    	}

    	// 根据不同的委托类型，返回不同的访问数据模型。
    	switch (method.getMethodType()) {
    	case MethodDelegate.METHOD_TYPE_PROPERTY:
    		return invokeProperty(method);  // v
    	case MethodDelegate.METHOD_TYPE_INDEXED_PROPERTY:
    		return wrapIndexedProperty(method);  // v
    	case MethodDelegate.METHOD_TYPE_METHOD:
    		return wrapSimpleMethod(method);
    	case MethodDelegate.METHOD_TYPE_OVERLOADED_METHOD:
    		return wrapOverloadedMethod(method);
    	}
    	
    	// 没有其它可能了，但是编译器不让。
    	throw new IllegalStateException("未知的方法类型: " + method.getMethodType());
    }

    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.model.BuiltinDataModel#builtin(java.lang.String, java.lang.Object[])
     */
	public Object builtin(String method_name, Object[] param_list) {
		if ("to_string".equals(method_name))
			return String.valueOf(super.target);
		else if ("is_number".equals(method_name))
			return false;
		else if ("is_string".equals(method_name))
			return false;
		else if ("is_boolean".equals(method_name))
			return false;
		else if ("is_map".equals(method_name))
			return false;
		else if ("is_date".equals(method_name))
			return false;
		else if ("is_null".equals(method_name))
			return false;
		
		// 尝试在 class 信息里面找 builtin
		String builtin_name = "_builtin_" + method_name;
		MethodDelegate method = this.getClassInfo().get(builtin_name);
		if (method != null)
			try {
				return method.invoke(target, new Object[]{param_list});
			} catch (InvocationTargetException e) {
				return "builtin @" + builtin_name + " failed: " + e.toString();
			} catch (IllegalAccessException e) {
				return "builtin @" + builtin_name + " failed: " + e.toString();
			}
		
    	return DataModel.NULL;
    }
    
    // === 实现 =================================================================
    
    /**
     * 找到的是一个属性，则直接访问此属性并返回访问结果。
     * @param method
     * @return
     */
    private final Object invokeProperty(MethodDelegate method) {
    	try {
    		// TODO: method.getReturnType() maybe void;
    		return method.invoke(target);
    	} catch (InvocationTargetException ex) {
    		// TODO: 异常的更好的包装。
    		throw new DataModelException("property method invoke InvocationTarget ex", ex);
    	} catch (IllegalAccessException ex) {
    		throw new DataModelException("property method invoke IllegalAccess ex", ex);
    	}
    }

    /**
     * 找到的是一个索引属性，返回对此索引属性的包装。
     * @param method
     * @return
     */
    private final Object wrapIndexedProperty(MethodDelegate method) {
    	return new IndexedPropertyModelImpl(target, wrapper, method);
    }
    
    // 包装简单方法调用
    private final Object wrapSimpleMethod(MethodDelegate method) {
    	return new SimpleMethodModelImpl(target, wrapper, method);
    }
    
    // 包装带有重载的方法调用
    private final Object wrapOverloadedMethod(MethodDelegate method) {
    	return new OverloadedMethodModelImpl(target, wrapper, method);
    }
}
