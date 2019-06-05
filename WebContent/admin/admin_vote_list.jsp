<%@ page contentType="text/html; charset=gb2312" language="java" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.VoteManage"
%><%
  VoteManage manager = new VoteManage(pageContext);
  manager.initListPage();

%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312' />
 <title>调查管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css'>
 <script type="text/javascript" src="main.js"></script>
</head>

<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<%@include file="element_vote.jsp" %>
<%@include file="element_keyword.jsp" %>

<%-- 主显示模板 --%>
<pub:template name="main">
 #{call vote_navigator }
 #{call vote_table_list }
</pub:template>

<%-- 定义显示调查列表。 --%>
<pub:template name="vote_table_list">
<form name='myform' method='post' action='admin_vote_action.jsp' style='margin:0px;'>
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
 <tr class='title'>
  <td width='30' height='22' align='center'><strong>选中</strong></td>
  <td width='30' height='22' align='center'><strong>ID</strong></td>
  <td height='22' align='center'><strong>主题</strong></td>
  <td width='60' height='22' align='center'><strong>调查状态</strong></td>
  <td width='60' height='22' align='center'><strong>调查类型</strong></td>
  <td width='120' height='22' align='center'><strong>发布时间</strong></td>
  <td width='120' height='22' align='center'><strong>终止时间</strong></td>
  <td width='80' height='22' align='center'><strong>操作</strong></td>
 </tr>
 #{if vote_list@size == 0 }
 <tr class='tdbg'>
  <td height='40' colspan='8' align='center'>
   暂时还没有调查！
  </td>
 </tr>
 #{else }
 
 #{foreach vote in vote_list }
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td width='30' align='center'>
   <input name='itemId' type='checkbox' value='#{vote.id }'>
  </td>
  <td width='30' align='center'>#{vote.id }</td>
  <td><a href='admin_vote_add.jsp?voteId=#{vote.id }'>#{vote.title@html }</a></td>
  <td width='60' align='center'>
   #{if vote.isSelected}<font color=green>启用</font>
   #{else }<font color=red>停止</font>#{/if }
  </td>
  <td width='60' align='center'>#{iif(vote.voteType == 0, "单选", "多选") }</td>
  <td align='center'>#{vote.beginTime@format }</td>
  <td align='center'>#{vote.endTime@format }</td>
  <td width='80' align='center'><a href='admin_vote_add.jsp?voteId=#{vote.id }'>修改</a>&nbsp;
   <a href='admin_vote_action.jsp?command=delete&voteId=#{vote.id }' onclick="return confirm('确定要删除此调查吗？');">删除</a>
  </td>
 </tr>
 #{/foreach }
 #{/if }     
</table>

<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td width='130' height='30'>
   <input name='chkAll' type='checkbox' id='chkAll' onclick='CheckAll(this)' value='checkbox'>选中所有的调查
  </td>
  <td>
   <input type='hidden' name='command' value='' />
   <input type='submit' value='删除选定的调查' name='submit' onclick="return batchDel();">&nbsp;&nbsp;
   <input type='submit' value='启用调查' name='submit1' onclick="document.myform.command.value='enable'">&nbsp;&nbsp;
   <input type='submit' value='停止调查' name='submit2' onclick="document.myform.command.value='disable'">&nbsp;&nbsp;
   <input type='submit' value='将选定的调查移动到 ->' name='submit3' onclick="document.myform.command.value='move'">
   <select name='channelId' id='ChannelID'>
    <option value='0' selected>网站首页调查</option>
   #{foreach channel in channel_list }
    <option value='#{channel.id }'>#{channel.name }调查</option>
   #{/foreach }
   </select>
  </td>
 </tr>
</table>
</form>
 #{call keyword_pagination_bar(page_info) }<br />
 
<br><b>说明：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;只有将调查设为最新调查后才会在前台显示<br><br>
</pub:template>

<pub:process_template name="main" />

<script type="text/javascript">
// 批量删除.
function batchDel(){
  if (confirm('确定要删除选中的所有调查吗？') == false) return;
  document.myform.command.value = 'batch_delete';
}
</script>

</body>
</html>

