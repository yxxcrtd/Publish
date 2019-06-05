package com.chinaedustar.common.bean;

/**
 * 类信息的提取器，其使用反射方法获取这个类的可用属性、函数的集合。
 * 
 * <p>
 * 使用：
 *  <pre>
 *  ClassInfo classInfo = ClassInfoExtractor.getInstance().extractClassInfo(clazz);
 *  or
 *  ClassInfo classInfo = ClassInfoExtractor.getInstance()
 *  	.extractClassInfo(clazz, ClassInfoExtractor.EXPOSE_PROPERTIES_ONLY);
 *  </pre>
 * </p>
 *
 * <p>这个类的方法是 singleton 的，可以被多线程安全的调用。</p>
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class ClassInfoExtractor {
    
    /** 缺省实例。 */
	private static ClassInfoExtractor instance;

	// === 提取等级定义 ====================================================================

    /** 提取所有方法和属性。*/
    public static final int EXTRACT_ALL = 0;
    
    /** 
     * 提取除了非安全的方法和属性。非安全的方法包括像 Object.wait(), Thread.sleep() 等
     * 其可能改变对象的状态，从而认为是不安全的。
     * 此为提取的缺省等级。
     */
    public static final int EXTRACT_SAFE = 1;
    
    /** 仅提取属性。非安全的 getters 也不提取。*/
    public static final int EXTRACT_PROPERTIES_ONLY = 2;

    /** 仅可用 generic-get 方法。 */
    public static final int EXTRACT_NOTHING = 3;

	// =======================================================================
	
	// 不外部构造。
	private ClassInfoExtractor() {
	}
	
	/**
	 * 获得 ClassInfoExtractor 的实例。
	 * @return
	 */
	public static final ClassInfoExtractor getInstance() {
		if (instance == null) {
			instance = new ClassInfoExtractor();
		}
		return instance;
	}

	// =======================================================================

    /**
     * 获得指定类的可访问属性、方法的集合。
     * @param clazz - 希望获得信息的类。
     * @return
     */
	public ClassInfo extractClassInfo(Class clazz) {
        return extractClassInfo(clazz, EXTRACT_SAFE);
    }

    /**
     * 获得指定类的可访问属性、方法的集合。
     * @param clazz - 希望获得信息的类。
     * @return
     */
    public ClassInfo extractClassInfo(Class clazz, int extract_level) {
    	return new ActualClassInfoExtractor(extract_level).extract(clazz);
    }
}
