package com.chinaedustar.publish.impl;

import com.chinaedustar.publish.itfc.WebContext;
import com.chinaedustar.publish.tag.PageContextTemplate;
import com.chinaedustar.publish.tag.TemplateContainer;
import com.chinaedustar.publish.tag.TemplateWrapper;
import com.chinaedustar.template.Template;
import com.chinaedustar.template.TemplateFinder;
import com.chinaedustar.template.VariableResolver;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LabelInterpreter;

/**
 * JSP 模板标签库使用的执行环境类。
 * 
 * @author liujunxing
 */
public final class JsptagTemplateProcessEnvironment extends TemplateProcessBase { 
    
	/** 页面环境变量。 */
	private final WebContext web_ctxt;
	
	/**
	 * 创建一个执行环境。
	 * @return
	 */
	public static final JsptagTemplateProcessEnvironment createInstance(WebContext web_ctxt) {
		JsptagTemplateProcessEnvironment env = new JsptagTemplateProcessEnvironment(web_ctxt);
		env.initialize();
		return env;
	}

	
	/**
	 * 使用指定的页面环境变量构造一个 JsptagTemplateProcessEnvironment 的实例。
	 * @param pageContext
	 */
	private JsptagTemplateProcessEnvironment(WebContext webContext) {
		super();
		this.web_ctxt = webContext;
		super.pub_ctxt = web_ctxt.getPublishContext();
	}
	
	/**
	 * 复制构造函数。
	 * @param parent
	 * @param para
	 */
	private JsptagTemplateProcessEnvironment(JsptagTemplateProcessEnvironment parent) {
		super(parent);
		this.web_ctxt = parent.web_ctxt;
		super.pub_ctxt = parent.pub_ctxt;
	}
	
	/**
	 * 初始化变量查找器。
	 */
	@Override protected final VariableResolver initVariableResolver() {
		return new PageVariableResolver(super.initVariableResolver(), this.web_ctxt.getPageContext());
	}
	
	/**
	 * 初始化模板查找器。
	 * @return
	 */
	@Override protected final TemplateFinder initTemplateFinder() {
		return new PageTemplateFinder(super.initTemplateFinder());
	}

	/**
	 * 初始化标签解释器。
	 * @return
	 */
	protected LabelInterpreter initLabelInterpreter() {
		LabelInterpreter parent = super.initLabelInterpreter();
		return new PublishLabelInterpreter(web_ctxt.getPublishContext(), parent);
	}

	/**
	 * 创建一个新的子模板执行环境，新的环境共享执行级数据。
	 * @param para
	 * @return
	 */
	@Override public final InternalProcessEnvironment createChildProcessEnvironment() {
		return new JsptagTemplateProcessEnvironment(this);
	}

	/**
	 * 获得页面级模板容器对象，如果没有则创建一个。
	 * @return
	 */
	private final TemplateContainer getPageTemplateContainer() {
		return PageContextTemplate.getPageTemplateContainer(web_ctxt.getPageContext());
	}

	/* === 变量查找器、模板查找器 子类实现 ==================================== */
	
	/**
	 * 页面环境的模板查找器实现。
	 */
	private final class PageTemplateFinder implements TemplateFinder {
		private TemplateFinder parent_finder;
		public PageTemplateFinder(TemplateFinder parent_finder) {
			this.parent_finder = parent_finder;
		}
		
		/**
		 * 查找具有指定名字的模板。
		 * @param template_name - 模板的名字。
		 * @return - 如果有此模板则返回该模板；否则返回 null。
		 */
		public Template findTemplate(String template_name) {
			// 得到页面模板容器，如果容器不存在，则返回 null.
			TemplateContainer templ_cont = getPageTemplateContainer();
			if (templ_cont == null) return null;
			
			TemplateWrapper templ_wrapper = templ_cont.getTemplateWrapper(template_name);
			if (templ_wrapper == null) return null;
			return templ_wrapper.getTemplate();
		}
		
		/**
		 * 获得父一级的模板装载器。
		 * @return
		 */
		public TemplateFinder getParentFinder() {
			return this.parent_finder;
		}
	}
}
