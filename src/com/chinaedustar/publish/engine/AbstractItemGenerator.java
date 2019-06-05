package com.chinaedustar.publish.engine;

import java.util.ArrayList;
import java.util.List;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.*;

/**
 * 项目生成的基类。
 * @author liujunxing
 *
 */
public abstract class AbstractItemGenerator extends AbstractGenerator {
	/** 所在频道。 */
	protected final Channel channel;
	
	/** 未生成项目的信息。 */
	protected static final class UngenItem {
		public int id;
		public String itemClass;
	}

	/** 待生成项目列表。 */
	protected final List<UngenItem> ungen_items = new ArrayList<UngenItem>();
	

	/**
	 * 构造。
	 */
	protected AbstractItemGenerator(PublishContext pub_ctxt, Channel channel) {
		super(pub_ctxt);
		this.channel = channel;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractGenerator#generate()
	 */
	@Override protected boolean generate() {
		// 每次获取前 N 个要生成的项目，如果没有项目了则返回。
		if (this.ungen_items.size() == 0)
			if (fillUngenItems() == false) 
				return false;
		
		// 为每个项目进行生成。
		UngenItem ui = this.ungen_items.get(0);
		this.ungen_items.remove(0);
		generateItem(ui);
		
		// 假定还有更多项目待生成，只有 fill 失败才返回 false。
		return true;
	}
	
	/**
	 *  派生类应该实现的。将待生成的项目填充到 ungen_items 集合中。
	 *  @return true 表示还有项目要生成；false 表示没有要生成的项目了。
	 */
	protected abstract boolean fillUngenItems();

	// 生成指定项目。
	private void generateItem(UngenItem ui) {
		Item item = channel.loadItem(ui.id, ui.itemClass);
		if (item == null) return;
		if (item.getDeleted()) return;						// 已经删除了则不生成。
		if (item.getStatus() != Item.STATUS_APPR) return;	// 未审核通过则不生成。
		// ignore isGenerated.
		
		
		// 如果地址变更了，立刻更新到数据库中，否则生成的时候会用到。
		if (item.rebuildStaticPageUrl())
			item.updateGenerateStatus();

		// 生成这个页面。
		PageGenerator page_gen = new PageGenerator(pub_ctxt);
		String page_content = page_gen.generateItemPage(channel, item);
		
		// 写入文件。
		super.writePagedObjectPage(item, page_content);
		
		callback.info("频道 '" + channel.getName() + "' 的项目 '" + item.getTitle() + 
			"'(id = " + item.getId() + ") 已经生成了静态网页，url = " + item.getStaticPageUrl());
	}

	/**
	 * 用 [id, itemClass] 集合填充 ungen_item 数组。
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	protected void fillUngenByList(List list) {
		// 组装为 UngenItem 数组。
		for (int i = 0; i < list.size(); ++i) {
			Object[] data = (Object[])list.get(i);
			UngenItem ui = new UngenItem();
			ui.id = (Integer)data[0];
			ui.itemClass = (String)data[1];
			this.ungen_items.add(ui);
		}
	}
}
