<%@page language="java" contentType="text/html; charset=gb2312" %>
<%@page import="com.chinaedustar.publish.model.*" %>
<%@page import="com.chinaedustar.publish.*" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<% 
  out.println("未实现, unimplement");
/*
  // 网站，site 就是 this 对象。
  PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
  ParamUtil putil = new ParamUtil(pageContext);
  java.util.HashMap vars = new java.util.HashMap();
  
  Channel channel = pub_ctxt.getSite().getChannels().getChannel(request.getRequestURI());
  
  vars.put("site", pub_ctxt.getSite());
  vars.put("channel", channel);
  vars.put("this", channel);
  pageContext.setAttribute("channel", vars.get("channel"));  
  
  // 初始化搜索的有关信息,如果没有搜索信息，将返回null。
  SearchInfo sInfo = SearchInfo.getInstance(pageContext);
  if (sInfo != null) {
	  sInfo.init(1);
	  // 设置搜索结果时显示的标题，在标签 ChannelSearchTitle 中会用到。
	  sInfo.resetResultTitle(channel.getItemName());
	  vars.put(LabelHandler.SEARCH_INFO_NAME, sInfo);
  }
  vars.put("ShowAdvanceSearch", sInfo == null);

  // 初始化分页的有关信息
  PublishUtil.initPaginationInfo(pageContext);
  */
%>
<%
 /*
<pub:data var="templ_content" param="article_search" scope="page"
  provider="com.chinaedustar.publish.admin.TemplateContentDataProvider"/> 
  String content = (String)pageContext.getAttribute("templ_content");

  // 2. 执行模板。
  String result = PublishUtil.showTemplatePage(pub_ctxt, content, vars);
  out.write(result);
  */
 %>
