package com.chinaedustar.template.token;

/**
 * 表示一个抽象的解析器基类。
 * 
 * @author liujunxing
 */
public abstract class AbstractParserBase {
	/** 要分析的内容。 */
	private String content;

	/** 开始分析的位置。(final) */
	private int start_pos;
	
	/** 结束分析的位置。(final) */
	private int end_pos;
	
	/** 当前扫描位置。 */
	protected int scan_ptr;

	/**
	 * 使用指定的要解析的内容初始化一个 AbstractParser 的实例。
	 * 其开始位置 start_pos 初始为 0, end_pos 初始为结尾, scan_ptr 初始为 0。 
	 * @param content
	 */
	public AbstractParserBase(String content) {
		this.content = content;
		this.start_pos = 0;
		this.end_pos = content.length();
		this.scan_ptr = 0;
	}
	
	/**
	 * 使用指定的要解析的内容、开始位置初始化一个 AbstractParser 的实例。
	 * end_pos 初始为结尾, scan_ptr 初始为 start_pos。 
	 * @param content
	 */
	public AbstractParserBase(String content, int start_pos) {
		this.content = content;
		this.start_pos = start_pos;
		this.end_pos = content.length();
		this.scan_ptr = start_pos;
	}
	
	/**
	 * 使用指定的要解析的内容、开始位置、结束位置初始化一个 AbstractParser 的实例。
	 * scan_ptr 初始为 start_pos。 
	 * @param content
	 */
	public AbstractParserBase(String content, int start_pos, int end_pos) {
		this.content = content;
		this.start_pos = start_pos;
		this.end_pos = end_pos;
		this.scan_ptr = start_pos;
	}

	/**
	 * 使用另一个解析器初始化此解析器，开始位置设置为 another_parser 的 scan_ptr，
	 * 内容和结束位置和 another_parser 相同。
	 * @param another_parser
	 */
	public AbstractParserBase(AbstractParserBase another_parser) {
		this.content = another_parser.content;
		this.start_pos = another_parser.scan_ptr;
		this.end_pos = another_parser.end_pos;
		this.scan_ptr = this.start_pos;
	}
	
	/**
	 * 获得分析的开始限定位置。
	 * @return
	 */
	public int getStartPos() {
		return this.start_pos;
	}
	
	/**
	 * 获得结束限定位置。
	 * @return
	 */
	public int getEndPos() {
		return this.end_pos;
	}
	
	/**
	 * 获得当前扫描到的分析位置。
	 * @return
	 */
	public int getCurrScanPos() {
		return this.scan_ptr;
	}
	
	/**
	 * 设置扫描限定区域。
	 * @param start_pos
	 * @param end_pos
	 */
	protected final void setLimit(int start_pos, int end_pos) {
		this.start_pos = start_pos;
		this.end_pos = end_pos;
		this.scan_ptr = start_pos;
	}

	/**
	 * 获得当前扫描位置的字符。
	 * @return
	 */
	protected char getCurrentChar() {
		return this.content.charAt(this.scan_ptr);
	}
	
	/**
	 * 获得整个内容。
	 * @return
	 */
	protected String getContent() {
		return this.content;
	}

	/**
	 * 从当前扫描位置开始跳过空白字符。不越过 end_pos
	 * @return
	 */
	protected final boolean skipWhitespace() {
		while (this.scan_ptr < this.end_pos) {
			char cur_ch = this.content.charAt(this.scan_ptr);
			if (isWhitespace(cur_ch) == false)
				return true;
			++this.scan_ptr;
		}
		return false;
	}

	/**
	 * 从当前位置解析一个标识符出来。
	 * @return null 表示这不是一个标识符，否则返回此标识符的字符串。
	 */
	protected final String parseIden() {
		char cur_ch = getCurrentChar();
		if (isIdenStart(cur_ch) == false) return null;	// 不是一个标识符。
		
		int iden_start = this.scan_ptr;
		++this.scan_ptr;
		while (!isEof()) {
			 cur_ch = getCurrentChar();
			 if (isIdenPart(cur_ch) == false) break;
			 ++this.scan_ptr;
		}
		
		String iden = this.content.substring(iden_start, this.scan_ptr);
		return iden;
	}
	
	/**
	 * 判定一个字符是否是一个空白字符。
	 * @param ch
	 * @return
	 */
	public static boolean isWhitespace(char ch) {
		return Character.isWhitespace(ch);
	}
	
	/**
	 * 判断指定字符是否是一个标识符的开始。
	 * @param ch
	 * @return
	 */
	public static boolean isIdenStart(char ch) {
		return Character.isUnicodeIdentifierStart(ch);
	}
	
	/**
	 * 判定指定字符是否是一个标识符的非开始部分。
	 * @param ch
	 * @return
	 */
	public static boolean isIdenPart(char ch) {
		if (isIdenStart(ch)) return true;
		return Character.isUnicodeIdentifierPart(ch);
	}
	
	/**
	 * 判定扫描指针是否已经到了或者过了末尾。
	 * @return
	 */
	public boolean isEof() {
		return (this.scan_ptr >= this.end_pos);
	}
}
