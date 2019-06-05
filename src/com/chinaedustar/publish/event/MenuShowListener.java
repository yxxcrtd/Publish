package com.chinaedustar.publish.event;

import java.util.EventListener;

/**
 * 用于接收管理菜单显示事件的侦听器接口。对菜单显示感兴趣的类可以实现此接口，
 *   并向 PublishContext.events.onMenuShow 使用 addMenuShowListener()
 *   方法向该事件注册。当管理菜单将要显示的时候，可调用侦听器的相关方法，并
 *   将 MenuShowEvent 对象传递给它。
 * 
 * @author liujunxing
 *
 */
public interface MenuShowListener extends EventListener {
	/**
	 * 当菜单将要显示之前，在菜单构造阶段被调用。
	 * @param event
	 */
	public void onMenuShow(MenuShowEvent event);
}
