<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.BizManage"
%><%
  // 初始化页面数据.
  BizManage manager = new BizManage(pageContext);
  manager.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>相关业务列表</title>
 <script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:declare>

<%@ include file="admin_biz_elem.jsp" %>

<pub:template name="main">
 #{call admin_biz_help }
 #{call show_biz_list }
</pub:template>


<pub:template name="show_biz_list">
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
<tr class='title'>
 <td width='50' align='center'><strong>选中</strong></td>
 <td align='center' width='80'><strong>名称</strong></td>
 <td align='center' width='200'><strong>简介</strong></td>
 <td width='240' height='22' align='center'><strong>管理人员和群组</strong></td>
 <td width='200' height='22' align='center'><strong>操作</strong></td>
</tr>
#{foreach biz in biz_list}
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td width='50' align='center' height="30">#{biz.id}</td>
 <td align='center' width='80'>#{biz.name@html}</td>
 <td align='center' width='200'>#{biz.description@html}</td>
 <td align='center' width='240'>
  <a href='admin_biz_user.jsp?bizId=#{biz.id }'>所属人员</a>
  <a href='admin_biz_group.jsp?bizId=#{biz.id }'>所属群组</a>
 </td>
 <td width='200' align='center'>
  <a href='admin_biz_add.jsp?bizId=#{biz.id }'>修改</a>
  <a href='admin_biz_action.jsp?command=delete&amp;bizId=#{biz.id }' 
   onclick="return confirm('确定要删除此业务连接吗？');">删除</a>
 </td>
</tr>
#{/foreach}
</table>

</pub:template>

</pub:declare>

<pub:process_template name="main" />
</body>
</html>
