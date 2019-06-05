package com.chinaedustar.template.expr;

/**
 * 索引访问节点。
 * 
 * 要访问的对象，使用左子树表示；访问的索引值，使用右子树表示。
 * 
 * @author liujunxing
 */
final class IndexOperatorNode extends AbstractContainerNode {
	/**
	 * 使用指定的访问对象和索引构造一个 IndexOperatorNode 的实例。
	 * @param obj_node
	 * @param index_val
	 */
	public IndexOperatorNode(AbstractExpressionNode obj_node, AbstractExpressionNode index_val) {
		super(obj_node, index_val);
	}
	
	/** 转换为友好的字符串表示。 */
	@Override public String toString() {
		return super.left_node + "[" + super.right_node + "]";
	}
	
	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	@Override public String getDescription() {
		return "#Index[]";
	}
	
	/**
	 * 获得索引器要访问的对象。
	 * @return
	 */
	public AbstractExpressionNode getObjectNode() {
		return super.getLeftNode();
	}
	
	/**
	 * 获得索引。
	 * @return
	 */
	public AbstractExpressionNode getIndex() {
		return super.getRightNode();
	}
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	public Object eval(ExpressionCalculator calc) {
		// 分别计算要访问的对象和索引。
		Object acc_obj = this.getLeftNode().eval(calc);
		Object acc_index = this.getRightNode().eval(calc);
		
		// 然后计算此索引下的数据。
		return calc.invokeIndexProperty(acc_obj, acc_index);
	}
}
