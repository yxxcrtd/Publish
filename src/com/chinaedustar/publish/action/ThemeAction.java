package com.chinaedustar.publish.action;

import java.util.List;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.Result;
import com.chinaedustar.publish.model.TemplateTheme;
import com.chinaedustar.publish.model.TemplateThemeCollection;

/**
 * 模板方案、模板、皮肤的操作。
 * 
 * @author liujunxing
 *
 */
public class ThemeAction extends AbstractAction {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	public void execute() throws Exception {
		String command = param_util.safeGetStringParam("command");
		if ("save".equals(command))
			save();
		else if ("delete".equalsIgnoreCase(command))
			delete();
		else if ("default".equalsIgnoreCase(command))
			set_default();
		else if ("batch_copy".equalsIgnoreCase(command))
			batch_copy_move(true);
		else if ("batch_move".equalsIgnoreCase(command))
			batch_copy_move(false);
		else
			unknownCommand(command);
	}

	/**
	 * 新建/修改模板方案。
	 *
	 */
	private void save() {
		// 获取数据。
		TemplateTheme theme = collect();
		
		// 执行
		tx_proxy.saveTemplateTheme(site.getTemplateThemeCollection(), theme);
		
		// 信息
		messages.add("添加/修改模板方案 '" + theme.getName() + "' 成功完成。");
		links.add(getThemeListLink());
		links.add(new ActionLink("管理方案的模板", "admin_template_list.jsp?themeId=" + theme.getId()));
		links.add(new ActionLink("管理方案的风格", "admin_skin_list.jsp?themeId=" + theme.getId()));
	}
	
	/**
	 * 删除指定的模板方案。
	 *
	 */
	private void delete() {
		TemplateTheme theme = getOperateTheme();
		if (theme == null) return;
		if (theme.getIsDefault()) {
			messages.add("您不能删除当前的缺省模板方案 '" + theme.getName() + "'");
			links.add(getBackActionLink());
			return;
		}

		// 操作。
		tx_proxy.deleteTemplateTheme(site.getTemplateThemeCollection(), theme);
		
		// 信息。
		messages.add("模板方案 '" + theme.getName() + "' 已经成功删除。");
		links.add(getBackActionLink());
	}
	
	/**
	 * 设置指定的模板方案是缺省模板方案。
	 *
	 */
	private void set_default() {
		TemplateTheme theme = getOperateTheme();
		if (theme == null) return;
		theme.setIsDefault(true);
		
		// 更新的时候会根据是否是 default 进行附加处理，我们不需要写单独的业务函数了。
		tx_proxy.saveTemplateTheme(site.getTemplateThemeCollection(), theme);
		
		// 信息。
		messages.add("模板方案 '" + theme.getName() + "' 已经设置成为网站的缺省模板方案。");
		links.add(getBackActionLink());
	}

	/**
	 * 批量复制模板。
	 * @param copy - true 表示复制，false 表示移动。
	 */
	private void batch_copy_move(boolean copy) {
		// 源模板方案标识。
		int themeId = param_util.safeGetIntParam("themeId");
		// 目标模板方案标识。
		int destThemeId = param_util.safeGetIntParam("destThemeId");
		// 要移动或复制的源模板方案标识。
		List<Integer> source_template_ids = param_util.safeGetIntValues("sourceTemplateId");
		
		// 验证参数。
		if (source_template_ids == null || source_template_ids.size() == 0) {
			messages.add("未给出要复制或移动的模板标识。");
		}
		if (themeId == destThemeId) {
			messages.add("源模板方案不能和目标模板方案相同。");
		}
		TemplateThemeCollection theme_coll = site.getTemplateThemeCollection();
		TemplateTheme source_theme = theme_coll.getTemplateTheme(themeId);
		if (source_theme == null) {
			messages.add("没有找到源模板方案，请确定您选择了源模板方案，以及从有效的链接进入的。");
		}
		TemplateTheme destin_theme = theme_coll.getTemplateTheme(destThemeId);
		if (destin_theme == null) {
			messages.add("没有找到目标模板方案，请确定您选择了源模板方案，以及从有效的链接进入的。");
		}
		if (messages.size() > 0) {
			links.add(getBackActionLink());
			return;
		}
		
		// 执行移动或复制。
		Result result = tx_proxy.batchCopyMoveTemplate(source_theme, destin_theme, source_template_ids, copy);

		// 设置信息。
		messages.addAll(result.getMessages());
		links.add(getBackActionLink());
	}
	
	private TemplateTheme getOperateTheme() {
		int themeId = param_util.safeGetIntParam("themeId");
		TemplateTheme theme = site.getTemplateThemeCollection().getTemplateTheme(themeId);
		if (theme == null) { 
			messages.add("无法找到指定标识的模板方案，请确定该模板方案存在。");
			links.add(getBackActionLink());
			return null;
		}
		return theme;
	}
	
	// 收集页面模板方案数据。
	private TemplateTheme collect() {
		int themeId = param_util.safeGetIntParam("themeId");
		TemplateTheme theme = null;
		if (themeId != 0) {
			theme = site.getTemplateThemeCollection().getTemplateTheme(themeId);
			if (theme == null)
				throw new PublishException("指定标识的模板方案不存在。");
		} else {
			theme = new TemplateTheme();
		}
		
		theme.setName(param_util.safeGetStringParam("name"));
		theme.setDescription(param_util.safeGetStringParam("description"));
		
		return theme;
	}

	private ActionLink getThemeListLink() {
		return new ActionLink("返回模板方案管理列表", "admin_theme_list.jsp");
	}
}
