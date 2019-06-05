package com.chinaedustar.publish.stat;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.DateUtil;
import com.chinaedustar.publish.util.StringUtil;

/**
 * 用于产生统计信息的辅助类。
 * @author liujunxing
 *
 */
@SuppressWarnings("deprecation")
public class StatCreator {
	/** 当前时间。 */
	private java.util.Date now;
	
	/** 发布系统环境对象。 */
	private PublishContext pub_ctxt;
	
	/** 访问 Stat_XXX 数据的 dao 对象。 */
	private StatDao stat_dao;
	
	/** 统计配置和主统计数据。 */
	private StatMain stat_main;
	
	/** 来访用户的 ip 地址。 */
	private String ip;
	
	// 一个例子：Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2; .NET CLR 1.1.4322; InfoPath.1; .NET CLR 2.0.50727) 
	/** 用户浏览器类型。 */
	private String userAgent;
	
	/** 来源地址，也即添加计数器的页面地址。 */
	private String referer;
	
	/** 用户的屏幕宽度。 */
	private String width;
	
	/** 用户的屏幕高度。 */
	private String height;
	
	/** 用户的屏幕色深（色彩数量）。 */
	private String color;
	
	/** 所在时区。 */
	private String timezone;
	
	/** 整数表示的时区值。 */
	private int _int_timezone;
	
	/**
	 * 构造函数。
	 *
	 */
	public StatCreator() {
		this.now = new java.util.Date();
	}
	
	/** 发布系统环境对象。 */
	public PublishContext getPublishContext() {
		return this.pub_ctxt;
	}

