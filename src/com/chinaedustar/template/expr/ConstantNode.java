package com.chinaedustar.template.expr;

/**
 * 表示一个常量节点，其为叶子节点，不再有子节点了。
 * 
 * @author liujunxing
 */
final class ConstantNode extends AbstractLeafNode {
	/** 此叶节点代表的常量值。 */
	private Object const_value;
	
	/**
	 * 构造一个缺省的 ConstantNode 的实例，其具有初始 null 的常量值。
	 *
	 */
	public ConstantNode() {
		const_value = null;
	}
	
	/**
	 * 使用指定的值构造一个 ConstantNode 的实例，其具有 val 的常量值。
	 * @param val
	 */
	public ConstantNode(Object val) {
		this.const_value = val;
	}
	
	/**
	 * 使用指定的值构造一个 ConstantNode 的实例，其具有 val 的常量值。
	 * @param val
	 */
	public ConstantNode(int val) {
		this.const_value = val;
	}

	/**
	 * 使用指定的值构造一个 ConstantNode 的实例，其具有 val 的常量值。
	 * @param val
	 */
	public ConstantNode(double val) {
		this.const_value = val;
	}
	
	/**
	 * 使用指定的值构造一个 ConstantNode 的实例，其具有 val 的常量值。
	 * @param val
	 */
	public ConstantNode(String val) {
		this.const_value = val;
	}

	/** 获得友好的字符串表示。 */
	@Override public String toString() {
		return const_value == null ? "null" : const_value.toString();
	}

	/**
	 * 获得这个节点的 dump 详细描述信息。
	 * @return
	 */
	@Override public String getDescription() {
		if (const_value == null) return "null";
		return "#Const{val=" + const_value + ",type=" + const_value.getClass().getName() + "}";
	}

	/**
	 * 获得此常量值。
	 * @return
	 */
	public Object getConstValue() {
		return this.const_value;
	}
	
	/**
	 * 使用指定的计算器计算这个节点的值。
	 * @param calc - 计算器。
	 * @return - 返回计算结果。
	 */
	public Object eval(ExpressionCalculator calc) {
		return calc.evalConst(this.const_value);
	}
}
