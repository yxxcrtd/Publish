package com.chinaedustar.template.core;

import java.util.Iterator;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.CollectionDataModel;
import com.chinaedustar.rtda.wrapper.DefaultObjectWrapper;
import com.chinaedustar.template.BuiltinFunction;
import com.chinaedustar.template.Template;
import com.chinaedustar.template.TemplateException;
import com.chinaedustar.template.TemplateFactory;
import com.chinaedustar.template.TemplateFinder;
import com.chinaedustar.template.VariableResolver;
import com.chinaedustar.template.Writer;
import com.chinaedustar.template.comp.AbstractExpressionCalculator;
import com.chinaedustar.template.comp.GlobalBuiltinContainer;
import com.chinaedustar.template.comp.GlobalVariableResovler;
import com.chinaedustar.template.comp.StandardVariableResolver;
import com.chinaedustar.template.comp.TemplateExpressionCalculator;
import com.chinaedustar.template.expr.Expression;
import com.chinaedustar.template.expr.ExpressionCalculator;
import com.chinaedustar.template.expr.ExpressionException;
import com.chinaedustar.template.expr.ExpressionParser;
import com.chinaedustar.template.expr.ParameterList;
import com.chinaedustar.template.token.TokenParser;

/**
 * 模板执行环境的实现。模板执行环境可能可以分解为可配置的各种参数，如 template_finder, variable_finder, label_interpreter 等
 * 
 * <p>
 * 另外，其具有一个关键的功能是对某些指令的支持，如 if, for, switch, break, return, call,
 *   try-catch-finally, include, declare 等，这种功能类似于一个虚拟机环境。
 * </p>
 * 
 * <p>
 * 具体的标签可以使用这些指令功能，以构成所需要的标签功能。
 * </p>
 * 
 * <p>这个类被设计为可以被派生，派生类负责设置适当的 templ_finder, var_container, label_inter
 *   等部件。</p>
 * 
 * @author liujunxing
 *
 */
public class DefaultProcessEnvironment implements InternalProcessEnvironment {
	
    /** 是否进行过初始化的标志。 */
    private boolean initialized = false;
    
    /** 模板工厂。 */
    private final TemplateFactory factory;
    
    /** 父执行环境。 */
    protected final DefaultProcessEnvironment parent_env;
    
    /** 当前正在执行的模板。 */
    private Template template;
    
	/** 标签解释器。 */
	private LabelInterpreter label_inter;
	
	/** 表达式计算器。 */
	private ExpressionCalculator expr_calcor;
	
	/** 变量容器链。 */
	private VariableResolver var_resolver;
	
	/** 对象包装器，通过其包装能够访问到这个对象的方法和属性。 */
	private ObjectWrapper obj_wrapper;
	
	/** 内建函数容器。 */
	private GlobalBuiltinContainer global_builtin;
	
	/** 模板查找器。 */
	private TemplateFinder templ_finder;
	
	/** 调用参数。 */
	private java.util.ArrayList<Object> call_param = new java.util.ArrayList<Object>();
	
	/** 局部执行环境的堆栈。 */
	private java.util.Stack<LocalContext> local_ctxt_stack = new java.util.Stack<LocalContext>();
	
	/** 当前正在执行的元素节点。 */
	private AbstractTemplateElement curr_elem;
	
	/* ===================================================================== */
	
	/**
	 * 构造一个缺省的执行环境。
	 */
	protected DefaultProcessEnvironment() {
		this.factory = new TemplateFactoryImpl(null);
		this.parent_env = null;
	}

	protected DefaultProcessEnvironment(TemplateFactory factory) {
		this.factory = factory;
		this.parent_env = null;
	}

	/**
	 * 初始化处理环境。
	 * 注意：原来的初始化放在构造函数里面完成的，但是派生类可能此时还没有完成变量设置，
	 *   导致空指针错误。所以现在将 initialize() 独立出来了。
	 * 此时派生类应该完成了自己的处理了。
	 */
	public void initialize() {
		// 初始化各个部件，注意初始化顺序。
		this.obj_wrapper = initObjectWrapper();
		this.global_builtin = initGlobalBuiltin();
		this.var_resolver = initVariableResolver();
		this.expr_calcor = initExpressionCalculator(null);
		this.label_inter = initLabelInterpreter(); 
		this.templ_finder = initTemplateFinder();
		
		this.initialized = true;
	}
	
