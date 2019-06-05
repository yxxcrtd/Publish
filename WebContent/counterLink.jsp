<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" 
%><%@page import="com.chinaedustar.publish.admin.StatManage"
%><%
  // 调用示例：<script src='#{InstallDir}counterLink.jsp?style=simple'></script>
  StatManage manager = new StatManage(pageContext);
  String style = manager.getStyle();
  String theurl = manager.getTheUrl();
  int intervalNum = manager.getIntervalNum();
  boolean isCountOnline = manager.isCountOnline();
%>
var style = '<%=style%>';
var url = '<%=theurl%>';
var intervalNum = <%=intervalNum%>;
var i = 0;
<% if (isCountOnline) { %>
doPageRef(0);
<% } %>
// send a online stat per 60 seconds.
function doPageRef() {
  if(i <= intervalNum) {
    var ref_image = new Image();
    ref_image.src = url + 'statOnline.jsp';
    setTimeout('doPageRef()', 60000);
  }
  ++i;
}
document.write("<scr" + "ipt language=javascript src=" + url + 
  "counter.jsp?style=" + style + "&Referer=" + escape(document.referrer) + 
  "&Timezone=" + escape((new Date()).getTimezoneOffset()) + 
  "&Width=" + escape(screen.width) + "&Height=" + escape(screen.height) + 
  "&Color=" + escape(screen.colorDepth) + "></sc" + "ript>");
