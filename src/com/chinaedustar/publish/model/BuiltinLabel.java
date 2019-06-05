package com.chinaedustar.publish.model;

import java.lang.ref.SoftReference;
import java.util.List;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.template.ProcessEnvironment;
import com.chinaedustar.template.Template;
import com.chinaedustar.template.core.InternalProcessEnvironment;

/**
 * 内建标签的实现类，其支持延迟装载 content, 缓存编译之后的模板，以及执行模板的能力。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class BuiltinLabel {
	/** 发布系统环境。 */
	private PublishContext pub_ctxt;
	
	/** 此标签的名字。 */
	private String name;
	
	/** 唯一标识。 */
	private int id;
	
	/**
	 * 使用指定名字和标识构造一个 BuiltinLabel 的实例。
	 * @param name
	 * @param id
	 */
	public BuiltinLabel(PublishContext pub_ctxt, String name, int id) {
		this.pub_ctxt = pub_ctxt;
		this.name = name;
		this.id = id;
	}
	
	/**
	 * 获得标签的名字。
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 获得指定名字的内建模板的模板内容。
	 * @param pub_ctxt
	 * @param name
	 * @return 读取出的模板内容，如果指定名字的模板不存在，则返回 null.
	 */
	public static final String getTempleteContent(PublishContext pub_ctxt, String name) {
		// 读取出这个模板的实际内容。
		String hql = "SELECT l.content FROM Label l WHERE l.name = :name";
		List list = pub_ctxt.getDao().queryByNamedParam(hql, new String[] {"name"}, new String[] { name });
		if (list.size() == 0) return null;
		String content = (String)list.get(0);
		return content;
	}
	
	// === 业务方法 ===============================================================
	
	/** 编译过的模板缓存。 */
	private SoftReference<Template> compiled_template;
	
	/**
	 * 使用 env 创建子执行环境，并执行此内建 Label，输出到 env 里面。
	 * @param env 
	 * @param args
	 * @return 返回 InternalProcessEnvironment.PROCESS_DEFAULT。
	 */
	public int process(InternalProcessEnvironment env, Object[] args) {
		String result = getProcessedResult(env, args);
		if (result != null && result.length() > 0)
			env.getOut().write(result);
		return InternalProcessEnvironment.PROCESS_DEFAULT;
	}
	
	/**
	 * 使用 env 创建子执行环境，并执行此内建 Label，返回执行结果。
	 * @param env 
	 * @param args
	 * @return
	 */
	public String getProcessedResult(InternalProcessEnvironment env, Object[] args) {
		// 获得编译过的模板。
		Template template = (compiled_template == null) ? null : compiled_template.get();
		if (template == null) {
			template = compileTemplate(env);
			if (template == null) return "";
		}

		// 创建一个子执行环境执行此模板，返回执行结果。
		return env.createChildProcessEnvironment().process(template, args);
	}
	
	/**
	 * 编译此内建模板。
	 * @param env
	 * @return
	 */
	private synchronized Template compileTemplate(ProcessEnvironment env) {
		// 1. 延迟读取出这个模板的实际内容。
		String hql = "SELECT l.content FROM Label l WHERE l.id = " + this.id;
		List list = pub_ctxt.getDao().list(hql);
		if (list.size() == 0) return null;
		String content = (String)list.get(0);
		
		// 2. 编译这个模板并缓存。
		Template template = env.compile(this.getName(), content);
		this.compiled_template = new SoftReference<Template>(template);
		return template;
	}
}
