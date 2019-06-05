package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.comp.TreeDataTable;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 自定义页面集合对象。
 * 
 * @author liujunxing
 */
@SuppressWarnings({"rawtypes", "unused"})
public class WebPageCollection extends AbstractPublishModelBase implements PublishModelObject {
	/** 表格名。 */
	private static final String THE_TABLENAME = "WebPage";
	
	/** 表格代替名。 */
	private static final String THE_ALIAS = "wp";
	
	/**
	 * 获得当前对象下的网站自定义页面集合。
	 * @return
	 */
	public List<WebPage> getWebPageList() {
		return this.getWebPageList(0);		// TODO: 0 - channelId
	}
	
	/**
	 * 获得指定频道下的网站自定义页面集合。
	 * @return
	 */
	public List<WebPage> getWebPageList(int channelId) {
		// 获得根自定义页面。
		WebPage root_page = getRootWebPage(channelId);
		
		// 构造查询。
		DataAccessObject dao = _getPublishContext().getDao();
		TreeViewModel tree_oper = getTreeViewModel();
		TreeViewQueryObject queryObj = tree_oper.getChildrensQuery(root_page.getId());
		String hql = "FROM WebPage AS wp " + 
					 " WHERE " + queryObj.getWhere() +
					 " ORDER BY " + queryObj.getOrder();
		
		// 查询数据。
		@SuppressWarnings("unchecked")
		List<WebPage> webpage_list = (List<WebPage>)dao.list(hql);
		
		// 初始化并返回。 TODO: 添加更多有用的自定义数据。
		PublishUtil.initModelList(webpage_list, _getPublishContext(), this);
		return webpage_list;
	}

	// === 业务方法 ===============================================================
	
	/**
	 * 获得指定标识的网站自定义页面对象。
	 * @param id - 对象标识。
	 * @return 返回该标识的自定义页面。如果不存在则返回 null.
	 */
	public WebPage getWebPage(int id) {
		WebPage web_page = (WebPage)_getPublishContext().getDao().get(WebPage.class, id);
		if (web_page != null)
			web_page._init(_getPublishContext(), this);
		return web_page;
	}
	
	/**
	 * 添加或更新一个自定义网页。
	 * @param web_page - 自定义网页对象。
	 */
	public void save(WebPage web_page) {
		if (web_page.getId() == 0)
			insertWebPage(web_page);
		else
			updateWebPage(web_page);
	}
	
	/** 新建一个自定义页面。 */
	public void insertWebPage(WebPage web_page) {
		// 得到父节点，如果 parentId = 0 则使用缺省根。
		int parentId = web_page.getParentId();
		WebPage parent_node = getWebPageOrRoot(parentId, web_page.getChannelId());
		if (parent_node == null)
			throw new PublishException("指定标识的父节点不存在。");
				
		// 得到新页面的 ParentPath, OrderPath 信息。channelId, parentId 确保其正确。
		TreeViewModel tree_oper = getTreeViewModel();
		TreeItemObject tree_item = tree_oper.addTreeNode(parent_node);
		web_page.setChannelId(parent_node.getChannelId());
		web_page.setParentId(parent_node.getId());
		web_page.setParentPath(tree_item.getParentPath());
		web_page.setOrderPath(tree_item.getOrderPath());
		
		// 保存自定义页面。
		_getPublishContext().getDao().insert(web_page);
		
		web_page._init(_getPublishContext(), this);
	}
	
	/** 更新现有的一个自定义页面。可能产生移动。 */
	public void updateWebPage(WebPage web_page) {
		// 得到原来的 WebPage 对象，部分数据不允许这里修改。
		WebPage origin_page = this.getWebPage(web_page.getId());
		if (origin_page == null) 
			throw new PublishException("标识为 " + web_page.getId() + " 的网站自定义页面不存在。");
		
		// 不允许移动 channelId, 即使移动，不是在这里完成。
		web_page.setChannelId(origin_page.getChannelId());
		if (web_page.getParentId() == 0) {
			// == 0 表示以根页面做为其父页面。
			WebPage root_page = this.getRootWebPage(origin_page.getChannelId());
			web_page.setParentId(root_page.getId());
		}
		// move_parent 表示此次修改要移动这个对象。如果为真，记录下来，一会更新之后进行移动。
		boolean move_parent = web_page.getParentId() != origin_page.getParentId();
		int new_parent_id = web_page.getParentId();	// 新的父对象标识。
		web_page.setParentId(origin_page.getParentId());
		web_page.setParentPath(origin_page.getParentPath());
		web_page.setOrderPath(origin_page.getOrderPath());
		// ??? 其它不能更新的字段
		
		// 更新自定义页面。
		pub_ctxt.getDao().update(web_page);
		// 更新扩展属性。
		web_page.getExtends().update();
		
		// 如果需要移动，则进行移动操作。
		if (move_parent) {
			// TODO: move
			throw new PublishException("WebPageCollection move parent unimplement.");
		}
		
		web_page._init(pub_ctxt, this);
	}

