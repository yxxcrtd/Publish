package com.chinaedustar.rtda.bean;

import java.lang.reflect.InvocationTargetException;

import com.chinaedustar.common.bean.MethodDelegate;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.DataModelException;
import com.chinaedustar.rtda.model.SequenceDataModel;

/**
 * 索引属性的数据访问模型的实现。
 * 
 * <p>
 * 如果一个对象实现了索引属性 (IndexedProperty) 则通过此实现对索引子(oper [])
 *   运算提供支持。
 * </p>
 * @author liujunxing
 */
public class IndexedPropertyModelImpl extends ModelImplBase<Object> 
		implements SequenceDataModel {
	/** 方法委托。 */
	private final MethodDelegate method;
	
	/**
	 * 使用指定的目标对象、对象包装器、方法委托构造一个 IndexedPropertyModel 的实例。
	 * @param target
	 * @param wrapper
	 * @param method
	 */
	public IndexedPropertyModelImpl(Object target, ObjectWrapper wrapper, MethodDelegate method) {
		super(target, wrapper);
		this.method = method;
	}
	
	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap() {
		return this;
	}

    /**
     * 访问指定索引的数据。
     * @return - 返回指定索引的数据。尽量返回实现了 DataModel 的数据。
     */
    public Object indexor(Object index) {
    	try {
    		return method.invoke(target, index);
    	} catch (InvocationTargetException ex) {
    		// TODO: 异常的更好的包装。
    		throw new DataModelException("property method invoke InvocationTarget ex", ex);
    	} catch (IllegalAccessException ex) {
    		throw new DataModelException("property method invoke IllegalAccess ex", ex);
    	}
    }

    /**
     * @return - 返回数据项的数量，此为可选实现，如果不支持可以抛出异常。
     */
    public int size() {
    	throw new DataModelException("索引属性不支持 size() 方法。");
    }
}
