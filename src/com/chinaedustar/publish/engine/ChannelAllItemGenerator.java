package com.chinaedustar.publish.engine;

import java.util.*;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.*;

/**
 * 频道所有项目的生成器，可以指定是否强制全部生成，或者只生成未生成的。
 * 
 * @author liujunxing
 *
 */
public class ChannelAllItemGenerator extends AbstractItemGenerator {
	private final boolean force_regen;
	
	/** 最后一次查询项目时候最大的项目标识。通过此条件限定了下次查询的 id 范围。 */
	private int last_max_id = Integer.MAX_VALUE;
	
	/**
	 * 构造函数。
	 * @param pub_ctxt - 环境对象。
	 * @param channel - 所在频道。
	 * @param force_regen - true 表示生成所有项目，包括已生成的；false 表示只生成未生成的。
	 */
	public ChannelAllItemGenerator(PublishContext pub_ctxt, Channel channel, boolean force_regen) {
		super(pub_ctxt, channel);
		this.force_regen = force_regen;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractItemGenerator#fillUngenItems()
	 */
	@SuppressWarnings("rawtypes")
	@Override protected boolean fillUngenItems() {
		// 加载前 50 个未生成的项目，每次加载 50 个是为了性能考虑。
		String hql = "SELECT id, itemClass FROM Item " +
					" WHERE channelId = " + channel.getId() +
					"  AND id < " + last_max_id + 
					"  AND deleted = false AND status = 1 ";
		if (this.force_regen == false)	// 如果强制全部生成则不限定 isGenerated 标志。
			hql += " AND (isGenerated = false) ";
		hql += " ORDER BY id DESC ";
		
		List list = PublishUtil.queryTopResult(pub_ctxt.getDao(), hql, 50);
		if (list == null || list.size() == 0) return false;
		
		// 组装为 UngenItem 数组。
		for (int i = 0; i < list.size(); ++i) {
			Object[] data = (Object[])list.get(i);
			UngenItem ui = new UngenItem();
			ui.id = (Integer)data[0];
			ui.itemClass = (String)data[1];
			this.ungen_items.add(ui);
		}
		
		this.last_max_id = this.ungen_items.get(ungen_items.size()-1).id;
		
		return true;
	}
}
