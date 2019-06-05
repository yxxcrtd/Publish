package com.chinaedustar.rtda.bean;

import java.util.Map;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.*;

/**
 * 对 Map 进行包装的访问模型实现。
 * 
 * <p>一个标准 Map 提供集合访问能力，也即通过 iterator() 返回访问此集合的能力。</p>
 * 
 * @author liujunxing
 *
 */
@SuppressWarnings("rawtypes")
public class MapModelImpl extends BeanModelImpl<Map> 
		implements HashDataModel, HashDataModel2, CollectionDataModel {
	/**
	 * 使用指定的具有 Map 接口的目标对象和对象包装器构造一个 MapModelImpl 的实例。
	 */
	public MapModelImpl(Map target, ObjectWrapper wrapper) {
		super(target, wrapper);
	}

	// === HashDataModel 接口实现 ==============================================
	
    /**
     * 获得指定键的数据。实现者尽量返回 DataModel 接口的数据，这将使数据访问更便利。
     *
     * @param key - 要访问的属性名称。
     * @return - 返回 null 表示没有；否则为找到的属性值。
     */
    @Override public Object get(String key) {
    	// 先从 map 里面查找，如果没有让基类查找。
    	if (target.containsKey(key))
    		return target.get(key);

    	// 查找基类的。
    	return super.get(key);
    }

	// === HashDataModel2 接口实现 =============================================

	/**
	 * 判定集合是否为空。
	 * @return
	 */
	public boolean isEmpty() {
		return super.target.isEmpty();
	}
	
	/**
	 * 获得所有键的集合，一般返回一个 CollectionDataModel 的实现。
	 * @return
	 */
	public Object keys() {
		return target.keySet();
	}
	
	/**
	 * 返回所有值的集合，一般返回一个 CollectionDataModel 的实现。
	 * @return
	 */
	public Object values() {
		return target.values();
	}

    // === CollectionDataModel 接口实现 ========================================
    
    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
    public java.util.Iterator iterator() {
    	return ((Map)super.target).values().iterator();
    }

    // === BuiltinDataModel 接口实现 ===========================================
    
    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_map".equals(method_name))
			return true;
		else if ("is_empty".equals(method_name))
			return target.size() == 0;
		else if ("size".equals(method_name))
			return target.size();
		else if ("contains".equals(method_name)) {
			if (param_list == null || param_list.length == 0) return false;
			return target.containsValue(param_list[0]);
		}
		
		return super.builtin(method_name, param_list);
	}
}
