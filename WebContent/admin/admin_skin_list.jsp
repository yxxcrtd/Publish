<%@ page contentType="text/html; charset=gb2312" language="java" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.SkinManage"
%><%
  SkinManage manager = new SkinManage(pageContext);
  manager.initListPage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312' />
 <title>风格管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css'>
</head>

<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<%@include file="element_skin.jsp" %>

<!-- 主显示模板 -->
<pub:template name="main">
 #{call skin_navigator }
 #{call skin_table_list }  
</pub:template>

<!-- 定义显示风格列表。 -->
<pub:template name="skin_table_list">
<br/><IMG SRC='images/img_u.gif' height='12'>您现在的位置： #{theme.name} &gt;&gt; 风格管理

<form name='myform' method='post' action='admin_skin_list.jsp'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>    
 <tr class='title' height='22' align='center'>
  <td width='30' align='center'><strong>选择</strong></td>
  <td width='50'><strong>ID</strong></td>
  <td width='100'><strong>方案名称</strong></td>
  <td ><strong>风格名称</strong></td>
  <td width='60'><strong>系统默认</strong></td>
  <td width='300' height='22' align='center'><strong> 操作</strong></td>
 </tr>
 #{foreach skin in skin_list}
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td width="30" align="center"><input type="checkbox" value='#{skin.id }' name="skinId"  #{iif(skin.isDefault, "disabled", "") }/></td>
  <td width='50' align='center'>#{skin.id }</td>
  <td align='center'>#{theme.name }</td>
  <td align='center' width='100'>#{skin.name }</td>
  <td width='60' align='center'><FONT style='font-size:12px' color='#008000'><b>#{iif(skin.isDefault, "√", "") }</b></FONT></td>
  <td width='300' align='center'>
   #{if skin.isDefault == false }
    <a href="admin_skin_action.jsp?command=setdefault&skinId=#{skin.id }">设为系统默认</a>
   #{else }
    <font color='gray'>设为系统默认</font>
   #{/if }
   <a href='admin_skin_add.jsp?themeId=#{skin.themeId }&skinId=#{skin.id }'>修改风格</a>
   #{if skin.isDefault == false }
    <a href='admin_skin_action.jsp?command=delete&skinId=#{skin.id }' onclick="return confirm('确定要删除此风格吗？删除此风格后原使用此风格的项目将改为使用系统默认风格。');">删除风格</a>
   #{else }
    <font color='gray'>删除风格</font>
   #{/if }
  </td> 
 </tr>
 #{/foreach}
 #{if skin_list@size == 0 }
  <tr class='tdbg'>
   <td colspan='10' align='center' height='50'>此方案中还没有风格</td>
  </tr>
 #{else }
 <tr class="tdbg"> 
   <td colspan=8 height="30">
    <input name="command" type="hidden"  value="delete" />
    <input name="chkAll" type="checkbox" id="chkAll" onclick='CheckAll(this.form)' value="checkbox" >选中所有项目
      &nbsp;&nbsp;&nbsp;&nbsp;将选定的项目： 
     <input type="submit" value=" 批 量 删 除 " name="Del" onclick='return batchDel();' />&nbsp;&nbsp;
   </td>
 </tr>
 <tr class='tdbg'>
  <td height='40' colspan='7' align='center'>
   <input type='submit' name='Submit' value='刷新风格CSS文件' onclick="refreshCss();">
  </td>
 </tr>
#{/if } 
</table>
</form>

<script type="text/javascript">
function CheckAll(thisform){
  for (var i=0;i<thisform.elements.length;i++){
    var e = thisform.elements[i];
    if (e.Name != "chkAll" && e.disabled != true && e.zzz != 1)
      e.checked = thisform.chkAll.checked;
  }
}

//批量删除.
function batchDel(){
  document.myform.action = 'admin_skin_action.jsp';
  document.myform.command.value = 'delete';
  return confirm('确定要删除此风格吗？删除此风格后原使用此风格的文章将改为使用系统默认风格。');
}

// 刷新风格CSS文件.
function refreshCss() {
  document.myform.action = 'admin_skin_action.jsp';
  document.myform.command.value = 'refreshCss';
}
</script>
</pub:template>

<pub:process_template name="main" />

</body>
</html>

