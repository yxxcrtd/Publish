<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ArticleManage"
%><%
  // 初始化页面数据。
  ArticleManage admin_data = new ArticleManage(pageContext);
  admin_data.initSpecialArticleListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>专题文章管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
 <script type='text/javascript' src='admin_special_item.js'></script>
</head>
<body>
<pub:declare>

<pub:template name="main">
 #{call article_manage_navigator("专题" + channel.itemName + "管理") }
 #{call article_manage_options }<br />
 #{call special_nav }<br />
 #{call your_position}
 #{call show_article_list }<br />
 #{call form_buttons }
 #{call pagination_bar(page_info) }
 #{call form_action }

 #{call article_search_bar }<br />
 #{call aritlce_property_description }
</pub:template>

<%@ include file="element_column.jsp" %>
<%@ include file="element_article.jsp" %>

<pub:template name="special_nav">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22'> |
 #{foreach special in special_list }
  <a href="admin_special_article_list.jsp?channelId=#{special.channelId }&amp;specialId=#{special.id }"> #{special.name} </a> | 
 #{/foreach }
  </td>
 </tr>
</table>
</pub:template>


<pub:template name="your_position">
<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>
  <tr>
    <td height='22'>
    您现在的位置：&nbsp;<a href="admin_article_list.jsp?channelId=#{channel.id }">#{channel.name }管理</a>&nbsp;&gt;&gt;&nbsp;<a
      href='admin_special_article_list.jsp?channelId=#{channel.id }'>专题#{channel.itemName }管理</a>
      &nbsp;&gt;&gt;&nbsp;所有#{channel.itemName }</td>
  </tr>
</table>
</pub:template>

<pub:template name="show_article_list">
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
<tr class='title' height='22'>
 <td height='22' width='30' align='center'><strong>选中</strong></td>
 <td width='25' align='center'><strong>ID</strong></td>
 <td width='120' align='center'><strong>所属专题</strong></td>
 <td align='center'><strong>#{channel.itemName }标题</strong></td>
 <td width='60' align='center'><strong>录入者</strong></td>
 <td width='40' align='center'><strong>点击数</strong></td>
 <td width='80' align='center'><strong>#{channel.itemName }属性</strong></td>
 <td width='60' align='center'><strong>审核状态</strong></td>
 <td width='100' align='center'><strong>专题管理操作</strong></td>
</tr>
#{foreach article in article_list }
<tr id="articleHtml" class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td width='30' align='center'>
  <input name="itemId" type='checkbox' value='#{article.refid}'>
 </td>
 <td width='25' align='center'>#{article.refid }
 </td>
 <td width='120' align='center'>
 #{if article.specialId != 0}
  <a href="admin_special_article_list.jsp?channelId=#{channel.id}&amp;specialId=#{article.specialId}">#{article.specialName}</a>
 #{/if}
 </td>
 <td>
 #{if article.columnName != '' }
 <a href="admin_special_article_list.jsp?channelId=#{channel.id }&amp;columnId=#{article.columnId }">
  [#{article.columnName}]</a>
 #{/if }
 <font color="blue">
  #{if (article.includePic == 1) }
  [图文]
  #{elseif (article.includePic == 2) }
  [组图]
  #{elseif (article.includePic == 3) }
  [推荐]
  #{elseif (article.includePic == 4) }
  [注意]
  #{/if }
 </font> 
 <a href="admin_article_add.jsp?channelId=#{channel.id}&amp;articleId=#{article.id }"
      title="标    题：#{article.title@html}
        &#13;作    者：#{article.author@html}
        &#13;更新时间：#{article.lastModified }
        &#13;点 击 数：#{article.hits }
        &#13;关 键 字：#{article.keywords@html}
        &#13;推荐等级：#{'★'@repeat(article.stars) }">#{article.title@html }</a>
 </td>
 <td width='60' align='center'>
  <a href="admin_special_article_list.jsp?channelId=#{channel.id }&inputer=#{article.inputer@url }"
   title="点击将查看此用户录入的所有#{channel.itemName }"> #{article.inputer@html} </a>
 </td>
 <td width='40' align='center'>#{article.hits }
 </td>
 <td width='80' align='center'>
  #{if article.top }<font color="blue">顶</font> #{/if }
  #{if article.commend }<font color="green">荐</font> #{/if }
  #{if article.elite }<font color="green">精</font> #{/if }
  #{if article.hot }<font color="red">热</font> #{/if }
  #{if (article.includePic == 1 || article.includePic == 2) }<font color="green">图</font> #{endif }
 </td>
 <td width='60' align='center'>
  #{if (article.status == 1) }
  <font color="black">终审通过</font>
  #{elseif (article.status == 0) }
  <font color="red">未审批</font>
  #{elseif (article.status == -1) }
  <font color="black">草稿</font>
  #{else }
  未知
  #{/if }
 </td>
 <td width='100' align='center'>
  <a href='javascript:void(0);' onclick="removeSpecials('#{article.refid }');return false;">移除</a></td>
</tr>
#{/foreach }
</table>
</pub:template>


<pub:template name="form_buttons">
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
<tr>
 <td width='200' height='30'>
 <input name='chkAll' type='checkbox' id='chkAll' onclick='CheckAll(this)' value='checkbox'>选中本页显示的所有#{channel.itemName}</td>
 <td><input type='button' onclick="removeSpecials()" value='从当前专题中移除' /> 
  <input type='button' onclick="copySpecialItems(true)" value='添加到其他专题中' /> 
  <input type='button' onclick="copySpecialItems(false)" value='移动到其他专题中' />
 </td>
</tr>
</table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>
