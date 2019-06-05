package com.chinaedustar.publish.action;

import java.util.List;

/**
 * 日志管理操作。
 * @author liujunxing
 *
 */
public class LogAction extends AbstractAction {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	@Override
	public void execute() throws Exception {
		String command = param_util.safeGetStringParam("command");
		if ("batch_delete".equalsIgnoreCase(command))
			batch_delete();
		else if ("clear".equalsIgnoreCase(command))
			clear();
		else
			unknownCommand(command);
	}

	// 批量删除日志。
	private void batch_delete() {
		List<Integer> ids = param_util.safeGetIntValues("id");
		tx_proxy.batchDeleteLogs(site.getLogCollection(), ids);
		
		messages.add("批量删除日志 id = " + ids + " 成功完成。");
		links.add(getBackActionLink());
	}
	
	// 清空所有日志。
	private void clear() {
		tx_proxy.clearLogs(site.getLogCollection());
		
		messages.add("清空日志成功完成。");
		links.add(getBackActionLink());
	}
}

