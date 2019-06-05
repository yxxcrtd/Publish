package com.chinaedustar.publish.itfc;

/**
 * 定义可以被释放的对象实现的接口，其在 DefaultFilter, ThreadCurrentMap 清理
 *   阶段会被执行来释放资源。
 *   
 * @author liujunxing
 */
public interface Disposeable {
	/**
	 * 实际释放此资源。
	 *
	 */
	public void dipose();
}
