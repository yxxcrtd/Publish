<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>

<pub:template name="check_form_script">
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
		showTab("baseInfo"); 
		alert("#{channel.itemName}标题不能为空！");
		document.myform.title.focus();
		return false;
	}	
	
	/* if (document.myform.keywords.value == ''){
	    showTab("baseInfo");
	    alert('关键字不能为空！');
	    document.myform.keywords.focus();
	    return false;
	}	*/

	if(document.myform.UseLinkUrl.checked == true){
    if (document.myform.LinkUrl.value == '' || document.myform.LinkUrl.value == 'http://'){
    	showTab("baseInfo");
    	alert('请输入转向链接的地址！');
    	document.myform.LinkUrl.focus();
    	return false;
    }
	} else {
    if (document.myform.content.value==''){
    	showTab("baseInfo");
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
	        	showTab("baseInfo");
	        	alert('#{channel.itemName}所属栏目不能指定为外部栏目！');
	        	obj.focus();
	        	return false;
	      	}
	      	if(obj.options[i].selected==true&&obj.options[i].value=='0'){
	        	showTab("baseInfo");
	        	alert('指定的栏目不允许添加#{channel.itemName}！只允许在其子栏目中添加#{channel.itemName}。');
	        	obj.focus();
	        	return false;
	      	}
	    }
	}
	if (iCount==0){
	    showTab("baseInfo");
	    alert('请选择所属栏目！');
	    obj.focus();
	    return false;
	}
	return true;
}
</script>
</pub:template>

<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>
	您现在的位置： <a href='admin_article_list.jsp?channelId=#{channel.id }'>#{channel.name}管理</a>
	  &gt;&gt; 添加#{channel.itemName}
  </td>
 </tr>
</table>
</pub:template>

<pub:template name="select_column_list">
 <select name="columnId">
  <option value='0'>频道根栏目</option>
  #{foreach column0 in dropdown_columns}
  <option value='#{column0.id}' #{iif (article.columnId == column0.id, "selected", "") }>#{column0.prefix}#{column0.name@html}</option>
 #{/foreach }
 </select>
  &nbsp; &nbsp; &nbsp; &nbsp; <font color='#FF0000'>
	<strong>注意：</strong> </font> <font color='#0000FF'>不能指定为外部栏目，栏目颜色为灰时不能添加#{channel.itemName }</font>
</pub:template>



<pub:template name="input_title_td">
<table width='100%' border='0' cellpadding='0' cellspacing='2'>
 <tr>
  <td width='64' class='tdbg5'>简短标题：</td>
  <td>
   <select name='includePic'>
	 <option value='0' #{iif(article.includePic==0, "selected", "") }></option>
	 <option value='1' #{iif(article.includePic==1, "selected", "") }>[图文]</option>
	 <option value='2' #{iif(article.includePic==2, "selected", "") }>[组图]</option>
	 <option value='3' #{iif(article.includePic==3, "selected", "") }>[推荐]</option>
	 <option value='4' #{iif(article.includePic==4, "selected", "") }>[注意]</option>
	</select> 
	<input name='shortTitle' type='text' id='shortTitle' value='#{article.shortTitle@html }' size='56'
		autocomplete='off' maxlength='255' class='bginput' />
	<select name='titleFontColor' id='titleFontColor'>
	 <option value='' selected='selected'>颜色</option>
	 <option value='' #{iif(article.titleFontColor=='', "selected", "") }>默认</option>
	 <option value='#000000' style='background-color:#000000' #{iif(article.titleFontColor=='#000000', "selected", "") }></option>
    <option value='#FFFFFF' style='background-color:#FFFFFF' #{iif(article.titleFontColor=='#FFFFFF', "selected", "") }></option>
	 <option value='#008000' style='background-color:#008000' #{iif(article.titleFontColor=='#008000', "selected", "") }></option>
	 <option value='#800000' style='background-color:#800000' #{iif(article.titleFontColor=='#800000', "selected", "") }></option>
	 <option value='#808000' style='background-color:#808000' #{iif(article.titleFontColor=='#808000', "selected", "") }></option>
	 <option value='#000080' style='background-color:#000080' #{iif(article.titleFontColor=='#000080', "selected", "") }></option>
	 <option value='#800080' style='background-color:#800080' #{iif(article.titleFontColor=='#800080', "selected", "") }></option>
    <option value='#808080' style='background-color:#808080' #{iif(article.titleFontColor=='#808080', "selected", "") }></option>
	 <option value='#FFFF00' style='background-color:#FFFF00' #{iif(article.titleFontColor=='#FFFF00', "selected", "") }></option>
	 <option value='#00FF00' style='background-color:#00FF00' #{iif(article.titleFontColor=='#00FF00', "selected", "") }></option>
	 <option value='#00FFFF' style='background-color:#00FFFF' #{iif(article.titleFontColor=='#00FFFF', "selected", "") }></option>
	 <option value='#FF00FF' style='background-color:#FF00FF' #{iif(article.titleFontColor=='#FF00FF', "selected", "") }></option>
	 <option value='#FF0000' style='background-color:#FF0000' #{iif(article.titleFontColor=='#FF0000', "selected", "") }></option>
	 <option value='#0000FF' style='background-color:#0000FF' #{iif(article.titleFontColor=='#0000FF', "selected", "") }></option>
	 <option value='#008080' style='background-color:#008080' #{iif(article.titleFontColor=='#008080', "selected", "") }></option>
	</select> 
	<select name='titleFontType' id='titleFontType'>
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
  <td><input name='title' type='text' id='title' value='#{article.title@html }' size='80' maxlength='500' /></td>
 </tr>
 <tr>
  <td width='64' class='tdbg5'>副 标 题：</td>
  <td><input name='subheading' type='text' id='subheading' size='80' maxlength='255' value="#{article.subheading@html }" /></td>
 </tr>
 <tr>
  <td></td>
  <td>
   <input name='commentFlag' type='checkbox' id='commentFlag' value='1'  #{iif(article.commentFlag == 1, "checked", "") }/> 显示#{channel.itemName}列表时在标题旁显示评论链接
			&nbsp; &nbsp; &nbsp; 
	<input type='button' name='checksame' value='检查重复标题'
	  onclick="showModalDialog('admin_check_same_title.jsp?channelId=#{channel.id}&title='+document.myform.title.value,'checksame','dialogWidth:350px; dialogHeight:250px; help: no; scroll: no; status: no');" />
  </td>
 </tr>
