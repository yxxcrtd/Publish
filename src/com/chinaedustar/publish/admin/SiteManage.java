package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.model.*;

/**
 * 站点管理.
 * 
 * @author liujunxing
 *
 */
public class SiteManage extends AbstractBaseManage {
	/**
	 * 构造.
	 * @param pageContext
	 */
	public SiteManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_site.jsp 页面数据初始化.
	 *
	 */
	public void initSitePage() {
		// 为使用此页面必须具有 "site.site_manage" 权限。
		if (admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_SITE_MANAGE) == false) {
			throw super.accessDenied(getAccessDeniedDesc(), true, Admin.OPERATION_SITE_MANAGE);
		}
		
		// 以后我们也许应该将 site 对象再次包装之后设置到页面中。而不是直接用内部 site 对象。
		setTemplateVariable("site", site);
		setTemplateVariable("object", site);
	}
}
