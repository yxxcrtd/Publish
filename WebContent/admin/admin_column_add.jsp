<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ColumnManage"
%><%
  // 产生管理页面所需的数据。
  ColumnManage admin_data = new ColumnManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>增加/修改栏目</title>
<script type="text/javascript">
<!--
// 验证表单提交的合法性.
function check() {
  var CurrentMode = editor.CurrentMode;
  if (CurrentMode==0){
    setOpenNew();
    document.form1.description.value=editor.HtmlEdit.document.body.innerHTML; 
  } else if (CurrentMode==1){
    setOpenNew();
    document.form1.description.value=editor.HtmlEdit.document.body.innerText;
  } else {
    alert('栏目详细说明在预览状态不能保存！请先回到编辑状态后再保存');
    return false;
  }
  
  if(document.form1.channelId.value > 0) {
    return true;
  } else {
    return false;
  }
}

// 将内容页中所有的链接的目标窗口都设置为弹出新窗口.
function setOpenNew() {
 var links = editor.HtmlEdit.document.getElementsByTagName("A");
 for (var i = 0; i < links.length; i++) {
  links[i].setAttribute("target", "_blank");
 }
}
// -->
</script>
</head>
<body>

<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="contentTabs" scope="page" purpose="">
 <pub:tab name="base_info_tab" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="column_option_tab" text="栏目选项" template="temp_columnCustmor"  default="false" />
 <pub:tab name="column_document_tab" text="详细说明" template="temp_column_document" />
</pub:tabs>

<!-- 导入栏目管理相关的元素。column_manage_navigator -->
<%@ include file="element_column.jsp" %>
<%@ include file="tabs_tmpl2.jsp" %>

<!-- 主模板。 -->
<pub:template name="main">
 #{call column_manage_navigator}<br />
 #{call your_position }<br />
<form name='form1' method='post' action='admin_column_action.jsp?command=save' onsubmit='return check()'>
 #{call tab_js }
 #{call tab_header(contentTabs) }
 #{call tab_content(contentTabs) }
 #{call form_buttons }
</form>

#{if readonly }
<font color='red'>注意</font>：当前您只具有该栏目的查看权限，不能修改此栏目的设置。
#{/if }
<br/><br/>
</pub:template>
 
 
<pub:template name="form_buttons">
<table width='100%' border='0' align='center'>
<tr class='tdbg'>
 <td height='40' colspan='2' align='center'>
  <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' /> 
  <input #{if readonly}disabled #{/if} name='save' type='submit' value=' #{iif (column.id > 0, "保 存", "添 加" ) } ' style='cursor:hand;'>&nbsp;&nbsp;
  <input name='cancel' type='button' id='Cancel' value=' 取 消 '
  onclick="window.location.href='admin_column_list.jsp?channelId=#{channel.id }'"
  style='cursor:hand;'></td>
</tr>
</table>
</pub:template>


<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>您现在的位置：<a href='admin_column_list.jsp?channelId=#{channel.id }'>#{channel.itemName }栏目管理</a>&nbsp;&gt;&gt;&nbsp;#{iif (column.id > 0, "修改", "添加" ) }栏目</td>
 </tr>
</table>
</pub:template>



