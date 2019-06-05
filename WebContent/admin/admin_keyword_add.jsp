<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.KeywordManage" 
%><%
  // 初始化页面所需数据。
  KeywordManage admin_data = new KeywordManage(pageContext);
  admin_data.initEditPageData();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>增加/修改关键字</title>
<link href='admin_style.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 验证表单提交。function checkForm() {
	if (isEmpty(document.myform.name.value)) {
		alert("关键字不能为空。");
		document.myform.name.focus();
		document.myform.name.select();
		return false;
	} else {
		return true;
	}
}
// -->
</script>
</head>
<body>

<%@include file="element_keyword.jsp" %>
<pub:template name="main">
#{call keyword_navigator(iif(keyword.id == 0, "新增关键字", "修改关键字")) }

<form method='post' action='admin_keyword_action.jsp?command=save' name='myform' onsubmit="return checkForm();">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title'>
   <td height='22' colspan='2'>
   <div align='center'><strong>
   #{if (keyword.id == 0) }新 增 关 键 字#{else }修 改 关 键 字#{/if }
   </strong></div>
   </td>
  </tr>
  <tr class='tdbg'>
   <td width='100%' align='center' class='tdbg'><strong> 关 键 字：</strong>
   <input name='name' type='text' value="#{keyword.name@html}" /> 
   <font color='#FF0000'>*</font></td>
  </tr>
  <tr class='tdbg'>
   <td width='100%' align='center' class='tdbg'><strong>
   所属频道：</strong>
   <select name='channelId'>
    <option value='0'>全部频道</option>
    #{foreach channel0 in channels }
    <option value='#{channel0.id }' #{iif (channel0.id==keyword.channelId, "selected", "") }>#{channel0.name }</option>
    #{/foreach }
   </select></td>
  </tr>
  <tr>
   <td height='40' colspan='2' align='center' class='tdbg'>
   <input name='id' type='hidden' value="#{keyword.id}" />
   <input name='hits' type='hidden' value="#{keyword.hits}" />
   <input name='lastUseTime' type='hidden' value="#{keyword.lastUseTime}" />
   <input type='submit' name='Submit' value=' #{iif (keyword.id == 0, "添 加", "保 存") } ' style='cursor:hand;' />&nbsp;
   <input type='button' name='Cancel' id='Cancel' value=' 取 消 '
    onclick="window.location.href='admin_keyword_list.jsp?channelId=#{channel.id}';"
    style='cursor:hand;'></td>
  </tr>
 </table>
 </form>
</pub:template>

<pub:process_template name="main" />
</body>
</html>
