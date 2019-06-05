package com.chinaedustar.template.expr;

/**
 * 对象的属性访问节点。
 * 
 * @author liujunxing
 */
final class PropertyAccessNode extends AbstractLeafNode {
	/** 访问的对象。 */
	private final AbstractExpressionNode obj_node;
	
	/** 调用的函数名字。 */
	private final String property_name;
	
	/**
	 * 使用指定的参数构造一个 PropertyAccessNode 的实例。
	 * @param obj_node - 所访问的对象。
	 * @param property_name - 属性的名字。
	 */
	public PropertyAccessNode(AbstractExpressionNode obj_node, String property_name) {
		this.obj_node = obj_node;
		this.property_name = property_name;
	}
	
	/** 转为友好字符串表示。 */
	@Override public String toString() {
		return this.obj_node + "." + this.property_name;
	}

	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	@Override public String getDescription() {
		return "#Property{" + property_name + "}";
	}

	/**
	 * 获得要访问的对象。
	 * @return
	 */
	public AbstractExpressionNode getObjectNode() {
		return this.obj_node;
	}
	
	/**
	 * 获得访问的属性名字。
	 * @return
	 */
	public String getPropertyName() {
		return this.property_name;
	}
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	public Object eval(ExpressionCalculator calc) {
		// 先计算出要访问的对象，然后请求获取这个对象的指定名字的属性。
		Object target = this.obj_node.eval(calc);
		return calc.invokeProperty(target, this.property_name);
	}

	public void dump(String indent) {
		super.dump(indent);
		System.out.print(indent + "  Object: ");
		this.obj_node.dump(indent + "  ");
	}
}
