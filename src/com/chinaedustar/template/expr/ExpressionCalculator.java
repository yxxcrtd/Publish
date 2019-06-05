package com.chinaedustar.template.expr;

/**
 * 定义表达式树计算器的接口。
 * 
 * @author liujunxing
 */
public interface ExpressionCalculator {
	/**
	 * 计算一个常量值，通常原样返回就可以了。
	 * @param const_value
	 * @return
	 */
	public Object evalConst(Object const_value);

	/**
	 * 计算一个变量的值，通常在环境中查找此变量然后返回其值。
	 * @param var_name
	 * @return
	 */
	public Object evalVariable(String var_name);

	/**
	 * 计算一元操作符 oper oper_obj 的值。如 !true, -user_age。
	 * @param oper
	 * @param oper_obj
	 * @return
	 */
	public Object evalOperator(Operator oper, Object oper_obj);

	/**
	 * 计算二元操作符 left_value oper right_value 的值。
	 * @param oper
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public Object evalOperator(Operator oper, Object left_value, Object right_value);
	
	/**
	 * 调用一个全局函数。
	 * @param method_name - 函数名字。
	 * @param param_list - 参数列表。
	 * @return - 返回值。
	 */
	public Object invokeGlobalMethod(String method_name, ParameterList param_list);
	
	/**
	 * 访问一个对象的指定属性。
	 * @param acc_obj
	 * @param prop_name
	 * @return
	 */
	public Object invokeProperty(Object acc_obj, String prop_name);
	
	/**
	 * 访问一个对象的指定方法。
	 * @param acc_obj - 要访问的对象。
	 * @param method_name - 方法名字。
	 * @param param_list - 参数列表。
	 * @return
	 */
	public Object invokeMethod(Object acc_obj, String method_name, ParameterList param_list);

	/**
	 * 访问一个对象的指定索引的值。
	 * @param acc_obj - 要访问的对象。
	 * @param acc_index - 访问的索引。
	 * @return
	 */
	public Object invokeIndexProperty(Object acc_obj, Object acc_index);
	
	/**
	 * 访问一个对象的内建方法。
	 * @param acc_obj
	 * @param method_name
	 * @param param_list
	 * @return
	 */
	public Object invokeBuiltin(Object acc_obj, String method_name, Object[] param_list);

	/**
	 * 访问一个对象的内建方法。
	 * @param acc_obj
	 * @param method_name
	 * @param param_list
	 * @return
	 */
	public Object[] calcParameter(ParameterList param_list);
}
