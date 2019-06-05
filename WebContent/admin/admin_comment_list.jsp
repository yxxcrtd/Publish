<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%@page import="com.chinaedustar.publish.admin.CommentManage" %>

<%
  // 产生管理用数据。
  CommentManage admin_data = new CommentManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>评论管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <script src='main.js'></script>
</head>
<body>

<pub:declare>
 
<%@ include file="element_column.jsp" %>
<%@ include file="element_item.jsp" %>

<pub:template name="main">
 #{call admin_comment_help }
 #{call admin_comment_position }
  
<form name='myform' method='post' action='admin_comment_action.jsp''>
 #{call show_comment_list }
 #{call admin_comment_batch }
</form>
 #{call pagination_bar(page_info)}
</pub:template>


<pub:template name="admin_comment_help">
<form name='form2' method='get' action='admin_comment_list.jsp'>  
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='2' align='center'><strong>#{channel.itemName }评论管理</strong></td>
 </tr>
 <tr class='tdbg'>
   <td width='70' height='30'>
  <strong>评论选项：</strong>
   </td>
   <td>
  <input name='channelId' type='hidden' value='#{channel.id }' />
  <input name='passed' type='radio' value='' onclick='submit();' #{iif(request.passed == null, "checked", "") } />所有#{channel.itemName }评论&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='passed' type='radio' value='false' onclick='submit();' #{iif(request.passed == false, "checked", "") } />未审核的#{channel.itemName }评论&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='passed' type='radio' value='true' onclick='submit();' #{iif(request.passed == true, "checked", "") } />已审核的#{channel.itemName }评论
  </td>
 </tr>
</table>
</form>
</pub:template>


<pub:template name="admin_comment_position">
<table border='0' cellpadding='2' width='100%' cellspacing='0'>
 <tr>
   <td>
  您现在的位置：&nbsp;<a href='admin_comment_list.jsp?channelId=#{channelId }'>评论管理</a>
    &nbsp;&gt;&gt;&nbsp;所有评论
   </td>
 </tr>
  </table>
</pub:template>


<%-- 评论列表 --%>
<pub:template name="show_comment_list">
<table class='border' width='100%' border='0' align='center' cellpadding='0' cellspacing='1'>
 <tr class='title'>
   <td width='28' height='22'><b>选中</b></td>
   <td width='20'><b>id</b></td>
   <td width='100'><b>发表人</b></td>
   <td width='120'><b>发表时间</b></td>
   <td width='30'><b>审核</b></td>
   <td width='300' align='center'><b>评论的项目</b></td>
   <td align='center'><b>操作</b></td>
 </tr>
#{foreach comment in comment_list }
 <tr class='tdbg'>
  <td><input type='checkbox' name='itemId' id='id' value='#{comment.id }'/></td>
  <td>#{comment.id }</td>
  <td>#{comment.userName }</td>
  <td>#{comment.writeTime@format }</td>
  <td width='30' align='center'>
    #{iif(comment.passed, "√", "<font color='red'>×</font>") }
  </td>
  <td>#{comment.title@html }</td>
  <td>
    <a href='admin_comment_action.jsp?command=delete&commentId=#{comment.id }'>删除</a>
    #{if comment.passed }
     <a href='admin_comment_action.jsp?command=unpass&commentId=#{comment.id }'>取消通过</a>
    #{else }
     <a href='admin_comment_action.jsp?command=pass&commentId=#{comment.id }'>审核通过</a>
    #{/if }
    <a href='admin_comment_reply.jsp?channelId=#{channel.id }&commentId=#{comment.id }' target='_blank'>回复</a>
    <a href='admin_comment_action.jsp?command=delete_reply&commentId=#{comment.id }'>删除回复</a>
  </td>
 </tr>
 <tr >
  <td align='center' colspan='3'>评论内容:</td>
  <td colspan='4' style='padding:4px;'>#{comment.content@html }</td>
 </tr>
 <tr >
  <td align='center' colspan='3'>管理员回复:</td>
  <td colspan='4' style='padding:4px;'><div style='background:#ececec;'>#{comment.reply }</div></td>
 </tr>
#{/foreach }
</table>
</pub:template>



<pub:template name="admin_comment_batch"> 
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
   <td width='200' height='30'>
  <input name='chkAll' type='checkbox' id='chkAll' onclick='CheckAll(this)' value='checkbox'>
    选中本页显示的所有评论
   </td>
   <td>
  <input type='hidden' name='channelId' value='#{channelId }' />
  <input type='hidden' name='command' value='' />
  <input type='submit' value='删除选定的评论' onclick='return ConfirmDel();'>&nbsp;&nbsp;
  <input type='submit' onclick="document.myform.command.value='batch_pass'" value='审核通过选定的评论'>&nbsp;&nbsp;
  <input type='submit' onclick="document.myform.command.value='batch_unpass'" value='取消审核选定的评论'>
   </td>
 </tr>
  </table>
</pub:template>


<pub:template name="admin_comment_search">
<form method='post' name='searchForm' action='admin_comment_list.jsp' style='padding:0;'>
<table width='100%' border='0' cellpadding='0' cellspacing='0' class='border'>
 <tr class='tdbg'>
  <td width='80' align='right'><strong>评论搜索：</strong></td>
  <td height='28'>
   <select name='field' size='1'>
    <option value='content' selected>评论内容</option>
    <option value='time'>评论时间</option>
    <option value='name'>评论人</option>
   </select>
   <input type='text' name='keyword' size='20' value='关键字' maxlength='50' onFocus='this.select();'>
   <input type='submit' name='Submit' value='搜索'>
   <input name='channelId' type='hidden' id='channelId' value='#{channelId }'>
  </td>
 </tr>
</table>
</form>
</pub:template>

</pub:declare>
 
<pub:process_template name="main"/>

<script language='javascript'>
function ConfirmDel() {
  if (confirm('您确定删除选中的评论吗？') == false) return false;
  document.myform.command.value = 'batch_delete';
  return true;
}
</script>

</body>
</html>
