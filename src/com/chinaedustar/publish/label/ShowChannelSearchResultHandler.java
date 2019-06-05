package com.chinaedustar.publish.label;

import java.util.List;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.itfc.LabelHandler2;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 显示频道搜索的结果
 * 
 * @param channelId 频道的标识。0：当前频道；>0：指定频道。默认为0。
 * @param pageUrl 分页页面的地址。默认为search.jsp
 * 
 * @author dengxiaolong
 * 注册在 GeneralSearchLabel.java 中。
 */
@SuppressWarnings("rawtypes")
final class ShowChannelSearchResultHandler extends AbstractLabelHandler implements LabelHandler2 {
	public static final String DEFAULT_TEMPLATE = ".builtin.showchannelsearchresult";
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
	 */
	@Override protected int handleLabel() {
		AttributeCollection coll = label.getAttributes();
		java.util.Map options = coll.attrToOptions();
		
		// 处理频道标识
		Channel channel = getCurrentChannel();
		if (channel == null) {
			out("#{?? ShowChannelSearchResult 没有频道信息}");
			return PROCESS_DEFAULT;
		}
		
		// 搜索信息的处理
		ChannelSearch search = getCurrentChannelSearch(env);
		if (search == null) {
			out("#{?? ShowChannelSearchResult 没有搜索对象信息}");
			return PROCESS_SIBLING;
		}
		
		// 分页信息的处理
		PaginationInfo page_info = (PaginationInfo)env.findVariable(PAGINATION_INFO_NAME);
		if (page_info == null) page_info = new PaginationInfo();
		page_info.setUrlPattern(search.getUrl(pub_ctxt.getSite().getInstallDir() + channel.getChannelDir() + "/search.jsp"));
		page_info.setItemName(channel.getItemName());
		page_info.setItemUnit(channel.getItemUnit());
		int pageSize = 10; // coll.safeGetIntAttribute("pageSize", 10);
		page_info.setPageSize(pageSize);
		
		// 查询数据。
		Object item_list = getData(pub_ctxt, channel, search, page_info);
		
		// 有子节点的时候，让子节点处理；否则使用缺省模板处理。
		if (label.hasChild()) {
			LocalContext local_ctxt = env.acquireLocalContext(label, PROCESS_DEFAULT);
			super.addRepeaterSupport(local_ctxt, "item", item_list);
			super.addPageNavSupport(local_ctxt, page_info);
			return PROCESS_DEFAULT;
		} else {
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			return builtin_label.process(env, new Object[]{item_list, options, page_info});
		}
	}

	/**
	 * 得到文章的搜索列表
	 * @param pub_ctxt
	 * @param keyword
	 * @param page_info
	 * @return
	 */
	private Object getData(PublishContext pub_ctxt, Channel channel, ChannelSearch search, PaginationInfo page_info) {
		// 创建查询条件。
		ItemQueryOptionEx query_option = search.createQueryOption();
		// 然后限定查询频道和项目类型。
		query_option.channelId = channel.getId();
		query_option.itemClass = channel.getChannelModule().getItemClass();
		
		// 执行查询。
		ItemQueryExecutor executor = new ItemQueryExecutor(pub_ctxt);
		
		System.out.println("888888888888888888888888888888888888888888888888888888888");
		List result = executor.getAdvItemList(query_option, page_info);
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.LabelHandler2#getBuiltinName()
	 */
	public String getBuiltinName() {
		return DEFAULT_TEMPLATE;
	}
}
