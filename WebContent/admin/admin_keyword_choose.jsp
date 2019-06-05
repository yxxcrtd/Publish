<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.KeywordManage" 
%><%
  // 初始化页面所需数据。
  KeywordManage admin_data = new KeywordManage(pageContext);
  admin_data.initChoosePageData();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <title>关键字选择对话框</title>
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312' />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <base target='_self'>
 <script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:declare>
<%@include file="element_keyword.jsp"%>

<pub:template name="main">
#{call top_selected }<br />
#{call keyword_table }
#{call keyword_pagination_bar(page_info) }
#{call file_js }
</pub:template>

<pub:template name="keyword_table">
<table width='560' border='0' align='center' cellpadding='2' cellspacing='0' class='border'>
 <tr height='22' class='title'>
  <td>
    <b><font color=red>关键字</font>列表：</b></td>
    <td align=right>
  <form method='get' name='myform' action='' style='margin:0px;'>
    <input name="channelId" type="hidden" value="#{channel.id }" />
    <input name="field" type="hidden" value="name" />
    <input name='keyword' type='text' size='20' value="" />&nbsp;&nbsp;
    <input type='submit' value='查找'>
  </form>
  </td>
 </tr>
 <tr>
   <td valign='top' height='100' colspan=2>
    #{call keywords }
  </td>
</tr>
<tr class='tdbg'>
 <td align=center colspan=2><a href='#' onclick="addAll()">增加以上所有关键字</a></td>
</tr>
</table>
</pub:template>

<!-- 顶部已经选中的关键字。 -->
<pub:template name="top_selected">
<table width='560' border='0' align='center' cellpadding='2' cellspacing='0' class='border'>
<tr class='title' height='22'>
 <td valign='top'><b>已经选定的关键字：</b></td>
 <td align='right'><a href='javascript:window.close();'>返回&gt;&gt;</a></td>
</tr>
<tr class='tdbg'>
 <td><input type='text' name='KeyList' id="KeyList" size='60' maxlength='200'
			readonly='readonly'></td>
 <td align='center'>
  <input type='button' name='del1' onclick='del(1)' value='删除最后' />
  <input type='button' name='del2' onclick='del(0)' value='删除全部' />
 </td>
</tr>
</table>
</pub:template>

<!-- 文章关键字的集合。 -->
<pub:template name="keywords">
<table width='550' border='0' cellspacing='1' cellpadding='1' bgcolor='#f9f9f9'>
 <tr>
  <td align='center'>
  #{foreach keyword in keyword_list }
   <a href='#' name="keyname" onclick='add("#{keyword.name@html}")'>#{keyword.name@html}</a>
  #{/foreach }
  </td>
 </tr>
</table>
</pub:template>

<!-- 文件的JS。 -->
<pub:template name="file_js">
<script language="javascript">
var keyList = byId("KeyList");
function setKeyList() {
  if (opener == null || opener.myform == null || opener.myform.keywords == null)
    return;
  keyList.value = opener.myform.keywords.value;
}

setKeyList();

function add(obj) {
    if(obj==""){return false;}
    if(opener.myform.keywords.value=="") {
        opener.myform.keywords.value=obj;
        keyList.value=opener.myform.keywords.value;
        return false;
    }
    var singleKey=obj.split("|");
    var ignoreKey="";
    for(i=0;i<singleKey.length;i++)
    {
        if(checkKey(opener.myform.keywords.value,singleKey[i]))
        {
            ignoreKey=ignoreKey+singleKey[i]+" "
        }
        else
        {
            opener.myform.keywords.value=opener.myform.keywords.value+"|"+singleKey[i];
            keyList.value=opener.myform.keywords.value;
        }
    }
    if(ignoreKey!="") {
        alert(ignoreKey+" 关键字已经存在，此操作已经忽略！");
    }
}

function addAll() {
	var keywords = byName("keyname");
	var str = [];
	for (var i = 0; i < keywords.length; i++) {
		str.push(keywords[i].innerHTML);
	}
	add(str.join("|"));
}

function del(num) {
    if (num==0 || opener.myform.keywords.value=="" || opener.myform.keywords.value=="|")
    {
        opener.myform.keywords.value="";
        keyList.value="";
        return false;
    }

    var strDel=opener.myform.keywords.value;
    var s=strDel.split("|");
    opener.myform.keywords.value=strDel.substring(0,strDel.length-s[s.length-1].length-1);
    keyList.value=opener.myform.keywords.value;
}

function checkKey(keyList,thisKey) {
  if (keyList==thisKey) {
        return true;
  }
  else{
    var s=keyList.split("|");
    for (j=0;j<s.length;j++){
        if(s[j]==thisKey)
            return true;
    }
    return false;
  }
}
</script>
</pub:template>
</pub:declare>
<pub:process_template name="main" />
</body>
</html>
