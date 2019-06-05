<%@ page language="java" contentType="text/html; charset=gb2312"
 pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@ taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.VoteManage"
%><%
  VoteManage manager = new VoteManage(pageContext);
  manager.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>增加/修改调查</title>
<link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body>

<%@include file="element_vote.jsp" %>

<pub:template name="main">
 #{call vote_navigator }
 #{call js_code }
 #{call main_form }<br/>
</pub:template>

<pub:template name="js_code">
<script language = 'JavaScript'>
function CheckForm(){
  if (document.myform.title.value=='') {
     alert('调查主题不能为空！');
     document.myform.title.focus();
     return false;
  }
  return true;
}
</script>
</pub:template>

<pub:template name="main_form">
<form method='post' name='myform' onsubmit='return CheckForm();' action='admin_vote_action.jsp' target='_self'>
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title'>
   <td height='22' class='title' colspan=4 align=center><b>添加/修改调查</b></td>
  </tr>
  <tr class='tdbg'>
   <td align='right'>所属频道：</td>
   <td colspan='3'>
      <select name='channelId' id='channelId'>
        <option value='0' #{if vote.channelId == 0 }selected#{/if }>网站首页调查</option>
        #{foreach channel in channel_list}
        <option value='#{channel.id}' #{if vote.channelId == channel.id }selected#{/if }>#{channel.name}</option>
        #{/foreach}
      </select>   
   </td>
  </tr>
  <tr class='tdbg'>
   <td align='right'>调查主题：</td>
   <td colspan='3'><textarea name='title' cols='60' rows='4'>#{vote.title@html }</textarea></td> 
  </tr>
  <tr class='tdbg'>
   <td width='20%' align='right'>选项1：</td>
   <td width='35%'><input type='text' name='select1' size='36' value='#{vote.select1@html }'></td>
   <td width='10%' align='right'>票数：</td>
   <td width='35%' ><input type='text' name='answer1' size='10' value='#{vote.answer1 }'></td>
  </tr>
  <tr class='tdbg'>
   <td width='20%' align='right'>选项2：</td>
   <td width='35%'><input type='text' name='select2' size='36' value='#{vote.select2@html }'></td>
   <td width='10%' align='right'>票数：</td>
   <td width='35%'> <input type='text' name='answer2' size='10' value='#{vote.answer2 }'></td>
  </tr>
   <tr class='tdbg'><td width='20%' align='right'>选项3：</td>
   <td width='35%'><input type='text' name='select3' size='36' value='#{vote.select3@html }'></td>
   <td width='10%' align='right'>票数：</td>
   <td width='35%'><input type='text' name='answer3' size='10' value='#{vote.answer3 }'></td>
  </tr>
  <tr class='tdbg'>
   <td width='20%' align='right'>选项4：</td>
   <td width='35%'><input type='text' name='select4' size='36' value='#{vote.select4@html }'></td> 
   <td width='10%' align='right'>票数：</td>
   <td width='35%'><input type='text' name='answer4' size='10' value='#{vote.answer4 }'></td>
  </tr>
  <tr class='tdbg'>
   <td width='20%' align='right'>选项5：</td>
   <td width='35%'><input type='text' name='select5' size='36' value='#{vote.select5@html }'> </td>
   <td width='10%' align='right'>票数：</td>
   <td width='35%'> <input type='text' name='answer5' size='10' value='#{vote.answer5 }'></td>
  </tr>
   <tr class='tdbg'><td width='20%' align='right'>选项6：</td>
   <td width='35%'><input type='text' name='select6' size='36' value='#{vote.select6@html }'></td>
   <td width='10%' align='right'>票数：</td>
   <td width='35%'><input type='text' name='answer6' size='10' value='#{vote.answer6 }'></td>
  </tr>
  <tr class='tdbg'>
   <td width='20%' align='right'>选项7：</td>
   <td width='35%'><input type='text' name='select7' size='36' value='#{vote.select7@html }'></td>
   <td width='10%' align='right'>票数：</td>
   <td width='35%'><input type='text' name='answer7' size='10' value='#{vote.answer7 }'></td>
  </tr>
  <tr class='tdbg'>
   <td width='20%' align='right'>选项8：</td>
   <td width='35%'><input type='text' name='select8' size='36' value='#{vote.select8@html }'></td>
   <td width='10%' align='right'>票数：</td>
   <td width='35%'><input type='text' name='answer8' size='10' value='#{vote.answer8 }'></td>
  </tr>
  <tr class='tdbg'> 
   <td align='right'>调查类型：</td>  
   <td colspan='3'>
    <select name='voteType' id='VoteType'>
     <option value='0' #{if vote.voteType == 0 }selected#{/if }>单选</option> 
     <option value='1' #{if vote.voteType == 1 }selected#{/if }>多选</option>
    </select>
   </td>
  </tr>
  <tr class='tdbg'> 
   <td align='right'>发布时间：</td>
   <td colspan='3'><input name='beginTime' type='text' id='BeginTime' value='#{vote.beginTime@format }' size='20' maxlength='20'></td> 
  </tr>
  <tr class='tdbg'>
   <td align='right'>终止时间：</td>
   <td colspan='3'><input name='endTime' type='text' id='EndTime' value='#{vote.endTime@format }' size='20' maxlength='20'></td>
  </tr>
  <tr class='tdbg'>
   <td align='right'>&nbsp;</td>
   <td colspan='3'><input name='isSelected' type='checkbox' id='IsSelected' value='true' #{if vote.isSelected }checked#{/if }> 启用本调查</td>
  </tr>
  <tr class='tdbg'> 
   <td colspan=4 align=center>
    <input name='voteId' type='hidden' value='#{vote.id }' />
    <input name='command' type='hidden' id='Command' value='save' />
    <input name='Submit' type='submit' id='Submit' value=#{if vote.id == 0 }' 添   加 '#{else }' 修   改 '#{/if } />&nbsp; 
    <input name='Reset' type='reset' id='Reset' value=' 清 除 ' />
    <input name='Cancel' type='button' id='Cancel' value=' 取 消 ' onclick='window.history.back()' />
   </td>
  </tr>
</table>
</form>
</pub:template>

<pub:process_template name="main" />

</body>
</html>