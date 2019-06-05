package com.chinaedustar.template.core;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.template.Writer;
import com.chinaedustar.template.ProcessEnvironment;
import com.chinaedustar.template.expr.Expression;
import com.chinaedustar.template.expr.ParameterList;

/**
 * 内部使用的执行环境接口。
 * 
 * @author liujunxing
 */ 
public interface InternalProcessEnvironment extends ProcessEnvironment {

	/**
	 * 获得当前环境的输出器。
	 * @return
	 */
	public Writer getOut();

	/**
	 * 在当前模板执行环境中访问指定的元素，如果其有子元素则按照其返回值可选遍历访问。
	 * @param elem - 要访问的元素。
	 * @param visitChild - 是否访问子节点。
	 * @param visitSibling - 是否访问兄弟元素。
	 */
	public void visit(AbstractTemplateElement elem, boolean visitChild, boolean visitSibling); 
	
	/**
	 * 执行指定元素的所有子元素，并将输出返回给调用者。输出不添加到整个模板输出中。
	 * @param elem
	 * @return 返回子节点执行产生的结果。
	 */
	public String processChild(AbstractTemplateElement elem);
	
	/**
	 * 请求解释指定的标签。
	 * @return
	 */
	public int interpreteLabel(AbstractLabelElement label);
	
	/**
	 * 求取指定表达式的值。
	 * @param expr - 所要求取的表达式。
	 * @return
	 */
	public Object calc(Expression expr);

	/**
	 * 解析一个表达式。
	 * @param str
	 * @return
	 */
	public Expression parseExpr(String str);
	
	/**
	 * 请求定义一个变量。
	 * @param var_name
	 * @param var_value
	 * @param scope
	 */
	public void declareVariable(String var_name, Object var_value, int scope);
	
	/**
	 * 请求查找一个变量。
	 * @param var_name
	 * @return
	 */
	public Object findVariable(String var_name);

	/**
	 * 请求查找循环变量 loop_var 的附加描述对象。
	 * @param loop_var
	 * @return 返回此对象对应的循环描述对象，如果没有则返回 null.
	 * 注意：当前的实现由于 DefaultProcessEnvironment 的 LocalContext
	 *   不能跨越作用域，所以如果将 loop_var 做为参数传递的时候可能导致找不到。
	 */
	public LoopVarDecorator findLoopVarDecorator(Object loop_var);
	
	/**
	 * 给指定名字的变量设置指定的值，如果变量不存在，则在当前 LocalContext 中创建一个新的变量并赋值。
	 *  注意：其仅能改变存储于 LocalContext 里面的变量，不影响全局变量。
	 * @param var_name
	 * @param val
	 */
	public void assignVariable(String var_name, Object val);
	
	/**
	 * 试图为对象 target 查找适用的 builtin 方法并执行。
	 * @param target
	 * @param method_name
	 * @param param_list
	 * @return
	 */
	public Object findInvokeBuiltinMethod(Object target, String method_name, Object[] param_list);
	
	/**
	 * 请求调用一个全局方法。
	 * @param method_name - 全局方法的名字。
	 * @param param_list - 调用方法的参数。
	 * @return
	 */
	public Object callGlobalMethod(String method_name, ParameterList param_list);

	/**
	 * 请求指定元素的本地执行环境，如果该环境未建立则建立一个。
	 * @param elem - 为此元素请求创建或获得局部执行环境。
	 * @param ctxt_attr - 此局部执行环境的属性，取值为 LocalContext.LOCAL_CONTEXT_XXX 的组合。
	 * @return
	 */
	public LocalContext acquireLocalContext(AbstractTemplateElement elem, int ctxt_attr);

	/**
	 * 获得对象包装器。
	 * @return
	 */
	public ObjectWrapper getObjectWrapper();
	
	/**
	 * 跳出当前 foreach 或 switch 循环。
	 * 此函数不返回。将通过特定异常抛出来变更程序执行路径。
	 */
	public void breakk();
	
	/**
	 * 使用指定的参数调用指定的子模板。
	 * @param child_template - 子模板的名字。
	 * @param para - 调用参数。
	 * @return 返回子模板执行输出的内容。
	 */
	public String call(String child_template, Object[] para);
	
	/**
	 * 弹出一个此模板被调用时的参数，如果没有了则返回 null。
	 * 此功能被 #param 指令调用，以匹配其定义的参数。
	 * @return
	 */
	public Object popParameter();
	
	/**
	 * 执行 foreach 循环。
	 * @param elem 标签元素。
	 * @param item_name 循环时放入LocalContext中的变量的名称。
	 * @param coll_obj 用来执行循环的对象。
	 * @param callback - 每次循环时候的回调，可以为 null.
	 */
	public void foreach(AbstractContainerElement label, String var_name, Object coll_obj, ForeachCallback callback);

	/**
	 * 计算参数。
	 * @param param_list
	 * @return
	 */
	public Object[] calcParameter(ParameterList param_list);
}
