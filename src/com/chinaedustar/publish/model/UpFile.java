package com.chinaedustar.publish.model;

import java.util.Date;

/**
 * 上传文件的对象，对应于数据库中的 Cor_UploadFile 表的数据。
 * @author wangyi
 *
 */
public class UpFile extends AbstractNamedModelBase {

	/** 上传文件所属的频道。 */
	private int channelId;
	
	/** 上传文件原来的名字，如：集体合影.jpg */
	private String oldName;
	
	/** 文件的路径，相对于网站的跟目录，如：'news/UploadFiles/2007/03/20070313102012212.txt' */
	private String filePath;
	
	/** 上传文件的扩展名，如：txt */
	private String fileExt;
	
	/** 上传的时间，如：2007-3-13 10:20:39.323 */
	private Date uploadTime = new Date();
	
	/** 上传人的名称。 */
	private String userName = "";

	/**
	 * 上传文件所属的频道。
	 * @return
	 */
	public int getChannelId() {
		return channelId;
	}
	
	/**
	 * 上传文件所属的频道。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 上传文件的扩展名，如：txt
	 * @return
	 */
	public String getFileExt() {
		return fileExt;
	}

	/**
	 * 上传文件的扩展名，如：txt
	 * @param fileExt
	 */
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	/**
	 * 文件的路径，相对于网站的跟目录，如：'news/UploadFiles/2007/03/20070313102012212.txt'
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 文件的路径，相对于频道的上传文件目录，如：'news/UploadFiles/2007/03/20070313102012212.txt'
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * 上传文件原来的名字，如：集体合影.jpg
	 * @return
	 */
	public String getOldName() {
		return oldName;
	}

	/**
	 * 上传文件原来的名字，如：集体合影.jpg
	 * @param oldName
	 */
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	/**
	 * 上传的时间，如：2007-3-13 10:20:39.323
	 * @return
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * 上传的时间，如：2007-3-13 10:20:39.323
	 * @param uploadTime
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * 上传人的名称。
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 上传人的名称。
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
