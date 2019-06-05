package com.chinaedustar.publish.model;

import java.util.List;

/**
 * 模版组的接口
 * 
 * @author dengxiaolong
 *
 */
public class TemplateGroup extends AbstractNamedModelBase {
	/** '网站通用模板' 分组的名字。 */
	public static final String ROOT_GROUP_NAME = "root";
	
	/** '用户管理模板' 分组的名字。 */
	public static final String USER_GROUP_NAME = "user";
	
	/** 标题 */
	private String title;
	
	/** 使用此模板分组的模块标识。 */
	private int moduleId;
	
	/** 是否是通用分组，非通用分组和一个模块绑定。 */
	private boolean isSystem;
	
	/** 是否禁用 */
	private boolean disabled;
	
	/** 频道标识，其不保存在数据库中，仅用在对象模型中。 */
	private int channelId;
	
	/**
	 * 标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 使用此模板分组的模块标识。 */
	public int getModuleId() {
		return this.moduleId;
	}
	
	/** 使用此模板分组的模块标识。 */
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	
	/** 是否是通用分组，非通用分组和一个模块绑定。 */
	public boolean getIsSystem() {
		return this.isSystem;
	}
	
	/** 是否是通用分组，非通用分组和一个模块绑定。 */
	public void setIsSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}
	
	/** 是否禁用 */
	public boolean getDisabled() {
		return this.disabled;
	}
	
	/** 是否禁用 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/** 频道标识，其不保存在数据库中，仅用在对象模型中。 */
	public int getChannelId() {
		return this.channelId;
	}
	
	/** 频道标识，其不保存在数据库中，仅用在对象模型中。 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	// === 业务方法  ========================================================
	
	/**
	 * 获得指定模板分组下所有模板类别集合。
	 * @param groupId
	 * @return
	 */
	public List<TemplateType> getTemplateTypeList() {
		// 这里的方法和 theme_collection 一致，所以用那里的。
		return pub_ctxt.getSite().getTemplateThemeCollection().getTemplateTypeList(this.getId());
	}
}
