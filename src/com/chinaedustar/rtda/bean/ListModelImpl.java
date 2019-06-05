package com.chinaedustar.rtda.bean;

import java.util.List;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.CollectionDataModel;
import com.chinaedustar.rtda.model.SequenceDataModel;
import com.chinaedustar.rtda.oper.MathCalculator;

/**
 * 对 List 进行包装，其支持 seq, coll 两种访问模式。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class ListModelImpl extends BeanModelImpl<List> 
		implements SequenceDataModel, CollectionDataModel {
	/**
	 * 使用指定的目标对象和对象包装器构造一个 ListModelImpl 的实例。
	 * @param target
	 * @param wrapper
	 */
	public ListModelImpl(List target, ObjectWrapper wrapper) {
		super(target, wrapper);
	}
	
	// === SequenceDataModel 接口实现 ===========================================
	
    /**
     * 访问指定索引的数据。
     * @return - 返回指定索引的数据。尽量返回实现了 DataModel 的数据。
     */
    public Object indexor(Object index) {
    	int i_idx = MathCalculator.getIntegerValue(index);
    	return target.get(i_idx);
    }

    /**
     * @return - 返回数据项的数量，此为可选实现，如果不支持可以抛出异常。
     */
    public int size() {
    	return target.size();
    }

    // === CollectionDataModel 接口实现 ========================================

    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
    public java.util.Iterator iterator() {
    	return target.iterator();
    }

    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_empty".equals(method_name))
			return size() == 0;
		else if ("size".equals(method_name))
			return size();
		else if ("contains".equals(method_name)) {
			if (param_list == null || param_list.length == 0) return false;
			return target.contains(param_list[0]);
		}
		
		return super.builtin(method_name, param_list);
	}
}
