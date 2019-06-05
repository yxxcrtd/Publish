package com.chinaedustar.publish.model;

import java.util.Date;

/**
 * 管理员对象，包含有管理员的基本信息和一些基本操作。
 * 主要业务是对安全的设置与验证，包括密码、身份和权限的控制。
 * @author wangyi
 *
 */
public class Admin extends AbstractNamedModelBase {
	// === 通用权限目标定义，对应 AdminRight.target 属性 ===========================
	
	/** 权限目标 - 站点对象，做为全站权限的通用目标。 */
	public static final String TARGET_SITE = "site";
	
	/** 权限目标 - 用户，做为用户权限的通用目标。 */
	public static final String TARGET_USER = "user";
	
	/** 权限目标 - 频道，做为频道权限的通用目标。 */
	public static final String TARGET_CHANNEL = "channel";
	
	/** 频道角色目标 - 该 target 定义的 operation 值作为频道角色看待。 */
	public static final String TARGET_CHANNEL_ROLE = "channel_role";
	
	/** 权限目标 - 栏目，做为栏目权限的通用目标。 */
	public static final String TARGET_COLUMN = "column";
	
	// === 站点级操作定义 ========================================================
	
	/** 修改自己的密码。 = 'self_password' */
	public static final String OPERATION_SELFPASSWORD = "self_password";

	/** 网站管理的操作。 = 'site_manage' */
	public static final String OPERATION_SITE_MANAGE = "site_manage";
	
	/** 频道管理的操作。 = 'channel_manage' */
	public static final String OPERATION_CHANNEL_MANAGE = "channel_manage";
	
	/** 全站专题管理权。 = 'site_special' */
	public static final String OPERATION_SITE_SPECIAL = "site_special";
	
	/** (当前未实现的)采集管理。 = 'collect_manage' */
	public static final String OPERATION_COLLECT_MANAGE = "collect_manage";
	
	/** (当前未实现的)短消息管理。 */
	public static final String OPERATION_MESSAGE_MANAGE = "message_manage";

	/** (当前未实现的)邮件列表管理。 */
	public static final String OPERATION_MAILLIST_MANAGE = "maillist_manage";
	
	/** (当前未实现的)网站广告管理。 */
	public static final String OPERATION_AD_MANAGE = "ad_manage";
	
	/** 友情链接管理的操作。 */
	public static final String OPERATION_FRIEND_MANAGE = "friend_manage";
	
	/** 公告管理的操作。 */
	public static final String OPERATION_ANNOUNCE_MANAGE = "announce_manage";
	
	/** (当前未实现的)调查管理的操作。 */
	public static final String OPERATION_VOTE_MANAGE = "vote_manage";

	/** (当前未实现的)统计管理的操作。 */
	public static final String OPERATION_STAT_MANAGE = "stat_manage";

	/** 皮肤管理。 */
	public static final String OPERATION_SKIN_MANAGE = "skin_manage";
	
	/** 模板管理的操作（适用于频道与网站）。 */
	public static final String OPERATION_TEMPLATE_MANAGE = "template_manage";
	
	/** 自定义标签管理的操作。 */
	public static final String OPERATION_LABEL_MANAGE = "label_manage";

	/** 模板方案管理的操作。 */
	public static final String OPERATION_THEME_MANAGE = "theme_manage";

	/** (当前未实现的)网站缓存管理。 */
	public static final String OPERATION_CACHE_MANAGE = "cache_manage";

	/** (当前未实现的)站内链接管理。 */
	public static final String OPERATION_LINK_MANAGE = "link_manage";

	/** (当前未实现的)字符过滤管理。 */
	public static final String OPERATION_FILTER_MANAGE = "filter_manage";

	/** (当前未实现的)充值卡管理。 */
	public static final String OPERATION_CARD_MANAGE = "card_manage";

	/** 日志管理的操作。 */
	public static final String OPERATION_LOG_MANAGE = "log_manage";
	
