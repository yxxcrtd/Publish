<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<pub:template name="generate_enque_succ">
<br /><br />
<table width='80%' align='center' cellspacing='0' cellpadding='0'>
<tr><td>
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
<div>
 <iframe src='admin_generate_statistic.jsp' border='1' width='600' height='300'></iframe>
</div>
</div>
</td></tr>
</table>
</pub:template>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.GenerateAction" />
</jsp:forward>

<%
 if (false) {
   com.chinaedustar.publish.action.GenerateAction action = new 
     com.chinaedustar.publish.action.GenerateAction();
   out.println("action = " + action);
 }
%>