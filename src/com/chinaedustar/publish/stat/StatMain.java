package com.chinaedustar.publish.stat;

/**
 * 计数器配置和主统计数据信息，其保存在表格 Stat_Main 中。
 * 该表格来自于动易 PE_StatInfoList, 其实际包含了主要统计数据和配置。
 * @author liujunxing
 *
 */
public class StatMain {
	/** 统计开始的日期，格式为 2007-3-1。 */
	private String startDate;
	
	/** 点击总数 */
	private int totalNum;
	
	/** 查看总数。 */
	private int totalView;
	
	/** 本月查看数。 */
	private int monthNum;
	
	/** 最大月查看数。 */
	private int monthMaxNum;
	
	/** 最后一次统计本月查看数的所在月份，格式为 2007-6 */
	private String oldMonth;
	
	/** 最大月查看数发生月份，格式为 2007-6 */
	private String monthMaxDate;
	
	/** 本日查看数。 */
	private int dayNum;
	
	/** 最大日查看数。 */
	private int dayMaxNum;
	
	/** 最后一次统计本日查看数所在日期，格式为 2007-12-3 */
	private String oldDay;
	
	/** 最大日查看数发生日期，格式为 2007-12-23 */
	private String dayMaxDate;
	
	/** 最近一个小时查看数。 */
	private int hourNum;
	
	/** 最大小时查看数。 */
	private int hourMaxNum;
	
	/** 最后一次统计小时查看数所在时间，格式为 2007-12-7 16:00:00 */
	private String oldHour;
	
	/** 最大小时查看数发生所在时间，格式为 2006-11-23 17:00:00 */
	private String hourMaxTime;
	
	/** 访客中来自中国的数量。 */
	private int chinaNum;
	
	/** 访客中来自非中国地区的数量。 */
	private int otherNum;
	
	/** 服务器所在时区，中国(北京)所在时区 = 8。 */
	private int masterTimeZone;
	
	/** 自动标记在线间隔循环次数，单位：次。
	 * 此是为了防止用户打开网页，但长时间无任何活动而设置。客户端浏览器向服务器提交在线信息次数
	 * 超过此次数，立即停止提交。 */
	private int intervalNum;
	
	/** 在线用户的保留时间：单位秒，缺省 150秒。
	 *  用户切换页面至其他网站或者关闭浏览器后，在线名单将在上述时间内删除该用户。
	 *  这个间隔越小，网站统计的当前时刻在线名单越准确；这个间隔越大，网站统计的在线人数越多。 
	 */ 
	private int onlineTime;
	
	/** 保留访问记录数：保存访问明细(最后访问)条目数。单位：条。*/
	private int visitRecord;
	
	/** 保留访问IP数(大于20小于800的数字)：当不启用“在线人数统计”功能时，系统将以
	 * 保留访问者IP的方式来防止刷新，即同一个IP访问多次或者在网站内切换页面，均只计算
	 * 浏览量而不计算访问量。  */
	private int killRefresh;
	
	/** 配置的需要统计的项目，格式为以逗号分隔的字符串。如：IsCountOnline, FIP, FAddress, 
	 * FRefer, FTimezone, FWeburl, FBrowser, FMozilla, FSystem, FScreen, FColor, 
	 * FKeyword, FVisit, FYesterDay */
	private String statItems;
	
	/** 使用本系统前的访问量，单位：人次。属于初始化设置。 */
	private int oldTotalNum;
	
	/** 使用本系统前的浏览量，单位：人次。属于初始化设置。 */
	private int oldTotalView;

	/**
	 * 构造函数。
	 *
	 */
	public StatMain() {
		
	}

	/** 统计开始的日期，格式为 2007-3-1。 */
	public String getStartDate() {
		return this.startDate;
	}
	
	/** 统计开始的日期，格式为 2007-3-1。 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	/** 点击总数 */
	public int getTotalNum() {
		return this.totalNum;
	}

	/** 点击总数 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/** 查看总数。 */
	public int getTotalView() {
		return this.totalView;
	}

	/** 查看总数。 */
	public void setTotalView(int totalView) {
		this.totalView = totalView;
	}

	/** 本月查看数。 */
	public int getMonthNum() {
		return this.monthNum;
	}

