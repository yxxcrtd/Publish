package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.model.*;

/**
 * 公告管理页面数据提供。
 * 
 * @author liujunxing
 * @jsppage 
 *  <li>admin_announce.jsp
 *  <li>admin_announce_add.jsp
 */
public class AnnounceManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public AnnounceManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_announce.jsp 页面数据初始化。
	 * @pageparam 
	 *   <li>channelId = -1 表示频道公用公告
	 *   <li>channelId = 0 表示网站首页公告
	 *   <li>channelId > 0 表示指定频道公告
	 */
	public void initListPage() {
		int channelId = super.safeGetIntParam("channelId", -1);
		super.request_param.put("channelId", channelId);
		
		// 获取当前频道的公告数据.
		Object announce_list = getAnnounceListData(channelId);
		setTemplateVariable("announce_list", announce_list);
		
		// 获取所有频道列表数据。
		Object channel_list = super.getChannelListData();
		setTemplateVariable("channel_list", channel_list);
	}
	
	/**
	 * admin_announce_add.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		// 获得当前页面新增或修改的公告数据对象。
		Announcement announce = getAnnounceData();
		setTemplateVariable("announce", announce);
		
		// 获取所有频道列表数据。
		Object channel_list = super.getChannelListData();
		setTemplateVariable("channel_list", channel_list);
	}
	
	/**
	 * 获得当前频道的公告列表。
	 * @param var_name
	 * @return 该频道的所有公告列表，类型为 DataTable. 
	 * @schema 包含的列 id, title, isSelected, showType, author, createDate, outTime
	 */
	private DataTable getAnnounceListData(int channelId) {
		AnnouncementCollection ac = pub_ctxt.getSite().getAnnouncementCollection();
		DataTable dt = ac.getAnnounceList(channelId);
		return dt;
	}
	
	/**
	 * 获得 admin_announce_add.jsp 页面中新建或修改一个公告数据的对象。
	 * @return 一个新的公告数据或现存的一个公告数据。
	 */
	private Announcement getAnnounceData() {
		int id = super.safeGetIntParam("id", 0);
		Announcement announce;
		if (id == 0) {
			announce = new Announcement();
			announce.setAuthor(admin.getAdminName());
			announce.setCreateDate(new java.util.Date());
			announce.setOutTime(3);
			announce.setIsSelected(true);
			announce.setShowType(1);
		} else {
			announce = pub_ctxt.getSite().getAnnouncementCollection().getAnnouncement(id);
			// TODO: announce == null 
		}
		return announce;
	}
	

}
