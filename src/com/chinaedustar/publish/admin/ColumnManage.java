package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.model.*;

/**
 * 栏目管理数据提供类。
 * 
 * @author liujunxing
 * @jsppage admin_column_list.jsp
 * @jsppage admin_column_add.jsp
 */
public class ColumnManage extends AbstractBaseManage {
	/** 当前频道。 */
	private Channel channel;
	
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public ColumnManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_column_list.jsp 页面所需数据初始化。
	 * @initdata 
	 *  <li>channel - 当前频道。
	 *  <li>column_list - 栏目列表 (DataTable)
	 */
	public void initListPage() {
		// 频道数据。
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 需要本频道 channel_role > 0
		int channel_role = admin.getChannelRoleValue(channel.getId());
		if (channel_role <= 0)
			throw super.accessDenied();
		
		// 获得本频道的栏目数据。
		ColumnTree column_tree = channel.getColumnTree();
		DataTable data_table = column_tree.getColumnListDataTable();
		// TreeUtil.addSelectPrefix(data_table, "&nbsp;");
		// TreeUtil.addChildNum(data_table);
		setTemplateVariable("column_list", data_table);
		// <pub:data var="columnList"	provider="com.chinaedustar.publish.admin.ColumnListDataProvider" />
	}

	/**
	 * admin_column_add.jsp 页面所需数据初始化。
	 * @initdata channel - 当前频道对象。
	 * @initdata column - 当前栏目对象。
	 * @initdata skin_list - 可用风格列表 (DataTable)
	 * @initdata column_home_tlist - 栏目首页可用模板列表。(DataTable)
	 * @initdata content_tlist - 内容页可用模板列表 (DataTable)
	 */
	public void initEditPage() {
		// 频道数据。
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);

		// 验证在频道的权限角色。
		int channel_role = admin.getChannelRoleValue(channel.getId());
		if (channel_role <= 0)
			throw super.accessDenied();
		
		// 提供页面所用的 Column 对象。
		initColumnData(channel_role);
		
		// 提供栏目下拉列表。
		initColumnDropDown();
		
		// 提供页面可用 skin 列表。
		initSkinList();
		
		// 提供栏目首页可用模板。
		initColumnTemplate();
		
		// 提供内容页可用模板。
		initContentTemplate();
		/*
<%
PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
TemplateTheme theme = pub_ctxt.getSite().getTemplateThemeCollection().getDefaultTemplateTheme();
// 模板风格列表.
Object skins = theme.getSkinCollection().getSkinList();
setTemplateVariable("skins", skins);
%>

<!-- 定义下拉栏目列表的数据。 -->
<pub:data var="dropdown_columns" 
 provider="com.chinaedustar.publish.admin.ColumnsDropDownDataProvider" />
<!-- 栏目对象。 -->
<pub:data var="column"
 provider="com.chinaedustar.publish.admin.ColumnDataProvider" />

		*/
	}
	
	// 初始化页面所用 Column 对象。 
	private void initColumnData(int channel_role) {
		
		//ColumnTreeModel的父对象是频道，从频道对象中获取栏目集合对象。
		ColumnTree column_tree = this.channel.getColumnTree();
		Column column = null;
		int columnId = safeGetIntParam("columnId", 0);
		int parentId = safeGetIntParam("parentId", 0);

		// 指定了 columnId 就是修改栏目的信息，否则就是增加操作。
		if (columnId != 0) {
			column = column_tree.getColumn(columnId);
			if (column == null)
				throw new PublishException("指定标识的栏目不存在，请确定您是从有效链接进入的。");
			
			boolean readonly = false;
			// 修改/查看操作需要权限： channel.role >= editor or column.view = true
			if (channel_role <= Admin.CHANNEL_ROLE_COLUMN) {
				boolean view = admin.checkColumnRight(channel.getId(), column, Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_VIEW);
				boolean modify = admin.checkColumnRight(channel.getId(), column, Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_MANAGE);
				if (view == false && modify == false)
					throw super.accessDenied();
				if (view == true && modify == false)
					readonly = true;
			}
			setTemplateVariable("readonly", readonly);
		} else {
			// 新增频道需要权限： channel.role >= manager
			if (channel_role < Admin.CHANNEL_ROLE_MANAGER)
				throw super.accessDenied();
			
			column = new Column();
			column.setChannelId(channel.getId());
			column.setParentId(parentId);
			setTemplateVariable("readonly", false);
		}
		setTemplateVariable("column", column);
	}
	
	// 初始化栏目下拉列表。
	private void initColumnDropDown() {
		Object dropdown_columns = super.getColumnsDropDownData(channel);
		setTemplateVariable("dropdown_columns", dropdown_columns);
	}

	// 获得页面可用的皮肤列表。
	private void initSkinList() {
		Object skin_list = super.getAvailableSkinDataTable();
		setTemplateVariable("skin_list", skin_list);
	}
	
	// 栏目首页可用模板。
	private void initColumnTemplate() {
		Object column_home_tlist = super.getAvailableTemplateDataTable(channel, PageTemplate.CHANNEL_COLUMN_PAGE);
		setTemplateVariable("column_home_tlist", column_home_tlist);
	}
	
	// 内容页可用模板。
	private void initContentTemplate() {
		Object content_tlist = super.getAvailableTemplateDataTable(channel, PageTemplate.CHANNEL_CONTENT_PAGE);
		setTemplateVariable("content_tlist", content_tlist);
	}
}
