<%@ page language="java" contentType="text/html; charset=gb2312" 
  pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.GenerateManage"
%><%
  // 初始化页面数据。
  GenerateManage manager = new GenerateManage(pageContext);
  manager.initChannelGeneratePage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>频道生成管理</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="main.js"></script>
</head>
<body>

<pub:declare>

<%@ include file="tabs_tmpl2.jsp"%>


<pub:tabs var="generate_tabs" scope="page">
 <pub:tab name="index_generate" text="首页生成" template="index_generate_template" />
 <pub:tab name="column_generate" text="栏目页生成" template="column_generate_template" />
 <pub:tab name="special_generate" text="专题页生成" template="special_generate_template" />
 <pub:tab name="item_generate" text="内容页生成" template="item_generate_template" />
</pub:tabs>


<pub:template name="main">
 #{call js_calendar }
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
    <td height='22' colspan='10'>
     <table width='100%'>
      <tr class='topbg'>
       <td align='center'><b>#{channel.name }生成管理</b></td>
       <td width='60' align='right'>
        <a href='help/index.jsp' target='_blank'><img src='images/help.gif' border='0'></a>
       </td>
      </tr>
     </table>
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='70' height='30'><strong>生成说明：</strong></td>
    <td>生成操作比较消耗系统资源及费时，每次生成时，请尽量减少要生成的文件量。</td>
  </tr>
</table>
</pub:template>


<pub:template name="js_calendar">
<script language='JavaScript' src='PopCalendar.js'></script>
<script language='JavaScript'>
PopCalendar = getCalendarInstance()
PopCalendar.startAt = 0 // 0 - sunday ; 1 - monday
PopCalendar.showWeekNumber = 0 // 0 - don't show; 1 - show
PopCalendar.showTime = 0 // 0 - don't show; 1 - show
PopCalendar.showToday = 0 // 0 - don't show; 1 - show
PopCalendar.showWeekend = 1 // 0 - don't show; 1 - show
PopCalendar.showHolidays = 1 // 0 - don't show; 1 - show
PopCalendar.showSpecialDay = 1 // 0 - don't show, 1 - show
PopCalendar.selectWeekend = 0 // 0 - don't Select; 1 - Select
PopCalendar.selectHoliday = 0 // 0 - don't Select; 1 - Select
PopCalendar.addCarnival = 0 // 0 - don't Add; 1- Add to Holiday
PopCalendar.addGoodFriday = 0 // 0 - don't Add; 1- Add to Holiday
PopCalendar.language = 0 // 0 - Chinese; 1 - English
PopCalendar.defaultFormat = 'yyyy-mm-dd' //Default Format dd-mm-yyyy
PopCalendar.fixedX = -1 // x position (-1 if to appear below control)
PopCalendar.fixedY = -1 // y position (-1 if to appear below control)
PopCalendar.fade = .5 // 0 - don't fade; .1 to 1 - fade (Only IE) 
PopCalendar.shadow = 1 // 0  - don't shadow, 1 - shadow
PopCalendar.move = 1 // 0  - don't move, 1 - move (Only IE)
PopCalendar.saveMovePos = 1  // 0  - don't save, 1 - save
PopCalendar.centuryLimit = 40 // 1940 - 2039
PopCalendar.initCalendar()
</script>
</pub:template>


<%-- 频道首页生成 tab 页 --%>
<pub:template name="index_generate_template">
<table width='530' border='0' cellspacing='0' cellpadding='0' align='center'>
 <tr>
  <td>
<form name='channelForm' method='post' action='admin_generate_action.jsp'>
 <br/>
 <input name='command' type='hidden' id='command' value='channel_index' />
 <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
 <input name='submit' type='submit' id='submit' value=' 生成#{channel.name}首页 ' />
</form>
<form name='channelJsForm' method='post' action='admin_generate_action.jsp'>
 <input name='command' type='hidden' id='command' value='channel_js' />
 <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
 <input name='submit' type='submit' id='submit' value=' 生成#{channel.name}菜单、搜索JS ' />
</form>
  </td>
 </tr>
</table>
</pub:template>


<%-- 频道栏目页生成 tab 页 --%>
<pub:template name="column_generate_template">
<form name='columnForm' method='post' action='admin_generate_action.jsp' style='margin:0px;'>
<table width='530' border='0' cellspacing='0' cellpadding='0' align='center'>
 <tr>
  <td>
   <select name='columnId' size='2' multiple='multiple' style='height:300px;width:300px;'>
   #{foreach column in dropdown_columns }
    <option value='#{column.id}'>#{column.prefix}#{column.name}</option>
   #{/foreach }
   </select>
  </td>
  <td valign='bottom'>
   <input name='command' type='hidden' id='command' value='column' />
   <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
   <input type='submit' name='Submit' onclick="columnForm.command.value='column'" value='生成选定栏目的列表页' style='cursor:hand;' />
   <br/><br/>
   <input type='submit' name='Submit' onclick="columnForm.command.value='column_all'" value='生成所有栏目的列表页' style='cursor:hand;' />
   <br/><br/><br/>提示：<br/>可以按住“CTRL”或“Shift”键进行多选<br/>
  </td>
 </tr>
