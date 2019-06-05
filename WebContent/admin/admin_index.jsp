<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp" session="true" %>
<jsp:directive.page import="com.chinaedustar.publish.PublishUtil"/>
<jsp:directive.page import="com.chinaedustar.publish.model.*"/>
<%
// 验证登录。
Admin admin = PublishUtil.getCurrentAdmin(session);
if (admin == null) {
	out.println("<script>window.location = 'admin_login.jsp';</script>");
	return;
}
Site site = PublishUtil.getPublishContext(pageContext).getSite();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title><%=site.getName() %>--后台管理</title>
  </head>
  <frameset rows="*" cols="200,*" framespacing="0" frameborder="0" border="false" id="frame" scrolling="yes">
    <frame name="left" scrolling="yes" marginwidth="0" marginheight="0" src="admin_index_left.jsp">
    <frameset rows="53,*" cols="*" framespacing="0" border="false" rows="35,*" frameborder="0" scrolling="yes">
      <frame name="top" scrolling="no" src="admin_index_top.jsp">
      <frame name="main" scrolling="auto" src="admin_index_main.jsp">
    </frameset>
  </frameset>
  <noframes>
    <body leftmargin="2" topmargin="0" marginwidth="0" marginheight="0">
      <p>您的浏览器版本过低！！！本系统要求IE5及以上版本才能使用本系统。</p>
    </body>
  </noframes>
</html>
