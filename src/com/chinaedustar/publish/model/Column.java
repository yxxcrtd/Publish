package com.chinaedustar.publish.model;

import java.util.ArrayList;
import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.itfc.ShowPathSupport;
import com.chinaedustar.publish.util.UpdateHelper;

/**
 * 表示一个栏目
 *
 * <p>
 * 关于栏目创建过程：
 *  1、栏目创建页面为 admin_column_add.jsp，里面填写栏目各个属性。
 *  2、页面提交给 admin_column_action.jsp，command=save。然后转给 ColumnAction.
 *  3、ColumnAction.save() 进行栏目数据收集，组装为 Column 对象，然后调用
 *    ColumnTree.insert() 方法创建栏目。
 *  4、ColumnTree.insert() 检查栏目合法性，创建栏目记录。
 *  5、ColumnAction 更新栏目所在频道 js 静态文件。
 * </p>
 */
@SuppressWarnings({"rawtypes","unused"})
public class Column extends AbstractPageModelBase implements TreeItemInterface, ShowPathSupport {
	
	/** 对象的名称。 */
	public static final String THE_TABLENAME = "Column";
	
	/** 对象的别名。 */
	public static final String THE_ALIAS = "Column";
	
	/** 项目模块 **/
	public static final int ARTICLE_MODULE = 1;
	
	/** 图片模块 */
	public static final int PHOTO_MODULE = 2;
	
	/** 下载模块 */
	public static final int SOFT_MODULE = 3;
	
	/**
	 * 所属的模块。Column.ARTICLE_MODULE：项目模块；
	 * Column.PHOTO_MODULE：图片模块；Column.PICTURE_MODULE：图片模块。
	 */
	private int belongToModule = 0;
	
	/** 栏目树对象。 */
	private ColumnTree column_tree;
	
	/** 栏目的父对象，其可能是一个栏目，或是频道。(在一般构建过程中此字段可能不填写) */
	private ModelObject parent;

	// fields
	/** 栏目所属频道的标识。 */
	private int channelId;
	
	/** 父栏目的标识，没有父栏目则为0。 */
	private int parentId;
	
	/** 树形结构的父节点全路径，格式为 /1/12/ 。 */
	private java.lang.String parentPath;
	
	/** 树形结构的排序全路径，各式为 ./0001/0010/ 。 */
	private java.lang.String orderPath;
	
	/** 栏目的简要提示，不支持html和标签。 */
	private java.lang.String tips;
	
	/** 栏目的详细说明，支持html和标签。 */
	private java.lang.String description;
	
	/** 栏目类型：内部 – 0。 */
	public static final int COLUMN_TYPE_INTERNAL = 0;
	/** 栏目类型：外部 – 1。 */
	public static final int COLUMN_TYPE_EXTERNAL = 1;
	
	/** 栏目类型：内部 – 0；外部 – 1；？其它。 */
	private int columnType;
	
	/** 栏目的英文名字，同时也是数据存储的路径名。严格限制使用英文。 */
	private java.lang.String columnDir;
	
	/** 外部栏目。外部栏目指链接到本系统以外的地址中。当此栏目准备链接到网站中的其他系统时，请使用这种方式。不能在外部栏目中添加项目，也不能添加子栏目。 */
	private String linkUrl;
	
	/** 打开方式：1：在新窗口打开；0：在原窗口打开。 */
	private int openType;
	
	/** 是否在顶部导航栏显示：此选项只对一级栏目有效。 */
	private boolean showOnTop;
	
	/** 是否在频道首页分类列表处显示：此选项只对一级栏目有效。如果一级栏目比较多，但首页不想显示太多的分类列表，这个选项就非常有用。 */
	private boolean showOnIndex;
	
	/** 是否在父栏目的分类列表处显示：如果某栏目下有几十个子栏目，但只想显示其中几个子栏目的项目列表，这个选项就非常有用。 */
	private boolean isElite;
	
	/** 有子栏目时是否可以在此栏目添加项目。 */
	private boolean enableAdd;
	
	/** 是否启用此栏目的防止复制、防盗链功能。 */
	private boolean enableProtect;
	
	/** 每页显示的项目数。 */
	private int maxPerPage = 20;
	
	/** 此栏目下的项目的默认模板。 */
	private int defaultItemTemplate;
	
	/** 此栏目下的项目的默认配色风格。 */
	private int defaultItemSkin;
	
	/** 此栏目下的项目列表的排序方式，1：项目ID（降序）；2：项目ID（升序）；3：更新时间（降序）；4：更新时间（升序）；5：点击次数（降序）；6：点击次数（升序）。 */
	private int itemListOrderType;
	
	/** 此栏目下的项目打开方式，0：在原窗口打开；1：在新窗口打开。 */
	private int itemOpenType;

