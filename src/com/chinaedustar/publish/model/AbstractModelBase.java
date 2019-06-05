package com.chinaedustar.publish.model;

/**
 * 基本模型对象的抽象实现接口。
 * 
 * <p>在新的模型对象中我们用通用 getter, setter 取代了原来的 PropertyAccessor.
 * </p>
 * 
 * <pre>
 * AbstractModelBase - 发布系统业务数据的基类，其支持属性 id(objectId), objectClass.
 *   AbstractNamedModelBase - 具有 objectUuid, name 属性的业务对象基类。
 *     AbstractPagedModelBase - 具有页面属性 (logo, banner, etc.) 的业务对象基类。
 *       Site - 发布中 站点 业务数据对象。
 *       Channel - 发布中 频道 业务数据对象。
 *       Column - 发布中 栏目 业务数据对象。
 *       Special -  发布中 专题 业务数据对象。
 *       Item - 发布中 项目 业务数据对象。
 *         Article - 文章模块的 文章 业务数据对象。
 * AbstractModelCollection - 发布系统抽象集合模型的实现。模板化的。
 *   ChannelCollection - 频道集合对象。
 * ColumnTree - 栏目树集合对象。
 * </pre>
 * 
 * @author liujunxing
 */
public abstract class AbstractModelBase extends AbstractPublishModelBase implements ModelObject, PublishModelObject {
	
	/**
	 * 缺省构造函数
	 */
	protected AbstractModelBase() {
		// 
	}
	
	/**
	 * 复制构造函数
	 * 
	 * @param src
	 */
	protected AbstractModelBase(AbstractModelBase src) {
		this.id = src.id;
	}
	
	// === 通用 getter, setter ==============================================
	
	/**
	 * 通用 get 方法。
	 * @param name - 要获取的属性名，支持 id, objectId, parent, objectClass, class.
	 * @exception IllegalArgumentException - 如果不是上述几种参数则抛出此错误。
	 */
	public Object get(String name) {
		if ("id".equals(name) || "objectId".equals(name))
			return this.getId();
		else if ("parent".equals(name))
			return this.getParent();
		else if ("objectClass".equals(name))
			return this.getObjectClass();
		else if ("class".equals(name))
			return this.getClass();
		else
			// throw new IllegalArgumentException("unknown property name:" + name);
			return UNEXIST;
	}
	
	/**
	 * 通用 set 方法。
	 * @param name - 属性名，支持 id, objectId.
	 * @param value 
	 * @exception IllegalArgumentException - 如果不是上述几种参数则抛出此错误。
	 */
	public void set(String name, Object value) {
		if ("id".equals(name) || "objectId".equals(name))
			this.setId((Integer)value);
		else
			throw new IllegalArgumentException("readonly or unknown property name:" + name);
	}
	
	/**
	 * 复制。
	 * @param src
	 */
	protected void copy(AbstractModelBase src) {
		this.id = src.id;
	}
	
	// === 属性 =============================================================
	
	/** 对象的标识。 */
	private int id;
	
	// === ModelObject 接口实现 ==============================================
	
	/**
	 * 获得此对象的标识。
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 设置标识 id。
	 */
	public void setId(int id) {
		this.id = id;
	}
	

	/**
	 * 等同于 getId, ObjectId 是数据库中的名字。
	 * @return
	 */
	public int getObjectId() {
		return this.id;
	}

	/**
	 * 等同于 setId, ObjectId 是数据库中的名字。
	 */
	protected void setObjectId(int id) {
		this.id = id;
	}
	
	/**
	 * 获得此模型对象的父对象，缺省实现为返回 null，派生类必须自己返回合适的父对象。
	 * @return 返回此对象的父对象。
	 */
	public ModelObject getParent() {
		return null;
	}

	/**
	 * 获得此对象的类型 - 缺省返回类的非限定名字，派生类可以返回特定的名字。
	 * @return getClass().getSimpleName().
	 */
	public String getObjectClass() {
		return getClass().getSimpleName();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ModelObject#getUrlResolver()
	 */
	public UrlResolver getUrlResolver() {
		if (this.getParent() == null)
			return pub_ctxt.getSite();		// Site 一定是一个绝对化器。
		
		// 我们自己不支持，上交给父对象解析。
		return this.getParent().getUrlResolver();
	}
}
