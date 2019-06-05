<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="utf-8"%>
<%@page import="com.chinaedustar.publish.model.*"%>
<%@page import="com.chinaedustar.publish.*"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%
	ParamUtil paramUtil = new ParamUtil(pageContext);
	Site site = paramUtil.getPublishContext().getSite();
	String installDir = site.getInstallDir();
	if (installDir == null || installDir.length() < 1) {
		installDir = "/";
	}
	int channelId = paramUtil.safeGetIntParam("channelId", 0);
	pageContext.setAttribute("channelId", channelId);
%>
<!-- 得到图片模块的所有频道。 -->
<pub:data var="channels" param="picture"
	provider="com.chinaedustar.publish.admin.ChannelsDataProvider" />
<!-- 定义下拉栏目列表的数据。 -->
<pub:data var="dropdown_columns" 
	provider="com.chinaedustar.publish.admin.ColumnsDropDownDataProvider"/>
<pub:data var="specials" param="-1"
	provider="com.chinaedustar.publish.admin.ChannelSpecialsDataProvider" />
	
<%@ include file="../../admin/element_column.jsp" %>	
<%@ include file="element_label.jsp"%>

<!-- 主执行模板定义 -->
<pub:template name="main">
<script Language="JavaScript">
function change_item(element){
    if(element.selectedIndex!=-1)
    var selectednumber = element.options[element.selectedIndex].value;

    if(selectednumber==1){
        objFiles.style.display="";
        
            $("common").src = "<%=installDir %>pic/images/Photo_common.gif"
            $("elite").src = "<%=installDir %>pic/images/Photo_elite.gif"
            $("ontop").src = "<%=installDir %>pic/images/Photo_ontop.gif"
        
    }
    else if (selectednumber==0)
    {
        objFiles.style.display="none";
    }
    else if (selectednumber==2)
    {
        objFiles.style.display="none";
    }
    else if (selectednumber>=3)
    {
        selectednumber = selectednumber - 1
        objFiles.style.display="";
        
            $("common").src = "<%=installDir %>pic/images/Photo_common" + selectednumber + ".gif"
            $("elite").src = "<%=installDir %>pic/images/Photo_elite" + selectednumber + ".gif"
            $("ontop").src = "<%=installDir %>pic/images/Photo_ontop" + selectednumber + ".gif"
        
    }
}
</script>
	#{call label_main_temp("ShowPicPhotoList","图片图文标签") }
</pub:template>

<pub:template name="label_property_init">
    label.set("channelId", $("channelId").value);
    label.set("columnId", $("columnId").value);
	label.set("specialId",$("specialId").value);
	label.set("includeChild",$("includeChild").checked);
	label.set("labelDesc",$("labelDesc").value);
	label.set("itemCou",$("itemCou").value);
	if ($("isHot").checked) {
		label.set("isHot", "true");
	}else{
		label.set("isHot", "false");
	}
	if ($("isElite").checked) {
		label.set("isElite", "true");
	}else{
		label.set("isElite", "false");
	}
	label.set("dateScope",$("DateNum").value);
	label.set("orderType",$("OrderType").value);
	//label.set("ShowType",$("ShowType").value);
	label.set("picWidth",$("ImgWidth").value);
	label.set("picHeight",$("ImgHeight").value);
	label.set("titleCharNum",$("TitleLen").value);
	label.set("contentNum",$("ContentLen").value);
if ($("showColumn").checked) {
		label.set("showColumn","true");
	}else{
		label.set("showColumn","false");
	}
	if ($("showPicArticle").checked) {
		label.set("showPicArticle","true");
	}else{
		label.set("showPicArticle","false");
	}
	if ($("showAuthor").checked) {
		label.set("showAuthor","true");
	}else{
		label.set("showAuthor","false");
	}
	label.set("lastModified",$("lastModified").value);
	if ($("showHits").checked) {
		label.set("showHits","true");
	}else{
		label.set("showHits","false");
	}
	if ($("showHot").checked) {
		label.set("showHot","true");
	}else{
		label.set("showHot","false");
	}
	if ($("showNew").checked) {
		label.set("showNew","true");
	}else{
		label.set("showNew","false");
	}
	label.set("colNum",$("Cols").value);
</pub:template>

