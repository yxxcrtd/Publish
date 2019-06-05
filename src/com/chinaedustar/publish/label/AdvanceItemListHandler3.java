package com.chinaedustar.publish.label;

import com.chinaedustar.publish.model.BuiltinLabel;
import com.chinaedustar.publish.model.ItemQueryOption;
import com.chinaedustar.publish.model.PaginationInfo;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 高级项目处理器基类，版本 3. (在 ModuleItemListHandler2 基础上改进了 option 对象)
 * 
 * @author liujunxing
 *
 */
public abstract class AdvanceItemListHandler3 extends AbstractLabelHandler2 {
	/** 对象查询选项对象。 */
	protected ItemQueryOption query_option;
	
	/** 选项。 */
	protected java.util.Map<String, Object> options;

	/** 分页信息。 */
	protected PaginationInfo page_info;

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
	 */
	@Override public int handleLabel() {
		// 获取参数。
		if (makeQueryOption() == false) return PROCESS_DEFAULT;
		
		// 获取数据。
		Object data = getData();
		
		// 合成。
		return process(data);
	}

	/** 处理标签里面的选项和参数，构造 ItemQueryOption 对象，返回 true 表示正确，返回 false 表示失败。 */
	protected boolean makeQueryOption() {
		this.query_option = new ItemQueryOption();
		AttributeCollection attrs = label.getAttributes();
		
		// TODO: 从 attrs 中获取选项参数。
		
		this.options = attrs.attrToOptions();

		// 构造分页信息。
		this.page_info = new PaginationInfo();
		int pageSize = attrs.safeGetIntAttribute("pageSize", 20);
		if (pageSize < 1) pageSize = 20;
		page_info.setPageSize(pageSize);
		initPagination(page_info);
		
		return true;
	}

	/**
	 * 派生类根据需要设置所需分页信息。
	 * @param pInfo
	 */
	protected void initPagination(PaginationInfo pInfo) { 
		
	}

	/** 执行合成。 */
	private int process(Object data) {
		if (label.hasChild()) {
			// 使用内部模板。
			LocalContext local_ctxt = env.acquireLocalContext(label, 0);
			local_ctxt.setVariable("item_list", data);
			// super.addRepeaterSupport(local_ctxt, "item", data, page_info.getPageSize());
			super.addRepeaterSupport(local_ctxt, "item", data);
			super.addPageNavSupport(local_ctxt, page_info);
			
			env.visit(label.getFirstChild(), true, true);
			return PROCESS_SIBLING;
		} else {
			// 使用指定模板展现。
			String template_name = getBuiltinName();
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			return builtin_label.process(env, new Object[]{data, options, page_info});
		}
	}

	// === 派生类要实现的 =============================================================

	/** 
	 * 获得数据。我们希望派生类能够派生这个方法以提供更好的封装。 
	 */
	protected abstract Object getData();
}
