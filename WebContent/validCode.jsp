<%@page import="com.chinaedustar.publish.util.ValidCodeImage" %><%
	new ValidCodeImage().outputImage(response, session);
%>