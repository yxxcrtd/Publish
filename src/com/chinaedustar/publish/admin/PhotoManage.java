package com.chinaedustar.publish.admin;

import java.util.List;

import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.model.*;

/**
 * 图片管理。
 * 
 * @author liujunxing
 *
 */
public class PhotoManage extends AbstractItemManage {
	
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public PhotoManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_photo_add.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		// 当前频道对象。
		super.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 新增或编辑的图片对象。
		Photo photo = getPhotoData();
		setTemplateVariable("photo", photo);
		
		// 编辑页其它数据。
		super.initEditPageCommonData();
	}
	
	/**
	 * admin_photo_list.jsp 页面数据初始化。
	 * @initdata 
	 *  <li>channel - 当前频道。
	 *  <li>photo_list - 需要显示的文章的集合。
	 *  <li>page_info - 文章列表的分页信息。
	 *  <li>dropdown_columns - 频道下拉列表数据。
	 *  <li>column_nav - 自根栏目开始到当前栏目的导航列表数据。
	 */
	public void initListPage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		// request 的 Parameter 中需要 channelId, [columnId], [status], [isTop], 
		//   [isElite], [isHot], [isCommend], [inputer], [page], [maxPerPage], [field], [keyWord]。
		ItemQueryOption query_option = getItemQueryOption();
		initByQueryOption(query_option);
	}
	
	/**
	 * admin_photo_approv.jsp 页面使用的数据的初始化。
	 * @initdata 初始化的数据包括
	 *  <li>channel - 当前频道。
	 *  <li>photo_list - 需要显示的图片的集合。
	 *  <li>page_info - 分页信息。
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
	 * admin_photo_generate.jsp 页面使用的数据的初始化。
	 * @initdata
	 *  <li>channel - 当前频道。
	 *  <li>photo_list - 需要显示的图片的集合。
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
	 * admin_photo_my.jsp 页面使用的数据的初始化。
	 *
	 */
	public void initMyPhotoPage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();

		// 构造数据。
		ItemQueryOption query_option = getItemQueryOption();
		query_option.inputer = super.admin.getAdminName();		// 强迫作者 = 当前管理员。
		
		initByQueryOption(query_option);
	}

	/**
	 * admin_recycle_photo_list.jsp 页面使用的数据的初始化。
	 *
	 */
	public void initRecyclePage() {
		// 一些公共数据初始化。
		initChannelAndColumnData();
		
		// 构造数据。
		ItemQueryOption query_option = super.getRecycleOption();
		initByQueryOption(query_option);
	}

	/**
	 * admin_photo_view.jsp 页面数据初始化。
	 *
	 */
	public void initViewPage() {
		// 当前频道对象。
		super.channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 新增或编辑的图片对象。
		Photo photo = getPhotoData();
		setTemplateVariable("photo", photo);
		
		// 获得此频道下专题列表。（包括：全站专题、当前频道专题。）
		Object channel_specials = super.getChannelSpecialDataTable(channel);
		setTemplateVariable("channel_specials", channel_specials);
	}
	
	/**
	 * admin_special_photo_list.jsp 页面数据初始化。
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
		DataTable photo_list = query_exec.getSpecialPhotos(option, page_info);
		addColumnNameField(photo_list);
		addThumbPicAbs(photo_list);
		page_info.init();
		setTemplateVariable("photo_list", photo_list);
		setTemplateVariable("page_info", page_info);

		// 得到当前频道的所有专题。
		List<SpecialWrapper> special_list = channel.getSpecialCollection().getSpecialList();
		setTemplateVariable("special_list", special_list);
	}
	
	/** 获得图片对象。 */
	private Photo getPhotoData() {
		// 得到当前栏目，如果 columnId == 0 则使用根栏目。
		Column column = super.getColumnData();
		if (column == null)
			throw new PublishException("不正确的栏目标识，请确定您是从有效的链接地址进入的。");
		
		// 创建或加载图片对象。
		int photoId = param_util.safeGetIntParam("photoId", 0);
		if (photoId == 0)
			// 新增，创建新的对象。
			return newPhotoObject(column.getId());

		// 在栏目对象中加载项目。
		Photo photo = column.getPhotoItem(photoId);
		return photo;
	}
	
	/** 新建一个图片对象。 */
	private Photo newPhotoObject(int columnId) {
		Photo photo = new Photo();
		photo.setChannelId(channel.getId());
		photo.setColumnId(columnId);
		photo.setInputer(admin.getAdminName());
		photo.setCommentFlag(1);
		return photo;
	}

	/**
	 * 通过给定查询选项来初始化页面数据。
	 * @param query_option
	 */
	private void initByQueryOption(ItemQueryOption query_option) {
		PaginationInfo page_info = getPaginationInfo();
		super.applyOptionToRequest(query_option);

		// 进行实际查询。
		ItemBusinessObject ibo = new ItemBusinessObject(pub_ctxt);
		DataTable photo_list = ibo.getItemDataTable(query_option, page_info);
		page_info.init();
		// 添加一个计算域: column_name
		photo_list.addCalcField("columnName", new ColumnNameFieldCalc(channel));
		setTemplateVariable("photo_list", photo_list);
		setTemplateVariable("page_info", page_info);
	}
}
