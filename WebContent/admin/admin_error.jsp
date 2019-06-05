<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
  isErrorPage="true"%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>错误报告</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
<style>
.msgBox {
  font-family: 宋体, verdana;
  font-size:12px;
  border:blue;
  padding:1px;
  border:solid 1px red;
}
.msgBox .msgTitle {
  background-color:#0650D2;height:16px;color:#FFFFFF;padding:2px;
}
</style>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<br/><br/>
<table class='msgBox' width="80%" align='center' cellspacing='1' cellpadding='1' >
 <tr>
  <td>
    <div class="msgTitle" style="background-color:red;">
      <font color='white'>发生错误</font>
    </div>
  </td>
 </tr>
 <tr>
  <td>
   <ul style="color:red">
    <li><%=exception.getMessage() %> (<%=exception.getClass().getName() %>)</li>
    <li><a href='javascript:void(0);'
      onclick='__detail_info.style.display=__detail_info.style.display=="none"?"":"none";'>点击这里查看详细信息</a></li>
   </ul>
   <div id="__detail_info" style="display:none" style='padding-left:20px;'>
    <ul>
    <% 
      StackTraceElement[] stack = exception.getStackTrace();
      for (int i = 0; i < stack.length; ++i) {
        out.println("<li>");
        out.println(stack[i]);
        // out.println("");
      }
    %>
    </ul>
   </div>
  </td>
 </tr>
 <tr>
  <td align='center' height='30'>
   <div>
   <a href='javascript:window.history.back();'>[返回]</a>
   </div>
  </td>
 </tr>
</table>
<br/><br/>

</body>
</html>
