<%@page language="java" contentType="text/html; charset=GB2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.admin.UserManage"
%><%@page import="com.chinaedustar.publish.Result"
%><%
  // 初始化页面.
  UserManage user_manage = new UserManage(pageContext);
  Result result = user_manage.userLogin();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>验证用户登录</title>
</head>
<body>

<% if (result.getCode() == 0) { %>
  登录成功
<% } else { %>
<script type="text/javascript">
<!--
  alert("<%=result.getTitle()%>");
  history.go(-1);
// -->
</script>
<% } %>
</body>
</html>
