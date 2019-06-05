package com.chinaedustar.publish.admin;

import java.util.List;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.ParamUtil;
import com.chinaedustar.publish.model.*;

/**
 * 模板管理。
 * 
 * @author liujunxing
 *
 */
public class TemplateManage extends AbstractBaseManage {
	/** 方案组，实际业务很多在这里完成。 */
	private TemplateThemeCollection theme_coll;
	
	/** 模板方案对象。 */
	private TemplateTheme theme;

	/** 栏目标识。 */
	private int channelId;
	
	/** 当前频道，可能没有。 */
	private Channel channel;
	
	/** 分组标识。 */
	private int groupId;
	
	/** 类别标识。 */
	private int typeId;
	
	/** 是否显示分组。 */
	private boolean showGroup;
	
	/** 当前分组对象。 */
	private TemplateGroup current_group;
	
	/** 当前类别对象。 */
	private TemplateType current_type;

	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public TemplateManage(PageContext pageContext) {
		super(pageContext);
	}

	public Site getSite() {
		return super.site;
	}
	
	public ParamUtil getParamUtil() {
		return super.param_util;
	}
	
	/**
	 * admin_template_list.jsp 页面数据初始化。
	 * @initdata
	 *  <li>theme - 当前模板方案。
	 *  <li>template_group_list - 所有模板分组。
	 *  <li>current_group - 当前模板分组。
	 * @pageparam 
	 *  <li>themeId - 方案标识，如果没有则使用缺省方案。
	 *  <li>channelId - 频道标识，可以为 0。
	 *  <li>groupId - 分组标识，如果没有，取第一个。
	 *  <li>typeId - 类别标识，如果没有，取第一个。
	 */
	public void initListPage() {
		// 得到指定标识的模板方案，如果没有给出方案标识，则使用缺省模板方案。
		initThemeData();
		
		// 当前频道，可能没有。
		initChannelData();
		
		// 得到的模板分组数据。
		initTemplateGroupList();
		
		// 得到模板类别信息。
		initTemplateTypeList();
		
		// 得到模板列表。
		initTemplateList();
		
		applyRequest();
	}

	/**
	 * admin_template_add.jsp 页面数据初始化。
	 * @initdata
	 *  <li>theme - 模板方案
	 */
	public void initEditPage() {
		this.initThemeData();
		
		// 得到当前要编辑的模板对象。
		PageTemplate template = initTemplateObject();
		
		// 当前频道，可能没有。
		initChannelData();
		
		// 模板方案集合。
		Object theme_list = site.getTemplateThemeCollection().getThemeList();
		setTemplateVariable("theme_list", theme_list);
		
		// 初始化编辑页面模板类型列表。
		initEditTypeList(template);
	}

	/**
	 * admin_template_recycle.jsp 页面数据初始化。
	 *
	 */
	public void initRecyclePage() {
		this.initThemeData();
		
		// 当前频道，可能没有。
		initChannelData();
		
		// 得到的模板分组数据。
		initTemplateGroupList();
		
		// 得到当前分组、当前频道回收站模板列表。
		initRecycleTemplateList();
/*		
<%-- 
  定义页面的数据提供者对象, Group 取出所有组；Type 取出当前组拥有的模板类型; List 取出列表。 
  页面参数：themeId - 方案标识。
    channelId -分组标识 -1 会员 0 通用  >0 频道标识。
    typeId - 分类标识。
--%>
<!-- 得到当前方案下的模板分组集合对象。 -->
<pub:data purpose="提取出所有的模板组" var="tmpl_groups" scope="page" 
	provider="com.chinaedustar.publish.admin.TemplateGroupListDataProvider" />
<!-- 得到当前页面中的〔默认〕模板分组对象 -->
<pub:data var="currentTemplateGroup" 
	provider="com.chinaedustar.publish.admin.TemplateGroupDataProvider" />
<!-- 得到当前模板分组对象中的模板类型集合对象。 -->
<pub:data purpose="提取出所有的模板类型" var="curr_types" 
	provider="com.chinaedustar.publish.admin.TemplateTypeListDataProvider" />
<!-- 得到当前页面中的〔默认〕模板类型对象。 -->
<pub:data var="currentTemplateType"
	provider="com.chinaedustar.publish.admin.TemplateTypeDataProvider" />
<!-- 得到当前模板类型对象中的模板集合对象，不加载 content 属性。 -->
<pub:data purpose="提取出模板列表" var="tmpl_list" param="delete"
	provider="com.chinaedustar.publish.admin.TemplateListDataProvider" />
		
		*/
	}
	
