package com.chinaedustar.publish.action;

/**
 * 回收站操作
 */
public class RecycleAction extends AbstractItemAction {
	
	/**
	 * 构造
	 */
	public RecycleAction() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	public void execute() throws Exception {
		String command = param_util.safeGetStringParam("command");
		
		super.executeCommand(command);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractItemAction#getChannelAndItem()
	 */
	@Override protected boolean getChannelAndItem() {
		// 得到当前频道。
		if (super.getChannelData() == false) return false;
		
		// 得到当前要操作的项目。
		int itemId = param_util.safeGetIntParam("itemId");
		super.item = channel.loadItem(itemId);
		if (item == null) {
			messages.add("指定标识 id = " + itemId + " 的项目对象不存在。");
			links.add(getBackActionLink());
			return false;
		}
		
		return true;
	}
}
