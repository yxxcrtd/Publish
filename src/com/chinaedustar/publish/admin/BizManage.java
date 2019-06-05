package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.biz.BizName;
import com.chinaedustar.publish.biz.BizNameCollection;
import com.chinaedustar.publish.model.PaginationInfo;

/**
 * 和别的系统连接用的。对应 BizName 对象。
 * @author liujunxing
 *
 */
public class BizManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param page_ctxt
	 */
	public BizManage(PageContext page_ctxt) {
		super(page_ctxt);
	}
	
	/**
	 * admin_biz_list.jsp 页面数据初始化。
	 *
	 */
	public void initListPage() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		Object biz_list = biz_coll.getBizNameList();
		setTemplateVariable("biz_list", biz_list);
	}
	
	/**
	 * admin_biz_edit.jsp 页面数据初始化。
	 *
	 */
	public void initEditPage() {
		int id = param_util.safeGetIntParam("bizId");
		if (id == 0) {
			setTemplateVariable("bizName", new BizName());
		} else {
			BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
			BizName biz_name = biz_coll.getBizName(id);
			setTemplateVariable("bizName", biz_name);
		}
	}
	
	/**
	 * admin_biz_user.jsp 页面数据初始化。
	 *
	 */
	public void initUserList() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		
		// 得到指定标识的业务连接对象。
		int biz_id = param_util.safeGetIntParam("bizId");
		BizName biz_name = biz_coll.getBizName(biz_id);
		if (biz_name == null) throw new PublishException("未找到指定标识的业务连接对象。");
		setTemplateVariable("biz_name", biz_name);
		
		// 得到该业务标识下的所有用户。
		Object user_list = biz_coll.getBizUserList(biz_name);
		setTemplateVariable("user_list", user_list);
	}

	/**
	 * admin_biz_seluser.jsp 页面数据初始化。
	 *
	 */
	public void initUserSelect() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		int page = param_util.safeGetIntParam("page", 1);
		PaginationInfo page_info = new PaginationInfo();
		page_info.setCurrPage(page);
		page_info.setPageSize(20);		// ? 配置到哪里好呢?
		page_info.setItemName("个用户");
		
		Object user_list = biz_coll.getSelectUserList(page_info);
		setTemplateVariable("user_list", user_list);
		setTemplateVariable("page_info", page_info);
	}

	/**
	 * admin_biz_group.jsp 页面数据初始化。
	 *
	 */
	public void initGroupList() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		
		// 得到指定标识的业务连接对象。
		int biz_id = param_util.safeGetIntParam("bizId");
		BizName biz_name = biz_coll.getBizName(biz_id);
		if (biz_name == null) throw new PublishException("未找到指定标识的业务连接对象。");
		setTemplateVariable("biz_name", biz_name);
		
		// 得到该业务标识下的所有群组。
		Object group_list = biz_coll.getBizGroupList(biz_name);
		setTemplateVariable("group_list", group_list);
	}

	/**
	 * admin_biz_selgroup.jsp 页面数据初始化。
	 *
	 */
	public void initGroupSelect() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		int page = param_util.safeGetIntParam("page", 1);
		PaginationInfo page_info = new PaginationInfo();
		page_info.setCurrPage(page);
		page_info.setPageSize(10);		// ? 配置到哪里好呢?
		page_info.setItemName("个群组");
		
		Object group_list = biz_coll.getSelectGroupList(page_info);
		setTemplateVariable("group_list", group_list);
		setTemplateVariable("page_info", page_info);
	}
}
