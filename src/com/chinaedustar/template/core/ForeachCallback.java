package com.chinaedustar.template.core;

/**
 * 执行 #foreach 支持的时候的回调。
 * 
 * @author liujunxing
 */
public interface ForeachCallback {
	/**
	 * 当每次循环的时候被调用。
	 * @param local_ctxt - 局部循环环境。
	 * @param coll_obj - 原集合对象。
	 * @param item - 本次循环的项目。
	 * @param index - 顺序索引，第一个项目索引 = 0。
	 */
	public void foreachObject(LocalContext local_ctxt, Object coll_obj, Object item, int index);
}
