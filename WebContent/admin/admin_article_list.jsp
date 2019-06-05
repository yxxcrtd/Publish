<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ArticleManage"
%><%
  // 初始化页面数据。
  ArticleManage admin_data = new ArticleManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>文章管理</title>
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src='admin_article.js'></script>
</head>
<body>
<pub:declare>

<%@ include file="element_article.jsp" %>
<%@ include file="element_column.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call article_manage_navigator(channel.itemName + "管理首页") }
 #{call article_manage_options }<br />
 #{call column_label_list(main_column_list, "admin_article_list.jsp") }<br />
 #{call your_position}
 #{call show_article_list}
 #{call operate_button}
 #{call pagination_bar(page_info)}
 #{call more_child_column_list }
 #{call form_action}<br />

#{call article_search_bar}
#{call aritlce_property_description}
</pub:template>


<%-- 特定的文章操作 --%>
<pub:template name="article_operate">
#{param article}
<span align='center'>
  <a href="admin_article_add.jsp?channelId=#{channel.id}&articleId=#{article.id}">修改</a> 
  <a href="admin_article_action.jsp?command=delete&amp;channelId=#{channel.id}&articleId=#{article.id}"
   onclick="return confirm('确定要删除此#{channel.itemName}吗？删除后你还可以从回收站中还原。');">删除</a> 
 #{if (article.top) }
  <a href="admin_article_action.jsp?command=untop&channelId=#{channel.id}&articleId=#{article.id}">解固</a> 
 #{else }
  <a href="admin_article_action.jsp?command=top&channelId=#{channel.id}&articleId=#{article.id}">固顶</a> 
 #{/if }
 #{if (article.commend) }
  <a href="admin_article_action.jsp?command=unelite&channelId=#{channel.id}&articleId=#{article.id}">取消推荐</a>
 #{else }
  <a href="admin_article_action.jsp?command=elite&channelId=#{channel.id}&articleId=#{article.id}">设为推荐</a>
 #{/if }
 </span>
</pub:template>


<%-- 输出一些调试信息 --%>
<pub:template name="debug_info">
<br/><br/><br/><hr/><h2>DEBUG INFO</h2>
 <li>channel = #{(channel) }
 <li>dropdown_columns = #{(dropdown_columns) }
 <li>column_nav = #{(column_nav) }
 <li>article_list = #{(article_list) }
 <li>page_info = #{(page_info) }
 <li>request = #{(request) }
<br/><br/><br/>
</pub:template>



<pub:template name="your_position">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
  <td height="22">
    您现在的位置： #{channel.name }管理 &gt;&gt; 
   <a href="admin_article_list.jsp?channelId=#{channel.id}"> #{channel.itemName }管理 </a>
  #{call column_tier(column_nav, "admin_article_list.jsp") }
   &gt;&gt; 所有#{channel.itemName }
  </td>
  <td width="200" height="22" align="right">
   <select onchange="location='admin_article_list.jsp?channelId=#{channel.id }&amp;columnId='+this.value">
    <option value="#{channel.rootColumnId }">跳转栏目至...</option>
    #{call dropDownColumns(0, dropdown_columns) }
   </select>
  </td>
 </tr>
</table>
</pub:template>


<pub:template name="operate_button">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
 <td width="200" height="30"><input name="chkAll"
  type="checkbox" id="chkAll" onclick="CheckAll(this)"
  value="checkbox" /> 选中本页显示的所有#{channel.itemName}</td>
  <td>
  <input type="button" value=" 批量删除 " onClick="deleteItems()" /> 
  <input type="button" value=" 批量移动 " onClick="moveToColumn()" /> 
  <input type="button" value=" 审核通过 " onClick="approItems(true)" /> 
  <input type="button" value=" 取消审核 " onClick="approItems(false)" /> 
  </td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />
</body>
</html>
