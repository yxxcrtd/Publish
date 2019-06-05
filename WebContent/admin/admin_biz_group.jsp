<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.BizManage"
%><%
  // 初始化页面数据.
  BizManage manager = new BizManage(pageContext);
  manager.initGroupList();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>相关业务群组列表</title>
 <script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:declare>

<%@ include file="admin_biz_elem.jsp" %>

<pub:template name="main">
 #{call admin_biz_help }
 #{call show_group_list }
 #{call hidden_form }
 #{call action_buttons }
</pub:template>


<pub:template name="show_group_list">
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
<tr class='title'>
 <td width='50' align='center'><strong>选中</strong></td>
 <td align='center' width='120'><strong>群组名</strong></td>
 <td align='center' width='240'><strong>简介</strong></td>
 <td width='200' height='22' align='center'><strong>操作</strong></td>
</tr>
#{foreach group in group_list}
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td width='50' align='center' height="30">
   <input type="checkbox" value='#{group.id }' name="itemId" />#{group.id}
 </td>
 <td align='center' width='120'>#{group.name@html }</td>
 <td align='center' width='240'>#{group.description@html}</td>
 <td width='200' align='center'>
  <a href='admin_biz_action.jsp?command=unlink_group&bizId=#{biz_name.id }&groupId=#{group.id }'>移除</a>
 </td>
</tr>
#{/foreach}
</table>

</pub:template>


<pub:template name="action_buttons">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
 <td width="200" height="30">
  <input name="chkAll" type="checkbox" id="chkAll" onclick="CheckAll(this)"
  value="checkbox" /> 选中所有</td>
  <td>
  <input type="button" value=" 批量移除 " onclick="batchUnlink()" /> 
  <input type="button" value=" 添加群组 " onclick="addLinkGroup()" /> 
  </td>
 </tr>
</table>
</pub:template>


<pub:template name="hidden_form">
<form name='form_action' method='post' action='admin_biz_action.jsp' style='display:none'>
  <input type='hidden' name='command' value='' />
  <input type='hidden' name='bizId' value='#{biz_name.id }' />
  <input type='hidden' name='groupId' value='' />
</form>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

<script language="javascript">
// 批量移除业务连接中的群组.
function batchUnlink() {
  var ids = getSelectItemIds();
  if (ids == null || ids == '') {
    alert('没有选择任何要移除的群组');
    return;
  }
  if (confirm('您是否确定要移除所选择的群组吗？') == false) return;
  form_action.command.value = 'unlink_group';
  form_action.groupId.value = getSelectItemIds();
  form_action.submit();
}

// 显示一个对话框让用户选择需要连接的群组.
function addLinkGroup() {
  var ret_val = window.showModalDialog('admin_biz_selgroupo.html', '', 'dialogWidth:600px;dialogHeight:500px');
  if (ret_val == null || ret_val == 'undefined') return;

  form_action.command.value = 'link_group';
  form_action.groupId.value = ret_val;
  form_action.submit();
}
</script>
</body>
</html>