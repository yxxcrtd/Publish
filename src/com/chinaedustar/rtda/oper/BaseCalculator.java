package com.chinaedustar.rtda.oper;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.chinaedustar.rtda.model.OperatorOverrideModel;

/**
 * 基本计算功能。
 * 
 * @author liujunxing
 */
public class BaseCalculator {
	/**
	 * 安全的执行数字解析。
	 * @param s
	 * @return
	 */
	public static double safeParseDouble(String s) {
		if (s == null || "".equals(s)) return 0;
		if (s.charAt(0) <= '9' && s.charAt(0) >= '0') {
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException ex) {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * 安全的执行 boolean 解析。
	 * @param s
	 * @return
	 */
	public static boolean safeParseBoolean(Number n) {
		// 数字 != 0 为真， == 0 为假。
		if (n instanceof Integer || n instanceof Long 
				|| n instanceof Byte || n instanceof Short)
			return n.longValue() != 0;
		
		// 可能计算有误差，用 0.000001 来比较。
		double double_val = n.doubleValue();
		if (Math.abs(double_val) < 0.0000001) return false;
		return true;
	}
	
	/**
	 * 安全的执行 boolean 解析。
	 * @param s
	 * @return
	 */
	public static boolean safeParseBoolean(String s) {
		if (s == null || "".equals(s)) return false;
		if ("true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s) ||
				"t".equalsIgnoreCase(s) || "y".equalsIgnoreCase(s))
			return true;
		return false;
	}

	/**
	 * 判断指定的对象是否支持指定的运算子。
	 * @param object
	 * @param oper
	 * @return
	 */
	public static boolean isSupportOperator(Object object, String oper) {
		if (!(object instanceof OperatorOverrideModel)) return false;
		return ((OperatorOverrideModel)object).isSupportOperator(oper);
	}

	/**
	 * 转换一个数字为 BigInteger 类型。
	 * @param num
	 * @return
	 */
    public static BigInteger toBigInteger(Number num) {
        return num instanceof BigInteger ? (BigInteger) num : new BigInteger(num.toString());
    }

    /**
     * 转换一个数字为 BigDecimal 类型。
     * @param num
     * @return
     */
    public static BigDecimal toBigDecimal(Number num) {
        return num instanceof BigDecimal ? (BigDecimal) num : new BigDecimal(num.toString());
    }
}
