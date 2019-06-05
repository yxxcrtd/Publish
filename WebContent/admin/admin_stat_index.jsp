<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.StatManage"
%><%
  // 初始化页面数据。
  StatManage manager = new StatManage(pageContext);
  manager.initIndexPage();
  
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

<pub:template name="main">
 #{call stat_header }<br/>
 #{switch command }
  #{case "FVisitor" }
    #{call your_position_visitor }
    #{call show_visitor }
  #{case "FCounter" }
    #{call your_position_visitnum }
    #{call show_visit_num }
  #{case "StatYear" }
    #{call show_stat_year }
  #{case "StatAllYear" }
    #{call show_stat_year }
  #{case "StatMonth" }
    #{call show_stat_month }
  #{case "StatAllMonth" }
    #{call show_stat_month }
  #{case "StatWeek" }
    #{call show_stat_week }
  #{case "StatAllWeek" }
    #{call show_stat_week }
  #{case "StatDay" }
    #{call show_stat_day }
  #{case "StatAllDay" }
    #{call show_stat_day }
  #{case "FIp" }
    #{call your_position_fip }
    #{call show_ip_list }
  #{case "FAddress" }
    #{call show_addr_list }
  #{case "FWeburl" }
    #{call show_weburl_list }
  #{case "FReferer" }
    #{call show_refer_list }
  #{case "FSystem" }
    #{call show_system_list }
  #{case "FBrowser" }
    #{call show_browser_list }
  #{case "FMozilla" }
    #{call show_mozilla_list }
  #{case "FScreen" }
    #{call show_screen_list }
  #{case "FColor" }
    #{call show_color_list }
  #{default }
    #{call your_position_general }
    #{call general_stat_info }
 #{/switch }
 
 <br /><br />
</pub:template>

<pub:template name="stat_header">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' Class='border'>
  <tr class='topbg'>
    <td height='22' colspan='10'>
      <table width='100%'>
        <tr class='topbg'>
          <td align='center'><b>网 站 统 计 管 理</b></td>
          <td width='60' align='right'>
            <a href='http://www.chinaedustar.com/publish/' target='_blank'><img src='images/help.gif' border='0'></a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class='tdbg'>
    <td height='30'>
      <a href='admin_stat_index.jsp'>综合统计</a>&nbsp;|
      <a href='admin_stat_index.jsp?command=FVisitor'>访问记录</a>&nbsp;|    
    <!-- <a href='admin_stat_index.jsp?command=FCounter'>访问次数</a>&nbsp;|  -->
      <a href='admin_stat_index.jsp?command=StatYear'>年 报 表</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=StatAllYear'>全 部 年</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=StatMonth'>月 报 表</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=StatAllMonth'>全 部 月</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=StatWeek'>周 报 表</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=StatAllWeek'>全 部 周</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=StatDay'>日 报 表</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=StatAllDay'>全 部 日</a>&nbsp;|<br>
    <!--  <a href='admin_stat_index.jsp?command=FOnline'>在线用户</a>&nbsp;|  -->
      <a href='admin_stat_index.jsp?command=FIp'>IP 地 址</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=FAddress'>地址分析</a>&nbsp;|    
    <!--  <a href='admin_stat_index.jsp?command=FTimezone'>时区分析</a>&nbsp;| --> 
    <!--  <a href='admin_stat_index.jsp?command=FKeyword'>关 键 词</a>&nbsp;| -->
      <a href='admin_stat_index.jsp?command=FWeburl'>来访网站</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=FReferer'>链接页面</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=FSystem'>操作系统</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=FBrowser'>浏 览 器</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=FMozilla'>字串分析</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=FScreen'>屏幕大小</a>&nbsp;|    
      <a href='admin_stat_index.jsp?command=FColor'>屏幕色深</a>&nbsp;|    
    </td>
  </tr>
</table>
</pub:template>

<pub:template name="show_color_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问者所使用的屏幕色深分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if color_list == null || color_list@size == 0 }
<li>系统中没有访问者屏幕色深统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>屏幕色深</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach color in color_list }
  <tr class='tdbg'>
    <td align=left>#{color.TColor }</td>
    <td align=left>#{color.TColNum }</td>
    <td align=left>#{color.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*color.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_screen_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问者所使用的屏幕分辨率分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if screen_list == null || screen_list@size == 0 }
