package com.chinaedustar.rtda.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.DatetimeDataModel;

/**
 * 
 * @author liujunxing
 *
 */
public class DatetimeModelImpl extends BeanModelImpl<Date> 
		implements DatetimeDataModel {
	public DatetimeModelImpl(Date target, ObjectWrapper wrapper) {
		super(target, wrapper);
	}
	
	/**
	 * 转换此标量为一个字符串表示。一般可以直接用 Object.toString() 来实现。
	 * @param optional_format - (可选的) 使用此格式，可能为 null.
	 * @return
	 */
	public String to_string(String optional_format) {
		return ((Date)target).toString();
	}

    /**
     * 返回此数据表示的日期值。
     */
    public Date getAsDate() {
    	return target;
    }

    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_date".equals(method_name))
			return true;
		else if ("format".equals(method_name))
			return format(target, param_list);
		else if ("format_java".equals(method_name))
			return format_java(target, param_list);
		
		return super.builtin(method_name, param_list);
	}
	
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	/** date@format 内建函数执行 */
	private static String format(Date target, Object[] args) {
		if (args == null || args.length == 0 || args[0] == null)
			return format_dobuiltin(target, DEFAULT_DATE_FORMAT);	// 缺省格式
		else
			return format_dobuiltin(target, args[0].toString());
	}
		
	private static String format_dobuiltin(java.util.Date date, String format) {
		if (format == "") format = DEFAULT_DATE_FORMAT;
		try {
			SimpleDateFormat formater = new SimpleDateFormat(format);
			return formater.format(date);
		} catch (java.util.IllegalFormatException ex) {
			return "(日期格式 " + format + " 错误)";
		}
	}
	
	private static final String DEFAULT_JAVA_DATE_FORMAT = "%1$tF %1$tT";
	/** date@format_java 内建函数执行。 */
	private static String format_java(Date target, Object[] args) {
		if (args == null || args.length == 0 || args[0] == null)
			return java_dobuiltin(target, DEFAULT_DATE_FORMAT);	// 缺省格式
		else
			return java_dobuiltin(target, args[0].toString());
	}
		
	private static String java_dobuiltin(java.util.Date date, String format) {
		if (format == "") format = DEFAULT_JAVA_DATE_FORMAT;
		try {
			return String.format(format, date);
		} catch (java.util.IllegalFormatException ex) {
			return "(日期格式 " + format + " 错误)";
		}
	}

}
