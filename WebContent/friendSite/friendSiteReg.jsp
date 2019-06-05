<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.FriendSiteManage"
%><%
  // 初始化页面数据.
  FriendSiteManage manage = new FriendSiteManage(pageContext);
  manage.initRegPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
 <title>申请友情链接</title>
 <link href="../Skin/DefaultSkin.css" rel="stylesheet" type="text/css">
<script language = "JavaScript">
<!--
function CheckForm(){
  if(document.myform.SiteName.value==""){
    alert("请输入网站名称！");
    document.myform.SiteName.focus();
    return false;
  }
  if(document.myform.SiteUrl.value=="" || document.myform.SiteUrl.value=="http://"){
    alert("请输入网站地址！");
    document.myform.SiteUrl.focus();
    return false;
  }
  if(document.myform.SiteAdmin.value==""){
    alert("请输入站长姓名！");
    document.myform.SiteAdmin.focus();
    return false;
  }
  if(document.myform.SitePassword.value==""){
    alert("请输入网站密码！");
    document.myform.SitePassword.focus();
    return false;
  }
  if(document.myform.SitePwdConfirm.value==""){
    alert("请输入确认密码！");
    document.myform.SitePwdConfirm.focus();
    return false;
  }
  if(document.myform.SitePwdConfirm.value!=document.myform.SitePassword.value){
    alert("网站密码与确认密码不一致！");
    document.myform.SitePwdConfirm.focus();
    document.myform.SitePwdConfirm.select();
    return false;
  }
  if(document.myform.SiteIntro.value==""){
    alert("请输入网站简介！");
    document.myform.SiteIntro.focus();
    return false;
  }
}
//-->
</script>
</head>
<body leftmargin="2" topmargin="0" marginwidth="0" marginheight="0">

<%-- 主模板 --%>
<pub:template name="main">
	#{call friendsite}
</pub:template>

<%-- 友情链接主体部分 --%>
<pub:template name="friendsite">
<form method="post" name="myform" onSubmit="return CheckForm()" action="friendSiteReg_save.jsp">
  <table width="760" border="0" align="center" cellpadding="0" cellspacing="0" class="center_tdbgall">
    <tr>
      <td align="center">
        <table width="400" border="0" cellspacing="0" cellpadding="0" class="main_title_575">
          <tr>
            <td><b>本站链接信息</b></td>
          </tr>
        </table>
        <table border="0" cellpadding="2" cellspacing="1" width="400" class="main_tdbg_575">
          <tr class="tdbg">
            <td width="92" height="25" align="right" valign="middle">本站名称：</td>
            <td width="297" height="25">#{site.name} </td>
          </tr>
          <tr class="tdbg">
            <td width="92" height="25" align="right">本站地址：</td>
            <td height="25">#{site.url}</td>
          </tr>
          <tr class="tdbg">
            <td width="92" height="25" align="right">本站Logo：</td>
            <td height="25"><a href='#{site.url}' title='#{site.title }' target='_blank'><img src='#{site.logo@uri}' border='0'></a></td>
          </tr>
          <tr class="tdbg">
            <td width="92" height="25" align="right">站长姓名：</td>
            <td height="25">#{site.webmaster}</td>
          </tr>
          <tr class="tdbg">
            <td width="92" height="25" align="right">电子邮件：</td>
            <td height="25">#{site.webmasterEmail}</td>
          </tr>
          <tr class="tdbg">
            <td width="92" align="right">本站简介：</td>
            <td valign="top">请申请链接的同时做好本站的链接。</td>
          </tr>
        </table>
        <br>
        <table width="400" border="0" cellspacing="0" cellpadding="0" class="main_title_575">
          <tr>
            <td><b>申请友情链接</b></td>
          </tr>
        </table>
        <table border="0" cellpadding="2" cellspacing="1" width="400" class="main_tdbg_575">
          <tr class="tdbg">
            <td width="93" height="25" align="right" valign="middle">所属类别：</td>
            <td width="296" height="25"><select name="KindID" id="KindID">
                <option value='0'>不属于任何类别</option>
					#{foreach kind in fs_kinds }
					 <option value='#{kind.id}'>#{kind.name}</option>
					#{/foreach }
            </select></td>
          </tr>
          <tr class="tdbg">
            <td width="93" height="25" align="right" valign="middle">所属专题：</td>
            <td height="25"><select name="SpecialID" id="SpecialID">
                <option value='0'>不属于任何专题</option>
				#{foreach special in fs_specials }
                <option value='#{special.id}'>#{special.name}</option>
				#{/foreach }

              </select></td>
          </tr>
          <tr class="tdbg">
            <td width="93" height="25" align="right" valign="middle">网站名称：</td>
            <td height="25"><input name="SiteName" size="30" maxlength="20" title="这里请输入您的网站名称，最多为20个汉字">
              <font color="#FF0000">*</font></td>
          </tr>
          <tr class="tdbg">
            <td width="93" height="25" align="right">网站地址：</td>
            <td height="25"><input name="SiteUrl" size="30" maxlength="100" type="text" value="http://" title="这里请输入您的网站地址，最多为50个字符，前面必须带http://">
              <font color="#FF0000">*</font></td>
          </tr>
          <tr class="tdbg">
            <td width="93" height="25" align="right">网站Logo：</td>
            <td height="25"><input name="LogoUrl" size="30" maxlength="100" type="text" value="http://" title="这里请输入您的网站LogoUrl地址，最多为50个字符，如果您在第一选项选择的是文字链接，这项就不必填"></td>
          </tr>
          <tr class="tdbg">
            <td width="93" height="25" align="right">站长姓名：</td>
            <td height="25"><input name="SiteAdmin" size="30" maxlength="20" type="text" title="这里请输入您的大名了，不然我知道您是谁啊。最多为20个字符">
              <font color="#FF0000">*</font></td>
          </tr>
          <tr class="tdbg">
            <td width="93" height="25" align="right">电子邮件：</td>
            <td height="25"><input name="SiteEmail" size="30" maxlength="30" type="text" value title="这里请输入您的联系电子邮件，最多为30个字符"></td>
          </tr>
          <tr class="tdbg">
            <td width="93" height="25" align="right">网站密码：</td>
            <td height="25"><input name="SitePassword" type="password" id="SitePassword" size="20" maxlength="20">
              <font color="#FF0000">*</font> 用于修改信息时用。</td>
          </tr>
          <tr class="tdbg">
            <td height="25" align="right">确认密码：</td>
            <td height="25"><input name="SitePwdConfirm" type="password" id="SitePwdConfirm" size="20" maxlength="20">
              <font color="#FF0000">*</font></td>
          </tr>
          <tr class="tdbg">
            <td width="93" align="right">网站简介：</td>
            <td valign="middle"><textarea name="SiteIntro" cols="35" rows="5" id="SiteIntro" title="这里请输入您的网站的简单介绍"></textarea></td>
          </tr>
          <tr class="tdbg">
            <td height="40" colspan="2" align="center"><input name="Action" type="hidden" id="Action" value="Reg"><input type="submit" value=" 确 定 " name="cmdOk">
              <input type="reset" value=" 重 填 " name="cmdReset"></td>
          </tr>
        </table>
        <br>
      </td>
    </tr>
  </table>
</form>
</pub:template>

<pub:process_template name="main" />

</body>
</html>