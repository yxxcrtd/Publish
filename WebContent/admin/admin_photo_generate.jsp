<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.PhotoManage"
%><%
  // 准备数据。
  PhotoManage admin_data = new PhotoManage(pageContext);
  admin_data.initGeneratePage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>图片生成</title>
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src="admin_photo.js"></script>
</head>
<body>
<pub:declare>

<%@ include file="element_photo.jsp" %>
<%@ include file="element_column.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call photo_manage_navigator(channel.itemName + "生成") }
 #{call generate_option}<br />
 #{call column_label_list(main_column_list, "admin_photo_generate.jsp") }<br />

 #{call your_position}
 #{call show_photo_list }
 #{call operate_buttons }
 #{call form_action }
 
 #{call pagination_bar(page_info) }
 #{call more_child_column_list }
 
 #{call photo_search_bar }
 #{call photo_property_description }
</pub:template>


<pub:template name="photo_operate">
#{param photo}
#{if(photo.status == 1) }
 <a href="admin_item_generate_action.jsp?command=generate&channelId=#{channel.id}&itemId=#{photo.id}">生成文件</a>
 #{if(photo.isGenerated) }
  <a href="#{InstallDir }#{photo.staticPageUrl }" target="_blank">查看文件</a>
  <a href="admin_item_generate_action.jsp?command=delete&channelId=#{channel.id }&itemId=#{photo.id}">删除文件</a>
 #{/if }
#{/if }
</pub:template>



<pub:template name="operate_buttons">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <td width="200" height="30">
   <input name="chkAll" type="checkbox" id="chkAll" onclick='CheckAll(this)' 
    value="checkbox" /> 选中本页显示的所有#{channel.itemName }</td>
   <td>
   #{if(columnId==0 || columnId==channel.rootColumnId) }
   <input type="submit" value="生成首页" onClick="setCommand('channel');" />
   #{/if }
   <input type="submit" value="生成#{iif(columnId!=0 && columnId!=channel.rootColumnId,"当前","所有") }栏目列表页" onClick="setCommand('column');" /> 
   <input type="submit" value="生成#{iif(columnId!=0 && columnId!=channel.rootColumnId,"当前栏目的","所有") }#{channel.itemName }" onClick="setCommand('allItems');" /> 
   <input type="submit" style="width: 110" value="生成选定的#{channel.itemName }" onClick="if(checkSelect()){setCommand('generate');}else{return false;}" /> 
   <input type="submit" style="width: 160" value="删除选定#{channel.itemName }的HTML文件" onClick="if(checkSelect()){setCommand('delete');}else{return false;}" /> 
   </td>
  </tr>
</table>
</pub:template>


<pub:template name="generate_option">
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
  <tr class="tdbg">
 <td width="70" height="30"><strong>#{channel.itemName}选项：</strong></td>
 <td>
  <input name="isGenerated" type="radio" onclick="location = setUrlParam(location.href, 'isGenerated', 'null');"
   value="null" #{if request.isGenerated == null}checked#{/if } /> 所有#{channel.itemName}
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'isGenerated', 'false');" 
   value="false" #{if request.isGenerated == false}checked#{/if} /> 未生成的#{channel.itemName} 
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'isGenerated', 'true');" 
   value="true" #{if request.isGenerated == true}checked#{/if} /> 已生成的#{channel.itemName}
 </td>
 <td width="50%"></td>
  </tr>
</table>
</pub:template>



<pub:template name="your_position">
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
   <td height="22">
   您现在的位置： #{channel.name }管理 &gt;&gt; 
   <a href="admin_photo_generate.jsp?channelId=#{channel.id }"> #{channel.itemName }生成 </a>
   #{call column_tier(columnTier, "admin_photo_generate.jsp") }
    &gt;&gt; 所有#{channel.itemName }
    </td>
   <td width="200" height="22" align="right">
   <select onchange="location='admin_photo_generate.jsp?channelId=#{channel.id }&amp;columnId='+this.value">
    <option value="#{channel.rootColumnId }">跳转栏目至...</option>
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
