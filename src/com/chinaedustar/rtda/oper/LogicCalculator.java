package com.chinaedustar.rtda.oper;

import com.chinaedustar.rtda.model.*;

/**
 * 逻辑运算器。
 * 
 * @author liujunxing
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LogicCalculator extends BaseCalculator {
	/* === 公共 ===================================================== */
	
	/**
	 * 对指定的 Boolean 值进行简单包装。
	 * @param value
	 * @return
	 */
	public static BooleanDataModel BooleanWrapper(boolean value) {
		return value == true ? BooleanDataModel.TRUE : BooleanDataModel.FALSE;
	}

	
	/**
	 * 将对象转换为 boolean 值。
	 * @param value
	 * @return
	 */
	public static boolean getBooleanValue(Object value) {
		if (value == null) return false;
		if (value instanceof Boolean) 
			return ((Boolean)value).booleanValue();
		if (value instanceof BooleanDataModel) 
			return ((BooleanDataModel)value).getAsBoolean();
		if (value instanceof Number)
			return safeParseBoolean((Number)value);
		if (value instanceof String)
			return safeParseBoolean((String)value);
		if (value instanceof StringDataModel)
			return safeParseBoolean(((StringDataModel)value).getAsString());
		return false;
	}
	
	/* === 逻辑与 ===================================================== */
	
	/**
	 * 计算 left_value && right_value
	 */
	public static boolean and(Object left_value, Object right_value) {
		if (left_value == null) return false;
		
		// 如果数据声称自己支持 && 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_AND)) {
			return getBooleanValue(((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_AND, right_value));
		}

		// 短路算法。
		boolean left_bool = getBooleanValue(left_value);
		if (left_bool == false) return false;
		boolean right_bool = getBooleanValue(right_value);

		return left_bool && right_bool;
	}
	
	/**
	 * 计算 left_value && right_value
	 * @param left_bool
	 * @param right_value
	 * @return
	 */
	public static boolean andOperator(boolean left_bool, Object right_value) {
		// any-bool & null = false
		if (right_value == null) return false;
		if (left_bool == false) return false;
		
		boolean right_bool = getBooleanValue(right_value);
		
		return left_bool && right_bool;
	}

	/* === 逻辑或 ===================================================== */

	/**
	 * 计算 left_value || right_value
	 */
	public static boolean or(Object left_value, Object right_value) {
		if (left_value == null) return false;
		
		// 如果数据声称自己支持 || 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_OR)) {
			return getBooleanValue(((OperatorOverrideModel)left_value)
					.executeOperator(OperatorOverrideModel.OPERATOR_OR, right_value));
		}

		// 短路算法。
		boolean left_bool = getBooleanValue(left_value);
		if (left_bool == true) return true;
		boolean right_bool = getBooleanValue(right_value);

		return (left_bool || right_bool);
	}

	/* === 逻辑异或 ===================================================== */
	
	/**
	 * 计算 left_value ^ right_value
	 */
	public static boolean xor(Object left_value, Object right_value) {
		if (left_value == null) return false;
		
		// 如果数据声称自己支持 ^ 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_XOR)) {
			return getBooleanValue(((OperatorOverrideModel)left_value)
					.executeOperator(OperatorOverrideModel.OPERATOR_XOR, right_value));
		}

		boolean left_bool = getBooleanValue(left_value);
		boolean right_bool = getBooleanValue(right_value);

		return (left_bool ^ right_bool);
	}

	/* === 逻辑求反 ===================================================== */
	
	public static boolean not(Object value) {
		if (value == null) return true;
		
		// 如果数据声称自己支持 ! 法运算，则调用其运算子函数。
		if (isSupportOperator(value, OperatorOverrideModel.OPERATOR_NOT)) {
			return getBooleanValue(((OperatorOverrideModel)value)
					.executeOperator(OperatorOverrideModel.OPERATOR_NOT, null));
		}
		
		return !getBooleanValue(value);
	}
	
	/* === 逻辑相同比较==================================================== */
	
	/**
	 * 计算 left_value == right_value
	 */
	public static boolean equal(Object left_value, Object right_value) {
		if (left_value == null && right_value == null) return true;
		if (left_value == null) return false;
		
		// 如果数据声称自己支持 == 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_EQUAL)) {
			return getBooleanValue( 
					((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_EQUAL, right_value));
		}
		
		// 数字进行特殊比较。
		if (left_value instanceof Number && right_value instanceof Number) {
			return 0 == ArithmeticEngine.DEFAULT_ENGINE.compareNumbers((Number)left_value, (Number)right_value);
		}
		
		// 数字 == 字符串进行特殊比较。
		if (left_value instanceof Number && right_value instanceof String) {
			return right_value.equals(left_value.toString());
		} else if (left_value instanceof String && right_value instanceof Number) {
			return left_value.equals(right_value.toString());
		}
		
		// 否则使用 object.equals() 进行比较运算。
		return left_value.equals(right_value);
	}
	
	/* === 逻辑不同比较==================================================== */
	
	/**
	 * 计算 left_value != right_value
	 */
	public static boolean notEqual(Object left_value, Object right_value) {
		return !equal(left_value, right_value);
		/*
		// null == null
		if (left_value == null && right_value == null) return false;
		// null != anything
		if (left_value == null || right_value == null) return true;
		if (left_value == null) return true;
		
		// 如果数据声称自己支持 == 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_NOTEQUAL)) {
			return getBooleanValue(
					((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_NOTEQUAL, right_value));
		}
		
		// 否则使用 object.equals() 进行比较运算。
		return !left_value.equals(right_value);
		*/
	}

	/* === 逻辑大于等于比较 ==================================================== */
	
	/**
	 * 执行 left_value >= right_value 比较。
	 */
	public static boolean greatEqual(Object left_value, Object right_value) {
		// 如果数据声称自己支持 >= 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_GREATOREQUAL)) {
			return getBooleanValue(((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_GREATOREQUAL, right_value));
		}
		
		// 否则我们进行比较。
		int compare_result = compareObject(left_value, right_value);
		return (compare_result >= 0) ? true : false; 
	}

	/* === 逻辑大于比较 ====================================================== */

	/**
	 * 执行 left_value > right_value 比较。
	 */
	public static boolean great(Object left_value, Object right_value) {
		// 如果数据声称自己支持 > 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_GREAT)) {
			return getBooleanValue(((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_GREAT, right_value));
		}
		
		// 否则我们进行比较。
		int compare_result = compareObject(left_value, right_value);
		return (compare_result > 0); 
	}
	
	/* === 逻辑小于等于比较 ====================================================== */

	/**
	 * 执行 left_value <= right_value 比较。
	 */
	public static boolean lessEqual(Object left_value, Object right_value) {
		// 如果数据声称自己支持 > 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_GREAT)) {
			return getBooleanValue(((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_GREAT, right_value));
		}
		
		// 否则我们进行比较。
		int compare_result = compareObject(left_value, right_value);
		return (compare_result <= 0); 
	}
	
	/* === 逻辑小于比较 ====================================================== */

	/**
	 * 执行 left_value < right_value 比较。
	 */
	public static boolean less(Object left_value, Object right_value) {
		// 如果数据声称自己支持 > 法运算，则调用其运算子函数。
		if (isSupportOperator(left_value, OperatorOverrideModel.OPERATOR_GREAT)) {
			return getBooleanValue(((OperatorOverrideModel)left_value).executeOperator(OperatorOverrideModel.OPERATOR_GREAT, right_value));
		}
		
		// 否则我们进行比较。
		int compare_result = compareObject(left_value, right_value);
		return (compare_result < 0); 
	}
	
	
	/* === 用于支持逻辑比较的操作 ============================================= */
	
	/**
	 * 返回对两个对象的比较结果。
	 * @param left_value
	 * @param right_value
	 * @return 0 - 表示两个对象相等(equals), 负数 表示 left < right, 正数 表示 left > right.
	 *   无法比较则抛出运行时异常。
	 */
	public static int compareObject(Object left_value, Object right_value) {
		if (left_value == null && right_value == null) return 0;
		if (left_value == null) return -1;	// null < right
		if (right_value == null) return 1;	// left > null
		
		// String
		if (left_value instanceof String)
			return compareString((String)left_value, right_value);
		if (left_value instanceof StringDataModel)
			return compareString(((StringDataModel)left_value).getAsString(), right_value);
		// 数字
		if (left_value instanceof Number)
			return compareObject((Number)left_value, right_value);
		if (left_value instanceof NumberDataModel)
			return compareObject(((NumberDataModel)left_value).getAsNumber(), right_value);
		// Boolean
		if (left_value instanceof Boolean)
			return compareObject((Boolean)left_value, right_value);
		if (left_value instanceof BooleanDataModel)
			return compareObject(((BooleanDataModel)left_value).getAsBoolean(), right_value);
		// Date
		if (left_value instanceof java.util.Date)
			return compareObject((java.util.Date)left_value, right_value);
		if (left_value instanceof DatetimeDataModel)
			return compareObject(((DatetimeDataModel)left_value).getAsDate(), right_value);
		
		// ?Comparable - 使用 Comparable 接口进行比较。
		if (left_value instanceof Comparable) {
			try {
				return ((Comparable)left_value).compareTo(right_value);
			} catch (ClassCastException ex) {
				throw new CalculationException("compareTo operator failed", ex);
			}
		}
		
		// 不知道如何比较。
		throw new CalculationException("can't compare object for class " + left_value.getClass().getName());
	}

	/**
	 * 返回对两个对象的比较结果。
	 * @param left_value
	 * @param right_value
	 * @return 0 - 表示两个对象相等(equals), 负数 表示 left < right, 正数 表示 left > right.
	 *   无法比较则抛出运行时异常。
	 */
	public static int compareString(String left_value, Object right_value) {
		if (left_value == null) return right_value == null ? 0 : -1;
		if (right_value == null) return 1;
		
		// 字符串 当前支持和字符串比较。
		if (right_value instanceof String) 
			return left_value.compareTo((String)right_value);
		if (right_value instanceof StringDataModel)
			return left_value.compareTo(((StringDataModel)right_value).getAsString());
		if (right_value instanceof ScalarDataModel)
			return left_value.compareTo(((ScalarDataModel)right_value).to_string(null));
		
		throw new CalculationException();
	}
	
	/**
	 * 返回对两个对象的比较结果。
	 * @param left_value
	 * @param right_value
	 * @return 0 - 表示两个对象相等(equals), 负数 表示 left < right, 正数 表示 left > right.
	 *   无法比较则抛出运行时异常。
	 */
	public static int compareObject(Number left_value, Object right_value) {
		if (left_value == null)	return right_value == null ? 0 : -1;
		if (right_value == null) return 1;
		
		if (right_value instanceof Number)
			return compareNumber(left_value, (Number)right_value);
		if (right_value instanceof NumberDataModel)
			return compareNumber(left_value, ((NumberDataModel)right_value).getAsNumber());
		
		throw new CalculationException("无法将数字和类型 " + right_value.getClass().getName() + " 进行比较操作。");
	}
	
	/**
	 * 比较两个数字，尽量精确比较。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static int compareNumber(Number left_value, Number right_value) {
		return ArithmeticEngine.DEFAULT_ENGINE.compareNumbers(left_value, right_value);
	}
    
	/**
	 * 返回对两个对象的比较结果。
	 * @param left_value
	 * @param right_value
	 * @return 0 - 表示两个对象相等(equals), 负数 表示 left < right, 正数 表示 left > right.
	 *   无法比较则抛出运行时异常。
	 */
	public static int compareObject(Boolean left_value, Object right_value) {
		if (left_value == null)	return right_value == null ? 0 : -1;
		if (right_value == null) return 1;
		
		if (right_value instanceof Boolean)
			return compareBoolean(left_value, (Boolean)right_value);
		if (right_value instanceof BooleanDataModel)
			return compareBoolean(left_value, ((BooleanDataModel)right_value).getAsBoolean());
		
		throw new CalculationException("无法将布尔值和类型 " + right_value.getClass().getName() + " 进行比较操作。");
	}
	
	/**
	 * 比较两个布尔值。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static int compareBoolean(Boolean left_value, Boolean right_value) {
		return left_value.compareTo(right_value);
	}

	/**
	 * 返回对两个对象的比较结果。
	 * @param left_value
	 * @param right_value
	 * @return 0 - 表示两个对象相等(equals), 负数 表示 left < right, 正数 表示 left > right.
	 *   无法比较则抛出运行时异常。
	 */
	public static int compareObject(java.util.Date left_value, Object right_value) {
		if (left_value == null)	return right_value == null ? 0 : -1;
		if (right_value == null) return 1;
		
		if (right_value instanceof java.util.Date)
			return compareDatetime(left_value, (java.util.Date)right_value);
		if (right_value instanceof BooleanDataModel)
			return compareDatetime(left_value, ((DatetimeDataModel)right_value).getAsDate());
		
		throw new CalculationException("无法将日期值和类型 " + right_value.getClass().getName() + " 进行比较操作。");
	}

	/**
	 * 比较两个日期值。
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public static int compareDatetime(java.util.Date left_value, java.util.Date right_value) {
		if (left_value == null)	return right_value == null ? 0 : -1;
		if (right_value == null) return 1;
		
		return left_value.compareTo(right_value);
	}
}
