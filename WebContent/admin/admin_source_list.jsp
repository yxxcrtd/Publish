<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SourceManage"
%><%
  SourceManage admin_data = new SourceManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>来源管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
function deleteSources() {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选择任何来源。");
		return;
	}
	if (confirm('确定要删除选中的来源吗？')) {
		document.actionForm.sourceIds.value = ids;
		document.actionForm.submit();
	}
}
// -->
</script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
<pub:declare>

<%@include file="element_keyword.jsp" %>

<pub:template name="main">
#{call source_navigator("来源管理") }<br />
#{call templ_channels("admin_source_list.jsp", "来源") }<br />
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
	<td height='22'>
	| <a href='admin_source_list.jsp?channelId=#{channel.id }&sourceType=1'>友情站点</a>
	| <a href='admin_source_list.jsp?channelId=#{channel.id }&sourceType=2'>中文站点</a>
	| <a href='admin_source_list.jsp?channelId=#{channel.id }&sourceType=3'>外文站点</a>
	| <a href='admin_source_list.jsp?channelId=#{channel.id }&sourceType=4'>其他站点</a>
	|</td>
 </tr>
</table><br />

#{call templ_sources }

<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td width='200' height='30'>
  <input type='checkbox' onclick='CheckAll(this)' value='checkbox' />
  选中本页显示的所有作者</td>
  <td>
  <td>
  <input type='button' value='删除选中的来源' onclick="deleteSources()" />
  </td>
 </tr>
</table>
<form name='actionForm' method='Post' action='admin_source_action.jsp'>
 <input name='command' type='hidden' value='delete' />
 <input name='channelId' type='hidden' id='channelId' value="#{channel.id }" />
 <input name="sourceIds" type="hidden" />
</form>
#{call keyword_pagination_bar(page_info) }<br />
#{call source_searchBar }
</pub:template>

<!-- 来源列表的模板。 -->
<pub:template name="templ_sources">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr align='center' class='title'>
  <td width='30'><strong>选中</strong></td>
  <td width='40' height='22'><strong>序号</strong></td>
  <td width='150' height='22'><strong>名称</strong></td>
  <td width='150' height='22'><strong>地址</strong></td>
  <td height='22'><strong>简介</strong></td>
  <td width='80' height='22'><strong>来源分类</strong></td>
  <td width='60' height='22'><strong>状态</strong></td>
  <td width='150' height='22'><strong>操 作</strong></td>
 </tr>
#{foreach source in source_list}
 <tr align='center' class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td>
  <input name='itemId' type='checkbox' id='sourceId' value='#{source.id}'/></td>
  <td>#{source.id }</td>
  <td>#{source.name@html}</td>
  <td>#{source.address@html}</td>
  <td>&nbsp;#{source.description@html}&nbsp;</td>
  <td>
  #{if (source.sourceType == 1) }
   友情站点
  #{elseif (source.sourceType == 2) }
   中文站点
  #{elseif (source.sourceType == 3) }
   外文站点
  #{elseif (source.sourceType == 4) }
   其他站点
  #{else }
   未知类型来源
  #{/if }
  </td>
  <td>
 #{if (source.passed) }
  <font color="green">√</font>
 #{else }
  <font color="red">×</font>
 #{/if }&nbsp;
  <font color="blue">#{iif (source.top, "固", "　") }</font>&nbsp;
  <font color="green">#{iif (source.commend, "荐", "　") }</font>
  </td>
  <td>
   <a href='admin_source_add.jsp?channelId=#{source.channelId }&sourceId=#{source.id }'>修改</a>&nbsp;
  #{if (source.passed) }
   <a href='admin_source_action.jsp?command=status&channelId=#{channel.id }&sourceId=#{source.id }&field=passed&status=false'>禁用</a>&nbsp;
  #{else }
   <a href='admin_source_action.jsp?command=status&channelId=#{channel.id }&sourceId=#{source.id }&field=passed&status=true'>启用</a>&nbsp;
  #{/if }
  #{if source.top }
   <a href='admin_source_action.jsp?command=status&channelId=#{channel.id }&sourceId=#{source.id }&field=top&status=false'>解固</a>&nbsp;
  #{else }
   <a href='admin_source_action.jsp?command=status&channelId=#{channel.id }&sourceId=#{source.id }&field=top&status=true'>固顶</a>&nbsp;
  #{/if }
  #{if source.commend }
   <a href='admin_source_action.jsp?command=status&channelId=#{channel.id }&sourceId=#{source.id }&field=commend&status=false'>解荐</a>&nbsp;
  #{else }
   <a href='admin_source_action.jsp?command=status&channelId=#{channel.id }&sourceId=#{source.id }&field=commend&status=true'>推荐</a>&nbsp;
  #{/if }
   <a href='admin_source_action.jsp?command=delete&channelId=1&sourceIds=#{source.id }' 
     onclick="return confirm('确定要删除此来源吗？');">删除</a>
   </td>
 </tr>
#{/foreach }
</table>
</pub:template>

<!-- 来源的搜索栏 -->
<pub:template name="source_searchBar">
<table width='100%' border='0' cellpadding='0' cellspacing='0' class='border'>
 <tr class='tdbg'>
  <td width='80' align='right'><strong>来源搜索：</strong></td>
  <td>
  <table border='0' cellpadding='0' cellspacing='0'>
   <tr>
    <td height='28' align='center'>
   <form method='get' name='SearchForm' action='' style='margin:0px;'>
    <input name='channelId' type='hidden' id='channelId' value='#{channel.id }' />
    <select name='field' size='1'>
     <option value='name' selected>来源名称</option>
     <option value='address'>来源地址</option>
     <option value='tel'>来源电话</option>
     <option value='description'>来源简介</option>
     <option value='contacterName'>联系人</option>
    </select>
    <input type='text' name='keyword' size='20' value='关键字'
     onmouseover="this.focus();this.select();" maxlength='50'>
    <input type='submit' name='Submit' value='搜索'>
   </form>
    </td>
   </tr>
  </table>
  </td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />
</body>
</html>
