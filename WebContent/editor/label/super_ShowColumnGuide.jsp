<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SuperLabel"
%><%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initSuperItemPage();
  
%>

<%@ include file="../../admin/element_column.jsp" %> 
<%@ include file="element_label.jsp"%>
<%@ include file="super_label_common.jsp" %>

<!-- 主执行模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowColumnGuide", "显示频道栏目导航图") }
</pub:template>


<pub:template name="label_property_init">
  setLabelProp('channelId');
  setLabelProp('openType');

  if (useCustom.checked) {
    label.template = template.value;
  }
</pub:template>

<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
 <!-- 选择频道 channelId -->
 <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><nobr><strong>所属频道：</strong></nobr></td>
   <td height='25'>
  <form action='super_ShowColumnGuide.jsp' method='post' name='myform' id='myform' style='margin:0px;'>
   <select id='ChannelId' name='channelId' __def_value='0' onchange='document.myform.submit();'>
    <option value='0' #{iif (channelId == 0, "selected", "") }>当前频道(缺省)</option>
   #{foreach channel in channel_list }
    <option value='#{channel.id }' #{iif (channelId == channel.id, "selected", "") }>#{channel.name }</option>
   #{/foreach }
   </select>
   <input type='hidden' name='width' value='800' />
   <input type='hidden' name='height' value='700' />
  </form>
   </td>
 </tr>
  
 <!-- openType -->
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>打开方式：</strong></td>
  <td height='25'><select name='OpenType' id='openType' __def_value=''>
   <option value=''>在原窗口打开</option>
   <option value='1'>在新窗口打开</option>
   </select>
  </td>
 </tr>
 
 <!-- 自己定制内部内容 -->
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>定制:</strong></td>
  <td height='25'>
   <input type='checkbox' name='UseCustom' id='useCustom' /> 在标签内使用如下定制的模板<br/>
   <textarea name='Template' id='template' rows='30' cols='110'><%@ include file='showColumnGuide.template.html' %></textarea>
   <br/>说明：在 ShowColumnGuide 内部提供有 column_list 数据，该数据是所选频道第一级栏目的列表。<br/>
    第一级子栏目的 column.childColumns 可以访问到该栏目下的子栏目。 (仅在此标签中有此数据支持)<br/>
    您也可以通过修改内建标签 '.builtin.showcolumnguide' 或新建一个并指定 ShowColumnGuide 的
     template='.builtin.showcolumnguide' 属性来改变子栏目导航的结果。
  </td>
 </tr>
 
</table>
</pub:template>

<%-- 执行模板 --%>
<pub:process_template name="main"/>
