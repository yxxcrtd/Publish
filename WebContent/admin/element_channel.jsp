<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<!--
频道导航
-->
<pub:template name="channel_manage_navigator">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
<tr class='topbg'>
 <td height='22' colspan='10'>
  <table width='100%'>
   <tr class='topbg'>
    <td align='center'>
     <b>频 道 管 理</b>
    </td>
    <td width='60' align='right'>
     <a href='help/index.jsp' target='_blank'><img src='images/help.gif' border='0' /></a>
    </td>
   </tr>
  </table>
 </td>
</tr>
<tr class='tdbg'>
 <td width='70' height='30'>
  <strong>管理导航：</strong>
 </td>
 <td>
  <a href='admin_channel_list.jsp'>频道管理首页</a> |
  <a href='admin_channel_add.jsp'>添加新频道</a> |
  <a href='admin_channel_order.jsp'>频道排序</a> |
  <a href='admin_channel_recycle.jsp'>频道回收站</a>
 </td>
</tr>
</table>
<br />
</pub:template>

<!-- 
当前管理位置
@param 静态文字
@param 动态文字
-->
<pub:template name="channel_manage_position">
#{param static_text, dynamic_text }
<table width='100%'>
 <tr>
  <td align='left'>
  您现在的位置：<a href='admin_channel_list.jsp'>频道管理</a>&nbsp;&gt;&gt;&nbsp;
  #{static_text }
  #{if dynamic_text != null}：<font color='red'>#{(dynamic_text)}</font>#{/if}
  </td>
 </tr>
</table>
</pub:template>
