<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><% 
  // 创建出投票页并输出。
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.showVotePage();
  out.write(result);
%>

<%@ include file='admin/_debug_req_param.jsp' %>