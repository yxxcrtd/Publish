package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.model.Label;
import com.chinaedustar.publish.model.PaginationInfo;

/**
 * 标签管理数据提供。
 * 
 * @author liujunxing
 * @jsppage admin_label_list.jsp
 * @jsppage admin_label_add.jsp
 */
public class LabelManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public LabelManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_label_list.jsp 页面所需数据初始化。
	 * @initdata label_list - 标签列表，其为一个 DataTable
	 * @initdata page_info - 分页信息。
	 */
	public void initListPage() {
		PaginationInfo page_info = getPaginationInfo();
		
		// 获得列表数据。
		int labelType = super.safeGetIntParam("type", 0);
		Object label_list = site.getLabelCollection().getLabelDataTable(labelType, page_info);
		
		page_info.init();
		setTemplateVariable("label_list", label_list);
		setTemplateVariable("page_info", page_info);
	}

	/**
	 * admin_label_add.jsp 页面所需数据初始化。
	 *
	 */
	public void initEditPage() {
		// provider="com.chinaedustar.publish.admin.LabelEditAddDataProvider" param="labelId"
		Label label = null;
		int labelId = super.safeGetIntParam("labelId", 0);
		if (labelId == 0) {
			label = new Label();
		} else {
			label = site.getLabelCollection().getLabel(labelId);
		}
		
		setTemplateVariable("label", label);
	}
}

