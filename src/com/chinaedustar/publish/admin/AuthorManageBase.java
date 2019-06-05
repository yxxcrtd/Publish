package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.itfc.ChannelContainer;
import com.chinaedustar.publish.model.*;

/**
 * 提供给 AuthorManage, KeywordManage, SourceManage 做为基类。
 * 
 * @author liujunxing
 *
 */
abstract class AuthorManageBase extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public AuthorManageBase(PageContext pageContext) {
		super(pageContext);
	}
	
	/** 所在频道。 */
	protected ChannelContainer channel;
	
	/** 提供当前频道和频道列表数据。 */
	protected void provideChannelAndList() {
		// 放置 container, channel 对象到 pageContext 中
		this.channel = (ChannelContainer)super.getChannelOrSite();
		if (channel == null) throw super.exChannelUnexist();
		
		setTemplateVariable("container", channel);
		if (channel instanceof Channel)
			setTemplateVariable("channel", channel);

		// 频道集合。
		Object channel_list = super.getManagedChannelList();
		setTemplateVariable("channel_list", channel_list);
	}
}
