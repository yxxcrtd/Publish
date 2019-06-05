<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%
// 用于测试，以及引用 PhotoAction 以检查错误.
if (false) {
  com.chinaedustar.publish.action.PhotoAction action = new com.chinaedustar.publish.action.PhotoAction();
  out.println("<li>action = " + action);
}
%>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.PhotoAction" />
</jsp:forward>
