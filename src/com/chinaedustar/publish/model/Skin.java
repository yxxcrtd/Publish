package com.chinaedustar.publish.model;

/**
 * 风格
 * @author caojianjun
 *
 */
public class Skin extends AbstractNamedModelBase {
	/** 是否为方案中的默认皮肤。 true为默认*/
	private boolean isDefault;
	
	/** 此风格的详细内容。 */
	private String skinCss;
	
	/** 暂时无意义*/
	private boolean isDefaultInProject;
	
	/** 此风格所属的方案ID。 */
	private int themeId;

	/**
	 * 缺省构造函数。
	 *
	 */
	public Skin() {
		
	}

	//	 === getter, setter =========================================================
	
	/** 获取是否为方案中的默认皮肤 */
	public boolean getIsDefault() {
		return isDefault;
	}
	
	/** 设置是否为方案中的默认皮肤*/
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	/** 暂时无意义*/
	public boolean getIsDefaultInProject() {
		return isDefaultInProject;
	}
	
	/** 暂时无意义*/
	public void setIsDefaultInProject(Boolean isDefaultInProject) {
		this.isDefaultInProject = isDefaultInProject;
	}
	
	/**
	 * 获取皮肤的Css内容
	 * @return
	 */
	public String getSkinCss() {
		return skinCss;
	}
	
	/**
	 * 设置皮肤的Css内容
	 * @param skinCss
	 */
	public void setSkinCss(String skinCss) {
		this.skinCss = skinCss;
	}
	
	/**
	 * 获取皮肤所属的模板方案的标识
	 * @return
	 */
	public int getThemeId() {
		return themeId;
	}
	
	/**
	 * 设置皮肤所属的模板方案的标识
	 * @param themeId
	 */
	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

}