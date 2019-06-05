<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ChannelManage"
%><%
  ChannelManage manager = new ChannelManage(pageContext);
  manager.initEditPage();
/*
  <!-- 得到网站信息。 -->
  <pub:data var="site"
    provider="com.chinaedustar.publish.admin.SiteInfoDataProvider" />

  <!-- 得到当前登录的管理员用户。也可以使用 在模板中也可以使用 #{ADMIN_USERNAME }得到登录的管理员对象，不建议使用。 -->
  <pub:data var="admin" param="login"
    provider="com.chinaedustar.publish.admin.AdminDataProvider" />
    
  <!-- 得到当前的频道对象。 -->
  <pub:data var="channel" param="login"
    provider="com.chinaedustar.publish.admin.ChannelDataProvider" />
*/  
%>

<pub:template name="main">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>#{site.name }--频道管理中心</title>
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
	        <td><span class="STYLE4">频道管理中心</span></td>
	      </tr>
	      <tr>
	        <td height="8"><img src="Images/adminmain0line.gif" width="283" height="1" /></td>
	      </tr>
	      <tr>
	        <td><img src="images/img_u.gif" align="absmiddle">您现在进行的是<font color="#FF0000">#{channel.name }管理</font></td>
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
    <td width="100" align="center" class="topbg">
      <a class="Class" href="admin_article_add.jsp?channelId=#{channel.id }" target="main">添加内容</a>
    </td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg">
      <a class='Class' href="admin_template_list.jsp?channelId=#{channnel.id }" target="main">模板管理</a>
    </td>
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
    <td width="400">　　您可以使用系统提供的强大<a href="#" title="在线编辑器能够在网页上实现许多桌面编辑软件（如：Word）所具有的强大可视编辑内容的功能。">
    <u>在线编辑器</u>
    </a>添加网站内容，并能选择简洁模式和高级模式；高级模式能进行更多高级的设置，如：
    <FONT color=#ff0000><a href="#" title="将内容链接系统以外的地址中。当此标题准备链接到其他网站中的内容时，请使用这种方式。">
    <u>转向链接</u></a></FONT>、
    <u>副标题</u>、
    <a href="#" title="设置内容的阅读等级，只有具有相应权限的人才能阅读此内容"><u>阅读等级</u></a>、
    <a href="#" title="设置用户在阅读此内容时将消耗相应点数。（对游客和管理员无效）"><u>阅读点数</u></a>、
    <a href="#" title="可设置固顶、热点、推荐等内容的属性"><u>内容属性</u></a>、
    <a href="#" title="包括配色风格（相关模板中包含CSS、颜色、图片等信息）和版面设计模板（相关模板中包含了版面设计的版式等信息）">
    <u>选择模板</u></a>等。<br>
      #{if channel.itemClass == 'Article' }
    　　 快捷菜单：<a href="admin_article_add.jsp?channelId=#{channel.id }" target=main><font color="#FF0000">
        <u>添加#{channel.itemName }</u></font></a>
      #{elseif channel.itemClass == 'Photo' }
    　　 快捷菜单：<a href="admin_photo_add.jsp?channelId=#{channel.id }" target=main><font color="#FF0000">
        <u>添加#{channel.itemName }</u></font></a>
      #{elseif channel.itemClass == 'Soft' }
    　　 快捷菜单：<a href="admin_soft_add.jsp?channelId=#{channel.id }" target=main><font color="#FF0000">
        <u>添加#{channel.itemName }</u></font></a>
      #{/if }
      
    </td>
    <td width="40">&nbsp;</td>
    <td width="400">　　系统提供版式模板管理功能，用来显示前台时所看到的网页的界面布局形式，如分栏、表格布局、图片和文字要显示的位置等等。几乎所有前台显示的页面格式都在此修改与设置。<br>
    　　快捷菜单：<a href="admin_template_list.jsp?channelId=#{channel.id }" target=main><u><font color="#FF0000">管理模板</font></u></a> | 
    <a href="admin_template_add.jsp?channelId=#{channel.id }"><u><font color="#FF0000">添加模板</font></u></a> | 
    <!-- <a href="#"><u><font color="#FF0000">导入模板</font></u></a> | 
    <a href="#"><u><font color="#FF0000">导出模板</font></u></a></td> -->
    <td width="21">&nbsp;</td>
  </tr>
