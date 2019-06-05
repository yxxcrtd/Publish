package com.chinaedustar.template;

/**
 * 定义 模板使用的常量，主要包括元素处理返回常量、变量查找范围常量。
 * 
 * @author liujunxing
 */
public interface TemplateConstant {
	/* === 标签解释返回结果定义 ================================================= */
	
	/** 未知的标签，由 LabelInterpreter 返回表示其不识别该标签，将继续用下一个标签处理器进行处理。 */
	public static final int LABEL_UNKNOWN = -1;
	
	/* === 元素执行返回结果定义，标签也是元素，也可以返回这些值 ======================= */
	
	/** 继续处理，如果有子节点，则将处理子节点；否则处理兄弟节点。此为缺省结果。 */
	public static final int PROCESS_DEFAULT = 0;
	
	/** 执行兄弟节点，不执行子节点。 */
	public static final int PROCESS_SIBLING = 1;
	
	/** 重复执行此节点，用于 for, while 等标签的解释。 */
	public static final int PROCESS_REPEAT = 2;
	
	/** 返回，用于从子模板中返回到调用者模板中。 */
	public static final int PROCESS_RETURN = 3;
	
	/** 跳出 switch, for, while 等控制，执行其下一个元素。 */
	public static final int PROCESS_BREAK = 4;
	
	/** 停止模板解析，产生一个指定的错误信息。 */
	public static final int PROCESS_STOP = 5;
	
	/* === 变量范围定义 ========================================================= */
	
	/** 没有任何范围。 */
	public static final int SCOPE_NONE = 0;
	
	/** 局部范围变量，局部范围只能在其执行元素期间被访问到。此为缺省值。 */
	public static final int SCOPE_LOCAL = 100;
	
	/** 模板级范围，此变量能够在这个模板解析期间被访问到。 */
	public static final int SCOPE_TEMPLATE = 200;
	
	/** 执行级范围，此变量能够在整个执行期间被访问到，执行可能跨多个模板。 */
	public static final int SCOPE_PROCESS = 300;

	/** 页面范围，对应于 pageContext。 */
	public static final int SCOPE_PAGE = 400;

	/** 请求范围，对应于 request。 */
	public static final int SCOPE_REQUEST = 500;

	/** 会话范围，对应于 session。 */
	public static final int SCOPE_SESSION = 600;

	/** 应用范围，此变量能够在整个应用期间被访问到。 */
	public static final int SCOPE_APPLICATION = 700;

	/** 全局范围，此变量能够在整个进程期间被访问到。 */
	public static final int SCOPE_GLOBAL = 800;
	
	/** 所有范围。 */
	public static final int SCOPE_ALL = Integer.MAX_VALUE;
	
	// 范围 page, request, session 需要有 JSP 环境的支持。global 可选由缺省系统支持。
	// 也可以定义自己的范围，然后由 VariableContainer 来负责支持。
}
