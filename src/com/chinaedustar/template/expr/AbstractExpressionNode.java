package com.chinaedustar.template.expr;

/**
 * 表示一个抽象的表达式节点。
 * 
 * @author liujunxing
 */
public abstract class AbstractExpressionNode implements ExpressionConstant {
	/**
	 * 获得节点是否是一个叶节点。叶节点不再有子节点。
	 * @return
	 */
	public abstract boolean isLeafNode();
	
	/**
	 * 获得左子树节点。
	 * @return
	 */
	public abstract AbstractExpressionNode getLeftNode();
	
	/**
	 * 获得右子树节点。
	 * @return
	 */
	public abstract AbstractExpressionNode getRightNode();
	
	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	public abstract String getDescription();
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	public abstract Object eval(ExpressionCalculator calc);
	
	/**
	 * 调试输出。
	 * @param indent
	 */
	public void dump(String indent) {
		System.out.println(indent + "dump node: " + this.getDescription());
		if (getLeftNode() != null) {
			getLeftNode().dump(indent + "  ");
		}
		if (getRightNode() != null) {
			getRightNode().dump(indent + "  ");
		}
	}
}
