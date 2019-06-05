package com.chinaedustar.rtda.simple;

import com.chinaedustar.rtda.model.BooleanDataModel;

/**
 * 实现按照布尔量来访问数据的简单数据访问对象。
 * 
 * <p>由于 boolean 值只有两种，所以不需要实例化这个类，而是直接使用其常量定义 TRUE, FALSE 即可。</p>
 * 
 * @author liujunxing
 *
 */
public class BooleanData implements BooleanDataModel {
	/** 所代表的内部值。 */
	private final boolean value;
	
	public static final BooleanDataModel forBoolean(boolean bool) {
		return bool ? BooleanDataModel.TRUE : BooleanDataModel.FALSE;
	}

	public static final BooleanDataModel forBoolean(Boolean bool) {
		return bool.booleanValue() ? BooleanDataModel.TRUE : BooleanDataModel.FALSE;
	}

	/**
	 * 初始化一个 BooleanData 的实例，其内部的 boolean 值为 false。
	 *
	 */
	public BooleanData() {
		this.value = false;
	}
	
	/**
	 * 使用指定的 boolean 值构造一个 BooleanData。
	 * @param value
	 */
	public BooleanData(boolean value) {
		this.value = value;
	}
	
	public String toString() {
		return Boolean.toString(value);
	}

	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap() {
		return this.value;
	}

	/**
	 * 转换此标量为一个字符串表示。一般可以直接用 Object.toString() 来实现。
	 * @param optional_format
	 * @return
	 */
	public String to_string(String optional_format) {
		return Boolean.toString(this.value);
	}

    /**
     * @return - 返回按照 boolean 量来解释此数据的值。
     */
    public boolean getAsBoolean() {
    	return this.value;
    }
}
