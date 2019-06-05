package com.chinaedustar.publish.action;

import com.chinaedustar.publish.model.Soft;

/**
 * 对软件的操作。
 * 
 * @author liujunxing
 */
public class SoftAction extends AbstractItemAction {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	@Override public void execute() throws Exception {
		String command = param_util.safeGetStringParam("command");
		
		if ("save".equalsIgnoreCase(command))
			save();
		else
			// 其它如 delete, top, elite 等命令由基类统一执行。
			super.executeCommand(command);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractItemAction#getChannelAndItem()
	 */
	@Override protected boolean getChannelAndItem() {
		// 获得频道。
		if (this.getChannelData() == false) return false;

		// 获得要删除的软件对象。
		int softId = param_util.safeGetIntParam("softId");
		Soft soft = channel.loadSoft(softId);
		if (soft == null) {
			messages.add("指定标识 id = " + softId + " 的图片不存在。");
			links.add(getBackActionLink());
			return false;
		}
		this.item = soft;
		
		return true;
	}
	
	/**
	 * 新建一个软件。
	 *
	 */
	private void save() {
		// 获得频道参数。
		if (super.getChannelData() == false) return;
		
		// 获得软件数据。
		Soft soft = collect();
		if (soft == null) return;
		
		// 检查权限。
		if (checkSaveItemRight(soft) == false) return;

		// 保存数据。
		tx_proxy.saveItem(channel, soft);
		saveLatestUserSource(soft);
		
		// 添加更新指令到队列
		@SuppressWarnings("unused")
		boolean createImmediate = param_util.safeGetBooleanParam("createImmediate", false);

		// 设置返回信息，使用特定的显示模板 'soft_add_success'。
		page_ctxt.setAttribute("channel", channel);
		page_ctxt.setAttribute("soft", soft);
		super.message_template_type = "soft_add_success";
	}
	
	private Soft collect() {
		int softId = param_util.safeGetIntParam("softId", 0);
		Soft soft = null;
		if (softId > 0) {
			soft = channel.loadSoft(softId);
			if (soft == null) {
				messages.add("您要编辑的软件对象不存在，请确定您是从有效链接进入的。");
				links.add(getBackActionLink());
				return null;
			}
		} else {
			soft = new Soft();
			soft.setId(0);
			soft.setUsn(0);
		}
		
		// Item通用属性。
		super.collectItemData(soft);
		
		// 软件的特有属性
		soft.setTitle(param_util.safeGetStringParam("softName"));
		
		soft.setVersion(param_util.safeGetStringParam("softVersion"));
		soft.setThumbPic(param_util.safeGetStringParam("softThumbPic"));
		soft.setType(param_util.safeGetStringParam("softType"));
		soft.setOS(param_util.safeGetStringParam("softOS"));
		soft.setLanguage(param_util.safeGetStringParam("softLanguage"));
		soft.setCopyrightType(param_util.safeGetStringParam("softCopyrightType"));
		soft.setSize(param_util.safeGetIntParam("softSize"));
		soft.setDownloadUrls(param_util.safeGetStringParam("downloadUrls"));
		soft.setDemoUrl(param_util.safeGetStringParam("demoUrl"));
		soft.setRegistUrl(param_util.safeGetStringParam("registUrl"));
		soft.setDecompPwd(param_util.safeGetStringParam("decompPwd"));

		soft.setDayHits(param_util.safeGetIntParam("dayHist"));
		soft.setWeekHits(param_util.safeGetIntParam("weekHist"));
		soft.setMonthHits(param_util.safeGetIntParam("monthHits"));
		
		// 收集所属专题数据。
		soft.setSpecialIds(super.collectSpecialIds());

		return soft;
	}
}
