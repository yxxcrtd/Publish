<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.BizManage"
%><%
  // 初始化页面数据.
  BizManage manager = new BizManage(pageContext);
  manager.initGroupSelect();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>选择相关业务群组</title>
 <base target='_self' />
 <script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:declare>

<pub:template name="main">
 #{call show_user_list }
 #{call show_page_nav }
 #{call form_buttons }
</pub:template>


<pub:template name="show_user_list">
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
<tr class='title'>
 <td align='center' width='160'><strong>群组名</strong></td>
 <td align='center' width='240'><strong>简介</strong></td>
 <td width='60' height='22' align='center'><strong>操作</strong></td>
</tr>
#{foreach group in group_list}
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td align='center' width='160'>#{group.name}</td>
 <td align='center' width='240'>#{group.description@html}</td>
 <td align='center'><a href='javascript:selectGroup(#{group.id })'>选择</a></td>
</tr>
#{/foreach}
</table>
</pub:template>


<pub:template name="show_page_nav">
<div class="show_page">
 共 <b>#{page_info.totalCount }</b> #{page_info.itemName},
#{if (page_info.currPage < 2) }
 <span>首页 上一页</span>
#{ else }
<a href="javascript:goPage(1)">首页</a>
<a href="javascript:goPage(#{page_info.currPage - 1})">上一页</a>
#{/if }
#{if (page_info.currPage >= page_info.totalPage) }
 <span>下一页 尾页</span>
#{else}
<a href="javascript:goPage(#{page_info.currPage + 1 })">下一页</a>
<a href="javascript:goPage(#{page_info.totalPage })">尾页</a>
#{/if }
 <strong>
 <font color="red">#{page_info.currPage }</font>/#{page_info.totalPage } </strong> 页 
 <input type="text" name="pageSize" id="pageSize" 
  size="3" maxlength="4" value="#{page_info.pageSize }"
  onkeypress="if (event.keyCode == 13) goPage(#{page_info.currPage }); else if (!isDigit()) return false;" />
 #{page_info.itemName }/页

 转到：
 <select id="paginationPage" onchange="goPage(this.value)"></select>
</div>
<script type="text/javascript">
<!--
var paginationPage = byId("paginationPage");
for (var i = 1; i <= #{page_info.totalPage }; i++) {
 var option = document.createElement("option");
 option.value = i;
 option.text = "第" + i + "页";
 if (i == #{page_info.currPage }) {
  option.selected = true;
 }
 paginationPage.add(option);
}

function goPage(pageNum) {
  var url = setUrlParam(location.href, "page", pageNum);
  url = setUrlParam(url, "pageSize", byId("pageSize").value);
  location = url;
}
// -->
</script>
</pub:template>


<pub:template name="form_buttons">
<div><center>
<input type='button' value=' 取   消 ' onclick='window.close();' />
</center></div>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

<script language="javascript">
function selectGroup(id) {
  window.returnValue = id;
  window.close();
}
</script>
</body>
</html>