	// ========================================================================

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString() {
		StringBuilder strbuf = new StringBuilder();
		strbuf.append("publish.model.Column{id = ").append(this.getId())
		   .append(", name = ").append(this.getName())
		   .append(", parentId = ").append(this.getParentId())
		   .append(", channelId = ").append(this.getChannelId()).append("}");
		return strbuf.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.itfc.PublishModelObject)
	 */
	@Override public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super._init(pub_ctxt, owner_obj);
		if (owner_obj instanceof ColumnTree) {
			this.column_tree = (ColumnTree)owner_obj;
		} else if (owner_obj instanceof Column) {
			this.column_tree = ((Column)owner_obj).getColumnTree();
		} else if (owner_obj instanceof Channel) {
			this.column_tree = ((Channel)owner_obj).getColumnTree();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_destroy()
	 */
	@Override public void _destroy() {
		this.column_tree = null;
		this.parent = null;
		super._destroy();
	}

	/**
	 * 得到栏目对象所属的栏目树对象。
	 * @return
	 */
	public ColumnTree getColumnTree() {
		return this.column_tree;
	}
	
	/**
	 * 设置此栏目所属的栏目树对象。
	 * @param column_tree
	 */
	public void setColumnTree(ColumnTree column_tree) {
		this.column_tree = column_tree;
	}
	
	/**
	 * 返回此栏目所在的频道。
	 * TODO: 做得更精确一点。
	 */
	public Channel getChannel() {
		// 如果有栏目树对象，且其所属频道标识和栏目里面记录的频道标识相同，则返回。
		if (this.column_tree != null && this.column_tree.getChannel().getId() == this.getChannelId())
			return this.column_tree.getChannel();
		
		if (pub_ctxt == null) return null;
		Channel channel = pub_ctxt.getSite().getChannel(this.getChannelId());
		if (channel != null && this.column_tree == null)
			this.column_tree = channel.getColumnTree();
		return channel;
	}

	// ========================================================================
	
	/**
	 * 返回所属的频道标识。
	 */
	public int getChannelId () {
		return channelId;
	}

	/**
	 * Set 所属的频道标识。外键关联到Cor_Channel表。
	 * @param channelId the ChannelId value
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * Return 父栏目的标识。如果没有父栏目，则此值为 0 。
	 */
	public int getParentId () {
		return parentId;
	}

	/**
	 * Set 父栏目的标识。如果没有父栏目，则此值为 0 。
	 * @param parentId the ParentId value
	 */
	public void setParentId (int parentId) {
		this.parentId = parentId;
	}



	/**
	 * Return 树形结构的父节点全路径，格式为 /1/12/ 。
	 */
	public String getParentPath () {
		return this.parentPath;
	}
	
	/**
	 * [计算出来的] 获得此栏目的深度，通过 parentPath(/1/12/) 可以方便的计算出深度。根栏目深度 = 0。
	 * @return
	 */
	public int getDepth() {
		return TreeViewModel.calcPathDepth(parentPath);
	}

	/**
	 * Set 树形结构的父节点全路径，格式为 /1/12/ 。
	 * @param parentPath the ParentPath value
	 */
	public void setParentPath (java.lang.String parentPath) {
		this.parentPath = parentPath;
	}

	/**
	 * Return 树形结构的排序全路径，各式为 ./0001/0010/ 。
	 */
	public String getOrderPath () {
		return orderPath;
	}

	/**
	 * Set 树形结构的排序全路径，各式为 ./0001/0010/ 。
	 * @param orderPath the OrderPath value
	 */
	public void setOrderPath (java.lang.String orderPath) {
		this.orderPath = orderPath;
	}



	/**
	 * Return 栏目的简要提示，不支持html和标签。
	 */
	public String getTips () {
		return tips;
	}

	/**
	 * Set 栏目的简要提示，不支持html和标签。
	 * @param tips the Tips value
	 */
	public void setTips (java.lang.String tips) {
		this.tips = tips;
	}



	/**
	 * Return 栏目的详细说明，支持html和标签。
	 */
	public String getDescription () {
		return description;
	}

	/**
	 * Set 栏目的详细说明，支持html和标签。
	 * @param description the Description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return 栏目类型：内部 – 0；外部 – 1；？其它。
	 */
	public int getColumnType () {
		return columnType;
	}

	/**
	 * Set 栏目类型：内部 – 0；外部 – 1；？其它。
	 * @param columnType the ColumnType value
	 */
	public void setColumnType (int columnType) {
		this.columnType = columnType;
	}
	
	/**
	* 所属的模块。Column.ARTICLE_MODULE：项目模块；
	* Column.PHOTO_MODULE：图片模块；Column.PICTURE_MODULE：图片模块。
	*/
	public int getBelongToModule() {
		if (this.belongToModule == 0) {
			initBelongToModule();
		}
		return this.belongToModule;
	}
	
	
	/**
	 * Return 栏目的英文名字，同时也是数据存储的路径名。
	 */
	public String getColumnDir () {
		return columnDir;
	}

	/**
	 * Set 栏目的英文名字，同时也是数据存储的路径名。
	 * @param columnDir the ColumnDir value
	 */
	public void setColumnDir (java.lang.String columnDir) {
		this.columnDir = columnDir;
	}
	
	/**
	 * 此栏目下的项目的默认配色风格。
	 * @return
	 */
	public int getDefaultItemSkin() {
		return defaultItemSkin;
	}
	
	/**
	 * 此栏目下的项目的默认配色风格。
	 * @param defaultItemSkin
	 */
	public void setDefaultItemSkin(int defaultItemSkin) {
		this.defaultItemSkin = defaultItemSkin;
	}

	/**
	 * 此栏目下的项目的默认模板。
	 * @return
	 */
	public int getDefaultItemTemplate() {
		return defaultItemTemplate;
	}
	
	/**
	 * 此栏目下的项目的默认模板。
	 * @param defaultItemTemplate
	 */
	public void setDefaultItemTemplate(int defaultItemTemplate) {
		this.defaultItemTemplate = defaultItemTemplate;
	}
	
	/**
	 * 有子栏目时是否可以在此栏目添加项目。
	 * @return
	 */
	public boolean getEnableAdd() {
		return enableAdd;
	}
	
	/**
	 * 有子栏目时是否可以在此栏目添加项目。
	 * @param enableAdd
	 */
	public void setEnableAdd(boolean enableAdd) {
		this.enableAdd = enableAdd;
	}
	
	/**
	 * 是否启用此栏目的防止复制、防盗链功能。
	 * @return
	 */
	public boolean isEnableProtect() {
		return enableProtect;
	}
	
	/**
	 * 是否启用此栏目的防止复制、防盗链功能。
	 * @param enableProtect
	 */
	public void setEnableProtect(boolean enableProtect) {
		this.enableProtect = enableProtect;
	}
	
	/**
	 * 是否在父栏目的分类列表处显示：如果某栏目下有几十个子栏目，但只想显示其中几个子栏目的项目列表，这个选项就非常有用。
	 * @return
	 */
	public boolean getIsElite() {
		return isElite;
	}

	/**
	 * 是否在父栏目的分类列表处显示：如果某栏目下有几十个子栏目，但只想显示其中几个子栏目的项目列,表，这个选项就非常有用。
	 * @param isElite
	 */
	public void setIsElite(boolean isElite) {
		this.isElite = isElite;
	}
	
	/**
	 * 此栏目下的项目列表的排序方式，1：项目ID（降序）；2：项目ID（升序）；3p：更新时间（降序）；4：更新时间（升序）；5：点击次数（降序）；6：点击次数（升序）。
	 * @return
	 */
	public int getItemListOrderType() {
		return itemListOrderType;
	}
	
	/**
	 * 此栏目下的项目列表的排序方式，1：项目ID（降序）；2：项目ID（升序）；3：更新时间（降序）；4：更新时间（升序）；5：点击次数（降序）；6：点击次数（升序）。
	 * @param itemListOrderType
	 */
	public void setItemListOrderType(int itemListOrderType) {
		this.itemListOrderType = itemListOrderType;
	}
	
	/**
	 * 此栏目下的项目打开方式，0：在原窗口打开；1：在新窗口打开。
	 * @return
	 */
	public int getItemOpenType() {
		return itemOpenType;
	}
	
	/**
	 * 此栏目下的项目打开方式，0：在原窗口打开；1：在新窗口打开。
	 * @param itemOpenType
	 */
	public void setItemOpenType(int itemOpenType) {
		this.itemOpenType = itemOpenType;
	}
	
	/**
	 * 外部栏目。外部栏目指链接到本系统以外的地址中。当此栏目准备链接到网站中的其他系统时，请使用这种方式。不能在外部栏目中添加项目，也不能添加子栏目。
	 * @return
	 */
	public String getLinkUrl() {
		return linkUrl;
	}
	
	/**
	 * 外部栏目。外部栏目指链接到本系统以外的地址中。当此栏目准备链接到网站中的其他系统时，请使用这种方式。不能在外部栏目中添加项目，也不能添加子栏目。
	 * @param linkUrl
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	/**
	 * 每页显示的项目数。
	 * @return
	 */
	public int getMaxPerPage() {
		return maxPerPage;
	}
	
	/**
	 * 每页显示的项目数。
	 * @param maxPerPage
	 */
	public void setMaxPerPage(int maxPerPage) {
		this.maxPerPage = maxPerPage;
	}
	
	/**
	 * 打开方式：1：在新窗口打开；0：在原窗口打开。
	 * @return
	 */
	public int getOpenType() {
		return openType;
	}
	
	/**
	 * 打开方式：1：在新窗口打开；0：在原窗口打开。
	 * @param openType
	 */
	public void setOpenType(int openType) {
		this.openType = openType;
	}
	
	/**
	 * 是否在频道首页分类列表处显示：此选项只对一级栏目有效。如果一级栏目比较多，但首页不想显示太多的分类列表，这个选项就非常有用。
	 * @return
	 */
	public boolean getShowOnIndex() {
		return showOnIndex;
	}
	
	/**
	 * 是否在频道首页分类列表处显示：此选项只对一级栏目有效。如果一级栏目比较多，但首页不想显示太多的分类列表，这个选项就非常有用。
	 * @param showOnIndex
	 */
	public void setShowOnIndex(boolean showOnIndex) {
		this.showOnIndex = showOnIndex;
	}
	
	/**
	 * 是否在顶部导航栏显示：此选项只对一级栏目有效。
	 * @return
	 */
	public boolean getShowOnTop() {
		return showOnTop;
	}
	
	/**
	 * 是否在顶部导航栏显示：此选项只对一级栏目有效。
	 * @param showOnTop
	 */
	public void setShowOnTop(boolean showOnTop) {
		this.showOnTop = showOnTop;
	}	
	
	// === ModelObject 实现 ========================================================
	
	/**
	 * 返回对父栏目的引用，没有则返回null。
	 * (注意：当前这个方法不一定能够获得栏目的父栏目，父栏目字段可能没有设置)
	 */
	@Override public ModelObject getParent() {
		// 如果已经有了则返回。
		if (this.parent != null) return this.parent;
		
		Channel channel = this.getChannel();
		if (channel == null) return null;
		
		// 如果自己就是根栏目或父栏目是根栏目
		if (this.getId() == 0 || this.getParentId() == 0 || 
				this.getId() == channel.getRootColumnId() ||
				this.getParentId() == channel.getRootColumnId()) {
			this.parent = channel;
			return this.parent;
		}
		
		// 否则获得父栏目。
		this.parent = channel.getColumnTree().getColumn(this.getParentId());
		return this.parent;
	}
	
	/**
	 * 得到这个栏目的父栏目。
	 * @return
	 */
	public Column getParentColumn() {
		ModelObject parent = getParent();
		if (parent == null) return null;
		if (parent instanceof Column) return (Column)parent;
		return null;
	}
	
	/**
	 * 得到这个栏目的祖先第一级根栏目。
	 * @return
	 */
	public Column getAncestorColumn() {
		if (this.getDepth() <= 1) return this;
		Column the_column = this;
		while (true) {
			Column parent = the_column.getParentColumn();
			if (parent == null) return null;
			if (parent.getDepth() <= 1) return parent;
			the_column = parent;
		}
	}
	
	/**
	 * 设置父对象。
	 *
	 */
	public void setParent(ModelObject parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractModelBase#getUrlResolver()
	 */
	@Override public UrlResolver getUrlResolver() {
		return this.getChannel();
	}

	// === 业务方法 ==============================================================
	
	/**
	* 初始化栏目所属的模块属性。
	* @deprecated TODO 实现这些功能在 Module 类里面。
	*/		 
	@Deprecated
	private void initBelongToModule() {		
		//初始化belongToModule属性
		if (this.belongToModule == 0) {
			Channel channel = this.getChannel(); this.pub_ctxt.getSite().getChannels().getChannel(this.getChannelId());
			Module module = this.pub_ctxt.getSite().getModules().getModule(channel.getModuleId());
			if ("ArticleModule".equalsIgnoreCase(module.getTitle())) {
				this.belongToModule = Column.ARTICLE_MODULE;
			} else if ("PhotoModule".equalsIgnoreCase(module.getTitle())) {
				this.belongToModule = Column.PHOTO_MODULE;
			} else if("SoftModule".equalsIgnoreCase(module.getTitle())) {
				this.belongToModule = Column.SOFT_MODULE;
			}
		}
	}

	/**
	 * 得到当前栏目下的项目列表信息，分页实现。
	 * id, columnId, columnName, lastModified, title, author, inputer, editor, hits, stars, top, elite, includePic, status, keywords
	 * @param includeChildColumn 是否显示子孙栏目的项目。
	 * @param isDeleted 是否已经删除。
	 * @param status 项目的状态，所有项目：9；待审核的项目：0；已审核的项目：1。默认为null，不处理。
	 * @param isTop 固顶项目，默认为null，不处理。
	 * @param isElite 推荐项目，默认为null，不处理。
	 * @param isHot 热门项目，默认为null，不处理。 
	 * @param inputer 项目录入者，默认为null，不处理。
	 * @param page 当前的页码，默认为1。
	 * @param maxPerPage 每页最多记录数，默认为20。
	 * @param field 搜索选项域，分别为标题，内容，作者，录入者。
	 * @param keyWord 搜索选项关键字，默认为"关键字"。
	 * @param titleNum 显示标题字符数，默认为20,0为全取。
	 * @return 
	 */
	@Deprecated
	public DataTable getItemList_Deprecated(Boolean includeChildColumn, final Boolean isDeleted, final Integer status, final Boolean isTop, final Boolean isElite, final Boolean isHot, final Boolean isGenerated, final String inputer, final Integer page, final Integer maxPerPage, final String field, final String keyword, final Integer titleNum) {
		throw new UnsupportedOperationException();
		/*
		initBelongToModule();
		
		String strcolumnCause = "columnId ";
		if (includeChildColumn) {
			// 得到该栏目的子孙栏目的标识集合。	
			DataTable dt0 = null;
			dt0 = columnTree.getColumnsDropDown(super.getId());

			strcolumnCause += "In(" + super.getId() + "";	// SQL 中的 in 子句。
			if (dt0 != null && !dt0.isEmpty()) {
				for (int i = 0; i < dt0.size(); i++) {
					strcolumnCause += "," + dt0.get(i).get(0);
				}
			}
			strcolumnCause += ")";
		} else {
			strcolumnCause += "= " + super.getId();
		}
		
		final String temp_inString = strcolumnCause;
		final int hits = ((Channel)this.columnTree._getOwnerObject()).getHitsOfHot();
		final DataTable dt = newDataTableForItemList();
		pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback () {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder hql = new StringBuilder("select " + getSelectFieldsForItemList() + " from "
						+ getItemTypeName() + " where " + temp_inString + " and deleted=?");
				if (status != null && status != 9) {
					hql.append(" and status=:status");
				}
				if (isTop != null) {
					hql.append(" and top=:isTop");
				}
				if (isElite != null) {
					hql.append(" and elite=:isElite");
				}
				if (isHot != null && isHot == true) {
					hql.append(" and hits>=:hits");
				}
				if (isGenerated != null) {
					hql.append(" and isGenerated=:isGenerated");
				}
				if (inputer != null && inputer.trim().length() > 0) {
					hql.append(" and inputer=:inputer");
				}
				if (field != null && field.trim().length() > 0) {	
					hql.append(" and " + field + " like :keyword");
				}				
				hql.append(" order by id desc");
				
				// 得到记录的数据。
				Query query = session.createQuery(hql.toString());
				query.setBoolean(0, isDeleted);
				if (status != null && status != 9) {
					query.setInteger("status", status);
				}
				if (isTop != null) {
					query.setBoolean("isTop", isTop);
				}
				if (isElite != null) {
					query.setBoolean("isElite", isElite);
				}
				if (isHot != null) {
					query.setInteger("hits", hits);
				}
				if (isGenerated != null) {
					query.setBoolean("isGenerated", isGenerated);
				}
				if (inputer != null && inputer.trim().length() > 0) {
					query.setString("inputer", inputer);
				}
				if (field != null && field.trim().length() > 0) {	
					query.setString("keyword", "%" + keyword + "%");
				}				
				int i_page = 1;
				int i_maxPerPage = 20;
				if (page != null && page > 0) {
					i_page = page;
				}
				if (maxPerPage != null && maxPerPage > 0) {
					i_maxPerPage = maxPerPage;
				}
				query.setFirstResult((i_page - 1) * i_maxPerPage);
				query.setMaxResults(i_maxPerPage);
				fillDataTable(query.list(), dt, titleNum);
				return dt;
			}
		});
		return dt;
		*/
	}

	/**
	 * 得到当前栏目下的项目列表记录总数，必须与getArticleList()方法同步的拥有那些参数。
	 * @param includeChildColumn 是否包括子栏目。
	 * @param isDeleted 是否已经删除。
	 * @param status 项目的状态，所有项目：9；待审核的项目：0；已审核的项目：1。默认为null，不处理。
	 * @param isTop 固顶项目，默认为null，不处理。
	 * @param isElite 推荐项目，默认为null，不处理。
	 * @param isHot 热门项目，默认为null，不处理。 
	 * @param inputer 项目录入者，默认为null，不处理。
	 * @param field 搜索选项域，分别为标题，内容，作者，录入者。
	 * @param keyWord 搜索选项关键字，默认为"关键字"。
	 * @return
	 */
	@Deprecated
	public long getItemListCount_Deprecated(boolean includeChildColumn, final Boolean isDeleted, final Integer status, final Boolean isTop, final Boolean isElite, final Boolean isHot, final Boolean isGenerated, final String inputer, final String field, final String keyword) {
		throw new UnsupportedOperationException();
		/*
		String strcolumnCause = "columnId ";
		if (includeChildColumn) {
			// 得到该栏目的子孙栏目的标识集合。	
			DataTable dt0 = null;
			dt0 = columnTree.getColumnsDropDown(super.getId());

			strcolumnCause += "In(" + super.getId() + "";	// SQL 中的 in 子句。
			if (dt0 != null && !dt0.isEmpty()) {
				for (int i = 0; i < dt0.size(); i++) {
					strcolumnCause += "," + dt0.get(i).get(0);
				}
			}
			strcolumnCause += ")";
		} else {
			strcolumnCause += "= " + super.getId();
		}
		// 记录总数。
		final String temp_inString = strcolumnCause;
		final int hits = ((Channel)this.columnTree._getOwnerObject()).getHitsOfHot();
		return (Long)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder("select Count(*) from Item where " + temp_inString + " and deleted=:isDeleted");
				if (status != null && status != 9) {
					hql.append(" and status=:status");
				}
				if (isTop != null) {
					hql.append(" and top=:isTop");
				}
				if (isElite != null) {
					hql.append(" and elite=:isElite");
				}
				if (isHot != null && isHot == true) {
					hql.append(" and hits>=:hits");
				}
				if (isGenerated != null) {
					hql.append(" and isGenerated=:isGenerated");
				}
				if (inputer != null && inputer.trim().length() > 0) {
					hql.append(" and inputer=:inputer");
				}
				if (field != null && field.trim().length() > 0) {
					hql.append(" and " + field + " like :keyword");
				}
				// 得到记录的数据。
				Query query = session.createQuery(hql.toString());
				query.setBoolean("isDeleted", isDeleted);
				if (status != null && status != 9) {
					query.setInteger("status", status);
				}
				if (isTop != null) {
					query.setBoolean("isTop", isTop);
				}
				if (isElite != null) {
					query.setBoolean("isElite", isElite);
				}
				if (isHot != null) {
					query.setInteger("hits", hits);
				}
				if (isGenerated != null) {
					query.setBoolean("isGenerated", isGenerated);
				}				
				if (inputer != null && inputer.trim().length() > 0) {
					query.setString("inputer", inputer);
				}
				if (field != null && field.trim().length() > 0) {
					query.setString("keyword", "%" + keyword + "%");
				}
				return (Long)query.list().get(0);
			}
		});
		*/
	}
	
	/** 子栏目列表。 */
	private List<Column> child_columns;
	
	/**
	 * 获得此栏目的第一级子栏目集合。必须通过 setChildColumns() 设置之后访问才是正确的。
	 * @return 返回此栏目的第一级子栏目集合。
	 */
	public List<Column> getChildColumns() {
		return child_columns;
	}
	
	/**
	 * 设置此栏目的直接子栏目。
	 * @param child_columns
	 */
	public void setChildColumns(List<Column> child_columns) {
		this.child_columns = child_columns;
	}
	
	/**
	 * 得到简单的子节点数据，DataTable 只包含 id, name。
	 * 在栏目排序页面与 ColumnTree.delete 方法中使用。
	 * @return
	 */
	public DataTable getSimpleChild() {
		return getSimpleChild(false);
	}

	/**
	 * 得到简单的子节点数据，DataTable 包含 id, name(, childCount)。
	 *  在栏目排序页面与ColumnTree.delete方法及评论管理中使用。
	 *  在评论管理中，如果是二级栏目，需要获取其子栏目的数量 childCount。
	 * @param getChildCount 是否需要得到子栏目的数量。
	 * @return - DataTable 包含 id, name(, childCount)。
	 */
	public DataTable getSimpleChild(boolean getChildCount) {
		String hql = "select c.id, c.name";
		if (getChildCount) {
			hql += ",(select count(*) from Column where parentId=c.id) ";
		}
		hql += " from Column c where c.parentId=" + super.getId() + " order by c.orderPath asc";
		List list = this._getPublishContext().getDao().list(hql);
		
		DataTable dt = new DataTable(new DataSchema(new String[]{"id", "name"}));
		if (getChildCount) {
			dt.getSchema().add("childCount");
		}
		
		PublishUtil.addToDataTable(list, dt);
		return dt;
	}
	
	
	/**
	 * 得到栏目对象所拥有的下级栏目数量。
	 * @return 下级栏目数量。
	 */
	public int getChildColumnCount() {
		String hql = "SELECT COUNT(*) FROM Column WHERE parentId = " + getId();
		return PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
	}
	
	/**
	 * 根据项目的标识加载一个项目对象。
	 * @param id 项目的标识。
	 * @return 项目对象。
	 */
	public Item loadItem(int id) {
		return getItem(id);
	}


	/**
	 * 更新一个项目的所属专题信息。
	 * @param item
	 */
	private void updateItemSpecials(Item item) {
		// 希望更新的。
		List<Integer> special_ids = item._getSpecialIds();
		
		// 现有的。
		List<Integer> orig_special_ids = item.loadSpecialIds();
		
		int insert_num = 0, delete_num = 0;
		// 进行更新 - 插入部分。
		DataAccessObject dao = pub_ctxt.getDao();
		for (int i = 0; i < special_ids.size(); ++i) {
			Integer special_id = special_ids.get(i);
			if (orig_special_ids.contains(special_id) == false) {
				// 原来不存在，则现在插入。 (需要检查专题是否存在，是否属于全站或当前频道吗？)
				RefSpecialItem rsi = new RefSpecialItem();
				rsi.setItemId(item.getId());
				rsi.setSpecialId(special_id);
				dao.save(rsi);
				++insert_num;
			}
		}
		
		// 进行更新 - 删除部分。
		for (int i = 0; i < orig_special_ids.size(); ++i) {
			Integer special_id = orig_special_ids.get(i);
			if (special_ids.contains(special_id) == false) {
				// 现在不存在，则删除原来的。 (也许以后优化一下可以通过一个 SQL 语句完成了)
				String hql = "DELETE FROM RefSpecialItem WHERE itemId=" + item.getId() +
					" AND specialId=" + special_id;
				dao.bulkUpdate(hql);
				++delete_num;
			}
		}
		
	}
	
	/**
	 * 添加一个特定的项目对象。调用者要保证 column 是项目的正确容器。
	 * 注意：需要事务的支持。
	 * @param item 项目对象。
	 * @return 添加完成后的项目对象。
	 */
	public void insertItem(Item item) {
		internalSaveItem(item, true);
	}
	
	/**
	 * 更新一个特定的项目对象。调用者要保证 column 是项目的正确容器。
	 * 注意：需要事务的支持。
	 * @param item 需要更新的项目对象。
	 */
	public void updateItem(Item item) {
		internalSaveItem(item, false);
	}
	
	/**
	 * 内部保存一个项目。
	 * @param item
	 * @param insert - = true 表示插入，= false 表示更新。 
	 */
	private void internalSaveItem(Item item, boolean insert) {
		// 保存前准备工作。
		item._init(pub_ctxt, this);
		item.setColumn(this);
		item.beforeSave(this);
		
		// 保存对象本身。
		if (insert)
			pub_ctxt.getDao().save(item);
		else
			pub_ctxt.getDao().update(item);
		
		if (item._getSpecialIds() != null) {
			// 表示要更新所属专题数据。
			updateItemSpecials(item);
		}
		getChannel().getKeywordCollection().createItemKeywords(item.getKeywords());

		// 生成静态化地址。因为命名需要项目的标识，因此在项目生成以后才能做这个操作。
		if (item.rebuildStaticPageUrl()) {
			// 更新静态化地址
			item.updateGenerateStatus(Item.class, pub_ctxt);
		}
		
		item.afterSave(this);
	}
	
	/**
	 * 删除一篇内容项，放入回收站。
	 * @param item - 要删除的项目。
	 * @return 返回 true 表示更新了删除标志，false 表示未更新(可能不存在或已经删除了)
	 */
	public boolean deleteItem(Item item) {
		String hql = "UPDATE Item SET deleted = true WHERE id = " 
			+ item.getId() + " AND deleted = false";
		int delete_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		return delete_num == 1;
	}
	
	/**
	 * 设置/取消项目置顶状态。
	 * @param item
	 * @param is_top
	 * @return
	 */
	public boolean setItemTop(Item item, boolean is_top) {
		String hql = "UPDATE Item SET top=" + is_top + " WHERE id=" + item.getId();
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		item.setTop(is_top);
		
		return update_num == 1;
	}
	
	/**
	 * 设置/取消项目推荐状态。
	 * @param item
	 * @param is_commend
	 * @return
	 */
	public boolean setItemCommend(Item item, boolean is_commend) {
		String hql = "UPDATE Item SET commend=" + is_commend + " WHERE id=" + item.getId();
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		item.setCommend(is_commend);
		
		return update_num == 1;
	}
	
	/**
	 * 设置项目的推荐状态。
	 * @param item
	 * @param is_elite
	 * @return
	 */
	public boolean setItemElite(Item item, boolean is_elite) {
		String hql = "UPDATE Item SET elite = " + is_elite + " WHERE id=" + item.getId();
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		item.setElite(is_elite);
		
		return update_num == 1;
	}
	
	/**
	 * 退稿。
	 * @param item
	 */
	public boolean rejectItem(Item item) {
		String hql = "UPDATE Item SET status = " + Item.STATUS_REJECTED + " WHERE id = " + item.getId();
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		item.setStatus(Item.STATUS_REJECTED);
		
		return update_num == 1;
	}
	
	/**
	 * 彻底销毁指定项目。
	 * @param item
	 * @return
	 */
	public boolean destroyItem(Item item) {
		// TODO: 进行一些验证工作。
		
		// 1. TODO: 删除这个项目的所有评论。
		// item.deleteAllComments();
		
		// 2. TODO: 删除掉这个项目的所有扩展属性 (Ext_Prop)
		
		// 3. 删除掉这个项目的所有专题的引用。 (RefSpecialItem)
		int rsi_num = item.deleteAllRsi();
		
		// 4. 删除掉这个项目的静态化文件。
		if (item.getIsGenerated()) {
			// TODO: item.deleteStaticPage();
		}
		
		// 5. 删除项目本身。
		pub_ctxt.getDao().delete(item);
		
		return true;
	}

	/**
	 * 批量删除内容项，放入回收站。
	 * @param item_ids
	 * @return - 返回实际被删除的项目标识集合。
	 */
	public List<Integer> batchDeleteItems(List<Integer> item_ids) {
		// 1. 获得要删除的这组内容项存在的标识数组。
		String hql = "SELECT id FROM Item WHERE channelId = " + this.getChannelId() + 
			" AND deleted = false AND id IN (" + PublishUtil.toSqlInString(item_ids) + ")";
		@SuppressWarnings("unchecked")
		List<Integer> exist_item_ids = pub_ctxt.getDao().list(hql);
		if (exist_item_ids == null || exist_item_ids.size() == 0) return exist_item_ids;
		
		// 2. 设置这些内容项为被删除状态。
		hql = "UPDATE Item SET deleted = true WHERE id IN (" + PublishUtil.toSqlInString(exist_item_ids) + ")";
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		return exist_item_ids;
	}

	/**
	 * 批量审核一组内容项。
	 * @param item_ids
	 * @param approved
	 * @return
	 */
	public List<Integer> batchApproveItems(List<Integer> item_ids, boolean approved) {
		// 1. 获得要操作的这组内容项存在的标识数组。
		String hql = "SELECT id FROM Item WHERE channelId = " + this.getChannelId() +
			" AND id IN (" + PublishUtil.toSqlInString(item_ids) + ")";
		if (approved)
			// 请求审核通过。原来状态为非审核状态的都能审核通过。
			hql += " AND status <> " + Item.STATUS_APPR; 
		else
			// 取消审核。原来状态为审核通过的，能够取消审核。
			hql += " AND status = " + Item.STATUS_APPR;
		
		@SuppressWarnings("unchecked")
		List<Integer> exist_item_ids = pub_ctxt.getDao().list(hql);
		if (exist_item_ids == null || exist_item_ids.size() == 0) return exist_item_ids;
		
		// 2. 设置这些内容项的新审核状态。
		hql = "UPDATE Item SET status = " + (approved ? Item.STATUS_APPR : Item.STATUS_UNAPPR) 
			+ " WHERE id IN (" + PublishUtil.toSqlInString(exist_item_ids) + ")";
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		return exist_item_ids;
	}

	/** 记录要被删除的项目的信息。 */
	private static final class ItemDestroyed {
		public int id;			// 项目标识。
		public String uuid;		// 全局唯一标识。
		public boolean isGenerated;		// 是否已经静态化了。
		public String staticPageUrl;	// 静态地址。
	}
	
	/**
	 * 批量彻底删除一组项目。
	 * @param item_ids, 如果 = null 表示删除所有的。
	 * @return
	 * TODO: 将这个业务转移到独立类里面完成更好。
	 */
	public List<Integer> batchDestroyItems(List<Integer> item_ids) {
		
		// 1. 获得要操作的这组内容项存在的标识数组。
		String hql = "SELECT id, objectUuid, isGenerated, staticPageUrl " +
			" FROM Item WHERE channelId = " + this.getChannelId() +
			" AND deleted = true ";
		if (item_ids != null)
			hql += " AND id IN (" + PublishUtil.toSqlInString(item_ids) + ")";
		
		List list = pub_ctxt.getDao().list(hql);
		if (list == null || list.size() == 0) return null;
		
		// 将返回的 List 转化为 List<ItemDestroyed>
		List<ItemDestroyed> exist_item_ids = buildItemDestroyed(list);
		
		// 实际删除各个相关部分。
		
		// 1. TODO: 删除这个项目的所有评论。
		// item.deleteAllComments();
		
		// 2. TODO: 删除掉这个项目的所有扩展属性 (Ext_Prop)
		
		// 3. 删除掉这个项目的所有专题的引用。 (RefSpecialItem)
		int rsi_num = internalDestroyAllRsi(exist_item_ids);
		
		// 4. 删除掉这个项目的静态化文件。
		internalDeleteStaticPage(exist_item_ids);
		
		// 5. 删除项目本身。
		internalDestroyItems(exist_item_ids);
	
		// 返回数据。
		List<Integer> result = toIntegerList(exist_item_ids);
		
		return result;
	}
	
	private List<ItemDestroyed> buildItemDestroyed(List list) {
		List<ItemDestroyed> exist_item_ids = new ArrayList<ItemDestroyed>();
		for (int i = 0; i < list.size(); ++i) {
			Object[] data = (Object[])list.get(i);
			ItemDestroyed item = new ItemDestroyed();
			item.id = (Integer)data[0];
			item.uuid = (String)data[1];
			item.isGenerated = (Boolean)data[2];
			item.staticPageUrl = (String)data[3];
			exist_item_ids.add(item);
		}
		return exist_item_ids;
	}
	
	private List<Integer> toIntegerList(List<ItemDestroyed> items) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < items.size(); ++i)
			result.add(items.get(i).id);
		return result;
	}
	
