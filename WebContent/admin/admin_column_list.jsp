<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%@page import="com.chinaedustar.publish.admin.ColumnManage" %>

<%
  // 产生管理页面所需的数据。
  ColumnManage admin_data = new ColumnManage(pageContext);
  admin_data.initListPage();
  
  // schema = [id, name, parentId, parentPath, orderPath, description, columnType, columnDir, 
  // openType, showOnTop, showOnIndex, isElite, enableAdd, enableProtect, prefix, childNum]
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>栏目管理</title>
</head>
<body>

<pub:declare>
<!-- 导入栏目管理相关的元素。-->
<%@include file="element_column.jsp" %>

<pub:template name="main">
 #{call column_manage_navigator }<br />
 #{call your_position}<br />
 #{call temp_columnList }
 #{call column_manage_description }
 
<% if (false) { %>
 #{call debug_info}
<% } %>
</pub:template>


<pub:template name="debug_info">
<br/><br/><hr/><h2>DEBUG INFO</h2>
<li>column_list.schema = #{column_list.schema }
#{foreach column in column_list }
<li> foreach column = #{(column)}
#{/foreach }
<br/><br/><br/><br/>
</pub:template>


<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>您现在的位置：#{channel.itemName}栏目管理&nbsp;&gt;&gt;&nbsp;首页</td>
 </tr>
</table>
</pub:template>


<pub:template name="temp_columnList">
<table width='100%' border='0' align='center' cellpadding='0' cellspacing='1' class='border'>
 <tr class='title' height='22'>
  <td width='30' align='center'><strong>ID</strong></td>
  <td align='center'><strong>栏目名称[目录](子栏目数)</strong></td>
  <td width='100' align='center'><strong>栏目属性</strong></td>
  <td width='240' align='center'><strong>操作选项</strong></td>
 </tr>
#{foreach column in column_list }
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td width='30' align='center'>#{column.id }</td>
  <td>
   #{foreach ch in column.treeFlag }
    #{if ch == 'T'}
     <img src='images/tree_line1.gif' valign='absmiddel' border='0' />#{elseif ch == 'L' }
     <img src='images/tree_line2.gif' valign='absmiddel' border='0' />#{elseif ch == '|' }
     <img src='images/tree_line3.gif' valign='absmiddel' border='0' />#{elseif ch == 'B' }
     <img src='images/tree_line4.gif' valign='absmiddel' border='0' />#{elseif ch == '+' }
     <img src='images/tree_folder4.gif' valign='absmiddel' border='0' />#{elseif ch == '-' }
     <img src='images/tree_folder3.gif' valign='absmiddel' border='0' />#{/if }
   #{/foreach }
    <b><a href='admin_column_add.jsp?channelId=#{channel.id }&amp;columnId=#{column.id }'>#{column.name }</a></b>
    [#{column.columnDir }]#{iif(column.hasChild, "(" + column.childCount + ")", "") }
  </td> 
  <td align='center' width='100'>
   #{iif (column.openType == 1, "新 ", "原 ") }
   #{iif (column.showOnIndex, "首 ", " ") }
   #{iif (column.isElite, "列 ", " ") }
   #{iif (column.enableAdd, "开 ", "锁 ") }
   #{iif (column.enableProtect, "保 ", " ") }
  </td> 
  <td align='center' width='240'>
   <a href='admin_column_add.jsp?channelId=#{channel.id }&amp;parentId=#{column.id}'>添加子栏目</a> | 
   <a href='admin_column_add.jsp?channelId=#{channel.id }&amp;columnId=#{column.id }'>修改设置</a> | 
   <a href='admin_column_action.jsp?command=clear&amp;channelId=#{channel.id }&amp;columnId=#{column.id}' 
     onclick="return confirm('确定要清空此栏目（#{column.name }）下的所有#{channel.itemName }吗？');">清空</a> | 
   <a href='admin_column_action.jsp?command=delete&amp;channelId=#{channel.id }&amp;columnId=#{column.id }' 
     onclick="return confirm('确定要删除此栏目（#{column.name}）吗？该栏目下的所有#{channel.itemName}也将全部删除。');">删除</a>
  </td> 
 </tr> 
#{/foreach } 
</table>
 
<form name='form1' action='admin_column_action.jsp' method='post' style='margin:0px;'>
<table width='100%'>
 <tr>
  <td align='center'>
   <input name='submit' type='submit' value=' 更新栏目菜单JS ' /> 
   <input name='command' type='hidden' id='command' value='update_js' />
   <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' /> 
  </td>
 </tr>
</table>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
