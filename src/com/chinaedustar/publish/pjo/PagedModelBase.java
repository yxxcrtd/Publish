package com.chinaedustar.publish.pjo;

/**
 * 具有页面属性如 title, logo, meta, template, skin, gen 等属性的对象基类。
 * 
 * @author liujunxing
 *
 */
public class PagedModelBase extends NamedModelBase {
	// === 属性 =============================================================
	
	/** 对象标题。 */
	private String title;
	
	/** Logo, inheritable */
	private String logo;
	
	/** Banner, inheritable */
	private String banner;
	
	/** 版权声明, inheritable。 */
	private String copyright;
	
	/** meta_key, inheritable. */
	private String meta_key;
	
	/** meta_desc, inheritable. */
	private String meta_desc;
	
	/** 模板标识。 */
	private int template_id;
	
	/** 样式标识。 */
	private int skin_id;
	
	/** 静态化页面是否已经生成 */
	private boolean isGenerated;
	
	/** 静态化页面地址 */
	private String staticPageUrl;
	
	/** 最后一次生成的时间。 */
	private java.sql.Timestamp lastGenerated; 

	// === getter, setter ====================================================

	/**
	 * 获得此页面的标题。
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 设置页面标题。
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	/**
	 * 获得此页面的 Logo.
	 * @return
	 */
	public String getLogo() {
		return this.logo;
	}
	
	/**
	 * 设置此页面的 Logo.
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	/**
	 * 获得此页面的 Banner.
	 * @return
	 */
	public String getBanner() {
		return this.banner;
	}
	
	/**
	 * 设置此页面的 Banner.
	 */
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	/**
	 * 获得此页面的 Copyright.
	 * @return
	 */
	public String getCopyright() {
		return this.copyright;
	}
	
	/**
	 * 设置此页面的 Copyright.
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	/**
	 * 获得此页面的 MetaKey.
	 * @return
	 */
	public String getMetaKey() {
		return this.meta_key;
	}
	
	/**
	 * 设置此页面的 MetaKey.
	 */
	public void setMetaKey(String meta_key) {
		this.meta_key = meta_key;
	}
	
	/**
	 * 获得此页面的 MetaDesc.
	 * @return
	 */
	public String getMetaDesc() {
		return this.meta_desc;
	}
	
	/**
	 * 设置此页面的 MetaDesc.
	 */
	public void setMetaDesc(String meta_desc) {
		this.meta_desc = meta_desc;
	}
	
	/**
	 * 获得此页面使用的模板标识。
	 * @return
	 */
	public int getTemplateId() {
		return this.template_id;
	}
	
	/**
	 * 设置此页面使用的模板标识。
	 */
	public void setTemplateId(int template_id) {
		this.template_id = template_id;
	}
	
	/**
	 * 获得样式标识。
	 * @return
	 */
	public int getSkinId() {
		return this.skin_id;
	}

	/**
	 * 设置样式标识。
	 */
	public void setSkinId(int skin_id) {
		this.skin_id = skin_id;
	}

	/**
	 * 获取静态化页面是否已经生成
	 * @return
	 */
	public boolean getIsGenerated() {
		return isGenerated;
	}

	/**
	 * 设置静态化页面是否已经生成
	 * @param isGenerated
	 */
	public void setIsGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}

	/**
	 * 获取静态化页面地址。
	 * <p><b><font color=red>重要约定</font></b>：静态化地址是相对于父对象目录
	 *   (site - '/PubWeb/', channel '/PubWeb/news/')的，
	 *   如 'news/index.html', 'column/international/asian/index.html'
	 * </p>
	 * @return
	 */
	public String getStaticPageUrl() {
		return staticPageUrl;
	}
	
	/**
	 * 设置静态化页面地址
	 * @param staticPageUrl
	 */
	public void setStaticPageUrl(String staticPageUrl) {
		this.staticPageUrl = staticPageUrl;
	}

	/** 获得最后一次生成的时间。 */
	public java.sql.Timestamp getLastGenerated() {
		return this.lastGenerated;
	}
	
	/** 设置最后一次生成的时间。 */
	public void setLastGenerated(java.sql.Timestamp lastGenerated) {
		this.lastGenerated = lastGenerated;
	}
}
