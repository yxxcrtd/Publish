<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.WebPageManage"
%><%
  // 构造获取数据的对象.
  WebPageManage manager = new WebPageManage(pageContext);
  manager.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>添加/修改自定义页面</title>
<script type="text/javascript">
<!--
// 验证表单提交的合法性。
function check() {
   return true;
	/*if(document.form1.channelId.value > 0)	{
		return true;
	} else {
		return false;
	}*/
}
// -->
</script>
</head>
<body>
<pub:declare>
<!-- 定义标签页集合。 -->
<pub:tabs var="webpage_tabs" scope="page" purpose="">
 <pub:tab name="baseInfo" text="基本信息" template="webpage_base_info" default="true" />
 <pub:tab name="extendsInfo" text="扩展属性" template="object_extends_edit" />
</pub:tabs>

<%@ include file="tabs_tmpl2.jsp" %>
<%@ include file="extends_prop.jsp" %>

<!-- 主模板。 -->
<pub:template name="main">
<!-- \#{call xxx_manage_navigator } -->
<br />
<form name='form1' method='post' action='admin_webpage_action.jsp' onsubmit='return check();'>
 #{call tab_js }
 #{call tab_header(webpage_tabs) }
 #{call tab_content(webpage_tabs) }
 #{call form_buttons}
</form>
<br /><br />
</pub:template>

<pub:template name="debug_info">
<h2>DEBUG</h2>
<ul>
<li>webpage = #{(webpage) }, .id = #{webpage.id }, .parentId = #{webpage.parentId }
<li>webpage.extends = #{webpage.extends }, .size = #{webpage.extends.size }
<li>container = #{(container) }
<li>parent_webpage_list = #{(parent_webpage_list) }
<li>parent_webpage_list.schema = #{(parent_webpage_list.schema) }
<li>avai_template_list = #{(template_list) }
<li>avai_template_list.schema = #{template_list.schema }
<li>avai_skin_list = #{(skin_list) }
<li>avai_skin_list.schema = #{skin_list.schema }
<li>webpage.extends = #{webpage.extends }, .size = #{webpage.extends.size }
#{foreach property in webpage.extends }
<li>foreach property .id = #{property.id }, .name = #{property.name },
  .propType = #{property.propType }, .propValue = #{property.propValue }
#{/foreach }

<li>webpage.fff = #{webpage.fff }
<li>webpage.multiString = #{webpage.multiString }
<li>webpage.mi = #{webpage.mi }
</ul>
<br /><br />
</pub:template>

<!-- 标签页使用的模板。 -->
<pub:template name="webpage_base_info">
<table width='100%' border='0'>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>所属页面：</strong></td>
  <td>
  <input name="id" type="hidden" value="#{webpage.id }" />
  <input name="parentPath" type="hidden" value="#{webpage.parentPath }" />
  <input name="orderPath" type="hidden" value="#{webpage.orderPath }" />
  <select name="parentId">
   <option value="0" #{if (webpage.parentId == 0)} selected#{/if}>无（作为一级页面）</option>
   #{foreach parent_page in parent_webpage_list}
   <option value="#{parent_page.id}" #{if (webpage.parentId == parent_page.id)} selected#{/if}>#{parent_page.prefix + parent_page.name}</option>
   #{/foreach }
  </select>
  <font color=blue></font></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面名称：</strong></td>
  <td><input name='name' type='text' size='20' maxlength='20' value="#{webpage.name@html }" />
  <font color=red>*</font> 必须给出，只能是英文或+数字，不能带有空格等特殊字符。</td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面标题：</strong></td>
  <td><input name='title' type='text' size='20' maxlength='64' value="#{webpage.title@html }" />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面图片地址：</strong><br>
  用于在页面页显示指定的图片</td>
  <td><input name='logo' type='text' id='Logo'
   size='60' maxlength='255' value="#{webpage.logo@html}"></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面banner图片地址：</strong><br>
  用于在页面页banner显示指定的图片</td>
  <td><input name='banner' type='text' id='Banner'
   size='60' maxlength='255' value="#{webpage.banner@html}"></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面提示：</strong><br>
  鼠标移至页面名称上时将显示设定的提示文字（不支持HTML）</td>
  <td><textarea name='tips' cols='60' rows='2' id='Tips'>#{webpage.tips@html}</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面说明：</strong><br>
  用于在页面页详细介绍页面信息，支持HTML</td>
  <td><textarea name='description' cols='60' rows='4' id='Readme' type="_moz">#{webpage.description@html}</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>页面META关键词：</strong><br>
  针对搜索引擎设置的关键词<br>
  例如：在文本框填写<br>
  &lt;meta name="Keywords" content="网站,门户,新闻,快讯"&gt;<br>
  多个关键词请用,号分隔</td>
  <td><textarea name='metaKey' cols='60' rows='4'
   id='Meta_Keywords' type="_moz">#{webpage.metaKey@html}</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>页面META网页描述：</strong><br>
  针对搜索引擎设置的网页描述<br>
  例如：在文本框填写<br>
  &lt;meta name="Description" content="网站,门户,新闻,快讯"&gt;<br>
  多个描述请用,号分隔</td>
  <td><textarea name='metaDesc' cols='60' rows='4'
   id='Meta_Description' type="_moz">#{webpage.metaDesc@html}</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面使用的模板：</strong><br>
  相关模板中包含了页面设计的版式等信息，如果是自行添加的设计模板，可能会导致“页面配色风格”失效。</td>
  <td>
  <select name='templateId' id='templateId'>
	<option value='0' #{iif (webpage.templateId == 0, "selected", "") }>默认的自定义页面模板</option>
	#{foreach templ in template_list}
	<option value='#{templ.id}' #{iif (webpage.templateId == templ.id, "selected", "") }>#{templ.name}</option>
	#{/foreach}
  </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>页面的配色风格：</strong><br>
  相关风格中包含CSS、颜色、图片等信息</td>
  <td>
  <select name='skinId' id='skinId'>
   <option value='0' #{iif (webpage.skinId == 0, 'selected', '')}>默认的配色风格</option>
  #{foreach skin in skin_list}
   <option value='#{skin.id}' #{iif (webpage.skinId == skin.id, 'selected', '')}>#{skin.name}</option>
  #{/foreach}
  </select>
  </td>
 </tr>
</table>
</pub:template>


<pub:template name="form_buttons">
<table width='100%' border='0' align='center'>
 <tr class='tdbg'>
  <td height='40' colspan='2' align='center'>
   <input name='command' type='hidden' id='command' value='save' /> 
    <input name='channelId' type='hidden' id='channelId' value='#{channel.id }' /> 
    <input name='save' type='submit' value=' #{iif (webpage.id > 0, "保 存", "添 加" ) } ' style='cursor:hand;' />&nbsp;&nbsp;
    <input name='cancel' type='button' id='Cancel' value=' 取 消 '
      onclick="window.location.href='admin_webpage.jsp?channelId=#{channel.id }'"
    style='cursor:hand;' /></td>
 </tr>
</table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>
