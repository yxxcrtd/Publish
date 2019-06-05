<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.LogManage"
%><%
  // prepare page data.
  LogManage manager = new LogManage(pageContext);
  manager.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>日志管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:declare>

<%@include file="element_keyword.jsp" %>

<pub:template name="main">
 #{call manage_option }<br/>
 #{call your_position }
 #{call show_log_list }
 #{call keyword_pagination_bar(page_info) }<br />
</pub:template>

<pub:template name="manage_option">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='10'>
    <table width='100%'>
      <tr class='topbg'>
        <td align='center'><b>网 站 日 志 管 理</b></td>
        <td width='60' align='right'><a
          href='help/index.jsp'
          target='_blank'><img src='images/help.gif' border='0'></a></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='70'>管理导航：</td>
    <td align='left' width='85%'><a href='admin_log.jsp'>全部日志</a></td>
  </tr>
</table>
</pub:template>



<pub:template name="your_position">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站日志管理&nbsp;&gt;&gt;&nbsp;全部日志</td>
  </tr>
</table>
</pub:template>



<pub:template name="show_log_list">
<form name='myform' method='Post' action='admin_log_action.jsp' style='margin:0px;'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title' align='center' height='22'>
    <td width='30'><strong>选中</strong></td>
    <td width='300'><strong>访问地址</strong></td>
    <td><strong>操作信息</strong></td>
    <td width='120'><strong>操作时间</strong></td>
    <td width='90'><strong>IP地址</strong></td>
    <td width='60'><strong>操作人</strong></td>
    <td width='40'><strong>详细</strong></td>
  </tr>
 #{foreach log in log_list }
  <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
    <td width='30' align='center'>
     <input name='id' type='checkbox' onclick="unselectall()" value='#{log.id }' 
        title='id: #{log.id }, user: #{log.userName }' />
    </td>
    <td width='300'>#{log.url}</td>
    <td>
     #{if log.status == 0 }
      #{log.operation} 成功
     #{else }
      <font color='red'>#{log.operation} 失败</font>
     #{/if }
    </td>
    <td width='120' align='center'>#{log.operTime@format }</td>
    <td width='90' align='center'>#{log.userIP }</td>
    <td width='60' align='center'>#{log.userName }</td>
    <td width='40' align='center'>
      <a href='admin_log_detail.jsp?id=#{log.id }'>查看</a>
    </td>
  </tr>
 #{/foreach }
</table>

#{call form_buttons }
</form>
</pub:template>


<pub:template name="form_buttons">
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
  <tr>
    <td width='200' height='30'>
    <input name='chkAll' type='checkbox' id='chkAll' onclick='CheckAll(this.form)' /> 
      选中本页显示的所有日志记录</td>
    <td>
     <input name='command' type='hidden' id='command' value='' />
     <input name='submit1' type='submit' id='submit1'
      onclick="document.myform.command.value='batch_delete'"
      value='删除选中的日志记录' />&nbsp; 
     <input name='Submit2' type='submit' id='Submit2'
      onclick="document.myform.command.value='clear'"
      value='清空日志记录' />
    </td>
  </tr>
</table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

<SCRIPT language=javascript>
function unselectall(){
    if(document.myform.chkAll.checked){
 document.myform.chkAll.checked = document.myform.chkAll.checked&0;
    }
}
function CheckAll(form){
  for (var i=0;i<form.elements.length;i++){
    var e = form.elements[i];
    if (e.Name != 'chkAll'&&e.disabled==false)
       e.checked = form.chkAll.checked;
    }
  }
function ConfirmDel(){
 if(document.myform.Action.value=='Del'){
     if(confirm('确定要删除选中的日志吗？'))
         return true;
     else
         return false;
 }
}
</SCRIPT>

</body>
</html>
