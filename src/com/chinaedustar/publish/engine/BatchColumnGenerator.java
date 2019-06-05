package com.chinaedustar.publish.engine;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Column;

/**
 * 频道栏目生成。
 * 
 * @author liujunxing
 *
 */
public class BatchColumnGenerator extends ChannelColumnGenerator {
	private List<Integer> column_ids;
	
	/**
	 * 构造函数。
	 *
	 */
	public BatchColumnGenerator(PublishContext pub_ctxt, Channel channel, List<Integer> column_ids) {
		super(pub_ctxt, channel, true);
		this.column_ids = column_ids;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.ChannelColumnGenerator#columnNeedGenerate(com.chinaedustar.publish.model.Column)
	 */
	protected boolean columnNeedGenerate(Column column) {
		if (column_ids == null) return false;
		return column_ids.contains(column.getId());
	}
}
