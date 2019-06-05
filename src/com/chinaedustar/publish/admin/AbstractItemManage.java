package com.chinaedustar.publish.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.ColumnTree;
import com.chinaedustar.publish.model.DataRow;
import com.chinaedustar.publish.model.DataTable;
import com.chinaedustar.publish.model.ItemQueryOption;
import com.chinaedustar.publish.model.SpecialCollection;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * Article, Soft, Photo - 新闻、软件、图片管理部分使用的支持类。
 *  
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
abstract class AbstractItemManage extends AbstractBaseManage {
	/** 当前频道对象。 */
	protected Channel channel;
	
	/**
	 * admin_check_same_title.jsp 页面数据初始化.
	 *
	 */
	public void initCheckSameTitle() {
		String result = internalCheckSameTitle();

		setTemplateVariable("result", result);
	}
	
	private String internalCheckSameTitle() {
		Channel channel = this.getChannelData();
		if (channel == null)
			return "必须给定频道的标识（channelId）";
		
		String title = param_util.safeGetChineseParameter("title", "");
		if (title == null || title.length() == 0)
			return "被检测的标题不可以为空！";
		
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Item ";
		query.whereClause = " WHERE channelId = " + channel.getId() +
			" AND title = :title ";
		query.setString("title", title);
		
		long count = query.queryTotalCount(pub_ctxt.getDao());
		
		if (count > 0) return "此标题已经被使用！";
		return "此标题尚未使用！";
	}
	
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	protected AbstractItemManage(PageContext pageContext) {
		super(pageContext);
	}

	/**
	 * 获得指定频道下专题对象集合。提供给 admin_add_article.jsp 页面显示专题使用。
	 * @param channel - 频道对象，必须非空。
	 * @return 返回一个 DataTable，其 schema 为 [id, name, channelId]
	 * @see com.chinaedustar.publish.model.SpecialCollection.getSpecialDataTable()
	 */
	protected Object getChannelSpecialDataTable(Channel channel) {
		if (channel == null) throw new java.lang.IllegalArgumentException("channel == null");
		
		SpecialCollection spec_coll = channel.getSpecialCollection();
		return spec_coll.getChannelSpecialDataTable(channel.getId());
	}

	/**
	 * 根据页面参数获取数据的选项对象。
	 * 研究在各个不同页面获取的选项的缺省值有所不同的问题。
	 *   调用者自己负责在调用之后重载某些处理，例如缺省值的处理。
	 * @return 返回 ItemQueryOption 对象用于 Item 的复杂查询功能。
	 */
	protected ItemQueryOption getItemQueryOption() {
		ItemQueryOption option = new ItemQueryOption();
		
		option.channelId = super.safeGetIntParam("channelId", 0);
		option.columnId = super.safeGetIntParam("columnId", 0);
		option.includeChildColumns = true;	// 缺省为 包含其所有子栏目。
		option.specialId = super.safeGetIntParam("specialId", 0);
		
		option.status = super.safeGetIntParam2("status", null);
		option.isTop = super.safeGetBooleanParam("isTop", null);
		option.isElite = super.safeGetBooleanParam("isElite", null);
		option.isHot = super.safeGetBooleanParam("isHot", null);
		option.isCommend = super.safeGetBooleanParam("isCommend", null);
		option.isGenerated = super.safeGetBooleanParam("isGenerated", null);
		option.inputer = super.safeGetStringParam("inputer", null);
		option.field = super.safeGetStringParam("field", null);
		option.keyword = super.safeGetChineseParameter("keyWord", null);
		
		return option;
	}
	
	/**
	 * 获得 admin_article_approv 页面使用的缺省 option 数据。
	 * @return
	 */
	protected ItemQueryOption getApprovOption() {
		ItemQueryOption query_option = getItemQueryOption();
		query_option.status = super.safeGetIntParam2("status", 0);	// 0 - 未审核，缺省
		applyOptionToRequest(query_option);
		return query_option;
	}

	/**
	 * 获得 admin_article_approv 页面使用的缺省 option 数据。
	 * @return
	 */
	protected ItemQueryOption getGenerateOption() {
		ItemQueryOption query_option = getItemQueryOption();
		query_option.isGenerated = super.safeGetBooleanParam("isGenerated", false);
		
		applyOptionToRequest(query_option);
		return query_option;
	}

