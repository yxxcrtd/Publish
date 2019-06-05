<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@ taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="com.chinaedustar.publish.admin.SuperLabel"
%><%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initFriendSitePage();
  
%>

<%@ include file="element_label.jsp" %>

<pub:template name="main">
  #{call label_main_temp("ShowFriendSite", "友情链接显示标签") }
</pub:template>

<pub:template name="label_property_init">
  setLabelProp('linkType');
  setLabelProp("maxNum");
  setLabelProp("cols");
  setLabelProp("tableWidth");
  setLabelProp("showStyle");
  setLabelProp("kindId");
  setLabelProp("specialId");
  setLabelProp("commend");
  setLabelProp("orderBy");
    
  setLabelProp("delay");
  setLabelProp("width");
  setLabelProp("height");
</pub:template>

<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
 <tr class='tdbg'>
  <td>链接类别</td>
  <td>
   <input type="radio" name="linkType" id="linkType1" value='' __def_value='' />任何链接   
   <input type="radio" name="linkType" id="linkType2" value='1'  __def_value='' >文字链接
   <input type="radio" name="linkType" id="linkType3" value='2' __def_value='' checked>图片链接
  </td>
 </tr> 
 <tr class='tdbg'>
  <td>最多显示站点数</td>
  <td>
   <input type="text" name="maxNum" id="maxNum" value="14" size="5" __def_value='' /> 个
  </td>
 </tr>
 <tr class='tdbg'>
  <td>每行显示站点数</td>
  <td>
   <input type="text" name="cols" id="cols" value="7" size="5" __def_value='' /> 个
  </td>
 </tr>
 <tr class='tdbg'>
  <td>显示宽度</td>
  <td>
   <input type="text" name="tableWidth" id="tableWidth" value="100%" size="5" __def_value='100%' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td>显示方式</td>
  <td>
   <input type="radio" name="showStyle" id="showStyle1" value="1" __def_value='2' onclick='enabelMarqueeParam()'>向上滚动
   <input type="radio" name="showStyle" id="showStyle2" value="2" __def_value='2' checked  onclick='disableMarqueeParam()'>横向列表
   <input type="radio" name="showStyle" id="showStyle3" value="3" __def_value='2' onclick='disableMarqueeParam()'>下拉列表
   <script type="text/javascript">
    function enabelMarqueeParam() {
     $("delay").disabled = false;
     $("width").disabled = false;
     $("height").disabled = false;
    }
    function disableMarqueeParam() {
     $("delay").disabled = true;
     $("width").disabled = true;
     $("height").disabled = true;
    }
   </script>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;&nbsp;滚动延迟时间</td>
  <td>
   <input type="text" name="delay" id="delay" value="20" __def_value='20' size="5" disabled />毫秒
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;&nbsp;滚动区域宽度</td>
  <td>
   <input type="text" name="width" id="width" value="100%" __def_value='100%' size="5" disabled />
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;&nbsp;滚动区域高度</td>
  <td>
   <input type="text" name="height" id="height" value="40" __def_value='40' size="5" disabled />
  </td>
 </tr>
 <tr class='tdbg'>
  <td>所属类别</td>
  <td>
   <select name="kindId" id="kindId" __def_value=''>
    <option value=''>任何类别(缺省)</option>
    #{foreach kind in fs_kinds }
    <option value="#{kind.id }">#{kind.name }</option>
    #{/foreach }
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>所属专题</td>
  <td>
   <select name="specialId" id="specialId" __def_value=''>
    <option value=''>任何专题(缺省)</option>
    #{foreach special in fs_specials }
    <option value="#{special.id }">#{special.name }</option>
    #{/foreach }
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>显示推荐</td>
  <td>
   <input type="radio" name="commend" id="commend" value="" __def_value='' checked />所有
   <input type="radio" name="commend" id="commend" value="true" __def_value='' />推荐的
   <input type="radio" name="commend" id="commend" value="false" __def_value='' />未推荐的
  </td>
 </tr>
 <tr class='tdbg'>
  <td>排序方式</td>
  <td>
   <select name="orderBy" id="orderBy" __def_value='' >
    <option value='' _value='orderId asc' selected>排序ID升序(缺省)</option>
    <option value='1' _value="orderId desc">排序ID降序</option>
    <option value='2' _value="id asc" >友情链接ID升序</option>
    <option value='3' _value="id desc">友情链接ID降序</option>
    <option value='4' _value="stars asc" >网站评分等级升序</option>
    <option value='5' _value="stars desc" >网站评分等级降序</option>
   </select>
  </td>
 </tr>
</table>
</pub:template>

<jsp:forward page="label_process.jsp" />
