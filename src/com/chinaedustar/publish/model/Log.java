package com.chinaedustar.publish.model;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * 表示一条日志记录。
 * @author liujunxing
 *
 */
public class Log {
	/** 成功。 */
	public static final int STATUS_SUCCESS = 0;
	
	/** 一般性失败。 */
	public static final int STATUS_FAIL = 1;
	
	/** 权限被拒绝。 */
	public static final int STATUS_ACCESS_DENIED = 2;
	
	/** 管理员未登录。 */
	public static final int STATUS_NOT_LOGIN = 3;
	
	/** 记录标识，数据库自动生成。 */
	private int id;
	
	/** 所进行的操作。 */
	private String operation;
	
	/** 状态码。 */
	private int status = STATUS_SUCCESS;
	
	/** 操作进行的时间。 */
	private java.util.Date operTime = new java.util.Date();
	
	/** 用户名。 */
	private String userName;
	
	/** 用户 IP 地址。 */
	private String userIP;
	
	/** 详细描述。 */
	private String description;
	
	/** 操作的 URL 地址。 */
	private String url;
	
	/** 从网页提交过来的数据。 */
	private String postData;

	// === getter/setter ===============================================================
	
	/** 记录标识，数据库自动生成。 */
	public int getId() {
		return this.id;
	}

	/** 记录标识，数据库自动生成。 */
	public void setId(int id) {
		this.id = id;
	}

	/** 所进行的操作。 */
	public String getOperation() {
		return this.operation;
	}
	
	/** 所进行的操作。 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	/** 状态码。 */
	public int getStatus() {
		return this.status;
	}
	
	/** 状态码。 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/** 操作进行的时间。 */
	public java.util.Date getOperTime() {
		return this.operTime;
	}

	/** 操作进行的时间。 */
	public void setOperTime(java.util.Date operTime) {
		this.operTime = operTime;
	}

	/** 用户名。 */
	public String getUserName() {
		return this.userName;
	}

	/** 用户名。 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** 用户 IP 地址。 */
	public String getUserIP() {
		return this.userIP;
	}
	
	/** 用户 IP 地址。 */
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	
	/** 详细描述。 */
	public String getDescription() {
		return this.description;
	}

	/** 详细描述。 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 操作的 URL 地址。 */
	public String getUrl() {
		return this.url;
	}

	/** 操作的 URL 地址。 */
	public void setUrl(String url) {
		this.url = url;
	}

	/** 从网页提交过来的数据。 */
	public String getPostData() {
		return this.postData;
	}
	
	/** 从网页提交过来的数据。 */
	public void setPostData(String postData) {
		this.postData = postData;
	}

	/**
	 * 辅助用函数，帮助设置 IP, Url, PostData
	 */
	@SuppressWarnings("rawtypes")
	public void setIPUrlPostData(PageContext page_ctxt) {
		try {
			this.userIP = page_ctxt.getRequest().getRemoteAddr();
			// 下述情况发生在测试时，我们是用 TestServletRequest 模拟。
			if (!(page_ctxt.getRequest() instanceof HttpServletRequest)) return;
			
			HttpServletRequest request = (HttpServletRequest)page_ctxt.getRequest();
			this.url = (String)request.getAttribute("javax.servlet.forward.request_uri");
			if (this.url == null || this.url.length() == 0)
				this.url = request.getRequestURI();
			
			Map map = request.getParameterMap();
			if (map == null || map.isEmpty()) return;
			
			StringBuilder strbuf = new StringBuilder();
			strbuf.append("{");
			@SuppressWarnings("unchecked")
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				// startsWith '__' 我们认为是系统的，不记录。
				if (entry.getKey().toString().startsWith("_")) continue;
				
				strbuf.append(entry.getKey()).append("=[");
				// 不记录 password, 最好前端也不传递 password 过来。
				if (entry.getKey().equals("password") == false) {
					Object[] values = (Object[])entry.getValue();
					if (values != null && values.length > 0) {
						for (int i = 0; i < values.length; ++i) {
							if (i > 0) strbuf.append(", ");
							strbuf.append(values[i]);
						}
					}
				} else {
					strbuf.append("******");
				}
				strbuf.append("]");
				if (iter.hasNext())
					strbuf.append(",\r\n");
			}
			strbuf.append("}");
			this.postData = strbuf.toString();
		} catch (Exception ex) {
			// ignore.
		}
	}
}
