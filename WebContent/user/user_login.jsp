<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%
  Object site = com.chinaedustar.publish.PublishUtil.getPublishContext().getSite();
  pageContext.setAttribute("site", site);
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>会员登录</title>
<link href="../admin/admin_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../admin/main.js"></script>
<script language=javascript>
function SetFocus()
{
if (document.login.userName.value=="")
    document.login.userName.focus();
else
    document.login.userName.select();
}
function checkLogin()
{
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

</script>
</head>
<body onload="refreshimg();">

<pub:template name="main">
<script type="text/javascript">
function refreshimg() {
	var newUrl = "#{site.installDir}/validCode.jsp?rdm=" + Math.random();
	byId("imgValidCode").src = newUrl;
}
</script>
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
  <td>
 <form name="login" action="user_login_action.jsp" method="post" onSubmit="return checkLogin()">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="120" height="164" background="images/user_login_0_02.gif"></td>
      <td width="60" height="164" background="images/user_login_0_04.gif"></td>
      <td valign="top" background="images/user_login_0_08.gif"><table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="220" height="79" background="images/user_login_0_05.gif"></td>
          <td width="279"><table width="100%" height="79"  border="0" cellpadding="0" cellspacing="0">
            <tr></tr>
            <tr>
              <td ><font color="#ffffff">欢迎您登录#{site.name }<br>
                如果您尚未注册，请先<a href="user_registry.jsp"><font color="#FFFF00">注册</font></a>。</font></td>
              <td width="85" valign="bottom" ><input name="returnUrl" type="hidden" id="returnUrl" value="<%=request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl")%>">
                  <input type="image" name="Submit" src="images/user_login_0_13.gif" style="width:85px; HEIGHT: 57px;"></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="85" colspan="2"><table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="35" rowspan="2"><img src="images/user_login_0_15.gif" width="20" height="30" alt=""></td>
              <td height="20"><font color="#ffffff">用户名称：</font></td>
              <td width="45" rowspan="2" align="center" valign="middle"><img src="images/user_login_0_19.gif" width="20" height="30" alt=""></td>
              <td><font color="#ffffff">用户密码：</font></td>
               <td width="50" rowspan="2" align="center"><img src="images/user_login_0_23.gif" width="29" height="30" alt=""></td>
              <td><font color="#ffffff">验证码：</font></td>
              <td>&nbsp;</td>
              <td width="35" rowspan="2" align="center"><img src="images/user_login_cookie.gif" alt=""></td>
              <td><font color="#ffffff">Cookie：</font></td>
            </tr>
            <tr>
              <td><input name="userName"  type="text"  id="userName" maxlength="20" style="width:70px; BORDER-RIGHT: #ffffff 0px solid; BORDER-TOP: #ffffff 0px solid; FONT-SIZE: 9pt; BORDER-LEFT: #ffffff 0px solid; BORDER-BOTTOM: #c0c0c0 1px solid; HEIGHT: 16px; BACKGROUND-COLOR: #ffffff"></td>
              <td><input name="password"  type="password" id="password" maxlength="20" style="width:70px; BORDER-RIGHT: #ffffff 0px solid; BORDER-TOP: #ffffff 0px solid; FONT-SIZE: 9pt; BORDER-LEFT: #ffffff 0px solid; BORDER-BOTTOM: #c0c0c0 1px solid; HEIGHT: 16px; BACKGROUND-COLOR: #ffffff"></td>
              <td><input name='checkCode' size='6' maxlength='6' style='width:50px; BORDER-RIGHT: #F7F7F7 0px solid; BORDER-TOP: #F7F7F7 0px solid; FONT-SIZE: 9pt; BORDER-LEFT: #F7F7F7 0px solid; BORDER-BOTTOM: #c0c0c0 1px solid; HEIGHT: 16px; BACKGROUND-COLOR: #F7F7F7; ime-mode:disabled;' onmouseover=''this.style.background='#ffffff';'' onmouseout=''this.style.background='#F7F7F7''' onFocus='this.select();'></td>
              <td>&nbsp;<a href='javascript:refreshimg()' title='看不清楚，换个图片'><img id='imgValidCode' src='' width='56' height='18' style='border: 1px solid #ffffff' /></a></td>
              <td width="40">
              <select name='CookieDate'  style='border: 1px solid #ffffff'>
                  <option selected value='0'>不保存</option>
                  <option value='1'>保存一天</option>
                  <option value=2>保存一月</option>
                  <option value=3>保存一年</option>
              </select></td>
            </tr>
          </table></td>
        </tr>
        
      </table></td>
    </tr>
  </table>
  </form>
  <script language="JavaScript" type="text/JavaScript">
  SetFocus();
  </script> 
  </td>
 </tr>
</table>
</pub:template>

<pub:process_template name="main" />
</body>
</html>