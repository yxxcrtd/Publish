<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.AuthorManage"
%><%
  AuthorManage admin_data = new AuthorManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>增加/修改作者</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
// 验证表单提交.
function checkForm() {
	if (isEmpty(document.myform.name.value)) {
		alert("姓名不能为空。");
		document.myform.name.focus();
		document.myform.name.select();
		return false;
	} else {
		document.myform.description.value=editor.HtmlEdit.document.body.innerHTML; 
		return true;
	}
}
// -->
</script>
<script type="text/javascript">
<!--
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
 #{call author_navigator(iif(author.id > 0, "修改作者信息", "新增作者信息")) }
<form method='post' action='admin_author_action.jsp?command=save' name='myform' onsubmit='return checkForm();'>
<table width='98%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22' colspan='2'>
  <div align='center'>
  <strong>
  #{if (author.id == 0) }新 增 作 者 信 息#{else }修 改 作 者 信 息#{/if }
  </strong>
  </div>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='62%' class='tdbg'>&nbsp;<strong> 姓名：</strong>
  <input name='name' type='text' size='20' maxlength='20' value="#{author.name@html }" /> 
  <font color='#FF0000'>*</font></td>
  <td rowspan='9' align='center' valign='top' class='tdbg'>
  <table width='180' height='200' border='1'>
   <tr>
    <td width='100%' align='center'><img id='showphoto'
     src='#{iif (author.photo@length < 1, "images/default.gif", author.photo@uri_resolve(site.url )) }' width='150' height='172'></td>
   </tr>
  </table>
  <strong>照 片 地 址</strong>
  <input name='photo' type='text' size='25' value="#{iif (author.photo@length < 1, "", author.photo@uri_resolve(site.url))}" onchange="changeImage();" />
  <br/><iframe class="TBGen" style="top:2px" id="UploadFiles" src="../editor/upload.jsp?showType=1&channelId=#{channel.id }&fileType=1"
         frameborder=0 scrolling=no width="300" height="25"></iframe>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 频道：</strong><select name='channelId'>
   <option value='0'>全部频道</option>
   #{foreach channel0 in channel_list }
   <option value='#{channel0.id }' #{iif (channel0.id==author.channelId, "selected", "") }>#{channel0.name }</option>
   #{/foreach }
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 性别：</strong>
   <input name='sex' type='radio' value='1' #{iif (author.sex == 1, "checked", "") }>男&nbsp;&nbsp;
   <input type='radio' name='sex' value='0' #{iif (author.sex == 0, "checked", "") }>女

   <input type='radio' style="visibility: hidden;" name='sex' value='-1' #{iif (author.sex == -1, "checked", "") }>
   </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 生日：</strong>
  <input name='birthDay' maxlength='20' value="#{if(author.birthDay != "")}#{author.birthDay@format("%tF") }#{/if }" />
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 地址：</strong>
  <input name='address' type='text' value="#{author.address@html }"
   size='20' maxlength='20' /></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 电话：</strong><input name='tel' type='text'
   size='20' maxlength='20' value="#{author.tel@html }" />
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 传真：</strong><input name='fax' type='text'
   size='20' maxlength='20' value="#{author.fax@html }" /></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 单位：</strong><input name='company' type='text'
   size='20' maxlength='20' value="#{author.company@html }" /></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 部门：</strong><input name='department'
   type='text' size='20' maxlength='20' value="#{author.department@html }" />
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 邮编：</strong><input name='zipCode' type='text'
   size='20' maxlength='20' value="#{author.zipCode@html }" /></td>
  <td><strong> 主页：</strong><input name='homePage' type='text' value="#{author.homePage@html }" /></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;<strong> 邮件：</strong><input name='email' type='text'
   size='20' maxlength='20' value="#{author.email@html }" /></td>
  <td><strong> ＱＱ：</strong><input name='qq' type='text' value="#{author.qq }" /></td>
 </tr>
 <tr class='tdbg'>
  <td colspan='2'>&nbsp;<strong>作者分类：</strong><input
   name='AuthorType' type='radio' value='1' #{iif (author.authorType == 1, "checked", "") }>大陆作者&nbsp;<input
   name='AuthorType' type='radio' value='2' #{iif (author.authorType == 2, "checked", "") }>港台作者&nbsp;<input
   name='AuthorType' type='radio' value='3' #{iif (author.authorType == 3, "checked", "") }>海外作者&nbsp;<input
   name='AuthorType' type='radio' value='4' #{iif (author.authorType == 4, "checked", "") }>本站特约&nbsp;<input
   name='AuthorType' type='radio' value='5' #{iif (author.authorType == 5, "checked", "") }>其他作者&nbsp;</td>
 </tr>
 <tr>
 <tr class='tdbg'>
  <td colspan='2'>&nbsp;<strong>作者简介</strong>↓<br>
  <textarea name='description' id='description' cols='72' rows='9'
   style='display: none;'>#{author.description@html }</textarea>
  <iframe id='editor' src='../editor/editor_new.jsp?channelId=#{author.channelId}&showType=2&tContentID=description' 
    frameborder='1' scrolling='no' width='550' height='250' ></iframe>
 </td>
 </tr>
 <tr>
  <td height='40' colspan='2' align='center' class='tdbg'>
  <input type='hidden' value="#{author.id }" name='id' />
  <input type='submit' value=' #{iif(author.id > 0, "修 改", "添 加") } ' style='cursor:hand;'>&nbsp;
  <input name='cancel' type='button' id='cancel' value=' 取 消 '
   onclick="window.location.href='admin_author_list.jsp?channelId=#{channel.id }';"
   style='cursor:hand;'></td>
 </tr>
</table>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />
</body>
</html>
