<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>标签简介</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<%@ include file='label_nav.jsp' %>
<h2>标签简介</h2>

<table width="90%" border="0" cellspacing="0" cellpadding="0" align='center'
  style="WORD-BREAK: break-all;Width:fixed">
  <tr>
    <td height="15"></td>
  </tr>
  <tr>
    <td height="30" align="center" class="main_ArticleTitle">发布系统标签简介</td>
  </tr>
  <tr>
    <td align="center" class="main_ArticleSubheading"></td>
  </tr>
  <tr>
    <td height="15"></td>
  </tr>
  <tr>
    <td height="200" valign="top" id="fontzoom"
      style="WORD-BREAK: break-all;">
    <p>
    发布系统中标签都以#{***}的形式进行调用。没有参数的标签为普通标签。如果标签带有自定义的参数则为函数式标签或超级函数式标签。
    函数式标签或超级函数式标签带有 x='y' 样式的属性，设置不同的参数显示相关的形式和内容。</p>
    
    <p>
    超级函数式标签是系统的核心标签，其参数比较多，在实际使用中只要更改不同的参数即可实现不同的显示效果。
    在文章、下载、图片等功能频道中，分别以“#{Show***List }”、“#{ShowPic***}”命名。
    标签调用时要加上定界符“#{ }”，如#{ShowLogo }。在后台管理中，修改相关版式模板，
    在版式模板代码中输入以上调用例举样式的标签，单击“保存修改结果”按钮后保存所作的修改。
    如果是本频道启用了HTML生成功能，则需要生成相关页面才能看到修改的效果。</P>
    
    <P><STRONG>标签例举</STRONG></P>
    <p>标签名：#{SiteName}&nbsp; （普通标签）<BR>
    作 用：显示网站名称</p>
    
    <p>标签名：#{ShowLogo width='width' height='height'} （函数式标签）<br/>
    作 用：显示网站LOGO<BR>
    参 数：width --显示LOGO宽度<BR>
    height --显示LOGO高度<BR>
    调用例举：#{ShowLogo width='180' height='60'}&nbsp;&nbsp; //显示180*60的LOGO图片</P>
    <P><BR>
    <STRONG>标签使用说明</STRONG></P>
    <P>
    以下按网站通用标签与专用页标签二大类，详细阐述每个标签的标签名与作用。
     对函数式标签和超级函数式标签，将详细说明参数以及调用举例。对频道中的标签只列出特殊标签的使用范围。</P>
    <P>
    如果是本频道启用了HTML生成功能，则需要生成相关页面才能看到修改的效果。</P>
    <P>&nbsp;</P>
    </td>
  </tr>
  <tr>
    <td height="15"></td>
  </tr>
</table>

</body>
</html>
