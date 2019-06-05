<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>注册新会员</title>
 <link href="../admin/admin_style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="../admin/main.js"></script>
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
  sendRequest("../admin/admin_user_checkUserName.jsp", "userName=" + name, "POST");
 }
}

// 验证表单提交的数据。
function checkForm() {
 if (form1.userName.value.length < 1) {
  alert("用户名不能为空！");
  form1.userName.focus();
  return false;
 }
 if (form1.password.value != form1.password2.value) {
   form1.password.value = '';
   form1.password2.value = '';
   form1.password.focus();
   alert('两次输入的密码不一致，请重新输入。');
   return false;
 }
 return true;
}

// -->
</script>
</head>
<body style="padding-top: 50px;">

<pub:template name="main">
<table class="border" border="0" align="center" cellspacing="1" width="500"
    cellpadding="0">
 <tr class="title" height="22">
  <td align="center"><strong>注册会员</strong></td>
 </tr>
 <tr>
  <td>
<form name="form1" action="../admin/admin_user_action.jsp"　method="post" onsubmit="return checkForm();">
  <table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
   <tr class='tdbg'>
   <td width='16%' align='right' class='tdbg'>用户名：</td>
   <td><input type="text" name="userName" value="" />
   <input type="button" value="检查用户名" onclick="checkUserName()" />
   </td>
   </tr>
   <tr class='tdbg'>
    <td width='16%' align='right' class='tdbg'>密码：</td>
    <td><input type="password" name="password" value="" /></td>
   </tr>
   <tr class='tdbg'>
    <td width='16%' align='right' class='tdbg'>确认密码：</td>
    <td><input type="password" name="password2" value="" /> 请再次输入密码。</td>
   </tr>
   <tr>
   <td height='40' colspan='2' align='center' class='tdbg'>
   <input type="hidden" name="status" value="0" />
   <input type='hidden' name='command' value='register' />
   <input type="submit" value="  注  册  " style='cursor:hand;' />
   <input type="hidden" name="backUrl" value="../user/user_login.jsp" />
   <input type="button" value="取 消" onClick="window.location.href='../';"
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
