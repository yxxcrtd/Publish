<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.chinaedustar.publish.PublishUtil"%>
<%@page import="com.chinaedustar.publish.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>会员退出登录</title>
</head>
<body>
<%
ParamUtil paramUtil = new ParamUtil(pageContext);
String returnUrl = paramUtil.getRequestParam("returnUrl");
session.setAttribute(PublishUtil.USER_USERNAME, null);
if (returnUrl == null || returnUrl.trim().length() < 1) {
	response.sendRedirect(paramUtil.getPublishContext().getSite().getInstallDir() + "user/user_login.jsp");
} else {
	response.sendRedirect(returnUrl);
}
%>
</body>
</html>