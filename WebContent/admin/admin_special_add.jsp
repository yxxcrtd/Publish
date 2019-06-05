<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SpecialManage"
%><%
  // 获得管理所使用的数据。
  SpecialManage admin_data = new SpecialManage(pageContext);
  admin_data.initEditPage();
  
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>增加/修改专题</title>
</head>
<body>
<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="contentTabs" purpose="">
 <pub:tab name="baseInfo" text="基本设置" template="temp_baseInfo" default="true" />
</pub:tabs>

<%@include file="tabs_tmpl2.jsp" %>
<%@include file="element_special.jsp" %>

<pub:template name="main">
 #{call special_navigator(iif (special.id == 0, "新增专题", "修改专题")) }<br />
 #{call your_position }

<form name='form1' method='post' action='admin_special_action.jsp'>
 #{call tab_js }
 #{call tab_header(contentTabs) }
 #{call tab_content(contentTabs) }

<div align="center">
 <input type='hidden' name='command' value='save' />
 <input name='channelId' type='hidden' id='channelId' value='#{special.channelId }' />
 <input name='specialId' type='hidden' id='specialId' value='#{special.id }' />
 <input name='specialOrder' type='hidden' id='specialOrder' value='#{special.specialOrder }' />
 
 <input type='submit' name='Submit' value=' #{iif (special.id > 0, "保 存", "添 加") } ' />&nbsp;&nbsp;
 <input name='Cancel' type='button' id='Cancel' value=' 取 消 ' style='cursor:hand;'
 onclick="window.location.href='admin_special_list.jsp?channelId=#{special.channelId }'" />
</div>
</form>
</pub:template>


<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>您现在的位置：<a href='admin_special_list.jsp?channelId=#{channel.id }'>专题管理</a>&nbsp;&gt;&gt;&nbsp;添加专题</td>
 </tr>
</table>
</pub:template>



<!-- 专题基本信息 -->
<pub:template name="temp_baseInfo">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' >
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>专题名称：</strong></td>
  <td class='tdbg'>
 <input name='specialName' type='text' id='name' size='49' maxlength='30' value="#{special.name@html}" />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>专题目录：</strong><br>
 只能是英文，不能带空格或“\”、“/”等符号。创建之后不能更改。</td>
  <td class='tdbg'><input name='specialDir' type='text' #{if special.id != 0}readonly='readonly' #{/if}
   id='specialDir' size='49' maxlength='30' value="#{special.specialDir }" />&nbsp;</td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>专题图片：</strong></td>
  <td class='tdbg'><input name='specialPicUrl' type='text'
  id='SpecialPicUrl' size='49' maxlength='200' value="#{special.logo@html}" />&nbsp;</td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>打开方式：</strong></td>
  <td>
   <input name='openType' type='radio' value='0' #{iif(special.openType==0, "checked", "") }>在原窗口打开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input name='openType' type='radio' value='1' #{iif(special.openType==1, "checked", "") }>在新窗口打开
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>是否为推荐专题：</strong></td>
  <td>
   <input name='isElite' type='radio' value='true' #{iif (special.isElite, "checked", "") }>是&nbsp;&nbsp;&nbsp;&nbsp;
   <input name='isElite' type='radio' value='false' #{iif (special.isElite, "", "checked") }>否
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>专题提示：</strong><br>
 鼠标移至专题名称上时将显示设定的提示文字（不支持HTML）</td>
  <td class='tdbg'><textarea name='tips' cols='60' rows='4'
  id='tips' type="_moz">#{special.tips@html }</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>专题说明：</strong><br>
 用于专题页对专题进行说明（支持HTML）</td>
  <td class='tdbg'><textarea name='description' cols='60' rows='4'
  id='Readme' type="_moz">#{special.description@html }</textarea></td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>每页显示的文章数：</strong></td>
  <td>
 <select name='maxPerPage'>
  #{foreach size in range(5,101) }
 <option value='#{size }' #{if size == special.maxPerPage }selected#{/if }>#{size}</option>
  #{/foreach }
 </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>默认配色风格：</strong><br>
 相关模板中包含CSS、颜色、图片等信息</td>
  <td class='tdbg'>
   <select name='skinId' id='SkinId'>
 <option value='0'  #{iif(special.skinId == 0, "selected", "") }>使用系统的默认皮肤</option>
  #{foreach skin in skin_list }
   <option value='#{skin.id }' #{iif(special.skinId == skin.id, "selected", "") }>#{skin.name }#{if(skin.isDefault)}（默认）#{/if }</option>
  #{/foreach }
 </select></td>
 </tr>
 <tr class='tdbg'>
  <td width='350' class='tdbg5'><strong>版面设计模板：</strong><br>
 相关模板中包含了版面设计的版式等信息，如果是自行添加的设计模板，可能会导致“专题配色风格”失效。</td>
  <td class='tdbg'>
   <select name='templateId' id='TemplateId'>
  <option value='0' #{iif(special.templateId == 0,"selected","") }>系统默认专题页面模板</option>
 #{foreach templ in special_templ_list}
  <option value='#{templ.id }' #{iif(special.templateId == templ.id, "selected", "") }>#{templ.name }#{if(templ.isDefault)}（默认）#{/if }</option>
 #{/foreach }
 </select>
  </td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
