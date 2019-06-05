<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>风格管理</title>
</head>
<body>
	<jsp:include flush="true" page="admin_base_action.jsp">
		<jsp:param name="action" value="com.chinaedustar.publish.action.SkinRefreshCssAction" />
		<jsp:param name="debug" value="true" />
	</jsp:include>
</body>
</html>