package com.chinaedustar.publish.model;

import com.chinaedustar.publish.*;
import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.util.UpdateHelper;

/**
 * 具有页面属性的模型抽象实现基类。
 * 
 * @author liujunxing
 */
public abstract class AbstractPageModelBase extends AbstractNamedModelBase implements PageAttrObject, ExtendPropertySupport, DynamicAttributeSupport {
	/**
	 * 缺省构造函数。
	 *
	 */
	protected AbstractPageModelBase() {
		
	}
	
	/**
	 * 复制构造函数，为子类实现 clone 提供支持。
	 * @param src
	 */
	protected AbstractPageModelBase(AbstractPageModelBase src) {
		copy(src);
	}
	
	// === 通用 getter, setter ================================================
	
	/**
	 * 获取指定名字的属性值。支持 logo, banner, copyright 等, 其它都委托给了父类实现。
	 * @param name - 要获取的属性名。
	 * @see AbstractNamedModelBase#get(String)
	 */
	@Override public Object get(String name) {
		// 自有属性
		if ("logo".equals(name))
			return this.getLogo();
		else if ("banner".equals(name))
			return this.getBanner();
		else if ("copyright".equals(name))
			return this.getCopyright();
		else if ("metaKey".equals(name))
			return this.getMetaKey();
		else if ("metaDesc".equals(name))
			return this.getMetaDesc();
		else if ("templateId".equals(name))
			return this.getTemplateId();
		else if ("skinId".equals(name))
			return this.getSkinId();
		else if ("isGenerated".equals(name))
			return this.getIsGenerated();
		else if ("staticPageUrl".equals(name))
			return this.getStaticPageUrl();
		else if ("lastGenerated".equals(name))
			return this.getLastGenerated();

		// 使基类进行解释。
		Object val = super.get(name);
		if (val != UNEXIST) return val;
		
		// 查找扩展属性。
		if (ext_props == null)
			ext_props = new ExtendPropertySet(this, super._getPublishContext());
		ExtendProperty prop = ext_props.get(name);
		if (prop != null)
			return prop.getValue();

		return null;
	}
	
	/**
	 * 通用 set 方法。
	 * @param name - 属性名，支持 logo, banner, copyright 等, 其它都委托给了父类实现。
	 * @param value 
	 * @see AbstractNamedModelBase#set(String, Object)
	 */
	@Override public void set(String name, Object value) {
		if ("logo".equals(name))
			this.setLogo((String)value);
		else if ("banner".equals(name))
			this.setBanner((String)value);
		else if ("copyright".equals(name))
			this.setCopyright((String)value);
		else if ("metaKey".equals(name))
			this.setMetaKey((String)value);
		else if ("metaDesc".equals(name))
			this.setMetaDesc((String)value);
		else if ("templateId".equals(name))
			this.setTemplateId((Integer)value);
		else if ("skinId".equals(name))
			this.setSkinId((Integer)value);
		else if ("isGenerated".equals(name))
			this.setIsGenerated((Boolean)value);
		else if ("staticPageUrl".equals(name))
			this.setStaticPageUrl((String)value);
		else if ("lastGenerated".equals(name))
			this.setLastGenerated((java.sql.Timestamp)value);
		else
			super.set(name, value);
	}

	/**
	 * 复制。
	 * @param src
	 */
	protected void copy(AbstractPageModelBase src) {
		super.copy(src);
		this.logo = src.logo;
		this.banner = src.banner;
		this.copyright = src.copyright;
		this.meta_key = src.meta_key;
		this.meta_desc = src.meta_desc;
		this.template_id = src.template_id;
		this.skin_id = src.skin_id;
		this.isGenerated = src.isGenerated;
		this.staticPageUrl = src.staticPageUrl;
		
	}
	
	// === 属性 =============================================================
	
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
	
	/** 获得最后一次生成的时间。 */
	public java.sql.Timestamp getLastGenerated() {
		return this.lastGenerated;
	}
	
	/** 设置最后一次生成的时间。 */
	public void setLastGenerated(java.sql.Timestamp lastGenerated) {
		this.lastGenerated = lastGenerated;
	}

	/**
	 * 设置静态化页面地址
	 * @param staticPageUrl
	 */
	public void setStaticPageUrl(String staticPageUrl) {
		this.staticPageUrl = staticPageUrl;
	}
	
	/**
	 * 得到文件扩展名。比如".html"。
	 * @param intExt 
	 * @return
	 */
	public static String getFileExtName(int intExt) {
		switch(intExt) {
		case 0:
			return ".html";
		case 1:
			return ".htm";
		case 2:
			return ".shtml";
		case 3:
			return ".shtm";
		case 4:
			return ".jspx";
		}
		return ".html";
	}

