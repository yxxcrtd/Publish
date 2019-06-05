package com.chinaedustar.publish.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.PaginationInfo;
import com.chinaedustar.publish.stat.GeneralStatInfo;
import com.chinaedustar.publish.stat.OnlineCreator;
import com.chinaedustar.publish.stat.StatCollection;
import com.chinaedustar.publish.stat.StatCreator;
import com.chinaedustar.publish.stat.StatDao;
import com.chinaedustar.publish.stat.StatHelper;
import com.chinaedustar.publish.stat.StatMain;
import com.chinaedustar.publish.util.DateUtil;

/**
 * 计数器支持
 * 
 * @author liujunxing
 */
@SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
public class StatManage extends AbstractBaseManage {
	/** 统计用 DAO */
	private StatDao stat_dao;
	
	private static final Integer ZERO = new Integer(0);
	
	/**
	 * 构造函数。
	 * @param page_ctxt
	 */
	public StatManage(PageContext page_ctxt) {
		super(page_ctxt);
		this.stat_dao = (StatDao)pub_ctxt.getSpringContext().getBean("stat_dao");
	}

	/**
	 * 提供给 counter.jsp 页面使用，获取 'style' 参数。
	 * @return
	 */
	public String getStyle() {
		return param_util.safeGetStringParam("style");
	}
	
	/**
	 * 得到链接地址。
	 * @return
	 */
	public String getTheUrl() {
		javax.servlet.http.HttpServletRequest request = getRequest();
		// sample: http://localhost:8080/PubWeb/_test/test_request.jsp
		String theurl = request.getRequestURL().toString();
		theurl = getdir(theurl);
		if (theurl.endsWith("/") == false) theurl += "/";
		return theurl;
	}

	/**
	 * 得到 统计配置中的 intervalNum 项。
	 * @return
	 */
	public int getIntervalNum() {
		StatMain stat_main = StatHelper.getCacheStatMain(pub_ctxt, stat_dao);
		return stat_main.getIntervalNum();
	}
	
	/**
	 * 得到 统计配置中是否统计在线人员 IsCountOnline 属性。
	 * @return
	 */
	public boolean isCountOnline() {
		StatMain stat_main = StatHelper.getCacheStatMain(pub_ctxt, stat_dao);
		return stat_main.isCountOnline();
	}
	
	private String getdir(String filepath) {
		int i = filepath.lastIndexOf('/');
		if (i < 0) return filepath;
		return filepath.substring(0, i + 1);
	}
	
	private javax.servlet.http.HttpServletRequest getRequest() {
		return (javax.servlet.http.HttpServletRequest)page_ctxt.getRequest();
	}
	
	/**
	 * 实际产生计数。
	 *
	 */
	public GeneralStatInfo performCount() {
		// 1. 加载计数配置，创建并初始化计数生成器。
		StatMain stat_main = stat_dao.loadStatMain();
		StatCreator stat_creator = new StatCreator();
		stat_creator.setPublishContext(pub_ctxt);
		stat_creator.setStatDao(stat_dao);
		stat_creator.setStatMain(stat_main);
		
		// 2. 准备计数用数据，如 ip、来源地址、屏幕宽度、高度、色深 等
		String ip = page_ctxt.getRequest().getRemoteAddr();
		String userAgent = ((HttpServletRequest)page_ctxt.getRequest()).getHeader("user-agent");
		stat_creator.setIp(ip);
		stat_creator.setUserAgent(userAgent);
		
		String referer = param_util.safeGetStringParam("Referer");
		stat_creator.setReferer(referer);
		String width = param_util.safeGetStringParam("Width");
		String height = param_util.safeGetStringParam("Height");
		String color = param_util.safeGetStringParam("Color");
		String timezone = param_util.safeGetStringParam("Timezone");
		stat_creator.setWidth(width);
		stat_creator.setHeight(height);
		stat_creator.setColor(color);
		stat_creator.setTimezone(timezone);
		
		// 3. 更新统计数据。
		stat_creator.updateStat();
		
		// 更新之后，stat_main 里面部分数据也更新了，可以提供给页面使用。
		GeneralStatInfo stat_info = new GeneralStatInfo();
		stat_info.setStatDao(stat_dao);
		stat_info.setStatMain(stat_main);
		stat_info.setOnlineNum(stat_creator.getOnlineNum());
		return stat_info;
	}

