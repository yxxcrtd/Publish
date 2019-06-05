<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp" %>
   
<%@ page import="com.chinaedustar.publish.admin.AdminManage" %>
<%@ taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%
  //
  AdminManage admin_data = new AdminManage(pageContext);
  admin_data.initChangePasswordPageData();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>增加/修改管理员信息</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
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


// 验证表单的提交。
function checkForm() {
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

<pub:template name="main">
<br />
<br />
	<form method='post' action='admin_admin_password_save.jsp' name='myForm'
		onsubmit='return checkForm();'>
	<table width='300' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
		<tr class='title'>
			<td height='22' colspan='2'>
			<div align='center'><strong>
			 修 改 管 理 员 密 码 </strong></div>
			</td>
		</tr>
		<tr class='tdbg'>
			<td width='100' align='right' class='tdbg'><strong>管理员名：</strong></td>
			<td class='tdbg'>
			#{admin.adminName }
			</td>
		</tr>
		<tr class='tdbg'>
			<td align='right' class='tdbg'><strong>权限类型：</strong></td>
			<td class='tdbg'>
			#{iif (admin.adminType==1, "超级管理员", "普通管理员") }
			</td>
		</tr>
		<tr class='tdbg'>
			<td align='right' class='tdbg'><strong>原密码：</strong></td>
			<td class='tdbg'>
			<input type='password' name='oldPassword' />
			</td>
		</tr>
		<tr class='tdbg'>
			<td align='right' class='tdbg'><strong>新密码：</strong></td>
			<td class='tdbg'><input type='password'
				name='password'
				onkeyup='javascript:EvalPwdStrength(document.forms[0],this.value);'
				onmouseout='javascript:EvalPwdStrength(document.forms[0],this.value);'
				onblur='javascript:EvalPwdStrength(document.forms[0],this.value);'></td>
		</tr>
		<tr class='tdbg'>
			<td align='right' class='tdbg'><strong>密码强度：</strong></td>
			<td class='tdbg'>
			<style>
input{FONT-FAMILY:宋体;FONT-SIZE: 9pt;}
.cssPWD{background-color:#EBEBEB;border-right:solid 1px #BEBEBE;border-bottom:solid 1px #BEBEBE;}
.cssWeak{background-color:#FF4545;border-right:solid 1px #BB2B2B;border-bottom:solid 1px #BB2B2B;}
.cssMedium{background-color:#FFD35E;border-right:solid 1px #E9AE10;border-bottom:solid 1px #E9AE10;}
.cssStrong{background-color:#3ABB1C;border-right:solid 1px #267A12;border-bottom:solid 1px #267A12;}
.cssPWT{width:132px;}
</style>
			<table cellpadding='0' cellspacing='0' class='cssPWT'
				style='height:16px'>
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
			<td align='right' class='tdbg'><strong>确认密码：</strong></td>
			<td class='tdbg'>
			<input type='password' name='newPassword'></td>
		</tr>
		<tr>
			<td height='40' colspan='2' align='center' class='tdbg'>
			<input type="hidden" name="adminId" value="#{admin.id }" />
			<input type='submit' value=' 确 定 ' style='cursor:hand;'>&nbsp;
			<input type='button' id='Cancel' value=' 取 消 ' onClick="window.location.href='admin_index_main.jsp';"
				style='cursor:hand;'>
			</td>
		</tr>
	</table>
	</form>
</pub:template>

<pub:process_template name="main" />
</body>
</html>
