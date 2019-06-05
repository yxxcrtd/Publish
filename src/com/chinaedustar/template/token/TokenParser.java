package com.chinaedustar.template.token;

/**
 * 符号分析器。其支持分析 iden, number, symbol, string 等几种基本标记。
 * 
 * @author liujunxing
 */
public class TokenParser extends AbstractParserBase {
	/**
	 * 构造一个符号分析器。
	 * @param content
	 */
	public TokenParser(String content) {
		super(content);
	}

	/**
	 * 使用指定的内容和范围构造一个 TokenParser，构造之后可以直接用 getNextToken() 方法来解析符号。
	 * @param content
	 * @param start_pos
	 * @param end_pos
	 */
	public TokenParser(String content, int start_pos, int end_pos) {
		super(content, start_pos, end_pos);
	}

	/**
	 * 从指定内容的开始到结束位置分析第一个符号出来。
	 * @param start_pos
	 * @param end_pos
	 * @return
	 */
	public Token getFirstToken(int start_pos, int end_pos) {
		super.setLimit(start_pos, end_pos);
		return internalGetToken();
	}
	
	/**
	 * 继续分析下一个符号。调用此函数之前要么调用过 getFirstToken(), 要么
	 *   使用 TokenParser(content, start, end) 初始化过解析器。
	 * @return
	 */
	public Token getNextToken() {
		return internalGetToken();
	}
	
	private Token internalGetToken() {
		// 试图跳过空白位置。
		if (skipWhitespace() == false) 
			return Token.END_OF_TOKEN; 
		
		// 获得当前字符，如果是标识符开始则分析标识符。
		char cur_ch = super.getCurrentChar();
		if (isIdenStart(cur_ch))
			return getIdenToken();
		
		// 如果是字符串开始则分析字符串。
		if (this.isStringStart(cur_ch)) 
			return getStringToken();
		
		// 如果是数字开始则分析数字。
		if (this.isDigitStart(cur_ch))
			return getNumberToken();
		
		// 当作符号处理。
		return getSymbolToken();
	}
	
	/**
	 * 解析一个标识符符号。
	 * @return
	 */
	private Token getIdenToken() {
		int iden_start = this.scan_ptr;
		
		while (!isEof()) {
			 char cur_ch = super.getCurrentChar();
			 if (isIdenPart(cur_ch) == false) break;
			 ++this.scan_ptr;
		}
		
		String iden = this.getContent().substring(iden_start, this.scan_ptr);
		return new Token(Token.TOKEN_TYPE_IDEN, iden, iden_start, scan_ptr);
	}
	
	/**
	 * 解析一个字符串。 'xxx', "xxx"
	 * @return
	 */
	private Token getStringToken() {
		int string_start = this.scan_ptr;
		String string = parseString();
		return new Token(Token.TOKEN_TYPE_CONSTANT, string, string_start, this.scan_ptr);
	}
	
	/**
	 * 
	 * @return
	 */
	private String parseString() {
		// 字符串值。
		StringBuilder strbuf = new StringBuilder();
		char string_quote = super.getCurrentChar();
		++this.scan_ptr;
		
		while (!isEof()) {
			char cur_ch = super.getCurrentChar();
			if (cur_ch == string_quote) {
				// 遇到结束字符，读取完成。
				++this.scan_ptr;
				return strbuf.toString();
			} else if (cur_ch == '\\') {
				++this.scan_ptr;		// accept '\'
				if (isEof()) break;		// bad end
				char next_ch = super.getCurrentChar();
				++this.scan_ptr;		// accept next char
				if (next_ch == 'b') strbuf.append('\b');
				else if (next_ch == 't') strbuf.append('\t');
				else if (next_ch == 'n') strbuf.append('\n');
				else if (next_ch == 'f') strbuf.append('\f');
				else if (next_ch == 'r') strbuf.append('\r');
				else if (next_ch == '\"') strbuf.append('\"');
				else if (next_ch == '\'') strbuf.append('\'');
				else if (next_ch == '\\') strbuf.append('\\');
				else strbuf.append(next_ch);	// java 会产生编译错，我们不要求如此严格，原样给出即可。
			} else {
				++this.scan_ptr;		// accept
				strbuf.append(cur_ch);
			}
		}
		
		return strbuf.toString();
	}
	
