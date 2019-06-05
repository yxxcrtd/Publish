<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.UserManage"
%><%
  Object user = com.chinaedustar.publish.PublishUtil.getCurrentUser(session);
  if (user == null) {
    response.sendRedirect("user_login.jsp");
    return;
  }
  UserManage manager = new UserManage(pageContext);
  manager.initUserHome();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>会员控制面板</title>
<link href="defaultSkin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="stm31.js"></script>
</head>
<body>

<%@ include file="element_user.jsp" %>
<pub:template name="main">
 #{call tmpl_top }
 <table width="756" align="center" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
  #{call tmpl_body }
  </td>
  </tr>
 </table>

 <br />
 #{call tmpl_bottom }
</pub:template>

<!-- 主体部分。 -->
<pub:template name="tmpl_body">
<table width="100%" align="center" border="0" cellpadding="5" cellspacing="0" class="user_box">
  <tr>
    <td class="user_righttitle"><img src="images/point2.gif" align="absmiddle">
    您现在的位置：<a href='../'>#{site.name }</a> &gt;&gt; <a href='index.jsp'>会员中心</a> &gt;&gt; 首页</td>
  </tr>
  <tr>
    <td height="200" valign='top'>

　　#{user.userName }，您好！欢迎您进入本网站客户自助管理系统！为使您正常使用本系统功能及获得最佳界面浏览效果，请您将IE浏览器升级为6.0，屏幕分辨率设置为1024*768。<br>

  <br>
  <br />
  <div align="center">当前用户：#{user.userName }</div>
  <div align="center">当前状态：#{iif (user.inputer, "允许投稿", "不允许投稿") }</div>
 </td>
 </tr>
</table>

</pub:template>

<pub:process_template name="main" />
</body>
</html>