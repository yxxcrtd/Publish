package com.chinaedustar.publish.model;

import java.util.Date;

/**
 * 网站留言。
 * @author wangyi
 *
 */
public class Feedback extends AbstractModelBase implements PublishModelObject {
	/** 留言的标题。 */
	private String title;
	
	/** 用户名。 **/
	private String userName;
	
	/** 留言的内容。 **/
	private String content;
	
	/** 留言的时间。 **/
	private Date createTime = new Date();
	
	/** 留言的点击次数。 */
	private int hits;
	
	/** 主文档标识。 */
	private int mainFlag;
	
	/** 主文档ID。 */
	private int mainId;
	
	/** 用户性别。 */
	private int userSex;
	
	/** 用户QQ。 */
	private String userQq;
	
	/** 用户ICQ。 */
	private String userIcq;
	
	/** 用户MSN。 */
	private String userMsn;
	
	/** 用户主页。 */
	private String homepage;
	
	/**用户Email*/
	private String email;
	
	/** 留言的状态，0：未审核，1：审核通过。 **/
	private int status;
	
	/** 是否公开显示，不设置的时候采取网站中的留言显示设置。 */
	private Boolean isDisplay;
	
	/** 是否静态化了。 **/
	private boolean isGenerated;
	
	/** 静态化链接地址。 **/
	private String staticPageUrl;
	
	/** 留言的链接地址。 */
	@SuppressWarnings("unused")
	private String pageUrl;

	/**
	 * 留言的内容。
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content 留言的内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 留言的时间。
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime 留言的时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 留言的点击次数。
	 * @return
	 */
	public int getHits() {
		return hits;
	}
	
	/**
	 * 留言的点击次数。
	 * @param hits
	 */
	public void setHits(int hits) {
		this.hits = hits;
	}

	/**
	 * 是否静态化了。
	 * @return isGenerated
	 */
	public boolean getIsGenerated() {
		return isGenerated;
	}

	/**
	 * @param isGenerated 是否静态化了。
	 */
	public void setIsGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}

	/**
	 * 静态化链接地址。
	 * @return staticPageUrl
	 */
	public String getStaticPageUrl() {
		return staticPageUrl;
	}

	/**
	 * @param staticPageUrl 静态化链接地址。
	 */
	public void setStaticPageUrl(String staticPageUrl) {
		this.staticPageUrl = staticPageUrl;
	}

	/**
	 * 留言的状态，0：未审核，1：审核通过。
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status 留言的状态，0：未审核，1：审核通过。
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * 是否公开显示，不设置的时候采取网站中的留言显示设置。
	 * @return
	 */
	public Boolean getIsDisplay() {
		return isDisplay;
	}
	
	/**
	 * 是否公开显示，不设置的时候采取网站中的留言显示设置。
	 * @param isDisplay
	 */
	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * 留言的标题。
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 留言的标题。
	 * @param title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 用户名。
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName 用户名。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 留言得了链接地址，暂时只返回动态的链接。
	 * @return
	 */
	public String getPageUrl() {
		return pub_ctxt.getSite().getInstallDir() + "feedback.jsp?feedbackId=" + getId();
	}
	
	/**
	 * 留言得了链接地址
	 * @param pageUrl
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * 用户主页
	 * @return
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * 用户主页
	 * @param homepae
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * 是否主文档
	 * @return
	 */
	public int getMainFlag() {
		return mainFlag;
	}

	/**
	 * 是否主文档标识
	 * @param mainFlag
	 */
	public void setMainFlag(int mainFlag) {
		this.mainFlag = mainFlag;
	}

	/**
	 * 主文档ID
	 * @return
	 */
	public int getMainId() {
		return mainId;
	}

	/**
	 * 主文档ID
	 * @param mainId
	 */
	public void setMainId(int mainId) {
		this.mainId = mainId;
	}

	/**
	 * 用户Icq
	 * @return
	 */
	public String getUserIcq() {
		return userIcq;
	}

	/**
	 * 用户Icq
	 * @param userIcq
	 */
	public void setUserIcq(String userIcq) {
		this.userIcq = userIcq;
	}

	/**
	 * 用户Msn
	 * @return
	 */
	public String getUserMsn() {
		return userMsn;
	}

	/**
	 * 用户Msn
	 * @param userMsn
	 */
	public void setUserMsn(String userMsn) {
		this.userMsn = userMsn;
	}

	/**
	 * 用户Qq
	 * @return
	 */
	public String getUserQq() {
		return userQq;
	}

	/**
	 * 用户Qq
	 * @param userQq
	 */
	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	/**
	 * 用户性别
	 * @return
	 */
	public int getUserSex() {
		return userSex;
	}

	/**
	 * 用户性别
	 * @param userSex
	 */
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	/**
	 * 用户Email
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 用户Email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
