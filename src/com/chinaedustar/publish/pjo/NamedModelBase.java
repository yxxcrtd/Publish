package com.chinaedustar.publish.pjo;

/**
 * 具有 id, name, uuid 三个基本属性的 PJO 类。
 * @author liujunxing
 *
 */
public class NamedModelBase {
	// === 属性 =============================================================
	
	/** 对象的标识。 */
	private int id;
	
	/** 对象唯一 UUID。 */
	private String uuid = java.util.UUID.randomUUID().toString().toUpperCase();
	
	/** 对象的名字。 */
	private String name;

	// === getter/setter ===================================================
	
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
