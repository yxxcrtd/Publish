<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.action.LoginAction"
%><%@page import="com.chinaedustar.publish.Result"
%><%
  LoginAction action = new LoginAction(pageContext);
  Result result = action.login();
  if (result.getCode() == 0) {   // success
    response.sendRedirect("admin_index.jsp");
    return;
  }
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>登录页面</title>
</head>
<body>
<% if (result.getCode() == LoginAction.INVALID_CHECKCODE) {%>
  <script type='text/javascript'>
  alert('验证码输入不正确，请重新填写。');
  window.history.back();
  </script>
<% } else if (result.getCode() == LoginAction.USER_OR_PASSWORD_ERROR) { %>
  <script type='text/javascript'>
  alert('用户名或密码不正确，请重新登录。');
  window.location = 'admin_login.jsp'
  </script>
<% } %>

</body>
</html>