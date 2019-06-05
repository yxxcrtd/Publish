package com.chinaedustar.publish.action;

import java.util.List;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.itfc.ChannelContainer;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.SpecialCollection;
import com.chinaedustar.publish.model.SpecialWrapper;
import com.chinaedustar.publish.pjo.Special;

/**
 * 专题管理的操作集合。
 * 
 * @author liujunxing
 *
 */
public final class SpecialAction extends AbstractActionEx {
	
	// 专题支持的操作： 'save', 'delete', 'reorder', 'unite', 'clear', 'remove_ref', 'copy_ref', 'move_ref'
	static {
		registerCommand(SpecialAction.class, new ActionCommand("save"));
		registerCommand(SpecialAction.class, new ActionCommand("delete"));
		registerCommand(SpecialAction.class, new ActionCommand("reorder"));
		registerCommand(SpecialAction.class, new ActionCommand("unite"));
		registerCommand(SpecialAction.class, new ActionCommand("clear"));
		registerCommand(SpecialAction.class, new ActionCommand("remove_ref"));
		registerCommand(SpecialAction.class, new ActionCommand("copy_ref"));
		registerCommand(SpecialAction.class, new ActionCommand("move_ref"));
	}
	
	/** 专题的容器对象。 */
	private ChannelContainer spec_cont;
	
	/** 专题集合对象。 */
	private SpecialCollection spec_coll;

	/** 当前正在操作的专题对象。 */
	private SpecialWrapper special;
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractActionEx#executeAction()
	 * 由于所有的页面都需要 channel, 所以预先检查。
	 */
	@Override protected ActionResult executeAction() {
		ActionResult result;
		if ((result = getChannelAndCheckRight()) != null) return result;
		
		return super.executeAction();
	}
	
	/** 保存/新增专题。 */
	protected ActionResult save() {
		// 收集数据。
		ActionResult result;
		if ((result = collectData()) != null) return result;
		
		// 修改或创建专题。
		createUpdateSpecial(special);

		// 日志和信息。
		return SUCCESS;
	}
	
	/** 删除指定专题。 */
	protected ActionResult delete() {
		// 获得当前要操作的专题。
		SpecialWrapper special = this.getSpecialAndCheck(null);
		if (special == null) return INVALID_PARAM;
		
		// 执行操作。
		tx_proxy.deleteSpecial(spec_coll, special);

		// 提示信息。
		// messages.add("专题 '" + special.getName() + "' 删除成功。");
		return success(special.getName());
	}

	/** 专题重新排序。 */
	protected ActionResult reorder() {
		// 获得更多参数。
		List<Integer> special_ids = param_util.safeGetIntValues("specialIds");

		// 执行操作。
		tx_proxy.reorderSpecial(spec_coll, special_ids);
		
		// 日志和信息。
		// messages.add("专题排序成功，新的顺序为：" + special_ids + "。");
		return SUCCESS;
	}
	
	/** 专题合并。 */
	protected ActionResult unite() {
		int source_special_id = param_util.safeGetIntParam("specialId");
		int target_special_id = param_util.safeGetIntParam("targetSpecialId");
		// 验证。
		if (source_special_id == target_special_id) {
			messages.add("源专题不能和目标专题相同。");
			// links.add(getBackActionLink());
			return INVALID_PARAM;
		}
		
		SpecialWrapper source_special = this.getSpecialAndCheck("specialId", "源专题");
		if (source_special == null) return INVALID_PARAM;
		
		SpecialWrapper target_special = this.getSpecialAndCheck("targetSpecialId", "目标专题");
		if (target_special == null) return INVALID_PARAM;

		
		// 执行操作。
		tx_proxy.uniteSpecial(spec_coll, source_special, target_special);
		
		// 日志和信息。
		// messages.add("合并专题 '" + source_special.getName() + "' 到 '"
		//		+ target_special.getName() + "' 成功完成。");
		return success(source_special.getName(), target_special.getName());
	}
	
	/** 清除专题中项目。 */
	protected ActionResult clear() {
		SpecialWrapper special = this.getSpecialAndCheck(null);
		if (special == null) return INVALID_PARAM;
		
		// 执行操作。
		tx_proxy.clearSpecialItems(spec_coll, special);
		
		// 信息和日志。
		// messages.add("清空专题 '" + special.getName() + "' 成功完成。");
		return success(special.getName());
	}

