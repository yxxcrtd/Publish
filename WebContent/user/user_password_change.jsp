<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.UserManage"
%><%
  UserManage manager = new UserManage(pageContext);
  manager.initUserHome();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>会员控制面板</title>
<link href="defaultSkin.css" rel="stylesheet" type="text/css">
<link href="../admin/admin_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="stm31.js"></script>
<script type="text/javascript" src="../admin/main.js"></script>
<script type="text/javascript">
<!--
function checkForm() {
 if (byId("password").value != byId("pwdConfirm").value) {
  alert("新密码与确认密码不一致，请重新输入！");
  byId("password").focus();
  byId("password").select();
  return false;
 } else {
  return true;
 }
}
// -->
</script>
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
    您现在的位置：<a href='../'>#{site.name }</a> >> <a href='index.jsp'>会员中心</a> >> 修改密码</td>
  </tr>
  <tr>
    <td height="200" valign='top'>
 #{call tmpl_body_content }
 </td>
  </tr>
</table>
</pub:template>

<pub:template name="tmpl_body_content">
<form name='myform' action='../admin/admin_user_action.jsp' method='post' onsubmit="return checkForm();">
  <table width='400' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
    <tr align='center' class='title'>
      <td height='22' colSpan='2'><b>修 改 密 码</b></td>
    </tr>
    <tr class='tdbg'>
      <td width='120' align='right' class='tdbg5'>用 户 名：</td>
      <td>#{user.userName }</td>
    </tr>
    <tr class='tdbg'> 
      <td width='120' align='right' class='tdbg5'>原 密 码：</td>
      <td><input name='oldPassword' type='password' maxLength='16' size='30'></td>
    </tr>
    <tr class='tdbg'>
      <td width='120' align='right' class='tdbg5'>新 密 码：</td>
      <td> <input name='password' type='password' maxLength='16' size='30'> </td>
    </tr>
    <tr class='tdbg'>
      <td width='120' align='right' class='tdbg5'>确认密码：</td>
      <td><input name='pwdConfirm' type='password' maxLength='16' size='30'>
      </td>
    </tr>
    <tr align='center' class='tdbg'>
      <td height='40' colspan='2'>
        <input name='userName' type='hidden' id='userName' value='#{user.userName }'>
        <input name='userId' type='hidden' id='userId' value='#{user.id }'>
        <input name='command' type='hidden' value='modify_password' />
        <input type='submit' id='Submit' value=' 保 存 '>
      </td>
    </tr>
  </table>
</form>
</pub:template>

<pub:process_template name="main"/>
</body>
</html>