package com.chinaedustar.template.expr;

/**
 * 具有标准左右子树的节点。
 * 
 * @author liujunxing
 */
abstract class AbstractContainerNode extends AbstractExpressionNode {
	/** 左子树。 */
	protected AbstractExpressionNode left_node;
	
	/** 右子树。 */
	protected AbstractExpressionNode right_node;
	
	public AbstractContainerNode() {
	}
	
	public AbstractContainerNode(AbstractExpressionNode left_node, AbstractExpressionNode right_node) {
		this.left_node = left_node;
		this.right_node = right_node;
	}
	
	/**
	 * 获得节点是否是一个叶节点。叶节点不再有子节点。
	 * @return
	 */
	@Override public boolean isLeafNode() { return false; }
	
	/**
	 * 获得左子树节点。
	 * @return
	 */
	@Override public AbstractExpressionNode getLeftNode() { return this.left_node; }
	
	/**
	 * 获得右子树节点。
	 * @return
	 */
	@Override public AbstractExpressionNode getRightNode() { return this.right_node; }
}
