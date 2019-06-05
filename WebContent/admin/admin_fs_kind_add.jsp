<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.FriendSiteManage"
%><%
  // 管理数据初始化,
  FriendSiteManage admin_data = new FriendSiteManage(pageContext);
  admin_data.initKindAddPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>友情链接类别管理</title>
</head>
<body>

<pub:template name="main">
 #{call fs_admin_help}
 #{call fs_kind_form}
</pub:template>

<pub:template name="fs_kind_form">
<form name='myform' method='post' action='admin_friendsite_action.jsp'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border' >
 <tr class='title'>
  <td height='22' colspan='2' align='center'><strong>#{iif(fs_kind.id >0, "修改", "添加") }友情链接类别</strong></td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg'><strong>类别名称：</strong></td>
  <td class='tdbg'><input name='kindName' type='text' id='KindName' size='49' maxlength='30' value='#{fs_kind.kindName}'></td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg'><strong>类别说明</strong><br>鼠标移至类别名称上时将显示设定的说明文字（不支持HTML）</td>
  <td class='tdbg'><textarea name='kindDesc' cols='40' rows='5' id='KindDesc' type="_moz">#{fs_kind.kindDesc@html}</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td colspan='2' align='center' class='tdbg'>
   <input name='command' type='hidden' id='Action' value='saveKind'>
   <input name='id' type='hidden' value='#{fs_kind.id}' />
   <input  type='submit' name='Submit' value=' #{iif(fs_kind.id >0, "修 改", "添 加") } '>&nbsp;&nbsp;
   <input name='Cancel' type='button' id='Cancel' value=' 取 消 ' onclick="window.location.href='admin_fs_kind.jsp'" style='cursor:hand;'>
  </td>
 </tr>
</table>
</form>
</pub:template>

<%@ include file="element_fs.jsp" %>

<pub:process_template name="main" />

</body>
</html>
