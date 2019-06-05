package com.chinaedustar.publish.action;

import java.util.List;
import com.chinaedustar.publish.model.*;

/**
 * 模板管理的操作。
 * 
 * @author liujunxing
 *
 */
public class TemplateAction extends AbstractActionEx {
	// 支持的命令：'save', 'set_default', 'delete' ...
	static {
		registerCommand(TemplateAction.class, new ActionCommand("save"));
		registerCommand(TemplateAction.class, new ActionCommand("set_default"));
		registerCommand(TemplateAction.class, new ActionCommand("delete"));
		registerCommand(TemplateAction.class, new ActionCommand("copy"));
		registerCommand(TemplateAction.class, new ActionCommand("batch_delete"));
		registerCommand(TemplateAction.class, new ActionCommand("recover"));
		registerCommand(TemplateAction.class, new ActionCommand("destroy"));
		registerCommand(TemplateAction.class, new ActionCommand("batch_destroy"));
		registerCommand(TemplateAction.class, new ActionCommand("batch_clear"));
		registerCommand(TemplateAction.class, new ActionCommand("refresh"));
		registerCommand(TemplateAction.class, new ActionCommand("channel_template_copy"));
	}
	
	/** 模板方案。 */
	private TemplateTheme theme;
	
	/**
	 * 保存一个模板。
	 *
	 */
	protected ActionResult save() {
		// 获得当前模板方案.
		if (getThemeData() == false) return INVALID_PARAM;
		
		// 收集模板数据.
		PageTemplate template = collectPageTemplate();
		if (template == null) {
			messages.add("无法找到指定标识的网页模板，请确定您是从有效链接进入的。");
			// links.add(getBackActionLink());
			return INVALID_PARAM;
		}
		
		// 保存。
		tx_proxy.savePageTemplate(theme, template);
		
		// 设置信息。
		messages.add("添加/修改模板 '" + template.getName() + "' 成功完成。");
		// links.add(getBackActionLink());
		return SUCCESS;
	}

	/**
	 * 设置指定模板为缺省模板。
	 *
	 */
	protected ActionResult set_default() {
		// 获得参数。
		int templateId = param_util.safeGetIntParam("templateId");
		
		// 加载此页面模板。
		PageTemplate template = site.getTemplateThemeCollection().loadPageTemplate(templateId);
		if (template == null) {
			messages.add("无法找到标识为 '" + templateId + "' 的页面模板。");
			// links.add(getBackActionLink());
			return INVALID_PARAM;
		}
		if (template.getDeleted()) {
			messages.add("标识为 '" + template.getId() + "'(" + template.getName() + ") 的模板已经删除了，不能将其设置为缺省模板。");
			// links.add(getBackActionLink());
			return INVALID_PARAM;
		}
		
		// 执行设置缺省操作。
		tx_proxy.setDefaultTemplate(site.getTemplateThemeCollection(), template);
		
		// 提示信息。
		messages.add("模板 '" + template.getName() + "' 已经设置为其所在方案和类别的缺省模板。");
		// links.add(getBackActionLink());
		return SUCCESS;
	}
	
	/**
	 * 批量删除模板。
	 *
	 */
	protected ActionResult delete() {
		// 获得要删除的模板标识。
		List<Integer> ids = param_util.safeGetIntValues("templateId");
		if (ids == null || ids.size() == 0) {
			super.noIdsParam(); 
			return INVALID_PARAM;
		}
		
		// 执行删除。
		int delete_num = tx_proxy.deletePageTemplate(site.getTemplateThemeCollection(), ids);
		
		// 显示信息。
		messages.add("批量删除模板成功完成，共删除了 " + delete_num + " 个模板。");
		// links.add(getBackActionLink());
		return SUCCESS;
	}
	
	/**
	 * 复制一个模板。
	 */
	protected ActionResult copy() {
		// 获得参数。
		int templateId = param_util.safeGetIntParam("templateId");
		
		// 加载此页面模板。
		PageTemplate template = site.getTemplateThemeCollection().loadPageTemplate(templateId);
		if (template == null) {
			messages.add("无法找到标识为 '" + templateId + "' 的页面模板。");
			// links.add(getBackActionLink());
			return INVALID_PARAM;
		}

		// 执行复制操作。
		PageTemplate new_template = tx_proxy.copyPageTemplate(site.getTemplateThemeCollection(), template);
		
		// 提示信息。
		messages.add("模板复制成功，新模板的标识为 " + new_template.getId() + ", 名字为 " + new_template.getName());
		// links.add(getBackActionLink());
		return SUCCESS;
	}

