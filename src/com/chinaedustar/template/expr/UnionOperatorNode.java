package com.chinaedustar.template.expr;

/**
 * 一元操作符节点。
 * 
 * @author liujunxing
 *
 */
class UnionOperatorNode extends AbstractLeafNode {
	/** 一元操作子。 */
	private final Operator operator;
	
	/** 操作的对象。 */
	private final AbstractExpressionNode obj_node;
	
	/**
	 * 
	 * @param operator
	 * @param expr_node
	 */
	public UnionOperatorNode(Operator operator, AbstractExpressionNode obj_node) {
		this.operator = operator;
		this.obj_node = obj_node;
	}
	
	@Override public String toString() {
		return this.operator.getSymbol() + this.obj_node;
	}
	
	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	@Override public String getDescription() {
		return "#UnionOper{oper=" + this.operator + "}";
	}

	/**
	 * 获得计算子。
	 * @return
	 */
	public Operator getOperator() {
		return this.operator;
	}
	
	/**
	 * 获得操作的对象。
	 * @return
	 */
	public AbstractExpressionNode getObjectNode() {
		return this.obj_node;
	}

	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	public Object eval(ExpressionCalculator calc) {
		Object oper_obj = obj_node.eval(calc);
		return calc.evalOperator(this.operator, oper_obj);
	}
}
