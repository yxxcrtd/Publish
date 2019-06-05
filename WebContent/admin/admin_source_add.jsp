<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SourceManage"
%><%
  SourceManage admin_data = new SourceManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>修改/新增来源</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 验证表单提交。
function checkForm() {
	if (isEmpty(document.myform.name.value)) {
		alert("来源名称不能为空。");
		document.myform.name.focus();
		document.myform.name.select();
		return false;
	} else {
		document.myform.description.value=editor.HtmlEdit.document.body.innerHTML; 
		return true;
	}
}
// 验证图片的异常处理方法。
function errImg() {
	alert("上传图片文件类型只能是gif|jpg|png|bmp|jpeg!");
	var wrongUrl = byId("photo").value;
	byId("photo").value = "";
	changeImage();
	byId("photo").value = wrongUrl;
	byId("photo").select();
	byId("photo").focus();
}

// 当图片的地址改变的时候执行的方法。
function changeImage() {
	if (byId('photo').value.length == 0) {
		byId("showphoto").src = "images/default.gif";
	} else {
		checkImage('showphoto', 'photo', errImg);
	}
}
// 上传完毕后设置文件的属性。
function setFileOptions(options) {
	var photo = options.url;
	byId("photo").value = photo;
	byId("showphoto").src = photo;
}
// -->
</script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
<pub:declare>

<%@include file="element_keyword.jsp" %>

<pub:template name="main">
#{call source_navigator(iif(source.id > 0, "修改来源", "新增来源")) }

<form method='post' action='admin_source_action.jsp?command=save' name='myform'
		onsubmit='return checkForm();'>
<table width='600' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
<tr class='title'>
 <td height='22' colspan='2'>
 <div align='center'>
 <strong> #{iif (source.id < 1, "新 增", "修 改") } 来 源 信 息 </strong>
 </div>
 </td>
</tr>
<tr class='tdbg'>
 <td width='300' class='tdbg'>&nbsp;<strong> 来源名称：</strong>
 <input name='name' type='text' value="#{source.name@html }" /> <font color='#FF0000'>*</font></td>
 <td rowspan='9' align='center' valign='top' class='tdbg'>
 <table width='180' height='200' border='1'>
  <tr>
   <td width='100%' align='center'>
   <img id='showphoto' src='#{iif (source.photo@html@length < 1, "images/default.gif", source.photo@html) }' width='150' height='172'>
   </td>
  </tr>
 </table>
 <input name='photo' type='text' size='25' value="#{source.photo@html }" onchange="changeImage();" /><strong>：图
 片 地 址</strong><br>
<iframe class="TBGen" style="top:2px" id="UploadFiles" src="../editor/upload.jsp?showType=1&channelId=#{channel.id }&fileType=1"
       frameborder=0 scrolling=no width="300" height="25"></iframe>
 </td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 所属频道：</strong><select name='channelId'>
  <option value='0'>全部频道</option>
  #{foreach channel0 in channel_list }
  <option value='#{channel0.id }' #{iif (channel0.id==source.channelId, "selected", "") }>#{channel0.name }</option>
  #{/foreach }
 </select></td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 联 系 人：</strong><input name='contacterName'
  type='text' value="#{source.contacterName@html }" /></td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 单位地址：</strong><input name='address'
  type='text' value="#{source.address@html }" /></td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 电话号码：</strong><input name='tel' type='text'
  value="#{source.tel@html }" /></td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 传真号码：</strong><input name='fax' type='text'
  value="#{source.fax@html }" /></td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 邮政信箱：</strong><input name='mail' type='text'
  value="#{source.mail@html }" /></td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 邮政编码：</strong><input name='zipCode'
  type='text' value="#{source.zipCode@html }" /></td>
</tr>
<tr class='tdbg'>
 <td>&nbsp;<strong> 电子邮件：</strong><input name='email' type='text'
  value="#{source.email@html }" /></td>
</tr>
<tr class='tdbg'>
 <td>
 &nbsp;<strong> 单位主页：</strong><input name='homePage' type='text'
  value="#{source.homePage@html }" />  
 </td>
 <td><strong> 联系ＱＱ：</strong>
 <input name='qq' type='text' value="#{source.qq }" onkeypress="if (!isDigit()) return false;" />
 </td>
</tr>
<tr class='tdbg'>
 <td colspan='2'>&nbsp;<strong>来源分类：</strong><input
  name='sourceType' type='radio' value='1' #{iif (source.sourceType==1, "checked", "") }>友情站点&nbsp;<input
  name='sourceType' type='radio' value='2' #{iif (source.sourceType==2, "checked", "") }>中文站点&nbsp;<input
  name='sourceType' type='radio' value='3' #{iif (source.sourceType==3, "checked", "") }>外文站点&nbsp;<input
  name='sourceType' type='radio' value='0' #{iif (source.sourceType==0, "checked", "") }>其他站点&nbsp;</td>
</tr>
<tr class='tdbg'>
 <td colspan='2'>&nbsp;<strong>简介</strong>↓<br>
 <textarea id="description" name='description' cols='72' rows='9' style='display: none;'>#{source.description@html }</textarea>
 <iframe id='editor' src='../editor/editor_new.jsp?channelId=#{source.channelId }&showType=2&tContentID=description' 
 frameborder='1' scrolling='no' width='550' height='250' ></iframe>
 </td>
</tr>
<tr>
<tr>
 <td height='40' colspan='2' align='center' class='tdbg'>
 <input name='id' type="hidden" value="#{source.id }" />
 <input type='submit' name='Submit'
  value=' #{iif(source.id > 0, "修 改", "添 加") } ' style='cursor:hand;'>&nbsp;<input
  name='Cancel' type='button' id='Cancel' value=' 取 消 '
  onclick="window.location.href='admin_source_list.jsp?channelId=#{channel.id}';"
  style='cursor:hand;'></td>
</tr>
</table>
</form>
</pub:template>

</pub:declare>
<pub:process_template name="main" />
</body>
</html>
