package com.chinaedustar.publish.model;

import java.util.Date;
import java.util.List;

import com.chinaedustar.publish.PublishContext;

/**
 * 作者对象，拥有者对象是作者集合对象，可以是频道的作者，也可以是网站的作者。
 * @author wangyi
 *
 */
public class Author extends AbstractPageModelBase {
	/** 频道的标识，默认为 0：全站的作者。 */
	private int channelId;
	
	/** 性别，1：男；0：女。 */
	private byte sex = -1;
	
	/** 出生日期。 */
	private Date birthDay;
	
	/** 照片。 */
	private String photo;
	
	/** 描述信息。 */
	private String description;
	
	/** 家庭地址。 */
	private String address;
	
	/** 联系电话。 */
	private String tel;
	
	/** 传真。 */
	private String fax;
	
	/** 公司名称。 */
	private String company;
	
	/** 部门名称。 */
	private String department;
	
	/** 邮编。 */
	private String zipCode;
	
	/** 主页地址。 */
	private String homePage;
	
	/** 电子邮箱。 */
	private String email;
	
	/** QQ号。 */
	private int qq;
	
	/** 作者类型，1：大陆作者 */
	public static final int AUTHOR_TYPE_DALU = 1;
	/** 作者类型，2：港台作者 */
	public static final int AUTHOR_TYPE_GANGTAI = 2;
	/** 作者类型，3：海外作者 */
	public static final int AUTHOR_TYPE_HAIWAI = 3;
	/** 作者类型，4：本站特约 */
	public static final int AUTHOR_TYPE_BENZHAN = 4;
	/** 作者类型，5：其他作者 */
	public static final int AUTHOR_TYPE_QITA = 5;
	
	/** 作者类型，1：大陆作者；2：港台作者；3：海外作者；4：本站特约；5：其他作者。 */
	private int authorType = 1;
	
	/** 最后使用日期。 */
	private Date lastUseTime;
	
	/** 是否已经审核通过。 */
	private boolean passed;
	
	/** 是否推荐。 */
	private boolean commend;
	
	/** 是否置顶。 */
	private boolean top;
	
	/** 点击次数。 */
	private int hits;
	
	/**
	 * 家庭地址。
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 家庭地址。
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 作者类型，1：大陆作者；2：港台作者；3：海外作者；4：本站特约；5：其他作者。
	 * @return
	 */
	public int getAuthorType() {
		return authorType;
	}
	
	public String getAuthorTypeString() {
		switch (authorType) {
		case 1: return "大陆作者";
		case 2: return "港台作者";
		case 3: return "海外作者";
		case 4: return "本站特约";
		case 5: return "其它作者";
		default: return "未知";
		}
	}
	
	/**
	 * 作者类型，1：大陆作者；2：港台作者；3：海外作者；4：本站特约；5：其他作者。
	 * @param authorType
	 */
	public void setAuthorType(int authorType) {
		this.authorType = authorType;
	}
	
	/**
	 * 出生日期。
	 * @return
	 */
	public Date getBirthDay() {
		return birthDay;
	}
	
	/**
	 * 出生日期。
	 * @param birthDay
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	/**
	 * 频道的标识，默认为0：网站的作者。
	 * @return
	 */
	public int getChannelId() {
		return channelId;
	}
	
	/**
	 * 频道的标识，默认为0：网站的作者。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 公司名称。
	 * @return
	 */
	public String getCompany() {
		return company;
	}
	
	/**
	 * 公司名称。
	 * @param company
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	
	/**
	 * 部门名称。
	 * @return
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * 部门名称。
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
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
	 * 主页地址。
	 * @return
	 */
	public String getHomePage() {
		return homePage;
	}
	
	/**
	 * 主页地址。
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
	 * 最后使用日期。
	 * @return
	 */
	public Date getLastUseTime() {
		return lastUseTime;
	}
	
	/**
	 * 最后使用日期。
	 * @param lastUseType
	 */
	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
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
	 * 是否已经审核通过。
	 * @return
	 */
	public boolean getPassed() {
		return passed;
	}
	
	/**
	 * 是否已经审核通过。
	 * @param passed
	 */
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	/**
	 * 照片。
	 * @return
	 */
	public String getPhoto() {
		return photo;
	}
	
	/**
	 * 照片。
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
	 * 性别，1：男；0：女。
	 * @return
	 */
	public byte getSex() {
		return sex;
	}
	
	/**
	 * 根据 sex 获得其显示文字。
	 * @return
	 */
	public String getSexValue() {
		if (this.sex == 1) return "男";
		else if (this.sex == 0) return "女";
		return "未知";
	}
	
	/**
	 * 性别，1：男；0：女。
	 * @param sex
	 */
	public void setSex(byte sex) {
		this.sex = sex;
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
	 * 得到作者的文章集合。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public List getArtilces() {
		String hql = "FROM Article WHERE author='" + super.getName() + "'";
		return _getPublishContext().getDao().list(hql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#calcPageUrl()
	 */
	public String calcPageUrl() {
		Site site = pub_ctxt.getSite();
		if (getIsGenerated()) {
			return site.getInstallDir() + getStaticPageUrl();
		}
		return site.getInstallDir() + "showAuthor.jsp?authorId=" + getId();
	}
	
	/**
	 * 得到新的静态化地址。
	 * @return
	 */
	protected String getNewStaticPageUrl(PublishContext pub_ctxt) {		
		Site site = pub_ctxt.getSite();
		if (channelId <= 0) {		// 全站专题
			return "author/author_" + getId() + ".html";
		} else {					// 频道的专题
			Channel channel = site.getChannels().getChannel(channelId);
			if (channel != null) {
				return channel.getChannelDir() + "/author/" + getId() + ".html";
			}
		}
		return "";
	}
}
