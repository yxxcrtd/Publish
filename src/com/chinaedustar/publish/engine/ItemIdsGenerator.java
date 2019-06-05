package com.chinaedustar.publish.engine;

import java.util.List;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 指定项目标识数组的生成器。
 * @author liujunxing
 *
 */
public class ItemIdsGenerator extends AbstractItemGenerator {
	/** 标识范围。 */
	private final List<Integer> itemIds;
	
	private boolean filled = false;
	
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 * @param channel
	 * @param beginId
	 * @param endId
	 */
	public ItemIdsGenerator(PublishContext pub_ctxt, Channel channel, List<Integer> itemIds) {
		super(pub_ctxt, channel);
		this.itemIds = itemIds;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractItemGenerator#fillUngenItems()
	 */
	@SuppressWarnings("rawtypes")
	@Override protected boolean fillUngenItems() {
		if (filled) return false;
		filled = true;
		
		// 查找 ID 范围 itemIds 之内的。
		QueryHelper query = new QueryHelper();
		query.selectClause = " SELECT id, itemClass ";
		query.fromClause = " FROM Item ";
		query.whereClause = " WHERE channelId = " + channel.getId() +
			"  AND deleted = false AND status = 1 " + 
			"  AND id IN (" + PublishUtil.toSqlInString(itemIds) + ")";
		query.orderClause = " ORDER BY id DESC";
		List list = query.queryData(pub_ctxt.getDao());
		if (list == null || list.size() == 0) return false;
		
		super.fillUngenByList(list);
		
		return true;
	}
}