	/**
	 * 获得 admin_recycle_xxx 页面使用的缺省 option 数据。
	 * @return
	 */
	protected ItemQueryOption getRecycleOption() {
		ItemQueryOption query_option = getItemQueryOption();
		query_option.isDeleted = true;
		
		applyOptionToRequest(query_option);
		return query_option;
	}
	
	/**
	 * 将指定的 option 里面的数据放到 super.request 对象里面，按照类型设置。
	 *
	 */
	protected void applyOptionToRequest(ItemQueryOption option) {
		request_param.put("channelId", option.channelId);
		request_param.put("columnId", option.columnId);
		request_param.put("specialId", option.specialId);
		request_param.put("status", option.status);
		request_param.put("isTop", option.isTop);
		request_param.put("isElite", option.isElite);
		request_param.put("isHot", option.isHot);
		request_param.put("isCommend", option.isCommend);
		request_param.put("isGenerated", option.isGenerated);
		request_param.put("isDeleted", option.isDeleted);
		request_param.put("inputer", option.inputer);
		request_param.put("field", option.field);
		request_param.put("keyword", option.keyword);
	}
	
	/**
	 * 提供 channel, dropdown_columns, column_nav 的初始化设置。
	 * @initdata 
	 *  <li>channel - 当前所在频道。
	 *  <li>dropdown_columns - 定义下拉栏目列表的数据。
	 *  <li>column_nav - 栏目层次结构的 List&lt;Column&gt; 数据。
	 */
	protected void initChannelAndColumnData() {
		// 根据 Request 的 channelId 获取 Channel
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
	
		// 得到栏目层次结构的ArrayList<Column>数据。
		int columnId = safeGetIntParam("columnId", 0);
		List<Column> column_nav = channel.getColumnTree().getColumnNavList(columnId);
		setTemplateVariable("column_nav", column_nav);
		// 此栏目层次结构中第一个是根栏目 ('新闻中心'), 第二个是首层栏目。
		Column first_level_column = (column_nav != null && column_nav.size() >= 2) 
			? column_nav.get(1) : null;
		Column second_level_column = (column_nav != null && column_nav.size() >= 3) 
			? column_nav.get(2) : null;
		Column current_column = (column_nav != null && column_nav.size() > 0)
			? column_nav.get(column_nav.size() - 1) : null;
		setTemplateVariable("first_level_column", first_level_column);
		setTemplateVariable("second_level_column", second_level_column);
		setTemplateVariable("current_column", current_column);
		
		// 定义下拉栏目列表的数据。
		DataTable dropdown_columns = super.getColumnsDropDownData(channel);
		setTemplateVariable("dropdown_columns", dropdown_columns);
		// apply childCount to dropdown_columns
		calcChildCount(dropdown_columns);
		
		// 从 dropdown_columns 中提取出第一层的子栏目，称之为 main_column_list
		Object main_column_list = createMainColumnList(dropdown_columns);
		setTemplateVariable("main_column_list", main_column_list);
		
		// 从 dropdown_columns 中提取出第二层的子栏目，称之为 sub_column_list
		Object sub_column_list = createSubColumnList(first_level_column, dropdown_columns);
		setTemplateVariable("sub_column_list", sub_column_list);
		
		// 从 dropdown_columns 中提取出当前栏目的子栏目，称之为 child_column_list
		Object child_column_list = createSubColumnList(current_column, dropdown_columns);
		setTemplateVariable("child_column_list", child_column_list);
	}
	
