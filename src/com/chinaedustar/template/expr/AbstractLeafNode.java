package com.chinaedustar.template.expr;

/**
 * 抽象叶节点的实现。
 * 
 * @author liujunxing
 */
abstract class AbstractLeafNode extends AbstractExpressionNode {
	/**
	 * 获得节点是否是一个叶节点。叶节点不再有子节点。
	 * @return
	 */
	@Override public boolean isLeafNode() { return true; }
	
	/**
	 * 获得左子树节点。
	 * @return
	 */
	@Override public AbstractExpressionNode getLeftNode() { return null; }
	
	/**
	 * 获得右子树节点。
	 * @return
	 */
	@Override public AbstractExpressionNode getRightNode() { return null; }
}
