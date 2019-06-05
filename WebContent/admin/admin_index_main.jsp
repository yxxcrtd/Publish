<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.admin.IndexManage"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import ="java.net.InetAddress" 
%><%
  // 得到管理员信息，如果未登录则回到登录页面。
  IndexManage admin_data = new IndexManage(pageContext);
  if (admin_data.isAdminLogined() == false) {
	 out.println("<script>window.location = 'admin_login.jsp';</script>");
    return;
  }
  
  // 初始化此页面所需的数据。
  admin_data.initMainPageData();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<pub:template name="main">
<html>
<head>
 <title>#{site.name}--后台管理首页</title>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	
 #{call main_guide_top }
 #{call main_guide_admin }
 #{call main_guide_item }
 #{call main_guide_server_info }
	
<table cellpadding="2" cellspacing="1" border="0" width="100%" class="border" align=center>
  <tr align="center">
    <td height=25 class="topbg"><span class="Glow">#{site.copyright }</span>
  </tr>
</table>
</body>
</html>
</pub:template>


<pub:template name="main_guide_top">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="392" rowspan="2b"><img src="images/adminmainLeft.gif" width="392" height="126"></td>
    <td height="114" valign="top" background="images/adminmainBg.gif">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="20"></td>
	      </tr>
	      <tr>
	        <td><span class="STYLE4">帮助公告</span></td>
	      </tr>
	      <tr>
	        <td height="8"><img src="images/adminmain0line.gif" width="283" height="1" /></td>
	      </tr>
	      <tr>
	        <td>
	        <div id="peinfo1" style='display:none'>正在读取数据中...</div>
	        <div id="peinfo2" style="z-index: 1; visibility: hidden; position: absolute"></div></td>
	      </tr>
	    </table>
    </td>
  </tr>  
</table>
</pub:template>

<pub:template name="main_guide_admin">
<table width="100%" height="10"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <table width="100%" border="0" cellpadding="3" cellspacing="0">      
      <tr>
        <td width="31%" height="87" style="padding-left:22px">
	        <table border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td>#{admin.adminName },您好， </td>
	            </tr>
	            <tr>
	              <td valign="top">今天是
	                <script type="text/JavaScript" src="../js/date.js"></script>
	                您尚有：</td>
	            </tr>
	            <tr>
	              <td valign="top">
	              #{foreach channel in channel_list }
	              <img src='images/img_u.gif' align='absmiddle'/>待审#{channel.itemName }：<font color=red>#{channel.notPassedItemCount }</font>#{channel.itemUnit }&nbsp;
                 #{if ((channel@index+1)%2) == 0}<br />#{/if}
	              #{/foreach }
                </td>
	            </tr>
	        </table>
        </td>
        <td width="1%">
	        <table width="3" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="3" height="65" bgcolor="#1890CC"></td>
	            </tr>
	        </table>
        </td>
        <td width="68%">欢迎您进入网站后台管理系统！在这里您可以利用系统提供的强大的HTML生成功能，便捷的后台管理功能，栏目无限级分类，任意添加网站频道功能，栏目批量设置、批量移动等功能有效地管理网站。您可以随时使用顶部的<font color="#FF0000">关闭左栏</font>功能关闭或打开左边的管理导航，以扩展操作界面。初次架设网站请配置以下信息：</td>
      </tr>
      <tr>
        <td height="5" colspan="3"></td>
        </tr>
    </table></td>
  </tr>
</table>
</pub:template>

<pub:template name="main_guide_item">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="400" align="center" class="topbg"><span class="Glow">建 站 管 理 快 捷 入 口</span></td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="400" align="center" class="topbg"><span class="Glow">日 常 管 理 快 捷 入 口</span></td>
    <td width="21" rowspan="2">&nbsp;</td>
  </tr>
</table>
<br/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class='Class' href="admin_site.jsp">网站信息配置</a></td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_column_list.jsp?channelId=1" target="main">网站栏目管理</a></td>
    <td width="300">&nbsp;</td>
    <td width="21" rowspan="2">&nbsp;</td>
  </tr>
  <tr class="topbg2">
    <td height="1" colspan="2"></td>
    <td colspan="2"></td>
  </tr>
</table>
<br/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20">&nbsp;</td>
    <td width="400">　　网站基本信息配置：如网站的名称、地址、LOGO、版权信息等；网站功能选项配置：如显示频道、保存远程图片等；用户选项配置：如是否允许新用户注册、是否需要认证等；另有邮件服务器选项等。<br>
      　　快捷菜单：<a href="admin_site.jsp" target="main"><u><font color="#FF0000">网站信息配置</font></u></a>
      | <a href="admin_site.jsp#siteoption" target="main"><u><font color="#FF0000">网站选项配置</font></u></a>
    | <a href="admin_site.jsp#user" target="main"><u><font color="#FF0000">用户选项</font></u></a></td>
    <td width="40">&nbsp;</td>
    <td width="400">　　管理网站各频道中所设置的各级栏目，栏目具有无级分类功能。可对栏目进行添加、删除、排序、复位、合并和批量设置等管理。<br>
      　　<font color="#0000FF">初次安装请在各频道中先添加栏目</font>。<br>
      　　快捷菜单：| 
      #{foreach channel in channel_list }
      	<a href="admin_column_list.jsp?channelId=#{channel.id }" target="main"><u><font color="#FF0000">#{channel.itemName }栏目管理</font></u></a> | 
      #{/foreach }
      </td>
    <td width="21">&nbsp;</td>
  </tr>
