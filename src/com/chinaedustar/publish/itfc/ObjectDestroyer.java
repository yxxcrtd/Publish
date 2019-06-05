package com.chinaedustar.publish.itfc;

/**
 * 定义可以释放对象的释放器的接口，其能够释放指定的对象。
 * 
 * @author liujunxing
 */
public interface ObjectDestroyer {
	/**
	 * 请求释放指定的对象。
	 * @param target
	 */
	public void destroy(Object target) throws Throwable;
	
	/**
	 * 不需要被释放的资源，不执行任何释放操作。
	 */
	public static final ObjectDestroyer EMPTY_OBJECT_DESTROYER = new ObjectDestroyer() {
		public void destroy(Object target) {
		}
	};
	
	/**
	 * 使用 java.io.Closeable 来关闭的对象的释放器。
	 */
	public static final ObjectDestroyer CLOSABLE_OBJECT_DESTROYER = new ObjectDestroyer() {
		public void destroy(Object target) throws java.io.IOException {
			if (target == null) return;
			((java.io.Closeable)target).close();
		}
	};
	
	/**
	 * 使用 com.chinaedustar.publish.Disposable 来释放对象的释放器。
	 */
	public static final ObjectDestroyer DISPOSEABLE_OBJECT_DESTROYER = new ObjectDestroyer() {
		public void destroy(Object target) {
			if (target == null) return;
			((Disposeable)target).dipose();
		}
	};
}
