package com.chinaedustar.publish.module;

import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Article;
import com.chinaedustar.publish.model.Channel;

/**
 * 文章管理模块的实现类。此模块用于支持新闻、文章等文章性频道。
 * 
 * @author liujunxing
 * @ModuleName = 'ArticleModule'
 */
public class ArticleModule extends AbstractItemModule {
	
	public ArticleModule() {
		super();
	}
	
	
	/**
	 * 获得指定频道的菜单。
	 * @param channel
	 * @return
	 */
	public Menu getChannelMenu(Channel channel, Admin admin) {
		// 如果不具有任何权限则不创建该频道的菜单了。
		int role = admin.getChannelRoleValue(channel.getId());
		if (role == Admin.CHANNEL_ROLE_NONE) return null;
		
		// 利用通用项目菜单创建器进行创建。
		MenuBuilder builder = new MenuBuilder(channel, admin);
		Menu menu = new Menu("article_" + channel.getChannelDir() + "_" + channel.getId(), 
				channel.getName());
		menu.setUrl("admin_channel_guide.jsp?channelId=" + channel.getId());
		builder.initSetMenu(menu);
		
		
		builder.addItemMenu("article");	// '添加文章', '我添加的文章'
		builder.addAritcleItemManageMenu();	// '文章管理', '审核', '生成'
		builder.addSpecialItemMenu("article");	// '专题文章管理'
		builder.addCommentMenu();		// '评论管理'
		builder.addRecycleMenu("article");		// '回收站管理'
		
		builder.addSeperator();			// =======================
		builder.addChannelSetting();	// '新闻中心设置'
		builder.addColumnMenu();		// '栏目管理', '专题管理'
		builder.addUploadMenu();		// '上传文件管理', '清理', '顶部菜单设置', '生成'
		builder.addOtherChannelMenu();	// '模板管理', '关键字管理', '作者管理', '来源管理'
		
		return builder.getMenu();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PublishModule#getModuleName()
	 */
	public String getModuleName() {
		return "ArticleModule";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.PublishModule#getModuleTitle()
	 */
	public String getModuleTitle() {
		return "文章模块";
	}

	// === ChannelModule 实现 ====================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getItemClassName()
	 */
	public String getItemClass() {
		return "Article";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getItemGeneralName()
	 */
	public String getDefaultItemName() {
		return "文章";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getDefaultItemUnit()
	 */
	public String getDefaultItemUnit() {
		return "篇";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#loadItem(int)
	 */
	public Article loadItem(int itemId) {
		return (Article)pub_ctxt.getDao().get(Article.class, itemId);
	}
}
