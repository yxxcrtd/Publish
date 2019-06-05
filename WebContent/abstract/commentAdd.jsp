<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="com.chinaedustar.publish.model.*"%>
<%@page import="com.chinaedustar.publish.*"%>
<%
	ParamUtil putil = new ParamUtil(pageContext);
	String formId = putil.safeGetStringParam("formId", null);
	java.util.List<String> errorList = new java.util.ArrayList<String>();
	if (PublishUtil.isFormValidId(session, formId)) {	
		int channelId = putil.safeGetIntParam("channelId");
		int columnId = putil.safeGetIntParam("columnId");
		int itemId = putil.safeGetIntParam("itemId", 0);
	
		CommentCollection comments = putil.getPublishContext().getSite()
				.getChannels().getChannel(channelId).getColumnTree()
				.getSimpleColumn(columnId).getSimpleItem(itemId)
				.getCommentCollection();
		Comment comment = new Comment();
		User user = PublishUtil.getCurrentUser(session);		
	
		if (user == null) {
			if ("".equals(putil.safeGetStringParam("userName").trim())) {
				errorList.add("输入姓名不能为空！");
			}
		}
	
		if ("".equals(putil.safeGetStringParam("content").trim())) {
			errorList.add("输入内容不能为空！");
		}
	
		if (!errorList.isEmpty()) {
			pageContext.setAttribute("errorList", errorList);		
		} else {
			pageContext.setAttribute("errorList", null);
	
			if (user != null) {
				//先假定一个用户。
				comment.setUserName(user.getUserName());
				comment.setUserType(1);
				comment.setQq("********");
				comment.setMsn("***@msn.com");
				comment.setEmail("***@163.com");
				comment.setHomepage("http://www.***.com");
				comment.setSex(1);
			} else {
				comment.setUserName(putil.safeGetStringParam("userName"));
				comment.setUserType(0);
				comment.setQq(putil.safeGetStringParam("qq"));
				comment.setMsn(putil.safeGetStringParam("msn"));
				comment.setEmail(putil.safeGetStringParam("email"));
				comment.setHomepage(putil.safeGetStringParam("homepage"));
				comment.setSex(putil.safeGetIntParam("sex", 1));
			}
			comment.setScore(putil.safeGetIntParam("score", 3));
			comment.setContent(putil.safeGetStringParam("content"));
			comment.setItemId(itemId);
			comment.setWriteTime(new java.util.Date());
			comment.setIp(request.getRemoteAddr());
			comment.setPassed(false);
	
			putil.getPublishContext().getProxy().insertComment(comments,comment);
		}
	} else {
		errorList.add("请勿重复提交表单！");
		pageContext.setAttribute("errorList", errorList);		
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html> 
 
<pub:template name="main">
  #{call comment_head }
  #{if(errorList == null) }
  	#{call comment_success }
  #{else }
  	#{call comment_error }
  #{/if }  
</pub:template>

<pub:template name="comment_head">
<head>
<title>#{iif(errorList == null, "成功信息", "错误信息") }</title>
<meta http-equiv='Content-Type' content='text/html; charset=gb2312'>
<link href='#{SiteUrl}index.template.css' rel='stylesheet' type='text/css' />
</head>
</pub:template>

<body>
<br>
<br>

<pub:template name="comment_success">
<table cellpadding=2 cellspacing=1 border=0 width=400 class='border' align=center>
	<tr align='center' class='title'>
		<td height='22'>
			<strong>恭喜你！</strong>
		</td>
	</tr>
	<tr class='tdbg'>
		<td height='100' valign='top'>
			<br>
			发表评论成功！请等候管理员的审核！审核后才会显示
		</td>
	</tr>
	<tr align='center' class='tdbg'>
		<td>
			<a href='javascript:window.close();'>【关闭】</a>
		</td>
	</tr>
</table>
</pub:template>

<pub:template name="comment_error">
<table cellpadding=2 cellspacing=1 border=0 width=400 class='border' align=center>
	<tr align='center' class='title'>
		<td height='22'>
			<strong>错误信息</strong>
		</td>
	</tr>
	<tr class='tdbg'>
		<td height='100' valign='top'>
			<b>产生错误的可能原因：</b>
			#{foreach error in errorList }
			<li>#{error }</li>
			#{/foreach }
		</td>
	</tr>
	<tr align='center' class='tdbg'>
		<td>
			<a href='javascript:window.close();'>【关闭】</a>
		</td>
	</tr>
</table>
</pub:template>

<pub:process_template name="main"/>

</body>
</html>
