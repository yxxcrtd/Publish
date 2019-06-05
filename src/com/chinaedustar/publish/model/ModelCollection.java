package com.chinaedustar.publish.model;

/**
 * 定义 Model 类型的 List 集合接口，其不需要 java.util.List 的那么多
 *   接口函数，而仅仅是提供了必要的一些信息。
 * 
 * @author liujunxing
 *
 */
public interface ModelCollection <T> extends java.lang.Iterable<T> {
	/**
	 * 判断此集合是否为空。在多线程条件下调用此函数是安全的，但是不能保证调用此函数
	 *   返回之后集合状态是否发生的变更。
	 *   
	 * 实现类可以使用最有效的方法来提供给使用者集合是否为空的信息，而不一定需要实际
	 *  装载集合的任何数据。
	 */
	public boolean isEmpty();

	/**
	 * 获得对此集合的枚举器，枚举器支持额外的几个属性。
	 * @return
	 */
	public ModelCollectionIterator<T> iterator();
	
	/**
	 * 将集合里面的数据转换为数组，此数组是一个复制品，从而对其的访问、更改不影响到
	 *   实际的集合。
	 * @return
	 */
	public T[] toArray();
}
