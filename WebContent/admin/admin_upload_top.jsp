<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.UploadManage" %><%
%><%@page import="com.chinaedustar.publish.model.Channel" %><%
  UploadManage manager = new UploadManage(pageContext);
  Channel channel = manager.getCurrentChannel();
  String channel_name = channel == null ? "" : channel.getName();
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title></title>
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width='100%' border='0' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='10'>
    <table width='100%'>
      <tr class='topbg'>
        <td align='center'><b><%=channel_name%>管理----上传文件管理</b></td>
        <td width='60' align='right'><a href='help/index.jsp'
          target='_blank'><img src='images/help.gif' border='0'></a></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr class='tdbg'>
    <td height='30'><strong>管理说明：</strong>&nbsp;请打开左边上传目录导航，以便您更快捷的管理上传目录中的文件。</td>
  </tr>
</table>
</body>
</html>