package com.chinaedustar.rtda.model;

/**
 * 具有扩展能力的 HashDataModel，其能够提供 isEmpty(), keys(), values() 信息的能力。
 * 
 * <p>
 *   isEmpty() 用于实现 builtin .is_empty();<br>
 *   keys() 用于实现 builtin    .keys();<br>
 *   values() 用于实现 builtin  .values();
 * </p>
 * 
 * @author liujunxing
 */
public interface HashDataModel2 extends HashDataModel {
	/**
	 * 判定集合是否为空。
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * 获得所有键的集合，一般返回一个 CollectionDataModel 的实现。
	 * @return
	 */
	public Object keys();
	
	/**
	 * 返回所有值的集合，一般返回一个 CollectionDataModel 的实现。
	 * @return
	 */
	public Object values();
}
