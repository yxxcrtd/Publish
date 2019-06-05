package com.chinaedustar.rtda.model;

/**
 * 做为简单标量(Scalar)数据访问模型的基类。String, Date, Number, Boolean
 *   都是简单标量。
 *  
 * @author liujunxing
 */
public interface ScalarDataModel extends DataModel {
	/**
	 * 转换此标量为一个字符串表示。一般可以直接用 Object.toString() 来实现。
	 * @param optional_format - (可选的) 使用此格式，可能为 null.
	 * @return
	 */
	public String to_string(String optional_format);
}
