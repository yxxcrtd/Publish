<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.AuthorManage"
%><%
  // 初始化页面数据.
  AuthorManage manage = new AuthorManage(pageContext);
  manage.initChoosePage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<pub:declare>
<%@include file="element_keyword.jsp" %>

<pub:template name="main">
<html>
<head>
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
 <title>选择#{channel.itemName }的作者对话框</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css'>
 <base target='_self'>
 <script type="text/javascript" src="main.js"></script>
</head>
<body>

#{call top_selected }<br />
#{call author_type }<br />

<table width='560' border='0' align='center' cellpadding='2' cellspacing='0' class='border'>
 <tr height='22' class='title'>
  <td><b><font color=red>作者</font>列表：</b></td>
  <td align=right>
 <form method='get' name='myform' action='' style='margin: 0px;'>
   <input name="field" type="hidden" value="name" />
   <input name="channelId" type="hidden" value="#{channel.id }" />
   <input name='keyword' type='text' size='20' value="" />&nbsp;&nbsp;
   <input type='submit' value='查找'>
 </form>
  </td>
 </tr>
 <tr>
  <td valign='top' height='100' colspan='2'>
    #{call author_list }
  </td>
 </tr>
</table>
#{call keyword_pagination_bar(page_info) }
#{call file_js }

</body>
</html>
</pub:template>

<!-- 顶部选中的作者姓名。 -->
<pub:template name="top_selected">
<table width='560' border='0' align='center' cellpadding='2'
 cellspacing='0' class='border'>
 <tr class='title' height='22'>
  <td valign='top'><b>已经选定的作者：</b></td>
  <td align='right'><a href='javascript:window.close();'>返回&gt;&gt;</a></td>
 </tr>
 <tr class='tdbg'>
  <td>
  <input type='text' name='authorList' id="authorList" size='60'
   maxlength='200' readonly='readonly'>
  </td>
  <td align='center'><input type='button' name='del1'
   onclick='del(1)' value='删除最后'> <input type='button'
   name='del2' onclick='del(0)' value='删除全部'></td>
 </tr>
</table>
</pub:template>

<!-- 作者分类。 -->
<pub:template name="author_type">
<table width='560' border='0' align='center' cellpadding='2'
 cellspacing='0' class='border'>
 <tr height='22' class='title'>
  <td>
  | <!-- <a href='admin_item_author.jsp?channelId=#{channel.id }&authorType=-1'>
  <FONT style='font-size:12px' #{iif (authorType == -1, "color='red'", "") }>最近常用</FONT></a> -->
  | <a href='admin_item_author.jsp?channelId=#{channel.id }&authorType=0'>
  <FONT style='font-size:12px' #{iif (authorType == 0, "color='red'", "") }>全部作者</FONT></a> 
  | <a href='admin_item_author.jsp?channelId=#{channel.id }&authorType=4'>
  <FONT style='font-size:12px' #{iif (authorType == 4, "color='red'", "") }>本站特约</FONT></a> 
  | <a href='admin_item_author.jsp?channelId=#{channel.id }&authorType=1'>
  <FONT style='font-size:12px' #{iif (authorType == 1, "color='red'", "") }>大陆作者</FONT></a> 
  | <a href='admin_item_author.jsp?channelId=#{channel.id }&authorType=2'>
  <FONT style='font-size:12px' #{iif (authorType == 2, "color='red'", "") }>港台作者</FONT></a> 
  | <a href='admin_item_author.jsp?channelId=#{channel.id }&authorType=3'>
  <FONT style='font-size:12px' #{iif (authorType == 3, "color='red'", "") }>海外作者</FONT></a> 
  | <a href='admin_item_author.jsp?channelId=#{channel.id }&authorType=5'>
  <FONT style='font-size:12px' #{iif (authorType == 5, "color='red'", "") }>其他作者</FONT></a> |
  </td>
 </tr>
</table>
</pub:template>

<!-- 作者列表。 -->
<pub:template name="author_list">
<table width='550' border='0' cellspacing='1' cellpadding='1'
 bgcolor='#f9f9f9'>
 <tr align='center'>
  <td width='150'>姓名</td>
  <td width='35'>性别</td>
  <td>简介</td>
 </tr>
 #{foreach author in author_list }
 <tr onmouseout="this.className='tdbg'"
  onmouseover="this.className='tdbgmouseover'">
  <td align='center'><a href='#' onclick='add("#{author.name@html }")'>#{author.name@html }</a></td>
  <td align='center'>#{iif (author.sex == 1, "男", "女") }</td>
  <td>#{author.description@html }</td>
 </tr>
 #{/foreach }
</table>
</pub:template>

<!-- 文件中的JS。 -->
<pub:template name="file_js">
<script language="javascript">
<!--
var authorList = byId("authorList");
authorList.value=opener.myform.author.value;

// 添加作者.
function add(obj) {
    if(obj=="") {return false;}
    if(opener.myform.author.value=="") {
      opener.myform.author.value=obj;
      authorList.value=opener.myform.author.value;
      return false;
    }
    var singleKey=obj.split("|");
    var ignoreKey="";
    for(i=0;i<singleKey.length;i++) {
      if(checkKey(opener.myform.author.value,singleKey[i])) {
        ignoreKey=ignoreKey+singleKey[i]+" "
      } else {
        opener.myform.author.value=opener.myform.author.value+"|"+singleKey[i];
        authorList.value=opener.myform.author.value;
      }
    }
    if(ignoreKey!="") {
      alert(ignoreKey+" 该作者已经存在，此操作已经忽略！");
    }
}

// 减少作者.
function del(num) {
  if (num==0 || opener.myform.author.value=="" || opener.myform.author.value=="|"){
    opener.myform.author.value="";
    authorList.value="";
    return false;
  }

  var strDel=opener.myform.author.value;
  var s=strDel.split("|");
  opener.myform.author.value=strDel.substring(0,strDel.length-s[s.length-1].length-1);
  authorList.value=opener.myform.author.value;
}

// 验证.
function checkKey(Keylist,thisKey) {
  if (Keylist==thisKey){
    return true;
  } else {
    var s=Keylist.split("|");
    for (j=0;j<s.length;j++){
      if(s[j]==thisKey)
        return true;
    }
    return false;
  }
}
// -->
</script>
</pub:template>
</pub:declare>
<pub:process_template name="main" />
