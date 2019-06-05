package com.chinaedustar.common;

/**
 * 定义缓存所需的接口。
 * 
 * @author liujunxing
 *
 */
public interface Cache {
	/**
	 * 获得指定键的对象，如果已经不存在了，则返回 null。
	 * @param key
	 * @return
	 */
	public Object get(Object key);
	
	/**
	 * 放置一个缓存项。
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value);
	
	/**
	 * 从 Cache 中移除一项。
	 * @param key
	 * @return
	 */
	public void remove(Object key);

	/**
	 * 清空缓存。
	 *
	 */
	public void clear();
}
