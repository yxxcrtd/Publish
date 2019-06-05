package com.chinaedustar.publish.action;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.ColumnTree;

/**
 * 栏目管理。
 * 
 * @author liujunxing
 *
 */
public class ColumnAction extends AbstractActionEx {
	// Column 支持的操作 save, delete, clear
	static {
		registerCommand(ColumnAction.class, new ActionCommand("save"));
		registerCommand(ColumnAction.class, new ActionCommand("delete"));
		registerCommand(ColumnAction.class, new ActionCommand("clear"));
		registerCommand(ColumnAction.class, new ActionCommand("update_js"));
	}

	/** 当前频道。 */
	private Channel channel;
	
	/** 要操作的栏目对象。 */
	private Column column;

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractActionEx#executeAction()
	 *  我们重载此函数以验证 channel 是否存在。这样不用每个命令检查了。
	 *  相当于 preTranslateCommand() 的作用。
	 */
	@Override protected ActionResult executeAction() {
		this.channel = super.getChannelNeed();
		if (this.channel == null) return INVALID_PARAM;
		
		return super.executeAction();
	}

	// === 命令执行函数 ==================================================================
	
	/** 创建或更新栏目。 */
	protected ActionResult save() {
		// 收集栏目数据。里面包含验证权限。
		ActionResult result;
		if ((result = collectColumnData()) != null) return result;
		
		// 实际保存栏目。
		save_column(column);
		
		// 更新相应的js文件
		regenChannelJs(channel);
		
		// MORE: 也许以后应该添加更多链接。
		// links.add(getBackActionLink());
		return success(column.getName());
	}
	
	/** 删除栏目。 */
	protected ActionResult delete() {
		// 获得要操作的栏目对象。
		ActionResult result;
		if ((result = getOperColumn()) != null) return result;

		if (column.getParentId() == 0) {
			// messages.add("不能删除频道的根栏目。(??也没有链接可以来到这里?)");
			return result("cant_delete_root");
		}
		
		// 验证权限。
		if (admin.getChannelRoleValue(channel.getId()) < Admin.CHANNEL_ROLE_EDITOR) {
			// messages.add("不能删除栏目 '" + column.getName() + "'，权限被拒绝。");
			return ACCESS_DENIED;
		}
		
		// 实际操作。
		ColumnTree column_tree = channel.getColumnTree();
		tx_proxy.deleteColumn(column_tree, column);
		
		// 更新相应的js文件
		regenChannelJs(channel);

		// 提示信息和日志。
		// messages.add("栏目 '" + column.getName() + "' 成功删除。");
		// links.add(getBackActionLink());
		// Log log = super.createWriteLog("delete_column", "删除栏目", Log.STATUS_SUCCESS);
		// pub_ctxt.log(log);
		return success(column.getName());
	}

	/** 清除栏目内所有文章。 */
	protected ActionResult clear() {
		// 参数。
		ActionResult result;
		if ((result = getOperColumn()) != null) return result;

		// 验证权限。
		if (admin.getChannelRoleValue(channel.getId()) < Admin.CHANNEL_ROLE_EDITOR) {
			// messages.add("不能清空栏目 '" + column.getName() + "'，权限被拒绝。");
			// links.add(getBackActionLink());
			return ACCESS_DENIED;
		}

		// 执行操作。
		tx_proxy.clearItems(column);
		
		// 显示信息。
		// messages.add("栏目 '" + column.getName() + "' 清空成功完成。");
		// links.add(getBackActionLink());
		// Log log = super.createWriteLog("clear_column", "清空栏目", Log.STATUS_SUCCESS);
		// pub_ctxt.log(log);
		return success(column.getName());
	}

	/** 更新栏目的菜单 JS */
	protected ActionResult update_js() {
		// 获得频道。
		int channelId = param_util.safeGetIntParam("channelId");
		Channel channel = site.getChannel(channelId);
		if (channel == null) return INVALID_PARAM;
		
		// 执行操作。
		regenChannelJs(channel);
		
		// 返回。
		return SUCCESS;
	}
	
	// === 支持函数 ======================================================================
	
