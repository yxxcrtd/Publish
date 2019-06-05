package com.chinaedustar.publish.util;

/**
 * 一些用到的字符串辅助函数。
 * @author liujunxing
 *
 */
public class StringUtil {
	private StringUtil() {}
	
	/**
	 * 得到指定字符串的左边 len 个长度的子串。
	 * @param str
	 * @param len
	 * @return
	 */
	public static final String left(String str, int len) {
		if (str == null || str.length() == 0) return str;
		if (len <= 0) return "";
		if (len >= str.length()) return str;
		return str.substring(0, len);
	}
}