	/** 本月查看数。 */
	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}

	/** 最大月查看数。 */
	public int getMonthMaxNum() {
		return this.monthMaxNum;
	}

	/** 最大月查看数。 */
	public void setMonthMaxNum(int monthMaxNum) {
		this.monthMaxNum = monthMaxNum;
	}

	/** 最后一次统计本月查看数的所在月份，格式为 2007-6 */
	public String getOldMonth() {
		return this.oldMonth;
	}

	/** 最后一次统计本月查看数的所在月份，格式为 2007-6 */
	public void setOldMonth(String oldMonth) {
		this.oldMonth = oldMonth;
	}

	/** 最大月查看数发生月份，格式为 2007-6 */
	public String getMonthMaxDate() {
		return this.monthMaxDate;
	}

	/** 最大月查看数发生月份，格式为 2007-6 */
	public void setMonthMaxDate(String monthMaxDate) {
		this.monthMaxDate = monthMaxDate;
	}

	/** 本日查看数。 */
	public int getDayNum() {
		return this.dayNum;
	}

	/** 本日查看数。 */
	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}

	/** 最大日查看数。 */
	public int getDayMaxNum() {
		return this.dayMaxNum;
	}

	/** 最大日查看数。 */
	public void setDayMaxNum(int dayMaxNum) {
		this.dayMaxNum = dayMaxNum;
	}

	/** 最后一次统计本日查看数所在日期，格式为 2007-12-3 */
	public String getOldDay() {
		return this.oldDay;
	}

	/** 最后一次统计本日查看数所在日期，格式为 2007-12-3 */
	public void setOldDay(String oldDay) {
		this.oldDay = oldDay;
	}

	/** 最大日查看数发生日期，格式为 2007-12-23 */
	public String getDayMaxDate() {
		return this.dayMaxDate;
	}

	/** 最大日查看数发生日期，格式为 2007-12-23 */
	public void setDayMaxDate(String dayMaxDate) {
		this.dayMaxDate = dayMaxDate;
	}

	/** 最近一个小时查看数。 */
	public int getHourNum() {
		return this.hourNum;
	}

	/** 最近一个小时查看数。 */
	public void setHourNum(int hourNum) {
		this.hourNum = hourNum;
	}

	/** 最大小时查看数。 */
	public int getHourMaxNum() {
		return this.hourMaxNum;
	}

	/** 最大小时查看数。 */
	public void setHourMaxNum(int hourMaxNum) {
		this.hourMaxNum = hourMaxNum;
	}

	/** 最后一次统计小时查看数所在时间，格式为 2007-12-7 16:00:00 */
	public String getOldHour() {
		return this.oldHour;
	}

	/** 最后一次统计小时查看数所在时间，格式为 2007-12-7 16:00:00 */
	public void setOldHour(String oldHour) {
		this.oldHour = oldHour;
	}

	/** 最大小时查看数发生所在时间，格式为 2006-11-23 17:00:00 */
	public String getHourMaxTime() {
		return this.hourMaxTime;
	}

	/** 最大小时查看数发生所在时间，格式为 2006-11-23 17:00:00 */
	public void setHourMaxTime(String hourMaxTime) {
		this.hourMaxTime = hourMaxTime;
	}

	/** 访客中来自中国的数量。 */
	public int getChinaNum() {
		return this.chinaNum;
	}
	
	/** 访客中来自中国的数量。 */
	public void setChinaNum(int chinaNum) {
		this.chinaNum = chinaNum;
	}
	
	/** 访客中来自非中国地区的数量。 */
	public int getOtherNum() {
		return this.otherNum;
	}

	/** 访客中来自非中国地区的数量。 */
	public void setOtherNum(int otherNum) {
		this.otherNum = otherNum;
	}

	/** 服务器所在时区，中国(北京)所在时区 = 8。 */
	public int getMasterTimeZone() {
		return this.masterTimeZone;
	}

	/** 服务器所在时区，中国(北京)所在时区 = 8。 */
	public void setMasterTimeZone(int masterTimeZone) {
		this.masterTimeZone = masterTimeZone;
	}

	/** 自动标记在线间隔循环次数，单位：次。
	 * 此是为了防止用户打开网页，但长时间无任何活动而设置。客户端浏览器向服务器提交在线信息次数
	 * 超过此次数，立即停止提交。 */
	public int getIntervalNum() {
		return this.intervalNum;
	}

	/** 自动标记在线间隔循环次数，单位：次。
	 * 此是为了防止用户打开网页，但长时间无任何活动而设置。客户端浏览器向服务器提交在线信息次数
	 * 超过此次数，立即停止提交。 */
	public void setIntervalNum(int intervalNum) {
		this.intervalNum = intervalNum;
	}

	/** 在线用户的保留时间：单位秒，缺省 150秒。
	 *  用户切换页面至其他网站或者关闭浏览器后，在线名单将在上述时间内删除该用户。
	 *  这个间隔越小，网站统计的当前时刻在线名单越准确；这个间隔越大，网站统计的在线人数越多。 
	 */ 
	public int getOnlineTime() {
		return this.onlineTime;
	}

	/** 在线用户的保留时间：单位秒，缺省 150秒。
	 *  用户切换页面至其他网站或者关闭浏览器后，在线名单将在上述时间内删除该用户。
	 *  这个间隔越小，网站统计的当前时刻在线名单越准确；这个间隔越大，网站统计的在线人数越多。 
	 */ 
	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

	/** 保留访问记录数：保存访问明细(最后访问)条目数。单位：条。*/
	public int getVisitRecord() {
		return this.visitRecord;
	}

	/** 保留访问记录数：保存访问明细(最后访问)条目数。单位：条。*/
	public void setVisitRecord(int visitRecord) {
		this.visitRecord = visitRecord;
	}

	/** 保留访问IP数(大于20小于800的数字)：当不启用“在线人数统计”功能时，系统将以
	 * 保留访问者IP的方式来防止刷新，即同一个IP访问多次或者在网站内切换页面，均只计算
	 * 浏览量而不计算访问量。  */
	public int getKillRefresh() {
		return this.killRefresh;
	}

	/** 保留访问IP数(大于20小于800的数字)：当不启用“在线人数统计”功能时，系统将以
	 * 保留访问者IP的方式来防止刷新，即同一个IP访问多次或者在网站内切换页面，均只计算
	 * 浏览量而不计算访问量。  */
	public void setKillRefresh(int killRefresh) {
		this.killRefresh = killRefresh;
	}

	/** 配置的需要统计的项目，格式为以逗号分隔的字符串。如：IsCountOnline, FIP, FAddress, 
	 * FRefer, FTimezone, FWeburl, FBrowser, FMozilla, FSystem, FScreen, FColor, 
	 * FKeyword, FVisit, FYesterDay */
	public String getStatItems() {
		return this.statItems;
	}
	
	/** 配置的需要统计的项目，格式为以逗号分隔的字符串。如：IsCountOnline, FIP, FAddress, 
	 * FRefer, FTimezone, FWeburl, FBrowser, FMozilla, FSystem, FScreen, FColor, 
	 * FKeyword, FVisit, FYesterDay */
	public void setStatItems(String statItems) {
		this.statItems = statItems;
	}
	
	/** 使用本系统前的访问量，单位：人次。属于初始化设置。 */
	public int getOldTotalNum() {
		return this.oldTotalNum;
	}

	/** 使用本系统前的访问量，单位：人次。属于初始化设置。 */
	public void setOldTotalNum(int oldTotalNum) {
		this.oldTotalNum = oldTotalNum;
	}

	/** 使用本系统前的浏览量，单位：人次。属于初始化设置。 */
	public int getOldTotalView() {
		return this.oldTotalView;
	}

	/** 使用本系统前的浏览量，单位：人次。属于初始化设置。 */
	public void setOldTotalView(int oldTotalView) {
		this.oldTotalView = oldTotalView;
	}

	/**
	 * 判断是否启用某项的统计。
	 * @param item
	 * @return
	 */
	public boolean is_stat(String item) {
		if (this.statItems == null || this.statItems.length() == 0) return false;
		String[] stat_items = this.statItems.split(",");
		for (int i = 0; i < stat_items.length; ++i) {
			if (item.equalsIgnoreCase(stat_items[i].trim()))
				return true;
		}
		return false;
	}
	
	/**
	 * 提供给模板使用的内建方法：判定是否启用某项的统计。用法：stat_main@is_stat('xxx')
	 * @param item
	 * @return
	 */
	public boolean _builtin_is_stat(Object[] args) {
		if (args == null || args.length == 0) return false;
		if (args[0] == null) return false;
		String item = String.valueOf(args[0]);
		return is_stat(item);
	}

	/**
	 * 'IsCountOnline' 是否存在。
	 * @return
	 */
	public boolean isCountOnline() {
		return is_stat("IsCountOnline");
	}
}
