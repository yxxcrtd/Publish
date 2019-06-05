package com.chinaedustar.publish.action;

import java.util.Date;
import java.util.List;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.Item;
import com.chinaedustar.publish.model.Log;
import com.chinaedustar.publish.model.TreeItemInterface;

/**
 * 项目操作基类，提供给 ArticleAction, PhotoAction, SoftAction 做为基类使用
 */
@SuppressWarnings("rawtypes")
abstract class AbstractItemAction extends AbstractAction {
	
	/** 成功时候显示信息的缺省模板名。 */
	protected String message_template_type =  AbstractAction.TEMPLATE_MESSAGE_BLANK;
	
	/** 要执行的命令。 */
	private String command;
	
	/** 当前频道。 */
	protected Channel channel;
	
	/** 当前项目对象。 */
	protected Item item;

	/**
	 * 构造
	 */
	protected AbstractItemAction() { 
		// 
	}

	/**
	 * 执行我们识别的命令，如果不识别则调用 unknownCommand
	 * @param command
	 */
	protected void executeCommand(String command) {
		if (isHasAdmin() == false) return;
		
		// 以下所有操作都需要有管理员。
		this.command = command;
		// 删除。
		if ("delete".equalsIgnoreCase(command))
			delete();
		// 置顶、推荐、精华、审核操作。
		else if ("top".equalsIgnoreCase(command))
			top(true);
		else if ("untop".equalsIgnoreCase(command))
			top(false);
		else if ("commend".equalsIgnoreCase(command))
			commend(true);
		else if ("uncommend".equalsIgnoreCase(command))
			commend(false);
		else if ("elite".equalsIgnoreCase(command))
			elite(true);
		else if ("unelite".equalsIgnoreCase(command))
			elite(false);
		else if ("approve".equalsIgnoreCase(command))
			approve(true);
		else if ("unappr".equalsIgnoreCase(command))
			approve(false);
		else if ("reject".equalsIgnoreCase(command))
			reject();
		else if ("batch_delete".equalsIgnoreCase(command))
			this.batch_delete();
		else if ("batch_appr".equalsIgnoreCase(command))
			this.batch_approve();
		// 回收站操作。
		else if ("destroy".equalsIgnoreCase(command))
			destroy_item();
		else if ("recover".equalsIgnoreCase(command))
			recover();
		else if ("clear".equalsIgnoreCase(command))
			clear();
		else if ("batch_destroy".equalsIgnoreCase(command))
			batch_destroy();
		else if ("batch_recover".equalsIgnoreCase(command))
			batch_recover();
		else if ("recover_all".equalsIgnoreCase(command))
			recover_all();
		// 生成操作。
		else if ("generate".equalsIgnoreCase(command))
			generate();
		else if ("remove_html".equalsIgnoreCase(command))
			remove_html();
		else
			unknownCommand(command);
	}

	@Override
	public String getMessageTemplateType() {
		return this.message_template_type;
	}

	/**
	 * 获得当前频道。如果未找到频道还会在 messages, links 里面添加相应信息。
	 * @return 返回 true 表示频道存在，false 表示不存在。
	 */
	protected boolean getChannelData() {
		// 1. 获得频道参数。
		this.channel = super.getChannel();
		if (channel == null) {
			messages.add("无法找到所在频道，请确定您是从有效链接提交的数据。");
			links.add(getBackActionLink());
			return false;
		}

		return true;
	}

	/**
	 * 安全的获取当前管理员的名字。
	 * @return
	 */
	protected String safeGetAdminName() {
		Admin admin = PublishUtil.getCurrentAdmin(page_ctxt.getSession());
		if (admin == null) return "";
		return admin.getAdminName();
	}
	
	/**
	 * 从页面收集所属专题数据，并组装为集合返回。
	 * @return
	 */
	protected List<Integer> collectSpecialIds() {
		return param_util.safeGetIntValues("specialIds");
	}
	
