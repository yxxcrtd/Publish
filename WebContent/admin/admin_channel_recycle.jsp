<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ChannelManage"
%><%
  // 初始化页面数据。
  ChannelManage admin_data = new ChannelManage(pageContext);
  admin_data.initRecyclePage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>频道回收站管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
</head>
<body>

<pub:declare>
<%@ include file="element_channel.jsp" %>

<pub:template name="main">
 #{call channel_manage_navigator }
 #{call show_channel_list }
</pub:template>

<%-- 定义频道页面显示模板 --%>
<pub:template name="show_channel_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title' height='22'>
  <td width='30' align='center'><strong>ID</strong></td>
  <td align='center'><strong>频道名称</strong></td>
  <td width='54' align='center'><strong>打开方式</strong></td>
  <td width='60' align='center'><strong>频道类型</strong></td>
  <td width='120' align='center'><strong>频道目录/链接地址</strong></td>
  <td width='60' align='center'><strong>项目名称</strong></td>
  <td width='54' align='center'><strong>功能模块</strong></td>
  <td width='60' align='center'><strong>生成HTML方式</strong></td>
  <td width='54' align='center'><strong>频道状态</strong></td>
  <td width='110' align='center'><strong>操作</strong></td>
  <td width='65' align='center' style="display:none"><strong>频道更新</strong></td>
 </tr>
#{foreach channel in channel_list}
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td align='center'>#{channel.id}</td>
 <td align='center'>
  <a href='admin_channel_add.jsp?action=modify&channelId=#{channel.id}' 
    title='#{channel.tips@html }'>#{channel.name}</a>
 </td>
 <td width='54' align='center'>#{iif(channel.openType == 0, "本窗口", "新窗口") }</td>
 <td width='60' align='center'>
  #{switch channel.channelType }
   #{case 0 }
    <font color='blue'>系统频道</font>
   #{case 1 }
    <font color='green'>内部频道</font>
   #{case 2 }
    <font color='red'>外部频道</font>
  #{/switch }
 </td>
 <td width='120' style='word-wrap:break-word'>
  #{if(channel.channelType == 2) }
    链接：#{channel.channelUrl }
  #{else }
    目录：#{channel.channelDir }
  #{/if }
 </td>
 <td width='60' align='center'>#{channel.itemName }</td>
 <td width='54' align='center'>#{channel.moduleName }</td>
 <td width='60' align='center'>
  #{switch (channel.createHTMLType) }
   #{case 0 }不生成
   #{case 1 }全部生成
   #{case 2 }部分生成1
   #{case 3 }部分生成2
  #{/switch }
 </td>
 <td width='54' align='center'>
   <font color='red'>已删除</font>
 </td>
 <td width='110' align='center'>
  <a href='admin_channel_action.jsp?command=recover&channelId=#{channel.id }'>恢复</a>
  <a href='javascript:destroyChannel(#{channel.id }, "#{channel.name }");'>彻底删除</a>
 </td>
 <td width='65' align='center' style="display:none">
  <a href='admin_channel_data.jsp?channelId=#{channel.id}'>数据</a>
 </td>
</tr>
#{/foreach}
</table>
</pub:template>

</pub:declare>
<pub:process_template name="main" />

<script language='javascript'>
function confirmDisable() {
  return confirm('您确定要禁用指定的频道吗？\r\n\r\n禁用只是暂时关闭频道而不是真实的删除了频道，稍后您可以再次启用该频道。');
}

function confirmEnable() {
  return confirm('您确定要启用指定的频道吗？');
}

function deleteChannel(channelId, channelName) {
  // 第一次提示.
  if (confirm('您正准备删除频道 "' + channelName + '"，频道是网站的关键性对象，其删除之后不能被恢复。\r\n\r\n'+
      '警告: 删除频道将导致该频道下所有数据、图片等都被删除，请确定您要进行此操作。\r\n' +
      '提示: 如果您只是希望该频道暂时不使用了，可以使用禁用功能。\r\n\r\n' +
      '?您是否再考虑一下? 点击确定进行删除，点击取消返回.') == false)
    return;
    
  // 第二次提示.
  if (confirm('警告: 您确定要删除频道 "' + channelName + '" 吗??') == false) return;
  
  window.location = 'admin_channel_action.jsp?command=delete&amp;channelId=' + channelId;
}

function destroyChannel(channelId, channelName) {
  if (confirm('警告: 您确定要彻底销毁频道 "' + channelName + '" 吗?') == false) return;
  window.location = 'admin_channel_action.jsp?command=destroy&amp;channelId=' + channelId;
}

</script>

</body>
</html>
