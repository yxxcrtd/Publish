<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%
  String channelId = request.getParameter("channelId");
  if (channelId == null) channelId = "1";
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>上传文件管理</title>
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<frameset framespacing="0" border="false" rows="70,*" frameborder="0" scrolling="yes">
    <frame name="UploadFile_top" scrolling="no" src="admin_upload_top.jsp?channelId=<%=channelId %>">
    <frameset rows="*" cols="0,*" framespacing="0" frameborder="0" border="false" id="frame" scrolling="yes">
        <frame name="UploadFile_left" scrolling="auto" src="admin_upload_nav.jsp?channelId=<%=channelId %>">
        <frame name="UploadFile_Main" scrolling="auto" src="admin_upload_list.jsp?channelId=<%=channelId %>">
    </frameset>
</frameset>
<noframes>
  <body leftmargin="2" topmargin="0" marginwidth="0" marginheight="0">
  <p>你的浏览器版本过低！！！本系统要求IE5及以上版本才能使用本系统。</p>
  </body>
</noframes>
</html>