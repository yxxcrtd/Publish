<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<title>评论管理</title>
</head>
<body>

<!-- 根据 Request 的 channelId 获取 Channel. -->
<pub:data purpose="为这个页面获取 Channel 的数据" var="channel" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />

<!-- 根据request的commentId产生需要的评论的数据。 -->
<pub:data var="comment" scope="page"
	provider="com.chinaedustar.publish.admin.CommentDataProvider"/>
	
<pub:template name="main">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='2' align='center'><strong>#{channel.itemName }评论管理</strong></td>
  </tr>
</table>
<br/>
<table width='100%' border='0' align='center' cellpadding='2'
	cellspacing='1' class='border'
	style='word-break:break-all;Width:fixed'>
	<form name='myform' method='post' action='admin_comment_save.jsp'>
	<tr align='center' class='title'>
		<td height='22' colspan='4'>
			<strong>修 改 评 论 </strong>&nbsp;&nbsp;（会员模式）
		</td>
	</tr>
	<tr>
		<td width='200' align='right' class='tdbg'>评论人姓名：</td>
		<td class='tdbg' colspan='3'>
			<input name='userName' type='text' id='userName' value='#{comment.userName }' disabled>
		</td>
	</tr>
	<tr>
		<td width='200' align='right' class='tdbg'>评论时间：</td>
		<td class='tdbg' width='200'>
			<input name='writeTime' type='text' id='writeTime'	value='#{comment.writeTime.substring(0, 19) }'>
		</td>
		<td class='tdbg' align='right' width='101'>评论人IP：</td>
		<td class='tdbg' width='475'>
			<input name='ip' type='text' id='ip' maxlength='15'	value='#{comment.ip }'>
		</td>
	</tr>
	<tr>
		<td width='200' align='right' class='tdbg'>评 分：</td>
		<td class='tdbg' colspan='3'>
			<input type='radio' name='score' value='1' #{iif(comment.score == 1, "checked", "") }>1分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type='radio' name='score' value='2' #{iif(comment.score == 2, "checked", "") }>2分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type='radio' name='score' value='3' #{iif(comment.score == 3, "checked", "") }>3分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type='radio' name='score' value='4' #{iif(comment.score == 4, "checked", "") }>4分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type='radio' name='score' value='5' #{iif(comment.score == 5, "checked", "") }>5分
		</td>
	</tr>
	<tr>
		<td width='200' align='right' class='tdbg'>评论内容：</td>
		<td class='tdbg' colspan='3'>
			<textarea name='content' cols='56' rows='8' id='content' type="_moz">#{comment.content@html }</textarea>
		</td>
	</tr>
	<tr align='center'>
		<td height='30' colspan='4' class='tdbg'>
				<input name='channelId' type='hidden' id='channelId' value='#{channelId }'>
				<input name='columnId' type='hidden' id='columnId' value='#{columnId }'>
				<input name='itemId' type='hidden' id='itemId' value='#{itemId }'>
				<input name='commentId' type='hidden' id='commentId' value='#{commentId }'>
				<input type='submit' name='Submit' value=' 保存修改结果 '>
		</td>
	</tr>
	</form>
</table>
</pub:template>

<pub:process_template name="main"/>
</body>
</html>
