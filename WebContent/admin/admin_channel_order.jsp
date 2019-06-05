<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ChannelManage"
%><% 
  // 准备页面数据.
  ChannelManage manage = new ChannelManage(pageContext);
  manage.initOrderPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href='admin_style.css' rel='stylesheet' type='text/css'>
<title>频道排序</title>
<script type="text/javascript" language="javascript" src="main.js" ></script>
<script type="text/javascript">
<!--

// 栏目的子栏目ID的初始值.
var channelIds = "";

// 将指定节点向上移动.
function moveUp(nodeId) {
 var node = byId(nodeId);
 var previous = node.previousSibling;
 // 实际的移动操作。

 if (previous != null) {
  node.swapNode(previous);
 }
 setMoveLinkStyle(nodeId, previous); 
}

// 将指定节点向下移动.
function moveDown(nodeId) {
 var node = byId(nodeId);
 var next = node.nextSibling;
 // 实际的移动操作。

 if (next != null) {
  node.swapNode(next);
 }
 setMoveLinkStyle(nodeId, next);
}

// 设置移动链接的样式.
function setMoveLinkStyle(nodeId, swapNode) {
 var node = byId(nodeId);
  
 // 设置上移操作链接的是否可用颜色.
 if (node.previousSibling == null || node.previousSibling == node.parentNode.firstChild) {
  byId(nodeId + "_up").style.visibility = "hidden";
 } else {
  byId(nodeId + "_up").style.visibility = "visible";
 }
 if (swapNode != null && swapNode != swapNode.parentNode.firstChild && swapNode.parentNode.firstChild != swapNode.previousSibling) {
  byId(swapNode.id + "_up").style.visibility = "visible";
 } else {
  byId(swapNode.id + "_up").style.visibility = "hidden";
 }

 // 设置下移操作链接的是否可用颜色.
 if (node.nextSibling == null) {
  byId(nodeId + "_down").style.visibility = "hidden";
 } else {
  byId(nodeId + "_down").style.visibility = "visible";
 }
 if (swapNode != null && swapNode != swapNode.parentNode.lastChild) {
  byId(swapNode.id + "_down").style.visibility = "visible";
 } else {
  byId(swapNode.id + "_down").style.visibility = "hidden";
 }
}

// 得到当前排序的栏目ID，用逗号分割.
function getChannelIds() {
 var root = byId("root").childNodes[0];
 var ids = "";
 if (root.childNodes.length > 1) {
  for (var i = 1; i < root.childNodes.length; i++) {
   ids += root.childNodes[i].id + ",";
  }
  ids = ids.substring(0, ids.length - 1);
 }
 return ids;
}

// 保存栏目排序结果.
function saveOrder() {
 var ids = getChannelIds();
 if (ids == channelIds) {
  alert("没有改变排序，不需要执行保存操作。");
  return;
 }
 if (ids.length < 1) {
  alert("没有可以排序的频道。");
 } else {
  orderForm.channelIds.value = ids;
  orderForm.submit();
 }
}

// 注册页面初始化事件.
(function(){
 if(document.all){ // IE 注册方法
  document.attachEvent("onreadystatechange", function(){
   if(document.readyState == "complete"){
    channelIds = getChannelIds();
   }
  });
 } else { // 其他浏览器的注册方法。

  document.addEventListener("DOMContentLoaded", function(e) {channelIds = getchannelIds();}, null);
 }
})();

// -->
</script>
</head>
<body>

<%@ include file="element_channel.jsp" %>

<pub:template name="main"> 
  #{call channel_manage_navigator}
  #{call channel_list_template}
  #{call channel_manage_form}
</pub:template>

<pub:template name="channel_list_template">
<table id='root' width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title'>   
   <td height='22' align='center'>
    <strong> 频道名称</strong>
   </td>
   <td width='80' align='center'>
    <strong>打开方式</strong>
   </td>
   <td width='80' align='center'>
    <strong>频道类型</strong>
   </td>
   <td width='180' align='center'>
    <strong>频道目录/链接地址</strong>
   </td>
   <td width='100' align='center'>
    <strong>功能模块</strong>
   </td>
   <td width='160' align='center'>
    <strong>操作</strong>
   </td>
  </tr>
#{foreach channel in channel_list }
  <tr class='tdbg' id="#{channel.id }"  onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">   
   <td align='center'>
    <a href='admin_channel_add.jsp?channelId=#{channel.id }' title=''>#{channel.name }</a>
   </td>
   <td align='center'>#{iif(channel.openType == 0, "本窗口", "新窗口") }</td>
   <td align='center'>
    #{switch channel.channelType }
    #{case 0 }
     <font color='blue'>系统频道</font>
    #{case 1 }
     <font color='green'>内部频道</font>
    #{case 2 }
     <font color='red'>外部频道</font>
    #{/switch } 
   </td>
   <td>
    #{if(channel.channelType == 2) }
      链接：#{channel.channelUrl }
    #{else }
      目录：#{channel.channelDir }
    #{/if }
   </td>
   <td align='center'>
    #{channel.moduleName}
   </td>   
   <td align='center'>
    <a style="color: #0000ff; visibility: #{iif (channel@is_first, "hidden", "visible") }" id="#{channel.id }_up" href="javascript:moveUp('#{channel.id }')">上移</a>&nbsp;
  <a style="color: #0000ff; visibility: #{iif (channel@is_last, "hidden", "visible") }" id="#{channel.id }_down" href="javascript:moveDown('#{channel.id }')">下移</a>
   </td>
  </tr>
#{/foreach }  
</table>
</pub:template>

<pub:template name="channel_manage_form">
<form name='orderForm' action='admin_channel_action.jsp' method='post' >
 <div align="center">
  <input type='hidden' name='command' value='reorder' />
  <input type='hidden' name='channelIds' value='' />
  <input name='save' type='button' value=' 保 存 ' onclick="saveOrder()" />&nbsp;&nbsp;
  <input name='cancel' type='button' id='Cancel' value=' 取 消 '
  onClick="window.location.href='admin_channel_list.jsp'"
  style='cursor:hand;' />
 </div>
</form>
</pub:template>

<pub:process_template name="main"/>

</body>
</html>
