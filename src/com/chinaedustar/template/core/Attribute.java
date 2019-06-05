package com.chinaedustar.template.core;

/**
 * 定义标签的属性字段，一个属性拥有一个名字和一个值。
 * 
 * <p>这个类也可以用来表示函数的参数，只是参数的名字没有。</p>
 * 
 * @author liujunxing
 *
 */
public class Attribute {
	public static final String EMPTY_STRING = "";
	private final String key;
	private final String value;
	
	public Attribute() {
		this.key = EMPTY_STRING;
		this.value = EMPTY_STRING;
	}
	
	public Attribute(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}
}