	/**
	 * 计算 dropdown_columns 的子节点数量。
	 * @param dropdown_columns
	 */
	private void calcChildCount(DataTable dropdown_columns) {
		Map<Integer, Integer> child_count = new java.util.HashMap<Integer, Integer>();
		// 1. 初始化 map
		for (int i = 0; i < dropdown_columns.size(); ++i) {
			DataRow row = dropdown_columns.get(i);
			Integer id = (Integer)row.get("id");
			child_count.put(id, 0);
		}
		
		// 2. 每个节点增加其父节点的子节点数。
		for (int i = 0; i < dropdown_columns.size(); ++i) {
			DataRow row = dropdown_columns.get(i);
			Integer parentId = (Integer)row.get("parentId");
			Integer count = child_count.get(parentId);
			if (count != null)
				child_count.put(parentId, count.intValue() + 1);
		}
		
		// 3. 设置回子节点数量。
		int field_index = dropdown_columns.getSchema().addColumn("childCount");
		for (int i = 0; i < dropdown_columns.size(); ++i) {
			DataRow row = dropdown_columns.get(i);
			Integer id = (Integer)row.get("id");
			Integer count = child_count.get(id);
			row.set(field_index, count);
		}
	}

	
	/**
	 * 从一个 DataTable{id, name, parentId, parentPath, orderPath, enableAdd} 中提取
	 *   出 first_level_column 的子栏目，并组装为 List 返回。
	 * @param dropdown_columns
	 * @return
	 */
	private List<DataRow> createSubColumnList(Column first_level_column, DataTable dropdown_columns) {
		if (first_level_column == null) return null;
		int parentId = first_level_column.getId();
		List<DataRow> sub_column_list = new java.util.ArrayList<DataRow>();
		for (int i = 0; i < dropdown_columns.size(); ++i) {
			DataRow row = dropdown_columns.get(i);
			if (row.get("parentId").equals(parentId))
				sub_column_list.add(row);
		}
		if (sub_column_list.size() == 0) return null;
		return sub_column_list;
	}

	/**
	 * 从一个 DataTable{id, name, parentId, parentPath, orderPath, enableAdd} 中提取
	 *   出第一级栏目，并组装为 List 返回。
	 * @param dropdown_columns
	 * @return
	 */
	private List<DataRow> createMainColumnList(DataTable dropdown_columns) {
		List<DataRow> main_column_list = new java.util.ArrayList<DataRow>();
		for (int i = 0; i < dropdown_columns.size(); ++i) {
			DataRow row = dropdown_columns.get(i);
			if (row.get("parentId").equals(channel.getRootColumnId()))
				main_column_list.add(row);
		}
		return main_column_list;
	}
	
	/**
	 * 得到当前栏目，如果 columnId == 0 则使用根栏目。
	 * @return
	 */
	protected Column getColumnData() {
		// 得到当前栏目，如果 columnId == 0 则使用根栏目。
		int columnId = param_util.safeGetIntParam("columnId");
		ColumnTree column_tree = this.channel.getColumnTree();
		Column column = null;
		if (columnId == 0)
			column = column_tree.getColumn(channel.getRootColumnId());
		else
			column = column_tree.getColumn(columnId);
		return column;
	}
	
	/**
	 * 初始化编辑页面所需的如下数据：
	 * @initdata
	 *  <li>dropdown_columns - 下拉栏目列表的数据
	 *  <li>channel_specials - 获得此频道下专题列表。（包括：全站专题、当前频道专题。）
	 *  <li>author_list - 得到最近使用过的作者集合
	 *  <li>keyword_list - 得到最近使用过的关键字集合
	 *  <li>source_list - 得到最近使用过的来源集合
	 *  <li>templ_list - 得到文章内容页模板列表, 其 type_name = 'content'
	 *  <li>skin_list - 提供默认方案下皮肤列表的数据
	 */
	protected void initEditPageCommonData() {
		// 下拉栏目列表的数据。
		DataTable dropdown_columns = super.getColumnsDropDownData(channel);
		setTemplateVariable("dropdown_columns", dropdown_columns);

		// 获得此频道下专题列表。（包括：全站专题、当前频道专题。）
		Object channel_specials = getChannelSpecialDataTable(channel);
		setTemplateVariable("channel_specials", channel_specials);

		// 得到最近使用过的作者集合
		List author_list = super.getAuthorsLatestUsed(channel);
		setTemplateVariable("author_list", author_list);

		// 得到最近使用过的关键字集合
		List keyword_list = super.getKeywordsLatestUsed(channel, 0);
		setTemplateVariable("keyword_list", keyword_list);
		
		// 得到最近使用过的来源集合
		List source_list = super.getSourcesLatestUsed(channel);
		setTemplateVariable("source_list", source_list);

		// 得到文章内容页模板列表
		Object templ_list = super.getAvailableTemplateDataTable(channel, "content");
		setTemplateVariable("templ_list", templ_list);

		// 提供默认方案下皮肤列表的数据
		Object skin_list = super.getAvailableSkinDataTable();
		setTemplateVariable("skin_list", skin_list);
	}
	