<!-- 标签页使用的模板。 -->
<pub:template name="temp_baseInfo">
<table width='100%'>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>所属栏目：</strong></td>
  <td>
  <input name="id" type="hidden" value="#{column.id}" />
  <select name="parentId">
  <option value="0" parentPath="/">无（作为一级栏目）</option>
  #{call dropDownColumns(column.parentId, dropdown_columns) }
  </select>
  <font color=blue>不能指定为外部栏目</font></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>栏目名称：</strong></td>
  <td><input name='name' type='text' size='20' maxlength='20' value="#{column.name@html }">
  <font color=red>*</font></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>栏目类型：</strong><br>
  <font color=red>请慎重选择，栏目一旦添加后就不能再更改栏目类型。</font></td>
  <td><input name='columnType' type='radio' value='0' #{iif(column.columnType==0, "checked ", "") } 
    onclick="HideTabTitle('', '1')" #{iif (column.id != 0, "disabled ", "") } />
  <font color=blue><b>内部栏目</b></font>&nbsp;&nbsp;内部栏目具有详细的参数设置。可以添加子栏目和#{channel.itemName }。<br>
  &nbsp;&nbsp;&nbsp;&nbsp;内部栏目的目录名：

  <input name='columnDir' #{iif (column.id != 0, "disabled ", "") } type='text' size='20' maxlength='20' value="#{column.columnDir@html }" /> 
  <font color='#FF0000'>注意，目录名只能是英文</font><br>
  <br>
  <input name='columnType' type='radio' value='1' #{iif(column.columnType==1, "checked", "") } 
  onClick="HideTabTitle('none')" #{iif (column.id != 0, "disabled ", "") } /> 
  <font color=blue><b>外部栏目</b></font>&nbsp;&nbsp;外部栏目指链接到本系统以外的地址中。当此栏目准备链接到网站中的其他系统时，请使用这种方式。不能在外部栏目中添加#{channel.itemName }，也不能添加子栏目。<br>
  &nbsp;&nbsp;&nbsp;&nbsp;外部栏目的链接地址：

  <input name='linkUrl' type='text' id='LinkUrl' value='#{column.linkUrl}' size='40'
   maxlength='200' #{iif (column.columnType == 0 && column.id != 0, "disabled ", "") }></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>栏目图片地址：</strong><br>
  用于在栏目页显示指定的图片</td>
  <td><input name='logo' type='text' id='logo' size='60' maxlength='255' value="#{column.logo@html }" /></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>栏目提示：</strong><br>
  鼠标移至栏目名称上时将显示设定的提示文字（不支持HTML）</td>
  <td><textarea name='tips' cols='60' rows='2' id='Tips' type="_moz">#{column.tips@html }</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>栏目META关键词：</strong><br>
  针对搜索引擎设置的关键词<br>
  例如：在文本框填写<br>
  &lt;meta name="Keywords" content="网站,门户,新闻,快讯"&gt;<br>
  多个关键词请用,号分隔</td>
  <td><textarea name='metaKey' cols='60' rows='4'
   id='Meta_Keywords' type="_moz">#{column.metaKey@html }</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>栏目META网页描述：</strong><br>
  针对搜索引擎设置的网页描述<br>
  例如：在文本框填写<br>
  &lt;meta name="Description" content="网站,门户,新闻,快讯"&gt;<br>
  多个描述请用,号分隔</td>
  <td><textarea name='metaDesc' cols='60' rows='4'
   id='Meta_Description' type="_moz">#{column.metaDesc@html }</textarea></td>
 </tr>
</table>
</pub:template>



