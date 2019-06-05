<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%@page import="com.chinaedustar.publish.*" %>
<%@page import="com.chinaedustar.publish.action.*" %>

<%
  // 获取参数，'__action', '__debug'.
  ParamUtil param_util = new ParamUtil(pageContext);
  String action = param_util.getRequestParam("__action");
  boolean debug = param_util.safeGetBooleanParam("__debug", false);
  // 在开发的时候需要详细的调试信息.
  if (debug == false)
    debug = "LIUJUNXING".equalsIgnoreCase(System.getenv("COMPUTERNAME"));
  
  String templateType = "template_exception_default";
  AbstractAction action_obj = null;
  try {
    // 实例化操作类。
    Class clazz = Class.forName(action);
    action_obj = (AbstractAction)clazz.newInstance();
    
    // 初始化并执行。
    try {
      action_obj.initialize(pageContext);
      action_obj.execute();
    } finally {
      try { action_obj.terminate(); } catch (Exception exIgnore) { }
    }
    
    // 获得返回结果。
    templateType = action_obj.getMessageTemplateType();
    pageContext.setAttribute("action_messages", action_obj.getActionMessages());
    pageContext.setAttribute("action_links", action_obj.getActionLinks());
  } catch (Exception exx) {
    templateType = action_obj == null ? "template_exception_default" : action_obj.getExceptionTemplateType();
    String action_exception_message = exx.getMessage();
    if (action_exception_message == null)
    	action_exception_message = exx.toString();
    pageContext.setAttribute("action_exception_message", action_exception_message);
    pageContext.setAttribute("action_exceptions", exx.getStackTrace());
    java.util.List<Object> ex_list = new java.util.ArrayList<Object>();
    ex_list.add(exx);
    Throwable cause = exx.getCause();
    while (cause != null) {
      ex_list.add(cause);
      cause = cause.getCause();
    }
    pageContext.setAttribute("exception_list", ex_list);
  } finally {
    pageContext.setAttribute("show_template", templateType);
  }

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>返回信息</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
<style>
.msgBox {
  font-family: 宋体, verdana;
  font-size:12px;
  border:blue;
  padding:1px;
  border:solid 1px #0650D2;
}
.msgBox .msgTitle {
  background-color:#0650D2;height:16px;color:#FFFFFF;padding:2px;
}
</style>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'><pub:declare>

<%-- 空白的消息模板。 --%>
<pub:template name="template_message_blank">
<br/><br/>
<table width='80%' align='center' cellspacing='0' cellpadding='0'>
<div align="center" class="msgBox">
<div class="msgTitle"></div>
<ul>
#{foreach message in action_messages }
 <li>#{(message)}</li>
#{/foreach }
</ul>
<br/>
<div>
#{foreach link in action_links }
 <a href="#{link.url }">#{link.text}</a>
#{/foreach }
</div>
</div>
</table>
</pub:template>

<%-- 默认的消息模板。 --%>
<pub:template name="template_message_default">
<br /><br />
<table width='80%' align='center' cellspacing='0' cellpadding='0'>
<tr><td>
<div align="center" class="msgBox">
<div class="msgTitle"></div>
<ul>
#{foreach message in action_messages }
 <li>#{(message)}</li>
#{/foreach }
</ul><br/>
<div>
#{foreach link in action_links }
 <a href="#{link.url }">#{link.text }</a>
#{/foreach }
</div>
</div>
</td></tr>
</table>
</pub:template>

<%-- 错误的消息模板。 --%>
<pub:template name="template_message_wrong">
<div align="center" class="msgBox" style="border-color:red;">
<div class="msgTitle" style="background-color:red;">出错了</div>
<ul style="color:red">
#{foreach message in action_messages }
 <li>#{(message)}</li>
#{/foreach }
</ul><br/>
<div>
#{foreach link in action_links }
 <a href="#{link.url }">#{link.text }</a>
#{/foreach }
</div>
</div>
</pub:template>


<%-- ============= 异常类型的模板 ========================================== --%>
<%-- 空白的异常模板。 --%>
<pub:template name="template_exception_blank">
 <div align="left" class="msgBox" style="color:red">
 <div class="msgTitle">发生异常</div>
<p>页面发生异常，异常原因：#{(action_exception_message) }</p>

#{foreach ex in exception_list}
<h4>原因：#{(ex) }</h4>
 <ul>
 #{foreach stack in ex.stackTrace }
  <li>#{(stack)}
 #{/foreach }
 </ul>
#{/foreach }
</div>
</pub:template>

<%-- 默认的异常模板。 --%>
<pub:template name="template_exception_default">
 <div align="left" class="msgBox" style="color:red">
 <div class="msgTitle">出错了</div>
<p>页面发生异常，异常：#{(action_exception_message) }</p>

#{foreach ex in exception_list}
<h4>原因：#{(ex) }</h4>
 <ul>
 #{foreach stack in ex.stackTrace }
  <li>#{(stack)}
 #{/foreach }
 </ul>
#{/foreach }
</div>
</pub:template>

<pub:template name="main">
 #{call dynamic(show_template) }
</pub:template>

</pub:declare>
<pub:process_template name="main" />

<% if (debug) { %>
<hr/><br/><br/><br/>
<pub:page_debug />
<br/><br/><br/><br/>
<%@include file="_debug_req_param.jsp" %>
<br/><br/><br/><br/>
<% } %>

</body>
</html>