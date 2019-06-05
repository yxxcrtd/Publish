package com.chinaedustar.publish.model;

import java.util.List;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.*;

/**
 * 管理员集合对象。
 * 
 * @author wangyi
 */
public class AdminCollection extends AbstractPublishModelBase {
	/**
	 * 构造函数。
	 */
	public AdminCollection() {
	}
	
	/**
	 * 得到指定的管理员用户对象。
	 * @param adminId - 管理员的标识。
	 * @return 该标识的管理员对象，如果未找到则返回 null.
	 */
	public Admin getAdmin(int adminId) {
		Admin admin = (Admin)pub_ctxt.getDao().get(Admin.class, adminId);
		if (admin != null)
			admin._init(pub_ctxt, this);
		return admin;
	}

	/**
	 * 得到指定的管理员用户对象。
	 * @param adminName 管理员的名称。
	 * @return
	 */
	public Admin getAdmin(String adminName) {
		QueryHelper qh = new QueryHelper();
		qh.fromClause = " FROM Admin ";
		qh.whereClause = " WHERE adminName = :adminName ";
		qh.setString("adminName", adminName);
		Admin admin = (Admin)qh.querySingleData(pub_ctxt.getDao());
		if (admin != null)
			admin._init(pub_ctxt, this);
		
		return admin;
	}
	
	/**
	 * 得到当前页的管理员对象的集合。
	 * @param page_info - 分页信息对象，返回的时候设置了总记录数数据。
	 * @return 返回 List&lt;Admin&gt; 数据。
	 */
	public List<Admin> getAdminList2(PaginationInfo page_info) {
		// 查询： 'FROM Admin ORDER BY id ASC'
		QueryHelper qh = new QueryHelper();
		qh.fromClause = " FROM Admin ";
		qh.orderClause = " ORDER BY id ASC ";
		
		// 先获得总记录数。
		page_info.setTotalCount(qh.queryTotalCount(pub_ctxt.getDao()));
		
		// 获得分页数据。
		@SuppressWarnings("unchecked")
		List<Admin> list = qh.queryData(pub_ctxt.getDao(), page_info);
		
		if (list == null) return null;
		PublishUtil.initModelList(list, pub_ctxt, this);
		return list;
	}
	
	/**
	 * 得到管理员的总数。
	 * @return
	 */
	public long getAdminCount() {
		String hql = "select count(id) from Admin";
		return PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
	}
	
	/**
	 * 是否存在指定管理员用户。
	 * @param adminName 管理员的名称。
	 * @return
	 */
	public boolean existAdmin(String adminName) {
		Admin admin = getAdmin(adminName);
		return (admin != null);
	}
	
	/**
	 * 判断指定管理员用户名和密码是否可以登录。
	 * @param adminName 管理员的名称。
	 * @param password 密码。
	 * @return
	 */
	public boolean canLogin(final String adminName, final String password) {
		Admin admin = getAdmin(adminName);
		if (admin == null) return false;
		
		return admin.getPassword().equals(password);
	}

	/**
	 * 更新用户的密码，返回更新的结果，1：更新成功；0：原密码不正确。
	 * 注意：需要事务支持。
	 * @param userId 用户的标识。
	 * @param oldPassword 用户的旧密码。
	 * @param newPassword 用户的新密码。
	 * @return
	 */
	public int updatePassword(final int userId, final String oldPassword, final String newPassword) {
		UpdateHelper query = new UpdateHelper();
		query.updateClause = "UPDATE Admin SET password=:password WHERE id=:userId and password=:oldPassword";
		query.setString("password", newPassword);
		query.setInteger("userId", userId);
		query.setString("oldPassword", oldPassword);
		return query.executeUpdate(pub_ctxt.getDao());
	}

	/**
	 * 创建新的管理员用户。
	 * 注意：需要事务支持。
	 * @param admin
	 */
	public void createAdmin(Admin admin) {
		super._getPublishContext().getDao().save(admin);
	}
	
	/**
	 * 更姓管理员用户信息。
	 * 注意：需要事务支持。
	 * @param admin
	 */
	public void updateAdmin(Admin admin) {
		if (admin.getAdminType() != 2)
			admin._init(_getPublishContext(), this);

		_getPublishContext().getDao().update(admin);
	}

	/**
	 * 删除指定的管理员，同时要删除它的权限。
	 * @param adminId 管理员的标识。
	 * @return
	 */
	public int deleteAdmin(int adminId) {
		Admin admin = getAdmin(adminId);
		return deleteAdmin(admin);
	}
	
	/**
	 * 删除指定的管理员，同时要删除它的权限。
	 * @param adminName 管理员的名称。
	 * @return
	 */
	public int deleteAdmin(String adminName) {
		Admin admin = getAdmin(adminName);
		return deleteAdmin(admin);
	}
	
	/**
	 * 删除指定的管理员，同时要删除它的权限。
	 * @param admin 管理员对象。
	 * @return
	 */
	public int deleteAdmin(Admin admin) {
		if (admin.getAdminName().equals("admin")) 
			throw new PublishException("试图删除内建管理员。"); 
		
		String hql = "DELETE FROM AdminRight WHERE userId = " + admin.getId();
		int num = pub_ctxt.getDao().bulkUpdate(hql);

		pub_ctxt.getDao().delete(admin);
		return (num + 1);
	}

	/**
	 * 删除一组管理员。
	 * @param admin_ids
	 */
	public void deleteAdmins(List<Integer> admin_ids) {
		if (admin_ids == null || admin_ids.size() == 0) return;
		
		// 删除这些管理员的权限。
		String hql = "DELETE FROM AdminRight WHERE userId IN (" + PublishUtil.toSqlInString(admin_ids) + ")";
		pub_ctxt.getDao().bulkUpdate(hql);
		
		// 删除管理员自身。
		hql = "DELETE FROM Admin WHERE id IN (" + PublishUtil.toSqlInString(admin_ids) + ") AND adminName <> 'admin'";
		pub_ctxt.getDao().bulkUpdate(hql);
	}
}
