package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import java.util.List;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.model.*;

/**
 * Article 管理的其它各个页面的数据提供。
 * 
 * @author liujunxing
 *
 * @jsppage 使用的页面。
 *  <li> admin_article_add.jsp
 *  <li> admin_article_list.jsp
 *  <li> admin_article_approv.jsp
 *  <li> admin_article_generate.jsp
 *  <li> admin_special_article_list.jsp - 专题文章管理页面。
 *  <li> admin_recycle_article_list.jsp - 回收站管理。
 */
public class ArticleManage extends AbstractItemManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public ArticleManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_article_add.jsp 页面使用的数据的初始化。
	 *
	 */
	public void initEditPage() {
		// 获得当前 Channel 对象。
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		  
		// 获得文章对象。
		Article article = getChannelArticleData();
		setTemplateVariable("article", article);
		if (article.getId() != 0) {
			// 修改文章的时候要验证权限, 为修改文章需要在栏目上有录入权。
			Column column = channel.getColumnTree().getColumn(article.getColumnId());
			if (column == null) column = channel._getCreateRootColumn();
			if (false == admin.checkColumnRight(channel.getId(), column, 
					Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_INPUTER)) {
				throw super.accessDenied();
			}
		}

		// 编辑页其它数据。
		super.initEditPageCommonData();
	}
	
	/**
	 * admin_article_view.jsp 页面使用的数据的初始化。
	 * TODO: 当前临时使用 initEditPage 的
	 *
	 */
	public void initViewPage() {
		// 获得当前 Channel 对象。
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 获得文章对象。
		Article article = getChannelArticleData();
		if (article.getId() == 0) 
			throw new PublishException("未能找到指定标识的" + channel.getItemName() + ", 请确定您是从有效链接进入的。");
		setTemplateVariable("article", article);
		
		// 验证权限, 为查看此文章，需要至少 Column.View 权限。
		Column column = channel.getColumnTree().getColumn(article.getColumnId());
		if (column == null) column = channel._getCreateRootColumn();
		if (false == admin.checkColumnRight(channel.getId(), column, 
				Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_VIEW)) {
			throw super.accessDenied();
		}
		setTemplateVariable("column", column);
		
		// 修改此文章需要有 inputer 权限。
		boolean can_modify = admin.checkColumnRight(channel.getId(), column, 
				Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_INPUTER);
		setTemplateVariable("can_modify", can_modify);
		// 删除,移动,取消审核,退稿等操作需要有 editor 权限。
		boolean can_editor = admin.checkColumnRight(channel.getId(), column, 
				Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_EDITOR);
		setTemplateVariable("can_editor", can_editor);
		// 回收站操作需要有管理权。
		boolean can_manage = admin.checkColumnRight(channel.getId(), column, 
				Admin.TARGET_COLUMN, Admin.COLUMN_RIGHT_MANAGE);
		setTemplateVariable("can_manage", can_manage);
		
		// 获得此频道下专题列表。（包括：全站专题、当前频道专题。）
		Object channel_specials = getChannelSpecialDataTable(channel);
		setTemplateVariable("channel_specials", channel_specials);
	}
	
	/**
	 * admin_article_approv.jsp 页面使用的数据的初始化。
	 * @initdata 初始化的数据包括
	 *  <li>util - 辅助对象，提供文章一些属性支持。(测试用)
	 *  <li>channel - 当前频道。
	 *  <li>article_list - 需要显示的文章的集合。
	 *  <li>page_info - 文章列表的分页信息。
	 *  <li>dropdown_columns - 频道下拉列表数据。
	 *  <li>column_nav - 自根栏目开始到当前栏目的导航列表数据。
	 */
	public void initApprovPage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getApprovOption();
		initByQueryOption(query_option);
	}

	/**
	 * admin_article_generate.jsp 页面使用的数据的初始化。
	 * @initdata
	 *  <li>channel - 当前频道。
	 *  <li>article_list - 需要显示的文章的集合。
	 *  <li>dropdown_columns - 频道下拉列表数据。
	 *  <li>column_nav - 自根栏目开始到当前栏目的导航列表数据。
	 *  <li>
	 */
	public void initGeneratePageData() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getGenerateOption();
		initByQueryOption(query_option);
	}

	/**
	 * admin_article_list.jsp 页面使用的数据的初始化。
	 * @initdata 
	 *  <li>channel - 当前频道。
	 *  <li>dropdown_columns - 定义下拉栏目列表的数据。
	 *  <li>column_nav - 栏目层次结构的 List&lt;Column&gt; 数据。
	 *  <li>article_list - 文章列表，是一个 DataTable.
	 *  <li>page_info - 分页信息。
	 */
	public void initListPage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getItemQueryOption();
		initByQueryOption(query_option);
	}

	/**
	 * admin_article_my.jsp 页面使用的数据的初始化。
	 *
	 */
	public void initMyArticlePage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getItemQueryOption();
		query_option.inputer = super.admin.getAdminName();		// 强迫作者 = 当前管理员。
		
		initByQueryOption(query_option);
	}

	/**
	 * admin_special_article_list.jsp 页面使用的数据的初始化。
	 * 
	 */
	public void initSpecialArticleListPage() {
		// 根据 Request 的 channelId 获取 Channel
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 获得当前专题下的需要显示的文章的集合，同时得到分页信息。
		ItemQueryOption option = getItemQueryOption();
		applyOptionToRequest(option);
		PaginationInfo page_info = getPaginationInfo();
		SpecialItemQuery query_exec = new SpecialItemQuery(pub_ctxt);
		DataTable article_list = query_exec.getSpecialArticles(option, page_info);
		addColumnNameField(article_list);
		page_info.init();
		setTemplateVariable("article_list", article_list);
		setTemplateVariable("page_info", page_info);

		// 得到当前频道的所有专题。
		List<SpecialWrapper> special_list = channel.getSpecialCollection().getSpecialList();
		setTemplateVariable("special_list", special_list);
	}

	/**
	 * admin_recycle_article_list.jsp 页面使用的数据的初始化。
	 *
	 */
	public void initRecyclePage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();
		
		// 构造数据。
		ItemQueryOption query_option = super.getRecycleOption();
		initByQueryOption(query_option);
	}

	/** 通过给出的 query_option 初始化页面数据。 */
	private void initByQueryOption(ItemQueryOption query_option) {
		// 构造数据。
		// request 的 Parameter 中需要 channelId, [columnId], [status], [isTop], 
		//   [isElite], [isHot], [isCommend], [inputer], [field], [keyWord]。
		PaginationInfo page_info = getPaginationInfo();
		super.applyOptionToRequest(query_option);

		// 进行实际查询。
		ItemBusinessObject ibo = new ItemBusinessObject(pub_ctxt);
		DataTable article_list = ibo.getItemDataTable(query_option, page_info);
		page_info.init();
		
		// 添加一个计算域: columnName
		// TODO: 增加的下面的 columName 计算字段也许应该预先计算好放入 DataTable 中。
		ThreadCurrentMap.current();  // 强迫 ThreadCurrentMap 建立一个 Map
		article_list.addCalcField("columnName", new ColumnNameFieldCalc(channel));
		setTemplateVariable("article_list", article_list);
		setTemplateVariable("page_info", page_info);
	}

	/**
	 * 获得 admin_article_add.jsp 页面的文章对象。
	 *   如果是新建文章，则产生一个默认属性的文章对象。
	 * @param channel - 频道对象。
	 * @return
	 */
	private Article getChannelArticleData() {
		// 得到当前栏目，如果 columnId == 0 则使用根栏目。
		Column column = super.getColumnData();
		if (column == null) throw new PublishException("不正确的栏目标识，请确定您是从有效的链接地址进入的。");

		// 如果 articleId == 0 表示新增一个文章。
		int articleId = super.safeGetIntParam("articleId", 0);
		if (articleId == 0)
			return newArticleObject(column.getId());

		Article article = column.getArticleItem(articleId);
		if (article == null) throw new PublishException("无法找到指定标识的文章对象，请确定您是从有效链接地址进入的。");
		return article;
		// 
		/*
			Column column = columnTree.getSimpleColumn(columnId);
			article = (Article)column.loadItem(articleId);
			String uploadFiles = article.getUploadFiles();
			if (uploadFiles != null && uploadFiles.trim().length() > 1) {
				String[] str_files = uploadFiles.split("\\|");
				ArrayList<UpFile> files = new ArrayList<UpFile>();
				String picExt = "jpg,jpeg,gif,png,bmp,raw,tif,tiff,jpe";
				for (int i = 0; i < str_files.length; i++) {
					String str_id = str_files[i].trim();
					if (str_id.length() > 0) {
						int fileId = Integer.parseInt(str_id);
						if (fileId > 0) {
						UpFile file = channel.getUpFileCollection().loadUpFile(fileId);
						System.out.println("File id is " + fileId + ". File ext is " + file.getFileExt());
						if (file != null && picExt.indexOf((file.getFileExt() + "").toLowerCase()) != -1) {
							Site site = web_ctxt.getPublishContext().getSite();
							file.setFilePath((site.getInstallDir() + channel.getChannelDir() + "/" + channel.getUploadDir() + "/" +  file.getFilePath()).replaceAll("\\\\", "/"));
							files.add(file);
							}
						}
					}
				}
				if (!files.isEmpty()) {
					web_ctxt.setTemplateVariable("picList", files);
				}
			}
		}
		web_ctxt.setTemplateVariable(name, article, scope);
		
		*/
	}
	
	/** 创建一个新的 article 对象，并设置所属频道、栏目标识。 */
	private Article newArticleObject(int columnId) {
		Article article = new Article();
		article.setChannelId(channel.getId());
		article.setColumnId(columnId);
		article.setCreateTime(new java.util.Date());
		article.setInputer(admin.getAdminName());
		article.setStars(3);
		article.setCommentFlag(1);
		return article;
	}

}
