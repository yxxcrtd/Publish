<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目属性－－删除</title>
</head>
<body>
<jsp:include flush="true" page="admin_base_action.jsp">
	<jsp:param name="action" value="com.chinaedustar.publish.action.ItemDeleteAction" />
	<jsp:param name="debug" value="true" />
</jsp:include>
</body>
</html>