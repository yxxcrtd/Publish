<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.LayoutManage"
%><%
  LayoutManage manager = new LayoutManage(pageContext);
  manager.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <link href='admin_style.css' rel='stylesheet' type='text/css' />
  <title>布局管理</title>
</head>
<body>
<pub:declare>

<%@include file="element_layout.jsp" %>

<pub:template name="main">
 #{call admin_layout_help }
 #{call your_position }<br/>
 #{call show_layout_list }
</pub:template>


<pub:template name="your_position">
<div>
您现在的位置：网站管理 &gt;&gt; 布局管理
</div>
</pub:template>


<pub:template name="show_layout_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title' height='22'>
  <td width='30' align='center'><strong>选择</strong></td>
  <td width='30' align='center'><strong>ID</strong></td>
  <td width='120' align='center'><strong>布局名称</strong></td>
  <td height='22' align='center'><strong>布局描述</strong></td>
  <td width='60' align='center'><strong>系统默认</strong></td>
  <td width='260' align='center'><strong>操作</strong></td>
 </tr>
#{if layout_list@size == 0 }
 <tr class='tdbg'>
  <td colspan="6" align='center' height='50'>还没有任何布局，点击<a href='admin_layout_add.jsp'>添加布局</a></td>
 </tr>
#{/if }
#{foreach layout in layout_list }
 <tr class='tdbg'>
 <td width="30" align="center" height="20">
   <input type="checkbox" value='#{layout.id}' name="layoutId" />
 </td>
 <td width='30' align='center'>#{layout.id}</td>
 <td><a href='admin_layout_add.jsp?layoutId=#{layout.id }'>#{layout.name }</a></td>
 <td>#{layout.description }</td>
 <td>?</td>
 <td>
   <a href='admin_layout_action.jsp?command=delete'>删除</a>
 </td>
 </tr>
#{/foreach }
</table>
</pub:template>

</pub:declare>
<pub:process_template name="main" />

</body>
</html>
