<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.RecycleAction" />
</jsp:forward>

<%
  // debug info
  if (false) {
    com.chinaedustar.publish.action.RecycleAction action = 
      new com.chinaedustar.publish.action.RecycleAction();
    out.println("<hr><li>action = " + action);
  }
%>