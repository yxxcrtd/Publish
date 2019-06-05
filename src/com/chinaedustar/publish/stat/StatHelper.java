package com.chinaedustar.publish.stat;

import java.text.ParseException;
import java.util.Date;

import com.chinaedustar.publish.PublishContext;


/**
 * 统计组件中使用的一些辅助函数。
 * @author liujunxing
 *
 */
public class StatHelper {
	/**
	 * 安全的分解统计中使用的日期，其格式支持 yyyy, yyyy-MM, yyyy-MM-dd, yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static final Date safeParseStatDate(String val) {
		return safeParseStatDate(val, null);
	}
	
	/**
	 * 安全的分解统计中使用的日期，其格式支持 yyyy, yyyy-MM, yyyy-MM-dd, yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static final Date safeParseStatDate(String val, Date def_date) {
		if (val == null || val.length() == 0) return def_date;
		val = val.trim();
		if (val.indexOf(' ') >= 0) {
			// 包含时间部分。
			return parseTime(val);
		} else {
			return parseDate(val);
		}
	}
	
	// 格式：yyyy-MM-dd hh:mm:ss
	private static Date parseTime(String val) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat();
		formatter.applyPattern("yyyy-M-d h:m:s");
		try {
			return formatter.parse(val);
		} catch (ParseException e) {
			// e.printStackTrace();
			return null;
		}
	}
	
	// 格式：yyyy, yyyy-M, yyyy-M-d
	private static Date parseDate(String val) {
		try {
			java.util.Calendar c = java.util.Calendar.getInstance();
			String[] parts = val.split("-");
			if (parts.length == 1)
				c.set(Integer.parseInt(parts[0]), 0, 1, 0, 0, 0);	// 2007-1-1, 该年的第一天。
			else if (parts.length == 2)
					// 该月的第一天
				c.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])-1, 1, 0, 0, 0);
			else
				c.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])-1, 
						Integer.parseInt(parts[2]), 0, 0, 0);
			return c.getTime();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 计算 end - start 的日子差值。
	 * @param start
	 * @param end
	 * @return
	 */
	public static final int getDiffDays(Date start, Date end) {
		long diff = end.getTime() - start.getTime();
		return (int)(diff / (3600*24*1000));	// 60分*60秒*24小时*1000毫秒
	}

	private static final String STAT_MAIN_KEY = "StatMain.Cached";
	/**
	 * 尝试从缓存
	 * @param pub_ctxt
	 * @param stat_dao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static StatMain getCacheStatMain(PublishContext pub_ctxt, StatDao stat_dao) {
		StatMain cached_stat_main = (StatMain)pub_ctxt.getMap().get(STAT_MAIN_KEY);
		if (cached_stat_main != null) return cached_stat_main;
		
		// 装载一个并放到 Map 中。
		cached_stat_main = stat_dao.loadStatMain();
		pub_ctxt.getMap().put(STAT_MAIN_KEY, cached_stat_main);
		return cached_stat_main;
	}
	
	/**
	 * 清掉缓存中的 stat_main.
	 * @param pub_ctxt
	 */
	public static void clearCacheStatMain(PublishContext pub_ctxt) {
		pub_ctxt.getMap().remove(STAT_MAIN_KEY);
	}
}
