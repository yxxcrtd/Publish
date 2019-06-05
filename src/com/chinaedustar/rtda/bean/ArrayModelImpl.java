package com.chinaedustar.rtda.bean;

import java.lang.reflect.Array;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.SequenceDataModel;
import com.chinaedustar.rtda.model.CollectionDataModel;
import com.chinaedustar.rtda.oper.MathCalculator;

/**
 * 标准数据的访问方法包装，数组可以序列访问和枚举访问。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class ArrayModelImpl extends BeanModelImpl<Object> 
		implements SequenceDataModel, CollectionDataModel {
	/** 数组的长度。 */
	private final int length;
	
	/**
	 * 使用指定的目标对象和对象包装器构造一个 ArrayModelImpl 的实例。
	 * @param target
	 * @param wrapper
	 */
	public ArrayModelImpl(Object target, ObjectWrapper wrapper) {
		super(target, wrapper);
		this.length = Array.getLength(target);
	}
	
	// === SequenceDataModel 接口实现 ==========================================
	
    /**
     * 访问指定索引的数据。
     * @return - 返回指定索引的数据。尽量返回实现了 DataModel 的数据。
     */
    public Object indexor(Object index) {
    	int i_idx = MathCalculator.getIntegerValue(index);
    	return Array.get(target, i_idx);
    }

    /**
     * @return - 返回数据项的数量，此为可选实现，如果不支持可以抛出异常。
     */
    public int size() {
    	return this.length;
    }

    // === CollectionDataModel 接口实现 ========================================
    
    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
	public java.util.Iterator iterator() {
    	return new Iterator();
    }

    /** 数组枚举器的实现。 */
    private final class Iterator implements java.util.Iterator {
        private int position = 0;

        public boolean hasNext() {
            return position < length;
        }
        
        public Object next() {
        	return Array.get(target, position++);
        }

        public void remove() {
        	throw new UnsupportedOperationException("Array 枚举器是只读的，不支持删除操作。");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_empty".equals(method_name))
			return this.length == 0;
		else if ("size".equals(method_name))
			return size();
		else if ("contains".equals(method_name))
			return contains(target, param_list);
		else if ("join".equals(method_name))
			return join(target, param_list);
		
		
		return super.builtin(method_name, param_list);
	}
	
	private static boolean contains(Object target, Object[] param_list) {
		if (param_list == null || param_list.length == 0) return false;
		Object[] array = (Object[])target;
		for (int i = 0; i < array.length; ++i)
			if (array[i] != null && array[i].equals(param_list[0])) return true;
		return false; 
	}
	
	private static String join(Object target, Object[] param_list) {
		String sep = (param_list == null || param_list.length == 0 || param_list[0] == null) ? "" :
			String.valueOf(param_list[0]);
		Object[] array = (Object[])target;
		StringBuilder strbuf = new StringBuilder();
		for (int i = 0; i < array.length; ++i) {
			if (i > 0) strbuf.append(sep);
			strbuf.append(array[i]);
		}
		return strbuf.toString();
	}
}
