package com.chinaedustar.publish.admin;

import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.model.*;

/**
 * 频道管理数据提供类。
 * 
 * @author liujunxing
 *
 */
public class ChannelManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public ChannelManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_channel_list.jsp 页面数据初始化。
	 *
	 */
	public void initListPage() {
		super.pragmaNocache();
		
		// 需要的权限: site.channel_manage
		if (admin.checkSiteRight(Admin.TARGET_USER, Admin.OPERATION_CHANNEL_MANAGE) == false)
			throw super.accessDenied();
		
		Object channel_list = site.getChannels().loadChannelList();
		setTemplateVariable("channel_list", channel_list);
	}

	/**
	 * admin_channel_add.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		super.pragmaNocache();
		
		// 为这个页面获取 Channel 的数据
		Channel channel = null;
		int channelId = param_util.safeGetIntParam("channelId");
		if (channelId == 0) {
			// 新增频道需要有 site.channel_manage 权限。
			if (admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_CHANNEL_MANAGE) == false)
				throw super.accessDenied();
			// 建立一个新频道对象。
			channel = new Channel();
			channel.setId(0);
			channel.setUploadDir("upload");
			channel.setMaxFileSize(1024);
			channel.setUpFileType("jpg|gif|bmp|png|swf|rar|zip");
			channel.setUpdatePages(3);
		} else {
			channel = site.getChannels().loadChannel(channelId);
			if (channel == null)
				throw new PublishException("无法找到指定标识为 " + channelId + " 的频道，请确定您是从有效的链接进入的。");
			// 需要权限 channel.role=manager, editor.
			if (admin.getChannelRoleValue(channelId) < Admin.CHANNEL_ROLE_EDITOR)
				throw super.accessDenied();
		}

		setTemplateVariable("channel", channel);
		setTemplateVariable("object", channel);
		
		// 提供频道首页模板列表的数据
		if (channel.getId() != 0) {
			Object templ_list = super.getAvailableTemplateDataTable(channel, PageTemplate.CHANNEL_INDEX_PAGE);
			setTemplateVariable("templ_home_templates", templ_list);
		}
		
		// 提供默认方案下皮肤列表的数据
		Object skin_list = super.getAvailableSkinDataTable();
		setTemplateVariable("skin_list", skin_list);
		
		// 提供模块列表。
		initModuleList();
	}

	/**
	 * 初始化 admin_channel_order.jsp 页面数据
	 */
	public void initOrderPage() {
		Object channel_list = site.getChannels().loadActiveChannelList();
		setTemplateVariable("channel_list", channel_list);
	}
	
	/**
	 * 初始化 admin_channel_recylce.jsp 页面。
	 *
	 */
	public void initRecyclePage() {
		// 需要的权限: site.channel_manage
		if (admin.checkSiteRight(Admin.TARGET_USER, Admin.OPERATION_CHANNEL_MANAGE) == false)
			throw super.accessDenied();

		Object channel_list = site.getChannels().getRecycleChannelList();
		setTemplateVariable("channel_list", channel_list);
	}
	
	private void initModuleList() {
		Iterator<Module> itor = site.getModules().iterator();
		List<Module> module_list = new java.util.ArrayList<Module>();
		while(itor.hasNext()){
			module_list.add(itor.next());
		}
		setTemplateVariable("module_list", module_list);	
	}
}
