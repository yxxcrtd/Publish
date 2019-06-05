<%@ page language="java" contentType="text/html; charset=gb2312"
 pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.SpecialManage"
%><%
  // 初始化页面数据。
  SpecialManage admin_data = new SpecialManage(pageContext);
  admin_data.initUnitePage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>文章专题管理</title>
<meta http-equiv='Content-Type' content='text/html; charset=gb2312' />
<link href='admin_style.css' rel='stylesheet' type='text/css'>
<script language='JavaScript' type='text/JavaScript'>
<!--
// 保存合并的结果.function saveUnite(){
 if (document.myform.specialId.value==document.myform.targetSpecialId.value){
  alert('请不要在相同专题内进行操作！');
  document.myform.targetSpecialId.focus();
  return false;
 } else {
  return true;
 }
}
// -->
</script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
<pub:declare>

<%@include file="element_special.jsp" %>
<pub:template name="main">
#{call special_navigator("合并专题") }<br />
<table width='100%' border='0' align='center' cellpadding='0' cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22' colspan='3' align='center'><strong>合并#{channel.itemName }专题</strong></td>
 </tr>
 <tr class='tdbg'>
  <td height='100'>
  <form name='myform' method='post' action='admin_special_action.jsp'
   onSubmit='return saveUnite();'>
  &nbsp;&nbsp;将专题 <select name='specialId' id='specialId'>
   #{foreach special in special_list }
   <option value='#{special.id }'>#{special.name }</option>
   #{/foreach }
  </select>
  合并到:
  <select name='targetSpecialId' id='targetSpecialId'>
   #{foreach special in special_list }
   <option value='#{special.id }'>#{special.name }</option>
   #{/foreach }
  </select> <br />
  <br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
  <input type='hidden' name='command' value='unite' />
  <input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
  <input type='submit' name='Submit' value=' 合并专题 ' style='cursor:hand;'>&nbsp;&nbsp;
  <input name='cancel' type='button' id='cancel' value=' 取 消 '
   onClick="window.location.href='admin_special_list.jsp?channelId=#{channel.id }'"
   style='cursor:hand;'>
  </form>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='60'><strong>注意事项：</strong><br>
  &nbsp;&nbsp;&nbsp;&nbsp;所有操作不可逆，请慎重操作！！！<br>
  &nbsp;&nbsp;&nbsp;&nbsp;不能在同一个专题内进行操作。<br>
  &nbsp;&nbsp;&nbsp;&nbsp;合并后您所指定的专题将被删除，所有文章将转移到目标专题中。</td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main"/>
</body>
</html>
