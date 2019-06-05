package com.chinaedustar.common.bean;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Set;

import com.chinaedustar.common.reflect.UnsafeMethodSet;

/**
 * 实际的类信息提取器。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
final class ActualClassInfoExtractor {

    /** 非安全的函数集合。 */
    private static final Set<Method> UNSAFE_METHODS = UnsafeMethodSet.getUnsafeMethodSet();

    /** 信息提取等级。 */
    private final int extract_level;
    
    /**
     * 使用指定的提取等级参数构造 ActualClassInfoExtractor 的实例。
     * @param extract_level
     */
    public ActualClassInfoExtractor(int extract_level) {
    	this.extract_level = extract_level;
    }
    
    /**
     * 提取类可用信息。
     * @return
     */
    public final ClassInfo extract(Class clazz) {
    	return populateClassInfo(clazz);
    }
    
	// === 实现部分 ====================================================================

    /**
     * Populates a map with property and method descriptors for a specified
     * class. If any property or method descriptors specifies a read method
     * that is not accessible, replaces it with appropriate accessible method
     * from a superclass or interface.
     */
    private ClassInfo populateClassInfo(Class clazz) {
        // Populate first from bean info
    	ClassInfo classInf = populateClassMapWithBeanInfo(clazz);
        
        // Next add constructors - 构造函数对于我们暂时没有用，反而增加复杂度，暂时不使用。
        // populateConstructos(clazz, classInf);

        // 优化一下集合。
        return optimizeClassMap(classInf);
    }
    
    /**
     * 根据 BeanInfo 获取类的信息。
     */
    /** Populate first from bean info */
	private ClassInfo populateClassMapWithBeanInfo(Class clazz) {
    	ClassInfo classInf = new ClassInfo(clazz);
        
        // 1、 建立这个类可被访问的公共方法集合。
        ClassAccessibleMethods pub_methods = new ClassAccessibleMethods(clazz);
        
        // 2、查找具有 get(string), get(object) 的公共方法做为 generic-get 方法看待。
        discoverGenericGet(pub_methods, classInf);
        
        // EXPOSE_NOTHING 仅提供 generic-get 方法给外面用。
        if (extract_level == ClassInfoExtractor.EXTRACT_NOTHING) return classInf;
        
        try {
        	// 获得 BeanInfo，并从该信息里面内省所需方法。
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            // 3、从 Bean 的 Property 描述中找到方法，并放到 ClassMap 中。
            discoverBeanProperties(classInf, pub_methods, beanInfo);

            if (extract_level < ClassInfoExtractor.EXTRACT_PROPERTIES_ONLY) {
            	// 4、从 Method 中。
            	discoverBeanMethods(classInf, pub_methods, beanInfo);
            } else {
                // 发现具有 '_builtin_', '_safe_' 开始名字的方法，做为内建方法看待。
                discoverBuiltinFunc(classInf, pub_methods, beanInfo);
            }
            
            return classInf;
        } catch(IntrospectionException e) {
            return ClassInfo.EMPTY_CLASS_INFO;
        }
    }

    /**
     * 发现类里面的内建方法，其标记为 _builtin_Xxx 方法。
     *
     */
    private void discoverBuiltinFunc(ClassInfo classInf, ClassAccessibleMethods pub_methods, BeanInfo beanInfo) {
    	// pub_methods.discoverBuiltin(classInf);
        MethodDescriptor[] mda = beanInfo.getMethodDescriptors();
        for (int i = mda.length - 1; i >= 0; --i) {
        	// 获得这个属性的读取方法，然后在对象的公开方法签名表(pub_methods)里面找具有此名称和签名的相同的函数。
            MethodDescriptor md = mda[i];
            Method publicMethod = pub_methods.getAccessibleMethod(md.getMethod());
            if (publicMethod == null || isSafeMethod(publicMethod) == false) continue;
            
            // 寻找 _builtin_ 方法。
    		String method_name = publicMethod.getName();
    		if (method_name.startsWith("_builtin_") || method_name.startsWith("_safe_")) {
                // 将找到的这个方法加入到 ClassInfo 中。
                classInf.putNamedMethod(method_name, publicMethod);
    		}
        }
    }
    
    /**
     * 将找到的方法里面的具有签名 get(string) 或 get(object) 作为 generic-get 方法加入到 classInfo 中。
     */
    private static final void discoverGenericGet(ClassAccessibleMethods pub_methods, ClassInfo classInf) {
    	Method genericGet = pub_methods.getGenericGetMethod();
        if (genericGet != null) {
        	// ClassMap<Special-Key(Object), Method>, can implement as a member var
        	classInf.putGenericGetMethod(genericGet);
        }
    }

    /**
     * 根据 BeanInfo 中的属性描述发现所需方法。
     * @param classMap
     * @param methodMap
     * @param beanInfo
     */
    private final void discoverBeanProperties(ClassInfo classInf, ClassAccessibleMethods methodMap, BeanInfo beanInfo) {
        PropertyDescriptor[] pda = beanInfo.getPropertyDescriptors();
        // ?? 为什么要反序循环呢 ??
        for (int i = pda.length - 1; i >= 0; --i) {
            PropertyDescriptor pd = pda[i];
            if (pd instanceof IndexedPropertyDescriptor) {
            	discoverIndexedProperty(classInf, methodMap, (IndexedPropertyDescriptor)pd);
            } else {
                discoverGeneralProperty(classInf, methodMap, pd);
            }
        }
    }
    
    /**  
     * 发现了 IndexedProerty 描述，处理它。
     */
    private final void discoverIndexedProperty(ClassInfo classInf, ClassAccessibleMethods pub_methods, 
    		IndexedPropertyDescriptor ipd) {
    	// 获得索引属性的读取方法，并验证是否公共可访问和安全。
        Method read_method = pub_methods.getAccessibleMethod(ipd.getIndexedReadMethod());
        if (read_method == null || isSafeMethod(read_method) == false) return;
        
        // ClassMap<String, IndexedPropertyDescriptor>
        classInf.putIndexProperty(ipd.getName(), read_method, ipd.getIndexedWriteMethod());
        
        // Map<Method, Class[]>
        // ?? 什么用途呢 ?? classInf.putArgTypes(read_method, read_method.getParameterTypes());
    }

    /**
     * 发现了一般　Property 描述，处理它。
     * @param classMap
     * @param pub_methods
     * @param pd
     */
    private final void discoverGeneralProperty(ClassInfo classInf, ClassAccessibleMethods pub_methods, PropertyDescriptor pd) {
    	// 获得这个属性的读取方法，然后在对象的公开方法签名表(pub_methods)里面找具有此名称和签名的相同的函数。
        Method read_method = pub_methods.getAccessibleMethod(pd.getReadMethod());
        if (read_method == null || isSafeMethod(read_method) == false) return;
        
        // 如果找到，并且认为方法是可靠的(非 wait(), notify() 函数)，则添加为一个属性信息。
        classInf.putProperty(pd.getName(), read_method, pd.getWriteMethod());
    }

    /**
     * 根据 BeanInfo 中方法描述发现所需方法。
     * @param classMap
     * @param methodMap
     * @param beanInfo
     */
    private final void discoverBeanMethods(ClassInfo classInf, ClassAccessibleMethods pub_methods, BeanInfo beanInfo) {
        MethodDescriptor[] mda = beanInfo.getMethodDescriptors();
        for (int i = mda.length - 1; i >= 0; --i) {
        	// 获得这个属性的读取方法，然后在对象的公开方法签名表(pub_methods)里面找具有此名称和签名的相同的函数。
            MethodDescriptor md = mda[i];
            Method publicMethod = pub_methods.getAccessibleMethod(md.getMethod());
            if (publicMethod == null || isSafeMethod(publicMethod) == false) continue;

            // 将找到的这个方法加入到 ClassInfo 中。
            classInf.putNamedMethod(md.getName(), publicMethod);
        }
    }

    /**
     * 内省出类的构造函数。
     * @param clazz
     * @param map
     */
    @SuppressWarnings("unused")
	private final void populateConstructos(Class clazz, ClassInfo classInf) {
    	throw new UnsupportedOperationException("populateConstructos 当前没有实现");
    	/*
        try {
            Constructor[] ctors = clazz.getConstructors();
            if (ctors.length == 1) {
            	Constructor ctor = ctors[0];
                classInf.putConstructor(ctor);
                classInf.putArgTypes(ctor, ctor.getParameterTypes());
            }
            else if(ctors.length > 1) {
                MethodMap ctorMap = new MethodMap("<init>");
                for (int i = 0; i < ctors.length; i++) {
                    ctorMap.addConstructor(ctors[i]);
                }
                classInf.putConstructors(ctorMap);
            }
        }
        catch(SecurityException e) {
            logger.warn("Canont discover constructors for class " + 
                    clazz.getName(), e);
        }
        */
    }

    // 某种优化措施，我们暂时先不使用，因为其引用了 Collections12
    private final ClassInfo optimizeClassMap(ClassInfo classInf) {
        /* 
        switch(map.size()) {
            case 0: {
                map = Collections12.EMPTY_MAP;
                break; 
            }
            case 1: {
                Map.Entry e = (Map.Entry)map.entrySet().iterator().next();
                map = Collections12.singletonMap(e.getKey(), e.getValue());
                break;
            }
        } */
    	return classInf;
    }
  
    /** 判定一个方法是否安全。 */
    private final boolean isSafeMethod(Method method) {
        return (this.extract_level < ClassInfoExtractor.EXTRACT_SAFE) || 
        	(!UNSAFE_METHODS.contains(method));
    }
}
