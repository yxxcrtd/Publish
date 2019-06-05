package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.model.*;

/**
 * 来源管理。
 * 
 * @author liujunxing
 *
 */
public class SourceManage extends AuthorManageBase {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public SourceManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_source_list.jsp 页面数据初始化。
	 *
	 */
	public void initListPage() {
		provideChannelAndList();
		initSourceList();
	}
	
	/**
	 * admin_source_add.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		provideChannelAndList();

		// 要编辑的来源对象。
		int sourceId = super.safeGetIntParam("sourceId", 0);
		Source source = null;
		if (sourceId == 0) {
			source = new Source();
			source.setChannelId(channel.getChannelId());
		} else {
			source = channel.getSourceCollection().getSource(sourceId);
		}
		setTemplateVariable("source", source);
	}
	
	/**
	 * admin_item_choose.jsp 页面数据初始化.
	 *
	 */
	public void initChoosePage() {
		this.channel = getChannelData();
		if (channel == null) return;
		
		initSourceList();
	}
	
	// 初始化来源列表。
	private void initSourceList() {
		// 来源类型，1：友情站点；2：中文站点；3：外文站点；4：其他站点；0：不处理来源类型；-1：最近常用。
		int sourceType = super.safeGetIntParam("sourceType", 0);
		String field = super.safeGetStringParam("field", null);
		String keyword = super.safeGetChineseParameter("keyword", null);
		
		PaginationInfo page_info = getPaginationInfo();
		page_info.setItemName("个来源");

		// 获得来源列表。
		SourceCollection source_coll = this.channel.getSourceCollection();
		Object source_list = source_coll.getSourceList(sourceType, field, keyword, page_info);
		setTemplateVariable("source_list", source_list);
		setTemplateVariable("page_info", page_info);
	}
}
