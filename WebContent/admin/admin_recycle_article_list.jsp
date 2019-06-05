<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ArticleManage"
%><%
  // 产生管理数据。
  ArticleManage admin_data = new ArticleManage(pageContext);
  admin_data.initRecyclePage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>文章回收站管理</title>
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src="admin_recycle.js"></script>
</head>
<body>

<pub:declare>
 
<%@ include file="element_article.jsp" %>
<%@ include file="element_column.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call article_manage_navigator(channel.itemName + "回收站管理") }
 #{call article_manage_options }<br/>
 #{call column_label_list(main_column_list, "admin_recycle_article_list.jsp") }<br />

 #{call your_position}
 #{call show_article_list("回收站操作") }
 #{call operate_buttons}
 #{call pagination_bar(page_info) }
 #{call more_child_column_list }
 
 #{call form_action}

 #{call article_search_bar }
 #{call aritlce_property_description }
</pub:template>


<%-- callback from show_article_list --%>
<pub:template name="article_operate">
#{param article}
 <a href="admin_recycle_action.jsp?command=destroy&amp;channelId=#{channel.id }&itemId=#{article.id}"
  onclick="return confirm('确定要删除此#{channel.itemName }吗？删除后你将无法还原。');">彻底删除</a> 
 <a href="admin_recycle_action.jsp?command=recover&amp;channelId=#{channel.id }&itemId=#{article.id}">还原</a>
</pub:template>



<%-- 操作按钮 --%>
<pub:template name="operate_buttons">
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



<%-- 管理位置 --%>
<pub:template name="your_position">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
  <td height="22">
    您现在的位置：#{channel.itemName }回收站管理&gt;&gt;
    <a href="admin_recycle_article_list.jsp?channelId=#{channel.id}">回收站管理</a>
    #{call column_tier(column_nav, "admin_recycle_article_list.jsp") }
     &gt;&gt; 所有#{channel.itemName }
   </td>
   <td width="200" hight="22" align="right">
    <select onchange="location='admin_recycle_article_list.jsp?channelId=#{channel.id}&amp;columnId='+this.value">
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