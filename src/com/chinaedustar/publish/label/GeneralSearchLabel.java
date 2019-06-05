package com.chinaedustar.publish.label;

import java.util.Iterator;
import java.util.List;

import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 搜索相关的标签。
 *
 */
public final class GeneralSearchLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralSearchLabel() {
	}

	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("GeneralSearchLabel 注册了其包含的一组标签解释器。");
		
		// 全站搜索。
		map.put("ShowSiteSearch", new ShowSiteSearchHandler());
		//--//map.put("ShowSiteTitle", new ShowSiteSearchHandler());
		//--//map.put("ShowSiteKeyword", new ShowSiteSearchHandler());
		map.put("ShowSiteSearchResult_Old", new ShowSiteSearchResultHandler());
		map.put("ShowSiteSearchResult", new ShowSiteSearchResultHandler2());
		
		// 频道搜索。
		map.put("ShowChannelSearch", new ShowChannelSearchHandler());
		map.put("ChannelSearchTitle", new ChannelSearchTitleHandler());
		map.put("ShowChannelKeyword", new ShowChannelKeywordHandler());		
		map.put("ShowChannelContent", new ShowChannelContentHandler());		
		map.put("ShowChannelSearchResult", new ShowChannelSearchResultHandler());
	}
	
	/**
	 * 全站搜索标签解释器
	 * #{ShowSiteSearch} 
	 * 自定义标签：
	 * <pre>
	 * <form action="/PubWeb/search.jsp" method="post">
	 *   #{Repeater}
	 *     <input type="radio" name="moduleId" value="#{module.id}" #{if(index == 1)}selected#{/if}>#{module.name}
	 *   #{/Repeater}
	 *   <input type="text" name="keyword"/>
	 *   <input type="submit" value="搜索"/>
	 *</form>
	 * </pre>
	 * 返回 DataTable(id, name) 图片地址
	 * @author dengxiaolong
	 *
	 */
	static final class ShowSiteSearchHandler extends AbstractLabelHandler2 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showsitesearch";
		@Override public int handleLabel() {
			Object data = getData();
			if (label.hasChild()) {
				// 如果有子节点，则设置变量等，然后执行子节点。
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("module_list", data);
				super.addRepeaterSupport(local_ctxt, "module", data);
				env.visit(label.getFirstChild(), true, true);
				return PROCESS_SIBLING;
			} else {
				// 使用所选择的模板产生 SiteSearch 结果。
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtin_label = getBuiltinLabel(template_name);
				if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
				return builtin_label.process(env, new Object[]{ data });
			}
		}
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		// 获取模块列表。
		private final Object getData() {
			Iterator<Module> itor = pub_ctxt.getSite().getModules().iterator();
			DataTable dt = new DataTable(new DataSchema(new String[]{
				"id", "name"
			}));
			while (itor.hasNext()) {
				Module module = itor.next();
				if (module.getPublishModule() instanceof ChannelModule) {
					// TODO: BAD 方式，我们有没有更好的方法???
					String moduleTitle = module.getTitle();
					if (moduleTitle.endsWith("模块"))
						moduleTitle = moduleTitle.substring(0, moduleTitle.indexOf("模块"));
					else {
						moduleTitle = module.getName();
						if (moduleTitle.endsWith("模块"))
							moduleTitle = moduleTitle.substring(0, moduleTitle.indexOf("模块"));
					}
					dt.add(dt.newRow(new Object[]{
							module.getId(),
							moduleTitle
					}));
				}
			}
			return dt;
		}
	}
	
	/**
	 * 显示站点搜索结果的处理器。
	 * @author dengxiaolong
	 */
	static final class ShowSiteSearchResultHandler extends ModuleItemListHandler2 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showsitesearchresult";
		private SiteSearch search;
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.ModuleItemListHandler2#handleLabel()
		 */
		@Override public int handleLabel() {
			this.search = getCurrentSearch(env);
			if (search == null) return PROCESS_DEFAULT;
			
			return super.handleLabel();
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler2#getBuiltinName()
		 */
		@Override public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.ModuleItemListHandler2#initPagination(com.chinaedustar.publish.model.PaginationInfo)
		 */
		@Override protected void initPagination(PaginationInfo page_info) {
			String installDir = pub_ctxt.getSite().getInstallDir();
			page_info.setUrlPattern(installDir + "search.jsp?moduleId=" + super.getModule().getId() + "&page={page}");		
			page_info.appendParam2Url(true, "keyword", search.getKeyword(), "UTF-8");
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.ModuleItemListHandler2#getData()
		 */
		@Override protected Object getData() {
			ItemBusinessObject ibo = new ItemBusinessObject(pub_ctxt);
			return ibo.searchItemList(super.module, search.getKeyword(), super.page_info);
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.ModuleItemListHandler2#getModuleId()
		 */
		@Override protected int getModuleId() {
			return search.getModuleId();
		}
	}
	
	/**
	 * ShowSiteSearchResult 的升级版本，不限定查询 module.
	 * @author liujunxing
	 *
	 */
	static final class ShowSiteSearchResultHandler2 extends AbstractShowItemListHandler3 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showsitesearchresult";
		private SiteSearch search;
		private ChannelModule module;
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractShowItemListHandler3#handleLabel()
		 */
		public int handleLabel() {
			this.search = getCurrentSearch(env);
			if (search == null) {
				out("#{?? search 页面没有找到当前 search 对象}");
				return PROCESS_DEFAULT;
			}
			
			// 获得查询模块。
			Module module = pub_ctxt.getSite().getModules().getModule(search.getModuleId());
			if (module != null && module.getPublishModule() instanceof ChannelModule)
				this.module = (ChannelModule)module.getPublishModule();
			return super.handleLabel();
		}

		@Override
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		
		@Override
		protected void buildQueryOption() {
			// 我们完全不使用基类的查询条件缺省获取方式，而是自己设置查询条件。
			AttributeCollection coll = label.getAttributes();
			this.options = coll.attrToOptions();
			this.query_option = new ItemQueryOption();

			query_option.status = Item.STATUS_APPR;		// 获取审核通过的
			query_option.isDeleted = false;				// 未删除的正常项目。
			query_option.orderType = ItemQueryOption.ORDER_TYPE_DEFAULT;
			query_option.field = "title";
			query_option.keyword = search.getKeyword();

			if (this.module != null)
				super.query_option.itemClass = this.module.getItemClass();
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractShowItemListHandler3#initPageInfo()
		 */
		@Override
		protected void initPageInfo() {
			super.initPageInfo();
			
			// 设置项目类型。
			if (module == null) {
				page_info.setItemName("项目");
				page_info.setItemUnit("个");
			} else {
				page_info.setItemName(module.getDefaultItemName());
				page_info.setItemUnit(module.getDefaultItemUnit());
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractShowItemListHandler3#getData()
		 */
		@SuppressWarnings("rawtypes")
		@Override 
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			System.out.println("77777777777777777777777777777777777777777777777777777");
			List result = ibo.getAdvItemList(query_option, page_info);
			debugOutputQuery(ibo.getAdvItemQueryHelper());
			
			return result;
		}
		
	}
	
	/**
	 * 显示频道内搜索栏。
	 * #{ShowChannelSearch(channelId)}
	 * @param moduleId 0:不是在全站搜索页面，直接使用channelId获取相应的搜索框。>0， 优先从moduleId获取对应频道的搜索框。默认为0。
	 * @param channelId 0:当前频道 >0:指定频道。默认为0。
	 * @author dengxiaolong
	 *
	 */
	static final class ShowChannelSearchHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			AttributeCollection coll = label.getAttributes();
			Integer moduleId = (Integer)env.findVariable("moduleId");
			Channel channel = null;
			if (moduleId != null && moduleId > 0) {
				Iterator<Channel> iter = pub_ctxt.getSite().getChannels().iterator();
				while (iter.hasNext()) {
					Channel tChannel = iter.next();
					if (tChannel.getModuleId() == moduleId && tChannel.getChannelType() == 0 && tChannel.getStatus() == 0) {
						channel = tChannel;
						break;
					}
				}
			}
			if (channel == null) {
				int channelId = coll.safeGetIntAttribute("channelId", 0);
				if (channelId <= 0) {
					channel = getCurrentChannel();
				} else {
					channel = pub_ctxt.getSite().getChannels().getChannel(channelId);
				}
			}
		
			//必须有相应的频道
			if (channel == null) {
				out("#{??ShowChannelSearch 未找到频道信息。}");
				return PROCESS_DEFAULT;
			}
			
			BuiltinLabel builtin_label = getBuiltinLabel(".builtin.showchannelsearch");
			return builtin_label.process(env, new Object[]{channel});
		}		
	}
	
	/**
	 * 显示在频道搜索页面 搜索结果的 上方的标题。
	 * #{ChannelSearchTitle} 
	 * @author dengxiaolong
	 *
	 */
	static final class ChannelSearchTitleHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			ChannelSearch search = getCurrentChannelSearch(env);
			if (search == null) {
				out("#{?? ChannelSearchTitle 没有找到搜索对象}");
				return PROCESS_SIBLING;
			}
			out(search.getSearchResultTitle());
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示频道搜索的关键字。
	 * #{ShowChannelKeyword}
	 * @author dengxiaolong
	 *
	 */
	static final class ShowChannelKeywordHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			ChannelSearch search = getCurrentChannelSearch(env);
			if (search == null) {
				out("#{?? ShowChannelKeyword 没有找到搜索对象}");
				return PROCESS_SIBLING;
			}
			out(search.getKeyword());
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示频道搜索的内容。
	 * #{ShowChannelContentHandler}
	 * @author dengxiaolong
	 *
	 */
	static final class ShowChannelContentHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			ChannelSearch search = getCurrentChannelSearch(env);
			if (search == null) {
				out("#{?? ShowChannelContent 没有找到搜索对象}");
				return PROCESS_SIBLING;
			}
			out(search.getFieldName());
			return PROCESS_DEFAULT;
		}
	}
}
