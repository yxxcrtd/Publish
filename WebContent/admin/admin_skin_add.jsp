<%@ page contentType="text/html; charset=gb2312" language="java" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.SkinManage"
%><%
  SkinManage manager = new SkinManage(pageContext);
  manager.initEditPage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>风格管理</title>
<meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
<link href='admin_style.css' rel='stylesheet' type='text/css'>
</head>

<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<%@include file="element_skin.jsp" %>
 
<pub:template name="main">
 #{call skin_navigator }
 #{call skin_form }
 
</pub:template>
 
<pub:template name="skin_form">
<form name='myform' method='post' action='admin_skin_action.jsp'>
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
 <tr align='center' class='title'>
  <td height='22' colspan='2'><strong>#{iif(skin.id == 0, "添加", "修改") }风格</strong></td>
 </tr>
 <tr class='tdbg'>
  <td width='100'><strong> 选择方案：</strong></td>
  <td>
   <select name='themeId' id='themeId' #{iif(skin.id != 0,"disabled ","") } 
    onchange="window.location.href='admin_skin_add.jsp?themeId=' + this.value ">
   #{foreach theme in theme_list }
     <option value='#{theme.id }' #{iif(theme.id == skin.themeId, "selected", "") }>#{theme.name }#{if(theme.isDefault) }（默认）#{/if }</option>
   #{/foreach }
    </select>
    #{if skin.id != 0 }<input type='hidden' id='themeId' name='themeId' value='#{skin.themeId }'/>#{/if }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='100'><strong>风格名称：</strong></td>
  <td> <input name='skinName' type='text' id='skinName' size='50' maxlength='50' value='#{skin.name}'></td>
 </tr>
 <tr class='tdbg'>
  <td width='100'><strong>风格配色设置</strong><br>
   <br>修改风格设置必须具备一定网页设计知识<br>
   <br>不能使用单引号或双引号，否则会容易造成程序错误</td>
  <td>
   <textarea name='skinCss' cols='110' rows='20' id='skinCss'>#{skin.skinCss }</textarea>
  </td>
 </tr>
 <tr align='center' class='tdbg'>
  <td height='50' colspan='2'>
   <input name='skinId' type='hidden' id='skinId' value='#{skin.id }'/>
   <input type='hidden' name='command' value='save' />
   <input type='submit' name='Submit' value=' #{iif(skin.id == 0, "添 加", "修 改") } '/>
   <input type='button' name='Cancel' value=' 取消 ' onclick='window.history.back();' />
  </td>
 </tr>
</table>
</form>
</pub:template>

<pub:process_template name="main" />

</body>
</html>