	/**
	 * 读取一个数字符号。 格式为 [+/-]digit[.[digit]][e][+|-]digit
	 * @return
	 */
	private Token getNumberToken() {
		int number_start = this.scan_ptr;
		boolean is_float = false;		// 是否是浮点数，我们尽量用整数。
		double the_value = 0;			// 实际的值。
		
		// 读取小数点前面部分。
		char cur_ch = ' ';
		while (!isEof()) {
			cur_ch = super.getCurrentChar();
			if (cur_ch <= '9' && cur_ch >= '0') {
				the_value = the_value*10 + (cur_ch - '0');	// maybe overflow?
			} else
				break;
			++this.scan_ptr;
		}
		if (isEof())
			return genNumberToken(number_start, the_value, is_float);
		
		// digit.xx - 读取小数点部分，并且强制认为是浮点数。
		if (cur_ch == '.') {
			is_float = true;
			++this.scan_ptr;
			double power = 10;
			while (!isEof()) {
				cur_ch = super.getCurrentChar();
				if (cur_ch <= '9' && cur_ch >= '0') {
					the_value = the_value + (cur_ch - '0') / power;
					power *= 10;
				} else 
					break;
				++this.scan_ptr;
			}
			if (isEof())
				return genNumberToken(number_start, the_value, is_float);
		}
		
		// 可能还有 e 部分。读取该部分，并且强制认为是浮点数。
		if (cur_ch == 'e' || cur_ch == 'E') {
			is_float = true;
			++this.scan_ptr;
			if (isEof()) {
				// TODO: warn(illeage number format, ignored)
				return genNumberToken(number_start, the_value, is_float);
			}
			cur_ch = super.getCurrentChar();
			int factor = 1;		// 指数的正负。
			if (cur_ch == '+' || cur_ch == '-') {
				++this.scan_ptr;
				if (isEof())  // TODO: 其实这里有一个错误。
					return genNumberToken(number_start, the_value, is_float);
				factor = (cur_ch == '-') ? -1 : +1;
			}
			
			double power = 0;
			while (!isEof()) {
				cur_ch = super.getCurrentChar();
				if (cur_ch <= '9' && cur_ch >= '0') {
					power = power*10 + (cur_ch - '0');
				} else
					break;
				++this.scan_ptr;
			}
			power *= factor;
			the_value = the_value * java.lang.Math.pow(10, power);
		}
		
		return genNumberToken(number_start, the_value, is_float);
	}
	
	/**
	 * 产生一个数字符号。
	 * @param the_value
	 * @param is_float
	 * @return
	 */
	private Token genNumberToken(int number_start, double the_value, boolean is_float) {
		java.lang.Double double_value = new java.lang.Double(the_value);
		
		// 如果确定是浮点数，则返回浮点数。
		if (is_float) {
			return new Token(Token.TOKEN_TYPE_CONSTANT, double_value, number_start, this.scan_ptr);
		}
		
		// 否则尽量按照整数来返回。
		if (the_value <= Integer.MAX_VALUE && the_value >= Integer.MIN_VALUE) {
			return new Token(Token.TOKEN_TYPE_CONSTANT, new Integer(double_value.intValue()), number_start, this.scan_ptr);
		}
		
		// 超出整数范围，按照 double 返回。
		return new Token(Token.TOKEN_TYPE_CONSTANT, double_value, number_start, this.scan_ptr);
	}
	
	/**
	 * 读取一个符号符号。
	 * @return
	 */
	private Token getSymbolToken() {
		int symbol_start = this.scan_ptr;
		char cur_ch = super.getCurrentChar();
		++this.scan_ptr;
		// 单个字符的符号。
		if (isEof())
			return new Token(Token.TOKEN_TYPE_SYMBOL, Character.toString(cur_ch), 
					symbol_start, this.scan_ptr);
		
		// 双字符的符号，如 ==, !=, && etc.
		char next_ch = super.getCurrentChar();
		if (isDoubleSymbol(cur_ch, next_ch)) {
			++this.scan_ptr;
			return new Token(Token.TOKEN_TYPE_SYMBOL, new String(new char[] {cur_ch, next_ch}), 
					symbol_start, this.scan_ptr);
		}
		
		// 单个字符的符号，如 +, ., <, =, >, *, ! 等。
		return new Token(Token.TOKEN_TYPE_SYMBOL, Character.toString(cur_ch), 
				symbol_start, this.scan_ptr);
	}
	
	/**
	 * 判断所给两个字符是否是一个双字符的符号。
	 * @param ch_first
	 * @param ch_next
	 * @return
	 */
	protected boolean isDoubleSymbol(char ch_first, char ch_next) {
		// ==
		if (ch_first == '=' && ch_next == '=') return true;
		// >=
		if (ch_first == '>' && ch_next == '=') return true;
		// <=
		if (ch_first == '<' && ch_next == '=') return true;
		// !=
		if (ch_first == '!' && ch_next == '=') return true;
		// &&
		if (ch_first == '&' && ch_next == '&') return true;
		// || 
		if (ch_first == '|' && ch_next == '|') return true;
		
		return false;
	}
	
	/**
	 * 判定指定字符是否是一个数字的开始。
	 * @param ch
	 * @return
	 */
	public boolean isDigitStart(char ch) {
		return Character.isDigit(ch);
	}
	
	/**
	 * 判定指定字符是否是一个字符串的开始。
	 * @param ch
	 * @return
	 */
	public boolean isStringStart(char ch) {
		return ch == '\'' || ch == '\"';
	}
}
