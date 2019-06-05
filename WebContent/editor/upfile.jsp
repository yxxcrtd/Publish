<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.*"
%><%@page import="com.chinaedustar.publish.util.*"
%><%@page import="java.util.*" %>
<%@page import="com.chinaedustar.publish.model.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>文件上传</title>
 <link rel='stylesheet' type='text/css' href='editor_dialog.css' />
</head>
<body class='Filebg' leftmargin='2' topmargin='2'>
<%
  // 我们这里一次只是上传一个文件.
  UploadFileUtil upload = new UploadFileUtil(request);
  Map<String, String> params = upload.getParams();
  // out.println("upfile.jsp params = " + params);

  // reqeust.getParameter 需要参数 channelId
  int channelId = params.containsKey("channelId") ? Integer.parseInt(params.get("channelId")) : 0;
  int fileType = params.containsKey("fileType") ? Integer.parseInt(params.get("fileType")) : 0;
  // 用来标识调用的页面，好区分返回的options是从哪个页面发出的.
  String caller = params.get("caller"); if (caller == null) { caller = ""; }

  // 获得相应对象, site, channel.
  ParamUtil paramUtil = new ParamUtil(pageContext);
  Site site = paramUtil.getPublishContext().getSite();
  Channel channel = site.getChannel(channelId);
  if (channel == null) {
    out.println("需要有频道的信息。");
    return;
  }

  // 频道的上传目录, 如 'news/upload/'.
  String channelDir = channel.getChannelDir() + "/" + channel.getUploadDir() + "/";
  // 实际上载上来的文件存放位置, 如 'd:/publish/news/upload/';
  String fileFolder = application.getRealPath("/") + channelDir;
  
  // 开始保存上传文件。
  List<UploadInfo> list = upload.upload(fileFolder);
  if (list != null && !list.isEmpty()) {
    UpFile[] upFiles = new UpFile[list.size()];
    Admin admin = PublishUtil.getCurrentAdmin(session);
    String adminName = admin == null ? "" : admin.getAdminName();
    UpFile upFile = null;
    UploadInfo options = null;
    for (int i = 0; i < list.size(); i++) {
      // fileOldPathName, fileOldName, fileNewPathName, fileNewName, fileExt, fileSize, contentType, fieldName, width, heigth
      options = list.get(i);
      upFile = new UpFile();
      upFile.setChannelId(channelId);
      upFile.setName(options.getFileNewName());
      upFile.setFileExt(options.getFileExt());
      String fileNewPath = channelDir + options.getFileNewPathName();
      fileNewPath = fileNewPath.replace('\\', '/');
      upFile.setFilePath(fileNewPath);
      upFile.setUploadTime(new Date());
      upFile.setOldName(options.getFileOldName());
      upFile.setUserName(adminName);
      
      // channel.getUpFileCollection().insertUpFile(upFile);
      upFiles[i] = upFile;
	 }
    // 保存到 数据库里面.
	 channel._getPublishContext().getTransactionProxy().insertUpFiles(channel.getUpFileCollection(), upFiles);
	 options.setId(upFile.getId());
  }
  
  // 一般用第一个上传成功的文件.
  UploadInfo first_upfile = list.get(0);
  String options_rel_url = channelDir + first_upfile.getFileNewPathName().toString().replace('\\', '/');
  String options_url = site.getUrl() + options_rel_url;
%>
<span style="color:red">文件上传成功！</span> 
<!-- 
<a href='upload.jsp?fileType=<%=fileType %>&channelId=<%=channelId %>'>继续上传</a>
 -->
<script language='javascript'>
//parent.setFileValue(id, url, fileName, fileExt) {
var options = {};
options.id = <%=first_upfile.getId() %>;
options.rel_url = "<%=options_rel_url%>";
options.url = "<%=options_url%>";
options.fileName = "<%=first_upfile.getFileNewName() %>";
options.oldFileName = "<%=first_upfile.getFileOldName() %>";
options.fileExt = "<%=first_upfile.getFileExt() %>";
options.width = <%=first_upfile.getWidth() %>;
options.height = <%=first_upfile.getHeight() %>;
options.caller = "<%=caller%>";
parent.setFileOptions(options);
</script>
<%
/* 上面输出的例子:
  调用父窗体的 setFileOptions 方法，传递过去 options 参数，其中含有字段:
//parent.setFileValue(id, url, fileName, fileExt) {
var options = {};
options.id = 86;    // 图片标识.
options.rel_url = "news/UploadFiles/2007/08/20070816114843704.jpg"; // 相对于网站的 url 地址.
options.url = 'http://localhost:8080/PubWeb/news/...'; // 完整 url.
options.fileName = "20070816114843704.jpg";
options.oldFileName = "测试用新闻图片.jpg";
options.fileExt = "jpg";
options.width = 450;
options.height = 311;
options.caller = "";
parent.setFileOptions(options);
*/
%>
</body>
</html>