<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href='admin_style.css' rel='stylesheet' type='text/css'>
<title>栏目排序</title>
<script type="text/javascript" language="javascript" src="main.js" ></script>
<script type="text/javascript">
<!--
// 栏目的子栏目ID的初始值。 
var columnIds = "";
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
function getColumnIds() {
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
// 注册页面初始化事件。
(function(){
	if(document.all){	// IE 注册方法
		document.attachEvent("onreadystatechange", function(){
			if(document.readyState == "complete"){
				columnIds = getColumnIds();
			}
		});
	} else {	// 其他浏览器的注册方法。
		document.addEventListener("DOMContentLoaded", function(e) {columnIds = getColumnIds();}, null);
	}
})();

// -->
</script>
</head>
<body>
<!-- 根据 Request 的 channelId 获取 ChannelObject. -->
<pub:data purpose="为这个页面获取 Channel 的数据" var="channel" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />
<!-- 定义下拉栏目列表的数据。 -->
<pub:data var="dropdown_columns" 
	provider="com.chinaedustar.publish.admin.ColumnsDropDownDataProvider" />
<!-- 得到栏目的子栏目的数据，DataTable 包含 id, name 。 -->
<pub:data var="simpleChild" 
	provider="com.chinaedustar.publish.admin.ColumnSimpleChildDataProvider" />
<!-- 得到栏目层次结构的ArrayList<Column>数据。 -->
<pub:data var="columnTier" 
	provider="com.chinaedustar.publish.admin.ColumnTierDataProvider" />


<!-- 导入栏目管理相关的元素。column_manage_navigator -->
<%@ include file="element_column.jsp" %>
<%@ include file="element_article.jsp" %>
<pub:template name="main">
<script type="text/javascript">
<!--
// 保存栏目排序结果。
function saveOrder() {
	var ids = getColumnIds();
	if (ids == columnIds) {
		alert("没有改变排序，不需要执行保存操作。");
		return;
	}
	if (ids.length < 1) {
		alert("没有可以排序的栏目。");
	} else {
		var url = "admin_column_order_save.jsp?channelId=#{channel.id }&columnId=#{columnId }&columnIds=" + ids;
		location = url;
	}
}
// -->
</script>
#{call column_manage_navigator }
<br />
<table width='100%'>
	<tr>
		<td align='left'>您现在的位置：<a href='admin_column_list.jsp?channelId=#{channel.id }'>#{channel.itemName }栏目管理</a>&nbsp;
		#{call column_tier(columnTier, "admin_column_order.jsp") }&gt;&gt;&nbsp;栏目排序</td>
	</tr>
</table>
<br />
<div>
	请选择要排序的栏目：
	<select onchange="location='admin_column_order.jsp?channelId=#{channel.id }&columnId=' + this.value">
	#{call dropDownColumns(columnId, dropdown_columns) }
	</select>
</div>
<br />
<table id="root" width='100%' border='0' align='center' cellpadding='0' cellspacing='1' class='border'>
	<tbody>
	<tr class='title' height='22'>
		<th>栏目名称</th>
		<th>操作</th>
	</tr>
	#{foreach column in simpleChild }
	<tr class='tdbg' id="#{column.id }" onmouseout="this.className='tdbg'"	onmouseover="this.className='tdbgmouseover'">
		<td align="center">#{column.name }</td>
		<td>
		<a style="color: #0000ff; visibility: #{iif (column@is_first, "hidden", "visible") }" id="#{column.id }_up" href="javascript:moveUp('#{column.id }')">上移</a>&nbsp;
		<a style="color: #0000ff; visibility: #{iif (column@is_last, "hidden", "visible") }" id="#{column.id }_down" href="javascript:moveDown('#{column.id }')">下移</a>
		</td>
	</tr>
	#{/foreach }
	</tbody>
</table>
<br />
<div align="center">
	<input name='save' type='button' value=' 保 存 ' style='cursor:hand;' onclick="saveOrder()" />&nbsp;&nbsp;
	<input name='cancel' type='button' id='Cancel' value=' 取 消 '
	onClick="window.location.href='admin_column_list.jsp?channelId=#{channel.id }'"
	style='cursor:hand;' />
</div>
</pub:template>

<pub:process_template name="main" />
</body>
</html>