package com.chinaedustar.template.comp;

import com.chinaedustar.rtda.model.DataModel;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.expr.ParameterList;

/**
 * 模板执行环境中使用的表达式计算器。
 * 
 * @author liujunxing
 */
public class TemplateExpressionCalculator extends AbstractExpressionCalculator {
	/** 执行环境。 */
	private final InternalProcessEnvironment env;
	
	/**
	 * 构造一个 DefaultExpressionCalculator 的实例。 
	 *
	 */
	public TemplateExpressionCalculator(InternalProcessEnvironment env) {
		// env 有自己独立的变量解析，所以不用基类去解析变量了。
		super(null, env.getObjectWrapper());
		this.env = env;
	}
	
	/**
	 * 计算一个变量的值，通常在环境中查找此变量然后返回其值。
	 * @param var_name
	 * @return
	 */
	@Override public Object evalVariable(String var_name) {
		Object var = env.findVariable(var_name);
		if (var == null) return DataModel.NULL;
		return var;
	}

	/**
	 * 调用一个全局函数。
	 * @param method_name - 函数名字。
	 * @param param_list - 参数列表。
	 * @return - 返回值。
	 */
	public Object invokeGlobalMethod(String method_name, ParameterList param_list) {
		return this.env.callGlobalMethod(method_name, param_list);
	}
	
	/**
	 * 访问一个对象的指定方法。
	 * @param acc_obj - 要访问的对象。
	 * @param method_name - 方法名字。
	 * @param param_list - 参数列表。
	 * @return
	 */
	@Override public Object invokeMethod(Object target, String method_name, ParameterList param_list) {
		return super.invokeMethod(target, method_name, param_list);
	}

	/**
	 * 访问一个对象的内建方法, 我们首先用缺省的，如果不支持该方法则使用模板环境的扩展 builtin.
	 * @param acc_obj
	 * @param method_name
	 * @param param_list
	 * @return
	 */
	@Override public Object invokeBuiltin(Object acc_obj, String method_name, Object[] param_list) {
		Object result = super.invokeBuiltin(acc_obj, method_name, param_list);
		if (result == DataModel.NULL)
			return this.env.findInvokeBuiltinMethod(acc_obj, method_name, param_list);
		else
			return result;
	}

	// 处理当访问一个对象属性失败的情况 - 查找这个对象是否有 builtin 可以执行。
	// 2007-8-14, 语法改变了，不允许这样访问 builtin 方法了。使用 obj@builtin 语法访问。
	@Override protected Object handleMethodAccessFail(Object target, String method_name, ParameterList param_list) {
		return null;
		// return this.env.findInvokeBuiltinMethod(target, method_name, param_list);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.comp.AbstractExpressionCalculator#calcParameter(com.chinaedustar.template.expr.ParameterList)
	 */
	@Override public Object[] calcParameter(ParameterList param_list) {
		return env.calcParameter(param_list);
	}
}
