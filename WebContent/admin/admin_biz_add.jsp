<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.BizManage"
%><%
  // 初始化管理页面数据.
  BizManage manager = new BizManage(pageContext);
  manager.initEditPage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>添加/修改联系</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0' >

<pub:declare>

<%@ include file="admin_biz_elem.jsp" %>

<pub:template name="main">
 #{call admin_biz_help }
 #{call show_edit_form }
 <hr>
 <li>bizName = #{(bizName) }, .id = #{bizName.id }, .name = #{bizName.name }
</pub:template>

<pub:template name="show_edit_form">
<form method='post' action='admin_biz_action.jsp' name='myform'>
<table width='98%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22' colspan='2'>
  <div align='center'>
  <strong>
  #{if (bizName.id == 0) }新增联系#{else }修改联系#{/if }
  </strong>
  </div>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='32%'>&nbsp;<strong> 名字：</strong></td>
  <td>
   <input name='name' value='#{bizName.name }' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='32%'>&nbsp;<strong> 描述：</strong></td>
  <td>
   <textarea name='description' cols='40' rows='3' id='description'
     >#{bizName.description@html }</textarea>
  </td>
 </tr>
 <tr>
  <td height='40' colspan='2' align='center' class='tdbg'>
  <input type='hidden' value="#{bizName.id }" name='bizId' />
  <input type='hidden' value='save' name='command' />
  <input type='submit' value=' 保 存 ' style='cursor:hand;'>&nbsp;
  <input name='cancel' type='button' id='cancel' value=' 取 消 '
   onclick="window.location.href='admin_biz_list.jsp';"
   style='cursor:hand;'>
  </td>
 </tr>
</table>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

 </body>
</html>
