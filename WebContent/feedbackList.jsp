<%@page language="java" contentType="text/html; charset=gb2312" 
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><% 
  PageHandler handler = new PageHandler(pageContext);
  String result = handler.showHomeGeneralPage("feedback_list", null);
  out.write(result);

%>