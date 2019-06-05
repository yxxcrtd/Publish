package com.chinaedustar.publish.stat;

/**
 * Stat_Visitor 对应的数据 bean 类。
 * @author liujunxing
 *
 */
public class StatVisitor {
	/** 唯一标识。 */
	private int id;
	
	/** 来访时间。 */
	private java.util.Date visitTime;
	
	/** 来访 IP 地址。 */
	private String ip;
	
	/** 用户来自的国家地区地址。 */
	private String address;
	
	/** 所使用的操作系统。 */
	private String system;
	
	/** 所使用的浏览器。 */
	private String browser;
	
	/** 用户屏幕大小。 */
	private String screen;
	
	/** 用户屏幕色深。 */
	private String color;
	
	/** 来访的前一个页面。 */
	private String referer;
	
	/** 所在时区。 */
	private int timezone;

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the referer
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer the referer to set
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * @return the screen
	 */
	public String getScreen() {
		return screen;
	}

	/**
	 * @param screen the screen to set
	 */
	public void setScreen(String screen) {
		this.screen = screen;
	}

	/**
	 * @return the system
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * @return the timezone
	 */
	public int getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the visitTime
	 */
	public java.util.Date getVisitTime() {
		return visitTime;
	}

	/**
	 * @param visitTime the visitTime to set
	 */
	public void setVisitTime(java.util.Date visitTime) {
		this.visitTime = visitTime;
	}
}
