package com.chinaedustar.template.core;

import com.chinaedustar.template.token.AbstractParserBase;

/**
 * 属性解析器，用于解析 html/xml 的属性列表。
 * 
 * @author wangyi
 */
public class AttributeParser extends AbstractParserBase {
	/**
	 * 使用指定的内容初始化一个 AttributeParser 的实例。
	 * @param content
	 */
	public AttributeParser(String content) {
		super(content);
	}
	
	/**
	 * 在指定的限定范围内解析内容的属性。
	 * @return 返回属性集合。
	 */
	public AttributeCollection parse(int start_pos, int end_pos) {
		super.setLimit(start_pos, end_pos);
		/*
		 * 1、得到属性key，遇到 = 结束，如果是只有 key ，没有 value ，遇到空格后的下一个字符结束；
		 * 2、得到属性value，遇到对应的开始符（',"）结束；
		 * 		如果没有开始符，遇到 空格 结束，遇到 < | > | } 意外结束；
		 * 3、属性分析到内容的结尾处，结束解析过程；
		 * 		遇到意外结束，结束分析过程。 
		 */
		AttributeCollection attr_list = new AttributeCollection();
		while (!isEof()) {
			Attribute attr = parseAttribute();
			if (attr != null) {
				attr_list.addAttribute(attr);
			} else {
				break;
			}
		}
		return attr_list;
	}
	
	/**
	 * 解析一个单独的属性。
	 * @return 返回 null 表示遇到了非属性的字符，此时一般解析结束。
	 */
	private Attribute parseAttribute() {
		// 跳过空格。
		if (skipWhitespace() == false) return null;
		
		// 解析 key, key 一定是一个 iden.
		String key = parseKey();
		if (key == null) return null;
		
		// 跳过空格。
		if (skipWhitespace() == false) {
			return new Attribute(key, null);		// 无 value 的属性。
		}
		
		char cur_ch = super.getCurrentChar();
		if (cur_ch == '=') {
			// 有 attribute 部分。
			++this.scan_ptr;		// accept '='
			String value = parseValue();
			return new Attribute(key, value);
		}
		
		// 否则不是 '='，要么是下一个 key, 要么结束了。
		return new Attribute(key, null);
	}
	
	/**
	 * 解析一个单独的 key, key 是一个 iden.
	 * @return
	 */
	private String parseKey() {
		return super.parseIden();
	}
	
	/**
	 * 解析一个 value 部分。
	 * @return
	 */
	private String parseValue() {
		if (super.skipWhitespace() == false) return null;
		
		char cur_ch = super.getCurrentChar();
		if (cur_ch == '\'' || cur_ch == '\"') {
			++this.scan_ptr;		// accept ', "
			return parseQuoteValue(cur_ch);
		}
		
		// 属性里面不考虑转义字符。 空白 < > } ' " 做为结束字符。
		int value_start = this.scan_ptr;
		while (!isEof()) {
			cur_ch = super.getCurrentChar();
			if (super.isWhitespace(cur_ch) || isEndOfValue(cur_ch)) break;
			++this.scan_ptr;		// accept 
		}
		
		return super.getContent().substring(value_start, this.scan_ptr);
	}
	
	/**
	 * 解析用 ', " 括起来的字符串。其支持简单转义，\', \"，其它原样输出。
	 * @param ch_quote
	 * @return
	 */
	private String parseQuoteValue(char ch_quote) {
		StringBuffer strbuf = new StringBuffer();
		while (!isEof()) {
			char cur_ch = super.getCurrentChar();
			if (cur_ch == ch_quote) {
				++this.scan_ptr;		// accept, end of value
				break;
			} else if (cur_ch == '\\') {
				++this.scan_ptr;		// accept, 转义开始
				if (isEof()) break;		// 提前结束?
				cur_ch = super.getCurrentChar();
				++this.scan_ptr;		// accept
				// 以下的逻辑指如果遇到 \', \" 序列，则变成 ', ", 否则原样如 \n, \t
				if (cur_ch != '\'' && cur_ch != '\"')
					strbuf.append('\\');
				strbuf.append(cur_ch);
			} else {
				++this.scan_ptr;		// accept
				strbuf.append(cur_ch);
			}
		}
		return strbuf.toString();
	}
	
	/**
	 * 是否遇到一个属性值结束的字符。 '<', '>', '}', ', " 都做为属性结束字符看待。
	 * @param cur_ch
	 * @return
	 */
	private final boolean isEndOfValue(char cur_ch) {
		if (cur_ch == '<' || cur_ch == '>' || cur_ch == '}' || 
				cur_ch == '\'' || cur_ch == '\"')
			return true;
		return false;
	}
}
