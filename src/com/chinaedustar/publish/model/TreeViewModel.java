package com.chinaedustar.publish.model;

import java.sql.CallableStatement;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.chinaedustar.publish.*;

/**
 * 这是一个处理树形结构的通用类，访问数据库，完成对树结构的一些基本操作。
 * @author wangyi
 *
 */
@SuppressWarnings("rawtypes")
public class TreeViewModel {
	/** 节点排序路径的每个数字转化为字符串的单元长度。 */
	public static final int TREE_ORDER_LENGTH = 4;
	
	/** 数字的进制长度，在路径类的存储中，使用36进制。 */
	public static final int TREE_NUM_RADIX = 36;
	
	/** 实际的数据库处理数据库的对象。 */
	private DataAccessObject dao = null;

	/** 树形结构所在的对象名称。 */
	private String objectName;
	
	/** 树形结构所在的对象名称的别名。 */
	private String alias;
	
	/**
	 * 构造方法，必须指定树形结构所在的表。
	 * 没有指定实际的数据库连接，不能执行实际的更新操作。
	 * @param objectName
	 */
	public TreeViewModel(String objectName, String alias) {
		this.objectName = objectName;
		this.alias = alias;
	}
	
	/**
	 * 构造方法，必须指定树形结构所在的表。
	 * @param objectName 表名。
	 */
	public TreeViewModel(String objectName, String alias, DataAccessObject dao) {
		this.objectName = objectName;
		this.alias = alias;
		this.dao = dao;
	}
	
	// === static 辅助函数 ====================================================
	
	/**
	 * 计算树结构中数字转化为的字符串，当前采用 36 进制以减少字符串长度。
	 * @param nodeId - 节点(记录)标识。
	 * @return 将数字值转化为36进制的较短字符串。
	 */
	public static final String calcTreePath(int nodeId) {
		return Integer.toString(nodeId, TREE_NUM_RADIX);
	}

	/**
	 * 为指定序号计算出其字符串表示。
	 * 此函数当 TREE_ORDER_LENGTH=4 做了优化。
	 * @param order - 顺序号。
	 * @return 采用 36 进制排序的顺序号，并补满 4 位(前面+0)
	 */
	@SuppressWarnings("unused")
	public static final String calcOrderString(int order) {
		String order_str = Integer.toString(order, TREE_NUM_RADIX);
		
		int length = order_str.length();
		
		// TODO 
		// if (TREE_ORDER_LENGTH == 4) {
		if (true) {
			if (length == 1)
				return "000" + order_str;
			else if (length == 2)
				return "00" + order_str;
			else if (length == 3)
				return "0" + order_str;
			return order_str;
		} 
		
		StringBuffer buf = new StringBuffer(TREE_NUM_RADIX);
		for (int i = length; i < TREE_NUM_RADIX; ++i) {
			buf.append('0');
		}
		buf.append(order_str);
		return buf.toString();
	}

	/**
	 * 将父路径解析为父标识的数组。
	 * @param nodePath - 父路径的字符串（/1/12/A3Z/）。
	 * @return
	 */
	public static int[] parseParentPath(String nodePath) {
		String[] paths = nodePath.split("/");
		int[] nums = new int[paths.length - 1];
		for (int i = 0; i < (paths.length - 1); i++) {
			nums[i] = Integer.parseInt(paths[i+1], TREE_NUM_RADIX);
		}
		return nums;
	}
	
	/**
	 * 将排序路径解析为排序的数组。
	 * @param orderPath - 排序路径的字符串（./0001/0001/）。
	 * @return
	 */
	public static int[] parseOrderPath(String orderPath) {
		return parseParentPath(orderPath.substring(1));
	}

	/**
	 * 计算一个路径的深度，根('/')的深度 = 0。根据路径中有多少个 / 来计算深度。
	 * 例如 '/1/Z3/' 深度 = 2。
	 * @param nodePath
	 * @return 返回此路径代表的节点的深度。
	 */
	public static int calcPathDepth(String nodePath) {
		if (nodePath == null || nodePath.length() == 0) return 0;
		int slash_num = 0;
		for (int i = 0; i < nodePath.length(); ++i) {
			if (nodePath.charAt(i) == '/') ++slash_num;
		}
		return slash_num - 1;
	}
	
