package com.chinaedustar.rtda.bean;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.NumberDataModel;

/**
 * 对数字访问的数据模型实现。
 * 
 * @author liujunxing
 */
public class NumberModelImpl extends BeanModelImpl<Number> implements NumberDataModel {
	/**
	 * 使用指定的数字和对象包装器构造一个 NumberModelImpl 的实例。
	 * @param number
	 * @param wrapper
	 */
	public NumberModelImpl(Number number, ObjectWrapper wrapper) {
		super(number, wrapper);
	}

	/**
	 * 转换此标量为一个字符串表示。一般可以直接用 Object.toString() 来实现。
	 * @param optional_format - (可选的) 使用此格式，可能为 null.
	 * @return
	 */
	public String to_string(String optional_format) {
		return super.target.toString();
	}

    /**
     * 返回此数据代表的数字值。尽量不要返回 null. 
     */
    public Number getAsNumber() {
    	return super.target;
    }
    
    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_number".equals(method_name))
			return true;
		else if ("byte".equals(method_name))
			return target.byteValue();
		else if ("short".equals(method_name))
			return target.shortValue();
		else if ("int".equals(method_name))
			return target.intValue();
		else if ("long".equals(method_name))
			return target.longValue();
		else if ("float".equals(method_name))
			return target.floatValue();
		else if ("double".equals(method_name))
			return target.doubleValue();
		else if ("string".equals(method_name) || "format".equals(method_name))
			return string(target, param_list);
		else if ("hex".equals(method_name))
			return hex(target, param_list);
		else if ("format_2".equals(method_name))
			return string(target, new String[]{"###,###,###,###,##0"});
		else if ("currency".equals(method_name))
			return string(target, new String[]{"###,###,###,###,##0.00"});
			
		
		return super.builtin(method_name, param_list);
	}
	
	   /** number.string() */
    private static String string(Number number, Object[] args) {
		if (args == null || args.length == 0 || args[0] == null)
			return number.toString();
		
		// 如果有参数，则用参数执行 format.
		java.text.DecimalFormat formatter = new java.text.DecimalFormat(args[0].toString());
		
		return formatter.format(number);
    }

    /** number.hex() */
    private static String hex(Number number, Object[] args) {
		return Long.toHexString(number.longValue());
    }
}
