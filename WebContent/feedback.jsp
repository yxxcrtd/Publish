<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@page import="com.chinaedustar.publish.*"
%><%@page import="com.chinaedustar.publish.admin.PageHandler"
%><%@page import="com.chinaedustar.publish.model.*"
%><% 
  ParamUtil paramUtil = new ParamUtil(pageContext);
  PublishContext pub_ctxt = paramUtil.getPublishContext();
  java.util.HashMap<String, Object> vars = new java.util.HashMap<String, Object>();

  Site site = pub_ctxt.getSite();
  FeedbackCollection collection = site.getFeedbackCollection();
  int feedbackId = paramUtil.safeGetIntParam("feedbackId");
  Feedback feedback = (Feedback)collection.getFeedback(feedbackId);
  vars.put("feedbackCollection", collection);
  vars.put("this", feedback);
  vars.put("feedback", feedback);
  
  PageHandler handler = new PageHandler(pageContext);
  handler.showHomeGeneralPage("feedback", vars);
%>
