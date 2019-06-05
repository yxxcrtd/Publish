package com.chinaedustar.publish.impl;

import java.util.Map;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.template.VariableResolver;
import com.chinaedustar.template.comp.StandardVariableResolver;
import com.chinaedustar.template.core.LabelInterpreter;

/**
 * 用户显示页面的模板执行环境。此执行环境不依赖于 PageContext.
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public final class UserPageProcessEnvironment extends TemplateProcessBase {
	/**
	 * 创建一个执行环境。
	 * @return
	 */
	public static final UserPageProcessEnvironment createInstance(PublishContext pub_ctxt, Map vars) {
		UserPageProcessEnvironment env = new UserPageProcessEnvironment(pub_ctxt, vars);
		env.initialize();
		return env;
	}
	
	/** 此模板执行环境的自己的变量的集合，可以为 null。 */
	private final Map vars;

	/**
	 * 使用指定的 pub_ctxt 和 变量构造一个 UserPageProcessEnvironment 的实例。
	 * @param pub_ctxt
	 * @param vars
	 */
	private UserPageProcessEnvironment(PublishContext pub_ctxt, Map vars) {
		if (pub_ctxt == null) throw new IllegalArgumentException("pub_ctxt == null");
		super.pub_ctxt = pub_ctxt;
		this.vars = vars;
	}
	
	/**
	 * 复制构造函数。
	 * @param src
	 */
	private UserPageProcessEnvironment(UserPageProcessEnvironment src) {
		super(src);
		super.pub_ctxt = src.pub_ctxt;
		this.vars = src.vars;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.DefaultProcessEnvironment#createChildProcessEnvironment()
	 */
	@Override public final UserPageProcessEnvironment createChildProcessEnvironment() {
		return new UserPageProcessEnvironment(this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.DefaultProcessEnvironment#initVariableResolver()
	 */
	protected VariableResolver initVariableResolver() {
		VariableResolver parent_vr = super.initVariableResolver();
		return new StandardVariableResolver(this.vars, parent_vr);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.DefaultProcessEnvironment#initLabelInterpreter()
	 */
	@Override protected LabelInterpreter initLabelInterpreter() {
		LabelInterpreter parent = super.initLabelInterpreter();
		return new PublishLabelInterpreter(this.pub_ctxt, parent);
	}
}
