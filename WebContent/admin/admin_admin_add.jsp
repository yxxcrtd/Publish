<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@ taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.AdminManage"
%><%
  // initialize page data
  AdminManage admin_data = new AdminManage(pageContext);
  admin_data.initAddPageData();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>增加/修改管理员信息</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <style>
 input{FONT-FAMILY:宋体;FONT-SIZE: 9pt;}
 .cssPWD{background-color:#EBEBEB;border-right:solid 1px #BEBEBE;border-bottom:solid 1px #BEBEBE;}
 .cssWeak{background-color:#FF4545;border-right:solid 1px #BB2B2B;border-bottom:solid 1px #BB2B2B;}
 .cssMedium{background-color:#FFD35E;border-right:solid 1px #E9AE10;border-bottom:solid 1px #E9AE10;}
 .cssStrong{background-color:#3ABB1C;border-right:solid 1px #267A12;border-bottom:solid 1px #267A12;}
 .cssPWT{width:132px;}
 </style>
 <script language='JavaScript' src='pwdStrength.js'></script>
<script type="text/javascript">
<!--
window.onerror = ignoreError;
function ignoreError(){return true;}
function EvalPwdStrength(oF,sP){
  PadPasswd(oF,sP.length*2);
  if(ClientSideStrongPassword(sP,gSimilarityMap,gDictionary)){DispPwdStrength(3,'cssStrong');}
  else if(ClientSideMediumPassword(sP,gSimilarityMap,gDictionary)){DispPwdStrength(2,'cssMedium');}
  else if(ClientSideWeakPassword(sP,gSimilarityMap,gDictionary)){DispPwdStrength(1,'cssWeak');}
  else{DispPwdStrength(0,'cssPWD');}
}
function PadPasswd(oF,lPwd){
  if(typeof oF.PwdPad=='object'){var sPad='IfYouAreReadingThisYouHaveTooMuchFreeTime';var lPad=sPad.length-lPwd;oF.PwdPad.value=sPad.substr(0,(lPad<0)?0:lPad);}
}
function DispPwdStrength(iN,sHL){
  if(iN>3){ iN=3;}for(var i=0;i<4;i++){ var sHCR='cssPWD';if(i<=iN){ sHCR=sHL;}if(i>0){ GEId('idSM'+i).className=sHCR;}GEId('idSMT'+i).style.display=((i==iN)?'inline':'none');}
}
function GEId(sID){return document.getElementById(sID);}

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
  alert("可以添加该管理员。");
 } else {
  alert("已经存在该管理员，请选择一个其它的名称。");
  document.myForm.adminName.focus();
 }
 
}

// 验证管理原名称是否已经存在。
function checkAdminName() {
 var name = document.myForm.adminName.value;
 if (name.length > 0) {
  sendRequest("admin_admin_action.jsp", "command=check_name&adminName=" + name, "POST");
 }
}

// 验证表单的提交。
function checkForm() {
 if (document.myForm.adminName.value.length < 1) {
  alert("管理员名不能为空");
  document.myForm.adminName.focus();
  return false;
 }
 if (document.myForm.userName.value.length < 1) {
  alert("前台会员名不能为空。");
  document.myForm.userName.focus();
  return false;
 }
 if (document.myForm.password.value != document.myForm.newPassword.value) {
  alert("新密码与验证密码不一致。");
  document.myForm.newPassword.focus();
  return false;
 } else {
  return true;
 }
 return false;
}
// -->
</script>
</head>
<body>
<pub:declare>
<%@ include file="element_admin.jsp" %>

<pub:template name="main">
#{call admin_manage_navigator }<br />
<form method='post' action='admin_admin_action.jsp' name='myForm' onsubmit='return checkForm();'>
<table width='100%' border='0' align='center' cellpadding='2'
 cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22' colspan='2'>
  <div align='center'><strong> #{if (admin_onedit.id != 0) } 修 改 管 理 员 密 码 
   #{else } 新 增 管 理 员 #{/if } </strong></div>
 </td>
</tr>
<tr class='tdbg'>
 <td width='12%' align='right' class='tdbg'><strong>管理员名：</strong></td>
 <td width='88%' class='tdbg'>
 <input name='adminName' #{iif (admin_onedit.id != 0, "readonly ", "") } type='text'
 value="#{admin_onedit.adminName@html }" />
 <input type="button" #{iif (admin_onedit.id != 0, 'disabled ', '') }
  value="检查管理员名" onclick="checkAdminName();" />
 </td>
</tr>
<tr class='tdbg'>
 <td width='12%' align='right' class='tdbg'><strong>前台会员名：</strong></td>
 <td width='88%' class='tdbg'>
 <input name='userName' #{iif (admin_onedit.id != 0, "readonly ", "") } type='text'
  value="#{admin_onedit.userName@html }" /> 
  <input type="hidden" #{iif (admin_onedit.id != 0, 'disabled ', '') }
   value="添加新会员" onclick="alert('暂时未完成。');" />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='12%' align='right' class='tdbg'><strong>初始密码：</strong></td>
  <td width='88%' class='tdbg'><input type='password'
   name='password'
   onkeyup='javascript:EvalPwdStrength(document.forms[0],this.value);'
   onmouseout='javascript:EvalPwdStrength(document.forms[0],this.value);'
   onblur='javascript:EvalPwdStrength(document.forms[0],this.value);'></td>
 </tr>
 <tr class='tdbg'>
  <td width='12%' align='right' class='tdbg'><strong>密码强度：</strong></td>
  <td width='88%' class='tdbg'>
<table cellpadding='0' cellspacing='0' class='cssPWT' style='height:16px'>
 <tr valign='bottom'>
  <td id='idSM1' width='33%' class='cssPWD' align='center'><span
   style='font-size:1px'>&nbsp;</span><span id='idSMT1'
   style='display:none;'>弱</span></td>
  <td id='idSM2' width='34%' class='cssPWD' align='center'
   style='border-left:solid 1px #fff'><span
   style='font-size:1px'>&nbsp;</span><span id='idSMT0'
   style='display:inline;font-weight:normal;color:#666'>无</span><span
   id='idSMT2' style='display:none;'>中</span></td>
  <td id='idSM3' width='33%' class='cssPWD' align='center'
   style='border-left:solid 1px #fff'><span
   style='font-size:1px'>&nbsp;</span><span id='idSMT3'
   style='display:none;'>强</span></td>
 </tr>
</table>
</td>
</tr>
<tr class='tdbg'>
 <td width='12%' align='right' class='tdbg'><strong>确认密码：</strong></td>
 <td width='88%' class='tdbg'>
 <input type='password' name='newPassword'></td>
</tr>
<tr class='tdbg'>
 <td width='12%' align='right' class='tdbg'><strong>权限设置：
 </strong></td>
 <td width='88%' class='tdbg'><input name='adminType'
  type='radio' value='1' #{iif (admin_onedit.adminType==1, "checked", "") }/>
 超级管理员：拥有所有权限。某些权限（如管理员管理、网站信息配置、网站频道管理等管理权限）只有超级管理员才有。 <br>
 <input type='radio' name='adminType'
  value='2' #{iif (admin_onedit.adminType==2, "checked", "") } /> 普通管理员：需要详细指定每一项管理权限
 <br />
 <input type="radio" name="adminType"
  value="0" #{iif (admin_onedit.adminType==0, "checked", "") } /> 无任何权限：没有任何管理权限。
 </td>
</tr>
<tr>
 <td height='40' colspan='2' align='center' class='tdbg'>
  <input type="hidden" name="command" value="save" />
  <input type="hidden" name="adminId" value="#{admin_onedit.id }" />
  <input type='submit' value=' #{iif (admin_onedit.id > 0, "保 存", "添 加") } ' style='cursor:hand;'>&nbsp;
  <input type='button' id='Cancel' value=' 取 消 ' onclick="window.location.href='admin_admin_list.jsp';"
    style='cursor:hand;'>
  </td>
 </tr>
</table>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />
</body>
</html>
