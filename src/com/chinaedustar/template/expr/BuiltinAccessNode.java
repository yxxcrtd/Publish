package com.chinaedustar.template.expr;

/**
 * 内建方法访问节点。
 * 
 * @author liujunxing
 *
 */
public class BuiltinAccessNode extends AbstractLeafNode {
	/** 访问的对象。 */
	private final AbstractExpressionNode obj_node;
	
	/** 调用的函数名字。 */
	private final String method_name;
	
	/** 参数列表。 */
	private final ParameterList param_list;

	public BuiltinAccessNode(AbstractExpressionNode obj_node, String method_name) {
		this.obj_node = obj_node;
		this.method_name = method_name;
		this.param_list = null;
	}
	
	public BuiltinAccessNode(AbstractExpressionNode obj_node, String method_name, ParameterList param_list) {
		this.obj_node = obj_node;
		this.method_name = method_name;
		this.param_list = param_list;
	}
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	@Override public Object eval(ExpressionCalculator calc) {
		Object acc_obj = this.obj_node.eval(calc);
		return calc.invokeBuiltin(acc_obj, this.method_name, calc.calcParameter(this.param_list));
	}
	
	/** 转为友好字符串表示。 */
	@Override public String toString() {
		return this.obj_node + "@" + this.method_name + "(" + this.param_list + ")";
	}
	
	@Override public String getDescription() {
		return "#Builtin{" + method_name + "}";
	}

	/**
	 * 获得要访问的对象。
	 * @return
	 */
	public Object getObjectNode() {
		return this.obj_node;
	}
	
	/**
	 * 获得要访问的方法名字。
	 * @return
	 */
	public String getMethodName() {
		return this.method_name;
	}
	
	/**
	 * 获得参数列表。
	 * @return
	 */
	public Object getParameterList() {
		return this.param_list;
	}

	public void dump(String indent) {
		super.dump(indent);
		System.out.print(indent + "  Object: ");
		this.obj_node.dump(indent + "  ");
	}
}
