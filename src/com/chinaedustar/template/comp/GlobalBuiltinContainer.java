package com.chinaedustar.template.comp;

import com.chinaedustar.template.BuiltinFunction;
import com.chinaedustar.template.builtin.*;

/**
 * 全局范围内的 Builtin 的容器实现。
 * 
 * @author liujunxing
 */
public class GlobalBuiltinContainer {
	private final static GlobalBuiltinContainer instance;
	
	static {
		instance = new GlobalBuiltinContainer();
		instance.initTemplateBuiltin();
	}
	
	/**
	 * 获得 GlobalBuiltinContainer 的唯一实例。
	 * @return
	 */
	public static final GlobalBuiltinContainer getInstance() {
		return instance;
	}

	/** 内建函数映射集合。 */
	private java.util.HashMap<String, BuiltinFunction> builtin_map = new java.util.HashMap<String, BuiltinFunction>(); 
	
	/**
	 * 私有实现，外部不能实例化。
	 */
	private GlobalBuiltinContainer() {
	}

	/**
	 * 初始化 template 库自己的内建函数集合。仅在初始化的时候被调用。
	 *
	 */
	private final void initTemplateBuiltin() {
		// 为枚举器注册相应的内建函数。这是我们特定支持的。
		IterDecoBuiltins.registerTo(builtin_map);
	}

	/**
	 * 查找给指定类具有指定函数名的扩展支持函数。
	 * @param clazz
	 * @param method_name
	 * @return
	 */
	public BuiltinFunction lookupBuiltin(String method_name) {
		return this.builtin_map.get(method_name);
	}
}
