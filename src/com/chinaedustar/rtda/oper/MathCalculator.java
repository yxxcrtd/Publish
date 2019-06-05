package com.chinaedustar.rtda.oper;

import com.chinaedustar.rtda.model.NumberDataModel;
import com.chinaedustar.rtda.model.OperatorOverrideModel;
import com.chinaedustar.rtda.model.ScalarDataModel;
import com.chinaedustar.rtda.model.StringDataModel;

/**
 * 数值计算的计算器。
 * 
 * @author liujunxing
 */
public class MathCalculator extends BaseCalculator {
	
	/**
	 * 将一个字符串转换为数字。
	 * @param s
	 * @return
	 */
	public static Number toNumber(String s) {
		return ArithmeticEngine.DEFAULT_ENGINE.toNumber(s);
	}
	
	/**
	 * 将一个对象转换为整数。
	 * @param val
	 * @return
	 */
	public static int getIntegerValue(Object value) {
		if (value == null) return 0;
		if (value instanceof Number)
			return ((Number)value).intValue();
		if (value instanceof NumberDataModel)
			return ((NumberDataModel)value).getAsNumber().intValue();
		return MathCalculator.toNumber(value.toString()).intValue();
	}
	
	// === 一元加法 =========================================================
	
	/**
	 * 一元加法。
	 */
	public static Object plus(Object left_value) {
		if (left_value == null) return null;
		
		// 如果数据声称自己支持 + 法运算，则调用其加法运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_PLUS)) {
			return ((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_PLUS, null);
		}
		
		if (left_value instanceof Number || left_value instanceof String)
			return left_value;
		
		// 其它类型的数据我们如何进行一元加法 ??
		return left_value;
	}
	
	// === 一元减法 ===========================================================
	
	public static Object substract(Object left_value) {
		if (left_value == null) return null;
		
		// 如果数据声称自己支持 + 法运算，则调用其加法运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_MINUS)) {
			return ((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_MINUS, null);
		}
		
		if (left_value instanceof Number)
			return substractNumber(0, (Number)left_value);
		if (left_value instanceof NumberDataModel)
			return substractNumber(0, ((NumberDataModel)left_value).getAsNumber());
		
		// 不识别的用 0 去减。
		return substract((Object)0, left_value);
	}
	
	/* === 加法运算 ======================================================= */
	
	/**
	 * 执行 + 加法运算。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static Object plus(Object left_value, Object right_value) {
		// null + anything == null.
		if (left_value == null) return null;
		
		// 如果数据声称自己支持 + 法运算，则调用其加法运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_PLUS)) {
			return ((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_PLUS, right_value);
		}

		// 数字和字符串这种运算多，先进行判定。
		if (left_value instanceof Number && right_value instanceof Number)
			return plusNumber((Number)left_value, (Number)right_value);
		else if (left_value instanceof String && right_value instanceof String)
			return (String)left_value + (String)right_value;
		
		return plusOperatorInternal(left_value, right_value);
	}
	
	/**
	 * 对内部支持的数据类型进行加法运算。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	private static Object plusOperatorInternal(Object left_value, Object right_value) {
		if (left_value instanceof Number)
			return plusOperator((Number)left_value, right_value);
		if (left_value instanceof NumberDataModel)
			return plusOperator(((NumberDataModel)left_value).getAsNumber(), right_value);
		if (left_value instanceof String)
			return plusOperator((String)left_value, right_value);
		
		// 其它数据类型我们无法支持其加法运算。
		return null;
	}
	
	/**
	 * 支持 数字+ null, number, string(parsed)
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static Object plusOperator(Number left_value, Object right_value) {
		if (right_value == null) 
			return left_value;
		
		if (right_value instanceof Number) 
			return plusNumber(left_value, (Number)right_value);
		if (right_value instanceof NumberDataModel)
			return plusNumber(left_value, ((NumberDataModel)right_value).getAsNumber());
		if (right_value instanceof String)
			return plusNumber(left_value, safeParseDouble((String)right_value));
		if (right_value instanceof StringDataModel)
			return plusNumber(left_value, safeParseDouble(((StringDataModel)right_value).getAsString()));
		
		// 其它类型无法支持其加法操作。
		return null;
	}
	
	/**
	 * 支持 字符串+ null, string, scalar, object.toString()
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static Object plusOperator(String left_value, Object right_value) {
		if (left_value == null) return null;
		if (right_value instanceof String) 
			return left_value + (String)right_value;
		if (right_value instanceof StringDataModel)
			return left_value + ((StringDataModel)right_value).getAsString();
		if (right_value instanceof ScalarDataModel)
			return left_value + ((ScalarDataModel)right_value).to_string(null);
		
		return left_value + right_value.toString();
	}
	
	/**
	 * 两个数字的加法。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static Number plusNumber(Number left_value, Number right_value) {
		return ArithmeticEngine.DEFAULT_ENGINE.add(left_value, right_value);
	}
	
	// TODO: 支持日期 + 数字算法
	
	/* === 减法运算 ======================================================= */
	
