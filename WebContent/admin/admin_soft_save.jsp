<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<jsp:forward flush="true" page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.SoftSaveAction" />
</jsp:forward>
