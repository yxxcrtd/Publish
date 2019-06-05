<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="admin_error.jsp" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%@page import="com.chinaedustar.publish.admin.IndexManage" %>

<%
  IndexManage manager = new IndexManage(pageContext);
  if (manager.isAdminLogined() == false) {
    out.println("未登录"); 
    return;
  }
  manager.initLeftPageData();

  boolean support_biz = manager.isSupportBiz();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>管理导航</title>
 <link href="admin_left.css" rel="stylesheet" type="text/css" />
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<%-- 菜单的定义和生成，菜单构造之后激发 onMenuShow 事件。 --%>
<pub:menu name="main_menu" text="管理菜单" event="true">
  <pub:submenu id="site_conf" text="系统设置" icon="images/__example_admin_left.gif" url="admin_system_guide.jsp">
    <pub:menuitem text="网站信息配置" url="admin_site.jsp" target="main" right="site.site_manage" />
    <pub:menuitem text="网站频道管理" url="admin_channel_list.jsp" target="main" right="site.channel_manage" />
    <pub:menuitem text="全站专题管理" url="admin_special_list.jsp" target="main" right="site.special_manage" />
    <pub:menuitem text="会员管理" url="admin_user_list.jsp" target="main" right="user.user_manage" />
    <pub:menuitem text="管理员管理" url="admin_admin_list.jsp" target="main" right="user.admin_manage" />
   <%-- <\pub : menuitem text="网站广告管理(未实现)" url="admin_advertisement.jsp" target="main" right="site.ad_manage" /> --%>
    <pub:menuitem text="友情链接管理" url="admin_friendsite_list.jsp" target="main" right="site.friend_manage" />
    <pub:menuitem text="网站公告管理" url="admin_announce.jsp" target="main" right="site.announce_manage" />
    <pub:menuitem text="网站调查管理" url="admin_vote_list.jsp" target="main" right="site.vote_manage" />
    <pub:submenu id="" text="">
      <pub:menuitem text="网站统计分析" url="admin_stat_index.jsp" target="main" right="site.site_manage" />
      <pub:menuitem text="配置" url="admin_stat_config.jsp" target="main" right="site.site_manage" />
    </pub:submenu>
   <%-- <\pub:menuitem text="网站留言管理(未实现)" url="admin_feedback_list_unimpl.jsp" target="main" right="site.feedback_manage" /> --%>
    <pub:menuitem text="扩展页面管理" url="admin_webpage.jsp" target="main" right="site.page_manage" />
    <pub:menuitem text="网站日志管理" url="admin_log.jsp" target="main" right="site.log_manage" />
    <pub:submenu id="" text="">
      <pub:menuitem text="本地帮助" url="help/index.jsp" target="main" />
      <pub:menuitem text="网上帮助" url="http://www.chinaedustar.com/publish/help/" target="_blank" itemBreak="false" />
    </pub:submenu>
  </pub:submenu>

  <pub:submenu id="tmpl_manage" text="模板管理" icon="images/__example_admin_left.gif" url="admin_system_guide.jsp">
    <pub:menuitem text="网站模板方案管理" url="admin_theme_list.jsp" target="main" right="site.theme_manage" />
    <pub:menuitem text="自定义标签管理" url="admin_label_list.jsp" target="main" right="site.label_manage" />
    <%--
    <\pub:menuitem text="模板页面布局管理" url="admin_layout_list.jsp" target="main" right="site.theme_manage" />
    <\pub:menuitem text="模板页面母版管理" url="admin_master_list.jsp" target="main" right="site.theme_manage" />
    --%>
  </pub:submenu>

  <pub:submenu id="site_generate" text="网站生成管理" icon="images/__example_admin_left.gif" url="admin_system_guide.jsp">
    <pub:menuitem text="全站生成管理" url="admin_generate_site.jsp" target="main" right="site.generate_manage" />
    <pub:sourcemenuitem source="channel_menus" />
    <%--
    <\pub:submenu id="site_make_1" text="">
      <\pub:menuitem text="定时设置" url="admin_timing_list.jsp" target="main" />
      <\pub:menuitem text="启动定时" url="admin_timimg_start.jsp" target="main" itemBreak="false" />
    <\/pub:submenu>
    --%>
  </pub:submenu>

  <%if (support_biz) { %>
  <pub:submenu id="unite_manage" text="相关业务连接管理" icon="images/__example_admin_left.gif" url="admin_system_guide.jsp">
    <pub:menuitem text="相关业务列表" url="admin_biz_list.jsp" target="main" />
  </pub:submenu>
  <%} %>
</pub:menu>

