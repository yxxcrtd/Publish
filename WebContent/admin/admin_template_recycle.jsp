<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.TemplateManage"
%><%
  // 模板管理数据提供。
  TemplateManage admin_data = new TemplateManage(pageContext);
  admin_data.initRecyclePage();

%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>模板方案回收站管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>

<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<pub:declare>

<%@ include file="element_template.jsp" %>

<pub:template name="main">
 #{call admin_template_help }
 #{call show_group_list }<br />
 
 <div>
 您现在的位置：#{theme.name} <span>&gt;&gt;</span> #{current_group.title} <span>&gt;&gt;</span> 模板回收站管理
 </div>
  
 #{call template_table_list}
</pub:template>


<pub:template name="debug_info">
<br/><br/><hr/><h2>DEBUG INFO</h2>
<li>template_list = #{(template_list) }
<br/><br/>
</pub:template>


<%-- 模板类型组的横向列表，例子：| 网站通用模板 | 新闻中心 | 图片中心  --%>
<pub:template name="template_table_list">
<form name='myform' method='post' action='admin_template_action.jsp'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
<tr class='title' height='22'>
 <td width='30' align='center'><strong>选择</strong></td>
 <td width='30' align='center'><strong>ID</strong></td>
 <td width='120' align='center'><strong>模板类型</strong></td>
 <td height='22' align='center'><strong>模板名称</strong></td>
 <td width='260' align='center'><strong>操作</strong></td>
</tr>
#{foreach templ in template_list}
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">  
 <td width="30" align="center" height="30">
  <input type="checkbox" value='#{templ.id}' name="templateId" />
 </td>
 <td width='30' align='center'>#{templ.id}</td>
 <td width='120' align='center'>#{templ.typeName}</td>
 <td align='center'>
  <a href='admin_template_add.jsp?themeId=#{templ.themeId }&templateId=#{templ.id}'>#{templ.name}</a>
 </td>
 <td width='260' align='center'>
  <a href='admin_template_action.jsp?command=recover&templateId=#{templ.id}' onclick="return confirm('确定要还原此模板吗？');">还原模板</a> 
  <a href='admin_template_action.jsp?command=destroy&templateId=#{templ.id}' onclick="return confirm('确定要删除此版面设计模板吗？删除后将不能还原它。');">彻底删除模板</a>
 </td>
</tr>
#{/foreach}

#{if template_list@size == 0}
<tr class='tdbg'>
 <td colspan='10' align='center' height='50'>此模板回收站中没有被删除的模板</td>
</tr>
#{/if}
</table>
<br/>
 #{call template_form_operate }
</form>
</pub:template>

<pub:template name="template_form_operate"> 
<input type='hidden' name='groupId' value='#{request.groupId}' /> 
<input type='hidden' name='channelId' value='#{request.channelId}' />
<input name="chkAll" type="checkbox" id="chkAll" onclick=CheckAll(this.form) value="checkbox" >选中所有模板
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='hidden' name='command' value='' />
<input type="button" value=" 批量删除 " onclick='return batchDel();' />&nbsp;&nbsp;
<input type="button" value=" 清空回收站 " onclick='return batchClear();' />&nbsp;&nbsp;
<input type="button" value=" 还原 " onclick='return batchRestore();' />&nbsp;&nbsp;
<input type="button" value=" 全部还原 " onclick='return batchRestoreAll();' />&nbsp;&nbsp;
<script type='text/javascript'>
function CheckAll(thisform){
  for (var i=0;i<thisform.elements.length;i++){
    var e = thisform.elements[i];
    if (e.name == "templateId")
      e.checked = thisform.chkAll.checked;
  }
}

//批量删除.
function batchDel(){
  if (confirm('确定要删除选中的模板吗？删除后将不能还原它。')) {
    document.myform.command.value = 'batch_destroy';
    document.myform.submit();
  }
}

//清空回收站.
function batchClear(){
  if (confirm('确定要清空回收站中的模板吗？删除后将不能还原它。')) {
    document.myform.command.value = 'batch_clear';
    document.myform.submit();
  }
}

//还原.
function batchRestore(){
  document.myform.action = 'admin_template_action.jsp';
  return  confirm('确定要还原选中的模板吗？');
}

//全部还原.
function batchRestoreAll(){
  document.myform.action = 'admin_template_action.jsp?scope=all';
  return  confirm('确定要还原回收站中所有的模板吗？');
} 
</script>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>
