<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.AnnounceManage"
%><%
  // 产生管理数据。
  AnnounceManage admin_data = new AnnounceManage(pageContext);
  admin_data.initListPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>公告管理</title>
</head>
<body>

<pub:declare>

<%-- 主模板 --%>
<pub:template name="main">
 #{call announce_help }
 #{call js_code }
 #{call channel_list_view }
 #{call show_announce_list }
</pub:template>

<%-- 网站公告管理的头部 --%>
<pub:template name="announce_help">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'> 
    <td height='22' colspan='10'>
      <table width='100%'>
        <tr class='topbg'>
          <td align='center'><b>网 站 公 告 管 理</b></td>
          <td width='60' align='right'>
            <a href='help/index.jsp' target='_blank'><img src='images/help.gif' border='0'></a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='70' height='30'><strong>管理导航：</strong></td>
    <td><a href='admin_announce.jsp'>公告管理首页</a>&nbsp;|&nbsp;<a href='admin_announce_add.jsp'>添加新公告</a></td>
  </tr>
</table>
<br />
</pub:template>

<pub:template name="js_code">
<script language='javascript'>
function unselectAll(){
  if(document.myform.chkAll.checked){
    document.myform.chkAll.checked = document.myform.chkAll.checked & 0;
  }
}
function checkAll(form){
  var checked = form.chkAll.checked;
  for (var i=0;i<form.elements.length;i++) {
    var e = form.elements[i];
    if (e.name == 'id' && e.disabled==false)
       e.checked = checked;
    }
  }
function confirmDel(){
  if (document.myform.command.value == 'delete') {
    return confirm('确定要删除选中的公告吗？');
  }
}
</script>
</pub:template>

<%-- 分栏目查看公告导航 --%>
<pub:template name="channel_list_view">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>  
 <tr class='title'>    
  <td height='22'> | 
   <a href='admin_announce.jsp?channelId=-1'>
    <font #{if request.channelId == -1}color='red'#{/if}><span>频道共用公告</span></font></a> | 
   <a href='admin_announce.jsp?channelId=0'>
    <font #{if request.channelId == 0}color='red'#{/if}><span>网站首页公告</span></font></a> | 
   #{foreach channel in channel_list }
   <a href='admin_announce.jsp?channelId=#{channel.id}'>
    <font #{if request.channelId == channel.id}color='red'#{/if}><span>#{channel.name}</span></font>
   </a> | 
   #{/foreach }
  </td>  
 </tr>
</table>
<br />
</pub:template>


<pub:template name="show_announce_list">
<form name='myform' method='post' action='admin_announce_action.jsp' onsubmit='return confirmDel();'>
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
<tr class='title'>
 <td width='30' height='22' align='center'><strong>选中</strong></td>
 <td width='30' height='22' align='center'><strong>ID</strong></td>
 <td height='22' align='center'><strong>标 题</strong></td>
 <td width='60' height='22' align='center'><strong>最新公告</strong></td>
 <td width='60' height='22' align='center'><strong>显示方式</strong></td>
 <td width='60' height='22' align='center'><strong>发布人</strong></td>
 <td width='120' height='22' align='center'><strong>发布时间</strong></td>
 <td width='60' height='22' align='center'><strong>有效期</strong></td>
 <td width='150' height='22' align='center'><strong>操作</strong></td>
</tr>
#{foreach announce in announce_list }
<tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
 <td width='30' align='center'>
  <input name='id' type='checkbox' onclick='unselectAll()' value='#{announce.id}'></td>
  <td width='30' align='center'>#{announce.id}</td>
  <td><a href='admin_announce_add.jsp?id=#{announce.id}' title=''>#{announce.title }</a></td>
  <td width='60' align='center'>
   #{if announce.isSelected }<font color=green>新</font>#{/if}
  </td>
  <td width='60' align='center'>
   #{if announce.showType == 1 }滚动
   #{elseif announce.showType == 2 }弹出
   #{else }全部#{/if }
  </td>
  <td width='60' align='center'>#{announce.author@html}</td>
  <td width='120' align='center'>#{announce.createDate}</td>
  <td width='60' align='center'>#{announce.outTime}天</td>
  <td width='150' align='center'>
   <a href='admin_announce_add.jsp?id=#{announce.id}'>修改</a>&nbsp; 
   <a href='admin_announce_action.jsp?command=delete&id=#{announce.id}' onClick="return confirm('确定要删除此公告吗？');">删除</a>&nbsp;      
   #{if announce.isSelected }
   <a href='admin_announce_action.jsp?command=unselect&id=#{announce.id}'>取消最新</a>
   #{else }
   <a href='admin_announce_action.jsp?command=select&id=#{announce.id}'>设为最新</a>
   #{/if }
 </td>
</tr>
#{/foreach }
</table>
<br />
   
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td>
   <input name='chkAll' type='checkbox' id='chkAll' onclick='checkAll(this.form)' value='checkbox'>选中所有的公告     
   <input type='submit' value='删除选定的公告' name='submit' onclick="document.myform.command.value='delete'" />&nbsp;&nbsp;
   <input type='submit' value='设置选定公告显示方式' name='submit' onclick="document.myform.command.value='setShowType'" />
    <select name='ShowType'>
   	<option value='0'>全部</option>  
   	<option value='1'>滚动</option>  
   	<option value='2'>弹出</option>
    </select>&nbsp;&nbsp;
   <input type='submit' value='将选定的公告移动到 ->' name='submit' onclick="document.myform.command.value='move'" />
   <select name='channelId' id='channelId'>
    <option value='-1'>频道共用公告</option>
    <option value='0' selected>网站首页公告</option>
    #{foreach channel in channel_list }
    <option value='#{channel.id}'>#{channel.name}</option>
    #{/foreach }
   </select>
   <input name='command' type='hidden' id='command' value='' />
  </td>
 </tr>
</table>
</form>

<br><b>说明：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;只有将公告设为最新公告后才会在前台显示<br><br>

</pub:template>
</pub:declare>
<pub:process_template name="main" />
</body>
</html>
