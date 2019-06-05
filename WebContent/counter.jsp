<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@page import="com.chinaedustar.publish.admin.StatManage"
%><%@page import="com.chinaedustar.publish.stat.*"
%><%
  StatManage manager = new StatManage(pageContext);
  GeneralStatInfo stat_info = manager.performCount();
  StatMain stat_main = stat_info.getStatMain();
  String style = manager.getStyle();
  String showInfo = "";
  if ("simple".equalsIgnoreCase(style)) {
    showInfo = "总访问量: " + (stat_main.getTotalNum() + stat_main.getOldTotalNum()) + "人次<br/>";
    if (stat_main.isCountOnline())
      showInfo += "当前在线: " + stat_info.getOnlineNum();
  } else if ("all".equalsIgnoreCase(style)) {
    showInfo = "总访问量: " + (stat_main.getTotalNum() + stat_main.getOldTotalNum()) + "人次<br/>";
	 showInfo += "总浏览量: " + (stat_main.getTotalView() + stat_main.getOldTotalView()) + "人次<br/>";
 	 showInfo += "今日访问：" + stat_main.getDayNum() + "人次<br/>";
	 showInfo += "日均访问：" + (0) + "人次<br/>";
    if (stat_main.isCountOnline())
      showInfo += "当前在线: " + stat_info.getOnlineNum();
  }
  
  if ("none".equalsIgnoreCase(style) == false) {
%>
  document.write('<%= showInfo %>');
<% } %>
