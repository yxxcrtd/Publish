package com.chinaedustar.publish.model;

/**
 * 模型数据枚举器，此枚举器扩展了枚举器所能提供的功能，如 size(), index() 功能。
 * 
 * <p>ModelCollection 在多线程并发条件下也能够保证返回合适的枚举器，并在该枚举器
 *   中能够获得有效的集合大小数据。</p> 
 * 
 * @author liujunxing
 *
 * @param <T>
 */
public interface ModelCollectionIterator <T> extends java.util.Iterator<T> {
	/**
	 * 获得所枚举的集合的大小。
	 * @return
	 */
	public int size();
	
	/**
	 * 获得所枚举的项目的当前索引。
	 * @return
	 */
	public int index();
}