<li>系统中没有访问者屏幕分辨率统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>屏幕分辨率</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach screen in screen_list }
  <tr class='tdbg'>
    <td align=left>#{screen.TScreen }</td>
    <td align=left>#{screen.TScrNum }</td>
    <td align=left>#{screen.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*screen.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_mozilla_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问者所使用的浏览器特征字串分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if mozilla_list == null || mozilla_list@size == 0 }
<li>系统中没有访问者浏览器特征字串统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='60%' nowrap height='22'>浏览器</td>
    <td align=left width='12%' nowrap>访问人数</td>
    <td align=left width='12%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach browser in mozilla_list }
  <tr class='tdbg'>
    <td align=left>#{browser.TMozilla }</td>
    <td align=left>#{browser.TMozNum }</td>
    <td align=left>#{browser.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*browser.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_browser_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问者所使用的浏览器分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if browser_list == null || browser_list@size == 0 }
<li>系统中没有访问者浏览器统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>浏览器</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach browser in browser_list }
  <tr class='tdbg'>
    <td align=left>#{browser.TBrowser }</td>
    <td align=left>#{browser.TBrwNum }</td>
    <td align=left>#{browser.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*browser.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_system_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问者所使用的操作系统分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if sys_list == null || sys_list@size == 0 }
<li>系统中没有访问者操作系统统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>操作系统</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach sys in sys_list }
  <tr class='tdbg'>
    <td align=left>#{sys.TSystem }</td>
    <td align=left>#{sys.TSysNum }</td>
    <td align=left>#{sys.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*sys.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_refer_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;链接页面地址分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if refer_list == null }
<li>系统中没有链接页面地址统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>链接地址</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach refer in refer_list }
  <tr class='tdbg'>
    <td align=left>#{refer.TRefer }</td>
    <td align=left>#{refer.TRefNum }</td>
    <td align=left>#{refer.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*refer.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_weburl_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;来访网站地址分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if weburl_list == null }
<li>系统中没有来访网站地址统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>地址</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach url in weburl_list }
  <tr class='tdbg'>
    <td align=left>#{url.TWeburl }</td>
    <td align=left>#{url.TWebNum }</td>
    <td align=left>#{url.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*url.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_addr_list">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问者地址分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
#{if addr_list == null }
<li>系统中没有访问者地址统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>地址</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach addr in addr_list }
  <tr class='tdbg'>
    <td align=left>#{addr.TAddress }</td>
    <td align=left>#{addr.TAddNum }</td>
    <td align=left>#{addr.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*addr.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="your_position_fip">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问者IP地址分析</td>
    <td align='right'>有效统计：<font color=red>#{total }</font></td>
  </tr>
</table>
</pub:template>

<pub:template name="show_ip_list">
#{if fip_list == null }
<li>系统中没有访问者 IP 地址统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>IP地址</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach ip in fip_list }
  <tr class='tdbg'>
    <td align=left>#{ip.TIp }</td>
    <td align=left>#{ip.TIpNum }</td>
    <td align=left>#{ip.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*ip.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="your_position_day">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;日访问统计分析</td>
    <td align='right'>有效统计：<font color=red>#{visit_num.total }</font></td>
  </tr>
</table>
</pub:template>

<pub:template name="show_stat_day">
#{if stat_day == null }
<li>系统中没有该日的统计数据。
#{else }
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>小时</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
 #{foreach hour in stat_list }
  <tr class='tdbg'>
    <td align=left>#{hour.hour }时-#{hour.hour+1 }时</td>
    <td align=left>#{hour.visit }</td>
    <td align=left>#{hour.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*hour.percent)@int }' height='10'></td>
  </tr>
 #{/foreach }
</table>
#{/if }
</pub:template>

<pub:template name="show_stat_week">
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>星期</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
  <tr class='tdbg'>
    <td align=left>星期日</td>
    <td align=left>&nbsp;&nbsp;#{stat_week.D1 }</td>
    <td align=left>#{stat_week.p1@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_week.p1)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>星期一</td>
    <td align=left>&nbsp;&nbsp;#{stat_week.D2 }</td>
    <td align=left>#{stat_week.p2@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_week.p2)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>星期二</td>
    <td align=left>&nbsp;&nbsp;#{stat_week.D2 }</td>
    <td align=left>#{stat_week.p2@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_week.p2)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>星期三</td>
    <td align=left>&nbsp;&nbsp;#{stat_week.D3 }</td>
    <td align=left>#{stat_week.p3@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_week.p3)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>星期四</td>
    <td align=left>&nbsp;&nbsp;#{stat_week.D4 }</td>
    <td align=left>#{stat_week.p4@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_week.p4)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>星期五</td>
    <td align=left>&nbsp;&nbsp;#{stat_week.D5 }</td>
    <td align=left>#{stat_week.p5@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_week.p5)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>星期六</td>
    <td align=left>&nbsp;&nbsp;#{stat_week.D6 }</td>
    <td align=left>#{stat_week.p6@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_week.p6)@int }' height='10'></td>
  </tr>
