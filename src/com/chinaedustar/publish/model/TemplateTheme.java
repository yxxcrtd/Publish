package com.chinaedustar.publish.model;

import java.util.List;
import com.chinaedustar.publish.*;

/**
 * 模板方案对象。
 * 
 * @author wangyi
 */
public class TemplateTheme extends AbstractNamedModelBase {
		
	/** 此模板方案的详细描述。 */
	private String description;
	
	/** 是否是缺省模板方案。 */
	private boolean isDefault;
	
	/**
	 * 缺省构造函数。
	 *
	 */
	public TemplateTheme() {
	}

	// === getter, setter =========================================================
	
	/** 此模板方案的详细描述。 */
	public String getDescription() {
		return this.description;
	}
	
	/** 此模板方案的详细描述。 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 是否是缺省模板方案。 */
	public boolean getIsDefault() {
		return this.isDefault;
	}

	/** 是否是缺省模板方案。 */
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	// === 业务方法 v2 ===========================================================
	
	/**
	 * 获得指定方案下站点级指定模板类别的缺省模板页面对象。
	 * @param themeId - 方案标识。
	 * @param type_name - 类别名字。
	 * @return
	 */
	public PageTemplate getSitePageTemplate(String type_name) {
		return getThemes().getSitePageTemplate(this.getId(), type_name);
	}

	/**
	 * 获得指定频道下指定模板类别的缺省模板页面对象。
	 * @param themeId - 方案标识。
	 * @param channel - 频道对象，为 null 表示获取 '网站通用模板' 的页面模板。
	 * @param type_name - 模板类别名字，对应 TemplateType.name 属性。
	 * @return
	 */
	public PageTemplate getChannelPageTemplate(Channel channel, String type_name) {
		return getThemes().getChannelPageTemplate(this.getId(), channel, type_name);
	}
	
	/** 获得集合对象。 */
	private TemplateThemeCollection getThemes() {
		if (owner_obj instanceof TemplateThemeCollection)
			return (TemplateThemeCollection)owner_obj;
		return pub_ctxt.getSite().getTemplateThemeCollection();
	}

	// === 管理业务方法 =============================================================
	
	/**
	 * 得到指定类型分组下所有模板列表数据。
	 * @param channel - 频道，== 0 表示获取通用的。
	 * @param group - 类型分组。
	 */
	public DataTable getTemplateDataTable(Channel channel, TemplateGroup group) {
		return getThemes().getManageTemplateDataTable_GroupId(this.getId(), 
				channel == null ? 0 : channel.getId(), 
				group == null ? 1 : group.getId());
	}
	
	/**
	 * 得到指定类型下的模板数据表。
	 * @param channel - 频道，== 0 表示获取通用的。
	 * @param type - 模板类型，typeId == 0 表示获取所有类型的。
	 * @return 返回一个 DataTable，其 schema = {id, name, typeId, isDefault, typeName}
	 */
	public DataTable getTemplateDataTable(Channel channel, TemplateType type) {
		return getTemplateDataTable((channel == null) ? 0 : channel.getId(),
				(type == null) ? 0 : type.getId());
	}
	
	/**
	 * 得到指定频道下的回收站中的模板列表。
	 * @param groupId - 分组标识。
	 * @param channelId - 频道标识。
	 * @return
	 */
	public DataTable getRecycleTemplateDataTable(int groupId, int channelId) {
		return getThemes().getRecycleTemplateDataTable(groupId, channelId);
	}
	
	/**
	 * 得到指定类型下的模板数据表。
	 * @param channelId - 频道，== 0 表示获取通用的。
	 * @param typeId - 模板类型，typeId == 0 表示获取所有类型的。
	 * @return 返回一个 DataTable，其 schema = {id, name, typeId, isDefault, typeName}
	 */
	public DataTable getTemplateDataTable(int channelId, int typeId) {
		return getThemes().getManageTemplateDataTable(this.getId(), channelId, typeId);
	}

	/**
	 * 得到指定模板方案下的风格列表。
	 * @return
	 */
	public DataTable getSkinDataTable() {
		return getThemes().getManageSkinDataTable(this.getId());
	}
	
