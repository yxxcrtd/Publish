<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SoftManage"
%><%
  // 初始化页面数据。
  SoftManage admin_data = new SoftManage(pageContext);
  admin_data.initGeneratePage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <title>软件生成</title>
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src="admin_soft.js"></script>
</head>
<body>

<pub:declare>

<%@ include file="element_soft.jsp" %>
<%@ include file="element_column.jsp" %>

<!-- 主模板 -->
<pub:template name="main">
 #{call soft_manage_navigator(channel.itemName + "生成") }
 #{call generate_manage_option}<br />
 #{call column_label_list(main_column_list, "admin_soft_generate.jsp")}<br />
 #{call your_position(channel.itemName + '生成') }
 #{call show_soft_list('生成HTML操作')}
 #{call operate_button}
 #{call pagination_bar(page_info) }
 #{call more_child_column_list }

 #{call soft_search_bar }
 #{call soft_property_description }
</pub:template>


<%-- called from 'element_soft.jsp::show_soft_list' --%>
<pub:template name="soft_operate">
#{param soft}
#{if (soft.status == 1)}
 <a href="admin_item_generate_action.jsp?command=generate&channelId=#{channel.channelId }&itemId=#{soft.id}">生成文件</a>
 #{if (soft.isGenerated)}
  <a href="#{InstallDir}#{soft.staticPageUrl}" target="_blank">查看文件</a>
  <a href="admin_item_generate_action.jsp?command=delete&channelId=#{channel.channelId}&itemId=#{soft.id}">删除文件</a>
 #{/if }
#{/if }
</pub:template>


<pub:template name="generate_manage_option">
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
  <tr class="tdbg">
 <td width="70" height="30"><strong>#{channel.itemName}选项：</strong></td>
 <td>
  <input name="isGenerated" type="radio" onclick="location = setUrlParam(location.href, 'isGenerated', 'null');"
   value="null" #{if request.isGenerated == null}checked#{/if} /> 所有#{channel.itemName}
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'isGenerated', this.checked?false:'');" 
   value="false" #{if request.isGenerated == false}checked#{/if} /> 未生成的#{channel.itemName} 
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'isGenerated', this.checked?true:'');" 
   value="true" #{if request.isGenerated == true}checked#{/if} /> 已生成的#{channel.itemName}
 </td>
 <td width="50%"></td>
  </tr>
</table>
</pub:template>


<pub:template name="operate_button">
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <td width="200" height="30">
   <input name="chkAll" type="checkbox" id="chkAll" onclick="CheckAll(this)" value="checkbox" /> 选中本页显示的所有#{channel.itemName }</td>
   <td>
   #{if(request.columnId == 0 || request.columnId == channel.rootColumnId) }
    <input type="submit" value="生成首页" onclick="setCommand('channel');" />
   #{/if }
   <input type="submit" value="生成#{iif(columnId!=0 && columnId!=channel.rootColumnId,"当前","所有") }栏目列表页" onclick="setCommand('column');" /> 
   <input type="submit" value="生成#{iif(columnId!=0 && columnId!=channel.rootColumnId,"当前栏目的","所有") }#{channel.itemName }" onclick="setCommand('allItems');" /> 
   <input type="submit" style="width: 110" value="生成选定的#{channel.itemName }" onclick="if(checkSelect()){setCommand('generate');}else{return false;}" /> 
   <input type="submit" style="width: 160" value="删除选定#{channel.itemName }的HTML文件" onclick="if(checkSelect()){setCommand('delete');}else{return false;}" /> 
   </td>
  </tr>
 </table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />
</body>
</html>
