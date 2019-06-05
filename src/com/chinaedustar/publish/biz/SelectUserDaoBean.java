package com.chinaedustar.publish.biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.chinaedustar.publish.model.PaginationInfo;

/**
 * SelectUserDao 实现 bean.
 * @author liujunxing
 *
 */
public class SelectUserDaoBean implements SelectUserDao {
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
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.biz.SelectUserDao#getSelectUserList(cn.edustar.jpub.model.PaginationInfo)
	 */
	public Object getSelectUserList(PaginationInfo page_info) {
		JdbcTemplate jdbc_t = new JdbcTemplate(dataSource);
		// 1. 查询用户总量。
		String sql = "SELECT COUNT(*) FROM App_User";
		int total = jdbc_t.queryForInt(sql);
		page_info.setTotalCount(total);
		if (total == 0) return null;
		
		// 2. 查询分页的用户。
		sql = "SELECT UserID AS id, UserName AS name, UserTitle As title, " +
			"  UserEmail AS email, Introduce AS description " +
			" FROM App_User ORDER BY UserID ";
		Object result = jdbc_t.query(sql, new PagedResultSetExtractor(page_info));
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.biz.SelectUserDao#getSelectGroupList(cn.edustar.jpub.model.PaginationInfo)
	 */
	@SuppressWarnings("rawtypes")
	public List getSelectGroupList(PaginationInfo page_info) {
		JdbcTemplate jdbc_t = new JdbcTemplate(dataSource);
		// 1. 查询群组总量。
		String sql = "SELECT COUNT(*) FROM g_GroupInfo";
		int total = jdbc_t.queryForInt(sql);
		page_info.setTotalCount(total);
		if (total == 0) return null;
		
		// 2. 查询分页的用户。
		sql = "SELECT gg_ID AS id, gg_Name AS name, gg_CreaterName AS creator, " +
			"  gg_description AS description " +
			" FROM g_GroupInfo ORDER BY gg_ID ";
		Object result = jdbc_t.query(sql, new PagedResultSetExtractor(page_info));
		
		return (List)result;
	}

	/**
	 * getSelectUserList 中使用的分页查询支持。
	 * 其跳过前面 N 条记录，只读取后面 page_size 条记录，也许效率很差，但不常用也就不计较了。
	 * @author liujunxing
	 *
	 */
	private static final class PagedResultSetExtractor implements ResultSetExtractor {
		private final PaginationInfo page_info;
		private final RowMapper rowMapper;
		public PagedResultSetExtractor(PaginationInfo page_info) {
			this.page_info = page_info;
			this.rowMapper = new ColumnMapRowMapper();
		}
		
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			// 跳过前面的 N 条记录。
			int skip_num = (page_info.getCurrPage() - 1) * page_info.getPageSize();
			if (skip_num > 0) {
				for (int i = 0; i < skip_num; ++i)
					if (rs.next() == false) 
						return null;
			}

			java.util.List<Object> result = new java.util.ArrayList<Object>(); 
			for (int count = 0; count < page_info.getPageSize(); ++count) {
				if (rs.next() == false) break;
				result.add(rowMapper.mapRow(rs, count));
			}
			return result;
		}
	}
	
}
