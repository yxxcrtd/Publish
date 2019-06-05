package com.chinaedustar.publish.comp;

import java.util.List;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.model.*;

/**
 * TreeDataTable 中使用的 DataRow, 其支持带有树信息。
 * 
 * @author liujunxing
 *
 */
public class TreeDataRow extends DataRow implements TreeItemInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3425629132098891372L;

	/**
	 * 在 DataTable 中的索引，不可更改。
	 * 为了优化计算，将大量使用 index 字段，因此外面一定不要更改。 
	 */
	int index;
	
	/**
	 * 构造函数。
	 * @param table
	 */
	public TreeDataRow(TreeDataTable table) {
		super(table);
	}

	/**
	 * 构造函数。
	 * @param table
	 */
	public TreeDataRow(TreeDataTable table, Object[] values) {
		super(table, values);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.DataRow#getDataTable()
	 */
	@Override public TreeDataTable getDataTable() {
		return (TreeDataTable)super.getDataTable();
	}
	
	// === TreeItemInterface 接口实现 ================================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.TreeItemInterface#getId()
	 */
	public int getId() {
		return (Integer)super.get("id");
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.TreeItemInterface#getOrderPath()
	 */
	public String getOrderPath() {
		return (String)super.get("orderPath");
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.TreeItemInterface#getParentId()
	 */
	public int getParentId() {
		return (Integer)super.get("parentId");
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.TreeItemInterface#getParentPath()
	 */
	public String getParentPath() {
		return (String)super.get("parentPath");
	}

	/**
	 * 获得栏目名。
	 * @return
	 */
	public String getName() {
		return (String)super.get("name");
	}
	
	/**
	 * 获得这个节点的路径，其 = parentPath + nodeId + '/'
	 * @return
	 */
	public String getNodePath() {
		return getParentPath() + TreeViewModel.calcTreePath(getId()) + "/";
	}
	
	// === 为树提供的功能 ==============================================================
	
	/**
	 * 获得此行代表的栏目的父栏目。如果没有则返回 null.
	 * @return 返回父栏目。
	 */
	public TreeDataRow getParentObject() {
		return getDataTable()._getParentObject(this);
	}
	
	/**
	 * 获得此栏目的所有祖先栏目列表。
	 * @return
	 */
	public List<TreeDataRow> getAncestorObjects() {
		List<TreeDataRow> result = new java.util.ArrayList<TreeDataRow>();
		result.add(0, this);
		
		TreeDataRow the_column = this;
		while (true) {
			TreeDataRow parent_column = getDataTable()._getParentObject(the_column);
			if (parent_column == null) break;
			result.add(0, parent_column);
			the_column = parent_column;
		}
		
		return result;
	}
	
	/**
	 * 获得此栏目的子栏目数量。
	 * @return
	 */
	public int getChildCount() {
		return getDataTable()._getChildCount(this);
	}
	
	/**
	 * 判断是否具有子栏目。
	 * @return
	 */
	public boolean getHasChild() {
		return getDataTable()._getHasChild(this);
	}
	
	/**
	 * 获得子栏目列表。
	 * @return
	 */
	public List<TreeDataRow> getChildObjects() {
		return getDataTable()._getChildObjects(this);
	}
	
	/**
	 * 获得所有子孙栏目，其返回又是一个新建的 List 。
	 * @return
	 */
	public List<TreeDataRow> getChildrens() {
		return getDataTable()._getChildrens(this);
	}
	
	/**
	 * 获得前一个兄弟栏目。
	 * @return
	 */
	public TreeDataRow getPrevSibling() {
		return getDataTable()._getPrevSibling(this);
	}

	/**
	 * 获得后一个兄弟栏目。
	 * @return
	 */
	public TreeDataRow getNextSibling() {
		return getDataTable()._getNextSibling(this);
	}
	
	/**
	 * 获得是否具有弟节点。
	 * @return
	 */
	public boolean getHasSibling() {
		return getNextSibling() != null;
	}
	
	/**
	 * 获得此栏目深度。
	 * @return
	 */
	public int getDepth() {
		return TreeViewModel.calcPathDepth(this.getParentPath());
	}

	/**
	 * 获得在 html select option 标记中前面显示的内容。 = 深度*两个空格 + '└'。
	 * 更精准的算法可以使用 treeFlag 来获得。
	 * @return
	 */
	public String getPrefix() {
		int depth = getDepth();
		String prefix = StringHelper.repeat("&nbsp;&nbsp;", depth - 2);
		if (depth > 1)
			prefix += "└";
		return prefix;
	}
	
	/**
	 * 获得这个节点为产生树状结构所使用的信息，包括 其是否有子节点、有没有弟节点、前面应该放多少个填充图片信息。
	 * 以下图为例：	
	   + 第一级栏目 			前面显示一个 +
	   |-+ 子栏目				前面显示 |-+				 
	   | L-+ A 
	   |   L-- B 
	   |-+ 子栏目2 
	   |  |-+ C
	   |    |-- D
	   |	|   L-- G 
	   |	|-- E 
	   |	    L-- F		前面显示 | 空 空 L-, 取决于其父节点没有弟节点了。 
	   |-- H
	   L-- I 
	 * 
	 * 总计有四种图，| 表示有弟节点；L 表示有子节点，子节点不再有弟节点；
	 *      |- 表示有子节点，子节点还有弟节点； 空 表示该层节点没有弟节点。
	 *      
	 * 对整个树进行一遍计算会加快整体速度，而且容易进行。
	 * 
	 * @return
	 */
	public String getTreeFlag() {
		if (this.tree_flag == null)
			getDataTable()._calcTreeFlags();
		return this.tree_flag;
	}
	
	/** internal 树节点标志，一次性计算出来，计算之后不要修改树结构。 */
	String tree_flag;
}
