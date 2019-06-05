package com.chinaedustar.publish.admin;

import java.util.List;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.model.TemplateTheme;

/**
 * 模板方案、模板、皮肤管理数据提供。
 * 
 * @author liujunxing
 *
 */
public class ThemeManage extends AbstractBaseManage {
	/**
	 * 构造。
	 * @param pageContext
	 */
	public ThemeManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_theme_list.jsp 页面数据初始化。
	 *
	 */
	public void initThemeListPage() {
		// 获得所有方案列表集合。 
		List<TemplateTheme> theme_list = site.getTemplateThemeCollection().getThemeList();
		setTemplateVariable("theme_list", theme_list);
	}

	/**
	 * admin_theme_add.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		int themeId = param_util.safeGetIntParam("themeId", 0);
		TemplateTheme theme = null;
		if (themeId == 0) {
			theme = new TemplateTheme();
		} else {
			theme = site.getTemplateThemeCollection().getTemplateTheme(themeId);
			if (theme == null)
				theme = new TemplateTheme();
		}
		setTemplateVariable("theme", theme);
	}
}
