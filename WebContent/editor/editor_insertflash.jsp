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
} else {
	channel = paramUtil.getPublishContext().getSite().getChannels().getChannel(channelId);
}
if (channelId > 0 && channel.getEnableUploadFile() && (showType == 0 || showType == 4 || showType == 5)) {
	isUpload = true;
}
%>
<HTML>
<HEAD>
<TITLE>插入FLASH文件</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="editor_dialog.css">
<script language="JavaScript">
//=================================================
//过程名：OK()
//作  用：提交信息
//=================================================
function OK(){
    var str1="";
    var strurl=document.form1.url.value;
    if (strurl==""||strurl=="http://"){
        alert("请先输入FLASH文件地址，或者上传FLASH文件！");
        document.form1.url.focus();
        return false;
    }else{
        str1 = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'  codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' width=" + document.form1.width.value + " height=" + document.form1.height.value + "><param name=movie value=" + document.form1.url.value + "><param name=quality value=high><embed src=" + document.form1.url.value + " pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width=" + document.form1.width.value + " height=" + document.form1.height.value + "></embed></object>"
        window.returnValue = str1+"$$$"+document.form1.UpFileName.value;
        window.close();
    }
}
//=================================================
//过程名：IsDigit()
//作  用：输入为数字
//=================================================
function IsDigit()
{
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}
//=================================================
//过程名：imgwidth
//作  用：在线显示Flash宽度
//参  数：element   --- 返回表单值
//=================================================
function swfModify(){
    if(document.form1.url.value=="http://"){
        document.form1.url.value = "logo3.swf"
    }
    objFiles.innerHTML = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'  codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' width=" + document.form1.width.value + " height=" + document.form1.height.value + "><param name=movie value=" + document.form1.url.value + "><param name=quality value=high><embed src=" + document.form1.url.value + " pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width=" + document.form1.width.value + " height=" + document.form1.height.value + "></embed></object>"
}
function SelectFile(){
	var arr=showModalDialog('../admin/admin_upload_list.jsp?selectFile=true&channelId=<%=channel.getId()%>', '', 'dialogWidth:820px; dialogHeight:600px; help: no; scroll: yes; status: no');
	// id$$$fileName&&&filePath
	if(arr!=null){
	    var ss=arr.split('$$$');
	    var fileName = ss[1];
	    var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
	    var flashExts = "swt";
	    if (flashExts.indexOf(fileExt.toLowerCase()) == -1) {
	    	alert("不支持扩展名为 " + fileExt + " 的FLASH文件，只支持扩展名为 " + flashExts + " 的文件。");
	    	return;
	    }
        document.form1.url.value=ss[2];
        document.form1.UpFileName.value = arr;
        swfModify();
    }
}

// 文件的属性。
var fileParams = {};
// 文件保存成功之后，返回必须的一些属性
function setFileOptions(options) {
	fileParams = options;
	document.form1.url.value = options.url;
	document.form1.UpFileName.value = options.fileName;
}
</script>
</head>
<body bgColor=#D4D0C8 topmargin=15 leftmargin=15 >
<form name="form1" method="post" action="">
  <table width=100% border="0" cellpadding="0" cellspacing="2">
    <tr>
      <td>
      <FIELDSET align=left>
      <LEGEND align=left>FLASH动画参数</LEGEND>
        <table border="0" cellpadding="0" cellspacing="3" >
          <tr>
            <td  height=5></td>
          </tr>
          <tr>
            <td width=350 align='center' id='objFiles'>
            <IMG SRC='images/filetype_flash.gif'  id=img align='center' width='300' height='200'  BORDER='0' ALT=''>
            </td>
          </tr>
          <tr>
            <td align='center' height='5'></td>
          </tr>
          <tr>
            <td height="17" >地址：
             <Input name="url" id=url value="http://"  onChange="javascript:swfModify()" size=45>
            <%if (isUpload) { %>
             <Input type="button" name="Submit" value="..." title="从已上传文件中选择" onClick="SelectFile()">
            <% } %>
            </td>
          </tr>
          <tr>
            <td>宽度：
             <Input name="width" id=width ONKEYPRESS="event.returnValue=IsDigit();" onChange="javascript:swfModify()" value=300 size=7 maxlength="4">   高度：
             <Input name="height" id=height ONKEYPRESS="event.returnValue=IsDigit();" onChange="javascript:swfModify()" value=200 size=7 maxlength="4">
            </TD>
          </tr>
        </table>
        </fieldset>
      </td>
      <td width=80 align="center">
       <Input name="cmdOK" type="button" id="cmdOK" value="  确定  " onClick="OK();">
      <br><br>
       <Input name="cmdCancel" type=button id="cmdCancel" onclick="window.close();" value='  取消  '>
      </td>
    </tr>
    <%if (isUpload) { %>
    <tr>
      <td>
      <fieldset align=left>
      <LEGEND align=left>上传本地FLASH文件</LEGEND>
        <iframe class="TBGen" style="top:2px" id="UploadFiles" src="upload.jsp?fileType=5&channelId=<%=channel.getId() %>&fileType=4"
	        frameborder="0" scrolling="no" width="350" height="25"></iframe>
      </fieldset></td>
     </tr>
    <% } %>
     <Input name="UpFileName" type="hidden" id="UpFileName" value="None">
  </table>
  </form>
</body>
</html>
