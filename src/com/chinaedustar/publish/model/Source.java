package com.chinaedustar.publish.model;

import java.util.Date;
import java.util.List;

import com.chinaedustar.publish.PublishContext;
/**
 * 来源对象，父对象是来源集合对象，可以是属于频道，也可以是属于网站。
 * @author wangyi
 *
 */
public class Source extends AbstractPageModelBase {
	/** 频道的标识，默认为0：来源属于整个网站。 */
	private int channelId;
	
	/** 来源的图片。 */
	private String photo;
	
	/** 描述信息。 */
	private String description;
	
	/** 单位地址。 */
	private String address;
	
	/** 联系电话。 */
	private String tel;
	
	/** 传真。 */
	private String fax;
	
	/** 邮箱。 */
	private String mail;
	
	/** 邮编。 */
	private String zipCode;
	
	/** 主页。 */
	private String homePage;
	
	/** 电子邮箱。 */
	private String email;
	
	/** QQ号。 */
	private int qq;
	
	/** 联系人。 */
	private String contacterName;
	
	/** 来源类型：1：友情站点；2：中文站点；3：外文站点；4：其他站点。	 */
	private int sourceType;
	
	/** 最后使用时间。 */
	private Date lastUseTime;
	
	/** 是否已经通过审核。 */
	private boolean passed;
	
	/** 是否置顶。 */
	private boolean top;
	
	/** 是否推荐。 */
	private boolean commend;
	
	/** 点击次数。 */
	private int hits;
	
	/**
	 * 单位地址。
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 单位地址。
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 频道的标识，默认为0：来源属于整个网站。
	 * @return
	 */
	public int getChannelId() {
		return channelId;
	}
	
	/**
	 * 频道的标识，默认为0：来源属于整个网站。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 联系人。
	 * @return
	 */
	public String getContacterName() {
		return contacterName;
	}
	
	/**
	 * 联系人。
	 * @param contacterName
	 */
	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	}
	
	/**
	 * 描述信息。
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 描述信息。
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 电子邮箱。
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 电子邮箱。
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 传真。
	 * @return
	 */
	public String getFax() {
		return fax;
	}
	
	/**
	 * 传真。
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	/**
	 * 点击次数。
	 * @return
	 */
	public int getHits() {
		return hits;
	}
	
	/**
	 * 点击次数。
	 * @param hits
	 */
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	/**
	 * 主页。
	 * @return
	 */
	public String getHomePage() {
		return homePage;
	}
	
	/**
	 * 主页。
	 * @param homePage
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	
	/**
	 * 是否推荐。
	 * @return
	 */
	public boolean getCommend() {
		return commend;
	}
	
	/**
	 * 是否推荐。
	 * @param commend
	 */
	public void setCommend(boolean commend) {
		this.commend = commend;
	}
	
	/**
	 * 最后使用时间。
	 * @return
	 */
	public Date getLastUseTime() {
		return lastUseTime;
	}
	
	/**
	 * 最后使用时间。
	 * @param lastUseTime
	 */
	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}
	
	/**
	 * 邮箱。
	 * @return
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * 邮箱。
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * 是否置顶。
	 * @return
	 */
	public boolean getTop() {
		return top;
	}
	
	/**
	 * 是否置顶。
	 * @param top
	 */
	public void setTop(boolean top) {
		this.top = top;
	}
	
	/**
	 * 是否已经通过审核。
	 * @return
	 */
	public boolean isPassed() {
		return passed;
	}
	
	/**
	 * 是否已经通过审核。
	 * @param passed
	 */
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	/**
	 * 来源的图片。
	 * @return
	 */
	public String getPhoto() {
		return photo;
	}
	
	/**
	 * 来源的图片。
	 * @param photo
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	/**
	 * QQ号。
	 * @return
	 */
	public int getQq() {
		return qq;
	}
	
	/**
	 * QQ号。
	 * @param qq
	 */
	public void setQq(int qq) {
		this.qq = qq;
	}
	
	/**
	 * 来源类型：1：友情站点；2：中文站点；3：外文站点；4：其他站点。
	 * @return
	 */
	public int getSourceType() {
		return sourceType;
	}
	
	/**
	 * 来源类型：1：友情站点；2：中文站点；3：外文站点；4：其他站点。
	 * @param sourceType
	 */
	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}
	
	/**
	 * 联系电话。
	 * @return
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * 联系电话。
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	/**
	 * 邮编。
	 * @return
	 */
	public String getZipCode() {
		return zipCode;
	}
	
	/**
	 * 邮编。
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	/**
	 * 得到该来源的文章集合。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getArtilces() {
		String hql = "FROM Article WHERE source='" + super.getName() + "'";
		return _getPublishContext().getDao().list(hql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#calcPageUrl()
	 */
	@Override public String calcPageUrl() {			
		Site site = pub_ctxt.getSite();
		if (getIsGenerated()) {
			return site.getInstallDir() + getStaticPageUrl();
		}
		return site.getInstallDir() + "showSource.jsp?sourceId=" + getId();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#getNewStaticPageUrl(com.chinaedustar.publish.PublishContext)
	 */
	@Override protected String getNewStaticPageUrl(PublishContext pub_ctxt) {		
		return "source/source_" + getId() + ".html";
	}
}
