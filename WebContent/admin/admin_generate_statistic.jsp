<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.engine.GenerateEngine"
%><%@page import="com.chinaedustar.publish.*"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
  <title>生成统计</title>
  <meta http-equiv="refresh" content="3; url=admin_generate_statistic.jsp" />
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
 </head>
<body style="padding:0px;margin:0px">
<h3>生成器的信息</h3>
<%
PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
GenerateEngine engine = pub_ctxt.getGenerateEngine();
String[] last_msg = engine.getLastMessage();
for (int i = 0; i < last_msg.length; ++i)
  out.println("<li>" + last_msg[i]);
if (last_msg.length == 0)
  out.println("<li>no message");
%>
</body>
</html>
