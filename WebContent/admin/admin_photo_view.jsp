<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.PhotoManage"
%><%
  // 初始化页面数据。
  PhotoManage admin_data = new PhotoManage(pageContext);
  admin_data.initViewPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>图片管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
 <script language="javascript" src='admin_photo_add.js'></script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="contentTabs" scope="page" purpose="">
 <pub:tab name="baseInfo" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="specialSet" text="所属专题" template="temp_specialSet" default="false" />
</pub:tabs>

<%@ include file="element_photo.jsp" %>
<%@ include file="element_column.jsp" %>
<%@ include file="tabs_tmpl2.jsp" %>

<pub:template name="main">
 #{call photo_manage_navigator("添加" + channel.itemName) }<br />
 #{call your_position}
<form method='post' name='myform' action='admin_photo_action.jsp' target='_self'>
<div style='padding:1px;'>
 #{call tab_js }
 #{call tab_header(contentTabs) }
 #{call tab_content(contentTabs) }
</div>

<p align='center'>
 <input name="__itemName" type='hidden' id="__itemName" value="#{channel.itemName}" />
 <input name='command' type='hidden' value='' />
 <input name='channelId' type='hidden' id='channelId' value='#{channel.id }' />
 <input name='photoId' type="hidden" value="#{photo.id }" />

 <input type='button' name='modify' value='修改/审核' onclick="window.location='admin_photo_add.jsp?channelId=#{channel.id}&amp;photoId=#{photo.id}'" />
 <input type='submit' name='submit' value=' 删 除 ' onclick="document.myform.command.value='delete'" />
 <!-- <input type='submit' name='submit' value=' 移 动 ' onclick="document.myform.command.value='move'" /> -->
 <input type='submit' name='submit' value='直接退稿' onclick="document.myform.command.value='reject'" />
 <input type='submit' name='submit' value='终审通过' onclick="document.myform.command.value='approve'" />
 <input type='submit' name='submit' value='设为固顶' onclick="document.myform.command.value='top'" />
 <input type='submit' name='submit' value='设为推荐' onclick="document.myform.command.value='elite'" /> 
</p>
<br />
</form>
 <% if (true) { %>
 #{call debug_info}
 <% } %>
</pub:template>



<pub:template name="debug_info">
<br/><br/><hr/><h2>DEBUG INFO</h2>
<li>channel = #{(channel) }
<li>photo = #{(photo) }
<li>channel_specials = #{(channel_specials) }
<li>channel_specials.schema = #{channel_specials.schema }
<li>photo.pictureList = #{photo.pictureList }
<li>photo.specialIds = #{photo.specialIds }
<br/><br/>
</pub:template>


<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>
  您现在的位置： <a href='admin_photo_list.jsp?channelId=#{channel.id }'>#{channel.name }管理</a>
   &gt;&gt; 添加#{channel.itemName }
  </td>
 </tr>
</table>
</pub:template>


<!-- 基本信息 -->
<pub:template name="temp_baseInfo">
<table width='100%' border='0' bgcolor='#FFFFFF' cellspacing='1' cellpadding='1'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属栏目：</td>
  <td>#{photo.colum.name}</td>
  <td rowspan='6'><img src='#{photo.thumbPicAbs }' width='150'/><br/>
  #{photo.title }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }名称：</td>
  <td>#{photo.title@html}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>更新时间：</td>
  <td>#{photo.lastModified@format }</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>评分等级：</td>
  <td>#{photo.stars}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>图片添加：</td>
  <td>#{photo.inputer@html}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>责任编辑：</td>
  <td>#{photo.editor@html}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>查看次数：</td>
  <td colspan='3'>本日：#{photo.dayHits}&nbsp;&nbsp;&nbsp;&nbsp;本周：#{photo.weekHits}&nbsp;&nbsp;&nbsp;&nbsp;本月：#{photo.monthHits }&nbsp;&nbsp;&nbsp;&nbsp;总计：#{photo.hits }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>图片地址：</td>
  <td colspan='3'>
   #{foreach picture in photo.pictureList}
   <div>#{picture.name}: <a href='#{picture.url}' target='_blank'>#{picture.url}</a></div>
   #{/foreach }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>图片简介：</td>
  <td colspan='3'>#{photo.description}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>图片属性：</td>
  <td colspan='3'>
   #{if photo.top}<font color='blue'>固顶#{channel.itemName}</font> #{/if}
   #{if photo.hot}<font color='red'>热门#{channel.itemName}</font> #{/if}
   #{if photo.commend}<font color='green'>推荐#{channel.itemName}</font> #{/if}
  </td>
 </tr>
</table>
</pub:template>



<!-- 所属专题 -->
<pub:template name="temp_specialSet">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属专题：</td>
  <td>
   <select name='specialIds' size='2' multiple='true' style='height:300px;width:260px;' disabled='disabled'>
 #{foreach special in channel_specials }
  <option value='#{special.id}' #{iif (photo.specialIds@contains(special.id), "selected", "")}>#{special.name}(#{iif (special.channelId==0, "全站", "本频道") })</option>
 #{/foreach }
 </select> <br />
 <input type='button' name='selectAll' value='  选定所有专题  ' onclick='selectAll()' disabled='disabled' /> <br />
 <input type='button' name='unSelectAll' value='取消选定所有专题' onclick='unSelectAll()' disabled='disabled' />
  </td>
 </tr>
</table></pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>