	/** 移除指定标识的专题、项目关联。 */
	protected ActionResult remove_ref() {
		// 获得参数。
		List<Integer> refids = param_util.safeGetIntValues("refids");
		if (refids == null || refids.size() == 0) return INVALID_PARAM;
		
		// 执行操作。
		tx_proxy.deleteRefSpecialItems(this.spec_coll, refids);
		
		// messages.add("移除项目成功完成");
		return SUCCESS;
	}
	
	/** 将指定 refids 复制到指定专题中 */
	protected ActionResult copy_ref() {
		return internal_copy_move_ref(true);
	}

	/** 将指定 refids 移动到指定专题中 */
	protected ActionResult move_ref() {
		return internal_copy_move_ref(false);
	}

	// === 辅助函数 ===================================================================

	/** 
	 * 根据 channelId 参数获得 Channel, SpecialCollection, 并验证权限。
	 * 
	 * @return null 表示正确，并设置了 this.spec_cont, this.spec_coll.
	 *   非 null 表示错误。
	 */
	private ActionResult getChannelAndCheckRight() {
		// 获得当前频道。
		int channelId = param_util.safeGetIntParam("channelId", 0);
		if (channelId == 0) {
			this.spec_cont = site;
		} else {
			Channel channel = site.getChannel(channelId);
			if (channel == null) {
				// messages.add("无法找到指定标识的频道，请确定您是从有效链接进入的。");
				return INVALID_CHANNEL;
			}
			this.spec_cont = channel;
		}
		this.spec_coll = this.spec_cont.getSpecialCollection();
		
		// 验证权限。
		return checkSpecialRight();
	}

	// 收集频道数据。
	private ActionResult collectData() {
		// 新建或找到原有 专题对象。
		Special special_pjo = null;
		int specialId = param_util.safeGetIntParam("specialId");
		if (specialId == 0) {
			special_pjo = new Special();
			this.special = new SpecialWrapper(special_pjo, pub_ctxt, site.getSpecialCollection());
		} else {
			this.special = site.getSpecialCollection().getSpecial(specialId);
			if (special == null) {
				// messages.add("无法找到指定标识的专题，可能该专题已经被删除了。");
				return result("invalid_param_id", specialId);
			}
		}
		
		// 收集该专题数据。
		special_pjo.setId(specialId);
		special_pjo.setName(param_util.safeGetStringParam("specialName"));
		
		// 处理频道标识。
		special_pjo.setChannelId(param_util.safeGetIntParam("channelId"));
		
		// 页面通用属性。
		special_pjo.setLogo(param_util.safeGetStringParam("logo"));
		special_pjo.setBanner(param_util.safeGetStringParam("banner"));
		special_pjo.setMetaKey(param_util.safeGetStringParam("metaKey"));
		special_pjo.setMetaDesc(param_util.safeGetStringParam("metaDesc"));
		special_pjo.setTemplateId(param_util.safeGetIntParam("templateId", 0));
		special_pjo.setSkinId(param_util.safeGetIntParam("skinId", 0));
		
		// 专题的特有属性。
		special_pjo.setSpecialOrder(param_util.safeGetIntParam("specialOrder", 1));
		special_pjo.setTips(param_util.safeGetStringParam("tips"));
		special_pjo.setDescription(param_util.safeGetStringParam("description"));
		
		// 处理频道目录
		// Site site = pub_ctxt.getSite();
		String dirPath = "";
		if (special.getId() == 0) {
			String specialDir = param_util.safeGetStringParam("specialDir");
			if (PublishUtil.isValidDir(specialDir) == false || PublishUtil.isSystemDir(specialDir)) {
				messages.add("专题的目录不能为空，且必须由英文或数字组成，第一个字母为英文。");
				messages.add("专题的目录不能为系统目录名，如 admin, skin 等。");
				// links.add(getBackActionLink());
				return INVALID_PARAM;
			}
			boolean exist = false;
			if (special.getChannelId() == 0) {
				dirPath = pub_ctxt.getRootDir() + "special/" + specialDir;
				if (new java.io.File(dirPath).exists()) {
					exist = true;
				}
			} else {
				Channel channel = (Channel)spec_cont;
				dirPath = pub_ctxt.getRootDir() + channel.getChannelDir() + "/special/" + specialDir;
				if (new java.io.File(dirPath).exists()) {
					exist = true;
				}
			}
			if (exist) {
				messages.add("专题目录已经存在，请重新填写。");
				// links.add(getBackActionLink());
				return INVALID_PARAM;
			} 
			special_pjo.setSpecialDir(specialDir);
		}
		special_pjo.setIsElite(param_util.safeGetBooleanParam("isElite", false));
		special_pjo.setSpecialPicUrl(param_util.safeGetStringParam("specialPicUrl"));
		
		special_pjo.setTemplateId(param_util.safeGetIntParam("templateId"));
		special_pjo.setSkinId(param_util.safeGetIntParam("skinId"));
		int maxPerPage = param_util.safeGetIntParam("maxPerPage", 20);
		if (maxPerPage <= 0) {
			maxPerPage = 20;
		}
		special_pjo.setMaxPerPage(maxPerPage);
		special_pjo.setOpenType(param_util.safeGetIntParam("openType"));
		
		return null;
	}

