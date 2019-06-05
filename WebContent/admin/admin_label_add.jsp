<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.LabelManage"
%><%
  // 初始化页面数据。
  LabelManage admin_data = new LabelManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>新增/修改标签</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body><pub:declare>

<%@include file="element_label.jsp" %>

<pub:template name="main">
 #{call label_manage}
 #{call label_edit_form}
 <br/><br/>
</pub:template>

<pub:template name="label_edit_form">
<form action='admin_label_action.jsp?command=save' method='post' name='myform' id='myform' __onsubmit='return checkForm();'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title' height='22'>
  <td  align='center'><strong>修 改 静 态 标 签</strong></td>
 </tr>
 <tr class='tdbg'>
  <td>
   <table border='0' cellpadding='0' cellspacing='0' width='100%' >
    <tr>
     <td width='100' align='center'><strong>标签名称：</strong></td>
     <td><input name='name' type='text' id='LabelName' size='30' maxlength='50' value='#{label.name@html}' />
     <td width='10'></td>
     <td><font color='#FF0000'>* 输入名称（可以用英文、中文和数字，英文要区分大小写），标签名称中不要带有特殊字符。</font></td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>
   <table border='0' cellpadding='0' cellspacing='0' width='100%' >
    <tr>
     <td width='100' align='center'><strong>标签分类：</strong></td>
     <td colspan='3'>
      <select name='labelType'>
       <option value='0' #{if label.labelType == 0} selected#{/if}>用户自定义标签</option>
       <option value='1' #{if label.labelType == 1} selected#{/if}>系统内建标签</option>
       <option value='2' #{if label.labelType == 2} selected#{/if}>测试用标签</option>
      </select>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>
   <table border='0' cellpadding='0' cellspacing='0' width='100%' >
    <tr>
     <td width='100' align='center'><strong>标签简介：</strong></td>
     <td>
      <textarea name='description' cols='80' rows='4' id='Description' type="_moz">#{label.description}</textarea>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>
   <table border='0' cellpadding='0' cellspacing='0' width='100%' >
    <tr>
     <td width='100' align='center'><strong>优 先 级：</strong></td>
     <td><input name='priority' type='text' id='Priority' size='5' maxlength='5' value='#{label.priority}'></td>
     <td width='10'></td>
     <td><font color='#FF0000'>数字越小，优先级越高。</font></td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='title' height='22'>
  <td  align='center'><strong>标 签 内 容</strong></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;
   <textarea name='content' class='body2' rows='32' cols='126' 
      __onMouseUp="setContent('get',1)" type="_moz">#{label.content@html}</textarea>
  </td>
 </tr>
  <%-- 先不支持
 <tr class='tdbg'>
  <td>&nbsp;
   <iframe style='display:none' ID='editor' src='../editor_new.jsp?channelId=1&ShowType=1&TemplateType=0&tContentid=LabelContent2' frameborder='1' scrolling='no' width='780' height='300' ></iframe>
  </td>
 </tr>
  --%>
 <tr class='tdbg'>
  <td height='40' align='center'>
   <input name='labelId' type='hidden' id='LabelId' value='#{label.id}'>
   <input name='submit' type='submit' id='Submit' value=' 保存修改结果 '>
  </td>
 </tr>
</table>
</form>
</pub:template>

<pub:template name="label_debug_output">
<br/><br/><hr /><h2>LABEL DEBUG OUTPUT</h2>
<li>label= #{(label)}
<li> .id = #{label.id }
<li> .name = #{label.name }
<li> .desc = #{label.description@html }
<li> .labelType = #{label.labelType }
<li> .content: 
<div><pre>#{label.content@html}</pre></div>
</pub:template>

</pub:declare><pub:process_template name="main" />

</body>
</html>
