package com.chinaedustar.publish.model;

import java.util.Date;
import java.util.List;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;
import com.chinaedustar.publish.util.UpdateHelper;

@SuppressWarnings("rawtypes")
public class UserCollection extends AbstractPublishModelBase {
	/**
	 * 根据标识得到指定的会员对象。
	 * @param id 会员的标识。
	 * @return
	 */
	public User getUser(int id) {
		User user = (User)pub_ctxt.getDao().get(User.class, id);
		if (user != null)
			user._init(pub_ctxt, this);

		return user;
	}
	
	/**
	 * 根据用户名得到指定的会员对象。
	 * @param userName 会员的名称。
	 * @return
	 */
	public User getUser(final String userName) {
		String hql = "FROM User WHERE userName = :userName";
		List list = pub_ctxt.getDao().queryByNamedParam(hql, new String[] {"userName"}, new String[] {userName});
		if (list.isEmpty()) return null;
		
		User user = (User)list.get(0);
		user._init(pub_ctxt, this);
		return user;
	}
	
	/**
	 * 得到当前网站中的会员总数（包括禁用的那些会员）。
	 * @return
	 */
	public int getUserCount() {
		String hql = "SELECT COUNT(id) FROM User";
		return PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
	}

	/**
	 * 得到指定条件的会员的集合。
	 * @param searchType - (当前不支持)查找的类型，0 显示全部；1 文章最多的100个；2 文章最少的100个；3 最近24登录的会员；4 最近24小时注册的会员；5 所有禁用的会员；6 所有启用的会员； 
	 * @param field - 字段。
	 * @param keyword - 搜索关键字。
	 * @return
	 */
	public List<User> getUserList(/*int searchType,*/String field, String keyword, PaginationInfo page_info) {
		// 构造查询。
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM User ";
		if (field != null && field.length() > 0) {
			query.whereClause += " WHERE " + field + " LIKE :keyword ";
			query.setString("keyword", "%" + keyword + "%");
		}

		// 查询数据。
		page_info.setTotalCount(query.queryTotalCount(pub_ctxt.getDao()));
		@SuppressWarnings("unchecked")
		List<User> list = query.queryData(pub_ctxt.getDao(), page_info);
		
		PublishUtil.initModelList(list, pub_ctxt, this);
		return list;
	}

	/**
	 * 是否存在该用户名的会员。
	 * @param userName - 会员的用户名。
	 * @return
	 */
	public boolean existUser(final String userName) {
		String hql = "SELECT COUNT(id) FROM User WHERE userName = :userName";
		List result = pub_ctxt.getDao().queryByNamedParam(hql, new String[] {"userName"}, new String[] {userName});
		
		return safeGetScalarValue(result) > 0;
	}
	
	private long safeGetScalarValue(List list) {
		if (list == null || list.size() == 0) return 0;
		return PublishUtil.safeGetLongVal(list.get(0));
	}
	
	/**
	 * 使用指定的用户名和密码获得用户。注意：返回的用户对象可能被禁用。
	 * @param userName
	 * @param password
	 * @return 如果用户不存在，或者密码不正确，则返回 null. 否则返回用户对象。
	 */
	public User getUser(String userName, String password) {
		String hql = "FROM User WHERE userName = :userName";
		List result = pub_ctxt.getDao().queryByNamedParam(hql,
				new String[] {"userName"},
				new String[] {userName});
		if (result == null || result.size() == 0) return null;	// 不存在。
		
		User user = (User)result.get(0);
		if (password == null) password = "";
		if (password.equals(user.getPassword()))
			return user;
		return null;		// 密码不正确。
	}
	
	/**
	 * 更新用户的密码，返回更新的结果，密码是否更新成功。
	 * 注意：需要事务支持。
	 * @param userId 用户的标识。
	 * @param oldPassword 用户的旧密码。
	 * @param newPassword 用户的新密码。
	 * @return true 表示更新成功，false 表示更新失败, 通常是旧密码不正确或用户不存在。
	 */
	public boolean updatePassword(int userId, String oldPassword, String newPassword) {
		String hql = "UPDATE User SET password = :password WHERE id = :userId AND password = :oldPassword";
		List result = pub_ctxt.getDao().queryByNamedParam(hql,
				new String[] {"password", "userId", "oldPassword" },
				new Object[] {newPassword, userId, oldPassword});
		return safeGetScalarValue(result) > 0;
	}

	/**
	 * 增加或修改会员。
	 * @param user
	 */
	public void saveUser(User user) {
		if (user.getId() == 0)
			createUser(user);
		else
			updateUser(user);
	}
	
	/**
	 * 添加新的会员。
	 * @param user
	 */
	public void createUser(User user) {
		pub_ctxt.getDao().save(user);
	}
	
	/**
	 * 修改会员的信息。
	 * @param user
	 */
	public void updateUser(User user) {
		UpdateHelper updator = new UpdateHelper();
		updator.updateClause = "UPDATE User SET status=:status, inputer=:inputer";
		updator.setInteger("status", user.getStatus());
		updator.setBoolean("inputer", user.getInputer());
		if (user.getPassword() != null) {
			updator.updateClause += ", password=:password";
			updator.setString("password", user.getPassword());
		}
		if (user.getLastLoginTime() != null) {
			updator.updateClause += ", lastLoginTime=:lastLoginTime, lastLoginIp=:lastLoginIp";
			updator.setDate("lastLoginTime", new Date());
			updator.setString("lastLoginIp", user.getLastLoginIp());
		}
		updator.updateClause += " WHERE id = " + user.getId();
		updator.executeUpdate(pub_ctxt.getDao());
	}
	
	/**
	 * 删除指定标识的会员。
	 * @param userId 会员的标识。
	 * @return
	 */
	public int deleteUser(int userId) {
		String hql = "DELETE FROM User WHERE id = " + userId;
		return pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 删除指定用户名的会员。
	 * @param userName 会员的用户名。
	 * @return
	 */
	public int deleteUser(String userName) {
		UpdateHelper updator = new UpdateHelper();
		updator.updateClause = "DELETE FROM User WHERE userName = :userName";
		updator.setString("userName", userName);
		return updator.executeUpdate(pub_ctxt.getDao());
	}
	
	/**
	 * 删除指定的会员对象。
	 * @param user 会员对象。
	 */
	public void deleteUser(User user) {
		if (user != null)
			pub_ctxt.getDao().delete(user);
	}

	/**
	 * 批量删除用户。
	 * @param userIds
	 * @return
	 */
	public int batchDeleteUser(List<Integer> userIds) {
		if (userIds == null || userIds.size() == 0) return 0;
		
		// 用户表现在和别的东西没有关联，所以删除比较容易。
		String hql = "DELETE FROM User WHERE id IN (" + PublishUtil.toSqlInString(userIds) + ")";
		return pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 修改会员的状态信息。
	 * 需要事务支持。
	 * @param userId 用户的标识。
	 * @param status 状态值。
	 */
	public void updateUserStatus(int userId, int status) {
		String hql = "UPDATE User SET status=" + status + " WHERE id = " + userId;
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 修改会员的是否允许投稿的状态信息。
	 * 需要事务的支持。
	 * @param userId 用户的标识。
	 * @param inputer 是否允许投稿。
	 */
	public void updateUserInputer(final int userId, final boolean inputer) {
		String hql = "UPDATE User SET inputer=" + inputer + " WHERE id = " + userId;
		pub_ctxt.getDao().bulkUpdate(hql);
	}
}
