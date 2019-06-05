<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@ taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.AdminManage"
%><%
  // 初始化数据.
  AdminManage admin_manage = new AdminManage(pageContext);
  admin_manage.initColumnRightPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head> 
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
 <title>栏目权限设置</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css'>
 <script type="text/javascript" src="main.js"></script>
</head>
<body>

<pub:declare>

<pub:template name="main">
<form name='rightForm'>
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr align='center' class='title' height='22'>
    <td width='*'><strong>栏目名称</strong></td>
    <td width='30'><strong>查看</strong></td>
    <td width='30'><strong>录入</strong></td>
    <td width='30'><strong>审核</strong></td>
    <td width='30' height='22'><strong>管理</strong></td>
  </tr>
 #{foreach column in column_right.columnList }
  <tr class='tdbg'>
    <td width='*' align='left'>
      #{column.prefix }#{column.name }
    </td>
    <td align='center'>
      <input name='right_view' type='checkbox' value='#{column.id }' 
        #{if column.view }checked #{/if }/>
    </td>
    <td align='center'>
      <input name='right_inputer' type='checkbox' value='#{column.id }' 
        #{if column.inputer }checked #{/if }/>
    </td>
    <td align='center'>
      <input name='right_editor' type='checkbox' value='#{column.id }' 
        #{if column.editor }checked #{/if }/>
    </td>
    <td align='center'>
      <input name='right_manage' type='checkbox' value='#{column.id }' 
        #{if column.manage }checked #{/if }/>
    </td>
  </tr>
 #{/foreach }
</table>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

<script language='javascript'>
/* 收集栏目权限, 组装为某种格式. */
function collectColumnRight() {
  // the input_elem.value is columnId, x result will be:
  //   (view)1,3,5,7,###(inputer)8,9,10,###(editor)11,12###(manage)999,1001
  // or ###,###,###
  var x = '';
  var i;
  for (i = 0; i < document.rightForm.right_view.length; ++i) {
    var input_elem = document.rightForm.right_view[i];
    if (input_elem.checked)
      x += input_elem.value + ',';
  }
  
  x += '###';
  for (i = 0; i < document.rightForm.right_inputer.length; ++i) {
    var input_elem = document.rightForm.right_inputer[i];
    if (input_elem.checked)
      x += input_elem.value + ',';
  }
  
  x += '###';
  for (i = 0; i < document.rightForm.right_editor.length; ++i) {
    var input_elem = document.rightForm.right_editor[i];
    if (input_elem.checked)
      x += input_elem.value + ',';
  }
  
  x += '###';
  for (i = 0; i < document.rightForm.right_manage.length; ++i) {
    var input_elem = document.rightForm.right_manage[i];
    if (input_elem.checked)
      x += input_elem.value + ',';
  }
  
  return x;
}
</script>

</body>
</html>
