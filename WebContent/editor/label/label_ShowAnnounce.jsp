<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@include file="element_label.jsp"%>

<pub:template name="main">
  #{call label_main_temp("ShowAnnounce", "公告显示标签") }
</pub:template>

<pub:template name="label_property_init">
    label.set("style", $("style1").checked ? "1" : "2");
    label.set("num", $("num").value);
</pub:template>

<pub:template name="label_param_setting">
  	<tr>
		<td>显示方向</td>
		<td>
			<input type="radio" name="style" id="style1" value="1" checked>横向
			<input type="radio" name="style" id="style2" value="2">纵向
		</td>
	</tr>	
	<tr>
		<td>最多显示公告条数</td>
		<td>
			<input type="text" name="num" id="num" value="5" size="5">个
		</td>
	</tr>
</pub:template>

<pub:process_template name="main"/>