	/**
	 * 给 DataTable 中添加 columName 字段，其使用批量查询方式加快检索速度。
	 *  DataTable 中必须有 'columnId' 字段, 表示栏目标识。
	 * @param data_table
	 */
	protected void addColumnNameField(DataTable data_table) {
		if (data_table == null || data_table.size() == 0) return;
		int column_index = data_table.getSchema().getColumnIndex("columnId");
		if (column_index < 0) return;
		
		// columnId 到 name 的映射表。
		Map<Integer, String> id_name_map = new java.util.HashMap<Integer, String>();
		// 1. 建立完整的 columnId 映射。
		for (int i = 0; i < data_table.size(); ++i) {
			id_name_map.put((Integer)data_table.get(i).get(column_index), "");
		}
		
		// 2. 查询所有 columnId -> Name
		List<Integer> column_ids = new java.util.ArrayList<Integer>();
		column_ids.addAll(id_name_map.keySet());
		String hql = "SELECT id, name FROM Column WHERE id IN (" + PublishUtil.toSqlInString(column_ids) + ")";
		List list = pub_ctxt.getDao().list(hql);
		for (int i = 0; i < list.size(); ++i) {
			Object[] data = (Object[])list.get(i);
			id_name_map.put((Integer)data[0], (String)data[1]);
		}
		
		// 3. 匹配所有 columnId -> DataTable 中
		int column_name_index = data_table.getSchema().addColumn("columnName");
		for (int i = 0; i < data_table.size(); ++i) {
			DataRow row = data_table.get(i);
			Integer column_id = (Integer)row.get(column_index);
			String name = id_name_map.get(column_id);
			if (name == null) name = "";
			row.set(column_name_index, name);
		}
	}
	
	/**
	 * 给 DataTable 中添加 thumbPicAbs 字段，其假定包含字段 thumbPic 和 channelId
	 *  适用于 Photo, Soft 的 DataTable
	 * @param data_table
	 */
	protected void addThumbPicAbs(DataTable data_table) {
		// 检查是否不符合条件。
		if (data_table == null || data_table.size() == 0) return;
		int thumb_pic_index = data_table.getSchema().getColumnIndex("thumbPic");
		if (thumb_pic_index < 0) return;
		int channel_id_index = data_table.getSchema().getColumnIndex("channelId");
		if (channel_id_index < 0) return;
		
		// 添加该字段。
		int thumb_abs_index = data_table.getSchema().addColumn("thumbPicAbs");
		int saved_channel_id = 0;
		Channel channel = null;
		
		// 循环所有行。
		for (int i = 0; i < data_table.size(); ++i) {
			DataRow row = data_table.get(i);
			// 找到频道。
			int channel_id = (Integer)row.get(channel_id_index);
			if (channel_id != saved_channel_id) {
				channel = site.getChannel(channel_id);
				saved_channel_id = channel_id;
			}
			if (channel == null) continue;
			
			// 获得当前缩略图。
			String thumbPic = (String)row.get(thumb_pic_index);
			if (PublishUtil.isEmptyString(thumbPic) == false) {
				// 如果有缩略图，则计算其绝对地址，设置到 DataTable 中。
				String thumbPicAbs = channel.resolveUrl(thumbPic);
				row.set(thumb_abs_index, thumbPicAbs);
			}
		}
	}
	
	
	/** 动态计算栏目名字的计算器。 */
	protected static final class ColumnNameFieldCalc implements DataTable.FieldCalculator {
		private Channel channel;
		public ColumnNameFieldCalc(Channel channel) {
			this.channel = channel;
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.model.DataTable.FieldCalculator#calcValue(java.lang.String, com.chinaedustar.publish.model.DataTable, com.chinaedustar.publish.model.DataRow)
		 */
		public Object calcValue(DataTable table, DataRow row, String name) {
			int columnId = (Integer)row.get("columnId");
			return channel.getColumnTree().getColumnName(columnId);
		}
	}
}
