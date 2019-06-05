package com.chinaedustar.rtda.bean;

import java.util.*;
import com.chinaedustar.common.Cache;
import com.chinaedustar.common.SimpleCache;
import com.chinaedustar.common.bean.ClassInfo;
import com.chinaedustar.common.bean.ClassInfoExtractor;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.*;
import com.chinaedustar.rtda.simple.NullData;

/**
 * 使用 bean 自省方法包装数据的包装器。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class BeanObjectWrapper implements ObjectWrapper {
	/** 当对 null 进行包装的时候返回的对象。 */
	protected DataModel null_data = NullData.NULL;
	
	/** 用于缓存 ClassInfo 的缓存。 */
	protected Cache class_cache = new SimpleCache();
	
	/** 已经包装过的对象缓存。 */
	protected Cache wrapped_cache = new SimpleCache();
	
	/** 提取信息的等级，缺省只获取 bean 的 property. */
	private int extract_level = ClassInfoExtractor.EXTRACT_PROPERTIES_ONLY;
	
	// === 构造 ================================================================
	
	/**
	 * 缺省构造函数。
	 *
	 */
	public BeanObjectWrapper() {
		this(null);
	}

	/**
	 * 使用指定的 class_cache 构造一个 BeanObjectWrapper 的实例。
	 * @param class_cache - 指定的 class_cache.
	 */
	public BeanObjectWrapper(Cache class_cache) {
		if (class_cache != null) {
			this.class_cache = class_cache;
		}
	}

	// === ObjectWrapper 接口实现 ==============================================
	
    /**
     * 返回对指定对象包装之后的一个对象。
     */
	public DataModel wrap(Object object) {
    	// 空对象包装为空。
        if (object == null) return null_data;
        // 已经包装过了，则不用重复包装了。
        if (object instanceof DataModel) return (DataModel)object;
        // 此对象被修饰了，返回对内部对象的包装。
        if (object instanceof DataDecorator) {
        	return ((DataDecorator)object)._wrapObject(this);
        }
        
        /* TODO:
        if (object instanceof TemplateModelAdapter)
            return ((TemplateModelAdapter)object).getTemplateModel();
        */
        
        // 在 model_cache 里面查找，如果已经包装过并且放到了 cache 中则不用重复包装。
    	DataModel wrapped_obj = (DataModel)this.wrapped_cache.get(object);
        if (wrapped_obj != null) return wrapped_obj;
        
        if (object instanceof Map) {
            // 对 Map 进行包装，其支持 '.', '[]', #foreach 访问。
        	wrapped_obj = new MapModelImpl((Map)object, this);
        } else if (object instanceof List) {
        	// 具有 List 接口，其支持 [] 索引子访问，也支持 #foreach 访问。
        	wrapped_obj = new ListModelImpl((List)object, this);
        } else if (object instanceof Iterable) {
        	// 具有 Iterable 接口，其支持 #foreach 访问。
        	wrapped_obj = new CollectionModelImpl((Iterable)object, this);
        } else if (object.getClass().isArray()) {
        	// 对数组进行包装，其支持 [], #foreach 访问。
        	wrapped_obj = new ArrayModelImpl(object, this);
        } else if (object instanceof Number) {
        	// 数字。
        	return new NumberModelImpl((Number)object, this);
        } else if (object instanceof Boolean) {
        	// 布尔值。
        	return new BooleanModelImpl((Boolean)object, this);
        } else if (object instanceof Date) {
        	// 日期。
        	return new DatetimeModelImpl((Date)object, this);
        } else if (object instanceof Iterator) {
        	// 枚举器。
        	return new IteratorModelImpl((Iterator)object);
        } else if (object instanceof Enumeration) {
        	// 枚举器。
        	return new EnumerationModelImpl((Enumeration)object);
        } else if (object instanceof String) {
        	wrapped_obj = new StringModelImpl((String)object, this);
        } else {
        	wrapped_obj = new GeneralModelImpl(object, this);
        }
        
        this.wrapped_cache.put(object, wrapped_obj);
        return wrapped_obj;
        /*
        TODO: if(object instanceof TemplateModelAdapter)
            return ((TemplateModelAdapter)object).getTemplateModel();
        ** if(object instanceof Map)
            return modelCache.getInstance(object, simpleMapWrapper ? SimpleMapModel.FACTORY : MapModel.FACTORY);
        ** if(object instanceof Collection)
            return modelCache.getInstance(object, CollectionModel.FACTORY);
        ** if(object.getClass().isArray())
            return modelCache.getInstance(object, ArrayModel.FACTORY);
        ** if(object instanceof Number)
            return modelCache.getInstance(object, NumberModel.FACTORY);
        ** if(object instanceof Date)
            return modelCache.getInstance(object, DateModel.FACTORY);
        ** if(object instanceof Boolean) 
            return ((Boolean)object).booleanValue() ? TRUE : FALSE;
        if(object instanceof ResourceBundle)
            return modelCache.getInstance(object, ResourceBundleModel.FACTORY);
        ** if(object instanceof Iterator)
            return new IteratorModel((Iterator)object, this);
        ** if(object instanceof Enumeration)
            return new EnumerationModel((Enumeration)object, this);
        ** return modelCache.getInstance(object, StringModel.FACTORY);
         */
    }

    /**
     * 对指定对象解除包装。
     * @param target
     * @return
     */
    public Object unwrap(Object target) {
    	if (target == null) return null;
    	// 被修饰过的，则返回修饰前的值。
    	if (target instanceof DataDecorator)
    		return ((DataDecorator)target)._getOriginObject();
    	// 被包装过的，返回包装之前的值。
    	if (target instanceof DataModel)
    		return ((DataModel)target).unwrap();
    	// 返回自己。
    	return target;
    }

    /**
     * 获得指定的类的可访问属性、方法的信息。
     * @param clazz - 类型。
     * @return - 返回 ClassInfo, 指明哪些属性方法可以访问。
     */
    public ClassInfo getClassInfo(Class clazz) {
    	// 先尝试从缓存中找。
    	ClassInfo class_inf = (ClassInfo)class_cache.get(clazz);
    	if (class_inf == null) {
    		synchronized (class_cache) {
	    		// 没找到则现在分析这个类。
	    		class_inf = ClassInfoExtractor.getInstance()
	    			.extractClassInfo(clazz, extract_level);
	    		class_cache.put(clazz, class_inf);
    		}
    	}
    	return class_inf;
    }
}
