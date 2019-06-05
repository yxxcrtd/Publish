<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%
	pageContext.setAttribute("channelId",request.getParameter("channelId")==null?"1":request.getParameter("channelId"));
	pageContext.setAttribute("Action",request.getParameter("Action")==null?"ClearBegin":request.getParameter("Action"));
	pageContext.setAttribute("delFileCount",request.getParameter("DelFileCount")==null?"0":request.getParameter("DelFileCount"));
%>

<html>
	<head>
		<title>上传文件管理</title>
		<link href='admin_style.css' rel='stylesheet' type='text/css'>
	</head>
	<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
	
<!-- 根据 Request 的 channelId 获取 Channel. -->
<pub:data purpose="为这个页面获取 Channel 的数据" var="channel" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />
	
	<pub:template name="main">
		#{call temp_share }
		
		#{if(Action=="ClearBegin") }
			#{call temp_begin }
		#{elseif(Action=="ClearOver") }
			#{call temp_over }
		#{endif }
	</pub:template>
	
	<pub:template name="temp_share">
		<table width='100%' border='0' align='center' cellpadding='2'
				cellspacing='1' Class='border'>
			<tr class='topbg'>
				<td height='22' colspan=2 align=center>
					<b>#{channel.name }管理----上传文件管理</b>
				</td>
			</tr>
			<tr class='tdbg'>
				<td width='70' height='30'>
					<strong>管理导航：</strong>
				</td>
				<td height='30'>
					<a href='admin_upload_manage.jsp?channelId=#{channelId }'>上传文件管理首页</a>
					|
					<a
						href='admin_upload_clear.jsp?channelId=#{channelId }'>清除无用文件</a>
				</td>
			</tr>
		</table>
		<br>
	</pub:template>
	
	<pub:template name="temp_begin">
		<table width='100%' border='0' cellspacing='1' cellpadding='2'
			class='border'>
			<tr class='title'>
				<td height='22' align='center'>
					<strong>清理无用的上传文件</strong>
				</td>
			</tr>
			<tr class='tdbg'>
				<td height='150'>
					<form name='form1' method='post' action='admin_upload_operate.jsp'>
						&nbsp;&nbsp;&nbsp;&nbsp;在添加内容时，经常会出现上传了图片后但却最终没有使用的情况，时间一久，就会产生大量无用垃圾文件。所以需要定期使用本功能进行清理。
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;如果上传文件很多，或者信息数量较多，执行本操作需要耗费相当长的时间，请在访问量少时执行本操作。
						</p>
						<p align='center'>
							<input name='Action' type='hidden' id='Action' value='DoClear'>
							<input name='channelId' type='hidden' id='channelId' value='#{channelId }'>
							<input type='submit' name='Submit3' value=' 开始清理 '>
						</p>
					</form>
				</td>
			</tr>
		</table>
	</pub:template>
	<pub:template name="temp_over">
		<table cellpadding=2 cellspacing=1 border=0 width=400 class='border'
			align=center>
			<tr align='center' class='title'>
				<td height='22'>
					<strong>恭喜你！</strong>
				</td>
			</tr>
			<tr class='tdbg'>
				<td height='100' valign='top'>
					<br>
					清理无用文件成功！共删除了<font color=red><b>#{delFileCount }</b></font> 个无用的文件。
				</td>
			</tr>
			<tr align='center' class='tdbg'>
				<td>
					<a href='admin_upload_clear.jsp?channelId=#{channelId }'>&lt;&lt;返回上一页</a>
				</td>
			</tr>
		</table>
	</pub:template>
	
	<pub:process_template name="main"/>
	</body>
</html>
