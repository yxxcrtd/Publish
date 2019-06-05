<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><% 
  // 产生图片显示页面。
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.showPhotoJsp();
  out.write(result);
%>