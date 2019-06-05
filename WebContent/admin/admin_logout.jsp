<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.action.LoginAction"
%><%
  LoginAction action = new LoginAction(pageContext);
  action.logout();
%>
<a href='../'>[返回主页]</a>
