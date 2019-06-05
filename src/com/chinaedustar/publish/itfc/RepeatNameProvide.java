package com.chinaedustar.publish.itfc;

/**
 * 对循环标签 #{Repeater} 时候支持的对象名字。
 * 
 * @author liujunxing
 */
public interface RepeatNameProvide {
	/**
	 * 获得当用于 #{Repeater} 标签内部，被设置为循环变量的时候，给变量设置的名字。
	 * 
	 * @return 返回要设置的名字的数组，每个名字都被设置为变量。
	 */
	public String[] getRepeatItemNames();
}
