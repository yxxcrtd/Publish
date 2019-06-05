package com.chinaedustar.publish.biz;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.chinaedustar.publish.PublishUtil;

/**
 * UserDao 接口的实现，用于在发布系统中。
 * @author liujunxing
 *
 */
@SuppressWarnings("rawtypes")
public class UserDaoBean implements UserDao {
	/** 到用户信息表的数据库连接。 */
	private javax.sql.DataSource dataSource;
	
	/** 到用户信息表的数据库连接。 */
	public javax.sql.DataSource getDataSource() {
		return this.dataSource;
	}
	
	/** 到用户信息表的数据库连接。 */
	public void setDataSource(javax.sql.DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private String userSql = "SELECT UserID AS id, UserName AS name, UserTitle As title, " +
			"  UserEmail AS email, UserIcon AS icon, Introduce AS description";

	private String groupSql = "SELECT gg_ID AS id, gg_Name AS name, gg_CreaterName AS creator, gg_description AS description, gg_Picture AS picture ";
	
	/**
	 * 获得选择用户信息用的 SELECT 语句部分。
	 * @return
	 */
	public String getUserSql() {
		return userSql;
	}
	
	/**
	 * 设置选择用户信息用的 SELECT 语句部分。
	 * @return
	 */
	public void setUserSql(String userSql) {
		this.userSql = userSql;
	}

	/**
	 * 获得选择群组信息用的 SELECT 语句部分。
	 * @return
	 */
	public String getGroupSql() {
		return groupSql;
	}
	
	/**
	 * 设置选择群组信息用的 SELECT 语句部分。
	 * @return
	 */
	public void setGroupSql(String groupSql) {
		this.groupSql = groupSql;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.biz.UserDao#getUserInfo(java.util.List)
	 */
	public List getUserInfo(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return null;
		
		/** Spring JDBC 访问辅助类。 */
		JdbcTemplate jdbc_t = new JdbcTemplate(dataSource);
		
		// 按照用户标识查找这些用户的核心信息。
		String sql = this.userSql +
			" FROM App_User " +
			" WHERE UserID IN (" + PublishUtil.toSqlInString(ids) + ")";
		
		// 返回为一个 List<Map{id, name, title, email, description}>
		return jdbc_t.queryForList(sql);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.biz.UserDao#getGroupInfo(java.util.List)
	 */
	public List getGroupInfo(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return null;
		
		JdbcTemplate jdbc_t = new JdbcTemplate(dataSource);
		String sql = this.groupSql +
			" FROM g_GroupInfo " +
			" WHERE gg_ID IN (" + PublishUtil.toSqlInString(ids) + ")";
		
		return jdbc_t.queryForList(sql);
	}
}
