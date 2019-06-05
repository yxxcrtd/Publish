package com.chinaedustar.publish.model;


/**
 * 实现此接口的对象表示该对象具有自定义属性。
 * 
 * @author liujunxing
 *
 */
public interface ExtendPropertySupport extends PublishModelObject {
	/**
	 * 获得父对象的 Uuid.
	 * @return
	 */
	public String getObjectUuid();
	
	/**
	 * 得到对象的类名，通过类名能够找到此对象。
	 * @return
	 */
	public String getObjectClass();
	
	/**
	 * 由子对象调用，父对象提示是否具有扩展属性，其数量是多少。
	 * 父对象如果不告知子对象这个数据，则子对象将尝试从数据库装载一次。
	 * @return -1 表示不知道；0 表示没有扩展属性(或不支持); >0 表示具有扩展属性(一般是扩展属性数量)
	 * 注意： 对象可能将扩展属性数量存储在本对象记录中，此时需要进行适当的同步。
	 */
	public int hintPropNum();
	
	/**
	 * 由子对象告知此对象，其拥有的子属性数量发生了变化。
	 * 在属性被持久化的时候产生此通知调用，父对象可以更新自己的所属字段；也可以忽略此通知。
	 * @param num
	 * @return
	 */
	public void propNumChanged(int num);

	/**
	 * 获得所有扩展属性集合。
	 * @return
	 */
	public ExtendPropertySet getExtends();
}
