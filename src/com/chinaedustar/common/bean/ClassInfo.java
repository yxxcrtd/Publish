package com.chinaedustar.common.bean;

//import java.beans.IndexedPropertyDescriptor;
//import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 表示一个类的可访问方法、属性的集合。
 * 
 * <p>每个类可能包含这样的几种可访问信息：
 * <ul>
 *   <li>Generic-get: 通用获取方法，其具有签名 Object get(String) 或 Object get(Object)</li>
 *   <li>Property: 属性获取方法，例如 name, 其具有读取方法 (getXxx()) 及设置方法 (setXxx(Class))</li>
 *   <li>IndexedProperty: 索引属性方法，其具有签名 getXxx(Integer)</li>
 *   <li>Method: 一般方法。</li>
 *   <li>OverloadMethod: 一组方法，其具有相同的名字，但参数签名不同。</li>
 * </ul>
 * </p>
 * 
 * <p>这个类原来参考自 freemarker.beanwrapper 中一个无类型的 Map 参考实现的。
 *   我们对它进行了强类型转换，并试图封装上面的几种方法为统一的接口。</p>
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public final class ClassInfo {
	
	/** 空的 ClassInfo 集合。 */
	public static final ClassInfo EMPTY_CLASS_INFO = new ClassInfo(Object.class);

	/** 代表的是此类的信息。 */
	private final Class the_clazz; 
	
	/** 一般获取方法。(generic-get method) */
	private GenericGetMethodDelegate generic_get;
	
	/** 包装起来的可访问方法、属性的映射集合。 */
	private final HashMap<String, MethodDelegate> deleg_map = new HashMap<String, MethodDelegate>();
	
	/** 参数类型的映射，TODO: 强类型化。 ?? 其现在有什么用途 ?? */
	private HashMap<Object, Object> arg_types = null;
	
	/** 可能是 Constructor 或 MethodMap 类型。 */
	@SuppressWarnings("unused")
	private Object constructors;
	
	/**
	 * 构造函数。
	 *
	 */
	ClassInfo(Class clazz) {
		this.the_clazz = clazz;
	}
	
	// === 外部可用方法部分 =======================================================
	
	/**
	 * 获得通用获取方法。
	 * @return
	 */
	public MethodDelegate getGenericGetMethod() {
		return generic_get;
	}
	
	/**
	 * 获得构造函数，其可能是 Constructor 或 MethodMap 类型。
	 * @return
	 */
	/*
	public Object getConstructors() {
		return this.constructors;
	}
	*/
	
	/**
	 * 从集合中查找具有指定名字的可调用方法。
	 * @param key
	 * @return - 返回该方法的委托包装对象。
	 */
	public MethodDelegate get(String key) {
		return deleg_map.get(key);
	}
	
	/**
	 * 用于测试用途，将内容输出到 System.out
	 *
	 */
	public void dump() {
		System.out.println("=== Dump ClassInfo for clazz '" + the_clazz + "' =============");
		System.out.println("  Generic-get: " + this.generic_get);
		Iterator<String> iter = deleg_map.keySet().iterator();
		while (iter.hasNext()) {
			String method_name = iter.next();
			System.out.println("  " + method_name + ": " + deleg_map.get(method_name));
		}
		System.out.println("");
	}
	
	// === 实现部分 =============================================================
	
	/**
	 * 设置通用获取方法。
	 * @return
	 */
	void putGenericGetMethod(Method generic_get) {
		this.generic_get = new GenericGetMethodDelegate(generic_get);
	}
	
	/**
	 * 设置一个方法项目。
	 *
	 */
	void putNamedMethod(String method_name, Method method) {
		// 获取原有的。
		MethodDelegate prev_method = deleg_map.get(method_name);
		if (prev_method == null) {
			// 原来没有则添加一个方法。
			SimpleMethodDelegate method_dele = new SimpleMethodDelegate(method_name, method);
			deleg_map.put(method_name, method_dele);
		} else if (prev_method.getMethodType() == MethodDelegate.METHOD_TYPE_METHOD) {
			// 原来有一个方法了，则要创建 OverloadedMethodDelegate, 并替代原有的。
			OverloadedMethodDelegate overl_deleg = new OverloadedMethodDelegate(method_name);
			overl_deleg.addMethod(((SimpleMethodDelegate)prev_method).getReadMethod());
			overl_deleg.addMethod(method);
			deleg_map.put(method_name, overl_deleg);
		} else if (prev_method.getMethodType() == MethodDelegate.METHOD_TYPE_OVERLOADED_METHOD) {
			// 原来就是重载方法，则添加进去，不用更新 deleg_map。
			OverloadedMethodDelegate overl_deleg = (OverloadedMethodDelegate)prev_method;
			overl_deleg.addMethod(method);
		} else {
			// 否则是一个 generic-get, property, indexed-property, 我们不要覆盖。
		}

		/*
        TODO:
        String name = md.getName();
        Object previous = classInf.get(name);
        if (previous instanceof Method) {
            // Overloaded method - replace method with a method map
            MethodMap methodMap = new MethodMap(name);
            methodMap.addMethod((Method)previous);
            methodMap.addMethod(publicMethod);
            // ClassMap<String, MethodMap>
            classInf.pubMethodMap(name, methodMap);
            // remove parameter type information
            classInf.removeArgTypes((Method)previous);
        }
        else if(previous instanceof MethodMap) {
            // Already overloaded method - add new overload
            ((MethodMap)previous).addMethod(publicMethod);
        } else {
            // Simple method (this far)
        	// ClassMap<String, Method>
        	classInf.putNamedMethod(name, publicMethod);
        	classInf.removeArgTypes(publicMethod);
        }
		*/
	}
	
	/**
	 * 添加一个 IndexedPropertyDescriptor 的映射项目。
	 * @param ipd
	 */
	void putIndexProperty(String prop_name, Method read_method, Method write_method) {
		IndexedPropertyDelegate indx_dele = new IndexedPropertyDelegate(prop_name, read_method, write_method);
		deleg_map.put(prop_name, indx_dele);
	}
	
	/**
	 * 添加一个属性的访问项目，其具有指定的读取方法和写入方法。
	 * @param prop_name - 此属性的名字。
	 */
	void putProperty(String prop_name, Method read_method, Method write_method) {
		PropertyDelegate prop_dele = new PropertyDelegate(prop_name, read_method, write_method);
        deleg_map.put(prop_name, prop_dele);
	}
	
	/**
	 * 在 ARGTYPES 集合中放一个 method, paramTypes 的项目。
	 * @param method
	 * @param paramTypes
	 */
	@SuppressWarnings("unused")
	private void putArgTypes(Method method, Class[] paramTypes) {
		// map<Method, Class[]>
		getArgTypes().put(method, paramTypes);
	}
	
	@SuppressWarnings("unused")
	private void putArgTypes(Constructor ctor, Class[] paramTypes) {
		// map<Constructor, Class[]>
		getArgTypes().put(ctor, paramTypes);
	}
	
	/**
	 * 在 ARGTYPES 集合中删除一项。
	 * @param method
	 */
	@SuppressWarnings("unused")
	private void removeArgTypes(Method method) {
		getArgTypes().remove(method);
	}
	
	/**
	 * 设置构造器。
	 * @param ctor
	 */
	@SuppressWarnings("unused")
	private void putConstructor(Constructor ctor) {
		this.constructors = ctor;
	}
	
	/**
	 * 设置构造器集合。
	 * @param ctorMap
	 */
	@SuppressWarnings("unused")
	private void putConstructors(OverloadedMethodMap ctorMap) {
		this.constructors = ctorMap;
	}

    /**
     * 获得或生成 ARGTYPES 的映射 HashMap 并返回。
     * @return
     */
    private HashMap<Object, Object> getArgTypes() {
    	if (arg_types == null) {
    		arg_types = new HashMap<Object, Object>();
    	}
    	return arg_types;
    }

	// === MethodDelegate 的实现 =====================================================
    
    /**
     * 支持读取、写入方法执行的 MethodDelegate 基本实现。
     */
    private static abstract class MethodDelegateBase implements MethodDelegate {
    	protected final String name;			// 方法或属性的名字。
    	protected final Method read_method;	// 读取方法。
    	protected final Method write_method;	// 写入方法，可能为 null。
    	
    	MethodDelegateBase(String name, Method read_method, Method write_method) {
    		this.name = name;
    		this.read_method = read_method;
    		this.write_method = write_method;
    	}
    	
    	/** 
    	 * 使用指定的参数在这个方法上产生 get 调用，对于 generic-get, property, 
    	 *   indexed-property, method, overloaded-method 都适用。
    	 * @param target - 调用目标对象。
    	 * @param args - 参数。
    	 */
    	public Object invoke(Object target, Object... args) throws InvocationTargetException, IllegalAccessException {
    		if (this.read_method == null)
    			throw new UnsupportedOperationException("类没有 '" + this.name + "' 的读取属性或方法。");
    		return this.read_method.invoke(target, args);
    	}

    	/**
    	 * 获得此方法或属性的名字。某些类型的方法可能没有名字。
    	 * @return
    	 */
    	public String getName() {
    		return this.name;
    	}

    	/**
    	 * 获得方法调用的返回类型。
    	 * @return
    	 */
    	public Class getReturnType() {
    		return this.read_method.getReturnType();
    	}

    	
    	/**
    	 * 使用指定的参数在这个方法上产生 set 调用，对于 property, indexed-property
    	 *   适用。其它类型方法不能用。
    	 * @param target - 调用目标对象。
    	 * @param args
    	 * @return
    	 */
    	@SuppressWarnings("unused")
		public Object invokeSet(Object target, Object... args) throws InvocationTargetException, IllegalAccessException {
    		if (this.write_method == null)
    			throw new UnsupportedOperationException("类不支持对属性 '" + this.name + "' 的写入调用。");
    		return this.write_method.invoke(target, args);
    	}
    
    	/** 获得读取方法，一般方法也保存在读取方法里面。 */
    	Method getReadMethod() { return this.read_method; }
    }
    
    /**
     * GenericGetMethodDelegate - 实现 generic-get 方法的包装。
     */
    private static final class GenericGetMethodDelegate extends MethodDelegateBase {
    	GenericGetMethodDelegate(Method generic_get) {
    		super("get", generic_get, null);
    	}
    	
    	public String toString() {
    		return "GenericGet{name=" + super.name + "}";
    	}
    	
    	/**
    	 * 返回方法类型，为上面定义的几种类型之一。
    	 * @return
    	 */
    	public int getMethodType() {
    		return METHOD_TYPE_GENERIC_GET;
    	}
    }

    /**
     * PropertyDelegate - 实现 Bean 一般属性(PropertyDescriptor)方法的包装。
     */
    private static final class PropertyDelegate extends MethodDelegateBase {
    	PropertyDelegate(String prop_name, Method read_method, Method write_method) {
    		super(prop_name, read_method, write_method);
    	}
    	
    	public String toString() {
    		return "Property{name=" + super.name + 
    			(super.read_method == null ? ",can't read" : "") + "}";
    	}
    	
    	/**
    	 * 返回方法类型，为上面定义的几种类型之一。
    	 * @return
    	 */
    	public int getMethodType() {
    		return METHOD_TYPE_PROPERTY;
    	}
    }

    /**
     * IndexedPropertyDelegate - 实现索引属性(IndexedProperty)方法的包装。
     */
    private static final class IndexedPropertyDelegate extends MethodDelegateBase {
    	IndexedPropertyDelegate(String prop_name, Method read_method, Method write_method) {
    		super(prop_name, read_method, write_method);
    	}
    	
    	public String toString() {
    		return "IndexedProperty{name=" + super.name + 
    		(super.read_method == null ? ",can't read" : "") + "}";
    	}
    	
    	/**
    	 * 返回方法类型，为上面定义的几种类型之一。
    	 * @return
    	 */
    	public int getMethodType() {
    		return METHOD_TYPE_INDEXED_PROPERTY;
    	}
    }

    /**
     * SingleMethodDelegate - 实现对单个方法的委托包装。
     */
    private static final class SimpleMethodDelegate extends MethodDelegateBase {
    	SimpleMethodDelegate(String method_name, Method method) {
    		super(method_name, method, null);
    	}
    	
    	@Override public String toString() {
    		return String.valueOf(super.read_method);
    	}
    	
    	/**
    	 * 返回方法类型，为上面定义的几种类型之一。
    	 * @return
    	 */
    	public int getMethodType() {
    		return METHOD_TYPE_METHOD;
    	}
    }

    /**
     * OverloadedMethodDelegate - 实现对多个同名重载函数的委托包装。
     */
    private static final class OverloadedMethodDelegate implements MethodDelegate {
    	private final OverloadedMethodMap method_map;
    	
    	public OverloadedMethodDelegate(String method_name) {
    		this.method_map = new OverloadedMethodMap(method_name);
    	}
    	
    	public String toString() {
    		return "OverloadedMethod{name=" + method_map.getName() + ",count=" + method_map.size() + "}";
    	}
    	
    	/** 添加一种方法，其方法签名和以前添加的有所不同。 */
    	void addMethod(Method method) {
    		this.method_map.addMethod(method);
    	}
    	
    	/**
    	 * 返回方法类型，为上面定义的几种类型之一。
    	 * @return
    	 */
    	public int getMethodType() {
    		return METHOD_TYPE_OVERLOADED_METHOD;
    	}
    	
    	/** 
    	 * 使用指定的参数在这个方法上产生 get 调用，对于 generic-get, property, 
    	 *   indexed-property, method, overloaded-method 都适用。
    	 * @param target - 调用目标对象。
    	 * @param args - 参数。
    	 */
    	public Object invoke(Object target, Object... args) 
    			throws InvocationTargetException, IllegalAccessException {
    		AccessibleObject acc_obj = this.method_map.getMostSpecific(args);
    		if (acc_obj == null) throw new RuntimeException("没有找到具有指定参数列表的可调用函数。");
    		// 这个对象一定是一个方法，现在用所给参数调用它。
    		return ((Method)acc_obj).invoke(target, args);
    	}
    	
    	/**
    	 * 获得此方法或属性的名字。某些类型的方法可能没有名字。
    	 * @return
    	 */
    	public String getName() {
    		return this.method_map.getName();
    	}
    	
    	/**
    	 * 获得方法调用的返回类型。
    	 * @return
    	 */
    	public Class getReturnType() {
    		return this.method_map.getReturnType();
    	}
    }
}
