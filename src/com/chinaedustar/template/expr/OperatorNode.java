package com.chinaedustar.template.expr;

/**
 * 标准操作符节点。
 * 
 * @author liujunxing
 */
final class OperatorNode extends AbstractContainerNode {
	/** 操作符。 */
	private Operator operator;
	
	public OperatorNode(Operator operator, AbstractExpressionNode left_node, AbstractExpressionNode right_node) {
		super(left_node, right_node);
		this.operator = operator;
	}
	
	/** 获得友好的字符串表示。 */
	@Override public String toString() {
		return "(" + super.left_node + " " + operator.getSymbol() + " " + super.right_node + ")";
	}
	
	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	@Override public String getDescription() {
		return "#Oper{" + getOperatorDisplayName() + "}";
	}
	
	/** 获得此操作符比较友好的显示名字。 */
	public String getOperatorDisplayName() {
		return this.operator.getSymbol();
	}
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	public Object eval(ExpressionCalculator calc) {
		Object left_value = this.left_node.eval(calc);
		Object right_value = this.right_node.eval(calc);
		return calc.evalOperator(this.operator, left_value, right_value);
	}
}
