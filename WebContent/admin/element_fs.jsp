<%@ page language="java" contentType="text/html; charset=gb2312"  pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%-- 友情链接等页面使用的公共模板块 --%>
<pub:template name="fs_admin_help">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'> 
  <td height='22' colspan='10'>
    <table width='100%'>
      <tr class='topbg'>
        <td align='center'><b>友 情 链 接 管 理</b></td>
        <td width='60' align='right'><a
          href='help/index.jsp'
          target='_blank'><img src='images/help.gif' border='0'></a></td>
      </tr>
    </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='70' height='30'><strong>管理导航：</strong></td>
  <td>
   <a href='admin_friendsite_list.jsp'>友情链接管理首页</a> |
   <a href='admin_friendsite_add.jsp'>添加友情链接</a> |
   <a href='admin_fs_kind.jsp'>链接类别管理</a> |
   <a href='admin_fs_kind_add.jsp'>添加链接类别</a> |
   <a href='admin_fs_special.jsp'>链接专题管理</a> |
   <a href='admin_fs_special_add.jsp'>添加链接专题</a> |
   <a href='admin_friendsite_sort.jsp'>友情链接排序</a>
  </td>
 </tr>
</table>
</pub:template>
