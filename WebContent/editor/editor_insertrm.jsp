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
<TITLE>插入RealPlay文件</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="editor_dialog.css">
<script language="JavaScript">
// 频道定义的可上传文件的类型
var fileType = 3;
var channelExts = "<%=channel.getUpFileType().replaceAll("\\|", "，") %>".toLowerCase();
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
	channelExts = tempExts.join("，");
}

function OK(){
    var str1="";
    var strurl=document.form1.url.value;
    if (strurl==""||strurl=="http://"||strurl=="rtsp://"){
        alert("请先输入RealPlay文件地址，或者上传RealPlay文件！");
        document.form1.url.focus();
        return false;
    }else{
        str1 = "<object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width="+document.form1.width.value+" height="+document.form1.height.value+"><param name='CONTROLS' value='ImageWindow'><param name='CONSOLE' value='Clip1'><param name='AUTOSTART' value='-1'><param name=src value="+document.form1.url.value+"></object><br><object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA'  width="+document.form1.width.value+" height=60><param name='CONTROLS' value='ControlPanel,StatusBar'><param name='CONSOLE' value='Clip1'></object>"
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
//过程名：ShowRm
//作  用：在线显示RM
//参  数：element   --- 返回表单值
//=================================================
function ShowRm(){
       if(document.form1.url.value=="http://"){
           document.Form1.url.Value = "地址"
       }
      objFiles.innerHTML = "<object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width="+document.form1.width.value+" height="+document.form1.height.value+"><param name='CONTROLS' value='ImageWindow'><param name='CONSOLE' value='Clip1'><param name='AUTOSTART' value='-1'><param name=src value="+document.form1.url.value+"></object><br><object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA'  width="+document.form1.width.value+" height=60><param name='CONTROLS' value='ControlPanel,StatusBar'><param name='CONSOLE' value='Clip1'></object>"
}
function SelectFile(){
    var arr=showModalDialog('../admin/admin_upload_list.jsp?selectFile=true&channelId=<%=channel.getId()%>', '', 'dialogWidth:820px; dialogHeight:600px; help: no; scroll: yes; status: no');
    // id$$$fileName&&&filePath
	if(arr!=null){
	    var ss=arr.split('$$$');
	    var fileName = ss[1];
	    var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
	    if (channelExts.indexOf(fileExt.toLowerCase()) == -1) {
	    	alert("不支持扩展名为 " + fileExt + " 的 RealPaly 视频文件，只支持扩展名为 " + channelExts + " 的文件。");
	    	return;
	    }
        document.form1.url.value=ss[2];
        document.form1.UpFileName.value = arr;
        ShowRm();
    }
}

// 文件的属性。
var fileParams = {};
// 文件保存成功之后，返回必须的一些属性
function setFileOptions(options) {
	fileParams = options;
	document.form1.url.value = options.url;
	// 视频的 fileName 在页面中没有必要显示出来。
}

</script>
</head>
<BODY bgColor=#D4D0C8 topmargin=15 leftmargin=15 >
<form name="form1" method="post" action="">
 <table width=100% border="0" cellpadding="0" cellspacing="2">
  <tr>
   <td>
    <FIELDSET align=left>
    <LEGEND align=left>RealPlay文件参数</LEGEND>
     <TABLE border="0" cellpadding="0" cellspacing="3">
        <tr><td  height=5></td></tr>
        <tr>
          <td width=350 align='center' id='objFiles'>
        <!-- **********    RM开始　********** -->
              <object id="player" name="player" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="300" height="220">
            <param name="CONTROLS" value="Imagewindow">
            <param name="CONSOLE" value="clip1">
            <param name="AUTOSTART" value="0">
            <param name="SRC" value="">
            </object><br>
            <object ID="RP2" CLASSID="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" WIDTH="300" HEIGHT="60">
            <PARAM NAME="CONTROLS" VALUE="ControlPanel,StatusBar">
            <param name="CONSOLE" value="clip1">
            </object>
        <!-- **********    RM结束　********** -->
          </td>
        </tr>
    <tr><td align='center' height='5'></td></tr>
      <TR>
        <TD >地址：<INPUT name="url" id=url  value="rtsp://" size=40 onChange="javascript:ShowRm()">
        <%if (isUpload) { %>
             <input type="button" name="Submit" value="..." title="从已上传文件中选择" onClick="SelectFile()">
        <% } %>
        </td>
      </TR>
      <TR>
       <TD>宽度：<INPUT name="width" id=width ONKEYPRESS="event.returnValue=IsDigit();" value=300 size=7 maxlength="4" onChange="javascript:ShowRm()"> &nbsp;&nbsp;高度：<INPUT name="height" id=height ONKEYPRESS="event.returnValue=IsDigit();" value=200 size=7 maxlength="4" onChange="javascript:ShowRm()">
       </TD>
      </TR>
      <TR>
        <TD align=center>支持格式为：<script language="javascript">document.write(channelExts);</script></TD>
      </TR>
     </TABLE>
     </fieldset>
    </td>
    <td width=80 align="center"><input name="cmdOK" type="button" id="cmdOK" value="  确定  " onClick="OK();">
    <br>
    <br>  <input name="cmdCancel" type=button id="cmdCancel" onclick="window.close();" value='  取消  '>
    </td>
 </tr>
 <%if (isUpload) { %>
 <tr>
   <td>
   <FIELDSET align=left>
    <LEGEND align=left>上传本地视频文件</LEGEND>
        <iframe class="TBGen" style="top:2px" id="UploadFiles" src="upload.jsp?fileType=4&channelId=<%=channel.getId() %>&fileType=3"
         frameborder=0 scrolling=no width="350" height="25"></iframe>
        </fieldset></td>
        </tr>
  
<% } %>
 <input name="UpFileName" type="hidden" id="UpFileName" value="None">
</table>
</form>
</body>
</html>