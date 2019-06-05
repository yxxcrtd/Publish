package com.chinaedustar.rtda.model;

import com.chinaedustar.rtda.simple.BooleanData;

/**
 * 定义布尔型量的数据访问模型。
 * 
 * @author liujunxing
 */
public interface BooleanDataModel extends ScalarDataModel {
    /**
     * @return - 返回按照 boolean 量来解释此数据的值。
     */
    public boolean getAsBoolean();

	/** true 的实现。 */
	public static final BooleanDataModel TRUE = new BooleanData(true);
	
	/** false 的实现。 */
	public static final BooleanDataModel FALSE = new BooleanData(false);
}
