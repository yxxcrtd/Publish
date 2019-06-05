<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"%>

<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%@page import="com.chinaedustar.publish.admin.WebPageManage" %>

<%
  WebPageManage admin_data = new WebPageManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>网站自定义页面管理</title>
 <script type="text/javascript" src="main.js"></script>
</head>
<body>

<pub:declare>
<%-- === 主模板定义 ======================= --%>
<pub:template name="main">
#{call webpage_manage_navigator } <br/>
#{call position_nav } <br/>
#{call webpage_list_show }
#{call form_buttons }
<br /><br /><br /><br />
</pub:template>


<pub:template name="form_buttons">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1'>
 <tr>
  <td>
<input name="chkAll" type="checkbox" id="chkAll" onclick="CheckAll(this)"
  value="checkbox" /> 选中所有自定义页面
  </td>
  <td>
<input type='button' value='生成所有选中的页面' />
<input type='button' value='生成所有页面' />
  </td>
 </tr>
</table>
</pub:template>

<%-- === 导航部分 ======================== --%>
<pub:template name="webpage_manage_navigator">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
   <td height='22' colspan='10'>
   <table width='100%'>
    <tr class='topbg'>
     <td align='center'><b>自定义页面管理</b></td>
     <td width='60' align='right'>
      <a href='help/index.jsp' target='_blank'>
     <img src='images/help.gif' border='0' /> </a></td>
    </tr>
   </table>
   </td>
  </tr>
  <tr class='tdbg'>
   <td width='70' height='30'><strong>管理导航：</strong></td>
   <td height='30'>
    <a href='admin_webpage.jsp?channelId=#{container.containerId}'>自定义页面管理首页</a> |
    <a href='admin_webpage_add.jsp?channelId=#{container.containerId}&parentId=0'>添加自定义页面</a>
   </td>
  </tr>
 </table>
</pub:template>

<%-- === 现在的位置 ====================== --%>
<pub:template name="position_nav">
<table width='100%'>
 <tr>
  <td align='left'>您现在的位置：自定义页面管理&nbsp;&gt;&gt;&nbsp;首页</td>
 </tr>
</table>
</pub:template>

<%--  === 自定义页面列表 ================== --%>
<pub:template name="webpage_list_show">
<table width='100%' border='0' align='center' cellpadding='0' cellspacing='1' class='border'>
 <tr class='title' height='22'>
  <td width='40' align='center'><strong>ID</strong></td>
  <td align='center'><strong>页面名称及目录</strong></td>
  <td width="40" align="center"><strong>已生成</strong></td>
  <td width='380' align='center'><strong>操作选项</strong></td>
 </tr>
#{foreach webpage in webpage_list }
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td align='center'>
   <input type='checkbox' name='itemId' value='#{webpage.id}' />#{webpage.id}
  </td>
  <td>
   #{foreach ch in webpage.treeFlag }
    #{if ch == 'T'}
     <img src='images/tree_line1.gif' valign='absmiddel' border='0' />#{elseif ch == 'L' }
     <img src='images/tree_line2.gif' valign='absmiddel' border='0' />#{elseif ch == '|' }
     <img src='images/tree_line3.gif' valign='absmiddel' border='0' />#{elseif ch == 'B' }
     <img src='images/tree_line4.gif' valign='absmiddel' border='0' />#{elseif ch == '+' }
     <img src='images/tree_folder4.gif' valign='absmiddel' border='0' />#{elseif ch == '-' }
     <img src='images/tree_folder3.gif' valign='absmiddel' border='0' />#{/if }
   #{/foreach }
   #{webpage.title}(#{webpage.name })</td>
  <td align='center'>
   #{if webpage.isGenerated}<font color='blue'><b>√</b></font>
   #{else}<font color='red'><b>×</b></font>#{/if}
  </td>
  <td align='center'>
   <a href='admin_webpage_add.jsp?channelId=#{container.containerId}&parentId=#{webpage.id}'>添加子页面</a> |
   <a href='admin_webpage_add.jsp?webpageId=#{webpage.id}'>修改</a> |
   <a href='admin_webpage_action.jsp?command=delete&webpageId=#{webpage.id}' onclick='return confirm("您确定要删除这个自定义页面吗？")'>删除</a> |
   <a href='../showWebpage.jsp?id=#{webpage.id}' target='_blank'>预览</a> |
   <a href='admin_webpage_action.jsp?command=generate&webpageId=#{webpage.id}'>生成</a>
  </td>
 </tr>
#{/foreach }
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
