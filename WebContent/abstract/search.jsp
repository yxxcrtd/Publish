<%@page language="java" contentType="text/html; charset=gb2312" %>
<%@page import="com.chinaedustar.publish.model.*" %>
<%@page import="com.chinaedustar.publish.*" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<% 
  out.println("δʵ��, unimplement");
/*
  // ��վ��site ���� this ����
  PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
  ParamUtil putil = new ParamUtil(pageContext);
  java.util.HashMap vars = new java.util.HashMap();
  
  Channel channel = pub_ctxt.getSite().getChannels().getChannel(request.getRequestURI());
  
  vars.put("site", pub_ctxt.getSite());
  vars.put("channel", channel);
  vars.put("this", channel);
  pageContext.setAttribute("channel", vars.get("channel"));  
  
  // ��ʼ���������й���Ϣ,���û��������Ϣ��������null��
  SearchInfo sInfo = SearchInfo.getInstance(pageContext);
  if (sInfo != null) {
	  sInfo.init(1);
	  // �����������ʱ��ʾ�ı��⣬�ڱ�ǩ ChannelSearchTitle �л��õ���
	  sInfo.resetResultTitle(channel.getItemName());
	  vars.put(LabelHandler.SEARCH_INFO_NAME, sInfo);
  }
  vars.put("ShowAdvanceSearch", sInfo == null);

  // ��ʼ����ҳ���й���Ϣ
  PublishUtil.initPaginationInfo(pageContext);
  */
%>
<%
 /*
<pub:data var="templ_content" param="article_search" scope="page"
  provider="com.chinaedustar.publish.admin.TemplateContentDataProvider"/> 
  String content = (String)pageContext.getAttribute("templ_content");

  // 2. ִ��ģ�塣
  String result = PublishUtil.showTemplatePage(pub_ctxt, content, vars);
  out.write(result);
  */
 %>