<pub:template name="main">
  #{call title }
  #{call admin_info }
  #{call show_menus }
  #{call copyright }
</pub:template>

<%-- 标题 --%>
<pub:template name="title">
<table width='180' border='0' align='center' cellpadding='0' cellspacing='0'>
 <tr>
  <td height='44' valign='top'>
    <img src='images/__example_title.gif' />
  </td>
 </tr>
</table>
</pub:template>

<%-- 管理员的信息 --%>
<pub:template name="admin_info">
<table cellpadding='0' cellspacing='0' width='180' align='center'>
 <tr>
  <td height='26' class='menu_title'
  onmouseover="this.className='menu_title_hover';"
  onmouseout="this.className='menu_title';"
  background='images/__example_title_bg_quit.gif' id='menuTitle0'>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <a href='admin_index_main.jsp' target='main'><b><span class='glow'>管理首页</span></b></a>
 <span class='glow'>|</span>
 <a href='admin_logout.jsp' target='_top'><b><span class='glow'>退出</span></b></a>
  </td>
 </tr>
 <tr>
  <td height='97' background='images/__example_title_bg_admin.gif' style='display:' id='submenu0'>
   <div style='width:180'>
   <table cellpadding='0' cellspacing='0' align='center' width='130'>
    <tr>
   <td height='25'>您的用户名：#{admin.adminName }</td>
  </tr>
  <tr>
   <td height='25'>您的身份：#{iif(admin.adminType == 1, "超级管理员", "普通管理员") }</td>
  </tr>
   </table>
   </div>
   <div style='width:167;height:20;'></div>
  </td>
 </tr>
</table>
</pub:template>

<%-- 主菜单显示 --%>
<pub:template name="show_menus">
 #{foreach submenu in main_menu}
   #{call show_sub_menu(submenu)}
 #{/foreach}
</pub:template>

<%-- 子菜单显示 --%>
<pub:template name="show_sub_menu">
#{param submenu}
<table cellpadding='0' cellspacing='0' width='167' align='center'>
 <tr>
  <td height='28' class='menu_title'
  onmouseover="this.className='menu_title_hover';" onmouseout="this.className='menu_title';"
  background='images/__example_admin_left.gif' id='mt_#{submenu.id}'
  onclick="js_toggle('#{submenu.id}')" style='cursor:hand;'>
 <a href='#{submenu.url}' target='main'><span class='glow'>#{submenu.text}</span></a>
  </td>
 </tr>
 <tr>
  <td style='display:none' align='right' id='#{submenu.id}'>
 <div class='sec_menu' style='width:165'>
  #{call show_menu_item(submenu) }
 </div>
 <div style='width:167;height:20;'></div>
  </td>
 </tr>
</table>
</pub:template>

  <%-- 菜单项显示 --%>
<pub:template name="show_menu_item">
#{param submenu}
<table cellpadding='0' cellspacing='0' align='center' width='140'>
#{foreach item in submenu}
 <tr height='20'>
    <td align='left'>
  #{if item.text == '' }
    #{foreach item2 in item }
      #{iif(!item2@is_first, ' | ', '') }<a href='#{item2.url}' target='#{item2.target }'>#{item2.text}</a>
    #{/foreach }
  #{elseif (item.lineBreak == true)}
  #{item.text}
  #{else }
  <a href='#{item.url}' target='#{item.target }'>#{item.text}</a>
  #{/if }
  </td>
 </tr>
#{/foreach}
</table>
</pub:template>


  <%-- 版权和支持 --%>
<pub:template name="copyright">
<table cellpadding='0' cellspacing='0' width='167' align='center'>
 <tr>
  <td height='28' class='menu_title'
   onmouseover="this.className='menu_title_hover';"
   onmouseout="this.className='menu_title';"
   background='images/__example_admin_left.gif' id='menuTitle208'>
  <span>系统信息</span></td>
 </tr>
 <tr>
  <td align='right'>
  <div class='sec_menu' style='width:165'>
  <table cellpadding='0' cellspacing='0' align='center' width='130'>
   <tr>
    <td height='20'><br />
    版权所有： <a href='http://www.chinaedustar.com/' target='_blank'>中教育星教育软件有限公司</a>
    <br />
    <br />
    </td>
   </tr>
  </table>
  </div>
  </td>
 </tr>
</table>
</pub:template>

<script language="javascript">
<!--
function js_toggle(menu_id) {
  var div = document.all(menu_id);
  if (div == null) return;
  div.style.display = (div.style.display == '') ? 'none' : '';
}
// -->
</script>

<%-- 执行模板 --%>
<pub:process_template name="main" />

</body>
</html>