	/**
	 * 加载指定标识的模板。
	 * @param templateId - 模板标识。 
	 * @return
	 */
	public PageTemplate loadTemplate(int templateId) {
		PageTemplate template = getThemes().loadPageTemplate(templateId);
		return template;
	}
	
	/**
	 * 加载指定标识的风格。
	 * @param skinId
	 * @return
	 */
	public Skin loadSkin(int skinId) {
		return getThemes().loadSkin(skinId);
	}

	/**
	 * 新建/更新一个模板信息。
	 * @param pageTemplate
	 */
	public void savePageTemplate(PageTemplate pageTemplate) {
		// 保存数据。
		pub_ctxt.getDao().save(pageTemplate);
		
		// TODO: 根据设置的 isDefault 可能要清除别的模板的 isDefault 标志。
		
		// 更新缓存。
		getThemes().clearPageTemplateCache();
	}

	/**
	 * 批量复制或移动一组模板到目标模板方案。
	 * (自己是源模板方案)
	 * @param destin_theme - 目标模板方案。
	 * @param template_ids - 要复制/移动的模板标识。
	 * @param copy - true 表示复制，false 表示移动。
	 */
	public Result batchCopyMoveTemplate(TemplateTheme destin_theme, List<Integer> template_ids, boolean copy) {
		if (this.getId() == destin_theme.getId())
			throw new PublishException("目标方案不能和源方案相同。");
		
		Result result = new Result();
		// 读取所有指定的模板方案
		String hql = "FROM PageTemplate WHERE themeId = " + this.getId() + 
			" AND id IN (" + PublishUtil.toSqlInString(template_ids) + ")";
		@SuppressWarnings("unchecked")
		List<PageTemplate> template_list = pub_ctxt.getDao().list(hql);
		if (template_list == null || template_list.size() == 0) {
			result.addMessage("没有找到任何需要移动/复制的模板");
			return result;
		}
		
		if (copy) 
			return internalCopyTemplate(template_list, destin_theme, result);
		else
			return internalMoveTemplate(template_list, destin_theme, result);
	}
	
	// 内部批量移动模板
	private Result internalMoveTemplate(List<PageTemplate> template_list, 
			TemplateTheme destin_theme, Result result) {
		// 循环复制/移动所有模板。
		for (int i = 0; i < template_list.size(); ++i) {
			PageTemplate template = template_list.get(i);
			// 移动
			if (template.getIsDefault() || template.getThemeDefault()) {
				result.addMessage("模板 '" + template.getName() + "' 是缺省的，不能移动该模板。");
			} else {
				String move_hql = "UPDATE PageTemplate SET themeId = " + destin_theme.getId() +
					" WHERE id = " + template.getId();
				pub_ctxt.getDao().bulkUpdate(move_hql);
				result.addMessage("模板 '<font color='blue'>" + template.getName() + 
						"</font>' 成功移动到了 '" + destin_theme.getName() + "'。");
			}
		}
		
		if (result.getMessages().size() == 0) {
			result.addMessage("由于所选择的所有模板都是缺省的，从而没有任何模板移动。");
		}
		return result;
	}
	
	// 内部批量复制模板
	private Result internalCopyTemplate(List<PageTemplate> template_list, 
			TemplateTheme destin_theme, Result result) {
		// 循环复制/移动所有模板。
		for (int i = 0; i < template_list.size(); ++i) {
			PageTemplate template = template_list.get(i);
			// 复制
			PageTemplate new_template = new PageTemplate();
			
			new_template.setName(template.getName());
			new_template.setThemeId(destin_theme.getId());
			new_template.setChannelId(template.getChannelId());
			new_template.setTypeId(template.getTypeId());
			new_template.setContent(template.getContent());
			new_template.setThemeDefault(false);
			new_template.setIsDefault(false);
			new_template.setDeleted(false);
			
			pub_ctxt.getDao().insert(new_template);
			
			result.addMessage("模板 '<font color='blue'>" + template.getName() + "</font>' 成功复制到了 '" + destin_theme.getName() + "'。");
		}
		return result;
	}
}
