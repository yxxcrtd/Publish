<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.AuthorManage"
%><%
  // 初始化页面数据。
  AuthorManage admin_data = new AuthorManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>作者管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 删除选中的作者。
function deleteAuthors() {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选择任何作者。");
		return;
	}
	if (confirm("确定要删除选中的作者吗？")) {
		document.actionForm.authorIds.value = ids;
		document.actionForm.submit();
	}
}
// -->
</script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<pub:declare>

<%@include file="element_keyword.jsp" %>

<pub:template name="main">
 #{call author_navigator("作者管理") }<br />
 #{call templ_channels("admin_author_list.jsp", "作者") }<br />
 #{call author_type_nav}<br />
 #{call templ_authors }
 #{call templ_actions }
 #{call keyword_pagination_bar(page_info) } <br/>
 #{call templ_searchBar }
</pub:template>


<pub:template name="author_type_nav">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
 <td height='22'>
 | <a href='admin_author_list.jsp?channelId=#{channel.id}&authorType=0'>所有作者</a>
 | <a href='admin_author_list.jsp?channelId=#{channel.id}&authorType=1'>大陆作者</a>
 | <a href='admin_author_list.jsp?channelId=#{channel.id}&authorType=2'>港台作者</a>
 | <a href='admin_author_list.jsp?channelId=#{channel.id}&authorType=3'>海外作者</a>
 | <a href='admin_author_list.jsp?channelId=#{channel.id}&authorType=4'>本站特约</a>
 | <a href='admin_author_list.jsp?channelId=#{channel.id}&authorType=5'>其他作者</a>
 |</td>
 </tr>
</table>
</pub:template>



<%-- 作者列表的模板。 --%>
<pub:template name="templ_authors">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr align='center' class='title' height='22'>
 <td width='30'><strong>选中</strong></td>
 <td width='40'><strong>序号</strong></td>
 <td width='80'><strong>姓名</strong></td>
 <td width='40' height='22'><strong>性别</strong></td>
 <td height='22'><strong>简介</strong></td>
 <td width='80' height='22'><strong>作者分类</strong></td>
 <td width='60' height='22'><strong>状态</strong></td>
 <td width='150' height='22'><strong>操 作</strong></td>
</tr>
#{foreach author in author_list }
<tr align='center' class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td><input name='itemId' type='checkbox' value='#{author.id}' /></td>
  <td>#{author.id }</td>
  <td>#{author.name@html}</td>
  <td>#{if (author.sex==1)}男#{elseif(author.sex==0)}女#{else}未知#{/if}</td>
  <td>#{author.description@html}</td>
  <td>
  #{if (author.authorType == 1) }大陆作者
  #{elseif (author.authorType == 2) }港台作者
  #{elseif (author.authorType == 3) }海外作者
  #{elseif (author.authorType == 4) }本站特约
  #{elseif (author.authorType == 5) }其他作者
  #{else }未知作者类型
  #{/if }
  </td> 
  <td align="center">
  #{if (author.passed) }
   <font color="green">√</font>
  #{else }
   <font color="red">×</font>
  #{/if }
  #{if author.top}<font color="blue"> 固 </font>#{/if}
  #{if author.commend}<font color="green"> 荐 </font>#{/if}
  </td>
  <td>
  <a href='admin_author_add.jsp?channelId=#{author.channelId }&authorId=#{author.id }'>修改</a>&nbsp;
  #{if (author.passed) }
   <a href='admin_author_action.jsp?command=status&channelId=#{channel.id }&authorId=#{author.id }&field=passed&status=false'>禁用</a>&nbsp;
  #{else }
   <a href='admin_author_action.jsp?command=status&channelId=#{channel.id }&authorId=#{author.id }&field=passed&status=true'>启用</a>&nbsp;
  #{/if }
  #{if (author.top) }
   <a href='admin_author_action.jsp?command=status&channelId=#{channel.id }&authorId=#{author.id }&field=top&status=false'>解固</a>&nbsp;
  #{else }
   <a href='admin_author_action.jsp?command=status&channelId=#{channel.id }&authorId=#{author.id }&field=top&status=true'>固顶</a>&nbsp;  
  #{/if }
  #{if (author.commend) }
   <a href='admin_author_action.jsp?command=status&channelId=#{channel.id }&authorId=#{author.id }&field=commend&status=false'>解荐</a>&nbsp;
  #{else }
   <a href='admin_author_action.jsp?command=status&channelId=#{channel.id }&authorId=#{author.id }&field=commend&status=true'>推荐</a>&nbsp;
  #{/if }
  <a href='admin_author_action.jsp?command=delete&channelId=#{author.channelId }&authorIds=#{author.id}'
    onclick="return confirm('确定要删除作者#{author.name }吗？');">删除</a>
  </td>
 </tr>
#{/foreach }
</table>
</pub:template>


<!-- 作者操作的模板 -->
<pub:template name="templ_actions">
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td width='200' height='30'><input name='chkAll' type='checkbox'
   id='chkAll' onclick='CheckAll(this)' value='checkbox'>
  选中本页显示的所有作者</td>
  <td>
  <td>
  <input type='button' value=' 添加作者 ' onclick="location.href='admin_author_add.jsp?channelId=#{channel.id}'" />
  <input type='button' value='删除选中的作者' onclick="deleteAuthors()" />
  </td>
 </tr>
</table>
<form name="actionForm" action="admin_author_action.jsp" method="post" style="display: none">
 <input type='hidden' name='command' value='delete' />
 <input type="hidden" name="authorIds" />
 <input type="hidden" name="channelId" value="#{channel.id}" />
</form>
</pub:template>


<!-- 搜索栏的模板 -->
<pub:template name="templ_searchBar">
<form method='get' name='SearchForm' action=''>
<table width='100%' border='0' cellpadding='0' cellspacing='0' class='border'>
 <tr class='tdbg'>
  <td width='80' align='right'><strong>作者搜索：</strong></td>
  <td>
    <input name='channelId' type='hidden' id='channelId' value='1'>
    <select name='field' size='1'>
     <option value='name' selected>作者名</option>
     <option value='address'>作者地址</option>
     <option value='phone'>作者电话</option>
     <option value='description'>作者简介</option>
    </select>
    <input type='text' name='keyword' size='20' value='关键字' maxlength='50' onmouseover="this.focus();this.select();" />
    <input type='submit' value='搜索' />
  </td>
 </tr>
</table>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
