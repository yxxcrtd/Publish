package com.chinaedustar.publish.event;

import java.util.EventListener;
import java.util.EventObject;

/**
 * 定义抽象的 EventListeners 集合类的基类。
 * @author liujunxing
 *
 * @param <LISTENER> - 侦听器接口类型，必须实现 EventListener 接口。
 * @param <EVT_OBJ> - 事件对象类型，必须派生自 EventObject。
 */
public abstract class EventListenersBase 
		<LISTENER extends EventListener, EVT_OBJ extends EventObject> {

	/** 所有侦听器的集合。 */
	private final java.util.ArrayList<LISTENER> listeners = 
		new java.util.ArrayList<LISTENER>();
	
	protected EventListenersBase() {
		
	}

	/**
	 * 得到此事件的名字。
	 * @return
	 */
	public abstract String getEventName();
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getName() + "{eventName=" + 
			this.getEventName() + ", listener.size=" + this.listeners.size() + "}";
	}
	
	/**
	 * 添加指定的事件侦听者。
	 * @param listener
	 */
	public synchronized void addListener(LISTENER listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * 删除指定的事件侦听者。
	 * @param listener
	 */
	public synchronized void removeListener(LISTENER listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * 对所有的事件侦听者激发指定的事件。
	 * @param event - 激发的事件对象。
	 */
	public synchronized void fireEvent(EVT_OBJ event) {
		for (int i = 0; i < this.listeners.size(); ++i) {
			fireEvent(this.listeners.get(i), event);
		}
	}
	
	/**
	 * 由派生类实现，实际产生事件调用。
	 * @param listener
	 * @param event
	 */
	protected abstract void fireEvent(LISTENER listener, EVT_OBJ event);
}
