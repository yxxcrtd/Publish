<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.*"
%><%@page import="com.chinaedustar.publish.model.*"
%><%
ParamUtil paramUtil = new ParamUtil(pageContext);
int channelId = paramUtil.safeGetIntParam("channelId");
int photoId = paramUtil.safeGetIntParam("photoId");
String method = request.getMethod();
// 如果是 POST 方法，得到 post 过来的文章对象的数据；

// 如果是 GET 方法，根据 photoId 得到从频道的栏目中得到文章对象的数据。

Photo photo = null;
if ("POST".equalsIgnoreCase(method)) {
	photo = new Photo();
	//photo.setContent(paramUtil.getRequestParam("content"));
	photo.setAuthor(paramUtil.getRequestParam("author"));
	photo.setSource(paramUtil.getRequestParam("source"));
	photo.setHits(paramUtil.safeGetIntParam("hits"));
	photo.setInputer(paramUtil.getRequestParam("inputer"));
	photo.setShortTitle(paramUtil.getRequestParam("shortTitle"));
	photo.setTitle(paramUtil.getRequestParam("title"));
} else {
	Channel channel = paramUtil.getPublishContext().getSite().getChannels().getChannel(channelId);
	photo = (Photo)channel.getColumnTree().getSimpleColumn(channel.getRootColumnId()).loadItem(photoId);
}
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>软件预览</title>
</head>
<body bgcolor="#eeeeee">
<br />
<div align="center">
<h3><%=photo.getTitle() %></h3>
</div>
<div align="center">
<font size="3"><%=photo.getShortTitle() %></font>
</div>
<div align="center">
<br />
作者：<%=photo.getAuthor() %>
转贴自：<%=photo.getSource() %>
点击数：<%=photo.getHits() %>
软件录入：<%=photo.getInputer() %>
<br />
</div>
<div align="left">

</div>
<div align="center">
<br />
<a href="javascript:window.close();">〔关闭窗口〕</a>
</div>

</body>
</html>