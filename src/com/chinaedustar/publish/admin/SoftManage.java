package com.chinaedustar.publish.admin;

import java.util.List;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.DataTable;
import com.chinaedustar.publish.model.ItemBusinessObject;
import com.chinaedustar.publish.model.ItemQueryOption;
import com.chinaedustar.publish.model.PaginationInfo;
import com.chinaedustar.publish.model.Soft;
import com.chinaedustar.publish.model.SpecialItemQuery;
import com.chinaedustar.publish.model.SpecialWrapper;

/**
 * 软件管理数据提供。
 * 
 * @author liujunxing
 *
 */
public class SoftManage extends AbstractItemManage {

	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public SoftManage(PageContext pageContext) {
		super(pageContext);
	}

	/** 通过给出的 query_option 初始化页面数据。 */
	private void initByQueryOption(ItemQueryOption query_option) {
		// 构造数据。
		// request 的 Parameter 中需要 channelId, [columnId], [status], [isTop], 
		//   [isElite], [isHot], [isCommend], [inputer], [field], [keyWord]。
		PaginationInfo page_info = getPaginationInfo();
		super.applyOptionToRequest(query_option);
		
		// 进行实际查询。
		ItemBusinessObject ibo = new ItemBusinessObject(pub_ctxt);
		DataTable soft_list = ibo.getItemDataTable(query_option, page_info);
		page_info.init();
		
		// 添加一个计算域: columnName
		soft_list.addCalcField("columnName", new ColumnNameFieldCalc(channel));
		setTemplateVariable("soft_list", soft_list);
		setTemplateVariable("page_info", page_info);
	}
	
	/**
	 * admin_soft_list.jsp 页面使用的数据的初始化。
	 * @initdata 
	 *  <li>channel - 当前频道。
	 *  <li>dropdown_columns - 定义下拉栏目列表的数据。
	 *  <li>column_nav - 栏目层次结构的 List&lt;Column&gt; 数据。
	 *  <li>soft_list - 软件列表，是一个 DataTable.
	 *  <li>page_info - 分页信息。
	 */
	public void initListPage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getItemQueryOption();
		initByQueryOption(query_option);
	}

	/**
	 * admin_soft_add.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		// 获得当前 Channel 对象。
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		  
		// 获得软件对象。
		Soft soft = getChannelSoftData();
		setTemplateVariable("soft", soft);
		
		// 初始化软件选项参数.
		initSoftParam();

		// 编辑页其它数据。
		super.initEditPageCommonData();
	}
	
	/**
	 * admin_soft_view.jsp 页面数据初始化。
	 *
	 */
	public void initViewPage() {
		// 当前频道对象。
		super.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 软件对象。
		Soft soft = getSoftData();
		setTemplateVariable("soft", soft);
		
		// 获得此频道下专题列表。（包括：全站专题、当前频道专题。）
		Object channel_specials = super.getChannelSpecialDataTable(channel);
		setTemplateVariable("channel_specials", channel_specials);
	}
	
	
	/**
	 * admin_special_soft_list.jsp 页面数据初始化。
	 *
	 */
	public void initSpecialItemListPage() {
		// 根据 Request 的 channelId 获取 Channel
		this.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 获得当前专题下的需要显示的图片的集合，同时得到分页信息。
		ItemQueryOption option = getItemQueryOption();
		applyOptionToRequest(option);
		PaginationInfo page_info = getPaginationInfo();
		SpecialItemQuery query_exec = new SpecialItemQuery(pub_ctxt);
		DataTable soft_list = query_exec.getSpecialSofts(option, page_info);
		addColumnNameField(soft_list);
		page_info.init();
		setTemplateVariable("soft_list", soft_list);
		setTemplateVariable("page_info", page_info);

		// 得到当前频道的所有专题。
		List<SpecialWrapper> special_list = channel.getSpecialCollection().getSpecialList();
		setTemplateVariable("special_list", special_list);
	}

	// 初始化软件选项参数.
	private void initSoftParam() {
		SoftParamProvider provider = new SoftParamProvider();
		Object param_map = provider.getSoftParam(pub_ctxt, this.channel.getId());
		setTemplateVariable("soft_param_list", param_map);
	}
	
	/**
	 * 获得 admin_soft_add.jsp 页面的软件对象。
	 *   如果是新建软件，则产生一个默认属性的软件对象。
	 * @param channel - 频道对象。
	 * @return
	 */
	private Soft getChannelSoftData() {
		// 得到当前栏目，如果 columnId == 0 则使用根栏目。
		Column column = super.getColumnData();
		if (column == null) throw new PublishException("不正确的栏目标识，请确定您是从有效的链接地址进入的。");

		// 如果 softId == 0 表示新增一个软件。
		int softId = super.safeGetIntParam("softId", 0);
		if (softId == 0)
			return newSoftObject(column.getId());

		Soft soft = column.getSoftItem(softId);
		if (soft == null) throw new PublishException("无法找到指定标识的软件对象，请确定您是从有效链接地址进入的。");
		return soft;
	}

	/** 创建一个新的 soft 对象，并设置所属频道、栏目标识。 */
	private Soft newSoftObject(int columnId) {
		Soft soft = new Soft();
		soft.setChannelId(channel.getId());
		soft.setColumnId(columnId);
		soft.setCreateTime(new java.util.Date());
		soft.setInputer(admin.getAdminName());
		soft.setStars(3);
		soft.setCommentFlag(1);
		return soft;
	}

	
	/**
	 * admin_soft_approv.jsp 页面使用的数据的初始化。
	 * @initdata 初始化的数据包括
	 *  <li>channel - 当前频道。
	 *  <li>soft_list - 需要显示的软件的集合。
	 *  <li>page_info - 软件列表的分页信息。
	 *  <li>dropdown_columns - 频道下拉列表数据。
	 *  <li>column_nav - 自根栏目开始到当前栏目的导航列表数据。
	 */
	public void initApprovPage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getApprovOption();
		initByQueryOption(query_option);
	}

	/**
	 * admin_soft_generate.jsp 页面使用的数据的初始化。
	 * @initdata
	 *  <li>channel - 当前频道。
	 *  <li>soft_list - 需要显示的软件的集合。
	 *  <li>page_info - 分页信息。
	 *  <li>dropdown_columns - 频道下拉列表数据。
	 *  <li>column_nav - 自根栏目开始到当前栏目的导航列表数据。
	 */
	public void initGeneratePage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getGenerateOption();
		initByQueryOption(query_option);
	}

	/**
	 * admin_soft_my.jsp 页面使用的数据的初始化。
	 *
	 */
	public void initMySoftPage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getItemQueryOption();
		query_option.inputer = super.admin.getAdminName();		// 强迫作者 = 当前管理员。
		
		initByQueryOption(query_option);
	}
	
	/**
	 * admin_recycle_soft_list.jsp 页面使用的数据的初始化。
	 *
	 */
	public void initRecyclePage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();
		
		// 构造数据。
		ItemQueryOption query_option = super.getRecycleOption();
		initByQueryOption(query_option);
	}

	/** 获得软件对象。 */
	private Soft getSoftData() {
		int softId = super.safeGetIntParam("softId", 0);
		Soft soft = channel.loadSoft(softId);
		return soft;
	}
}