	// === PageAttrObject 生成部分接口实现 =========================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PageAttrObject#rebuildStaticPageUrl()
	 */
	public boolean rebuildStaticPageUrl() {
		// 重新计算静态化地址，如果发生了变化则删除旧的静态化文件。
		String newStaticPageUrl = getNewStaticPageUrl(pub_ctxt);
		String oldStaticPageUrl = getStaticPageUrl();
		// System.out.println("new = " + newStaticPageUrl + ", old = " + oldStaticPageUrl);
		boolean hasChanged = !newStaticPageUrl.equals(oldStaticPageUrl);
		if (hasChanged && oldStaticPageUrl != null && oldStaticPageUrl.trim().length() > 0) {
			// 删除旧的文件。
			String abs_path = this.getUrlResolver().resolvePath(oldStaticPageUrl);
			java.io.File oldFile = new java.io.File(abs_path);
					// pub_ctxt.getRootDir() + "\\"				
					// + oldStaticPageUrl.replace("/", "\\"));
			if (oldFile.exists()) {
				if (oldFile.delete() == false) {
					// TODO: logger delete fail
				}
			}
		}
		synchronized (this) {
			this.staticPageUrl = newStaticPageUrl;
			if (hasChanged) {
				this.isGenerated = false;
			}
		}
		return hasChanged;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PageAttrObject#updateGenerateStatus()
	 */
	public void updateGenerateStatus() {	
		updateGenerateStatus(this.getClass(), pub_ctxt);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PageAttrObject#getPageUrl()
	 */
	public String getPageUrl() {
		return calcPageUrl();
	}
	
	/**
	 * 得到新的静态化地址，返回的地址基于其父对象。
	 * @return 返回静态化的页面地址，必须非空。
	 */
	protected abstract String getNewStaticPageUrl(PublishContext pub_ctxt);

	/**
	 * 派生类必须实现的。返回对象的页面的地址。
	 *   页面地址的约定：返回的地址是绝对地址，如 '/PubWeb/news/xxx.html', 'http://localhost/PubWeb/soft/showArticle.jsp?x'
	 *   根据 site 中设置的 '链接地址方式(siteUrlType)' 返回不同形式的链接地址。
	 * @param 发布环境上下文。
	 * @return
	 */
	protected abstract String calcPageUrl();
	
	/**
	 * 更新生成状态（线程安全）
	 * @param className 类别
	 * @param isGenerated 是否生成
	 * 派生类也许自己有特定的修改方式。
	 */
	@SuppressWarnings("rawtypes")
	protected void updateGenerateStatus(Class pageClass, PublishContext pub_ctxt) {
		// 获得要更新的类的名字。
		String className = pageClass.getName();
		className = StringHelper.capFirst(className.substring(className.lastIndexOf(".") + 1));
		
		// 更新数据库
		String hql = "UPDATE " + className + " SET isGenerated=:isGenerated, staticPageUrl=:staticPageUrl WHERE id=:id";
		UpdateHelper updator = new UpdateHelper();
		updator.updateClause = hql;
		updator.setBoolean("isGenerated", isGenerated);
		updator.setString("staticPageUrl", staticPageUrl);
		updator.setInteger("id", getId());
		
		// int update_num = 
		updator.executeUpdate(pub_ctxt.getDao());
	}

	// === DynamicAttributeSupport 接口实现 =================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.DynamicAttributeSupport#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		this._getExtendPropertySet().setAttribute(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.DynamicAttributeSupport#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		this._getExtendPropertySet().removeAttribute(name);
	}
	
	// === 扩展属性支持 =========================================================
	
	/** 扩展属性集合包。 */
	private ExtendPropertySet ext_props;
	
	/**
	 * 获得扩展属性集合。
	 * @return
	 */
	public ExtendPropertySet getExtends() {
		return this._getExtendPropertySet();
	}
	
	/** 获得扩展属性包，如果还没有创建则创建它。 */
	private ExtendPropertySet _getExtendPropertySet() {
		if (ext_props == null) {
			synchronized (this) {
				if (ext_props == null)
					ext_props = new ExtendPropertySet(this, _getPublishContext());
			}
		}
		return ext_props;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractNamedModelBase#getObjectUuid()
	 */
	@Override public String getObjectUuid() {
		return super.getObjectUuid();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.ExtendPropertySupport#hintPropNum()
	 */
	public int hintPropNum() {
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.ExtendPropertySupport#propNumChanged(int)
	 */
	public void propNumChanged(int num) {
		synchronized (this) {
			this.ext_props = null;
		}
	}
}
