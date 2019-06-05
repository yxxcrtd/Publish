package com.chinaedustar.rtda;

import com.chinaedustar.common.bean.ClassInfo;
import com.chinaedustar.rtda.model.DataModel;

/**
 * 定义能够知道如何包装一个对象为 DataModel 访问模型的接口。
 *
 * <p>此接口的实现者能够将一个对象包装为 DataModel 模型的方法。</p>
 */
@SuppressWarnings("rawtypes")
public interface ObjectWrapper {
    /**
     * 返回对指定对象包装之后的一个对象。
     * @param target - 要进行包装的对象。
     */
    public DataModel wrap(Object target);
    
    /**
     * 对指定对象解除包装。
     * @param target
     * @return
     */
    public Object unwrap(Object target);
    
    /**
     * 获得指定的类的可访问属性、方法的信息。
     * @param clazz - 类型。
     * @return - 返回 ClassInfo, 指明哪些属性方法可以访问。
     */
	public ClassInfo getClassInfo(Class clazz);
}
