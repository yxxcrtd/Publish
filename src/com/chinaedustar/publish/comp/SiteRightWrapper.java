package com.chinaedustar.publish.comp;

import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.AdminRightCollection;

/**
 * 网站权限的封装，为了便于模板访问。
 * 
 * <pre>
 * 组建站点权限对象。模型如下：
 * SiteRight
 *   (以后考虑实现以支持更多动态产生页面)RightGroups - List&lt;RightGroup&gt;, Map&lt;GroupName, RightGroup&gt;
 *     RightGroup - List&lt;Right&gt;
 *       GroupName
 *       Right - AdminRight
 *       selfPassword (boolean) - 是否允许自己修改密码
 *       
 *   (以后考虑实现)System - RightGroup which GroupName='System'
 *   selfPassword, siteManage ... - boolean 直接返回该权限。
 *   
 * 模板中使用示例：
 *  input type=checkbox #{if siteRight.selfPassword}checked #{/if}
 * </pre>
 * 
 * @author liujunxing
 */
public class SiteRightWrapper {
	private final AdminRightCollection right_coll;
	
	/**
	 * 构造。
	 *
	 */
	public SiteRightWrapper(AdminRightCollection right_coll) {
		this.right_coll = right_coll;
	}
	
	/** 修改自己的密码。 */
	public boolean getSelfPassword() {
		// public static final String OPERATION_SELFPASSWORD = "self_password";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_SELFPASSWORD);
	}

	/** 网站管理的操作。 */
	public boolean getSiteManage() {
		// public static final String OPERATION_SITE_MANAGE = "site_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_SITE_MANAGE);
	}
	
	/** 频道管理的操作。 */
	public boolean getChannelManage() {
		// public static final String OPERATION_CHANNEL_MANAGE = "channel_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_CHANNEL_MANAGE);
	}
	
	/** (当前未实现的)采集管理。 */
	public boolean getCollectManage() {
		// public static final String OPERATION_COLLECT_MANAGE = "collect_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_COLLECT_MANAGE);
	}
	
	/** (当前未实现的)短消息管理。 */
	public boolean getMessageManage() {
		// public static final String OPERATION_MESSAGE_MANAGE = "message_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_MESSAGE_MANAGE);
	}

	/** (当前未实现的)邮件列表管理。 */
	public boolean getMailListManage() {
		// public static final String OPERATION_MAILLIST_MANAGE = "maillist_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_MAILLIST_MANAGE);
	}
	
	/** (当前未实现的)网站广告管理。 */
	public boolean getAdManage() {
		// public static final String OPERATION_AD_MANAGE = "ad_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_AD_MANAGE);
	}
	
	/** 友情链接管理的操作。 */
	public boolean getFriendManage() {
		// public static final String OPERATION_FRIEND_MANAGE = "friend_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_FRIEND_MANAGE);
	}
	
	/** 公告管理的操作。 */
	public boolean getAnnounceManage() {
		// public static final String OPERATION_ANNOUNCE_MANAGE = "announce_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_ANNOUNCE_MANAGE);
	}
	
	/** (当前未实现的)调查管理的操作。 */
	public boolean getVoteManage() {
		// public static final String OPERATION_VOTE_MANAGE = "vote_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_VOTE_MANAGE);
	}

	/** (当前未实现的)统计管理的操作。 */
	public boolean getStatManage() {
		// public static final String OPERATION_STAT_MANAGE = "stat_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_STAT_MANAGE);
	}

	/** 皮肤管理。 */
	public boolean getSkinManage() {
		// public static final String OPERATION_SKIN_MANAGE = "skin_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_SKIN_MANAGE);
	}
	
	/** 模板管理的操作（适用于频道与网站）。 */
	public boolean getTemplateManage() {
		// public static final String OPERATION_TEMPLATE_MANAGE = "template_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_TEMPLATE_MANAGE);
	}
	
	/** 自定义标签管理的操作。 */
	public boolean getLabelManage() {
		// public static final String OPERATION_LABEL_MANAGE = "label_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_LABEL_MANAGE);
	}

	/** 模板方案管理的操作。 */
	public boolean getThemeManage() {
		// public static final String OPERATION_THEME_MANAGE = "theme_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_THEME_MANAGE);
	}

	/** (当前未实现的)网站缓存管理。 */
	public boolean getCacheManage() {
		// public static final String OPERATION_CACHE_MANAGE = "cache_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_CACHE_MANAGE);
	}

	/** (当前未实现的)站内链接管理。 */
	public boolean getLinkManage() {
		// public static final String OPERATION_LINK_MANAGE = "link_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_LINK_MANAGE);
	}

	/** (当前未实现的)字符过滤管理。 */
	public boolean getFilterManage() {
		// public static final String OPERATION_FILTER_MANAGE = "filter_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_FILTER_MANAGE);
	}

	/** (当前未实现的)充值卡管理。 */
	public boolean getCardManage() {
		// public static final String OPERATION_CARD_MANAGE = "card_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_CARD_MANAGE);
	}

	/** 日志管理的操作。 */
	public boolean getLogManage() {
		// public static final String OPERATION_LOG_MANAGE = "log_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_LOG_MANAGE);
	}
	
	/** 自定义页面管理的操作。 */
	public boolean getPageManage() {
		// public static final String OPERATION_PAGE_MANAGE = "page_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_PAGE_MANAGE);
	}
	
	// ?? 下面几个也许放别的地方。
	
	/** 留言管理的操作。 */
	public boolean getFeedbackManage() {
		// public static final String OPERATION_FEEDBACK_MANAGE = "feedback_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_FEEDBACK_MANAGE);
	}
	
	/** 页面生成管理的操作（适用于频道与网站）。 */
	public boolean getGenerateManage() {
		// public static final String OPERATION_GENERATE_MANAGE = "generate_manage";
		return right_coll.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_GENERATE_MANAGE);
	}
	
	// === 用户管理 
	
	/** 管理员管理的操作。 */
	public boolean getAdminManage() {
		// public static final String OPERATION_ADMIN_MANAGE = "admin_manage";
		return right_coll.checkSiteRight(Admin.TARGET_USER, Admin.OPERATION_ADMIN_MANAGE);
	}
	
	/** 会员管理的操作。 */
	public boolean getUserManage() {
		// public static final String OPERATION_USER_MANAGE = "user_manage";
		return right_coll.checkSiteRight(Admin.TARGET_USER, Admin.OPERATION_USER_MANAGE);
	}

	/** (当前未实现的)会员组管理的操作。 */
	public boolean getGroupManage() {
		// public static final String OPERATION_GROUP_MANAGE = "group_manage";
		return right_coll.checkSiteRight(Admin.TARGET_USER, Admin.OPERATION_GROUP_MANAGE);
	}
}
