package com.chinaedustar.publish.model;

import com.chinaedustar.publish.*;
import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.pjo.Special;

/**
 * 专题对象模型。
 * 
 * TODO: 专题对象的静态化地址不正确，没有给出其频道的地址。
 * 
 * @author liujunxing
 */
public class SpecialWrapper extends AbstractPageModel2<Special> {
	/**
	 * 构造函数。
	 *
	 */
	public SpecialWrapper(Special special) {
		super(special);
	}

	/**
	 * 构造函数。
	 *
	 */
	public SpecialWrapper(Special special, PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super(special, pub_ctxt, owner_obj);
	}

	// === getter ============================================================
	
	/**
	 * 得到专题所属的频道标识。关联到 Channel 对象。
	 * @return
	 */
	public int getChannelId() {
		return target.getChannelId();
	}
	
	/**
	 * 得到专题的描述信息。
	 * @return
	 */
	public String getDescription() {
		return target.getDescription();
	}
	
	/**
	 * 得到专题排序。
	 * @return
	 */
	public int getSpecialOrder() {
		return target.getSpecialOrder();
	}
	
	/**
	 * 得到专题目录：限定为英文名。
	 * @return
	 */
	public String getSpecialDir() {
		return target.getSpecialDir();
	}
	
	/**
	 * 得到专题的tips提示信息。
	 * @return
	 */
	public String getTips() {
		return target.getTips();
	}
	
	/**
	 * 是否推荐，默认为 false（不推荐）。
	 * @return
	 */
	public boolean getIsElite() {
		return target.getIsElite();
	}
	
	/**
	 * 得到专题图片的URL。
	 * @return
	 */
	public String getSpecialPicUrl() {
		return target.getSpecialPicUrl();
	}
	
	/**
	 * 专题的打开方式，1：新窗口打开；0：原窗口打开。默认为1。
	 * @return
	 */
	public OpenType getOpenType() {
		return OpenType.fromInteger(target.getOpenType());
	}
	
	/**
	 * 获取每页显示的项目数量。
	 * @return
	 */
	public int getMaxPerPage() {
		return target.getMaxPerPage();
	}
	
	// === 相关对象 =======================================================================
	
	/**
	 * 获得此专题所属频道对象, 如果此专题没有所属频道则返回 null.
	 */
	public Channel getChannel() {
		if (target.getChannelId() == 0) return null;	// 没有所属频道
		if (this.pub_ctxt == null) return null;	// 未初始化所以无法获得
		
		return pub_ctxt.getSite().getChannel(target.getChannelId());
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractModelBase#getParent()
	 */
	@Override public ModelObject getParent() {
		// 如果是全站专题，则父对象是 Site，否则是 Channel.
		if (pub_ctxt == null) return null;
		Site site = pub_ctxt.getSite();
		if (target.getChannelId() == 0) return site;
		
		Channel channel = site.getChannel(target.getChannelId());
		return (channel == null) ? site : channel;
	}
	
	// === 专题对象的业务实现 ===============================================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#calcPageUrl()
	 */
	public String calcPageUrl() {
		String page_url = "";
		Site site = pub_ctxt.getSite();
		if (target.getChannelId() <= 0) {			// 如果是全站专题
			if (target.getIsGenerated()) {
				page_url = target.getStaticPageUrl();
			} else {
				page_url = "showSpecial.jsp?specialId=" + getId();
			}
			return site.resolveUrl(page_url);
		} else {
			Channel channel = this.getChannel();
			if (channel.getNeedGenerateSpecial()) {
				page_url = target.getStaticPageUrl();
			} else {
				page_url = "showSpecial.jsp?specialId=" + getId();
			}
			return channel.resolveUrl(page_url);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#getNewStaticPageUrl(com.chinaedustar.publish.PublishContext)
	 */
	protected String getNewStaticPageUrl() {
		Site site = pub_ctxt.getSite();
		if (site.getNeedGenerate() == false) return "";
		
		if (target.getChannelId() <= 0) {		// 全站专题
			return "special/" + target.getSpecialDir() + "/index" + AbstractPageModelBase.getFileExtName(site.getFileExt_SiteSpecial());
		} else {					// 频道的专题
			Channel channel = this.getChannel();
			if (channel != null) {
				return "special/" + target.getSpecialDir()
					+ "/index" + AbstractPageModelBase.getFileExtName(channel.getFileExtList());
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractModelBase#getUrlResolver()
	 */
	@Override public UrlResolver getUrlResolver() {
		// parent 要么是 Site 要么是 Channel, 总之负责解析 Url.
		return (UrlResolver)this.getParent();
	}
}
