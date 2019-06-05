<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>

<pub:template name="user_manage_navigator">
<form name='searchmyform' action='admin_user_list.jsp' method='get'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
   <td height='22' colspan='10'>
   <table width='100%'>
    <tr class='topbg'>
     <td align='center'><b>会 员 管 理</b></td>
     <td width='60' align='right'><a
      href='help/index.jsp' target='_blank'><img
      src='images/help.gif' border='0'></a></td>
    </tr>
   </table>
   </td>
  </tr>
  <tr class='tdbg'>
   <td width='100' height='30'><!-- 快速查找会员： --></td>
   <td width='687' height='30'>
   <!-- 
   <select size=1 name='searchType'
    onChange="javascript:submit()">
    <option value='0'>列出所有会员</option>
    <option value='1'>文章最多的TOP100会员</option>
    <option value='2'>文章最少的100个会员</option>
    <option value='3'>最近24小时内登录的会员</option>
    <option value='4'>最近24小时内注册的会员</option>
    <option value='5'>所有禁用的会员</option>
    <option value='6'>所有启用的会员</option>
   </select> &nbsp;&nbsp;&nbsp;&nbsp; -->
   <a href='admin_user_list.jsp'>会员管理首页</a>&nbsp;|&nbsp;
   <a href='admin_user_add.jsp'>添加新会员</a>
   </td>
  </tr>
</table>
</form>
</pub:template>
