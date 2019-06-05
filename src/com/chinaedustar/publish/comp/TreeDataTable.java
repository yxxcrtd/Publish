package com.chinaedustar.publish.comp;

import java.util.List;

import com.chinaedustar.publish.model.*;

/**
 * 支持树信息的 DataTable.
 * 
 * @author liujunxing
 *
 */
public class TreeDataTable extends DataTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7034775520910407571L;

	/**
	 * 构造函数。
	 * @param schema
	 */
	public TreeDataTable(DataSchema schema) {
		super(schema);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.ArrayList#get(int)
	 */
	@Override public TreeDataRow get(int index) {
		return (TreeDataRow)super.get(index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.DataTable#newRow()
	 */
	@Override public TreeDataRow newRow() {
		return new TreeDataRow(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.DataTable#newRow(java.lang.Object[])
	 */
	@Override public TreeDataRow newRow(Object[] values) {
		return new TreeDataRow(this, values);
	}

	/**
	 * 添加一个新行。
	 */
	public boolean addRow(TreeDataRow row) {
		super.add(row);
		row.index = this.size() - 1;
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override public boolean add(DataRow row) {
		return addRow((TreeDataRow)row);
	}
	
	// === 提供给 TreeDataRow 的支持函数 =============================================
	
	/**
	 * 得到指定节点的父节点。
	 */
	public TreeDataRow _getParentObject(TreeDataRow column_row) {
		int parent_id = column_row.getParentId();
		if (parent_id == 0) return null;		// 没有了。

		// 从当前索引向上找，速度会比较快。
		for (int i = column_row.index - 1; i >= 0; --i) {
			TreeDataRow row = this.get(i);
			if (row.getId() == parent_id) return row;
		}
		return null;
	}
	
	/**
	 * 计算指定节点的子节点数量，不含孙节点。
	 * @param column_row
	 * @return
	 */
	public int _getChildCount(TreeDataRow column_row) {
		int node_id = column_row.getId();
		String node_path = column_row.getNodePath();
		
		int child_count = 0;
		for (int i = column_row.index + 1; i < this.size(); ++i) {
			TreeDataRow row = this.get(i);
			if (row.getParentPath().startsWith(node_path) == false) break;
			if (row.getParentId() == node_id)
				++child_count;
		}
		return child_count;
	}
	
	/**
	 * 计算指定节点的所有子孙节点数量。
	 * @param column_row
	 * @return
	 */
	public int _getAllChildrenCount(TreeDataRow column_row) {
		String node_path = column_row.getNodePath();
		
		int children_count = 0;
		for (int i = column_row.index + 1; i < this.size(); ++i) {
			TreeDataRow row = this.get(i);
			if (row.getParentPath().startsWith(node_path) == false) break;
			++children_count;
		}
		return children_count;
	}

	/**
	 * 计算是否有子节点。
	 * @param column_row
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean _getHasChild(TreeDataRow column_row) {
		int node_id = column_row.getId();
		for (int i = column_row.index + 1; i < this.size(); ++i) {
			TreeDataRow row = this.get(i);
			// 下一个节点要么是子节点，要么不是。如果不是，则一定没有子节点了。
			if (row.getParentId() == node_id)
				return true;
			else
				return false;
		}
		return false;
	}
	
	/**
	 * 获得子节点集合。
	 * @param column_row
	 * @return
	 */
	public List<TreeDataRow> _getChildObjects(TreeDataRow column_row) {
		String node_path = column_row.getNodePath();
		int node_id = column_row.getId();
		List<TreeDataRow> result = new java.util.ArrayList<TreeDataRow>();
		for (int i = column_row.index + 1; i < this.size(); ++i) {
			TreeDataRow row = this.get(i);
			if (row.getParentId() == node_id)
				result.add(row);
			if (row.getParentPath().startsWith(node_path) == false) break;
		}
		return result;
	}

	/**
	 * 获得所有子孙节点。
	 * @param column_row
	 * @return
	 */
	public List<TreeDataRow> _getChildrens(TreeDataRow column_row) {
		String node_path = column_row.getNodePath();
		List<TreeDataRow> result = new java.util.ArrayList<TreeDataRow>();
		for (int i = column_row.index + 1; i < this.size(); ++i) {
			TreeDataRow row = this.get(i);
			if (row.getParentPath().startsWith(node_path))
				result.add(row);
			else
				break;
		}
		return result;
	}
	
	/**
	 * 获得前一个兄节点。
	 * @param column_row
	 * @return
	 */
	public TreeDataRow _getPrevSibling(TreeDataRow column_row) {
		int parent_id = column_row.getParentId();
		for (int i = column_row.index - 1; i >= 0; --i) {
			TreeDataRow row = this.get(i);
			if (row.getParentId() == parent_id)
				return row;
		}
		return null;
	}

	/**
	 * 获得后一个弟节点。
	 * @param column_row
	 * @return
	 */
	public TreeDataRow _getNextSibling(TreeDataRow column_row) {
		int parent_id = column_row.getParentId();
		for (int i = column_row.index + 1; i < this.size(); ++i) {
			TreeDataRow row = this.get(i);
			if (row.getParentId() == parent_id)
				return row;
		}
		return null;
	}

	/**
	 * 整体计算一遍所有子节点的 tree_flag.
	 * tree_flag 是一个字符串，其包含如下几种字符：
	 *    '|' - 表示这里显示一个竖线，也即代表该层的祖先节点拥有一个弟节点。
	 *    'B' - 显示一个空白图片，表示该层的祖先节点没有弟节点。
	 *    'L' - 显示一个折线，表示是一个子节点，且子节点没有弟节点。
	 *    'T' - 显示一个 '|-' (横过来的 T)型的线，表示是一个子节点，且有弟节点。
	 *    '+' - 显示一个 '+' 的图片，表示该节点有子节点。
	 *    '-' - 显示一个 '-' 的图片，表示该节点没有子节点。
	 */
	public void _calcTreeFlags() {
		if (this.size() == 0) return;
		
		// 整个反向扫描一次所有行，使用特定的算法计算出所有行的 tree_flag
		TreeDataRow last_row = this.get(this.size()-1);
		String tree_flag = initLastTreeFlag(last_row);
		last_row.tree_flag = tree_flag;
		
		for (int i = this.size() - 2; i >= 0; --i) {
			TreeDataRow row = this.get(i);
			tree_flag = calcRowFlag(row, tree_flag);
			row.tree_flag = tree_flag;
		}
	}
	
	// 初始化为最后一行产生 tree_flag
	private String initLastTreeFlag(TreeDataRow last_row) {
		// 根据生成机制，len 和 depth 应该相同。
		int depth = last_row.getDepth();
		String tree_flag = "";
		for (int i = 2; i < depth; ++i)
			tree_flag += 'B';
		if (depth > 1) tree_flag += 'L';
		tree_flag += '-';
		return tree_flag;
	}
	
	// 计算指定行的标志。下面的计算根据下一行和当前行的各种关系进行着复杂的计算，太复杂我不想再计算一遍了。
	private String calcRowFlag(TreeDataRow row, String flags) {
		int len = flags.length();
		flags = len <= 1 ? "" : flags.substring(0, len - 1); // 吃掉最后一个 +- 标志。
		int depth = row.getDepth();
		if (len == depth) {
			// 前一行和当前行在同一级，则前一行是我们的弟节点，而我们没有子节点。
			if (flags.length() > 0)
				return flags.substring(0, flags.length() - 1) + "T-";
			else 
				return "-";
		} 
		if (len < depth) {
			// 当前行和前一行的关系是，当前行的某个祖先是前一行的兄节点。
			if (flags.length() > 0)
				flags = flags.substring(0, flags.length() - 1) + '|'; // 最后一个换成 '|'
			// 补齐中间的空白祖先。
			while (len < depth-1) { 
				flags += 'B';
				++len;
			}
			return flags + "L-";
		}
		
		// 最后剩下 len > depth, 如果数据没有错误一定是 len-1 = depth
		// 这代表当前节点是前一个节点的父节点。
		// sample: prev_flags = '|T-', this_flags = 'T+' 
		if (depth > 1) {
			flags = flags.substring(0, depth - 1);
			if (flags.endsWith("|"))		// 最后一个 '|' 换成 'T'
				flags = flags.substring(0, flags.length() - 1) + 'T';
			else if (flags.endsWith("B"))		// 最后一个 'B' 换成 'L'
				flags = flags.substring(0, flags.length() - 1) + 'L';
		}
		else
			flags = "";
		return flags + '+';
	}
}
