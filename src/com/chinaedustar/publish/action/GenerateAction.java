package com.chinaedustar.publish.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.chinaedustar.publish.engine.GenerateEngine;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Column;


/**
 * 生成操作集合。
 * 
 * @author liujunxing
 *
 */
public class GenerateAction extends AbstractAction {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	@Override public void execute() throws Exception {
		String command = param_util.safeGetStringParam("command");
		// 从 admin_generate_site.jsp 页面提交的。
		if ("index".equalsIgnoreCase(command))
			index_generate();
		else if ("full_site".equalsIgnoreCase(command))
			full_site_generate();
		else if ("channel_index".equalsIgnoreCase(command))
			channel_generate();
		else if ("channel_js".equalsIgnoreCase(command))
			channel_js_generate();
		else if ("channel_index_all".equalsIgnoreCase(command))
			channel_generate_all();
		else if ("channel_js_all".equalsIgnoreCase(command))
			channel_js_all();
		else if ("special_list".equalsIgnoreCase(command))
			special_list();
		else if ("special_list_all".equalsIgnoreCase(command))
			special_list_all();
		
		// 从 admin_generate_channel.jsp 页面提交的。
		// 其中 channel_index, channel_js 上面已经处理。
		else if ("column".equalsIgnoreCase(command))
			channel_column_generate();
		else if ("column_all".equalsIgnoreCase(command))
			channel_all_column_generate();
		// TODO: 频道专题
		
		else if ("item_new".equalsIgnoreCase(command))
			channel_item_new();
		else if ("item_date_range".equalsIgnoreCase(command))
			item_date_range();
		else if ("item_id_range".equalsIgnoreCase(command))
			item_id_range();
		else if ("item_ids".equalsIgnoreCase(command))
			item_ids();
		else if ("item_in_column".equalsIgnoreCase(command))
			item_in_column();
		else if ("item_ungen".equalsIgnoreCase(command))
			item_ungen();
		else if ("item_all".equalsIgnoreCase(command))
			item_all();
		else
			unknownCommand(command);
		
		if (links.size() == 0) 
			links.add(getBackActionLink());
	}

	/** 生成网站主页。 */
	private void index_generate() {
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genIndexPage();
		
		setEnqueOK();
	}
	
	/** 生成网站所有页面。 */
	private void full_site_generate() {
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genAllPage();
		
		setEnqueOK();
	}