	/**
	 * 安全的获得 columnId 参数，如果所给 column 不存在或不在当前频道，则返回频道根栏目标识.
	 * @param channel
	 * @return
	 */
	protected int safeGetColumnId(Channel channel) {
		int columnId = param_util.safeGetIntParam("columnId", 0);
		if (columnId != 0) {
			Column column = channel.getColumnTree().getColumn(columnId);
			if (column == null || column.getChannelId() != channel.getId())
				columnId = 0;
		}
		if (columnId == 0)
			columnId = channel._getCreateRootColumn().getId();
		return columnId;
	}
	
	/**
	 * 从用户输入中收集 Item 统一具有的属性。
	 *  当前包括：channelId, columnId, usn, status, editor, 
	 *    stars, top, commend, elite, hot, hits, deleted
	 *    templateId, skinId 
	 *    author, source, inputer, keywords, description, 
	 *    lastModified, createDate
	 * @return
	 */
	protected void collectItemData(Item item) {
		item.setChannelId(channel.getId());
		item.setColumnId(safeGetColumnId(channel));
		item.setUsn(item.getUsn() + 1);
		item.setStatus(param_util.safeGetIntParam("status"));
		if (item.getStatus() == Item.STATUS_APPR) {
			item.setEditor(safeGetAdminName());
		} else {
			item.setEditor("");
		}
		item.setStars(param_util.safeGetIntParam("stars"));
		item.setTop(param_util.safeGetBooleanParam("top", false));
		item.setCommend(param_util.safeGetBooleanParam("commend", false));
		item.setElite(param_util.safeGetBooleanParam("elite", false));
		item.setHot(param_util.safeGetBooleanParam("hot", false));
		item.setHits(param_util.safeGetIntParam("hits"));
		item.setDeleted(param_util.safeGetBooleanParam("deleted", false));			
		item.setTemplateId(param_util.safeGetIntParam("templateId"));
		item.setSkinId(param_util.safeGetIntParam("skinId"));

		item.setAuthor(param_util.safeGetStringParam("author"));
		item.setSource(param_util.safeGetStringParam("source"));
		if (item.getId() == 0) {
			item.setInputer(safeGetAdminName());
		}
		item.setKeywords(param_util.safeGetStringParam("keywords"));
		item.setDescription(param_util.safeGetStringParam("description"));
		item.setLastModified(new Date());	// 最后修改时间。
		item.setCreateTime(param_util.safeGetDate("createTime", new Date()));

	}
	
	/**
	 * 将最近使用作者，来源保存在用户 cookie 中。
	 * ??? 保存在 Cookie 里面好吗？
	 *
	 */
	protected void saveLatestUserSource(Item item) {
		// 保存作者
		if (StringHelper.isNotEmpty(item.getAuthor()) && !item.getAuthor().equals(item.getInputer())) {
			// TODO: 这里保存的东西有错误，想办法修正。
			// param_util.addCookie("LUA_" + item.getChannelId(), item.getAuthor());
		}
		
		// 保存来源
		if (StringHelper.isNotEmpty(item.getSource())) {
			// param_util.addCookie("LUS_" + item.getChannelId(), item.getSource());
		}
	}

	/**
	 * 获得当前要操作的项目和频道对象，派生类必须实现。
	 * 获得数据放到 channel, item 成员变量里面，delete, top 等操作需要使用这些数据。
	 * @return true 表示成功，false 表示失败。
	 */
	protected abstract boolean getChannelAndItem();

	// === 统一业务操作 =================================================================
	
