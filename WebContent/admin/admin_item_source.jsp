<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.SourceManage"
%><%
  // init page
  SourceManage manage = new SourceManage(pageContext);
  manage.initChoosePage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<pub:declare>
<%@include file="element_keyword.jsp" %>

<pub:template name="main">
<html>
<head>
<title>选择#{channel.itemName }的来源对话框</title>
<meta http-equiv='Content-Type' content='text/html; charset=gb2312' />
<link href='admin_style.css' rel='stylesheet' type='text/css'>
<base target='_self'>
<script	language="javascript" src="main.js"></script>
<script	language="javascript">
<!--
function add(obj)
{
    if(obj==""){return false;}
    opener.myform.source.value=obj;
    window.close();
}
// -->
</script>
</head>
<body>

#{call source_types }
<br>
<table width='560' border='0' align='center' cellpadding='2'
	cellspacing='0' class='border'>
	<tr height='22' class='title'>
		<td><b><font color=red>来源</font>列表：</b></td>
		<td align=right>
    <form method='get' name='myform' action='' style='margin:0px;'>
		<input name="field" type="hidden" value="name" />
		<input name="channelId" type="hidden" value="#{channel.id }" />
		<input name='keyword' type='text' size='20' value="" />&nbsp;&nbsp;
		<input type='submit' value='查找'>
    </form>
		</td>
	</tr>
	<tr>
		<td valign='top' height='100' colspan='2'>
		<table width='550' border='0' cellspacing='1' cellpadding='1'
			bgcolor='#f9f9f9'>
			<tr align='center'>
				<td width='100'>名称</td>
				<td width='100'>联系人</td>
				<td>简介</td>
			</tr>
			#{foreach source in sources }
			<tr>
				<td align='center'><a href='#' onclick='add("#{source.name@html }")'>#{source.name@html }</a></td>
				<td>#{source.contacterName@html }</td>
				<td>#{source.description@html }</td>
			</tr>
			#{/foreach }
		</table>
		</td>
	</tr>
</table>
#{call keyword_pagination_bar(totalNum, totalPage, currentPage, maxPerPage, "个来源") }

</body>
</html>
</pub:template>

<!-- 来源的类型。 -->
<pub:template name="source_types">
<table width='560' border='0' align='center' cellpadding='2'
	cellspacing='0' class='border'>
	<tr height='22' class='title'>
		<td>
		| <a href='admin_item_source.jsp?channelId=#{channel.id }&sourceType=-1'>
		<FONT style='font-size:12px' #{iif (sourceType == -1, "color='red'", "") }>最近常用</FONT></a> 
		| <a href='admin_item_source.jsp?channelId=#{channel.id }&sourceType=0'>
		<FONT style='font-size:12px' #{iif (sourceType == 0, "color='red'", "") }>全部来源</FONT></a> 
		| <a href='admin_item_source.jsp?channelId=#{channel.id }&sourceType=1'>
		<FONT style='font-size:12px' #{iif (sourceType == 1, "color='red'", "") }>友情站点</FONT></a> 
		| <a href='admin_item_source.jsp?channelId=#{channel.id }&sourceType=2'>
		<FONT style='font-size:12px' #{iif (sourceType == 2, "color='red'", "") }>中文站点</FONT></a> 
		| <a href='admin_item_source.jsp?channelId=#{channel.id }&sourceType=3'>
		<FONT style='font-size:12px' #{iif (sourceType == 3, "color='red'", "") }>外文站点</FONT></a> 
		| <a href='admin_item_source.jsp?channelId=#{channel.id }&sourceType=4'>
		<FONT style='font-size:12px' #{iif (sourceType == 4, "color='red'", "") }>其他站点</FONT></a> |
		</td>
	</tr>
</table>
</pub:template>
</pub:declare>

<pub:process_template name="main"/>

