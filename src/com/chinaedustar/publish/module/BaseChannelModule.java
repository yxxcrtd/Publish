package com.chinaedustar.publish.module;

import java.util.HashMap;
import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.AbstractPublishModelBase;
import com.chinaedustar.publish.model.AdminRightType;
import com.chinaedustar.publish.model.ChannelModule;

/**
 * 基本的频道模块实现类，提供了对 ArtileModule, SofeModule, PictureModule 的一些默认支持。
 * 管理权限类型： （权限类型 + 页面 + 页面参数 + 是否禁止状态）。
 * 1 频道管理员：模板管理，关键字管理（未选中），作者管理，来源管理（未选中）。
 * 		channelManager + *.jsp + channelId=#{channel.id } + 允许 ；
 *		channelManager_more + admin_keyword_*.jsp, admin_source_*.jsp + channelId=#{channel.id } + 禁止 。
 * 2 栏目总编：
 * 		columnChiefManager + admin_article_*.jsp, admin_special_article_*.jsp + channelId=1 + 允许 ；
 * 		columnManager_comment + admin_article_comment_*.jsp + channelId=1 + 禁止 。
 * 3 栏目管理：
 * 		columnManager + admin_article_my.jsp, admin_article_add.jsp + channelId=#{channel.id } + 允许 。
 * 		查看：columnManager_view + admin_article_list.jsp + channelId=#{channel.id }, columnId=#{column.id }|#{column2.id } + 允许 。
 * 		录入：columnManager_input + admin_article_save.jsp + channelId=#{channel.id }, columnId=#{column.id }|#{column2.id } + 允许 。
 * 		审核：columnManager_appro + admin_article_appro.jsp, admin_article_doappro.jsp + channelId=#{channel.id }, columnId=#{column.id }|#{column2.id } + 允许 。
 * 		管理：columnManager_manage + admin_article_*.jsp + channelId=#{channel.id }, columnId=#{column.id }|#{column2.id } + 允许 。
 * 			 columnManager_comment + admin_article_comment_*.jsp + channelId=1 + 禁止 。
 * 4 无任何管理权限：
 * 
 * @author wangyi
 *
 */
public abstract class BaseChannelModule extends AbstractPublishModelBase implements ChannelModule, ManageMenuModule {
	/** 模块的名称。 */
	private String moduleName = "BaseChannelModule";
	
	/** 是否系统的模块。 */
	private boolean isSystem;
	
	/** 是否频道的模块。 */
	private boolean isChannel;
		
	/** 权限类型：频道管理。 */
	public static final String RIGHT_TYPE_CHANNEL_MANAGER = "channelManager";
	
	/** 权限类型：栏目管理。 */
	public static final String RIGHT_TYPE_COLUMN = "columnManager";
	
	/** 权限类型：栏目管理-管理。 */
	public static final String RIGHT_TYPE_COLUMN_MANAGER = "columnManager_manage";
	
	/** 权限类型：栏目管理-编辑。 */
	public static final String RIGHT_TYPE_COLUMN_EDITOR = "columnManager_editor";
	
	/** 权限类型：无任何频道权限。 */
	public static final String RIGHT_TYPE_NONE = "channel_none";
	
	/** 当前管理员权限模块中的权限类型。 */
	private static final HashMap<String, AdminRightType> rightTypes = new HashMap<String, AdminRightType>();

	/**
	 * 得到当前管理员权限模块的名称。
	 * @return
	 */
	public String getRightModuleName() {
		return moduleName;
	}
	
	/**
	 * 设置管理员权限模块的名称。
	 * @param moduleName
	 */
	public void setRightModuleName(String moduleName) {
		this.moduleName = moduleName;
	}	

	/**
	 * 是否系统的管理员权限模块。
	 * @return
	 */
	public boolean getIsSystem() {
		return isSystem;
	}
	
	/**
	 * 是否频道的管理员权限模块。
	 * @return
	 */
	public boolean getIsChannel() {
		return isChannel;
	}
	
	// **************** PublishModule 接口的实现********************************* //
	
	/**
	 * 模块被加载进入内存的时候的初始化，调用且仅调用一次。
	 *
	 */
	public void initialize(PublishContext pub_ctxt) {
		super._init(pub_ctxt, null);
		/* TODO: 
		String hql = "select isSystem, isChannel from AdminRightModuleObject where moduleName='" + moduleName + "'";
		List list = pub_ctxt.getDao().list(hql);
		if (!list.isEmpty()) {
			isSystem = (Boolean)((Object[])list.get(0))[0];
			isChannel = (Boolean)((Object[])list.get(0))[1];
		}
		*/
	}
	
	/**
	 * 模块从内存卸载的时候，被调用且仅调用一次。
	 *
	 */
	public void destroy() {
		super._destroy();
	}

	// **************** AdminRightModule 接口的实现，权限相关的处理 **************************************** //

	/**
	 * 得到当前管理员权限模块下面的所有权限类型对象。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public synchronized HashMap<String, AdminRightType> getAdminRightTypes() {
		if (rightTypes.isEmpty()) {
			String hql = "from AdminRightType where RightModuleName='" + this.moduleName + "'";
			List list = pub_ctxt.getDao().list(hql);
			for (int i = 0; i < list.size(); i++) {
				AdminRightType rightType = (AdminRightType)list.get(i);
				if (!rightTypes.containsKey(rightType.getRightType())) {
					rightTypes.put(rightType.getRightType(), rightType);
				}
			}
		}
		return rightTypes;
	}
	
}