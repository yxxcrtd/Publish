<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" errorPage="admin_error.jsp" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>留言管理</title>
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="main.js"></script>
</head>
<body>
<pub:data var="feedback_list" 
	provider="com.chinaedustar.publish.admin.FeedbackListDataProvider" />
<%@include file="element_keyword.jsp" %>
<%@include file="element_feedback.jsp" %>
<pub:template name="main">
<script type="text/javascript">
<!--
// 批量删除文章。

function deleteFeedback() {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选择任何留言。");
	} else {
		if (confirm("确定要删除选中的这些留言吗？")) {
			var url = "admin_feedback_delete.jsp";
			formAction.action = url;
			formAction.feedbackIds.value=ids;
			formAction.submit();
		}
	}
}

// 审核/取消审核。
function approFeedback(status) {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选择任何留言。");
	} else {
		var url = "admin_feedback_status.jsp";
		formAction.action = url;
		formAction.feedbackIds.value = ids;
		formAction.status.value = status;
		formAction.submit();
	}
}
// -->
</script>

#{call feedback_manage_navigator }
<br />
#{call temp_feedback_list }
<form name="formAction" id="formAction" action="" method="post" style="display:none; ">
	<input type="hidden" name="feedbackIds" value="" />
	<input type="hidden" name="status" value="" />	
</form>
<br />
#{call keyword_pagination_bar(totalNum, totalPage, page, maxPerPage, '条留言') }
<br />
#{call tmpl_search }
</pub:template>

<!-- 留言列表 -->
<pub:template name="temp_feedback_list">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<form name="myform" method="post" action=""	onsubmit="return ConfirmDel();">
		<tr>
			<td>
			<table class="border" border="0" cellspacing="1" width="100%"
				cellpadding="0">
				<tr class="title" height="22">
					<td height="22" width="30" align="center"><strong>选中</strong></td>
					<td width="25" align="center"><strong>ID</strong></td>
					<td align="center"><strong>留言标题</strong></td>
					<td width="60" align="center"><strong>留言人</strong></td>
					<td width="100" align="center"><strong>留言时间</strong></td>
					<td width="60" align="center"><strong>状态</strong></td>
					<td width="75" align="center"><strong>是否已回复</strong></td>
					<td width="100" align="center"><strong>回复时间</strong></td>
					<td width="130" align="center"><strong>常规管理操作</strong></td>
				</tr>
				#{foreach feedback in feedback_list }
				<tr class="tdbg" onmouseout="this.className='tdbg'"	onmouseover="this.className='tdbgmouseover'">
					<td align="center">
						<input name="itemId" type="checkbox" value="#{feedback.id }" />
					</td>
					<td align="center">#{feedback.id }</td>
					<td align="center">
						<a href="admin_feedback_add.jsp?feedbackId=#{feedback.id }"> #{feedback.title } </a>
					</td>
					<td align="center">
						<a href="admin_feedback_list.jsp?field=userName&keyWord=#{feedback.userName }"
							title="点击将查看此用户录入的所有留言"> #{feedback.userName } </a>
					</td>
					<td align="center" width="100">#{feedback.createTime }</td>
					<td align="center">
						#{if (feedback.status == 0) }
						<font color="red">未审核</font> 
						#{elseif (feedback.status == 1) }
						审核通过
						#{endif }
					</td>
					<td align="center">
						#{if (feedback.feedbackUser == null) }
						<font color="red">否</font>
						#{else }
						<font color="black">是</font>
						#{/if }
					</td>
					<td width="100" align="center">
						#{feedback.feedbackTime }
					</td>
					<td align="center">
						<a href="admin_feedback_add.jsp?feedbackId=#{feedback.id }"> 修改 </a> 
						#{if (feedback.status == 0) }
						<a href="admin_feedback_status.jsp?feedbackIds=#{feedback.id }&status=1"> 审核通过 </a> 
						#{else }
						<a href="admin_feedback_status.jsp?feedbackIds=#{feedback.id }&status=0"> 取消审核 </a> 
						#{/if }
						<a href="admin_feedback_delete.jsp?feedbackIds=#{feedback.id }"
							onclick="return confirm('确定要删除此留言吗？');"> 删除 </a> 
					</td>
				</tr>
				#{/foreach }
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="200" height="30"><input name="chkAll"
						type="checkbox" id="chkAll" onclick="CheckAll(this)"
						value="checkbox" /> 选中本页显示的所有#{channel.itemName}</td>
					<td>
					<input type="button" value=" 批量删除 " onClick="deleteFeedback()" /> 
					<input type="button" value=" 审核通过 " onClick="approFeedback(1)" /> 
					<input type="button" value=" 取消审核 " onClick="approFeedback(0)" /> 
					</td>
				</tr>
			</table>
			</td>
		</tr>
		</form>
	</table>
</pub:template>

<pub:template name="tmpl_search">
	<form method="get">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="border">
		<tr class="tdbg">
			<td width="80" align="right"><strong>留言搜索：</strong></td>
			<td><select name="field" size="1">
				<option value="title" selected="selected">留言标题</option>
				<option value="content">留言内容</option>
				<option value="userName">留言人</option>
				<option value="feedbackContent">回复内容</option>
			</select> 
			<input type="text" name="keyWord" size="20" value="关键字" maxlength="50" onFocus="this.select();" /> 
			<input type="submit" value="搜索"/>
			</td>
		</tr>
	</form>

</pub:template>

<pub:process_template name="main" />
</body>
</html>