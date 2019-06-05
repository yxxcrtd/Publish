package com.chinaedustar.publish.label;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.itfc.LabelHandler;
import com.chinaedustar.template.TemplateConstant;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.InternalProcessEnvironment;

/**
 * 简单 LabelHandler 实现，其 getLabelHandler() 方法返回自己。
 *  派生类必须自己实现 handleLabel 方法。
 * @author liujunxing
 *
 */
abstract class AbstractSimpleLabelHandler implements Cloneable, LabelHandler, TemplateConstant {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.LabelHandler#getLabelHandler(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
	 */
	public LabelHandler getLabelHandler(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
		return this;
	}

	/**
	 * 格式化一个日期输出，从 label 中获取 format='' 属性。
	 * @param value
	 * @param label
	 * @return
	 */
	public static String formatDateValue(java.util.Date value, AbstractLabelElement label) {
		if (value == null) return "";
		String format = label == null ? null : label.getAttributes().safeGetStringAttribute("format", null);
		if (format == null || format.length() == 0)
			format = "yyyy-MM-dd hh:mm:ss";		// 缺省格式。
		try {
			java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
			return formater.format(value);
		} catch (IllegalArgumentException ex) {
			@SuppressWarnings("deprecation")
			String result = value.toLocaleString();
			return result;
		}
	}
}
