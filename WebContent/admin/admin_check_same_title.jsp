<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.*" %>
<%
  // 初始化此页面的数据.
  ArticleManage manage = new ArticleManage(pageContext);
  manage.initCheckSameTitle();
  
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>检测重复</title>
<link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body>

<pub:template name="main">
 <Table width='100%' border='0' align='center' cellpadding='2'
  cellspacing='0' class='border' Height='100%'>
  <tr class='title' height='22'>
   <td align=center>
    <b>检测结果</b>
   </td>
  </tr>
  <tr class='tdbg'>
   <td valign='top' align='left'>#{result }</td>
  </tr>
  <tr class='title' height='22'>
   <td align=center>
    <input type=button name='button1' id='button1' value='关闭窗口'
     onClick='javascript:window.close();'>
   </td>
  </tr>
 </Table>
</pub:template>

<pub:process_template name="main"/>
 
</body>
</html>
