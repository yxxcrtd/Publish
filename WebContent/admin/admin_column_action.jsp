<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.ColumnAction" />
</jsp:forward>

<%
// FOR DEBUG
if (false) {
  com.chinaedustar.publish.action.ColumnAction action = new com.chinaedustar.publish.action.ColumnAction();
  System.out.println("action = " + action);
}
%>