package com.chinaedustar.publish.event;

import java.util.EventListener;
import java.util.EventObject;

/**
 * 定义一个发布系统的事件接口。
 * 
 * @author liujunxing
 */
public interface PublishEvent {
	/**
	 * 获得此事件的名字，每个事件都必须具有唯一的名字。
	 * @return
	 */
	public String getEventName();
	
	/**
	 * 添加指定的事件侦听者。
	 * @param listener
	 */
	public void addListener(EventListener listener);
	
	/**
	 * 删除指定的事件侦听者。
	 * @param listener
	 */
	public void removeListener(EventListener listener);
	
	/**
	 * 对所有的事件侦听者激发指定的事件。
	 * @param event
	 */
	public void fireEvent(EventObject event);
}