	// === Override ===========================================================
	
	/**
	 * 初始化标签解释器。
	 * @return
	 */
	protected LabelInterpreter initLabelInterpreter() {
		return new BuiltinLabelInterpreter();
	}
	
	/**
	 * 初始化建立表达式计算器。
	 * @return
	 */
	protected ExpressionCalculator initExpressionCalculator(DefaultProcessEnvironment parent_env) {
		return new TemplateExpressionCalculator(this);
	}
	
	/**
	 * 初始化建立模板的执行级变量容器并返回，其保存整个模板执行期间的变量。
	 * @return
	 */
	protected VariableResolver initVariableResolver() {
		return new StandardVariableResolver();
	}
	
	/**
	 * 初始化环境使用的对象包装器。
	 * @return
	 */
	protected ObjectWrapper initObjectWrapper() {
		return DefaultObjectWrapper.getInstance();
	}
	
	/**
	 * 初始化内建函数容器。
	 * @return
	 */
	protected GlobalBuiltinContainer initGlobalBuiltin() {
		return GlobalBuiltinContainer.getInstance();
	}

	/**
	 * 初始化模板查找器。
	 * @return
	 */
	protected TemplateFinder initTemplateFinder() {
		return null;
	}

	/**
	 * 使用指定源执行环境构造一个新的执行环境，相当于复制构造。
	 * @param parent_env - 父执行环境。
	 * @param para
	 */
	protected DefaultProcessEnvironment(DefaultProcessEnvironment parent_env) {
		this.parent_env = parent_env;
		this.factory = parent_env.factory;
		this.obj_wrapper = parent_env.obj_wrapper;			// 使用父执行环境的 ObjectWrapper.
		this.global_builtin = parent_env.global_builtin;	// 使用父执行环境的 GlobalBuiltin
		this.var_resolver = parent_env.var_resolver;
		this.expr_calcor = initExpressionCalculator(parent_env);	// ?? 为什么只有计算器不同。 
		this.label_inter = parent_env.label_inter;
		this.templ_finder = parent_env.templ_finder;
		this.initialized = true;
	}

	/* ===================================================================== */

	/**
	 * 编译指定的模板。
	 * @param name
	 * @param content
	 * @return
	 */
	public Template compile(String name, String content) {
		return this.factory.compileTemplate(name, content);
	}

	/**
	 * 使用此环境执行一个模板元素, 此函数表示一个模板的执行级别。
	 * TODO: 不同于 call(), call() 是模板执行的子级别。
	 * @return - 返回执行之后的输出结果。
	 * @exception TemplateException - 如果发生了某种错误。此异常是不检查的。
	 */
	public String process(Template template, Object[] args) {
		setArguments(args);
		
		if (template == null)
			throw new IllegalArgumentException("template == null");
		if (this.initialized == false)
			throw new IllegalStateException("没有调用 initialize() 方法。");
		this.template = template;

		AbstractTemplateElement root_elem = template.getRootElement();
		if (root_elem == null) return "";
		
		try {
			// 为执行构造一个 '执行级' 局部环境。
			this.pushLocalContext(null, LocalContext.ROOT_TEMPLATE);
			
			// 从根元素开始，遍历所有子元素、兄弟元素。
			processInternal(root_elem, true, true);
		} catch (TemplateException ex) {
			return "#{执行模板 '" + this.template.getName() + "' 产生异常：" + ex.getMessage() + "}";
		} catch (java.lang.Exception ex) {
			return "#{执行模板 '" + this.template.getName() + "' 产生异常：" + ex.getMessage() + "}";
		} catch (InstructionChangeError ice) {
			// 如果在这里捕获了这个异常，表示 break, return, 则我们直接返回。
		}
		 
		// 返回输出结果。
		//logger.info("返回输出结果.......");
		return popAllLocalContext().getOut().toString();
	}
	
	/**
	 * 创建一个新的子模板执行环境，新的环境共享执行级数据。
	 * @param para - 传递的参数。
	 * @return
	 */
	public InternalProcessEnvironment createChildProcessEnvironment() {
		return new DefaultProcessEnvironment(this);
	}

