<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>

<%-- 友情链接等页面使用的公共模板块 --%>
<pub:template name="cp_admin_help">
<table width='100%' border='0' align='center' cellpadding='2'
	cellspacing='1' class='border'>
	<tr class='topbg'>
		<td height='22' colspan='2' align='center'>
			<strong>自 定 义 页 面 管 理</strong>
		</td>
	</tr>
	<tr class='tdbg'>
		<td width='70' height='30'>	<strong>管理导航：</strong></td>
		<td>
			<a href='admin_custompage.jsp'>自定义页面管理首页</a>&nbsp;|&nbsp;
			<a href='admin_cp_kind_add.jsp'>添加自定义分类</a>&nbsp;|&nbsp;
			<a href='admin_custompage_add.jsp'>添加自定义页面</a>&nbsp;|&nbsp;
			<a href='admin_cp_kind_import.jsp'>导入自定义分类</a>&nbsp;|&nbsp;
			<a href='admin_cp_kind_export.jsp'>导出自定义分类</a>&nbsp;|&nbsp;
		</td>
	</tr>
</table>
</pub:template>
