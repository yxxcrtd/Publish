package com.chinaedustar.common.reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * 主要类型的内存映射表提供类。
 *  
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public final class PrimitiveClassesMap {
	/** 类型映射表。 */
	private static HashMap<String, Class> pc_map;
	
	/**
	 * 获得类型映射表，类型映射表对 boolean, byte, char, short, int, long, float, double 
	 *  几种系统基本类型提供一个映射表。
	 * @return
	 */
	public static Map<String, Class> getPrimitiveClassesMap() {
		if (pc_map != null) return pc_map;
		// 现用现创建，即使有多线程进入，重复创建一次也不会产生大的问题，因为这个不会老被调用。
		pc_map = createPrimitiveClassesMap();
		return pc_map;
	}
	
	public PrimitiveClassesMap() {
		
	}

    /** 创建主要类型的集合。 */
    private static HashMap<String, Class> createPrimitiveClassesMap() {
        HashMap<String, Class> map = new HashMap<String, Class>();
        map.put("boolean", Boolean.TYPE);
        map.put("byte", Byte.TYPE);
        map.put("char", Character.TYPE);
        map.put("short", Short.TYPE);
        map.put("int", Integer.TYPE);
        map.put("long", Long.TYPE);
        map.put("float", Float.TYPE);
        map.put("double", Double.TYPE);
        return map;
    }
}
