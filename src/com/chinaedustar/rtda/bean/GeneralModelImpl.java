package com.chinaedustar.rtda.bean;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.StringDataModel;

/**
 * 一般对象模型。
 * 
 * @author liujunxing
 *
 */
public class GeneralModelImpl extends BeanModelImpl<Object> implements StringDataModel {
	/**
	 * 构造。
	 * @param target
	 * @param wrapper
	 */
	public GeneralModelImpl(Object target, ObjectWrapper wrapper) {
		super(target, wrapper);
	}

	public String getAsString() {
		return String.valueOf(target);
	}

	public String to_string(String optional_format) {
		return String.valueOf(target);
	}
}
