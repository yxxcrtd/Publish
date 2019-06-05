package com.chinaedustar.rtda.bean;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.CollectionDataModel;
import com.chinaedustar.rtda.model.HashDataModel;

/**
 * 标准集合类型访问实现。一个集合的最小要求是能够提供枚举器 iterator()，从而能够
 *   被 #foreach 方法访问。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class CollectionModelImpl extends BeanModelImpl<Iterable> 
		implements HashDataModel, CollectionDataModel {
	private final boolean is_empty;
	/**
	 * 使用指定的集合和包装器构造一个 CollectionModelImpl 的实例。
	 * @param target
	 * @param wrapper
	 */
	public CollectionModelImpl(Iterable target, ObjectWrapper wrapper) {
		super(target, wrapper);
		this.is_empty = !target.iterator().hasNext();
	}
	
	// === CollectionDataModel 接口实现 =======================================
	
    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
    public java.util.Iterator iterator() {
    	return target.iterator();
    }

    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_empty".equals(method_name))
			return is_empty;
		return super.builtin(method_name, param_list);
	}}