</table>
</pub:template>



<pub:template name="input_keyword_td">
 <div style="clear: both;">
  <input name='keywords' type='text' style="clear:both" id='keywords' value='#{article.keywords@html }' autocomplete='off'	size='50' maxlength='255' />
  <font color='#FF0000'> * </font>
  #{foreach keyword in keyword_list }
   <font color='blue'>【<font 	color='green' onclick="byId('keywords').value+=(byId('keywords').value==''?'':'|')+'#{keyword}'" style="cursor:hand;">#{keyword}</font>】
  #{/foreach }
  【<font color='green' onclick="window.open('admin_keyword_choose.jsp?channelId=#{channel.id }', 'KeywordList', 'width=600,height=450,resizable=0,scrollbars=yes');"	style="cursor:hand;">更多</font>】 </font>
 </div>
 <div id="skey" style='display:none'></div>
 <font color='blue'> 用来查找相关#{channel.itemName}，可输入多个关键字，中间用 
  <font color='red'> “|” </font> 隔开。不能出现&quot;'&amp;?;:()等字符。 </font>
</pub:template>



<pub:template name="input_author_td">
 <div style="clear: both;">
  <input name='author' type='text' id='author' value='#{article.author@html }' autocomplete='off' size='50' maxlength='100' /> 
  #{foreach author in author_list }
   <font	color='blue'>【<font color='green'  onclick="document.myform.author.value='#{author }'" style="cursor:hand;">#{author }</font>】
  #{/foreach }
  【<font color='green' onclick="window.open('admin_item_author.jsp?channelId=#{channel.id }', 'AuthorList', 'width=600,height=450,resizable=0,scrollbars=yes');" style="cursor:hand;">更多</font>】 </font>
 </div>
 <div id="sauthor" style='display:none'></div>
</pub:template>



<pub:template name="input_source_td">
 <div style="clear: both;">
  <input name='source' type='text' id='souce' value='#{article.source@html }' 
   autocomplete='off' size='50' maxlength='100' /> 
  #{foreach source in source_list }
   <font	color='blue'> 【<font color='green'	onclick="document.myform.source.value='#{source }'" style="cursor:hand;">#{source }</font>】
  #{/foreach }
  【<font color='green' onclick="window.open('admin_item_source.jsp?channelId=#{channel.id }', 'CopyFromList', 'width=600,height=450,resizable=0,scrollbars=yes');"
	style="cursor:hand;">更多</font>】 </font></div>
 <div id="scopyfrom" style='display:none'></div>
</pub:template>



<pub:template name="form_buttons">
<p align='center'>
 <input name='__itemName' id='__itemName' type='hidden' value='#{channel.itemName}' />
 <input name='command' type='hidden' id='command' value='save' /> 
 <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
 <input name='articleId' type="hidden" value="#{article.id }" />
 <input name='inputer' type="hidden" value="#{article.inputer@html }" />
	
 <input name='add' type='submit' id='Add'
	value=' #{iif (article.id > 0, "修 改", "添 加") } '
	onclick="document.myform.action='admin_article_action.jsp';document.myform.target='_self';"
	style='cursor:hand;' /> &nbsp; 
 <input name='Preview' type='submit'id='preview' value=' 预 览 '
	onclick="document.myform.action='admin_article_preview.jsp';document.myform.target='_blank';" style='cursor:hand;' />
 <input name='Cancel' type='button' id='Cancel'	value=' 取 消 '	onClick="history.go(-1);"	style='cursor:hand;' />
</p>
</pub:template>