	/** 自定义页面管理的操作。 */
	public static final String OPERATION_PAGE_MANAGE = "page_manage";
	
	// ?? 下面几个也许放别的地方。
	
	/** 留言管理的操作。 */
	public static final String OPERATION_FEEDBACK_MANAGE = "feedback_manage";
	
	/** 页面生成管理的操作（适用于频道与网站）。 */
	public static final String OPERATION_GENERATE_MANAGE = "generate_manage";

	/**
	 * 判定指定操作是否是一个合法的站点权限操作名。
	 * 主要用于验证来自客户端的提交，以防止出现问题。
	 * @param operation
	 * @return 返回 null 表示非法，否则是一个合法的操作名。
	 */
	public static final String matchSiteOperation(String operation) {
		if (operation == null) return null;
		operation = operation.trim();
		if (operation.length() == 0) return null;
		
		// 更强的验证是判断字符串是否在指定集合中，但是这样需要建立并维护该集合。扩展带来麻烦，不如不验证。
		return operation;
	}
	
	/**
	 * 判定指定操作是否是一个合法的频道权限操作名。
	 * 主要用于验证来自客户端的提交，以防止出现问题。
	 * @param operation
	 * @return 返回 null 表示非法，否则是一个合法的操作名。
	 */
	public static final String matchChannelOperation(String operation) {
		if (operation == null) return null;
		operation = operation.trim();
		if (operation.length() == 0) return null;
		
		// 更强的验证是判断字符串是否在指定集合中，但是这样需要建立并维护该集合。扩展带来麻烦，不如不验证。
		return operation;
	}
	
	// === 用户管理操作 =========================================================
	
	/** 管理员管理的操作。 */
	public static final String OPERATION_ADMIN_MANAGE = "admin_manage";
	
	/** 会员管理的操作。 */
	public static final String OPERATION_USER_MANAGE = "user_manage";

	/** (当前未实现的)会员组管理的操作。 */
	public static final String OPERATION_GROUP_MANAGE = "group_manage";

	// === 频道级操作定义 ========================================================

	/** 频道中模板管理。 */
	public static final String OPERATION_CHANNEL_TEMPLATE = "template_manage";
	
	/** (当前不支持)频道中JS文件管理。 */
	public static final String OPERATION_CHANNEL_JS = "js_manage";
	
	/** (当前不支持)顶部菜单管理。 */
	public static final String OPERATION_CHANNEL_MENU = "menu_manage";
	
	/** 频道中关键字管理。 */
	public static final String OPERATION_CHANNEL_KEYWORD = "keyword_manage";
	
	/** 频道中作者管理。 */
	public static final String OPERATION_CHANNEL_AUTHOR = "author_manage";
	
	/** 频道中来源管理。 */
	public static final String OPERATION_CHANNEL_SOURCE = "source_manage";
	
	/** (当前不支持)频道中更新 XML. */
	public static final String OPERATION_CHANNEL_XML = "xml_manage";

	/** (当前不支持)频道中自定义字段. */
	public static final String OPERATION_CHANNEL_FIELD = "field_manage";

	/** (当前不支持)频道中广告管理. */
	public static final String OPERATION_CHANNEL_AD = "ad_manage";

	// === 栏目级操作 =============================================================
	
	/** 栏目中查看操作。 */
	//public static final String OPERATION_COLUMN_VIEW = "column_view";

	/** 栏目中录入操作。 */
	//public static final String OPERATION_COLUMN_INPUTER = "column_inputer";

	/** 栏目中审核操作。 */
	//public static final String OPERATION_COLUMN_EDITOR = "column_editor";
	
	/** 栏目中管理操作。 */
	//public static final String OPERATION_COLUMN_MANAGE = "column_manage";

	// ==========================================================================
	
	/** 此管理员前台登录的名字。此名字可以不唯一。 */
	private String userName;
	
	/** 后台登录密码的hash加密值。 */
	private String password;
	
	/** 最后一次登录的IP地址。 */
	private String lastLoginIp;
	
