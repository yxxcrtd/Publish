<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>会员控制面板</title>
<link href="defaultSkin.css" rel="stylesheet" type="text/css">
<link href="../admin/admin_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="stm31.js"></script>
<script type="text/javascript" src="../admin/main.js"></script>
</head>
<body>
<!-- 当前登录的会员。 -->
<pub:data var="user" param="login"
 provider="com.chinaedustar.publish.admin.UserDataProvider" />
<pub:data var="channel" 
 provider="com.chinaedustar.publish.admin.ChannelDataProvider" />
<!-- 定义下拉栏目列表的数据。 -->
<pub:data var="dropdown_columns" 
 provider="com.chinaedustar.publish.admin.ColumnsDropDownDataProvider"
 param="0" />
<!-- 定义当前栏目下的需要显示的文章的集合 -->
<pub:data var="article_list" param="my"
 provider="com.chinaedustar.publish.admin.ItemListDataProvider" />
<!-- 得到栏目层次结构的ArrayList<Column>数据。 -->
<pub:data var="columnTier" 
 provider="com.chinaedustar.publish.admin.ColumnTierDataProvider" />


<%@ include file="element_user.jsp" %>
<%@ include file="../admin/element_article.jsp" %>
<%@ include file="../admin/element_column.jsp" %>
<pub:template name="main">
 #{call tmpl_top }
 <table width="756" align="center" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
  #{call tmpl_body }
  </td>
  </tr>
 </table>

 <br />
 #{call tmpl_bottom }
</pub:template>

<!-- 主体部分。 -->
<pub:template name="tmpl_body">
<table width="100%" align="center" border="0" cellpadding="5" cellspacing="0" class="user_box">
  <tr>
    <td class="user_righttitle"><img src="images/point2.gif" align="absmiddle">
    您现在的位置：<a href='../'>#{site.name }</a> >> <a href='index.jsp'>会员中心</a> >> 信息管理 >> <a href="user_article_list.jsp?channelId=#{channel.id }">#{channel.name }</a> >> 文章中心</td>
  </tr>
  <tr>
    <td height="200" valign='top'>
    <TABLE align=center>
     <TBODY>
     <TR vAlign=top align=middle>
       <TD width=90>
       #{if (user.inputer) }
       <A 
         href="user_article_add.jsp?channelId=#{channel.id }"><IMG 
         src="images/article_add.gif" 
         align=absMiddle border=0><BR>添加文章</A>
       #{/if }  
       </TD>
       <TD width=90><A 
         href="user_article_list.jsp?channelId=#{channel.id }"><IMG 
         src="images/article_all.gif" 
         align=absMiddle border=0><BR>所有文章</A></TD>
       <TD width=90><A 
         href="user_article_list.jsp?channelId=#{channel.id }&amp;status=-1"><IMG 
         src="images/article_draft.gif" 
         align=absMiddle border=0><BR>草 稿</A></TD>
       <TD width=90><A 
         href="user_article_list.jsp?channelId=#{channel.id }&amp;status=0"><IMG 
         src="images/article_unpassed.gif" 
         align=absMiddle border=0><BR>待审核的文章</A></TD>
       <TD width=90><A 
         href="user_article_list.jsp?channelId=#{channel.id }&amp;status=1"><IMG 
         src="images/article_passed.gif" 
         align=absMiddle border=0><BR>已审核的文章</A></TD>
       <TD width=90><A 
         href="user_article_list.jsp?channelId=#{channel.id }&amp;status=-2"><IMG 
         src="images/article_reject.gif" 
         align=absMiddle border=0><BR>未被采用的文章</A></TD></TR>
      </TBODY>
    </TABLE>
 #{call tmpl_body_content }
 </td>
  </tr>
</table>
</pub:template>

<!-- 主模板 -->
<pub:template name="tmpl_body_content">
<script type="text/javascript">
<!--
// 批量删除文章。
function deleteItems() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert("没有选择任何#{channel.itemName }。");
 } else {
  if (confirm("确定要删除选中的这些#{channel.itemName }吗？")) {
   var url = "user_item_delete.jsp";
   formAction.action = url;
   formAction.itemIds.value=ids;
   formAction.submit();
  }
 }
}
// -->
</script>
#{call column_label_list(dropdown_columns, "user_article_list.jsp") }
<br />

#{call temp_article_list }
#{call pagination_bar(totalNum, totalPage, currentPage) }

<form name="formAction" id="formAction" action="" method="post" style="display:none; ">
 <input type="hidden" name="channelId" value="#{channel.id }" />
 <input type="hidden" name="itemIds" value="" />
 <input type="hidden" name="status" value="" /> 
</form>

#{call article_search_bar }
<br />
#{call aritlce_property_description }

</pub:template>

