package com.chinaedustar.publish.action;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.AdminCollection;

/**
 * 保存管理员密码的类，在 admin_admin_password_save.jsp 中被调用
 */
public class AdminPasswordSaveAction extends AbstractAction {
	
	private String templateType = TEMPLATE_MESSAGE_DEFAULT;

	/**
	 * 业务的执行方法。
	 *
	 */
	@Override
	public void execute() throws Exception {
		Admin admin = PublishUtil.getCurrentAdmin(page_ctxt.getSession());
		String oldPassword = param_util.getRequestParam("oldPassword");
		String password = param_util.getRequestParam("password");
		String newPassword = param_util.getRequestParam("newPassword");
		ActionLink link = new ActionLink("返回");
		
		// 业务检测。
		if (password != null && !password.equals(newPassword)) {
			messages.add("新密码与确认密码不一致，不能修改密码。");
			templateType = TEMPLATE_MESSAGE_WRONG;
//			link.setUrlPattern("javascript:history.go(-1);");
			link.setUrl("javascript:history.go(-1);");
		} else {
			AdminCollection collection = pub_ctxt.getSite().getAdminCollection();
			if (collection.canLogin(admin.getAdminName(), oldPassword)) {
				admin.setPassword(newPassword);
				// 实际执行业务。
				// TODO: 提供单独的修改密码的业务，而不是用 updateAdmin 方法。
				pub_ctxt.getTransactionProxy().updateAdmin(collection, admin);
				messages.add("密码修改成功。");
				link.setUrl("admin_index_main.jsp");
			} else {
				messages.add("原密码错误，不能修改密码。");
				templateType = TEMPLATE_MESSAGE_WRONG;
				link.setUrl("javascript:history.go(-1);");
			}
		}
		links.add(link);
	}

	@Override
	public String getExceptionTemplateType() {
		return TEMPLATE_EXCEPTION_DEFAULT;
	}

	@Override
	public String getMessageTemplateType() {
		return templateType;
	}
	
}
