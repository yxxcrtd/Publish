package com.chinaedustar.rtda.model;

import com.chinaedustar.rtda.OperatorConstant;

/**
 * 支持运算子重载的数据访问模型。
 * 
 * @author liujunxing
 */
public interface OperatorOverrideModel extends DataModel, OperatorConstant {
	/**
	 * 获得是否支持指定类型运算子。标准运算子包括 +, -, *, / 等。
	 * @param oper - 运算子编码。
	 * @return
	 */
	public boolean isSupportOperator(String oper);

	/**
	 * 执行指定的运算子。
	 * @param oper - 运算子。
	 * @param right_value - 右值。
	 * @return - 返回运算结果。
	 */
	public Object executeOperator(String oper, Object right_value);
}
