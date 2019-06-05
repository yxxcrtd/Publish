package com.chinaedustar.rtda.model;

/**
 * 实现此接口的对象访问模型表示可以执行一个方法调用。
 * 
 * <p>
 * 从运算的角度看，支持此数据访问模型的对象支持运算子 '()' - 函数子。
 * 也即在此对象上面执行一个函数调用的能力。example: user(3)。
 * </p>
 * 
 * @author liujunxing
 */
public interface MethodDataModel extends DataModel {
    /**
     * 能够执行一个方法调用。参数在传递前已经计算出来了。
     * @return - 返回计算的结果, 或者空。
     */
    public Object functor(Object[] args);
    
    /**
     * 获得此方法的名字。此方法是可选实现的。
     * @return
     */
	public String getMethodName();
	
	/**
	 * 获得此方法的返回类型。此方法是可选实现的。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getReturnType();
}