<pub:template name="temp_columnCustmor">
<table width='100%'>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>打开方式：</strong></td>
  <td>
   <input name='openType' type='radio' value='0' #{iif (column.openType == 0, "", "checked ") }/>在原窗口打开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input name='openType' type='radio' value='1' #{iif (column.openType == 1, "checked ", "") }/>在新窗口打开
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>是否在顶部导航栏显示：</strong><br>
  此选项只对一级栏目有效。</td>
  <td>
   <input name='showOnTop' type='radio' value='true' #{iif (column.showOnTop, "", "checked ") }/>是&nbsp;&nbsp;&nbsp;&nbsp;
   <input name='showOnTop' type='radio' value='false' #{iif (column.showOnTop, "checked ", "") }/>否
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>是否在频道首页分类列表处显示：</strong><br>
  此选项只对一级栏目有效。如果一级栏目比较多，但首页不想显示太多的分类列表，这个选项就非常有用。</td>
  <td><input name='showOnIndex' type='radio' value='True' #{iif (column.showOnIndex, "checked", "") }>是&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='showOnIndex' type='radio' value='False' #{iif (column.showOnIndex, "", "checked") }>否</td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>是否在父栏目的分类列表处显示：</strong><br>
  如果某栏目下有几十个子栏目，但只想显示其中几个子栏目的#{channel.itemName }列表，这个选项就非常有用。</td>
  <td><input name='isElite' type='radio' value='True' #{iif (column.isElite, "checked", "") }>是&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='isElite' type='radio' value='False' #{iif (column.isElite, "", "checked") }>否</td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>有子栏目时是否可以在此栏目添加#{channel.itemName }：</strong></td>
  <td><input name='enableAdd' type='radio' value='True' #{iif (column.enableAdd, "checked", "") }>是&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='enableAdd' type='radio' value='False' #{iif (column.enableAdd, "", "checked") }>否</td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>是否启用此栏目的防止复制、防盗链功能：</strong></td>
  <td><input name='enableProtect' type='radio' value='True' #{iif (column.enableProtect, "checked", "") }>是&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='enableProtect' type='radio' value='False' #{iif (column.enableProtect, "", "checked") }>否</td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>栏目配色风格：</strong><br>
  相关模板中包含CSS、颜色、图片等信息</td>
  <td>
  #{call column_css("skinId", "skinId", column.skinId) }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>栏目模板：</strong><br>
  相关模板中包含了栏目设计的版式等信息，如果是自行添加的设计模板，可能会导致“栏目配色风格”失效。</td>
  <td>
  #{call column_templ("templateId", "templateId", column.templateId) }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>每页显示的#{channel.itemName }数：</strong><br>
  当此栏目为最下一级栏目时，则会分页显示此栏目中的#{channel.itemName }，这里指定的是每页显示的#{channel.itemName }数。</td>
  <td>
  #{call maxPerPage("maxPerPage", "maxPerPage", column.maxPerPage) }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>此栏目下的#{channel.itemName }的默认配色风格：</strong><br>
  相关模板中包含CSS、颜色、图片等信息</td>
  <td>
  #{call defaultItemSkin("defaultItemSkin", "defaultItemSkin", column.defaultItemSkin) }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>此栏目下的#{channel.itemName }的默认模板：</strong></td>
  <td>
  #{call defaultItemTemplate("defaultItemTemplate", "defaultItemTemplate", column.defaultItemTemplate) }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><b>此栏目下的#{channel.itemName }列表的排序方式：</b></td>
  <td><select name='itemListOrderType'>
   <option value='1' #{iif (column.itemListOrderType == 1, "selected", "") }>#{channel.itemName }ID（降序）</option>
   <option value='2' #{iif (column.itemListOrderType == 2, "selected", "") }>#{channel.itemName }ID（升序）</option>
   <option value='3' #{iif (column.itemListOrderType == 3, "selected", "") }>更新时间（降序）</option>
   <option value='4' #{iif (column.itemListOrderType == 4, "selected", "") }>更新时间（升序）</option>
   <option value='5' #{iif (column.itemListOrderType == 5, "selected", "") }>点击次数（降序）</option>
   <option value='6' #{iif (column.itemListOrderType == 6, "selected", "") }>点击次数（升序）</option>
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><b>此栏目下的#{channel.itemName }打开方式：</b></td>
  <td>
  <input name='itemOpenType' type='radio' value='0' #{iif (column.openType, "", "checked") }>在原窗口打开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='itemOpenType' type='radio' value='1' #{iif (column.openType, "checked", "") }>在新窗口打开
  </td>
 </tr>
</table>
</pub:template>



<pub:template name="temp_column_document">
<table width='100%' cellpadding='0' cellspacing='0' border='0'>
 <tr class='tdbg'>
  <td>
  栏目详细说明：(用于在栏目页详细介绍栏目信息，支持HTML)<br/>
  <textarea name='description' type='_moz' style="display:none; width:100%; height:380;">#{column.description@html}</textarea>
  <iframe style='display:' ID='editor' src='../editor/editor_new.jsp?channelId=#{channel.id}&showType=3&tContentID=description' 
    frameborder='1' scrolling='no' width='700' height='500' ></iframe>
  </td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
