package com.chinaedustar.publish.action;

import java.util.Date;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.model.Article;
import com.chinaedustar.publish.model.Item;

/**
 * 文章管理的操作。
 * 
 * @author liujunxing
 *
 */
public class ArticleAction extends AbstractItemAction {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	public void execute() throws Exception {
		String command = param_util.safeGetStringParam("command");
		
		if ("save".equalsIgnoreCase(command))
			save();
		else
			// 其它如 delete, top, elite 等命令由基类统一执行。
			super.executeCommand(command);
	}
	
	/**
	 * 保存一个新闻。
	 *
	 */
	private void save() {
		// 1. 获得频道参数。
		if (super.getChannelData() == false) return;

		// 2. 获得文章对象。
		Article article = collectArticleData();
		// String oper = article.getId() == 0 ? "新增" : "更新";
		
		// 检查权限。
		if (checkSaveItemRight(article) == false) return;

		// 保存数据。
		tx_proxy.saveItem(channel, article);
		saveLatestUserSource(article);
		
		// 添加更新指令到队列
		@SuppressWarnings("unused")
		boolean createImmediate = param_util.safeGetBooleanParam("createImmediate", false);

		// 设置返回信息，使用特定的显示模板 'article_add_success'。
		// messages.add(channel.getItemName() + " '" + article.getTitle() + "' " + oper + "成功。");
		// links.add(getBackActionLink());
		page_ctxt.setAttribute("channel", channel);
		page_ctxt.setAttribute("article", article);
		super.message_template_type = "article_add_success";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractItemAction#getChannelAndItem()
	 */
	@Override protected boolean getChannelAndItem() {
		// 获得频道。
		if (this.getChannelData() == false) return false;

		// 获得文章对象。
		int articleId = param_util.safeGetIntParam("articleId");
		Article article = channel.loadArticle(articleId);
		if (article == null) {
			messages.add("指定标识 id = " + articleId + " 的项目不存在。");
			links.add(getBackActionLink());
			return false;
		}
		if (article.getChannelId() != channel.getId()) {
			messages.add("项目 '" + article.getTitle() + "' 并不在当前频道中，请确定您是从有效链接进入的。");
			links.add(getBackActionLink());
			return false;
		}
		this.item = article;
		
		return true;
	}
	
	// 收集文章数据。
	private Article collectArticleData() {
		// 根据标识新建或加载现有文章。
		int articleId = param_util.safeGetIntParam("articleId");
		Article article = null;
		if (articleId > 0) {
			article = channel.loadArticle(articleId);
			if (article == null) throw new PublishException("无法找到指定标识的文章对象。");
			if (article.getChannelId() != channel.getId())
				throw new PublishException("项目所在的频道不是当前频道，请确定您是从有效的链接进入的。");
		} else {
			article = new Article();
		}

		// 从提交的数据中收集属性。
		// Item 通用属性。
		article.setChannelId(channel.getId());
		article.setColumnId(safeGetColumnId(channel));
		article.setName(param_util.safeGetStringParam("name"));
		
		article.setStatus(param_util.safeGetIntParam("status"));
		if (article.getStars() == Item.STATUS_APPR) {
			article.setEditor(super.safeGetAdminName());
		} else {
			article.setEditor(null);
		}		
		article.setStars(param_util.safeGetIntParam("stars"));
		article.setTop(param_util.safeGetBooleanParam("top", false));
		article.setCommend(param_util.safeGetBooleanParam("commend", false));
		article.setElite(param_util.safeGetBooleanParam("elite", false));
		article.setHot(param_util.safeGetBooleanParam("hot", false));
		article.setHits(param_util.safeGetIntParam("hits"));
		article.setDeleted(param_util.safeGetBooleanParam("deleted", false));			
		article.setTemplateId(param_util.safeGetIntParam("templateId"));
		article.setSkinId(param_util.safeGetIntParam("skinId"));
		article.setPrivilege(param_util.safeGetIntParam("privilege"));
		article.setCharge(param_util.safeGetIntParam("charge"));
		article.setCustom(param_util.safeGetIntParam("custorm"));
		article.setVoteFlag(param_util.safeGetIntParam("voteFlag"));
		article.setBlogFlag(param_util.safeGetIntParam("blogFlag"));
		article.setBbsFlag(param_util.safeGetIntParam("bbsFlag"));
		article.setCommentFlag(param_util.safeGetIntParam("commentFlag"));
		article.setTitle(param_util.safeGetStringParam("title"));
		article.setShortTitle(param_util.safeGetStringParam("shortTitle"));
		article.setAuthor(param_util.safeGetStringParam("author"));
		article.setSource(param_util.safeGetStringParam("source"));
		if (article.getId() == 0)
			article.setInputer(super.safeGetAdminName());

		article.setEditor(param_util.safeGetStringParam("editorName"));
		article.setKeywords(param_util.safeGetStringParam("keywords"));
		article.setDescription(param_util.safeGetStringParam("description"));		
		article.setLastModified(new Date());	// 最后修改时间。
		article.setCreateTime(param_util.safeGetDate("createTime", new java.util.Date()));
				
		// 文章的特定属性。
		article.setSubheading(param_util.safeGetStringParam("subheading"));
		article.setContent(param_util.safeGetStringParam("content"));
		article.setIncludePic(param_util.safeGetIntParam("includePic"));
		article.setDefaultPicUrl(param_util.safeGetStringParam("defaultPicUrl"));
		article.setUploadFiles(param_util.safeGetStringParam("uploadFiles", ""));
		article.setTitleFontColor(param_util.safeGetStringParam("titleFontColor"));
		article.setTitleFontType(param_util.safeGetIntParam("titleFontType"));
		article.setMaxCharPerPage(param_util.safeGetIntParam("maxCharPerPage"));
		article.setPaginationType(param_util.safeGetIntParam("paginationType"));
		
		// 3. 收集所属专题数据。
		article.setSpecialIds(super.collectSpecialIds());

		return article;
	}
}

/*
保存数据提交时候的例子：
Request.Parameters
request.attr[javax.servlet.include.request_uri] = /PubWeb/admin/admin_base_action.jsp 
request.attr[javax.servlet.include.context_path] = /PubWeb 
request.attr[javax.servlet.include.servlet_path] = /admin/admin_base_action.jsp 
request.attr[javax.servlet.include.query_string] = action=com.chinaedustar.publish.action.ArticleAction&debug=true 
request.para[defaultPicUrl][0] = 
request.para[channelId][0] = 1 
request.para[title][0] = g 
request.para[__itemName][0] = 新闻 
request.para[hits][0] = 0 
request.para[skinId][0] = 0 
request.para[command][0] = save 
request.para[columnId][0] = 24 
request.para[DefaultPicList][0] = 不指定首页图片 
request.para[inputer][0] = 未登录测试用 
request.para[createTime][0] = 
request.para[stars][0] = 0 
request.para[shortTitle][0] = f 
request.para[action][0] = com.chinaedustar.publish.action.ArticleAction 
request.para[subheading][0] = 
request.para[add][0] = 添 加 
request.para[includePic][0] = 0 
request.para[articleId][0] = 0 
request.para[commentFlag][0] = 1 
request.para[keywords][0] = 洪灾 
request.para[content][0] = g 
request.para[titleFontColor][0] = 
request.para[status][0] = 0 
request.para[templateId][0] = 0 
request.para[debug][0] = true 
request.para[titleFontType][0] = 0 
request.para[description][0] = f 
request.para[uploadFiles][0] = 
request.para[source][0] = 
request.para[author][0] = 
*/