<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%
if (false) {
  com.chinaedustar.publish.action.ArticleAction action = new com.chinaedustar.publish.action.ArticleAction();
  out.println("<hr><li>action = " + action);
}
%>

<pub:template name="article_add_success">
<br /><br />
<table class='border' align='center' border='0' cellpadding='2' cellspacing='1'>
  <tr class='title'>
    <td height='22' align='center' colspan='2'><b>添加/修改文章成功</b></td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
    <td width='400'>#{article.column.name}
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>文章标题：</strong></td>
    <td width='400'>#{article.title@html}</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong></td>
    <td width='400'>#{article.author@html}</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>文章来源：</strong></td>
    <td width='400'>#{article.source@html}</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>关 键 字：</strong></td>
    <td width='400'>#{article.keywords@html}</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'><strong>文章状态：</strong></td>
    <td width='400'>TODO: 待审核 </td>
  </tr>
  <tr class='tdbg' align='center'>
    <td height='30' colspan='2'>
    【<a href='admin_article_add.jsp?channelId=#{article.channelId}&articleId=#{article.id}'>修改本文</a>】
    【<a href='admin_article_add.jsp?channelId=#{article.channelId}&columnId=#{article.columnId}'>继续添加文章</a>】
    【<a href='admin_article_list.jsp?channelId=#{article.channelId}&columnId=#{article.columnId}'>文章管理</a>】
    【<a href='admin_article_view.jsp?channelId=#{article.channelId}&articleId=#{article.id}'>查看文章内容</a>】
    </td>
  </tr>
</table>
</pub:template>

<jsp:forward page="admin_base_action.jsp">
 <jsp:param name="__action" value="com.chinaedustar.publish.action.ArticleAction" />
</jsp:forward>
