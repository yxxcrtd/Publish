<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>签写新的留言</title>
</head>
<body>
<div align="center">签写新的留言</div>
<div align="center">
<form name="form1" action="admin/admin_feedback_action.jsp?command=save" target="_self" methord="post">
<table border="1" cellpadding="3" cellspacing="0" style="border-collapse: collapse;">
	<tr>
		<td width="100" align="right">留言标题</td>
		<td><input type="text" name="title"></td>
	</tr>
	<tr>
		<td align="right">留言人</td>
		<td><input type="text" name="userName"></td>
	</tr>
	<tr>
		<td align="right">留言内容</td>
		<td>
		<textarea name="content" cols="40" rows="6"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<input type="hidden" name="backUrl" value="javascript:window.close();">
		<input type="submit" value="提交">
		<input type="button" value="关闭" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</div>
<script type="text/javascript">
self.resizeTo(520,450);
</script>
</body>
</html>