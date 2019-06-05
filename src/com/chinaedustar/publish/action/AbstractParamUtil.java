package com.chinaedustar.publish.action;

import java.util.List;
import com.chinaedustar.publish.PublishContext;

/**
 * 获取 Request Param 的辅助工具类。
 * 
 * @author liujunxing
 *
 */
public abstract class AbstractParamUtil {
	/**
	 * 得到指定键的请求值。
	 * @param key
	 * @return
	 */
	public abstract String getRequestParam(String key);
	
	/**
	 * 得到指定键的请求值，返回为数组形态。
	 * @param key
	 * @return
	 */
	public abstract String[] getRequestParamValues(String key);
	
	/**
	 * 获得发布系统环境。
	 * @return
	 */
	public abstract PublishContext getPublishContext();
	
	/**
	 * 把一个属性放置到 pageContext 里面，可选实现。
	 * @param key
	 * @param val
	 */
	public void setAttribute(String key, Object val) {
		
	}
	
	/**
	 * 判定所给的字符串是否是一个整数。
	 * @param val
	 * @return
	 */
	public static boolean isInteger(String val) {
		if (val == null) return false;
		if (val.length() == 0) return false;
		val = val.trim();
		if (val.length() == 0) return false;
		for (int i = 0; i < val.length(); ++i) {
			char c = val.charAt(i);
			if (c == '+' || c == '-') continue;
			if (c > '9' || c < '0') return false;
		}
		try {
			Integer.parseInt(val);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	/**
	 * 判定所给的字符串是否是一个空字符串，空字符串 == null 或其 trim() == ""。
	 * @param val
	 * @return
	 */
	public static boolean isEmptyString(String val) {
		if (val == null) return true;
		if (val.length() == 0) return true;
		if (val.trim().length() == 0) return true;
		return false;
	}

	/**
	 * 安全的获取请求值，如果没有或非法则返回缺省值 0。
	 * @param key
	 * @return
	 */
	public Integer safeGetIntParam(String key) {
		return safeGetIntParam(key, 0);
	}
	
	/**
	 * 安全的获取请求值，如果没有或非法则返回缺省值。
	 * @param key
	 * @param defval
	 * @return
	 */
	public Integer safeGetIntParam(String key, Integer defval) {
		String val = getRequestParam(key);
		if ("null".equalsIgnoreCase(val)) 
			return null;
		if (isInteger(val) == false)
			return defval;
		return Integer.parseInt(val);
	}

	/**
	 * 安全的获取boolean型的请求值，如果没有或者非法返回缺省值 null 。
	 * @param key request.getParameter 中的 Key 。
	 * @return 
	 */
	public Boolean safeGetBooleanParam(String key) {
		return safeGetBooleanParam(key, null);
	}
	
	/**
	 * 安全的获取boolean型的请求值，如果没有或者非法返回给定的默认值省。
	 * @param key 
	 * @param defval
	 * @return
	 */
	public Boolean safeGetBooleanParam(String key, Boolean defval) {
		String val = getRequestParam(key);
		if (val == null || val.trim().length() < 1)
			return defval;
		val = val.trim();
		if (val.equals("0"))
			return false;
		else if (val.equals("1"))
			return true;
		else if (val.equalsIgnoreCase("yes"))
			return true;
		else if (val.equalsIgnoreCase("no"))
			return false;
		else if ("null".equalsIgnoreCase(val)) 
			return null;
		else {
			return Boolean.parseBoolean(val);
		}
	}
	
	/**
	 * 判断给定键的请求参数是否是一个整数。
	 * @param key
	 * @return
	 */
	public boolean isIntegerParam(String key) {
		String val = getRequestParam(key);
		return isInteger(val);
	}
	
	/**
	 * 给定的字符串是否是合法的 boolean 型。
	 * @param val 内容字符串。
	 * @return 是否是合法的 boolean 型字符串。
	 */
	public boolean isBoolean(String val) {
		if (val == null) {
			return false;
		} else if (val.trim().length() < 1) {
			return false;
		} else {
			try {
				Boolean.parseBoolean(val);
			} catch (Exception ex) {
				return false;
			}
			return true;
		}		
	}

	/**
	 * 给定的字符串是否全部由英文字母组成。
	 * 注意：如果字符串为null，将返回false。
	 * @param val
	 * @return
	 * 注意：原来名字 isEnglish()
	 */
	public static boolean isEnglishName(String val) {
		if (val == null || val.length() == 0) return false;
		
		// 检查频道目录是否为英文
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);
			if (ch < 'A' || (ch > 'Z' && ch < 'a') || ch > 'z') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否是空字符串，null, "", "  " 。
	 * @param val 内容字符串。
	 * @return
	 */
	public boolean isBlank(String val) {
		if (val == null || val.trim().length() < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获得指定键的字符串参数。
	 * @param key
	 * @return
	 */
	public String safeGetStringParam(String key) {
		return safeGetStringParam(key, "");
	}
	
	/**
	 * 获得指定键的字符串参数，如果没有则使用缺省值 defval。
	 * @param key
	 * @return
	 */
	public String safeGetStringParam(String key, String defval) {
		String val = getRequestParam(key);
		if (val == null) return defval;
		return val.trim();
	}

	/**
	 * 获得指定键的参数，并转换为一个 int[] 类型。
	 * 此函数主要用于 id 数组类型。
	 * @param key
	 * @return 返回 List&lt;Integer&gt; 整数集合，如果没有任何数据则返回空集合。
	 */
	public List<Integer> safeGetIntValues(String key) {
		// 返回一个数组，如 {'1', '2', '3, 4'}
		String[] values = getRequestParamValues(key);
		java.util.ArrayList<Integer> id_array = new java.util.ArrayList<Integer>();
		if (values == null || values.length == 0) return id_array;
		
		for (int i = 0; i < values.length; ++i) {
			if (values[i] == null || values[i].length() == 0) continue;
			// 每个单项都可以是 '3, 4, 5' 的 标识数组。
			String[] val_split = values[i].split(",");
			for (int j = 0; j < val_split.length; ++j) {
				try {
					id_array.add(Integer.parseInt(val_split[j]));
				} catch (NumberFormatException ex) {
					// ignore
				}
			}
		}
		return id_array;
	}

	/**
	 * 安全的获得日期参数。
	 * @param key
	 * @return
	 */
	public java.util.Date safeGetDate(String key) {
		return safeGetDate(key, new java.util.Date());
	}
	
	/**
	 * 安全的获得日期参数。
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public java.util.Date safeGetDate(String key, java.util.Date defval) {
		String temp = safeGetStringParam(key);
		if (temp == null || temp.length() == 0) 
			return defval;
		
		// 尝试用 SimpleDateFormat 解析。
		try {
			return new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(temp);
		} catch (Exception ex) {
			// ignore
		}
		
		// 尝试用 DateFormat 解析。
		try {
			return java.text.DateFormat.getInstance().parse(temp);
		} catch (Exception ex) {
			// ignore
		}
		
		// 不行用 Date.parse 方法。
		try {
			return new java.util.Date(java.util.Date.parse(temp));
		} catch (Exception ex) {
			// ignore
		}
		
		// 还不行吗？
		return defval;
	}
}