	/**
	 * 是否存在节点。(将通过查询数据库完成这个任务)
	 * @param nodeId 节点的标识。
	 * @return true 表示节点存在；false 表示节点不存在。
	 */
	public boolean existNode(int nodeId) {
		String hql = "SELECT COUNT(id) FROM " + objectName + " WHERE id=" + nodeId;
		int count = PublishUtil.executeIntScalar(dao, hql);
		return count > 0;
	}
	
	/**
	 * 为添加一个根节点获得节点数据。
	 * @return
	 */
	public TreeItemObject addRootTreeNode() {
		TreeItemObject data = new TreeItemObject();
		data.setParentPath("/");
		int order = 1;		// 因为是根，所以应该总是等于 1 。
		String orderPath = "./" + calcOrderString(order) + "/";
		data.setOrderPath(orderPath);
		return data;
	}
	
	/**
	 * 给指定节点增加一个子结点，默认增加在最末尾。
	 * @param nodeId - 父节点的标识。
	 * @return 增加的节点数据结构包含的数据（ParentPath, OrderPath）。
	 */
	public TreeItemObject addTreeNode(int nodeId) {
		// 获得父节点的树结构信息。
		TreeItemObject parent_node = getTreeNode(nodeId);
		return addTreeNode(parent_node);
	}

	/**
	 * 给指定节点增加一个子节点，默认增加在最末尾。
	 * 此重载形式比 addTreeNode(int) 节省一次数据库查询。
	 * @param parent_node - 父节点对象，其中必须包括正确的 id, parentPath, orderPath 数据。
	 * @return
	 */
	public TreeItemObject addTreeNode(TreeItemInterface parent_node) {
		if (parent_node == null) return null;

		int parent_node_id = parent_node.getId();
		// 计算新节点的父路径。
		String parentPath = parent_node.getParentPath() + calcTreePath(parent_node_id) + "/";
		// 计算新节点的排序路径。
		String orderPath;
		int order = 0;
		String lastOrderPath = getLastOrderPath(parent_node_id);
		if (lastOrderPath == null) {
			order = 1;
		} else {
			int[] orders = parseOrderPath(lastOrderPath);
			order = orders[orders.length - 1] + 1;
		}
		orderPath = parent_node.getOrderPath() + calcOrderString(order) + "/";
		
		// 构造新节点应该填写的 parentId, parentPath, orderPath 的对象返回。
		TreeItemObject data = new TreeItemObject();
		data.setParentId(parent_node_id);
		data.setParentPath(parentPath);
		data.setOrderPath(orderPath);
		return data;
	}
	
