package com.chinaedustar.publish.model;

/**
 * 定义发布系统使用的模型对象的基接口。接口大部分规定了用于获取信息的部分，部分含有业务。
 * 
 * <p>所有的模型对象都实现有此基本接口。基本接口规定了对象必须具有唯一标识(id),具有一个
 *   类型(objectClass),具有父对象(Parent)。
 * </p>
 * 
 * 接口的继承树如下：
 * <pre>
 * ModelObject - 模型对象基接口
 *   NamedUuidObject - 具有名字和 UUID 属性的模型对象
 *     PageAttrObject - 具有页面属性的模型对象
 *       Site - 站点对象
 *       Channel - 频道对象
 *       Column - 栏目对象
 *       Special - 专题对象
 *     Module - 模块对象
 *     Account - 账户对象
 *     Role - 角色对象
 *   Ace - 权限项对象
 *   Collection - 集合对象
 *     TreeCollection - 树集合对象
 *       ColumnTree - 栏目树
 *     ChannelCollection - 频道集合
 *     ModuleCollection - 模块集合
 *     SpecialCollection - 专题集合
 * </pre>
 * 
 * 另外还有几种方面支持的接口：
 * <pre>
 * ModelObject - 模型对象基接口
 *   PrivilegeSupport - 支持设置权限的对象。
 *   CustomSupport - 支持自定义属性。
 *   ChargeSupport - 支持计费设置。
 * </pre>
 * 
 * @author liujunxing
 */
public interface ModelObject extends PublishModelObject {
	/**
	 * 获得此对象的标识。
	 * @return
	 */
	public int getId();
	
	/**
	 * 等同于 getId, ObjectId 是数据库中的名字。
	 * @return
	 */
	public int getObjectId();
	
	/**
	 * 获得此模型对象的父对象。
	 * @return 返回此对象在业务逻辑上的父对象，如果没有或者无法获得则返回 null. 派生类如果不支持也可以返回 null
	 */
	public ModelObject getParent();
	
	/**
	 * 获得此对象的类型。
	 * @return
	 */
	public String getObjectClass();

	/**
	 * 获得此对象的 Url 绝对化器。
	 * @return
	 */
	public UrlResolver getUrlResolver();
}
