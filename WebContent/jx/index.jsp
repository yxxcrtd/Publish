<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@page import="com.chinaedustar.publish.admin.PageHandler"%>

<% 
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.createChannelIndexJsp();
  out.write(result);
%>