<%@ page language="java" contentType="text/html; charset=gb2312"
 pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@page import="com.chinaedustar.publish.admin.ArticleManage" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%
  // 初始化页面数据。
  ArticleManage admin_data = new ArticleManage(pageContext);
  admin_data.initViewPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>文章管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
 <script src="../js/prototype.js"></script>
 <script src='admin_article.js'></script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="item_tabs">
 <pub:tab name="baseInfo" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="specialSet" text="所属专题" template="temp_specialSet" default="false" />
</pub:tabs>

<%@ include file="element_article.jsp" %>
<%@ include file="element_column.jsp" %>
<%@ include file="tabs_tmpl2.jsp" %>


<pub:template name="main">
 #{call article_manage_navigator("添加" + channel.itemName) } <br />
 #{call your_position }<br />
 <div>
  #{call tab_js }
  #{call tab_header(item_tabs) }
  #{call tab_content(item_tabs) }
 </div>
 #{call operate_form}<br />

 <% if (false) { %>
  #{call debug_info}
 <% } %>
</pub:template>


<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>
  您现在的位置： <a href='admin_article_list.jsp?channelId=#{channel.id}'>#{channel.name}管理</a>
    &gt;&gt; 查看文章内容：#{article.title@html}
  </td>
 </tr>
</table>
</pub:template>



<!-- 文章基本信息 -->
<pub:template name="temp_baseInfo">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr align='center' class='tdbg'>
  <td height='40'>
   <font size='4'><b>#{article.title@html}</b></font>
  </td>
 </tr>
 <tr align='center' class='tdbg'>
  <td>作者：#{article.author@html}&nbsp;&nbsp;&nbsp;&nbsp;
   文章来源：#{article.source@html}&nbsp;&nbsp;&nbsp;&nbsp;
   点击数：#{article.hits}&nbsp;&nbsp;&nbsp;&nbsp;
   更新时间：#{article.lastModified}&nbsp;&nbsp;&nbsp;&nbsp;
   文章属性：#{if article.top}<font color='blue'>顶</font>&nbsp;#{/if }
    #{if article.commend}<font color='green'>荐</font>&nbsp;#{/if}
    #{if article.hot}<font color='red'>热</font>&nbsp;#{/if}
    #{if article.elite}<font color='red'>精</font>&nbsp;#{/if}
    <font color='#009900'>#{foreach star in range(0, article.stars)}★#{/foreach}</font>
  </td>
 </tr>
 <tr align='center' class='tdbg'>
  <td>
   <table width='90%' border='0' cellpadding='2' cellspacing='1'>
    <tr>
     <td>
      #{article.content}
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr align='right' class='tdbg'>
  <td>
   文章录入：#{article.inputer@html}&nbsp;&nbsp;&nbsp;&nbsp;
   责任编辑：#{article.editor}
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
  <option value='#{special.id}' #{iif (article.specialIds@contains(special.id), "selected", "")}>#{special.name}(#{iif (special.channelId==0, "全站", "本频道") })</option>
 #{/foreach }
 </select> <br />
 <input type='button' name='selectAll' value='  选定所有专题  ' onclick='selectAll()' disabled='disabled' /> <br />
 <input type='button' name='unSelectAll' value='取消选定所有专题' onclick='unSelectAll()' disabled='disabled' />
  </td>
 </tr>
</table>
</pub:template>


<pub:template name="operate_form">
<form name='formAction' method='get' action='admin_article_action.jsp'>
<div align='center'>
 <input type='hidden' name='channelId' value='#{channel.id}' />
 <input type='hidden' name='articleId' value='#{article.id}' />
 <input type='hidden' name='itemId' value='#{article.id}' />
 <input type='hidden' name='command' value='' />
#{if article.deleted == false }
 <input type='button' name='submit' value='修改/审核' #{if can_modify == false }disabled #{/if }
  onclick="window.location='admin_article_add.jsp?channelId=#{channel.id}&articleId=#{article.id}'" />&nbsp;&nbsp;
 <input type='submit' name='submit' value=' 删 除 ' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='delete'" />&nbsp;&nbsp;
 <input type='submit' name='submit' value=' 移 动 ' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='move_column'" />&nbsp;&nbsp;
 <input type='submit' name='submit' value='直接退稿'  #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='reject'" />&nbsp;&nbsp;
 #{if article.status == 1}
 <input type='submit' name='submit' value='取消审核' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='unappr'" />&nbsp;&nbsp;
 #{else}
 <input type='submit' name='submit' value='审核通过' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='appr'" />&nbsp;&nbsp;
 #{/if }
 #{if article.top }
 <input type='submit' name='submit' value='取消固顶' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='untop'" />&nbsp;&nbsp;
 #{else }
 <input type='submit' name='submit' value='固顶' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='top'" />&nbsp;&nbsp;
 #{/if }
 #{if article.commend }
 <input type='submit' name='submit' value='取消推荐' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='uncommend'" />
 #{else }
 <input type='submit' name='submit' value='设为推荐' #{if can_editor == false }disabled #{/if }
  onclick="document.formAction.command.value='commend'" />
 #{/if }
#{else}
 <input type='submit' name='submit' value='从回收站恢复' #{if can_manage == false }disabled #{/if }
  onclick="document.formAction.command.value='recover'" />
 <input type='submit' name='submit' value='从回收站彻底删除' #{if can_manage == false }disabled #{/if }
  onclick="document.formAction.command.value='destroy'" />
#{/if}
</div>
</form>

</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
