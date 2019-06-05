package com.chinaedustar.rtda.bean;

import java.util.Iterator;
import com.chinaedustar.rtda.model.CollectionDataModel;
import com.chinaedustar.rtda.model.DataModelException;

/**
 * Iterator 的一个包装，只能读取一次 iterator.
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class IteratorModelImpl implements CollectionDataModel {
	private final Iterator iter;
	private boolean accessed = false;
	
	/**
	 * 构造。
	 * @param iter
	 */
	public IteratorModelImpl(Iterator iter) {
		this.iter = iter;
	}
	
	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap() {
		return this.iter;
	}
	
    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
    public java.util.Iterator iterator() {
    	if (this.accessed) 
    		throw new DataModelException("iterator 对象是有状态的，其不能被多次访问。");
    	this.accessed = true;
    	return this.iter;
    }
}
