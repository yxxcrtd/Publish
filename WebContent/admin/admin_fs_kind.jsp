<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.FriendSiteManage"
%><%
  // 管理数据初始化。
  FriendSiteManage admin_data = new FriendSiteManage(pageContext);
  admin_data.initKindListPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>友情链接类别管理</title>
</head>
<body>

<pub:template name="main">
 #{call js_code}
 #{call fs_admin_help}
 <br />
 #{call fs_kind_list}
</pub:template>

<pub:template name="fs_kind_list">
<table width='100%' border='0' align='center' cellpadding='0' cellspacing='1' class='border'>
 <tr class='title' height='22'>
  <td width='30' align='center'><strong>ID</strong></td>
  <td width='200' align='center'><strong>类别名称</strong></td>
  <td align='center'><strong>类别说明</strong></td>
  <td width='80' align='center'><strong>包含链接数</strong></td>
  <td width='120' align='center'><strong>常规操作</strong></td>
 </tr>
 #{foreach kind in fs_kinds }
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td width='30' align='center'>#{kind.id}</td>
  <td width='200' align='center'>
   <a href='admin_friendsite_list.jsp?type=0&kindId=#{kind.id}' title='点击进入管理此类别的友情链接'>#{kind.name}</a>
  </td>
  <td>#{kind.kindDesc@html}</td>
  <td width='80' align='center'>#{kind.count}</td>
  <td width='120' align='center'>
   <a href='admin_fs_kind_add.jsp?kindId=#{kind.id}'>修改</a>&nbsp;
   <a href='admin_friendsite_action.jsp?command=deleteKind&kindId=#{kind.id}' onclick="return confirmDelete();">删除</a>&nbsp;
   <a href='admin_friendsite_action.jsp?command=emptyKind&kindId=#{kind.id}' onclick="return confirmEmpty();">清空</a>
  </td>
 </tr>
 #{/foreach}
</table>
</pub:template>

<pub:template name="js_code">
<script language="javascript">
function confirmDelete() {
  return confirm('确定要删除此类别吗？删除此类别后原属于此类别的友情链接将不属于任何类别。');
}

function confirmEmpty() {
  return confirm('确定要清空此类别中的友情链接吗？本操作将原属于此类别的友情链接改为不属于任何类别。')
}
</script>
</pub:template>

<%@ include file="element_fs.jsp" %>

<pub:process_template name="main" />

</body>
</html>
