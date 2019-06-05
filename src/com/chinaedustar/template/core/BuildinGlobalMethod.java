package com.chinaedustar.template.core;

import java.util.Iterator;

import com.chinaedustar.rtda.oper.LogicCalculator;
import com.chinaedustar.rtda.oper.MathCalculator;
import com.chinaedustar.template.ProcessEnvironment;
import com.chinaedustar.template.expr.ParameterList;

/**
 * 内置全局方法的实现。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class BuildinGlobalMethod {
	/** 方法未找到时返回此值。 */
	public static final Object METHOD_NOT_FOUND = new Object(); 
	
	/** 唯一实例。 */
	private static final BuildinGlobalMethod instance = new BuildinGlobalMethod();
	
	public static BuildinGlobalMethod getInstance() { return instance; }
	
	private BuildinGlobalMethod() {
	}
	
	/**
	 * 调用全局函数。
	 * @param method
	 * @param env
	 * @param param_list
	 * @return
	 */
	public Object invoke(String method, InternalProcessEnvironment env, ParameterList param_list) {
		if ("today".equals(method))
			return today(env, param_list);
		if ("random".equals(method))
			return random(env, param_list);
		if ("iif".equals(method))
			return iif(env, param_list);
		if ("range".equals(method))
			return range(env, param_list);
		
		return METHOD_NOT_FOUND;
	}
	
	/**
	 * 返回今天的日期。
	 * @param env - 环境信息。
	 * @param param_list - 参数列表。
	 * TODO: 缺省无参数的时候取得当前时间。其它参数要定义其含义。
	 * @return
	 */
	protected java.util.Date today(ProcessEnvironment env, ParameterList param_list) {
		return java.util.Calendar.getInstance().getTime();
	}

	/**
	 * 返回一个随机数。
	 * @param env - 环境信息。
	 * @param param_list - 参数列表。
	 * TODO: 参数含义处理。
	 * @return
	 */
	protected Integer random(ProcessEnvironment env, ParameterList param_list) {
		java.util.Random rnd = new java.util.Random();
		return new Integer(rnd.nextInt());
	}
	
	/**
	 * 计算 iif(bool, true_result[, false_result])，其中 false_result 部分可选。
	 *   如果没有给出 false_result 而布尔结果为 false, 则返回 null。
	 * @param env
	 * @param param_list
	 * @return
	 */
	protected Object iif(InternalProcessEnvironment env, ParameterList param_list) {
		if (param_list.size() < 2)
			throw new RuntimeException("iif() 需要至少 2 个参数，最多 3 个参数。");
		Object bool_param = env.calc(param_list.get(0));
		boolean bool = LogicCalculator.getBooleanValue(bool_param);
		if (bool) {
			// 计算 true-part 部分。
			return env.calc(param_list.get(1));		// true-part
		} else {
			if (param_list.size() < 2) return null;
			return env.calc(param_list.get(2));		// false-part
		}
	}
	
	/**
	 * 返回一个枚举器，其从数字 start 到 end, 可选有 step.
	 * @param env
	 * @param param_list
	 * @return
	 */
	protected java.util.Iterator range(InternalProcessEnvironment env, ParameterList param_list) {
		if (param_list.size() < 2)
			throw new RuntimeException("range() 需要至少 2 个参数，最多 3 个参数。");
		int start = MathCalculator.getIntegerValue(env.calc(param_list.get(0)));
		int end = MathCalculator.getIntegerValue(env.calc(param_list.get(1)));
		int step = 1;
		if (param_list.size() >= 3)
			step = MathCalculator.getIntegerValue(env.calc(param_list.get(2)));
		
		// 返回此范围的枚举器。
		return new RangeIterator(start, end, step);
	}
	
	// 模拟 for (int = start; i < end; i += step) { }
	private static final class RangeIterator implements Iterator {
		private int start;
		private final int end;
		private final int step;
		
		public RangeIterator(int start, int end, int step) {
			if (step == 0) throw new IllegalArgumentException("step 不能等于 0");
			this.start = start;
			this.end = end;
			this.step = step;
		}
		
		public boolean hasNext() {
			if (step > 0)
				return (start + step) <= end;
			else
				return (start + step) >= end;
		}
		
		public Object next() {
			int result = start;
			start += step;
			return result;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
