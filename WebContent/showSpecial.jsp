<%@page language="java" contentType="text/html; charset=gb2312" %>
<%@page import="com.chinaedustar.publish.model.*" %>
<%@page import="com.chinaedustar.publish.*" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<% 
  PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
  ParamUtil putil = new ParamUtil(pageContext);
 
  int specialId = putil.safeGetIntParam("specialId", 0);
  java.util.HashMap vars = new java.util.HashMap();
  Site site = pub_ctxt.getSite();
  vars.put("site", site);
  vars.put("special", site.getSpecialCollection().getSpecial(specialId));
  vars.put("this", vars.get("special"));
%>