	/** 最后一次登录的时间。 */
	private Date lastLoginTime;
	
	/** 最后一次登出的时间。 */
	private Date lastLogoutTime;
	
	/** 管理员类型，1：超级管理员；2：普通管理员；0：普通管理员且没有设置任何权限。默认为0。 */
	private int adminType;
	
	/**
	 * 此管理员的名字。名字必须唯一。
	 * @return
	 */
	public String getAdminName() {
		return super.getName();
	}
	/**
	 * 此管理员的名字。名字必须唯一。
	 * @param adminName
	 */
	public void setAdminName(String adminName) {
		super.setName(adminName);
	}
	
	/**
	 * 此管理员前台登录的名字。此名字可以不唯一。
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 此管理员前台登录的名字。此名字可以不唯一。
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 后台登录密码的hash加密值。
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 后台登录密码的hash加密值。
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 最后一次登录的IP地址。
	 * @return
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	
	/**
	 * 最后一次登录的IP地址。
	 * @param lastLoginIp
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	/**
	 * 最后一次登录的时间。
	 * @return
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	/**
	 * 最后一次登录的时间。
	 * @param lastLoginTime
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	/**
	 * 最后一次登出的时间。
	 * @return
	 */
	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}
	
	/**
	 * 最后一次登出的时间。
	 * @param lastLogoutTime
	 */
	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	/**
	 * 管理员类型，1：超级管理员；2：普通管理员；0：普通管理员且没有设置任何权限。默认为0。
	 * @return
	 */
	public int getAdminType() {
		return adminType;
	}
	
	/**
	 * 管理员类型，1：超级管理员；2：普通管理员；0：普通管理员且没有设置任何权限。默认为0。
	 * @param adminType
	 */
	public void setAdminType(int adminType) {
		this.adminType = adminType;
	}

	// === 管理员类型定义 =================================================================

	/** 普通管理员且没有设置任何权限, = 0 */
	public static final int ADMIN_TYPE_NONE = 0;
	
	/** 超级管理员，具有所有权限, = 1 */
	public static final int ADMIN_TYPE_SUPER = 1;
	
	/** 普通管理员，具有部分权限, = 2。 */
	public static final int ADMIN_TYPE_NORMAL = 2;

	// === 管理员在频道上的角色值定义 =======================================================
	
	/** 频道管理员, 拥有所有栏目的管理权限，并可以添加栏目和专题、拥有额外频道权限。 = 400 */
	public static final int CHANNEL_ROLE_MANAGER = 400;
	
	/** 栏目总编：拥有所有栏目的管理权限，(??? 为何设定为不能添加栏目和专题)但不能添加栏目和专题。 = 300 */
	public static final int CHANNEL_ROLE_EDITOR = 300;
	
	/** 栏目管理员：只拥有部分栏目管理权限。 = 100 */
	public static final int CHANNEL_ROLE_COLUMN = 100;
	
	/** 在此频道里无任何管理权限. = 0, 这是缺省值。 */
	public static final int CHANNEL_ROLE_NONE = 0;
	
	// === 管理员在栏目上的权限值定义 =======================================================
	
	/** 在该栏目有查看权。 */
	public static final int COLUMN_RIGHT_VIEW = 1;
	
	/** 在该栏目有录入权。 */
	public static final int COLUMN_RIGHT_INPUTER = 2;
	
	/** 在该栏目有审核权。 */
	public static final int COLUMN_RIGHT_EDITOR = 4;
	
	/** 在该栏目有管理权。 */
	public static final int COLUMN_RIGHT_MANAGE = 8;
	
	public static final int COLUMN_RIGHT_ALL = 15;	// VIEW | INPUTER | EDITOR | MANAGE
	
	// ******************* 管理员对象的业务方法 ******************* //

	/** 管理员权限集合对象。 */
	private AdminRightCollection admin_right = null;
	
	/** 内部获得权限集合。 */
	public AdminRightCollection _getRights() {
		if (admin_right == null) {
			admin_right = new AdminRightCollection();
			admin_right._init(pub_ctxt, this);
		}
		return admin_right;
	}

	/**
	 * 检查此管理员是否在 Site 全局 TARGET_SITE 对象上面的指定操作权限。
	 * 等同于调用 checkSiteRight(Admin.TARGET_SITE, operation); 
	 * @param target
	 * @param operation
	 * @return
	 */
	public boolean checkSiteRight(String operation) {
		return checkSiteRight(TARGET_SITE, operation);
	}

	/**
	 * 检查此管理员是否在 Site 全局 TARGET_USER 对象上面的指定操作权限。
	 * 等同于调用 checkSiteRight(Admin.TARGET_USER, operation); 
	 * @param target
	 * @param operation
	 * @return
	 */
	public boolean checkUserRight(String operation) {
		return checkSiteRight(TARGET_USER, operation);
	}

	/**
	 * 检查此管理员是否在 Site 全局指定对象上面的指定操作权限。 
	 * @param target
	 * @param operation
	 * @return
	 */
	public boolean checkSiteRight(String target, String operation) {
		if (this.adminType == ADMIN_TYPE_SUPER) return true;
		if (this.adminType == ADMIN_TYPE_NONE) return false;
		
		return _getRights().checkSiteRight(target, operation);
	}

	/**
	 * 获得该管理员在指定频道的角色。
	 * @param channelId
	 * @return
	 */
	public int getChannelRoleValue(int channelId) {
		if (this.adminType == ADMIN_TYPE_SUPER) return Admin.CHANNEL_ROLE_MANAGER;
		if (this.adminType == ADMIN_TYPE_NONE) return Admin.CHANNEL_ROLE_NONE;
		
		return this._getRights().getChannelRoleValue(channelId);
	}

	/**
	 * 检查此管理员是否具有指定频道、指定对象上面的指定操作权限。
	 * @param channel
	 * @param target
	 * @param operation
	 * @return
	 */
	public boolean checkChannelRight(int channelId, String target, String operation) {
		if (this.adminType == ADMIN_TYPE_SUPER) return true;
		if (this.adminType == ADMIN_TYPE_NONE) return false;
		
		return _getRights().checkChannelRight(channelId, target, operation);
	}

	/**
	 * 检查此管理员是否具有指定频道、指定栏目、指定对象上面的指定操作权限。
	 * @param channel - 频道对象，可能为 null
	 * @param column - 栏目对象，可能为 null
	 * @param target - 目标对象，可能为 null
	 * @param operation - 操作类型。
	 * @return
	 */
	public boolean checkColumnRight(int channelId, TreeItemInterface column, String target, int operation) {
		if (this.adminType == ADMIN_TYPE_SUPER) return true;
		if (this.adminType == ADMIN_TYPE_NONE) return false;
		
		return _getRights().checkColumnRight(channelId, column, target, operation);
	}

	/**
	 * 更新管理员的权限。
	 * @param new_rights - 新的权限集合。
	 * @param old_rights - 旧的权限集合。
	 */
	public void updateRights(AdminRightCollection new_rights, AdminRightCollection old_rights) {
		// 超级管理员不需要额外设置权限。
		if (this.adminType == Admin.ADMIN_TYPE_SUPER)
			return;
		
		new_rights.pub_ctxt = this.pub_ctxt;
		new_rights.owner_obj = this;
		
		// 如果当前是无任何权限，而实际设置了权限，则更新状态。必须在 new_rights.update() 之前调用。
		this.adminType = new_rights._isNoRight() ? Admin.ADMIN_TYPE_NONE : Admin.ADMIN_TYPE_NORMAL;
		String hql = "UPDATE Admin SET adminType = " + this.adminType + " WHERE id = " + this.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
			
		// 更新权限。
		new_rights.update(old_rights);
		
		this.admin_right = null;		// force reload admin_right
		// TODO: 如果这个管理员正在登录中，则是否更新 session 里面的??
	}
}