	/** 发布系统环境对象。 */
	public void setPublishContext(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	/** 访问 Stat_XXX 数据的 dao 对象。 */
	public StatDao getStatDao() {
		return this.stat_dao;
	}
	
	/** 访问 Stat_XXX 数据的 dao 对象。 */
	public void setStatDao(StatDao stat_dao) {
		this.stat_dao = stat_dao;
	}

	/** 统计配置和主统计数据。 */
	public StatMain getStatMain() {
		return this.stat_main;
	}
	
	/** 统计配置和主统计数据。 */
	public void setStatMain(StatMain stat_main) {
		this.stat_main = stat_main;
	}

	/** 来访用户的 ip 地址。 */
	public String getIp() {
		return this.ip;
	}
	
	/** 来访用户的 ip 地址。 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/** 用户浏览器类型。 */
	public String getUserAgent() {
		return this.userAgent;
	}

	/** 用户浏览器类型。 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/** 来源地址，也即添加计数器的页面地址。 */
	public String getReferer() {
		return this.referer;
	}
	
	/** 来源地址，也即添加计数器的页面地址。 */
	public void setReferer(String referer) {
		this.referer = referer; 
	}

	/** 用户的屏幕宽度。 */
	public String getWidth() {
		return this.width;
	}
	
	/** 用户的屏幕宽度。 */
	public void setWidth(String width) {
		this.width = width;
	}
	
	/** 用户的屏幕高度。 */
	public String getHeight() {
		return this.height;
	}

	/** 用户的屏幕高度。 */
	public void setHeight(String height) {
		this.height = height;
	}

	/** 用户的屏幕色深（色彩数量）。 */
	public String getColor() {
		return this.color;
	}

	/** 用户的屏幕色深（色彩数量）。 */
	public void setColor(String color) {
		this.color = color;
	}

	/** 所在时区。 */
	public String getTimezone() {
		return this.timezone;
	}
	
	/** 所在时区。 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * 总浏览量+1，更新。
	 *
	 */
	private void addTotalView() {
		stat_main.setTotalView(stat_main.getTotalView() + 1);
		stat_dao.addTotalView();
	}
	
	/**
	 * 将当前访问用户 ip 保存在缓存中。
	 *
	 */
	@SuppressWarnings("unchecked")
	public void saveLastIpCache() {
		String last_ip_cache = (String)pub_ctxt.getMap().get(StatCreator.LAST_IP_KEY);
		if (last_ip_cache == null) last_ip_cache = "#0.0.0.0#";
		
		// 1. 计算现在已经保存了多少个 ip, 数 '#' 个数 -1 即可。
		int count = countStringChar(last_ip_cache, '#') - 1;
		// 如果数量不到 killRefresh 指定数量则添加到 ip_cache 末尾。
		if (count < stat_main.getKillRefresh()) {
			last_ip_cache += (this.ip + "#");
		} else {
			// 否则我们去掉最前面的，放在末尾。
			int pos = last_ip_cache.indexOf('#', 1);
			last_ip_cache = last_ip_cache.substring(pos) + (this.ip + "#");
		}
		pub_ctxt.getMap().put(StatCreator.LAST_IP_KEY, last_ip_cache);
	}
	
	private static int countStringChar(String str, char ch) {
		if (str == null || str.length() == 0) return 0;
		int count = 0;
		for (int i = 0; i < str.length(); ++i) {
			if (str.charAt(i) == ch) ++count;
		}
		return count;
	}
	
	/** 最后访问 IP 地址。 */
	private static final String LAST_IP_KEY = "StatCreator.last_ip_cache";
	
	private int online_num = 0;
	
	/**
	 * 获得当前在线人数，在调用 updateStat() 之后可以获得正确的在线人数。
	 * @return
	 */
	public int getOnlineNum() {
		return this.online_num;
	}
	
	/**
	 * 实际更新统计信息。
	 *
	 */
	public synchronized void updateStat() {
		prepare();
		
		if (this.isStatItem("IsCountOnline")) {
			// 从 Stat_Online 中统计当前在线人数。
			java.util.Date online_time = 
				DateUtil.addSeconds(DateUtil.getNow(), -stat_main.getOnlineTime());
			this.online_num = stat_dao.getOnlineNum(online_time);
			
			// 查找在线记录中是否有自己，如果有则更新总浏览数, 如果没有则更新（添加）
			if (stat_dao.hasUserOnlineRecord(this.ip, online_time))
				this.addTotalView();
			else
				update();
		} else {
			// 如果不统计在线数据，则根据 ip 更新浏览次数。
			String last_ip_cache = (String)pub_ctxt.getMap().get(StatCreator.LAST_IP_KEY);
			if (last_ip_cache == null) last_ip_cache = "";
			if (last_ip_cache.indexOf("#" + this.ip + "#") >= 0) {
				// 如果IP已经存在于保存的列表中，是刷新。
				addTotalView();
			} else {
				// 否则这是一个新访问者，保存其 IP 地址，更新统计信息。
				saveLastIpCache();
				update();
			}
		}
	}

	/** 要统计的项目。 */
	private String[] _stat_fields;
	
	/**
	 * 做一些准备工作。
	 *  分解 StatItems 为数组。
	 */
	private void prepare() {
		String temp = this.stat_main.getStatItems();
		if (temp == null) temp = "";
		this._stat_fields = temp.split(",");
		if (this._stat_fields == null) {
			this._stat_fields = new String[] {};
		} else {
			for (int i = 0; i < this._stat_fields.length; ++i)
				this._stat_fields[i] = this._stat_fields[i].trim();
		}
	}
	
	/**
	 * 判断是否统计指定的项目。
	 * @param item
	 * @return
	 */
	private boolean isStatItem(String item) {
		if (item == null) return false;
		if (item.length() == 0) return false;
		for (int i = 0; i < _stat_fields.length; ++i) {
			if (item.equalsIgnoreCase(_stat_fields[i])) 
				return true;
		}
		return false;
	}
	
	/** 将 ip 转换为浮点数。 */
	private float _sip;
	
	/** 来访用户地址，通过 ip 计算出来。 */
	private String _address;
	
	/** 来访用户区域，中国或非中国。 */
	private String _scope;
	
	/**
	 * 根据 ip 计算出浮点的 _sip.
	 *
	 */
	private void calcSip() {
		this._sip = 0;
		String[] ip_array = this.ip.split("\\.");
		this._sip = (float)Integer.parseInt(ip_array[0])*256*256*256 +
			(float)Integer.parseInt(ip_array[1])*256*256 +
			(float)Integer.parseInt(ip_array[2])*256 +
			(float)Integer.parseInt(ip_array[3]) - 
			1;
	}
	
	/**
	 * 根据 _sip 从数据库中获得其所在地址和区域。
	 *
	 */
	private void calcAddress() {
		if ("127.0.0.1".equals(this.ip)) {
			this._address = "本机地址";
			this._scope = "ChinaNum";
		} else {
			this._address = this.stat_dao.getAddress(this._sip);
			if (this._address == null || this._address.length() == 0)
				this._address = "其它地区";
			String Province = "北京天津上海重庆黑龙江吉林辽宁江苏浙江安徽河南河北湖南湖北山东山西内蒙古陕西甘肃宁夏青海新疆西藏云南贵州四川广东广西福建江西海南香港澳门台湾内部网未知";
			if (Province.indexOf(StringUtil.left(this._address, 2)) >= 0)
				this._scope = "ChinaNum";
			else
				this._scope = "OtherNum";
			
		}
		this._address = StringHelper.replace(this._address, "'", "");
		this._address = StringUtil.left(this._address, 50);
	}
	
	/** 计算 referer */
	private void calcReferer() {
		if (this.referer == null || this.referer.length() == 0)
			this.referer = "直接输入或书签导入";
		this.referer = StringHelper.replace(this.referer, "'", "");
		this.referer = StringUtil.left(this.referer, 50);
	}
	
	/** 用户屏幕大小。 */
	private String _screen;
	
	/** 计算用户屏幕大小。 */
	private void calcScreen() {
		if (PublishUtil.isInteger(width) && PublishUtil.isInteger(height))
			this._screen = "" + width + "x" + height;
		else
			this._screen = "其它";
		this._screen = StringUtil.left(this._screen, 10);
	}
	
	/** 计算屏幕色深。 */
	private void calcColor() {
		if (PublishUtil.isInteger(this.color)) {
			switch (Integer.parseInt(this.color)) {
			case 4:
				this.color = "16 色"; break;
			case 8:
				this.color = "256 色"; break;
			case 16:
				this.color = "增强色（16位）"; break;
			case 24:
				this.color = "真彩色（24位）"; break;
			case 32:
				this.color = "真彩色（32位）"; break;
			}
		} else {
			this.color = "其它";
		}
	}
	
	private String _weburl;
	
	/** 计算 WebUrl 数据，用于统计。 */
	private void calcWebUrl() {
		this._weburl = StringUtil.left(this.referer, this.referer.indexOf('/', 8));
		if (_weburl == null || _weburl.length() == 0)
			this._weburl = "直接输入或书签导入";
		// 限定最大长度 50。
		this._weburl = StringHelper.replace(this._weburl, "'", "");
		this._weburl = StringUtil.left(_weburl, 50);
	}
	
	/** 所使用的浏览器类型。 */
	private String _mozilla;
	
	/** 浏览器类型。 */
	private String _browser;
	
	/** 用户的操作系统。 */
	private String _system;
	
	/** 计算浏览器类型、使用系统等。 */
	private void calcBrowser() {
		this._mozilla = this.userAgent;
		
		// 根据浏览器特征字符串计算浏览器类型、所用操作系统。
		// 一个例子：Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2; .NET CLR 1.1.4322; InfoPath.1; .NET CLR 2.0.50727) 
		String[] agent_s = this.userAgent.split(";");
		int browser_type = 0;
		if (agent_s.length > 2) {
			if (agent_s[1].indexOf('U') >= 0 ||
					agent_s[1].indexOf('I') >= 0) 
				browser_type = 1;			// Netscape
			if (agent_s[1].indexOf("MSIE") >= 0)
				browser_type = 2;			// IE
		}
		switch (browser_type) {
		case 0:
			this._browser = "其它";
			this._system = "其它";
			break;
		case 1: {
			try {
				String ver = agent_s[0].substring(agent_s[0].indexOf('/') + 1);
				ver = ver.substring(0, ver.indexOf(' ') - 1);
				this._browser = "Netscape" + ver;
				this._system = agent_s[0].substring(agent_s[0].indexOf('(') + 1);
			} catch (Exception ex) {
				this._browser = "Netscape";
				this._system = "未知";
			}
			break;
			}
		case 2:
			 this._browser = agent_s[1];
			 this._system = agent_s[2];
			 this._system = StringHelper.replace(_system, ")", "");
			 break;
		}
		
		this._system = StringHelper.replace(_system, "Win ", "Windows ");
		this._system = StringHelper.replace(_system, " ", "");
		this._system = StringHelper.replace(_system, "NT5.0", "2000");
		this._system = StringHelper.replace(_system, "NT5.1", "XP");
		this._system = StringHelper.replace(_system, "NT5.2", "2003");
		
		this._system = StringHelper.replace(this._system, "'", "");
		this._system = StringUtil.left(_system, 20);
		this._browser = StringHelper.replace(this._browser, "'", "");
		this._browser = StringUtil.left(_browser, 20);
		
		this._mozilla = StringHelper.replace(this._mozilla, "'", "");
		this._mozilla = StringUtil.left(this._mozilla, 100);
	}
	
	// 计算时区，可能计算不对，需要测试一下。
	private void calcTimezone() {
		if (this.timezone == null || PublishUtil.isInteger(this.timezone) == false) {
			this.timezone = "未知";
		} else {
			this._int_timezone = PublishUtil.safeParseInt(this.timezone, 0) / 60;
			if (_int_timezone < 0)
				this.timezone = "GMT+" + (-_int_timezone);
			else
				this.timezone = "GMT-" + _int_timezone;
		}
	}
	
	// 计算各个字段，部分还未实现。
	private void calcFields() {
		calcSip();			// 浮点表示的 ip 地址，用于计算地址。
		calcAddress();		// 用户地址和区域。
		calcReferer();		// 引用此计数器的页面地址。
		calcWebUrl();		// 引用此计数器的网址，只有网站名称部分。
		calcScreen();		// 屏幕大小。
		calcColor();		// 屏幕色深。
		calcBrowser();		// 浏览器类型。
		calcTimezone();		// 计算时区。
	}
	
	/**
	 * 更新 最近访问记录(最大记录数量有配置，一般为 5000)
	 *
	 */
	private void updateVisit() {
		int visitCount = stat_dao.getVisitorCount();

		if (visitCount >= stat_main.getVisitRecord()) {
			// 如果记录数超出，则覆盖掉最老的一条记录。
			stat_dao.overwriteVisitor(new java.util.Date(), this.ip, this._address, 
					this._browser, this._system, this._screen, 
					this.color, this._int_timezone, this.referer);
		} else {
			// 插入一条新的。
			stat_dao.insertVisitor(new java.util.Date(), this.ip, this._address, 
					this._browser, this._system, this._screen, 
					this.color, this._int_timezone, this.referer);
		}
	}
	
	// 判定一个字符串是否是空串。包括：null, "", 全部是空格字符
	private static final boolean isEmpty(String v) {
		if (v == null) return true;
		if (v.length() == 0) return true;
		if (v.trim().length() == 0) return true;
		return false;
	}
	
	/**
	 * 更新最大月、日、时访问量。
	 *
	 */
	private void modiMaxNum() {
		StringBuilder update_sql = new StringBuilder();
		update_sql.append("UPDATE Stat_Main SET ");
		
		// 总访问量 + 1
		update_sql.append("TotalNum = TotalNum + 1");
		// 总浏览量 + 1
		update_sql.append(", TotalView = TotalView + 1");
		// 国内 或 国外访问人次 + 1
		if ("ChinaNum".equals(this._scope)) 
			update_sql.append(", ChinaNum = ChinaNum + 1");
		else
			update_sql.append(", OtherNum = OtherNum + 1");
		// 开始统计日期，格式：2007-3-1。
		String strDayLong = "" + (now.getYear()+1900) + "-" + 
			(now.getMonth()+1) + "-" + now.getDate();
		if (isEmpty(stat_main.getStartDate())) {
			stat_main.setStartDate(strDayLong);
			update_sql.append(", StartDate = '" + strDayLong + "'");
		}
		// 最后一次统计本日查看数日期。
		if (isEmpty(stat_main.getOldDay())) {
			stat_main.setOldDay(strDayLong);
			update_sql.append(", OldDay = '" + strDayLong + "'");
		}
		
		// 更新月访问量，OldMonth 格式: 2007-6
		String strMonthLong = "" + (now.getYear()+1900) + "-" + (now.getMonth()+1);
		if (strMonthLong.equals(stat_main.getOldMonth())) {
			stat_main.setMonthNum(stat_main.getMonthNum() + 1);
			update_sql.append(", MonthNum = MonthNum + 1");
		}
		else {
			stat_main.setOldMonth(strMonthLong);
			stat_main.setMonthNum(1);
			update_sql.append(", OldMonth = '" + strMonthLong + "'")
				.append(", MonthNum = 1");
		}
		// 更新最大月访问量
		if (stat_main.getMonthNum() > stat_main.getMonthMaxNum()) {
			update_sql.append(", MonthMaxNum = " + stat_main.getMonthNum())
				.append(", MonthMaxDate = '" + strMonthLong + "'");
		}
		// 更新日访问量。
		if (strDayLong.equals(stat_main.getOldDay())) {
			stat_main.setDayNum(stat_main.getDayNum() + 1);
			update_sql.append(", DayNum = DayNum + 1");
		} else {
			stat_main.setOldDay(strDayLong);
			stat_main.setDayNum(1);
			update_sql.append(", OldDay = '" + strDayLong + "'")
				.append(", DayNum = 1");
		}
		// 更新日最大访问量。
		if (stat_main.getDayNum() > stat_main.getDayMaxNum()) {
			stat_main.setDayMaxNum(stat_main.getDayNum());
			stat_main.setDayMaxDate(strDayLong);
			update_sql.append(", DayMaxNum = " + stat_main.getDayNum())
				.append(", DayMaxDate = '" + strDayLong + "'");
		}
		// 更新时访问量。
		String strHourLong = strDayLong + " " + now.getHours() + ":00:00";
		if (strHourLong.equals(stat_main.getOldHour())) {
			stat_main.setHourNum(stat_main.getHourNum() + 1);
			update_sql.append(", HourNum = HourNum + 1");
		} else {
			stat_main.setOldHour(strHourLong);
			stat_main.setHourNum(1);
			update_sql.append(", OldHour = '" + strHourLong + "'")
				.append(", HourNum = 1");
		}
		// 更新最大时访问量。
		if (stat_main.getHourNum() > stat_main.getHourMaxNum()) {
			stat_main.setHourMaxNum(stat_main.getHourNum());
			stat_main.setHourMaxTime(strHourLong);
			update_sql.append(", HourMaxNum = " + stat_main.getHourNum())
				.append(", HourMaxTime = '" + strHourLong + "'");
		}
		
		stat_dao.executeUpdate(update_sql.toString());
	}
	
	/**
	 * 更新其它统计信息。referer, system, browser, mozilla, screen, color 等。
	 *
	 */
	private void updateOtherStat() {
		// 统计来访关键字，通常是从搜索引擎查询某个关键字链接过来的??
		if (isStatItem("FKeyword")) {
			// 提取算法我们暂时不实现，因此这个统计先不做。
			// addNum visitorKeyword, "Stat_Keyword", "TKeyword", "TKeywordNum"
		}
		// 用户操作系统。
		if (isStatItem("FSystem")) {
			stat_dao.addNum(this._system, "Stat_System", "TSystem", "TSysNum");
		}
		// 用户浏览器类型。
		if (isStatItem("FBrowser")) {
			stat_dao.addNum(this._browser, "Stat_Browser", "TBrowser", "TBrwNum");
		}
		// Mozilla
		if (isStatItem("FMozilla")) {
			stat_dao.addNum(this._mozilla, "Stat_Mozilla", "TMozilla", "TMozNum");
		}
		// 屏幕分辨率。
		if (isStatItem("FScreen")) {
			stat_dao.addNum(this._screen, "Stat_Screen", "TScreen", "TScrNum");
		}
		// 屏幕颜色数。
		if (isStatItem("FColor")) {
			stat_dao.addNum(this.color, "Stat_Color", "TColor", "TColNum");
		}
		// 所在时区。
		if (isStatItem("FTimezone")) {
			stat_dao.addNum(this.timezone, "Stat_Timezone", "TTimezone", "TTimNum");
		}
		// Refer (在 CounterLink 里面, document.referer)
		if (isStatItem("FRefer")) {
			stat_dao.addNum(this.referer, "Stat_Refer", "TRefer", "TRefNum");
		}
		// Weburl
		if (isStatItem("FWeburl")) {
			stat_dao.addNum(this._weburl, "Stat_Weburl", "TWeburl", "TWebNum");
		}
		// 地址。
		if (isStatItem("FAddress")) {
			stat_dao.addNum(this._address, "Stat_Address", "TAddress", "TAddNum");
		}
		// IP 地址。
		if (isStatItem("FIP")) {
			stat_dao.addNum(this.ip, "Stat_Ip", "TIp", "TIpNum");
		}
	}
	
	/**
	 * 更新日期统计信息（年、月、周、日 访问统计）。
	 *
	 */
	private void updateDateStat() {
		// 日访问统计。格式：2007-3-1。 Stat_Day: TDay, H0-H23
		String strDayLong = DateUtil.getDateString(now);
		stat_dao.addNum(strDayLong, "Stat_Day", "TDay", "H" + now.getHours());
		stat_dao.addNum("Total", "Stat_Day", "TDay", "H" + now.getHours());
		
		// 年访问统计。 Stat_Year: TYear, M1-M12
		String strYear = "" + (now.getYear()+1900);
		stat_dao.addNum(strYear, "Stat_Year", "TYear", "M" + (now.getMonth()+1));
		stat_dao.addNum("Total", "Stat_Year", "TYear", "M" + (now.getMonth()+1));
		
		// 月访问统计。 Stat_Month: TMonth, D1-D31
		String strMonthLong = "" + (now.getYear()+1900) + "-" +	(now.getMonth()+1);
		stat_dao.addNum(strMonthLong, "Stat_Month", "TMonth", "D" + now.getDate());
		stat_dao.addNum("Total", "Stat_Month", "TMonth", "D" + now.getDate());
		
		// 周访问统计，以本周第一天为 key。
		String strWeek = "D" + (now.getDay()+1);
		String strDateLong = DateUtil.getDateString(DateUtil.getFirstDayOfWeek(now));
		stat_dao.addNum(strDateLong, "Stat_Week", "TWeek", strWeek);
		stat_dao.addNum("Total", "Stat_Week", "TWeek", strWeek);
	}
	
	private void update() {
		// 计算各个字段。
		calcFields();

		// 进行更新。
		updateVisit();
		modiMaxNum();
		updateOtherStat();
		updateDateStat();
	}
}