	/**
	 * 验证指定频道指定项目是否具有编辑权，为 delete, top, elite 操作提供统一验证方式。
	 * @return true 表示有权限； false 表示没有权限。
	 * 做为副产品，item 的 column 对象会被设置为正确的 Column.
	 */
	private boolean checkEditorRight(Channel channel, Item item, String operation) {
		// 验证权限，删除项目需要有在该栏目的编辑权。
		Column column = channel.getColumnTree().getColumn(item.getColumnId());
		if (column == null) column = channel._getCreateRootColumn();
		
		// ? 栏目数据不正常吗?
		if (column.getChannelId() != channel.getId()) {
			messages.add("项目 '" + item.getTitle() + "' 所属栏目不在当前频道，通常是数据发生错误导致，操作无法进行下去。");
			links.add(getBackActionLink());
			return false;
		}
		
		// ? 是否具有编辑权限 ?
		if (false == admin.checkColumnRight(channel.getId(), column, 
				Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_EDITOR)) {
			messages.add("权限被拒绝，为进行" + operation + "必须在栏目具有审核权，或者在频道具有总编角色。");
			links.add(getBackActionLink());
			return false;
		}
		item.setColumn(column);
		return true;
	}

	/**
	 * 验证是否在指定项目上有管理权。
	 * @param channel
	 * @param item
	 * @param operation
	 * @return
	 */
	private boolean checkManageRight(Channel channel, Item item, String operation) {
		// 验证权限，项目需要有在该栏目的管理权。
		Column column = channel.getColumnTree().getColumn(item.getColumnId());
		if (column == null) column = channel._getCreateRootColumn();
		
		// ? 栏目数据不正常吗?
		if (column.getChannelId() != channel.getId()) {
			messages.add("项目 '" + item.getTitle() + "' 所属栏目不在当前频道，通常是数据发生错误导致，操作无法进行下去。");
			links.add(getBackActionLink());
			return false;
		}
		
		// ? 是否具有编辑权限 ?
		if (false == admin.checkColumnRight(channel.getId(), column, Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_MANAGE)) {
			messages.add("权限被拒绝，为进行" + operation + "必须在栏目具有管理权，或者在频道具有总编角色。");
			links.add(getBackActionLink());
			return false;
		}
		item.setColumn(column);
		return true;
	}
	
	/**
	 * 删除单一 Article, Soft, Photo 对象
	 */
	private Object delete() {
		String operation = "删除项目";
		if (getChannelAndItem() == false) return cant_get_item(operation);
		
		// 验证权限。
		if (checkEditorRight(channel, item, operation) == false) {
			log_access_denied(item, operation);
			return null;
		}
		
		// 删除此图片对象。
		tx_proxy.deleteItem(channel, item);
		
		// 日志。
		log_succ(item, operation);
		
		// 信息。
		messages.add(Item.toInfoString(item) +" 已成功放入回收站。");
		links.add(getBackActionLink());
		
		return item;
	}
	
	// 记录成功日志。
	private void log_succ(Item item, String operation) {
		Log log = new Log();
		log.setUserName(admin.getAdminName());
		log.setOperation(this.command);
		log.setStatus(0);
		log.setDescription(operation + " '" + item.getTitle() + 
				"' (id=" + item.getId() + ") 成功完成。");
		log.setIPUrlPostData(page_ctxt);
		
		pub_ctxt.log(log);
	}
	
	// 记录权限失败日志。
	private void log_access_denied(Item item, String operation) {
		Log log = new Log();
		log.setUserName(admin.getAdminName());
		log.setOperation(this.command);
		log.setStatus(Log.STATUS_ACCESS_DENIED);
		log.setDescription(operation + " '" + item.getTitle() + 
				"' (id=" + item.getId() + ") 权限被拒绝。");
		log.setIPUrlPostData(page_ctxt);
		
		pub_ctxt.log(log);
	}
	
	
	/**
	 * 设置单一 Article, Soft, Photo 项目的 top 状态。
	 * @param is_top
	 */
	private Object top(boolean is_top) {
		String operation = is_top ? "固顶" : "解固";
		if (getChannelAndItem() == false) return cant_get_item(operation);
		
		// 验证权限。
		if (checkEditorRight(channel, item, operation) == false) { 
			log_access_denied(item, operation);
			return null;
		}
		
		// 设置此项目 top 状态。
		tx_proxy.setItemTop(channel, item, is_top);
		
		// 日志和信息。
		log_succ(item, operation);
		messages.add(Item.toInfoString(item) + " 已成功" + operation);
		links.add(getBackActionLink());
		return item;
	}
	
