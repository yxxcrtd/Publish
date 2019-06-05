package com.chinaedustar.publish.action;

/**
 * 表示消息提示页面中一个操作链接的信息。
 * @author wangyi
 *
 */
public class ActionLink {
	/** 链接的URL，如：http://www.chinaedustar.com 。 */
	private String url;
	
	/** 链接的目标，如：_blank, _self 。 */
	private String target = "";
	
	/** 链接的显示标题。可选。 */
	private String title = "";
	
	/** 链接的文本。可选。 */
	private String text = "";

	/** 常用的链接： "返回", "javascript:history.go(-1);" */
	public static final ActionLink HISTORY_BACK = new ActionLink("返回", "javascript:history.go(-1);"); 
	
	/**
	 * 使用缺省值构造一个 ActionLink 的新实例。
	 *
	 */
	public ActionLink() {
		
	}
	
	/**
	 * 使用指定的链接文本参数构造一个 ActionLink 的新实例。
	 * @param text
	 */
	public ActionLink(String text) {
		this.text = text;
	}
	
	/**
	 * 使用指定的链接文本和链接地址参数构造一个 ActionLink 的新实例。
	 * @param text
	 * @param url
	 */
	public ActionLink(String text, String url) {
		this.text = text;
		this.url = url;
	}
	
	/**
	 * 使用指定的链接文本、链接地址、目标窗口、提示参数构造一个 ActionLink 的新实例。
	 * @param text
	 * @param url
	 * @param target
	 * @param title
	 */
	public ActionLink(String text, String url, String target, String title) {
		this.text = text;
		this.url = url;
		this.target = target;
		this.title = title;
	}
	
	/**
	 * 链接的URL，如：http://www.chinaedustar.com 。
	 * @return href
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 链接的URL，如：http://www.chinaedustar.com 。
	 * @param href 要设置的 href
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 链接的目标，如：_blank, _self 。
	 * @return target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 链接的目标，如：_blank, _self 。
	 * @param target 要设置的 target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 链接的文本。
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * 链接的文本。
	 * @param text 要设置的 text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 链接的显示标题。
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 链接的显示标题。
	 * @param title 要设置的 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
