<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@page import="com.chinaedustar.publish.admin.StatManage"
%><%
  StatManage manager = new StatManage(pageContext);
  manager.statOnline();
%><jsp:forward page="images/blank.gif"></jsp:forward>