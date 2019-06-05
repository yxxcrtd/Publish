<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.model.AdminCollection"
%><%@page import="com.chinaedustar.publish.PublishContext"
%><%@page import="com.chinaedustar.publish.PublishUtil"
%><%
// 验证管理员名称 
String name = request.getParameter("adminName");
PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
AdminCollection admin_coll = pub_ctxt.getSite().getAdminCollection();
out.println(admin_coll.existAdmin(name));
%>