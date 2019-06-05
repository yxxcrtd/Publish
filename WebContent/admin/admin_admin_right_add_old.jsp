<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@ taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%@ page import="com.chinaedustar.publish.impl.TemplateProcessor" %>
<%@ page import="com.chinaedustar.publish.module.AdminRightModule" %>
<%@ page import="com.chinaedustar.publish.module.SiteModule" %>
<%@ page import="com.chinaedustar.publish.model.*" %>
<%@ page import="com.chinaedustar.publish.*" %>
<%@page import="java.util.Iterator"%>
<%
ParamUtil paramUtil = new ParamUtil(pageContext);
int adminId = paramUtil.safeGetIntParam("adminId", 0);
if (adminId == 0) {
 out.println("<div align='center'>管理员不存在！<a href='admin_admin_list.jsp'>返回</a></div>");
 out.close();
 return;
}
%>
<html>
<head> 
<title>管理员管理--设置权限</title>
<meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
<link href='admin_style.css' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 验证表单提交。

function checkForm() {
// alert("开始提交表单。");
 return true;
}
// -->
</script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<%@ include file="element_admin.jsp" %>

<pub:template name="main">
#{call admin_manage_navigator }
<form method='post' action='admin_admin_right_save.jsp' name='form1'
 onsubmit='javascript:checkForm();'>
<table width='100%' border='0' align='center' cellpadding='2'
 cellspacing='1' class='border'>
 <tr class='tdbg'>
  <td colspan='2'>
  <table id='PurviewDetail' width='100%' border='0' cellspacing='10'
   cellpadding='0'>
   <tr>
    <td colspan='2' align='center'><strong>管 理 员 权 限 详 细 设 置</strong></td>
   </tr>
#{call admin_right_channels }
#{call admin_right_others }
  </table>
  </td>
 </tr>
 <tr>
  <td height='40' colspan='2' align='center' class='tdbg'>
  <input type="hidden" name="adminId" value="<%=adminId %>" />
  <input type='submit' value='保存修改结果' style='cursor:hand;'>&nbsp;
  <input type='button' value=' 取 消 '
   onClick="window.location.href='admin_admin_list.jsp'" style='cursor:hand;'>
  </td>
 </tr>
</table>
</form>
</pub:template>

<!-- 频道类型的权限设置。 -->
<pub:template name="admin_right_channels">
<%
Site site = paramUtil.getPublishContext().getSite();
java.util.HashMap<String, Object> options = null;
try {
// Admin admin = PublishUtil.getCurrentAdmin(pageContext);
 Admin admin = site.getAdminCollection().getSimpleAdmin(adminId);
 ChannelCollection channels = site.getChannels();
 TemplateProcessor tp = new TemplateProcessor(site._getPublishContext());
 // 频道的权限设置。

 for (Iterator<Channel> iterator = channels.iterator(); iterator.hasNext(); ) {
  Channel channel = iterator.next();
  if (channel.getStatus() == 0 && channel.getChannelType() != 2) {
   int moduleId = channel.getModuleId();
   PublishModule publishModule = site.getModules().getModule(moduleId).getPublishModule();
   if (publishModule instanceof AdminRightModule) {
    out.println("<tr valign='top'><td>");
    AdminRightModule channelModule = (AdminRightModule)publishModule;
    options = channelModule.getRightHtmlData(admin.getAdminRightCollection(), channel.getId());
    String template = channelModule.getRightHtmlTemplate();
    if (template == null) {
     template = "";
    }
    String result = tp.processTemplate(template, options, null);
    out.println(result);
    out.println("</td></tr>");
   }
  }
 }
 // 网站其它管理权限设置。
 for (Iterator<AdminRightModule> iterator = paramUtil.getPublishContext().getSite().getAdminRightModules().iterator(); iterator.hasNext(); ) {
  AdminRightModule module = iterator.next();
  if (module instanceof SiteModule) {
   options = module.getRightHtmlData(admin.getAdminRightCollection(), 0);
   pageContext.setAttribute("site_more", options.get("site_more"));   
  }
 }
} catch (Exception ex) {
 ex.printStackTrace();
 out.println(ex.getMessage());
}
%>
</pub:template>

<!-- 网站管理的权限设置 -->
<pub:template name="admin_right_others">
   <tr>
    <td>
    <fieldset><legend>此管理员的其他网站管理权限：<input
     name='chkAll' type='checkbox' id='chkAll' value='Yes'
     onclick='CheckAll(this)'>选中所有权限</legend>
    <table width='100%' border='0' cellspacing='1' cellpadding='2'>
     <tr>
      <td width='16%'>
      <input name='itemId' type='checkbox' value='siteManager_selfPassword' 
        #{iif (site_more@contains("siteManager_selfPassword"), "checked", "") }/>修改自己密码
      </td>
      <td width='16%'>
      <input name='itemId' type='checkbox' value='siteManager_channel'
       #{iif (site_more@contains("siteManager_channel"), "checked", "") }/>网站频道管理
      </td>
      <td width='16%'>
      <input name='itemId' type='checkbox' value='siteManager_label'
       #{iif (site_more@contains("siteManager_label"), "checked", "") }/>自定义标签管理

      </td>
      <td width='16%'>
      <input name='itemId' type='checkbox' value='siteManager_friend'
       #{iif (site_more@contains("siteManager_friend"), "checked", "") }/>友情链接管理
      </td>
      <td width='16%'>
      <input name='itemId' type='checkbox' value='siteManager_skin'
       #{iif (site_more@contains("siteManager_skin"), "checked", "") }/>网站风格管理
      </td>
      <td width='16%'>
      <input name='itemId' type='checkbox' value='siteManager_template'
       #{iif (site_more@contains("siteManager_template"), "checked", "") }/>通用模板管理
      </td>
     </tr>
    </table>
    </fieldset>
    </td>
   </tr>
</pub:template>

<pub:process_template name="main"/>
</body>
</html>
