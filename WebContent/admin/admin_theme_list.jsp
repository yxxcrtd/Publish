<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.ThemeManage"
%><%
  // 模板管理数据提供。
  ThemeManage admin_data = new ThemeManage(pageContext);
  admin_data.initThemeListPage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>模板方案管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<pub:declare>

<%@include file="element_theme.jsp"%>

<%-- 主显示模板 --%>
<pub:template name="main">
 #{call admin_theme_help}
 #{call tmpl_theme_list}
</pub:template>


<%-- 定义模板方案显示列表 --%>
<pub:template name="tmpl_theme_list">
<form name='myform' method='Post' action='admin_theme_action.jsp' onsubmit='return ConfirmDel();'>
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
<tr class='title'>
 <td width='50' align='center'><strong>选中</strong></td>
 <td align='center' width='80'><strong>方案名称</strong></td>
 <td align='center' width='200'><strong>方案简介</strong></td>
 <td width='60' align='center'><strong>是否默认</strong></td>
 <td width='240' height='22' align='center'><strong> 方案下的模板风格管理</strong></td>
 <td width='200' height='22' align='center'><strong> 方案操作</strong></td>
</tr>
#{foreach theme in theme_list}
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td width='50' align='center' height="30">#{theme.id}</td>
 <td align='center' width='80'>#{theme.name@html}</td>
 <td align='center' width='200'>#{theme.description@html}</td>
 <td width='60' align='center'>#{iif(theme.isDefault, "√", "")}</td>
 <td align='center' width='240'>
  <a href='admin_template_list.jsp?themeId=#{theme.id}&showGroup=true'>管理所属模板</a>
  <a href='admin_skin_list.jsp?themeId=#{theme.id}'>管理风格</a>
 </td>
 <td width='200' align='center'>
  <a href='admin_theme_add.jsp?themeId=#{theme.id}'>修改方案</a>
  #{if theme.isDefault == false}
  <a href='admin_theme_action.jsp?command=delete&amp;themeId=#{theme.id }' 
   onclick="return confirm('确定要删除此方案吗？删除此方案后方案隶属的模板,风格都将会被删除,请严格注意!');">删除方案</a>
  #{else }
    <font color='gray'>删除方案</font>
  #{/if }
  #{if (theme.isDefault == false) }
  <a href='admin_theme_action.jsp?command=default&amp;themeId=#{theme.id }'  
   onclick="return confirm('您确定该方案的模板和风格都有默认数据了么,如果没有请先添加或方案迁移!');">设为默认</a>
  #{else }
    <font color='gray'>设为默认</font>
  #{/if }
 </td>
</tr>
#{/foreach}
</table>
</form>
</pub:template>


</pub:declare>

<%-- 执行主模板 --%>
<pub:process_template name="main" />

</body>
</html>
