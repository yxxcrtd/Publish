package com.chinaedustar.template.core;

import com.chinaedustar.template.ProcessEnvironment;
import com.chinaedustar.template.TemplateConstant;
import com.chinaedustar.template.Writer;

/**
 * 表示一个执行子环境。
 * 
 * @author liujunxing
 */
public class LocalContext implements TemplateConstant {
	/** 表示这是一个根执行模板。 */
	public static final int LOCAL_CONTEXT_ROOT_TEMPLATE = 0x0001;
	
	/** 表示这是一个子执行模板。 */
	public static final int LOCAL_CONTEXT_CHILD_TEMPLATE = 0x0002;
	
	/** 表示这个局部环境可以使用 #break 指令跳出，通常是 #switch, #foreach 指令建立此局部环境。 */
	public static final int LOCAL_CONTEXT_BREAKABLE = 0x0004;
	
	/** 表示这个局部环境可以使用 #return 指令跳出，通常是子模板建立此局部环境。 */
	public static final int LOCAL_CONTEXT_RETURNABLE = 0x0008;
	
	/** 表示这个局部环境将 #catch 某些异常，通常是 #try 指令建立此局部环境。 */
	public static final int LOCAL_CONTEXT_CATCHABLE = 0x0010;
	
	
	/** 表示一个根执行模板的所有属性。 */
	public static final int ROOT_TEMPLATE = LOCAL_CONTEXT_ROOT_TEMPLATE |
			LOCAL_CONTEXT_CHILD_TEMPLATE | LOCAL_CONTEXT_BREAKABLE | 
			LOCAL_CONTEXT_RETURNABLE;
	
	/** 表示一个子执行模板的所有属性。 */
	public static final int CHILD_TEMPLATE = LOCAL_CONTEXT_CHILD_TEMPLATE | 
			LOCAL_CONTEXT_BREAKABLE | LOCAL_CONTEXT_RETURNABLE;

	/** 所在的执行环境。 */
	private final ProcessEnvironment env;
	
	/** 此环境为这个节点建立的，如果 elem == null 表示是根节点。 */
	private final AbstractTemplateElement elem;
	
	/** 此执行环境的属性，取值为 LOCAL_CONTEXT_XXX 的组合。 */
	private final int ctxt_attr;
	
	/** 输出器。 */
	private StringWriter out;
	
	/** 局部变量容器。 */
	private java.util.Map<Object, Object> local_vars; 

	/** 为了支持 #foreach 指令，额外添加的循环变量对象。*/
	private Object loop_var;
	
	/** 循环变量的修饰对象，用于提供 @index, @is_first 等内建函数。 */
	private LoopVarDecorator loop_var_decorator;

	/**
	 * 使用指定的执行环境构造一个 LocalContext 的实例。
	 * @param env - 执行环境。
	 * @param elem - 为此元素构造的执行环境。
	 * @param ctxt_attr - 此执行环境的属性，取值为 LOCAL_CONTEXT_XXX 的组合。
	 */
	public LocalContext(ProcessEnvironment env, AbstractTemplateElement elem, int ctxt_attr) {
		this.env = env;
		this.elem = elem;
		this.ctxt_attr = ctxt_attr;
	}
	
	/**
	 * 获得此本地指令环境所在的执行环境。
	 * @return
	 */
	public ProcessEnvironment getEnv() {
		return this.env;
	}
	
	/**
	 * 获得此本地环境为哪个元素建立的。
	 * @return
	 */
	public AbstractTemplateElement getElement() {
		return this.elem;
	}
	
	/**
	 * 获得此执行环境的属性，取值为 LOCAL_CONTEXT_XXX 的组合。
	 * @return
	 */
	public int getContextAttribute() {
		return this.ctxt_attr;
	}
	
	/**
	 * 获得输出器。
	 * @return
	 */
	public Writer getOut() {
		if (this.out == null)
			this.out = new StringWriter();
		return this.out;
	}

	public void clearOutput() {
		out.strbuf = new StringBuilder();
	}
	
	/**
	 * 将指定 LocalContext 的输出合并到当前 LocalContext 中。
	 * @return - 返回自己。
	 */
	public LocalContext appendOutput(LocalContext local_ctxt) {
		if (local_ctxt.out == null)		// 没有输出要合并。 
			return this;
		if (this.out == null) {			// 直接使用子的输出对象。
			this.out = local_ctxt.out;
			local_ctxt.out = null;
			return this;
		}
		
		// 两者皆不为空。
		this.out.append(local_ctxt.out);
		return this;
	}
	
	/**
	 * 在此局部执行环境中查找具有指定名字的变量。
	 * @param var_name
	 * @return
	 */
	public Object findVariable(String var_name) {
		if (local_vars == null) return null;
		return local_vars.get(var_name);
	}
	
	/**
	 * 在此局部执行环境中添加或更新具有指定名字的变量。
	 * @param var_name
	 * @param value
	 */
	public void setVariable(String var_name, Object value) {
		if (local_vars == null)
			local_vars = new java.util.HashMap<Object, Object>();
		local_vars.put(var_name, value);
	}

	/**
	 * 在此局部执行环境中更新具有指定名字的变量，如果变量不存在则返回 false。
	 * @param var_name
	 * @param value
	 * @return 变量如果存在则返回 true 并更新了其值；否则返回 false.
	 */
	public boolean assignVariable(String var_name, Object value) {
		// 如果变量不存在则返回 false.
		if (local_vars == null) return false;
		if (local_vars.containsKey(var_name) == false) return false;
		
		// 修改变量值，并返回 true.
		local_vars.put(var_name, value);
		return true;
	}
	
	/**
	 * 判断此局部环境是否是 break 终止的。
	 * @return
	 */
	public boolean isBreakable() {
		return ((this.ctxt_attr & LOCAL_CONTEXT_BREAKABLE) != 0);
	}

	/**
	 * 设置一个循环变量对象。
	 * @param var_name
	 * @param loop_var
	 */
	void setLoopVariable(String var_name, Object loop_var, LoopVarDecorator loop_var_decorator) {
		this.setVariable(var_name, loop_var);
		this.loop_var = loop_var;
		this.loop_var_decorator = loop_var_decorator;
	}
	
	/**
	 * 查找指定循环变量的修饰对象，如果没有则返回 null.
	 * @param loop_var
	 * @return
	 */
	public LoopVarDecorator findLoopVarDecorator(Object loop_var) {
		if (this.loop_var == loop_var)
			return this.loop_var_decorator;
		else
			return null;
	}
}
