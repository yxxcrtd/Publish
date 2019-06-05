package com.chinaedustar.publish;

import java.util.List;

/**
 * 表示一个操作的复杂返回结果。
 * 
 * @author liujunxing
 *
 */
public class Result {
	
	/** 表示一个缺省的表示成功的返回结果。 */
	public static final Result SUCCESS = new Result(0);
	
	/** 结果代码，一般 0 表示成功完成，其它值表示出错。 */
	private int code = 0;
	
	/** 最重要的信息标题，缺省为没有。 */
	private String title = "";
	
	/** 更多信息。 */
	private List<String> messages = new java.util.ArrayList<String>();
	
	/**
	 * 缺省构造。
	 *
	 */
	public Result() {
		
	}
	
	/**
	 * 构造。
	 * @param code
	 */
	public Result(int code) {
		this.code = code;
	}
	
	/**
	 * 构造。
	 * @param code
	 * @param title
	 */
	public Result(int code, String title) {
		this.code = code;
		this.title = title;
	}
	
	/** 结果代码，一般 0 表示成功完成，其它值表示出错。 */
	public int getCode() {
		return this.code;
	}
	
	/** 结果代码，一般 0 表示成功完成，其它值表示出错。 */
	public void setCode(int code) {
		this.code = code;
	}
	
	/** 最重要的信息标题，缺省为没有。 */
	public String getTitle() {
		return this.title;
	}
	
	/** 最重要的信息标题，缺省为没有。 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 更多信息。 */
	public List<String> getMessages() {
		return this.messages;
	}
	
	/** 添加更多信息。 */
	public void addMessage(String message) {
		this.messages.add(message);
	}
}
