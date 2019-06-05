<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>会员控制面板</title>
<link href="defaultSkin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="stm31.js"></script>
<link href='../admin/admin_style.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="../admin/main.js"></script>
</head>
<body>
<!-- 当前登录的会员。 -->
<pub:data var="user" param="login"
	provider="com.chinaedustar.publish.admin.UserDataProvider" />
<pub:data var="channel" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />
<!-- 定义下拉栏目列表的数据。 -->
<pub:data var="dropdown_columns" 
	provider="com.chinaedustar.publish.admin.ColumnsDropDownDataProvider" 
	scope="page" />

<!-- 文章对象的数据提供者，如果是新建文章，则产生一个默认属性的文章对象。 -->
<pub:data var="article" 
	provider="com.chinaedustar.publish.admin.ArticleDataProvider" />

<%@ include file="element_user.jsp" %>
<%@ include file="../admin/element_article.jsp" %>
<%@ include file="../admin/element_column.jsp" %>
<pub:template name="main">
	#{call tmpl_top }
	<table width="756" align="center" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td>
		#{call tmpl_body }
		</td>
		</tr>
	</table>

	<br />
	#{call tmpl_bottom }
</pub:template>

<!-- 主体部分。 -->
<pub:template name="tmpl_body">
<table width="100%" align="center" border="0" cellpadding="5" cellspacing="0" class="user_box">
  <tr>
    <td class="user_righttitle"><img src="images/point2.gif" align="absmiddle">
    您现在的位置：<a href='../'>#{site.name }</a> >> <a href='index.jsp'>会员中心</a> >> 信息管理 >> <a href="user_article_list.jsp?channelId=#{channel.id }">#{channel.name }</a> >> 文章中心</td>
  </tr>
  <tr>
    <td height="200" valign='top'>
        <TABLE align=center>
	    <TBODY>
	    <TR vAlign=top align=middle>
	      <TD width=90>
	      #{if (user.inputer) }
	      <A 
	        href="user_article_add.jsp?channelId=#{channel.id }"><IMG 
	        src="images/article_add.gif" 
	        align=absMiddle border=0><BR>添加文章</A>
	      #{/if }
	        </TD>
	      <TD width=90><A 
	        href="user_article_list.jsp?channelId=#{channel.id }"><IMG 
	        src="images/article_all.gif" 
	        align=absMiddle border=0><BR>所有文章</A></TD>
	      <TD width=90><A 
	        href="user_article_list.jsp?channelId=#{channel.id }&amp;status=-1"><IMG 
	        src="images/article_draft.gif" 
	        align=absMiddle border=0><BR>草 稿</A></TD>
	      <TD width=90><A 
	        href="user_article_list.jsp?channelId=#{channel.id }&amp;status=0"><IMG 
	        src="images/article_unpassed.gif" 
	        align=absMiddle border=0><BR>待审核的文章</A></TD>
	      <TD width=90><A 
	        href="user_article_list.jsp?channelId=#{channel.id }&amp;status=1"><IMG 
	        src="images/article_passed.gif" 
	        align=absMiddle border=0><BR>已审核的文章</A></TD>
	      <TD width=90><A 
	        href="user_article_list.jsp?channelId=#{channel.id }&amp;status=-2"><IMG 
	        src="images/article_reject.gif" 
	        align=absMiddle border=0><BR>未被采用的文章</A></TD></TR>
	     </TBODY>
    </TABLE>
    #{if (user.inputer) }
    #{call tmpl_body_content }
    #{else }
    <div align="center"><p>不允许投稿，无法进行内容的编辑。</p></div>
    #{/if }
	</td>
</tr>
</table>
</pub:template>

