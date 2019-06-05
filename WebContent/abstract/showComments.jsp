<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="java.util.*" %>
<%@page import="com.chinaedustar.publish.model.*" %>
<%@page import="com.chinaedustar.publish.*" %>
<%
/*
	ParamUtil putil = new ParamUtil(pageContext);
	PublishContext pub_ctxt = putil.getPublishContext();
	HashMap vars = new HashMap();
	Site site = pub_ctxt.getSite();
	Channel channel = site.getChannels().getChannel(request.getRequestURI());
	int columnId = putil.safeGetIntParam("columnId", channel.getRootColumnId());
	Column column = null;
	if (columnId == channel.getRootColumnId()) {
		column = channel.getColumnTree().getSimpleColumn(columnId);
	} else {
		column = channel.getColumnTree().getColumn(columnId);
	}
	int articleId = putil.safeGetIntParam("itemId", 0);
	Article article = (Article)column.getSimpleItem(articleId);
	article.setTitle(column.getItemTitle(articleId));
	//查询是否允许评论
	boolean allowComment = column.allowComment(articleId);
	if (!allowComment) {
		out.print("<script type='text/javascript'>alert('该文章不允许评论，请确认您是通过正常链接进入此页面！');</script>");
		return;
	}
	
	boolean showComment = putil.safeGetBooleanParam("showComment", false);
	
	vars.put("site", site);
	vars.put("channel", channel);
	vars.put("column", column);
	vars.put("article", article);
	vars.put("item", article);
	vars.put("this", article);
	
	vars.put("showComment", showComment);
	vars.put("columnId", columnId);
	vars.put("itemId", articleId);
	vars.put("formId", PublishUtil.getFormValidId(session));
	//是否为会员
	vars.put("isMember", PublishUtil.getCurrentUser(session) != null);
	PublishUtil.initPaginationInfo(pageContext);
	
	pageContext.setAttribute("channel",vars.get("channel"));

	<pub:data var="templ_content" param="article_comments" scope="page"
		provider="com.chinaedustar.publish.admin.TemplateContentDataProvider"/>	

	*/
%>

TODO:
<%
/*
  String content = (String)pageContext.getAttribute("templ_content");

  // 2. 执行模板。
  String result = PublishUtil.showTemplatePage(pub_ctxt, content, vars);
  out.write(result);
  */
 %>
