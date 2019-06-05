<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.TemplateManage"
%><%
  // 初始化页面数据.
  TemplateManage admin_data = new TemplateManage(pageContext);
  admin_data.initMovePage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>方案间模板迁移</title>
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<%-- 主显示模板 --%>
<pub:template name="main">
  #{call admin_theme_help}
  #{call action_form }
</pub:template>

<pub:template name="action_form">
<form name='form1' method='post' action='admin_theme_move.jsp'>
<table width='100%' border='0' align='center' cellpadding='5' cellspacing='0' class='border'>
 <tr align='center'>
  <td class='tdbg' height='200' valign='top'>
   <table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
    <tr align='center'>
     <td class='tdbg5' valign='top' width='50%'>
      <table width='100%' border='0' cellpadding='2' cellspacing='1'>
       <tr>
        <td width='40'></td>
        <td align='left'>
         &nbsp;&nbsp;&nbsp;&nbsp;<b>选择方案中要迁移的的模板</b>
         <br>#{call theme_source_list }
         <br>#{call template_group_list}
         <br>#{call template_list }
         <br>
         <input type='button' name='Submit' value=' 选定所有 ' onclick='SelectAll()' />
         <input type='button' name='Submit' value=' 取消选定 ' onclick='UnSelectAll()' /><br>
         <FONT style='font-size:12px' color=''><b>按住“Ctrl”或“Shift”键可以多选</b></FONT>
<script language='javascript'>
function SelectAll(){
  for(var i=0;i<document.form1.sourceTemplateId.length;i++){
  document.form1.sourceTemplateId.options[i].selected=true;}
}
function UnSelectAll(){
  for(var i=0;i<document.form1.sourceTemplateId.length;i++){
  document.form1.sourceTemplateId.options[i].selected=false;}
}
function CheckForm(){
  if (document.form1.sourceTemplateId.value == ""){
    alert("迁移模板不能为空！");
    document.form1.sourceTemplateId.focus();
    return false;
  }
  if (document.form1.destThemeId.value == ""){
    alert("迁移的方案不能为空！");
    document.form1.destThemeId.focus();
    return false;
  }
  if (document.form1.themeId.value == document.form1.destThemeId.value){
    alert("方案迁移不能自己给自己移动复制！");
    document.form1.themeId.focus();
    return false;
  }
  document.form1.action = 'admin_theme_action.jsp';
  return true;
}
</script>
        </td>
       </tr>
      </table>
     </td>
     <td width='80' class='tdbg' align='center'>
      <input TYPE='radio' Name='command' value='batch_move' /> 移动到 &gt;&gt;<br/>
      <input TYPE='radio' Name='command' value='batch_copy' checked /> 复制到 &gt;&gt;<br/>
      <input type='submit' name='Submit' value=' 确 定 ' onclick="javascript:return CheckForm()" />
     </td>          
     <td class='tdbg' align='left'>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>目标模板方案</strong><br>
      &nbsp;&nbsp;
      #{call theme_destin_list }
     </td>
    </tr>
   </table>
  </td>
 </tr>
</table>

<center><FONT color='red'> 注:</FONT>移动的时候,<FONT color='#3366FF'>系统默认,方案默认</FONT>是不会移动的。</center> 
</form>
</pub:template>

<%-- 左侧当前方案列表 --%>
<pub:template name="theme_source_list">
 <select name='themeId' style='width:210px;' onchange='document.form1.submit();'>
 #{foreach theme0 in theme_list }
  <option value='#{theme0.id}' #{if theme0.id == theme.id}selected#{/if}>#{theme0.name}</option>
 #{/foreach }
 </select>
</pub:template>

<%-- 左侧模板类型列表 --%>
<pub:template name="template_group_list">
 <select name='groupId_channelId' style='width:200px' onchange='document.form1.submit();'>
 #{foreach group in template_group_list }
  <option value='#{group.id},#{group.channelId}' #{if group == current_group}selected#{/if}>#{group.title}</option>
 #{/foreach }
  <!-- <option value='all'>方案所有模板</option> -->
 </select>
</pub:template>

<%-- 左侧模板列表，在指定方案，指定分组中 --%>
<pub:template name="template_list">
 <select name='sourceTemplateId' id='sourceTemplateId' size='2' 
    multiple='' style='height:250px;width:320px;' >
  #{foreach template in template_list }
   <option value='#{template.id}'>[#{template.typeName@replace('#itemName#', channel.itemName)}] #{template.name}</option>
  #{/foreach }
 </select>
</pub:template>

<%-- 右侧所有已有的方案列表。 --%>
<pub:template name="theme_destin_list">
 <select name='destThemeId' size='2' style='height:300px;width:200px;' >
 #{foreach theme in theme_list }
  <option value='#{theme.id}'>#{theme.name }</option>
 #{/foreach }
 </select>
</pub:template>

<%@include file="element_theme.jsp"%>

<%-- 执行主模板 --%>
<pub:process_template name="main" />

</body>
</html>