</table>
<br/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_article_list.jsp?channelId=#{channel.id }" target="main">管理内容</a></td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a  class='Class' href="admin_column_list.jsp?channelId=#{channel.id }" target="main">管理栏目</a></td>
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
    <td width="400">　　对添加的内容提供便捷的管理。相应权限管理员可以审核注册用户发表的内容，修改、移动、删除已经发表的内容，可批量修改内容的属性，也可将指定的内容批量移动到另一栏目中。<br>
      　　快捷菜单：
     #{if channel.itemClass == 'Article' }
      <a href="admin_article_list.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>管理#{channel.itemName }</u></font></a> | 
      <a href="admin_article_approv.jsp?channelId=#{channel.id }&status=0" target=main><font color="#FF0000"><u>审核#{channel.itemName }</u></font></a> | 
      <a href="admin_article_my.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>我添加的#{channel.itemName }</u></font></a>
     #{elseif channel.itemClass == 'Photo'}
      <a href="admin_photo_list.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>管理#{channel.itemName }</u></font></a> | 
      <a href="admin_photo_approv.jsp?channelId=#{channel.id }&status=0" target=main><font color="#FF0000"><u>审核#{channel.itemName }</u></font></a> | 
      <a href="admin_photo_my.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>我添加的#{channel.itemName }</u></font></a>
     #{elseif channel.itemClass == 'Soft'}
      <a href="admin_soft_list.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>管理#{channel.itemName }</u></font></a> | 
      <a href="admin_soft_approv.jsp?channelId=#{channel.id }&status=0" target=main><font color="#FF0000"><u>审核#{channel.itemName }</u></font></a> | 
      <a href="admin_soft_my.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>我添加的#{channel.itemName }</u></font></a>
     #{/if }
    </td>
    <td width="20">&nbsp;</td>
    <td width="400">　　管理本频道中设置的各级栏目，栏目具有无级分类功能。可添加、删除、排序、移动、复位、合并和批量设置栏目等。<br>
      　　快捷菜单：<a href="admin_column_list.jsp?channelId=#{channel.id }" target="main"><u><font color="#FF0000">栏目管理</font></u></a> | 
      <a href="admin_column_add.jsp?channelId=#{channel.id }" target="main"><u><font color="#FF0000">添加栏目</font></u></a> | 
    </td>
    <td width="40">&nbsp;</td>   
  </tr>
</table>
<br/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg">专题与评论</td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg">上传与回收站</td>
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
    <td width="400">　　如果不同栏目中的内容属于同一主题，则可以建立相应专题，以便浏览与管理。专题栏目可以进行修改、删除和清空等操作。<br>
      　　用户可以对网站的内容发表相关评论，管理员可以回复、修改、删除和审核评论。<br>
      　　快捷菜单：
     #{if channel.itemClass == 'Article' }
      <a href="admin_special_article_list.jsp?channelId=#{channel.id }" target="main"><u><font color="#FF0000">专题#{channel.itemName }管理</font></u></a> | 
     #{elseif channel.itemClass == 'Photo' }
      <a href="admin_special_photo_list.jsp?channelId=#{channel.id }" target="main"><u><font color="#FF0000">专题#{channel.itemName }管理</font></u></a> | 
     #{elseif channel.itemClass == 'Soft' }
      <a href="admin_special_soft_list.jsp?channelId=#{channel.id }" target="main"><u><font color="#FF0000">专题#{channel.itemName }管理</font></u></a> | 
     #{/if }
      <a href="admin_special_list.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>专栏管理</u></font></a> | 
      <a href="admin_comment_list.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>评论管理</u></font></a></td>
    <td width="40">&nbsp;</td>
    <td width="400"> 　　系统具有<a href="#" title="指不需要组件支持就可以上传指定文件类型的功能。"><u>无组件上传</u></a>功能，可上传频道中设定的文件类型。对无用的上传文件，可使用清除无用文件功能定期进行清理。<br>
      　　注册用户和管理员删除无用的内容时，先删除至回收站，以防止误操作。回收站内的内容可随时恢复或清除。<br>
快捷菜单:
    
    <a href="admin_upload_list.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>上传文件管理</u></font></a> | 
    <a href="admin_upload_clear.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>清理无用上传文件</u></font></a> | 
    <a href="admin_recycle_article_list.jsp?channelId=#{channel.id }" target=main><font color="#FF0000"><u>回收站管理</u></font></a></td>
    <td width="21">&nbsp;</td>
  </tr>
</table>
<br/>
</pub:template>

<pub:process_template name="main"/>
