package com.chinaedustar.publish.comp;

import com.chinaedustar.publish.model.*;

/**
 * 频道的权限包装类，当前适用于通用 Article, Photo, Soft 模块。
 *  其它模块需要进一步改进。
 * 
 * <pre>
 * 文章模块产生的权限对象模型:
 * ChannelRightWrapper
 *   channel - 频道对象
 *   admin - 管理员
 *   roleValue - 在频道中的权限角色。每个频道可能定义不同，基本含：
 *     400 - 频道管理员：拥有所有栏目的管理权限，并可以添加栏目和专题
 *     300 - 栏目总编：拥有所有栏目的管理权限，但不能添加栏目和专题
 *     100 - 栏目管理员：只拥有部分栏目管理权限
 *     0 - 在此频道里无任何管理权限
 *     (其它频道可能定义不同值)
 * 　更多权限获取方法：
 *   templateManage - 模板管理
 *   jsManage - JS文件管理
 *   等等。。。
 * </pre>
 * 
 * @author liujunxing
 *
 */
public class ChannelRightWrapper extends BaseRightWrapper {
	/** 管理员在此频道中权限子集。 */
	private AdminRightCollection channel_rights;
	
	/** 在这个频道的角色值。 */
	private int role_value;
	
	/**
	 * 构造。
	 *
	 */
	public ChannelRightWrapper(Channel channel, Admin admin) {
		super(channel, admin);
		this.channel_rights = admin._getRights().getChannelRightSubColl(channel.getId());
		this.role_value = channel_rights.getChannelRoleValue(channel.getId());
	}
	
	// === 在此频道的角色 ============================================================
	
	/** 在此频道的角色 */
	public int getRoleValue() {
		return this.role_value;
	}
	
	// === 在此频道的其它权限 =========================================================
	
	/**
	 * 获得是否有权限进行模板管理。
	 */
	public boolean getTemplateManage() {
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_TEMPLATE);
	}
	
	/** (当前不支持)频道中JS文件管理。 */
	public boolean getJsManage() {
		// public static final String OPERATION_CHANNEL_JS = "js_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_JS);
	}

	/** (当前不支持)顶部菜单管理。 */
	public boolean getMenuManage() {
		// public static final String OPERATION_CHANNEL_MENU = "menu_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_MENU);
	}
	
	/** 频道中关键字管理。 */
	public boolean getKeywordManage() {
		// public static final String OPERATION_CHANNEL_KEYWORD = "keyword_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_KEYWORD);
	}
	
	/** 频道中作者管理。 */
	public boolean getAuthorManage() {
		// public static final String OPERATION_CHANNEL_AUTHOR = "author_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_AUTHOR);
	}
	
	/** 频道中来源管理。 */
	public boolean getSourceManage() {
		// public static final String OPERATION_CHANNEL_SOURCE = "source_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_SOURCE);
	}
	
	/** (当前不支持)频道中更新 XML. */
	public boolean getXmlManage() {
		// public static final String OPERATION_CHANNEL_XML = "xml_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_XML);
	}

	/** (当前不支持)频道中自定义字段. */
	public boolean getFieldManage() {
		// public static final String OPERATION_CHANNEL_FIELD = "field_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_FIELD);
	}
	
	/** (当前不支持)频道中广告管理. */
	public boolean getAdManage() {
		// public static final String OPERATION_CHANNEL_AD = "ad_manage";
		return channel_rights.checkChannelRight(channel.getId(), Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_AD);
	}
}
