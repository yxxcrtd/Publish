package com.chinaedustar.publish.model;

/**
 * 具有命名和 UUID 属性的对象模型的抽象实现。
 * 
 * @author liujunxing
 *
 */
public abstract class AbstractNamedModelBase extends AbstractModelBase implements NamedObject {
	/**
	 * 构造函数，提供给派生类使用。
	 *
	 */
	protected AbstractNamedModelBase() {
		
	}
	
	/**
	 * 复制构造函数。
	 * @param src
	 */
	protected AbstractNamedModelBase(AbstractNamedModelBase src) {
		copy(src);
	}
	
	// === 属性定义 ===========================================================
	
	/** 对象唯一 UUID。 */
	private String uuid = java.util.UUID.randomUUID().toString().toUpperCase();
	
	/** 对象的名字。 */
	private String name;

	// === 通用 getter, setter ================================================
	
	/**
	 * 获取指定名字的属性值。支持 objectUuid, name, 其它都委托给了父类实现。
	 * @param name - 要获取的属性名。
	 * @see AbstractModelBase#get(String)
	 */
	@Override public Object get(String name) {
		if ("objectUuid".equals(name))
			return this.getObjectUuid();
		else if ("name".equals(name))
			return this.getName();
		else
			return super.get(name);
	}
	
	/**
	 * 通用 set 方法。
	 * @param name - 属性名，支持 objectUuid, name, 其它都委托给了父类实现。
	 * @param value 
	 */
	@Override public void set(String name, Object value) {
		if ("objectUuid".equals(name))
			this.setObjectUuid((String)value);
		else if ("name".equals(name))
			this.setName((String)value);
		else
			super.set(name, value);
	}

	/**
	 * 复制。
	 * @param src
	 */
	protected void copy(AbstractNamedModelBase src) {
		super.copy(src);
		this.uuid = src.uuid;
		this.name = src.name;
}
	
	// === 属性的 getter, setter 实现 ==========================================
	
	/**
	 * 获得此对象的唯一标识。
	 * @return
	 */
	public String getUuid() {
		return this.uuid;
	}
	
	/**
	 * 获得此对象的唯一标识。
	 * @return
	 */
	public String getObjectUuid() {
		return this.uuid;
	}
	
	/**
	 * 设置 UUID。
	 */
	protected void setObjectUuid(String uuid) {
		this.uuid = (uuid == null) ? null : uuid.toUpperCase();
	}
	
	/**
	 * 获得此对象的名字。
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置对象的名字。
	 */
	public void setName(String name) {
		this.name = name;
	}
}
