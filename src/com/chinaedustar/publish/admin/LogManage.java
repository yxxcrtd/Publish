package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Log;
import com.chinaedustar.publish.model.LogCollection;
import com.chinaedustar.publish.model.PaginationInfo;

/**
 * 日志管理。
 * 
 * @author liujunxing
 *
 */
public class LogManage extends AbstractBaseManage {
	public LogManage(PageContext page_ctxt) {
		super(page_ctxt);
	}
	
	/**
	 * admin_log.jsp 页面数据初始化。
	 *
	 */
	public void initListPage() {
		// 检查权限。
		if (admin.checkSiteRight(Admin.OPERATION_LOG_MANAGE) == false)
			throw super.accessDenied();

		// 获得日志列表。
		PaginationInfo page_info = super.getPaginationInfo();
		Object log_list = site.getLogCollection().getLogDataTable(page_info);
		setTemplateVariable("log_list", log_list);
		page_info.setItemName("个日志项");
		setTemplateVariable("page_info", page_info);
	}

	/**
	 * admin_log_detail.jsp 页面数据初始化。
	 *
	 */
	public void initDetailPage() {
		// 检查权限。
		if (admin.checkSiteRight(Admin.OPERATION_LOG_MANAGE) == false)
			throw super.accessDenied();

		// 获得日志条目。
		LogCollection log_coll = site.getLogCollection();
		int id = param_util.safeGetIntParam("id");
		Log log = log_coll.getLog(id);
		setTemplateVariable("log", log);
		
		if (log != null) {
			// 准备其前一个 Log 记录和后一个 Log 记录。
			Log prev_log = log_coll.getPrevLog(log.getId());
			setTemplateVariable("prev_log", prev_log);
			
			Log next_log = log_coll.getNextLog(log.getId());
			setTemplateVariable("next_log", next_log);
		}
	}
}
