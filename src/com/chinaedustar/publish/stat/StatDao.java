package com.chinaedustar.publish.stat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.util.DateUtil;

/**
 * 对计数器表格进行访问的辅助类
 * 
 * @author liujunxing
 */
@SuppressWarnings({ "rawtypes" })
public class StatDao {
    
	/** 到包含统计表格的数据库连接。 */
	private javax.sql.DataSource dataSource;
	
	private JdbcTemplate jdbc_t;
	
	/** 到包含统计表格的数据库连接。 */
	public javax.sql.DataSource getDataSource() {
		return this.dataSource;
	}
	
	/** 到包含统计表格的数据库连接。 */
	public void setDataSource(javax.sql.DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc_t = new JdbcTemplate(dataSource);
	}

	/**
	 * 加载 Stat_Main 信息到内存中。
	 * @return
	 */
	public StatMain loadStatMain() {
		// 查询 Stat_Main 表格。
		StatMain result = (StatMain)jdbc_t.query(StatMainRse.QUERY_SQL, new StatMainRse());
		
		return result;
	}

	/**
	 * 更新统计的配置。
	 * 仅更新部分字段, 参见 StatAction.save_config()。
	 */
	public void updateStatConfig(StatMain stat_main) {
		String update_sql = "UPDATE Stat_Main SET " +
			"  MasterTimeZone = " + stat_main.getMasterTimeZone() + 
			", OnlineTime = " + stat_main.getOnlineTime() + 
			", IntervalNum = " + stat_main.getIntervalNum() + 
			", VisitRecord = " + stat_main.getVisitRecord() +
			", KillRefresh = " + stat_main.getKillRefresh() +
			", OldTotalNum = " + stat_main.getOldTotalNum() +
			", OldTotalView = " + stat_main.getOldTotalView() +
			", StatItems = '" +  StringHelper.replace(stat_main.getStatItems(), "'", "''") + "'";
			
		jdbc_t.update(update_sql);
	}
	
	/**
	 * 获得自指定时间以来在线人数。
	 * @param online_time
	 * @return
	 */
	public int getOnlineNum(java.util.Date online_time) {
		String sql = "SELECT COUNT(*) FROM Stat_Online WHERE LastTime > ?";
		return jdbc_t.queryForInt(sql, new Object[] { online_time });
	}
	
	/**
	 * 总浏览数 + 1, 其它不变。
	 *
	 */
	public void addTotalView() {
		String sql = "UPDATE Stat_Main SET TotalView=TotalView+1";
		jdbc_t.update(sql);
	}
	
	/**
	 * 根据浮点表示的 ip 获得其所属的地区信息。
	 * @param sip
	 * @return
	 */
	public String getAddress(float sip) {
		String sql = "SELECT Address FROM Stat_IpInfo " +
			" WHERE StartIp <= " + sip + " AND EndIp >= " + sip + 
			" ORDER BY (EndIp - StartIp) ASC";
		Map result = jdbc_t.queryForMap(sql);
		if (result == null || result.containsKey("Address") == false) 
			return null;
		return (String)result.get("Address");
	}

	/**
	 * 获得访问者数量。
	 * @return
	 */
	public int getVisitorCount() {
		String sql = "SELECT COUNT(*) FROM Stat_Visitor";
		return jdbc_t.queryForInt(sql);
	}
	
