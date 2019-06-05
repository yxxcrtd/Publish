<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@include file="element_label.jsp"%>

<!-- 提供频道 -->
<pub:data var="channel" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />

<!-- 提供所有频道对象 -->
<pub:data var="channel_list" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelListDataProvider" />
	
<pub:template name="main">
  #{call label_main_temp("ShowAnnounce", "公告显示标签") }
</pub:template>

<pub:template name="label_property_init">
	label.set("channelId", $("channelId").value);
    label.set("style", $("style1").checked ? "1" : "2");
    label.set("num", $("num").value);
    label.set("showAuthor", $("showAuthor1").checked ? "true" : "false");
    label.set("showDate", $("showDate1").checked ? "true" : "false");
    label.set("maxChar", $("maxChar").value);
</pub:template>

<pub:template name="label_param_setting">
  	<tr>
		<td>所属频道</td>
		<td>
			<select name="channelId" id="channelId">
			    <option value='-1'>频道共用公告</option>
			    <option value='0' #{iif(channelId == 0, "selected", "") }>网站首页公告</option>
			    #{foreach channel in channel_list}
			    <option value='#{channel.id}'  #{iif(channelId == channel.id, "selected", "") }>#{channel.name}</option>
			    #{/foreach}
			</select>     
		</td>
	</tr>
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
	<tr>
		<td>是否显示作者</td>
		<td>
			<input type="radio" name="showAuthor" id="showAuthor1" checked>是
			<input type="radio" name="showAuthor" id="showAuthor2">否
		</td>
	</tr>
	<tr>
		<td>是否显示日期</td>
		<td>
			<input type="radio" name="showDate" id="showDate1" checked>是
			<input type="radio" name="showDate" id="showDate2">否
		</td>
	</tr>
	<tr>
		<td>公告内容最多字符数</td>
		<td>
			<input type="text" name="maxChar" id="maxChar" value="50" size="5">个
		</td>
	</tr>
</pub:template>

<pub:process_template name="main"/>