	private int internalDestroyItems(List<ItemDestroyed> items) {
		String clazz_name = getChannel().getChannelModule().getItemClass();
		String hql = "DELETE FROM " + clazz_name + " AS Photo WHERE Photo.id IN (" +
			toSqlInString(items) + ")";
		
		UpdateHelper updator = new UpdateHelper();
		updator.updateClause = hql;
		return updator.executeUpdate(pub_ctxt.getDao());
	}
	
	private String toSqlInString(List<ItemDestroyed> items) {
		if (items == null || items.size() == 0) return "";
		StringBuilder strbuf = new StringBuilder();
		strbuf.append(items.get(0).id);
		for (int i = 1; i < items.size(); ++i)
			strbuf.append(",").append(items.get(i).id);
		return strbuf.toString();
	}
	
	// batchDestroyItems 方法使用的，删除一组项目的专题引用。
	private int internalDestroyAllRsi(List<ItemDestroyed> items) {
		String hql = "DELETE FROM RefSpecialItem WHERE itemId IN (" + toSqlInString(items) + ")";
		return pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	// batchDestroyItems 方法使用的，删除一组项目的静态化文件。
	private void internalDeleteStaticPage(List<ItemDestroyed> items) {
		String root_dir = pub_ctxt.getRootDir() + pub_ctxt.getSite().getInstallDir();
		for (int i = 0; i < items.size(); ++i) {
			ItemDestroyed item = items.get(i);
			if (item.isGenerated) {
				java.io.File file = new java.io.File(root_dir + item.staticPageUrl);
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}
	
	/**
	 * 删除一篇内容项，放入回收站。
	 * 注意：需要事务的支持。
	 * @param id 内容项的标识。
	 * @deprecated
	 */
	public void deleteItem(int id) {
		// TODO: 更多检测。
		String hql = "UPDATE Item SET deleted = true WHERE id = " + id;
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 实际的从数据库中删除一篇内容项。
	 * 注意：需要事务的支持。
	 * @param id 内容项的标识。
	 */
	public void realDeleteItem(int id) {
		// TODO: 更多检测。
		String hql = "DELETE FROM Item WHERE id = " + id;
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 清空当前栏目下所有的内容项，放入回收站。
	 * 注意：需要事务的支持。
	 */
	public int clearItems() {
		String hql = "UPDATE Item SET deleted = true WHERE columnId = " + this.getId();
		return pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 实际从数据库中删除当前栏目下所有的内容项。
	 * 注意：需要事务的支持。
	 * @return 更新的记录数。
	 */
	public int realClearItems() {
		// 得到该栏目的子孙栏目的标识集合。
		List<Integer> child_ids = this.column_tree.getAllChildColumnIds(getId());
		String ids_sql = PublishUtil.toSqlInString(child_ids);

		// 删除。
		String hql = "DELETE FROM Item WHERE columnId IN (" + ids_sql + ") ";
		return pub_ctxt.getDao().bulkUpdate(hql);
	}

	/**
	 * 还原数据库中当前栏目下所有的内容项
	 * 注意：需要事务的支持。
	 * @return 更新的记录数。
	 */
	public int restoreAllItems() {
		// 得到该栏目的子孙栏目的标识集合。
		List<Integer> child_ids = this.column_tree.getAllChildColumnIds(getId());
		String ids_sql = PublishUtil.toSqlInString(child_ids);

		// 执行。
		String hql = "UPDATE Item SET deleted = false WHERE columnId IN (" + ids_sql + ") ";
		return pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 重新排序当前节点的子节点。
	 * 注意：需要事务处理。
	 * @param ids 全部字节点的标识数组，按照排序的顺序。
	 * @return 更新的记录数。
	 */
	public int orderChild(int[] ids) {
		TreeViewModel treeView = new TreeViewModel(THE_TABLENAME, THE_ALIAS, pub_ctxt.getDao());
		return treeView.reorderChildrens(super.getId(), ids);
	}
	
	
	/**
	 * 更新内容项(Item)的推荐属性（elite）。
	 * 注意：需要事务的支持。
	 * @param itemId 内容项的标识。
	 * @param elite 是否推荐。
	 * @deprecated - 被 setItemElite() 取代。
	 */
	public void updateElite(final int itemId, final boolean elite) {
		String hql = "update Item set elite=" + elite + " where id=" + itemId;
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 还原内容项（Item），更新一个删除属性（deleted）。
	 * 注意：需要事务的支持。
	 * @param item_ids - 操作的项目标识数组，如果 = null 表示恢复所有项目。
	 */
	public List<Integer> batchRestoreItems(List<Integer> item_ids){
		// String hql = "update Item set deleted=false where id=" + itemId;
		// pub_ctxt.getDao().bulkUpdate(hql, null);
		// 1. 获得要删除的这组内容项存在的标识数组。
		String hql = "SELECT id FROM Item WHERE channelId = " + this.getChannelId() + 
			" AND deleted = true ";
		if (item_ids != null)
			hql += " AND id IN (" + PublishUtil.toSqlInString(item_ids) + ")";
		@SuppressWarnings("unchecked")
		List<Integer> exist_item_ids = pub_ctxt.getDao().list(hql);
		if (exist_item_ids == null || exist_item_ids.size() == 0) return exist_item_ids;
		
		// 2. 设置这些内容项删除为未删除。
		hql = "UPDATE Item SET deleted = false WHERE id IN (" + PublishUtil.toSqlInString(exist_item_ids) + ")";
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		
		return exist_item_ids;
	}
	
	/**
	 * 移动内容项到当前栏目中。
	 * 注意：需要事务的支持。
	 * @param itemId 内容项的标识。
	 */
	public void moveItem(final int itemId) {
		// TODO: 验证这个 item 属于此频道。
		String hql = "UPDATE Item SET columnId = " + this.getId() + " WHERE id = " + itemId;
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	

	
	/**
	 * 更新内容项的状态，-1：草稿；0：未审核；1：已审核；？？。
	 * 可以执行审核/取消审核的操作。
	 * 注意：需要事务的支持。
	 * @param itemId 内容项的标识。
	 * @param status 状态值。
	 */
	public void updateStatus(final int itemId, final String editor, final int status) {
		updateStatus(new int[] {itemId}, editor, status);
	}
	
	/**
	 * 更新内容项的状态，-1：草稿；0：未审核；1：已审核；？？。
	 * 可以执行审核/取消审核的操作。
	 * 注意：需要事务的支持。
	 * @param itemIds 内容项的标识的数组。
	 * @param editor 编辑。
	 * @param status 状态值。
	 */
	public void updateStatus(final int itemIds[], final String editor, final int status) {
		UpdateHelper uh = new UpdateHelper();
		uh.updateClause = "UPDATE Item SET status=:status, editor=:editor WHERE id IN (" + PublishUtil.toSqlInString(itemIds) + ")";
		uh.setInteger("status", status);
		if (status == 1)
			uh.setString("editor", editor);
		else
			uh.setString("editor", null);
		uh.executeUpdate(pub_ctxt.getDao());
	}
	
	/**
	 * 得到栏目里边拥有的评论。
	 * @param columnId 栏目的标识。如果是所有栏目，则为0。
	 * @para passed 是否验证通过。-1：所有；0：未通过验证；1：通过验证。
	 * @param page 页次。
	 * @param maxPerPage 每页显示的最大数目。
	 * @return DataTable("item","comments")。 item为项目项目，包括id, channelId, columnId, title。
	 * comments为item所拥有的评论对象集合。
	 */
	@Deprecated /* 重新修改这个函数 */
	public DataTable getCommentList(int passed, final int page, final int maxPerPage) {
		throw new UnsupportedOperationException();
		/*
		String hql = "SELECT c.id, c.itemId, c.content, c.score, c.userType, c.userName, c.writeTime, c.passed, "
			+ "c.replyName, c.replyContent, c.replyTime, c.passed, c.email, c.qq, c.msn, c.ip, c.homepage, "
			+ "i.channelId, i.columnId, i.title  FROM Comment c, Item i "
			+ " WHERE c.itemId = i.id AND i.channelId = " + this.getChannelId();
		
		if (!"/".equals(this.parentPath)) {	//如果当前栏目不是根栏目，则需要对其columnId有所限制。
			hql += " AND (i.columnId = " + super.getId() + "OR i.columnId in(SELECT id From Column where parentPath Like '" 
				+ this.getParentPath() + super.getId() + "/%'))";
		}			
		if (passed == 0) {
			hql += " AND c.passed = false";
		} else if(passed == 1) {
			hql += " AND c.passed = true";
		}		
		hql += " ORDER BY c.itemId + c.writeTime desc";
		final String hql0 = hql;
		
		return (DataTable)this.pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DataTable dataTable = new DataTable(PublishUtil.columnsToSchema("item, comments"));
				
				//找到符合条件的评论及对应项目的数据。
				org.hibernate.Query query = session.createQuery(hql0);
				query.setFirstResult((page - 1) * maxPerPage);
				query.setMaxResults(maxPerPage);					
				List list = query.list();	
				
				//cao改
				int commentId = -1;
				if (list != null && list.size() > 0) {
					List<Comment> commentList = new ArrayList<Comment>();
					for(int i = 0;i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						int prevItemId = (Integer)obj[1];						
						HashMap<String, Object> hm = new HashMap<String, Object>();
						if(prevItemId != commentId){
							hm.put("id", prevItemId);
							hm.put("channelId", obj[17]);
							hm.put("columnId", obj[18]);
							hm.put("title", obj[19]);
							commentList = new ArrayList<Comment>();
							//把属于同篇项目的评论放到commentList中。
							for(int j=0; j < list.size(); j++){
								obj = (Object[])list.get(j);
								int itemId = (Integer)obj[1];
								if(itemId == prevItemId){
									commentList.add(getComment(obj));
									commentId = commentList.get(0).getItemId();
								}
							}
							Object[] item = new Object[2];
							item[0] = hm;
							item[1] = commentList;
							DataRow row = dataTable.newRow(item);
							dataTable.add(row);
						}
					}
				}
				return dataTable;
			}				
		});		
	*/	
	}
	
	/**
	 * 根据Object数组生成对应的评论对象。
	 * @param obj Object数组。
	 * @return 评论对象。
	 */
	private Comment getComment(Object[] obj){
		Comment comment = new Comment();
		comment.setId((Integer)(obj[0]));
		comment.setItemId((Integer)obj[1]);
		comment.setContent((String)obj[2]);
		comment.setScore((Integer)obj[3]);
		comment.setUserType((Integer)obj[4]);
		comment.setUserName((String)obj[5]);
		comment.setWriteTime((java.util.Date)obj[6]);
		comment.setPassed((Boolean)obj[7]);
		comment.setReplyName((String)obj[8]);
		comment.setReplyContent((String)obj[9]);
		comment.setReplyTime((java.util.Date)obj[10]);	
		comment.setPassed((Boolean)obj[11]);
		comment.setEmail((String)obj[12]);
		comment.setQq((String)obj[13]);
		comment.setMsn((String)obj[14]);
		comment.setIp((String)obj[15]);
		comment.setHomepage((String)obj[16]);
		
		return comment;
	}
	
	/**
	 * 得到指定栏目里边拥有的评论条数。
	 * @para passed 是否验证通过。-1：所有；0：未通过验证；1：通过验证。
	 * @param columnId 栏目的标识。如果是指所有栏目，则为0。
	 * @return 评论条数。
	 */
	public long getCommentListCount(int passed) {
		//生成用来查询的hql语句。
		String hql = "SELECT count(*) FROM Comment c, Item i "
			+ " WHERE c.itemId = i.id AND i.channelId = " + this.getChannelId();
		if (!"/".equals(this.getParentPath()) ) {
			hql += " AND (i.columnId = " + super.getId() + "OR i.columnId in(SELECT id From Column where parentPath Like '" 
				+ this.getParentPath() + super.getId() + "/%'))";
		}		
		if (passed == 0) {
			hql += " AND c.passed = false";
		} else if(passed == 1) {
			hql += " AND c.passed = true";
		}
		
		List list = this.pub_ctxt.getDao().list(hql);
		if (list != null && list.size() > 0) {
			return Long.parseLong(list.get(0).toString());
		}
		return 0;
	}
	
	/**
	 * 得到一个项目。
	 * @param itemId - 项目的标识。
	 * @return 项目对象，可能是 Article, Soft, Photo。(加载的项目可能频道不一致的，要注意。)
	 */
	public Item getItem(int itemId) {
		Item item = this.getChannel().loadItem(itemId);
		if (item != null) {
			if (item.getColumnId() == this.getId())
				item.setColumn(this);
		}
		return item;
	}
	
	/**
	 * 得到文章对象。
	 * @param itemId
	 * @return
	 */
	public Article getArticleItem(int itemId) {
		Article article = getChannel().loadArticle(itemId);
		initItemModel(article);
		return article;
	}
	
	/**
	 * 得到图片对象。
	 * @param photoId - 图片标识。
	 * @return 返回图片对象。
	 */
	public Photo getPhotoItem(int photoId) {
		Photo photo = getChannel().loadPhoto(photoId);
		initItemModel(photo);
		return photo;
	}
	
	/**
	 * 得到软件对象。
	 * @param softId
	 * @return
	 */
	public Soft getSoftItem(int softId) {
		Soft soft = getChannel().loadSoft(softId);
		initItemModel(soft);
		return soft;
	}
	
	// 初始化对象模型。
	private void initItemModel(Item item) {
		if (item == null) return;
		item._init(pub_ctxt, this);
		if (item.getColumnId() == this.getId())
			item.setColumn(this);
	}
	
	/**
	 * 得到一个仅含有标识的项目对象。
	 * @param itemId 项目的标识
	 * @return
	 * @deprecated 最好不要用这个方法。
	 */
	@Deprecated
	public Item getSimpleItem(int itemId) {
		Item item = null;
		initBelongToModule();
		if (this.belongToModule == Column.ARTICLE_MODULE) {
			item = new Article();
		} else if (this.belongToModule == Column.PHOTO_MODULE) {
			item = new Photo();
		} else if (this.belongToModule == Column.SOFT_MODULE) {
			item = new Soft();
		}
		if (item != null) {
			item.setId(itemId);
			item._init(pub_ctxt, this);
		}
		return item;
	}
	
	/**
	 * 得到项目的标题。
	 * @param itemId 项目的标识。
	 * @return 项目的标题。
	 */
	public String getItemTitle(int itemId) {
		String title = "";
		String hql = "Select title from Item where id = " + itemId;
		List list = this.pub_ctxt.getDao().list(hql);
		if (list != null && list.size() > 0) {
			title = (String)list.get(0);
		}
		return title;
	}
	
	/**
	 * 得到该频道下没有经过审核的项目数量。
	 * 主要用于后台管理的首页。
	 * @return
	 */
	public int getNotPassedItemCount() {
		String hql ="SELECT count(*) FROM Item WHERE channelId = " + this.getChannelId()
			+ " AND columnId = " + super.getId() 
			+ " AND status = 0 and deleted = false";
		return PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
	}
	
	/**
	 * 获取项目是否允许评论
	 * @param itemId 项目的标识。
	 * @return
	 */
	public boolean allowComment(int itemId) {
		String hql = "select commentFlag from Item where id = " + itemId + " and deleted = false";
		int commentFlag = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		return (commentFlag == 1);
	}
	
	// === PrivilegeObject 接口实现 ================================================
	
	/**
	 * 获得权限标志。
	 * @return
	 */
	public int getPrivilegeFlag() {
		// TODO: return this.privilege_flag;
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#calcPageUrl()
	 */
	@Override public String calcPageUrl() {
		// 如果是外部栏目则返回外部栏目链接地址。
		if (this.columnType == COLUMN_TYPE_EXTERNAL)
			return this.linkUrl;
		
		// 否则返回自己的地址。
		Channel channel = this.getChannel();
		if (channel == null) return "";
		
		// 如果设置为栏目页静态化，则返回静态地址。
		if (channel.getNeedGenerateColumn() && this.getStaticPageUrl() != null
				&& this.getStaticPageUrl().length() != 0)
			return channel.resolveUrl(getStaticPageUrl());

		// 否则返回动态地址。
		return pub_ctxt.getSite().resolveUrl(
				channel.getChannelDir()	+ "/showColumn.jsp?columnId=" + getId());
	}
	
	/**
	 * 得到栏目的真实路径。
	 * @param column 栏目。
	 * @return 返回相对于频道的 url 地址，如 'animal/cat/'
	 */
	public String getRealColumnPath() {
		if (this.getId() == 0) return "";
		Channel channel = this.getChannel();
		if (channel == null) throw new PublishException("在计算栏目地址的时候无法找到此频道的栏目，栏目对象非法或没有正确创建。");

		// 根栏目吗？ - ''
		if (this.getParentId() == 0) return "";
		// 上一级是根栏目吗？ - 'animal/'
		if (this.getParentId() == channel.getRootColumnId()) return this.getColumnDir() + "/";
		
		if (this.parent != null) {
			String path = this.getColumnDir() + "/";
			// 如果给出了 parent 对象，则假定每一级 parent 都能正确获得。
			ModelObject parent_column = this.parent;
			while (true) {
				// 向上找所有父级栏目。
				if (!(parent_column instanceof Column)) break;
				path = ((Column)parent_column).getColumnDir() + "/" + path;
				parent_column = parent_column.getParent();
			}
			return path;
		} else {
			// 否则我们自己去查找数据库，此过程也许比较慢。
			// sample: this.parentPath = '/1/3/8/'
			int[] parent_ids = TreeViewModel.parseParentPath(this.parentPath);
			String hql = "SELECT columnDir FROM Column " +
				" WHERE id IN (" + PublishUtil.toSqlInString(parent_ids) + ") " +
				" ORDER BY parentPath ";
			List parent_dirs = pub_ctxt.getDao().list(hql);
			String path = "";
			// 跳过第一个 Column，其是隐含的根栏目，不具有目录。
			for (int i = 1; i < parent_dirs.size(); ++i) {
				path += parent_dirs.get(i) + "/";
			}
			path += this.columnDir + "/";
			// ? 每次都计算 or 缓存??
			return path;
		}
	}
	
	/**
	 * 得到新的静态化地址。
	 * @return
	 */
	protected String getNewStaticPageUrl(PublishContext pub_ctxt) {
		Channel channel = this.getChannel();
		if (channel == null) return "";
		if (channel.getNeedGenerateColumn() == false) return "";

		// 产生静态化规则的首页文件名。
		String column_url = "";
		switch (channel.getListFileType()) {
		case 1:		// 1：列表文件统一保存在指定的“list”文件夹中；
			// 频道目录 + /list/list_ID.html
			column_url = "list/list_" + getId();
			break;
		case 2:		// 2：列表文件统一保存在频道文件夹中。
			// 频道目录 + list_ID.html
			column_url = "list_" + getId();
			break;
		default:
		case 0:		// 0：列表文件分目录保存在所属栏目的文件夹中；
			// 频道目录 + 所有父栏目路径 + 自己路径 + "index.html"
			column_url = getRealColumnPath() + "index";
			break;
		}
		column_url += super.getFileExtName(channel.getFileExtList());
		return column_url;
	}

	/**
	 * 获得列表页面的 url pattern, 例如 'xxx/list_{page}.html'
	 * @return 注意：返回的 url 不包括网站和频道的目录。
	 */
	public String getListUrlPattern() {
		Channel channel = this.getChannel();
		if (channel == null) return null;
		// 如果频道设置为不静态化，则返回动态地址。
		if (channel.getNeedGenerateColumn() == false) {
			return "showColumn.jsp?columnId=" + this.getId() + "&amp;page={page}";
		}
		
		// 否则产生静态化规则的列表文件名。
		String list_url_pattern = "";
		switch (channel.getListFileType()) {
		case 1:
		     // 1 - 列表文件统一保存在指定的“List”文件夹中；<br>
		     //    如 Article/List/List_236.html（栏目首页）<br>
		     //       Article/List/List_236_2.html（第二页）
			list_url_pattern = getRealColumnPath() + "list/list_" + getId() + "_{page}";
			break;
		case 2:
		     // 2 - 列表文件统一保存在频道文件夹中。<br>
		     //    如 Article/List_236.html（栏目首页）<br>
		     //       Article/List_236_2.html（第二页）
			list_url_pattern = "list_" + getId() + "_{page}";
			break;
		case 0:
		default:
		     // 0 - 列表文件分目录保存在所属栏目的文件夹中<br>
		     //    如 Article/ASP/JiChu/index.html（栏目首页）<br>
		     //       Article/ASP/JiChu/List_2.html（第二页）
			list_url_pattern = getRealColumnPath() + "list_{page}";
			break;
		}

		list_url_pattern += super.getFileExtName(channel.getFileExtList());
		return list_url_pattern;
	}

	// === ShowPathSupport 接口实现 ====================================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#isShowInPath()
	 */
	public boolean isShowInPath() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#getPathTitle()
	 */
	public String getPathTitle() {
		return this.getName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#getPathTarget()
	 */
	public String getPathTarget() {
		return this.getOpenType() == 0 ? "" : "_blank";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ShowPathSupport#getPathPageUrl()
	 */
	public String getPathPageUrl() {
		return this.getPageUrl();
	}

	// === 内部业务 ================================================================
	
	/**
	 * 获得此栏目内项目数量，指此栏目(不含子栏目)，审核通过及非删除的项目。
	 * @return 返回项目数量。
	 */
	public int _getItemCount() {
		String hql = "SELECT COUNT(*) FROM Item WHERE channelId = " + this.getChannelId() + 
			" AND columnId = " + this.getId() + " AND status = " + Item.STATUS_APPR +
			" AND deleted = false";
		return PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
	}
}

