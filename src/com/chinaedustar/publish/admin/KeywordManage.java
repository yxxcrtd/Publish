package com.chinaedustar.publish.admin;

import java.util.List;
import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.model.*;

/**
 * 关键字管理的数据提供类。
 * 
 * @author liujunxing
 * @jsppage admin_keyword_add.jsp
 * @jsppage admin_keyword_list.jsp
 * @jsppage admin_keyword_choose.jsp
 */
@SuppressWarnings("rawtypes")
public class KeywordManage extends AuthorManageBase {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public KeywordManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_keyword_add.jsp 页面的数据提供。
	 *
	 */
	public void initEditPageData() {
		provideChannelAndList();
		
		// keyword 数据。
		Keyword keyword = null;
		int keyword_id = super.safeGetIntParam("keywordId", 0);
		if (keyword_id == 0) {
			keyword = new Keyword();
			keyword.setChannelId(channel.getChannelId());
		} else {
			keyword = channel.getKeywordCollection().getKeyword(keyword_id);
		}
		setTemplateVariable("keyword", keyword);
	}

	/**
	 * admin_keyword_list.jsp 页面的数据提供。
	 *
	 */
	public void initListPageData() {
		provideChannelAndList();
		
		// keyword 列表。
		// 搜索的字段，为 null 或者 "" 的时候不处理。
		String field = super.safeGetStringParam("field", null);
		// 搜索的关键字，与 field 同时出现。
		String keyword = super.safeGetChineseParameter("keyword", null);

		PaginationInfo page_info = getPaginationInfo();
		page_info.setItemName("个关键字");

		// 查询关键字数据，设置 keyword_list, page_info 数据。
		KeywordCollection keyword_coll = this.channel.getKeywordCollection();
		List keyword_list = keyword_coll.getKeywordList(field, keyword, page_info);
		page_info.init();
		setTemplateVariable("keyword_list", keyword_list);
		setTemplateVariable("page_info", page_info);

	}

	/**
	 * admin_keyword_choose.jsp 页面的数据提供。
	 *
	 */
	public void initChoosePageData() {
		// 当前频道。
		Channel channel = super.getChannelData();
		setTemplateVariable("channel", channel);
		
		PaginationInfo page_info = getPaginationInfo();
		page_info.setItemName("个关键字");

		// 查询关键字数据，设置 keyword_list, page_info 数据。
		KeywordCollection keyword_coll = channel.getKeywordCollection();
		List keyword_list = keyword_coll.getKeywordList(null, null, page_info);
		page_info.init();
		setTemplateVariable("keyword_list", keyword_list);
		setTemplateVariable("page_info", page_info);
	}
}
