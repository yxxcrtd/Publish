package com.chinaedustar.publish.label;

import com.chinaedustar.publish.itfc.RepeatNameProvide;
import com.chinaedustar.template.core.ForeachCallback;
import com.chinaedustar.template.core.LocalContext;

/**
 * 对支持自己设置循环变量名字的项目提供支持的辅助类。
 * 
 * @author liujunxing
 *
 */
public class RepeatNameSetter implements ForeachCallback {
	/** 缺省实例。 */
	public static final RepeatNameSetter Instance = new RepeatNameSetter();
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.ForeachCallback#foreachObject(com.chinaedustar.template.core.LocalContext, java.lang.Object, java.lang.Object, int)
	 */
	public void foreachObject(LocalContext local_ctxt, Object coll_obj, Object item, int index) {
		if (item == null) return;
		// 如果项目支持自己设置名字到 LocalContext 里面，则让它自己设置名字。
		if (item instanceof RepeatNameProvide) {
			String[] names = ((RepeatNameProvide)item).getRepeatItemNames();
			if (names == null || names.length == 0) return;
			for (int i = 0; i < names.length; ++i)
				local_ctxt.setVariable(names[i], item);
		}
	}
}
