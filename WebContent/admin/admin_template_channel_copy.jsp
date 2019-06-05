<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.TemplateManage"
%><%
  TemplateManage manager = new TemplateManage(pageContext);
  manager.initCopyPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>频道模板复制</title>
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
 
</script>
</head>

<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<%@ include file="element_template.jsp" %>

<pub:template name="main">
 #{call debug_info }
 #{call admin_template_help }
 #{call copy_table_main }
</pub:template>


<pub:template name="debug_info">
<h2>DEBUG INFO</h2>
<li>channel = #{(channel) }, .id = #{channel.id }
<br/><br/>
</pub:template>



<pub:template name="copy_table_main">
<form name='myform' method='post' action='admin_template_action.jsp'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title'>
 <td height='22' align='center'><strong>频道模板复制</strong></td>
  </tr>
  <tr class='tdbg'>
 <td height='100' align='center'>
   <br>
   <table border='0' cellspacing='0' cellpadding='0'>
  <tr align='center'>
    <td><strong>选择要复制的频道模板</strong></td>
    <td></td>
    <td><strong>要复制到那个频道</strong></td>
  </tr>
  <tr>
    <td colspan='3' height='25'>
     #{call copy_table_list }
    </td>
  </tr>
  <tr>
    <td>
      #{call copy_table_from }
    </td>
    <td width='80'>&nbsp;&nbsp;复制到 &gt;&gt;&gt;</td>
    <td>
   #{call copy_table_to }
    </td>
  </tr>
  <tr>
    <td colspan='3' height='10'></td>
  </tr>
  <tr>
    <td height='25' align='center'><b> 提示：按住“Ctrl”或“Shift”键可以多选</b> </td>
    <td height='25' align='center'></td>
    <td height='25' align='center'><b> 提示：按住“Ctrl”或“Shift”键可以多选</b> </td>
  </tr>
  <tr>
    <td colspan='3' height='20'></td>
  </tr>
  <tr>
    <td colspan='3' height='25' align='center'>
     <input type='submit' name='Submit' value=' 复制模板 ' />
    </td>
  </tr>
   </table>
   <input name='command' type='hidden' id='command' value='channel_template_copy' />
   <input name='themeId' type='hidden' id='themeId' value='#{theme.id }' />
   <br>
 </td>
  </tr>
</table>
</form>
</pub:template>

<!-- 频道列表 -->
<pub:template name="copy_table_list">
 <select name='channelId' onchange="window.location='admin_template_channel_copy.jsp?themeId=#{theme.id}&channelId=' + this.options[this.selectedIndex].value">
 #{foreach channel0 in channel_list }
   <option value='#{channel0.id }' #{iif(channel.id == channel0.id, "selected", "") }>#{channel0.name }</option>
 #{/foreach }
 </select>
 <br>
</pub:template>

<!-- 复制的模板来源 -->
<pub:template name="copy_table_from">
 <select name='templateId' size='2' multiple style='height:300px;width:250px;'>
 #{foreach template in template_list }
  <option value='#{template.id }'>#{template.name }</option> 
 #{/foreach }
  <option value='28'>文章频道首页模板</option> 
 </select>
</pub:template>

<!-- 复制到的频道 -->
<pub:template name="copy_table_to">
 <select name='targetChannelId' size='2' multiple style='height:300px;width:250px;'>
 #{foreach channel0 in channel_list }
  #{if (channel.id != channel0.id) }
  <option value='#{channel0.id }'>#{channel0.name }</option>
  #{/if }
 #{/foreach }
 </select>  
</pub:template>

<pub:process_template name="main"/>

</body>
</html>
