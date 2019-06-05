package com.chinaedustar.template;

/**
 * 内容输出器。此输出器和 java.io.Writer 的方法完全一致，不同的地方是
 *   其不要求抛出需要检查的 java.io.IOException，取而代之的是抛出
 *   一个 RuntimeException 或其子类。
 * 
 * @author liujunxing
 */
public interface Writer {
	/** 
	 * 将指定字符追加到此 writer。
	 */
	public Writer append(char c); 
    
	/**
	 * 将指定字符序列追加到此 writer。 
	 */
	public Writer append(CharSequence csq); 
    
	/**
	 * 将指定字符序列的子序列追加到此 writer.Appendable。 
	 */
	public Writer append(CharSequence csq, int start, int end); 
    
	/**
	 * 关闭此流，但要先刷新它。 
	 */
	public void close(); 
    
	/**
	 * 刷新此流。
	 */
	public void flush(); 
    
	/**
	 * 写入字符数组。 
	 */
	public void write(char[] cbuf); 
    
	/**
	 * 写入字符数组的某一部分。 
	 */
	public void write(char[] cbuf, int off, int len); 
    
	/**
	 * 写入单个字符。 
	 */
	public void write(int c); 
    
	/**
	 * 写入字符串。 
	 * @param str
	 */
	public void write(String str); 

	/**
	 * 写入字符串的某一部分。
	 * @param str
	 * @param off
	 * @param len
	 */
	public void write(String str, int off, int len);  
}
