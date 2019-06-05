package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 自定义标签的管理集合对象。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class LabelCollection extends AbstractPublishModelBase implements PublishModelObject {
	/**
	 * 构造一个 LabelCollection 的实例。
	 *
	 */
	public LabelCollection() {
		
	}
	
	// === LabelCollection 的核心业务 ==============================================
	
	/**
	 * 获取指定标识的 Label 对象。
	 * @return - 从数据库装载的 Label 数据，如果没有则返回 null.
	 */
	public Label getLabel(int labelId) {
		Label label = (Label)this.pub_ctxt.getDao().get(Label.class, new Integer(labelId));
		// TODO: ??? label._init()
		//   如果 _init 了，则需要配对的 _destroy 等操作。
		return label;
	}

	/**
	 * 创建或更新自定义标签。
	 * @param label
	 */
	public void createOrUpdateLabel(Label label) {
		if (label.getObjectId() == 0)
			internalCreateLabel(label);
		else
			internalUpdateLabel(label);
	}
	
	/**
	 * 创建标签。
	 * @param label
	 */
	public void createLabel(Label label) {
		createOrUpdateLabel(label);
	}
	
	/**
	 * 更新标签。
	 * @param label
	 */
	public void updateLabel(Label label) {
		createOrUpdateLabel(label);
	}
	
	/**
	 * 查找指定名字的 Label 
	 * @param label_name
	 */
	public BuiltinLabel findLabel(String label_name) {
		return internalFindLabel(label_name);
	}
	
	/**
	 * 删除指定的标签。
	 * @param labelId 标签的标识。
	 */
	public void deleteLabel(int labelId) {
		// 获得此标签。
		Label label = getLabel(labelId);
		if (label == null) return;
		
		// 更新 Label 的删除标志。
		String hql = "UPDATE Label SET deleted = true WHERE id = " + labelId;
		pub_ctxt.getDao().bulkUpdate(hql);
		
		// 更新内存中映射表。
		updateLabelMap(label, null);
	}
	
	/**
	 * 获得管理用的标签列表。
	 * @param labelType - 标签类型，0 为用户，1 为系统内建，2 为学习。
	 * @param page_info
	 * @return 返回一个 DataTable, schema = {id, name, priority, labelType, description}
	 */
	public DataTable getLabelDataTable(int labelType, PaginationInfo page_info) {
		// 构造查询。
		String select_columns = "id, name, priority, labelType, description";
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + select_columns;
		query.whereClause = " WHERE labelType = " + labelType + " AND deleted = false ";
		query.fromClause = " FROM Label ";
		query.orderClause = " ORDER BY id ";

		// 查询数量。
		page_info.setTotalCount(query.queryTotalCount(pub_ctxt.getDao()));
		List list = query.queryData(pub_ctxt.getDao(), page_info);
		
		// 组装为 DataTable 并返回。
		DataTable data_table = new DataTable(PublishUtil.columnsToSchema(select_columns));
		PublishUtil.addToDataTable(list, data_table);
		
		return data_table;
	}
	
	// === 实现 ===================================================================
	
	// 内部创建一个标签。
	private final synchronized void internalCreateLabel(Label label) {
		// TODO: 执行验证.
		
		// 创建此标签到数据库.
		this.pub_ctxt.getDao().save(label);
		
		// 更新内存数据.
		updateLabelMap(null, label);
	}
	
	// 内部更新一个标签。
	private final synchronized void internalUpdateLabel(Label label) {
		// 1. 获取原来的标签。
		Label origin_label = getLabel(label.getId());
		if (origin_label == null) {
			throw new PublishException("要更新的标签不存在，要么该标签已经被删除，要么所指定的标识不正确。");
		}
		
		// 2. TODO: 执行验证。
		this._getPublishContext().getDao().clear();
		
		// 3. 更新此标签到数据库。
		this.pub_ctxt.getDao().save(label);
		
		// 4. 更新内存数据。
		updateLabelMap(origin_label, label);
	}

	// 内部查找一个标签.
	private final synchronized BuiltinLabel internalFindLabel(String label_name) {
		if (this._full_loaded == false) 
			performFullLoad();
		return this.label_map.get(label_name);
	}
	
	// 更新内存中的 label_map 表。
	private final synchronized void updateLabelMap(Label origin_label, Label new_label) {
		if (this._full_loaded == false) return;		// 还不需要更新。
		
		// 删除旧的项目,添加一个新的项目.
		if (origin_label != null)
			label_map.remove(origin_label.getName());
		if (new_label != null)
			label_map.put(new_label.getName(), 
					newBuiltinLabel(new_label.getName(), new_label.getId()));
	}
	
	
	private final BuiltinLabel newBuiltinLabel(String name, int id) {
		return new BuiltinLabel(this.pub_ctxt, name, id);
	}
	
	// 完全加载所有自定义标签.
	private final void performFullLoad() {
		this._full_loaded = false;
		label_map.clear();
		
		// 仅装载名字和 id 的一个映射表。
		String hql = "SELECT l.name, l.id FROM Label l WHERE deleted = false ";
		java.util.List list = pub_ctxt.getDao().list(hql);
		for (int index = 0; index < list.size(); ++index) {
			Object[] values = (Object[])list.get(index);
			BuiltinLabel entry = newBuiltinLabel((String)values[0], (Integer)values[1]);
			label_map.put(entry.getName(), entry);
		}
		list.clear();
		
		this._full_loaded = true;
	}
	
	// === 内部所有标签的名字集合 =====================================================

	/** 当前所有的 Label 名字,对象映射表，通过此表能够快速找到一个自定义标签。 */
	private final java.util.HashMap<String, BuiltinLabel> label_map = 
		new java.util.HashMap<String, BuiltinLabel>();
	
	/** label_map 数据是否已经装载的标志。 */
	private boolean _full_loaded = false;
}
