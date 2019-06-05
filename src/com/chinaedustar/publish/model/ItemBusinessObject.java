package com.chinaedustar.publish.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.module.ArticleModule;
import com.chinaedustar.publish.module.PhotoModule;
import com.chinaedustar.publish.module.SoftModule;
import com.chinaedustar.publish.util.QueryHelper;
 
/**
 * 项目访问业务对象，提供对 Item(Article, Soft, Photo) 访问的一个封装。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class ItemBusinessObject {
	
	/** 发布系统环境对象。 */
	private final PublishContext pub_ctxt;
	
	/**
	 * 使用指定的 pub_ctxt 构造一个 ItemBusinessObject 的新实例。
	 * @param pub_ctxt
	 */
	public ItemBusinessObject(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	// === searchItemList ===========================================
	
	/**
	 * 搜索指定模块下所属的文章(下载、图片)对象，关键字为 keyword.
	 * HQL: title LIKE :keyword
	 * 
	 * @param module - 所属模块。
	 * @param keyword - 查询关键字。
	 * @param page_info - 分页设置，返回时将设置其中必要的值。
	 */
	public List searchItemList(Module module, String keyword, PaginationInfo page_info) {
		QueryHelper qh = new QueryHelper();
		qh.fromClause = " FROM " + getModuleItemClazz(module);
		qh.whereClause = " WHERE deleted=false AND status=1 AND title LIKE :keyword ";
		qh.setString("keyword", "%" + keyword + "%");
		qh.orderClause = " ORDER BY createTime DESC ";
		
		return this.performQuery(qh, page_info);
	}
	
	/** 根据模块类型获得对应对象类的名字，如返回 'Article', 'Photo', 'Soft' 等。 */
	private String getModuleItemClazz(Module module) {
		if (module.getPublishModule() instanceof ArticleModule) 
			return "Article";
		else if (module.getPublishModule() instanceof PhotoModule)
			return "Photo";
		else if (module.getPublishModule() instanceof SoftModule)
			return "Soft";
		
		return "";
	}

	// === getItemDataTable ==============================================
		
	/**
	 * 获得指定选项的项目列表。其中选项参见 ShowArticleListHandler 的说明。
	 * @param options - 选项
	 * @param page_info - 分页信息
	 * 
	 * options 参数：
	 * @param options.itemName - 项目名字，如 "Article", "Soft"
	 * @param options.currentChannel - 当前频道对象。
	 * 
	 * @return
	 */
	public List getItemList(Map options, PaginationInfo page_info) {
		QueryHelper qh = createItemListQueryHelper(options, page_info);
		
		return performQuery(qh, page_info);
	}

	private QueryHelper createItemListQueryHelper(Map options, PaginationInfo page_info) {
		ItemListQuery qh = new ItemListQuery(options);
		qh.init();
		return qh;
	}

	/**
	 * 得到指定栏目下的项目列表信息，分页实现。
	 * @return 返回 DataTable.
	 * @schema = id, columnId, columnName, lastModified, title, author, inputer, editor, hits, stars, top, elite, includePic, status, keywords
	 * @options.param includeChildColumn 是否显示子孙栏目的项目。
	 * @options.param isDeleted 是否已经删除。
	 * @options.param status 项目的状态，所有项目：9；待审核的项目：0；已审核的项目：1。默认为null，不处理。
	 * @options.param isTop 固顶项目，默认为null，不处理。
	 * @options.param isElite 推荐项目，默认为null，不处理。
	 * @options.param isHot 热门项目，默认为null，不处理。 
	 * @options.param isDeleted 是否被删除，默认为 false。
	 * @options.param inputer 项目录入者，默认为null，不处理。
	 * @options.param field 搜索选项域，分别为标题，内容，作者，录入者。
	 * @options.param keyWord 搜索选项关键字，默认为"关键字"。
	 * @page_info.currPage - 当前页
	 * @page_info.pageSize - 每页记录数量
	 */
	public DataTable getItemDataTable(ItemQueryOption option, PaginationInfo page_info) {
		// 构造 Query.
		ItemDataTableQuery query = new ItemDataTableQuery(pub_ctxt, option);
		query.init();
		
		// 进行数据查询。
		
		page_info.setTotalCount(query.queryTotalCount(pub_ctxt.getDao()));
		List list = query.queryData(pub_ctxt.getDao(), page_info);
		
		// 构造返回 DataTable
		DataSchema schema = PublishUtil.columnsToSchema(query.getSelectFieldsForItemList());
		DataTable data_table = new DataTable(schema);
		PublishUtil.addToDataTable(list, data_table);
		
		return data_table;
	}
	
	
	/**
	 * 提供给 ItemDataTableQuery 和 SpecialItemQuery 做为公共基类。
	 * @author liujunxing
	 *
	 */
	private static abstract class ItemQueryBase extends QueryHelper {
		protected final PublishContext pub_ctxt;
		protected final ItemQueryOption option;
		protected final Channel channel;
		public ItemQueryBase(PublishContext pub_ctxt, ItemQueryOption option) {
			this.pub_ctxt = pub_ctxt;
			this.option = option;
			this.channel = pub_ctxt.getSite().getChannel(option.channelId);
		}
	}
	
	/**
	 * 根据栏目查询项目的辅助类。
	 * 
	 * @author liujunxing
	 *
	 */
	private static final class ItemDataTableQuery extends ItemQueryBase {
		private ModuleInfo module_info;
		public ItemDataTableQuery(PublishContext pub_ctxt, ItemQueryOption option) {
			super(pub_ctxt, option);
		}
		public void init() {
			this.module_info = new ModuleInfo(channel);
			
			option.initQueryHelper(this, pub_ctxt);
			
			super.selectClause = "SELECT " + getSelectFieldsForItemList();
			super.fromClause = " FROM " + this.module_info.getItemClassName();
			super.orderClause = " ORDER BY id DESC ";
		}
	
		/** 项目项目列表查询中hql语句中的select字段 */
		public String getSelectFieldsForItemList() {
			return module_info.getSelectFieldsForItemList();
		}
	}

	/** 执行查询。 */
	private List performQuery(QueryHelper qh, PaginationInfo page_info) {
		// 查询数据总量。
		page_info.setTotalCount(qh.queryTotalCount(pub_ctxt.getDao()));
		
		// 查询项目。
		List item_list = qh.queryData(pub_ctxt.getDao(), page_info);
		
		// 对 item 进行 Model 处理。
		initItem(item_list);

		return item_list;
	}

	/** 初始化对象 Model 结构。 */
	private void initItem(List list) {
		if (list == null || list.size() == 0) return;
		for (int i = 0; i < list.size(); ++i) {
			Item item = (Item)list.get(i);
			item._init(pub_ctxt, null);
		}
	}

	/** 根据选项构造 QueryHelper */

	/**
	 * 通过给定选项查询项目的辅助类。
	 * @author liujunxing
	 */
	private class ItemListQuery extends QueryHelper {
		private final Map options;
		
		private int channelId;
		private Channel currentChannel;
		private int columnId;
		private Column currentColumn;
		private int specialId;
		
		public ItemListQuery(Map options) {
			this.options = options;
		}
		
		public void init() {
			// example: ' FROM Article '
			this.fromClause = " FROM " + StringHelper.capFirst(options.get("itemName").toString()) + " ";
			// deleted = false: 未删除的； status = 1: 已经审核的。
			this.whereClause = " WHERE (deleted = false) AND (status = 1) ";
			
			handleChannelId();		// 处理频道标识参数。
			handleColumnId();		// 处理栏目标识参数。
			handleSpecialId();		// 处理专题标识参数。
			
			handleHotElite();		// 处理 isHot, isElite 参数。
			handleAuthor();			// 处理作者参数。
			handleDateScope();		// 处理日期范围参数。
			
			this.orderClause = calcOrderBy();
		}
		
		private String calcOrderBy() {
			// orderBy 实际的处理 orderBy
			Object orderBy = options.get("orderBy"); 
			if (orderBy != null && orderBy.toString().length() > 0) {
				return " ORDER BY " + orderBy.toString();
			} else {
				return " ORDER BY id DESC";
			}
		}
		
		/** 处理频道参数。
		 * 频道标识（0:当前频道；-2:不属于任何频道（全站专题时）；-1：同类频道；1：频道标识为1。默认为 0 。）
		 */
		private void handleChannelId() {
			// 处理channelId
			// 全站专题的时候文章不属于任何频道			
			this.channelId = (Integer)options.get("channelId");
			this.currentChannel = (Channel)options.get("currentChannel");
			if (channelId == 0) {	// 当前频道
				if (currentChannel != null)
					this.whereClause += " AND channelId = " + currentChannel.getId();
				return;
			}
			
			if (channelId > 0) {	// 指定频道标识
				this.whereClause += " AND channelId = " + channelId;
				return;
			} 
			
			if (channelId == -1) {	// 同类频道
				if (currentChannel != null) {
					int moduleId = currentChannel.getModuleId();
					// 枚举具有相同模块的所有频道。
					String channelIds = "";
					for (Iterator<Channel> iterator = pub_ctxt.getSite().getChannels().iterator(); iterator.hasNext(); ) {
						Channel channel0 = iterator.next();
						if (channel0.getChannelType() != 2 && channel0.getStatus() == 0 && channel0.getModuleId() == moduleId) {
							channelIds += channel0.getId() + ",";
						}
					}
					if (channelIds.length() > 0) {
						channelIds = channelIds.substring(0, channelIds.length() - 1);
						this.fromClause += " AND channelId in(" + channelIds.toString() + ")";
					}
				}
				return;
			} 
			
			// -2 不属于任何频道(全站专题时)
		}

		/** 处理栏目参数。
		 * 栏目标识（0：当前栏目；-1：显示频道中所有栏目；1：栏目标识为1。默认为 0 。）
		 */
		private void handleColumnId() {
			// columnId 实际的写入 columnId ，真实的读取数据。
			this.columnId = (Integer)options.get("columnId");
			this.currentColumn = (Column)options.get("currentColumn");
			
			if (columnId >= 0) {	// 等于 0 ，处理当前的栏目，大于 0 ，处理指定的栏目。
				if (columnId == 0) {
					columnId = currentColumn.getId();
				}
				if ((Boolean)options.get("isIncludeChild")) { // 包含子栏目
					// TODO: 也许下面的查询应该放在 column 里面实现。
					TreeViewModel treeView = new TreeViewModel(Column.THE_TABLENAME, Column.THE_ALIAS, pub_ctxt.getDao());
					TreeViewQueryObject query = treeView.getChildrensQuery(columnId);
					String hql0 = "select " + Column.THE_ALIAS + ".id from " + Column.THE_TABLENAME 
						+ " as " + Column.THE_ALIAS + " where " + query.getWhere();
					List list0 = pub_ctxt.getDao().list(hql0);
					String temp = "" + columnId;
					for (int i = 0; i < list0.size(); i++) {
						temp += ", " + list0.get(i);
					}
					this.whereClause += " AND columnId in(" + temp + ")";
				} else {
					this.whereClause += " AND columnId=" + columnId;
				}
			}
		}

		/** 处理专题参数。
		 * 0：当前专题；-1：不对专题予以考虑；-2：当前频道的所有专题; -3：不属于任何专题；1：专题标识为1。默认为 0 。
		 */
		private void handleSpecialId() {
			// specialId 专题，实际的处理专题的数据。
			this.specialId = (Integer)options.get("specialId");
			
			if (specialId == -2) {	
				// 当前频道的所有专题
				String whereCause = "";
				if (channelId == -2) {
					whereCause = "channelId = 0";
				} else if(currentChannel != null) {
					whereCause = "channelId = " + currentChannel.getId();
				}
				if (!whereCause.equals("")) {
					// TODO: 下面的封装起来。
					String hql0 = "select distinct itemId From RefSpecialItem Where specialId In(Select id From Special WHERE " 
						+ whereCause  +")";
					List list = pub_ctxt.getDao().list(hql0);
					if (list != null && list.size() > 0) {
						String str_itemIds = String.valueOf(list.get(0));
						for (int i = 1; i < list.size(); i++) {
							str_itemIds += ("," + list.get(i));
						}
						this.whereClause += " AND id in(" + str_itemIds + ")";
					}
				}				
			} else if (specialId == -3) {
				// TODO: 不属于任何专题
				// RefSpecialItem refObject = new RefSpecialItem(pub_ctxt);
				// List<Integer> ids = refObject.getAllItemIds();
				// this.whereClause += " AND id not in(" + PublishUtil.toSqlInString(ids) + ")";
			} else if (specialId > 0) {
				RefSpecialItem ref_obj = new RefSpecialItem(pub_ctxt);
				List<Integer> list = ref_obj.getItemIds2(specialId);
				if (list != null && list.size() > 0) {
					this.whereClause += " AND id IN (" + PublishUtil.toSqlInString(list) + ")";
				} 
			}
		}
		
		/** 处理 isHot, isElite 参数。
		 * */
		private void handleHotElite() {
			// isHot
			if ((Boolean)options.get("isHot") && currentChannel != null) {
				this.whereClause += " AND hits>=:hotHits";
				super.setInteger("hotHits", currentChannel.getHitsOfHot());
			}
			// isElite
			if ((Boolean)options.get("isElite")) {
				this.whereClause += " AND elite=true";
			}
		}
		
		/** 处理作者参数。
		 * */
		private void handleAuthor() {
			String author = (String)options.get("author");
			if (author == null || author.length() == 0) return;
			
			// author
			this.whereClause += " AND author = :author";
			super.setString("author", author);
		}
		
		/** 处理日期范围参数。 */
		@SuppressWarnings("deprecation")
		private void handleDateScope() {
			int dateScope = (Integer)options.get("dateScope");
			if (dateScope <= 0) return;
			
			this.whereClause += " AND createTime >= :createTime";
			Date date = new Date();
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date.setDate(date.getDate() - dateScope);
			super.setDate("createTime", date);
		}
	}

	// === 辅助类 ==================================================================
	
	public static final class ModuleInfo {
		private ChannelModule module;
		public ModuleInfo(Channel channel) {
			this.module = channel.getChannelModule();
		}
		
		/** 项目项目列表查询中hql语句中的select字段 */
		public String getSelectFieldsForItemList() {
			if ("ArticleModule".equalsIgnoreCase(module.getModuleName()))
				return "id, columnId, lastModified, title, author, inputer, editor, source, hits, stars, top, elite, includePic, status, keywords, isGenerated, staticPageUrl";
			else if ("PhotoModule".equalsIgnoreCase(module.getModuleName()))
				return "id, columnId, lastModified, title, author, inputer, editor, source, hits, stars, top, elite, status, keywords, thumbPic, isGenerated, staticPageUrl";
			else if ("SoftModule".equalsIgnoreCase(module.getModuleName()))
				return "id, columnId, lastModified, title, author, inputer, editor, source, hits, stars, top, elite, status, keywords, version, thumbPic, isGenerated, staticPageUrl";
			throw new PublishException("未知或不支持的模块类型：" + module.getModuleName());
		}
		
		public String getItemClassName() {
			return module.getItemClass();
		}
	}

}
