package com.chinaedustar.template;

/**
 * 定义变量查找器的接口，模板执行引擎通过此接口找到变量。
 * 
 * <p>变量查找器在内存中构成一个堆栈，如下图示：
 * <pre>
 *   LocalContextVariableResolver - 局部变量容器，在一个函数中用于查找局部变量或参数值
 *       如果调用了多层函数或模板，则可能有多个这个容器。此容器放在 LocalContext 中的。
 *       
 *   TemplateVariableResolver - 模板级的变量容器。
 *   PageVariableResolver - 页面级变量容器。(以下 4种 由JSP系统提供)
 *     RequestVariableResolver - 请求级变量查找器。
 *     SessionVariableResolver - 回话级变量查找器。
 *     ApplicationVariableResolver - 应用程序级变量查找器。(可以合并为一个查找器)
 *   GlobalVariableResolver - 全局变量查找器。
 * </pre>
 * 
 * 当查找的时候，先从表的最上一个开始，如果找到则返回对象；否则返回 null。
 *   返回 null 将继续向下查找直到找到一个非空的对象或者搜索到最后一个查找器。
 * </p>
 * 
 * @author liujunxing
 *
 */
public interface VariableResolver extends TemplateConstant {
	/** 如果找到了变量，但是变量值为空，可以返回这个值来代表空。 */
	public static final Object EMPTY = new Object();
	
	/**
	 * 查找具有名字为 name 的变量。
	 * @param name
	 * @return - 返回 null 表示没有找到；返回
	 */
	public Object resolveVariable(String name);
	
	/**
	 * 获得父变量查找器。
	 * @return
	 */
	public VariableResolver getParentResolver();
	
	/**
	 * 设置父变量查找器。
	 * @param vr
	 */
	public void setParentResolver(VariableResolver vr);
}
