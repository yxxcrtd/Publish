package com.chinaedustar.template.expr;

/**
 * 代表一个运算符。包括数学或者逻辑的运算符。
 * 
 * @author liujunxing
 */
public class Operator {
	/** '||' 逻辑或 - level 10。 */
	public static final Operator OPERATOR_OR = new Operator(1, 10, "||", "Or");
	public static final int OPERATOR_OR_CODE = 1;
	
	/** '&&' 逻辑与。 - level 20。 */
	public static final Operator OPERATOR_AND = new Operator(2, 20, "&&", "And");
	public static final int OPERATOR_AND_CODE = 2;
	/** '^' 逻辑异或 - level 20。 */
	public static final Operator OPERATOR_XOR = new Operator(3, 20, "^", "Xor");
	public static final int OPERATOR_XOR_CODE = 3;

	/** '==' equal 比较。 - level 30. */
	public static final Operator OPERATOR_EQUAL = new Operator(4, 30, "==", "Equal");
	public static final int OPERATOR_EQUAL_CODE = 4; 
	/** '!=' not equal 比较。 */
	public static final Operator OPERATOR_NOEQUAL = new Operator(5, 30, "!=", "Not Equal");
	public static final int OPERATOR_NOEQUAL_CODE = 5; 
	/** '>=' great than or equal 大于等于 */
	public static final Operator OPERATOR_GREATOREQUAL = new Operator(6, 30, ">=", "Great or Equal");
	public static final int OPERATOR_GREATOREQUAL_CODE = 6;
	/** '<=' less than and equal 小于等于 */
	public static final Operator OPERATOR_LESSOREQUAL = new Operator(7, 30, "<=", "Less or Equal");
	public static final int OPERATOR_LESSOREQUAL_CODE = 7;
	/** '>' great 大于。 */
	public static final Operator OPERATOR_GREAT = new Operator(8, 30, ">", "Great");
	public static final int OPERATOR_GREAT_CODE = 8;
	/** '<' less 小于。 */
	public static final Operator OPERATOR_LESS = new Operator(9, 30, "<", "Less");
	public static final int OPERATOR_LESS_CODE = 9;

	/** '+', 加。 - level 40. */
	public static final Operator OPERATOR_PLUS = new Operator(10, 40, "+", "Plus");
	public static final int OPERATOR_PLUS_CODE = 10;
	/** '-', 减。 */
	public static final Operator OPERATOR_SUBSTRACT = new Operator(11, 40, "-", "Subtract");
	public static final int OPERATOR_SUBSTRACT_CODE = 11;
	
	/** '*', 乘。 - level 50. */
	public static final Operator OPERATOR_MULTIPLY = new Operator(12, 50, "*", "Multiply");
	public static final int OPERATOR_MULTIPLY_CODE = 12; 
	/** '/', 除。 */
	public static final Operator OPERATOR_DIVIDE = new Operator(13, 50, "/", "Divide");
	public static final int OPERATOR_DIVIDE_CODE = 13;
	/** '%', 求余。*/
	public static final Operator OPERATOR_MODULUS = new Operator(14, 50, "%", "Modulus");
	public static final int OPERATOR_MODULUS_CODE = 14;

	/** '!', 求非 其为一元操作符。 */
	public static final Operator OPERATOR_NOT = new Operator(15, 60, "!", "Not");
	public static final int OPERATOR_NOT_CODE = 15; 
	
	/** 一个简单的映射表，从 symbol -> operator */
	protected static final java.util.HashMap<String, Operator> oper_map = 
		new java.util.HashMap<String, Operator>();
	
	protected static final void putOperMap(Operator oper) {
		oper_map.put(oper.getSymbol(), oper);
	}
	
	static {
		putOperMap(OPERATOR_OR);
		putOperMap(OPERATOR_AND);
		putOperMap(OPERATOR_XOR);
		
		putOperMap(OPERATOR_EQUAL);
		putOperMap(OPERATOR_NOEQUAL);
		putOperMap(OPERATOR_GREATOREQUAL);
		putOperMap(OPERATOR_LESSOREQUAL);
		putOperMap(OPERATOR_GREAT);
		putOperMap(OPERATOR_LESS);
		
		putOperMap(OPERATOR_PLUS);
		putOperMap(OPERATOR_SUBSTRACT);
		
		putOperMap(OPERATOR_MULTIPLY);
		putOperMap(OPERATOR_DIVIDE);
		putOperMap(OPERATOR_MODULUS);
	}
	
	/** 操作符编码。 */
	private final int operator;
	/** 优先等级。 */
	private final int level;
	/** 符号表示。 */
	private final String symbol;
	/** 描述。 */
	private final String desc;
	
	/**
	 * 使用指定的运算符编码 operator、优先级别、符号表示和描述构造一个 Operator 的实例。
	 * @param operator
	 * @param level
	 * @param symbol
	 * @param desc
	 */
	protected Operator(int operator, int level, String symbol, String desc) {
		this.operator = operator;
		this.level = level;
		this.symbol = symbol;
		this.desc = desc;
	}

	/**
	 * 从符号转换为一个二元操作符。
	 * @param symbol
	 * @return
	 */
	public static Operator fromSymbol(String symbol) {
		return oper_map.get(symbol);
	}
	
	/**
	 * 从符号转换为一个一元操作符。
	 * @param symbol
	 * @return
	 */
	public static Operator fromSymbolUnion(String symbol) {
		if ("!".equals(symbol))
			return OPERATOR_NOT;
		if ("-".equals(symbol))
			return OPERATOR_SUBSTRACT;
		if ("+".equals(symbol))
			return OPERATOR_PLUS;
		return null;
	}
	
	/** 获得友好字符串表示。 */
	@Override public String toString() {
		return "#Oper{" + symbol + "}";
	}
	
	/** 比较是否相同。 */
	@Override public boolean equals(Object val) {
		if (val == null) return false;
		if (!(val instanceof Operator)) return false;
		return this.operator == ((Operator)val).operator;
	}
	
	/**
	 * 获得操作符编码。
	 * @return
	 */
	public int getOperator() {
		return this.operator;
	}
	
	/**
	 * 获得此操作符的优先级别。
	 * @return
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * 获得操作符的 java/c++ 语法符号。
	 * @return
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * 获得操作符的说明。
	 * @return
	 */
	public String getDesc() {
		return this.desc;
	}
}
