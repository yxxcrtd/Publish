package com.chinaedustar.rtda.simple;

import com.chinaedustar.rtda.model.NumberDataModel;

/**
 * 数字型数据的访问模型。
 * 
 * @author liujunxing
 */
public class NumberData implements NumberDataModel {
	private final Number number;
	
	/**
	 * 获得指定数字的包装对象。
	 * @param number
	 * @return
	 */
	public static NumberData forNumber(Number number) {
		return new NumberData(number);
	}
	
	/**
	 * 构造缺省的 NumberData，其数字为 0。
	 *
	 */
	public NumberData() {
		number = new Integer(0);
	}

	/**
	 * 使用指定的 number 构造一个 NumberData 的实例。
	 * @param number
	 */
	public NumberData(Number number) {
		this.number = number;
	}
	
	/**
	 * 使用指定的 number 构造一个 NumberData 的实例。
	 * @param number
	 */
	public NumberData(int number) {
		this.number = number;
	}

	/**
	 * 使用指定的 number 构造一个 NumberData 的实例。
	 * @param number
	 */
	public NumberData(double number) {
		this.number = number;
	}
	
	public String toString() {
		return this.number.toString();
	}

	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap() {
		return this.number;
	}

	/**
	 * 转换此标量为一个字符串表示。一般可以直接用 Object.toString() 来实现。
	 * @param optional_format - (可选的) 使用此格式，可能为 null.
	 * @return
	 */
	public String to_string(String optional_format) {
		return this.number.toString();
	}

    /**
     * 返回此数据代表的数字值。尽量不要返回 null. 
     */
    public Number getAsNumber() {
    	return this.number;
    }
}
