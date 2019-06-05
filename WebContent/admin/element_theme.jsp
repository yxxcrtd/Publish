<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<!-- admin_theme_xxx 页面使用的公共元素：管理页面头 -->
<pub:template name="admin_theme_help">
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="border">
  <tr class='topbg'> 
    <td height='22' colspan='10'>
      <table width='100%'>
        <tr class='topbg'>
          <td align='center'><b>网站模板方案管理</b></td>
          <td width='60' align='right'>
            <a href='help/index.jsp' target='_blank'>
              <img src='images/help.gif' border='0'>
            </a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="tdbg"> 
    <td width="70" height="30"><strong>管理导航：</strong></td>
    <td height="30">
      <a href="admin_theme_list.jsp">管理首页</a> | 
      <a href="admin_theme_add.jsp">添加新模板方案项目</a> | 
   <!--     <a href="admin_theme_import.jsp">导入模板方案</a> | 
      <a href="admin_theme_export.jsp">导出模板方案</a> | -->
      <a href="admin_theme_move.jsp">方案间模板迁移</a> | 
      <a href="admin_theme_move.jsp">方案间风格迁移</a> |
    </td>
  </tr>
 </table><br>
</pub:template>
