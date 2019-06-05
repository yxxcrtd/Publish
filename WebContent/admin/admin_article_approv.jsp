<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ArticleManage"
%><%
  // 初始化页面数据。
  ArticleManage admin_data = new ArticleManage(pageContext);
  admin_data.initApprovPage();
  
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
 #{call article_manage_navigator(channel.itemName + "审核") }
 #{call article_manage_options }<br />
 #{call column_label_list(main_column_list, "admin_article_approv.jsp") }<br />
 #{call your_position}
<form name="myform" method="post" action="" style="margin:0px;" >
 #{call show_article_list}
 #{call form_buttons}
</form>
 #{call pagination_bar(page_info) }
 #{call more_child_column_list }
 
 #{call form_action}<br />
 #{call article_search_bar }
 #{call aritlce_property_description }
</pub:template>



<pub:template name="your_position">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
  <td height="22">
   您现在的位置： <a href="admin_article_list.jsp?channelId=#{channel.id}">#{channel.name }管理</a> &gt;&gt; 
   <a href="admin_article_approv.jsp?channelId=#{channel.id}&status=0"> #{channel.itemName }审核 </a>
   #{call column_tier(column_nav, "admin_article_approv.jsp") }
    &gt;&gt; 所有#{channel.itemName }
  </td>
  <td width="200" height="22" align="right">
   <select onchange="location='admin_article_approv.jsp?channelId=#{channel.id}&amp;status=0&amp;columnId='+this.value">
    <option value="#{channel.rootColumnId }">跳转栏目至...</option>
    #{call dropDownColumns(0, dropdown_columns) }
   </select>
  </td>
 </tr>
</table>
</pub:template>



<pub:template name="article_operate">
#{param article}
 #{if (article.status == 0)}
  <a href="admin_article_add.jsp?channelId=#{channel.id}&articleId=#{article.id}">查看/审核</a>
  <a href="admin_article_action.jsp?command=approve&channelId=#{channel.id}&articleId=#{article.id}">通过</a>
 #{else }
  <a href="admin_article_action.jsp?command=unappr&channelId=#{channel.id}&articleId=#{article.id}">取消审核</a>
 #{/if }
  <a href="admin_article_action.jsp?command=reject&amp;channelId=#{channel.id}&articleId=#{article.id}">退稿</a>
</pub:template>



<!-- FORM 的按钮 -->
<pub:template name="form_buttons">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 <tr>
  <td width="200" height="30">
   <input name="chkAll" type="checkbox" id="chkAll" onclick="CheckAll(this)" value="checkbox" /> 
     选中本页显示的所有#{channel.itemName }
  </td>
  <td>
   <input type="button" value=" 批量审核通过 " onClick="approItems(true)" /> 
   <input type="button" value=" 批量取消审核 " onClick="approItems(false)" /> 
   <input type="button" value=" 批量删除 " onClick="deleteItems()" /> 
  </td>
 </tr>
</table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>
