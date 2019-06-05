package com.chinaedustar.publish.event;

/**
 * 实现后台管理的 admin_index_left 页面中显示菜单时产生的事件的侦听者容器类。
 * 
 * @author liujunxing
 *
 */
public class MenuShowDelegate extends EventListenersBase <MenuShowListener, MenuShowEvent> {
	/**
	 * 构造一个 MenuShowDelegate 的实例。
	 *
	 */
	public MenuShowDelegate() {
	}
	
	/**
	 * 获得此事件的名字，每个事件都必须具有唯一的名字。
	 * @return
	 */
	public String getEventName() {
		return "onMenuShow";
	}
	
	/**
	 * 对指定的事件侦听者激发指定的事件。
	 * @param event
	 */
	@Override protected void fireEvent(MenuShowListener listener, MenuShowEvent event) {
		listener.onMenuShow(event);
	}
}
