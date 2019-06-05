package com.chinaedustar.rtda.model;

/**
 * 定义字符串访问数据模型。
 * 
 * <p>其同时也能支持对单个字符的访问，因此同时最好也实现 SequenceDataModel。</p>
 * 
 * @author liujunxing
 */
public interface StringDataModel extends ScalarDataModel {
    /**
     * 返回此数据的字符串表示。一般而言不要返回 null。
     */
    public String getAsString();
}