</table>
</pub:template>

<pub:template name="show_stat_month">
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>日期</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
#{foreach day in stat_list }
  <tr class='tdbg'>
    <td align=left>#{stat_month.monthString }#{day.day }日</td>
    <td align=left>&nbsp;&nbsp;#{day.visit }</td>
    <td align=left>#{day.percent@format('#0.00') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*day.percent)@int }' height='10'></td>
  </tr>
#{/foreach }
</table>
</pub:template>

<pub:template name="show_stat_year">
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>月份</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年1月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M1 }</td>
    <td align=left>#{stat_year.p1@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p1)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年2月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M2 }</td>
    <td align=left>#{stat_year.p2@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p2)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年3月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M3 }</td>
    <td align=left>#{stat_year.p3@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p3)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年4月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M4 }</td>
    <td align=left>#{stat_year.p4@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p4)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年5月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M5 }</td>
    <td align=left>#{stat_year.p5@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p5)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年6月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M6 }</td>
    <td align=left>#{stat_year.p6@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p6)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年7月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M7 }</td>
    <td align=left>#{stat_year.p7@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p7)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年8月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M8 }</td>
    <td align=left>#{stat_year.p8@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p8)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年9月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M9 }</td>
    <td align=left>#{stat_year.p9@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p9)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年10月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M10 }</td>
    <td align=left>#{stat_year.p10@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p10)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年11月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M11 }</td>
    <td align=left>#{stat_year.p11@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p11)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>#{stat_year.TYear }年12月</td>
    <td align=left>&nbsp;&nbsp;#{stat_year.M12 }</td>
    <td align=left>#{stat_year.p12@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*stat_year.p12)@int }' height='10'></td>
  </tr>
</table>
</pub:template>

<pub:template name="your_position_visitnum">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;访问次数统计分析</td>
    <td align='right'>有效统计：<font color=red>#{visit_num.total }</font></td>
  </tr>
</table>
</pub:template>

<pub:template name="show_visit_num">
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class=title>
    <td align=left width='30%' nowrap height='22'>次数分析</td>
    <td align=left width='20%' nowrap>访问人数</td>
    <td align=left width='20%' nowrap>百分比</td>
    <td align=left width='30%' nowrap>图示</td>
  </tr>
  <tr class='tdbg'>
    <td align=left>首次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T1 }</td>
    <td align=left>#{visit_num.p1@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{2.20*visit_num.p1 }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>二次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T2 }</td>
    <td align=left>#{visit_num.p2@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p2)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>三次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T3 }</td>
    <td align=left>#{visit_num.p3@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p3)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>四次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T4 }</td>
    <td align=left>#{visit_num.p4@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p4)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>五次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T5 }</td>
    <td align=left>#{visit_num.p5@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p5)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>六次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T6 }</td>
    <td align=left>#{visit_num.p6@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p6)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>七次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T7 }</td>
    <td align=left>#{visit_num.p7@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p7)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>八次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T8 }</td>
    <td align=left>#{visit_num.p8@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p8)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>九次</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T9 }</td>
    <td align=left>#{visit_num.p9@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p9)@int }' height='10'></td>
  </tr>
  <tr class='tdbg'>
    <td align=left>十次以上</td>
    <td align=left>&nbsp;&nbsp;#{visit_num.T10 }</td>
    <td align=left>#{visit_num.p10@format('#0.0') }%</td>
    <td align=left><img src='images/bar.gif' width='#{(2.20*visit_num.p10)@int }' height='10'></td>
  </tr>
</table>
</pub:template>


<pub:template name="your_position_general">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;网站综合统计信息</td>
    <td align='right'>开始统计日期：<font color=blue>#{stat_main.startDate }</font></td>
  </tr>
</table>
</pub:template>


<pub:template name="your_position_visitor">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站统计管理&nbsp;&gt;&gt;&nbsp;最近访问记录</td>
    <td align='right'>共 <font color=red>#{page_info.totalCount }</font> 个访问记录</td>
  </tr>
</table>
</pub:template>

