package com.chinaedustar.publish.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期常用工具函数。
 * 
 * @author liujunxing
 *
 * 部分代码来自于：http://www.moon-soft.com/doc/37817.htm
 */
@SuppressWarnings("deprecation")
public class DateUtil {
	//private static final int[] dayArray = 
	//	new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	//private static final SimpleDateFormat sdf = new SimpleDateFormat();

	private DateUtil() {}
	
	/**
	 * 得到一个日历。
	 * @return
	 */
	public static Calendar getCalendar() {
		return GregorianCalendar.getInstance();
	}
	
	/**
	 * 得到当前时间。
	 * @return
	 */
	public static final Date getNow() {
		return new java.util.Date();
	}
	
	/**
	 * 比较两个日期实例代表的日期是否是同一天，同一天意味着 year, month, date 都相等。
	 * @param date1
	 * @param date2
	 * @return
	 * java 不让用 date.getDate() 等方法，但是用 Calendar 方法未免太繁琐???
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return (date1.getDate() == date2.getDate()) &&
			(date1.getMonth() == date2.getMonth()) &&
			(date1.getYear() == date2.getYear());
	}

	/**
	 * 给一个日期添加天数。
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * 给一个时间添加指定的秒数。
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSeconds(Date date, int seconds) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	/**
	 * 得到指定日期的前一天。
	 * @param date
	 * @return
	 */
	public static Date getPrevDay(Date date) {
		return addDays(date, -1);
	}
	
	/**
	 * 得到指定日期的后一天。
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		return addDays(date, +1);
	}
	
	/**
	  * 取得指定日期的所处星期的第一天。（以星期日做为一周开始的第一天）
	  * 
	  * @param date
	  *            指定日期。
	  * @return 指定日期的所处星期的第一天
	  */
	public static Date getFirstDayOfWeek(Date date) {
		/**
		 * 详细设计： 
		 * 1.如果date是星期日，则减0天 
		 * 2.如果date是星期一，则减1天 
		 * 3.如果date是星期二，则减2天
		 * 4.如果date是星期三，则减3天 
		 * 5.如果date是星期四，则减4天 
		 * 6.如果date是星期五，则减5天
		 * 7.如果date是星期六，则减6天
		 */
		GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
		gc.setTime( date );
		switch ( gc.get( Calendar.DAY_OF_WEEK ) )
		{
		case ( Calendar.SUNDAY  ):
			gc.add( Calendar.DATE, 0 );
		break;
		case ( Calendar.MONDAY  ):
			gc.add( Calendar.DATE, -1 );
		break;
		case ( Calendar.TUESDAY  ):
			gc.add( Calendar.DATE, -2 );
		break;
		case ( Calendar.WEDNESDAY  ):
			gc.add( Calendar.DATE, -3 );
		break;
		case ( Calendar.THURSDAY  ):
			gc.add( Calendar.DATE, -4 );
		break;
		case ( Calendar.FRIDAY  ):
			gc.add( Calendar.DATE, -5 );
		break;
		case ( Calendar.SATURDAY  ):
			gc.add( Calendar.DATE, -6 );
		break;
		}
		return gc.getTime();
	}
	
	/**
	 * 取得指定日期的所处星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的最后一天
	 */
	public static Date getLastDayOfWeek(Date date) {
		/**
		 * 详细设计： 
		 * 1.如果date是星期日，则加6天 
		 * 2.如果date是星期一，则加5天 
		 * 3.如果date是星期二，则加4天
		 * 4.如果date是星期三，则加3天 
		 * 5.如果date是星期四，则加2天 
		 * 6.如果date是星期五，则加1天
		 * 7.如果date是星期六，则加0天
		 */
		GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
		gc.setTime( date );
		switch ( gc.get( Calendar.DAY_OF_WEEK ) )
		{
		case ( Calendar.SUNDAY  ):
			gc.add( Calendar.DATE, 6 );
			break;
		case ( Calendar.MONDAY  ):
			gc.add( Calendar.DATE, 5 );
			break;
		case ( Calendar.TUESDAY  ):
			gc.add( Calendar.DATE, 4 );
			break;
		case ( Calendar.WEDNESDAY  ):
			gc.add( Calendar.DATE, 3 );
			break;
		case ( Calendar.THURSDAY  ):
			gc.add( Calendar.DATE, 2 );
			break;
		case ( Calendar.FRIDAY  ):
			gc.add( Calendar.DATE, 1 );
			break;
		case ( Calendar.SATURDAY  ):
			gc.add( Calendar.DATE, 0 );
			break;
		}
		return gc.getTime();
	}

	/**
	 * 得到指定日期的年份。
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return date.getYear() + 1900;
	}
	
	/**
	 * 得到指定日期的月份。月份从 1-12。
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return date.getMonth() + 1;
	}
	
	/**
	 * 得到指定日期的日。范围从1-31。
	 * @param date
	 * @return
	 */
	public static int getDate(Date date) {
		return date.getDate();
	}

	/**
	 * 得到指定日期的年字符串，格式为 yyyy。
	 * @param date
	 * @return
	 */
	public static String getYearString(Date date) {
		return String.valueOf(date.getYear()+1900);
	}
	
	/**
	 * 得到指定日期的年-月字符串，格式为 yyyy-M。
	 * @param date
	 * @return
	 */
	public static String getMonthString(Date date) {
		return String.valueOf(date.getYear()+1900) + "-" +
			String.valueOf(date.getMonth()+1);
	}

	/**
	 * 得到指定日期的年-月-日字符串，格式为 yyyy-M-d。
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		return String.valueOf(date.getYear()+1900) + "-" +
			String.valueOf(date.getMonth()+1) + "-" +
			String.valueOf(date.getDate());
	}
}
