<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"%>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.TemplateAction" />
</jsp:forward>

<%
  if (false) {
    com.chinaedustar.publish.action.TemplateAction action = new com.chinaedustar.publish.action.TemplateAction();
    out.println("action = " + action);
  }
%>