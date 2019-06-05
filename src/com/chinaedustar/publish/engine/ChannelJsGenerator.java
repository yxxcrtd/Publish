package com.chinaedustar.publish.engine;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.ColumnTree;

/**
 * 频道的 JS 文件生成器。
 * @author liujunxing
 *
 */
public class ChannelJsGenerator extends AbstractGenerator {
	/** 要生成的频道标识集合。 */
	private final List<Integer> channel_ids; 

	/**
	 * 构造函数。
	 * @param channel_ids
	 */
	public ChannelJsGenerator(PublishContext pub_ctxt, List<Integer> channel_ids) {
		super(pub_ctxt);
		this.channel_ids = channel_ids;
	}
	
	@Override protected boolean generate() {
		if (channel_ids == null || channel_ids.size() == 0) return false;
		
		// 获得对象。
		int channelId = channel_ids.remove(0);
		Channel channel = pub_ctxt.getSite().getChannel(channelId);
		
		// 生成频道的几种不同 js.
		ColumnTree column_tree = channel.getColumnTree();
		column_tree.generateJs();
		callback.info("频道 '" + channel.getName() + "' 的 js 文件已经生成完成。");

		return channel_ids.size() > 0;	// > 0 表示还有项目需要生成。
	}
}