	/**
	 * 获得当前要操作的栏目对象.
	 * @return
	 */
	private ActionResult getOperColumn() {
		// 得到栏目对象。
		int columnId = param_util.safeGetIntParam("columnId");
		ColumnTree column_tree = channel.getColumnTree();
		this.column = column_tree.loadColumn(columnId);
		// 检测非空。
		if (column == null) {
			// messages.add("指定标识 id=" + columnId + " 的栏目不存在，请确定您是从有效链接进入的。");
			return result("invalid_param_id", columnId);
		}
		
		// 检测在当前频道。
		if (column.getChannelId() != channel.getId()) {
			// messages.add("您要操作的栏目 '" + column.getName() + "' 不在当前频道中，请确定您是从有效链接进入的。");
			return result("column_not_in_this_channel", column.getName());
		}

		return null;
	}
	
	/** 保存栏目。 */
	private void save_column(Column column) {
		ColumnTree column_tree = channel.getColumnTree();
		if (column.getId() != 0) {
			tx_proxy.updateColumn(column_tree, column);
			// messages.add("栏目 '" + column.getName() + "' 更新成功。");
		} else {
			tx_proxy.insertColumn(column_tree, column);
			// messages.add("栏目 '" + column.getName() + "' 添加成功。");
		}
	}

	// 重新生成频道所有静态 js 文件。
	private void regenChannelJs(Channel channel) {
		ColumnTree column_tree = channel.getColumnTree();
		column_tree.generateJs();
	}
	
