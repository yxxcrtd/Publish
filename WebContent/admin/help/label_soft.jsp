<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>发布系统文章标签</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>发布系统文章标签</h2>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="*" valign="top">
    <p>
    下载标签适用于文章显示页面，以及嵌有文章的其它标签之内，在没有文章对象的地方则不能使用。以下将详细说明各标签的作用：
    </p>
    
    <!-- ColumnID -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ColumnID}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示栏目标识</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.id}</td>
      </tr>
    </table>
    
    </td>
  </tr>
</table>

</body>
</html>
    