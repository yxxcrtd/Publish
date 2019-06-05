package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.itfc.ChannelContainer;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.DataTable;
import com.chinaedustar.publish.model.WebPage;
import com.chinaedustar.publish.model.WebPageCollection;

/**
 * 自定义页面管理的数据提供者。
 * 
 * @author liujunxing
 *
 */
public class WebPageManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public WebPageManage(PageContext pageContext) {
		super(pageContext);
	}

	/**
	 * 提供给 admin_webpage.jsp (list page) 的数据。
	 * @initdata container, channel - 页面容器对象，一般是频道。
	 * @initdata webpage_list - 自定义页面列表。
	 */
	public void initListPage() {
		// 检查权限。
		if (admin.checkSiteRight(Admin.OPERATION_PAGE_MANAGE) == false)
			throw super.accessDenied();
		
		// 获取容器对象，其可能是 site 或 channel.
		ChannelContainer container = (ChannelContainer)super.getChannelOrSite();
		if (container == null) throw new PublishException("无法获得指定标识的频道数据，请确定您是从有效链接进入的。");
		setTemplateVariable("container", container);
		if (container instanceof Channel)
			setTemplateVariable("channel", container);

		// 获得自定义页面列表。
		Object webpage_list = getWebPageDropDownList(container.getWebPageCollection());
		setTemplateVariable("webpage_list", webpage_list);
	}
	
	/**
	 * admin_webpage_add.jsp 的页面初始化。
	 *
	 */
	public void initEditPage() {
		// 检查权限。
		if (admin.checkSiteRight(Admin.OPERATION_PAGE_MANAGE) == false)
			throw super.accessDenied();
		
		ChannelContainer container = super.getChannelOrSite();
		if (container == null) throw new PublishException("无法获得指定标识的频道数据，请确定您是从有效链接进入的。");
		setTemplateVariable("container", container);

		// 获得当前修改对象或新建一个对象。
		WebPage webpage = getWebPageData(container);
		setTemplateVariable("webpage", webpage);
		setTemplateVariable("object", webpage);

		// 获得父级页面的下拉列表数据。
		Object parent_webpage_list = getWebPageDropDownList(container.getWebPageCollection());
		setTemplateVariable("parent_webpage_list", parent_webpage_list);

		// 获得自定义页面所有可用模板。自定义页面模板不区分是哪个频道的，统一用站点的。
		Object template_list = site.getTemplateThemeCollection().getAvailableTemplateDataTable(0, 0, "webpage");
		setTemplateVariable("template_list", template_list);

		// 获得自定义页面所有可用风格(skin)。
		Object skin_list = site.getTemplateThemeCollection().getAvailableSkinDataTable(0);
		setTemplateVariable("skin_list", skin_list);
	}
	
	/**
	 * 根据当前 webpageId, parentId 获取一个原来的 WebPage 对象(modify),
	 *  或新建一个 WebPage 对象(add).
	 */
	private WebPage getWebPageData(ChannelContainer container) {
		WebPageCollection wp_coll = container.getWebPageCollection();
		WebPage webpage = null;
		int webpageId = safeGetIntParam("webpageId", 0);
		int parentId = safeGetIntParam("parentId", 0);
		if (webpageId == 0) {
			// 新建一个自定义页面对象。 ?? wp_coll.newWebPage();
			webpage = new WebPage();
			webpage.setChannelId(container.getChannelId());
			webpage._init(pub_ctxt, wp_coll);
			webpage.setParentId(parentId);
		} else {
			// 获取现有的一个自定义页面。
			webpage = wp_coll.getWebPage(webpageId);
		}
		return webpage;
	}

	/**
	 * 获得用于支持显示下拉列表框的 WebPage 列表数据。
	 * @param webpage_coll
	 * @return 返回完整树结构支持的 ColumnDataTable
	 */
	private DataTable getWebPageDropDownList(WebPageCollection webpage_coll) {
		DataTable data = webpage_coll.getWebPageDataTable(WebPageCollection.WEBPAGE_MANAGE_FIELDS);
		return data;
	}
}
