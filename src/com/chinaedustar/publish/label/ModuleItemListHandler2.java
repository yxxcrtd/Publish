package com.chinaedustar.publish.label;

import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 提供对模块级别的项目列表的显示，抽象基类实现。 (ModuleItemListHandler 版本2)
 * 
 * <br>可自定义。能嵌套使用Repeater和PageNav标签。Repeater中能使用的变量是item。
 * 
 * @param moduleId 模块的标识。必须提供。（可通过在标签中给出，也可以通过设置环境变量给出）。
 * @param contentLength 内容长度。默认为100。
 * @param usePage 是否显示分页，默认为true。
 * @param pageSize 每页显示搜索结果的个数。默认为10。
 * 
 * @author dengxiaolong
 *
 */
public abstract class ModuleItemListHandler2 extends AbstractLabelHandler2 {
	/** 项目所属模块。 */
	protected Module module;
	
	/** 选项。 */
	protected java.util.Map<String, Object> options;
	
	/** 分页信息。 */
	protected PaginationInfo page_info;
	
	public Module getModule() {
		return this.module;
	}
	
	@Override public int handleLabel() {
		// 获取参数。
		if (makeOptions() == false) return PROCESS_DEFAULT;
		
		// 获取数据。
		Object data = getData();
		
		// 合成。
		return process(data);
	}

	/** 处理标签里面的选项和参数，返回 true 表示正确，返回 false 表示失败。 */
	private boolean makeOptions() {
		AttributeCollection attrs = label.getAttributes();
		
		// 必须提供模块的标识, 从而获得模块对象 this.module
		int moduleId = attrs.safeGetIntAttribute("moduleId", 0);
		if (moduleId <= 0) {
			moduleId = getModuleId();
		}
		if (moduleId <= 0) {
			out("#{??" + label.getLabelName() + " 没有给出模块的标识}");
			return false;
		}
		this.module = pub_ctxt.getSite().getModules().getModule(moduleId);
		if (module == null) {
			out("#{??" + label.getLabelName() + " 没有给出模块的标识}");
			return false;
		}
		
		// 其它属性。
		int contentLength = attrs.safeGetIntAttribute("contentLength", 100);
		int pageSize = attrs.safeGetIntAttribute("pageSize", 10);
		if (pageSize < 1) pageSize = 10;
		boolean usePage = attrs.safeGetBoolAttribute("usePage", true);
		
		this.options = attrs.attrToOptions();
		options.put("contentLength", contentLength);
		options.put("usePage", usePage);
		
		this.page_info = new PaginationInfo();
		page_info.setPageSize(pageSize);
		initPagination(page_info);
		
		return true;
	}
	
	/** 执行合成。 */
	private int process(Object item_list) {
		if (label.hasChild()) {
			// 使用内部模板。
			LocalContext local_ctxt = env.acquireLocalContext(label, 0);
			local_ctxt.setVariable("item_list", item_list);
			super.addRepeaterSupport(local_ctxt, "item", item_list);
			super.addPageNavSupport(local_ctxt, page_info);
			
			env.visit(label.getFirstChild(), true, true);
			return PROCESS_SIBLING;
		} else {
			// 使用指定模板展现。
			String template_name = super.getTemplateName(getBuiltinName());
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			return builtin_label.process(env, new Object[]{item_list, options, page_info});
		}
	}
	
	/**
	 * 从Env中得到字符串
	 * @param name
	 * @param defvar
	 * @return
	 */
	protected final String getStringFromEnv(String name, String defvar) {
		Object obj = env.findVariable(name);
		return (null == obj) ? defvar : obj.toString();
	}

	/** 派生类必须实现的，获得所需的模块标识。 */
	protected int getModuleId() {
		return getIntFromEnv("moduleId", 0);
	}
	
	/**
	 * 从Env中得到整数
	 * @param name
	 * @param defvar 
	 * @return
	 */
	protected final int getIntFromEnv(String name, int defvar) {
		Object obj = env.findVariable(name);

		if (null == obj) return defvar;
		try {
			return (Integer)obj;
		} catch (Exception ex) {
			return defvar;
		}
	}
	
	// === 派生类要实现的 =============================================================

	/**
	 * 派生类根据需要设置所需分页信息。
	 * @param pInfo
	 */
	protected void initPagination(PaginationInfo pInfo) { }

	/** 
	 * 获得数据。我们希望派生类能够派生这个方法以提供更好的封装。 
	 */
	protected abstract Object getData();
}
