package com.chinaedustar.common.reflect;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import com.chinaedustar.common.util.ClassHelper;

/**
 * 提供非安全的函数名字集合。
 * 
 * @author liujunxing
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class UnsafeMethodSet {
	/** 非安全的方法集合。 */
	private static Set<Method> unsafe_method_set;
	
	private UnsafeMethodSet() {
		// 防止外部使用。
	}
	
	/**
	 * 获得非安全的方法集合，此方法集合描述在文件 unsafe_methods.txt 中。
	 * @return
	 */
	public static final Set<Method> getUnsafeMethodSet() {
		if (unsafe_method_set != null) return unsafe_method_set;
		unsafe_method_set = createUnsafeMethodsSet();
		return unsafe_method_set;
	}
	
	/** 从我们带的描述 unsafe_methods.txt 文件中读取属性集合。*/
	private static final Properties loadUnsafeProperties() throws java.io.IOException {
        InputStream in = UnsafeMethodSet.class.getResourceAsStream("unsafe_methods.txt");
        if (in == null) return null;
        try {
            Properties props = new Properties();
            props.load(in);
            return props;
        } finally {
            in.close();
        }
	}
	
    /** 初始化创建一个非安全的函数集合。 */
    private static final Set<Method> createUnsafeMethodsSet() {
        String methodSpec = null;
        try {
        	// 装载 unsafe_methods.txt -> Properties 中。
            Properties props = loadUnsafeProperties();
            if (props == null) return new HashSet<Method>();

            // 获得主要类型映射表。
            Map<String, Class> primClasses = PrimitiveClassesMap.getPrimitiveClassesMap();
            
            // 为每个条目解析其方法。
            HashSet<Method> set = new HashSet<Method>(props.size() * 4/3, .75f);
            for (Iterator iterator = props.keySet().iterator(); iterator.hasNext(); ) {
                methodSpec = (String) iterator.next();
                try {
                	Method method = parseMethodSpec(methodSpec, primClasses);
                	if (method != null)
                		set.add(method);
                } catch(ClassNotFoundException e) {
                    throw e;
                } catch(NoSuchMethodException e) {
                    throw e;
                }
            }
            
            return set;
        } catch(Exception e) {
            throw new RuntimeException("Could not load unsafe method " + methodSpec + " " + e.getClass().getName() + " " + e.getMessage());
        }
    }
    
    /** 从一行输入中解析一个方法的定义。 */
	private static Method parseMethodSpec(String methodSpec, Map<String, Class> primClasses)
    		throws ClassNotFoundException, NoSuchMethodException    {
        int brace = methodSpec.indexOf('(');
        int dot = methodSpec.lastIndexOf('.', brace);
        if (brace < 0 || dot <0) return null;
        
        Class clazz = ClassHelper.classForName(methodSpec.substring(0, dot));
        String methodName = methodSpec.substring(dot + 1, brace);
        String argSpec = methodSpec.substring(brace + 1, methodSpec.length() - 1);
        StringTokenizer tok = new StringTokenizer(argSpec, ",");
        int argcount = tok.countTokens();
        Class[] argTypes = new Class[argcount];
        for (int i = 0; i < argcount; i++) {
            String argClassName = tok.nextToken();
            argTypes[i] = primClasses.get(argClassName);
            if (argTypes[i] == null) {
                argTypes[i] = ClassHelper.classForName(argClassName);
            }
        }
        return clazz.getMethod(methodName, argTypes);
    }
}
