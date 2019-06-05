package com.chinaedustar.rtda.model;

/**
 * 定义可以通过枚举方式访问集合中的数据的数据访问模型。
 * 
 * @author liujunxing
 */
public interface CollectionDataModel extends DataModel {
    /**
     * 获得一个枚举器用于访问此集合中的所有数据。此枚举器一般实现为只读枚举器。
     */
    @SuppressWarnings("rawtypes")
	public java.util.Iterator iterator();
}