	/**
	 * statOnline.jsp 页面调用，对在线人员进行统计。
	 *
	 */
	public void statOnline() {
		// 创建 OnlineCreator bean.
		OnlineCreator onl_creator = new OnlineCreator();
		onl_creator.setPublishContext(pub_ctxt);
		onl_creator.setStatDao(stat_dao);
		
		String ip = page_ctxt.getRequest().getRemoteAddr();
		String userAgent = ((HttpServletRequest)page_ctxt.getRequest()).getHeader("user-agent");
		String referer = ((HttpServletRequest)page_ctxt.getRequest()).getHeader("referer");
		onl_creator.setIp(ip);
		onl_creator.setUserAgent(userAgent);
		onl_creator.setReferer(referer);
		
		// 执行记录。
		onl_creator.doStatOnline();
	}
	
	/**
	 * admin_stat_config.jsp 初始化配置页面。
	 *
	 */
	public void initConfigPage() {
		// 检查权限。
		if (admin.checkSiteRight(Admin.OPERATION_STAT_MANAGE) == false)
			throw super.accessDenied();
		
		StatMain stat_main = stat_dao.loadStatMain();
		page_ctxt.setAttribute("stat_main", stat_main);
	}
	
	/**
	 * admin_stat_index.jsp 页面数据初始化。
	 *
	 */
	public void initIndexPage() {
		// 检查权限。
		if (admin.checkSiteRight(Admin.OPERATION_STAT_MANAGE) == false)
			throw super.accessDenied();
		
		String command = param_util.safeGetStringParam("command");
		if (command == null) command = "";
		page_ctxt.setAttribute("command", command);
		
		// stat_main - 统计的配置信息和总统计信息。
		StatMain stat_main = stat_dao.loadStatMain();
		page_ctxt.setAttribute("stat_main", stat_main);
		
		if ("FVisitor".equalsIgnoreCase(command))
			// 访问记录。
			initVisitorData();
		else if ("FCounter".equalsIgnoreCase(command))
			// 访问次数。
			initVisitNumData();
		else if ("StatYear".equalsIgnoreCase(command))
			// 年报表。
			initYearData();
		else if ("StatAllYear".equalsIgnoreCase(command))
			// 全部年报表。
			initAllYearData();
		else if ("StatMonth".equalsIgnoreCase(command))
			// 月报表。
			initMonthData();
		else if ("StatAllMonth".equalsIgnoreCase(command))
			// 全部月报表。
			initAllMonthData();
		else if ("StatWeek".equalsIgnoreCase(command))
			// 周报表。
			initWeekData();
		else if ("StatAllWeek".equalsIgnoreCase(command))
			// 全部周报表。
			initAllWeekData();
		else if ("StatDay".equalsIgnoreCase(command))
			// 日报表。
			initDayData();
		else if ("StatAllDay".equalsIgnoreCase(command))
			// 全部日报表。
			initAllDayData();
		else if ("FIp".equalsIgnoreCase(command))
			// 访问者IP地址分析。
			initFipData();
		else if ("FAddress".equalsIgnoreCase(command))
			// 访问者地址分析。
			initAddressData();
		else if ("FWeburl".equalsIgnoreCase(command))
			// 来访网站分析。
			initWeburlData();
		else if ("FReferer".equalsIgnoreCase(command))
			// 链接页面分析。
			initRefererData();
		else if ("FSystem".equalsIgnoreCase(command))
			// 访问者所用操作系统分析。
			initSystemData();
		else if ("FBrowser".equalsIgnoreCase(command))
			// 访问者所用浏览器分析。
			initBrowserData();
		else if ("FMozilla".equalsIgnoreCase(command))
			// 浏览器特征字串分析。
			initMozillaData();
		else if ("FScreen".equalsIgnoreCase(command))
			// 屏幕分辨率。
			initScreenData();
		else if ("FColor".equalsIgnoreCase(command))
			// 屏幕色深
			initColorData();
		else {
			// 综合统计信息。
			GeneralStatInfo stat_info = new GeneralStatInfo();
			stat_info.setStatMain(stat_main);
			stat_info.setStatDao(stat_dao);
			page_ctxt.setAttribute("stat_info", stat_info);
		}
	}
	
