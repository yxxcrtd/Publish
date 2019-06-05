<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8"%>
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
<!-- 得到文章模块的所有频道。 -->
<pub:data var="channels" param="article"
	provider="com.chinaedustar.publish.admin.ChannelsDataProvider" />
<!-- 定义下拉栏目列表的数据。 -->
<pub:data var="dropdown_columns"
	provider="com.chinaedustar.publish.admin.ColumnsDropDownDataProvider" />
<pub:data var="specials" param="-1"
	provider="com.chinaedustar.publish.admin.ChannelSpecialsDataProvider" />

<%@ include file="../../admin/element_column.jsp" %>	
<%@ include file="element_label.jsp"%>
<!-- 主模板定义 -->
<pub:template name="main">
<script Language="JavaScript">
function change_item(element){
    if(element.selectedIndex!=-1)
    var selectednumber = element.options[element.selectedIndex].value;

    if(selectednumber==1){
        objFiles.style.display="";
        
            $("common").src = "<%=installDir %>news/images/article_common.gif";
            $("elite").src = "<%=installDir %>news/images/article_elite.gif";
            $("ontop").src = "<%=installDir %>news/images/article_ontop.gif";
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
        $("common").src = "<%=installDir %>news/images/article_common" + selectednumber + ".gif";
        $("elite").src = "<%=installDir %>news/images/article_elite" + selectednumber + ".gif";
        $("ontop").src = "<%=installDir %>news/images/article_ontop" + selectednumber + ".gif";
    }
}
</script>
	#{call label_main_temp("ShowSlidePicArticle","幻灯片文章标签") }
</pub:template>

<pub:template name="label_property_init">
    label.set("channelId", $("channelId").value);
    label.set("columnId", $("columnId").value);
	label.set("specialId",$("specialId").value);
	label.set("includeChild",$("includeChild").checked);
	label.set("num",$("Num").value);
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
	label.set("ImgWidth",$("ImgWidth").value);
	label.set("ImgHeight",$("ImgHeight").value);
	label.set("titleCharNum",$("TitleLen").value);
	label.set("iTimeOut",$("iTimeOut").value);
	label.set("effectID",$("effectID").value);
</pub:template>

<pub:template name="label_param_setting">
<form action='super_ShowSlidePicArticle.jsp' method='post' name='myform' id='myform'>
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
				<option value='0'>当前频道</option>
				#{foreach special in specials }
				<option value='#{special.id }'>#{special.name }（#{iif (special.channelId == 0, "全站", "本频道") }）</option>
				#{/foreach }
				<option value='-1'>不属于任何专题</option>				
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
			<td height='25'><INPUT TYPE='text' NAME='lableExplain' value=''
				id='lableExplain' size='15' maxlength='20'>&nbsp;&nbsp;<FONT
				style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</FONT></td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><strong>文章数目：</strong></td>
			<td height='25'><input id='Num' name='Num' type='text' value=4 size='5'
				maxlength='3'> <font color='#FF0000'>如果为0，将显示所有文章。</font></td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><strong>文章属性：</strong></td>
			<td height='25'><input name='IsHot' type='checkbox' id='IsHot'
				value='0'> 热门文章&nbsp;&nbsp;&nbsp;&nbsp; <input
				name='IsElite' type='checkbox' id='IsElite' value='0'> 推荐文章
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果都不选，将显示所有文章</font></td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><strong>日期范围：</strong></td>
			<td height='25'>只显示最近 <input name='DateNum' type='text'
				id='DateNum' value=30 size='5' maxlength='3'>
			天内更新的文章&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的文章</font></td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><strong>排序方法：</strong></td>
			<td height='25'><select name='OrderType' id='OrderType'>
				<option value='1'>文章ID（降序）</option>
				<option value='2'>文章ID（升序）</option>
				<option value='3'>更新时间（降序）</option>
				<option value='4'>更新时间（升序）</option>
				<option value='5'>点击次数（降序）</option>
				<option value='6'>点击次数（升序）</option>
			</select></td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><b>首页图片设置：</b></td>
			<td height='25'>&nbsp;宽度： <input name='ImgWidth' type='text'
				id='ImgWidth' value=130 size='5' maxlength='3'>
			像素&nbsp;&nbsp;&nbsp;&nbsp; 高度： <input name='ImgHeight' type='text'
				id='ImgHeight' value=90 size='5' maxlength='3'> 像素</td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><b>标题/名称长度</b></td>
			<td height='25'><input name='TitleLen' type='text' id='TitleLen'
				value=30 size='5' maxlength='3'> 个字符</td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><b>效果变换间隔时间</b></td>
			<td height='25'><input name='iTimeOut' type='text' id='iTimeOut'
				value=5000 size='5' maxlength='5'>&nbsp;&nbsp;<font
				color=blue><b>毫秒为单位</b></td>
		</tr>
		<tr class='tdbg'>
			<td height='25' align='right' class='tdbg5'><b>图片转换效果</b></td>
			<td height='25'><input name='effectID' type='text' id='effectID'
				value=-1 size='5' maxlength='3'>&nbsp;&nbsp;<font color=blue><b>-1表示随机效果，0至23指定某一种特效</b></td>
		</tr>
	</form>
</pub:template>

<pub:process_template name="main" />
