package com.chinaedustar.rtda.bean;

import java.util.Enumeration;

import com.chinaedustar.rtda.model.CollectionDataModel;
import com.chinaedustar.rtda.model.DataModelException;

/**
 * 提供对 Enumeration 包装的实现
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class EnumerationModelImpl implements CollectionDataModel {
	private final Enumeration enumer;
	private boolean accessed;
	
	public EnumerationModelImpl(Enumeration enumer) {
		this.enumer = enumer;
	}

	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap() {
		return this.enumer;
	}

    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
    public Iterator iterator() {
    	if (this.accessed) 
    		throw new DataModelException("enumeration 对象是有状态的，其不能被多次访问。");
    	
    	this.accessed = true;
    	return new Iterator();
    }
    
	private final class Iterator implements java.util.Iterator {
        public boolean hasNext() {
        	return enumer.hasMoreElements();
        }

        public Object next() {
        	return enumer.nextElement();
        }

        public void remove() {
        	throw new UnsupportedOperationException("此枚举器 iterator 不支持删除操作。");
        }
    }
}
