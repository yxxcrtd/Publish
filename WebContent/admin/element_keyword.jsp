<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<!-- 
关键字管理的导航部分，

@param channel 对象。 
@param text 描述文字，如：关键字管理|新增关键字

-->
<pub:template name="keyword_navigator">
#{param text }
<table width='100%' border='0' align='center' cellpadding='2'
  cellspacing='1' class='border'>
  <tr class='topbg'>
   <td height='22' colspan='10'>
   <table width='100%'>
    <tr class='topbg'>
     <td align='center'><b>#{channel.name }管理－－#{text }</b></td>
     <td width='60' align='right'><a
      href='help/index.jsp' target='_blank'><img
      src='images/help.gif' border='0'></a></td>
    </tr>
   </table>
   </td>
  </tr>
  <tr class='tdbg'>
   <td width='82' height='30'><strong>管理导航：</strong></td>
   <td height='30'>
   <a href='admin_keyword_list.jsp?channelId=#{channel.id }'>关键字管理首页</a>&nbsp;|&nbsp;
   <a href='admin_keyword_add.jsp?channelId=#{channel.id }'>新增关键字</a>
   </td>
  </tr>
</table>
</pub:template>

<!-- 
作者管理的导航部分，

@param channel 对象。 
@param text 描述文字，如：作者管理|新增作者信息

-->
<pub:template name="author_navigator">
#{param text }
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='2'>
  <table width='100%'>
   <tr class='topbg'>
    <td align='center'><b>#{channel.name }管理－－#{text}</b></td>
    <td width='60' align='right'>
    <a href='help/index.jsp' target='_blank'><img
     src='images/help.gif' border='0'></a></td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='82' height='30'><strong>管理导航：</strong></td>
  <td height='30'>
   <a href='admin_author_list.jsp?channelId=#{channel.id}'>作者管理首页</a>&nbsp;|&nbsp;
   <a href='admin_author_add.jsp?channelId=#{channel.id}'>添加作者</a>
  </td>
 </tr>
</table>
</pub:template>

<!-- 
来源管理的导航部分，
@param channel 对象。

@param text 描述文字，如：来源管理|新增来源
-->
<pub:template name="source_navigator">
#{param text }
<table width='100%' border='0' align='center' cellpadding='2'
 cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='10'>
  <table width='100%'>
   <tr class='topbg'>
    <td align='center'><b>#{channel.name }管理－－#{text }</b></td>
    <td width='60' align='right'><a
     href='help/index.jsp' target='_blank'><img
     src='images/help.gif' border='0'></a></td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='82' height='30'><strong>管理导航：</strong></td>
  <td height='30'><a
   href='admin_source_list.jsp?channelId=#{channel.id }'>来源管理首页</a>&nbsp;|&nbsp;<a
   href='admin_source_add.jsp?channelId=#{channel.id }'>新增来源</a></td>
 </tr>
</table>
</pub:template>

<!-- 
记录分页栏

@param totalNum 总记录数。

@param totalPage 总页数。

@param page 当前页码。

@param maxPerPage 每页最大记录数。

@param text 特定文字，如：个关键字

 -->
<pub:template name="keyword_pagination_bar">
#{param page_info}
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

<!-- 
频道列表的模板

@param channel 当前频道对象。

@param channels 所有的频道集合。

@param url 操作的链接。

@param text 特定描述文字，如：作者|关键字|来源
 -->
<pub:template name="templ_channels">
#{param url, text }
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22'>| 
  #{if channel == null || channel.id == 0 }
   <font color='red'>全站#{text }</font>
  #{else }
  <a href='#{url}?channelId=0'>全站#{text }</a>
  #{/if }
 #{foreach channel0 in channel_list }
  #{if (channel0.id == channel.id) }
   | <font color='red'>#{channel0.name }</font> 
  #{else }
   | <a href='#{url }?channelId=#{channel0.id }'>#{channel0.name }</a>
  #{/if }
 #{/foreach }
  </td>
 </tr>
</table>
</pub:template>
