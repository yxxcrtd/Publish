<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@ taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.AdminManage"
%><%
  // 初始化页面数据.
  AdminManage admin_manage = new AdminManage(pageContext);
  admin_manage.initRightPage();

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head> 
 <meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
 <title>管理员管理--设置权限</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css'>
 <script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:declare>

<%@ include file="tabs_tmpl2.jsp" %>

<pub:template name="main">
<h2>管理员权限设置</h2>
<form name='rightForm' action='admin_admin_action.jsp' method='post' onsubmit='return checkModify();'>
 #{call tab_js }
 #{call tab_header(right_tabs) }
 #{call tab_content(right_tabs) }
 
 <br/><div><center>
 <input type='hidden' name='adminId' value='#{admin_onedit.id }' />
 <input type='hidden' name='command' value='update_right' />
 <input type='submit' name='submit' value='保存修改结果' style='cursor:hand;' />&nbsp;
 <input type='button' name='cancel' id='Cancel' value=' 取 消 ' onclick="window.location.href='admin_admin_list.jsp'" 
   style='cursor:hand;' />
 </center></div>
</form>
</pub:template>


<%-- 频道的权限模板 --%>
<pub:template name="channel_right_template">
#{param ignore, tab }
#{assign channel_right = tab.extend }

<h3>管理员 '#{admin_onedit.name }' 在频道 '#{channel_right.channel.name }' 的权限</h3>
<table width='98%' align='center' border='0' cellspacing='1' cellpadding='2'>
  <tr>
    <td width='50%'>
     <input type='radio' name='role_#{channel_right.uniqueName}' value='400'
      onclick="role_clicked(this);" #{if channel_right.roleValue == 400 }checked #{/if } 
      />频道管理员：拥有所有栏目的管理权限，并可以添加栏目和专题</td>
    <td width='50%'>
     <input type='radio' name='role_#{channel_right.uniqueName}' value='300'
      onclick="role_clicked(this);" #{if channel_right.roleValue == 300 }checked #{/if } 
      />栏目总编：拥有所有栏目的管理权限，但不能添加栏目和专题</td>
  </tr>
  <tr>
    <td width='50%'>
     <input type='radio' name='role_#{channel_right.uniqueName}' value='100'
      onclick="role_clicked(this);" #{if channel_right.roleValue == 100 }checked #{/if } 
      />栏目管理员：只拥有部分栏目管理权限</td>
    <td width='50%'>
     <input type='radio' name='role_#{channel_right.uniqueName}' value='0'
      onclick="role_clicked(this);" #{if channel_right.roleValue == 0 }checked #{/if } 
      />在此频道里无任何管理权限</td>
  </tr>
  <!-- 频道的栏目权限详细 -->
  <tr id='column_#{channel_right.uniqueName}' style='display:#{iif (channel_right.roleValue == 100, '', 'none') };'>
    <td width='50%'>
      <iframe id='frmColumnRight' name='frm_#{channel_right.uniqueName }' height='200' width='100%' 
        src='admin_admin_column_right.jsp?adminId=#{admin_onedit.id}&channelId=#{channel_right.channelId}'></iframe>
      <input type='hidden' name='column_right_#{channel_right.uniqueName}' value='111' />
    </td>
    <td>
    <strong>
     <font color='#0000FF'>注：</font></strong>栏目权限采用继承制度，即在某一栏目拥有某项管理权限，
       则在此栏目的所有子栏目中都拥有这项管理权限，并可在子栏目中指定更多的管理权限。
    </td>
  </tr>
  <tr id='channel_#{channel_right.uniqueName}' style='display:#{iif (channel_right.roleValue == 400, '', 'none') }'>
    <td colspan='2'>
      <input name='target_#{channel_right.uniqueName}' type='checkbox' value='template_manage' 
          #{if channel_right.templateManage }checked #{/if } />模板管理

      <input name='target_#{channel_right.uniqueName}' type='checkbox' value='keyword_manage' 
          #{if channel_right.keywordManage }checked #{/if } />关键字管理

      <input name='target_#{channel_right.uniqueName}' type='checkbox' value='author_manage' 
          #{if channel_right.authorManage }checked #{/if } />作者管理
  
      <input name='target_#{channel_right.uniqueName}' type='checkbox' value='source_manage' 
          #{if channel_right.sourceManage }checked #{/if } />来源管理
    </td>
  </tr>
