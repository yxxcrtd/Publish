<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>

<pub:template name="channel_operate_success">
<div align="center" class="msgBox">
<div class="msgTitle"></div>
<p>
#{foreach message in action_messages }
 #{(message)}
#{/foreach }
</p>
<p>
#{foreach link in action_links }
 <a href="#{link.url }">#{link.text}</a>
#{/foreach }
</p>
</div>
<script language='javascript'>
// 安全的刷新左侧窗口,一般当更新频道信息之后需要刷新左侧窗口.
if (window.parent != null) {
  if (window.parent.left != null) { // left - 左边frame的名字.
    window.parent.left.window.location.reload();
  }
}
</script>
</pub:template>

<jsp:forward page="admin_base_action.jsp">
  <jsp:param name="__action" value="com.chinaedustar.publish.action.ChannelAction" />
</jsp:forward>

