package com.chinaedustar.publish.engine;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Channel;

/**
 * 生成指定频道最新 N 个项目的生成器。
 * @author liujunxing
 *
 */
public class ItemNewestGenerator extends AbstractItemGenerator {
	/** 最新项目数。 */
	private int itemNum;
	
	/**
	 * 构造。
	 * @param pub_ctxt
	 * @param channel
	 * @param itemNum
	 */
	public ItemNewestGenerator(PublishContext pub_ctxt, Channel channel, int itemNum) {
		super(pub_ctxt, channel);
		this.itemNum = itemNum;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractItemGenerator#fillUngenItems()
	 */
	@SuppressWarnings("rawtypes")
	@Override protected boolean fillUngenItems() {
		if (itemNum <= 0) return false;
		
		// 加载前 itemNum 个未生成的项目。
		String hql = "SELECT id, itemClass FROM Item " +
					" WHERE channelId = " + channel.getId() +
					"  AND deleted = false AND status = 1 " +
					" ORDER BY id DESC";
		List list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, itemNum);
		itemNum = -1;
		if (list == null || list.size() == 0) return false;

		// 组装为 UngenItem 数组。
		fillUngenByList(list);

		return true;
	}
}
