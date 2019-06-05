<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.GenerateManage"
%><%
  // 初始化页面数据。
  GenerateManage manager = new GenerateManage(pageContext);
  manager.initIndexGeneratePage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>全站生成管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="main.js"></script>
</head>
<body>

<pub:declare>

<pub:tabs var="generate_tabs" scope="page" purpose="频道属性的属性页集合">
 <pub:tab name="item_generate" text="首页生成" template="index_generate_template" />
 <pub:tab name="channel_generate" text="频道页生成" template="channel_generate_template" />
 <pub:tab name="column_generate" text="专题页生成" template="special_generate_template" />
</pub:tabs>

<%@ include file="element_channel.jsp" %>
<%@ include file="tabs_tmpl2.jsp"%>

<!-- 主模板 -->
<pub:template name="main">
 #{call manage_option }<br />
<div>
 #{call tab_js}
 #{call tab_header(generate_tabs)}
 #{call tab_content(generate_tabs)}
</div>
</pub:template>



<pub:template name="manage_option">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='2' align='center'><strong>全站生成管理</strong></td>
  </tr>
  <tr class='tdbg'>
    <td width='70' height='30'><strong>生成说明：</strong></td>
    <td>生成操作比较消耗系统资源及费时，每次生成时，请尽量减少要生成的文件量。    </td>
  </tr>
</table>
</pub:template>



<pub:template name="index_generate_template">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td align="center">
   <table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style="margin:10px;">
    <tr>
     <td colspan="2">  
      <form method="get" action="admin_generate_action.jsp?command=index">
      生成网站首页  <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' #{if site.needGenerate == false }disabled#{/if }/> 
       #{if site.needGenerate == false }&nbsp;&nbsp;<font color='blue'>(注: 当前网站首页设置为不需要生成)</font>#{/if }
      </form>
     </td> 
    </tr>
    <tr class='tdbg'>
     <td colspan="2">
      <form method="post" action="admin_generate_action.jsp?command=full_site">
      生成网站所有未生成的页面  <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' 
        onclick="return confirm('生成网站全部页面将耗费大量的服务器资源，而且生成时间较长，你确定要继续吗？');" /> 
      </form>
      发布系统将根据配置定期生成网站主页、各个频道主页，以及未生成的栏目、专题、文章等页面，除非必要，一般不用全部生成。
     </td>
    </tr>
   </table>
  </td>
 </tr>
</table>
</pub:template>


<pub:template name="channel_generate_template">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class="tdbg">
  <td>
   <form method='post' name='channelGenForm' action='admin_generate_action.jsp'>
   <input type='hidden' name='command' value='' />
   <table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style="margin:10px;">
    <tr>
     <td>
      <select name='channelId' size='2' multiple='' style='height:300px;width:300px;'>
       #{foreach channel in channel_list }
        <option value="#{channel.id }">#{channel.name }</option>
       #{/foreach }
      </select><br/>
      注: 频道未设置生成功能的，将不会生成主页，但可以生成 JS 文件。
     </td>
     <td>
      <input name='param' type='hidden' id='cParam' value='0'/>
      <input type='submit' name='submit' value='生成选定频道的首页' 
        onclick='channelGenForm.command.value="channel_index";' /><br/><br/>
      <input type='submit' name='submit' value='生成选定频道的JS' 
        onclick='channelGenForm.command.value="channel_js";' /><br/><br/><br/>
      <input type='submit' name='submit' value='生成所有频道的首页' 
        onclick='channelGenForm.command.value="channel_index_all"' /> <br/><br/>
      <input type='submit' name='submit' value='生成所有频道的JS文件' 
        onclick='channelGenForm.command.value="channel_js_all"' /> <br/><br/>
       提示：<br>可以按住“CTRL”或“Shift”键进行多选 
     </td>
    </tr>
   </table>
   </form>
  </td>
 </tr>
</table>
</pub:template>

<pub:template name="special_generate_template">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class="tdbg">
  <td>
   <form method='post' name='specialGenForm' action='admin_generate_action.jsp'>
   <input type='hidden' name='command' value='' />
   <table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style="margin:10px;">
    <tr>
     <td>
      <select name='specialId' size='2' multiple style='height:300px;width:300px;'>
       #{foreach special in special_list }
        <option value="#{special.id }">#{special.name }</option>
       #{/foreach }
      </select>
     </td>
     <td>
      <input name='param' type='hidden' id='sParam' value='0'/>
      <input type='submit' name='submit' value='生成选定专题的列表页' 
         onclick='specialGenForm.command.value="special_list";' /><br/><br/>
      <input type='submit' name='submit' value='生成所有专题的列表页' 
         onclick='specialGenForm.command.value="special_list_all";' /> <br/>
       提示：<br>可以按住“CTRL”或“Shift”键进行多选 
     </td>
    </tr>
   </table>
   </form>
  </td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />
</body>
</html>