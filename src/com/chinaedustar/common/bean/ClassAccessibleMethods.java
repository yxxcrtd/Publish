package com.chinaedustar.common.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 代表一个类可访问的公共方法的集合。
 * 
 * <p>这个类使用方法的名字和参数签名做为查找键，在查找时能够找到具有指定名字和参数签名的方法，
 *   用于解决方法重载的情况(相同函数名，但有多种形式)。</p>
 * 
 * @author liujunxing
 *
 */
@SuppressWarnings("rawtypes")
public final class ClassAccessibleMethods {
    
    /** 分析的类。 */
    private final Class the_clazz;
    
    /** 实际的映射。 */
	private final HashMap<MethodSignature, Method> the_map = new HashMap<MethodSignature, Method>();

	/**
	 * 使用指定类型构造一个 MethodSignatureMap 的实例，此实例中已经提取出了 clazz 的
	 *   所有公共可访问的方法。
	 * @param clazz
	 */
	public ClassAccessibleMethods(Class clazz) {
		if (clazz == null) throw new IllegalArgumentException("clazz == null");
		this.the_clazz = clazz;
		buildAccessiableMethods(clazz);
	}
	
	/**
	 * 获得此实例所分析的类。
	 * @return
	 */
	public final Class getSourceClass() {
		return this.the_clazz;
	}
	
	/**
	 * 获得指定方法在此集合中是否具有相同名字和签名的方法。
	 * @param m
	 * @return - 如果存在则返回该方法，否则返回 null。
	 */
    public final Method getAccessibleMethod(Method m) {
        return (m == null) ? null : the_map.get(new MethodSignature(m));
    }

    /**
     * 获得 generic-get 方法。首先找 get(String) 签名的函数，如果没有
     *   则找 get(Object) 签名的。
     * @return - 没有则返回 null。
     */
    public final Method getGenericGetMethod() {
    	// 先找 get(string).
        Method genericGet = the_map.get(MethodSignature.GET_STRING_SIGNATURE);
        if (genericGet == null) {
        	// 没有则查找 get(object)
            genericGet = the_map.get(MethodSignature.GET_OBJECT_SIGNATURE);
        }
        return genericGet;
    }

    // === 实现部分 =================================================================
    
    /**
     * 放一个项目进去。
     * @param methodSig
     * @param method
     */
    private void put(MethodSignature methodSig, Method method) {
    	the_map.put(methodSig, method);
    }
 
    /**
     * 提取一个类的公共可访问方法放到指定集合中，该集合以方法名字和签名为查找键。
     * 如果这个类不是一个公共类，则尝试查找其基类和实现的接口的公共方法。
     * @param clazz
     * @param methodSigMap
     */
    private final void buildAccessiableMethods(Class clazz) {
        if (Modifier.isPublic(clazz.getModifiers())) {	
        	// 判定此类是一个公共类?
            try {
            	// 返回这个对象所有的公共方法。
                Method[] methods = clazz.getMethods();
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    MethodSignature sig = new MethodSignature(method);
                    put(sig, method);
                }
                return;
            } catch (SecurityException e) {
            	//
            }
        }

        // 内省其实现的接口的方法集合。
        Class[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
        	buildAccessiableMethods(interfaces[i]);
        }
        
        // 内省其基类的方法集合。
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
        	buildAccessiableMethods(superclass);
        }
    }

    /**
     * 方法及其签名的简单封装类。其支持比较操作 (equals)，从而被用于 MethodSignatureMap 
     *   中做为键。
     * 
     * @author liujunxing
     */
    private static final class MethodSignature {
        /** 无参数的参数列表, 这个最常用。 */
        public static final Class[] NO_PARAMS = new Class[] { };
        
        /** 只有一个整数的参数列表。 */
        public static final Class[] INT_PARAMS = new Class[] { Integer.class };
        
        /** 只有一个字符串类型的参数列表。 */
        public static final Class[] STRING_PARAMS = new Class[] { String.class };
        
        /** 只有一个对象类型的参数列表。 */
        public static final Class[] OBJECT_PARAMS = new Class[] { Object.class };
        

    	/** generic-get get(string) 方法签名。 */
        public static final MethodSignature GET_STRING_SIGNATURE = new MethodSignature("get", STRING_PARAMS );
        
        /** generic-get get(object) 方法签名。 */
        public static final MethodSignature GET_OBJECT_SIGNATURE = new MethodSignature("get", OBJECT_PARAMS );

        /** 方法名字。 */
        private final String name;
        
        /** 方法的参数。 */
        private final Class[] args;
        
		public MethodSignature(String name, Class[] args) {
            this.name = name;
            if (args == null) 
            	this.args = NO_PARAMS;
            else if (args.length == 0)
            	this.args = NO_PARAMS;
            else if (Arrays.equals(args, INT_PARAMS))
            	this.args = INT_PARAMS;
            else if (Arrays.equals(args, STRING_PARAMS))
            	this.args = STRING_PARAMS;
            else if (Arrays.equals(args, OBJECT_PARAMS))
            	this.args = OBJECT_PARAMS;
            else
            	this.args = args;
        }
        
        public MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }
        
        /** 获得字符串表示。 */
        @Override public String toString() {
        	return "MethodSig{name=" + this.name + ",args_num=" + this.args.length + "}";
        }
        
        /** 比较操作。 */
        @Override public boolean equals(Object o) {
            if (o instanceof MethodSignature) {
                MethodSignature ms = (MethodSignature)o;
                return ms.name.equals(name) && Arrays.equals(args, ms.args);
            }
            return false;
        }
        
        /** hash 计算。 */
        @Override public int hashCode() {
            return name.hashCode() ^ args.length;
        }
    }
}
