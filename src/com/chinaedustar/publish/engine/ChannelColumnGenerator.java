package com.chinaedustar.publish.engine;

import java.util.List;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.*;

/**
 * 频道全部栏目的生成器。
 * @author liujunxing
 *
 */
public class ChannelColumnGenerator extends AbstractGenerator {
	private final Channel channel;
	
	private final boolean force_regen;
	
	private boolean initialized = false;
	
	/** 此频道的全部栏目。 */
	private List<Column> all_columns;
	
	/** 最后一次生成的栏目索引。 */
	private int last_column_index = -1;
	
	/**
	 * 构造函数。
	 *
	 */
	public ChannelColumnGenerator(PublishContext pub_ctxt, Channel channel, boolean force_regen) {
		super(pub_ctxt);
		this.channel = channel;
		this.force_regen = force_regen;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractGenerator#generate()
	 */
	@Override protected boolean generate() {
		// 初始化及简单检测。
		if (this.initialized == false) 
			initialize();
		if (this.all_columns == null || this.all_columns.size() == 0) 
			return false;
		
		genCurrentColumn();
		
		return endOfGenerate();
	}
	
	// 初始化。
	private void initialize() {
		this.initialized = true;
		
		// 加载所有栏目进入，以加快后续的生成过程。
		// WARN: 此行为导致如果过程中栏目发生改变，则可能生成结果不完全正确。
		this.all_columns = channel.getColumnTree().getAllChildColumnList(0);
	}

	// 是否结束。
	private boolean endOfGenerate() {
		if (this.all_columns == null || this.all_columns.size() == 0)
			return false;
		if (this.last_column_index >= this.all_columns.size())
			return false;
		
		return true;
	}
	
	/** 给派生类一个机会判定是否需要生成。 */
	protected boolean columnNeedGenerate(Column column) {
		return true;
	}
	
	// 生成当前栏目，当前栏目指 last_column_index 索引指定的栏目。
	private void genCurrentColumn() {
		++this.last_column_index;
		if (this.last_column_index >= this.all_columns.size()) return;
		
		Column column = all_columns.get(last_column_index);
		if (column == null | column.getParentId() == 0) return;
		// 如果不强制全部生成，且栏目已经生成过了，则返回。
		if (force_regen == false && column.getIsGenerated()) return;
		// 让派生类有机会决定是否要生成这个栏目。
		if (columnNeedGenerate(column) == false) return;
		
		applyColumnParent(column);
		
		// 生成栏目页面。
		if (column.rebuildStaticPageUrl())
			column.updateGenerateStatus();
		PageGenerator page_gen = new PageGenerator(pub_ctxt);
		String page_content = page_gen.generateColumnPage(channel, column);
		
		// 写入其静态位置。
		super.writePagedObjectPage(column, page_content);
		
		callback.info("频道 '" + channel.getName() + "' 的栏目 '" + column.getName() + "' 已经生成完成。");
	}
	
	// 查找并设置指定栏目的父栏目，并递归向上一直设置到根栏目。
	private void applyColumnParent(Column column) {
		// parentId == 0 表示是根栏目，不应该出现的。
		if (column.getParentId() == 0 ||
				column.getParentId() == channel.getRootColumnId()) {
			column.setParent(channel);
			return;
		}
		
		Column parent_column = fastFindParentColumn(column);
		if (parent_column == null) 	{ // internal error - 不应该出现的。
			column.setParent(channel);
			return;
		}
		column.setParent(parent_column);
		
		// 递归向上设置父栏目的父对象。
		applyColumnParent(parent_column);
	}
	
	private Column fastFindParentColumn(Column column) {
		// 由于按照排序，parent 一定在自己前面，所以从自己开始向前找。
		for (int i = this.last_column_index; i >= 0; --i) {
			Column curr_column = all_columns.get(i);
			if (curr_column.getId() == column.getParentId())
				return curr_column;
		}
		return null;
	}
}
