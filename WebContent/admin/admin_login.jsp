<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" errorPage="admin_error.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>管理员登录</title>
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312' />
 <link rel='stylesheet' href='admin_style.css' />
 <script type="text/javascript" src="main.js"></script>
<script language=javascript>
<!--
var closestr=0;
function setFocus() {
 var obj = byId("userName");
 if (obj.value == '')
   obj.focus();
 else
   obj.select();
}
function checkLogin() {
 if (trim(byId("userName").value) == "") {
  alert("用户名不能为空！");
  byId("userName").focus();
  return false;
 }
 if (trim(byId("password").value) == "") {
  alert("密码不能为空！");
  byId("password").focus();
  return false;
 }
 if (trim(byId("checkCode").value) == "") {
  alert("验证码不能为空！");
  byId("checkCode").focus();
  return false;
 }
 return true;
}

function checkBrowser() {
	var app=navigator.appName;
	var verStr=navigator.appVersion;
	if(app.indexOf('Netscape') != -1) {
		alert('友情提示：\n    你使用的是Netscape、Firefox或者其他非IE浏览器，可能会导致无法使用后台的部分功能。建议您使用 IE6.0 或以上版本。');
	} else if(app.indexOf('Microsoft') != -1) {
		if (verStr.indexOf('MSIE 3.0')!=-1 || verStr.indexOf('MSIE 4.0') != -1 || verStr.indexOf('MSIE 5.0') != -1 || verStr.indexOf('MSIE 5.1') != -1)
			alert('友情提示：\n    您的浏览器版本太低，可能会导致无法使用后台的部分功能。建议您使用 IE6.0 或以上版本。');
		}
}

function refreshimg() {
 var newUrl = "../validCode.jsp?rdm=" + Math.random();
 byId("imgValidCode").src = newUrl;
}
//-->
</script>
</head>
<body onload="refreshimg();setFocus();">
<table width='100%' height='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td>
  <form name='Login' action='admin_login_action.jsp' method='post' target='_parent' onSubmit='return checkLogin();'>
  <table width='100%' border='0' cellpadding='0' cellspacing='0'>
   <tr>
    <td width='219' height='164'
     background='images/admin_login1_0_02.gif'></td>
    <td width='64' height='164'
     background='images/admin_login1_0_04.gif'></td>
    <td valign='top' background='images/admin_login1_0_09.gif'>
    <table border='0' cellpadding='0' cellspacing='0'>
     <tr>
      <td>
      <table width='100%' border='0' cellpadding='0' cellspacing='0'>
       <tr>
        <td width='270' height='79'
         background='images/admin_login1_0_05.gif'></td>
        <td width='150' height='79'
         background='images/admin_login1_0_06.gif'></td>
        <td valign='top'>
        <table width='100%' border='0' cellpadding='0' cellspacing='0'>
         <tr>
          <td height='21'></td>
          <td></td>
         </tr>
         <tr>
          <td>
          <input
           type='image' name='Submit' src='images/admin_login1_0_10.gif'
           style='width:50px; HEIGHT: 50px;' /></td>
          <td width='58' height='50'
           background='images/admin_login1_0_11.gif'></td>
         </tr>
        </table>
        </td>
       </tr>
      </table>
      </td>
     </tr>
     <tr>
      <td height='85'>
      <table border='0' cellspacing='0' cellpadding='0'>
       <tr>
        <td width='22' rowspan='2' valign='bottom'><img
         src='images/admin_login1_0_15.gif' alt='' /></td>
        <td width='100'><font color='#ffffff'>用户名称：</font></td>
        <td width='22' rowspan='2' valign='bottom'><img
         src='images/admin_login1_0_19.gif' alt='' /></td>
        <td width='100'><font color='#ffffff'>用户密码：</font></td>
        <td width='22' rowspan='2' valign='bottom'><img 
        src='images/admin_login1_0_23.gif' alt='' /></td>
        <td colspan='2'><font color='#ffffff'>验证码：</font></td>
       </tr>
       <tr>
        <td><input name='adminName' type='text' id='userName'
         maxlength='20'
         style='width:80px; BORDER-RIGHT: #F7F7F7 0px solid; BORDER-TOP: #F7F7F7 0px solid; FONT-SIZE: 9pt; BORDER-LEFT: #F7F7F7 0px solid; BORDER-BOTTOM: #c0c0c0 1px solid; HEIGHT: 16px; BACKGROUND-COLOR: #F7F7F7'
         onmouseover="this.style.background='#ffffff'"
         onmouseout="this.style.background='#F7F7F7'"
         onFocus='this.select();'></td>
        <td><input name='password' type='password' maxLength='20'
         style='width:80px; BORDER-RIGHT: #F7F7F7 0px solid; BORDER-TOP: #F7F7F7 0px solid; FONT-SIZE: 9pt; BORDER-LEFT: #F7F7F7 0px solid; BORDER-BOTTOM: #c0c0c0 1px solid; HEIGHT: 16px; BACKGROUND-COLOR: #F7F7F7'
         onmouseover='this.style.background="#ffffff";'
         onmouseout='this.style.background="#F7F7F7";'
         onFocus='this.select();'></td>
        <td width='53'><input name='checkCode' size='6' maxlength='6' style='width:50px; BORDER-RIGHT: #F7F7F7 0px solid; BORDER-TOP: #F7F7F7 0px solid; FONT-SIZE: 9pt; BORDER-LEFT: #F7F7F7 0px solid; BORDER-BOTTOM: #c0c0c0 1px solid; HEIGHT: 16px; BACKGROUND-COLOR: #F7F7F7; ime-mode:disabled;'
         onmouseover='this.style.background="#ffffff";'
          onmouseout='this.style.background="#F7F7F7"'
          onFocus='this.select();'></td>
        <td width='51'><a href='javascript:refreshimg()' title='看不清楚，换个图片'><img id='imgValidCode' src='' width='56' height='18' style='border: 1px solid #ffffff' /></a></td>
       </tr>
      </table>
      </td>
     </tr>
    </table>
    </td>
   </tr>
  </table>
  </form>
  </td>
 </tr>
</table>
<script language='JavaScript' type='text/JavaScript'><!--
checkBrowser();
// -->
</script>
</body>
</html>