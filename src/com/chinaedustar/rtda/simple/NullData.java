package com.chinaedustar.rtda.simple;

import java.util.Date;

import com.chinaedustar.rtda.model.*;

/**
 * 表示一个空的数据实现。这个数据就像一个黑洞。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class NullData implements StringDataModel, BooleanDataModel, 
		NumberDataModel, Comparable, HashDataModel, DatetimeDataModel, 
		SequenceDataModel, CollectionDataModel, java.util.Iterator, BuiltinDataModel {
	/** 对 null 的标准包装。 */
	public static final NullData NULL = new NullData();
	
	/** 对 empty 的标准包装。 */
	public static final NullData EMPTY = new NullData();
	
	/** 对 unexist 的标准包装。 */
	public static final NullData UNEXIST = new NullData();
	
	public static final Integer ZERO = new Integer(0);
	
	public static final Date DATE_ZERO = new Date(0);
	
	public static final String STRING_EMPTY = "";
	
	private NullData() {
		// 外部不要构造。
	}
	
	/** 字符串表示。 */
	@Override 
	public String toString() {
		return STRING_EMPTY;
	}
	
	/** 和其它对象比较。 */
	@Override 
	public boolean equals(Object o) {
		if ((o == null) || (o instanceof NullData)) return true;
		return false;
	}
	
	/** 和其它对象比较。 */
	public int compareTo(Object o) {
		if (o == null || o instanceof NullData) return 0;
		return -1;
	}
	
	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap() {
		return null;
	}

	public String to_string(String format) {
		return STRING_EMPTY;
	}
	
    /**
     * 返回此数据的字符串表示。一般而言不要返回 null。
     */
    public String getAsString() {
    	return STRING_EMPTY;
    }

    /**
     * @return - 返回按照 boolean 量来解释此数据的值。
     */
    public boolean getAsBoolean() {
    	return false;
    }
    
    /**
     * 返回此数据代表的数字值。尽量不要返回 null. 
     */
    public Number getAsNumber() {
    	return ZERO;
    }

    /**
     * 返回此数据表示的日期值。
     */
    public Date getAsDate() {
    	return DATE_ZERO;
    }

    /**
     * 获得指定键的数据。
     * <p>实现者可以尽量返回 DataModel 接口的数据，这将使后续的数据访问便利一些。</p>
     * 
     * @param key - 要访问的属性名称。
     * @return - 属性值。
     */
    public Object get(String key) {
    	return this;
    }

    /**
     * 访问指定索引的数据。
     * @return - 返回指定索引的数据。尽量返回实现了 DataModel 的数据。
     */
    public Object indexor(Object index) {
    	return this;
    }

    /**
     * @return - 返回数据项的数量，此为可选实现，如果不支持可以抛出异常。
     */
    public int size() {
    	return 0;
    }

    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
	public java.util.Iterator iterator() {
    	return this;
    }

    public boolean hasNext() { return false; }
    
    public Object next() { return this; }
    
    public void remove() {  }

    /* (non-Javadoc)
     * 
     * @see com.chinaedustar.rtda.model.BuiltinDataModel#builtin(java.lang.String, java.lang.Object[])
     */
	public Object builtin(String method_name, Object[] param_list) {
		if ("is_null".equals(method_name))
			return true;
		return null;
	}
	
}
