package com.chinaedustar.rtda.model;

/**
 * 支持执行内建函数的数据模型。
 * 
 * @author liujunxing
 *
 */
public interface BuiltinDataModel {
	/**
	 * 执行指定的内建函数。
	 * @param method_name - 要执行的内建函数名字。
	 * @param param_list - 函数的参数，可能为 null。
	 * @return 返回 DataModel.NULL 表示不支持该内建方法。
	 */
	public Object builtin(String method_name, Object[] param_list);
}
