package com.chinaedustar.publish.engine;

import java.util.List;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 指定栏目下项目生成器。(包含其子栏目)
 * @author liujunxing
 *
 */
public class ItemInColumnGenerator extends AbstractItemGenerator {
	/** 指定的栏目。 */
	private final Column column;
	
	private boolean filled = false;
	
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 * @param channel
	 * @param beginId
	 * @param endId
	 */
	public ItemInColumnGenerator(PublishContext pub_ctxt, Channel channel, Column column) {
		super(pub_ctxt, channel);
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractItemGenerator#fillUngenItems()
	 */
	@SuppressWarnings("rawtypes")
	@Override protected boolean fillUngenItems() {
		if (filled) return false;
		filled = true;
		
		// 得到栏目及其所有子栏目。
		List<Integer> columnIds = channel.getColumnTree().getAllChildColumnIds(column);
		columnIds.add(column.getId());
		
		// 查找日期范围 beginDate, endDate 之内的。
		QueryHelper query = new QueryHelper();
		query.selectClause = " SELECT id, itemClass ";
		query.fromClause = " FROM Item ";
		query.whereClause = " WHERE channelId = " + channel.getId() +
			"  AND deleted = false AND status = 1 " + 
			"  AND columnId IN (" + PublishUtil.toSqlInString(columnIds) + ")";
		query.orderClause = " ORDER BY id DESC";
		List list = query.queryData(pub_ctxt.getDao());
		if (list == null || list.size() == 0) return false;
		
		super.fillUngenByList(list);
		
		return true;
	}
}