	/** 收集栏目数据。 
		 * 提交数据的例子：
			Request.Parameters
			request.attr[javax.servlet.include.request_uri] = /PubWeb/admin/admin_base_action.jsp 
			request.attr[javax.servlet.include.context_path] = /PubWeb 
			request.attr[javax.servlet.include.servlet_path] = /admin/admin_base_action.jsp 
			request.attr[javax.servlet.include.query_string] = action=com.chinaedustar.publish.action.ColumnAction&debug=true 
			request.para[defaultItemSkin] = 0 
			request.para[maxPerPage] = 20 
			request.para[channelId] = 2 
			request.para[showOnTop] = True 
			request.para[enableAdd] = False 
			request.para[isElite] = False 
			request.para[defaultItemTemplate] = 0 
			request.para[skinId] = 0 
			request.para[id] = 80 
			request.para[logo] = 
			request.para[command] = save 
			request.para[columnType] = 0 
			request.para[itemOpenType] = 0 
			request.para[showOnIndex] = False 
			request.para[enableProtect] = False 
			request.para[name] = 人物图片2 
			request.para[action] = com.chinaedustar.publish.action.ColumnAction 
			request.para[parentId] = 0 
			request.para[save] = 保 存 
			request.para[tips] = 
			request.para[templateId] = 0 
			request.para[metaDesc] = 
			request.para[debug] = true 
			request.para[banner] = 
			request.para[metaKey] = 
			request.para[openType] = 0 
			request.para[columnDir] = renwu 
			request.para[description] = 
			request.para[itemListOrderType] = 1 
	 * */
	private ActionResult collectColumnData() {
		// 获取栏目。
		int columnId = param_util.safeGetIntParam("id", 0);
		if (columnId == 0)
			this.column = new Column();
		else {
			this.column = channel.getColumnTree().loadColumn(columnId);
			if (column == null || column.getChannelId() != channel.getId()) {
				messages.add("无法找到所指定的栏目，请确定您是从有效页面提交的数据。");
				// links.add(getBackActionLink());
				return INVALID_PARAM;
			}
			if (column.getParentId() == 0 || column.getId() == channel.getRootColumnId()) {
				messages.add("不能修改频道内部的根栏目。");
				// links.add(getBackActionLink());
				return FAIL;
			}
		}
		
		// 验证权限。
		int channel_role = admin.getChannelRoleValue(channel.getId());
		boolean can_move = false;
		if (column.getId() == 0) {
			// 新增栏目。需要有频道总编角色。
			if (channel_role < Admin.CHANNEL_ROLE_EDITOR) {
				messages.add("不能添加栏目，权限被拒绝，为添加栏目需要有频道总编或管理员角色。");
				// links.add(getBackActionLink());
				return ACCESS_DENIED;
			}
			can_move = true;
		} else {
			// 修改栏目。需要频道总编，或者此栏目的管理员。
			boolean can_modify = false;
			if (channel_role >= Admin.CHANNEL_ROLE_EDITOR) {
				can_modify = true;
				can_move = true;
			} else if (channel_role == Admin.CHANNEL_ROLE_COLUMN) {
				can_modify = admin.checkColumnRight(channel.getId(), column, 
						Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_MANAGE);
				can_move = false;
			} else
				can_modify = false;
			if (can_modify == false) {
				messages.add("不能修改栏目，权限被拒绝，为修改栏目需要频道总编或管理员角色，或者在该栏目有管理权。");
				// links.add(getBackActionLink());
				return FAIL;
			}
		}

		// 从提交的数据中获得所需字段。
		column.setId(columnId);
		column.setName(param_util.safeGetStringParam("name"));
		
		// 页面属性。
		column.setLogo(param_util.safeGetStringParam("logo"));
		column.setBanner(param_util.safeGetStringParam("banner"));
		column.setMetaKey(param_util.safeGetStringParam("metaKey"));
		column.setMetaDesc(param_util.safeGetStringParam("metaDesc"));
		column.setTemplateId(param_util.safeGetIntParam("templateId", 0));
		column.setSkinId(param_util.safeGetIntParam("skinId", 0));
		
		// 栏目的更多属性。
		column.setChannelId(channel.getId());
		if (can_move)
			column.setParentId(param_util.safeGetIntParam("parentId", channel.getRootColumnId()));
		column.setTips(param_util.safeGetStringParam("tips"));
		column.setDescription(param_util.safeGetStringParam("description"));
		if (column.getId() == 0) {
			// 只有新建的时候才能给出。
			column.setColumnType(param_util.safeGetIntParam("columnType"));
			column.setColumnDir(param_util.safeGetStringParam("columnDir"));
			if (column.getColumnType() == 0 && PublishUtil.isEmptyString(column.getColumnDir())) {
				messages.add("必须给出内部栏目的栏目地址。");
				return INVALID_PARAM;
			}
		}
		
		// 外部栏目。外部栏目指链接到本系统以外的地址中。当此栏目准备链接到网站中的其他系统时，请使用这种方式。不能在外部栏目中添加文章，也不能添加子栏目。
		column.setLinkUrl(param_util.safeGetStringParam("linkUrl"));
		// 打开方式：1：在新窗口打开；0：在原窗口打开。
		column.setOpenType(param_util.safeGetIntParam("openType"));
		// 是否在顶部导航栏显示：此选项只对一级栏目有效。
		column.setShowOnIndex(param_util.safeGetBooleanParam("showOnTop", false));
		// 是否在频道首页分类列表处显示：此选项只对一级栏目有效。如果一级栏目比较多，但首页不想显示太多的分类列表，这个选项就非常有用。
		column.setShowOnIndex(param_util.safeGetBooleanParam("showOnIndex", false));
		// 是否在父栏目的分类列表处显示：如果某栏目下有几十个子栏目，但只想显示其中几个子栏目的文章列表，这个选项就非常有用。
		column.setIsElite(param_util.safeGetBooleanParam("isElite", false));
		// 有子栏目时是否可以在此栏目添加文章。
		column.setEnableAdd(param_util.safeGetBooleanParam("enableAdd", false));
		// 是否启用此栏目的防止复制、防盗链功能。
		column.setEnableProtect(param_util.safeGetBooleanParam("enableProtect", false));
		// 每页显示的文章数。
		column.setMaxPerPage(param_util.safeGetIntParam("maxPerPage", 20));
		// 此栏目下的文章的默认模板。
		column.setDefaultItemTemplate(param_util.safeGetIntParam("defaultItemTemplate", 0));
		// 此栏目下的文章的默认配色风格。
		column.setDefaultItemSkin(param_util.safeGetIntParam("defaultItemSkin", 0));
		// 此栏目下的文章列表的排序方式，1：文章ID（降序）；2：文章ID（升序）；3：更新时间（降序）；4：更新时间（升序）；5：点击次数（降序）；6：点击次数（升序）。
		column.setItemListOrderType(param_util.safeGetIntParam("itemListOrderType", 1));
		// 此栏目下的文章打开方式，0：在原窗口打开；1：在新窗口打开。
		column.setItemOpenType(param_util.safeGetIntParam("itemOpenType", 0));

		return null;
	}
	
	// 获得返回的 ActionLink 对象。
	@Override protected ActionLink getBackActionLink() {
		return new ActionLink("返回", "admin_column_list.jsp?channelId=" + param_util.safeGetIntParam("channelId"));
	}
}
