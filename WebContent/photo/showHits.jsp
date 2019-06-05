<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@page import="com.chinaedustar.publish.admin.HitsManage"
%><%
  // 构造数据。
  HitsManage hit_manage = new HitsManage(pageContext);
  HitsManage.ItemHitInfo hit_info = hit_manage.updatePhotoHitsAndGet();
  
  // 此页面被当作一个动态的 js 使用，所以返回 js 脚本。
  // ??? 也许有人专门访问这个页面伪造点击数吗？如果是这样我们如何防止呢？
%>
document.write('<%=hit_info.hits%>');