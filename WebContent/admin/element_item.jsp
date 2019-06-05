<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<pub:template name="more_child_column_list">
#{if current_column != null && child_column_list != null }
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr height='20' class='tdbg'>
    <td width='150' align='right'>
    【<a href='?channelId=#{channel.id}&columnId=#{current_column.id}'>#{current_column.name }</a>】子栏目导航：</td>
    <td>&nbsp;&nbsp;
  #{foreach column in child_column_list }
    <a href='?channelId=#{channel.id}&columnId=#{column.id}'>#{column.name }</a>#{if 
      column.childCount > 0 }(#{column.childCount })#{/if }&nbsp;
  #{/foreach }
    </td>
  </tr>
</table><br/>
#{/if }
</pub:template>

<%-- 
产生出标签形式的水平的栏目集合。
@param channel 频道对象。
@param column_data 栏目层次结构的数据，DataTable(id, name) 。
@param url 导航的链接，例如：admin_article_list.jsp 。
 --%>
<pub:template name="column_label_list">
#{param main_column_data, url, sub_column_data}
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
  <tr class="title">
 <td height="22">| 
 #{foreach column in main_column_data }
  <a href="#{(url)}?channelId=#{channel.id}&columnId=#{column.id}">
   #{if column.id == first_level_column.id }
    <font color='red'>#{column.name}</font>
   #{else}
    #{column.name}
   #{/if }
  </a>  | 
 #{/foreach }
  </td>
 </tr> 
#{if sub_column_list != null }
 <tr class='tdbg'>
  <td>&nbsp;&nbsp;
 #{foreach column in sub_column_list }
   <a href='#{url }?channelId=#{channel.id }&columnId=#{column.id }'>#{if column.id == second_level_column.id }
   <font color='red'>#{column.name }</font>#{else }
   #{column.name }
  #{/if }</a>#{if column.childCount > 0 }(#{column.childCount })#{/if }&nbsp;
 #{/foreach }
  </td>
 </tr>
#{/if }
</table>
</pub:template>


<%-- 在页面上产生一个隐藏操作 form, 其提供一个 form 和 __itemName --%>
<pub:template name="form_action">
<form name="formAction" id="formAction" action="" method="post" style="display:none; ">
 <input type='hidden' name='command' value='' />
 <input type='hidden' name='channelId' value='#{channel.id}' />
 <input type='hidden' name='itemIds' value='' />
 <input type='hidden' name='refids' value='' />
 <input type='hidden' name='status' value='' />
 <input type='hidden' name='__itemName' id='__itemName' value="#{channel.itemName}" />
</form>
</pub:template>

<%-- 
产生出栏目层次结构导航部分，例如：栏目1 >> 栏目2 >> 栏目3 。
@param column_data - 栏目层次结构的数据，List<Column> 。
@param url - 导航的链接，例如：admin_soft_list.jsp (不给就是当前页面)。
 --%>
<pub:template name="column_tier">
#{param column_data, url}
#{foreach column in column_data }
 #{if (column.id != channel.rootColumnId) }
 &gt;&gt; <a href='#{url}?channelId=#{channel.id }&columnId=#{column.id }'>#{column.name }</a>
 #{/if }
#{/foreach }
</pub:template>


<%-- 记录分页栏 --%>
<pub:template name="pagination_bar">
#{param page_info}
<div class="show_page">
共 <b>#{page_info.totalCount }</b> #{channel.itemUnit }#{channel.itemName},
#{if (page_info.currPage < 2) }
 首页 上一页 
#{else }
 <a href="javascript:goPage(1)">首页</a>&nbsp;<a href="javascript:goPage(#{page_info.currPage - 1 })">上一页</a>
#{/if }
#{if (page_info.currPage >= page_info.totalPage) }
 下一页 尾页 
#{else }
 <a href="javascript:goPage(#{page_info.currPage + 1 })">下一页</a>&nbsp;<a href="javascript:goPage(#{page_info.totalPage })">尾页</a>
#{/if }
 <strong>
 <font color="red">#{page_info.currPage }</font>/#{page_info.totalPage }</strong>页 
 <input type="text" name="pageSize" id="pageSize" size="3" maxlength="4" value="#{page_info.pageSize}"
  onkeypress="if (event.keyCode == 13) goPage(this.value);" />
 #{channel.itemUnit}#{channel.itemName}/页
 转到：
 <select id="paginationPage" onchange="goPage(this.value)"></select>
</div>
<script type="text/javascript">
<!--
var paginationPage = byId("paginationPage");
for (var i = 1; i <= #{(page_info.totalPage)}; i++) {
 var option = document.createElement("option");
 option.value = i;
 option.text = "第" + i + "页";
 paginationPage.add(option);
 if (i == #{page_info.currPage}) {
  option.selected = true;
 }
}

function goPage(pageNum) {
 var url = setUrlParam(location.href, "page", pageNum);
 url = setUrlParam(url, "pageSize", byId("pageSize").value);
 location = url;
}
// -->
</script>
</pub:template>