	/**
	 * admin_theme_move.jsp 页面数据初始化.
	 *
	 */
	public void initMovePage() {
		// 初始化识别的参数, 此页面参数 groupId, channelId 是一起传递的。
		this.groupId = 0;
		this.channelId = 0;
		this.theme_coll = site.getTemplateThemeCollection();
		String groupId_channelId = param_util.safeGetStringParam("groupId_channelId");
		if (groupId_channelId != null && groupId_channelId.length() > 0) {
			String[] ids = groupId_channelId.split(",");
			if (ids != null && ids.length >= 2) {
				this.groupId = Integer.parseInt(ids[0]);
				this.channelId = Integer.parseInt(ids[1]);
			}
		}
			
		// 获得所有方案列表集合。 
		List<TemplateTheme> theme_list = theme_coll.getThemeList();
		setTemplateVariable("theme_list", theme_list);
		
		// 当前方案
		int themeId = param_util.safeGetIntParam("themeId", 0);
		this.theme = theme_coll.getTemplateTheme(themeId);
		if (this.theme == null) this.theme = theme_coll.getDefaultTemplateTheme();
		setTemplateVariable("theme", theme);
		
		// 提取所有模板分组。
		this.initTemplateGroupList(this.groupId);
		
		// 提取当前方案、当前分组下所有模板列表。
		this.channel = site.getChannel(this.channelId);
		setTemplateVariable("channel", channel);
		Object template_list = theme.getTemplateDataTable(channel, current_group);
		setTemplateVariable("template_list", template_list);
		
		/*
		<%-- 产生管理所需数据 --%>
		<pub:data provider="com.chinaedustar.publish.admin.TemplateThemeList"
		  var="tmpl_themes" purpose="" />
		<pub:data provider="com.chinaedustar.publish.admin.TemplateGroupProvider" scope="page" 
		  var="tmpl_groups" purpose="提取出所有的模板类型" />
		<pub:data provider="com.chinaedustar.publish.admin.TemplateListProvider"
		  var="tmpl_list" purpose="提取出所有的模板类型" scope="page" />
		
		*/
	}

	/**
	 * admin_template_channel_copy.jsp 页面数据初始化。
	 *
	 */
	public void initCopyPage() {
		// 得到指定标识的模板方案，如果没有给出方案标识，则使用缺省模板方案。
		initThemeData();
		
		// 频道列表。
		List<Channel> channel_list = super.getChannelListData();
		setTemplateVariable("channel_list", channel_list);
		
		// 源频道。
		Channel channel = super.getChannelData();
		if (channel == null && channel_list.size() > 0) channel = channel_list.get(0);
		setTemplateVariable("channel", channel);
		
		// 源频道下所有模板列表。
		Object src_template_list = getSourceTemplateList(channel);
		setTemplateVariable("template_list", src_template_list);
		
		/*
		<!-- 得到当前的模板方案对象。 -->
		<pub:data var="currentTemplateTheme" param="default"
			provider="com.chinaedustar.publish.admin.TemplateThemeDataProvider" />
		<!-- 得到当前页面中的〔默认〕模板分组对象 -->
		<pub:data var="currentTemplateGroup" 
			provider="com.chinaedustar.publish.admin.TemplateGroupDataProvider" />
		<!-- 得到频道列表 -->
		<pub:data var="channel_list"
			provider="com.chinaedustar.publish.admin.ChannelListDataProvider"/>
		*/

	}
	
	// === 实现 =====================================================================
	
	/** 获得源频道下模板列表。 */
	private Object getSourceTemplateList(Channel channel) {
		if (channel == null) return null;
		
		return this.theme_coll.getAvailableTemplateDataTable(theme.getId(), channel.getId());
	}
	
	/** 根据 themeId 参数获得模板方案对象，如果没有则使用缺省方案。 */
	private void initThemeData() {
		this.theme_coll = site.getTemplateThemeCollection();
		int themeId = super.safeGetIntParam("themeId", 0);
		if (themeId == 0)
			this.theme = theme_coll.getDefaultTemplateTheme();
		else
			this.theme = theme_coll.getTemplateTheme(themeId);
		setTemplateVariable("theme", theme);
	}

	// 当前频道，可能没有。
	private void initChannelData() {
		this.channelId = super.safeGetIntParam("channelId", 0);
		this.channel = site.getChannel(channelId);
		setTemplateVariable("channel", channel);
		if (channel == null) channelId = 0;
	}
	
