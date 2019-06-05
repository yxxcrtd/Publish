package com.chinaedustar.rtda.model;

import com.chinaedustar.rtda.simple.NumberData;

/**
 * 定义数字的访问数据模型接口。
 * 
 * @author liujunxing
 */
public interface NumberDataModel extends ScalarDataModel {
    /**
     * 返回此数据代表的数字值。尽量不要返回 null. 
     */
    public Number getAsNumber();
    
    /**
     * 0 的实现。
     */
    public static final NumberDataModel ZERO = new NumberData(new Integer(0));
}
