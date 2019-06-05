<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章审核，修改 status 的值。</title>
</head>
<body>
<jsp:include flush="true" page="admin_base_action.jsp">
	<jsp:param name="action" value="com.chinaedustar.publish.action.ItemApproAction" />
	<jsp:param name="debug" value="true" />
</jsp:include>
</body>
</html>