package com.chinaedustar.publish.action;

import java.io.File;
import java.util.List;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.ChannelCollection;

/**
 * 频道管理的操作.
 * 
 * @author liujunxing
 *
 */
public class ChannelAction extends AbstractActionEx {
	
	// Channel 支持的操作： save, delete, disable, enable, reorder
	static {
		registerCommand(ChannelAction.class, new ActionCommand("save"));
		registerCommand(ChannelAction.class, new ActionCommand("delete"));
		registerCommand(ChannelAction.class, new ActionCommand("disable"));
		registerCommand(ChannelAction.class, new ActionCommand("enable"));
		registerCommand(ChannelAction.class, new ActionCommand("reorder"));
		registerCommand(ChannelAction.class, "recover");	// 恢复
		registerCommand(ChannelAction.class, "destroy");	// 彻底销毁
	}
	
	/** channel 操作成功之后，显示模板会变化，刷新左侧列表框用。 */
	private String message_template = AbstractAction.TEMPLATE_MESSAGE_BLANK;
	
	// save 业务方法使用的变量
	private String old_UploadDirName;
	private Channel channel;
	private String root_path;		// 网站根所在实际目录。

	/**
	 * 保存频道.
	 * 注意：此方法从 AbstractActionEx 中被调用。
	 */
	protected ActionResult save() {
		// 先进行基本设置和检查。
		this.root_path = pub_ctxt.getRootDir();
		ActionResult result;
		if ((result = basicSetting()) != null) return result;

		// 收集更多信息。
		if (channel.getChannelType() == Channel.CHANNEL_TYPE_SYSTEM || 
				channel.getChannelType() == Channel.CHANNEL_TYPE_INTERNAL) {
			// 非外部频道还要收集更多信息。
			moreSetting(); 					// 更多设置				
			privilegeSetting(); 			// 权限设置				
			uploadSetting(); 				// 上传设置				
			generateSetting(); 				// 生成设置
		
			// 如果是修改的内部/系统频道，则需要修改上传目录名称。
			if (channel.getId() != 0) {
				renameChannelUploadDir();					// 重命名上传文件夹					
			}	
		} else {
			// 如果是外部频道，给一些不能为空的字段赋值。NOW: 现在不需要做了。
		}
		// 收集扩展属性。
		if (channel.getId() != 0)
			super.collectExtendsProp(channel);
		
		// 实际执行操作。
		ChannelCollection channel_coll = site.getChannels();	
		if (channel.getId() == 0)
			tx_proxy.createChannel(channel_coll, channel);
		else
			tx_proxy.updateChannel(channel_coll, channel);

		// 频道创建成功之后选用特定的显示模板。
		message_template = "channel_operate_success";
		return success(channel.getName());
	}

	/** 删除频道 */
	protected ActionResult delete() {
		// 1. 检查权限。
		if (canChannelManage() == false) return ACCESS_DENIED;
		
		// 获得要操作的频道对象。
		ActionResult result;
		if ((result = getOperatedChannel()) != null) return result;
		
		// 检查删除条件。
		if ((result = checkCanDelete()) != null) return result;
		
		// 删除频道。
		ChannelCollection channel_coll = site.getChannels();
		tx_proxy.deleteChannel(channel_coll, channel);
		
		// 提示信息。
		// messages.add("频道 '" + channel.getName() + "' 已经成功删除。");
		message_template = "channel_operate_success";
		return success(channel.getName());
	}
	
	/** 使能/启用频道。 */
	protected ActionResult enable() {
		return internalEnable(true);
	}
	
	/** 禁用/停用频道。 */
	protected ActionResult disable() {
		return internalEnable(false);
	}
	
	/** 重新排序频道。 */
	protected ActionResult reorder() {
		// 检查权限。
		if (canChannelManage() == false) return ACCESS_DENIED;
		
		// 获得参数。
		List<Integer> new_channel_ids_order = param_util.safeGetIntValues("channelIds");
		if (new_channel_ids_order == null || new_channel_ids_order.size() == 0) {
			messages.add("没有给出排序参数，请确定您是从有效的页面进入的。");
			return INVALID_PARAM;
		}
		
		// 执行排序。
		tx_proxy.reorderChannel(site.getChannels(), new_channel_ids_order);
		
		// 输出信息，其中要求重新刷新左侧菜单列表。
		// messages.add("更新频道排序成功完成。");
		this.message_template = "channel_operate_success";
		return SUCCESS;
	}
	
