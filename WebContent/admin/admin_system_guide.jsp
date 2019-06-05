<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%
  // TODO: 提供页面的 site, channel, admin 数据.
%>

<pub:template name="main">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <title>#{site.name }--系统设置中心</title>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
 
 #{call channel_guide_top }
 #{call channel_guide_item }
 
 <table cellpadding="2" cellspacing="1" border="0" width="100%" class="border" align=center>
   <tr align="center">
     <td height=25 class="topbg"><span class="Glow">#{site.copyright }</span>
   </tr>
 </table>
</body>
</html>
</pub:template>

<pub:template name="channel_guide_top">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="392" rowspan="2"><img src="images/adminmainLeft.gif" width="392" height="126"></td>
    <td height="114" valign="top" background="images/adminmainBg.gif">
     <table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td height="20"></td>
       </tr>
       <tr>
         <td>#{admin.adminName }您好，今天是<script type="text/JavaScript" src="../js/date.js"></script></td>
       </tr>
       <tr>
         <td height="8"><img src="Images/adminmain0line.gif" width="283" height="1" /></td>
       </tr>
       <tr>
         <td><img src="images/img_u.gif" align="absmiddle">您现在进行的是<font color="#FF0000">系统设置管理</font></td>
       </tr>
     </table>
    </td>
  </tr>  
</table>
<br/>
</pub:template>

<pub:template name="channel_guide_item">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class='Class' href="admin_site.jsp">网站信息配置</a></td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_template_list.jsp" target="main">首页模板管理</a></td>
    <td width="300">&nbsp;</td>
    <td width="21" rowspan="2">&nbsp;</td>
  </tr>
  <tr class="topbg2">
    <td height="1" colspan="2"></td>
    <td colspan="2"></td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20">&nbsp;</td>
    <td width="20">&nbsp;</td>
    <td width="400">　　网站基本信息配置：如网站的名称、地址、LOGO、版权信息等；网站功能选项配置：如显示频道、保存远程图片等；用户选项配置：如是否允许新用户注册、是否需要认证等；另有邮件服务器选项等。<br>
      　　快捷菜单：<a href="admin_site.jsp" target="main"><u><font color="#FF0000">网站信息配置</font></u></a>
      | <a href="admin_site.jsp#siteoption" target="main"><u><font color="#FF0000">网站选项配置</font></u></a>
    | <a href="admin_site.jsp#user" target="main"><u><font color="#FF0000">用户选项</font></u></a></td>
    <td width="40">&nbsp;</td>
    <td width="400">　　第一次安装系统请<font color="#FF0000">生成网站首页</font>。首页、栏目页、内容页、专题页……都可以生成完全的HTML页面（评论和点击数统计除外）。各频道启用生成功能请在
    <a href=admin_channel_list.jsp" target=main title="中教育星发布系统中，频道是指某一功能模板的集合。某一频道可以是具备文章系统功能，或具备下载系统、图片系统的功能。"><U>网站频道管理</U></a>中设置。<br>
      　　快捷菜单：<a href="admin_template_list.jsp"><font color="#FF0000"><u>管理网站首页模板</u></font></a> | 
      <a href="#"><font color="#000000"><u>生成网站首页</u></font></a>。</td>
    <td width="21">&nbsp;</td>
  </tr>
</table>
<br/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_channel_list.jsp" target="main">网站频道管理</a></td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_skin_list.jsp" target="main">网站风格管理</a></td>
    <td width="300">&nbsp;</td>
    <td width="21" rowspan="2">&nbsp;</td>
  </tr>
  <tr class="topbg2">
    <td height="1" colspan="2"></td>
    <td colspan="2"></td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20">&nbsp;</td>
    <td width="400">　　管理网站的各个频道的功能模块，如文章、下载、图片和留言等频道。频道可分为
    <a href="#" title="系统内部频道指的是在现有功能模块（新闻、文章、图片等）基础上添加新的频道，新频道具备和所使用功能模块完全相同的功能。"><U>系统内部频道</U></a>与
    <a href="#" title="外部频道指链接到系统以外的地址中。当此频道准备链接到网站中的其他系统时"><U>外部频道</U></a>二类。
    系统的一些重要功能，如生成HTML功能、频道的审核功能、上传文件类型、顶部导航栏每行显示的栏目数、底部栏目导航的显示方式、都在此进行设置。<br>
    　　快捷菜单：<A href="admin_channel_add.jsp" target=main><font color="#FF0000"><u>添加网站频道</u></font></A> | 
    <A href="admin_channel_list.jsp" target=main><font color="#FF0000"><u>管理网站频道</u></font></A>。</td>
    <td width="40">&nbsp;</td>
    <td width="400">　　风格模板是控制整个网站在前台显示时看到的的字体、风格、图片等，通常是用css网页样式语句来进行设计和控制的。
    利用网页技术中的层叠样式表(CSS)样式来定义特定的HTML标签以按照特定方式设置文本格式。系统具有自定义CSS样式的功能，并随时可以修改样式。<br>
      　　快捷菜单：<A href="admin_skin_add.jsp" target=main><font color="#FF0000"><u>添加网站风格</u></font></A> | 
      <A href="admin_skin_list.jsp" target=main><font color="#FF0000"><u>管理网站风格</u></font></A>。</td>
    <td width="21">&nbsp;</td>
  </tr>
</table>
<br/>
</pub:template>

<pub:process_template name="main"/>

