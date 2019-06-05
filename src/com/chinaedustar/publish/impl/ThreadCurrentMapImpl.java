package com.chinaedustar.publish.impl;

import java.util.Iterator;

import com.chinaedustar.publish.itfc.Disposeable;
import com.chinaedustar.publish.itfc.ObjectDestroyer;
import com.chinaedustar.publish.model.ThreadCurrentMap;

/**
 * ThreadCurrentMap 的实现。
 * 
 * @author liujunxing
 *
 */
public class ThreadCurrentMapImpl extends ThreadCurrentMap {
	public static ThreadLocal<ThreadCurrentMapImpl> CURRENT = 
		new ThreadLocal<ThreadCurrentMapImpl>();
		

	public static final ThreadCurrentMap impl_current() {
		ThreadCurrentMapImpl cur = CURRENT.get();
		if (cur == null) {
			cur = new ThreadCurrentMapImpl();
			CURRENT.set(cur);
		}
		return cur;
	}
	
	public static final void impl_clear() {
		ThreadCurrentMapImpl cur = CURRENT.get();
		if (cur != null) {
			cur.cleanup();
			CURRENT.remove();
		}
	}
	
	/**
	 * Map 中实际存放的 Entry.
	 *
	 */
	public static final class Entry {
		public final String name;
		private Object target;
		private ObjectDestroyer destroyer;
		
		public Entry(String name, Object target, ObjectDestroyer destroyer) {
			this.name = name;
			this.target = target;
			this.destroyer = destroyer;
		}
		
		private void cleanup() {
			if (this.target != null) {
				if (this.destroyer == null)
					this.destroyer = getObjectDestroyer();
				if (this.destroyer != null) {
					try {
						System.out.println("使用 " + destroyer + " 释放对象 " + target);
						this.destroyer.destroy(target);
					} catch (Throwable ex) {
						// ignore
						System.err.println("Exception when cleanup(destroy): " + ex);
					}
				}
			}

			target = null;
			destroyer = null;
		}
		
		/**
		 * 计算对象的缺省释放方法。我们当前只严格支持 Disposeable, Closeable 两种接口。
		 * @param target
		 * @return
		 */
		private ObjectDestroyer getObjectDestroyer() {
			if (target == null) return null;
			if (target instanceof Disposeable) 
				return ObjectDestroyer.DISPOSEABLE_OBJECT_DESTROYER;
			if (target instanceof java.io.Closeable) 
				return ObjectDestroyer.CLOSABLE_OBJECT_DESTROYER;
			return null;
		}
	}
	
	private java.util.HashMap<String, Entry> map = null; 

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ThreadCurrentMap#getNamedObject(java.lang.String)
	 */
	@Override public Object getNamedObject(String name) {
		if (this.map == null) return null;
		Entry entry = this.map.get(name);
		if (entry == null) return null;		// ?? 怎么会发生 ??
		return entry.target;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ThreadCurrentMap#putNamedObject(java.lang.String, java.lang.Object)
	 */
	@Override public void putNamedObject(String name, Object target) {
		putNamedObject(name, target, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ThreadCurrentMap#putNamedObject(java.lang.String, java.lang.Object, com.chinaedustar.publish.itfc.ObjectDestroyer)
	 */
	@Override public void putNamedObject(String name, Object target, ObjectDestroyer destroyer) {
		Entry entry = new Entry(name, target, destroyer);
		// 延迟初始化。
		if (this.map == null) 
			this.map = new java.util.HashMap<String, Entry>();
		this.map.put(name, entry);
	}

	/**
	 * 清理内部所有资源。
	 *
	 */
	private void cleanup() {
		if (this.map == null) return;
		
		// 释放每一个项目。
		Iterator<Entry> iter = this.map.values().iterator();
		while (iter.hasNext()) {
			Entry entry = iter.next();
			if (entry != null)
				entry.cleanup();
		}
		this.map = null;
	}
}
