<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@include file="element_label.jsp"%>

<pub:template name="main">
  #{call label_main_temp("ShowFriendSite", "友情链接显示标签") }
</pub:template>

<pub:template name="label_property_init">
    label.set("linkType", $("linkType1").checked ? "1" : "2");
    label.set("maxNum", $("maxNum").value);
    label.set("cols", $("cols").value);
    var showStyle = 2;
    if ($("showStyle1").checked) {
    	showStyle = 1;
    } else if($("showStyle3").checked) {
    	showStyle = 3;
    }
    label.set("showStyle", showStyle);   
    
    label.set("delay", $("delay").value);
    label.set("width", $("width").value);
    label.set("height", $("height").value); 
</pub:template>

<pub:template name="label_param_setting">
  	<tr>
		<td>链接类别</td>
		<td>
			<input type="radio" name="linkType" id="linkType1" value="1" >文字链接
			<input type="radio" name="linkType" id="linkType2" value="2" checked>图片链接
		</td>
	</tr>	
	<tr>
		<td>最多显示站点数</td>
		<td>
			<input type="text" name="maxNum" id="maxNum" value="14" size="5">个
		</td>
	</tr>
	<tr>
		<td>每行显示站点数</td>
		<td>
			<input type="text" name="cols" id="cols" value="7" size="5">个
		</td>
	</tr>
  	<tr>
		<td>显示方式</td>
		<td>
			<input type="radio" name="showStyle" id="showStyle1" value="1" onclick='enabelMarqueeParam()'>向上滚动
			<input type="radio" name="showStyle" id="showStyle2" value="2" checked  onclick='disableMarqueeParam()'>横向列表
			<input type="radio" name="showStyle" id="showStyle3" value="3" onclick='disableMarqueeParam()'>下拉列表
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
	<tr>
		<td>-滚动延迟时间</td>
		<td>
			<input type="text" name="delay" id="delay" value="20" size="5" disabled>毫秒
		</td>
	</tr>
	<tr>
		<td>-滚动区域宽度</td>
		<td>
			<input type="text" name="width" id="width" value="100%" size="5" disabled>
		</td>
	</tr>
	<tr>
		<td>-滚动区域高度</td>
		<td>
			<input type="text" name="height" id="height" value="40" size="5" disabled>
		</td>
	</tr>
</pub:template>

<pub:process_template name="main"/>