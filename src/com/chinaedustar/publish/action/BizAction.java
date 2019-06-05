package com.chinaedustar.publish.action;

import java.util.List;

import com.chinaedustar.publish.biz.BizName;
import com.chinaedustar.publish.biz.BizNameCollection;

/**
 * Biz 业务连接管理的业务处理。
 * @author liujunxing
 *
 */
public class BizAction extends AbstractActionEx {
	// link_user, unlink_user ...
	static {
		registerCommand(BizAction.class, "save");
		registerCommand(BizAction.class, "delete");
		registerCommand(BizAction.class, new ActionCommand("link_user"));
		registerCommand(BizAction.class, new ActionCommand("unlink_user"));
		registerCommand(BizAction.class, new ActionCommand("link_group"));
		registerCommand(BizAction.class, new ActionCommand("unlink_group"));
	}
	
	/**
	 * 保存一个。
	 * @return
	 */
	protected ActionResult save() {
		// 收集。
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		int bizId = param_util.safeGetIntParam("bizId");
		BizName biz_name = null;
		if (bizId == 0)
			biz_name = new BizName();
		else
			biz_name = biz_coll.getBizName(bizId);
		biz_name.setName(param_util.safeGetStringParam("name"));
		biz_name.setDescription(param_util.safeGetStringParam("description"));
		
		// 执行。
		biz_coll.saveBizName(biz_name);
		
		// 返回。
		return SUCCESS;
	}
	
	/**
	 * 删除一个连接。
	 * @return
	 */
	protected ActionResult delete() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		// 获得 bizId 参数。
		int bizId = param_util.safeGetIntParam("bizId");
		if (bizId == 0) return INVALID_PARAM;
		
		biz_coll.deleteBizName(bizId);
		
		// 返回。
		return SUCCESS;
	}
	
	/**
	 * 添加用户连接。
	 * @return
	 */
	protected ActionResult link_user() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		// 1. 获得 biz_name, user_ids 参数。
		int biz_id = param_util.safeGetIntParam("bizId");
		BizName biz_name = biz_coll.getBizName(biz_id);
		if (biz_name == null) return INVALID_PARAM;
		
		List<Integer> user_ids = param_util.safeGetIntValues("userId");
		
		// 执行操作。(注意：不含事务包装)
		biz_coll.linkUser(biz_name, user_ids);
		
		// 返回。
		return SUCCESS;
	}
	
	/**
	 * 去掉用户连接。
	 * @return
	 */
	protected ActionResult unlink_user() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		
		// 1. 获得 biz_name, user_ids 参数。
		int biz_id = param_util.safeGetIntParam("bizId");
		BizName biz_name = biz_coll.getBizName(biz_id);
		if (biz_name == null) return INVALID_PARAM;
		
		List<Integer> user_ids = param_util.safeGetIntValues("userId");
		
		// 执行操作。(注意：不含事务包装)
		biz_coll.unlinkUser(biz_name, user_ids);
		
		// 返回。
		return SUCCESS;
	}

	/**
	 * 添加群组连接。
	 * @return
	 */
	protected ActionResult link_group() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		// 1. 获得 biz_name, user_ids 参数。
		int biz_id = param_util.safeGetIntParam("bizId");
		BizName biz_name = biz_coll.getBizName(biz_id);
		if (biz_name == null) return INVALID_PARAM;
		
		List<Integer> group_ids = param_util.safeGetIntValues("groupId");
		
		// 执行操作。(注意：不含事务包装)
		biz_coll.linkGroup(biz_name, group_ids);
		
		// 返回。
		return SUCCESS;
	}

	/**
	 * 去掉群组连接。
	 * @return
	 */
	protected ActionResult unlink_group() {
		BizNameCollection biz_coll = new BizNameCollection(pub_ctxt);
		
		// 1. 获得 biz_name, group_ids 参数。
		int biz_id = param_util.safeGetIntParam("bizId");
		BizName biz_name = biz_coll.getBizName(biz_id);
		if (biz_name == null) return INVALID_PARAM;
		
		List<Integer> group_ids = param_util.safeGetIntValues("groupId");
		
		// 执行操作。(注意：不含事务包装)
		biz_coll.unlinkGroup(biz_name, group_ids);
		
		// 返回。
		return SUCCESS;
	}
}
