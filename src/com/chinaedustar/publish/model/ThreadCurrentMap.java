package com.chinaedustar.publish.model;

import com.chinaedustar.publish.DefaultFilter;
import com.chinaedustar.publish.impl.ThreadCurrentMapImpl;
import com.chinaedustar.publish.impl.DummyThreadCurrentMapImpl;
import com.chinaedustar.publish.itfc.ObjectDestroyer;

/**
 * 一个存放在 ThreadLocal 中的容器对象，其存放本线程范围内的共享状态。
 * 
 * <p>
 * 一般通过 xxx.current() 方法来获取当前线程绑定的该类的一个实例，这个实例当第一次
 *   被访问的时候通常被创建，而在线程完成操作之后被释放。
 * 具体的释放是在 DefaultFilter 中完成的。
 * </p>
 * 
 * @author liujunxing
 */
public abstract class ThreadCurrentMap {
	/**
	 * 获得当前线程内绑定的 ThreadCurrentMap 对象，如果还没有值，则立刻创建一个并
	 *   放置到线程中。
	 * @return
	 */
	public static final ThreadCurrentMap current() {
		return ThreadCurrentMapImpl.impl_current();
	}
	
	/**
	 * 获得当前线程内绑定的 ThreadCurrentMap 对象，如果不存在，则返回 null,
	 *   和 current() 方法相比，其不会被自动创建 ThreadCurrentMap 对象。
	 * @return
	 */
	public static final ThreadCurrentMap _current() {
		return ThreadCurrentMapImpl.CURRENT.get();
	}
	
	/**
	 * 获得当前线程内绑定的 ThreadCurrentMap 对象，如果不存在，则返回一个 dummy 对象,
	 *   和 current() 方法相比，其不会被自动创建 ThreadCurrentMap 对象。
	 *   和 _current() 方法相比，其不会返回 null 对象。dummy 的所有操作都被忽略。
	 * @return 
	 */
	public static final ThreadCurrentMap default_current() {
		ThreadCurrentMap cur = _current();
		if (cur != null) return cur;
		return DummyThreadCurrentMapImpl.DEFAULT;
	}
	
	/**
	 * 清除当前线程内绑定的 ThreadCurrentMap 对象。通常在释放了所有资源之后执行
	 *   此操作。
	 * @return - 返回原来保存的 ThreadCurrentMap 对象。
	 * @see DefaultFilter 在 doFilter() 之后总会调用此方法。
	 */
	public static final void clear() {
		ThreadCurrentMapImpl.impl_clear();
	}
	
	/**
	 * 不希望从外部构造。
	 *
	 */
	protected ThreadCurrentMap() {
		
	}

	/**
	 * 获得具有指定名字的 ThreadLocal 范围的对象。一定不要从别的线程调用。
	 * @param name
	 * @return
	 */
	public abstract Object getNamedObject(String name);
	
	/**
	 * 给 ThreadLocal 中放一个指定名字的对象。
	 * @param name
	 * @param target
	 */
	public abstract void putNamedObject(String name, Object target);
	
	/**
	 * 给 ThreadLocal 中放一个指定名字的对象，其使用指定的释放器进行释放。
	 * @param name
	 * @param target
	 * @param destroyer
	 */
	public abstract void putNamedObject(String name, Object target, ObjectDestroyer destroyer);
}