</table>
</form>
</pub:template>


<%-- 频道专题页生成 tab --%>
<pub:template name="special_generate_template">
<form name='formSpecial' method='post' action='admin_generate_action.jsp'>
<table width='530' border='0' cellspacing='0' cellpadding='0' align='center'>
 <tr>
  <td>
   <select name='specialId' size='2' multiple='multiple' id='specialId' style='height:300px;width:300px;'>
   #{foreach special in channel_specials }
    <option id='#{special.id}'>#{special.name }</option>
   #{/foreach }
   </select>
  </td>
  <td valign='bottom'>
   <input name='command' type='hidden' id='command' value='special' />
   <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
   <input type='submit' name='Submit' value='生成选定专题的列表页' style='cursor:hand;' />
   <br/><br/>
   <input type='submit' name='Submit' value='生成所有专题的列表页' style='cursor:hand;' />
   <br/><br/><br/>提示：<br/>可以按住“CTRL”或“Shift”键进行多选<br/>
  </td>
 </tr>
</table>
</form>      
</pub:template>


<%-- 项目内容页生成 tab --%>
<pub:template name="item_generate_template">
<table width='500' border='0' align='center' cellpadding='0' cellspacing='0'>
 <tr>
  <td>
  <form name='itemForm1' method='post' action='admin_generate_action.jsp'>
   <input name='command' type='hidden' id='command' value='item_new' />
   <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
    生成最新 <input name='itemNum' id='itemNum' value='50' size=8 maxlength='10' /> 篇文章&nbsp;
   <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' />
  </form>
  </td>        
 </tr>        
 <tr>          
  <td height='40'>            
  <form name='itemForm2' method='post' action='admin_generate_action.jsp'>            
    生成更新时间为 <input name='beginDate' type='text' id='BeginDate' value='2007-8-25' size=10 maxlength='20' />
    <a style='cursor:hand;' onclick='PopCalendar.show(document.itemForm2.beginDate, "yyyy-mm-dd", null, null, null, "11");'
     ><img src='images/Calendar.gif' border='0' style='Padding-Top:10px' align='absmiddle'></a>
     到 <input name='endDate' type='text' id='EndDate' value='2007-8-25' size=10 maxlength='20' title='不包含此日期' />
    <a style='cursor:hand;' onclick='PopCalendar.show(document.itemForm2.endDate, "yyyy-mm-dd", null, null, null, "11");'
     ><img src='images/Calendar.gif' border='0' Style='Padding-Top:10px' align='absmiddle'></a>
     的文章
   <input name='command' type='hidden' id='command' value='item_date_range' />
   <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
   <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' />
  </form>
  </td>
 </tr>
 <tr>
  <td height='40'>
   <form name='itemForm3' method='post' action='admin_generate_action.jsp'>
     生成ID号为 <input name='beginId' type='text' id='BeginID' value='1' size=8 maxlength='10'> 
     到 <input name='endId' type='text' id='EndID' value='100' size=8 maxlength='10'> 的文章
    <input name='command' type='hidden' id='command' value='item_id_range' />
    <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
    <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;'>      
   </form>
  </td>
 </tr>
 <tr>
  <td height='40'>
  <form name='itemForm4' method='post' action='admin_generate_action.jsp'>
    生成指定ID的文章（多个ID可用逗号隔开）：
    <input name='itemIds' type='text' id='ItemIds' value='1,3,5' size='20' />
    <input name='command' type='hidden' id='command' value='item_ids' />
    <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
    <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' />
  </form>
  </td>
 </tr>
 <tr>
  <td height='40'>
  <form name='itemForm5' method='post' action='admin_generate_action.jsp'>
   生成指定栏目的文章： 
    <select name='columnId'>
    #{foreach column in dropdown_columns}
     <option value='#{column.id}'>#{column.prefix}#{column.name}</option>
    #{/foreach }
    </select>
    <input name='command' type='hidden' id='command' value='item_in_column' />
    <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
    <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' />
  </form>
  </td>
 </tr>
 <tr>
  <td height='40'>
  <form name='itemForm6' method='post' action='admin_generate_action.jsp'>
    生成所有未生成的文章
    <input name='command' type='hidden' id='command' value='item_ungen' />
    <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
    <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' />
  </form>
  </td>
 </tr>
 <tr>
  <td height='40'>
  <form name='itemForm7' method='post' action='admin_generate_action.jsp'>
      生成所有文章
     <input name='command' type='hidden' id='command' value='item_all' />
     <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
     <input name='submit' type='submit' id='submit' value='开始生成 &gt;&gt;' />
  </form>
  </td>
 </tr>
</table>
</pub:template>


</pub:declare>
<pub:process_template name="main" />

</body>
</html>
