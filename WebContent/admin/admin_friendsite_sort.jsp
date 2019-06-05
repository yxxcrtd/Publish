<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.FriendSiteManage"
%><%
  // 管理数据初始化。
  FriendSiteManage admin_data = new FriendSiteManage(pageContext);
  admin_data.initReorderPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript">
<!--
// 友情链接标识
var fsIds = "";
// 将指定节点向上移动。

function moveUp(nodeId) {
 var node = byId(nodeId);
 var previous = node.previousSibling;
 // 实际的移动操作。

 if (previous != null) {
  node.swapNode(previous);
 }
 setMoveLinkStyle(nodeId, previous); 
}
// 将指定节点向下移动。

function moveDown(nodeId) {
 var node = byId(nodeId);
 var next = node.nextSibling;
 // 实际的移动操作。

 if (next != null) {
  node.swapNode(next);
 }
 setMoveLinkStyle(nodeId, next);
}
// 设置移动链接的样式。

function setMoveLinkStyle(nodeId, swapNode) {
 var node = byId(nodeId);
 // 设置上移操作链接的是否可用颜色。

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
 // 设置下移操作链接的是否可用颜色。

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
// 得到当前排序的栏目ID，用逗号分割。

function getFsIds() {
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

// 保存栏目排序结果。

function saveOrder() {
 var ids = getFsIds();
 if (ids == fsIds) {
  alert("没有改变排序，不需要执行保存操作。");
  return;
 }
 if (ids.length < 1) {
  alert("没有可以排序的栏目。");
 } else {
  var url = "admin_friendsite_action.jsp?command=sort&ids=" + ids;
  location = url;
 }
}
// 注册页面初始化事件。

(function(){
 if(document.all){ // IE 注册方法
  document.attachEvent("onreadystatechange", function(){
   if(document.readyState == "complete"){
    fsIds = getFsIds();
   }
  });
 } else { // 其他浏览器的注册方法。

  document.addEventListener("DOMContentLoaded", function(e) {channelIds = getchannelIds();}, null);
 }
})();

// -->
</script>
 <title>友情链接排序</title>
</head>
<body>

<pub:template name="main">
 #{call fs_admin_help}
 #{call fs_sort_form}
</pub:template>

<pub:template name="fs_sort_form">
 <table id='root' class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
  <tr class='title' height='22'>
   <td width='80' align='center'><strong>链接类别</strong></td>
   <td width='60' align='center'><strong>链接类型</strong></td>
   <td align='center'><strong>网站名称</strong></td>
   <td width='100' align='center'><strong>网站LOGO</strong></td>
   <td width='60' align='center'><strong>站长</strong></td>
   <td width='100' align='center'><strong>操作</strong></td>
  </tr>
 #{foreach fs in fs_list}
  <tr class='tdbg' id="#{fs.id }"  onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
   <td width='80' align='center'>
    #{if(!(fs.kind==null)) }<a href='admin_friendsite_list.jsp?approved=#{para.approved}&type=0&kindId=#{fs.kind.id}'>#{fs.kind.name}</a><br/>#{/if }
   </td>
   <td width='60' align='center'>
    #{if fs.linkType == 1}文字链接
    #{elseif fs.linkType == 2}图片链接
    #{/if}
   </td>
   <td>
    <a href='#{fs.siteUrl}' target='blank' title='网站名称：#{fs.siteName}
 网站地址：#{fs.siteUrl}
 评分等级：#{fs.stars}
 点 击 数：#{fs.hits}
 更新时间：#{fs.lastModified}
 网站简介：#{fs.description}'>#{fs.siteName}</a>
   </td>
   <td width='100' align='center'>
     #{if(fs.logo != "") }<img src="#{fs.logo }" width='88' height='31' alt='#{fs.siteName }' />#{/if }
   </td >
   <td width='60' align='center'>
    <a href='mailto:#{fs.siteEmail}'>#{fs.siteAdmin}</a>
   </td>   
   <td width='100' align='center'>
     <a style="color: #0000ff; visibility: #{iif (fs@is_first, "hidden", "visible") }" id="#{fs.id }_up" href="javascript:moveUp('#{fs.id }')">上移</a>&nbsp;
  <a style="color: #0000ff; visibility: #{iif (fs@is_last, "hidden", "visible") }" id="#{fs.id }_down" href="javascript:moveDown('#{fs.id }')">下移</a>
   </td>
  </tr>
 #{/foreach}
 </table>
 <div align="center">
  <input name='save' type='button' value=' 保 存 ' onclick="saveOrder()" />&nbsp;&nbsp;
  <input name='cancel' type='button' id='Cancel' value=' 取 消 ' onClick="window.location.href='admin_friendsite.jsp'"
  style='cursor:hand;' />
 </div>
</pub:template>

<%@ include file="element_fs.jsp" %>

<pub:process_template name="main" />

</body>
</html>
