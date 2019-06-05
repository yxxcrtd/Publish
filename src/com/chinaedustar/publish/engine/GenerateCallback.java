package com.chinaedustar.publish.engine;

/**
 * 生成操作的时候的回调接口。
 * 
 * @author liujunxing
 *
 */
public interface GenerateCallback {
	/**
	 * 向日志中输出指定文字。
	 * @param inf
	 */
	public void info(String inf);
	
	/**
	 * 向队列尾部添加一个生成器。
	 * @param gen
	 */
	public void appendGenerator(Generator gen);
}
