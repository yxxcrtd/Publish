<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="com.chinaedustar.guv.*" 
%><%
String PassportUrl = "";
if(application.getAttribute("PassportUrl") == null)
{
	  out.print("统一用户没有配置好，无法继续使用。");
	  return;
}
else
{
	  PassportUrl = application.getAttribute("PassportUrl").toString();
}
User user= new User();
user.SetVerUrl(PassportUrl);
user.Close(request,response);
%>

<iframe name="fff" style="display:none"></iframe>
<script type="text/javascript">
self.parent.document.location.href=self.parent.document.location.href;
</script>
</body>
</html>
