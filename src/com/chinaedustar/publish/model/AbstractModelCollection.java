package com.chinaedustar.publish.model;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 发布系统抽象集合模型的实现。
 * 
 * <p>
 * 内部使用 ArrayList 存储对象，并尽量保证多线程访问下的安全性。
 * </p>
 * 
 * <p>
 * 访问的最佳方法是使用线程可靠的 iterator() 枚举器进行访问。可以在获得枚举器之后通过枚举器
 *   方法得到 size(), 当前位置 index() 信息。
 * </p>
 * 
 * @author liujunxing
 * 
 * @param <T> - 集合中项目的类型，必须实现 PublishModelObject 接口。
 */
public abstract class AbstractModelCollection<T extends PublishModelObject>
			extends AbstractPublishModelBase
			implements PublishModelObject, ModelCollection<T> {
	/** 实际存放子数据模型的集合对象，更改 child_v 的值本身必须要同步。 */
	protected transient CopyOnWriteArrayList<T> child_v = new CopyOnWriteArrayList<T>();
	
	/**
	 * 构造函数。
	 * 
	 */
	protected AbstractModelCollection() {

	}

	// === 派生类必须实现的 ======================================================
	
	/**
	 * 派生类必须实现的，保证集合被完整的装载了，其在将要完整访问集合的时候调用。
	 * 派生类必须保证多线程的安全性。
	 */
	protected abstract void ensureCollectionLoaded();
	
	// === 派生类可以使用的 ======================================================
	
	/** 获得当前集合对象的大小，不会导致 collection load 操作。 */
	protected int internal_size() {
		return this.child_v.size();
	}
	
	/** 设置当前对象内部使用的集合对象，通常用于完全装载的时候。 */
	protected void set_collection(CopyOnWriteArrayList<T> coll) {
		synchronized (this) {
			this.child_v = coll;
		}
	}
	
	/** 设置当前对象内部使用的集合对象，通常用于完全装载的时候。 */
	@SuppressWarnings("rawtypes")
	protected void set_collection(java.util.List list) {
		@SuppressWarnings("unchecked")
		CopyOnWriteArrayList<T> new_col = new CopyOnWriteArrayList<T>(list);
		synchronized (this) {
			this.child_v = new_col;
		}
	}
	
	// === ModelCollection 接口实现 ====================================================

	/**
	 * 返回此集合的大小，通常不建议外部使用此方法，而是改用 iterator().size() 方法。
	 */
	public int size() {
		ensureCollectionLoaded();
		return this.child_v.size();
	}

	/**
	 * 判断此集合是否为空。在多线程条件下调用此函数是安全的，但是不能保证调用此函数
	 *   返回之后集合状态是否发生的变更。
	 *   
	 * 另外，其实现使用 size() == 0 判定是否为空，也就以为着完整装载整个集合的方法
	 *  ensureCollectionLoaded() 将被调用。派生类可以重载此函数，使其在不需要完整
	 *  装载集合的情况下就能获得是否为空的信息。
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * 获得对此集合的枚举器，枚举器支持额外的几个属性。
	 * @return
	 */
	public ModelCollectionIterator<T> iterator() {
		ensureCollectionLoaded();
		synchronized (this) {
			return new Itr<T>(this.child_v);
		}
	}

	/**
	 * 将集合里面的数据转换为数组，此数组是一个复制品，从而对其的访问、更改不影响到
	 *   实际的集合。
	 */
	public T[] toArray() {
		this.ensureCollectionLoaded();
		return this.child_v.toArray((T[])null);
	}

	// String conversion

	/**
	 * Returns a string representation of this collection.
	 */
	public String toString() {
		return "ModelCollection{cur_size=" + this.child_v.size() + "}";
	}

	/** 枚举器的实现。 */
	private static class Itr <T> implements ModelCollectionIterator<T> {
		// protected final CopyOnWriteArrayList<T> child_v;
		protected final java.util.Iterator<T> iter;
		protected int cursor = 0;
		protected int size;
		
		Itr(CopyOnWriteArrayList<T> child_v) {
			this.iter = child_v.iterator();
			this.size = child_v.size();
		}
		
		public boolean hasNext() {
			return cursor != size();
		}

		public T next() {
			++cursor;
			return iter.next();
		}

		public void remove() {
			throw new UnsupportedOperationException("此集合不支持在枚举器上面执行 remove() 操作。");
		}
		
		/** 获得所枚举的集合的大小。*/
		public int size() {
			return this.size;
		}
		
		/** 获得所枚举的项目的当前索引。 */
		public int index() {
			return this.cursor;
		}
	}
}