	/** 恢复指定的频道。 */
	protected ActionResult recover() {
		// 检查权限。
		if (canChannelManage() == false) return ACCESS_DENIED;

		// 获得要操作的频道对象。
		ActionResult result;
		if ((result = getOperatedChannel()) != null) return result;
		
		// 验证逻辑上是否能够使能或禁用。
		if (channel.getStatus() != Channel.CHANNEL_STATUS_DELETING.getCode()) {
			messages.add("频道 '" + channel.getName() + "' 未被删除，不能对其执行恢复操作。");
			return FAIL;
		}
		
		// 恢复频道。
		ChannelCollection channel_coll = site.getChannels();
		tx_proxy.recoverChannel(channel_coll, channel);
		
		// 日志和信息。
		message_template = "channel_operate_success";
		return success(channel.getName());
	}
	
	/** 彻底销毁一个频道。 */
	protected ActionResult destroy() {
		// 检查权限。
		if (canChannelManage() == false) return ACCESS_DENIED;

		// 获得要操作的频道对象。
		ActionResult result;
		if ((result = getOperatedChannel()) != null) return result;
		
		// 验证逻辑上是否能够使能或禁用。
		if (channel.getStatus() != Channel.CHANNEL_STATUS_DELETING.getCode()) {
			messages.add("频道 '" + channel.getName() + "' 未被删除，不能对其执行彻底销毁操作。");
			return FAIL;
		}
		
		// 销毁频道。
		ChannelCollection channel_coll = site.getChannels();
		tx_proxy.destroyChannel(channel_coll, channel);
		if (channel.getChannelDir() != null && channel.getChannelDir().length() > 0) {
			String channel_dir = pub_ctxt.getRootDir() + "/" + channel.getChannelDir() + "/";
			ChannelCollection.deepDeleteDirectory(channel_dir);
			messages.add("频道所在目录 " + channel.getChannelDir() + " 需要同时被删除，可能由于存在只读文件或正在使用中文件导致系统无法删除此目录的时候，需要您手工删除该目录。");
		}
		
		// 日志和信息。
		message_template = "channel_operate_success";
		return success(channel.getName());
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.action.AbstractAction#getMessageTemplateType()
	 */
	@Override public String getMessageTemplateType() {
		return message_template;
	}

	// **** save 业务方法 ******************************

	// 根据 channelId 参数初始化要保存的 Channel 对象。
	private ActionResult initSaveChannel() {
		int channelId = param_util.safeGetIntParam("channelId", 0);
		if(channelId > 0){
			channel = site.getChannels().loadChannel(channelId);
			if (channel == null) {
				// "标识为 {0,channelId} 的频道不存在。"
				return result("invalid_param_id", channelId);
			}
			if (channel.getStatus() == Channel.CHANNEL_STATUS_DELETING.getCode()) {
				// "频道 {0,channel.name} 已经被删除，从而不能修改其属性。"
				return result("channel_deleted", channel);
			}
		} else {
			channel = new Channel();
			channel.setId(0);
		}
		channel._init(pub_ctxt, site);
		return null;
	}
	
	// 检查频道更新/创建权限。
	private ActionResult checkSaveRight(Channel channel) {
		if (channel.getId() != 0) {
			// update channel - 频道管理员，或者具有频道管理权限。
			int channel_role = admin.getChannelRoleValue(channel.getId());
			boolean channel_manage = admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_CHANNEL_MANAGE);
			if (channel_manage == false && channel_role < Admin.CHANNEL_ROLE_MANAGER) {
				// messages.add("更新频道信息的操作权限被拒绝。");
				return access_denied(null, channel);
			}
		} else {
			// create channel - 频道管理员权限。
			if (false == admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_CHANNEL_MANAGE)) {
				// messages.add("创建频道的操作权限被拒绝。");
				return access_denied(null);
			}
		}
		return null;
	}
	
	// 收集 Channel 的基本信息，仅负责收集不做检查。 
	private void collectBasicInfo(Channel channel) {
		// 设置频道名称 
		channel.setName(param_util.safeGetStringParam("Name"));
		
		// 如果是新增加的频道，则需要进行一些基本的检验。
		if (channel.getId() == 0) {
			// 设置频道类型。0：系统频道；1：内部频道；2：外部频道。
			int channelType = param_util.safeGetIntParam("ChannelType", Channel.CHANNEL_TYPE_INTERNAL);
			if (channelType != Channel.CHANNEL_TYPE_INTERNAL && channelType != Channel.CHANNEL_TYPE_EXTERNAL) 
				channelType = Channel.CHANNEL_TYPE_INTERNAL;
			channel.setChannelType(channelType);
			
			// 对内部频道进行检查
			if (channel.getChannelType() == Channel.CHANNEL_TYPE_INTERNAL) {
				// 对模块类型进行检查
				int moduleId = param_util.safeGetIntParam("ModuleId", 0);
				//设置模块
				channel.setModuleId(moduleId);
				
				// 对频道目录进行检查
				String channelDir = param_util.safeGetStringParam("ChannelDir");
				// 设置频道目录
				channel.setChannelDir(channelDir);
			}
		}	
		
		// 对内部频道检查其项目名称
		if (channel.getChannelType() < 2) {				
			String itemName = param_util.safeGetStringParam("ItemName", "");
			//设置频道项目名称
			channel.setItemName(itemName);
		}

		//频道提示
		channel.setTips(param_util.safeGetStringParam("Tips"));
		//详细说明
		channel.setDescription(param_util.safeGetStringParam("Description"));
		//打开方式
		channel.setOpenType(param_util.safeGetIntParam("OpenType",0));
		//是否禁用
		channel.setStatus(param_util.safeGetIntParam("Disabled",0));
		//项目单位
		channel.setItemUnit(param_util.safeGetStringParam("ItemUnit"));
		
		// 外部频道
		if (channel.getChannelType() == Channel.CHANNEL_TYPE_EXTERNAL) {
			//链接地址
			channel.setChannelUrl(param_util.safeGetStringParam("ChannelUrl"));
		}
		// 显示在导航条。
		channel.setShowInNav(param_util.safeGetBooleanParam("ShowInNav"));
	}
	
	// 检查基本信息是否正确。返回 true 表示正确。
	private boolean checkBasicInfo(Channel channel) {
		boolean has_error = false;
		// 检查频道名称
		if (PublishUtil.isEmptyString(channel.getName())) {
			messages.add("频道名称不能为空，请重新填写。");
			has_error = true;
		}		
		
		// 如果是新增加的频道，则需要进行一些基本的检验。
		if (channel.getId() == 0) {
			//对内部频道进行检查
			if (channel.getChannelType() == Channel.CHANNEL_TYPE_INTERNAL) {
				// 对模块类型进行检查
				if (channel.getModuleId() <= 0) {
					messages.add("请选择频道所属的模块。");
					has_error = true;
				}
				
				// 对频道目录进行检查
				String channelDir = channel.getChannelDir();
				if (PublishUtil.isEmptyString(channelDir)) {
					messages.add("频道目录不能为空，请重新填写。");
					has_error = true;
				} 
				if (!PublishUtil.isValidDir(channelDir)) {		
					messages.add("频道目录必须为英文。");
					has_error = true;
				}
				// 部分知名名称不允许使用。
				if (PublishUtil.isSystemDir(channelDir)) {
					messages.add("不能使用 " + channel.getChannelDir() + " 作为频道目录，其为一个系统目录。");
					has_error = true;
				}
				
				// 检查频道目录是否已经存在。					
				if (new File(this.root_path + "\\" + channelDir).exists()) {
					messages.add("频道目录已经存在，请重新选择目录名称并填写。");
					has_error = true;
				}
			}
		}	
		
		// 对内部频道检查其项目名称
		if (channel.getChannelType() < 2) {				
			if (PublishUtil.isEmptyString(channel.getItemName())) {
				messages.add("频道项目名称不能为空，请重新填写。");
				has_error = true;
			}
		}
		return !has_error;
	}
	
	/**
	 * 基本设置。并进行基本的验证。
	 * @return 通过检查则返回 null；否则返回错误结果 ActionResult。
		
		新建频道的时候提交的数据示例：
		request.para[channelId][0] = 0 
		request.para[ModuleId][0] = 1 
		request.para[FileExtList][0] = 0 
		request.para[SkinId][0] = 0 
		request.para[ChannelType][0] = 1 
		request.para[StructureType][0] = 0 
		request.para[Disabled][0] = 0 
		request.para[FileExtIndex][0] = 0 
		request.para[EnableUploadFile][0] = false 
		request.para[AutoCreateType][0] = 0 
		request.para[OpenType][0] = 0 
		request.para[command][0] = save 
		request.para[ChannelDir][0] = fff 
		request.para[Description][0] = 
		request.para[Banner][0] = 
		request.para[Tips][0] = 
		request.para[Name][0] = fff 
		request.para[MetaDesc][0] = 
		request.para[MetaKey][0] = 
		request.para[UploadDir][0] = UploadDirs 
		request.para[action][0] = cn.edustar.jpub.action.ChannelAction 
		request.para[FileNameType][0] = 0 
		request.para[FileExtItem][0] = 0 
		request.para[Logo][0] = 
		request.para[ItemUnit][0] = 
		request.para[MaxFileSize][0] = 1024 
		request.para[ChannelUrl][0] = 
		request.para[UpdatePages][0] = 3 
		request.para[UseCreateHTML][0] = 0 
		request.para[debug][0] = true 
		request.para[Copyright][0] = 
		request.para[ItemName][0] = ff2 
		request.para[Submit][0] = 添 加 
		request.para[UpFileType][0] = jpg|gif|bmp|png|swf|rar|zip|rm|wma 
		request.para[ListFileType][0] = 0 
	 *  
	 */
	private ActionResult basicSetting() {
		ActionResult result;
		if ((result = initSaveChannel()) != null) return result;
		
		// 验证权限。
		if ((result = checkSaveRight(channel)) != null) return result;
		
		// 收集 Channel 的基本信息，仅负责收集不做检查。
		collectBasicInfo(channel);
		
		// 检查基本信息。
		if (checkBasicInfo(channel) == false) return INVALID_PARAM;
		
		return null;
	}
	
	/**
	 * 更多设置。
	 * 注意，外部频道不需要进行这些设置。
	 */
	private void moreSetting() {
		//LOGO
		channel.setLogo(param_util.safeGetStringParam("Logo"));
		//Banner
		channel.setBanner(param_util.safeGetStringParam("Banner"));
		//版权
		channel.setCopyright(param_util.safeGetStringParam("Copyright"));
		//META关键字
		channel.setMetaKey(param_util.safeGetStringParam("MetaKey"));
		//META描述
		channel.setMetaDesc(param_util.safeGetStringParam("MetaDesc"));
		//首页模版
		channel.setTemplateId(param_util.safeGetIntParam("TemplateId",0));
		//默认皮肤
		channel.setSkinId(param_util.safeGetIntParam("SkinId",0));
	}

	/**
	 * 权限设置。
	 * 注意，外部频道不需要进行这些设置。
	 */
	private void privilegeSetting() {
		
	}

	/**
	 * 上传设置。
	 * 注意，外部频道不需要进行这些设置。
	 */
	private void uploadSetting() {
		channel.setEnableUploadFile(param_util.safeGetBooleanParam("EnableUploadFile",true));
		//如果是修改一个频道对象，则需要记录其原始的上传目录。
		if (channel.getId() > 0) {
			this.old_UploadDirName = channel.getUploadDir();
		}		
		//上传目录
		String uploadDir = param_util.safeGetStringParam("UploadDir");
		if (uploadDir == null || uploadDir.length() < 1) {
			uploadDir = "UploadFiles";
		}
		channel.setUploadDir(uploadDir);
		//上传文件的限制大小
		channel.setMaxFileSize(Integer.valueOf(param_util.safeGetIntParam("MaxFileSize",0)));
		//上传文件的限制类型
		channel.setUpFileType(param_util.safeGetStringParam("UpFileType"));
	}

	/**
	 * 生成选项。
	 * 注意，外部频道不需要进行这些设置。
	 */
	private void generateSetting() {
		channel.setUseCreateHTML(param_util.safeGetIntParam("UseCreateHTML"));
		channel.setUpdatePages(param_util.safeGetIntParam("UpdatePages"));
		channel.setAutoCreateType(param_util.safeGetIntParam("AutoCreateType"));
		channel.setListFileType(param_util.safeGetIntParam("ListFileType"));
		channel.setStructureType(param_util.safeGetIntParam("StructureType"));
		channel.setFileNameType(param_util.safeGetIntParam("FileNameType"));
		channel.setFileExtIndex(param_util.safeGetIntParam("FileExtIndex"));
		channel.setFileExtList(param_util.safeGetIntParam("FileExtList"));
		channel.setFileExtItem(param_util.safeGetIntParam("FileExtItem"));
	}

	/**
	 * 重命名频道的上传目录名称。
	 * @param Channel 频道对象。
	 * @param newUploadDirName 新的上传目录名称。
	 */
	private void renameChannelUploadDir() {
		if (this.old_UploadDirName == channel.getUploadDir()) {
			return;
		}
		File oldUploadDir = new File(this.root_path + channel.getChannelDir() + "\\" + this.old_UploadDirName);
		File newUploadDir = new File(this.root_path + channel.getChannelDir() + "\\" + channel.getUploadDir());
		
		//如果旧的文件夹已经存在，则重命名；否则新建。
		if (oldUploadDir.exists()) {
			oldUploadDir.renameTo(newUploadDir);
		} else {
			newUploadDir.mkdir();
		}
	}

	// 检测是否具有 site.channel_manage 权限，如果没有则提示信息并记录日志。
	
	/**
	 * 获得当前要操作的频道对象。如果成功，设置到 channel 对象中。
	 */
	private ActionResult getOperatedChannel() {
		// 获得原来的频道。
		int channelId = param_util.safeGetIntParam("channelId");
		ChannelCollection channel_coll = site.getChannels();
		this.channel = channelId == 0 ? null : channel_coll.loadChannel(channelId);
		// 验证频道是否存在。
		if (channelId == 0 || channel == null) {
			// messages.add("未给出频道参数或频道标识 " + channelId + " 的频道不存在。");
			// links.add(getBackActionLink());
			return super.result("invalid_param_id", channelId);
		}
		
		return null;
	}

	// 返回是否具有 site.channel_manage 权限
	private boolean canChannelManage() {
		return admin.checkSiteRight(Admin.TARGET_SITE, Admin.OPERATION_CHANNEL_MANAGE);
	}

	// 检查是否逻辑上不允许删除频道。
	private ActionResult checkCanDelete() {
		if (channel.getStatus() == Channel.CHANNEL_STATUS_DELETING.getCode()) {
			messages.add("频道 '" + channel.getName() + "' 已经删除了。");
			return FAIL;
		}
		if (channel.getChannelType() == Channel.CHANNEL_TYPE_SYSTEM) {
			messages.add("频道 '" + channel.getName() + "' 是一个系统频道，您不能删除该系统频道，但是可以禁用该频道。");
			return FAIL;
		}
		return null;
	}

	/** enable/disable 的具体实现。 */
	private ActionResult internalEnable(boolean enable_status) {
		// 检查权限。
		if (canChannelManage() == false) return ACCESS_DENIED;

		// 获得要操作的频道对象。
		ActionResult result;
		if ((result = getOperatedChannel()) != null) return result;
		
		// 验证逻辑上是否能够使能或禁用。
		if (channel.getStatus() == Channel.CHANNEL_STATUS_DELETING.getCode()) {
			messages.add("频道 '" + channel.getName() + "' 已经删除了，不能对其禁用或启用。");
			return FAIL;
		}
		
		// 禁用/启用频道。
		ChannelCollection channel_coll = site.getChannels();
		tx_proxy.enableChannel(channel_coll, channel, enable_status);
		
		// 日志和信息。
		message_template = "channel_operate_success";
		return success(channel.getName());
	}
}