	/**
	 * 设置单一 Item 项目的 commend 状态。
	 * @param is_commend
	 * @return
	 */
	private Object commend(boolean is_commend) {
		String operation = is_commend ? "设为推荐" : "解除推荐";
		if (getChannelAndItem() == false) return cant_get_item(operation);
		
		// 验证权限。
		if (checkEditorRight(channel, item, operation) == false) { 
			log_access_denied(item, operation);
			return null;
		}
		
		// 设置此项目 top 状态。
		tx_proxy.setItemCommend(channel, item, is_commend);
		
		// 日志和信息。
		log_succ(item, operation);
		messages.add(Item.toInfoString(item) + " 已成功" + operation);
		links.add(getBackActionLink());
		return item;
	}
	
	/**
	 * 设置单一图片 elite 状态。
	 * @param is_elite
	 */
	private Object elite(boolean is_elite) {
		String operation = is_elite ? "设为精华" : "解除精华";
		if (getChannelAndItem() == false) return cant_get_item(operation);

		// 验证权限。
		if (checkEditorRight(channel, item, operation) == false) { 
			log_access_denied(item, operation);
			return null;
		}
		
		// 设置此项目 elite 状态。
		tx_proxy.setItemElite(channel, item, is_elite);
		
		// 日志和信息。
		log_succ(item, operation);
		messages.add(Item.toInfoString(item) + " 已成功" + operation);
		links.add(getBackActionLink());
		return item;
	}
	
	/**
	 * 审核单一图片对象。
	 * @param is_approved
	 */
	private Object approve(boolean is_approved) {
		String operation = is_approved ? "审核通过" : "取消审核";
		if (getChannelAndItem() == false) return cant_get_item(operation);
		
		// 验证权限，审核需要有编辑权限。
		if (checkEditorRight(channel, item, operation) == false) { 
			log_access_denied(item, operation);
			return null;
		}
		
		// 设置项目的 approve 状态 (status)
		List<Integer> item_ids = new java.util.ArrayList<Integer>();
		item_ids.add(this.item.getId());
		@SuppressWarnings("unused")
		List<Integer> result = tx_proxy.batchApproveItems(channel, item_ids, is_approved);
		
		// 日志和信息。
		log_succ(item, operation);
		messages.add(Item.toInfoString(item) + " 已经成功" + operation + "。");
		links.add(getBackActionLink());
		return item;
	}
	
	/**
	 * 单一图片退稿。
	 *
	 */
	private Object reject() {
		String operation = "退稿";
		if (getChannelAndItem() == false) return cant_get_item(operation);
		
		// 验证权限，退稿需要有编辑权限。
		if (checkEditorRight(channel, item, operation) == false) { 
			log_access_denied(item, operation);
			return null;
		}
		
		// 执行操作。
		tx_proxy.rejectItem(channel, item);
		
		// 日志和信息。
		log_succ(item, operation);
		messages.add(Item.toInfoString(item) + " 已经成功退稿。");
		links.add(getBackActionLink());
		return item;
	}