	/**
	 * 访问记录数据初始化。
	 *
	 */
	private void initVisitorData() {
		// 页面大小和当前页号。
		int page_size = param_util.safeGetIntParam("pageSize", 20);
		int page = param_util.safeGetIntParam("page", 1);
		PaginationInfo page_info = new PaginationInfo(page, page_size);
		page_info.setItemName("个访问记录");
		
		StatCollection stat_coll = new StatCollection();
		java.util.List visitor_list = stat_coll.getVisitorList(page_info);
		page_ctxt.setAttribute("visitor_list", visitor_list);
		page_ctxt.setAttribute("page_info", page_info);
	}
	
	/**
	 * 访问次数统计。
	 *
	 */
	private void initVisitNumData() {
		// 得到访问统计数据。
		java.util.Map visit_num = stat_dao.getVisitNum();
		// 计算总数。
		Integer total = ZERO;
		if (visit_num == null) {
			visit_num = new java.util.HashMap();
			total = ZERO;
		} else {
			total = (Integer)visit_num.get("T1") +
				(Integer)visit_num.get("T2") +
				(Integer)visit_num.get("T3") +
				(Integer)visit_num.get("T4") +
				(Integer)visit_num.get("T5") +
				(Integer)visit_num.get("T6") +
				(Integer)visit_num.get("T7") +
				(Integer)visit_num.get("T8") +
				(Integer)visit_num.get("T9") +
				(Integer)visit_num.get("T10");
		}
		visit_num.put("total", total);
		
		// 计算百分比。
		if (total.intValue() == 0) {
			for (int i = 1; i <= 10; ++i)
				visit_num.put("p" + i, ZERO);
		} else {
			for (int i = 1; i <= 10; ++i)
				visit_num.put("p" + i, ((Integer)visit_num.get("T" + i)).floatValue()*100.0/total.floatValue());
		}
		page_ctxt.setAttribute("visit_num", visit_num);
	}

	/**
	 * 产生年报表。
	 *
	 */
	private void initYearData() {
		// 得到今年的统计数据。
		java.util.Date now = new java.util.Date();
		int year = now.getYear() + 1900;
		String strYear = "" + year;
		java.util.Map stat_year = stat_dao.getStatYear(strYear);
		
		// 对此统计数据进行处理。
		handleStatYear(stat_year);
		page_ctxt.setAttribute("stat_year", stat_year);
	}
	
	/**
	 * 全部年报表。
	 *
	 */
	private void initAllYearData() {
		// 得到今年的统计数据。
		String strYear = "Total";
		java.util.Map stat_year = stat_dao.getStatYear(strYear);
		
		// 对此统计数据进行处理。
		handleStatYear(stat_year);
		if (stat_year != null) stat_year.put("TYear", "全部");
		page_ctxt.setAttribute("stat_year", stat_year);
	}
	
	// 处理年统计数据，计算总值和各个月平均值。
	private void handleStatYear(Map stat_year) {
		// 计算总值。
		if (stat_year == null) return;
		Integer zero = new Integer(0);
		Integer total = zero;
		int i;
		for (i = 1; i <= 12; ++i) {
			total += (Integer)stat_year.get("M" + i);
		}
		stat_year.put("total", total);
		
		// 计算百分比。
		for (i = 1; i <= 12; ++i) {
			if (total.intValue() == 0)
				stat_year.put("p" + i, zero);
			else
				stat_year.put("p" + i, ((Integer)stat_year.get("M" + i)).floatValue()*100.0/total.floatValue());
		}
	}

	/**
	 * 初始化月报表。
	 *
	 */
	private void initMonthData() {
		// 得到本月的统计数据。
		java.util.Date now = new java.util.Date();
		// strMonth: 2007-12
		String strMonth = "" + (now.getYear()+1900) + "-" + (now.getMonth()+1);
		java.util.Map stat_month = stat_dao.getStatMonth(strMonth);
		if (stat_month == null) {
			// 系统无数据。
			page_ctxt.setAttribute("stat_month", null);
			return;
		}
		
		handleMonthData(stat_month, strMonth);
	}
	
	/**
	 * 全部月报表。
	 *
	 */
	private void initAllMonthData() {
		String strMonth = "Total";
		java.util.Map stat_month = stat_dao.getStatMonth(strMonth);
		if (stat_month == null) {
			// 系统无数据。
			page_ctxt.setAttribute("stat_month", null);
			return;
		}
		
		handleMonthData(stat_month, null);
	}
	
