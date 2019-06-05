<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SoftManage"
%><%
  // 初始化页面数据。
  SoftManage admin_data = new SoftManage(pageContext);
  admin_data.initRecyclePage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>软件回收站管理</title>
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src="admin_recycle.js"></script>
</head>
<body>

<pub:declare>
 
<%@ include file="element_soft.jsp" %>
<%@ include file="element_column.jsp" %>


<!-- 主模板 -->
<pub:template name="main">
 #{call soft_manage_navigator(channel.itemName + "回收站管理") }
 #{call soft_manage_options }<br />   
 #{call column_label_list(main_column_list, "admin_recycle_soft_list.jsp") }<br />

 #{call your_position(channel.itemName + '回收站管理')}
 #{call show_soft_list('回收站管理操作')}
 #{call operate_button }
 #{call pagination_bar(page_info) }
 #{call more_child_column_list }
 
 #{call form_action }

 #{call soft_search_bar }
 #{call soft_property_description }
</pub:template>


<%-- called from 'element_soft.jsp::show_soft_list' --%>
<pub:template name="soft_operate">
#{param soft}
 <a href="admin_recycle_item_delete.jsp?channelId=#{channel.id }&columnId=#{soft.columnId }&itemId=#{soft.id}"
  onclick="return confirm('确定要删除此#{channel.itemName }吗？删除后你将无法还原。');">彻底删除</a> 
 <a href="admin_recycle_item_recover.jsp?channelId=#{channel.id }&columnId=#{soft.columnId }&itemId=#{soft.id }">还原</a>     
</pub:template>


<pub:template name="operate_button">
<table>
 <tr>
  <td width="200" hight="30">
   <input name="chkAll" type="checkbox" id="chkAll" onclick="CheckAll(this)" value="checkbox"/>选中本页显示的所有#{channel.itemName }</td>
  <td>
   <input type="button" value="彻底删除" onclick="destroyItems()"/>
   <input type="button" value="清空回收站" onclick="clearItems()"/>
   <input type="button" value="还原选定#{channel.itemName }" onclick="recoverItems()"/>
   <input type="button" value="还原所有#{channel.itemName }" onclick="recoverAllItems()"/>
  </td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>