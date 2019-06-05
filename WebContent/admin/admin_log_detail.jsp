<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.LogManage"
%><%
  // prepare page data.
  LogManage manager = new LogManage(pageContext);
  manager.initDetailPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>日志管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:declare>

<pub:template name="main">
 #{call manage_option }<br/>
 #{call your_position }
 #{call log_detail_info }
</pub:template>

<pub:template name="manage_option">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='10'>
    <table width='100%'>
      <tr class='topbg'>
        <td align='center'><b>网 站 日 志 管 理</b></td>
        <td width='60' align='right'><a
          href='help/index.jsp'
          target='_blank'><img src='images/help.gif' border='0'></a></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='70'>管理导航：</td>
    <td align='left' width='85%'><a href='admin_log.jsp'>全部日志</a></td>
  </tr>
</table>
</pub:template>



<pub:template name="your_position">
<table width='100%'>
  <tr>
    <td align='left'>您现在的位置：网站日志管理&nbsp;&gt;&gt;&nbsp;查看日志详细信息</td>
  </tr>
</table>
</pub:template>


<pub:template name="log_detail_info">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title'>
    <td height='22' colspan='2' align='center'><strong>详  细 信 息</strong></td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>操 作 人：</strong></td>
    <td>#{log.userName }</td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>操作时间：</strong></td>
    <td>#{log.operTime@format }</td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>IP 地 址：</strong></td>
    <td>#{log.userIP }</td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>操作信息：</strong></td>
    <td>
     #{if log.status == 0 }
      #{log.operation}成功
     #{else }
      <font color='red'>#{log.operation}失败, 错误码: #{log.status }</font>
     #{/if }
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>描述：</strong></td>
    <td style='word-break:break-all;Width:fixed'>#{log.description@replace('\r\n', '<br/>') }</td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>访问地址：</strong></td>
    <td>#{log.url }</td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>提交参数：</strong></td>
    <td>
      <textarea cols='90' rows='10' readonly="readonly">#{log.postData@html }</textarea>
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='20%' align='center' class='tdbg5'><strong>更多日志：</strong></td>
    <td>
      #{if prev_log != null }
      <div>前一条日志：<a href='admin_log_detail.jsp?id=#{prev_log.id }'>
        '#{prev_log.userName }' #{prev_log.operation }#{iif (prev_log.status == 0, ' 成功', ' <font color=red>失败</font>') }</a>
      </div>
      #{/if }
      #{if next_log != null }
      <div>后一条日志：<a href='admin_log_detail.jsp?id=#{next_log.id }'>
        '#{next_log.userName }' #{next_log.operation }#{iif (next_log.status == 0, ' 成功', ' <font color=red>失败</font>') }</a>
      </div>
      #{/if }
    </td>
  </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />
</body>
</html>
