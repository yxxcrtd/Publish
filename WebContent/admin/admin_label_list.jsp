<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.LabelManage"
%><%
  LabelManage admin_data = new LabelManage(pageContext);
  admin_data.initListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <link href='admin_style.css' rel='stylesheet' type='text/css' />
  <title>标签管理</title>
</head>
<body>
<pub:declare>
<%@include file="element_label.jsp" %>

<pub:template name="main"> 
 #{call label_manage}<br />
 #{call label_type_nav}<br />
 #{call show_label_list}<br />
 #{call show_page(page_info) }
 <br/><br/>
</pub:template>

<!-- 调试输出的信息 -->
<pub:template name="show_label_list_debug">
<hr />
  <h2>LABEL_LIST DEBUG INFORMATION</h2>
  #{foreach label in label_list}
    <li>label = #{label.id}, #{label.name}, #{label.labelClass}
  #{/foreach}
  <br/><br/><br/><br/>
</pub:template>


<pub:template name="label_type_nav">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
 <td height='22'>
 | <a href='admin_label_list.jsp?type=0'>用户自定义标签</a>
 | <a href='admin_label_list.jsp?type=1'>系统内建标签</a>
 | <a href='admin_label_list.jsp?type=2'>测试用标签</a>
 |</td>
 </tr>
</table>
</pub:template>


<!-- 显示自定义标签列表 -->
<pub:template name="show_label_list">
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
 <tr align='center' class='title'>
  <td width='20'>id</td>
  <td width='150' height='22'>标签名称</td>
  <td width='40'>优先级</td>
  <td width='60'>标签类型</td>
  <td width='*'>标签简介</td>
  <td width='70' align='center'>操作</td>
 </tr>
#{foreach label in label_list}
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td align='right'>#{label.id}</td>
  <td align='left'>
   &nbsp;<a href='admin_label_add.jsp?labelId=#{label.id}'>#{label.name@html}</a>
  </td>
  <td align='center'>#{label.priority}</td>
  <td align='center'>
  #{if label.labelType == 0}
   用户自定义
  #{elseif label.labelType == 1}
   系统内建
  #{elseif label.labelType == 2}
    测试用
  #{else}
   未知类型
  #{/if}
  </td>
  <td style='word-break:break-all;Width:fixed'>#{label.description@html}</td>
  <td align='center'>
   <a href='admin_label_add.jsp?labelId=#{label.id}'>修改</a>&nbsp;
   <a href='admin_label_action.jsp?command=delete&amp;labelId=#{label.id}' onclick="return confirm('真的要删除此标签吗？如果有文件或模板中使用此标签，请注意修改过来呀！');">删除</a>
  </td>
 </tr>
#{/foreach}
</table>
</pub:template>

<!-- 显示分页用的模板 -->
<pub:template name="show_page">
#{param page_info}
<div class="show_page">
 共 <b>#{page_info.totalCount}</b> 个标签&nbsp;&nbsp;
#{if (page_info.currPage != 1)}
 <a href='admin_label_list.jsp?type=#{request.type}&pageSize=#{page_info.pageSize}&page=1'>首页</a>
 <a href='admin_label_list.jsp?type=#{request.type}&pageSize=#{page_info.pageSize}&page=#{page_info.currPage - 1}'>上一页</a>
#{else}
  <span>首页 上一页</span>
#{/if}
#{if (page_info.currPage < page_info.totalPage)}
 <a href='admin_label_list.jsp?type=#{request.type}&pageSize=#{page_info.pageSize}&page=#{page_info.currPage + 1}'>下一页</a>
 <a href='admin_label_list.jsp?type=#{request.type}&pageSize=#{page_info.pageSize}&page=#{page_info.totalPage}'>尾页</a>
#{else}
 <span>下一页 尾页</span>
#{/if}
 页次：<strong><font color=red>#{page_info.currPage}</font>/#{page_info.totalPage}</strong>页 &nbsp;
  <input type='text' name='pageSize' size='3' maxlength='4' value='#{page_info.pageSize}' 
   onKeyPress="if (event.keyCode==13) window.location='admin_label_list.jsp?type=0&page=#{page_obj.currPage }&pageSize='+this.value;">个标签/页&nbsp;
  转到：<select name='page' size='1' onchange="javascript:window.location='admin_label_list.jsp?type=0&pageSize=#{page_info.pageSize}&page='+this.options[this.selectedIndex].value;">
#{foreach page in range(1, page_info.totalPage + 1)}
 <option value='#{(page)}'#{iif(page_info.currPage == page, " selected=''", "")}>第#{page}页</option>
#{/foreach}
  </select>
</div>
</pub:template>
</pub:declare>

<pub:process_template name="main" />

</body>
</html>
