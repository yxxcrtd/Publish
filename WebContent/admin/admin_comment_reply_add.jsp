<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8" errorPage="admin_error.jsp" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<title>评论管理</title>
</head>
<body>

<!-- 根据 Request 的 channelId 获取 Channel. -->
<pub:data purpose="为这个页面获取 Channel 的数据" var="channel" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />

<!-- 根据request的commentId产生需要的评论的数据。 -->
<pub:data var="comment" scope="page" param="getItemTitle"
	provider="com.chinaedustar.publish.admin.CommentDataProvider"/>

<pub:template name="main">
	<table width='100%' border='0' align='center' cellpadding='2'
		cellspacing='1' class='border'>
		<tr class='topbg'>
			<td height='22' colspan='2' align='center'>
				<strong>#{channel.itemName }评论管理</strong>
			</td>
		</tr>
	</table>
	<br>
	<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border' style='word-break:break-all;Width:fixed'>
		<form method='post' action='admin_comment_reply_save.jsp' name='myform'>
		<tr align='center' class='title'>
			<td height='22' colspan='2'>
				<strong>回 复 评 论</strong>
			</td>
		</tr>
		<tr>
			<td width='200' align='right' class='tdbg'>
				评论文章标题：
			</td>
			<td class='tdbg'>
				#{itemTitle }
			</td>
		</tr>
		<tr>
			<td width='200' align='right' class='tdbg'>
				评论人用户名：
			</td>
			<td class='tdbg'>
				#{comment.userName }
			</td>
		</tr>
		<tr>
			<td width='200' align='right' class='tdbg'>
				评论内容：
			</td>
			<td class='tdbg'>
				#{comment.content@html }
			</td>
		</tr>
		<tr>
			<td align='right' class='tdbg'>
				回复内容：
			</td>
			<td class='tdbg'>
				<textarea name='replyContent' cols='50' rows='6' id='replyContent' type="_moz">#{comment.replyContent@html }</textarea>
			</td>
		</tr>
		<tr align='center'>
			<td height='30' colspan='2' class='tdbg'>
				<input name='channelId' type='hidden' id='channelId' value='#{channelId }'>
				<input name='columnId' type='hidden' id='columnId' value='#{columnId }'>
				<input name='itemId' type='hidden' id='itemId' value='#{itemId }'>
				<input name='commentId' type='hidden' id='commentId' value='#{commentId }'>
				<input type='submit' name='Submit' value=' 回 复 '>
			</td>
		</tr>
	</form>
	</table>
</pub:template>	

<pub:process_template name="main"/>

</body>
</html>
