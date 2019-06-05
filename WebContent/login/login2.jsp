<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="currentUser" scope="session" class="com.chinaedustar.users.CurrentUserBean"></jsp:useBean>
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<link type="text/css" rel="stylesheet" href="../Skin/TH/style.css"/>
</head>
<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<!--登陆框-->
<%
currentUser.init(application,request);
String ErrDescription=currentUser.ErrDescription;
String loginUrl="#";
//login.jsp?RedUrl=http%3A%2F%2Fwww.ae.ee.tsinghua.edu.cn%2Fspace%2Flogin.jsp
if(ErrDescription==null) ErrDescription="";
boolean islogin=false;
String userLoginName="";
String userName="";
if(!ErrDescription.equals(""))
{
	islogin=false;
	  loginUrl=currentUser.getReturnUrl();
	  if(loginUrl.equals(""))
	  {
		  //out.println(ErrDescription);
	  }
	  else
	  {
		  //out.println("<li>"+ErrDescription);	  
		  //out.println("<li>请<a href=\""+loginUrl+"\" target=\"_blank\">登陆</a>");	  		  
	  }
}
else
{
	islogin=true;
	userLoginName=currentUser.getLoginName();
	userName=currentUser.getName();
}

%>

<%
if (!islogin)
{
String url=request.getRequestURL().toString();
//loginUrl=loginUrl+"/login.jsp?RedUrl="+url;
%>
<table border="0" cellpadding="0" cellspacing="0" width="228" height=114  style="background:url(../images/nologin.jpg)">
  <tr>
  	<td width=148 height="66"></td>
  	<td></td>
  </tr>
  <tr>
   <td></td>
   <td valign="top" align="left"><a id="aaa" href="<%= loginUrl%>" target="_parent"><img border=0 src="../images/go.gif" width=24 height=21></a></td>
  </tr>
  </table>
  <script>
  	var surl="<%=loginUrl%>";
  	var purl=self.parent.document.location.href;
  	surl=surl+"/login.jsp?RedUrl="+purl;
  	document.all["aaa"].href=surl;
  </script>
<%}
else
{
%>
<table border="0" cellpadding="0" cellspacing="0" width="228" height=114  style="background:url(../images/loginwelcome.jpg)">
  <tr>
  	<td width=100>
  	</td>
  	<td>
<%
	out.println("欢迎您: "+userName);
	out.println("<br>进入<a href='http://ae.ee.tsinghua.edu.cn/groups/"+userLoginName+"' target='_blank'><font color=red>个人空间</font></a>");
	out.println("<br><a href='logout.jsp'><font color=red>退出登录</font></a>");
%>  	
  	</td>
  </tr>
  </table>
<%} %>
</body>
</html>