	// 获得模板分组数据。
	private void initTemplateGroupList() {
		this.groupId = super.safeGetIntParam("groupId", 0);
		initTemplateGroupList(this.groupId);
	}
	
	private void initTemplateGroupList(int groupId) {
		// 获得所有系统模板分组。
		List<TemplateGroup> template_group_list = theme_coll.getSystemTemplateGroupList();
		
		// 产生所有频道的模板分组。
		List<TemplateGroup> channel_group = theme_coll.getChannelTemplateGroupList();
		if (channel_group != null) template_group_list.addAll(channel_group);
		
		// 得到当前分组。
		this.current_group = template_group_list.get(0);
		for (int i = 0; i < template_group_list.size(); ++i) {
			TemplateGroup group = template_group_list.get(i);
			// groupId, channelId 匹配的分组就是当前分组。
			if (group.getId() == groupId && group.getChannelId() == channelId) {
				this.current_group = group;
				break;
			}
		}
		// 如果上述循环未找到，则 template_group_list.get(0) 为当前分组。
		groupId = this.current_group.getId();
		
		setTemplateVariable("template_group_list", template_group_list);
		setTemplateVariable("current_group", current_group);
	}

	// 得到模板类别数据。
	private void initTemplateTypeList() {
		this.typeId = super.safeGetIntParam("typeId", 0);
		
		// 得到模板类别集合。
		List<TemplateType> template_type_list = current_group.getTemplateTypeList();
		TemplateType all_type = new TemplateType();
		all_type.setId(-1);
		all_type.setTitle("所有模板");
		template_type_list.add(all_type);
		this.current_type = template_type_list.get(0);
		
		// 得到当前模板类别。
		if (template_type_list != null && template_type_list.size() > 0) {
			for (int i = 0; i < template_type_list.size(); ++i) {
				TemplateType type = template_type_list.get(i);
				if (type.getId() == this.typeId) {
					this.current_type = type;
					break;
				}
			}
		}
		
		setTemplateVariable("template_type_list", template_type_list);
		setTemplateVariable("current_type", current_type);
	}
	
	// 得到模板列表。
	private void initTemplateList() {
		Object template_list = theme.getTemplateDataTable(this.channel, this.current_type);
		setTemplateVariable("template_list", template_list);
	}
	
	// 设置 request 中强数据类型。
	private void applyRequest() {
		this.showGroup = super.safeGetBooleanParam("showGroup", false);
		request_param.put("showGroup", this.showGroup);
	}

	// 初始化页面要加载的模板对象。
	private PageTemplate initTemplateObject() {
		int templateId = safeGetIntParam("templateId", 0);
		PageTemplate template = null;
		if (templateId == 0) {
			template = new PageTemplate();
			template.setThemeId(this.theme.getId());
			template.setChannelId(safeGetIntParam("channelId", 0));
			template.setTypeId(safeGetIntParam("typeId", 0));
		} else {
			template = theme.loadTemplate(templateId);
		}
		
		setTemplateVariable("template", template);
		return template;
	}
	
	// 初始化编辑页面模板类型列表。
	private void initEditTypeList(PageTemplate template) {
		if (template.getId() == 0) {
			initEditTypeListNew();
		} else {
			initEditTypeListExist(template);
		}
	}
	
	// 当 template 存在的时候初始化 template_type_list
	private void initEditTypeListExist(PageTemplate template) {
		int typeId = template.getTypeId();
		// 根据此 typeId 找到其所属分组。
		int groupId = theme_coll.getTemplateGroupIdByTypeId(typeId);
		
		// 获得该分组下的类型列表。
		Object template_type_list = theme_coll.getTemplateTypeList(groupId);
		setTemplateVariable("template_type_list", template_type_list);
	}
	
	// 新建一个模板时候。
	private void initEditTypeListNew() {
		// 获得分组标识。
		int groupId = param_util.safeGetIntParam("groupId");
		this.typeId = super.safeGetIntParam("typeId", 0);
		if (groupId == 0) {
			if (channel == null)
				groupId = theme_coll.getTemplateGroupIdByName(null);
			else
				groupId = theme_coll.getTemplateGroupIdByModuleId(channel.getModuleId());
		}
		
		// 获得该分组下的类型列表。
		Object template_type_list = theme_coll.getTemplateTypeList(groupId);
		setTemplateVariable("template_type_list", template_type_list);
	}

	/** 初始化回收站模板列表。 */
	private void initRecycleTemplateList() {
		Object template_list = theme_coll.getRecycleTemplateDataTable(this.groupId, this.channelId);
		setTemplateVariable("template_list", template_list);
	}
}
