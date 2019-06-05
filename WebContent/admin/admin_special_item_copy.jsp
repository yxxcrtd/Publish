<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SpecialManage"
%><%
  // 初始化页面数据。
  SpecialManage manager = new SpecialManage(pageContext);
  manager.initCopyMoveItemPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>添加/移动专题中项目</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
 <script type='text/javascript' src='admin_special_item.js'></script>
</head>
<body>
<pub:declare>

<%@ include file="element_article.jsp" %>

<pub:template name="main">
 #{call article_manage_navigator("添加/移动项目到专题") }
 #{call show_copy_form } 
</pub:template>


<pub:template name="show_copy_form">
<form method='post' name='myform' action='admin_special_action.jsp' target='_self'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title'>
    <td height='22' colspan='4' align='center'><b>将项目添加到专题中</td>
  </tr>
  <tr align='left' class='tdbg'>
    <td width='100' class='tdbg5'>选定的标识：</td>
    <td><input type='text' name='refids' value='#{request.refids }' size='50'></td>
  </tr>
  <tr align='left' class='tdbg'>
    <td width='100' class='tdbg5' valign='top'>添加到目标专题：</td>
    <td>
     <select name='specialIds' size='2' multiple='' style='height:300px;width:300px;'>
     #{foreach special in special_list }
      <option value='#{special.id }'>#{special.name }#{iif(special.channelId == 0, '(全站)', '(本频道)') }</option>
     #{/foreach }
    </select></td>
  </tr>
</table>
<p align='center'>
 <input name='channelId' type='hidden' id='ChannelID' value='#{channel.id }' />
 <input name='command' type='hidden' id='Command' value='#{request.command }' />
 <input name='submit' type='submit' id='submit' value=' 执   行 ' style='cursor:hand;'>&nbsp; 
 <input name='Cancel' type='button' id='Cancel' value=' 取 消 '
  onclick="window.history.back();" style='cursor:hand;'>
</p>
<br>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>