	// 处理月统计数据。
	private void handleMonthData(java.util.Map stat_month, String strMonth) {
		// 得到该月的总的天数。
		int days = 31;
		String monthString = "";
		if (strMonth != null) {
			java.util.Date date = StatHelper.safeParseStatDate(strMonth);	// 该月的1号。
			java.util.GregorianCalendar c = new java.util.GregorianCalendar();
			c.setTime(date);
			int[] days_of_month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
			days = days_of_month[date.getMonth()];
			if (date.getMonth() == 1)	// 2 月要计算闰年。
				if (c.isLeapYear(date.getYear()+1900))
					days = 29;
			monthString = "" + (date.getYear()+1900) + "年" + (date.getMonth()+1) + "月";
		}
		
		// 计算所有天的数据总和。
		Integer zero = new Integer(0);
		Integer total = zero;
		for (int i = 1; i <= days; ++i) {
			total += (Integer)stat_month.get("D" + i);
		}
		stat_month.put("total", total);
		stat_month.put("monthString", monthString);
		page_ctxt.setAttribute("stat_month", stat_month);
		
		// 构造一个新的 List[Map] 来表示每天的数量，百分比。
		java.util.List list = new java.util.ArrayList();
		for (int i = 1; i <= days; ++i) {
			java.util.Map day_data = new java.util.HashMap();
			list.add(day_data);
			day_data.put("day", i);
			Integer num = (Integer)stat_month.get("D" + i);
			day_data.put("visit", num);
			if (total.intValue() == 0)
				day_data.put("percent", zero);
			else
				day_data.put("percent", num.floatValue()*100.0/total.floatValue());
		}
		page_ctxt.setAttribute("stat_list", list);
	}
	
	/**
	 * 初始化周报表。
	 *
	 */
	private void initWeekData() {
		// 得到本周的统计数据。
		java.util.Date now = new java.util.Date();
		// strDate: 2007-12-9, 使用一周的第一天做为 key
		String strWeekDate = DateUtil.getDateString(DateUtil.getFirstDayOfWeek(now));
		java.util.Map stat_week = stat_dao.getStatWeek(strWeekDate);
		
		handleWeekData(stat_week);
		page_ctxt.setAttribute("stat_week", stat_week);
	}
	
	/**
	 * 全部周报表。
	 *
	 */
	private void initAllWeekData() {
		// 得到所有周的统计数据。
		String strWeekDate = "Total";
		java.util.Map stat_week = stat_dao.getStatWeek(strWeekDate);
		
		handleWeekData(stat_week);
		page_ctxt.setAttribute("stat_week", stat_week);
	}
	
	// 处理周数据。
	private void handleWeekData(java.util.Map stat_week) {
		// 计算总值。
		if (stat_week == null) return;
		Integer zero = new Integer(0);
		Integer total = zero;
		int i;
		for (i = 1; i <= 7; ++i) {
			total += (Integer)stat_week.get("D" + i);
		}
		stat_week.put("total", total);
		
		// 计算百分比。
		for (i = 1; i <= 7; ++i) {
			if (total.intValue() == 0)
				stat_week.put("p" + i, zero);
			else
				stat_week.put("p" + i, ((Integer)stat_week.get("D" + i)).floatValue()*100.0/total.floatValue());
		}
	}

	/**
	 * 日报表。
	 *
	 */
	private void initDayData() {
		// 得到本日统计数据。
		java.util.Date now = new java.util.Date();
		String strDayLong = DateUtil.getDateString(now);
		java.util.Map stat_day = stat_dao.getStatDay(strDayLong);
		
		handleDayData(stat_day, strDayLong);
	}
	
	/**
	 * 全部日报表。
	 *
	 */
	private void initAllDayData() {
		// 得到所有日的统计数据。
		String strDayLong = "Total";
		java.util.Map stat_day = stat_dao.getStatDay(strDayLong);
		
		handleDayData(stat_day, null);
	}
	