<pub:template name="tmpl_body_content">
<script language="javascript">
// 验证表单提交。
function checkForm() {
	var CurrentMode=editor.CurrentMode;
	if (CurrentMode==0){
		setOpenNew();
		document.myform.content.value=editor.HtmlEdit.document.body.innerHTML; 
	} else if (CurrentMode==1){
		setOpenNew();
		document.myform.content.value=editor.HtmlEdit.document.body.innerText;
	} else {
		alert('预览状态不能保存！请先回到编辑状态后再保存');
		return false;
	}
	
	if (document.myform.title.value == '') {
		alert("#{channel.itemName}标题不能为空！");
		document.myform.title.focus();
		return false;
	}	
	
	if (document.myform.keywords.value == ''){
	    alert('关键字不能为空！');
	    document.myform.keywords.focus();
	    return false;
	}	

	if(document.myform.UseLinkUrl.checked == true){
	    if (document.myform.LinkUrl.value == '' || document.myform.LinkUrl.value == 'http://'){
	    	alert('请输入转向链接的地址！');
	      	document.myform.LinkUrl.focus();
	      	return false;
	    }
	} else {
	    if (document.myform.content.value==''){
	      	alert('#{channel.itemName}内容不能为空！');
	      	editor.HtmlEdit.focus();
	      	return false;
	    }
	}
	var obj=document.myform.columnId;
	var iCount=0;
	for(var i=0;i<obj.length;i++){
		if(obj.options[i].selected==true){
	      	iCount=iCount+1;
	      	if(obj.options[i].value==''){
	        	alert('#{channel.itemName}所属栏目不能指定为外部栏目！');
	        	obj.focus();
	        	return false;
	      	}
	      	if(obj.options[i].selected==true&&obj.options[i].value=='0'){
	        	alert('指定的栏目不允许添加#{channel.itemName}！只允许在其子栏目中添加#{channel.itemName}。');
	        	obj.focus();
	        	return false;
	      	}
	    }
	}
	if (iCount==0){
	    alert('请选择所属栏目！');
	    obj.focus();
	    return false;
	}
	return true;
}

// 将内容页中所有的链接的目标窗口都设置为弹出新窗口。
function setOpenNew() {
	var links = editor.HtmlEdit.document.getElementsByTagName("A");
	for (var i = 0; i < links.length; i++) {
		links[i].setAttribute("target", "_blank");
	}
}

</script>
<form method='post' name='myform' onSubmit='return checkForm();'
	action='admin_article_save.jsp' target='_self'>
<table width='100%' border='0' align='center' cellpadding='5'
	cellspacing='0' class='border'>
	<tr class="title">
		<td align="center" height="22"><strong>#{iif (article.id > 0, "修改", "添加") }文章</strong></td>
	</tr>
	<tr align='center'>
		<td class='tdbg' height='200' valign='top'>
		<table width='98%' border='0' cellpadding='2' cellspacing='1'
			bgcolor='#FFFFFF'>

#{call temp_baseInfo }

		</table>
		</td>
	</tr>
</table>
<p align='center'>
	<input name='Action' type='hidden' id='Action' value='SaveAdd' /> 
	<input name='channelId' type='hidden' id='channelId' value='#{article.channelId }' />
	<input name='articleId' type="hidden" value="#{article.id }" />
	<input name='inputer' type="hidden" value="#{article.inputer@html }" />
	<input name='itemClass' type="hidden" value="news" />
	<input name='usn' type="hidden" value="#{article.usn }" />
	<input name='editorName' type="hidden" value="#{article.editor@html }" />
	<input name='staticPageUrl' type="hidden" value="#{article.staticPageUrl }" />
	<input name='add' type='submit' id='Add'
	value=' #{iif (article.id > 0, "修 改", "添 加") } '
	onClick="document.myform.action='user_article_save.jsp';document.myform.target='_self';"
	style='cursor:hand;' /> &nbsp; 
	<input name='Preview' type='submit'id='preview' value=' 预 览 '
	onClick="document.myform.action='user_article_preview.jsp';document.myform.target='_blank';" style='cursor:hand;' />
	<input name='Cancel' type='button' id='Cancel'	value=' 取 消 '	onClick="history.go(-1);"	style='cursor:hand;' /></p>
