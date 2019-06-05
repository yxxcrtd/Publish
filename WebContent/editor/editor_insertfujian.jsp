<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@page import="com.chinaedustar.publish.*"%>
<%@page import="com.chinaedustar.publish.model.Channel"%>
<%
Channel channel = null;
ParamUtil paramUtil = new ParamUtil(pageContext);
int channelId = paramUtil.safeGetIntParam("channelId");
int showType = paramUtil.safeGetIntParam("showType");
boolean isUpload = false;
if (channelId < 1) {
	channel = new Channel();
	channel.setUpFileType("");
} else {
	channel = paramUtil.getPublishContext().getSite().getChannels().getChannel(channelId);
}
if (channelId > 0 && channel.getEnableUploadFile() && (showType == 0 || showType == 4 || showType == 5)) {
	isUpload = true;
}
%>
<HTML>
<HEAD>
<TITLE>插入上传附件</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="editor_dialog.css">
<script language="JavaScript">

function OK(){
    var str="";
    var strurl=document.form1.url.value;
    if (strurl==""||strurl=="http://"){
        alert("请先输入上传文件的地址！");
        document.form1.url.focus();
        return false;
    }else if (document.form1.title.value==""){
        alert("附件名称不能为空！");
        document.form1.title.focus();
        return false;
    }else{
        str="<a href='"+document.form1.url.value+"' title='"+document.form1.title.value+"'>"+document.form1.title.value+"</a>"
        window.returnValue=str+"$$$"+document.form1.UpFileName.value;
        window.close();
    }
}
function IsDigit()
{
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}
function SelectFile(){
  var arr=showModalDialog('../admin/admin_upload_list.jsp?selectFile=true&channelId=<%=channel.getId()%>', '', 'dialogWidth:820px; dialogHeight:600px; help: no; scroll: yes; status: no');
  // id$$$fileName&&&filePath
  if(arr!=null){
    var ss=arr.split('$$$');
    var fileName = ss[1];
    var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
    document.form1.url.value=ss[2];
  }
}

// 文件的属性。
var fileParams = {};
// 文件保存成功之后，返回必须的一些属性
function setFileOptions(options) {
	fileParams = options;
	document.form1.url.value = options.url;
	document.form1.UpFileName.value = options.fileName;
	document.form1.title.value = options.oldFileName;
}
</script>
</head>
<BODY bgColor=#D4D0C8 topmargin=15 leftmargin=15 >
<form name="form1" method="post" action="">
 <table width=100% border="0" cellpadding="0" cellspacing="2">
   <tr>
     <td>
       <FIELDSET align=left>
       <LEGEND align=left>上传附件参数</LEGEND>
       <TABLE border="0" cellpadding="0" cellspacing="3" >
        <TR>
     <TD height="17" >地址：<INPUT name="url" id=url value="http://" size=40>
        <%if (isUpload) { %>
             <input type="button" name="Submit" value="..." title="从已上传文件中选择" onClick="SelectFile()">
        <% } %>
     </td>
        </TR>
        <TR>
      <TD >请输入附件名称：<INPUT TYPE="text" NAME="title" size="20"></TD></TR>
        <TR>
      <TD align='center'><FONT style='font-size:12px' color='#339900'>支持格式为 <%=channel.getUpFileType().replaceAll("\\|", "，").toLowerCase() %></FONT>
      </TD>
    </TR>
       </TABLE>
       </fieldset>
     </td>
     <td width=80 align="center"><input name="cmdOK" type="button" id="cmdOK" value="  确定  " onClick="OK();">
     <br><br><input name="cmdCancel" type=button id="cmdCancel" onclick="window.close();" value='  取消  '>
     </td>
   </tr>
   <%if (isUpload) { %>
    <tr>
      <td><fieldset align=left>
      <legend align=left>上传本地附件</legend>
    
        <iframe class="TBGen" style="top:2px" id="UploadFiles" src="upload.jsp?fileType=0&channelId=<%=channelId %>&fileType=0"
         frameborder="0" scrolling="no" width="350" height="25"></iframe>
        </fieldset></td>
    </tr>
    <% } %>
    <tr>
      <td height=5></td>
    </tr>
    <input name="UpFileName" type="hidden" id="UpFileName" value="None">
  </table>
</form>
</body>
</html>

