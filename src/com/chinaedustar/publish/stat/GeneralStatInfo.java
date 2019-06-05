package com.chinaedustar.publish.stat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Site;


/**
 * 综合统计信息 bean, 同时提供 StatMain 的一些额外包装。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class GeneralStatInfo {
	/** 统计配置和总统计数据, 原始的。 */
	private StatMain stat_main;
	
	/** 数据访问对象。 */
	private StatDao stat_dao;
	
	/** 在线人数，必须调用 setOnlineNum() 之后才能 getOnlineNum() */
	private int onlineNum;
	
	/**
	 * 构造函数。
	 *
	 */
	public GeneralStatInfo() {
		
	}
	
	/** 统计配置和总统计数据, 原始的。 */
	public void setStatMain(StatMain stat_main) {
		this.stat_main = stat_main;
	}
	
	/** 统计配置和总统计数据, 原始的。 */
	public StatMain getStatMain() {
		return this.stat_main;
	}
	
	/** 访问 Stat_XXX 数据的 dao 对象。 */
	public StatDao getStatDao() {
		return this.stat_dao;
	}
	
	/** 访问 Stat_XXX 数据的 dao 对象。 */
	public void setStatDao(StatDao stat_dao) {
		this.stat_dao = stat_dao;
	}


	/**
	 * 获得从开始统计日期算起到现在的总统计天数。
	 * @return
	 */
	public int getTotalDays() {
		Date start_date = StatHelper.safeParseStatDate(stat_main.getStartDate());
		if (start_date == null) return 0;	// 还未开始统计。
		
		// 计算该日期到现在的日差。
		return StatHelper.getDiffDays(start_date, new Date());
	}

	/** 总访问量，= -1 表示未获取。 */
	private int _total_visit = -1;
	
	/**
	 * 获得总访问人次。 = Sigma[Stat_Visit]
	 * @return
	 */
	public int getTotalVisitNum() {
		if (_total_visit == -1) 
			_total_visit = stat_dao.getTotalVisitNum();
		return _total_visit;
	}

	/**
	 * 获得平均日访问量。
	 * @return
	 */
	public int getAvgVisitNum() {
		int total_days = getTotalDays();
		if (total_days <= 0) return 0;
		return getTotalVisitNum()/total_days;
	}
	
	private boolean isStatItem(String item) {
		if (item == null || item.length() == 0) return false;
		
		String temp = stat_main.getStatItems();
		if (temp == null) return false;
		String[] stat_fields = temp.split(",");
		for (int i = 0; i < stat_fields.length; ++i) {
			if (item.equalsIgnoreCase(stat_fields[i].trim()))
				return true;
		}
		return false;
	}
	
	/** 最常用的浏览器。 */
	private Map _max_browser;
	
	/**
	 * 判断是否统计浏览器类型。
	 * @return
	 */
	public boolean getIsStatBrowser() {
		return isStatItem("FBrowser");
	}
	
	/**
	 * 获得最常用的浏览器信息。
	 * @return
	 */
	public Map getMaxBrowser() {
		if (_max_browser == null) {
			_max_browser = stat_dao.getMaxBrowser();
			if (_max_browser == null) _max_browser = new HashMap();
		}
		return _max_browser;
	}

	/** 最常用操作系统。 */
	private Map _max_system;
	
	/**
	 * 判断是否统计操作系统类型。
	 * @return
	 */
	public boolean getIsStatSystem() {
		return isStatItem("FSystem");
	}
	
	/**
	 * 获得最常用操作系统信息。
	 * @return
	 */
	public Map getMaxSystem() {
		if (_max_system == null) {
			_max_system = stat_dao.getMaxSystem();
			if (_max_system == null) _max_system = new java.util.HashMap();
		}
		return _max_system;
	}
	
	/** 最常用屏幕大小。 */
	private Map _max_screen;
	
	/**
	 * 判断是否统计屏幕大小信息。
	 * @return
	 */
	public boolean getIsStatScreen() {
		return isStatItem("FScreen");
	}
	
	/**
	 * 获得最常用屏幕大小。
	 * @return
	 */
	public Map getMaxScreen() {
		if (_max_screen == null) {
			_max_screen = stat_dao.getMaxScreen();
			if (_max_screen == null) _max_screen = new HashMap(); 
		}
		return _max_screen;
	}
	
	/** 最常用颜色数。 */
	private Map _max_color;
	
	/**
	 * 判断是否统计屏幕颜色数。
	 * @return
	 */
	public boolean getIsStatColor() {
		return isStatItem("FColor");
	}
	
	/**
	 * 获得最常用颜色数。
	 * @return
	 */
	public Map getMaxColor() {
		if (_max_color == null) {
			_max_color = stat_dao.getMaxColor();
			if (_max_color == null) _max_color = new HashMap(); 
		}
		return _max_color;
	}

	/** 在线人数，必须调用 setOnlineNum() 之后才能 getOnlineNum() */
	public int getOnlineNum() {
		return this.onlineNum;
	}
	
	/** 在线人数，必须调用 setOnlineNum() 之后才能 getOnlineNum() */
	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}

	/**
	 * 判断是否统计来访网站地址。
	 * @return
	 */
	public boolean getIsStatWeburl() {
		return isStatItem("FWeburl");
	}
	
	private Map _max_weburl;
	
	/**
	 * 获得最多的来访网站地址。
	 * @return
	 */
	public Map getMaxWeburl() {
		if (_max_weburl == null) {
			_max_weburl = stat_dao.getMaxWeburl();
			if (_max_weburl == null) _max_weburl = new HashMap(); 
		}
		return _max_weburl;
	}
	
	/**
	 * 判断是否统计用户地址。
	 * @return
	 */
	public boolean getIsStatAddress() {
		return isStatItem("FAddress");
	}
	
	private Map _max_address;

	/**
	 * 获得最多的用户地址。
	 * @return
	 */
	public Map getMaxAddress() {
		if (_max_address == null) {
			_max_address = stat_dao.getMaxAddress();
			if (_max_address == null) _max_address = new HashMap(); 
		}
		return _max_address;
	}

	private int _week_num = -1;
	
	/**
	 * 得到本周的访问数量。
	 * @return
	 */
	public int getWeekNum() {
		if (_week_num == -1) {
			_week_num = stat_dao.getWeekNum();
		}
		return _week_num;
	}

	/**
	 * 将数字字符串格式化为一组 <img> 图片。
	 * @param number
	 * @return
	 */
	public String formatNumberAsImage(int number, String format) {
		// step1: 将数字按照指定格式转换为字符串。
		java.text.DecimalFormat a = new java.text.DecimalFormat();
		a.applyPattern(format);
		String result = a.format((long)number);
		
		// step2: 将字符串变成 <img> 串。
		if (result == null || result.length() == 0) return "";
		
		String installDir = getInstallDir();
		StringBuilder strbuf = new StringBuilder();
		for (int i = 0; i < result.length(); ++i) {
			char ch = result.charAt(i);
			strbuf.append("<img border=\"0\" src=\"")
				.append(installDir).append("images/num/").append(ch).append(".gif\" />");
		}
		return strbuf.toString();
	}
	
	/**
	 * 得到系统安装路径。
	 * @return
	 */
	public String getInstallDir() {
		PublishContext pub_ctxt = PublishUtil.getPublishContext();
		Site site = pub_ctxt.getSite();
		return site.getInstallDir();
	}
	
}
