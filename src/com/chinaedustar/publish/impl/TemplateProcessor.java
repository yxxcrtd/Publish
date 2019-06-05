package com.chinaedustar.publish.impl;

import java.util.Map;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.template.Template;
import com.chinaedustar.template.TemplateFactory;

/**
 * 构造一个模板处理器。
 * @author liujunxing
 *
 */
public class TemplateProcessor {
	private final PublishContext pub_ctxt;
	
	/**
	 * 使用指定的页面环境构造一个 TemplateProcessor 的实例。
	 * @param page_ctxt
	 */
	public TemplateProcessor(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	/**
	 * 处理一个页面级模板。
	 * @param tmpl_content
	 * @param vars - 这个模板执行时使用的全局变量 Map，其传递给 UserPageProcessEnvironment.
	 * @param args - 传递给这个模板的参数（局部变量数组）。
	 * @return - 返回模板执行结果。
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String processTemplate(String tmpl_content, Map vars, Object[] args) {
		//System.out.println("处理一个页面级模板.......");
		// 1. 编译。
		TemplateFactory factory = TemplateFactory.createTemplateFactory(null);
		Template templ = factory.compileTemplate(".main", tmpl_content);
		
		// 2. 执行。
		//System.out.println("执行一个页面级模板.......");
		UserPageProcessEnvironment env = UserPageProcessEnvironment.createInstance(pub_ctxt, vars);
		return env.process(templ, args);
	}
}
