<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<pub:template name="soft_add_success">
<table class='border' align=center width='500' border='0' cellpadding='2' cellspacing='1'>
  <tr align=center>
    <td height='22' align='center' class='title' colspan='2'><b>添加/修改软件成功</b>
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
    <td width='400'>#{if soft.columnId == 0}不属于任何栏目#{else}#{soft.column.name }#{/if }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>软件名称：</strong></td>
    <td width='400'>#{soft.title@html }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>软件版本：</strong></td>
    <td width='400'>#{soft.softVersion }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>软件作者：</strong></td>
    <td width='400'>#{soft.author@html }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>关 键 字：</strong></td>
    <td width='400'>#{soft.keywords@html }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>软件状态：</strong></td>
    <td width='400'>#{soft.status }</td>
  </tr>
  <tr class='tdbg'>
    <td height='40' colspan='2' align='center'>
     【<a href='admin_soft_add.jsp?channelId=#{soft.channelId}&amp;softId=#{soft.id }'>修改此软件</a>】
     【<a href='admin_soft_add.jsp?channelId=#{soft.channelId}&amp;columnId=#{soft.columnId}'>继续添加软件</a>】
     【<a href='admin_soft_list.jsp?channelId=#{soft.channelId}&amp;columnId=#{soft.columnId}'>软件管理</a>】
     【<a href='admin_soft_view.jsp?softId=#{soft.id}'>预览软件内容</a>】
    </td>
  </tr>
</table>
</pub:template>

<jsp:forward page="admin_base_action.jsp">
  <jsp:param name="__action" value="com.chinaedustar.publish.action.SoftAction" />
</jsp:forward>
