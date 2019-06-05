<%@ page language="java" contentType="text/html; charset=gb2312"
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href='admin_style.css' rel='stylesheet' type='text/css'>
<title>栏目合并</title>
<script type="text/javascript" language="javascript" src="main.js" ></script>
<script type="text/javascript">
<!--
// 保存合并的结果。
function saveUnite() {
	var columnId = byId("columnId");
	var targetColumnId = byId("targetColumnId");
	var parentPath = columnId.options[columnId.selectedIndex].parentPath;
	var parentPath2 = targetColumnId.options[targetColumnId.selectedIndex].parentPath;
	if (columnId.value == targetColumnId.value) {
		alert("不能在同一个栏目内进行操作。");
		return false;
	} else if (parentPath2.indexOf(parentPath + new Number(columnId.value).toString(32) + "/") > -1) {
		alert("不能将一个栏目合并到其下属栏目中");
		return false;
	}
	return true;
}
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


<!-- 导入栏目管理相关的元素。column_manage_navigator -->
<%@ include file="element_column.jsp" %>
<pub:template name="main">
#{call column_manage_navigator }
<br />
<table width='100%'>
	<tr>
		<td align='left'>您现在的位置：<a href='admin_column_list.jsp?channelId=#{channel.id }'>#{channel.itemName }栏目管理</a>&nbsp;&gt;&gt;&nbsp;栏目合并</td>
	</tr>
</table>
<form action="admin_column_unite_save.jsp" method="post" onsubmit="return saveUnite();">
<table id="root" width='100%' border='0' align='center' cellpadding='0' cellspacing='1' class='border'>
	<tbody>
	<tr class='title' height='22'>
		<th>文章栏目合并</th>
	</tr>
	<tr class='tdbg'><td align="center">	
	<br />
	将栏目
	<select name="columnId" id="columnId">
	#{call dropDownColumns(0, dropdown_columns) }
	</select>
	合并到
	<select name="targetColumnId" id="targetColumnId">
	#{call dropDownColumns(0, dropdown_columns) }
	</select>
	<br />
	<br />
	<div align="center">
	<input type="hidden" name="channelId" value="#{channel.id }" />
	<input type='submit' value=' 合并栏目 ' style='cursor:hand;' />&nbsp;&nbsp;
	<input type='button' value=' 取 消 '
	onClick="window.location.href='admin_column_list.jsp?channelId=#{channel.id }'"
	style='cursor:hand;' />	</div>
	<br />
	</td></tr>
	<tr class='tdbg'><td style="padding-left: 20px;">
	<p>
	<br />
	<b>注意事项：</b><br />
    所有操作不可逆，请慎重操作！！！<br />
    不能在同一个栏目内进行操作，不能将一个栏目合并到其下属栏目中。目标栏目中不能含有子栏目。<br />
    合并后您所指定的栏目（或者包括其下属栏目）将被删除，所有文章将转移到目标栏目中。
    </p>
	</td></tr>
	</tbody>
</table>
</form>
</pub:template>

<pub:process_template name="main" />
</body>
</html>