</table>
</pub:template>


<%-- 其它权限模板 --%>
<pub:template name="other_right_template">
#{param ignore, tab }
<h3>管理员 '#{admin_onedit.name }' 的网站其它权限</h3>

<table width='98%' align='center' border='0' cellspacing='1' cellpadding='2'>
 <tr>
  <td width='100%'>
   此管理员的其他网站管理权限：<input name='chkAll' type='checkbox' id='chkAll' 
     value='Yes' onclick='selectAll()'>选中所有权限
  <table width='100%' border='0' cellspacing='1' cellpadding='2' >
    <tr>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='self_password' 
          #{if site_right.selfPassword }checked #{/if } />修改自己密码
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='site_manage' 
          #{if site_right.siteManage }checked #{/if } />网站管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='channel_manage' 
          #{if site_right.channelManage }checked #{/if }/>频道管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='friend_manage' 
          #{if site_right.friendManage }checked #{/if }/>友情链接管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='announce_manage' 
          #{if site_right.announceManage }checked #{/if }/>公告管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='vote_manage' 
          #{if site_right.voteManage }checked #{/if }/>调查管理
      </td>
    </tr>
    <tr>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='skin_manage' 
          #{if site_right.skinManage }checked #{/if }/>皮肤管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='template_manage' 
          #{if site_right.templateManage }checked #{/if }/>模板管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='label_manage' 
          #{if site_right.labelManage }checked #{/if }/>标签管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='theme_manage' 
          #{if site_right.themeManage }checked #{/if }/>模板方案管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='log_manage' 
          #{if site_right.logManage }checked #{/if }/>日志管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='page_manage' 
          #{if site_right.pageManage }checked #{/if }/>自定义页面管理
      </td>
    </tr>
    <tr> 
      <td width='16%'>
        <input name='target_site' type='checkbox' value='feedback_manage' 
          #{if site_right.feedbackManage }checked #{/if }/>留言管理
      </td>
      <td width='16%'>
        <input name='target_site' type='checkbox' value='generate_manage' 
          #{if site_right.generateManage }checked #{/if }/>页面生成管理
      </td>
    </tr>
  </table>
  
  <div>用户管理权限</div>
  <table width='100%' border='0' cellspacing='1' cellpadding='2' >
    <tr>
      <td width='16%'>
        <input name='target_user' type='checkbox' value='user_manage' 
          #{if site_right.userManage }checked #{/if }/>用户管理
      </td>
      <td width='16%'>
        <input name='target_user' type='checkbox' value='admin_manage' 
          #{if site_right.adminManage }checked #{/if }/>管理员管理
      </td>
      <td width='16%'></td>
      <td width='16%'></td>
      <td width='16%'></td>
      <td width='16%'></td>
    </tr>
  </table>
  
  </td>
 </tr>
</table>

</pub:template>


</pub:declare>

<pub:process_template name="main" />

<script language='javascript'>

function checkModify() {
  // collect column_right for every child_form, which id is 'frmColumnRight'
  var child_forms = document.all['frmColumnRight'];
  for (var i = 0; i < child_forms.length; ++i) {
    var child_form = child_forms[i];
    var column_right = child_form.contentWindow.collectColumnRight();
    var channel_name = child_form.name.substring(child_form.name.indexOf('_') + 1);
    // put column_right value to 'column_right_news_1' etc
    var input_name = 'column_right_' + channel_name;
    var column_right_input = document.rightForm.all[input_name];
    column_right_input.value = column_right;
  }

  return true;
}

function role_clicked(element) {
  // alert('role_clicked element = ' + element + ', name=' + element.name);
  var channel_name = element.name.substring(element.name.indexOf('_') + 1);
  // alert('channel_name = ' + channel_name);
  var column_tr = document.getElementById('column_' + channel_name);
  column_tr.style.display = element.value == '100' ? '' : 'none';
  var channel_tr = document.getElementById('channel_' + channel_name);
  channel_tr.style.display = element.value == '400' ? '' : 'none';
}

// 选中所有站点权限.
function selectAll() {
  for (var i = 0;i < rightForm.target_site.length;i++) {
    var e = rightForm.target_site[i];
    if (e.disabled == false)
       e.checked = rightForm.chkAll.checked;
    }
}

function helloWorld() {
  alert('parent s helloWorld');
}
</script>

</body>
</html>
