package com.chinaedustar.template;

import com.chinaedustar.template.core.InternalProcessEnvironment;

/**
 * 能够支持内建函数的接口。
 * 
 * @author liujunxing
 */
public interface BuiltinFunction {
	/**
	 * 对目标对象执行此内建函数。
	 * @param env - 执行环境。
	 * @param target - 目标对象。
	 * @param param_list - 参数。
	 * @return
	 */
	public Object exec(InternalProcessEnvironment env, Object target, Object[] param_list);
}
