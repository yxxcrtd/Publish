<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>发布系统后台管理－顶部页面</title>
<style type='text/css'>
a:link { color:#ffffff;text-decoration:none}
a:hover {color:#ffffff;}
a:visited {color:#f0f0f0;text-decoration:none}
.spa {FONT-SIZE: 9pt; FILTER: Glow(Color=#0F42A6, Strength=2) dropshadow(Color=#0F42A6, OffX=2, OffY=1,); COLOR: #8AADE9; FONT-FAMILY: '宋体'}
img {filter:Alpha(opacity:100); chroma(color=#FFFFFF)}
</style>
<base target='main'>
<script language='JavaScript' type='text/JavaScript'>
function preloadImg(src) {
  var img=new Image();
  img.src=src
}
preloadImg('images/__admin_top_open.gif');

var displayBar=true;
function switchBar(obj) {
  if (displayBar) {
    parent.frame.cols='0,*';
    displayBar=false;
    obj.src='images/__admin_top_open.gif';
    obj.title='打开左边管理导航菜单';
  } else {
    parent.frame.cols='200,*';
    displayBar=true;
    obj.src='images/__admin_top_close.gif';
    obj.title='关闭左边管理导航菜单';
  }
}
</script>
</head>
<body background='images/__admin_top_bg.gif' leftmargin='0' topmargin='0'>
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
  <tr valign='middle'>
    <td width=60><img onclick='switchBar(this)' src='images/__admin_top_close.gif' title='关闭左边管理导航菜单' style='cursor:hand'></td>
    <td width=92><a href='admin_admin_password_add.jsp'><img src='images/__top_an_1.gif' border='0'></a></td>
    <td width=92><a href='http://www.chinaedustar.com/publish/' target='_blank'><img src='images/__top_an_6.gif' border='0'></a></td>
    <td align='right' class='spa'>
      JPublish 1.0.0 (www.chinaedustar.com)
    </td>
  </tr>
</table>
</body>
</html>
