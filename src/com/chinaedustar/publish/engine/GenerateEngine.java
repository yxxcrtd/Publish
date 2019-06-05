package com.chinaedustar.publish.engine;

import java.util.Date;
import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.ThreadCurrentMap;
import com.chinaedustar.publish.model.WebPage;

/**
 * 发布系统生成引擎。
 * 
 * @author wumy
 *
 */
public class GenerateEngine implements GenerateCallback {
	/** 发布系统环境对象。 */
	private PublishContext pub_ctxt;
	
	/** 引擎状态：未启动 = 0。 */
	public static final int ENGINE_STATUS_UNSTART = 0;
	/** 引擎状态：正在启动 = 1。 */
	public static final int ENGINE_STATUS_STARTING = 1;
	/** 引擎状态：启动完成正常可用了 = 2。 */
	public static final int ENGINE_STATUS_STARTED = 2;
	/** 引擎状态：正在停止 = 3, 停止之后状态恢复为 UNSTART = 0 状态。 */
	public static final int ENGINE_STATUS_STOPING = 3;
	
	/** 引擎状态 */
	private int status = ENGINE_STATUS_UNSTART;

	/**
	 * 使用指定的发布系统环境构造一个 GenerateEngine 对象。
	 * @param pub_ctxt
	 */
	public GenerateEngine(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	// === 引擎启动、停止、状态 =======================================================
	
	/**
	 * 启动静态化引擎。
	 *
	 */
	public void startEngine() {
		EngineThread thread = new EngineThread();
		thread.start();
	}
	
	/**
	 * 停止静态化引擎。
	 *
	 */
	public void stopEngine() {
		
	}
	
	/**
	 * 获得当前引擎状态。
	 * @return
	 */
	public int getEngineStatus() {
		return this.status;
	}
	
	/**
	 * 引擎的页面生成线程。
	 * @author liujunxing
	 *
	 */
	private class EngineThread extends Thread {
		/**
		 * 缺省构造。
		 */
		public EngineThread() {
			super("静态化线程");
		}
		/*
		 * (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override public void run() {
			internalThreadRunning();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.GenerateCallback#info(java.lang.String)
	 */
	public void info(String inf) {
		synchronized (this.last_msg) {
			java.util.Date now = new java.util.Date();
			this.last_msg.add(now.toString() + ": " + inf);
			if (this.last_msg.size() > this.getRemainMessageNum())
				this.last_msg.remove();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.GenerateCallback#appendGenerator(com.chinaedustar.publish.engine.Generator)
	 */
	public void appendGenerator(Generator gen_obj) {
		// TODO: 线程处理。
		this.gen_obj_q.add(gen_obj);
	}
	
	// === 生成业务包装 ==================================================================
	
	/**
	 * 生成网站主页。
	 *
	 *
	 * 这些操作的方式是将要生成的项目添加到 优先生成队列的最前面，然后生成线程开始生成这些
	 *   页面。
	 * 如果前面已经添加了生成项目，则尽量合并，如果不能合并多生成一些也没有关系。
	 * 
	 */
	public void genIndexPage() {
		SiteIndexGenerator gen_obj = new SiteIndexGenerator(pub_ctxt);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成网站所有页面。
	 *
	 */
	public void genAllPage() {
		FullSiteGenerator gen_obj = new FullSiteGenerator(pub_ctxt, true);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道[]的主页。
	 *
	 */
	public void genChannelIndex(List<Integer> channel_ids) {
		ChannelIndexGenerator gen_obj = new ChannelIndexGenerator(pub_ctxt, channel_ids);
		appendGenerator(gen_obj);
	}

	/**
	 * 生成指定频道[]的js脚本文件。
	 *
	 */
	public void genChannelJs(List<Integer> channel_ids) {
		ChannelJsGenerator gen_obj = new ChannelJsGenerator(pub_ctxt, channel_ids);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道最新n个项目。
	 *
	 */
	public void genChannelNewestItem(Channel channel, int itemNum) {
		ItemNewestGenerator gen_obj = new ItemNewestGenerator(pub_ctxt, channel, itemNum);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道指定日期范围内的项目。
	 *
	 */
	public void genChannelDateRangeItem(Channel channel, Date beginDate, Date endDate) {
		ItemDateRangeGenerator gen_obj = new ItemDateRangeGenerator(pub_ctxt, channel, beginDate, endDate);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道指定标识范围的项目。
	 *
	 */
	public void genChannelIdRangeItem(Channel channel, int beginId, int endId) {
		ItemIdRangeGenerator gen_obj = new ItemIdRangeGenerator(pub_ctxt, channel, beginId, endId);
		appendGenerator(gen_obj);
	}

	/**
	 * 生成指定频道指定标识集合的项目。
	 *
	 */
	public void genChannelIdsItem(Channel channel, List<Integer> itemIds) {
		ItemIdsGenerator gen_obj = new ItemIdsGenerator(pub_ctxt, channel, itemIds);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道指定栏目下的所有项目。
	 *
	 */
	public void genChannelColumnItem(Channel channel, Column column) {
		ItemInColumnGenerator gen_obj = new ItemInColumnGenerator(pub_ctxt, channel, column);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道下所有未生成的项目。
	 *
	 */
	public void genChannelUngenItem(Channel channel) {
		ChannelAllItemGenerator gen_obj = new ChannelAllItemGenerator(pub_ctxt, channel, false);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道下所有项目。
	 *
	 */
	public void genChannelAllItem(Channel channel) {
		ChannelAllItemGenerator gen_obj = new ChannelAllItemGenerator(pub_ctxt, channel, true);
		appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道指定栏目[]的列表页。
	 *
	 */
	public void genChannelColumns(Channel channel, List<Integer> column_ids) {
		BatchColumnGenerator gen_obj = new BatchColumnGenerator(pub_ctxt, channel, column_ids);
		this.gen_obj_q.add(gen_obj);
	}
	
	/**
	 * 生成指定频道所有栏目的列表页。
	 *
	 */
	public void genChannelAllColumn(Channel channel) {
		ChannelColumnGenerator gen_obj = new ChannelColumnGenerator(pub_ctxt, channel, true);
		this.appendGenerator(gen_obj);
	}
	
	/**
	 * 生成指定频道指定专题[]的列表页。
	 *
	 */
	public void genChannelSpecial() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 生成指定频道所有专题的列表页。
	 *
	 */
	public void genChannelAllSpecial() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 生成指定的自定义页面。
	 * @param channel
	 * @param webpage
	 */
	public void genWebPage(Channel channel, WebPage webpage) {
		WebPageGenerator gen_obj = new WebPageGenerator(pub_ctxt, webpage);
		this.appendGenerator(gen_obj);
	}
	
	// === thread entry =============================================================
	
	private Object curr_generator;
	
	/** 得到当前生成对象。 */
	public Object getCurrentGenerator() { return curr_generator; }
	
	/** 最后的 n 个消息。 */
	private java.util.Queue<String> last_msg = new java.util.LinkedList<String>();
	
	/** 保留多少个消息。 */
	private int getRemainMessageNum() {
		return 10;
	}
	
	/** 获得最后消息队列。 */
	public String[] getLastMessage() {
		synchronized (last_msg) {
		  return last_msg.toArray(new String[]{});
		}
	}
	
	private void internalThreadRunning() {
		while (true) {
			// 如果请求停止，则返回。
			if (this.status == ENGINE_STATUS_STOPING) break;
			
			Generator gen_obj = null;
			try {
				gen_obj = gen_obj_q.take();
			} catch (InterruptedException e) {
			} 
			if (gen_obj == null) break;		// 我们假定获得了 null 项目就表示停止。
			if (this.status == ENGINE_STATUS_STOPING) break;

			// 对这个生成器进行生成。
			try {
				this.curr_generator = gen_obj;
				while (true) {
					boolean result = genObjectPages(gen_obj);
					if (result == false) break;
					
					// 如果请求停止，则返回。
					if (this.status == ENGINE_STATUS_STOPING) break;
				}
			} catch(Exception ex) {
			} finally {
				this.curr_generator = null;
			}
			
			// 继续下一个生成项目。
		}
	}
	
	private boolean genObjectPages(Generator gen_obj) {
		try {
			ThreadCurrentMap.current();			// 强迫产生一个 Map, 用于页面缓存。
			return gen_obj.genObjectPages(this);
		} finally {
			ThreadCurrentMap.clear();			// 每次生成之后清除 Map.
		}
	}
	
	/** 所有要生成项目的队列。 */
	private java.util.concurrent.LinkedBlockingQueue<Generator> gen_obj_q = 
		new java.util.concurrent.LinkedBlockingQueue<Generator>();
}
