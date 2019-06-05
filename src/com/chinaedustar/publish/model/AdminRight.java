package com.chinaedustar.publish.model;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishUtil;

/**
 * 管理员的权限对象，保存有权限的信息。
 * @author wangyi
 *
 */
public class AdminRight {
	/** 权限的标识，由数据库自动产生。 */
	private int id;
	
	/** 管理员用户的标识，关联到管理员用户。 */
	private int userId;
	
	/** 频道的标识，关联到频道或者网站的其它权限设置，null：网站通用权限设置。 */
	private Integer channelId;
	
	/** 栏目的标识，为 null 表示权限项与栏目无关。 */
	private Integer columnId;
	
	/** 目标权限对象或操作。 */
	private String target;
	
	/** 在此权限对象上的操作。 */
	private String operation;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString() {
		return "AdminRight{id=" + id + ", userId=" + userId + ", channelId=" + channelId +
			", columnId=" + columnId + ", target=" + target + ", operation=" + operation + "}";
	}
	
	/**
	 * 权限的标识，由数据库自动产生。
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 权限的标识，由数据库自动产生。
	 * @param id 要设置的 id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 用户的标识，关联到管理员用户。
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 用户的标识，关联到管理员用户。
	 * @param userId 要设置的 userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * 频道的标识。
	 * @return channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}

	/**
	 * 频道的标识。
	 * @param channelId 要设置的 channelId
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * 栏目的标识。
	 * @return params
	 */
	public Integer getColumnId() {
		return this.columnId;
	}

	/**
	 * 栏目的标识。
	 * @param columns 要设置的 columns
	 */
	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	/** 目标权限对象。 */
	public String getTarget() {
		return this.target;
	}
	
	/** 目标权限对象。 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	/** 在此权限对象上的操作。 */
	public String getOperation() {
		return this.operation;
	}
	
	/** 以整数的形式返回权限操作值。 */
	public int getOperAsInt() {
		return PublishUtil.safeParseInt(this.operation, 0);
	}

	/** 在此权限对象上的操作。 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 是否在指定目标上具有指定权限。
	 * @param target
	 * @param operation
	 * @return
	 */
	public boolean hasRight(String target, String operation) {
		// 目标对象相同吗？
		if (isEqualsTarget(target) == false) return false;
		
		if (operation.equalsIgnoreCase(this.operation))
			return true;
		else
			return false;
	}
	
	private final boolean isEqualsTarget(String target) {
		if (target == null && this.target == null) return true;
		if (target == null || this.target == null) return false;
		return target.equalsIgnoreCase(this.target);
	}

	/** 和另外一个对象比较是否是相同的权限项。 */
	boolean _equals(AdminRight right) {
		if (!this.target.equals(right.target)) return false;
		if (!this.operation.equals(right.operation)) return false;
		if (equalsInteger(this.channelId, right.channelId) == false) return false;
		if (equalsInteger(this.columnId, right.columnId) == false) return false;
		
		return true;
	}
	
	public static final boolean equalsInteger(Integer a, Integer b) {
		// null == null
		if (a == null && b == null) return true;
		// a == null MUST b != null, and a != null MUST b == null
		if (a == null || b == null) return false;
		return a.equals(b);
	}

	/**
	 * 删除指定栏目的所有权限项。
	 * @param dao
	 * @param columnId
	 */
	public static final void deleteColumnRights(DataAccessObject dao, int columnId) {
		String hql = "DELETE FROM AdminRight WHERE columnId = " + columnId;
		dao.bulkUpdate(hql);
	}
	
	/**
	 * 删除和指定频道相关的所有权限项。
	 * @param dao
	 * @param channelId
	 */
	public static final void deleteChannelRights(DataAccessObject dao, int channelId) {
		String hql = "DELETE FROM AdminRight WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}
	
}
