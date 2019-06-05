<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.StatManage"
%><%
  // 初始化页面数据。
  StatManage manager = new StatManage(pageContext);
  manager.initConfigPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>网站统计管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="item_tabs">
 <pub:tab name="base_info" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="init_config" text="初始化设置" template="temp_initConfig" default="false" />
 <pub:tab name="stat_items" text="功能项目" template="temp_statItems" default="false" />
 <pub:tab name="call_code" text="调用代码" template="temp_callCode" default="false" />
</pub:tabs>

<%@ include file="tabs_tmpl2.jsp" %>

<pub:template name="main">
 #{call stat_header }<br/>
 #{call your_position }
<form action='admin_stat_action.jsp' method='post' name='form1' onsubmit='' target='_self'>
<div>
 #{call tab_js }
 #{call tab_header(item_tabs) }
 #{call tab_content(item_tabs) }
</div>
 #{call form_button }
</form>
</pub:template>

<pub:template name="stat_header">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' Class='border'>  
  <tr class='topbg'> 
    <td height='22' colspan='10'>
      <table width='100%'>
        <tr class='topbg'>
          <td align='center'><b>网 站 统 计 配 置</b></td>
          <td width='60' align='right'><a href='http://www.chinaedustar.com/publish/' target='_blank'><img src='images/help.gif' border='0'></a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='70' height='30'><strong>管理导航：</strong></td>
    <td height='30'>
      <a href='admin_stat_config.jsp'>网站统计信息配置</a> |
      <!-- 
      <a href='Admin_Counter.asp?Action=IPAdd'>统计IP库添加</a> | 
      <a href='Admin_Counter.asp?Action=IPManage'>统计IP库管理</a> | 
      <a href='Admin_Counter.asp?Action=Compact'>压缩统计数据库</a> | 
      <a href='Admin_Counter.asp?Action=Init'>统计数据初始化</a>
      -->
    </td>
  </tr>
</table>
</pub:template>

<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>您现在的位置：网站统计信息配置</td>
 </tr>
</table>
</pub:template>

<pub:template name="temp_baseInfo">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
  <tr class='tdbg'>
    <td width='300' height='25' class='tdbg5'><strong> 服务器所在时区：</strong></td>
    <td> 
      <input name='MasterTimeZone' type='text' id='MasterTimeZone' value='#{stat_main.masterTimeZone }' size='20' maxlength='50'>
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong>在线用户的保留时间：</strong><br>
      用户切换页面至其他网站或者关闭浏览器后，在线名单将在上述时间内删除该用户。这个间隔越小，网站统计的当前时刻在线名单越准确；这个间隔越大，网站统计的在线人数越多。
    </td>
    <td> 
      <input name='OnlineTime' type='text' id='OnlineTime' value='#{stat_main.onlineTime }' size='20' maxlength='50'>
      秒      </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong>自动标记在线间隔：</strong><br>
      客户端浏览器会每隔上述时间向服务器提交一次在线信息，同时服务器将其标记为在线，这个间隔越小，服务器需要处理的请求越多。</td>
    <td> 
      <input name='Interval' type='text' id='Interval' value='60' size='20' maxlength='50' disabled>
        秒
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong>自动标记在线间隔循环次数：</strong><br>
      此是为了防止用户打开网页，但长时间无任何活动而设置。客户端浏览器向服务器提交在线信息次数超过此次数，立即停止提交。
    </td>
    <td> 
      <input name='IntervalNum' type='text' id='IntervalNum' value='#{stat_main.intervalNum }' size='20' maxlength='50'>
        次
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong>保留访问记录数：</strong><br>
 保存访问明细(最后访问)条目数。</td>
    <td> 
      <input name='VisitRecord' type='text' id='VisitRecord' value='#{stat_main.visitRecord }' size='20' maxlength='50'>
        条
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong> 保留访问IP数(大于20小于800的数字)： </strong><br>
      当不启用“在线人数统计”功能时，系统将以保留访问者IP的方式来防止刷新，即同一个IP访问多次或者在网站内切换页面，均只计算浏览量而不计算访问量。    
    </td>
    <td> 
      <input name='KillRefresh' type='text' id='KillRefresh' value='#{stat_main.killRefresh }' size='20' maxlength='50'>
        个
    </td>
  </tr>
</table>
</pub:template>

<pub:template name="temp_initConfig">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong>使用本系统前的访问量：</strong>
    </td>
    <td colspan='3'> 
      <input name='OldTotalNum' type='text' id='OldTotalNum' value='#{stat_main.oldTotalNum }' size='20' maxlength='9'>
        人次
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong> 使用本系统前的浏览量：</strong>
    </td>
    <td colspan='3'> 
        <input name='OldTotalView' type='text' id='OldTotalView' value='#{stat_main.oldTotalView }' size='20' maxlength='9'>
        人次
    </td>
  </tr>
</table>
</pub:template>

