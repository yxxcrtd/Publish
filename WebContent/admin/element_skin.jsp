<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%-- 
风格管理的导航部分，
@param channel 对象。 
--%>
<pub:template name="skin_navigator">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='2' align='center'><strong>#{theme.name } ---- 风格管理</strong></td>
  </tr>
  <tr class='tdbg'>
    <td width='70' height='30'><strong>管理导航：</strong></td>
    <td>
      <a href='admin_skin_list.jsp?themeId=#{themeId }'>风格管理首页</a> |
      <a href='admin_skin_add.jsp?themeId=#{theme.id}'>添加风格</a> |
      <a href='admin_skin_import.jsp'>风格导出</a> |
      <a href='admin_skin_import.jsp'>风格导入</a>
    </td>
  </tr>
</table>
</pub:template>