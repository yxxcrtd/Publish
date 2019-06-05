<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%-- 专题管理的导航栏
 @param text 描述文字，如：专题管理|新增专题|专题排序
--%>
<pub:template name="special_navigator">
#{param text}
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='10'>
   <table width='100%'>
    <tr class='topbg'>
     <td align='center'>
      <b>#{channel.name}管理----#{(text)}</b>
     </td>
     <td width='60' align='right'>
      <a href='help/index.jsp' target='_blank'><img src='images/help.gif' border='0'/></a>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='70' height='30'><strong>管理导航：</strong></td>
  <td>
   <a href='admin_special_list.jsp?channelId=#{channel.id}'>#{channel.name}专题管理首页</a> |
   <a href='admin_special_add.jsp?channelId=#{channel.id}'>添加专题</a> |
   <a href='admin_special_order.jsp?channelId=#{channel.id }'>专题排序</a> |
   <a href='admin_special_unite.jsp?channelId=#{channel.id }'>合并专题</a>
   <!--  &nbsp; | &nbsp; <a href='admin_special_generate.jsp?channelId=#{channel.id }'> <b>生成HTML管理</b></a>-->
  </td>
 </tr>
</table>
</pub:template>