<%-- 综合统计信息 --%>
<pub:template name="general_stat_info">
<table border=0 cellpadding=2 cellspacing=1 width='100%' bgcolor='#FFFFFF' class='border'>
  <tr class='title' align='center'>
    <td align=center width='20%' height='22'>统计项</td>
    <td align=center width='30%'>统计数据</td>
    <td width='20%'>统计项</td>
    <td align='center' width='30%'>统计数据</td>
  </tr>
  <tbody>
  <tr class='tdbg'>
    <td align=center width='20%'>总统计天数</td>
    <td align=center width='30%'>#{stat_info.totalDays }</td>
    <td align=center width='20%'>最高月访量</td>
    <td align=center width='30%'>#{stat_main.monthMaxNum }</td>
  </tr>
  <tr class=tdbg>
    <td align=center width='20%'>总访问量</td>
    <td align=center width='30%'>#{stat_main.totalNum }</td>
    <td align=center width='20%'>最高月访量月份</td>
    <td align=center width='30%'>#{stat_main.monthMaxDate }</td>
  </tr>
  <tr class='tdbg'>
    <td align=center width='20%'>总访问人数</td>
    <td align=center width='30%'>#{stat_info.totalVisitNum }</td>
    <td align=center width='20%'>最高日访量</td>
    <td align=center width='30%'>#{stat_main.dayMaxNum }</td>
  </tr>
  <tr class=tdbg>
    <td align=center width='20%'>总浏览量</td>
    <td align=center width='30%'>#{stat_main.totalNum }</td>
    <td align=center width='20%'>最高日访量日期</td>
    <td align=center width='30%'>#{stat_main.dayMaxDate }</td>
  </tr>
  <tr class='tdbg'>
    <td align=center width='20%'>平均日访量</td>
    <td align=center width='30%'>#{stat_info.avgVisitNum }</td>
    <td align=center width='20%'>最高时访量</td>
    <td align=center width='30%'>#{stat_main.hourMaxNum }</td>
  </tr>
  <tr class=tdbg>
    <td align=center width='20%'>今日访问量</td>
    <td align=center width='30%'>#{stat_main.dayNum }</td>
    <td align=center width='20%'>最高时访量时间</td>
    <td align=center width='30%'>#{stat_main.hourMaxTime }</td>
  </tr>
  <tr class=tdbg>
    <td align=center width='20%'>预计今日访问量</td>
    <td align=center width='30%'>#{stat_info.avgVisitNum }</td>
    <td align=center width='20%'></td>
    <td align=center width='30%'></td>
  </tr>
  <tr bgcolor='#39867B'>
    <td align=center width='20%' height='1'></td>
    <td align=center width='30%' height='1'></td>
    <td align=center width='20%' height='1'></td>
    <td align=center width='30%' height='1'></td>
  </tr>
  <tr class=tdbg>
    <td align=center width='20%'>国内访问人数</td>
    <td align=center width='30%'>#{stat_main.chinaNum }</td>
    <td align=center width='20%'>国外访问人数</td>
    <td align=center width='30%'>#{stat_main.otherNum }</td>
  </tr>
  <tr class='tdbg'>
    <td align=center width='20%'>常用操作系统</td>
    <td align=center width='30%'>
      #{if stat_info.isStatSystem }
        #{stat_info.maxSystem.TSystem } (#{stat_info.maxSystem.TSysNum })
      #{else }
         未设置此项统计
      #{/if }
    </td>
    <td align=center width='20%'>常用浏览器</td>
    <td align=center width='30%'>
      #{if stat_info.isStatBrowser }
        #{stat_info.maxBrowser.TBrowser } (#{stat_info.maxBrowser.TBrwNum })
      #{else }
         未设置此项统计
      #{/if }
    </td>
  </tr>
  <tr class='tdbg'>
    <td align=center width='20%'>访问最多的地址</td>
    <td align=center width='30%'>
      #{if stat_info.isStatAddress }
        #{stat_info.maxAddress.TAddress } (#{stat_info.maxAddress.TAddNum })
      #{else }
         未设置此项统计
      #{/if }
    </td>
    <td align=center width='20%'>访问最多的网站</td>
    <td align=center width='30%'>
      #{if stat_info.isStatWeburl }
        #{stat_info.maxWeburl.TWeburl } (#{stat_info.maxWeburl.TWebNum })
      #{else }
         未设置此项统计
      #{/if }
    </td>
  </tr>
  <tr class=tdbg>
    <td align=center width='20%'>常用屏幕分辨率</td>
    <td align=center width='30%'>
      #{if stat_info.isStatScreen }
        #{stat_info.maxScreen.TScreen } (#{stat_info.maxScreen.TScrNum })
      #{else }
         未设置此项统计
      #{/if }
    </td>
    <td align=center width='20%'>常用屏幕显示颜色</td>
    <td align=center width='30%'>
      #{if stat_info.isStatColor }
        #{stat_info.maxColor.TColor }(#{stat_info.maxColor.TColNum })
      #{else }
         未设置此项统计
      #{/if }
    </td>
  </tr>
  </tbody>