	/**
	 * 在当前模板执行环境中访问指定的元素，如果其有子元素则按照其返回值可选遍历访问。
	 * @param elem - 要访问的元素。
	 * @param visitChild - 如果指定为 true，则根据访问返回值访问其子元素。
	 * @param visitSibling - 如果指定为 true，则根据访问返回值访问其兄弟元素。
	 */
	public void visit(AbstractTemplateElement elem, boolean visitChild, boolean visitSibling) {
		processInternal(elem, visitChild, visitSibling);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.InternalProcessEnvironment#processChild(com.chinaedustar.template.core.AbstractTemplateElement)
	 */
	public String processChild(AbstractTemplateElement elem) {
		if (elem == null) return "";
		
		LocalContext localContext = acquireLocalContext(elem, PROCESS_DEFAULT);
		// 解析、执行子模板
		this.visit(elem.getFirstChild(), true, true);
		String result = localContext.getOut().toString();
		localContext.clearOutput();
		return result;
	}

	
	/**
	 * 请求解释指定的标签
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public int interpreteLabel(AbstractLabelElement label) {
		// 如果这是标签没有命名空间，没有任何属性，则优先做为变量看待。
		
		if (isVariableLikeLabel(label)) {
			Object as_var = this.findVariable(label.getLabelName());
			if (as_var != null && as_var != VariableResolver.EMPTY) {
				// 如果找到了变量，则输出这个变量的值
				this.getOut().write(as_var.toString());
				return PROCESS_DEFAULT;
			}
		}
		
		// 否则请求标签解释器解释这个标签
		try {
			int result = this.label_inter.processLabel(label, this);
			if (result == LABEL_UNKNOWN) {
				// TODO: 继续查找标签链，如果最终仍然未知，则输出一个疑问。
				getOut().write("#{?? 未知标签: " + label.getLabelName() + "(行:" + label.getStartLine() + ",列" + label.getStartCol() + ")}");
				return PROCESS_DEFAULT;
			}
			return result;
		} catch (Exception ex) {
			String info = "在解释标签 " + label.getLabelName() + "(行:" + label.getStartLine() + ", 列:" + label.getEndCol() + ") 的时候发生错误.";
			getOut().write("#{?? " + label.getLabelName() + "(行:" + label.getStartLine() + ", 列:" + label.getEndCol() + "), log 里面有详细信息}");
			// 如果这个标签发生错误, 则跳过其所有子内容, 继续解释剩余的部分.
			return PROCESS_SIBLING;
		}
	}
	
	/**
	 * 判断一个标签是否更像一个变量，如果标签没有命名空间，且没有任何属性，则我们认为更像变量
	 * 
	 * @param label
	 * @return
	 */
	private boolean isVariableLikeLabel(AbstractLabelElement label) {
		String namespace = label.getLabelNamespace();
		if (namespace == null || "".equals(namespace)) {
			// ?? 更像独立变量
			if (label.getAttributes() == null)
				return true;
			if (label.getAttributes().isEmpty())
				return true;
		}
		return false;
	}
	
	/**
	 * 求取指定表达式的值。
	 * @param expr - 所要求取的表达式。
	 * @return
	 */
	public Object calc(Expression expr) {
		try {
			Object result = expr.eval(expr_calcor);
			// if (logger.isDebugEnabled()) {
			//	logger.debug("求取表达式 '" + expr + "' 的结果为 " + result);
			// }
			return result;
		} catch (Exception ex) {
			return "{表达式 " + expr.getRootNode().toString() + " 执行异常: " + ex.getMessage() + "}";
		}
	}
	
	/**
	 * 解析一个表达式。
	 * @param str
	 * @return
	 */
	public Expression parseExpr(String str) {
		if (str == null) throw new IllegalArgumentException("str == null");
		try {
			TokenParser token_parser = new TokenParser(str, 0, str.length());
			ExpressionParser expr_parser = new ExpressionParser(token_parser);
			Expression expr = expr_parser.parse();
			// TODO: 验证必须完全解析，表达式之后没有东西了。
			return expr;
		} catch (ExpressionException ex) {
			throw new TemplateException("在解析表达式 " + str + " 的时候发生错误：" + ex.getMessage(), ex);
		}
	}

	/**
	 * 获得当前环境的输出器。
	 * @return
	 */
	public Writer getOut() {
		return this.getCurrentLocalContext().getOut();
	}
	

	/* ============================================================================= */

	/**
	 * 请求定义一个变量。
	 * @param var_name
	 * @param var_value
	 * @param scope
	 */
	public void declareVariable(String var_name, Object var_value, int scope) {
		// 我们先不考虑 scope 都认为是 template-level.
		LocalContext first_local_ctxt = this.local_ctxt_stack.firstElement();
		first_local_ctxt.setVariable(var_name, var_value);
		// TODO: 支持定义不同范围的变量。
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.InternalProcessEnvironment#findVariable(java.lang.String)
	 */
	public Object findVariable(String name) {
		return findVariable(name, SCOPE_ALL);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.InternalProcessEnvironment#findLoopVarDecorator(java.lang.Object)
	 */
	public LoopVarDecorator findLoopVarDecorator(Object loop_var) {
		// 反向遍历局部环境堆栈，查找这个循环变量的描述对象。循环对象只在局部环境里面，所以不用查找更多地方。
		for (int i = this.local_ctxt_stack.size()-1; i >= 0; --i) {
			LocalContext local_ctxt = this.local_ctxt_stack.elementAt(i);
			LoopVarDecorator decorator = local_ctxt.findLoopVarDecorator(loop_var);
			if (decorator != null) return decorator;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.InternalProcessEnvironment#assignVariable(java.lang.String, java.lang.Object)
	 */
	public void assignVariable(String var_name, Object val) {
		// 反向遍历局部环境堆栈，查找指定名字的变量。
		for (int i = this.local_ctxt_stack.size()-1; i >= 0; --i) {
			LocalContext local_ctxt = this.local_ctxt_stack.elementAt(i);
			if (local_ctxt.assignVariable(var_name, val)) return;
		}
		
		// 添加一个新变量在最后的 LocalContext 里面。
		LocalContext local_ctxt = this.local_ctxt_stack.lastElement();
		local_ctxt.setVariable(var_name, val);
	}
	
	/**
	 * 请求调用一个全局方法并返回结果。
	 * @param method_name - 全局方法的名字。
	 * @param param_list - 调用方法的参数。
	 * @return 
	 */
	public Object callGlobalMethod(String method_name, ParameterList param_list) {
		Object result = BuildinGlobalMethod.getInstance().invoke(method_name, this, param_list);
		if (result == BuildinGlobalMethod.METHOD_NOT_FOUND) 
			return null;
		return result;
	}

	/**
	 * 请求指定元素的本地执行环境，如果该环境未建立则建立一个。
	 * @param elem
	 * @return
	 */
	public LocalContext acquireLocalContext(AbstractTemplateElement elem, int ctxt_attr) {
		if (elem == null)
			throw new IllegalArgumentException("elem == null");
		if (this.local_ctxt_stack.size() <= 0)
			throw new TemplateException("没有找到所需的本地环境，你一定在非节点执行期间使用了此函数。");
		
		// 如果还没有任何本地环境，则为该元素创建第一个。
		if (this.local_ctxt_stack.isEmpty()) {
			return this.pushLocalContext(elem, ctxt_attr);
		}
		
		// 如果最后一个本地环境不是所给元素的本地环境，且该环境的拥有元素是 elem 的祖先节点
		//   则创建一个新的本地环境。
		LocalContext last_ctxt = this.local_ctxt_stack.peek();
		AbstractTemplateElement ctxt_owner = last_ctxt.getElement();
		if (ctxt_owner == elem) return last_ctxt;		// 这种情况比较常见。
		if (ctxt_owner != elem && isAncestorElement(ctxt_owner, elem)) {
			return this.pushLocalContext(elem, ctxt_attr);
		}
		
		// 在本地环境堆栈中查找该元素的本地环境。但此时最后一个不是请求者拥有，也不是其祖先节点。
		for (int index = this.local_ctxt_stack.size() - 1; index >= 0; --index) {
			LocalContext local_ctxt = this.local_ctxt_stack.get(index);
			if (local_ctxt.getElement() == elem) return local_ctxt;
			if (isAncestorElement(local_ctxt.getElement(), elem))
				break;
		}
		
		throw new TemplateException("为指定元素所请求的本地环境不存在，且因为已经存在了其子孙元素的本地环境，从而不能建立新的本地环境。你一定在不正确的地方调用了此函数。");
	}

	/**
	 * 判断 ancestor 节点是否是 child 的祖先节点。
	 * @param ancestor - 如果为 null 表示根节点，其为所有节点的祖先节点。
	 * @param child
	 * @return
	 */
	private static final boolean isAncestorElement(AbstractTemplateElement ancestor, AbstractTemplateElement child) {
		if (ancestor == null) return true;
		while (child != null) {
			child = child.getParent();
			if (child == ancestor) return true;
		}
		return false;
	}

	/**
	 * 获得对象包装器。
	 * @return
	 */
	public ObjectWrapper getObjectWrapper() {
		return this.obj_wrapper;
	}

	/**
	 * 尝试查找指定对象的内建方法，并执行它。
	 * @param target
	 * @param method_name
	 * @param param_list
	 * @return
	 */
	public Object findInvokeBuiltinMethod(Object target, String method_name, Object[] param_list) {
		// 查找内建函数。
		BuiltinFunction builtin = lookupBuiltin(method_name);
		
		// 执行。
		if (builtin == null) { 
			return AbstractExpressionCalculator.getObjectClassName(target) + " 类型的对象不支持调用内建方法 '@" + method_name + "'。";
		} else 
			return builtin.exec(this, target, param_list);
	}

	/**
	 * 计算所有的参数值
	 * 
	 * @param param_list
	 * @return
	 */
	public Object[] calcParameter(ParameterList param_list) {
		if (param_list == null) return new Object[0];
		
		// 计算所有参数值。
		Object[] args = new Object[param_list.size()];
		for (int index = 0; index < param_list.size(); ++index) {
			args[index] = this.calc(param_list.get(index));
		}
		return args;
	}

	/**
	 * 查找具有 method_name 的全局内建函数。
	 * @param target
	 * @return
	 */
	protected BuiltinFunction lookupBuiltin(String method_name) {
		BuiltinFunction builtin = this.global_builtin.lookupBuiltin(method_name);
		if (builtin != null) return builtin;

		return null;
	}
	
	/**
	 * 在指定范围内查找具有指定名字的变量并返回其值。
	 * @param var_name - 变量名字。
	 * @param scope - 查找范围。 
	 * @return
	 */
	protected Object findVariable(String var_name, int scope) {
		// 1. 在 LocalContext 里面找
		Object var = findLocalContextVariable(var_name);
		if (var != null) {
			return var;
		}
		
		// 2. 在变量容器链里面查找。
		VariableResolver iter_vc = this.var_resolver;
		while (iter_vc != null) {
			// 在当前容器里面找。
			var = iter_vc.resolveVariable(var_name);
			if (var != null) {
				return var;
			}
			
			// 如果没有找到，则在其父容器里面找。
			iter_vc = iter_vc.getParentResolver();
		}
		
		// 3. 在全局变量容器里面查找。
		var = findGlobalVariable(var_name);
		return var;
	}
	
	/**
	 * 查找具有名字为 template_name 的模板。
	 * @param template_name
	 * @return
	 */
	protected Template findTemplate(String template_name) {
		// 遍历查找链查找具有指定名字的模板。
		TemplateFinder finder = this.templ_finder;
		while (finder != null) {
			Template templ = finder.findTemplate(template_name);
			if (templ != null) {
				return templ;
			}
			finder = finder.getParentFinder();
		}
		return null;
	}

	/* === 各种指令的支持 ======================================================== */

	/**
	 * 定义一个变量。
	 * @param name - 此变量的名字。
	 * @param val - 此变量的值。
	 * @param scope - 变量应用范围，取 TemplateConstant.SCOPE_XXX 的值。
	 */
	/*@Override*/ public void declare(String name, Object val, int scope) {
		
	}
	
	/**
	 * 跳出当前可跳出元素，可跳出元素包括 switch, foreach 等。<br>
	 * 注意：这个函数不会返回，将直接抛出特定异常来改变执行流程。
	 */
	public void breakk() {
		throw new InstructionChangeError(InstructionChangeError.INSTRU_CODE_BREAK);
	}
	
	/**
	 * 使用指定的参数调用指定的子模板。
	 * @param child_template - 子模板的名字。
	 * @param para - 调用参数。
	 */
	public String call(String child_template_name, Object[] para) {
		// 查找子模板。
		Template child_template = this.findTemplate(child_template_name);
		if (child_template == null) {
			return "#{?? call 无法找到要执行的子模板的 '" + child_template_name + 
				"', 请确定名字没有写错，并且该模板能够找到。}";
			// throw new TemplateException("无法找到要执行的子模板的 '" + child_template_name + "', 请确定名字没有写错，并且该模板能够找到。");
		}
		
		return call(child_template, para);
	}
	
	/**
	 * 使用指定的参数执行子模板。
	 * @param templ
	 * @param para
	 * @return
	 */
	public String call(Template templ, Object[] para) {
		if (templ == null) throw new IllegalArgumentException("templ == null");
		
		// 产生一个新的执行环境，新的执行环境具有原来相同的全局环境，但局部环境不同。
		InternalProcessEnvironment child_env = this.createChildProcessEnvironment();
		
		// 使用新的执行环境执行子模板。
		return child_env.process(templ, para);
	}

	/**
	 * 弹出一个此模板被调用时的参数，如果没有了则返回 null。
	 * @return
	 */
	public Object popParameter() {
		if (this.call_param == null) return null;
		if (this.call_param.isEmpty()) return null;
		Object result = this.call_param.get(0);
		this.call_param.remove(0);
		return result;
	}

	
	/**
	 * 从函数调用、子模板调用中返回。
	 */
	/*@Override*/ public void returnn(Object retval) {
		throw new UnsupportedOperationException("还未实现 return");
	}
	
	/**
	 * 抛出一个错误。
	 * @param thing
	 */
	/*@Override*/ public void throww(Object thing) {
		throw new UnsupportedOperationException("还未实现 throw");
	}

	
	/* === 其它公共功能 ================================================================== */

	/**
	 * 获得当前局部执行环境对象，当前局部执行环境在堆栈顶部。
	 * @return
	 */
	public final LocalContext getCurrentLocalContext() {
		return this.local_ctxt_stack.peek();
	}
	
	/* === 实现 ================================================================== */
	
	/**
	 * 设置模板执行参数。
	 */
	private final void setArguments(Object[] args) {
		this.call_param.clear();
		if (args != null) {
			for (int index = 0; index < args.length; ++index)
				this.call_param.add(args[index]);
		}
	}
	
	/**
	 * 创建并压入一个局部执行环境。
	 *
	 */
	private final LocalContext pushLocalContext(AbstractTemplateElement elem, int ctxt_attr) {
		LocalContext local_ctxt = new LocalContext(this, elem, ctxt_attr);
		this.local_ctxt_stack.push(local_ctxt);
		return local_ctxt;
	}
	
	/**
	 * 弹出所有本地环境。并返回最后一个弹出的本地环境。
	 *
	 */
	private final LocalContext popAllLocalContext() {
		if (this.local_ctxt_stack.isEmpty()) {
			throw new TemplateException("不正确的本地环境栈。");
		}
		LocalContext local_ctxt = this.local_ctxt_stack.pop(); 
		while (this.local_ctxt_stack.isEmpty() == false) {
			LocalContext prev_ctxt = this.local_ctxt_stack.pop();
			local_ctxt = prev_ctxt.appendOutput(local_ctxt);
		}
		return local_ctxt;
	}
	
	/**
	 * 弹出多个局部执行环境对象，直到遇到当前执行元素的祖先节点的本地环境。
	 * @return
	 */
	private final void popToCurLocalContext() {
		if (this.local_ctxt_stack.isEmpty()) return;
		
		LocalContext local_ctxt = this.local_ctxt_stack.peek();
		// 获取当前栈顶的本地环境，如果元素是 null 表示是根，则返回。
		while (local_ctxt.getElement() != null) {
			// 如果是当前执行节点的祖先节点，则返回。
			if (isAncestorElement(local_ctxt.getElement(), this.curr_elem)) break;
			
			// 弹出这个本地环境，将其输出合并到前一个本地环境。
			local_ctxt = this.local_ctxt_stack.pop();
			LocalContext prev_ctxt = this.local_ctxt_stack.peek();	// must exist
			prev_ctxt.appendOutput(local_ctxt);
			local_ctxt = prev_ctxt;
		}
	}
	
	/**
	 * 在 LocalContext 栈中查找指定名字的变量。
	 * @param var_name - 要查找的变量名。
	 * @return
	 */
	protected final Object findLocalContextVariable(String var_name) {
		// 反向遍历局部环境堆栈，查找指定名字的变量。
		for (int i = this.local_ctxt_stack.size()-1; i >= 0; --i) {
			LocalContext local_ctxt = this.local_ctxt_stack.elementAt(i);
			Object var = local_ctxt.findVariable(var_name);
			if (var != null) return var;
		}
		return null;
	}
	
	/**
	 * 查找全局变量。
	 * @param var_name
	 * @return
	 */
	protected final Object findGlobalVariable(String var_name) {
		return GlobalVariableResovler.INSTANCE.resolveVariable(var_name);
	}
	
	/**
	 * 执行元素。不为执行此元素创建新的局部环境。
	 * @param elem - 要执行的元素。
	 * @param processSibling - 是否执行兄弟节点。
	 * @param processChild - 是否执行子节点。
	 */
	private final void processInternal(AbstractTemplateElement elem, boolean visitChild, boolean visitSibling) {
		while (elem != null) {
			// 访问此节点
			int accept_result = processElement(elem);
			
			// logger.info("elem.accept() return " + accept_result + ", elem is " + elem);
			
			// 计算下一个要执行的节点。
			elem = getNextProcessElement(elem, accept_result, visitChild, visitSibling);
		}
	}

	/**
	 * 执行一个节点
	 * 
	 * @param elem
	 */
	private final int processElement(AbstractTemplateElement elem) {
		// 保存前一次执行节点。
		AbstractTemplateElement old_curr_elem = this.curr_elem;
		try {
			// 设置当前执行节点。
			this.curr_elem = elem;
			
			// 执行该节点。
			int result = elem.accept(this);
			
			if (result == PROCESS_DEFAULT) {
				// 继续处理，如果有子节点，则将处理子节点；否则处理兄弟节点。此为缺省结果。
				if (elem.getFirstChild() != null) {
					// 递归调用执行子元素。
					processInternal(elem.getFirstChild(), true, true);
				}
				
				// 执行下一个弟节点。
				return PROCESS_SIBLING;
			}
			return result;
		} catch (InstructionChangeError ice) {
			// 捕获到指令变更异常
			LocalContext local_ctxt = this.getCurrentLocalContext();
			if (local_ctxt.getElement() != elem || local_ctxt.isBreakable() == false)
				throw ice;		// 重新抛出异常。
			return PROCESS_SIBLING;
		} finally {
			// BUG: 如果返回 PROCESS_DEFAULT, 则可能要执行子节点，需要使用 LocalContext 里面的数据。
			//   修改方法？ 这里不弹出 LocalContext
			this.popToCurLocalContext();
			this.curr_elem = old_curr_elem;
		}
	}
	
	/**
	 * 根据 elem 的返回结果计算下一个应该执行的节点。
	 * @param elem
	 * @param accept_result
	 * @return
	 */
	private final AbstractTemplateElement getNextProcessElement(AbstractTemplateElement elem, 
			int accept_result, boolean visitChild, boolean visitSibling) {
		switch (accept_result) {
			case PROCESS_DEFAULT: {
				throw new InternalError("return PROCESS_DEFAULT at bad position.");
				/*
				// 继续处理，如果有子节点，则将处理子节点；否则处理兄弟节点。此为缺省结果。
				if (visitChild && elem.getFirstChild() != null) {
					// 递归调用执行子元素。
					processInternal(elem.getFirstChild(), true, true);
				}
				
				// 执行下一个弟节点。
				return visitSibling ? elem.getNextElement() : null;
				*/
			}
			case PROCESS_SIBLING: {
				// 执行兄弟节点，不执行子节点。
				return visitSibling ? elem.getNextElement() : null;
			}
			case PROCESS_REPEAT: {	// 重复执行此节点，用于 for, while 等标签的解释。
				throw new UnsupportedOperationException("PROCESS_REPEAT not support");
			}
			case PROCESS_RETURN: {  // 返回，用于从子模板中返回到调用者模板中。
				throw new UnsupportedOperationException("PROCESS_RETURN not support");
			}
			case PROCESS_BREAK: { 	// 跳出 switch, for, while 等控制，执行其下一个元素。
				this.breakk();
			}
			case PROCESS_STOP: { 	// 停止模板解析，产生一个指定的错误信息。
				throw new UnsupportedOperationException("PROCESS_STOP not support");
			}
			default: {	// ?? 有一个家伙返回了我们不知道的值，我们假定其意思是停止。
				throw new UnsupportedOperationException("PROCESS_unknown value =" + accept_result + " not support");
			}
		} // end of switch
	}

	/* === 流程改变用的特殊异常 ======================================================= */

	/** 表示这是一个特殊的流程变更异常，应用程序如果接收到此异常应该将其重新抛出。 */
	public static final class InstructionChangeError extends java.lang.Error {
		private static final long serialVersionUID = -4530587815638574851L;
		
		/** break */
		public static final int INSTRU_CODE_BREAK = 1;
		/** return */
		public static final int INSTRU_CODE_RETURN = 2;
		
		private final int instru_code;
		
		public InstructionChangeError(int instru_code) {
			this.instru_code = instru_code;
		}
		
		public int getInstruCode() {
			return this.instru_code;
		}
	}
	
	/**
	 * 执行 foreach 循环
	 * 
	 * @param elem 标签元素。
	 * @param item_name 循环时放入LocalContext中的变量的名称。
	 * @param coll_obj 用来执行循环的对象。
	 */
	//public void foreach(AbstractLabelElement label, String var_name, Object coll_obj) {	
	//	foreach(label, var_name, coll_obj, 1, 1);
	//}
	
	/**
	 * 执行 foreach 循环。
	 * 会将当前数据以 var_name 存入 LocalContext中。
	 * 
	 * @param elem - 标签或指令元素。
	 * @param item_name - 循环时放入LocalContext中的变量的名称。
	 * @param coll_obj - 用来执行循环的对象。
	 */
	@SuppressWarnings("rawtypes")
	public void foreach(AbstractContainerElement elem, String var_name, Object coll_obj, ForeachCallback callback) {
		// 如果没有任何东西需要枚举，则直接返回。
		if (elem.getFirstChild() == null) return;

		// 请求这个元素的本地执行环境，该环境用于定义局部 #foreach 变量及 #break 跳出。
		LocalContext local_ctxt = acquireLocalContext(elem, LocalContext.LOCAL_CONTEXT_BREAKABLE);
		
		// 计算 coll_expr, 从结果获得一个枚举接口，用于枚举这个集合。
		Object coll_data = getObjectWrapper().wrap(coll_obj);
		if ((coll_data instanceof CollectionDataModel) == false) {
			throw new TemplateException("'" + coll_data.getClass().getName() + "' 类型的数据不支持 foreach 运算，其不具备集合遍历所需的接口。");
		}
		Iterator iter = ((CollectionDataModel)coll_data).iterator();
		
		LoopVarDecorator decorator = new LoopVarDecorator();
		decorator.index = 0;
		decorator.is_first = true;
		while (iter.hasNext()) {
			// 定义枚举变量。
			Object item = iter.next();
			decorator.target = item;
			decorator.is_last = iter.hasNext() ? false : true;
			// IteratorDecorator item_deco = new IteratorDecorator(item, index, is_first, is_last);
			local_ctxt.setLoopVariable(var_name, item, decorator);
			local_ctxt.setVariable(".loop_var", item);
			if (callback != null)
				callback.foreachObject(local_ctxt, coll_obj, item, decorator.index);
			
			// 执行所有子元素。
			visit(elem.getFirstChild(), true, true);
			
			++decorator.index;
			decorator.is_first = false;
		}
		
		return;
	}
	
}
