package com.chinaedustar.common;

import java.util.HashMap; 
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * 一个简单的 Cache 实现。
 * 
 * @author liujunxing
 *
 */
public final class SimpleCache implements Cache {
	/** 唯一的实例。 */
	private static final SimpleCache default_cache = new SimpleCache();

	/**
	 * 实际在 HashMap 中存放的数据。
	 */
	private static final class SoftValue extends SoftReference<Object> {
		private final Object key;
		private SoftValue(Object key, Object value, ReferenceQueue<Object> q) {
			super(value, q);
			this.key = key;
		}
	}

	/** 实际缓存的数据映射集合。 */
	private final HashMap<Object, SoftValue> cache_m = new HashMap<Object, SoftValue>();
	
	/** 引用队列，在检测到适当的可到达性更改后，垃圾回收器将已注册的引用对象追加到该队列中。 */
	private final ReferenceQueue<Object> ref_q = new ReferenceQueue<Object>();
	
	/** 清理计数器到达此值时就进行一次清理。 */
	private final static int CLEAN_NUM = 32;
	
	/** 何时进行一次清理的计数器。 */
	private final AtomicInteger clean_counter = new AtomicInteger(0); 
	
	/** 对缓存集合访问使用的锁。 */
	private final ReentrantReadWriteLock rw_lock = new ReentrantReadWriteLock();  

	/** 最近访问过的 多少 个项目不被清除。 */
	private final static int MAX_RECENT_ACCESS_OBJS = 32;
	
	/** 最近访问过的 N 个项目的强引用列表。 */
	private final Object[] recent_access_objs = new Object[MAX_RECENT_ACCESS_OBJS];
	
	/** recent_access_objs 访问指针，回环的。 */
	private int recent_access_ptr = 0;
	
	/**
	 * 构造一个 SimpleCache 的实例。
	 *
	 */
	public SimpleCache() {
		
	}
	
	/** 转换为字符串表示。 */
	@Override public String toString() {
		return "DataCache{item_n=" + this.cache_m.size() + "}";
	}
	
	/**
	 * 获得此缓存的实例。
	 * @return
	 */
	public static SimpleCache getDefault() {
		return default_cache;
	}
	
	/**
	 * 获得指定键的对象，如果已经不存在了，则返回 null。
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		rw_lock.readLock().lock();
		try {
			SoftReference<Object> ref = cache_m.get(key);
			if (ref == null) return null;
			
			Object value = ref.get();
			if (value == null) {
				// 引用已经不存在了，我们争取在下次 put() 的时候清除掉这个项目。
				clean_counter.incrementAndGet();
				// TODO: 也许很长时间不 put()，最好当引用计数大于 100 的时候，在这里清除一次。
				return null;
			}
			
			addToRecentList(value);
			return value;
		} finally {
			rw_lock.readLock().unlock();
		}
	}
	
	/**
	 * 放置一个缓存项。
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value) {
		if (key == null || value == null) 
			throw new IllegalArgumentException("key or value can't be null.");
		rw_lock.writeLock().lock();
		try {
			// 清理。
			if (clean_counter.get() > CLEAN_NUM) {
				cleanCache();
				clean_counter.set(0);
			}
			
			// 添加。
			SoftValue ref = new SoftValue(key, value, ref_q);
			cache_m.put(key, ref);
		} finally {
			rw_lock.writeLock().unlock();
		}
	}
	
	/**
	 * 从 Cache 中移除一项。
	 * @param key
	 * @return
	 */
	public void remove(Object key) {
		rw_lock.writeLock().lock();
		try {
			// 清理。
			if (clean_counter.get() > CLEAN_NUM) {
				cleanCache();
				clean_counter.set(0);
			}
			
			// 删除。
			cache_m.remove(key);
		} finally {
			rw_lock.writeLock().unlock();
		}
	}

	/**
	 * 清空缓存。
	 *
	 */
	public void clear() {
		rw_lock.writeLock().lock();
		try {
			// 清空缓存。
			cache_m.clear();
			
			// 清空引用队列。
			while (ref_q.poll() != null) { };
			
			// 清空引用队列。
			clearRecentList();
		} finally {
			rw_lock.writeLock().unlock();
		}
	}
	
	/**
	 * 将指定对象放到最新访问对象列表中，这样该对象就不会被 gc 收集了。
	 * @param value
	 */
	private void addToRecentList(Object value) {
		synchronized (recent_access_objs) {
			recent_access_objs[recent_access_ptr] = value;
			++recent_access_ptr;
			if (recent_access_ptr >= MAX_RECENT_ACCESS_OBJS) recent_access_ptr = 0;
		}
	}

	/** 清空最近访问的引用队列。 */
	private void clearRecentList() {
		synchronized (recent_access_objs) {
			for (int i = 0; i < MAX_RECENT_ACCESS_OBJS; ++i)
				recent_access_objs[i] = null;
			recent_access_ptr = 0;
		}
	}
	
	/** 根据 ref_q 清理一下缓冲区，假定已经写锁定了。 */
	private void cleanCache() {
		SoftValue sv;
		while ((sv = (SoftValue)ref_q.poll()) != null) {
			cache_m.remove(sv.key);
		}
	}
}
