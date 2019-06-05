package com.chinaedustar.publish.engine;

import java.util.List;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 指定标识范围内的项目生成器。
 * 
 * @author liujunxing
 *
 */
public class ItemIdRangeGenerator extends AbstractItemGenerator {
	/** 开始时间。 */
	private final int beginId;
	
	/** 结束时间。 */
	private final int endId;
	
	private boolean filled = false;
	
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 * @param channel
	 * @param beginId
	 * @param endId
	 */
	public ItemIdRangeGenerator(PublishContext pub_ctxt, Channel channel, int beginId, int endId) {
		super(pub_ctxt, channel);
		this.beginId = beginId;
		this.endId = endId;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractItemGenerator#fillUngenItems()
	 */
	@SuppressWarnings("rawtypes")
	@Override protected boolean fillUngenItems() {
		if (filled) return false;
		filled = true;
		
		// 查找日期范围 beginDate, endDate 之内的。
		QueryHelper query = new QueryHelper();
		query.selectClause = " SELECT id, itemClass ";
		query.fromClause = " FROM Item ";
		query.whereClause = " WHERE channelId = " + channel.getId() +
			"  AND deleted = false AND status = 1 " + 
			"  AND id >= :beginId AND id < :endId ";
		query.orderClause = " ORDER BY id DESC";
		query.setInteger("beginId", beginId);
		query.setInteger("endId", endId);
		List list = query.queryData(pub_ctxt.getDao());
		if (list == null || list.size() == 0) return false;
		
		super.fillUngenByList(list);
		
		return true;
	}
}
