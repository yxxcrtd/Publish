<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@ taglib prefix="pub" uri="/WEB-INF/publish.tld"%>

<%@ include file="element_label.jsp" %>

<pub:template name="main">#{call label_main_temp("ShowLogo", "网站Logo显示标签") }
</pub:template>

<pub:template name="label_property_init">
 label.set("width", $("width").value);
 label.set("height", $("height").value);
</pub:template>

<pub:template name="label_param_setting">
<table width='100%'>
 <tr>
  <td>Logo宽度</td>
  <td><input type="text" id="width" name="width" value="170" /></td>
 </tr>
 <tr>
  <td>Logo高度</td>
  <td><input type="text" id="height" name="height" value="60" /></td>
 </tr> 
</table>
</pub:template>

<jsp:forward page="label_process.jsp" />