	// 内部实现 copy_ref, move_ref. copy == true 表示复制，== false 表示移动。
	private ActionResult internal_copy_move_ref(boolean copy) {
		// 获取参数。
		List<Integer> refids = param_util.safeGetIntValues("refids");
		List<Integer> specialIds = param_util.safeGetIntValues("specialIds");
		
		// 验证参数。
		ActionResult result;
		if ((result = checkCopyMoveParams(refids, specialIds)) != null) return result;
		
		// 执行操作。
		tx_proxy.copyMoveRefSpecialItems(this.spec_coll, refids, specialIds, true);
		
		// messages.add("成功将选定的文章复制/移动到目标专题中");
		links.add(new ActionLink("[返回]", "javascript:window.history.back(2);"));
		return SUCCESS;
	}
	
	// 检查专题管理权限。返回 null 表示有权限，否则无权限。
	private ActionResult checkSpecialRight() {
		if (this.spec_cont.getChannelId() == 0) {
			//  需要全站专题管理权。
			if (admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_SITE_SPECIAL))
				return null;
		} else {
			// 需要该频道的专题管理权。channel_role >= 总编
			int channel_role = admin.getChannelRoleValue(this.spec_cont.getChannelId());
			if (channel_role >= Admin.CHANNEL_ROLE_EDITOR) return null;
		}
		
		return ACCESS_DENIED;
	}
	
	// 获得指定 'key' 参数的专题，并验证其 channelId 是否符合。
	private SpecialWrapper getSpecialAndCheck(String key) {
		return getSpecialAndCheck(key, "专题");
	}

	// 获得指定 'key' 参数的专题，并验证其 channelId 是否符合。
	private SpecialWrapper getSpecialAndCheck(String key, String spec_name) {
		if (key == null) key = "specialId";
		int specialId = param_util.safeGetIntParam(key, 0);
		SpecialWrapper special = spec_coll.getSpecial(specialId);
		if (special == null || special.getChannelId() != spec_cont.getChannelId()) {
			if (special == null)
				messages.add("无法找到指定标识的" + spec_name + "，可能该专题已经被删除了。");
			if (special.getChannelId() != spec_cont.getChannelId())
				messages.add(spec_name + " '" + special.getName() + "' 不在当前频道/站点中，请确定您是从有效链接进入的。");
			links.add(getBackActionLink());
			return null;
		}
		return special;
	}

	// 创建或修改专题。
	private void createUpdateSpecial(SpecialWrapper special) {
		// 专题集合对象。
		SpecialCollection spec_coll = spec_cont.getSpecialCollection();

		// 实际的操作。
		if (special.getId() != 0) {
			// 修改, spec_coll.updateSpecial(special);
			tx_proxy.updateSpecial(spec_coll, special);
			messages.add("专题 '" + special.getName() + "' 更新成功。");
			
			// TODO: 创建频道目录
			// new java.io.File(dirPath).mkdirs();
		} else {	// 增加，需要计算出 specialOrder 。
			// TODO: 计算顺序的工作，也放在 spec_coll.insertSpecial(special) 里面。
			//计算出 specialOrder 。
			int orerId = spec_coll.getLastOrderId();
			special.getTargetObject().setSpecialOrder(orerId + 1);
			
			tx_proxy.insertSpecial(spec_coll, special);
			messages.add("专题 '" + special.getName() + "' 添加成功。");
		}
	}
	
	// 检查移动/复制专题项目的参数。返回 null 表示没有错误。
	private ActionResult checkCopyMoveParams(List<Integer> refids, List<Integer> specialIds) {
		boolean has_error = false;
		if (refids == null || refids.size() == 0) {
			messages.add("未给出要复制/移动的项目关联标识。");
			has_error = true;
		}
		if (specialIds == null || specialIds.size() == 0) {
			messages.add("未选择复制/移动的目标专题。");
			has_error = true;
		}
		
		return has_error ? INVALID_PARAM : null;
	}
}
