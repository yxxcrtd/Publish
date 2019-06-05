<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><% 
  // 创建频道推荐项目。
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.showCommendPage();
  out.write(result);
%>