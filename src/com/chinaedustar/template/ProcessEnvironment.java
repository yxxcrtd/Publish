package com.chinaedustar.template;

/**
 * 定义模板的执行环境类。
 * 
 * @author liujunxing
 */
public interface ProcessEnvironment extends TemplateConstant {
	/**
	 * 编译指定的模板。
	 * @param name
	 * @param content
	 * @return
	 */
	public Template compile(String name, String content);
	
	/**
	 * 执行一个模板。
	 * @param template - 要执行的模板。
	 * @param args - 传递的执行参数，可以为 null.
	 */
	public String process(Template template, Object[] args);
	
	/**
	 * 创建一个子执行模板，其用于执行模板中嵌套的子模板。
	 * @param tmpl_param - 要执行的模板的参数，可选。
	 * @return
	 */
	public ProcessEnvironment createChildProcessEnvironment();
}
