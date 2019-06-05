package com.chinaedustar.publish.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chinaedustar.publish.PublishUtil;

/**
 * 
 * @author 
 *
 */
public class User extends AbstractModelBase implements PublishModelObject {
	
	/** 管理员对象的父对象，UserCollection 。 */
	@SuppressWarnings("unused")
	private UserCollection parent;
	
	/** 管理员GUID，此标识必须唯一，由应用生成。 */
	private String uuid = UUID.randomUUID().toString().toUpperCase();
	
	/** 此管理员前台登录的名字。此名字必须唯一。 */
	private String userName;
	
	/** 后台登录密码的hash加密值。 */
	private String password;
	
	/** 会员注册的时间。 */
	private Date registryTime;
	
	/** 最后一次登录的IP地址。 */
	private String lastLoginIp;
	
	/** 最后一次登录的时间。 */
	private Date lastLoginTime;
	
	/** 用户状态 0 - 正常 */
	public static final int USER_STATUS_NORMAL = 0;

	/** 用户状态 1 - 禁用 */
	public static final int USER_STATUS_DISABLED = 1;

	/** 用户状态，1：禁用；0：正常，默认为0。 */
	private int status;
	
	/** 是否允许投稿，默认允许。 */
	private boolean inputer = true;
	
	/** 用户一组简单的信息 */
	private java.util.List<UserSimpleInfo> simpleInfos = null;

	/**
	 * @return uuid
	 */
	public String getObjectUuid() {
		return uuid;
	}

	/**
	 * @param uuid 要设置的 uuid
	 */
	public void setObjectUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * 是否允许投稿，默认允许。
	 * @return inputer
	 */
	public boolean getInputer() {
		return inputer;
	}

	/**
	 * 是否允许投稿，默认允许。
	 * @param inputer 要设置的 inputer
	 */
	public void setInputer(boolean inputer) {
		this.inputer = inputer;
	}

	/**
	 * 最后一次登录的IP地址。
	 * @return lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * 最后一次登录的IP地址。
	 * @param lastLoginIp 要设置的 lastLoginIp
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * 最后一次登录的时间。
	 * @return lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * 最后一次登录的时间。
	 * @param lastLoginTime 要设置的 lastLoginTime
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * 后台登录密码的hash加密值。
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 后台登录密码的hash加密值。
	 * @param password 要设置的 password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 会员注册的时间。
	 * @return registryTime
	 */
	public Date getRegistryTime() {
		return registryTime;
	}

	/**
	 * 会员注册的时间。
	 * @param registryTime 要设置的 registryTime
	 */
	public void setRegistryTime(Date registryTime) {
		this.registryTime = registryTime;
	}

	/**
	 * 用户状态，1：禁用；0：正常，默认为0。
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 用户状态，1：禁用；0：正常，默认为0。
	 * @param status 要设置的 status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 此管理员前台登录的名字。此名字必须唯一。
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 此管理员前台登录的名字。此名字必须唯一。
	 * @param userName 要设置的 userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 用户是否可以删除指定的内容项。
	 * @param itemId 内容项的标识。
	 * @return
	 */
	public boolean canDeleteItem(final int itemId) {
		Object val = pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(id) from Item where inputer=:inputer and id=:id and status!=1";
				Query query = session.createQuery(hql);
				query.setString("inputer", getUserName());
				query.setInteger("id", itemId);
				return query.list().get(0);
			}
		});
		long count = PublishUtil.safeGetLongVal(val);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 设置用户的一组简单信息
	 * @param simpleInfos
	 */
	public void setSimpleInfos(java.util.List<UserSimpleInfo> simpleInfos) {
		this.simpleInfos = simpleInfos;
	}
	
	/**
	 * 获取用户的一组简单信息
	 * @return
	 */
	public java.util.List<UserSimpleInfo> getSimpleInfos() {
		if (simpleInfos == null) {
			simpleInfos = new java.util.ArrayList<UserSimpleInfo>();
			
			simpleInfos.add(getSimpleInfo("最后登录时间", "", new SimpleDateFormat("yyyy/MM/dd").format(lastLoginTime)));
			simpleInfos.add(getSimpleInfo("最后登录IP", "", lastLoginIp));
		}
		return simpleInfos;
	}
	
	private UserSimpleInfo getSimpleInfo(String itemName, String itemUnit, String itemValue) {
		UserSimpleInfo info = new UserSimpleInfo();
		info.setItemName(itemName);
		info.setItemUnit(itemUnit);
		info.setItemValue(itemValue);
		return info;
	}
}
