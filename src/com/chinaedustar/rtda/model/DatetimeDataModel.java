package com.chinaedustar.rtda.model;

import java.util.Date;

/**
 * 定义日期型数据访问模型。
 * 
 * @author liujunxing
 */
public interface DatetimeDataModel extends ScalarDataModel {
    /**
     * 返回此数据表示的日期值。
     */
    public Date getAsDate();
}
