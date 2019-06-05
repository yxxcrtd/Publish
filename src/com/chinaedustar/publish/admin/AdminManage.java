package com.chinaedustar.publish.admin;

import java.util.List;
import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.comp.Tabs;
import com.chinaedustar.publish.comp.Tab;
import com.chinaedustar.publish.itfc.ChannelRightModule;
import com.chinaedustar.publish.model.*;

/**
 * 管理员管理的数据提供者类。
 * 
 * @author liujunxing
 * 
 * @jsppage 使用的页面
 *  <li>admin_admin_list.jsp
 *  <li>admin_admin_add.jsp
 *  <li>admin_admin_password_add.jsp
 */
public class AdminManage extends AbstractBaseManage {
	/**
	 * 构造一个 AdminManagerData 的新实例。
	 * @param pageContext
	 */
	public AdminManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * 初始化 admin_admin_list.jsp 页面所需的数据。
	 *
	 */
	public void initListPageData() {
		// 检查管理员权限。
		checkAdminRight();
		
		PaginationInfo page_info = getPaginationInfo();
		page_info.setItemName("个管理员");
		
		AdminCollection admin_coll = pub_ctxt.getSite().getAdminCollection();
		List<Admin> admin_list = admin_coll.getAdminList2(page_info);
		
		setTemplateVariable("admin_list", admin_list);
		setTemplateVariable("page_info", page_info);
	}

	/**
	 * 初始化 admin_admin_add.jsp 页面所需的数据。
	 * 将新建的或要编辑的管理员对象放到 pageContext 中，名字为 "admin_onedit"
	 */
	public void initAddPageData() {
		// 检查管理员权限。
		checkAdminRight();
		
		int adminId = super.safeGetIntParam("adminId", 0);
		if (adminId == 0) {
			admin = new Admin();
			admin.setAdminType(0);
		} else {
			admin = pub_ctxt.getSite().getAdminCollection().getAdmin(adminId);
			if (admin == null) throw new PublishException("未能找到指定标识的管理员，请确定您是从有效链接进入的。");
		}
		setTemplateVariable("admin_onedit", admin);
		// <pub:data var="admin" provider="com.chinaedustar.publish.admin.AdminDataProvider" />
		
	}

	/**
	 * 初始化 admin_admin_password_add.jsp 页面所需的数据。
	 *
	 */
	public void initChangePasswordPageData() {
		// TODO: 验证 self_password 能够自己修改自己。
		// 检查管理员权限。
		// checkAdminRight();

		setTemplateVariable("admin", super.admin);
	}

	/**
	 * 初始化权限设置页面。
	 *
	 */
	public void initRightPage() {
		// 检查管理员权限。
		checkAdminRight();
		
		// 当前编辑的管理员信息。
		Admin admin = getRightAdmin();
		
		// 为该管理员准备数据。
		initRightTabs(admin);
	}
	
	/**
	 * 初始化 admin_admin_column_right.jsp 页面数据。
	 *
	 */
	public void initColumnRightPage() {
		// 检查管理员权限。
		checkAdminRight();
		
		// 当前编辑的管理员信息。
		Admin admin = getRightAdmin();
		setTemplateVariable("admin_onedit", admin);

		// 获得当前频道。
		Channel channel = super.getChannelData();
		if (channel == null) throw new PublishException("没有找到指定标识的频道。");
		setTemplateVariable("channel", channel);
		
		// 获得该管理员在该频道的栏目权限。
		Object column_right = admin._getRights().createColumnRightWrapper(channel, admin);
		setTemplateVariable("column_right", column_right);
	}
	
	// 获得当前编辑的管理员对象。
	private Admin getRightAdmin() {
		AdminCollection admin_coll = pub_ctxt.getSite().getAdminCollection();
		int adminId = super.safeGetIntParam("adminId", 0);
		if (adminId == 0) {
			throw new PublishException("未指定管理员标识，请确定您是从有效链接进入的。");
		} 
		
		Admin admin = admin_coll.getAdmin(adminId);
		if (admin == null) 
			throw new PublishException("未能找到指定标识的管理员，请确定您是从有效链接进入的。");

		setTemplateVariable("admin_onedit", admin);
		return admin;
	}
	
	// 初始化权限设置 Tabs.
	private void initRightTabs(Admin admin) {
		Tabs right_tabs = new Tabs();
		// 先生成所有频道的。
		List<Channel> channels = super.getChannelListData();
		for (int i = 0; i < channels.size(); ++i) {
			Channel channel = channels.get(i);
			Tab tab = createChannelRightTab(channel, admin);
			if (tab != null)
				right_tabs.add(tab);
		}
		
		// 其它权限的。
		right_tabs.add(createSiteRightTab(admin));
		// 第一个 tab 页设置为缺省的。
		right_tabs.get(0).setDefault(true);
		
		// 放到页面中。
		setTemplateVariable("right_tabs", right_tabs);
	}
	
	/**
	 * 为频道生成权限对象，如果频道不支持权限设置则返回 null。
	 * @param admin
	 * @return
	 * 
	 * 文章模块产生的权限对象模型 请参见 ChannelRightWrapper
	 * 如果角色是栏目管理员，则还会在各个栏目上设置更详细的权限。
	 *  
	 */
	private Tab createChannelRightTab(Channel channel, Admin admin) {
		if (channel.getStatus() != Channel.CHANNEL_STATUS_NORMAL.getCode())
			return null;
		
		ChannelModule module = channel.getChannelModule();
		if (!(module instanceof ChannelRightModule)) 
			return null;
		
		ChannelRightModule right_module = (ChannelRightModule)module;
		
		// Tab 的名字为 channel_channelDir_tab, 如 'channel_news_tab'
		Tab tab = new Tab();
		tab.setName("channel_" + channel.getChannelDir() + "_tab");
		tab.setText(channel.getName());
		tab.setTemplate(right_module.getTemplateName(channel));
		tab.setExtend(right_module.createRightData(channel, admin));
		
		return tab;
	}
	
	/**
	 * 组建站点权限对象。模型参见 SiteRightWrapper
	 *
	 * 模板中使用：
	 *  input type=checkbox #{if siteRight.selfPassword}checked #{/if}
	 *  ...
	*/
	private Tab createSiteRightTab(Admin admin) {
		Tab tab = new Tab();
		tab.setName("other_right_tab");
		tab.setText("其它权限");
		tab.setTemplate("other_right_template");
		// 使这个 tab 的扩展对象为 siteRight 包装对象。
		Object site_right = admin._getRights().createSiteRightWrapper();
		tab.setExtend(site_right);
		setTemplateVariable("site_right", site_right);
		return tab;
	}

	/**
	 * 检查当前管理员是否具有管理管理员的权限。
	 * @return
	 */
	private void checkAdminRight() {
		if (this.admin == null)
			throw new PublishException("权限被拒绝。");
		
		if (false == this.admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_ADMIN_MANAGE)) {
			throw new PublishException("权限被拒绝，必须具有管理管理员的权限才能进行此操作。");
		}
	}
}