<pub:template name="temp_article_list">
 <table width="100%" border="0" align="center" cellpadding="0"
  cellspacing="0">
  <tr>
   <td height="22">
   您现在的位置： #{channel.name }管理 &gt;&gt; 
   <a href="user_article_list.jsp?channelId=#{channel.id }"> #{channel.itemName }管理 </a>
   #{call column_tier(columnTier, "admin_article_list.jsp") }
    &gt;&gt; 所有#{channel.itemName }

    </td>
   <td width="200" height="22" align="right">
   <select onchange="location='user_article_list.jsp?channelId=#{channel.id }&amp;columnId='+this.value">
    <option value="#{channel.rootColumnId }">跳转栏目至···</option>
    #{call dropDownColumns(0, dropdown_columns) }
   </select>
   </td>
  </tr>
 </table>
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <form name="myform" method="post" action="" onsubmit="return ConfirmDel();">
   <td>
   <table class="border" border="0" cellspacing="1" width="100%"
    cellpadding="0">
    <tr class="title" height="22">
     <td height="22" width="30" align="center"><strong>选中</strong></td>
     <td width="25" align="center"><strong>ID</strong></td>
     <td align="center"><strong>#{channel.itemName}标题</strong></td>
     <td width="60" align="center"><strong>录入者</strong></td>
     <td width="40" align="center"><strong>点击数</strong></td>
     <td width="80" align="center"><strong>#{channel.itemName}属性</strong></td>
     <td width="60" align="center"><strong>审核状态</strong></td>
     <td width="40" align="center"><strong>已生成</strong></td>
     <td width="150" align="center"><strong>常规管理操作</strong></td>
    </tr>
    #{foreach article in article_list }
    <tr class="tdbg" onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
     <td width="30" align="center">
     #{if (article.status < 1) }
     <input name="itemId" type="checkbox" value="#{article.id }" />
     #{/if }
     </td>
     <td width="25" align="center">#{article.id }</td>
     <td>
     #{if (article.columnId != columnId) }
     <a href="user_article_list.jsp?channelId=#{channel.id }&amp;columnId=#{article.columnId }">
     [#{article.columnName }] </a> 
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
     <a href="user_article_add.jsp?<%=request.getQueryString() %>&amp;articleId=#{article.id }"
      title="标    题：#{article.title }
作    者：#{article.author }
来    源：#{article.source }
更新时间：#{article.lastModified }
点 击 数：#{article.hits }
关 键 字：#{article.keywords }
推荐等级：#{foreach i in range(0, article.stars) }★#{/foreach }
分页方式：不分页**
阅读点数：0"> #{article.title } </a></td>
     <td width="60" align="center">#{article.inputer }</td>
     <td width="40" align="center">#{article.hits }</td>
     <td width="80" align="center">
      #{if article.top }<font color="blue">顶</font> #{/if }
      #{if article.commend }<font color="green">荐</font> #{/if }
      #{if article.elite }<font color="green">精</font> #{/if }
      #{if article.hot }<font color="red">热</font> #/if }
      #{if (article.includePic == 1 || article.includePic == 2) }<font color="green">图</font> #{/if }
     </td>
     <td width="60" align="center">
      #{if (article.status == 1) }
      <font color="black">终审通过</font>
      #{elseif (article.status == 0) }
      <font color="red">未审批</font>
      #{elseif (article.status == -1) }
      <font color="black">草稿</font>
      #{elseif (article.status == -2) }
      <font color="black">退稿</font>
      #{else }
      未知
      #{/if }
     </td>
     <td width="40" align="center">
      #{iif(article.created, "<font color='red'><b>×</b></font>", "<font color='blue'><b>√</b></font>") }
     </td>
     <td width="150" align="left">
     #{if (article.status < 1 && user.inputer) }
     <a href="user_article_add.jsp?channelId=#{channel.id }&columnId=#{article.columnId }&articleId=#{article.id }"> 修改 </a> 
     <a href="user_item_delete.jsp?channelId=#{channel.id }&columnId=#{article.columnId }&itemIds=#{article.id }"
      onclick="return confirm('确定要删除此#{channel.itemName}吗？');"> 删除 </a> 
     #{/if }
     </td>
    </tr>
    #{/foreach }
   </table>
   <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
     <td width="200" height="30"><input name="chkAll"
      type="checkbox" id="chkAll" onclick="CheckAll(this)"
      value="checkbox" /> 选中本页显示的所有#{channel.itemName}</td>
     <td>
     <input type="button" value=" 删除选定的#{channel.itemName } " onClick="deleteItems()" /> 
     </td>
    </tr>
   </table>
   </td>
  </tr>
  </form>
 </table>
</pub:template>


<pub:process_template name="main"/>
</body>
</html>