package com.chinaedustar.template.expr;

/**
 * 表示一个独立变量的节点。
 * 
 * @author liujunxing
 */
final class VariableNode extends AbstractLeafNode {
	/** 此变量的名字。 */
	private String var_name;
	
	public VariableNode() {
		
	}
	
	public VariableNode(String var_name) {
		this.var_name = var_name;
	}
	
	/**	友好的字符串表示。 */
	@Override public String toString() {
		return this.var_name;
	}

	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	@Override public String getDescription() {
		return "#Var{name=" + var_name + "}";
	}
	
	/**
	 * 获得变量的名字。
	 * @return
	 */
	public String getVariableName() {
		return this.var_name;
	}
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	public Object eval(ExpressionCalculator calc) {
		if (calc == null) throw new IllegalArgumentException("calc == null"); 
		return calc.evalVariable(this.var_name);
	}
}
