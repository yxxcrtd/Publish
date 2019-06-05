package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.DataTable;

/**
 * 生成管理。
 * 
 * @author liujunxing
 */
public class GenerateManage extends AbstractBaseManage {
	/**
	 * 构造。
	 * @param pageContext
	 */
	public GenerateManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * 初始化 admin_generate_site.jsp 页面数据。
	 *
	 */
	public void initIndexGeneratePage() {
		if (admin.checkSiteRight(Admin.OPERATION_GENERATE_MANAGE) == false)
			throw super.accessDenied();
			
		// 1. 生成频道列表，其设置了生成属性。
		Object channel_list = site.getChannels().getChannelList();
		setTemplateVariable("channel_list", channel_list);
		
		// 2. 获得全站专题列表
		Object special_list = site.getSpecialCollection().getSpecialList();
		setTemplateVariable("special_list", special_list);
		
		/*
		<!-- 生成频道列表 -->
		<pub:data var="channel_list" 
		 scope="page" provider="cn.edustar.jpub.admin.ChannelListDataProvider"/>

		<!-- 获得频道的专题列表的数据。 -->
		<pub:data var="special_list" 
		 provider="cn.edustar.jpub.admin.ChannelSpecialsDataProvider" param="0"  />
		*/
	}

	/**
	 * 初始化 admin_generate_channel.jsp 页面数据。
	 *
	 */
	public void initChannelGeneratePage() {
		if (admin.checkSiteRight(Admin.OPERATION_GENERATE_MANAGE) == false)
			throw super.accessDenied();
		
		// 当前频道。
		Channel channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		super.setTemplateVariable("channel", channel);
		
		// 当前频道下的栏目。
		// 下拉栏目列表的数据。
		DataTable dropdown_columns = super.getColumnsDropDownData(channel);
		setTemplateVariable("dropdown_columns", dropdown_columns);

		// 获得此频道下专题列表。
		Object channel_specials = channel.getSpecialCollection().getChannelSpecialDataTable(channel.getId());
		setTemplateVariable("channel_specials", channel_specials);
	}
}
