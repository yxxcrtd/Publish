package com.chinaedustar.publish.biz;

/**
 * 业务连接时候使用的业务名称、描述映射。
 * @author liujunxing
 *
 */
public class BizName {
	/** 唯一标识。 */
	private int id;
	
	/** 业务名字。 */
	private String name;
	
	/** 此业务的描述。 */
	private String description;
	
	/** 所属用户集合，以逗号分隔。 */
	private String userList;
	
	/** 所属群组集合，以逗号分隔。 */
	private String groupList;
	
	/** 唯一标识。 */
	public int getId() {
		return this.id;
	}
	
	/** 唯一标识。 */
	public void setId(int id) {
		this.id = id;
	}
	
	/** 业务名字。 */
	public String getName() {
		return this.name;
	}
	
	/** 业务名字。 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 此业务的描述。 */
	public String getDescription() {
		return this.description;
	}
	
	/** 此业务的描述。 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 所属用户集合，以逗号分隔。 */
	public String getUserList() {
		return this.userList;
	}
	
	/** 所属用户集合，以逗号分隔。 */
	public void setUserList(String userList) {
		this.userList = userList;
	}
	
	/** 所属群组集合，以逗号分隔。 */
	public String getGroupList() {
		return this.groupList;
	}
	
	/** 所属群组集合，以逗号分隔。 */
	public void setGroupList(String groupList) {
		this.groupList = groupList;
	}
}
