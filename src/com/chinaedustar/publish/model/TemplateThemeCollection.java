package com.chinaedustar.publish.model;

import java.io.File;
import java.util.*;

import com.chinaedustar.publish.*;

/**
 * 模板方案的集合业务对象。
 * 
 * @author liujunxing
 *
 * 模板业务对象的层次关系：
 *   TemplateThemeCollection [模板业务对象根，通过 site.getTemplateThemes() 得到]
 *     TemplateTheme
 *       Skin
 *       PageTemplate
 *     TemplateGroup
 *       TemplateType
 *         PageTemplate
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class TemplateThemeCollection extends AbstractPublishModelBase implements PublishModelObject {
	/**
	 * 使用缺省参数构造一个 TemplateThemeCollection 的实例。
	 *
	 */
	public TemplateThemeCollection() {
		
	}
 
	/** 系统缺省模板方案，被缓存在系统里面。 */
	private TemplateTheme default_theme;
	
	// === 业务方法 ======================================================================
	
	/**
	 * 获得模板方案的列表。
	 * @return 返回 List&lt;TemplateTheme&gt;
	 */
	public List<TemplateTheme> getThemeList() {
		String hql = "FROM TemplateTheme t ORDER BY t.id ASC ";
		@SuppressWarnings("unchecked")
		List<TemplateTheme> list = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(list, pub_ctxt, this);
		return list;
	}
	
	/**
	 * 获得具有指定标识的模板方案对象。
	 * @param themeId
	 * @return 具有该标识的模板方案对象；如果没有则返回 null.
	 */
	public TemplateTheme getTemplateTheme(int themeId) {
		TemplateTheme theme = (TemplateTheme)pub_ctxt.getDao().get(TemplateTheme.class, themeId);
		if (theme != null)
			theme._init(pub_ctxt, this);
		return theme;
	}
	
	/**
	 * 创建/修改一个模板方案对象。
	 * @param theme 模板方案对象。
	 */
	public void saveTemplateTheme(TemplateTheme theme) {
		TemplateTheme defaultTheme = getDefaultTemplateTheme();
		// 如果还没有默认模板方案，则将当前加入的模版方案设为默认模板方案。
		if (defaultTheme == null) {
			theme.setIsDefault(true);
		}
		
		// 更新DB
		pub_ctxt.getDao().save(theme);
		pub_ctxt.getDao().flush();
		
		// 如果这个模板方案是缺省的，则别人不使缺省的，更新别人。
		if (theme.getIsDefault()) {
			String hql = "UPDATE TemplateTheme SET isDefault = false WHERE id != " + theme.getId();
			pub_ctxt.getDao().bulkUpdate(hql);
		}
		
		// 如果设置这个模板方案是缺省的，则设置缺省 skin.
		if (theme.getIsDefault()) {
			generateDefaultSkinCss(theme);
		}
		
		clearPageTemplateCache();
	}
	
	/**
	 * 删除一个模板方案对象。
	 * @param themeId 模板方案对象标识。
	 * @return 
	 */
	public void deleteTemplateTheme(TemplateTheme theme) {
		// 删除方案中的所有模板
		String hql = "DELETE FROM PageTemplate WHERE themeId = " + theme.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
		
		// 删除方案中的所有风格
		hql = "DELETE FROM Skin WHERE themeId = " + theme.getId();
		pub_ctxt.getDao().bulkUpdate(hql);

		// 删除方案本身。
		hql = "DELETE FROM TemplateTheme WHERE isDefault = false AND id = " + theme.getId();
		pub_ctxt.getDao().bulkUpdate(hql);

		clearPageTemplateCache();
	}
	
	/**
	 * 获得缺省模板方案对象，如果没有缺省方案则返回 null。
	 *   注意：缺省模板方案对象是缓存的。
	 * 如果更改了缺省模板方案，则必须先刷新缓存，然后再获取数据。
	 * @return 默认模板方案对象。
	 */
	public TemplateTheme getDefaultTemplateTheme() {
		if (default_theme == null){
			String hql = "FROM TemplateTheme t WHERE t.isDefault = true";
			List list = pub_ctxt.getDao().list(hql);
			if (list != null && list.size() >0){
				synchronized (this) {
					default_theme = (TemplateTheme)list.get(0);
					default_theme._init(pub_ctxt, this);
				}
			}
		}		
		return default_theme;
	}

	// === 业务方法 v2 ================================================================
	/*
	 * 准备在内存保留一份完整的 Tmpl_Type 表格的集合。
	 * 其按照 type, name 进行索引，能够通过 type, name 快速找到指定模板分组、模板类别。
	 */
	
	/**
	 * 指定模板分组的所有模板类别映射。
	 */
	private static final class TemplateTypeMap {
		/** 构造函数。 */
		public TemplateTypeMap(TemplateGroup the_group) {
			this.the_group = the_group;
		}
		
		/** 模板分组 */
		private TemplateGroup the_group;
		
		/** 按照名字索引的模板类别集合。 */
		private HashMap<String, TemplateType> type_map = new HashMap<String, TemplateType>();

		/** 获得此模板分组。 */
		public TemplateGroup getGroup() {
			return this.the_group;
		}
		/** 设置此模板分组。 */
		public void setGroup(TemplateGroup group) {
			this.the_group = group;
		}
		/** 放置一个类别的项目。 */
		public void put(String name, TemplateType ttype) {
			type_map.put(name, ttype);
		}
		/** 得到指定名字的模板类别。 */
		public TemplateType get(String name) {
			return type_map.get(name);
		}
	}
	
	/**
	 * 模板分组映射。
	 * 
	 * 模板分组当前的名字有： 
	 *   root - 网站通用模板分组。其为网站缺省模板分组。
	 *   webpage - 自定义页面模板分组。
	 *   article - 文章模块使用的模板分组。
	 *   soft - 软件下载模块使用的模板分组。
	 *   photo - 图片模块使用的模板分组。
	 * 以后可能扩展更多种类的模板分组。
	 * 
	 * 当这个对象被构造好之后，就不再发生改变。如果更新，需要重新构造一个新对象并重新装载。
	 * 
	 * 类内部的数据构成两层 HashMap，第一层是 {name, TemplateTypeMap(模板分组)} 的映射。
	 *   第二层是 {name, TemplateType(模板类别)} 的映射。
	 * 查找是先查找模板分组 然后 查找模板类别。
	 */
	private static final class TemplateTypeCache {
		private PublishContext pub_ctxt;
		
		/** 按照模板分组名字索引的 TemplateTypeMap 集合。 */
		private HashMap<String, TemplateTypeMap> group_map = new HashMap<String, TemplateTypeMap>();
		
		public TemplateTypeCache(PublishContext pub_ctxt) {
			this.pub_ctxt = pub_ctxt;
			initCache();
		}
		
		/** 初始化缓存。其装载所有分组、类别到内存中。 */
		private void initCache() {
			// 加载所有分组。
			String hql = "FROM TemplateGroup";
			@SuppressWarnings("unchecked")
			List<TemplateGroup> groups = (List<TemplateGroup>)pub_ctxt.getDao().list(hql);
			for (int i = 0; i < groups.size(); ++i)
				loadGroup(groups.get(i));
		}
		
		/** 装载指定模板分组。 */
		private void loadGroup(TemplateGroup group) {
			// 构造 type_map.
			TemplateTypeMap type_map = new TemplateTypeMap(group);
			
			// 装载该模板分组的所有模板类别。
			String hql = "FROM TemplateType t WHERE t.groupId = " + group.getId();
			@SuppressWarnings("unchecked")
			List<TemplateType> types = (List<TemplateType>)pub_ctxt.getDao().list(hql);
			for (int i = 0; i < types.size(); ++i) {
				TemplateType type = types.get(i);
				type_map.put(type.getName(), type);
			}
			
			// 将 type_map 添加到集合中。
			group_map.put(group.getName(), type_map);
		}
	
		/**
		 * 通过分组名字找到该模板分组的映射集合。
		 * @param group_name
		 * @return
		 */
		public TemplateTypeMap get(String group_name) {
			return group_map.get(group_name);
		}
	}
	
	/** 模板分组和类别在内存中的缓存。 */
	private TemplateTypeCache type_cache;
	
	private TemplateTypeCache getTypeCache() {
		if (this.type_cache == null)
			this.type_cache = new TemplateTypeCache(pub_ctxt);
		return this.type_cache;
	}
	
	/**
	 * 获得指定分组名字、指定类别(类别名字) 的 TemplateType 标识。
	 * @param group_name - 分组名字，频道使用其对应模块的名字。
	 * @param type_name - 类别名字。 
	 * @return 返回类别标识。 = 0 表示未找到。
	 */
	private int getTemplateTypeId(String group_name, String type_name) {
		// 通过模块名字(分组名字)找到分组的 TemplateTypeMap (第一级查找)
		TemplateTypeMap tt_map = getTypeCache().get(group_name);
		if (tt_map == null) return 0;
		
		// 通过模板类别名字 (template_type) 找到该类别的标识 (第二级查找)
		TemplateType t_type = tt_map.get(type_name);
		if (t_type == null) return 0;
		
		return t_type.getId();
	}
	
	
	/** 页面模板在内存的缓存。 */
	private com.chinaedustar.common.Cache page_template_cache = new com.chinaedustar.common.SimpleCache();
	
	/** 
	 * 清除页面模板缓存，当修改了模板内容、添加、删除模板之后调用此方法更新模板缓存。 
	 */
	public synchronized void clearPageTemplateCache() {
		this.default_theme = null;
		page_template_cache = new com.chinaedustar.common.SimpleCache();
	}

	// === 辅助实现 ======
	
	private int getTemplateTypeIdByGroupId(int groupId, String type_name) {
		String hql = "SELECT id FROM TemplateType WHERE groupId = " + groupId + 
			" AND name = '" + type_name.replace("'", "''") + "'";
		return PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
	}
	
	private int getTemplateTypeIdByGroupName(String group_name, String type_name) {
		int groupId = getTemplateGroupIdByName(group_name);
		if (groupId == 0) return 0;
		
		return getTemplateTypeIdByGroupId(groupId, type_name);
	}
	
	private int getTemplateTypeIdByModuleId(int moduleId, String type_name) {
		// 找到分组标识。
		int groupId = getTemplateGroupIdByModuleId(moduleId);
		if (groupId == 0) return 0;
		
		return getTemplateTypeIdByGroupId(groupId, type_name);
	}
	
	// === 获取缺省模板业务方法 =======================================================
	
	/**
	 * 获得指定标识的页面模板。通过此方法得到的页面模板仅用于显示页面，不用于管理。
	 * 此处返回的页面模板对象可能是缓存在内存中的，因而需要被更新。
	 * 
	 * 页面模板在 page_template_cache 中的缓存 key = "ptc_" + id
	 * @return 返回页面模板对象，其可能是被缓存的。
	 * 
	 * TODO: 当前缓存更新算法还没有做，暂时先关闭缓存处理。
	 */
	public PageTemplate getPageTemplateMayCache(int id) {
		// 首先尝试从缓存中查找。
		String ptc_key = "ptc_" + String.valueOf(id);
		PageTemplate result = (PageTemplate)page_template_cache.get(ptc_key);
		if (result != null)
			return result;
		
		// 从数据库中装载这个页面模板。
		result = (PageTemplate)pub_ctxt.getDao().get(PageTemplate.class, id);
		if (result == null) return null;
		result._init(pub_ctxt, null);		// 仅设置 pub_ctxt，不设置 owner_obj
		// TODO: 打开缓存处理。 page_template_cache.put(key, result);

		return result;
	}

	/**
	 * 获得指定方案下站点级指定模板类别的缺省模板页面对象。
	 * @param themeId - 方案标识。
	 * @param type_name - 类别名字。
	 * @return 返回 PageTemplate 对象。
	 */
	public PageTemplate getSitePageTemplate(int themeId, String type_name) {
		return internalGetPageTemplate(themeId, 0, TemplateGroup.ROOT_GROUP_NAME, type_name);
	}

	/**
	 * 获得指定频道下指定模板类别的缺省模板页面对象。
	 * @param themeId - 方案标识。= 0 表示使用缺省方案的。
	 * @param channelId - 频道标识。
	 * @param type_name - 模板类别名字，对应 TemplateType.name 属性。
	 * @return 返回 PageTemplate 页面模板对象，如果不存在则返回 null.
	 */
	public PageTemplate getChannelPageTemplate(int themeId, int channelId, String type_name) {
		Channel channel = this.pub_ctxt.getSite().getChannel(channelId);
		return getChannelPageTemplate(themeId, channel, type_name);
	}
	
	/**
	 * 获得指定频道下指定模板类别的缺省模板页面对象。
	 * @param themeId - 方案标识。= 0 表示使用缺省方案的。
	 * @param channel - 频道对象，为 null 表示获取 '网站通用模板' 的页面模板。(== null 未实现)
	 * @param type_name - 模板类别名字，对应 TemplateType.name 属性。
	 * @return 返回 PageTemplate 页面模板对象，如果不存在则返回 null.
	 */
	public PageTemplate getChannelPageTemplate(int themeId, Channel channel, String type_name) {
		if (channel == null || channel.getId() == 0) 
			return this.getSitePageTemplate(themeId, type_name);
		
		return internalGetPageTemplate(themeId, channel.getId(), 
				channel.getChannelModule().getItemClass().toLowerCase(),
				type_name);
	}

	/**
	 * 获得指定频道下指定模板类别的缺省模板页面对象。
	 * @param themeId - 方案标识。
	 * @param channelId - 频道标识，= 0 表示是网站的；> 0 表示是指定频道的。
	 * @param group_name - 模板分组名字，对应 TemplateGroup.name 属性。
	 * @param type_name - 模板类别名字，对应 TemplateType.name 属性。
	 * @return
	 */
	private PageTemplate internalGetPageTemplate(int themeId, int channelId, String group_name, String type_name) {
		// 0. themeId = 0 缺省参数处理。
		if (themeId == 0) themeId = this.getDefaultTemplateTheme().getId();
			
		// 1. 找到此频道下(频道使用的模块决定的)指定类别名字(type_name)的类别标识。
		// moduleName - 模块名字，同时也是模板分组的名字。
		int type_id = getTemplateTypeId(group_name, type_name);
		if (type_id == 0) return null;

		// 2. 找到当前方案下(themeId)、指定频道下(channelId)、指定类别(type_id)下缺省页面模板。
		//   先在缓存中找。
		String pt_key = "th_" + themeId + "_ch_" + channelId + "_ty_" + type_id;
		Integer pt_id = (Integer)page_template_cache.get(pt_key);
		PageTemplate result = null;
		if (pt_id != null) {
			String ptc_key = "ptc_" + String.valueOf(pt_id);
			result = (PageTemplate)page_template_cache.get(ptc_key);
			if (result != null) return result;
		}
		
		// 3. 从数据库中装载。条件：指定方案+频道+类别+缺省，正常情况下满足条件的有且只有一个。
		String hql = "FROM PageTemplate " +
					" WHERE themeId = " + themeId + " AND channelId = " + channelId + 
					"  AND typeId = " + type_id + " AND isDefault = true";
		result = (PageTemplate)PublishUtil.executeSingleObjectQuery(pub_ctxt.getDao(), hql);
		if (result != null) {
			result._init(pub_ctxt, null);
			// TODO: put to cache
		}
		
		return result;
	}

	// === 获取可用模板集合业务方法 ===================================================
	
	/**
	 * 获得指定方案下指定频道的所有可用模板列表。返回较少的数据。
	 */
	public DataTable getAvailableTemplateDataTable(int themeId, int channelId) {
		// 如果 themeId == 0 则使用缺省模板方案。
		if (themeId == 0) themeId = this.getDefaultTemplateTheme().getId();
		
		// 根据 themeId, channelId, type_id 找所有可用模板。
		String select_fields = "id, name, isDefault";
		String hql = "SELECT " + select_fields +
					" FROM PageTemplate " +
					" WHERE themeId = " + themeId + " AND channelId = " + channelId + 
						" AND deleted = false " +
					" ORDER BY id ASC ";
		List list = pub_ctxt.getDao().list(hql);
		
		// 构造 DataTable 并返回。
		DataTable data_table = new DataTable(PublishUtil.columnsToSchema(select_fields));
		PublishUtil.addToDataTable(list, data_table);
		
		return data_table;
	}
	
	/**
	 * 获得指定方案下指定频道的指定模板类别的所有可用模板列表。返回较少的数据，主要用于支持
	 *  页面上面选择可用模板时候的下拉列表框。
	 * <p>
	 * 使用例子：
	 *  获得网站通用模板中 '搜索页' 所有可用模板。 
	 *  <code> getAvailableTemplateDataTable(0, 0, "search"); </code>
	 * </p>
	 * @param themeId - 模板方案标识，如果 = 0 表示使用缺省模板方案。
	 * @param chanenlId - 频道标识，如果 = 0 表示网站通用模板。
	 * @param type_name - 模板类别名字，对应 TemplateType.name 属性。
	 * 
	 * @return 返回一个 DataTable, 其 schema 为 [id, name, isDefault].
	 */
	public DataTable getAvailableTemplateDataTable(int themeId, int channelId, String type_name) {
		// 如果 themeId == 0 则使用缺省模板方案。
		if (themeId == 0) themeId = this.getDefaultTemplateTheme().getId();

		// 如果 channelId == 0，表示使用网站通用模板分组；否则为指定频道的模板分组。
		// 找到此频道下(频道使用的模块决定的)指定类别名字(type_name)的类别标识。
		String group_name = "root";			// 网站通用模板分组名。
		int type_id = 0;
		if (channelId != 0) {
			Channel channel = pub_ctxt.getSite().getChannel(channelId);
			if (channel == null) return null;
			// 根据模块标识找。
			type_id = getTemplateTypeIdByModuleId(channel.getModuleId(), type_name);
		} else {
			// 根据分组名字找。
			type_id = getTemplateTypeIdByGroupName(group_name, type_name);
		}
		if (type_id == 0) return null;

		// 根据 themeId, channelId, type_id 找所有可用模板。
		String select_fields = "id, name, isDefault";
		String hql = "SELECT " + select_fields +
					" FROM PageTemplate " +
					" WHERE themeId = " + themeId + " AND channelId = " + channelId + 
						" AND typeId = " + type_id +
						" AND deleted = false " +
					" ORDER BY id ASC ";
		List list = pub_ctxt.getDao().list(hql);
		
		// 构造 DataTable 并返回。
		DataTable data_table = new DataTable(PublishUtil.columnsToSchema(select_fields));
		PublishUtil.addToDataTable(list, data_table);
		
		return data_table;
	}

	/**
	 * 获得指定方案下所有可用皮肤列表。这个函数返回较少的数据，主要用于支持
	 *   页面上面选择可用皮肤时候的下拉列表框。
	 * @param themeId - 模板方案标识，如果 = 0 表示使用缺省模板方案。
	 * @return 返回一个 DataTable, 其 schema 为 [id, name, isDefault].
	 */
	public DataTable getAvailableSkinDataTable(int themeId) {
		// 如果 themeId == 0 则使用缺省模板方案。
		if (themeId == 0) themeId = this.getDefaultTemplateTheme().getId();
		
		String select_fields = "id, name, isDefault";
		String hql = "SELECT " + select_fields + 
					" FROM Skin " +
					" WHERE themeId = " + themeId +
					" ORDER BY id ASC";
		List list = pub_ctxt.getDao().list(hql);

		// 构造 DataTable 并返回。
		DataTable data_table = new DataTable(PublishUtil.columnsToSchema(select_fields));
		PublishUtil.addToDataTable(list, data_table);
		
		return data_table;
	}
	
	// === 业务方法 v 2007-8-17 ===============================================
	
	/**
	 * 通过分组名得到分组标识。
	 * @param group_name - 分组名字，如果 == null 则查找 'root' 网站通用模板分组。
	 */
	public int getTemplateGroupIdByName(String group_name) {
		if (group_name == null || group_name.length() == 0) group_name = "root";
		String hql = "SELECT id FROM TemplateGroup WHERE name = '" +
		group_name.replace("'", "''") + "'";
		int groupId = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		return groupId;
	}
	
	/**
	 * 通过频道所使用的模块标识获得该频道使用的模板分组标识。
	 * @param moduleId - 模块标识。
	 * @return
	 */
	public int getTemplateGroupIdByModuleId(int moduleId) {
		String hql = "SELECT id FROM TemplateGroup WHERE moduleId = " + moduleId;
		int groupId = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		return groupId;
	}
	
	/**
	 * 通过指定的类型标识找到其所属分组标识。
	 * @param typeId - 类别标识。
	 * @return
	 */
	public int getTemplateGroupIdByTypeId(int typeId) {
		String hql = "SELECT groupId FROM TemplateType WHERE id = " + typeId;
		int groupId = PublishUtil.executeIntScalar(pub_ctxt.getDao(), hql);
		return groupId;
	}
	
	/**
	 * 得到所有模板分组，含禁用的。
	 * @return List&lt;TemplateGroup&gt;
	 */
	public List<TemplateGroup> getTemplateGroupList() {
		String hql = "FROM TemplateGroup ORDER BY id ASC ";
		@SuppressWarnings("unchecked")
		List<TemplateGroup> group_list = (List<TemplateGroup>)pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(group_list, pub_ctxt, this);
		return group_list;
	}

	/**
	 * 获得指定分组的模板类型集合。
	 * @return
	 */
	public List<TemplateType> getTemplateTypeList(int groupId) {
		String hql = "FROM TemplateType t WHERE t.groupId = " + groupId +
			" ORDER BY groupOrder ASC, id ASC ";
		@SuppressWarnings("unchecked")
		List<TemplateType> type_list = (List<TemplateType>)pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(type_list, pub_ctxt, this);
		return type_list;
	}
	
	/**
	 * 得到系统模板分组，系统模板分组指非频道分组、非禁用的。
	 * @return 返回 List&lt;TemplateGroup&gt; 的集合。
	 */
	public List<TemplateGroup> getSystemTemplateGroupList() {
		String hql = "FROM TemplateGroup WHERE isSystem = true AND disabled = false ORDER BY id ASC";
		@SuppressWarnings("unchecked")
		List<TemplateGroup> group_list = (List<TemplateGroup>)pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(group_list, pub_ctxt, this);
		return group_list;
	}
	
	/**
	 * 得到所有频道的模板分组。
	 * @return 返回 List&lt;TemplateGroup&gt; 的集合。
	 * 注意：尽管返回的 TemplateGroup 进行了模型化，但其不要用于 save, delete 等数据库操作。
	 */
	public List<TemplateGroup> getChannelTemplateGroupList() {
		// 1. 查询出所有频道->模块->分组。
		String hql = "SELECT g.id, g.objectUuid, g.name, c.name, g.moduleId, g.isSystem, g.disabled, c.id " +
				" FROM TemplateGroup g, Channel c " +
				" WHERE g.moduleId = c.moduleId AND g.disabled = false AND g.isSystem = false AND c.status = 0 " +
				" ORDER BY c.channelOrder ASC ";
		List list = pub_ctxt.getDao().list(hql);
		
		// 2. 根据这些信息组装 TemplateGroup
		List<TemplateGroup> channel_group_list = new java.util.ArrayList<TemplateGroup>();
		for (int i = 0; i < list.size(); ++i) {
			Object[] data = (Object[])list.get(i);
			TemplateGroup group = new TemplateGroup();
			group.setId((Integer)data[0]);			// g.id
			group.setObjectUuid((String)data[1]);	// g.objectUuid
			group.setName((String)data[2]);			// g.name
			group.setTitle((String)data[3] + "模板");// c.name
			group.setModuleId((Integer)data[4]);	// g.moduleId
			group.setIsSystem(false);				// g.isSystem always false
			group.setDisabled(false);				// g.disabled always false
			group.setChannelId((Integer)data[7]);	// c.id
			channel_group_list.add(group);
		}
		
		PublishUtil.initModelList(channel_group_list, pub_ctxt, this);
		return channel_group_list;
	}
	
	/**
	 * 得到指定类型下的模板数据表。
	 * @param themeId - 方案标识。
	 * @param channelId - 频道，== 0 表示获取通用的。
	 * @param typeId - 模板类型，typeId == 0 表示获取所有类型的。
	 * @return 返回一个 DataTable，其 schema = {id, name, typeId, isDefault, typeName}
	 */
	public DataTable getManageTemplateDataTable(int themeId, int channelId, int typeId) {
		// 查询符合条件的模板。
		String hql = "SELECT p.id, p.name, p.typeId, p.isDefault, t.title " +
			" FROM PageTemplate p, TemplateType t " +
			" WHERE p.typeId = t.id AND p.deleted = false AND p.themeId = " + themeId + 
			"   AND p.channelId = " + channelId;
		if (typeId > 0)
			hql += " AND p.typeId = " + typeId;
		List list = pub_ctxt.getDao().list(hql);
		
		// 构造数据返回。
		DataSchema schema = new DataSchema("id, name, typeId, isDefault, typeName");
		DataTable data_table = new DataTable(schema);
		PublishUtil.addToDataTable(list, data_table);
		return data_table;
	}
	
	/**
	 * 得到指定方案中指定频道和类型分组的所有模板。
	 * @param themeId
	 * @param channelId
	 * @param groupId
	 * @return
	 */
	public DataTable getManageTemplateDataTable_GroupId(int themeId, int channelId, int groupId) {
		String hql = "SELECT p.id, p.name, p.typeId, p.isDefault, t.title " +
			" FROM PageTemplate p, TemplateType t " +
			" WHERE p.typeId = t.id AND p.deleted = false AND p.themeId = " + themeId + 
			"   AND p.channelId = " + channelId +
			"   AND t.groupId = " + groupId + 
			" ORDER BY t.id, p.id ";
		
		List list = pub_ctxt.getDao().list(hql);
		
		// 构造数据返回。
		DataSchema schema = new DataSchema("id, name, typeId, isDefault, typeName");
		DataTable data_table = new DataTable(schema);
		PublishUtil.addToDataTable(list, data_table);
		return data_table;
	}
	
	/**
	 * 得到指定频道下的回收站中的模板列表。
	 * @param groupId - 模板分组标识。
	 * @param channelId - 频道标识。
	 */
	public DataTable getRecycleTemplateDataTable(int groupId, int channelId) {
		// 1. 查询出该类别中有哪些模板分类。
		List<Integer> typeIds = getTypeIdArray(groupId);
		
		// 2. 查询出该频道具有这些分类的模板列表。
		String hql = "SELECT p.id, p.name, p.typeId, p.isDefault, t.title " +
			" FROM PageTemplate p, TemplateType t " +
			" WHERE p.typeId = t.id AND p.deleted = true AND p.channelId = " + channelId;
		hql += " AND p.typeId IN (" + PublishUtil.toSqlInString(typeIds) + ")";
		List list = pub_ctxt.getDao().list(hql);
		
		// 构造数据返回。
		DataSchema schema = new DataSchema("id, name, typeId, isDefault, typeName");
		DataTable data_table = new DataTable(schema);
		PublishUtil.addToDataTable(list, data_table);
		return data_table;
	}
	
	/**
	 * 获得指定方案下的 skin 列表。
	 * @param themeId
	 * @return
	 */
	public DataTable getManageSkinDataTable(int themeId) {
		String hql = "SELECT id, themeId, name, isDefault FROM Skin WHERE themeId = " + themeId;
		List result = pub_ctxt.getDao().list(hql);
		
		DataTable data_table = new DataTable(new DataSchema("id, themeId, name, isDefault"));
		PublishUtil.addToDataTable(result, data_table);
		return data_table;
	}
	
	/**
	 * 得到指定模板分组中含有哪些模板类别。
	 * @param groupId - 模板分组标识。
	 * @return
	 */
	private List<Integer> getTypeIdArray(int groupId) {
		String hql = "SELECT id FROM TemplateType WHERE groupId = " + groupId;
		@SuppressWarnings("unchecked")
		List<Integer> result = pub_ctxt.getDao().list(hql);
		return result;
	}
	
	// === 模板回收站 业务 =========================================================== 
	
	/**
	 * 删除一组指定标识的模板，这些模板不包括 isDefault 的。
	 *  删除模板仅是将模板标记为删除状态的，并不真实删除。
	 * @param ids
	 * @return
	 */
	public int deleteTemplates(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return 0;
		
		String hql = "UPDATE PageTemplate SET deleted = true " +
			"WHERE isDefault = false AND deleted = false AND id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int num = pub_ctxt.getDao().bulkUpdate(hql);
			
		// 更新缓存。
		clearPageTemplateCache();
		
		return num;
	}
	
	/**
	 * 复制一个页面模板，复制出来的模板具有相同的 themeId, channelId, typeId.
	 *   isDefault 设置为 false, 名字为原模板名字 + 副本1(数字)
	 * @param src_tmpl
	 * @return
	 */
	public PageTemplate copyTemplate(PageTemplate src_tmpl) {
		// 找一个还没有使用过的名字。
		String new_name = getCopiedTemplateName(src_tmpl.getName());
		
		// 构造新对象并插入。
		PageTemplate new_template = new PageTemplate();
		new_template.setName(new_name);
		new_template.setThemeId(src_tmpl.getThemeId());
		new_template.setChannelId(src_tmpl.getChannelId());
		new_template.setTypeId(src_tmpl.getTypeId());
		new_template.setContent(src_tmpl.getContent());
		new_template.setThemeDefault(false);
		new_template.setIsDefault(false);
		new_template.setDeleted(false);
		
		pub_ctxt.getDao().insert(new_template);
		new_template._init(pub_ctxt, this);
		
		this.clearPageTemplateCache();
		
		return new_template;
	}
	
	/**
	 * 频道间复制模板。
	 * @param template_ids
	 * @param target_channel_ids
	 */
	public void copyChannelTemplate(List<Integer> template_ids, List<Integer> target_channel_ids) {
		if (template_ids == null || template_ids.size() == 0) return;
		if (target_channel_ids == null || target_channel_ids.size() == 0) return;
		
		// 1. 得到所有源模板。
		String hql = "FROM PageTemplate WHERE id IN (" + PublishUtil.toSqlInString(template_ids) + ")";
		@SuppressWarnings("unchecked")
		List<PageTemplate> template_list = pub_ctxt.getDao().list(hql);
		if (template_list == null || template_list.size() == 0) return;
		
		// 2. 得到所有目标频道。
		hql = "FROM Channel WHERE id IN (" + PublishUtil.toSqlInString(target_channel_ids) + ")";
		@SuppressWarnings("unchecked")
		List<Channel> channel_list = pub_ctxt.getDao().list(hql);
		if (channel_list == null || channel_list.size() == 0) return;
	
		// 循环每个频道进行复制。
		for (int i = 0; i < channel_list.size(); ++i) {
			copyChannelTemplate(template_list, channel_list.get(i));
		}
		
		// 刷新缓存返回。
		pub_ctxt.getDao().flush();
		this.clearPageTemplateCache();
	}
	
	// 内部复制一组模板到指定频道。
	private final void copyChannelTemplate(List<PageTemplate> template_list, Channel channel) {
		for (int i = 0; i < template_list.size(); ++i) {
			PageTemplate template = template_list.get(i);
			// 本频道的不复制。
			if (template.getChannelId() == channel.getId()) continue;
			
			// 构造新对象并插入。
			PageTemplate new_template = new PageTemplate();
			new_template.setName(template.getName());
			new_template.setThemeId(template.getThemeId());
			new_template.setChannelId(channel.getChannelId());
			new_template.setTypeId(template.getTypeId());
			new_template.setContent(template.getContent());
			new_template.setThemeDefault(false);
			new_template.setIsDefault(false);
			new_template.setDeleted(false);
			
			pub_ctxt.getDao().insert(new_template);
		}
	}
	
	// 找一个还没有使用过的名字。
	private String getCopiedTemplateName(String origin_name) {
		for (int i = 0; i < 100; ++i) {
			String new_name = origin_name + "副本" + i;
			String hql = "SELECT COUNT(*) FROM PageTemplate WHERE name = :name";
			List list = pub_ctxt.getDao().queryByNamedParam(hql, new String[] {"name"}, new String[] {new_name});
			if (PublishUtil.safeGetLongVal(list.get(0)) == 0) return new_name;
		}
		return origin_name + "副本 xxx"; 
	}

	/**
	 * 批量恢复一组指定标识的模板。 restore 是 delete 的反操作。
	 * @param ids
	 */
	public int restoreTemplates(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return 0;
		
		// 设置这些被恢复的模板，但去掉缺省标志。
		String hql = "UPDATE PageTemplate SET deleted = false, isDefault = false " +
			"WHERE deleted = true AND id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int num = pub_ctxt.getDao().bulkUpdate(hql);
		
		// 更新缓存。
		clearPageTemplateCache();
	
		return num;
	}
	
	/**
	 * 实际删除一组指定标识的模板。 其真实从数据表中删除记录不再可以恢复了。
	 * @param ids
	 * @return
	 */
	public int realDeleteTemplates(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return 0;
		
		// 实际删除。
		String hql = "DELETE FROM PageTemplate " +
			"WHERE deleted = true AND id IN (" + PublishUtil.toSqlInString(ids) + ")";
		int num = pub_ctxt.getDao().bulkUpdate(hql);
		
		// 更新缓存。
		clearPageTemplateCache();
		
		return num;
	}

	// === PageTemplate 有关业务 ====================================================
	
	/**
	 * 加载指定标识的页面模板。
	 * @param templateId
	 * @return
	 */
	public PageTemplate loadPageTemplate(int templateId) {
		PageTemplate template = (PageTemplate)pub_ctxt.getDao().get(PageTemplate.class, templateId);
		if (template != null)
			template._init(pub_ctxt, this);
		return template;
	}
	
	/**
	 * 设置指定的模板为该类的缺省模板。
	 * @param template - 页面模板对象，用户要保证该模板存在。
	 */
	public boolean setDefaultTemplate(PageTemplate template) {
		if (template == null || template.getId() == 0 || template.getDeleted()) return false;
		
		// 设置自己在当前 theme, channel, type 里面是 default 的。
		String hql = "UPDATE PageTemplate SET isDefault = true " +
			" WHERE deleted = false AND id = " + template.getId();
		int update_num = pub_ctxt.getDao().bulkUpdate(hql);
		if (update_num == 0) return false;		// ?? 不存在或有什么错误。
		
		// 自己设置为缺省的，则设置'同方案同频道同类别'的其他模板为非 default 的。
		hql = "UPDATE PageTemplate SET isDefault = false " +
			" WHERE themeId = " + template.getThemeId() + 
			"   AND channelId = " + template.getChannelId() +
			"   AND typeId = " + template.getTypeId() +
			"   AND id <> " + template.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
		
		// 更新缓存。
		clearPageTemplateCache();
		return true;
	}

	// === Skin 有关业务 ============================================================

	/**
	 * 加载指定标识的风格对象。
	 */
	public Skin loadSkin(int skinId) {
		Skin skin = (Skin)pub_ctxt.getDao().get(Skin.class, skinId);
		if (skin != null)
			skin._init(pub_ctxt, this);
		return skin;
	}
	
	/**
	 * 创建/修改一个风格。
	 * @param skin
	 */
	public void saveSkin(Skin skin) {
		// TODO: 如果没有缺省风格，则设置一个。
		if (skin.getIsDefault() == true) {
			// skin.setIsDefault(true);
		}
		
    	skin._init(pub_ctxt, this);
    	pub_ctxt.getDao().save(skin);

		generateCssFile(skin, false);
	}

	/**
	 * 设置为该模板方案中的缺省风格。
	 * @param skin
	 */
	public void setDefaultSkin(Skin skin) {
		// 得到其所属模板方案。
		TemplateTheme theme = getTemplateTheme(skin.getThemeId());
		if (theme == null) throw new PublishException("未知模板方案");
		
		// 设置方案中自己是缺省的，其它不是缺省的。
		String hql = "UPDATE Skin SET isDefault = false WHERE themeId = " + skin.getThemeId() + 
			" AND id <> " + skin.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
		hql = "UPDATE Skin SET isDefault = true WHERE id = " + skin.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
		
		// 如果模板方案是缺省的，则 DefaultSkin.css 为自己。
		if (theme.getIsDefault())
			generateCssFile(skin, true);
	}
	
    /**
     * 生成Css文件
     * @param skin
     * @param fileName
     */
    private void generateCssFile(Skin skin, boolean set_default) {
    	String fileName = pub_ctxt.getRootDir() + "\\Skin\\Skin" + skin.getId() + ".css";
    	try {
	    	PublishUtil.writeTextFile(fileName, skin.getSkinCss(), pub_ctxt.getSite().getCharset());
    	} catch (java.io.IOException ex) {
    		throw new PublishException("不能更新 CSS 文件：" + fileName, ex);
    	}
    	
    	if (set_default == false) return;
    	fileName = pub_ctxt.getRootDir() + "\\Skin\\DefaultSkin.css";
    	try {
	    	PublishUtil.writeTextFile(fileName, skin.getSkinCss(), pub_ctxt.getSite().getCharset());
    	} catch (java.io.IOException ex) {
    		throw new PublishException("不能更新 CSS 文件：" + fileName, ex);
    	}
    }

    private void generateDefaultSkinCss(TemplateTheme theme) {
    	// 得到缺省 Skin
    	String hql = " FROM Skin WHERE isDefault = true AND themeId = " + theme.getId();
    	Skin skin = (Skin)PublishUtil.executeSingleObjectQuery(pub_ctxt.getDao(), hql);
    	if (skin == null) return;
    	skin._init(pub_ctxt, this);
    	
    	generateCssFile(skin, true);
    }

    /**
     * 删除一组风格。
     * @param skinIds
     */
    public void deleteSkin(List<Integer> skinIds) {
    	String hql;
    	// 将原来使用此CSS文件的项目改为系统默认的Skin文件
    	hql = "UPDATE Item SET skinId = 0 WHERE skinId IN (" + PublishUtil.toSqlInString(skinIds) + ")";
    	pub_ctxt.getDao().bulkUpdate(hql);
 
    	hql = "UPDATE Channel SET skinId = 0 WHERE skinId IN (" + PublishUtil.toSqlInString(skinIds) + ")";
    	pub_ctxt.getDao().bulkUpdate(hql);

    	hql = "UPDATE Column SET skinId = 0 WHERE skinId IN (" + PublishUtil.toSqlInString(skinIds) + ")";
    	pub_ctxt.getDao().bulkUpdate(hql);

    	hql = "UPDATE Special SET skinId = 0 WHERE skinId IN (" + PublishUtil.toSqlInString(skinIds) + ")";
    	pub_ctxt.getDao().bulkUpdate(hql);

    	
    	hql = "DELETE FROM Skin WHERE id IN (" + PublishUtil.toSqlInString(skinIds) + ")";
    	pub_ctxt.getDao().bulkUpdate(hql);
    	
    	// 删除对应的CSS文件
    	for (int i = 0; i < skinIds.size(); i++) {
    		new File(pub_ctxt.getRootDir() + "\\Skin\\Skin" + skinIds.get(i) + ".css").delete();
    	}
    }
    
	/**
	 * 刷新所有的Css文件
	 *
	 */
	public void refreshAllCssFile() {
		TemplateTheme default_theme = this.getDefaultTemplateTheme();
		int default_theme_id = default_theme == null ? 0 : default_theme.getId();
		String hql = "FROM Skin";
		@SuppressWarnings("unchecked")
		List<Skin> list = pub_ctxt.getDao().list(hql);
		if (list == null || list.size() == 0) return;
		
		for (int i = 0; i < list.size(); i++) {
			Skin skin = list.get(i);
			// 如果方案是缺省的，和风格是缺省的，则同时生成 DefaultSkin.css
			if (skin.getIsDefault() && skin.getThemeId() == default_theme_id) {
				generateCssFile(skin, true);
			} else {
				generateCssFile(skin, false);
			}
		}
	}

	/**
	 * 删除指定频道的所有模板相关信息。
	 * @param dao
	 * @param channelId
	 */
	public static final void deleteChannelTemplates(DataAccessObject dao, int channelId) {
		String hql = "DELETE FROM PageTemplate WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}

}
