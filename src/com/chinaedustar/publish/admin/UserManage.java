package com.chinaedustar.publish.admin;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.publish.util.ValidCodeImage;

/**
 * 用户管理。
 * 
 * @author liujunxing
 *
 */
public class UserManage extends AbstractBaseManage {
	/** 验证码输入不正确，请重新填写。 */
	public static final int VALID_CODE_FAIL = 1;
	public static final String VALID_CODE_FAIL_TITLE = "验证码输入不正确，请重新填写。";
	
	/** 用户名或密码不正确，请重新登录。 */
	public static final int USER_PASSWORD_FAIL = 2;
	public static final String USER_PASSWORD_FAIL_TITLE = "用户名或密码不正确，请重新登录。";
	
	/** 当前用户被禁用，无法登录，具体原因请询问网站管理员。 */
	public static final int USER_DISABLED = 3;
	public static final String USER_DISABLED_TITLE = "当前用户被禁用，无法登录，具体原因请询问网站管理员。";
	
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public UserManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_user_list.jsp 页面数据初始化。
	 *
	 */
	public void initListPage() {
		// 用户列表。
		initUserList();
	}

	/**
	 * admin_user_add.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		// 获得会员的信息
		int userId = param_util.safeGetIntParam("userId", 0);
		UserCollection user_coll = site.getUserCollection();
		User user = null;
		if (userId == 0) {
			user = new User();
			user._init(pub_ctxt, user_coll);
		} else {
			user = user_coll.getUser(userId);
		}
		setTemplateVariable("user", user);
	}

	/**
	 * admin_user_checkUserName.jsp 页面。
	 *
	 */
	public boolean checkUserExistPage() {
		// 验证会员的用户名
		String user_name = param_util.safeGetChineseParameter("userName");
		UserCollection user_coll = site.getUserCollection();
		return user_coll.existUser(user_name);
	}
	
	/**
	 * 用户登录.
	 *
	 */
	@SuppressWarnings("null")
	public Result userLogin() {
		// 基本参数。
		String returnUrl = param_util.safeGetStringParam("returnUrl", pub_ctxt.getSite().getInstallDir() + "user/index.jsp");
		String userName = param_util.safeGetStringParam("userName");
		String password = param_util.getRequestParam("password");
		String validCode = param_util.safeGetStringParam("checkCode");
		HttpServletResponse response = (HttpServletResponse)page_ctxt.getResponse();
		
		// 检查验证码是否正确
		if (!ValidCodeImage.checkValidCode(page_ctxt.getSession(), validCode)) {
			// 验证码输入不正确，请重新填写
			return new Result(VALID_CODE_FAIL, VALID_CODE_FAIL_TITLE);
		} 
		
		// 根据用户名和密码获得用户。
		UserCollection user_coll = pub_ctxt.getSite().getUserCollection();
		User user = user_coll.getUser(userName, password);
		if (user == null)
			return new Result(USER_PASSWORD_FAIL, USER_PASSWORD_FAIL_TITLE);
		if (user.getStatus() == 1)
			return new Result(USER_DISABLED, USER_DISABLED_TITLE);

		// 更新用户登录信息。
		user.setLastLoginTime(new Date());
		user.setLastLoginIp(page_ctxt.getRequest().getRemoteAddr());
		tx_proxy.saveUser(user_coll, user);
		
		// 设置当前用户并重定向。
		PublishUtil.setCurrentUser(user, page_ctxt);
		if (returnUrl != null || returnUrl.trim().length() > 0) {
			try {
				response.sendRedirect(returnUrl);
			} catch (java.io.IOException ex) {
				// ignore
			}
		}
		return Result.SUCCESS;
	}

	// 初始化用户列表。
	private void initUserList() {
		PaginationInfo page_info = getPaginationInfo();
		UserCollection user_coll = site.getUserCollection();
		
		// int searchType = paramUtil.safeGetIntParam("searchType", 0);
		String field = param_util.getRequestParam("field");
		String keyword = param_util.getRequestParam("keyword");
		List<User> user_list = user_coll.getUserList(field, keyword, page_info);
		page_info.setItemName("个用户");
		page_info.init();
		
		setTemplateVariable("user_list", user_list);
		setTemplateVariable("page_info", page_info);
	}
}
