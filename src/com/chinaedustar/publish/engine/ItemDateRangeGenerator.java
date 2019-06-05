package com.chinaedustar.publish.engine;

import java.util.Date;
import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 
 * @author liujunxing
 *
 */
public class ItemDateRangeGenerator extends AbstractItemGenerator {
	/** 开始时间。 */
	private final Date beginDate;
	
	/** 结束时间。 */
	private final Date endDate;
	
	private boolean filled = false;
	
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 * @param channel
	 * @param beginDate
	 * @param endDate
	 */
	public ItemDateRangeGenerator(PublishContext pub_ctxt, Channel channel, Date beginDate, Date endDate) {
		super(pub_ctxt, channel);
		this.beginDate = beginDate;
		this.endDate = endDate;
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
			"  AND lastModified >= :beginDate AND lastModified < :endDate ";
		query.orderClause = " ORDER BY id DESC";
		query.setTimestamp("beginDate", beginDate);
		query.setTimestamp("endDate", endDate);
		List list = query.queryData(pub_ctxt.getDao());
		if (list == null || list.size() == 0) return false;
		
		super.fillUngenByList(list);
		
		return true;
	}
}
