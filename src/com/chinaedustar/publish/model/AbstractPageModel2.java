package com.chinaedustar.publish.model;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.pjo.PagedModelBase;
import com.chinaedustar.publish.util.UpdateHelper;

/**
 * 带有页面属性的模型对象，版本2。
 * @author liujunxing
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractPageModel2 <PJO extends PagedModelBase> extends AbstractNamedModel2<PJO> 
		implements PageAttrObject {
	/**
	 * 构造函数。
	 *
	 */
	protected AbstractPageModel2(PJO target) {
		super(target);
	}
	
	/**
	 * 构造函数。
	 * @param target
	 * @param pub_ctxt
	 * @param owner_obj
	 */
	protected AbstractPageModel2(PJO target, PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super(target, pub_ctxt, owner_obj);
	}
	
	// === getter ===========================================================

	/**
	 * 获得此页面的标题。
	 */
	public String getTitle() {
		return target.getTitle();
	}
	
	/**
	 * 获得此页面的 Logo.
	 * @return
	 */
	public String getLogo() {
		return target.getLogo();
	}
	
	/**
	 * 获得此页面的 Banner.
	 * @return
	 */
	public String getBanner() {
		return target.getBanner();
	}
	
	/**
	 * 获得此页面的 Copyright.
	 * @return
	 */
	public String getCopyright() {
		return target.getCopyright();
	}
	
	/**
	 * 获得此页面的 MetaKey.
	 * @return
	 */
	public String getMetaKey() {
		return target.getMetaKey();
	}
	
	/**
	 * 获得此页面的 MetaDesc.
	 * @return
	 */
	public String getMetaDesc() {
		return target.getMetaDesc();
	}
	
	/**
	 * 获得此页面使用的模板标识。
	 * @return
	 */
	public int getTemplateId() {
		return target.getTemplateId();
	}

	/**
	 * 获得样式标识。
	 * @return
	 */
	public int getSkinId() {
		return target.getSkinId();
	}

	/**
	 * 获取静态化页面是否已经生成
	 * @return
	 */
	public boolean getIsGenerated() {
		return target.getIsGenerated();
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
		return target.getStaticPageUrl();
	}
	
	/** 获得最后一次生成的时间。 */
	public java.sql.Timestamp getLastGenerated() {
		return target.getLastGenerated();
	}
	
	// === PageAttrObject 生成部分接口实现 =========================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PageAttrObject#getPageUrl()
	 */
	public String getPageUrl() {
		return calcPageUrl();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PageAttrObject#rebuildStaticPageUrl()
	 */
	public boolean rebuildStaticPageUrl() {
		// 重新计算静态化地址，如果发生了变化则删除旧的静态化文件。
		String newStaticPageUrl = getNewStaticPageUrl();
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
			target.setStaticPageUrl(newStaticPageUrl);
			if (hasChanged) {
				target.setIsGenerated(false);
			}
		}
		return hasChanged;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PageAttrObject#updateGenerateStatus()
	 */
	public void updateGenerateStatus() {	
		updateGenerateStatus(target.getClass(), pub_ctxt);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.StaticSupportObject#setIsGenerated(boolean)
	 */
	public void setIsGenerated(boolean isGenerated) {
		target.setIsGenerated(isGenerated);
	}

	/**
	 * 得到新的静态化地址，返回的地址基于其父对象。
	 * @return 返回静态化的页面地址，必须非空。
	 */
	protected abstract String getNewStaticPageUrl();

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
	protected void updateGenerateStatus(Class pageClass, PublishContext pub_ctxt) {
		// 获得要更新的类的名字。
		String className = pageClass.getName();
		className = StringHelper.capFirst(className.substring(className.lastIndexOf(".") + 1));
		
		// 更新数据库
		String hql = "UPDATE " + className + " SET isGenerated=:isGenerated, staticPageUrl=:staticPageUrl WHERE id=:id";
		UpdateHelper updator = new UpdateHelper();
		updator.updateClause = hql;
		updator.setBoolean("isGenerated", target.getIsGenerated());
		updator.setString("staticPageUrl", target.getStaticPageUrl());
		updator.setInteger("id", getId());
		
		// int update_num = 
		updator.executeUpdate(pub_ctxt.getDao());
	}
}
