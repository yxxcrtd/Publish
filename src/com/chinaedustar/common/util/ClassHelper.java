package com.chinaedustar.common.util;

/**
 * 实例化类的辅助工具类。
 * 
 * <p>(摘自 Hibernate 和 FreeMarker)</p>
 * 
 */
@SuppressWarnings("rawtypes")
public class ClassHelper {
    private ClassHelper() {
    }
    
	/**
	 * 实例化指定名字的类。
	 * <p>此方法尝试先用本线程的 ContextClassLoader 来装载类，
	 *   如果不成功，则用缺省的 Class.forName() 方法来实例化类。
	 * </p>
	 * @param name - 类的全名。
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static final Class classForName(String name) throws ClassNotFoundException {
		try {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			if (contextClassLoader != null) {
				return contextClassLoader.loadClass(name);
			}
		}
		catch (Throwable t) {
		}
		return Class.forName( name );
	}

	/**
	 * 实例化指定名字的类。
	 * <p>此方法尝试先用本线程的 ContextClassLoader 来装载类；
	 *   如果不成功，则用缺省的 Class.forName() 方法来实例化类，其中使用 caller
	 *   给出的类装载器进行装载。
	 * </p>
	 * @param name - 类的全名。
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static final Class classForName(String name, Class caller) throws ClassNotFoundException {
		try {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			if (contextClassLoader != null) {
				return contextClassLoader.loadClass( name );
			}
		}
		catch (Throwable e) {
		}
		return Class.forName(name, true, caller.getClassLoader());
	}

    /** 
     * 检查指定类是否是一个有效的类，且实现了指定的接口。
     * @param clazz - 要检查的类。
     * @param itfc - 是否实现了此接口。 
     */
	public static boolean clazzImplementInterface(Class clazz, Class itfc) {
		if (clazz == null) return false;
		if (itfc == null) throw new IllegalArgumentException("itfc == null");
		
		// 检查这个类实现的接口。
    	Class[] itfcs = clazz.getInterfaces();
    	Class superClass = clazz.getSuperclass();
    	for (int i = 0; i < itfcs.length; ++i) {
    		if (itfcs[i] == itfc) return true;
    		// 检查接口的父接口。
    		if (clazzImplementInterface(itfcs[i], itfc)) return true;
    	}
    	if (clazzImplementInterface(superClass, itfc)) {
    		return true;
    	} else {
    		return false;
    	}
	}
}