	/** 管理中要获得核心字段。 */
	public static final String WEBPAGE_CORE_FIELDS = 
			"id, name, parentId, parentPath, orderPath";
	
	/** 管理时用到的字段, 额外增加了 标题、生成标志字段 。 */
	public static final String WEBPAGE_MANAGE_FIELDS =
			WEBPAGE_CORE_FIELDS +
			", title, isGenerated";
	
	/**
	 * 获得用于管理中 WebPage 列表或下拉列表中的数据，其仅提供有限的字段。
	 * @return 返回具有 schema[id, name, parentId, parentPath, orderPath] 的 DataTable.
	 */
	public DataTable getWebPageDataTable(String select_fields) {
		// 获得根节点，计算从根节点查询其所有子节点所需查询条件(query_obj)。
		WebPage root_page = this.getRootWebPage(0);	// TODO: 0 变成当前频道标识
		TreeViewModel tree_oper = getTreeViewModel();
		TreeViewQueryObject query_obj = tree_oper.getChildrensQuery(root_page, true);
		
		// 使用 QueryHelper 帮助进行查询，查询结果为一个 List<Object[]>.
		QueryHelper qh = new QueryHelper();
		if (select_fields == null || select_fields.length() == 0)
			select_fields = WEBPAGE_CORE_FIELDS;
		qh.selectClause = "SELECT " + select_fields;
		qh.fromClause = " FROM WebPage AS wp ";
		qh.whereClause = " WHERE " + query_obj.getWhere();
		qh.orderClause = " ORDER BY " + query_obj.getOrder();
		
		List list = qh.queryData(pub_ctxt.getDao());
		
		// 组装为全功能 ColumnDataTable, 其为支持树结构进行了增强。
		TreeDataTable data_table = new TreeDataTable(new DataSchema(select_fields));
		for (int i = 0; i < list.size(); ++i) {
			data_table.addRow(data_table.newRow((Object[])list.get(i)));
		}
		
		return data_table;
	}
	
	// === 实现辅助 ===============================================================
	
	/** 得到一个 TreeViewModel 的新实例。 */
	private final TreeViewModel getTreeViewModel() {
		TreeViewModel tree_oper = new TreeViewModel(THE_TABLENAME, THE_ALIAS, _getPublishContext().getDao());
		return tree_oper;
	}
	
	/** 被缓存起来的根对象。 */
	private WebPage cached_root_page;
	
	/**
	 * 获得根页面，树型结构都含有一个隐含的根，此函数负责得到这个隐含的根，如果不存在，则创建这个隐含的根。
	 * @param channelId - 频道标识。
	 * @return 返回根自定义页面。此页面对用户不可见。
	 */
	private final WebPage getRootWebPage(int channelId) {
		// 如果有缓存的根对象，且所属频道相同，则直接返回这个缓存对象。
		if (cached_root_page != null && cached_root_page.getChannelId() == channelId)
			return cached_root_page;
		
		// 尝试装载。条件为 频道标识确定，parentId = 0(表示根节点)
		String hql = "FROM WebPage WHERE (channelId = " + channelId + ") AND (parentId = 0)";
		WebPage root_page = (WebPage)PublishUtil.executeSingleObjectQuery(_getPublishContext().getDao(), hql);

		if (root_page == null) {
			// 如果没有则现在立刻创建一个。
			root_page = new WebPage();
			root_page.setChannelId(channelId);
			root_page.setName("根页面");
			root_page.setDescription("这是系统自动创建的自定义页面的根，不要修改和删除这个记录。");
			// 设置 parentId, parentPath, orderPath, 应该设置成多少值从 TreeViewModel 中获得。
			TreeItemObject item_obj = getTreeViewModel().addRootTreeNode();
			root_page.setParentId(item_obj.getParentId());
			root_page.setParentPath(item_obj.getParentPath());
			root_page.setOrderPath(item_obj.getOrderPath());
			_getPublishContext().getDao().insert(root_page);
		}
		
		// 缓存并返回。
		root_page._init(_getPublishContext(), this);
		this.cached_root_page = root_page;
		return root_page;
	}
	
	/**
	 * 获得指定标识的 WebPage 对象。如果 webpageId = 0 则返回隐含的根自定义页面。
	 * @param webpageId
	 * @return
	 */
	private WebPage getWebPageOrRoot(int webpageId, int channelId) {
		if (webpageId == 0)
			return this.getRootWebPage(channelId);
		else
			return this.getWebPage(webpageId);
	}
}
