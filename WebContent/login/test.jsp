<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="com.chinaedustar.guv.*" 

%><%
String PassportUrl = "";
if(application.getAttribute("PassportUrl") == null)
{
	  out.print("统一用户没有配置好，无法继续使用。");
	  return;
}
else
{
	  PassportUrl = application.getAttribute("PassportUrl").toString();
}
User oUser = new User();
oUser.SetVerUrl(PassportUrl);
//out.print("<li>oUser.getLoginId()"+oUser.getLoginId());
oUser.Close(request, response);

out.print("<li>oUser.getLoginId()="+oUser.getLoginId());
out.print("<li>oUser.getLoginId()="+oUser.getLoginId());
out.print("<li>"+PassportUrl);

//Cookie cNewCookie = new Cookie("UserTicket", "");
//cNewCookie.setMaxAge(0);
//response.addCookie(cNewCookie);




%>