	/** 生成频道首页。 */
	private void channel_generate() {
		// 得到参数。
		List<Integer> channel_ids = param_util.safeGetIntValues("channelId");
		if (channel_ids == null || channel_ids.size() == 0) {
			messages.add("未给出频道参数，请确定选择了要生成的频道。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelIndex(channel_ids);
		setEnqueOK();
	}
	
	/** 生成所有频道主页。 */
	private void channel_generate_all() {
		List<Integer> all_channel_ids = getAllChannelIds();
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelJs(all_channel_ids);
		
		setEnqueOK();
	}
	
	/** 生成频道的所有 JS. */
	private void channel_js_generate() {
		// 得到参数。
		List<Integer> channel_ids = param_util.safeGetIntValues("channelId");
		if (channel_ids == null || channel_ids.size() == 0) {
			messages.add("未给出频道参数，请确定选择了要生成的频道。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelJs(channel_ids);
		
		setEnqueOK();
	}
	
	/** 生成所有频道的 JS */
	private void channel_js_all() {
		List<Integer> all_channel_ids = getAllChannelIds();
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelJs(all_channel_ids);
		
		setEnqueOK();
	}
	
	private void special_list() {
		throw new UnsupportedOperationException();
	}
	
	private void special_list_all() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 生成选中的频道的选中栏目集合。
	 *
	 */
	private void channel_column_generate() {
		int channelId = param_util.safeGetIntParam("channelId");
		Channel channel = site.getChannel(channelId);
		if (channel == null) {
			messages.add("未给出频道参数");
			return;
		}
		List<Integer> column_ids = param_util.safeGetIntValues("columnId");
		if (column_ids == null || column_ids.size() == 0) {
			messages.add("未选择任何一个要生成的栏目。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelColumns(channel, column_ids);
		
		setEnqueOK();
	}
	
	private void channel_all_column_generate() {
		int channelId = param_util.safeGetIntParam("channelId");
		Channel channel = site.getChannel(channelId);
		if (channel == null) {
			messages.add("未给出频道参数");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelAllColumn(channel);
		
		setEnqueOK();
	}
	
	/** 生成最新 n 个项目。 */
	private void channel_item_new() {
		Channel channel = getChannelNeed();
		if (channel == null) return;
		
		int itemNum = param_util.safeGetIntParam("itemNum");
		
		if (itemNum <= 0) {
			messages.add("没有给出要生成的最新项目数量，或数量不正确。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelNewestItem(channel, itemNum);
		
		setEnqueOK();
	}
	
	/** 生成指定日期范围内的项目。 */
	private void item_date_range() {
		Channel channel = getChannelNeed();
		if (channel == null) return;
		
		Date beginDate = param_util.safeGetDate("beginDate", null);
		Date endDate = param_util.safeGetDate("endDate", null);
		if (beginDate == null || endDate == null) {
			messages.add("未给出日期范围参数，或者日期范围参数非法。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelDateRangeItem(channel, beginDate, endDate);
		
		setEnqueOK();
	}
	
	/** 生成指定标识范围内的项目。 */
	private void item_id_range() {
		Channel channel = getChannelNeed();
		if (channel == null) return;
		
		int beginId = param_util.safeGetIntParam("beginId");
		int endId = param_util.safeGetIntParam("endId");
		if (beginId <= 0 || endId <= 0 || beginId >= endId) {
			messages.add("未给出标识范围参数，或者标识范围参数非法。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelIdRangeItem(channel, beginId, endId);
		
		setEnqueOK();
	}
	
	/** 生成指定标识的项目。 */
	private void item_ids() {
		Channel channel = getChannelNeed();
		if (channel == null) return;

		List<Integer> itemIds = param_util.safeGetIds("itemIds");
		if (itemIds == null || itemIds.size() == 0) {
			messages.add("未给出要生成的项目标识。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelIdsItem(channel, itemIds);
		
		setEnqueOK();
	}

	/** 生成指定栏目的项目。 */
	private void item_in_column() {
		Channel channel = getChannelNeed();
		if (channel == null) return;

		int columnId = param_util.safeGetIntParam("columnId");
		Column column = channel.getColumnTree().getColumn(columnId);
		if (column == null || column.getChannelId() != channel.getId()) {
			messages.add("未选择一个栏目，或栏目非法。");
			return;
		}
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelColumnItem(channel, column);
		
		setEnqueOK();
	}
	
	/** 生成频道内所有未生成的项目。 */
	private void item_ungen() {
		Channel channel = getChannelNeed();
		if (channel == null) return;
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelUngenItem(channel);
		
		setEnqueOK();
	}
	
	/** 生成频道内所有项目，包括已经生成的。 */
	private void item_all() {
		Channel channel = getChannelNeed();
		if (channel == null) return;
		
		// 生成。
		GenerateEngine engine = pub_ctxt.getGenerateEngine();
		engine.genChannelAllItem(channel);
		
		setEnqueOK();
	}
	
	/** 加入队列成功完成。 */
	private void setEnqueOK() {
		messages.add("生成请求已经添加到队列中，请稍等进行生成。");
		messages.add("您的生成请求正在进行中，请勿刷新此页面，刷新将导致重复加入生成请求。");
		messages.add("您可以继续进行别的管理操作，不影响生成的结果。");
		links.add(getBackActionLink());
		message_template_type = "generate_enque_succ";
	}

	private String message_template_type = "template_message_blank";
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#getMessageTemplateType()
	 */
	public String getMessageTemplateType() {
		return message_template_type;
	}
	
	private List<Integer> getAllChannelIds() {
		List<Integer> ids = new java.util.ArrayList<Integer>();
		Iterator<Channel> iter = pub_ctxt.getSite().getChannels().iterator();
		while (iter.hasNext()) {
			Channel channel = iter.next();
			ids.add(channel.getId());
		}
		return ids;
	}
}