	/** 删除指定的节点，同时还要删除它的子孙节点。 */
	public int deleteTreeNode(int nodeId) {
		int num = 0;
		// TODO: 利用 session 执行删除操作。
//		try {
//			// 删除子孙节点。
//			num = deleteChildrens(nodeId);
//			// 删除自己。
//			String sql = "delete from " + objectName + " where id=" + nodeId;
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			try {
//				num += pstmt.executeUpdate();
//			} finally {
//				pstmt.close();
//			}
//		} catch (Exception ex) {
//			throw new SQLException(ex.getMessage());
//		} finally {
//			
//		}
		return num;
	}
	
	/**
	 * 删除指定节点的子孙节点。
	 * @param nodeId 节点的标识。
	 * @return 删除的记录数。
	 * @throws SQLException
	 */
	public int deleteChildrens(int nodeId) {
		TreeItemObject data = getTreeNode(nodeId);
		if (data == null) {
			throw new PublishException("指定的节点'" + nodeId + "'不存在，无法执行删除字节点的操作。");
		}
		return deleteChildrensAsPath(data.getParentPath() + Integer.toString(nodeId, TREE_NUM_RADIX) + "/");
	}
	
	/**
	 * 移动节点。
	 * @param node - 需要移动的节点标识。
	 * @param parentNode - 移动之后的父节点标识。
	 */
	public void moveNode(TreeItemInterface node, TreeItemInterface parentNode) {
		// 基本验证。
		if (node.getId() == parentNode.getId())
			throw new PublishException("当前移动节点不能与父节点相同。");
		if (node.getId() < 1 || parentNode.getId() < 1)
			throw new PublishException("当前移动节点或父节点不存在。");
		// ? 父节点属于当前移动节点的子孙节点?
		if (!parentNode.getParentPath().equals(node.getParentPath()) && 
				parentNode.getParentPath().startsWith(node.getParentPath()))
			throw new PublishException("不能将当前节点'" + node.getId() + "'移动到其子孙节点'" + parentNode.getId() + "'。");
		
		// 1. 更新自己的 parentId, parentPath, orderPath
		// 2. 更新所有子孙节点的 parentPath, orderPath
		// 为此，先获得所有的子节点 id, parentId, parentPath, orderPath。
		List<Object[]> all_children = internalGetChildData(node);

		// 计算 node 的新 parentPath
		String new_parentPath = parentNode.getParentPath() + calcTreePath(parentNode.getId()) + "/";
		// 计算 node 的新 orderPath
		String new_orderPath = getLastOrderPath(parentNode.getId());
		if (new_orderPath == null) {
			new_orderPath = parentNode.getOrderPath() + calcOrderString(1) + "/";
		} else {
			int[] orders = parseOrderPath(new_orderPath);
			int order = orders[orders.length - 1] + 1;
			new_orderPath = parentNode.getOrderPath() + calcOrderString(order) + "/";
		}
		
		// 更新自己的 parentId, parentPath, orderPath
		String hql = "UPDATE " + this.objectName + " SET parentId = " + parentNode.getId() + 
				", parentPath = '" + new_parentPath + "', orderPath = '" + new_orderPath + "' " +
				" WHERE id = " + node.getId();
		dao.bulkUpdate(hql);
		
		// 更新所有子孙节点的 parentPath, orderPath
		int old_parentPath_len = node.getParentPath().length();
		int old_orderPath_len = node.getOrderPath().length();
		for (int i = 0; i < all_children.size(); ++i) {
			Object[] children = all_children.get(i);
			int child_id = (Integer)children[0];
			String child_parentPath = (String)children[1];
			String child_orderPath = (String)children[2];
			// 计算新 child_parentPath
			child_parentPath = new_parentPath + child_parentPath.substring(old_parentPath_len);
			child_orderPath = new_orderPath + child_orderPath.substring(old_orderPath_len);
			hql = "UPDATE " + this.objectName + " SET parentPath = '" + child_parentPath +
				"', orderPath = '" + child_orderPath + "' " +
				" WHERE id = " + child_id;
			dao.bulkUpdate(hql);
		}
		
		// OK 完成了所有更新，DEBUG: 检查结果。
	}
	
	// 获得指定节点的所有子孙节点的 id, parentPath, orderPath
	// 内部获得指定栏目的所有子孙节点的 id, parentId, parentPath, orderPath 
	// 返回为 List<Object[]>
	private List<Object[]> internalGetChildData(TreeItemInterface node) {
		TreeViewQueryObject query_cond = getChildrensQuery(node, true);
		String hql = "SELECT id, parentPath, orderPath " +
					" FROM " + this.objectName + " AS " + this.alias +
					" WHERE " + query_cond.getWhere() + 
					" ORDER BY " + query_cond.getOrder();
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = this.dao.list(hql);
		return result;
	}
	

	/**
	 * 移动节点 (当前实现有问题)。
	 * @param nodeId 需要移动的节点标识。
	 * @param parentId 移动后的父节点。
	 * @param brotherId 移动后的前一个兄弟节点标识，如果前一个兄弟节点不存在，将brotherId设置为0；
	 * 					如果要将节点移动到第一个字节点，将brotherId设置为0;
	 * 					如果要将节点移动到最后一个节点，将brotherId设置为负数（-1）。
	 * @return 移动节点影响到的记录数。
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private int moveTreeNode(int nodeId, int parentId, int brotherId) {
		if (nodeId == parentId) {
			throw new PublishException("当前移动节点不能与父节点相同。");
		} else if (nodeId < 1 || parentId < 1) {
			throw new PublishException("当前移动节点或父节点不存在。");
		}
		// 需要移动的节点的树形结构信息。
		TreeItemObject selfData = getTreeNode(nodeId);
		if (selfData == null) {
			throw new PublishException("当前移动节点'" + nodeId + "'不能为空！");
		}
		// 父节点的树形结构信息。
		TreeItemObject parentData = getTreeNode(parentId);
		if (parentData == null) {
			throw new PublishException("父节点'" + parentId + "'为空！");
		} else if (!parentData.getParentPath().equals(selfData.getParentPath()) && parentData.getParentPath().indexOf(selfData.getParentPath()) > 0) {
			// 父节点属于当前移动节点的子孙节点。
			throw new PublishException("不能将当前节点'" + nodeId + "'移动到其子孙节点'" + parentId + "'。");
		}
		String parentPath = null;	// 当前移动节点的父节点路径。
		int order = 0;				// 当前移动节点在兄弟节点中的排序。
		parentPath = parentData.getParentPath() + Integer.toString(parentId, TREE_NUM_RADIX) + "/";
		if (brotherId > 0) {
			TreeItemObject brotherData = getTreeNode(brotherId);
			if (brotherData != null) {
				if (brotherData.getParentId() != parentId) {
					throw new PublishException("给定的兄弟节点'" + brotherId + "'不合法，因为它不是当前父节点的字节点。");
				}
				String brotherOrderString = brotherData.getOrderPath();
				int[] orders = parseOrderPath(brotherOrderString);
				order = orders[orders.length - 1] + 1;
			} else {
				order = 1;
			}
		} else if (brotherId == 0) {
			order = 1;
		} else {
			String orderPath = getLastOrderPath(parentId);
			if (orderPath != null && orderPath.trim().length() > 0) {
				int[] orders = parseOrderPath(orderPath);
				order = orders[orders.length - 1] + 1;
			} else {
				order = 1;
			}
		}
		
		// 开始事务处理。
		int num = 0;
		try {
			// TODO: 利用 session 执行更新操作。
//			// 更新当前节点数据结构。
//			String sql = "update " + objectName + " set ParentId=?, ParentPath=?, OrderPath=? where id=?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, parentId);
//			pstmt.setString(2, parentPath);
//			String orderPath = parentData.getOrderPath() + getStaticString(Integer.toString(order, radix), orderLength, "0") + "/";
//			pstmt.setString(3, orderPath);
//			pstmt.setInt(4, nodeId);
//			num = pstmt.executeUpdate();
//			pstmt.close();
//			// 更新其节点的子孙节点。
//			num += moveChildrens(selfData.getParentPath() + Integer.toString(nodeId, radix) + "/", selfData.getOrderPath(), parentPath + Integer.toString(nodeId, radix) + "/", orderPath);
//			// 更新它的后面的兄弟节点。
//			num += updateBrothersOrder(nodeId, ++order);
		} finally {

		}
		return num;
	}
	
	/**
	 * 指定节点是否有子节点。
	 * @param nodeId 节点的标识。
	 * @return
	 */
	public boolean hasChildren(int nodeId) {
		if (nodeId < 1) {
			throw new PublishException("节点'" + nodeId + "'不存在。");
		}
		String hql = "select count(id) from " + objectName + " where parentId=" + nodeId;
		List list = dao.list(hql);
		if (list != null && list.size() > 0) {
			try {
				if(PublishUtil.safeGetLongVal(list.get(0)) > 0) {
					return true;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 指定节点与父节点，是否是父自关系。
	 * @param nodeId 当前节点的标识。
	 * @param parentId 父节点的标识。
	 * @return
	 * @throws SQLException
	 */
	public boolean isParentNode(int nodeId, int parentId) {
		if (nodeId < 1 || parentId < 1) {
			throw new PublishException("当前节点或者父节点不存在。");
		}
		String hql = "select count(id) from " + objectName + 
			" where id=" + nodeId + " and parentId=" + parentId;
		List list = dao.list(hql);
		if (list != null && list.size() > 0) {
			if (((Integer[])list.get(0))[0] > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 得到查询一个树节点的查询对象。
	 * @param nodeId 树节点标识。
	 * @return
	 */
	public TreeViewQueryObject getNodeQuery(int nodeId) {
		TreeViewQueryObject query = new TreeViewQueryObject();
		query.setWhere("(" + alias + ".id=" + nodeId + ")");
		return query;
	}
	
	/**
	 * 得到指定节点的子一级节点集合的查询对象（条件，排序）。
	 * @param nodeId - 指定节点的标识。 
	 * @return 树节点查询对象。
	 */
	public TreeViewQueryObject getChildQuery(int nodeId) {
		if (existNode(nodeId)) {
			TreeViewQueryObject query = new TreeViewQueryObject();
			query.setWhere(" (" + alias + ".parentId=" + nodeId + ") ");
			query.setOrder(" " + alias + ".orderPath ASC ");
			return query;
		} else {
			throw new PublishException("指定的节点不存在，Nodeid=" + nodeId);
		}
	}

	/**
	 * 得到查询一个节点的子孙节点集合的查询对象（条件，排序）。
	 * @param parent_node - 根节点对象。
	 * @param isAsc - = true 表示自小到大排列，= false 表示自大到小排列。
	 * @return 返回具有合适的 WHERE 和 ORDER BY 子句的对象。
	 */
	public TreeViewQueryObject getChildrensQuery(TreeItemInterface parent_node, boolean isAsc) {
		TreeViewQueryObject query = new TreeViewQueryObject();
		// WHERE = parentPath LIKE '/1/3/%'
		query.setWhere("(" + alias + ".parentPath LIKE '" + 
			parent_node.getParentPath() + TreeViewModel.calcTreePath(parent_node.getId()) + "/%')");
		if (isAsc)
			query.setOrder(" " + alias + ".orderPath ASC ");
		else
			query.setOrder(" " +  alias + ".orderPath DESC ");

		return query;
	}
	
	/**
	 * 得到查询一个节点的子孙节点集合的查询对象（条件，排序）。
	 * 直接调用 getChildrensQuery(nodeId, isAsc=true) 减少代码重复。
	 * @param nodeId 树节点标识 - 必须存在该节点。
	 * @return
	 */
	public TreeViewQueryObject getChildrensQuery(int nodeId) {
		return getChildrensQuery(nodeId, true);
		/*
		// 得到当前节点的树形结构信息。
		TreeItemObject data = getTreeNode(nodeId);
		if (data == null) {
			throw new PublishException("当前节点'" + nodeId + "'不存在。");
		}
		TreeViewQueryObject query = new TreeViewQueryObject();
		query.setWhere("(" + alias + ".parentPath like '" + data.getParentPath() + Integer.toString(nodeId, TREE_NUM_RADIX) + "/%')");
		query.setOrder(alias + ".orderPath asc");
		return query;
		*/
	}
	
	/**
	 * 得到查询一个节点的子孙节点集合的查询对象（条件，可调排序）。
	 * @param nodeId - 树节点标识。
	 * @return
	 */
	public TreeViewQueryObject getChildrensQuery(int nodeId, boolean isAsc) {
		// 得到当前节点的树形结构信息。
		TreeItemObject parent_node = getTreeNode(nodeId);
		if (parent_node == null)
			throw new PublishException("当前节点'" + nodeId + "'不存在。");
		
		return getChildrensQuery(parent_node, isAsc);
	}

	/**
	 * 按照字节点的标识顺序重新排序字节点。
	 * 注意：需要事务支持。
	 * @param nodeId 父节点的标识。
	 * @param childrens 子节点的标识集合（有序）。
	 * @return 更新的记录数。
	 */
	public int reorderChildrens(int nodeId, int[] childrens) {
		if (getChildrensNum(nodeId) != childrens.length) {
			throw new PublishException("reOrderChildrens(int, int[]) 重新排序的字节点个数也当前节点的字节点个数不相等。");
		}
		// TODO:childrens 中存在相同的节点标识，抛出异常。
		int num = 0;
		// 验证子结点集合中的节点的父节点是否是当前的父节点。
		for (int i = 0; i < childrens.length; i++) {
			TreeItemObject temp = getTreeNode(childrens[i]);
			if (nodeId == temp.getParentId()) {
				// 开始更新排序路径。
				String oldOrderPath = temp.getOrderPath();
				String newOrderPath = oldOrderPath.substring(0, oldOrderPath.length() - TREE_ORDER_LENGTH - 1) + getStaticString(Integer.toString(i + 1, TREE_NUM_RADIX), TREE_ORDER_LENGTH, "0") + "/";
				String parentPath = temp.getParentPath() + Integer.toString(childrens[i], TREE_NUM_RADIX) + "/";
				num += updateNodesOrder(childrens[i], oldOrderPath, newOrderPath, parentPath);
			} else {
				throw new PublishException("出现一个意外的节点'" + childrens[i] + "'， 该节点不是节点'" + nodeId + "'的子节点。");
			}
		}
		return num;
	}
	
	/**
	 * 获得指定节点的子节点的个数。
	 * @param nodeId 节点的标识。
	 * @return 字节点的个数。
	 * @throws SQLException
	 */
	public long getChildrensNum(int nodeId) {
		String hql = "select count(id) from " + objectName + " where parentId=" + nodeId;
		List list = dao.list(hql);
		if (list != null && list.size() > 0) {
			return PublishUtil.safeGetLongVal(list.get(0));
		} else {
			throw new PublishException("getChildrensNum(int) 获得子节点个数的时候出现异常。");
		}
	}
	
	/**
	 * 得到指定节点的树形结构数据。
	 * @param nodeId 节点的标识。
	 * @return 树形结构数据。
	 * @throws SQLException
	 */
	public TreeItemObject getTreeNode(int nodeId) {
		String hql = "select parentId, parentPath, orderPath from " + objectName + " where id=" + nodeId;
		List list = dao.list(hql);
		if (list != null && list.size() > 0) {
			TreeItemObject treeViewData = new TreeItemObject();
			treeViewData.setId(nodeId);
			Object[] objects = (Object[])list.get(0);
			treeViewData.setParentId((Integer)objects[0]);
			treeViewData.setParentPath((String)objects[1]);
			treeViewData.setOrderPath((String)objects[2]);
			return treeViewData;
		}
		return null;
	}

	/**
	 * 删除自定路径下面的所有子孙结点。（未实现）
	 * @param path 指定节点的路径。
	 * @return
	 * @throws SQLException
	 */
	private int deleteChildrensAsPath(String path) {
//		String sql = "delete from " + objectName + " where ParentPath like '" + path + "%'";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		try {
//			return pstmt.executeUpdate();
//		} finally {
//			pstmt.close();
//		}
		return 0;
	}
	
	/**
	 * 移动子孙结点。(未实现)
	 * @param oldParentPath 原先的父路径。
	 * @param oldOrderPath 原先的排序路径。
	 * @param newParentPath 新的父路径。
	 * @param newOderPath 新的排序路径。
	 * @throws SQLException
	 * @return 更新影响的记录数。
	 */
	@SuppressWarnings("unused")
	private int moveChildrens(String oldParentPath, String oldOrderPath, String newParentPath, String newOrderPath) {
//		String sql = "update " + objectName + " set OrderPath=REPLACE(OrderPath, '" + oldOrderPath + "', '" + newOrderPath + "'), ParentPath=REPLACE(ParentPath, '" + oldParentPath + "', '" + newParentPath + "') where ParentPath like '" + oldParentPath + "%'";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		try {
//			return pstmt.executeUpdate();
//		} finally {
//			pstmt.close();
//		}
		return 0;
	}
	
	/**
	 * 更新兄弟节点的排序，指定的节点以及它后面的全部兄弟节点，按照指定的排序开始。
	 * @param nodeId 开始更新的兄弟节点的标识。
	 * @param orderId 开始更新的排序。
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private int updateBrothersOrder(int nodeId, int orderId) {
		// 得到当前节点后面的所有兄弟节点的树形结构信息。
		Iterator<TreeItemObject> brothers = getBrothers(nodeId);
		if (brothers == null) {
			throw new PublishException("兄弟节点不存在，无法执行更新。");
		}
		int num = 0;
		while (brothers.hasNext()) {
			TreeItemObject nodeData =  brothers.next();
			// 字节点的父节点路径。
			String parentPath = nodeData.getParentPath() + Integer.toString(nodeData.getId(), TREE_NUM_RADIX) + "/";
			String orderPath = nodeData.getOrderPath().substring(0, nodeData.getOrderPath().length() - TREE_ORDER_LENGTH - 1)+ getStaticString(Integer.toString(orderId, TREE_NUM_RADIX), TREE_ORDER_LENGTH, "0")  + "/";
			num += updateNodesOrder(nodeData.getId(), nodeData.getOrderPath(), orderPath, parentPath);
			orderId++;
		}
		return num;
	}
	
	/**
	 * 更新当前节点以及子孙节点的排序路径。
	 * @param nodeId 当前节点的标识。
	 * @param oldOrderPath 原先的排序路径。
	 * @param orderPath 新的排序路径。
	 * @param parentPath 父节点路径。
	 * @return 执行影响的记录数。
	 * @throws SQLException
	 */
	private int updateNodesOrder(final int nodeId, final String oldOrderPath, final String orderPath, final String parentPath) {
		// String sql = update Cor_Column set OrderPath=REPLACE(OrderPath, @oldOrderPath, @orderPath) where objectId=@columnId or ParentPath like @parentPath +'%';
		// TODO: pro_orderColumn 存储过程，执行批量的更新排序，调用存储过程完成该操作。
		// 或者直接调用connection执行数据库的SQL完成操作。
		dao.getHibernateTemplate().execute(new HibernateCallback() {
			// 得到底层的Connction，执行SQL的方法或者存储过程。
			public Object doInHibernate(Session session) throws HibernateException {
				try {
					CallableStatement cstmt = session.connection().prepareCall("{call pro_orderColumn(?, ?, ?, ?)}");
					cstmt.setInt(1, nodeId);
					cstmt.setString(2, oldOrderPath);
					cstmt.setString(3, orderPath);
					cstmt.setString(4, parentPath);
					try {
						cstmt.execute();
					} finally {
						cstmt.close();
					}
					return null;
				} catch (java.sql.SQLException ex) {
					throw new PublishException(ex);
				}
			}			
		});
		
		return 0;
	}
	
	/**
	 * 得到指定节点下面最后一个节点的排序路径。
	 * @param nodeId 节点的标识。
	 * @return 节点数据结构的数据。
	 */
	private String getLastOrderPath(int nodeId) {
		String hql = "select orderPath from " + objectName + " where parentId=" + nodeId + " order by orderPath desc";
		List list = dao.list(hql);
		if (list != null && list.size() > 0) {
			return (String)list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 得到指定节点后面的所有兄弟节点（不包括自己）。
	 * @param nodeId 节点的标识。
	 * @return 兄弟节点的集合。
	 * @throws SQLException
	 */
	private Iterator<TreeItemObject> getBrothers(int nodeId) {
		TreeItemObject selfData = getTreeNode(nodeId);
		if (selfData == null) {
			throw new PublishException("当前节点'" + nodeId + "'不存在。");
		}
		String hql = "select id, parentId, parentPath, orderPath from " + objectName + " where parentId=" + selfData.getParentId() + " and orderPath>='" + selfData.getOrderPath() + "' and id<>" + nodeId;
		List qlist = dao.list(hql);
		ArrayList<TreeItemObject> list = new ArrayList<TreeItemObject>();
		if (qlist != null && qlist.size() > 0) {
			for (int i = 0; i < qlist.size(); i++) {
				TreeItemObject nodeData = new TreeItemObject();
				nodeData.setId((Integer)((Object[])qlist.get(i))[0]);
				nodeData.setParentId((Integer)((Object[])qlist.get(i))[1]);
				nodeData.setParentPath((String)((Object[])qlist.get(i))[2]);
				nodeData.setOrderPath((String)((Object[])qlist.get(i))[3]);
				list.add(nodeData);
			}
		}
		return list.iterator();
	}
	
	/**
	 * 得到指定长度的字符串，在给定的字符串前面用指定的标记补充缺少的长度。
	 * @param str 原先的字符串。
	 * @param length 指定的长度。
	 * @param flag 补充长度的标记字符串。
	 * @return 给定长度的字符串。
	 */
	public String getStaticString(String str, int length, String flag) {
		StringBuffer buf = new StringBuffer();
		for (int i=0;i<(length - str.length());i++) {
			buf.append(flag);
		}
		buf.append(str);
		return buf.toString();
	}

}