	private void handleDayData(Map stat_day, String strDayLong) {
		page_ctxt.setAttribute("stat_day", stat_day);
		if (stat_day == null) return;
		
		// 计算所有小时的数据总和。
		Integer total = ZERO;
		for (int i = 0; i < 24; ++i) {
			total += (Integer)stat_day.get("H" + i);
		}
		stat_day.put("total", total);
		stat_day.put("dayString", strDayLong);
		
		// 构造一个新的 List[Map] 来表示每小时的数量，百分比。
		java.util.List list = new java.util.ArrayList();
		for (int i = 0; i < 24; ++i) {
			java.util.Map hour_data = new java.util.HashMap();
			list.add(hour_data);
			hour_data.put("hour", i);
			Integer num = (Integer)stat_day.get("H" + i);
			hour_data.put("visit", num);
			if (total.intValue() == 0)
				hour_data.put("percent", ZERO);
			else
				hour_data.put("percent", num.floatValue()*100.0/total.floatValue());
		}
		page_ctxt.setAttribute("stat_list", list);
	}

	/**
	 * 访问者IP地址分析
	 *
	 */
	private void initFipData() {
		List fip_list = stat_dao.getFipList();
		page_ctxt.setAttribute("fip_list", fip_list);
		
		// 统计总数。
		Integer total = calcTotalPercent(fip_list, "TIpNum");
		page_ctxt.setAttribute("total", total);
	}

	/**
	 * 访问者地址分析。
	 */
	private void initAddressData() {
		List addr_list = stat_dao.getAddressList();
		page_ctxt.setAttribute("addr_list", addr_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(addr_list, "TAddNum");
		page_ctxt.setAttribute("total", total);
	}

	/**
	 * 计算指定 List 中 numField 的总数(SUM)，并计算 percent 放到每个记录中。
	 * @param list
	 * @param numField
	 * @return - 返回总数，可能为 0。
	 */
	private Integer calcTotalPercent(List list, String numField) {
		// 统计总数。
		Integer total = ZERO;
		if (list == null || list.size() == 0) return total;
		
		for (int i = 0; i < list.size(); ++i) {
			Map rec = (Map)list.get(i);
			total += (Integer)rec.get(numField);
		}
			
		// 计算百分比。
		if (total.intValue() == 0) {
			for (int i = 0; i < list.size(); ++i) {
				Map rec = (Map)list.get(i);
				rec.put("percent", ZERO);
			}
		} else {
			for (int i = 0; i < list.size(); ++i) {
				Map rec = (Map)list.get(i);
				rec.put("percent", ((Integer)rec.get(numField)).floatValue()*100.0/total.floatValue());
			}
		}
		return total;
	}

	/**
	 * 来访网站分析。
	 * 
	 */
	private void initWeburlData() {
		List weburl_list = stat_dao.getWeburlList();
		page_ctxt.setAttribute("weburl_list", weburl_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(weburl_list, "TWebNum");
		page_ctxt.setAttribute("total", total);
	}

	/**
	 * 链接页面分析。
	 */
	private void initRefererData() {
		List refer_list = stat_dao.getRefererList();
		page_ctxt.setAttribute("refer_list", refer_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(refer_list, "TRefNum");
		page_ctxt.setAttribute("total", total);
	}

	/**
	 * 访问者所用操作系统分析。
	 */
	private void initSystemData() {
		List sys_list = stat_dao.getSystemList();
		page_ctxt.setAttribute("sys_list", sys_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(sys_list, "TSysNum");
		page_ctxt.setAttribute("total", total);
	}

	/**
	 * 访问者所用浏览器分析。
	 */
	private void initBrowserData() {
		List browser_list = stat_dao.getBrowserList();
		page_ctxt.setAttribute("browser_list", browser_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(browser_list, "TBrwNum");
		page_ctxt.setAttribute("total", total);
	}

	/**
	 * 浏览器特征字串分析。
	 */
	private void initMozillaData() {
		List mozilla_list = stat_dao.getMozillaList();
		page_ctxt.setAttribute("mozilla_list", mozilla_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(mozilla_list, "TMozNum");
		page_ctxt.setAttribute("total", total);
	}
	
	/**
	 * 提供用户屏幕数据。
	 *
	 */
	private void initScreenData() {
		List screen_list = stat_dao.getScreenList();
		page_ctxt.setAttribute("screen_list", screen_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(screen_list, "TScrNum");
		page_ctxt.setAttribute("total", total);
	}
	
	/**
	 * 用户屏幕色深数据。
	 *
	 */
	private void initColorData() {
		List color_list = stat_dao.getColorList();
		page_ctxt.setAttribute("color_list", color_list);
		
		// 统计总数，计算百分比。
		Integer total = calcTotalPercent(color_list, "TColNum");
		page_ctxt.setAttribute("total", total);
	}
}
