<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@page import="com.chinaedustar.publish.admin.CommentManage"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%
  // 产生管理用数据。
  CommentManage admin_data = new CommentManage(pageContext);
  admin_data.initReplyPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<title>评论管理</title>
</head>
<body>

<pub:template name="main">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='2' align='center'><strong>#{channel.itemName }评论管理</strong></td>
  </tr>
</table>

<br/>
<form method='post' name='myform' action='admin_comment_action.jsp'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'
    style='word-break:break-all;Width:fixed'>
  <tr align='center' class='title'>
    <td height='22' colspan='2'><strong>回 复 评 论</strong></td>
  </tr>
  <tr>
    <td width='200' align='right' class='tdbg'>评论文章标题：</td>
    <td class='tdbg'>#{item.title }</td>
  </tr>
  <tr>
    <td width='200' align='right' class='tdbg'>评论人用户名：</td>
    <td class='tdbg'>#{comment.userName }</td>
  </tr>
  <tr>
    <td width='200' align='right' class='tdbg'>评论内容：</td>
    <td class='tdbg'>#{comment.content@html }</td>
  </tr>
  <tr>
    <td align='right' class='tdbg'>回复内容：</td>
    <td class='tdbg'>
     <textarea name='replyContent' cols='50' rows='6' id='replyContent'>#{comment.replyContent@html }</textarea>
    </td>
  </tr>
  <tr align='center'>
    <td height='30' colspan='2' class='tdbg'>
      <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
      <input type='hidden' name='command' value='reply' />
      <input name='commentId' type='hidden' id='commentId' value='#{comment.id}' /> 
      <input type='submit' name='Submit' value=' 回 复 '>
    </td>
  </tr>
</table>
</form>
</pub:template>	

<pub:process_template name="main"/>

</body>
</html>
