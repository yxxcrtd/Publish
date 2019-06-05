<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%@page import="com.chinaedustar.publish.admin.TemplateManage"%>

<%
  // 模板管理数据提供。
  TemplateManage admin_data = new TemplateManage(pageContext);
  admin_data.initListPage();

%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>模板方案管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<pub:declare>

<%@include file="element_template.jsp" %>

<pub:template name="main">
 #{call admin_template_help }
  
 #{if true || (request.showGroup) }
  #{call show_group_list }<br/>
 #{/if }
  
 #{call show_type_list}<br/>
 #{call your_position}
 #{call show_template_list}
</pub:template>


<pub:template name="debug_info">
<br/><br/><hr/><h2>DEBUG INFO</h2>
<li>theme = #{(theme) }, .name = #{theme.name }
<li>channel = #{(channel) }, .name = #{(channel.name) }
<li>request = #{(request) }
<li>template_group_list = #{(template_group_list) }
<li>current_group = #{(current_group) }
<li>template_type_list = #{(template_type_list) }
<li>current_type = #{(current_type) }
<li>template_list.schema = #{template_list.schema }
<li>template_list = #{(template_list) }
<br/><br/>
</pub:template>



<pub:template name="your_position">
您现在的位置：#{theme.name}<span> &gt;&gt;</span> #{current_group.title}
  <span> &gt;&gt;</span> #{current_type.title@replace("#itemName#", channel.itemName)}
</pub:template>


<%-- 模板类型组的横向列表，例子：| 网站通用模板 | 新闻中心 | 图片中心  --%>
<pub:template name="show_template_list">
<form name='myform' method='post' action=''>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
<tr class='title' height='22'>
 <td width='30' align='center'><strong>选择</strong></td>
 <td width='30' align='center'><strong>ID</strong></td>
 <td width='120' align='center'><strong>模板类型</strong></td>
 <td height='22' align='center'><strong>模板名称</strong></td>
 <td width='60' align='center'><strong>系统默认</strong></td>
 <td width='260' align='center'><strong>操作</strong></td>
</tr>
#{if template_list@size == 0}
 <tr class='tdbg'>
  <td colspan='10' align='center' height='50'>此模板类型中还没有模板</td>
 </tr>
#{/if }
#{foreach template in template_list}
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">  
 <td width="30" align="center" height="30">
    <input type="checkbox" value='#{template.id}' name="templateId" #{if template.isDefault}disabled#{/if} />
 </td>
 <td width='30' align='center'>#{template.id}</td>
 <td width='120' align='center'>#{template.typeName@replace("#itemName#", channel.itemName)}</td>
 <td align='center'>
  <a href='admin_template_add.jsp?themeId=#{theme.id}&channelId=#{channel.channelId}&typeId=#{template.typeId}&templateId=#{template.id}'>#{template.name}</a>
 </td>
 <td width='60' align='center'>#{iif(template.isDefault,"√","")}</td>
 <td width='260' align='center'>
  #{if template.isDefault == false}
  <a href='admin_template_action.jsp?command=set_default&templateId=#{template.id}'>设为默认</a>
  #{/if }
  <a href='admin_template_add.jsp?themeId=#{theme.id}&channelId=#{channel.id}&templateId=#{template.id}'>修改</a>
  #{if template.isDefault == false}
  <a href='admin_template_action.jsp?command=delete&templateId=#{template.id}&showGroup=#{request.showGroup}' onclick="return confirm('确定要删除此版面设计模板吗？删除后你可以从回收站还原它。');">删除</a>
  #{/if }
  <a href='admin_template_action.jsp?command=copy&templateId=#{template.id}'>复制</a>
 </td>
</tr>
#{/foreach}
</table>
<br/>
 #{call template_form_operate }
</form>
</pub:template>


<pub:template name="template_form_operate">  
  <input name="themeId" type="hidden"  value='#{theme.id}' />
  <input name="channelId" type="hidden"  value='#{channel.id}' />
  <input name="typeId" type="hidden"  value='#{current_type.id}' />
  <input name='command' type='hidden' value='' />
  <input name="chkAll" type="checkbox" id="chkAll" onclick=CheckAll(this.form) value="checkbox" >选中所有模板
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input type="button" value="批量删除 " onclick='return batchDel();' >&nbsp;&nbsp;
  <input type="submit" value=" 批量复制 " onclick="return batchCopy();" >&nbsp;&nbsp;
  <input type="submit" value=" 批量替换 " onclick="return batchReplace();" >&nbsp;&nbsp;
  <input type='button' value='添加模板' onclick="window.location.href='admin_template_add.jsp?themeId=#{theme.id}&channelId=#{channel.id}&typeId=#{current_type.id}'" >
  <script type='text/javascript'>
  	function CheckAll(thisform){
        for (var i=0;i<thisform.elements.length;i++){
            var e = thisform.elements[i];
            if (e.Name != "chkAll"&&e.disabled!=true&&e.zzz!=1)
                e.checked = thisform.chkAll.checked;
        }
    }
    
    //批量删除.
    function batchDel(){
      if (confirm('确定要删除选中的模板吗？删除后你可以从回收站还原它。')) {
    	  document.myform.action = 'admin_template_action.jsp';
        document.myform.command.value = 'batch_delete';
        document.myform.submit();
    	}
    }
       
    //批量复制.
    function batchCopy(){
    	document.myForm.action = 'admin_template_copy.jsp';
    	return false;
    }
    
    //批量替换.
    function batchReplace(){
    	document.myForm.action = 'admin_template_replace.jsp';
    	return  false;
    } 
  </script>
</pub:template>


</pub:declare>
<pub:process_template name="main" />

</body>
</html>
