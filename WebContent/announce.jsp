<%@page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:directive.page import="com.chinaedustar.publish.model.Site"/>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="com.chinaedustar.publish.*"%>
<% 
  //网站主页，site 就是 this 对象。 ����
  PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
  java.util.HashMap vars = new java.util.HashMap();
  Site site = pub_ctxt.getSite();
  vars.put("site", site);
  vars.put("this", vars.get("site"));
%>
<pub:data var="templ_content" param="announce" scope="page"
	provider="com.chinaedustar.publish.admin.TemplateContentDataProvider"/>	
<%
  String content = (String)pageContext.getAttribute("templ_content");

  // 2. 执行模板
  String result = PublishUtil.showTemplatePage(pub_ctxt, content, vars);
  out.write(result);
 %>