</table>
<br/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class='Class' href="#" target="main">首页生成管理</a></td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_article_add.jsp?channelId=1" target="main">网站内容添加</a></td>
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
    <td width="400">　　首次安装系统请<a href="#" target="main"><font color="#FF0000">生成网站首页</font></a>。首页、栏目页、内容页、专题页……都可以生成完全的HTML页面（评论和点击数统计除外）。各频道启用生成功能请在<a href=admin_channel_list.jsp target=main title="中教育星发布系统中，频道是指某一功能模板的集合。某一频道可以是具备文章系统功能，或具备下载系统、图片系统的功能。"><U><font color="#FF0000">网站频道管理</font></U></a>中设置。</td>
    <td width="40">&nbsp;</td>
    <td width="400">　　系统提供强大的<a href="#" title="在线编辑器能够在网页上实现许多桌面编辑软件（如：Word）所具有的强大可视编辑内容的功能。"><u>在线编辑器</u></a>（文章中心），增加、删除、修改网站各个频道下各栏目的相关内容（文字、软件、图片等），方便设置内容的相关属性等。<br>
      　　快捷菜单： | 
      #{foreach channel in channel_list }
      <a href="admin_article_add.jsp?channelId=#{channel.id }" target="main"><u><font color="#FF0000">添加#{channel.itemName }</font></u>
      </a><a href="admin_article_list.jsp?channelId=#{channel.id }"><u><font color="#FF0000">管理</font></u></a> | 
      #{/foreach }
    </td>
    <td width="21">&nbsp;</td>
  </tr>
</table>
<br/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_admin_list.jsp" target="main">管理员管理</a></td>
    <td width="300">&nbsp;</td>
    <td width="40" rowspan="2">&nbsp;</td>
    <td width="100" align="center" class="topbg"><a class="Class" href="admin_channel_list.jsp" target="main" title="网站管理系统中，频道是指某一功能模板的集合。某一频道可以是具备文章系统功能，或具备下载系统、图片系统的功能。">网站频道管理</a></td>
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
    <td width="400">　　强大的<a href="admin_admin_list.jsp" target="main"><U><font color="#FF0000">网站权限管理</font></U></a>，可增删管理员和指定详细的管理权限，使网站的管理分级分类多人共同管理。设置网站添加<a href="admin_admin_add.jsp" target="main" title="超级管理员：拥有所有权限。某些权限（如管理员管理、网站信息配置、网站选项配置等管理权限）只有超级管理员才有。"><U>超级管理员</U></a>和<a href="admin_admin_add.jsp" target="main" title="普通管理员：捅有指定部分网站管理功能，需要详细指定每一项管理权限。"><U>普通管理员</U></a>，您也可以自由设定<a href="#" target="main" title="用户组是用户账户的集合，通过创建用户组，赋予相关用户享有授予组的权力和权限。具体的权限设置在“频道管理”及各频道的“栏目管理”中。"><font color="#FF0000"><U>用户组</U></font></a>以管理注册用户级别。</td>
    <td width="40">&nbsp;</td>
    <td width="20">&nbsp;</td>
    <td width="400">　　<a href="admin_channel_list.jsp" target="main">管理网站的各个频道的功能模块，如文章、下载、图片和留言等频道。</a>频道可分为<a href="#" title="系统内部频道指的是在MY动力现有功能模块（新闻、文章、图片等）基础上添加新的频道，新频道具备和所使用功能模块完全相同的功能。"><U>系统内部频道</U></a>与<a href="#" title="外部频道指链接到MY动力系统以外的地址中。当此频道准备链接到网站中的其他系统时"><U>外部频道</U></a>。频道类型请慎重选择，频道一旦添加后就不能再更改频道类型。</td>
    <td width="40">&nbsp;</td>
  </tr>
</table>
<br/>
</pub:template>

<pub:template name="main_guide_server_info">
<table cellpadding="2" cellspacing="1" border="0" width="100%" class="border" align=center>
  <tr align="center">
    <td height=25 colspan=2 class="topbg"><span class="Glow">服 务 器 信 息</span><tr>
    <td width="50%"  class="tdbg" height=23>服务器IP:<%=InetAddress.getLocalHost().toString()%>:<%=request.getServerPort()%></td>
    <td width="50%" class="tdbg">站点物理路径：      <%=request.getRealPath("/") %>
    </td>
  </tr>
</table>
<br/>
</pub:template>

<pub:process_template name="main"/>

