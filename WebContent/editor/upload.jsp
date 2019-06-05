<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.*"
%><%@page import="com.chinaedustar.publish.model.Channel"
%><%
  // 初始化页面数据.
  ParamUtil paramUtil = new ParamUtil(pageContext);
  int channelId = paramUtil.safeGetIntParam("channelId");
  // 文件类型，0：附件；1：图片；2：视频文件；3：RealPlay文件；4：Flash动画。
  int fileType = paramUtil.safeGetIntParam("fileType", 0);
  String caller = paramUtil.safeGetStringParam("caller");
  Channel channel = paramUtil.getPublishContext().getSite().getChannel(channelId);
  
  if (channel == null) {
    out.println("非法频道标识，请确定从有效链接进入。");
	 return;
  }

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>文件上传</title>
 <link rel='stylesheet' type='text/css' href='editor_dialog.css' />
<script language=javascript>
<!--
// 频道定义的可上传文件的类型.
var fileType = <%=fileType %>;
var channelExts = "<%=channel.getUpFileType().replaceAll("\\|", ",") %>".toLowerCase();
var fileExts = [];
if (fileType == 1) { // 图片
	fileExts.push("bmp", "gif", "jpg", "jpeg", "tiff", "tif", "psd", "png", "pcx", "dxf", "wmf", "emf", "lic", "eps", "tga");
} else if (fileType == 2) { // 视频文件
	fileExts.push("avi", "mpeg", "mpg", "dat", "mov", "qt", "asf", "wmv", "mp3", "m3u", "mpv", "mps", "mpe", "m1v", "m2v", "mpa", "mp4", "m4e", "wav", "mp1", "mp2", "mpga", "aif", "aiff", "mid", "midi", "vpg");
} else if (fileType == 3) { // RealPlay文件
	fileExts.push("ra", "rm", "ram", "rmvb", "rpm", "rt", "rp", "smi", "smil", "avi", "rv", "rmi");
} else if (fileType == 4) { // Flash文件
	fileExts.push("swf");
} else { 

} 
if (fileType > 0) { 
	var tempExts = [];
	for (var i = 0; i < fileExts.length; i++) {
		if (channelExts.indexOf(fileExts[i]) != -1) {
			tempExts.push(fileExts[i]);
		}
	}
	channelExts = tempExts.join(",");
}

// 提交上传之前的验证.
function check() {
    var strFileName=document.form1.fileName.value;
    if (strFileName=='')
    {
        alert('请选择要上传的文件');
        document.form1.fileName.focus();
        return false;
    } else {
    	var fileExt = '';
      if (strFileName.lastIndexOf("\.") >= 0)
        fileExt = strFileName.substring(strFileName.lastIndexOf("\.") + 1, strFileName.length).toLowerCase();
    	if (channelExts.indexOf(fileExt) == -1) {
    		alert("不支持扩展名为" + fileExt + "的文件，只能上传扩展名为 " + channelExts + " 的文件。");
    		return false;
    	} else {
    		return true;
    	}
    }
}
// -->
</script>
</head>
<body class='Filebg' leftmargin='5' topmargin='0'>
<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td>
<form action='upfile.jsp' method='post' name='form1' onSubmit='return check()' enctype='multipart/form-data'>
  <input name='fileName' type='file' class='FileButton' size='35' />
  <input type='submit' value='上传' />
  <input name='fileType' type='hidden' id='fileType' value='<%=fileType %>' />
  <input name='channelId' type='hidden' id='channelId' value='<%=channelId %>' /> 
  <input name='caller' type='hidden' value='<%=caller %>' />
</form>
</td></tr></table>
<script type="text/javascript">
if (document.body.scrollWidth < 330) {
	document.form1.fileName.size="25";
}
</script>
</body>
</html>