</table>
</pub:template>

<%@include file="element_keyword.jsp" %>

<pub:template name="show_visitor">
<table width='100%' border='0' cellspacing='1' cellpadding='0' class='border'>
  <tr class=title height='22'>
    <td align=center height='22'>访问时间(服务器端)</td>
    <td align=center height='22'>访问时间(客户端)</td>
    <td align=center height='22'>访问者IP</td>
    <td align=center height='22'>地址</td>
    <td align=center height='22'>链接页面</td>
    <td align=center height='22'>操作</td>
  </tr>
 #{foreach visitor in visitor_list }
  <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
    <td align=left width='120' height='22'>#{visitor.visitTime@format }</td>
    <td align=left width='120' height='22'>#{visitor.visitTime@format }</td>
    <td align=left width='80' height='22'>#{visitor.ip }</td>
    <td align=left width='100' height='22'>#{visitor.address }</td>
    <td align=left height='22'><a href='#{visitor.referer }' title='#{visitor.referer }' target='_blank'>#{visitor.referer }</a></td>
    <td align=left width='60' height='22'><a href='admin_stat_detail.jsp?id=#{visitor.id }'>查看明细</a></td>
  </tr>
 #{/foreach }
</table>

#{call keyword_pagination_bar(page_info) }
</pub:template>

<%-- 报表查询表单 --%>
<pub:template name="query_form">
<form name='form1' method='post' action='Admin_Counter.asp'>
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
  <tr class='tdbg'>
    <td width='120'><strong>网站统计查询：</strong></td>
    <td>报表类型：         
      <select name='type' size='1' class='Select' onChange=change_type()>
        <option value='1' selected>日报表</option>
        <option value='2'>月报表</option>
        <option value='3'>年报表</option>
      </select>
      <select name='qyear' size='1' class='Select' onChange=change_it()>
        <option value='2003'>2003</option>
        <option value='2004'>2004</option>
        <option value='2005'>2005</option>
        <option value='2006'>2006</option>
        <option value='2007' selected>2007</option>
        <option value='2008'>2008</option>
        <option value='2009'>2009</option>
        <option value='2010'>2010</option>        
      </select>        年        
      <select name='qmonth' size='1' onChange=change_it()>
        <option value='1'>1</option>
        <option value='2'>2</option>
        <option value='3'>3</option>
        <option value='4'>4</option>
        <option value='5'>5</option>
        <option value='6'>6</option>
        <option value='7'>7</option>
        <option value='8'>8</option>
        <option value='9'>9</option>
        <option value='10'>10</option>
        <option value='11'>11</option>
        <option value='12'>12</option>
      </select>        月        
      <select name='qday' size='1' >
        <option  value='1'>1</option>
        <option  value='2'>2</option>
        <option  value='3'>3</option>
        <option  value='4'>4</option>
        <option  value='5'>5</option>
        <option  value='6'>6</option>
        <option  value='7'>7</option>
        <option  value='8'>8</option>
        <option  value='9'>9</option>
        <option  value='10'>10</option>
        <option  value='11'>11</option>
        <option  value='12'>12</option>
        <option  value='13'>13</option>
        <option  value='14'>14</option>
        <option  value='15'>15</option>
        <option  value='16'>16</option>
        <option  value='17'>17</option>
        <option  value='18'>18</option>
        <option  value='19'>19</option>
        <option  value='20'>20</option>
        <option  value='21'>21</option>
        <option  value='22'>22</option>
        <option  value='23'>23</option>
        <option  value='24'>24</option>
        <option  value='25'>25</option>
        <option  value='26'>26</option>
        <option  value='27'>27</option>
        <option  value='28'>28</option>
        <option  value='29'>29</option>
        <option  value='30'>30</option>
        <option  value='31'>31</option>        
      </select>        日
      <input type='submit' name='Search' value='查询'>
    </td>
    <td width='120' align='center'> </td>
  </tr>
</table>
</form>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
