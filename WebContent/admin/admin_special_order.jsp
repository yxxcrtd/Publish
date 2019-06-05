<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.SpecialManage"
%><%
  // 初始化页面数据。
  SpecialManage admin_data = new SpecialManage(pageContext);
  admin_data.initSortPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>专题排序</title>
<link href='admin_style.css' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 频道的专题ID的初始值.
var specialIds = "";

// 将指定节点向上移动.function moveUp(nodeId) {
 var node = byId(nodeId);
 var previous = node.previousSibling;

 // 实际的移动操作. if (previous != null) {
  node.swapNode(previous);
 }
 setMoveLinkStyle(nodeId, previous); 
}

// 将指定节点向下移动.function moveDown(nodeId) {
 var node = byId(nodeId);
 var next = node.nextSibling;
 // 实际的移动操作. if (next != null) {
  node.swapNode(next);
 }
 setMoveLinkStyle(nodeId, next);
}

// 设置移动链接的样式.function setMoveLinkStyle(nodeId, swapNode) {
 var node = byId(nodeId);
 // 设置上移操作链接的是否可用颜色. if (node.previousSibling == null || node.previousSibling == node.parentNode.firstChild) {
  byId(nodeId + "_up").style.visibility = "hidden";
 } else {
  byId(nodeId + "_up").style.visibility = "visible";
 }
 if (swapNode != null && swapNode != swapNode.parentNode.firstChild && swapNode.parentNode.firstChild != swapNode.previousSibling) {
  byId(swapNode.id + "_up").style.visibility = "visible";
 } else {
  byId(swapNode.id + "_up").style.visibility = "hidden";
 }

 // 设置下移操作链接的是否可用颜色. if (node.nextSibling == null) {
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

// 得到当前排序的专题ID，用逗号分割.function getSpecialIds() {
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

// 保存专题排序结果.function saveOrder() {
 var ids = getSpecialIds();
 if (ids == specialIds) {
  alert("没有改变排序，不需要执行保存操作。");
  return false;
 }
 if (ids.length < 1) {
  alert("没有可以排序的专题。");
 } else {
  document.myform.specialIds.value = ids;
  return true;
 }
}

// 注册页面初始化事件.(function(){
 if(document.all){ // IE 注册方法
  document.attachEvent("onreadystatechange", function(){
   if(document.readyState == "complete"){
    specialIds = getSpecialIds();
   }
  });
 } else { // 其他浏览器的注册方法.  document.addEventListener("DOMContentLoaded", function(e) {specialIds = getSpecialIds();}, null);
 }
})();
// -->
</script>
</head>
<body>
<pub:declare>

<%@include file="element_special.jsp" %>

<pub:template name="main">
#{call special_navigator("专题排序") }
<form name="myform" action="admin_special_action.jsp" method='post' onsubmit="return saveOrder();">
<table id="root" width='100%' border='0' align='center' cellpadding='0' cellspacing='1' class='border'>
 <tbody>
 <tr class='title' height='22'>
  <th>专题名称</th>
  <th>操作</th>
 </tr>
 #{foreach special in special_list }
 <tr class='tdbg' id="#{special.id }" onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td align="center">#{special.name }</td>
  <td>
  <a style="color: #0000ff; visibility: #{iif (special@is_first, "hidden", "visible") }" id="#{special.id }_up" href="javascript:moveUp('#{special.id }')">上移</a>&nbsp;
  <a style="color: #0000ff; visibility: #{iif (special@is_last, "hidden", "visible") }" id="#{special.id }_down" href="javascript:moveDown('#{special.id }')">下移</a>
  </td>
 </tr>
 #{/foreach }
 </tbody>
</table>
<br />
<div align="center">
 <input name="channelId" type="hidden" value="#{channel.id }" />
 <input name="specialIds" type="hidden" value="" />
 <input type='hidden' name='command' value='reorder' />
 <input name='save' type='submit' value=' 保 存 ' style='cursor:hand;' />&nbsp;&nbsp;
 <input name='cancel' type='button' id='Cancel' value=' 取 消 '
  onclick="window.location.href='admin_special_list.jsp?channelId=#{channel.id }'"
  style='cursor:hand;' />
</div>
</form>
</pub:template>


</pub:declare>

<pub:process_template name="main" />
</body>
</html>