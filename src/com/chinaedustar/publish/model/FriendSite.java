package com.chinaedustar.publish.model;

import java.util.Date;

/**
 * 友情链接。
 * 
 * @author liujunxing
 */
public class FriendSite extends AbstractNamedModelBase {
	/**
	 * 缺省构造函数。
	 * 
	 */
	public FriendSite() {
	}

	/** 友情链接的 类别 标识。关联到 FsKind 对象。 */
	private FriendSiteKind kind;
	// private int kindId;

	/** 友情链接的 专题 标识。关联到 FsSpecial 对象。 */
	private FriendSiteSpecial special;
	// private int specialId;
	
	/** 链接类型。文字链接 = 1；图片链接 = 2。 */
	private int linkType;

	/** 网站的名字。 */
	private String siteName;

	/** 网站的链接地址。 */
	private String siteUrl;

	/** 该网站的描述。 */
	private String description;

	/** 该网站的 Logo 图片。 */
	private String logo;

	/** 该网站的管理员名字。 */
	private String siteAdmin;

	/** 该网站的邮件地址。 */
	private String siteEmail;

	/** 该网站管理员登录管理网站信息的密码。 */
	private String sitePassword;

	/** 网站星级，取值 0 - 5。 */
	private int stars;

	/** 点击数。 */
	private int hits;

	/** 最后修改日期。 */
	private Date lastModified;

	/** 是否精华站点。 */
	private boolean elite;

	/** 是否已经经过了审核。 */
	private boolean approved;

	/** 排序标识。 */
	private int orderId;

	// Property accessors

	public FriendSiteKind getKind() {
		return this.kind;
	}
	
	public void setKind(FriendSiteKind kind) {
		this.kind = kind;
	}
	
	public int getKindId() {
		return this.kind == null ? 0 : kind.getId();
	}

	public FriendSiteSpecial getSpecial() {
		return this.special;
	}
	
	public void setSpecial(FriendSiteSpecial special) {
		this.special = special;
	}
	
	public int getSpecialId() {
		return this.special == null ? 0 : special.getId();
	}
	
	public int getLinkType() {
		return this.linkType;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	@Override public String getName() {
		return this.siteName;
	}

	@Override public void setName(String name) {
		this.siteName = name;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUrl() {
		return this.siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSiteAdmin() {
		return this.siteAdmin;
	}

	public void setSiteAdmin(String siteAdmin) {
		this.siteAdmin = siteAdmin;
	}

	public String getSiteEmail() {
		return this.siteEmail;
	}

	public void setSiteEmail(String siteEmail) {
		this.siteEmail = siteEmail;
	}

	public String getSitePassword() {
		return this.sitePassword;
	}

	public void setSitePassword(String sitePassword) {
		this.sitePassword = sitePassword;
	}

	public int getStars() {
		return this.stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public int getHits() {
		return this.hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public boolean getElite() {
		return this.elite;
	}

	public void setElite(boolean elite) {
		this.elite = elite;
	}

	public boolean getApproved() {
		return this.approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
