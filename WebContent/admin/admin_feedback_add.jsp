<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" errorPage="admin_error.jsp" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>留言管理--修改/审核/回复</title>
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
function checkForm() {
	if (myForm.title.value.length < 1) {
		alert("留言标题不能为空。");
		myForm.title.focus();
		return false;
	}
	return true;
}
</script>
</head>
<body>
<!-- 留言对象的数据提供者 -->
<pub:data var="feedback"
	provider="com.chinaedustar.publish.admin.FeedbackDataProvider" />

<%@include file="element_feedback.jsp" %>
<pub:template name="main">
#{call feedback_manage_navigator }
<br />
<form method='post' name='myform' onsubmit='return checkForm()' action='admin_feedback_save.jsp'>
<table border='0' cellpadding='3' cellspacing='1' align='center' width='100%' class='border'>
	<tr class='title'>
	<td height='22' colspan='2' align='center'><strong>修改留言</strong></td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">留言标题：</td>
		<td><input type="text" name="title" value="#{feedback.title }" style="width: 300;"></td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">留言人：</td>
		<td>
		<input type="hidden" name="userName" value="#{feedback.userName }">
		#{feedback.userName }
		</td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">留言内容：</td>
		<td>
			<textarea rows="5" cols="60" name="content">#{feedback.content }</textarea>
		</td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">留言时间：</td>
		<td>
		#{feedback.createTime }
		<input type="hidden" name="createTime" value="#{feedback.createTime }"></td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">回复人：</td>
		<td>
		#{iif (feedback.feedbackUser == null, "无",  feedback.feedbackUser)}
		<input type="hidden" name="feedbackUser" value="#{feedback.feedbackUser }"></td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">回复内容：</td>
		<td>
		<textarea rows="5" cols="60" name="feedbackContent">#{feedback.feedbackContent }</textarea>
		</td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">回复时间：</td>
		<td>
		#{iif (feedback.feedbackTime == null, "无", feedback.feedbackTime) }
		<input type="hidden" name="feedbackTime" value="#{feedback.feedbackTime }"></td>
	</tr>
	<tr class='tdbg'>
		<td align="right" width="120">审核状态：</td>
		<td>
			<input type="checkbox" name="status" value="1" #{iif (feedback.status==1, "checked", "") } >审核通过
		</td>
	</tr>
	<tr class='tdbg'>
		<td colspan="2" align="center">
		<input type="hidden" name="feedbackId" value="#{feedback.id }">
		<input type="hidden" name="isDisplay" value="#{feedback.isDisplay }">
		<input type="submit" value="保存">
		<input type="button" value="取消" onclick="location.href='admin_feedback_list.jsp'">
		</td>
	</tr>
</table>
</form>

</pub:template>

<pub:process_template name="main"/>
</body>
</html>