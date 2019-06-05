<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="currentUser" scope="session" class="com.chinaedustar.users.CurrentUserBean"></jsp:useBean>
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<link type="text/css" rel="stylesheet" href="../Skin/TH/style.css"/>
</head>
<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<div style="text-align:right;padding-top:10px">
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
  <a id="aaa" href="<%=loginUrl%>" target="_parent">登 录</a> 
  <script>
  	var surl="<%=loginUrl%>";
  	var purl=self.parent.document.location.href;
  	surl=surl+"/login.jsp?RedUrl="+purl;
  	document.all["aaa"].href=surl;
  </script>
<%}
else
{

	out.print("欢迎您: " + userName);
	out.print("&nbsp;|&nbsp;<a href='http://ae.ee.tsinghua.edu.cn/groups/"+userLoginName+"' target='_blank'><font color=red>进入个人空间</font></a>");
	out.print("&nbsp;|&nbsp;<a href='logout.jsp'><font color=red>退出登录</font></a>");
}
%>&nbsp;
</div>
</body>
</html>