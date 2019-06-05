package com.chinaedustar.rtda.model;

/**
 * 定义可以通过数字索引访问数据及获取队列大小的数据模型。
 * 
 * @author liujunxing
 */
public interface SequenceDataModel extends DataModel {
    /**
     * 访问指定索引的数据。
     * @return - 返回指定索引的数据。尽量返回实现了 DataModel 的数据。
     */
    public Object indexor(Object index);

    /**
     * @return - 返回数据项的数量，此为可选实现，如果不支持可以抛出异常。
     */
    public int size();
}
