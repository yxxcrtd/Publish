<%@ page language="java" contentType="text/html; charset=gb2312"
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ExtendsManage"
%><%
  ExtendsManage manager = new ExtendsManage(pageContext);
  manager.initAddPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>添加自定义属性</title>
</head>
<body>

<h2>添加自定义属性</h2>

<pub:template name="main">
<form name='myform' action='admin_extends_action.jsp' method='post' onsubmit='return check_form();'>
<table width='100%' border='0' class='border'>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>所属对象：</strong></td>
  <td>
   #{object.objectClass }, id=#{object.id}, #{object.title }(#{object.name })
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>属性名字：</strong></td>
  <td>
    <input type='text' name='propName' value='' />
    <font color='red'>*</font> 必须给出，名字是英文+数字组成，第一个字符必须是英文。
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>属性类型：</strong></td>
  <td>
    <select name='propType' onchange='change_type(this)'>
     <option value='string'>字符串</option>
     <option value='int'>整数</option>
     <option value='number'>数字(支持浮点)</option>
     <option value='date'>日期</option>
     <option value='multi.string'>多个字符串</option>
     <option value='multi.int'>多个整数</option>
     <option value='html'>HTML</option>
    </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='300' class='tdbg5'><strong>属性值：</strong></td>
  <td>
   <div id='__value' style='display:'>
    <textarea name='propValue' rows='8' cols='80'></textarea>
    <br/>* 如果是多值属性，请每个值一行，空行会忽略掉。
   </div>
   <div id='__html_value' style='display:none'>
    <iframe style='display:' id='editor' src='../editor/editor_new.jsp?showType=3&tContentID=propValue' 
      frameborder='1' scrolling='no' width='700' height='300' ></iframe>
   </div>
  </td>
 </tr>
 <tr>
  <td colspan='2' align='center'>
   <input type='hidden' name='command' value='save' />
   <input type='hidden' name='id' value='#{object.id }' />
   <input type='hidden' name='objectClass' value='#{object.objectClass }' />
   <input type='submit' value=' 提    交 ' />
   <input type='button' value=' 取 消 ' onclick='window.history.back();' />
  </td>
 </tr>
</table>
</form>
</pub:template>
<pub:process_template name="main" />

<script language='javascript'>
// 切换值类型，用不同的编辑方式.
function change_type(select) {
  var type = select.options[select.selectedIndex].value;
  if (type == 'html') {
    __value.style.display = 'none';
    __html_value.style.display = '';
  } else {
    __value.style.display = '';
    __html_value.style.display = 'none';
  }
}

// 提交前处理.
function check_form() {
  var prop_name = document.myform.propName.value;
  if (prop_name == '') {
    alert('必须给出属性名字。');
    return false;
  }
  var type = document.myform.propType.value;
  if (type == 'html') {
    var CurrentMode = editor.CurrentMode;
    if (CurrentMode==0){
      //setOpenNew();
      document.myform.propValue.value = editor.HtmlEdit.document.body.innerHTML; 
    } else if (CurrentMode==1){
      //setOpenNew();
      document.myform.propValue.value = editor.HtmlEdit.document.body.innerText;
    } else {
      alert('属性值在预览状态不能保存！请先回到编辑状态后再保存');
      return false;
    }
  }
  
  return true;
}
</script>

</body>
</html>
