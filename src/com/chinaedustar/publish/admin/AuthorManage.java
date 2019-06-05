package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import java.util.List;
import com.chinaedustar.publish.model.*;

/**
 * 作者管理的数据提供者类。
 * 
 * @author liujunxing
 * @jsppage
 *  <li>admin_author_list.jsp
 *  <li>admin_author_add.jsp
 */
public class AuthorManage extends AuthorManageBase {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public AuthorManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * 初始化 admin_author_list.jsp 页面所需的数据。
	 * @initdata 
	 *  <li>container(channel) - 作者容器对象，一般是频道。
	 *  <li>channel_list - 频道列表。
	 *  <li>author_list - List&lt;Author&gt; 作者列表。
	 *  <li>page_info - 分页信息。
	 */
	public void initListPage() {
		super.provideChannelAndList();
		
		// initAuthorList 数据。
		initAuthorList();
	}

	/**
	 * 初始化 admin_author_add.jsp 页面所需的数据。
	 *
	 */
	public void initEditPage() {
		super.provideChannelAndList();
		
		// author 数据。
		Author author = null;
		int authorId = super.safeGetIntParam("authorId", 0);
		if (authorId == 0) {
			author = new Author();
			author.setChannelId(channel.getChannelId());
		} else {
			author = channel.getAuthorCollection().getAuthor(authorId);
		}
		setTemplateVariable("author", author);
		// <pub:data var="author" provider="com.chinaedustar.publish.admin.AuthorDataProvider" />
	}

	/**
	 * 初始化 admin_item_author.jsp 页面数据.
	 *
	 */
	public void initChoosePage() {
		this.channel = super.getChannelData();
		if (channel == null) return;
		setTemplateVariable("channel", channel);

		// 作者的列表集合
		initAuthorList();
	}
	
	@SuppressWarnings("rawtypes")
	private void initAuthorList() {
		// <pub:data var="authors" provider="com.chinaedustar.publish.admin.ChannelAuthorsDataProvider" />
		AuthorCollection author_coll = channel.getAuthorCollection();
		// 作者的类型，1：大陆作者；2：港台作者；3：海外作者；4：本站特约；5：其它作者；0：不区分作者，默认为0。
		int authorType = super.safeGetIntParam("authorType", 0);
		// 搜索的字段，为 null 或者 "" 的时候不处理。
		String field = super.safeGetStringParam("field", null);
		if (field != null && field.length() > 0) {
			if ("name".equalsIgnoreCase(field))	field = "name";
			else if ("address".equalsIgnoreCase(field)) field = "address";
			else if ("phone".equalsIgnoreCase(field)) field = "phone";
			else if ("description".equalsIgnoreCase(field)) field = "description";
			else field = null;
		}
		// 搜索的关键字，与 field 同时出现。
		String keyword = super.safeGetChineseParameter("keyword", null);
		
		PaginationInfo page_info = getPaginationInfo();
		page_info.setItemName("个作者");

		// 查询作者数据，设置 author_list, page_info 数据。
		List author_list = author_coll.getAuthorList(authorType, field, keyword, page_info);
		page_info.init();
		setTemplateVariable("author_list", author_list);
		setTemplateVariable("page_info", page_info);
	}
}
