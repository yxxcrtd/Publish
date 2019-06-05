<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SoftManage"
%><%
  // 初始化页面数据。
  SoftManage admin_data = new SoftManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>软件管理</title>
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src="admin_soft.js"></script>
</head>
<body>

<pub:declare>

<%@ include file="element_soft.jsp" %>
<%@ include file="element_column.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call soft_manage_navigator(channel.itemName + "管理首页")}
 #{call soft_manage_options}<br />
 #{call column_label_list(main_column_list, "admin_soft_list.jsp")}<br />
 #{call your_position(channel.itemName + '管理')}
 #{call show_soft_list('常规管理操作')}
 #{call operate_button}
 #{call pagination_bar(page_info)}
 #{call more_child_column_list }
 
 #{call form_action}<br />

 #{call soft_search_bar}
 #{call soft_property_description}
</pub:template>


<%-- called from 'element_soft.jsp::show_soft_list' --%>
<pub:template name="soft_operate">
#{param soft}
 <a href="admin_soft_add.jsp?channelId=#{channel.id }&columnId=#{soft.columnId}&softId=#{soft.id}">修改</a> 
 <a href="admin_soft_action.jsp?command=delete&amp;channelId=#{channel.id }&softId=#{soft.id }"
  onclick="return confirm('确定要删除此#{channel.itemName}吗？删除后你还可以从回收站中还原。');">删除</a> 
 #{if soft.top}
  <a href="admin_soft_action.jsp?command=untop&amp;channelId=#{channel.id }&amp;softId=#{soft.id}">解固</a> 
 #{else}
  <a href="admin_soft_action.jsp?command=top&amp;channelId=#{channel.id }&amp;softId=#{soft.id }">固顶</a> 
 #{/if}
 #{if soft.commend}
  <a href="admin_soft_action.jsp?command=uncommend&amp;channelId=#{channel.id}&amp;softId=#{soft.id }">取消推荐</a>
 #{else}
  <a href="admin_soft_action.jsp?command=commend&amp;channelId=#{channel.id }&amp;softId=#{soft.id }">设为推荐</a>
 #{/if}
</pub:template>


<pub:template name="operate_button">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
 <td width="200" height="30">
  <input name="chkAll" type="checkbox" id="chkAll" onclick="CheckAll(this)"
   value="checkbox" /> 选中本页显示的所有#{channel.itemName}</td>
 <td>
 <input type="button" value=" 批量删除 " onclick="deleteItems()" /> 
 <input type="button" value=" 批量移动 " onclick="moveToColumn()" /> 
 <input type="button" value=" 审核通过 " onclick="approItems(true)" /> 
 <input type="button" value=" 取消审核 " onclick="approItems(false)" /> 
 </td>
</tr>
</table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />
</body>
</html>
