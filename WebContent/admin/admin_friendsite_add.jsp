<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.FriendSiteManage"
%><%
  // 初始化此页面所需数据.
  FriendSiteManage manage = new FriendSiteManage(pageContext);
  manage.initEditPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>友情链接管理</title>
</head>
<body>

<pub:template name="main">
 #{call fs_admin_help}
 #{call js_code}
 #{call fs_form}
</pub:template>

<pub:template name="fs_form">
<form method='post' name='myform' onsubmit='return checkForm()' action='admin_friendsite_action.jsp'>
<table border='0' cellpadding='2' cellspacing='1' align='center' width='100%' class='border'>
 <tr class='title'>
  <td height='22' colspan='2' align='center'><strong>添加友情链接</strong></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>链接所属类别：</strong></td>
  <td>
   <select name='kindId' id='KindID'>
    <option value='0' #{if fs_obj.kind == null}selected#{/if}>不属于任何类别</option>
    #{foreach kind in fs_kinds}
     <option value='#{kind.id}' #{if kind == fs_obj.kind}selected#{/if}>#{kind.name}</option>
    #{/foreach}
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>链接所属专题：</strong></td>
  <td>
   <select name='specialId' id='SpecialID'>
    <option value='0' #{if fs_obj.special == null}selected#{/if}>不属于任何专题</option>
    #{foreach special in fs_specials}
    <option value='#{special.id}' #{if special == fs_obj.special}selected{#/if}>#{special.name}</option>
    #{/foreach}
   </select>
   </td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>网站名称：</strong></td>
  <td><input type='text' name='siteName' id='SiteName' size='60' maxlength='50' value='#{fs_obj.siteName}'> <font color='#FF0000'> *</font></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>网站地址：</strong></td>
  <td><input type='text' name='siteUrl' id='SiteUrl' size='80' maxlength='100' value='#{fs_obj.siteUrl}'> <font color='#FF0000'>*</font></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>网站Logo地址：</strong></td>
  <td><input type='text' name='logo' id='Logo' size='80' maxlength='100' value='#{fs_obj.logo}'></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>站长姓名：</strong></td>
  <td><input type='text' name='siteAdmin' id='SiteAdmin' size='40'  maxlength='25' value='#{fs_obj.siteAdmin}'></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>电子邮件：</strong></td>
  <td><input type='text' name='siteEmail' id='SiteEmail' size='40'  maxlength='50' value='#{fs_obj.siteEmail}'></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>网站密码：</strong></td>
  <td><input type='password' name='sitePassword' id='SitePassword' size='30' maxlength='20'>
   #{if fs_obj.id == 0}<font color='#FF0000'>*</font> 用于修改信息时用。
   #{else}<font color='#FF0000'>若不修改，请保持为空</font>
   #{/if}
   </td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>确认密码：</strong></td>
  <td><input type='password' name='sitePwdConfirm' id='SitePwdConfirm' size='30' maxlength='20'>
   #{if fs_obj.id == 0}<font color='#FF0000'>*</font>#{/if}</td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>网站简介：</strong></td>
  <td><textarea name='description' id='Description' cols='67' rows='4'>#{site.description@html}</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>网站评分等级：</strong></td>
  <td>
   <select name='stars' id='Stars'>
    <option value='5' #{if fs_obj.stars == 5}selected#{/if}>★★★★★</option>
    <option value='4' #{if fs_obj.stars == 4}selected#{/if}>★★★★</option>
    <option value='3' #{if fs_obj.stars == 3}selected#{/if}>★★★</option>
    <option value='2' #{if fs_obj.stars == 2}selected#{/if}>★★</option>
    <option value='1' #{if fs_obj.stars == 1}selected#{/if}>★</option>
    <option value='0' #{if fs_obj.stars == 0}selected#{/if}>无</option>
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>点 击 数：</strong></td>
  <td><input type='text' name='hits' id='Hits' size='10' maxlength='10' value='#{fs_obj.hits}'></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>录入时间：</strong></td>
  <td><input type='text' name='lastModified' readonly="readonly" id='LastModified' value='#{fs_obj.lastModified@format }' maxlength='50'> 时间格式为“年-月-日 时:分:秒”，如：<font color='#0000FF'>2007-8-23 16:18:32</font></td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>是否推荐站点：</strong></td>
  <td><input type='radio' name='elite' value='true' #{if fs_obj.elite == true}checked#{/if}> 是&nbsp;&nbsp;
      <input type='radio' name='elite' value='false' #{if fs_obj.elite == false}checked#{/if}> 否&nbsp;&nbsp;      </td>
 </tr>
 <tr class='tdbg'>
  <td width='150' align='right'><strong>是否审核通过：</strong></td>
  <td><input type='radio' name='approved' value='true' #{if fs_obj.approved == true}checked#{/if}> 是&nbsp;&nbsp;        
      <input type='radio' name='approved' value='false' #{if fs_obj.approved == false}checked#{/if}> 否&nbsp;&nbsp;      </td>
 </tr>
 <tr class='tdbg'>
  <td height='40' colspan='2' align='center'>
   <input name='command' type='hidden' id='Action' value='#{if fs_obj.id == 0}save#{else}update#{/if}' />
   <input name='id' type='hidden' value='#{fs_obj.id}' />
   <input type='submit' value=' 确 定 ' name='submit' />&nbsp;&nbsp;
   <input type='reset' value=' 重 填 ' name='reset' />
  </td>
 </tr>
</table>
</form>
</pub:template>

<pub:template name="js_code">
<script language = 'JavaScript'>
function checkForm(){
  if (document.myform.SiteName.value==''){
    alert('请输入网站名称！');
    document.myform.SiteName.focus();
    return false;
  }
  if (document.myform.SiteUrl.value=='' || document.myform.SiteUrl.value=='http://'){
    alert('请输入网站地址！');
    document.myform.SiteUrl.focus();
    return false;
  }
  if (document.myform.Action.value=='save' && document.myform.SitePassword.value==''){
    alert('请输入网站密码！');
    document.myform.SitePassword.focus();
    return false;
  }
  if (document.myform.Action.value=='save'&&document.myform.SitePwdConfirm.value==''){
    alert('请输入确认密码！');
    document.myform.SitePwdConfirm.focus();
    return false;
  }
  if (document.myform.SitePwdConfirm.value != document.myform.SitePassword.value){
    alert('网站密码与确认密码不一致！');
    document.myform.SitePwdConfirm.focus();
    document.myform.SitePwdConfirm.select();
    return false;
  }
}
</script>
</pub:template>

<%@ include file="element_fs.jsp" %>

<pub:process_template name="main" />
	
</body>
</html>