	/**
	 * 获得项目核心信息。
	 * @param channel
	 * @param item_ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List getItemCoreInfo(Channel channel, List<Integer> item_ids) {
		// 提取这些项目的核心信息，包括： id, itemClass, title, columnId, parentPath
		String hql = "SELECT I.id, I.itemClass, I.title, C.id, C.parentPath " +
			" FROM Item AS I, Column AS C " +
			" WHERE I.columnId = C.id AND I.id IN (" + PublishUtil.toSqlInString(item_ids) + ")";
		List list = pub_ctxt.getDao().list(hql);
		for (int i = 0; i < list.size(); ++i) {
			list.set(i, new ItemCoreInfo((Object[])list.get(i)));
		}

		return list;
	}
	
	/**
	 * 批量验证权限，在验证的时候会提取关于项目的一些核心数据。
	 * @param channel
	 * @param item_ids
	 */
	private List batch_checkRight(Channel channel, List<Integer> item_ids, int column_oper, String operation) {
		// 如果管理员是超级管理员，则不需要验证了，这样可以节省一些时间。
		if (admin.getAdminType() == Admin.ADMIN_TYPE_SUPER) return null;
		
		// 现在根据这些核心信息进行权限判定。
		List list = getItemCoreInfo(channel, item_ids);
		for (int i = 0; i < list.size(); ++i) {
			ItemCoreInfo item = (ItemCoreInfo)list.get(i);
			item.allowed = admin.checkColumnRight(channel.getId(), item, 
					Admin.TARGET_COLUMN, column_oper);
		}
		
		logger_batch_checkRight(list, operation);
		
		return list;
	}

	// 记录日志。
	@SuppressWarnings("unused")
	private void logger_batch_checkRight(List list, String operation) {
		for (int i = 0; i < list.size(); ++i) {
			ItemCoreInfo item = (ItemCoreInfo)list.get(i);
		}
	}
	
	/**
	 * 项目核心信息。
	 * @author liujunxing
	 *
	 */
	private static class ItemCoreInfo implements TreeItemInterface {
		public ItemCoreInfo(Object[] data) {
			this.id = (Integer)data[0];
			this.itemClass = (String)data[1];
			this.title = (String)data[2];
			this.columnId = (Integer)data[3];
			this.parentPath = (String)data[4];
		}
		public int id;					// 项目标识。
		@SuppressWarnings("unused")
		public String itemClass;		// 项目类型。
		public String title;			// 项目标题。
		public int columnId;			// 栏目标识。
		public String parentPath;		// 栏目的父路径，用于验证权限。
		public boolean allowed;			// 是否允许操作。
		/** 获得栏目标识。 */
		public int getId() {
			return this.columnId;
		}
		public String getOrderPath() {
			throw new UnsupportedOperationException();
		}
		public int getParentId() {
			throw new UnsupportedOperationException();
		}
		/** 获得父路径。 */
		public String getParentPath() {
			return this.parentPath;
		}
	}
	
	private void log_batch_result(String operation, int status) {
		Log log = new Log();
		log.setUserName(admin.getAdminName());
		log.setOperation(this.command);
		log.setStatus(status);
		String description = operation + " 结果：\r\n";
		for (int i = 0; i < messages.size(); ++i)
			description += messages.get(i) + "\r\n";
		log.setDescription(description);
		log.setIPUrlPostData(page_ctxt);
		
		pub_ctxt.log(log);
	}
	
	/**
	 * 批量删除一组 item.
	 * @page_param itemIds - 对象标识(可以多个)
	 */
	private void batch_delete() {
		// 获得频道。
		String operation = "批量删除";
		if (getChannelData() == false) return;
		
		List<Integer> item_ids = param_util.safeGetIntValues("itemIds");
		if (item_ids == null || item_ids.size() == 0) {
			messages.add("没有给出要操作的对象标识。");
			links.add(getBackActionLink());
			return;
		}
		
		// 批量验证权限。
		List list = batch_checkRight(channel, item_ids, Admin.COLUMN_RIGHT_EDITOR, operation);
		if (list != null) {
			// list == null 表示全部有权限。
			item_ids.clear();
			for (int i = 0; i < list.size(); ++i) {
				ItemCoreInfo item = (ItemCoreInfo)list.get(i);
				if (item.allowed)
					item_ids.add(item.id);
				else
					messages.add("项目 '" + item.title + "' (id=" + item.id + ") 删除权限被拒绝。");
			}
			if (item_ids.size() == 0) {
				messages.add("所选择的项目的删除操作权限全部被拒绝。");
				links.add(getBackActionLink());
				log_batch_result(operation, Log.STATUS_ACCESS_DENIED);
				return;
			}
		}
		
		// 执行操作。
		List<Integer> result = tx_proxy.batchDeleteItems(channel, item_ids);
		
		// 日志和信息。
		messages.add("标识为 " + result + " 的" + channel.getItemName() + "已经成功删除，放入到回收站中。");
		links.add(getBackActionLink());
		log_batch_result(operation, Log.STATUS_SUCCESS);
	}

