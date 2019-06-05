package com.chinaedustar.publish.label;

import com.chinaedustar.publish.itfc.LabelHandler;

/**
 * LabelHandler 的集合。
 * 
 * @author liujunxing
 */
public class LabelHandlerMap {
	private final java.util.HashMap<String, LabelHandler>
	  h_map = new java.util.HashMap<String, LabelHandler>();
	
	public LabelHandlerMap() {
		
	}
	
	/**
	 * 查找指定名字的 LabelHandler.
	 * @param name
	 * @return
	 */
	public LabelHandler get(String name) {
		return h_map.get(name);
	}
	
	/**
	 * 添加一个 LabelHandler。
	 * @param name
	 * @param h
	 */
	public void put(String name, LabelHandler h) {
		h_map.put(name, h);
	}
	
	/**
	 * 测试目的：返回实际的 LabelHandler Map.
	 * @return
	 */
	public java.util.HashMap<String, LabelHandler> testGetMap() {
		return this.h_map;
	}
}
