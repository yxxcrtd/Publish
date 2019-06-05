package com.chinaedustar.publish.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.comp.ColumnRightWrapper;
import com.chinaedustar.publish.comp.SiteRightWrapper;

/**
 * 表示一个管理员的所有权限的集合。
 * 
 * @author liujunxing
 * 
 * 其中全站权限
 *    每个权限项以一个 target='site' or 'user' 表示。
 * 
 * 频道权限项含：
 *    target='channel_role', operation=int role_value (最多一个)
 *    target='channel' operation='操作' (多个)
 *    
 * 栏目权限项含：
 *    target='column' operation='view|inputer|editor|manage' 的组合。
 *    
 */
public class AdminRightCollection extends AbstractPublishModelBase {
	/** 表示一个空的集合。 */
	private static final List<AdminRight> EMPTY_RIGHT_LIST = new ArrayList<AdminRight>();
	
	/** 内部管理员权限集合1 - 全站权限。 */
	private List<AdminRight> site_rights;
	
	/** 内部管理员权限集合2 - 频道权限。 */
	private List<AdminRight> channel_rights;

	/** 内部管理员权限集合3 - 栏目权限。 */
	private List<AdminRight> column_rights;

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.model.PublishModelObject)
	 */
	@Override public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		// 从数据库加载此用户的所有权限进入。
		String hql = "FROM AdminRight WHERE userId = " + ((Admin)owner_obj).getId() +
			" ORDER BY channelId, columnId ";
		@SuppressWarnings("unchecked")
		List<AdminRight> result = pub_ctxt.getDao().list(hql);
		
		this.site_rights = new ArrayList<AdminRight>();
		this.channel_rights = new ArrayList<AdminRight>();
		this.column_rights = new ArrayList<AdminRight>();
		
		for (int i = 0; i < result.size(); ++i) {
			AdminRight right = result.get(i);
			if (right.getChannelId() == null && right.getColumnId() == null)
				site_rights.add(right);
			else if (right.getColumnId() == null)
				channel_rights.add(right);
			else
				column_rights.add(right);
		}
	}
	
	/**
	 * 得到指定频道的栏目权限集合的子集。子集能够加快对某个频道下栏目权限验证速度。
	 * 
	 * @param channelId
	 * @return
	 */
	public AdminRightCollection getColumnRightSubColl(int channelId) {
		// 构造子集对象。
		AdminRightCollection new_coll = new AdminRightCollection();
		new_coll.pub_ctxt = this.pub_ctxt;
		new_coll.owner_obj = this.owner_obj;
		new_coll.site_rights = EMPTY_RIGHT_LIST;
		new_coll.channel_rights = EMPTY_RIGHT_LIST;
		new_coll.column_rights = new ArrayList<AdminRight>();
		
		// 将所有指定频道的权限项放到子集中。
		for (int i = 0; i < this.column_rights.size(); ++i) {
			AdminRight right = this.column_rights.get(i);
			if (right.getChannelId() == channelId)
				new_coll.column_rights.add(right);
		}
		return new_coll;
	}
	
	/**
	 * 得到指定频道的频道权限的子集。子集用于方便获取权限。
	 * @param channelId
	 * @return
	 */
	public AdminRightCollection getChannelRightSubColl(int channelId) {
		// 构造子集对象。
		AdminRightCollection new_coll = new AdminRightCollection();
		new_coll.pub_ctxt = this.pub_ctxt;
		new_coll.owner_obj = this.owner_obj;
		new_coll.site_rights = EMPTY_RIGHT_LIST;
		new_coll.channel_rights = new ArrayList<AdminRight>();
		new_coll.column_rights = EMPTY_RIGHT_LIST;
		
		// 将所有指定频道的权限项放到子集中。
		for (int i = 0; i < this.channel_rights.size(); ++i) {
			AdminRight right = this.channel_rights.get(i);
			if (right.getChannelId() == channelId)
				new_coll.channel_rights.add(right);
		}
		return new_coll;
	}
	
	/**
	 * 检查此管理员是否在 Site 全局指定对象上面的指定操作权限。 
	 * @param target
	 * @param operation
	 * @return
	 */
	public boolean checkSiteRight(String target, String operation) {
		// 查找所有站点权限项。
		for (int i = 0; i < site_rights.size(); ++i) {
			AdminRight right = site_rights.get(i);
			if (right.hasRight(target, operation))
				return true;
		}
		return false;
	}
	
	/**
	 * 检查此管理员是否具有指定频道、指定对象上面的指定操作权限。
	 * @param channel
	 * @param target
	 * @param operation
	 * @return
	 */
	public boolean checkChannelRight(int channelId, String target, String operation) {
		// 查找所有频道权限项。
		for (int i = 0; i < channel_rights.size(); ++i) {
			AdminRight right = channel_rights.get(i);
			if (right.getChannelId().intValue() == channelId) {
				if (right.hasRight(target, operation))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查此管理员是否具有指定频道、指定栏目、指定对象上面的指定操作权限。
	 * @param channel - 频道，可能为 null
	 * @param column - 栏目，可能为 null
	 * @param target - 目标对象，可能为 null。
	 * @param operation - 操作类型。
	 * @return
	 */
	public boolean checkColumnRight(int channelId, TreeItemInterface column, String target, int oper) {
		if (column == null) throw new java.lang.IllegalArgumentException("column is null");
		// 判定在当前频道的角色。
		int roleValue = this.getChannelRoleValue(channelId);
		// role = none 则没有任何权限。
		if (roleValue == Admin.CHANNEL_ROLE_NONE) return false;
		// role = manager 则拥有所有栏目权限。
		if (roleValue == Admin.CHANNEL_ROLE_MANAGER) return true;
		// role = editor 则拥有栏目所有查看、录入、审核权，但没有管理权。
		if (roleValue == Admin.CHANNEL_ROLE_EDITOR) {
			if ((oper & Admin.COLUMN_RIGHT_MANAGE) == 0)
				return true;
			else
				return false;
		}
		
		// role = column 栏目管理员则要查找所在栏目权限项。
		int columnId = column.getId();
		int[] parent_columnId = TreeViewModel.parseParentPath(column.getParentPath());
		int oper_mask = oper & Admin.COLUMN_RIGHT_ALL;
		for (int i = 0; i < column_rights.size(); ++i) {
			AdminRight right = column_rights.get(i);
			// 频道标识相同
			if (right.getChannelId().intValue() == channelId) {
				// 栏目标识 == columnId 或者在该栏目的父栏目中。
				if (right.getColumnId().intValue() == columnId ||
						in_array(right.getColumnId().intValue(), parent_columnId)) {
					int mask = PublishUtil.safeParseInt(right.getOperation(), 0) & Admin.COLUMN_RIGHT_ALL;
					oper_mask -= (oper_mask & mask); 	// 去掉相同的位
					if (oper_mask == 0) return true;
					// oper_mask != 0 表示还有权限项等待判定，继续循环找。
				}
			}
		}
		return false;
	}

	/**
	 * 获得此管理员在指定频道的角色。
	 * @param channelId
	 * @return
	 */
	public int getChannelRoleValue(int channelId) {
		// 如果没有则返回缺省。
		if (this.channel_rights == null || this.channel_rights.size() == 0)
			return Admin.CHANNEL_ROLE_NONE;
		
		// 遍历集合查找 target == Admin.TARGET_CHANNEL_ROLE 的值。
		for (int i = 0; i < channel_rights.size(); ++i) {
			AdminRight right = channel_rights.get(i);
			if (right.getChannelId().equals(channelId) && Admin.TARGET_CHANNEL_ROLE.equals(right.getTarget())) {
				return right.getOperAsInt();
			}
		}
		
		return Admin.CHANNEL_ROLE_NONE;
	}
	
	private static final boolean in_array(int columnId, int[] ids) {
		if (ids == null || ids.length == 0) return false;
		for (int i = 0; i < ids.length; ++i)
			if (ids[i] == columnId)
				return true;
		return false;
	}

	// === 为模板支持提供的数据 ================================================================
	
	/**
	 * 构造站点权限对象，该对象为了能够在模板中访问有访问增强。
	 */
	public SiteRightWrapper createSiteRightWrapper() {
		return new SiteRightWrapper(this);
	}

	// 由模块负责实现的。createChannelRightWrapper(Channel channel, Admin admin)
	
	/**
	 * 构造指定频道的栏目权限包装对象。
	 * @return
	 */
	public Object createColumnRightWrapper(Channel channel, Admin admin) {
		ColumnRightWrapper wrapper = new ColumnRightWrapper(channel, admin);
		return wrapper;
	}

	/**
	 * 查找具有指定标识的栏目的权限项。假定已经获取了一个频道的子集了，该调用在子集上面发生。
	 * @param columnId
	 * @return 如果未找到则返回 0。
	 */
	public AdminRight _findColumnRightEntry(int columnId) {
		if (this.column_rights == null || this.column_rights.size() == 0) return null;
		Iterator<AdminRight> iter = this.column_rights.iterator();
		while (iter.hasNext()) {
			AdminRight right = iter.next();
			// 也许以后限定 right.getTarget() == 'column'
			if (columnId == right.getColumnId())
				return right;
		}
		return null;
	}

	/**
	 * 更新权限, 需要事务支持。
	 * @param old_rights - 原来的权限，用于比对。
	 */
	public void update(AdminRightCollection old_rights) {
		// 比较粗野的方式是删除掉所有旧的权限，然后插入新的权限。但是这将导致即使不修改也会新插入
		//   很多数据记录，删除很多记录。粗野也不是我们的风格。所以这里使用了复杂的比较插入法。
		
		// 1. 更新站点级权限。
		_updateSiteRight(old_rights);
	
		// 2. 更新频道级权限。
		_updateChannelRight(old_rights);
		
		// 3. 更新栏目级权限。
		_updateColumnRight(old_rights);
		
		pub_ctxt.getDao().flush();
	}
	
	// 内部更新站点级权限。
	private void _updateSiteRight(AdminRightCollection old_rights) {
		DataAccessObject dao = pub_ctxt.getDao();
		// 1. 查找所有已经存在的权限项，如果已经存在，则不进行更新。
		for (int i = this.site_rights.size()-1; i >= 0; --i) {
			AdminRight new_right = site_rights.get(i);
			AdminRight old_right = old_rights._takeSiteRightEntry(new_right);
			if (old_right != null) 
				this.site_rights.remove(i);
		}
		
		// 2. 新权限剩下的项目需要插入.
		for (int i = 0; i < this.site_rights.size(); ++i) {
			AdminRight right = site_rights.get(i);
			dao.insert(right);
		}
		
		// 3. old_rights 剩下的项目需要删除。
		for (int i = 0; i < old_rights.site_rights.size(); ++i) {
			AdminRight right = old_rights.site_rights.get(i);
			dao.delete(right);
		}
	}
	
	// 内部更新频道级权限。
	private void _updateChannelRight(AdminRightCollection old_rights) {
		DataAccessObject dao = pub_ctxt.getDao();
		// 1. 查找所有已经存在的权限项，如果已经存在，则不进行更新。
		for (int i = this.channel_rights.size() - 1; i >= 0; --i) {
			AdminRight new_right = this.channel_rights.get(i);
			AdminRight old_right = old_rights._takeChannelRightEntry(new_right);
			if (old_right != null)
				this.channel_rights.remove(i);
		}
		
		// 2. 新权限剩下的项目需要插入.
		for (int i = 0; i < this.channel_rights.size(); ++i) {
			AdminRight right = channel_rights.get(i);
			dao.insert(right);
		}
		
		// 3. old_rights 剩下的项目需要删除。
		for (int i = 0; i < old_rights.channel_rights.size(); ++i) {
			AdminRight right = old_rights.channel_rights.get(i);
			dao.delete(right);
		}
	}
	
	// 内部更新栏目级权限。
	private void _updateColumnRight(AdminRightCollection old_rights) {
		DataAccessObject dao = pub_ctxt.getDao();
		// 1. 查找所有已经存在的权限项，如果已经存在，则不进行更新。
		for (int i = this.column_rights.size() - 1; i >= 0; --i) {
			AdminRight new_right = this.column_rights.get(i);
			AdminRight old_right = old_rights._takeColumnRightEntry(new_right);
			if (old_right != null)
				this.column_rights.remove(i);
		}
		
		// 2. 新权限剩下的项目需要插入，或者借用原来的权限项进行更新。
		for (int i = 0; i < this.column_rights.size(); ++i) {
			AdminRight new_right = this.column_rights.get(i);
			AdminRight old_right = old_rights._useExistColumnRightEntry();
			if (old_right != null) {
				new_right.setId(old_right.getId());
				dao.update(new_right);
			} else {
				dao.insert(new_right);
			}
		}
		
		// 3. old_rights 剩下的项目需要删除。
		for (int i = 0; i < old_rights.column_rights.size(); ++i) {
			AdminRight right = old_rights.column_rights.get(i);
			dao.delete(right);
		}
	}
	
	// 查找并取走和 right 相同的 Site 级别权限项，如果没有则返回 null。
	private AdminRight _takeSiteRightEntry(AdminRight right) {
		for (int i = 0; i < this.site_rights.size(); ++i) {
			AdminRight this_right = site_rights.get(i);
			if (right._equals(this_right)) {
				site_rights.remove(i);
				return this_right;
			}
		}
		return null;
	}
	
	// 查找并取走一个频道级权限。如果没有则返回 null
	private AdminRight _takeChannelRightEntry(AdminRight right) {
		for (int i = 0; i < this.channel_rights.size(); ++i) {
			AdminRight this_right = channel_rights.get(i);
			if (right._equals(this_right)) {
				channel_rights.remove(i);
				return this_right;
			}
		}
		return null;
	}
	
	// 查找并取走一个栏目级权限。如果没有则返回 null
	private AdminRight _takeColumnRightEntry(AdminRight right) {
		for (int i = 0; i < this.column_rights.size(); ++i) {
			AdminRight this_right = column_rights.get(i);
			if (right._equals(this_right)) {
				column_rights.remove(i);
				return this_right;
			}
		}
		return null;
	}
	
	// 使用现有的一个栏目权限项进行更新。如果没有则返回 null.
	private AdminRight _useExistColumnRightEntry() {
		if (this.column_rights.size() > 0)
			return this.column_rights.remove(this.column_rights.size()-1);

		return null;
	}
	
	
	// === 为组装对象提供的支持 ==================================================================
	
	/**
	 * 初始化一个 AdminRightCollection 的构建，用于从提交的数据中组装 AdminRightCollection 对象。
	 */
	public void _initBuild() {
		this.site_rights = new ArrayList<AdminRight>();
		this.channel_rights = new ArrayList<AdminRight>();
		this.column_rights = new ArrayList<AdminRight>();
	}
	
	/**
	 * 添加一个网站级权限项。
	 * @param right
	 */
	public void _addRightEntry(AdminRight right) {
		if (right.getChannelId() == null && right.getColumnId() == null)
			this.site_rights.add(right);
		else if (right.getColumnId() == null)
			this.channel_rights.add(right);
		else
			this.column_rights.add(right);
	}

	/**
	 * 判定是否没有任何权限。
	 *
	 */
	public boolean _isNoRight() {
		if (this.site_rights.size() > 0) return false;
		if (this.channel_rights.size() > 0) return false;
		if (this.column_rights.size() > 0) return false;
		
		return true;
	}

	/**
	 * 在 DataTable{id, parentId, name ...} 的栏目数据表中添加所有栏目权限信息。
	 * 调用之前，使用 getColumnRightSubColl() 获得子集之后再调用。
	 */
	public void addColumnRightInfo(DataTable data_table) {
		internalAddRightInfo(data_table);
	}
	
	private void internalAddRightInfo(DataTable data_table) {
		if (data_table == null || data_table.size() == 0) return;
		
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
			AdminRight right = _findColumnRightEntry(columnId);
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
}
