<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.*"
%><%@page import="com.chinaedustar.publish.model.*"
%><%
ParamUtil paramUtil = new ParamUtil(pageContext);
int channelId = paramUtil.safeGetIntParam("channelId");
int articleId = paramUtil.safeGetIntParam("articleId");
String method = request.getMethod();
// 如果是 POST 方法，得到 post 过来的文章对象的数据；

// 如果是 GET 方法，根据 articleId 得到从频道的栏目中得到文章对象的数据。

Article article = null;
if ("POST".equalsIgnoreCase(method)) {
	article = new Article();
	article.setContent(paramUtil.getRequestParam("content"));
	article.setAuthor(paramUtil.getRequestParam("author"));
	article.setSource(paramUtil.getRequestParam("source"));
	article.setHits(paramUtil.safeGetIntParam("hits"));
	article.setInputer(paramUtil.getRequestParam("inputer"));
	article.setShortTitle(paramUtil.getRequestParam("shortTitle"));
	article.setTitle(paramUtil.getRequestParam("title"));
} else {
	Channel channel = paramUtil.getPublishContext().getSite().getChannels().getChannel(channelId);
	article = (Article)channel.getColumnTree().getSimpleColumn(channel.getRootColumnId()).loadItem(articleId);
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>文章预览</title>
</head>
<body bgcolor="#eeeeee">
<br />
<div align="center">
<h3><%=article.getTitle() %></h3>
</div>
<div align="center">
<font size="3"><%=article.getShortTitle() %></font>
</div>
<div align="center">
<br />
作者：<%=article.getAuthor() %>
转贴自：<%=article.getSource() %>
点击数：<%=article.getHits() %>
文章录入：<%=article.getInputer() %>
<br />
</div>
<div align="left">
<%=article.getContent() %>
</div>
<div align="center">
<br />
<a href="javascript:window.close();">〔关闭窗口〕</a>
</div>

</body>
</html>