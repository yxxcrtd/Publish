<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.CommentManage" 
%><%
  // 添加一个评论, 如果添加失败会有非空的 error_list 集合；否则有 comment, item, channel 变量可用
  CommentManage manage = new CommentManage(pageContext);
  manage.userAddComment();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<pub:template name="main">
<html> 
<head>
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
 <title>#{iif(error_list@size == 0, "成功信息", "错误信息") }</title>
 <link href='#{InstallDir}Skin/DefaultSkin.css' rel='stylesheet' type='text/css' />
</head>
<body> <br/><br/>
 #{if (error_list != null) }
   #{call comment_success }
 #{else }
   #{call comment_error }
 #{/if }  
</body>
</html>
</pub:template>



<pub:template name="comment_success">
<table cellpadding=2 cellspacing=1 border=0 width=400 class='border' align='center'>
 <tr align='center' class='title'>
  <td height='22'>
   <strong>恭喜你！</strong>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='100' valign='top'>
   <br>
   发表评论成功！请等候管理员的审核！审核后才会显示
  </td>
 </tr>
 <tr align='center' class='tdbg'>
  <td>
   <a href='javascript:window.close();'>【关闭】</a>
  </td>
 </tr>
</table>
</pub:template>

<pub:template name="comment_error">
<table cellpadding=2 cellspacing=1 border=0 width=400 class='border' align=center>
 <tr align='center' class='title'>
  <td height='22'>
   <strong>添加评论失败，错误信息如下</strong>
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
