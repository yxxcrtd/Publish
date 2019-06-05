<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.admin.UserManage"
%><%
  // 初始化管理数据。
  UserManage manager = new UserManage(pageContext);
  boolean result = manager.checkUserExistPage();
  out.println(result);
%>