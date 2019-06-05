package com.chinaedustar.publish.model;

import java.util.Date;

import com.chinaedustar.publish.PublishContext;

/**
 * 表示网站公告业务对象。
 * 
 * @author liujunxing
 *
 */
public class Announcement extends AbstractPageModelBase {
	/** 公告的标题。 */
	private String title;
	
	/** 公告的内容。 */
	private String content;
	
	/** 公告的作者。 */
	private String author;
	
	/** 公告创建日期。 */
	private Date createDate;
	
	/** 显示公告的截止日期 */
	private Date offDate;
	
	/** 是否选中，只有被选中的公告才会列出。 */
	private boolean isSelected;
	
	/** 所属频道标识。如果=-1表示为频道公共公告，=0，表示是主页公告，其它为该频道的公告。 */
	private int channelId;
	
	/** 显示方式：0 – 全部；1-滚动；2-弹出。 */
	private int showType;
	
	/** 过期时间，单位：天。 */
	private int outTime;
	
	/**
	 * 获取公告的标题。
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 设置公告的标题。
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取公告的内容。
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * 设置公告的内容。
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 获取公告的作者。
	 * @return
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * 设置公告的作者。
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * 获取公告创建日期。
	 * @return
	 */
	public Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * 设置公告创建日期。
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 获取显示公告的截止日期。
	 * @return
	 */
	public Date getOffDate() {
		return offDate;
	}
	
	/**
	 * 设置显示公告的截止日期。
	 * @param date
	 */
	public void setOffDate(Date date) {
		offDate = date;
	}
	
	/**
	 * 获取是否选中，只有被选中的公告才会列出。
	 * @return
	 */
	public boolean getIsSelected() {
		return this.isSelected;
	}
	
	/**
	 * 设置是否选中，只有被选中的公告才会列出。
	 * @param isSelected
	 */
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	/**
	 * 获取所属频道标识。如果=-1表示为频道公共公告，=0，表示是主页公告，其它为该频道的公告。
	 * @return
	 */
	public int getChannelId() {
		return this.channelId;
	}
	
	/**
	 * 设置所属频道标识。如果=-1表示为频道公共公告，=0，表示是主页公告，其它为该频道的公告。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 获取显示方式：0 – 全部；1-滚动；2-弹出。
	 * @return
	 */
	public int getShowType() {
		return this.showType;
	}
	
	/**
	 * 设置显示方式：0 – 全部；1-滚动；2-弹出。
	 * @param showType
	 */
	public void setShowType(int showType) {
		this.showType = showType;
	}
	
	/**
	 * 获取过期时间，单位：天。
	 * @return
	 */
	public int getOutTime() {
		return this.outTime;
	}
	
	/**
	 * 设置过期时间，单位：天。
	 * @param outTime
	 */
	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}
	
	/**
	 * 得到新的静态化地址。
	 * @return
	 */
	protected String getNewStaticPageUrl(PublishContext pub_ctxt) {
		if (channelId <= 0) {
			return "announce/announce_" + channelId + "_" + getId() + ".html";
		} else {
			Channel channel = pub_ctxt.getSite().getChannels().getChannel(channelId);
			if (channel != null) {
				return channel.getChannelDir() + "/announce/announce_" + channelId + "_" + getId() + ".html";
			}
		}
		return "";
	}
	
	/**
	 * 返回对象的页面的地址。
	 * @return
	 */
	public String calcPageUrl() {
		Site site = pub_ctxt.getSite();
		String installDir = site.getInstallDir();
		if (channelId <= 0) {
			if (getIsGenerated()) {
				return installDir + getStaticPageUrl();
			}
		} else {
			Channel channel = site.getChannels().getChannel(channelId);
			if (channel != null) {
				if (getIsGenerated() && channel.getUseCreateHTML() == 1) {
					return installDir + getStaticPageUrl();
				}
			}
		}
		return installDir + "showAnnounce.jsp?announceId=" + getId();
	}
}
