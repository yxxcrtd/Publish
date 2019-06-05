package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.comp.TreeDataTable;
import com.chinaedustar.publish.module.ArticleModule;
import com.chinaedustar.publish.module.PhotoModule;
import com.chinaedustar.publish.module.SoftModule;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 栏目集合的数据模型对象。
 * @author wangyi
 * 注意：此对象的 owner 应该是 Channel，否则必须自己设置 Channel 对象。
 */ 
public class ColumnTree extends AbstractPublishModelBase {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.model.PublishModelObject)
	 */
	@Override public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super._init(pub_ctxt, owner_obj);
		if (owner_obj instanceof Channel)
			this.channel = (Channel)owner_obj;
	}
	
	/** 所属频道。 */
	private Channel channel;
	
	/**
	 * 获得所属频道。
	 */
	public Channel getChannel() {
		return this.channel;
	}
	
	/**
	 * 设置所属频道。
	 * @param channel
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	// =========== 业务操作方法 ================================

	/**
	 * 得到指定栏目的所有子孙节点。以 List&lt;Column&gt; 的结构返回。
	 *  返回指定节点的子孙节点的信息，不包括请求的节点自己。
	 * @param columnId - 栏目的标识。
	 * @return List&lt;Column&gt;
	 */
	public List<Column> getAllChildColumnList(int columnId) {
		if (columnId == 0)
			return getAllChildColumnList((TreeItemInterface)null);
		
		TreeViewModel tree_model = getTreeViewModel();
		TreeItemInterface parent_column = tree_model.getTreeNode(columnId);
		return getAllChildColumnList(parent_column);
	}
	
	/**
	 * 得到指定栏目的所有子孙节点。以 List&lt;Column&gt; 的结构返回。
	 * @param parent_column - 父栏目对象，如果 = null 表示使用根栏目。
	 * @return List&lt;Column&gt;
	 */
	public List<Column> getAllChildColumnList(TreeItemInterface parent_column) {
		if (parent_column == null) 
			parent_column = channel._getCreateRootColumn();
				
		// 构造查询。
		TreeViewModel tree_model = getTreeViewModel();
		TreeViewQueryObject queryObject = tree_model.getChildrensQuery(parent_column, true);
		String hql = "FROM Column AS Column " +
					 "WHERE " + queryObject.getWhere() + " " +
					 "ORDER BY " + queryObject.getOrder();
		// 查询数据
		@SuppressWarnings("unchecked")
		List<Column> column_list = (List<Column>)pub_ctxt.getDao().list(hql);
		
		// 对返回数据进行对象模型处理。
		PublishUtil.initModelList(column_list, pub_ctxt, this);
		
		// TODO: 对模型数据进行更多处理，使得其全部能够连接起来。
		
		return column_list;
	}
	
	/**
	 * 得到指定栏目的下一级子栏目集合。
	 * @param columnId 栏目的标识, = 0 表示为根栏目。 
	 * @return 返回指定栏目的下一级子栏目集合，类型为 List&lt;Column&gt;。
	 */
	public List<Column> getChildColumnList(int columnId) {
		if (columnId == 0) columnId = channel.getRootColumnId();
		
		// 构造查询。
		String hql = "FROM Column AS Column " +
					 " WHERE channelId = " + channel.getId() + " AND parentId = " + columnId +
					 " ORDER BY orderPath ASC ";
		
		// 查询数据
		@SuppressWarnings("unchecked")
		List<Column> column_list = (List<Column>)pub_ctxt.getDao().list(hql);
		
		// 对返回数据进行对象模型处理。
		PublishUtil.initModelList(column_list, pub_ctxt, this);
		
		return column_list;
	}
	
	/**
	 * 获得指定标识的栏目的导航列表，从该栏目的根栏目排列起。
	 * 例如栏目标识为 7，其父栏目为 3，则返回的 List 为 "root, 3, 7" 的顺序。
	 * @param columnId
	 * @return 返回从根栏目起的栏目列表。
	 */
	public List<Column> getColumnNavList(int columnId) {
		if (columnId == 0) return null;		// 不需要导航。
		
		// 先获得此栏目。
		Column column = this.getColumn(columnId);
		if (column == null) return null;	// 栏目不存在。
		
		// 根据 parentPath 能够得到其所有父栏目对象，获取它们。
		String parentPath = column.getParentPath();
		int[] parent_ids = TreeViewModel.parseParentPath(parentPath);
		
		// 例子： 'FROM Column WHERE id IN (3, 7)'
		String hql = "FROM Column " +
					" WHERE id IN (" + PublishUtil.toSqlInString(parent_ids) +
				  " ) ORDER BY parentPath ASC ";
		@SuppressWarnings("unchecked")
		List<Column> list = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(list, pub_ctxt, this);
		
		list.add(column);		// 最后加上自己。
		
		// TODO: 由于已经获取了自根开始的所有栏目，所以应该可以为每个栏目设置其 parent 对象了。
		
		return list;
	}
	
	/**
	 * 获得指定标识的栏目的所有子孙栏目的标识。
	 * @param columnId - 栏目标识，如果 = 0 表示使用频道的根栏目。
	 * @return 返回为一个标识的数组。如果没有任何子节点则返回为一个没有任何元素的数组。
	 */
	public List<Integer> getAllChildColumnIds(int columnId) {
		if (columnId == 0)
			return getAllChildColumnIds(this.getChannel()._getCreateRootColumn());

		TreeViewModel tree_oper = getTreeViewModel();
		TreeItemInterface column_node = tree_oper.getTreeNode(columnId);
		return getAllChildColumnIds(column_node);
	}
	
	/**
	 * 获得指定标识的栏目的所有子孙栏目的标识。
	 * @param column_node - 栏目节点，如果 == null 表示使用根节点。
	 * @return 返回为一个标识的集合。如果没有任何子节点则返回为一个没有任何元素的数组。
	 */
	public List<Integer> getAllChildColumnIds(TreeItemInterface column_node) {
		// 参数处理。
		if (column_node == null) column_node = this.getChannel()._getCreateRootColumn();
		TreeViewModel tree_oper = getTreeViewModel();
		TreeViewQueryObject query_obj = tree_oper.getChildrensQuery(column_node, true);
		
		// 使用 QueryHelper 帮助进行查询，查询结果为一个 List<int>.
		QueryHelper qh = new QueryHelper();
		qh.selectClause = "SELECT id ";
		qh.fromClause = " FROM Column AS Column ";
		qh.whereClause = " WHERE channelId = " + this.getChannel().getId() + " AND " + query_obj.getWhere();
		qh.orderClause = " ORDER BY " + query_obj.getOrder();
		
		@SuppressWarnings("unchecked")
		List<Integer> list = qh.queryData(_getPublishContext().getDao());

		return list;
	}
	
	/**
	 * 得到栏目管理下拉列表中需要的数据，以DataTable的结构返回。
	 * 增强：等同于调用 getAdvColumnListDataTable(COLUMN_MANAGE_FILEDS);
	 * @return DataTable
	 * @schema = {id, name, parentId, parentPath, orderPath, description, 
	 * 		columnType, columnDir, openType, showOnTop, showOnIndex, isElite, 
	 *      enableAdd, enableProtect}
	 */
	public DataTable getColumnListDataTable() {
		return getAdvColumnListDataTable(COLUMN_MANAGE_FILEDS);
		/*
		// 准备工作。
		// int nodeId = this.getChannel().getRootColumnId();
		Column root_column = this.getChannel()._getCreateRootColumn();
		TreeViewModel tree_model = this.getTreeViewModel();
		TreeViewQueryObject query_obj = tree_model.getChildrensQuery(root_column, true);
		
		// 构造 QueryHelper
		String select_fields = "id, name, parentId, parentPath, orderPath, description, " +
			"columnType, columnDir, openType, showOnTop, showOnIndex, isElite, enableAdd, enableProtect";
		QueryHelper qh = new QueryHelper();
		qh.selectClause = " SELECT " + select_fields;
		qh.fromClause = " FROM Column AS Column ";
		qh.whereClause = " WHERE channelId = " + this.getChannel().getId();
		qh.whereClause += " AND " + query_obj.getWhere();
		qh.orderClause = " ORDER BY " + query_obj.getOrder();
		
		// 进行查询。
		List list = qh.queryData(pub_ctxt.getDao());
		
		// 组装 DataTable
		DataSchema schema = PublishUtil.columnsToSchema(select_fields);
		DataTable data_table = new DataTable(schema);
		PublishUtil.addToDataTable(list, data_table);
		
		return data_table;
		*/
	}
	
	/** 调用 getAdvColumnListDataTable 时缺省的核心字段集合，如果仅简单需要，用这些字段就可以。 */
	public static final String COLUMN_CORE_FIELDS = "id, name, parentId, parentPath";
	
	/** 为产生下拉列表框时候使用的字段，比核心字段添加了 enableAdd 属性。 */
	public static final String COLUMN_DROPDOWN_FIELDS = 
		"id, name, parentId, parentPath, enableAdd";
	
	/** 用于管理栏目信息时候获取的字段信息。 */
	public static final String COLUMN_MANAGE_FILEDS = 
		"id, name, parentId, parentPath, orderPath, description, " +
		"columnType, columnDir, openType, showOnTop, showOnIndex, isElite, enableAdd, enableProtect";
	
	/**
	 * getColumnListDataTable 的高级版本。
	 *  提供字段选择，封装为全功能对象树 ColumnDataTable。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public TreeDataTable getAdvColumnListDataTable(String fields) {
		// 缺省只获取一些核心数据。
		if (fields == null || fields.length() == 0)
			fields = COLUMN_CORE_FIELDS;
		
		// 准备工作。
		// int nodeId = this.getChannel().getRootColumnId();
		Column root_column = this.getChannel()._getCreateRootColumn();
		TreeViewModel tree_model = this.getTreeViewModel();
		TreeViewQueryObject query_obj = tree_model.getChildrensQuery(root_column, true);
		
		// 构造 QueryHelper
		String select_fields = fields;
		QueryHelper qh = new QueryHelper();
		qh.selectClause = " SELECT " + select_fields;
		qh.fromClause = " FROM Column AS Column ";
		qh.whereClause = " WHERE channelId = " + this.getChannel().getId();
		qh.whereClause += " AND " + query_obj.getWhere();
		qh.orderClause = " ORDER BY " + query_obj.getOrder();
		
		// 进行查询。
		List list = qh.queryData(pub_ctxt.getDao());
		
		// 组装为全功能 ColumnDataTable.
		TreeDataTable data_table = new TreeDataTable(new DataSchema(select_fields));
		for (int i = 0; i < list.size(); ++i) {
			data_table.addRow(data_table.newRow((Object[])list.get(i)));
		}
		
		// 为支持显示带有比较好看的树结构， ColumnDataTable 进行了增强。
		// 第一次调用 ColumnDataRow.treeFlag 时会进行计算。
		
		return data_table;
	}


	// =========== 未测试的 ================================

	/**
	 * 从数据库中加载一个栏目对象，如果栏目对象不存在，则返回 null。
	 *  注意：此函数具有缓存能力，能够从线程缓存中得到以前已经加载过的栏目对象。
	 * 如果不希望从缓存中加载，请使用 loadColumn() 方法。
	 * @param columnId - 栏目标识。
	 * @return
	 */
	public Column getColumn(int columnId) {
		// 1. 尝试从缓存中获取。
		ThreadCurrentMap cache =  ThreadCurrentMap.default_current();
		String key = String.valueOf(columnId) + getChannel().getObjectUuid() + "_Column";
		Column column = (Column)cache.getNamedObject(key);
		if (column != null) return column;
		
		// 2. 加载。
		column = (Column)pub_ctxt.getDao().get(Column.class, columnId);
		if (column != null) {
			column._init(pub_ctxt, this);
			cache.putNamedObject(key, column);
		}
		return column;
	}
	
	/**
	 * 立刻从数据库加载一个栏目对象，如果栏目对象不存在，则返回 null.
	 *  和 getColumn() 不同之处在于，getColumn() 可能从缓存中获取，而 loadColumn()
	 *  总是从数据库中获取。
	 * @param columnId
	 * @return
	 */
	public Column loadColumn(int columnId) {
		Column column = (Column)pub_ctxt.getDao().get(Column.class, columnId);
		if (column != null) {
			column._init(pub_ctxt, this);
		}
		return column;
	}

	/**
	 * 从数据库加载此频道下的栏目对象，以及其所有父栏目对象(不含频道根栏目)。
	 *  如果栏目不在此频道，则返回 null.
	 * 此函数用于支持在 column.jsp 等页面中使用的当前栏目对象，其具有完整的 parent 对象链。
	 * @param columnId
	 * @return
	 */
	public Column getColumnWithParent(int columnId) {
		Column column = (Column)pub_ctxt.getDao().get(Column.class, columnId);
		if (column == null) return null;
		// 不在我们频道，则不处理。
		if (column.getChannelId() != this.getChannel().getId()) return null;
		
		column._init(pub_ctxt, this);
		if (column.getParentId() == 0) {
			// 根栏目, 其父对象是栏目。
			column.setParent(getChannel());
			return column;
		}
		
		// 继续加载其所有祖先栏目。
		Column curr_column = column;
		while (curr_column.getParentId() != 0) {
			Column parent_column = (Column)pub_ctxt.getDao().get(Column.class, curr_column.getParentId());
			if (parent_column == null) return column;			// 内部错误，这里忽略返回。
			parent_column._init(pub_ctxt, this);
			if (parent_column.getParentId() == 0) {
				// parent_column 就是根栏目，所以设置 column.parent = channel 然后返回。
				curr_column.setParent(getChannel());
				break;
			}
			curr_column.setParent(parent_column);
			curr_column = parent_column;
		}
		
		// 现在栏目及其所有父栏目已经加载进来了，这个对象可以用于产生栏目页面。
		return column;
	}
	
	/**
	 * 增加栏目信息。
	 * 注意：需要事务支持。
	 * @param column 栏目对象。
	 * @return 增加完成后的栏目对象。
	 */
	public Column insert(Column column) {
		// 对数据进行一些整理。
		column.setChannelId(this.getChannel().getId());
		if (column.getParentId() == 0)
			column.setParentId(this.getChannel().getRootColumnId());
		
		TreeViewModel tree_model = getTreeViewModel();
		// 得到新栏目的 ParentPath, OrderPath 信息。
		TreeItemObject treeItem = tree_model.addTreeNode(column.getParentId());
		column.setParentPath(treeItem.getParentPath());
		column.setOrderPath(treeItem.getOrderPath());
		
		// 保存栏目信息。
		pub_ctxt.getDao().insert(column);
		column._init(pub_ctxt, this);
		
		// 创建静态化地址.
		// 因为目录的静态化地址有可能依赖于其ID，所以先保存然后创建静态化地址。
		if (column.getParentId() > 0) {	// 如果是根目录就无需创建了。
			column.rebuildStaticPageUrl();
			column.updateGenerateStatus();
		}
		
		return column;
	}
	
	/**
	 * 修改栏目。
	 * 注意：需要事务支持。
	 * @param column 栏目对象。
	 * @param isMoved 栏目的是否移动过。(当前不支持移动)
	 * @return 修改后的栏目对象。
	 */
	public void update(Column column) {
		if (column.getId() == channel.getRootColumnId()) return;	// 不支持根栏目更新。
		// 如果设置父栏目 = 0，则表示设置其父栏目 = 根栏目。
		if (column.getParentId() == 0) column.setParentId(channel.getRootColumnId());
		
		// 获得旧的栏目对象。
		Column old_column = loadColumn(column.getId());
		if (old_column == null) throw new PublishException("要更新的栏目不存在。");
		DataAccessObject dao = pub_ctxt.getDao();
		dao.evict(old_column);
		
		// 设置那些不更新的字段。
		column.setChannelId(old_column.getChannelId());		// 频道标识不能更改。
		
		// 更新栏目对象, 此更新不包括父栏目标识、OrderPath, ParentPath。
		dao.update(column);
		dao.flush();
		
		// 如果是父栏目发生了变化，则表示要移动栏目位置。
		if (column.getParentId() != old_column.getParentId()) {
			Column new_parent_column = loadColumn(column.getParentId());
			if (new_parent_column.getChannelId() != column.getChannelId())
				throw new PublishException("试图跨越频道移动栏目被拒绝。");
			move(old_column, new_parent_column);
		}
		
		// 重新加载 Column, 并重计算静态化地址
		column = loadColumn(column.getId());
		column.rebuildStaticPageUrl();
		column.updateGenerateStatus();
	}
	
	/**
	 * 判断指定的栏目是否具有子栏目。
	 * @param columnId - 栏目标识。
	 * @return true 表示有子栏目，false 表示没有子栏目。
	 */
	private boolean hasChildColumns(int columnId) {
		String hql = "SELECT COUNT(*) FROM Column WHERE parentId = " + columnId;
		int child_count = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		return (child_count > 0);
	}
	
	/**
	 * 删除指定的栏目。
	 * 注意：需要事务支持。
	 * @param column - 要删除的栏目。
	 */
	public void delete(Column column) {
		// 验证：栏目必须存在，栏目所在频道和 ColumnTree 匹配。
		if (column.getParentId() == 0)
			throw new PublishException("不能删除频道根栏目。");
		
		// 验证其没有子栏目。当前我们暂时不支持同时删除子栏目。
		if (hasChildColumns(column.getId()))
			throw new PublishException("不能删除带有子栏目的栏目，必须先删除掉子栏目才能删除该栏目。");
		
		// 设置属于此栏目的项目现在都放到根栏目下面。
		internalMoveItem(column, column.getChannel()._getCreateRootColumn());
		// 删除掉内容项。(现在不删除内容项了，而是都置为根栏目的。)
		// column.realClearItems();
		
		// 删除该栏目的扩展属性。
		internalDeleteExtends(column);
		
		// 删除该栏目相关的权限。
		internalDeleteColumnRight(column);
		
		// ?? 还有和栏目相关的对象也都要删除掉。
		
		// 删除掉自己。
		pub_ctxt.getDao().delete(column);
		
		/*
		// 得到所有的子孙栏目。
		DataTable dt = column.getSimpleChild();
		final StringBuffer inString = new StringBuffer(columnId + "");
		for (int i = 0; i < dt.size(); i++) {
			int columnId0 = (Integer)dt.get(i).get(0);
			inString.append("," + columnId0);
		}
		// 删除内容项。
		int num = column.realClearItems();
		// 删除指定栏目以及子孙栏目。
		num += (Integer)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "delete from Column where id in(" + inString.toString() + ")";
				Query query = session.createQuery(hql);
				return query.executeUpdate();
			}
		});
		return num;
		*/
	}
	
	// 设置属于此栏目的项目现在都放到根栏目下面。
	private void internalMoveItem(Column src, Column dest) {
		String hql = "UPDATE Item SET columnId = " + dest.getId() + 
			" WHERE columnId = " + src.getId() + " AND channelId = " + src.getChannelId();
		pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	// 删除此栏目的扩展属性。
	private void internalDeleteExtends(Column column) {
		ExtendPropertySet.deleteObjectExtends(pub_ctxt.getDao(), column);
	}
	
	// 删除此栏目相关的权限设置。
	private void internalDeleteColumnRight(Column column) {
		AdminRight.deleteColumnRights(pub_ctxt.getDao(), column.getId());
	}

	/**
	 * 移动一个栏目到新的指定的父栏目。
	 * @param column
	 * @param new_parentId
	 */
	public void move(Column column, Column new_parent_column) {
		TreeViewModel tree_model = getTreeViewModel();
		tree_model.moveNode(column, new_parent_column);
	}
	
	/**
	 * 将第一个栏目合并到第二个栏目中，需要移动文章，修改栏目的排序。
	 * 1、将第一个栏目下面的文章移动到目标栏目下（不包括子孙栏目的文章）；
	 * 2、将第一个栏目下的子栏目移动到目标栏目下；
	 * 3、将目标栏目的子栏目进行重新排序。
	 * 4、删除第一个栏目。
	 * 注意：需要事务支持。
	 * @param columnId 需要移动的栏目的标识。
	 * @param targetColumnId 移动的目标栏目的标识。
	 * @return 更新的记录数。
	 */
	@Deprecated
	public int uniteColumn(final int columnId, final int targetColumnId) {
		throw new UnsupportedOperationException("未实现的操作。");
		/*
		return (Integer)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				// 得到目标栏目下的现有子栏目的标识。
				TreeViewModel treeView = new TreeViewModel(Column.THE_TABLENAME, Column.THE_ALIAS, pub_ctxt.getDao());
				TreeViewQueryObject treeViewQuery = treeView.getChildQuery(targetColumnId);
				String hql = "select id from " + Column.THE_TABLENAME + " as " + Column.THE_ALIAS + " where " 
					+ treeViewQuery.getWhere() + " order by " + treeViewQuery.getOrder();
				Query query = session.createQuery(hql);
				List list = query.list();
				int[] targetChildIds = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					targetChildIds[i] = (Integer)list.get(i);
				}
				// 得到第一个栏目的现有子栏目的标识。
				treeViewQuery = treeView.getChildQuery(columnId);
				hql = "select id from " + Column.THE_TABLENAME + " as " + Column.THE_ALIAS + " where " 
					+ treeViewQuery.getWhere() + " order by " + treeViewQuery.getOrder();
				query = session.createQuery(hql);
				list = query.list();
				int[] childIds = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					childIds[i] = (Integer)list.get(i);
				}
				// 移动文章。
				hql = "update Item set columnId=:targetId where columnId=:id";
				query = session.createQuery(hql);
				query.setInteger("id", columnId);
				query.setInteger("targetId", targetColumnId);
				int num = query.executeUpdate();
				if (childIds.length > 0) {	// 如果第一个栏目有子栏目。
					// 移动子栏目。
					hql = "update " + Column.THE_TABLENAME + " set parentId=:targetId where parentId=:id";
					query = session.createQuery(hql);
					query.setInteger("id", columnId);
					query.setInteger("targetId", targetColumnId);
					num += query.executeUpdate();
					// 重新排序。
					int[] columnIds = new int[targetChildIds.length + childIds.length];
					for (int i = 0; i < targetChildIds.length; i++) {
						columnIds[i] = targetChildIds[i];
					}
					for (int i = 0; i < childIds.length; i++) {
						columnIds[i + targetChildIds.length] = childIds[i];
					}
					Column column = getSimpleColumn(targetColumnId);
					column.orderChild(columnIds);
					num += columnIds.length;
				}
				// 删除第一个栏目。
				getChannel().getColumnTree().delete(columnId);
				return num;
			}
			
		});
		*/
	}
	
	// === 其它业务 =====================================================================
	
	/**
	 * 通过栏目的标识找到栏目的名称。等同于调用 getColumn().getName().
	 * @param columnId
	 * @return
	 */
	public String getColumnName(int columnId) {
		Column column = getColumn(columnId);
		if (column == null) return null;
		return column.getName();
		/*
		String hql = "SELECT name FROM Column WHERE id=" + columnId;
		List list = pub_ctxt.getDao().list(hql);
		if (list != null && list.size() > 0) {
			return (String)list.get(0);
		} else {
			return null;
		} 
		*/
	}
	
	/**
	 * 获取栏目菜单的js文件的url地址。比如：/PubWeb/news/js/showColumnMenu.js。
	 *   如果还没有生成，则立刻生成。
	 * @return
	 */
	public String getColumnMenuJsUrl() {
		String url = "";
		Channel channel = this.getChannel();
		
		String fileName = "js/showColumnMenu.js";
		String file_path = channel.resolvePath(fileName);
		java.io.File file = new java.io.File(file_path);
		if (!file.exists()) {
			generateColumnMenuJsFile();
		}
		url = channel.resolveUrl(fileName);
		return url;
	}
	
	/**
	 * 生成所有 Js 文件。
	 *
	 */
	public void generateJs() {
		// 生成栏目菜单的js文件。
		generateColumnMenuJsFile();
		// 生成栏目下拉列表框的js文件。
		generateColumnJsFile();
		// 生成栏目搜索的js文件。
		generateSearchJsFile();
	}
	
	/**
	 * 生成栏目菜单的js文件。
	 * TODO: 也许我们应该把生成过程放在某个模板里面完成，这样不需要写这么多代码，
	 *   而且更加灵活。
	 *   另外，现在使用的 stm31.js 产生的菜单比较难懂，难生成，难道不能有别的方法吗？
	 *   PowerEasy 使用 stm31 带有很多选项，我们如何弄懂它??
	 */
	private void generateColumnMenuJsFile() {
		// 产生 js 代码。
		Channel channel = this.getChannel();
		String strJSCode = getColumnMenuJsCode(channel);
		String file_name = channel.resolvePath("js/showColumnMenu.js");
		
		// 写入文件中。
		try {
			PublishUtil.writeTextFile(file_name, strJSCode, channel.getSite().getCharset());
		} catch (java.io.IOException ex) {
			return;
		}
	}

	/**
	 * 生成栏目下拉列表框的js文件。
	 */
	private void generateColumnJsFile() {
		Channel channel = this.getChannel();
		String strJSCode = "document.write(\"" + getColumnJsCode(channel) + "\");";
		String file_name = pub_ctxt.getRootDir() + "\\"
			+ channel.getChannelDir() + "\\js\\showClass_Option.js";

		// 栏目下拉列表JS
		try {
			PublishUtil.writeTextFile(file_name, strJSCode, channel.getSite().getCharset());
		} catch (java.io.IOException ex) {
			return;
		} 
	}
	
	/**
	 * 生成栏目搜索的js文件。
	 */
	private void generateSearchJsFile() {
		//搜索JS
		Channel channel = this.getChannel();
		String strJSCode = "document.write(\"" + getSearchJsCode(channel) + "\");";
		String file_name = pub_ctxt.getRootDir() + "\\" 
			+ channel.getChannelDir() + "\\js\\showSearchForm.js";

		try {
			PublishUtil.writeTextFile(file_name, strJSCode, channel.getSite().getCharset());
		} catch (java.io.IOException ex) {
			return;
		}
	}
	
	/**
	 * 得到栏目菜单的js代码。
	 * @param channel 频道对象。
	 * @return 返回生成的 js 字符串。
	 */
	private String getColumnMenuJsCode(Channel channel) {
		String installDir = pub_ctxt.getSite().getInstallDir();
		StringBuffer str = new StringBuffer();
		
		// stm_bm(['uueoehr',400,'','/PubWeb/images/blank.gif',0,'','',0,0,0,0,0,1,0,0]);
		str.append("stm_bm(['uueoehr',400,'','" + installDir + "images/blank.gif',0,'','',0,0,0,0,0,1,0,0]);\n");
		// stm_bp('p0',[0,4,0,0,2,2,0,0,100,'',-2,'',-2,90,0,0,'#000000','transparent','',3,0,0,'#000000']);
		str.append("stm_bp('p0',[0,4,0,0,2,2,0,0,100,'',-2,'',-2,90,0,0,'#000000','transparent','',3,0,0,'#000000']);\n");
		// stm_ai('p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#cccccc',1,'','',3,3,0,0,'#fffff7','#000000','#000000','#000000','9pt 宋体','9pt 宋体',0,0]);
		str.append("stm_ai('p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#cccccc',1,'','',3,3,0,0,'#fffff7','#000000','#000000','#000000','9pt 宋体','9pt 宋体',0,0]);\n");

		// 为每个栏目生成 js 代码片。
		// 首先得到第一级子栏目列表。
		java.util.List<Column> list = this.getChildColumnList(channel.getRootColumnId());
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				str.append(generateJs(installDir, channel.getChannelDir(), list.get(i), 0, i + 3));
			}
		}
		
		str.append("stm_em();\n");
		return str.toString();
	}

	/**
	 * 产生js。
	 * @param installDir - 站点安装目录，比如/PubWeb/。
	 * @param channelDir - 频道目录，比如news。
	 * @param column - 栏目对象。
	 * @param level - 栏目所处的层深。
	 * @param order - 排序数。
	 * @return
	 * 这是一个递归产生代码的过程，让我们跟踪一下。
	 */
	private String generateJs(String installDir, String channelDir, Column column, int level, int order) {
		// 得到 column 的子栏目。
		java.util.List<Column> list = getChildColumnList(column.getId());
		boolean hasChild = (list != null && list.size() > 0);
		
		StringBuffer jsCodeBuffer = new StringBuffer();
		String column_url = column.getPageUrl();
		
		if (level == 1 && order == 0) {
			jsCodeBuffer.append("stm_bp('p1',[1,4,0,0,2,3,6,7,100,'progid:DXImageTransform.Microsoft.Fade(overlap=.5,enabled=0,Duration=0.43)',-2,'',-2,67,2,3,'#999999','#ffffff','',3,1,1,'#aca899']);\n");
		}
		
		if (level >= 2 && order == 0) {
			jsCodeBuffer.append("stm_bpx('p" + level + "','p" + (level - 1) + "',[1,2,-2,-3,2,3,0]);\n");
		}
		
		if (level == 0) {
			jsCodeBuffer.append("stm_aix('p" + level + "i" + order + "','p" + (level > 0 ? (level - 1) : 0)  + "i0',[0,'" + column.getName() 
					+ "','','',-1,-1,0,'" + column_url + "','_self','"
					+ column_url + "','','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#cccccc',1,'',''," 
					+"3,3,0,0,'#fffff7','#ff0000','#000000','#cc0000','9pt 宋体','9pt 宋体']);\n");
		} else {
			jsCodeBuffer.append("stm_aix('p" + level + "i" + order + "','p" + (level > 0 ? (level - 1) : 0)  + "i0',[0,'" + column.getName() 
					+ "','','',-1,-1,0,'" + column_url + "','_self','"
					+ column_url + "','','','',0,0,0,'"
					+ (hasChild ? installDir + "images/arrow_r.gif" : "") +"','"
					+ (hasChild ? installDir + "images/arrow_w.gif" : "") + "',0,0,0,0,1,'#ffffff',0,'#cccccc',0,'','',3,3,0,0,'#fffff7','#000000','#000000','#ffffff','9pt 宋体']);\n");
		}	
		
		if (hasChild) {
			for (int i = 0; i < list.size(); i++) {
				jsCodeBuffer.append(generateJs(installDir, channelDir, list.get(i), level + 1, i));
			}
			jsCodeBuffer.append("stm_ep();\n");
		}
		
		if (level == 0) {
			jsCodeBuffer.append("stm_aix('p0i2','p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#cccccc',1,'','',3,3,0,0,'#fffff7','#000000','#000000','#000000','9pt 宋体','9pt 宋体',0,0]);\n");
		}
		
		return jsCodeBuffer.toString();
	}
	
	/**
	 * 得到栏目下拉列表的js代码。
	 * @param channel 频道对象。
	 * @return
	 * TODO: 这个用模板来生成应该很简单。
	 */
	private String getColumnJsCode(Channel channel) {
		// schema used: id, name
		DataTable dt = this.getAdvColumnListDataTable(null);
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < dt.size(); j++) {
			DataRow row = dt.get(j);
			sb.append("<option value='").append(row.get("id")).append("'>")
				.append(row.get("name")).append("</option>");
		}
		return sb.toString();
	}
	
	/**
	 * 得到搜索的js代码。
	 * @param channel 频道对象。
	 * @return
	 * TODO: 很明显，用模板生成这些代码更简单一些。
	 */
	private String getSearchJsCode(Channel channel) {
		String installDir = pub_ctxt.getSite().getInstallDir();
		Module module = pub_ctxt.getSite().getModules().getModule(channel.getModuleId());
		String rootColumnDir = installDir + channel.getChannelDir();
		String appendStr = "<table border='0' cellpadding='0' cellspacing='0'>";
		appendStr += "<form method='post' name='searchForm' action='" + rootColumnDir +"/search.jsp'>";
		appendStr += "<tr><td height='28' align='center'><select name='field' size='1' style='width:95'>";
		if (module.getPublishModule() instanceof ArticleModule) {
			appendStr += getArticleJsOption(channel);
		} else if (module.getPublishModule() instanceof PhotoModule) {
			appendStr += getPhotoJsOption(channel);
		} else if (module.getPublishModule() instanceof SoftModule) {
			appendStr += getSoftJsOption(channel);
		}
		appendStr += "</select>&nbsp;";
		appendStr += "<select name='columnId' style='width:100'><option value=''>所有栏目</option>" + getColumnJsCode(channel) + "</select>&nbsp;";
		appendStr += "<input type='text' name='keyword'  size='20' value='关键字' maxlength='50' onfocus='this.select();' style='width:140'>&nbsp;";
		appendStr += "<input type='hidden' name='showAdSearch' value='0'>&nbsp;";
		appendStr += "<input type='submit' name='Submit'  value='搜&nbsp;索'></td></tr>";
		appendStr += "</form></table>";
		return appendStr;
	}
	
	/**
	 * 得到文章模块的频道的js代码中Option部分的字符串。
	 * @param channel 频道
	 * @return
	 */
	private String getArticleJsOption(Channel channel) {
		String str_option = "";
		str_option += "<option value='title' selected>" + channel.getItemName() + "标题</option>";
		str_option += "<option value='content'>" + channel.getItemName() + "内容</option>";
		str_option += "<option value='author'>" + channel.getItemName() + "作者</option>";
		str_option += "<option value='inputer'>录 入 者</option>";
		str_option += "<option value='keywords'>关 键 字</option>";
		return str_option;
	}
	
	/**
	 * 得到图片模块的频道的js代码中Option部分的字符串。
	 * @param channel 频道
	 * @return
	 */
	private String getPhotoJsOption(Channel channel) {
		String str_option = "";
		str_option += "<option value='title' selected>" + channel.getItemName() + "名称</option>";
		str_option += "<option value='description'>" + channel.getItemName() + "简介</option>";
		str_option += "<option value='author'>" + channel.getItemName() + "作者</option>";
		str_option += "<option value='inputer'>录 入 者</option>";
		return str_option;
	}
	
	/**
	 * 得到软件模块的频道的js代码中Option部分的字符串。
	 * @param channel 频道
	 * @return
	 */
	private String getSoftJsOption(Channel channel) {
		String str_option = "";
		str_option += "<option value='title' selected>" + channel.getItemName() + "名称</option>";
		str_option += "<option value='description'>" + channel.getItemName() + "简介</option>";
		str_option += "<option value='author'>" + channel.getItemName() + "作者</option>";
		str_option += "<option value='inputer'>录 入 者</option>";
		return str_option;
	}

	// === 辅助函数 =========================================================================
	
	/** 获得一个 Column 的 TreeViewModel 新对象。 */
	private TreeViewModel getTreeViewModel() {
		TreeViewModel tree_oper = new TreeViewModel(Column.THE_TABLENAME, Column.THE_ALIAS, pub_ctxt.getDao());
		return tree_oper;
	}
}
