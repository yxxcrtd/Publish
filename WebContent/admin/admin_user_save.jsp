<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>保存会员信息</title>
</head>
<body>
<jsp:include page="admin_base_action.jsp">
	<jsp:param name="action" value="com.chinaedustar.publish.action.UserSaveAction" />
	<jsp:param name="debug" value="true" />
</jsp:include>
</body>
</html>