	/**
	 * 批量审核一组图片。
	 *
	 */
	private void batch_approve() {
		// 获得频道。
		if (getChannelData() == false) return;
		
		// 对象标识。
		List<Integer> item_ids = param_util.safeGetIntValues("itemIds");
		if (item_ids == null || item_ids.size() == 0) {
			messages.add("没有给出要操作的对象标识。");
			links.add(getBackActionLink());
			return;
		}
		
		// 审核状态。
		boolean approved = param_util.safeGetBooleanParam("status");
		String operation = approved ? "审核通过" : "取消审核";

		// 批量验证权限。
		List list = batch_checkRight(channel, item_ids, Admin.COLUMN_RIGHT_EDITOR, "批量审核");
		if (list != null) {
			// list == null 表示全部有权限。
			item_ids.clear();
			for (int i = 0; i < list.size(); ++i) {
				ItemCoreInfo item = (ItemCoreInfo)list.get(i);
				if (item.allowed)
					item_ids.add(item.id);
				else
					messages.add("项目 '" + item.title + "' (id=" + item.id + ") 审核权限被拒绝。");
			}
			if (item_ids.size() == 0) {
				messages.add("所选择的项目的审核操作权限全部被拒绝。");
				links.add(getBackActionLink());
				log_batch_result(operation, Log.STATUS_ACCESS_DENIED);
				return;
			}
		}
		
		// 执行操作。
		List<Integer> result = tx_proxy.batchApproveItems(channel, item_ids, approved);

		// 日志和信息。
		messages.add(operation + " 标识为 " + result + " 的" + channel.getItemName() + "成功完成。");
		links.add(getBackActionLink());
		log_batch_result(operation, Log.STATUS_SUCCESS);
	}
	
	/**
	 * 彻底销毁一个项目，包括其所有引用等。
	 *
	 */
	private void destroy_item() {
		// 获得频道和项目对象。
		String operation = "销毁项目";
		if (getChannelAndItem() == false) return;
		
		// 验证权限。
		if (checkManageRight(channel, item, operation) == false) {
			log_access_denied(item, operation);
			return;
		}
		
		// 执行操作。
		tx_proxy.destroyItem(channel, item);
		
		// 提示信息。
		log_succ(item, operation);
		messages.add(channel.getItemName() + " '" + item.getTitle() + "' 已经成功彻底删除掉。");
		links.add(getBackActionLink());
	}
	
	/**
	 * 恢复一个项目。
	 *
	 */
	private void recover() {
		// 获得频道和项目对象。
		String operation = "恢复项目";
		if (getChannelAndItem() == false) return;

		// 验证权限。
		if (checkManageRight(channel, item, operation) == false) {
			log_access_denied(item, operation);
			return;
		}

		// 执行操作。
		List<Integer> item_ids = new java.util.ArrayList<Integer>();
		item_ids.add(item.getId());
		tx_proxy.batchRestoreItems(channel, item_ids);
		
		// 提示信息。
		log_succ(item, operation);
		messages.add(channel.getItemName() + " '" + item.getTitle() + "' 已经成功恢复。");
		links.add(getBackActionLink());
	}
	
