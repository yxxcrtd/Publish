package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.biz.BizConst;

/**
 * 管理主页的数据提供。
 * 
 * @author liujunxing
 */
public class IndexManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public IndexManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_index_main.jsp 页面数据初始化。
	 * @initdata site - 总是放置一个站点对象
	 * @initdata channel_list - 所有频道列表。
	 */
	public void initMainPageData() {
		Object channel_list = super.getChannelListData();
		setTemplateVariable("channel_list", channel_list);
	}
	
	/**
	 * admin_index_left.jsp 页面数据初始化。
	 * 
	 */
	public void initLeftPageData() {
		// TODO: 得到频道的菜单列表。
		// <pub:data var="channel_menus" provider="com.chinaedustar.publish.admin.ChannelMenuItemListDataProvider" />
	}
	
	
	/**
	 * 是否支持 Biz_Name 处理，其从 spring 中查找是否存在 'biz_sel_dao' 的 bean。
	 * @return
	 */
	public boolean isSupportBiz() {
		return pub_ctxt.getSpringContext().containsBeanDefinition(BizConst.BIZ_SELECT_DAO_BEAN);
	}
}
