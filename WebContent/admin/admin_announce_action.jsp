<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%
  if (false) {
    com.chinaedustar.publish.action.AnnounceAction action = new com.chinaedustar.publish.action.AnnounceAction();
    System.out.println("action = " + action);
  }
  
%><jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.AnnounceAction" />
</jsp:forward>