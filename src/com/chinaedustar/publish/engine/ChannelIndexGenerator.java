package com.chinaedustar.publish.engine;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Channel;

/**
 * 频道主页生成。
 * 
 * @author liujunxing
 *
 */
public class ChannelIndexGenerator extends AbstractGenerator {
	/** 要生成的频道标识集合。 */
	private final List<Integer> channel_ids;
	
	/**
	 * 构造函数。
	 * @param channel_ids
	 */
	public ChannelIndexGenerator(PublishContext pub_ctxt, List<Integer> channel_ids) {
		super(pub_ctxt);
		this.channel_ids = channel_ids;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractPublishGeneratable#generate()
	 */
	@Override protected boolean generate() {
		if (channel_ids == null || channel_ids.size() == 0) return false;
		
		// 获得对象。
		int channelId = channel_ids.remove(0);
		Channel channel = pub_ctxt.getSite().getChannel(channelId);
		
		if (channel != null && channel.getNeedGenerate()) {
			// 如果地址变更了，立刻更新到数据库中，否则生成的时候会用到。
			if (channel.rebuildStaticPageUrl())
				channel.updateGenerateStatus();
			
			// 生成页面。
			PageGenerator page_gen = new PageGenerator(pub_ctxt);
			String page_content = page_gen.generateChannelIndexPage(channel);
			
			// 写入文件。
			super.writePagedObjectPage(channel, page_content);
			
			callback.info("频道 '" + channel.getName() + "' 已经生成了静态化主页，url = " + channel.getStaticPageUrl());
		} else {
			callback.info("频道 id=" + channelId + "' 不存在或未设置需要生成。");
		}
		
		return channel_ids.size() > 0;	// > 0 表示还有项目需要生成。
	}
}
