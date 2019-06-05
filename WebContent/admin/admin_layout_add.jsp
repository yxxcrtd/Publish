<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.LayoutManage"
%><%
  LayoutManage manager = new LayoutManage(pageContext);
  manager.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <link href='admin_style.css' rel='stylesheet' type='text/css' />
  <title>布局添加/修改</title>
</head>
<body>
<pub:declare>

<%@include file="element_layout.jsp" %>

<pub:template name="main">
 #{call admin_layout_help }
 #{call your_position }<br/>
 #{call edit_layout_form }
</pub:template>


<pub:template name="your_position">
<div>
您现在的位置：网站管理 &gt;&gt; 布局管理 &gt;&gt; 添加/修改布局
</div>
</pub:template>


<pub:template name="edit_layout_form">
<form action='admin_layout_action.jsp' method='post' name='myform' id='myform' __onsubmit='return checkForm();'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title' height='22'>
  <td align='center'><strong>添加/修改布局</strong></td>
 </tr>
 <tr class='tdbg'>
  <td>
   <table border='0' cellpadding='0' cellspacing='0' width='100%' >
    <tr>
     <td width='100' align='center'><strong>布局名称：</strong></td>
     <td><input name='name' type='text' id='Name' size='30' maxlength='50' value='#{layout.name@html}' />
     <td width='10'></td>
     <td><font color='#FF0000'>* 输入名称。</font></td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>
   <table border='0' cellpadding='0' cellspacing='0' width='100%' >
    <tr>
     <td width='100' align='center'><strong>布局简介：</strong></td>
     <td>
      <textarea name='description' cols='80' rows='4' id='Description' type="_moz">#{layout.description@html}</textarea>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='title' height='22'>
  <td  align='center'><strong>布 局 内 容</strong></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;
   <textarea name='content' class='body2' rows='32' cols='126' 
      __onMouseUp="setContent('get',1)" type="_moz">#{layout.content@html}</textarea>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='40' align='center'>
   <input name='layoutId' type='hidden' id='LayoutId' value='#{layout.id}' />
   <input name='command' type='hidden' value='save' />
   <input name='submit' type='submit' id='Submit' value=' 保存修改结果 ' />
  </td>
 </tr>
</table>
</form>

</pub:template>

</pub:declare>
<pub:process_template name="main" />

</body>
</html>
