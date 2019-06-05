<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.UserManage"
%><%
  // 初始化管理数据。
  UserManage manager = new UserManage(pageContext);
  manager.initEditPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>会员管理——添加/修改会员信息</title>
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 异步处理的静态变量。
var READY_STATE_UNINITIALIZED=0;
var READY_STATE_LOADING=1;
var READY_STATE_LOADED=2;
var READY_STATE_INTERACTIVE=3;
var READY_STATE_COMPLETE=4;
//初始化XMLHTTPRequest
function initXMLHTTPRequest(){
 var xRequest=null;
 if(window.XMLHTTPRequest){
  xRequest=new XMLHTTPRequest();
 }else if((typeof ActiveXObject) != "undefined"){
  try {
   xRequest = new ActiveXObject("Msxml2.XMLHTTP");
   } catch (e) {
   try {
    xRequest = new ActiveXObject("Microsoft.XMLHTTP");
   } catch (e2) {
    xRequest = null;
   }
  }
 }
 else{
  xRequest=new XMLHTTPRequest();
 }
 return xRequest;
}
//发送请求，页面地址，参数，方法
function sendRequest(url,params,HttpMethod){
 if(!HttpMethod){
  HttpMethod="POST";
 }
 req=initXMLHTTPRequest();
 if(req){
  req.onreadystatechange=onReadyState;
  req.open(HttpMethod,url + "?tmp=" + Date.parse(new Date()),true);
  req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  req.send(params);
 }
 else{
  alert("不能进行异步通信");
 }
}
//迭代方法，得到状态
function onReadyState(){
 var ready=req.readyState;
 if(ready==READY_STATE_COMPLETE){
  writeData(req.responseText);
 }
}

//输出返回数据
function writeData(data){
 if (data.indexOf("false") > -1) {
  alert("可以添加该新会员。");
 } else {
  alert("已经存在该会员，请选择一个其它的用户名。");
  document.form1.userName.focus();
  document.form1.userName.select();
 }
 
}

// 验证管理原名称是否已经存在。
function checkUserName() {
 var name = document.form1.userName.value;
 if (name.length > 0) {
  sendRequest("admin_user_checkUserName.jsp", "userName=" + name, "POST");
 }
}
// -->
</script>
</head>
<body>

<%@ include file="element_user.jsp" %>

<pub:template name="main">
<script type="text/javascript">
<!--
// 验证表单提交的数据。
function checkForm() {
 if (form1.userName.value.length < 1) {
  alert("用户名不能为空！");
  form1.userName.focus();
  return false;
 }
 if (#{user.id } == 0 && form1.password.value.length < 1) {
  alert("密码不能为空！");
  form1.password.focus();
  return false;
 }
 return true;
}
// -->
</script>
#{call user_manage_navigator }
<br />
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
  <td height="22">
  您现在的位置： <a href="admin_user_list.jsp">会员管理</a> &gt;&gt; 
  #{iif (user.id != 0, "修改", "添加") }会员
   </td>
 </tr>
</table>
#{call tmpl_body }
</pub:template>

<pub:template name="tmpl_body">
<table class="border" border="0" cellspacing="1" width="100%" cellpadding="0">
 <tr class="title" height="22">
 <td align="center"><strong>#{iif(user.id > 0, "修改", "添加") }会员信息</strong>
 </td></tr>
 <tr><td>
  <form name="form1" action="admin_user_action.jsp" method="post" onsubmit="return checkForm();">
  <table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
   <tr class='tdbg'>
   <td width='12%' align='right' class='tdbg'>用户名：</td>
   <td><input type="text" name="userName" value="#{user.userName }" #{iif (user.id > 0, "readonly ", " ") } />
   <input type="button" value="检查用户名" onclick="checkUserName()" #{iif (user.id > 0, "disabled ", "") } />
   </td>
   </tr>
   <tr class='tdbg'>
    <td width='12%' align='right' class='tdbg'>密码：</td>
    <td><input type="password" name="password" value="" />
    <font color="red">如果不修改，请保留为空！</font></td>
   </tr>
   <tr>
   <td width='12%' align='right' class='tdbg'>状态：</td>
   <td class='tdbg'>
   <input type="radio" name="status" value="0" #{iif (user.status != 1, "checked", "") } />启用
   <input type="radio" name="status" value="1" #{iif (user.status == 1, "checked", "") } />禁用
   </td>
   </tr>
   <tr>
   <td width='12%' align='right' class='tdbg'>允许投稿：</td>
   <td class='tdbg'>
   <input type="radio" name="inputer" value="1" #{iif (user.inputer, "checked", "") } />允许
   <input type="radio" name="inputer" value="0" #{iif (!user.inputer, "checked", "") } />不允许
   </td>
   </tr>
   <tr>
   <td height='40' colspan='2' align='center' class='tdbg'>
   <input type="hidden" name="userId" value="#{user.id }" />
   <input type='hidden' name='command' value='save' />
   <input type="submit" value="#{iif (user.id != 0, "修 改", "添 加") }" style='cursor:hand;' />
   <input type="button" value="取 消" onclick="window.location.href='admin_user_list.jsp';"
    style='cursor:hand;'/>
   </td>
   </tr>
  </table>
  </form>
 </td></tr>
</table>

</pub:template>

<pub:process_template name="main" />
</body>
</html>