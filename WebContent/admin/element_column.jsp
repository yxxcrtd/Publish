<%@ page language="java" contentType="text/html; charset=gb2312"  pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%-- 
@param channel 频道对象
 --%>
<pub:template name="column_manage_navigator">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='10'>
   <table width='100%'>
    <tr class='topbg'>
     <td align='center'><b>#{channel.name }管理----栏目管理</b></td>
     <td width='60' align='right'><a
      href='help/index.jsp' target='_blank'>
     <img src='images/help.gif' border='0' /> </a></td>
    </tr>
   </table>
   </td>
  </tr>
  <tr class='tdbg'>
   <td width='70' height='30'><strong>管理导航：</strong></td>
   <td height='30'><a href='admin_column_list.jsp?channelId=#{channel.id }'>#{channel.itemName}栏目管理首页</a>
   | <a href='admin_column_add.jsp?channelId=#{channel.id}'>添加栏目</a> 
   | <a href='admin_column_order.jsp?channelId=#{channel.id}'>栏目排序</a>
   | <a href='admin_column_unite.jsp?channelId=#{channel.id}'>栏目合并</a>
   </td>
  </tr>
 </table>
</pub:template>

<%-- 
@param channel 频道对象
 --%>
<pub:template name="column_manage_description">
 <br />
 <table width='100%'>
  <tr>
   <td colspan='5'><b>栏目属性中各项的含义：</b></td>
  </tr>
  <tr>
   <td>原----在原窗口打开</td>
   <td>新----在新窗口打开</td>
   <td>首----在首页分类列表处显示，只对一级栏目有效</td>
  </tr>
  <tr>
   <td>列----在父栏目分类列表处显示</td>
   <td>锁----有子栏目时不允许添加#{channel.itemName }</td>
   <td>开----有子栏目时可以添加#{channel.itemName }</td>
  </tr>
  <tr>
   <td>保----启用防复制/下载功能</td>
  </tr>
 </table>
</pub:template>

<%-- 
@param defaultValue 栏目下拉列表框的默认值。
@param dropdown_columns 下拉列表框的数据对象 －－ DataTable 。
 --%>
<pub:template name="dropDownColumns">
#{param defaultValue, dropdown_columns, checkAddItem}
#{foreach column0 in dropdown_columns}
<option #{iif(checkAddItem && !column0.enableAdd, "value='0' style='color:gray' ", "value='" + column0.id + "' ") } 
 #{iif(defaultValue==column0.id, "selected ", " ") }>#{column0.prefix}#{column0.name}</option>
#{/foreach }
</pub:template>

<%-- 
栏目的风格.
@param css_name
@param css_id
--%>
<pub:template name="column_css">
#{param css_name, css_id, defaultValue}
<select name='#{(css_name)}' id='#{(css_id)}'>
 <option value='0' #{iif (defaultValue==0, "selected", "") }>系统默认风格</option>
 #{foreach skin in skin_list }
 <option value='#{skin.id}' #{iif (defaultValue==skin.id, "selected", "") }>#{skin.name}</option>
 #{/foreach }
</select>
</pub:template>

<%-- 
栏目首页的模板。 
@param templ_name
@param templ_id
--%>
<pub:template name="column_templ">
#{param templ_name, templ_id, defaultValue }
<select name='#{templ_name }' id='#{templ_id }'>
 <option value='0' #{iif (defaultValue==0, "selected", "") }>系统默认栏目模板</option>
#{foreach templ in column_home_tlist}
 <option value='#{templ.id}' #{iif (defaultValue == templ.id, "selected", "") }>#{templ.name}</option>
#{/foreach}
</select>
</pub:template>

<%-- 
每页最多显示的记录数。 
@param ele_id 元素的ID。
@param ele_name 元素的名称。
--%>
<pub:template name="maxPerPage">
#{param ele_id, ele_name, defaultValue}
<select id='#{ele_id }' name='#{ele_name }'>
<% for (int i = 5; i < 101; ++i) { %>
 <option value='<%=i%>' #{iif (defaultValue==<%=i%>, "selected", "") }><%=i%></option>
<% } %>
</select>
</pub:template>

<%-- 
栏目内容页的风格。 
@param css_name
@param css_id
--%>
<pub:template name="defaultItemSkin">
#{param css_name, css_id, defaultValue}
<select name='#{(css_name)}' id='#{(css_id)}'>
 <option value='0' #{iif (defaultValue==0, "selected", "") }>系统默认风格</option>
#{foreach skin in skin_list}
 <option value='#{skin.id}' #{iif (defaultValue==skin.id, "selected", "") }>#{skin.name}</option>
#{/foreach }
</select>
</pub:template>

<%-- 
栏目内容页的模板。 
@param templ_name
@param templ_id
--%>
<pub:template name="defaultItemTemplate">
#{param templ_name, templ_id, defaultValue}
<select name='#{templ_name }' id='#{templ_id }'>
 <option value='0' #{iif (defaultValue==0, "selected", "") }>系统默认内容页模板</option>
#{foreach templ in content_tlist}
 <option value='#{templ.id}' #{iif (defaultValue == templ.id, "selected", "") }>#{templ.name}</option>
#{/foreach}
</select>
</pub:template>