	/**
	 * 插入一个新的访问者记录。
	 *
	 */
	public void insertVisitor(java.util.Date VTime, String IP, String Address, 
			String Browser, String System, String Screen,
			String Color, int Timezone, String Referer) {
		// VTime,IP,Address,Browser,System,Screen,Color,Timezone,Referer
		String sql = "INSERT INTO Stat_Visitor " +
			"(VTime,IP,Address,Browser,System,Screen,Color,Timezone,Referer) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbc_t.update(sql, new Object[] {VTime, IP, Address, Browser, System, 
				Screen, Color, Timezone, Referer});
	}
	
	/**
	 * 覆盖最老的一个访问者记录。
	 *
	 */
	public void overwriteVisitor(java.util.Date VTime, String IP, String Address, 
			String Browser, String System, String Screen,
			String Color, int Timezone, String Referer) {
		// 1. 得到最老的一条记录的 id
		String sql = "SELECT TOP 1 Id FROM Stat_Visitor ORDER BY VTime ASC";
		int id = jdbc_t.queryForInt(sql);
		
		// 2. 覆盖该记录。
		sql = "UPDATE Stat_Visitor SET VTime = ?, IP = ?, Address = ?, Browser = ?, " +
			"System = ?, Screen = ?, Color = ?, Timezone = ?, Referer = ? " +
			" WHERE Id = " + id;
		jdbc_t.update(sql, new Object[] {VTime, IP, Address, Browser, System, 
				Screen, Color, Timezone, Referer});
	}
	
	/**
	 * 执行一个 SQL 更新语句。
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String sql) {
		return jdbc_t.update(sql);
	}
	
	/**
	 * 给指定表添加一个计数。
	 * @param key - 要添加计数的键值，如 color -  '真彩色'。
	 * @param tableName - 添加计数的表, 如 Stat_Color。
	 * @param keyField - 键的字段名字, 如 TColor。
	 * @param numField - 值的字段名字, 如 TColNum。
	 */
	public void addNum(String key, String tableName, String keyField, String numField) {
		// 先更新，如果没有记录，则插入。
		String update_sql = "UPDATE " + tableName + " SET " + numField + 
			" = " + numField + " + 1 WHERE " + keyField + " = '" + key + "'";
		int row_count = jdbc_t.update(update_sql);
		if (row_count == 0) {
			String insert_sql = "INSERT INTO " + tableName + " (" + keyField + ", " + numField + 
				") VALUES ('" + key + "', 1)";
			jdbc_t.update(insert_sql);
		}
	}

	/**
	 * 获得总访问人次，总访问人次 = Stat_VisitNum SUM(T1+T2+T3+T4+T5+T6+T7+T8+T9+T10)。
	 * @return
	 */
	public int getTotalVisitNum() {
		String sql = "SELECT SUM(T1+T2+T3+T4+T5+T6+T7+T8+T9+T10) FROM Stat_VisitNum";
		return jdbc_t.queryForInt(sql);
	}
	
	/**
	 * 获得访问人次数据。
	 * @return
	 */
	public Map getVisitNum() {
		String sql = "SELECT * FROM Stat_VisitNum";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 得到指定年的访问次数统计数据。
	 * @param strYear
	 * @return
	 */
	public Map getStatYear(String strYear) {
		String sql = "SELECT * FROM Stat_Year WHERE TYear = '" + strYear + "'";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 得到指定月的访问次数统计数据。
	 * @param strMonth
	 * @return
	 */
	public Map getStatMonth(String strMonth) {
		String sql = "SELECT * FROM Stat_Month WHERE TMonth = '" + strMonth + "'";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 得到指定周的访问次数统计数据。
	 * @param strWeek
	 * @return
	 */
	public Map getStatWeek(String strWeek) {
		String sql = "SELECT * FROM Stat_Week WHERE TWeek = '" + strWeek + "'";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 得到指定日的访问次数统计数据。
	 * @param strDayLong
	 * @return
	 */
	public Map getStatDay(String strDayLong) {
		String sql = "SELECT * FROM Stat_Day WHERE TDay = '" + strDayLong + "'";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 查询一个 Map 或者为 null.
	 * @param sql
	 * @return
	 */
	private Map queryForMapOrNull(String sql) {
		List result = jdbc_t.queryForList(sql);
		if (result == null || result.size() == 0) return null;
		return (Map)result.get(0);
	}
	
	/**
	 * 查询一个 Map 或者为 null.
	 * @param sql
	 * @return
	 */
	private Map queryForMapOrNull(String sql, Object[] args) {
		List result = jdbc_t.queryForList(sql, args);
		if (result == null || result.size() == 0) return null;
		return (Map)result.get(0);
	}
	
	/**
	 * 获得最常用的浏览器。
	 * @return
	 */
	public Map getMaxBrowser() {
		String sql = "SELECT TOP 1 * FROM Stat_Browser ORDER BY TBrwNum DESC";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 获得最常用的操作系统。
	 * @return
	 */
	public Map getMaxSystem() {
		String sql = "SELECT TOP 1 * FROM Stat_System ORDER BY TSysNum DESC";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 获得最常用的用户屏幕大小。
	 * @return
	 */
	public Map getMaxScreen() {
		String sql = "SELECT TOP 1 * FROM Stat_Screen ORDER BY TScrNum DESC";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 获得最常用的用户屏幕色深。
	 * @return
	 */
	public Map getMaxColor() {
		String sql = "SELECT TOP 1 * FROM Stat_Color ORDER BY TColNum DESC";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 获得最多的用户来访网址。
	 * @return
	 */
	public Map getMaxWeburl() {
		String sql = "SELECT TOP 1 * FROM Stat_Weburl ORDER BY TWebNum DESC";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 获得最多的用户地址。
	 * @return
	 */
	public Map getMaxAddress() {
		String sql = "SELECT TOP 1 * FROM Stat_Address ORDER BY TAddNum DESC";
		return queryForMapOrNull(sql);
	}
	
	/**
	 * 得到 IP 地址列表。(当前暂时不支持分页)
	 * @return
	 */
	public List getFipList() {
		String sql = "SELECT * FROM Stat_Ip ORDER BY TIpNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到 地址列表。(当前暂时不支持分页)
	 * @return
	 */
	public List getAddressList() {
		String sql = "SELECT * FROM Stat_Address ORDER BY TAddNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到来访网站地址列表。(当前暂时不支持分页)
	 * @return
	 */
	public List getWeburlList() {
		String sql = "SELECT * FROM Stat_Weburl ORDER BY TWebNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到链接页面列表。(当前暂时不支持分页)
	 * @return
	 */
	public List getRefererList() {
		String sql = "SELECT * FROM Stat_Refer ORDER BY TRefNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到操作系统页面列表。(当前暂时不支持分页)
	 * @return
	 */
	public List getSystemList() {
		String sql = "SELECT * FROM Stat_System ORDER BY TSysNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到浏览器列表。(当前暂时不支持分页)
	 * @return
	 */
	public List getBrowserList() {
		String sql = "SELECT * FROM Stat_Browser ORDER BY TBrwNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到浏览器特征字串列表。(当前暂时不支持分页)
	 * @return
	 */
	public List getMozillaList() {
		String sql = "SELECT * FROM Stat_Mozilla ORDER BY TMozNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到用户屏幕分辨率统计数据。
	 * @return
	 */
	public List getScreenList() {
		String sql = "SELECT * FROM Stat_Screen ORDER BY TScrNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到用户屏幕色深统计数据。
	 * @return
	 */
	public List getColorList() {
		String sql = "SELECT * FROM Stat_Color ORDER BY TColNum DESC";
		return this.jdbc_t.queryForList(sql);
	}
	
	/**
	 * 得到指定 ip 的用户在 onl_time 之内记录的标识，如果没有记录，则返回 0。
	 * @param onl_time
	 * @param ip
	 * @return
	 */
	public int getUserOnlineRecordId(Date onl_time, String ip) {
		String sql = "SELECT id FROM Stat_Online WHERE UserIP = '" + ip + "' AND LastTime > ?";
		List result = jdbc_t.queryForList(sql, new Object[] {onl_time});
		if (result == null || result.size() == 0) return 0;
		Object v = result.get(0);
		if (v == null) return 0;
		return (Integer)((Map)v).get("id");
	}

	/**
	 * 更新指定 id 的记录的最后访问时间和所在页面。
	 * @param id
	 * @param lastTime
	 * @param referer
	 */
	public void updateUserOnlineRecord(int id, Date lastTime, String referer) {
		String sql = "UPDATE Stat_Online SET LastTime=?, UserPage=? " + 
			" WHERE id = " + id;
		jdbc_t.update(sql, new Object[] {lastTime, referer});
	}
	
	/**
	 * 得到指定时间之前(过期)的用户在线数据记录的标识，如果没有则返回 0.
	 * @param onl_time
	 * @return
	 */
	public int getOuttimeUserOnlineRecord(Date onl_time) {
		String sql = "SELECT TOP 1 id FROM Stat_Online WHERE LastTime < ? ORDER BY LastTime";
		
		List result = jdbc_t.queryForList(sql, new Object[]{onl_time});
		if (result == null || result.size() == 0) return 0;
		Object v = result.get(0);
		if (v == null) return 0;
		return (Integer)((Map)v).get("id");
	}
	
	/**
	 * 覆盖指定标识的用户在线记录数据。
	 * @param id
	 * @param ip
	 * @param userAgent
	 * @param referer
	 * @param onl_time
	 */
	public void overwriteUserOnlineRecord(int id, String ip, String userAgent, String referer, Date onl_time) {
		String sql = "UPDATE Stat_Online SET UserIP = ?, UserAgent = ?, UserPage = ?, Ontime = ?, LastTime = ? " +
			" WHERE id = " + id;
		jdbc_t.update(sql, new Object[]{ip, userAgent, referer, onl_time, onl_time});
	}
	
	/**
	 * 插入一条用户在线记录数据。
	 * @param ip
	 * @param userAgent
	 * @param referer
	 * @param onl_time
	 */
	public void insertUserOnlineRecord(String ip, String userAgent, String referer, Date onl_time) {
		String sql = "INSERT INTO Stat_Online (UserIP, UserAgent, UserPage, OnTime, LastTime) " +
			" VALUES (?, ?, ?, ?, ?)";
		jdbc_t.update(sql, new Object[]{ip, userAgent, referer, onl_time, onl_time});
	}
	
	/**
	 * 判断有没有指定用户在指定时间段内的在线信息。
	 * @param ip
	 * @param onl_time
	 * @return
	 */
	public boolean hasUserOnlineRecord(String ip, Date onl_time) {
		String sql = "SELECT LastTime, OnTime FROM Stat_Online WHERE UserIP = ? AND LastTime > ?";
		Map result = this.queryForMapOrNull(sql, new Object[] {ip, onl_time});
		if (result == null) return false;
		Date lastTime = (Date)result.get("LastTime");
		Date onTime = (Date)result.get("OnTime");
		if (lastTime == null || onTime == null) return false;
		if (lastTime.equals(onTime)) return false;
		
		return true;
	}

	/**
	 * 得到本周的访问数量。
	 * @return
	 */
	public int getWeekNum() {
		try {
			java.util.Date today = DateUtil.getNow();
			java.util.Date first_of_week = DateUtil.getFirstDayOfWeek(today);
			String strWeekLong = DateUtil.getDateString(first_of_week);
			// SUM 保证总有一个数据。
			String sql = "SELECT SUM(D1+D2+D3+D4+D5+D6+D7) FROM Stat_Week WHERE TWeek = '" + strWeekLong + "'";
			return jdbc_t.queryForInt(sql);
		} catch (Exception ex) {
			// ignore
			return 0;
		}
	}
	
	/**
	 * 读取 Stat_Main 信息的辅助类。
	 * @author liujunxing
	 *
	 */
	private static final class StatMainRse implements ResultSetExtractor {
		// 查询语句，必须和 extractData() 中匹配。
		public static final String QUERY_SQL = "SELECT * FROM Stat_Main";
		
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			// 必须有且有一条记录。
			if (rs.next() == false) {
				throw new PublishException("Stat_Main 表格中没有任何数据，可能数据库中数据被损坏了。");
			}
			StatMain obj = new StatMain();
			
			// 读取所有字段。
			obj.setStartDate(rs.getString("StartDate"));
			obj.setTotalNum(rs.getInt("TotalNum"));
			obj.setTotalView(rs.getInt("TotalView"));
			obj.setMonthNum(rs.getInt("MonthNum"));
			obj.setMonthMaxNum(rs.getInt("MonthMaxNum"));
			obj.setOldMonth(rs.getString("OldMonth"));
			obj.setMonthMaxDate(rs.getString("MonthMaxDate"));
			obj.setDayNum(rs.getInt("DayNum"));
			obj.setDayMaxNum(rs.getInt("DayMaxNum"));
			obj.setOldDay(rs.getString("OldDay"));
			obj.setDayMaxDate(rs.getString("DayMaxDate"));
			obj.setHourNum(rs.getInt("HourNum"));
			obj.setHourMaxNum(rs.getInt("HourMaxNum"));
			obj.setOldHour(rs.getString("OldHour"));
			obj.setHourMaxTime(rs.getString("HourMaxTime"));
			obj.setChinaNum(rs.getInt("ChinaNum"));
			obj.setOtherNum(rs.getInt("OtherNum"));
			obj.setMasterTimeZone(rs.getInt("MasterTimeZone"));
			obj.setIntervalNum(rs.getInt("IntervalNum"));
			obj.setOnlineTime(rs.getInt("OnlineTime"));
			obj.setVisitRecord(rs.getInt("VisitRecord"));
			obj.setKillRefresh(rs.getInt("KillRefresh"));
			obj.setStatItems(rs.getString("StatItems"));
			obj.setOldTotalNum(rs.getInt("OldTotalNum"));
			obj.setOldTotalView(rs.getInt("OldTotalView")); 

			return obj;
		}
	}
}
