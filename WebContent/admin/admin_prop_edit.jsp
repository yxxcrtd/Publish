<%@ page language="java" contentType="text/html; charset=gb2312"
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>修改 HTML 属性</title>
</head>
<body>

<h3>扩展属性编辑</h3>
<div>
 <textarea name='content' style="display:none; width:100%; height:380;"></textarea>
 <script>content.value = window.opener.getEditPropValue();</script>
 <iframe ID='editor' src='../editor/editor_new.jsp?showType=3&tContentID=content' 
   frameborder='1' scrolling='no' width='780' height='600' ></iframe>
</div>
<center>
 <input type='button' value=' 修改并返回 ' onclick='applyValue();'/>
 <input type='button' value='关闭(不修改)' onclick='window.close();' />
</center>
<script>
function applyValue() {
  var CurrentMode = editor.CurrentMode;
  var value = '';
  if (CurrentMode==0){
    value = editor.HtmlEdit.document.body.innerHTML; 
  } else if(CurrentMode==1){
    value = editor.HtmlEdit.document.body.innerText;
  }

  opener.setEditPropValue(value);
  window.close();
}
</script>

</body>
</html>
