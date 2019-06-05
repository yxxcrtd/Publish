<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.admin.UploadManage"
%><%
  UploadManage manager = new UploadManage(pageContext);
  String nv_js = manager.getNavJsString();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>管理导航菜单</title>  
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="JTree.js"></script>
  <script type="text/javascript">
  	window.onload=function(){
  		<%= nv_js%>
  	}
  </script>
  <style>
  	body{background:#F2f2f2; }
  </style>
</head>
<body>	
	<strong>上传目录导航</strong><br/>
	<div id="root"/>
</body>
</html>
