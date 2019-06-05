<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.SiteAction" />
</jsp:forward>

<%
  if (false) {
    com.chinaedustar.publish.action.SiteAction action = new com.chinaedustar.publish.action.SiteAction();
    out.println("action = " + action);
  }
%>