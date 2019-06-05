package com.chinaedustar.publish.comp;

import com.chinaedustar.publish.model.*;

/**
 * 栏目权限包装。
 * 
 * <pre>
 * 对象模型：
 * DataTable
 *   .schema = 'prefix, id(column), name, depth,  --- 
 *   		view(right), inputer, editor, manage'
 *   
 * 根据此 List 能够产生出整个频道下所有栏目的权限列表。模板语法：
 * 
 * #foreach column in column_list
 *   #column.prefix #column.name | input name='view', input name='inputer', ...
 * #/foreach
 * </pre>
 *  
 * @author liujunxing
 *
 */
public class ColumnRightWrapper extends BaseRightWrapper {
	/** 栏目权限项子集。 */
	private AdminRightCollection column_rights;
	
	private DataTable column_list;
	
	/**
	 * 构造。
	 * @param channel
	 * @param admin
	 */
	public ColumnRightWrapper(Channel channel, Admin admin) {
		super(channel, admin);
		
		init();
	}
	
	/**
	 * 获得栏目列表，其中包含权限信息。
	 * @return
	 */
	public DataTable getColumnList() {
		return this.column_list;
	}
	
	// 内部初始化。
	private void init() {
		this.column_rights = admin._getRights().getColumnRightSubColl(channel.getId());
		
		// 构造基本 DataTable, 其具有 id, name, parentId, parentPath 属性。
		ColumnTree column_tree = channel.getColumnTree();
		DataTable data_table = column_tree.getAdvColumnListDataTable(null);
		// 不再需要了：添加前缀。
		// TreeUtil.addSelectPrefix(data_table, "&nbsp;&nbsp;");
		
		// 添加权限信息。
		addRightInfo(data_table);
		
		this.column_list = data_table;
	}
	
	private void addRightInfo(DataTable data_table) {
		// 添加 view, inputer, editor, manage 4 个权限项，每个都是 boolean 型。
		DataSchema schema = data_table.getSchema();
		int view_index = schema.addColumn("view");
		int inputer_index = schema.addColumn("inputer");
		int editor_index = schema.addColumn("editor");
		int manage_index = schema.addColumn("manage");
		int id_index = schema.getColumnIndex("id");
		
		// 为每个栏目设置其权限值。
		for (int i = 0; i < data_table.size(); ++i) {
			boolean view = false;
			boolean inputer = false;
			boolean editor = false;
			boolean manage = false;
			
			DataRow row = data_table.get(i);
			int columnId = (Integer)row.get(id_index);
			AdminRight right = findColumnRightEntry(columnId);
			if (right != null) {
				int mask = right.getOperAsInt();
				view = (mask & Admin.COLUMN_RIGHT_VIEW) != 0;
				inputer = (mask & Admin.COLUMN_RIGHT_INPUTER) != 0;
				editor = (mask & Admin.COLUMN_RIGHT_EDITOR) != 0;
				manage = (mask & Admin.COLUMN_RIGHT_MANAGE) != 0;
			}
			
			row.set(view_index, view);
			row.set(inputer_index, inputer);
			row.set(editor_index, editor);
			row.set(manage_index, manage);
		}
	}
	
	// 查找指定标识的栏目的权限项。
	private AdminRight findColumnRightEntry(int columnId) {
		return column_rights._findColumnRightEntry(columnId);
	}
}
