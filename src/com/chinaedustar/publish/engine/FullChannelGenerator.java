package com.chinaedustar.publish.engine;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.*;

/**
 * 全频道生成器。
 * 
 * @author liujunxing
 *
 */
public class FullChannelGenerator extends AbstractGenerator {
	/** 要生成的频道。 */
	private final Channel channel;
	
	/** = true 表示强制全部重新生成，= false 表示只生成未生成的。 */
	private final boolean force_regen;
	
	/**
	 * 构造。
	 * @param pub_ctxt
	 */
	public FullChannelGenerator(PublishContext pub_ctxt, Channel channel, boolean force_regen) {
		super(pub_ctxt);
		this.channel = channel;
		this.force_regen = force_regen;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractGenerator#generate()
	 */
	@Override protected boolean generate() {
		// 如果频道未设置需要生成，则直接返回。
		if (channel.getUseCreateHTML() == Channel.CHANNEL_CREATE_HTML_NONE) 
			return false;
			
		// 全频道生成器需要生成的有：
		//  频道主页。（总是生成）
		addChannelIndexGenerator();
		
		if (channel.getNeedGenerateColumn()) {
			//  频道所有的栏目页。
			addChannelColumnGenerator();
		}

		if (channel.getNeedGenerateSpecial()) {
			//  频道未生成的专题页。
			addChannelSpecialGenerator();
		}
		
		if (channel.getNeedGenerateItem()) {
			//  频道所有未生成的项目页。
			addChannelItemGenerator();
		}
		
		return false;
	}
	
	// 添加频道主页生成器。
	private void addChannelIndexGenerator() {
		java.util.List<Integer> channel_ids = new java.util.ArrayList<Integer>();
		channel_ids.add(channel.getId());
		ChannelIndexGenerator ci_gen = new ChannelIndexGenerator(pub_ctxt, channel_ids);
		callback.appendGenerator(ci_gen);
	}
	
	// 添加频道所有栏目生成器。
	private void addChannelColumnGenerator() {
		ChannelColumnGenerator gen_obj = new ChannelColumnGenerator(pub_ctxt, channel, force_regen);
		callback.appendGenerator(gen_obj);
	}
	
	// TODO: 添加频道专题生成器。
	private void addChannelSpecialGenerator() {
		// TODO:
	}
	
	// 添加频道所有项目生成器。
	private void addChannelItemGenerator() {
		ChannelAllItemGenerator gen_obj = new ChannelAllItemGenerator(pub_ctxt, channel, force_regen);
		callback.appendGenerator(gen_obj);
	}
}