	/**
	 * 批量删除模板。
	 * @return
	 */
	protected ActionResult batch_delete() {
		return delete();
	}
	
	/**
	 * 恢复指定标识的模板。
	 *
	 */
	protected ActionResult recover() {
		// 获得参数。
		List<Integer> ids = param_util.safeGetIntValues("templateId");
		if (ids == null || ids.size() == 0) {
			super.noIdsParam(); 
			return INVALID_PARAM;
		}
		
		// 执行操作。
		tx_proxy.restorePageTemplate(site.getTemplateThemeCollection(), ids);
		
		// 显示信息。
		messages.add("恢复模板成功完成。");
		// links.add(getBackActionLink());
		return SUCCESS;
	}
	
	/**
	 * 彻底删除模板。
	 *
	 */
	protected ActionResult destroy() {
		// 获得参数。
		List<Integer> ids = param_util.safeGetIntValues("templateId");
		if (ids == null || ids.size() == 0) {
			super.noIdsParam(); 
			return INVALID_PARAM;
		}
		
		// 执行操作。
		tx_proxy.realDeletePageTemplate(site.getTemplateThemeCollection(), ids);
		
		// 显示信息。
		messages.add("标识为 " + ids + " 的模板已经彻底删除。");
		// links.add(getBackActionLink());
		return SUCCESS;
	}
	
	/**
	 * 批量删除。
	 * @return
	 */
	protected ActionResult batch_destroy() {
		return destroy();
	}
	
	/**
	 * 彻底删除指定分组、指定频道的所有回收站中的模板。
	 *
	 */
	protected ActionResult batch_clear() {
		// proxy.realDeletePageTemplate(theme, ids);
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 刷新发布系统模板缓存。
	 *
	 */
	protected ActionResult refresh() {
		site.refreshTemplateCache();
		return SUCCESS;
	}

	/**
	 * 频道间模板复制。
	 * @return
	 */
	protected ActionResult channel_template_copy() {
		// 获得当前模板方案.
		if (getThemeData() == false) return INVALID_PARAM;

		// 得到参数：templateId, targetChannelId
		List<Integer> templateId = param_util.safeGetIds("templateId");
		List<Integer> targetChannelId = param_util.safeGetIds("targetChannelId");
		if (templateId == null || templateId.size() == 0) return INVALID_PARAM;
		if (targetChannelId == null || targetChannelId.size() == 0) return INVALID_PARAM;
		
		// 执行。
		TemplateThemeCollection theme_coll = site.getTemplateThemeCollection();
		tx_proxy.copyChannelTemplate(theme_coll, templateId, targetChannelId);
		
		// 返回。
		return SUCCESS;
	}
	
	// === 实现 =================================================================
	
	// 根据页面参数得到 theme 对象。
	private boolean getThemeData() {
		// 获得当前模板方案.
		int themeId = param_util.safeGetIntParam("themeId");
		if (themeId == 0)
			this.theme = site.getDefaultTheme();
		else
			this.theme = site.getTemplateThemeCollection().getTemplateTheme(themeId);
		return this.theme != null;
	}
	
	private PageTemplate collectPageTemplate() {
		/* 从页面提交的数据如下：
		(ignore) request.para[EditorContent][0] = 
		request.para[isDefault][0] = true - 是否设置为缺省. 
		request.para[channelId][0] = 0  - 所在频道.
		request.para[themeId][0] = 1  - 所属方案
		(ignore) request.para[action][0] = com.chinaedustar.publish.action.TemplateSaveAction 
		request.para[templateName][0] = 海蓝网站首页模板 - 模板名字 
		(ignore) request.para[rollContent][0] = 
		request.para[Content][0] = <html> ... - 模板内容
		request.para[templateId][0] = 1  - 模板标识
	 */
		int templateId = param_util.safeGetIntParam("templateId");
		PageTemplate template = null;
		if (templateId == 0)
			template = new PageTemplate();
		else
			template = theme.loadTemplate(templateId);
		if (template == null) return null;
		
		// 设置各个字段。
		template.setId(templateId);
		template.setName(param_util.safeGetStringParam("templateName"));
		if (templateId == 0) { // 修改的时候不允许更改 themeId, typeId, channelId
			template.setThemeId(theme.getId());
			template.setChannelId(param_util.safeGetIntParam("channelId"));
			template.setTypeId(param_util.safeGetIntParam("typeId"));
		}
		template.setContent(param_util.safeGetStringParam("Content"));
		template.setIsDefault(param_util.safeGetBooleanParam("isDefault", false));
		
		return template;
	}

}