	/**
	 * 清除回收站中所有项目。
	 *
	 */
	private void clear() {
		// 获得所在频道。
		String operation = "清空回收站";
		if (getChannelData() == false) return;
		
		// 需要有频道管理员权限。
		int channel_role = admin.getChannelRoleValue(channel.getId());
		if (channel_role < Admin.CHANNEL_ROLE_MANAGER) {
			messages.add("清空回收站的操作被拒绝，必须在频道具有管理员角色才能执行此操作。");
			links.add(getBackActionLink());
			log_batch_result(operation, Log.STATUS_ACCESS_DENIED);
			return;
		}
		
		// 执行操作。
		List<Integer> result = tx_proxy.batchDestroyItems(channel, null);
		
		// 提示信息。
		messages.add("清除回收站成功完成，共 " + (result == null ? 0 : result.size()) 
				+ " 个" + channel.getItemName() + "彻底删除。");
		links.add(getBackActionLink());
		log_batch_result(operation, Log.STATUS_SUCCESS);
	}
	
	/**
	 * 批量销毁一组项目。
	 *
	 */
	private void batch_destroy() {
		// 获得参数。
		String operation = "批量销毁项目";
		if (getChannelData() == false) return;
		
		// 需要有频道管理员权限。
		int channel_role = admin.getChannelRoleValue(channel.getId());
		if (channel_role < Admin.CHANNEL_ROLE_MANAGER) {
			messages.add("批量销毁项目的操作被拒绝，必须在频道具有管理员角色才能执行此操作。");
			links.add(getBackActionLink());
			log_batch_result(operation, Log.STATUS_ACCESS_DENIED);
			return;
		}
		
		List<Integer> item_ids = param_util.safeGetIntValues("itemIds");
		if (item_ids == null || item_ids.size() == 0) {
			messages.add("没有给出要操作的项目标识。");
			links.add(getBackActionLink());
			return;
		}
		
		// 执行操作。
		List<Integer> result = tx_proxy.batchDestroyItems(channel, item_ids);
		
		// 日志和信息。
		messages.add("共 " + (result == null ? 0 : result.size()) 
				+ "个 " + channel.getItemName() + " 已经彻底删除。");
		messages.add("  销毁的项目标识为 " + result);
		links.add(getBackActionLink());
		log_batch_result(operation, Log.STATUS_SUCCESS);
	}
	
	/**
	 * 批量恢复。
	 *
	 */
	private void batch_recover() {
		// 获得频道对象。
		String operation = "批量恢复";
		if (getChannelData() == false) return;
		
		// 需要有频道管理员权限。
		int channel_role = admin.getChannelRoleValue(channel.getId());
		if (channel_role < Admin.CHANNEL_ROLE_MANAGER) {
			messages.add("批量恢复项目的操作被拒绝，必须在频道具有管理员角色才能执行此操作。");
			links.add(getBackActionLink());
			log_batch_result(operation, Log.STATUS_ACCESS_DENIED);
			return;
		}

		List<Integer> item_ids = param_util.safeGetIntValues("itemIds");
		if (item_ids == null || item_ids.size() == 0) {
			messages.add("没有给出要操作的项目标识。");
			links.add(getBackActionLink());
			return;
		}
		
		// 执行操作。
		List<Integer> result = tx_proxy.batchRestoreItems(channel, item_ids);
		
		// 日志和信息。
		messages.add("共 " + (result == null ? 0 : result.size()) 
				+ "个 " + channel.getItemName() + " 成功恢复。");
		messages.add("  恢复的项目标识为 " + result);
		links.add(getBackActionLink());
		log_batch_result(operation, Log.STATUS_SUCCESS);
	}
	
