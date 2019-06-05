<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="com.chinaedustar.publish.admin.KeywordManage" %>

<%
  // 初始化页面所需数据。
  KeywordManage admin_data = new KeywordManage(pageContext);
  admin_data.initListPageData();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>关键字管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 删除选定的关键字
function deleteKeywords() {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选定任何关键字。");
		return;
	}
	if (confirm("确定要删除选中的关键字吗？")) {
		document.actionForm.keywordIds.value = ids;
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
 #{call keyword_navigator("关键字管理") }<br />
 #{call templ_channels("admin_keyword_list.jsp", "关键字") }<br />
 #{call templ_keyword }
 #{call keyword_operate }
 #{call keyword_pagination_bar(page_info) }<br />
 #{call keyword_search }
</pub:template>

<pub:template name="templ_keyword">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr align='center' class='title'>
  <td width='30'><strong>选中</strong></td>
  <td width='40' height='22'><strong>序号</strong></td>
  <td height='22'><strong>关键字</strong></td>
  <td width='80' height='22'><strong>使用频率</strong></td>
  <td width='150' height='22'><strong>最后使用时间</strong></td>
  <td width='70' height='22'><strong>操 作</strong></td>
 </tr>
#{foreach keyword in keyword_list }
 <tr align='center' class='tdbg' onmouseout="this.className='tdbg'"
  onmouseover="this.className='tdbgmouseover'">
  <td><input name='itemId' type='checkbox' value='#{keyword.id }' /></td>
  <td>#{keyword.id }</td>
  <td>#{keyword.name }</td>
  <td>#{keyword.hits }</td>
  <td>#{keyword.lastUseTime }</td>
  <td><a
   href='admin_keyword_add.jsp?channelId=#{keyword.channelId }&keywordId=#{keyword.id }'>修改</a>&nbsp;&nbsp;
   <a href='admin_keyword_action.jsp?command=delete&channelId=#{keyword.channelId}&keywordIds=#{keyword.id}'
   onClick="return confirm('确定要删除此关键字吗？');">删除</a></td>
 </tr>
#{/foreach }
</table>
</pub:template>


<pub:template name="keyword_operate">
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td width='200' height='30'>
   <input name='chkAll' type='checkbox' id='chkAll' onclick='CheckAll(this)' value='checkbox' />
  选中本页显示的所有关键字</td>
  <td><input name='TypeSelect' type='hidden' id='TypeSelect' value='DelKeyword' />
  <td><input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
  <input type='button' value='删除选中的关键字' onclick="deleteKeywords()" />&nbsp;&nbsp;&nbsp;&nbsp;
  <input type='button' value='删除本频道全部关键字' onclick="if (confirm('确定要删除本频道的全部关键字吗？')) {location = 'admin_keyword_action.jsp?command=clear&channelId=#{channel.id}';}" />
 </tr>
</table>
<form name='actionForm' method='post' action='admin_keyword_action.jsp' style='display:none;'>
 <input type="hidden" name="command" value='delete' />
 <input type="hidden" name="channelId" value='#{channel.id }' />
 <input type="hidden" name="keywordIds" />
</form>
</pub:template>


<pub:template name="keyword_search">
<table width='100%' border='0' cellpadding='0' cellspacing='0' class='border'>
 <tr class='tdbg'>
  <td width='80' align='right'><strong>关键字搜索：</strong></td>
  <td>
  <form method='get' name='SearchForm' action='' style='margin:0px;'>
  <input name='channelId' type='hidden' id='channelId' value='#{channel.id }' />
  <table border='0' cellpadding='0' cellspacing='0'>
   <tr>
    <td height='28' align='center'>
    <select name='field' size='1'>
     <option value='name' selected>关键字名</option>
    </select>
    <input type='text' name='keyword' size='20' value='关键字' maxlength='50' onmouseover="this.focus();this.select();" />
    <input type='submit' value='搜索'>
    </td>
   </tr>
  </table>
  </form>
  </td>
 </tr>
</table>
</pub:template>

<pub:template name="debug_info">
<br/><hr/><h2>DEBUG INFO</h2>
<li>page_info = #{(page_info) }, .currPage = #{page_info.currPage }, .totalCount = #{page_info.totalCount }
<br/><br/><br/><br/>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
