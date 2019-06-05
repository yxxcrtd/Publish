<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%>

<pub:template name="admin_layout_help">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='10'>
   <table width='100%'>
    <tr class='topbg'>
     <td align='center'><b>布局管理</b></td>
     <td width='60' align='right'>
      <a href='help/index.jsp' target='_blank'><img src='images/help.gif' border='0'></a>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='70' height='30'>
   <strong>管理导航：</strong></td>
  <td align="left">
   <a href='admin_layout_list.jsp'>布局管理首页</a> | 
   <a href='admin_layout_add.jsp'>添加布局</a>
   <!-- 导入/导出/刷新?? -->
  </td>
 </tr>
</table>
<br/>
</pub:template>
