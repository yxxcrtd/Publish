package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.pjo.Layout;

/**
 * 布局集合业务对象。
 * @author liujunxing
 *
 */
public class LayoutCollection extends AbstractPublishModelBase {
	/**
	 * 构造函数。
	 *
	 */
	public LayoutCollection() {
		
	}
	
	/**
	 * 得到管理用的所有布局列表。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public DataTable getLayoutDataTable() {
		// 查询 Layout 表，获取 id, name, description 字段。
		String fields = "id, name, description";
		String hql = "SELECT " + fields + " FROM Layout ORDER BY id";
		List list = pub_ctxt.getDao().list(hql);
		
		// 组装为 DataTable 返回。
		DataTable result = new DataTable(new DataSchema(fields));
		PublishUtil.addToDataTable(list, result);
		return result;
	}

	/**
	 * 得到指定标识的布局对象，用于编辑修改。如果没有则返回 null.
	 * @param id
	 * @return
	 */
	public Layout getLayout(int id) {
		Layout layout = (Layout)pub_ctxt.getDao().get(Layout.class, id);
		return layout;
	}

	/**
	 * 新建/更新一个布局。
	 * @param layout
	 */
	public void saveLayout(Layout layout) {
		pub_ctxt.getDao().save(layout);
		pub_ctxt.getDao().flush();
	}
}