	/**
	 * 执行 - 减法运算。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static Object substract(Object left_value, Object right_value) {
		// null - anything == null.
		if (left_value == null) return null;
		
		// 如果数据声称自己支持 - 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_MINUS)) {
			return ((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_MINUS, right_value);
		}
		
		return substractOperatorInternal(left_value, right_value);
	}

	private static Object substractOperatorInternal(Object left_value, Object right_value) {
		if (left_value instanceof Number)
			return substractOperator((Number)left_value, right_value);
		if (left_value instanceof NumberDataModel)
			return substractOperator(((NumberDataModel)left_value).getAsNumber(), right_value);
		
		// 其它数据类型我们无法支持其加法运算。
		return null;
	}

	/**
	 * 支持 数字- null, number, string(parsed)
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static Object substractOperator(Number left_value, Object right_value) {
		if (right_value == null) 
			return left_value;
		
		if (right_value instanceof Number) 
			return substractNumber(left_value, (Number)right_value);
		if (right_value instanceof NumberDataModel)
			return substractNumber(left_value, ((NumberDataModel)right_value).getAsNumber());
		if (right_value instanceof String)
			return substractNumber(left_value, safeParseDouble((String)right_value));
		if (right_value instanceof StringDataModel)
			return substractNumber(left_value, safeParseDouble(((StringDataModel)right_value).getAsString()));

		// 其它类型无法支持其减法操作，从而返回 null。
		return null;
	}

	/**
	 * 对两个数字执行减法。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static Number substractNumber(Number left_value, Number right_value) {
		return ArithmeticEngine.DEFAULT_ENGINE.subtract(left_value, right_value);
	}
	
	// TODO: 支持日期减法。
	
	/* === 乘法运算 ======================================================== */
	
	/**
	 * 执行 * 乘法运算。
	 */
	public static Object multiply(Object left_value, Object right_value) {
		// null*anything = null.
		if (left_value == null) return null;
		
		// 如果数据声称自己支持 * 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_MULTIPLY)) {
			return ((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_MULTIPLY, right_value);
		}
		
		// anything*null = null.
		if (right_value == null) return null;
		
		if (left_value instanceof Number)
			return multiplyOperator((Number)left_value, right_value);
		if (left_value instanceof NumberDataModel)
			return multiplyOperator(((NumberDataModel)left_value).getAsNumber(), right_value);
		
		return null;
	}
	
	/**
	 * 执行 * 乘法运算。
	 */
	public static Object multiplyOperator(Number left_value, Object right_value) {
		if (right_value == null) return null;
		if (right_value instanceof Number)
			return multiplyNumber(left_value, (Number)right_value);
		if (right_value instanceof NumberDataModel)
			return multiplyNumber(left_value, ((NumberDataModel)right_value).getAsNumber());
		
		return null;
	}

	/**
	 * 执行 * 乘法运算。
	 */
	public static Number multiplyNumber(Number left_value, Number right_value) {
		return ArithmeticEngine.DEFAULT_ENGINE.multiply(left_value, right_value);
	}
	
	// ? boolean 乘法有意义吗?
	
	/* === 除法运算 ======================================================== */

	/**
	 * 执行 / 除法运算。
	 */
	public static Object divide(Object left_value, Object right_value) {
		// null/anything = null.
		if (left_value == null) return null;
		
		// 如果数据声称自己支持 / 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_DIVIDE)) {
			return ((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_DIVIDE, right_value);
		}
		
		// anything/null = null.
		if (right_value == null) return null;
		
		if (left_value instanceof Number)
			return divideOperator((Number)left_value, right_value);
		if (left_value instanceof NumberDataModel)
			return divideOperator(((NumberDataModel)left_value).getAsNumber(), right_value);
		
		return null;
	}
	
	/**
	 * 执行 / 除法运算。
	 */
	public static Object divideOperator(Number left_value, Object right_value) {
		if (right_value == null) return null;
		if (right_value instanceof Number)
			return divideNumber(left_value, (Number)right_value);
		if (right_value instanceof NumberDataModel)
			return divideNumber(left_value, ((NumberDataModel)right_value).getAsNumber());
		
		return null;
	}

	/**
	 * 执行 / 除法运算。
	 */
	public static Number divideNumber(Number left_value, Number right_value) {
		if (right_value.doubleValue() == 0.0)
			return Double.NaN;
		return ArithmeticEngine.DEFAULT_ENGINE.divide(left_value, right_value);
	}
	
	/* === 求余运算 ======================================================== */

	/**
	 * 执行 % 求余运算。
	 */
	public static Object modulus(Object left_value, Object right_value) {
		// null%anything = null.
		if (left_value == null) return null;
		
		// 如果数据声称自己支持 % 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_MOD)) {
			return ((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_MOD, right_value);
		}
		
		// anything % null = null.
		if (right_value == null) return null;
		
		if (left_value instanceof Number)
			return modulusOperator((Number)left_value, right_value);
		if (left_value instanceof NumberDataModel)
			return modulusOperator(((NumberDataModel)left_value).getAsNumber(), right_value);
		
		return null;
	}
	
	/**
	 * 执行 % 求余运算。
	 */
	public static Object modulusOperator(Number left_value, Object right_value) {
		if (right_value == null) return null;
		if (right_value instanceof Number)
			return modulusNumber(left_value, (Number)right_value);
		if (right_value instanceof NumberDataModel)
			return modulusNumber(left_value, ((NumberDataModel)right_value).getAsNumber());
		
		return null;
	}

	/**
	 * 执行 % 求余运算。
	 */
	public static Number modulusNumber(Number left_value, Number right_value) {
		if (right_value.doubleValue() == 0.0)
			return Double.NaN;
		return ArithmeticEngine.DEFAULT_ENGINE.modulus(left_value, right_value);
	}
}
