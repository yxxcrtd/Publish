<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><% 
  // 创建出搜索页并输出.
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.showSearchPage();
  out.write(result);

%>