package com.chinaedustar.template.core;

import com.chinaedustar.template.Writer;

/**
 * 使用 StringBuilder 实现的一个 Writer。
 * 
 * @author liujunxing
 */
public class StringWriter implements Writer {
	/** 实际的数据缓存区。 */
	public StringBuilder strbuf = new StringBuilder();
	
	public StringWriter() {
		
	}
	
	/**
	 * 获得已经写入的所有内容。
	 */
	@Override public String toString() {
		return strbuf.toString();
	}
	
	/** 
	 * 将指定字符追加到此 writer。
	 */
	public Writer append(char c) {
		strbuf.append(c);
		return this;
	}
    
	/**
	 * 将指定字符序列追加到此 writer。 
	 */
	public Writer append(CharSequence csq) {
		strbuf.append(csq);
		return this;
	}
    
	/**
	 * 将指定字符序列的子序列追加到此 writer.Appendable。 
	 */
	public Writer append(CharSequence csq, int start, int end) {
		strbuf.append(csq, start, end);
		return this;
	}
    
	/**
	 * 关闭此流，但要先刷新它。 
	 */
	public void close() {
		// donothing
	}
    
	/**
	 * 刷新此流。
	 */
	public void flush() {
		// donothing
	}
    
	/**
	 * 写入字符数组。 
	 */
	public void write(char[] cbuf) {
		strbuf.append(cbuf);
	}
    
	/**
	 * 写入字符数组的某一部分。 
	 */
	public void write(char[] cbuf, int off, int len) {
		strbuf.append(cbuf, off, len);
	}
    
	/**
	 * 写入单个字符。 
	 */
	public void write(int c) {
		strbuf.append(c);
	}
    
	/**
	 * 写入字符串。 
	 * @param str
	 */
	public void write(String str) {
		strbuf.append(str);
	}

	/**
	 * 写入字符串的某一部分。
	 * @param str
	 * @param off
	 * @param len
	 */
	public void write(String str, int off, int len) {
		strbuf.append(str, off, len);
	}

	/**
	 * 内部使用的，合并两个 StringWriter。
	 * @param another
	 * @return
	 */
	public StringWriter append(StringWriter another) {
		this.strbuf.append(another.strbuf);
		return this;
	}
}
