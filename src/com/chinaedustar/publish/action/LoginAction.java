package com.chinaedustar.publish.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.ParamUtil;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.Result;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.AdminCollection;
import com.chinaedustar.publish.model.Log;
import com.chinaedustar.publish.util.ValidCodeImage;

/**
 * 登录和退出
 * 
 * @author liujunxing
 */
public class LoginAction {
	
	/** 验证码错误 */
	public static final int INVALID_CHECKCODE = 1;
	
	/** 用户名或密码不正确 */
	public static final int USER_OR_PASSWORD_ERROR = 2;
	
	/** 页面环境 */
	private final PageContext page_ctxt;
	private final ParamUtil param_util;
	private final PublishContext pub_ctxt;
	
	/**
	 * 构造
	 * 
	 * @param page_ctxt
	 */
	public LoginAction(PageContext page_ctxt) {
		this.page_ctxt = page_ctxt;
		this.param_util = new ParamUtil(this.page_ctxt);
		this.pub_ctxt = this.param_util.getPublishContext();
	}
	
	/**
	 * 登录进入
	 */
	public Result login() {
		Result result = internal_login();
		log_login(result);
		return result;
	}
	
	private Result internal_login() {
		String adminName = param_util.safeGetStringParam("adminName");
		String password = param_util.getRequestParam("password");
		String validCode = param_util.safeGetStringParam("checkCode");

		// 检查验证码是否正确
		if (!ValidCodeImage.checkValidCode(page_ctxt.getSession(), validCode)) {
			return new Result(LoginAction.INVALID_CHECKCODE);
		} else {
			AdminCollection admin_coll = pub_ctxt.getSite().getAdminCollection();
			boolean right = admin_coll.canLogin(adminName, password);
			if (right) {
				PublishUtil.setCurrentAdmin(adminName, page_ctxt);
				Admin admin = PublishUtil.getCurrentAdmin(page_ctxt.getSession());
				admin.setLastLoginTime(new java.util.Date());
				admin.setLastLoginIp(page_ctxt.getRequest().getRemoteAddr());
				pub_ctxt.getTransactionProxy().updateAdmin(pub_ctxt.getSite().getAdminCollection(), admin);
			
				// response.sendRedirect("admin_index.jsp");
				return Result.SUCCESS;
			} else {
				return new Result(USER_OR_PASSWORD_ERROR);
			}
		}
	}
	
	/**
	 * 登录退出
	 */
	public void logout() {
		Admin admin = PublishUtil.getCurrentAdmin(page_ctxt.getSession());
		if (admin != null) {
			log_logout(admin);
		}
		page_ctxt.getSession().removeAttribute(PublishUtil.ADMIN_USERNAME);

		try {
			HttpServletResponse response = (HttpServletResponse)page_ctxt.getResponse();
			response.sendRedirect(pub_ctxt.getSite().getUrl());
		} catch (java.io.IOException ex) {
			// ignore
		}
	}
	
	/**
	 * 记录日志：登录失败
	 */
	private void log_login(Result result) {
		Log log = new Log();
		log.setOperation("login");
		log.setStatus(result.getCode());
		String adminName = param_util.safeGetStringParam("adminName");
		log.setUserName(adminName);
		String description = "管理员 '" + adminName + "' 登录";
		if (result.getCode() == 0)
			description += "成功。";
		else if (result.getCode() == INVALID_CHECKCODE) 
			description += "失败: 非法验证码。";
		else if (result.getCode() == USER_OR_PASSWORD_ERROR)
			description += "失败: 用户名或密码错误。";
		log.setDescription(description);

		log.setIPUrlPostData(page_ctxt);
		pub_ctxt.log(log);
	}

	/**
	 * 记录日志，管理员登出
	 * 
	 * @param admin
	 */
	private void log_logout(Admin admin) {
		Log log = new Log();
		log.setOperation("logout");
		log.setStatus(0);
		log.setUserName(admin.getAdminName());
		log.setDescription("管理员 '" + admin.getAdminName() + "' 已经登出。");
		log.setIPUrlPostData(page_ctxt);
		pub_ctxt.log(log);
	}
	
}
