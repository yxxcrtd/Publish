package com.chinaedustar.publish.admin;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.action.ActionLink;
import com.chinaedustar.publish.model.*;

/**
 * 友情站点管理页面数据提供。
 * 
 * @author liujunxing
 *
 */
public class FriendSiteManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public FriendSiteManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_friendsite_list.jsp 页面数据初始化。
	 * @initdata
	 *  <li>fs_list - 友情站点列表。
	 *  <li>page_info - 分页信息。
	 *  <li>fs_nav - 管理导航数据。
	 */
	public void initListPage() {
		// 获取友情链接列表。
		initListData();
		
		// 友情链接类别列表
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		Object fs_kinds = fsc.getFsKindList();
		setTemplateVariable("fs_kinds", fs_kinds);
		
		// 所有友情链接专题列表
		Object fs_specials = fsc.getFsSpecialList();
		setTemplateVariable("fs_specials", fs_specials);
	}

	/**
	 * admin_friendsite_add.jsp 页面数据初始化.
	 *
	 */
	public void initEditPage() {
		// 友情链接类别列表。
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		Object fs_kinds = fsc.getFsKindList();
		setTemplateVariable("fs_kinds", fs_kinds);
		
		// 友情链接专题列表。
		Object fs_specials = fsc.getFsSpecialList();
		setTemplateVariable("fs_specials", fs_specials);

		FriendSite fs_obj;
		int id = param_util.safeGetIntParam("id");
		if (id == 0) { 
			fs_obj = new FriendSite();
		} else {
			fs_obj = fsc.getFriendSite(id);
		}
		setTemplateVariable("fs_obj", fs_obj);
	}

	/**
	 * admin_fs_kind.jsp 页面数据初始化.
	 *
	 */
	public void initKindListPage() {
		// 提供所有友情链接类别对象, 并额外计算每个友情链接类别下面有多少个友情链接(param=count)
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		Object fs_kinds = fsc.getFsKindWithCount();
		setTemplateVariable("fs_kinds", fs_kinds);
	}
	
	/**
	 * admin_fs_special.jsp 页面数据初始化.
	 *
	 */
	public void initSpecialListPage() {
		// 提供所有友情链接专题对象, 并额外计算每个友情链接专题下面有多少个友情链接(param=count)
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		Object fs_specials = fsc.getFsSpecialListWithCount();
		setTemplateVariable("fs_specials", fs_specials);
	}

	/**
	 * admin_fs_kind_add.jsp 页面数据初始化.
	 *
	 */
	public void initKindAddPage() {
		int id = param_util.safeGetIntParam("kindId");
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		FriendSiteKind fs_kind = null;
		if (id != 0)
			fs_kind = fsc.getFriendSiteKind(id);
		if (fs_kind == null)
			fs_kind = new FriendSiteKind();
		setTemplateVariable("fs_kind", fs_kind);
	}
	
	/**
	 * admin_fs_special_add.jsp 页面数据初始化.
	 *
	 */
	public void initSpecialAddPage() {
		int id = param_util.safeGetIntParam("specialId");
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		FriendSiteSpecial fs_special = null;
		if (id != 0)
			fs_special = fsc.getFriendSiteSpecial(id);
		if (fs_special == null)
			fs_special = new FriendSiteSpecial();
		setTemplateVariable("fs_special", fs_special);
	}
	
	/**
	 * admin_friendsite_order.jsp 页面数据初始化.
	 *
	 */
	public void initReorderPage() {
		Object fs_list = site.getFriendSiteCollection().getFriendSiteList();
		setTemplateVariable("fs_list", fs_list);
	}
	
	/**
	 * 友情链接申请页面 friendSiteReg.jsp 数据初始化.
	 *
	 */
	public void initRegPage() {
		// 友情链接类别列表。
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		Object fs_kinds = fsc.getFsKindList();
		setTemplateVariable("fs_kinds", fs_kinds);
		
		// 友情链接专题列表。
		Object fs_specials = fsc.getFsSpecialList();
		setTemplateVariable("fs_specials", fs_specials);
	}
	
	/**
	 * 从 friendSiteReg.jsp 页面提交的友情链接网站申请请求。
	 * 如果检测有问题，则 error_list 非空，否则里面存有错误信息。
	 */
	public void userSaveReg() {
		// 准备。
		java.util.List<String> error_list = new java.util.ArrayList<String>();
		setTemplateVariable("error_list", error_list);

		// 收集数据。
		String siteName = param_util.safeGetStringParam("SiteName", "");
		String siteUrl = param_util.safeGetStringParam("SiteUrl", "");
		if (siteName == null || siteName.length() == 0)
			error_list.add("输入网站名称不能为空。");
		if (siteUrl == null || siteUrl.length() == 0)
			error_list.add("输入网站的 URL 不能为空。");
		if (error_list.isEmpty() == false) return;

		
		FriendSite fs = new FriendSite();
		fs.setSiteName(siteName);
		fs.setSiteUrl(siteUrl);
		fs.setApproved(false);
		fs.setLogo(param_util.safeGetStringParam("LogoUrl", ""));
		fs.setSiteAdmin(param_util.safeGetStringParam("SiteAdmin", ""));
		fs.setSiteEmail(param_util.safeGetStringParam("SiteEmail", ""));
		if(param_util.safeGetStringParam("SitePwdConfirm").equals(param_util.safeGetStringParam("SitePassword"))){
			fs.setSitePassword(param_util.safeGetStringParam("SitePassword", ""));
		}
		fs.setDescription(param_util.safeGetStringParam("SiteIntro", ""));
		
		// 验证名字和URL是否已经有了。
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		if (fsc.checkNewReg(siteName, siteUrl) == false) {
			error_list.add("您请求注册的网站名字或网站地址已经存在，如果您是要修改网站信息，请选择修改友情链接注册(需要您给出密码)");
			return;
		}

		// 执行插入操作。
		tx_proxy.saveFriendSite(fsc, fs);
	}

	/** 初始化友情站点列表数据。 */
	private void initListData() {
		// 收集参数。
		PaginationInfo page_info = getPaginationInfo();
		int type = super.safeGetIntParam("type", 0);
		Boolean approved = super.safeGetBooleanParam("approved", null);
		int kindId = super.safeGetIntParam("kindId", 0);
		int specialId = super.safeGetIntParam("specialId", 0);
		if (type < 0 || type > 1) type = 0;
		
		request_param.put("approved", approved);
		request_param.put("type", type);
		request_param.put("kindId", kindId);
		request_param.put("specialId", specialId);
		
		// 获取指定条件的友情链接 List<FriendSite> - fs_list, page_info 。
		FriendSiteCollection fsc = site.getFriendSiteCollection();
		List<FriendSite> fs_list = fsc.getFriendSiteList(approved, null, 0, kindId, specialId, 0, page_info);
		page_info.setItemName("个友情站点");
		page_info.init();
		setTemplateVariable("fs_list", fs_list);
		setTemplateVariable("page_info", page_info);

		// 导航 - fs_nav。
		Object nav = createNavData(fsc, type, approved, kindId, specialId);
		setTemplateVariable("fs_nav", nav);
	}
	
	// 根据页面参数产生导航数据。List<ActionLink(text, url)>
	// 例子：您现在的位置： 友情链接管理 >> 按类别分类 >> 所有友情链接。
	private static Object createNavData(FriendSiteCollection fsc, int type, Boolean approved, int kindId, int specialId) {
		ArrayList<ActionLink> nav = new ArrayList<ActionLink>();
		
		// 友情链接管理
		nav.add(new ActionLink("友情链接管理", "admin_friendsite_list.jsp"));
		
		// 按照XX分类
		if (type == 0)
			nav.add(new ActionLink("按类别分类", "admin_friendsite_list.jsp?type=0"));
		else
			nav.add(new ActionLink("按专题分类", "admin_friendsite_list.jsp?type=1"));
		
		// {所有|未审核|已审核} {|xxx类别|xxx专题} 友情链接
		String text = getApproveText(approved);
		if (kindId != 0)
			text += getKindText(fsc, kindId);
		else if (specialId != 0)
			text += getSpecialText(fsc, specialId);
		text += "友情链接";
		String link = "admin_friendsite_list.jsp?type=" + type + "&approved=" + approved + "&kindId=" + kindId + "&specialId=" + specialId;
		nav.add(new ActionLink(text, link));
		
		return nav;
	}
	
	private static final String getApproveText(Boolean approved) {
		// approved - 审核状态标识。缺省 = 0 表示全部；= 1 表示取未审核的； = 2 表示取已审核的。
		if (approved == null)
			return "所有";
		else if (approved == true)
			return "已审核的";
		else
			return "未审核的";
	}
	
	private static final String getKindText(FriendSiteCollection fsc, int kindId) {
		FriendSiteKind kind = fsc.getFriendSiteKind(kindId);
		return kind == null ? "" : kind.getName() + "类别";
	}
	
	private static final String getSpecialText(FriendSiteCollection fsc, int specialId) {
		FriendSiteSpecial special = fsc.getFriendSiteSpecial(specialId);
		return special == null ? "" : special.getName() + "专题";
	}

}