<pub:template name="temp_statItems">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'> 
  <td width='300' height='25' class='tdbg5'><strong>功能项目:</strong><br>
    统计太多的项目会减慢访问速度，耗费太多网站资源，一段时间不想分析的功能项目建议不要起用！<br>
    <font color='red'>强烈建议尽量选择少的功能项目，最好一个都不启用！</font><br>
  </td>
  <td>
   <table width='100%'>
    <tr>
     <td>
     <input name='RegFields_Fill' type='checkbox' value='IsCountOnline' 
        #{if stat_main@is_stat('IsCountOnline') }checked#{/if } >启用“在线人数统计”功能</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FIP'
        #{if stat_main@is_stat('FIP') }checked#{/if } >客户端IP地址分析</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FAddress'
        #{if stat_main@is_stat('FAddress') }checked#{/if }>客户端地址分析</td>
    </tr>
    <tr class='tdbg'>
     <td><input name='RegFields_Fill' type='checkbox' value='FRefer'
        #{if stat_main@is_stat('FRefer') }checked#{/if } >客户端链接页面分析</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FTimezone'
        #{if stat_main@is_stat('FTimezone') }checked#{/if } >客户端时区分析</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FWeburl'
        #{if stat_main@is_stat('FWeburl') }checked#{/if } >客户端来访网站分析</td>
    </tr>
    <tr class='tdbg'>
     <td><input name='RegFields_Fill' type='checkbox' value='FBrowser'
        #{if stat_main@is_stat('FBrowser') }checked#{/if } >客户端浏览器分析</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FMozilla'
        #{if stat_main@is_stat('FMozilla') }checked#{/if } >客户端字串分析</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FSystem'
        #{if stat_main@is_stat('FSystem') }checked#{/if } >客户端操作系统分析</td>
    </tr>
    <tr class='tdbg'>
     <td><input name='RegFields_Fill' type='checkbox' value='FScreen'
        #{if stat_main@is_stat('FScreen') }checked#{/if } >客户端屏幕大小分析</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FColor'
        #{if stat_main@is_stat('FColor') }checked#{/if } >客户端屏幕色彩分析</td>
     <td><input name='RegFields_Fill' type='checkbox' value='FKeyword'
        #{if stat_main@is_stat('FKeyword') }checked#{/if } >搜索关键词分析</td>
    </tr>
    <tr class='tdbg'>
     <td><input name='RegFields_Fill' type='checkbox' value='FVisit' 
        #{if stat_main@is_stat('FVisit') }checked#{/if } >访问次数统计分析 </td>
     <td><input name='RegFields_Fill' type='checkbox' value='FYesterDay'
        #{if stat_main@is_stat('FYesterDay') }checked#{/if } >启用昨日统计  </td>
     <td></td>
    </tr>
   </table>
  </td>
 </tr>
</table>
</pub:template>

<pub:template name="temp_callCode">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong>统计计数代码类型：</strong><br>
    [请先选择您想要的输出信息类型]</td>
    <td colspan='3'> 
    <select name='select'  onChange='setFileFileds(this.value)'>
      <option value='1' selected>显示简单样式信息</option>
      <option value='2'>显示普通样式信息</option>
      <option value='3'>显示复杂样式信息</option>
      <option value='4'>统计但不显示信息</option>
    </select>
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong> 显示数据代码：</strong><br>
    请将此模板代码拷贝到您需要做统计的页面，此代码不仅用于向放置了此代码的页面输出统计数据，而且还<font color='red'>对该页面计数</font>。<br></td>
    <td colspan='3'>
      <textarea name='selectKey' cols='50' rows='5' id='selectKey'><script src='\#{InstallDir}counterLink.jsp?style=simple'></script></textarea>
    </td>
  </tr>
  <tr class='tdbg'> 
    <td width='300' height='25' class='tdbg5'><strong> 前台显示在线链接代码：</strong><br>
    请将此模板代码拷贝到您需要显示在线列表链接的模板中，此代码仅用于向放置了此代码的页面显示在线列表链接，而不对该页面计数。<br></td>
    <td colspan='3'>
      <textarea name='LinkContent' cols='50' rows='5' id='LinkContent'><a href='\#{InstallDir}showOnline.jsp' target='_blank'>网站在线情况详细列表</a></textarea>
    </td>
  </tr>
</table>
</pub:template>

<pub:template name="form_button">
<p align='center'>
 <input name='command' type='hidden' id='Command' value='save_config' />
 <input name='cmdSave' type='submit' id='cmdSave' value=' 保存设置 ' />
</p>
</pub:template>

</pub:declare>

<script language="javascript">
function setFileFileds(num){    
  var str="";
  if (num==1){
    str = str += "<scri" + "pt src='#{InstallDir}counterLink.jsp?style=simple'></sc" + "ri" +"pt>";
  } else if(num==2){
    str = str += "<scri" + "pt src='#{InstallDir}counterLink.jsp?style=common'></sc" + "ri" +"pt>";
  } else if(num==3){
    str = str += "<scri" + "pt src='#{InstallDir}counterLink.jsp?style=all'></sc" + "ri" +"pt>";
  } else if(num==4){
    str = str += "<scri" + "pt src='#{InstallDir}counterLink.jsp?style=none'></sc" + "ri" +"pt>";
  }
  document.form1.selectKey.value=str;
}
</script>
<pub:process_template name="main" />

</body>
</html>