<br />
</form>
</pub:template>

<!-- 基本信息 -->
<pub:template name="temp_baseInfo">
	<tr class='tdbg'>
		<td width='120' align='right' class='tdbg5'>所属栏目：</td>
		<td>
		<select name="columnId">
		<!-- <option value="#{channel.rootColumnId }" parentPath="/">不指定任何栏目</option> -->
		#{call dropDownColumns(article.columnId, dropdown_columns, true) }
		</select>
		 &nbsp; &nbsp; &nbsp; &nbsp; <font color='#FF0000'>
		<strong>注意：</strong> </font> <font color='#0000FF'>不能指定为外部栏目，栏目颜色为灰时不能添加#{channel.itemName }</font>
		</td>
	</tr>
	<tr class='tdbg'>
		<td width='120' align='right' class='tdbg5'>#{channel.itemName}标题：</td>
		<td>
		<table width='100%' border='0' cellpadding='0' cellspacing='2'>
			<tr style="display: none;">
				<td width='64' class='tdbg5'>简短标题：</td>
				<td><select name='includePic'>
					<option value='0' #{iif(article.includePic==0, "selected", "") }></option>
					<option value='1' #{iif(article.includePic==1, "selected", "") }>[图文]</option>
					<option value='2' #{iif(article.includePic==2, "selected", "") }>[组图]</option>
					<option value='3' #{iif(article.includePic==3, "selected", "") }>[推荐]</option>
					<option value='4' #{iif(article.includePic==4, "selected", "") }>[注意]</option>
				</select> <input name='shortTitle' type='text' id='shortTitle' value='#{article.shortTitle@html }' size='56'
					autocomplete='off' maxlength='255' class='bginput'/>
					<select
					name='titleFontColor' id='titleFontColor'>
					<option value='' selected='selected'>颜色</option>
					<option value='' #{iif(article.titleFontColor=='', "selected", "") }>默认</option>
					<option value='#000000' style='background-color:#000000' #{iif(article.titleFontColor=='#000000', "selected", "") }>
					</option>
					<option value='#FFFFFF' style='background-color:#FFFFFF' #{iif(article.titleFontColor=='#FFFFFF', "selected", "") }>
					</option>
					<option value='#008000' style='background-color:#008000' #{iif(article.titleFontColor=='#008000', "selected", "") }>
					</option>
					<option value='#800000' style='background-color:#800000' #{iif(article.titleFontColor=='#800000', "selected", "") }>
					</option>
					<option value='#808000' style='background-color:#808000' #{iif(article.titleFontColor=='#808000', "selected", "") }>
					</option>
					<option value='#000080' style='background-color:#000080' #{iif(article.titleFontColor=='#000080', "selected", "") }>
					</option>
					<option value='#800080' style='background-color:#800080' #{iif(article.titleFontColor=='#800080', "selected", "") }>
					</option>
					<option value='#808080' style='background-color:#808080' #{iif(article.titleFontColor=='#808080', "selected", "") }>
					</option>
					<option value='#FFFF00' style='background-color:#FFFF00' #{iif(article.titleFontColor=='#FFFF00', "selected", "") }>
					</option>
					<option value='#00FF00' style='background-color:#00FF00' #{iif(article.titleFontColor=='#00FF00', "selected", "") }>
					</option>
					<option value='#00FFFF' style='background-color:#00FFFF' #{iif(article.titleFontColor=='#00FFFF', "selected", "") }>
					</option>
					<option value='#FF00FF' style='background-color:#FF00FF' #{iif(article.titleFontColor=='#FF00FF', "selected", "") }>
					</option>
					<option value='#FF0000' style='background-color:#FF0000' #{iif(article.titleFontColor=='#FF0000', "selected", "") }>
					</option>
					<option value='#0000FF' style='background-color:#0000FF' #{iif(article.titleFontColor=='#0000FF', "selected", "") }>
					</option>
					<option value='#008080' style='background-color:#008080' #{iif(article.titleFontColor=='#008080', "selected", "") }>
					</option>
				</select> <select name='titleFontType' id='titleFontType'>
					<option value='0' #{iif(article.titleFontType==0, "selected", "") }>字形</option>
					<option value='1' #{iif(article.titleFontType==1, "selected", "") }>粗体</option>
					<option value='2' #{iif(article.titleFontType==2, "selected", "") }>斜体</option>
					<option value='3' #{iif(article.titleFontType==3, "selected", "") }>粗+斜</option>
					<option value='4' #{iif(article.titleFontType==4, "selected", "") }>规则</option>
				</select>
				<div id="satitle" style='display:none'></div>
				</td>
			</tr>
			<tr>
				<td width='64' class='tdbg5'>完整标题：</td>
				<td><input name='title' type='text' id='title'
					value='#{article.title@html }' size='80' maxlength='500' /></td>
			</tr>
			<tr style="display: none;">
				<td width='64' class='tdbg5'>副 标 题：</td>
				<td>
				<input name='subheading' type='text' id='subheading'
					size='80' maxlength='255' value="#{article.subheading@html }" /></td>
			</tr>
			<tr style="display: none;">
				<td></td>
				<td><input name='commentFlag' type='checkbox' id='commentFlag' value='1'  #{iif(article.commentFlag == 1, "checked", "") }/> 显示#{channel.itemName}列表时在标题旁显示评论链接

				&nbsp; &nbsp; &nbsp; &nbsp; <input type='button'
					name='checksame' value='检查重复标题'
					onclick="showModalDialog('admin_check_same_title.jsp?channelId=#{channel.id }&title='+document.myform.title.value,'checksame','dialogWidth:350px; dialogHeight:250px; help: no; scroll: no; status: no');" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr class='tdbg'>
		<td width='120' align='right' class='tdbg5'>关键字：</td>
		<td>
		<div style="clear: both;"><input name='keywords' type='text'
			style="clear:both" id='keywords' value='#{article.keywords@html }' autocomplete='off'
			size='50' maxlength='255' /> <font
			color='#FF0000'> * </font>#{foreach keyword in keyword_list }<font 
			color='blue'>【<font 	color='green'
			onclick="byId('keywords').value+=(byId('keywords').value==''?'':'|')+'#{keyword }'"
			style="cursor:hand;">#{keyword }</font>】
			#{/foreach }【<font color='green'
			onclick="window.open('../admin/admin_keyword_choose.jsp?channelId=#{channel.id }', 'KeywordList', 'width=600,height=450,resizable=0,scrollbars=yes');"
			style="cursor:hand;">更多</font>】 </font></div>
		<div id="skey" style='display:none'></div>
		<font color='#0000FF'> 用来查找相关#{channel.itemName}，可输入多个关键字，中间用 <font
			color='#FF0000'> “|” </font> 隔开。不能出现&quot;'&amp;?;:()等字符。 </font></td>
	</tr>
	<tr class='tdbg'>
		<td width='120' align='right' class='tdbg5'>#{channel.itemName}作者：</td>
		<td>
		<div style="clear: both;"><input name='author' type='text'
			id='author' value='#{article.author@html }'
			autocomplete='off' size='50' maxlength='100' /> #{foreach author in author_list }<font
			color='blue'>【<font color='green'  onclick="document.myform.author.value='#{author }'" 
			style="cursor:hand;">#{author }</font>】#{/foreach }【<font color='green'
			onclick="window.open('../admin/admin_item_author.jsp?channelId=#{channel.id }', 'AuthorList', 'width=600,height=450,resizable=0,scrollbars=yes');"
			style="cursor:hand;">更多</font>】 </font></div>
		<div id="sauthor" style='display:none'></div>
		</td>
	</tr>
	<tr class='tdbg'>
		<td width='120' align='right' class='tdbg5'>#{channel.itemName}来源：</td>
		<td>
		<div style="clear: both;"><input name='source' type='text'
			id='souce' value='#{article.source@html }' autocomplete='off' size='50'
			maxlength='100' /> #{foreach source in source_list }<font
			color='blue'> 【<font color='green'	onclick="document.myform.source.value='#{source }'"
			style="cursor:hand;">#{source }</font>】#{/foreach }【<font color='green'
			onclick="window.open('../admin/admin_item_source.jsp?channelId=#{channel.id }', 'CopyFromList', 'width=600,height=450,resizable=0,scrollbars=yes');"
			style="cursor:hand;">更多</font>】 </font></div>
		<div id="scopyfrom" style='display:none'></div>
		</td>
	</tr>
	<tr class='tdbg' style="display: none;">
		<td width='120' align='right' class='tdbg5'><font
			color='#FF0000'> 转向链接： </font></td>
		<td><input name='LinkUrl' type='text' id='LinkUrl'
			value='http://' size='50' maxlength='255' disabled="true" /> <input
			name='UseLinkUrl' type='checkbox' id='UseLinkUrl' value='Yes'
			onClick='rUseLinkUrl();' /> <font color='#FF0000'> 使用转向链接
		</font></td>
	</tr>
	<tr class='tdbg'>
		<td width='120' align='right' class='tdbg5'>#{channel.itemName}简介：</td>
		<td>
		<textarea name='description' cols='80' rows='4' type="_moz">#{article.description@html }</textarea>
		</td>
	</tr>
	<tr class='tdbg' id='ArticleContent' style="display:''">
		<td width='120' align='right' valign="middle" class='tdbg5'>
		<p>#{channel.itemName}内容：</p>
		<br />
		<font color='red'> 换行请按Shift+Enter <br />
		<br />
		另起一段请按Enter </font>
		<br />
		<img id='frmPreview' width="120" height="150" src="#{iif(article.defaultPicUrl@html != "", article.defaultPicUrl@html, "../images/nopic.gif") }" border="1" />
						</td>
					<td>
					<textarea name='content' style="display:none; width:100%; height:380;">#{article.content@html }</textarea>
