<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.FriendSiteManage"
%><%
  // 初始化页面数据.
  FriendSiteManage manage = new FriendSiteManage(pageContext);
  manage.userSaveReg();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 
<pub:template name="main">
<html> 
  #{call comment_head }
<body>
<br /><br />
  #{if error_list == null || error_list@size == 0 }
  	#{call fs_success }
  #{else }
  	#{call fs_error }
  #{/if }  
</body>
</html>
</pub:template>

<pub:template name="comment_head">
<head>
<title>#{iif(error_list == null, "成功信息", "错误信息") }</title>
<meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
<link href='#{SiteUrl}index.template.css' rel='stylesheet' type='text/css' />
</head>
</pub:template>

<pub:template name="fs_success">
<table cellpadding=2 cellspacing=1 border=0 width=400 class='border' align=center>
	<tr align='center' class='title'>
		<td height='22'>
			<strong>恭喜你！</strong>
		</td>
	</tr>
	<tr class='tdbg'>
		<td height='100' valign='top'>
			<br>
			申请友情链接成功！请等候管理员的审核！审核后才会显示.<br/>
		</td>
	</tr>
	<tr align='center' class='tdbg'>
		<td>
			<a href='javascript:window.close();'>【关闭】</a>
		</td>
	</tr>
</table>
</pub:template>

<pub:template name="fs_error">
<table cellpadding=2 cellspacing=1 border=0 width=400 class='border' align=center>
	<tr align='center' class='title'>
		<td height='22'>
			<strong>错误信息</strong>
		</td>
	</tr>
	<tr class='tdbg'>
		<td height='100' valign='top'>
			<b>产生错误的可能原因：</b>
			#{foreach error in error_list }
			<li>#{error }</li>
			#{/foreach }
		</td>
	</tr>
	<tr align='center' class='tdbg'>
		<td>
			<a href='javascript:window.close();'>【关闭】</a>
		</td>
	</tr>
</table>
</pub:template>

<pub:process_template name="main"/>
