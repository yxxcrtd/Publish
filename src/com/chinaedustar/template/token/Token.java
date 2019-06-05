package com.chinaedustar.template.token;

/**
 * 定义模板语言中使用的基本符号类型。用于表达式解析中。
 * 
 * @author liujunxing
 */
public class Token {
	/** 符号结束了。 */
	public static final int TOKEN_TYPE_EOT = 0;
	
	/** 标识符类型。 */
	public static final int TOKEN_TYPE_IDEN = 1;
	
	/** 一个常量值，可以是 integer, double, string, (optional)date 几种。 */
	public static final int TOKEN_TYPE_CONSTANT = 2;
	
	/** 符号，例如 '=', '+', '.', '>=', '<=', '!=', '!', '&&', ',' etc. */
	public static final int TOKEN_TYPE_SYMBOL = 3;
	
	/** 表示结束。 */
	public static final Token END_OF_TOKEN = new Token(TOKEN_TYPE_EOT, "{EOT}", -1, -1);
	
	/** 此符号的类型，取值为 TOKEN_TYPE_xxx。 */
	private int token_type;
	
	/** 解析出来的符号值。 */
	private Object token_value;
	
	/** 符号开始位置。 */
	private int start_pos;
	
	/** 符号结束位置。 */
	private int end_pos;
	
	/**
	 * 使用指定参数构造一个符号。
	 * @param token_type
	 * @param token_value
	 */
	public Token(int token_type, Object token_value, int start_pos, int end_pos) {
		this.token_type = token_type;
		this.token_value = token_value;
		this.start_pos = start_pos;
		this.end_pos = end_pos;
	}
	
	public String toString() {
		return "Token{type=" + getTokenTypeName() + ",val=" + this.token_value + "}";
	}
	
	/**
	 * 判定是否是 end_of_token.
	 * @return
	 */
	public boolean isEOT() {
		return (this.token_type == TOKEN_TYPE_EOT);
	}
	
	/**
	 * 获得符号的类型。
	 * @return
	 */
	public int getTokenType() {
		return this.token_type;
	}
	
	/**
	 * 获得符号类型的友好字符串表示。
	 * @return
	 */
	public String getTokenTypeName() {
		switch (this.token_type) {
		case TOKEN_TYPE_EOT: return "EOT";
		case TOKEN_TYPE_IDEN: return "IDEN";
		case TOKEN_TYPE_CONSTANT: return "CONST";
		case TOKEN_TYPE_SYMBOL: return "SYMBOL";
		default: return "UNKNOWN";
		}
	}
	
	/**
	 * 获得符号的值。
	 * @return
	 */
	public Object getTokenValue() {
		return this.token_value;
	}
	
	/**
	 * 按照标识符类型获得符号值。调用者自己需要保证此符号代表的是一个标识符。
	 * @return
	 */
	public String getIdentifier() {
		return (String)this.token_value;
	}
	
	/**
	 * 获得此符号表示的常量值。调用者需要自己保证这是一个常量。
	 * @return
	 */
	public Object getConstant() {
		return this.token_value;
	}

	/**
	 * 获得符号字符。调用者需要自己保证此符号代表的是一个符号。
	 * @return
	 */
	public String getSymbol() {
		return (String)this.token_value;
	}

	/**
	 * 获得此符号的开始位置。
	 * @return
	 */
	public int getStartPos() {
		return this.start_pos;
	}
	
	/**
	 * 获得此符号的结束位置。
	 * @return
	 */
	public int getEndPos() {
		return this.end_pos;
	}
	
	/**
	 * 判定当前符号是否是一个常量。
	 * @return
	 */
	public boolean isConstant() {
		return this.token_type == TOKEN_TYPE_CONSTANT;
	}
	
	/**
	 * 判定当前符号是否是一个标识符。
	 * @return
	 */
	public boolean isIdentifier() {
		return this.token_type == TOKEN_TYPE_IDEN;
	}

	/**
	 * 判定当前符号是否是一个标识符，且其值为 the_iden。
	 * @return
	 */
	public boolean isIdentifier(String the_iden) {
		if (this.token_type != TOKEN_TYPE_IDEN) return false;
		return the_iden.equals(this.token_value); 
	}

	/**
	 * 判定当前符号是否是符号。
	 * @param symbol
	 * @return
	 */
	public boolean isSymbolToken() {
		return (this.token_type == TOKEN_TYPE_SYMBOL);
	}

	/**
	 * 判定当前符号是否是指定参数的符号。
	 * @param symbol
	 * @return
	 */
	public boolean isSymbolToken(String symbol) {
		if (this.token_type != TOKEN_TYPE_SYMBOL) return false;
		return symbol.equals(this.token_value);
	}
}
