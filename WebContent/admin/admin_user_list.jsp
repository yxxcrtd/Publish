<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.UserManage"
%><%
  // 初始化管理数据。
  UserManage admin_data = new UserManage(pageContext);
  admin_data.initListPage();
 
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>会员管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src='admin_user.js'></script>
</head>
<body>

<pub:declare>

<%@include file="element_keyword.jsp" %>
<%@include file="element_user.jsp" %>

<pub:template name="main">
 #{call user_manage_navigator }<br />
 #{call tmpl_body }<br />
 #{call keyword_pagination_bar(page_info) }<br />
 #{call searchBar }
</pub:template>

<pub:template name="tmpl_body">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
  <td height="22">
   您现在的位置： <a href="admin_user_list.jsp">会员管理</a> &gt;&gt; 
   #{if (searchType == 0) }
   #{iif (keyword != null, "用户名中含有“" + keyword + "”的会员", "所有会员") }
   #{elseif (searchType == 1) }
   添加信息最多的前100个会员
   #{elseif (searchType == 2) }
   添加信息最少的前100个会员
   #{elseif (searchType == 3) }
   最近24小时内登录的会员
   #{elseif (searchType == 4) }
   最近24小时内注册的会员
   #{elseif (searchType == 5) }
   所有禁用的会员
   #{elseif (searchType == 6) }
   所有启用的会员
   #{/if }
    </td>
  </tr>
 </table>
 
<table class="border" border="0" cellspacing="1" width="100%" cellpadding="0">
<tr align="center" class="title" height="22">
 <td height="22" width="30" align="center"><strong>选中</strong></td>
 <td width="25" align="center"><strong>ID</strong></td>
 <td align="center"><strong>用户名</strong></td>
 <td width="100" align="center"><strong>注册时间</strong></td>
 <td width="100" align="center"><strong>最后登录IP</strong></td>
 <td width="100" align="center"><strong>最后登录时间</strong></td>
 <td width="40" align="center"><strong>状态</strong></td>
 <td width="40" align="center"><strong>投稿</strong></td>
 <td width="150" align="center"><strong>常规管理操作</strong></td>
</tr>
#{foreach user in user_list }
<tr align="center" class="tdbg" onmouseout="this.className='tdbg'"
 onmouseover="this.className='tdbgmouseover'">
 <td><input type="checkbox" name="itemId" value="#{user.id }" /></td>
 <td>#{user.id }</td>
 <td>#{user.userName }</td>
 <td width="100">#{user.registryTime@format }</td>
 <td>#{user.lastLoginIP }</td>
 <td width="100">#{user.lastLoginTime@format }</td>
 <td>
  #{if user.status == 0 }启用
  #{else }<font color='red'>禁用</font>
  #{/if }
 </td>
 <td>
  #{if user.inputer }允许
  #{else }<font color='red'>拒绝</font>
  #{/if }
 </td>
 <td>
  <a href="admin_user_add.jsp?userId=#{user.id }">修改</a>
  #{if user.status == 0 }
   <a href="admin_user_action.jsp?command=disable&userId=#{user.id }">禁用</a>
  #{else }
   <a href="admin_user_action.jsp?command=enable&userId=#{user.id }">启用</a>
  #{/if }
  #{if user.inputer}
   <a href="admin_user_action.jsp?command=uninput&userId=#{user.id }">禁止投稿</a>
  #{else }
   <a href="admin_user_action.jsp?command=input&userId=#{user.id }">允许投稿</a>
  #{/if }
  <a href="admin_user_action.jsp?command=delete&userId=#{user.id }" onclick="return confirm('确定要删除该会员吗？');">删除</a>
 </td>
</tr>
#{/foreach }
</table>
   
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
 <td width="200" height="30">
  <input name="chkAll" type="checkbox" id="chkAll" onclick="CheckAll(this)" value="checkbox" />
   选中本页显示的所有会员</td>
 <td>
 <input type="button" value=" 批量删除 " onclick="deleteUsers()" style='cursor:hand;'/> 
 <input type="button" value=" 批量启用 " onclick="doStatus(0)" style='cursor:hand;'/> 
 <input type="button" value=" 批量禁用 " onclick="doStatus(1)" style='cursor:hand;'/> 
 <input type="button" value=" 允许投稿 " onclick="doInputer(true)" style='cursor:hand;'/> 
 <input type="button" value=" 拒绝投稿 " onclick="doInputer(false)" style='cursor:hand;'/> 
 </td>
</tr>
</table>
 
<form name="actionForm" action="admin_user_action.jsp" method="post">
 <input type="hidden" name="userIds" value="" />
 <input type="hidden" name="command" value="" />
</form>
</pub:template>

<pub:template name="searchBar">
<form name="searchForm" action="admin_user_list.jsp" method="post">
<table class="border" border="0" cellspacing="1" width="100%" cellpadding="0">
 <tr class='tdbg'>
  <td>
  会员查询：<input type="hidden" name="field" value="userName" />
  <input type="text" name="keyword" value="输入用户名" onmouseover="this.focus();this.select();"/>
  <input type="submit" value="搜 索" />
  </td>
 </tr>
</table>
</form>
</pub:template>

</pub:declare>
 
<pub:process_template name="main" />

</body>
</html>