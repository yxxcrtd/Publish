<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>增加/修改文章</title>
</head>
<body>
<jsp:include flush="true" page="../admin/admin_base_action.jsp">
	<jsp:param name="action" value="com.chinaedustar.publish.action.ArticleSaveAction" />
	<jsp:param name="backPage" value="../user/user_article_list.jsp" />
	<jsp:param name="debug" value="true" />
</jsp:include>
</body>
</html>