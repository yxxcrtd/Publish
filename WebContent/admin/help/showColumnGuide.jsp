<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ShowColumnGuide -- 显示指定频道的栏目导航</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>ShowColumnGuide -- 显示指定频道的栏目导航</h2>

<p>#{ShowColumnGuide } 内部实现为获取当前或指定频道的一级子栏目，放在变量 'column_list' 中，
  其内部结构为 List&lt;Column&gt;，其中每个 Column 都有一个 childColumns 属性，这个属性提供该栏目的下一级子栏目。
</p>

<p>缺省的 #{ShowColumnGuide } 使用 '.builtin.showcolumnguide' 内建标签来显示栏目导航，
  其结果为一个子栏目的垂直表格，每行一个一级栏目。
</p>

示例：
<pre>
  #{ShowColumnGuide channelId='1'}
   #{foreach column in column_list}
    li. column = #{column}
    li. childColumns = #{column.childColumns }
   #{/foreach}
  #{/ShowColumnGuide}
</pre>

<p>如果要获取所有子栏目，需要使用 #{ShowAllChildColumn } 标签，其获取所有级别的子孙栏目。
</p>

</body>
</html>
