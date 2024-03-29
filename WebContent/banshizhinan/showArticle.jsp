<%@page language="java" contentType="text/html; charset=UTF-8" 
  pageEncoding="UTF-8" import="com.chinaedustar.common.util.StringHelper" %>
<jsp:directive.page import="com.chinaedustar.publish.model.Column"/>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="com.chinaedustar.publish.*"%>
<% 
  // 网站主页，site 就是 this 对象。
  PublishContext pub_ctxt = PublishUtil.getPublishContext(pageContext);
  // pageContext.setAttribute("this", pub_ctxt.getSite());
  // pageContext.setAttribute("site", pub_ctxt.getSite());
  java.util.HashMap vars = new java.util.HashMap();
  
  vars.put("site", pub_ctxt.getSite());
  vars.put("channel", pub_ctxt.getSite().getChannels().getChannel(Integer.parseInt(request.getParameter("channelId"))));
  vars.put("column", ((com.chinaedustar.publish.model.Channel) vars.get("channel")).getColumnTree().getColumn(Integer.parseInt(request.getParameter("columnId"))));
  vars.put("article", ((com.chinaedustar.publish.model.Column) vars.get("column")).loadArticle(Integer.parseInt(request.getParameter("articleId"))));
  vars.put("this", vars.get("article"));

  /*
    
  vars.put("this", column);
  vars.put("site", site);
  */
  
  // 1. 从数据库中读取出模板内容，为了方便测试，我们先从文件中读取吧。
  String templ_content = test.Util.readFileContent(
		  application.getRealPath("/news/article_template.html"));

  // 2. 执行模板。
  String result = PublishUtil.showTemplatePage(pub_ctxt, templ_content, vars);
  out.write(result);
%>

<hr>
<h2>DEBUG   INFORMATION</h2>
<div style="border:1px solid blue; margin:3px; padding:3px;">
  <pre>
  <%= StringHelper.htmlEncode(templ_content)%>
  </pre>
</div>
<br /><br />
