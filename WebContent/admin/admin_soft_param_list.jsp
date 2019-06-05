<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<title>我添加的软件</title>
</head>
<body>
<!-- 根据 Request 的 channelId 获取 Channel. -->
<pub:data purpose="为这个页面获取 Channel 的数据" var="channel" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />
	
<!-- 得到软件参数列表  -->
<pub:data var="soft_param_list" param="content"
	provider="com.chinaedustar.publish.admin.SoftParamsDataProvider"/>

<pub:template name="main">
  #{call soft_manage_navigator(channel.itemName + "参数管理") }	
  
  <form name='myform' method='post' action='admin_soft_param_save.jsp'>
	<table width='100%' border='0' cellpadding='5' cellspacing='1'
		class='border'>
		<tr align='center' valign='top' class='title'>
			<td colspan='4'><strong>#{channel.itemName }参数管理</strong></td>
		</tr>
		<tr align='center' valign='top' class='tdbg'>
			<td>
				#{channel.itemName }类别管理<br>
				<textarea name='softTypes' cols='20' rows='10' id='SoftTypes' 
				type="_moz">#{foreach type in soft_param_list.type}#{type }
#{/foreach }</textarea><br>
				<div align='left'>说明：每一个类别为一行	</div>
			</td>
			<td>
				#{channel.itemName }语言管理<br>
				<textarea name='softLanguages' cols='20' rows='10'	id='SoftLanguages' 
				type="_moz">#{foreach language in soft_param_list.language}#{language }
#{/foreach }</textarea><br>
				<div align='left'>说明：每一种语言为一行	</div>
			</td>
			<td>
				授权形式管理<br>
				<textarea name='softCopyrights' cols='20' rows='10'	id='SoftCopyrights'
				type="_moz">#{foreach copyright in soft_param_list.copyright}#{copyright }
#{/foreach }</textarea><br>
				<div align='left'>说明：每一种授权形式为一行</div>
			</td>
			<td>
				#{channel.itemName }平台管理<br>
				<textarea name='softOSs' cols='20' rows='10'id='SoftOSs' 
				type="_moz">#{foreach os in soft_param_list.os}#{os }
#{/foreach }</textarea><br>
				<div align='left'>说明：每一种运行平台为一行</div>
			</td>
		</tr>
		<tr align='center' valign='top' class='tdbg'>
			<td colspan='4'>
				<input name='channelId' type='hidden' id='channelId' value='#{channelId }'>
				<input type='submit' name='Submit' value=' 保存设置 '>
			</td>
		</tr>
	</table>
	</form>
</pub:template>

<%@ include file="element_soft.jsp" %>

<pub:process_template name="main"/>
</body>
</html>
