package com.chinaedustar.rtda.bean;

import com.chinaedustar.common.bean.ClassInfo;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.DataModel;

/**
 * 标准数据模型的实现基类。
 * 
 * <p>
 * <pre>
 * ModelImplBase - 数据模型实现的基类。
 *   BeanModelImpl - 对一个 Bean 进行封装的标准模型实现。
 *   IndexedPropertyModelImpl - 对索引属性的模型实现。
 *   SimpleMethodModelImpl - 单个方法的模型实现。
 *   OverloadedMethodModelImpl - 具有重载形式的方法的模型实现。
 * </pre>
 * </p>
 * @author liujunxing
 */
public abstract class ModelImplBase<T> implements DataModel {
	/** 被封装的目标对象。 */
	protected final T target;
	
	/** 包装此对象的包装器。*/
	protected final ObjectWrapper wrapper;
	
	/** 这个对象的可访问属性方法的信息对象，从 wrapper 中得到的。 */
	private ClassInfo class_inf;

	/**
	 * 使用指定的目标对象和包装器构造 ModelBaseImpl 的实例。
	 * @param target
	 * @param wrapper
	 */
	public ModelImplBase(T target, ObjectWrapper wrapper) {
		if (target == null) throw new IllegalArgumentException("target == null");
		if (wrapper == null) throw new IllegalArgumentException("wrapper == null");
		this.target = target;
		this.wrapper = wrapper;
	}

	@Override public String toString() {
		return (target == null) ? "" : target.toString();
	}

	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap() {
		return this.target;
	}

	/**
	 * 获得对象所属类型的名字。
	 * @return
	 */
	public final String getObjectClassName() {
		return (target == null) ? "null" : target.getClass().getName();
	}
	
    /**
     * 获得包装的对象类的可访问方法、属性的信息。
     * @return
     */
    @SuppressWarnings("rawtypes")
	protected final ClassInfo getClassInfo() {
    	if (this.class_inf == null) {
    		Class clazz = this.target.getClass();
    		this.class_inf = wrapper.getClassInfo(clazz);
    	}
    	return this.class_inf;
    }
}
