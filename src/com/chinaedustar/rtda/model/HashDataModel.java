package com.chinaedustar.rtda.model;

import com.chinaedustar.rtda.simple.NullData;

/**
 * Hash 数据模型表示实现此接口的数据，能够通过　key-value 对的方法访问到其属性。
 *
 * <p>
 * 从运算的角度看，Hash 数据模型支持 '.' 运算子，即获取此对象的具有指定名字的属性。
 * 这个属性可以是一个标量 (Scalar)，也可以是另一个复杂对象如方法等。
 * </p>
 * 
 * @author liujunxing
 */
public interface HashDataModel extends DataModel {
	/** 表示一个不存在属性，比返回 null 能获知更多信息。 */
	public static final DataModel UNEXIST = NullData.UNEXIST;
	
    /**
     * 获得指定键的数据。
     * <p>实现者可以尽量返回 DataModel 接口的数据，这将使后续的数据访问便利一些。</p>
     * 
     * @param key - 要访问的属性名称。
     * @return - 属性值。
     */
    public Object get(String key);
    
    // TODO: 为了测试方便，将能够使用的信息都展现出来的一个接口，信息来自于 ClassInfo.
}
