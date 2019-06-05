<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>增加/修改风格</title>
<link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body>

<jsp:include flush="true" page="admin_base_action.jsp">
	<jsp:param name="action" value="com.chinaedustar.publish.action.SkinSaveAction" />
	<jsp:param name="debug" value="true" />
</jsp:include>

</body>
</html>