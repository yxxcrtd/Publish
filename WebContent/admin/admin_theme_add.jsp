<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.ThemeManage"
%><%
  // 模板管理数据提供。
  ThemeManage admin_data = new ThemeManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>模板方案管理</title>
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
  <script type = "text/javaScript">
    function checkForm(){
      if (document.myform.Name.value==""){
        alert("方案名称不能为空！");
        document.myform.Name.focus();
        return false;
      }
      if (document.myform.Description.value==""){
        alert("方案简介不能为空！");
        document.myform.Description.focus();
        return false;
      }
      return true;
    }
  </script>
</head>

<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<pub:declare>

<%@include file="element_theme.jsp"%>

<pub:template name="main">
 #{call admin_theme_help}
 #{call theme_form }
</pub:template>

<pub:template name="theme_form">
<form name='myform' action='admin_theme_action.jsp' method='post'>
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
 <tr align='center' class='title'>
  <td height='22' colspan='2'><strong>  添 加  方 案</strong></td>
 </tr>
 <tr align='center'>
   <td class='tdbg'  valign='top'>
    #{call field_table }
   </td>
  </tr>
</table>
<br />
<center>
  <input type='hidden' id='themeId' value='#{theme.id}' name='themeId' />
  <input type='hidden' name='command' value='save' />
  <input type='submit' value=' 确 定 ' name='submit' onClick="return checkForm();" />&nbsp;&nbsp;&nbsp;&nbsp;
  <input type='Reset' name='reset' value=' 清 除 ' />
</center>
</form>
</pub:template>

<pub:template name="field_table">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
  <tr class='tdbg'> 
   <td width='150' class='tdbg5' align='right' ><strong> 方案名称：&nbsp;</strong></td>
   <td class='tdbg' align='left'>
    <input name='name' type='text' id='Name' size='30' maxlength='30' value='#{theme.name@html}' />
       <font color=red> * </font>
     </td>
   </tr>
   <tr class='tdbg'>
     <td width='150' class='tdbg5' align='right'><strong> 方案简介：&nbsp;</strong></td>
     <td align='left'>
       <textarea name='description' style='width:450px;height:100px' id='Description' type="_moz">#{theme.description@html}</textarea>
       <font color=red> * </font>
     </td>
   </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
