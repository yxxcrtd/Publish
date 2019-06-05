package com.chinaedustar.publish.module;

import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Soft;

/**
 * 软件下载类模块的实现类。此模块用于支持软件下载频道。
 * 
 * @author liujunxing
 * @ModuleName = "SoftModule"
 */
public class SoftModule extends AbstractItemModule {
	/**
	 * 构造。
	 *
	 */
	public SoftModule() {
		super();
	}

	/**
	 * 获得指定频道的菜单。
	 * @param channel
	 * @return
	 */
	public Menu getChannelMenu(Channel channel, Admin admin) {
		// 如果不具有任何权限则不创建该频道的菜单了。
		int role = admin.getChannelRoleValue(channel.getId());
		if (role == Admin.CHANNEL_ROLE_NONE) return null;
		
		// 利用通用项目菜单创建器进行创建。
		MenuBuilder builder = new MenuBuilder(channel, admin);
		Menu menu = new Menu("soft_" + channel.getChannelDir() + "_" + channel.getId(), 
				channel.getName());
		menu.setUrl("admin_channel_guide.jsp?channelId=" + channel.getId());
		builder.initSetMenu(menu);
		
		builder.addItemMenu("soft");		// '添加软件', '我添加的软件'
		builder.addSoftItemManageMenu();	// '软件管理', '审核', '生成'
		builder.addSpecialItemMenu("soft");	// '专题文章管理'
		builder.addCommentMenu();			// '评论管理'
		builder.addRecycleMenu("soft");		// '软件回收站管理'
		
		builder.addSeperator();			// =======================
		builder.addChannelSetting();	// '下载中心设置'
		builder.addColumnMenu();		// '栏目管理', '专题管理'
		builder.addUploadMenu();		// '上传文件管理', '清理', '顶部菜单设置', '生成'
		builder.addOtherChannelMenu();	// '模板管理', '关键字管理', '作者管理', '来源管理'
		
		// menu.addMenuItem(new MenuItem(item + "参数管理", "admin_soft_param_list.jsp?channelId=" + sid, ""));
		return builder.getMenu();
	}

	/**
	 * 获得模块的唯一名字。
	 * @return
	 */
	public String getModuleName() {
		return "SoftModule";
	}

	/**
	 * 获得模块的中文名字（标题）。
	 * @return
	 */
	public String getModuleTitle() {
		return "下载模块";
	}

	// === ChannelModule 实现 ====================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getItemClassName()
	 */
	public String getItemClass() {
		return "Soft";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getItemGeneralName()
	 */
	public String getDefaultItemName() {
		return "下载";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getDefaultItemUnit()
	 */
	public String getDefaultItemUnit() {
		return "个";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#loadItem(int)
	 */
	public Soft loadItem(int itemId) {
		return (Soft)pub_ctxt.getDao().get(Soft.class, itemId);
	}
}
