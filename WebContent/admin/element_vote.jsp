<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%-- 
调查管理的导航部分，

@param channel 对象。 
@param text 描述文字，如：调查管理首页|添加新调查

--%>
<pub:template name="vote_navigator">
#{param text }
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='2'  align='center'><strong>网站调查管理</strong></td>  </tr>
  <tr class='tdbg'>
    <td width='70' height='30'><strong>管理导航：</strong></td>
    <td><a href='admin_vote_list.jsp'>调查管理首页</a>&nbsp;|&nbsp;<a href='admin_vote_add.jsp?action=Add'>添加新调查</a></td>
  </tr>
</table>

<table width='100%' border='0' align='center' cellpadding='2'cellspacing='1' class='border'>
	<tr class='title'>
		<td height='22'>| 
		<a href='admin_vote_list.jsp?channelId=0'>网站首页调查</a>
		#{foreach channel0 in channel_list }
		#{if (channel0.id == channel.id) }
		| <font color='red'>#{channel0.name }</font> 
		#{else }
		| <a href='admin_vote_list.jsp?channelId=#{channel0.id }'>#{channel0.name }</a>
		#{/if }
		#{/foreach }
		</td>
	</tr>
</table>
	<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>
	<tr>
		<td height='22'>您现在的位置：网站调查管理&nbsp;&gt;&gt;&nbsp;#{channel.name }调查</td>
	</tr>
</table>
</pub:template>