<pub:template name="label_param_setting">
<form action='super_ShowPicPhoto.jsp' method='post' name='myform' id='myform'>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><strong>所属频道：</strong></td>
			<td height='25'>
			<select id='channelId' name='channelId' onChange='document.myform.submit();'>
				#{foreach channel in channels }
				<option value='#{channel.id }' #{iif (channelId == channel.id, "selected", "") }>#{channel.name }</option>
				#{/foreach }
				<option value='0' #{iif (channelId == 0, "selected", "") }>当前频道</option>
				<option value='-1' #{iif (channelId == -1, "selected", "") }>所有同类频道</option>
			</select>
			</td>
		</tr>
		#{if (channelId > 0) }
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
			<td height='25'>
			<select name='columnId' id="columnId" size='1'>
			#{call dropDownColumns(channelId, dropdown_columns) }
				<option value='0' style=''>当前栏目</option>
				<option value='-1' style=''>显示所有栏目</option>
				<option value='-2' style=''>未指定任何栏目</option>
			</select> 
			<input type='checkbox' id='includeChild' name='includeChild' value='1'>包含子栏目&nbsp;&nbsp;<br>

		</td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><strong>所属专题：</strong></td>
			<td height='25'>
			<select name='specialId' id='specialId'>
				<option value='0'>当前专题</option>
				#{foreach special in specials }
				<option value='#{special.id }'>#{special.name }（#{iif (special.channelId == 0, "全站", "本频道") }）</option>
				#{/foreach }
				<option value='-1'>不属于任何专题</option>	
				<option value='-2'>当前频道所有专题</option>	
				<option value='-3'>不考虑专题</option>				
			</select>
			</td>
		</tr>
		#{else }
		<tr style="display: none;"><td></td><td>
		<input type="hidden" value="-1" name="columnId" id="columnId" />
		<input type="checkbox" name='includeChild' value='1' id="includeChild" checked="checked" />
		<input type="hidden" name="specialId" value="0" id="specialId" />
		</td>
		</tr>
		#{/if }
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>标签说明：</strong></td>
		<td height='25'><INPUT TYPE='text' NAME='labelDesc' value=''
			id='labelDesc' size='15' maxlength='20'>&nbsp;&nbsp;<FONT
			style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</FONT></td>
	</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>图片数目：</strong></td>
		<td height='25'><input id='itemCou' name='itemCou' type='text' value=4 size='5'
			maxlength='3'> <font color='#FF0000'>如果为0，将显示所有图片。</font></td>
	</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>图片属性：</strong></td>
		<td height='25'><input name='IsHot' type='checkbox' id='IsHot'
			value='0'> 热门图片&nbsp;&nbsp;&nbsp;&nbsp; <input
			name='IsElite' type='checkbox' id='IsElite' value='0'> 推荐图片
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果都不选，将显示所有图片</font></td>
	</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>日期范围：</strong></td>
		<td height='25'>只显示最近 <input name='DateNum' type='text'
			id='DateNum' value=30 size='5' maxlength='3'>
		天内更新的图片&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的图片</font></td>
	</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>排序方法：</strong></td>
		<td height='25'><select name='OrderType' id='OrderType'>
			<option value='1'>图片ID（降序）</option>
			<option value='2'>图片ID（升序）</option>
			<option value='3'>更新时间（降序）</option>
			<option value='4'>更新时间（升序）</option>
			<option value='5'>点击次数（降序）</option>
			<option value='6'>点击次数（升序）</option>
		</select></td>
	</tr>
	<tr class='tdbg' style="display:none">
		<td height='25' align='right' class='tdbg5'><strong>显示样式：</strong></td>
		<td height='25'><select name='ShowType' id='ShowType'>
			<option value='1'>图片+标题+内容简介：上下排列</option>
			<option value='2'>（图片+标题：上下排列）+内容简介：左右排列</option>
			<option value='3'>图片+（标题+内容简介：上下排列）：左右排列</option>
			<option value='4'>输出DIV格式</option>
		</select></td>
	</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><b>图片设置：</b></td>
		<td height='25'>&nbsp;宽度： <input name='ImgWidth' type='text'
			id='ImgWidth' value=130 size='5' maxlength='3'>
		像素&nbsp;&nbsp;&nbsp;&nbsp; 高度： <input name='ImgHeight' type='text'
			id='ImgHeight' value=90 size='5' maxlength='3'> 像素</td>
	</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>标题最多字符数：</strong></td>
		<td height='25'><input name='TitleLen' type='text' id='TitleLen'
			value=30 size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;<font
			color='#FF0000'>若为0，则不显示标题；若为-1，则显示完整标题。字母算一个字符，汉字算两个字符。</font></td>
	</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>图片内容字符数：</strong></td>
		<td height='25'><input name='ContentLen' type='text'
			id='ContentLen' value=0 size='5' maxlength='3'>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果大于0，则显示指定字数的图片内容</font></td>
	</tr>
		<tr class='tdbg'>
			<td height='50' align='right' class='tdbg5'><strong>显示内容：</strong></td>
			<td height='50'>
			<table width='100%' border='0' cellpadding='1' cellspacing='2'>
				<tr>
					<td><input name='ShowColumn' type='checkbox'
						id='showColumn' value='0'>所属栏目</td>
					<td><input name='ShowPicArticle' type='checkbox'
						id='showPicArticle' value='0' >"图文"标志</td>
					<td><input name='ShowAuthor' type='checkbox' id='showAuthor'
						value='0'>作者</td>
					<td>更新时间 <select name='LastModified' id='lastModified'>
						<option value='0'>不显示</option>
						<option value='1'>年月日</option>
						<option value='2'>月日</option>
						<option value='3'>月-日</option>
						<option value='4'>年-月-日</option>
						<option value='5'>年-月-日 小时:分</option>
					</select></td>
				</tr>
				<tr>
					<td><input name='ShowHits' type='checkbox' id='showHits'
						value='0'>点击次数</td>
					<td><input name='ShowHot' type='checkbox' id='showHot'
						value='0'>热点图片标志</td>
					<td><input name='ShowNew' type='checkbox' id='showNew'
						value='0'>最新图片标志</td>
				</tr>
			</table>
			</td>
		</tr>
	<tr class='tdbg'>
		<td height='25' align='right' class='tdbg5'><strong>每行显示图片数：</strong></td>
		<td height='25'><select name='Cols' id='Cols'>
			<option value='1'>1</option>
			<option value='2'>2</option>
			<option value='3'>3</option>
			<option value='4'>4</option>
			<option value='5'>5</option>
			<option value='6'>6</option>
			<option value='7'>7</option>
			<option value='8'>8</option>
			<option value='9'>9</option>
			<option value='10'>10</option>
			<option value='11'>11</option>
			<option value='12'>12</option>
		</select> &nbsp;&nbsp;&nbsp;&nbsp;超过指行列数就会换行</td>
	</tr>
</form>
</pub:template>

<pub:process_template name="main"/>