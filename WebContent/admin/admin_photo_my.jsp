<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.PhotoManage"
%><%
  // 准备数据。
  PhotoManage admin_data = new PhotoManage(pageContext);
  admin_data.initMyPhotoPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>我添加的图片</title>
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src="admin_photo.js"></script>
</head>
<body>

<pub:declare>

<%@ include file="element_photo.jsp" %>
<%@ include file="element_column.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call photo_manage_navigator(channel.itemName + "管理首页") }
 #{call photo_my_manage_options }<br />
 #{call column_label_list(main_column_list, "admin_photo_my.jsp") }<br />

 #{call your_position}
 #{call show_photo_list }
 #{call operate_buttons }
 #{call pagination_bar(page_info)}
 #{call more_child_column_list }
 
 #{call form_action }

 #{call photo_search_bar }
 #{call photo_property_description }
</pub:template>



<pub:template name="photo_operate">
#{param photo}
<a href="admin_photo_add.jsp?channelId=#{channel.id}&amp;photoId=#{photo.id }">修改</a> 
<a href="admin_photo_action.jsp?command=delete&amp;channelId=#{channel.id }&photoId=#{photo.id}"
 onclick="return confirm('确定要删除此#{channel.itemName}吗？删除后你还可以从回收站中还原。');">删除</a> 
#{if photo.top }
 <a href="admin_photo_action.jsp?command=untop&amp;channelId=#{channel.id}&amp;photoId=#{photo.id}">解固</a> 
#{else }
 <a href="admin_photo_action.jsp?command=top&amp;channelId=#{channel.id }&amp;photoId=#{photo.id}">固顶</a> 
#{/if }
#{if photo.elite }
 <a href="admin_photo_action.jsp?command=unelite&amp;channelId=#{channel.id}&amp;photoId=#{photo.id}">取消推荐</a>
#{else }
 <a href="admin_photo_action.jsp?command=elite&amp;channelId=#{channel.id}&amp;photoId=#{photo.id}">设为推荐</a>
#{/if }
</pub:template>



<pub:template name="operate_buttons">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <td width="200" height="30">
    <input name="chkAll" type="checkbox" id="chkAll" onclick='CheckAll(this)'
    value="checkbox" /> 选中本页显示的所有#{channel.itemName}</td>
   <td>
   <input type='hidden' name='__itemName' id='__itemName' value='#{channel.itemName}' />
   <input type="button" value=" 批量删除 " onClick="deleteItems()" /> 
   <input type="button" value=" 批量移动 " onClick="moveToColumn()" /> 
   <input type="button" value=" 审核通过 " onClick="approItems(true)" /> 
   <input type="button" value=" 取消审核 " onClick="approItems(false)" /> 
   </td>
  </tr>
 </table>
</pub:template>


<pub:template name="your_position">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
   <td height="22">
   您现在的位置： #{channel.name }管理 &gt;&gt; 
   <a href="admin_photo_my.jsp?channelId=#{channel.id }"> #{channel.itemName }管理 </a>
   #{call column_tier(column_nav, "admin_photo_my.jsp") }
    &gt;&gt; <font color="red">#{admin.name }</font> 添加的#{channel.itemName }
    </td>
   <td width="200" height="22" align="right">
   <select onchange="location='admin_photo_my.jsp?channelId=#{channel.id }&amp;columnId='+this.value">
    <option value="0">跳转栏目至...</option>
    #{call dropDownColumns(0, dropdown_columns) }
   </select>
   </td>
  </tr>
</table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>