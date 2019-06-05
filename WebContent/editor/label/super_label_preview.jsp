<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.chinaedustar.publish.PublishUtil"/>
<jsp:directive.page import="com.chinaedustar.publish.PublishContext"/>
<jsp:directive.page import="com.chinaedustar.publish.model.*"/>
<jsp:directive.page import="java.util.*"/>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>标签预览</title>
 <link href='../../Skin/DefaultSkin.css' rel='stylesheet' type='text/css' />
</head>
<body leftmargin="0" topmargin="0">
<%
  PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
  
  Map<String, Object> vars = new HashMap<String, Object>();
  Site site = pub_ctxt.getSite();
  Channel channel = site.getChannels().getTestChannel();
  // Column column = channel.getColumnTree().getColumn(channel.getRootColumnId());
  
  vars.put("site", site);
  vars.put("channel", channel);
  vars.put("this", channel);
  // vars.put("column", column);

  String labelContent = "";
  labelContent = request.getParameter("LABEL_CONTENT");
  
  String result = PublishUtil.showTemplatePage(pub_ctxt, labelContent, vars);
  out.println(result);
%>

</body>
</html>

<!-- labelContent = <%=PublishUtil.HtmlEncode(labelContent)%> -->
