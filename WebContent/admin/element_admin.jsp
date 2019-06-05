<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!-- 管理员管理的导航栏 -->
<pub:template name="admin_manage_navigator">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='10'>
  <table width='100%'>
   <tr class='topbg'>
    <td align='center'><b>管 理 员 管 理</b></td>
    <td width='60' align='right'><a
     href='help/index.jsp' target='_blank'><img
     src='images/help.gif' border='0'></a></td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='70' height='30'><strong>管理导航：</strong></td>
  <td height='30'>
   <a href='admin_admin_list.jsp'>管理员管理首页</a>&nbsp;|&nbsp;
   <a href='admin_admin_add.jsp'>新增管理员</a>
  </td>
 </tr>
</table>
</pub:template>
