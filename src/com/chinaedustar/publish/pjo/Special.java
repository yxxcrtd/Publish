package com.chinaedustar.publish.pjo;

/**
 * 专题对象。
 * 
 * @author liujunxing
 */
public class Special extends PagedModelBase {
	/** 所属的频道标识，如果标识为0，则为全站专题。关联到 Channel 对象。 */
	private int channelId;
	
	/** 专题排序。 */
	private int specialOrder;
	
	/** 专题的tips提示信息。 */
	private String tips;
	
	/** 专题的描述信息。 */
	private String description;
	
	/** 专题目录：限定为英文名。 */
	private String specialDir;
	
	/** 是否推荐，默认为0（不推荐）。 */
	private boolean isElite;
	
	/** 专题图片的URL。 */
	private String specialPicUrl;
	
	/** 专题的打开方式，1：新窗口打开；0：原窗口打开。默认为1。 */
	private int openType;
	
	/** 每页显示的项目数量。 */
	private int maxPerPage;

	// === getter/setter ======================================================
	
	/**
	 * 得到专题所属的频道标识。关联到 Channel 对象。
	 * @return
	 */
	public int getChannelId() {
		return channelId;
	}
	
	/**
	 * 设置专题所属的频道标识。关联到 Channel 对象。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 得到专题的描述信息。
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 设置专题的描述信息。
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 得到专题排序。
	 * @return
	 */
	public int getSpecialOrder() {
		return specialOrder;
	}
	
	/**
	 * 设置专题排序。
	 * @param specialOrder
	 */
	public void setSpecialOrder(int specialOrder) {
		this.specialOrder = specialOrder;
	}
	
	/**
	 * 得到专题目录：限定为英文名。
	 * @return
	 */
	public String getSpecialDir() {
		return specialDir;
	}
	
	/**
	 * 设置专题目录：限定为英文名。
	 * @param speicalDir
	 */
	public void setSpecialDir(String specialDir) {
		this.specialDir = specialDir;
	}
	
	/**
	 * 得到专题的tips提示信息。
	 * @return
	 */
	public String getTips() {
		return tips;
	}
	
	/**
	 * 设置专题的tips提示信息。
	 * @param tips
	 */
	public void setTips(String tips) {
		this.tips = tips;
	}
	
	/**
	 * 是否推荐，默认为0（不推荐）。
	 * @return
	 */
	public boolean getIsElite() {
		return isElite;
	}
	
	/**
	 * 是否推荐，默认为0（不推荐）。
	 * @param isElite
	 */
	public void setIsElite(boolean isElite) {
		this.isElite = isElite;
	}
	
	/**
	 * 得到专题图片的URL。
	 * @return
	 */
	public String getSpecialPicUrl() {
		return specialPicUrl;
	}
	
	/**
	 * 设置专题图片的URL。
	 * @param specialPicUrl
	 */
	public void setSpecialPicUrl(String specialPicUrl) {
		this.specialPicUrl = specialPicUrl;
	}
	
	/**
	 * 专题的打开方式，1：新窗口打开；0：原窗口打开。默认为1。
	 * @return
	 */
	public int getOpenType() {
		return openType;
	}
	
	/**
	 * 专题的打开方式，1：新窗口打开；0：原窗口打开。默认为1。
	 * @param openType
	 */
	public void setOpenType(int openType) {
		this.openType = openType;
	}
	
	/**
	 * 获取每页显示的项目数量。
	 * @return
	 */
	public int getMaxPerPage() {
		return maxPerPage;
	}
	
	/**
	 * 设置每页显示的项目数量。
	 * @param maxPerPage
	 */
	public void setMaxPerPage(int maxPerPage) {
		this.maxPerPage = maxPerPage;
	}
}
