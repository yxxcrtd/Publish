package com.chinaedustar.rtda;

/**
 * 各种操作子的定义，用字符串直接表示比较直观。
 * 
 * @author liujunxing
 *
 */
public interface OperatorConstant {
	/** '||' 逻辑或。 */
	public static final String OPERATOR_OR = "||";
	
	/** '&&' 逻辑与。 */
	public static final String OPERATOR_AND = "&&";
	
	/** '^' 逻辑异或。 */
	public static final String OPERATOR_XOR = "^";

	/** '==' equal 比较。 */
	public static final String OPERATOR_EQUAL = "==";
	/** '!=' not equal 比较。 */
	public static final String OPERATOR_NOTEQUAL = "!=";
	/** '>=' great than or equal 大于等于 */
	public static final String OPERATOR_GREATOREQUAL = ">=";
	/** '<=' less than or equal 小于等于 */
	public static final String OPERATOR_LESSOREQUAL = "<=";
	/** '>' great 大于。 */
	public static final String OPERATOR_GREAT = ">";
	/** '<' less 小于。 */
	public static final String OPERATOR_LESS = "<";

	/** '+', 加。 - level 40. */
	public static final String OPERATOR_PLUS = "+"; 
	/** '-', 减。 */
	public static final String OPERATOR_MINUS = "-";
	
	/** '*', 乘。 - level 50. */
	public static final String OPERATOR_MULTIPLY = "*";
	/** '/', 除。 */
	public static final String OPERATOR_DIVIDE = "/";
	/** '%', 求余。*/
	public static final String OPERATOR_MOD = "%";

	/** '!', 求非 - 其为一元操作符。 */
	public static final String OPERATOR_NOT = "!";
}
