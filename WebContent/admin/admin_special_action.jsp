<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.SpecialAction" />
</jsp:forward>

<%
// 用于测试。
if (false) {
  com.chinaedustar.publish.action.SpecialAction action = new com.chinaedustar.publish.action.SpecialAction();
  System.out.println("action = " + action);
}
%>