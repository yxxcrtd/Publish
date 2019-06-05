package com.chinaedustar.publish.engine;

import java.util.*;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.*;

/**
 * 全站网页生成器。
 * 
 * @author liujunxing
 * 
 * 全站网页生成定时启动，最好每 24 小时进行一次。全站生成器实质使用其它生成器进行生成
 *   内部管理一个私有生成队列，如果队列为空则表示已经全部生成完成了。
 * 
 * 使用私有队列的好处是不用担心受完整队列算法的干扰。
 */
public class FullSiteGenerator implements Generator, GenerateCallback {
	private final PublishContext pub_ctxt;
	
	/** = true 表示强制全部重新生成，= false 表示只生成未生成的。 */
	private final boolean force_regen;
	
	/** 内部私有的生成队列。 */
	private LinkedList<Generator> gen_q = new LinkedList<Generator>();
	
	/** 回调接口。 */
	private GenerateCallback callback;
	
	/** 是否进行过初始化。 */
	private boolean initialized = false;
	
	/**
	 * 构造。
	 * @param pub_ctxt
	 * @param force_regen - = true 表示强制全部重新生成，= false 表示只生成未生成的。
	 */
	public FullSiteGenerator(PublishContext pub_ctxt, boolean force_regen) {
		this.pub_ctxt = pub_ctxt;
		this.force_regen = force_regen;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.Generatable#genObjectPages(com.chinaedustar.publish.engine.GenerateCallback)
	 */
	public boolean genObjectPages(GenerateCallback callback) {
		this.callback = callback;
		if (this.initialized == false) 
			initialize();
		
		if (this.gen_q.size() == 0) 
			return endOfGenerate();		// 全部生成完毕。
		
		// 获取第一个生成器进行生成，这里是私有队列，不用担心线程问题。
		Generator gen = gen_q.getFirst();
		boolean result = false;
		try {
			result = gen.genObjectPages(this);
		} finally {
			// 如果返回生成结束，则去掉此生成器。
			// 当出现 exception 的时候由于 result = false 所以也会被去掉。
			if (result == false) {
				gen_q.removeFirst();
			}
		}
		
		// 还有没有要生成的？
		return endOfGenerate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.GenerateCallback#info(java.lang.String)
	 */
	public void info(String inf) {
		if (callback == null) return;
		callback.info("[全站生成] " + inf);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.GenerateCallback#appendGenerator(com.chinaedustar.publish.engine.Generator)
	 */
	public void appendGenerator(Generator gen_obj) {
		this.gen_q.add(gen_obj);
	}
	
	// 初始化此生成器。
	private void initialize() {
		this.initialized = true;
		this.info("全站生成已经开始。");
		
		// 1. 添加网站主页生成。
		addSiteGenerator();
		
		// 2. 添加网站所有可生成频道的生成器。
		addChannelGenerator();
		
		// 3. 添加网站全站专题生成器。
		addSiteSpecialGenerator();
	}

	// 返回是否还有需要生成的，如果没有，通知 parent 全站生成完成。
	private boolean endOfGenerate() {
		boolean hasMore = gen_q.size() > 0;
		if (hasMore == false) {
			callback.info("全站生成已经完成。");
		}
		return hasMore;
	}

	// 添加网站主页生成器。
	private void addSiteGenerator() {
		SiteIndexGenerator gen = new SiteIndexGenerator(pub_ctxt);
		gen_q.add(gen);
	}
	
	// 添加所有全频道生成器。
	private void addChannelGenerator() {
		ChannelCollection channel_coll = pub_ctxt.getSite().getChannels();
		Iterator<Channel> iter = channel_coll.iterator();
		while (iter.hasNext()) {
			Channel channel = iter.next();
			if (channel.getNeedGenerate()) {
				// 添加一个全频道生成器。
				FullChannelGenerator gen = new FullChannelGenerator(pub_ctxt, channel, force_regen);
				gen_q.add(gen);
			}
		}
	}
	
	private void addSiteSpecialGenerator() {
		// TODO: 
	}
}
