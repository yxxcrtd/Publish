package com.chinaedustar.publish.action;

import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Site;

/**
 * 站点管理的操作.
 * @author liujunxing
 *
 */
public final class SiteAction extends AbstractActionEx {
	// 注册命令, 站点支持 'save' 操作。
	static {
		registerCommand(SiteAction.class, new ActionCommand("save"));
	}

	protected ActionResult dispatchCommand() {
		String command = this.action_command.getCommand();
		if ("save".equalsIgnoreCase(command))
			return save();
		else
			return UNKNOWN_COMMAND;
	}
	
	/**
	 * 保存站点信息.
	 */
	protected ActionResult save() {
		// 验证权限。
		if (admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_SITE_MANAGE) == false) {
			return ACCESS_DENIED;
			// messages.add("更新站点信息权限被拒绝。"); - 配置在 strings.property 里面了。
		}
		
		// 收集参数，有时候这一步骤在验证权限之前。
		Site site = collectData();
		if (site == null) return INVALID_PARAM;
		
		// 执行操作。
		tx_proxy.updateSite(pub_ctxt, site);

		// 日志和信息。
		// addMessage("站点配置更新成功。");
		return SUCCESS;
	}
	
	// 收集数据。
	private Site collectData() {
		Site site = (Site)pub_ctxt.getDao().get(Site.class, pub_ctxt.getSite().getId());
		site._init(pub_ctxt, null);
		
		String name = param_util.safeGetStringParam("SiteName");
		String title = param_util.safeGetStringParam("SiteTitle");
		String url = param_util.safeGetStringParam("SiteUrl");
		String installdir = param_util.safeGetStringParam("InstallDir");
		String logourl = param_util.safeGetStringParam("LogoUrl");
		String bannerurl = param_util.safeGetStringParam("BannerUrl");
		String webmastername = param_util.safeGetStringParam("WebmasterName");
		String webmasteremail = param_util.safeGetStringParam("WebmasterEmail");
		String copyright = param_util.safeGetStringParam("Copyright");
		String meta_keywords = param_util.safeGetStringParam("Meta_Keywords");
		String meta_description = param_util.safeGetStringParam("Meta_Description");

		// 网站选项
		int hitsOfHot = param_util.safeGetIntParam("HitsOfHot");
		int announceCookieTime = param_util.safeGetIntParam("AnnounceCookieTime");
		int showSiteChannel = param_util.safeGetIntParam("ShowSiteChannel",1);
		int showAdminLogin = param_util.safeGetIntParam("ShowAdminLogin");
		int enableSaveRemote = param_util.safeGetIntParam("EnableSaveRemote");
		int enableLinkReg = param_util.safeGetIntParam("EnableLinkReg");
		int enableCountFriendSiteHits = param_util.safeGetIntParam("EnableCountFriendSiteHits");
		int enableSoftKey = param_util.safeGetIntParam("EnableSoftKey");
		int fileExt_SiteIndex = param_util.safeGetIntParam("FileExt_SiteIndex");
		int fileExt_SiteSpecial = param_util.safeGetIntParam("FileExt_SiteSpecial");

		site.setName(name);
		site.setTitle(title);
		site.setUrl(url);
		site.setInstallDir(installdir);
		site.setLogo(logourl);
		site.setBanner(bannerurl);
		site.setWebmaster(webmastername);
		site.setWebmasterEmail(webmasteremail);
		site.setCopyright(copyright);
		site.setMetaKey(meta_keywords);
		site.setMetaDesc(meta_description);
		// 网站选项
		site.setHitsOfHot(hitsOfHot);
		site.setAnnounceCookieTime(announceCookieTime);
		site.setShowSiteChannel(showSiteChannel);
		site.setShowAdminLogin(showAdminLogin);
		site.setEnableSaveRemote(enableSaveRemote);
		site.setEnableLinkReg(enableLinkReg);
		site.setEnableCountFriendSiteHits(enableCountFriendSiteHits);
		site.setEnableSoftKey(enableSoftKey);
		site.setFileExt_SiteIndex(fileExt_SiteIndex);
		site.setFileExt_SiteSpecial(fileExt_SiteSpecial);
		
		// 收集扩展属性。
		super.collectExtendsProp(site);
		
		return site;
	}
}
