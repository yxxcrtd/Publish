<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.FriendSiteManage"
%><%
  // 管理数据初始化。
  FriendSiteManage admin_data = new FriendSiteManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>友情链接管理</title>
 <script type="text/javascript" src="main.js"></script>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<pub:declare>

<%@include file="element_keyword.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call js_code}
 #{call admin_help}
 #{if request.type == 0}
	#{call fs_kind_list}
 #{else}
   #{call fs_special_list}
 #{/if}
 #{call admin_nav}
 #{call friendsite_list}
 #{call keyword_pagination_bar(page_info) }
 <% if (true) { %> #{call debug_info }  <% } %>
</pub:template>


<pub:template name="debug_info">
<br/><br/><hr/><h2>DEBUG INFO</h2>
<li>fs_list - 友情站点列表。 = #{fs_list }
<li>page_info - 分页信息。 = #{page_info }
<li>fs_nav - 管理导航数据。 = #{fs_nav }
<li>request = #{request }
<li>fs_kinds = #{fs_kinds }
<li>fs_specials = #{fs_specials }
<br/><br/><br/><br/>
</pub:template>


<!-- 管理帮助部分 -->
<pub:template name="admin_help">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'> 
  <td height='22' colspan='10'>
    <table width='100%'>
      <tr class='topbg'>
        <td align='center'><b>友 情 链 接 管 理</b></td>
        <td width='60' align='right'>
          <a href='help/index.jsp' target='_blank'><img src='images/help.gif' border='0'></a>
        </td>
      </tr>
    </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='70' height='30'><strong>管理导航：</strong></td>
  <td>
   <a href='admin_friendsite_list.jsp'>友情链接管理首页</a> |
   <a href='admin_friendsite_add.jsp'>添加友情链接</a> |
   <a href='admin_fs_kind.jsp'>链接类别管理</a> |
   <a href='admin_fs_kind_add.jsp'>添加链接类别</a> |
   <a href='admin_fs_special.jsp'>链接专题管理</a> |
   <a href='admin_fs_special_add.jsp'>添加链接专题</a> |
   <a href='admin_friendsite_sort.jsp'>友情链接排序</a>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='70' height='30' ><strong>管理选项：</strong></td>
  <td>
  <form name='FilterForm' method='GET' action='admin_friendsite_list.jsp' style='margin:0px;'>
   <input name='approved' type='radio' value='null' onclick='submit();' #{if request.approved == null}checked#{/if}>所有链接&nbsp;&nbsp;
   <input name='approved' type='radio' value='false' onclick='submit();' #{if request.approved == false}checked#{/if}>未审核的链接&nbsp;&nbsp;
   <input name='approved' type='radio' value='true' onclick='submit();' #{if request.approved == true}checked#{/if}>已审核的链接&nbsp;&nbsp;|&nbsp;&nbsp;
   <input name='type' type='radio' value='0' onclick='submit();' #{if request.type == 0}checked#{/if}>按类别分类&nbsp;&nbsp;
   <input name='type' type='radio' value='1' onclick='submit();' #{if request.type == 1}checked#{/if}>按专题分类
   </form>
  </td>
 </tr>
</table>
<br />
</pub:template>

<!-- 友情链接类别列表部分 -->
<pub:template name="fs_kind_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22'>
   | <a href='admin_friendsite_list.jsp?approved=#{request.approved}&type=0'>所有类别</a>
  #{foreach kind in fs_kinds }
   | <a href='admin_friendsite_list.jsp?approved=#{request.approved}&type=0&kindId=#{kind.id}'>#{kind.name}</a>
  #{/foreach } | 
  </td>
 </tr>
</table>
<br />
</pub:template>

<!-- 友情链接专题列表部分 -->
<pub:template name="fs_special_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
  <td height='22'>
   | <a href='admin_friendsite_list.jsp?approved=#{request.approved}&type=1'>所有专题</a>
   #{foreach special in fs_specials }
   | <a href='admin_friendsite_list.jsp?approved=#{request.approved}&type=1&specialId=#{special.id}'>#{special.name}</a> 
   #{/foreach } |
  </td>
 </tr>
</table>
<br />
</pub:template>

<!-- 友情链接管理位置导航 -->
<pub:template name="admin_nav">
<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>
 <tr>
  <td height='22'>您现在的位置：
  #{foreach link in fs_nav }
   #{if link@is_first == false}&gt;&gt;#{/if}&nbsp;<a href='#{link.url}'>#{link.text}</a>
  #{/foreach}
  </td>
 </tr>
</table>
</pub:template>

<pub:template name="js_code">
<script language=javascript>
<!--
function unselectAll(){
    if(document.myform.chkAll.checked){
	 	document.myform.chkAll.checked = document.myform.chkAll.checked&0;
    }
}
function checkAll(form){
  for (var i = 0; i < form.elements.length; ++i) {
    var e = form.elements[i];
    if (e.Name != 'chkAll' && e.disabled==false)
       e.checked = form.chkAll.checked;
    }
  }
function confirmForm(){
  var command = document.myform.command.value;
  if (command == 'delete') {
	 return confirm('确定要删除选中的友情链接吗？');
  } else if(command == 'movekind') {
	 return confirm('确定要将选中的友情链接移动到指定的类别吗？');
  }
  return true;
}
// -->
</script>
</pub:template>

<!-- 友情链接的列表 -->
<pub:template name="friendsite_list">
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td>
   <form name='myform' method='Post' action='admin_friendsite_action.jsp' onsubmit='return confirmForm();'>
   #{call fs_list_table}
   #{call fs_operate_table}
   </form>
  </td>
 </tr>
</table>
</pub:template>

<pub:template name="fs_list_table">
<table class='border' border='0' cellspacing='1' width='100%' cellpadding='0'>
 <tr class='title' height='22'>
  <td width='30' align='center'><strong>选中</strong></td>
  <td width='80' align='center'>
   #{if request.type == 0}<strong>链接类别</strong>
   #{else}<strong>链接专题</strong>#{/if}
  </td>
  <td width='60' align='center'><strong>链接类型</strong></td>
  <td align='center'><strong>网站名称</strong></td>
  <td width='100' align='center'><strong>网站LOGO</strong></td>
  <td width='60' align='center'><strong>站长</strong></td>
  <td width='40' align='center'><strong>点击数</strong></td>
  <td width='40' align='center'><strong>状态</strong></td>
  <td width='40' align='center'><strong>已审核</strong></td>
  <td width='100' align='center'><strong>操作</strong></td>
 </tr>
#{foreach fs in fs_list}
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td width='30' align='center'>
   <input name='id' type='checkbox' onclick='unselectAll()' id='id' value='#{fs.id}'>
  </td>
  <td width='80' align='center'>
  #{if request.type == 0}
   <a href='admin_friendsite_list.jsp?approved=#{request.approved}&type=0&kindId=#{fs.kind.id}'>#{fs.kind.name}</a><br/>
  #{else}
   <a href='admin_friendsite_list.jsp?approved=#{request.approved}&type=1&specialId=#{fs.special.id}'>#{fs.special.name}</a>
  #{/if}
  </td>
  <td width='60' align='center'>
   #{if fs.linkType == 1}文字链接
   #{elseif fs.linkType == 2}图片链接
   #{/if}
  </td>
  <td>
   <a href='#{fs.siteUrl}' target='blank' title='网站名称：#{fs.siteName@html}
      &#13;网站地址：#{fs.siteUrl@html}
      &#13;评分等级：#{fs.stars}
      &#13;点 击 数：#{fs.hits}
      &#13;更新时间：#{fs.lastModified}
      &#13;网站简介：#{fs.description@html}'>#{fs.siteName@html}</a>
  </td>
  <td width='100' align='center'>
    #{if(fs.logo != "") }
     <img src="#{fs.logo}" width='88' height='31' alt='#{fs.siteName@html }' />
    #{/if }
  </td>
  <td width='60' align='center'>
   <a href='mailto:#{fs.siteEmail}'>#{fs.siteAdmin}</a>
  </td>
  <td width='40' align='center'>#{fs.hits}</td>
  <td width='40' align='center'>#{if fs.elite}<font color='green'>推荐</font>#{/if}</td>
  <td width='40' align='center'>#{if fs.approved}<b>√</b>#{/if}</td>
  <td width='100' align='center'>
  #{if fs.approved}
   <a href='admin_friendsite_action.jsp?command=unappr&id=#{fs.id}'>取消审核</a>&nbsp;
  #{else}
   <a href='admin_friendsite_action.jsp?command=approve&id=#{fs.id}'>通过审核</a>&nbsp;
  #{/if}
   <a href='admin_friendsite_add.jsp?id=#{fs.id}'>修改</a><br>
  #{if fs.elite}
   <a href='admin_friendsite_action.jsp?command=unelite&id=#{fs.id}'>取消推荐</a>&nbsp;
  #{else}
   <a href='admin_friendsite_action.jsp?command=elite&id=#{fs.id}'>设置推荐</a>&nbsp;
  #{/if}
   <a href='admin_friendsite_action.jsp?command=delete&id=#{fs.id}' onclick="return confirm('确定要删除此友情链接站点吗？');">删除</a>
  </td>
 </tr>
#{/foreach}
</table>
</pub:template>

<pub:template name="fs_operate_table">
<table width='100%' border='0' cellpadding='0' cellspacing='0'>
 <tr>
  <td width='160' height='30'>
   <input name='chkAll' type='checkbox' id='chkAll' onclick='checkAll(this.form)' value='checkbox'>选中本页所有友情链接
  </td>
  <td>
   <input type='submit' value='删除选定链接' name='submit' onclick="document.myform.command.value='delete'">&nbsp;
   <input type='submit' value='设为推荐链接' name='submit' onclick="document.myform.command.value='elite'">&nbsp;
   <input type='submit' value='取消推荐链接' name='submit' onclick="document.myform.command.value='unelite'">&nbsp;
   #{if request.type == 0}
   <input type='submit' value='移动选定的链接 ->' name='submit' onclick="document.myform.command.value='moveKind'">
    <select name='kindId' id='kindId'>
     <option value='0'>不属于任何类别</option>
     #{foreach kind in fs_kinds}
     <option value='#{kind.id}'>#{kind.name}</option>
     #{/foreach}
    </select>
   #{else}
   <input type='submit' value='移动选定的链接 ->' name='submit' onclick="document.myform.command.value='moveSpecial'">
    <select name='specialId' id='specialId'>
     <option value='0'>不属于任何专题</option>
     #{foreach special in fs_specials}
     <option value='#{special.id}'>#{special.name}</option>
     #{/foreach}
    </select>
   #{/if}
   <input name='type' type='hidden' id='type' value='#{request.type}' />
   <input name='command' type='hidden' id='command' value='' />
  </td>
 </tr>
</table>
</pub:template>

<!-- 分页与搜索我们暂时不做。 -->
</pub:declare>

<pub:process_template name="main" />

</body>
</html>
