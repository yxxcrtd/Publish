<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><% 
  // 显示热点项目页面。
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.showHotPage();
  out.write(result);
%>