<iframe ID='editor' src='../editor/editor_new.jsp?channelId=#{channel.id }&showType=2&tContentID=content' 
frameborder='1' scrolling='no' width='600' height='600' ></iframe>
					</td>
				</tr>
				<tr class='tdbg'>
					<td width='120' align='right' class='tdbg5'><font
						color='#FF0000'> 首页图片： </font></td>
					<td><input name='defaultPicUrl' type='text' id='defaultPicUrl' value="#{article.defaultPicUrl@html }"
			size='70' maxlength='200' /> 用于在首页的图片#{channel.itemName}处显示 <br />
		直接从上传图片中选择： <select name='DefaultPicList' id='DefaultPicList'
			onChange="defaultPicUrl.value=this.value;document.all.frmPreview.src=((this.value == '') ? '../images/nopic.gif' : this.value);">
			<option selected='selected'>不指定首页图片</option>
			#{foreach pic in picList }
			<option value="#{pic.filePath@html }">#{pic.name@html }</option>
			#{/foreach }
		</select> 
		<input name='uploadFiles' type='hidden' id='uploadFiles' value="#{article.uploadFiles@html }" /></td>
	</tr>
	<tr class='tdbg'>
		<td width='120' align='right' class='tdbg5'>#{channel.itemName}状态：</td>
		<td>
		<input name='status' type='radio' id='status' value='-1' #{iif (article.status == -1, "checked", "") } />
		草稿 &nbsp; &nbsp; 
		<input name='status' Type='Radio'	id='status' value='0' #{iif (article.status == 0, "checked", "") } />
		投稿 &nbsp; &nbsp; 
		</td>
	</tr>
</pub:template>

<pub:process_template name="main"/>
</body>
</html>