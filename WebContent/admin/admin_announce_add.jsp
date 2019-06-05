<%@page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.AnnounceManage"
%><% 
  AnnounceManage admin_data = new AnnounceManage(pageContext);
  admin_data.initEditPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>增加/修改公告</title>
<link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body>

<pub:template name="main">
 #{call announce_help }
 #{call js_code }
 #{call main_form }
</pub:template>

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
    <td><a href='admin_announce.jsp'>公告管理首页</a>&nbsp;|&nbsp;<a href='admin_announce.jsp'>添加新公告</a>    </td>
  </tr>
</table>
</pub:template>

<pub:template name="js_code">
<script language = 'JavaScript'>
function CheckForm(){
  if (document.myform.Title.value==''){
     alert('公告标题不能为空！');
     document.myform.Title.focus();
     return false;
  }
  var CurrentMode=editor.CurrentMode;
  if (CurrentMode==0){
    document.myform.Content.value=editor.HtmlEdit.document.body.innerHTML; 
  } else if(CurrentMode==1){
    document.myform.Content.value=editor.HtmlEdit.document.body.innerText;
  }
  if (document.myform.Content.value==''){
     alert('公告内容不能为空！');
     editor.HtmlEdit.focus();
     return false;
  }
  return true;  
}
</script>
</pub:template>

<pub:template name="main_form">
<form method='POST' name='myform' onSubmit='return CheckForm();' action='admin_announce_action.jsp?command=save' target='_self'> 
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>    
 <tr class='title'>      
  <td height='22' colspan='2' align='center'><strong>#{iif(announce.id > 0, "修 改", "添 加")} 公 告</strong></td>    
 </tr>    
 <tr class='tdbg'>      
  <td width='20%' align='right'>所属频道：</td>      
  <td width='80%'>        
   <select name='channelId' id='channelId'>
    <option value='-1' #{iif(announce.channelId == -1, "selected", "") }>频道共用公告</option>
    <option value='0' #{iif(announce.channelId == 0, "selected", "") }>网站首页公告</option>
    #{foreach channel in channel_list}
    <option value='#{channel.id}'  #{iif(announce.channelId == channel.id, "selected", "") }>#{channel.name}</option>
    #{/foreach}
   </select>      
  </td>    
 </tr>    
 <tr class='tdbg'>      
  <td align='right'>标题：</td>      
  <td><input type='text' name='Title' size='66' id='Title' value='#{announce.title@html}'></td>
 </tr>    
 <tr class='tdbg'>      
  <td align='right'>内容：</td>      
  <td><textarea name='Content' id='Content' style='display:none' type="_moz">#{announce.content@html}</textarea>
    <iframe ID='editor' src='../editor/editor_new.jsp?channelId=1&showType=2&tContentID=Content' 
	frameborder='1' scrolling='no' width='500' height='300' ></iframe>
  </td>
 </tr>    
 <tr class='tdbg'>      
  <td align='right'>发布人：</td>      
  <td><input name='Author' type='text' id='Author' value='#{announce.author@html}' size='20' maxlength='20'></td>
 </tr>
 <tr class='tdbg'>
  <td align='right'>发布时间：</td>
  <td><input name='CreateDate' type='text' id='CreateDate' value='#{announce.createDate@format}' size='20' maxlength='20'></td>
 </tr>
 <tr class='tdbg'>
  <td align='right'>有效期：</td>
  <td><input name='OutTime' type='text' id='OutTime' value='#{announce.outTime}' size='10' maxlength='20'> 天（为0时，表示永远有效）</td>
 </tr>
 <tr class='tdbg'>
  <td align='right'>显示类型：</td>
  <td>
   <input type='radio' name='ShowType' value='0' #{if announce.showType==0}checked#{/if}>全部&nbsp;&nbsp;
   <input type='radio' name='ShowType' value='1' #{if announce.showType==1}checked#{/if}>滚动&nbsp;&nbsp;
   <input type='radio' name='ShowType' value='2' #{if announce.showType==2}checked#{/if}>弹出&nbsp;&nbsp;</td>
 </tr>
 <tr class='tdbg'>
  <td align='right'>&nbsp;</td>
  <td><input name='IsSelected' type='checkbox' id='IsSelected' value='yes' #{if announce.isSelected}checked#{/if}> 设为最新公告</td>
 </tr>
 <tr class='tdbg'>
  <td height='40' colspan='2' align='center'>
   <input name='id' type='hidden' value='#{announce.id}' />
   <input type='submit' name='Submit' value=' #{iif(announce.id > 0, "修 改", "添 加")} ' />
  </td>
 </tr>
</table>
</form>
</pub:template>

<pub:process_template name="main" />

</body>
</html>
