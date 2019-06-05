<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.PhotoManage"
%><%
  // 准备数据。
  PhotoManage admin_data = new PhotoManage(pageContext);
  admin_data.initListPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>图片管理</title>
 <script type="text/javascript" src="main.js"></script>
 <!-- <script type="text/javascript" src="photoshare.js"></script>  -->
 <script type="text/javascript" src="admin_photo.js"></script>
</head>
<body>
<pub:declare>

<%@ include file="element_photo.jsp" %>
<%@ include file="element_column.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call photo_manage_navigator(channel.itemName + "管理首页") }
 #{call photo_manage_options }<br />
 #{call column_label_list(main_column_list, "admin_photo_list.jsp") }<br />

 #{call your_position }
 #{call show_photo_list }
 #{call operate_buttons }
 #{call pagination_bar(page_info) }
 #{call more_child_column_list }
 
 #{call form_action}

 #{call photo_search_bar }
 #{call photo_property_description }
</pub:template>



<pub:template name="your_position">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
  <td height="22">
   您现在的位置： #{channel.name }管理 &gt;&gt; 
   <a href="admin_photo_list.jsp?channelId=#{channel.id }"> #{channel.itemName }管理 </a>
   #{call column_tier(column_nav, "admin_photo_list.jsp") }
    &gt;&gt; 所有#{channel.itemName }

    </td>
   <td width="200" height="22" align="right">
   <select onchange="location='admin_photo_list.jsp?channelId=#{channel.id }&amp;columnId='+this.value">
    <option value="#{channel.rootColumnId }">跳转栏目至...</option>
    #{call dropDownColumns(0, dropdown_columns) }
   </select>
   </td>
  </tr>
</table>
</pub:template>


<pub:template name="operate_buttons"> 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <td width="200" height="30"><input name="chkAll"
    type="checkbox" id="chkAll" onclick='CheckAll(this)'
    value="checkbox" /> 选中本页显示的所有#{channel.itemName }</td>
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