	/**
	 * 恢复当前频道所有被删除项目。
	 *
	 */
	private void recover_all() {
		// 获得频道对象。
		String operation = "恢复所有被删除项目";
		if (getChannelData() == false) return;

		// 需要有频道管理员权限。
		int channel_role = admin.getChannelRoleValue(channel.getId());
		if (channel_role < Admin.CHANNEL_ROLE_MANAGER) {
			messages.add("恢复所有项目的操作被拒绝，必须在频道具有管理员角色才能执行此操作。");
			links.add(getBackActionLink());
			log_batch_result(operation, Log.STATUS_ACCESS_DENIED);
			return;
		}
		
		// 执行操作。
		List<Integer> result = tx_proxy.batchRestoreItems(channel, null);
		
		// 日志和信息。
		messages.add("回收站全部恢复成功完成，共 " + (result == null ? 0 : result.size()) 
				+ "个 " + channel.getItemName() + " 成功恢复。");
		messages.add("  恢复的项目标识为 " + result);
		links.add(getBackActionLink());
		log_batch_result(operation, Log.STATUS_SUCCESS);
	}

	private Object cant_get_item(String command) {
		messages.add("在执行命令 '" + command + "' 的时候无法获得要操作的频道和项目数据，操作无法进行。");
		links.add(getBackActionLink());
		return null;
	}

	/**
	 * 检查是否有添加项目的权限。 
	 *
	 */
	private boolean checkAddItemRight(Item item) {
		// 得到栏目。
		Column column = channel._getCreateRootColumn();
		if (item.getColumnId() != 0)
			column = channel.getColumnTree().getColumn(item.getColumnId());
		
		if (false == admin.checkColumnRight(channel.getId(), column, 
				Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_INPUTER)) {
			messages.add("在栏目 '" + column.getName() + "' 添加项目的权限被拒绝。");
			links.add(getBackActionLink());
			return false;
		}
		
		item.setColumn(column);
		return true;
	}
	
	/**
	 * 检查是否具有修改项目的权限。
	 * @param item
	 * @return
	 */
	private boolean checkUpdateItemRight(Item item) {
		// 审核通过的项目任何人都不能乱修改。
		if (item.getStatus() == Item.STATUS_APPR) {
			if (admin.getAdminType() != Admin.ADMIN_TYPE_SUPER) {
				messages.add("项目 '" + item.getTitle() + "' (id=" + item.getId() + ") 已经审核通过，其不能被修改。");
				links.add(getBackActionLink());
				return false;
			}
		}
		
		// 得到栏目。
		Column column = channel._getCreateRootColumn();
		if (item.getColumnId() != 0)
			column = channel.getColumnTree().getColumn(item.getColumnId());
		
		if (false == admin.checkColumnRight(channel.getId(), column, 
				Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_INPUTER)) {
			messages.add("在栏目 '" + column.getName() + "' 修改项目的权限被拒绝。");
			links.add(getBackActionLink());
			return false;
		}
		item.setColumn(column);
		
		return true;
	}

	/**
	 * 检查是否有存储项目的权限。
	 * @param item
	 * @return
	 */
	protected boolean checkSaveItemRight(Item item) {
		if (item.getId() == 0)
			return checkAddItemRight(item);
		else
			return checkUpdateItemRight(item);
	}

	// === 生成操作 ==================================================================
	
	/**
	 * 生成指定项目。
	 */
	protected Object generate() {
		// 获得参数。
		String operation = "生成";
		if (getChannelAndItem() == false) return cant_get_item(operation);
		
		// TODO: 验证权限。
		
		// 操作。
		List<Integer> ids = new java.util.ArrayList<Integer>();
		ids.add(item.getId());
		pub_ctxt.getGenerateEngine().genChannelIdsItem(channel, ids);
		
		// 返回。
		messages.add("生成操作正在处理，您可以稍等一会使后台完成生成操作。");
		links.add(getBackActionLink());
		return null;
	}
	
	/**
	 * 删除指定项目已经生成的 html 文件。
	 * @return
	 */
	protected Object remove_html() {
		// 获得参数。
		String operation = "删除已生成文件";
		if (getChannelAndItem() == false) return cant_get_item(operation);
		
		// TODO: 验证权限。
		
		// 操作。
		tx_proxy.removeHtmlItem(channel, item);
		
		// 返回。
		messages.add("操作成功完成。");
		links.add(getBackActionLink());
		return null;
	}
}
