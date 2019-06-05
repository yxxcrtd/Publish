<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.AdminManage"
%><%
  // 初始化页面数据.
  AdminManage admin_data = new AdminManage(pageContext);
  admin_data.initListPageData();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <title>管理员管理</title>
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312' />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 删除选中的管理员。
function deleteAdmins() {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选中任何管理员。");
	} else {
		document.myform.adminIds.value = ids;
		if (confirm('确定要删除选中的管理员吗？')) {
			document.myform.submit();
		}
	}
}
// -->
</script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
<pub:declare>

<%@ include file="element_admin.jsp" %>
<%@ include file="element_keyword.jsp" %>

<pub:template name="main">
#{call admin_manage_navigator }<br />
#{call tmpl_admin_list }
#{call keyword_pagination_bar(page_info) }
</pub:template>


<pub:template name="tmpl_admin_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr align='center' class='title' height='22'>
  <td width='30'><strong>选中</strong></td>
  <td width='30' height='22'><strong>序号</strong></td>
	<td><strong>管理员名</strong></td>
	<td><strong>前台会员名</strong></td>
	<td width='70'><strong>权 限</strong></td>
	<td width='95'><strong>最后登录IP</strong></td>
	<td width='115'><strong>最后登录时间</strong></td>
	<td width='180'><strong>操 作</strong></td>
 </tr>
 #{foreach admin in admin_list }
 <tr align='center' class='tdbg' onmouseout="this.className='tdbg'"
		onmouseover="this.className='tdbgmouseover'">
	<td width='30'>
		<input name='itemId' type='checkbox' value='#{admin.id }'>
	</td>
	<td width='30'>#{admin.id }</td>
	<td>
	 #{if (admin.adminType == 1) }
		<font color=red><b>#{admin.adminName }</b></font>
	 #{else }
		#{admin.adminName }
	 #{/if }
	</td>
	<td>#{admin.userName }</td>
	<td width='70'>
	 #{if (admin.adminType == 1) }
		<font color=blue>超级管理员</font>
	 #{elseif (admin.adminType == 2) }
		<font color=blue>普通管理员</font>
	 #{else }
		<font color=red>无任何权限</font>
	 #{/if }
	</td>
	<td width='95'>#{admin.lastLoginIp }</td>
	<td width='115'>#{admin.lastLoginTime }</td>
	<td width='180'>
	 <a href='admin_admin_add.jsp?adminId=#{admin.id }'>修改密码及设置</a>&nbsp;&nbsp;
	#{if (admin.adminType > 1) }
	 <a href='admin_admin_right.jsp?adminId=#{admin.id }'>修改权限</a>&nbsp;&nbsp;
	#{elseif (admin.adminType == 0) }
	 <a href='admin_admin_right.jsp?adminId=#{admin.id }'>设置权限</a>&nbsp;&nbsp;
	#{/if }
	</td>
 </tr>
 #{/foreach }
</table>
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td width='200' height='30'><input name='chkAll'
		type='checkbox' id='chkAll' onclick='CheckAll(this)'
		value='checkbox'> 选中本页显示的所有管理员</td>
  <td><input name='Action' type='hidden' id='Action' value='Del'>
		<input type='button' value='删除选中的管理员' onclick="deleteAdmins();"></td>
 </tr>
</table>

<form name='myform' method='post' action='admin_admin_action.jsp'>
 <input type="hidden" name="command" value='delete' />
 <input type="hidden" name="adminIds" />
</form>
</pub:template>
</pub:declare>

<pub:process_template name="main"/>

</body>
</html>
