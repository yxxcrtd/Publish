package com.chinaedustar.template.expr;

/**
 * 表示一个全局函数调用的访问节点。
 * 
 * @author liujunxing
 */
final class GlobalMethodNode extends AbstractLeafNode {
	/** 所调用的函数名字。 */
	private final String method_name;
	
	/** 参数列表。 */
	private final ParameterList param_list;
	
	/**
	 * 使用指定的函数名字和参数构造 GlobalMethodNode 节点。
	 * @param method_name
	 * @param param_list
	 */
	public GlobalMethodNode(String method_name, ParameterList param_list) {
		this.method_name = method_name;
		this.param_list = param_list;
	}
	
	@Override public String toString() {
		return this.method_name + "(" + param_list + ")";
	}
	
	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	@Override public String getDescription() {
		return "#GlobalMethod{Name=" + this.method_name + "}";
	}

	
	/**
	 * 获得函数名字。
	 * @return
	 */
	public String getMethodName() {
		return this.method_name;
	}
	
	/**
	 * 获得参数列表。
	 * @return
	 */
	public Object getParameters() {
		return param_list;
	}
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	@Override public Object eval(ExpressionCalculator calc) {
		return calc.invokeGlobalMethod(this.method_name, param_list);
	}
}
