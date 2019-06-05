<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><% 
  // 创建频道精华项目。
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.showElitePage();
  out.write(result);
%>