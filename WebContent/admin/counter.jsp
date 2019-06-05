<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.admin.StatManage" 
%><%// 产生实际的计数。
  StatManage counter = new StatManage(pageContext);
  counter.performCount();%>
<span>
TODO: 总访问量：xxx 人次；<br/>
总浏览量：xxx 人次；
</span>
