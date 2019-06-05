package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import java.util.List;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.itfc.ChannelContainer;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.publish.pjo.Special;

/**
 * 专题管理的数据提供。
 * 
 * @author liujunxing
 * @jsppage 
 *  <li>admin_special_list.jsp - 专题列表页面。
 *  <li>admin_special_add.jsp - 新增/修改专题页面。
 */
public class SpecialManage extends AbstractBaseManage {
	/** 所在频道。 */
	private Channel channel;
	
	/** 专题集合。 */
	private SpecialCollection spec_coll;
	
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public SpecialManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_special_list.jsp 页面数据提供。
	 * @initdata 
	 *  <li>channel - 当前频道，可能为空。
	 *  <li>channel_list - 所有可管理频道列表。
	 *  <li>special_list - 当前频道下的专题列表。
	 */
	public void initListPage() {
		// 当前频道.
		this.channel = super.getChannelData();			// may be null.
		setTemplateVariable("channel", channel);
		
		// 检查权限。
		checkRight();
	
		internalInitListSortUnitePage();
	}

	/**
	 * admin_special_add.jsp 页面数据提供。
	 * @initdata 
	 *  <li>channel - 当前频道，可能为空。
	 *  <li>special - 当前编辑的专题，或一个新建的专题。
	 *  <li>skin_list - 可用风格列表。
	 */
	public void initEditPage() {
		// 当前频道
		this.channel = super.getChannelData();			// may be null.
		setTemplateVariable("channel", channel);
		
		// 2. 根据当前 channel, specialId 获得一个原来的 Special 对象(modify)，
		//   或者一个新的 Special 对象(add)。
		// <\pub:data var="special"	provider="com.chinaedustar.publish.admin.SpecialDataProvider" />
		SpecialWrapper special = this.getSpecialData();
		if (special == null) throw new PublishException("指定标识的专题不存在。");
		setTemplateVariable("special", special);
		
		// 可用风格列表。
		Object skin_list = super.getAvailableSkinDataTable();
		setTemplateVariable("skin_list", skin_list);
		
		// 可用模板列表。
		Object special_templ_list = super.getAvailableTemplateDataTable(channel, "special");
		setTemplateVariable("special_templ_list", special_templ_list);
	}

	/**
	 * admin_special_order.jsp 页面数据初始化。
	 *
	 */
	public void initSortPage() {
		// 当前频道.
		this.channel = super.getChannelData();			// may be null.
		setTemplateVariable("channel", channel);
		
		// 检查权限。
		checkRight();

		internalInitListSortUnitePage();
	}
	
	/**
	 * admin_special_unite.jsp 页面数据初始化。
	 *
	 */
	public void initUnitePage() {
		// 当前频道.
		this.channel = super.getChannelData();			// may be null.
		setTemplateVariable("channel", channel);
		
		// 检查权限。
		checkRight();

		internalInitListSortUnitePage();
	}

	/**
	 * admin_special_item_copy.jsp 页面数据初始化。
	 *
	 */
	public void initCopyMoveItemPage() {
		// 当前频道.
		this.channel = super.getChannelData();			// may be null.
		setTemplateVariable("channel", channel);
		
		// 检查权限。
		checkRight();
		
		// 公共数据。
		internalInitListSortUnitePage();
		
		// 额外数据： refids
		List<Integer> refids = param_util.safeGetIntValues("refids");
		setTemplateVariable("refids", refids);
	}
	
	// 初始化公共的各个页面所需数据。
	private void internalInitListSortUnitePage() {
		// 所有频道列表。
		Object channel_list = super.getManagedChannelList();
		// TODO: ?? 过滤掉自己不能管理的频道。
		setTemplateVariable("channel_list", channel_list);
		
		// 当前频道下的专题，如果没有当前频道，则为全站专题。
		ChannelContainer container = this.channel;
		if (container == null) container = site;
		this.spec_coll = container.getSpecialCollection();
		Object special_list = spec_coll.getSpecialList();
		setTemplateVariable("special_container", container);
		setTemplateVariable("special_list", special_list);
	}
	
	/**
	 * 根据当前 channel, specialId 获得一个原来的 Special 对象(modify)，
     *   或者一个新的 Special 对象(add)。
	 * @param spec_cont - 专题的容器对象。如果 = null，则使用 Site 做为容器对象。
	 * @return
	 */
	protected SpecialWrapper getSpecialData() {
		ChannelContainer container = this.channel;
		if (container == null) container = site;
		
		SpecialCollection spec_coll = container.getSpecialCollection();
		SpecialWrapper special = null;
		int specialId = super.safeGetIntParam("specialId", 0);
		if (specialId == 0) {
			// 新建一个专题对象。
			Special special_pjo = new Special();
			special = new SpecialWrapper(special_pjo, pub_ctxt, spec_coll);
			special_pjo.setChannelId(container.getChannelId());
		} else {
			// 获取一个现有的专题对象。
			special = spec_coll.getSpecial(specialId);
		}
		
		return special;
	}

	// 检查权限。
	private void checkRight() {
		if (channel != null) {
			// 在频道中必须具有总编权限。
			int channel_role = admin.getChannelRoleValue(channel.getId());
			if (channel_role < Admin.CHANNEL_ROLE_EDITOR)
				throw super.accessDenied();
		} else {
			// 全站专题必须要全站专题管理权。
			if (false == admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_SITE_SPECIAL))
				throw super.accessDenied();
		}
	}
}
