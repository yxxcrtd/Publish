<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.*"
%><%@page import="com.chinaedustar.publish.model.Channel"
%><%
  /// 这个文件被 editor_new.jsp 文件调用，其使用此文件插入图片.
  /// 其中参数 channelId, showType 从 editor_new.jsp 
  /// 页面传递过来，(从原始调用 editor_new.jsp 页面传递过来)
  ParamUtil paramUtil = new ParamUtil(pageContext);
  int channelId = paramUtil.safeGetIntParam("channelId");
  int showType = paramUtil.safeGetIntParam("showType");
  
  Channel channel = null;
  if (channelId > 0) {
    channel = paramUtil.getPublishContext().getSite().getChannel(channelId);
  }
  
  boolean isUpload = false;
  if (channelId > 0 && channel.getEnableUploadFile() && (showType == 0 || showType == 4 || showType == 5)) {
    isUpload = true;
  }
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link rel="stylesheet" type="text/css" href="editor_dialog.css">
</head>
<body bgColor=#D4D0C8 onload="InitDocument()">
<script language="JavaScript">
var sAction = "INSERT";
var sTitle = "插入";

var oControl;
var oSeletion;
var sRangeType;

var sFromUrl = "http://";
var sAlt = "";
var sBorder = "0";
var sBorderColor = "";
var sFilter = "";
var sAlign = "";
var sWidth = "350";
var sHeight = "200";
var sVSpace = "";
var sHSpace = "";
var UpFileName="None";

var sCheckFlag = "file";

/// 这里假定被某个窗口调用，其参数为 dialogArguments, 并且里面有 HtmlEdit 对象.
/// 其中 dialogArguments 是父窗口.
if (window.dialogArguments != null) {
  oSelection = dialogArguments.HtmlEdit.document.selection.createRange();
  sRangeType = dialogArguments.HtmlEdit.document.selection.type;
} else {
  alert('这个页面被设计从别的页面做为对话框调用, 请确定您是从有效的链接进入的.');
}

if (sRangeType == "Control") {
  if (oSelection.item(0).tagName == "IMG"){
    sAction = "MODI";
    sTitle = "修改";
    sCheckFlag = "url";
    oControl = oSelection.item(0);
    sFromUrl = oControl.getAttribute("src", 2);
    sAlt = oControl.alt;
    sBorder = oControl.border;
    sBorderColor = oControl.style.borderColor;
    sFilter = oControl.style.filter;
    sAlign = oControl.align;
    sWidth = oControl.width;
    sHeight = oControl.height;
    sVSpace = oControl.vspace;
    sHSpace = oControl.hspace;
  }
}

document.title = '图片属性(' + sTitle + ')';

/// 搜索下拉框值与指定值匹配，并选择匹配项.
function SearchSelectValue(o_Select, s_Value){
  for (var i=0;i<o_Select.length;i++){
    if (o_Select.options[i].value == s_Value){
      o_Select.selectedIndex = i;
      return true;
    }
  }
  return false;
}

/// 初始值.
function InitDocument() {
  SearchSelectValue(styletype, sFilter);
  SearchSelectValue(aligntype, sAlign.toLowerCase());
      
  url.value = sFromUrl;
  alttext.value = sAlt;
  border.value = sBorder;
  t_bordercolor.value = sBorderColor;
  s_bordercolor.style.backgroundColor = sBorderColor;
  width.value = sWidth;
  height.value = sHeight;
  vspace.value = sVSpace;
  hspace.value = sHSpace;
  upfilename.value = UpFileName;
    
  if (sAction == "MODI") {
    frmPreview.img.src =sFromUrl;
    frmPreview.img2.src =sFromUrl;
    frmPreview.img.alt=sAlt;
    frmPreview.img.border=sBorder;
    frmPreview.img.style.borderColor =sBorderColor;
    frmPreview.img.style.backgroundColor = sBorderColor;
    frmPreview.img.width=sWidth;
    frmPreview.img.height=sHeight;
    frmPreview.img.vspace=sVSpace;
    frmPreview.img.hspace=sHSpace;
    frmPreview.img.style.filter=sFilter;
  }
}

function OK() {
  sFromUrl = document.all.url.value;
  sAlt = document.all.alttext.value;
  sBorder = document.all.border.value;
  sBorderColor = document.all.t_bordercolor.value;
  sFilter = document.all.styletype.options[styletype.selectedIndex].value;
  sAlign = document.all.aligntype.value;
  sWidth = frmPreview.img.width;
  sHeight = frmPreview.img.height;
  sVSpace = document.all.vspace.value;
  sHSpace = document.all.hspace.value;
  UpFileName = document.all.upfilename.value;
  if (sFromUrl == "" || sFromUrl == "http://") {
    alert("请先输入图片文件地址，或者上传图片文件！");
    url.focus();
    return false;
  }
    
  if (sAction == "MODI") {
    // 修改现有图片属性.
    oControl.src = sFromUrl;
    oControl.alt = sAlt;
    oControl.border = sBorder;
    oControl.style.borderColor = sBorderColor;
    oControl.style.filter = sFilter;
    oControl.align = sAlign;
    oControl.width = sWidth;
    oControl.height = sHeight;
    oControl.style.width = sWidth;
    oControl.style.height = sHeight;
    oControl.vspace = sVSpace;
    oControl.hspace = sHSpace;
  }else{
    var sHTML = '';
    var style = '';
    if (sFilter != "")
      style += 'filter:' + sFilter + ';';
    if (sBorderColor != "")
      style += 'border-color:' + sBorderColor + ';';
    if (style != "")
      sHTML = ' style="' + style + '"';

    sHTML = '<img id=HtmlEdit_TempElement_Img src="' + sFromUrl + '"' + sHTML;
    if (sBorder != "")
        sHTML += ' border="' + sBorder + '"';
    if (sAlt != "")
        sHTML += ' alt="' + sAlt + '"';
    if (sAlign != "")
        sHTML += ' align="' + sAlign + '"';
    if (sWidth != "")
        sHTML += ' width="' + sWidth + '"';
    if (sHeight != "")
        sHTML += ' height="' + sHeight + '"';
    if (sVSpace != "")
        sHTML += ' vspace="' + sVSpace + '"';
    if (sHSpace != "")
        sHTML += ' hspace="' + sHSpace + '"';
    sHTML += '/>';
    // debug: alert(sHTML);
    
    dialogArguments.insertHTML(sHTML);
    // 难道我们不需要等待 浏览器完成 sHTML 的解析吗 ??
    var oTempElement = dialogArguments.HtmlEdit.document.getElementById("HtmlEdit_TempElement_Img");
    oTempElement.src = sFromUrl;
    oTempElement.removeAttribute("id");
  }

  if (UpFileName == "None") {
    window.returnValue = null;
  } else {
    window.returnValue = UpFileName;
  }
  window.close();
}

function IsDigit()
{
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}
//=================================================
//过程名：Preview
//作  用：在线显示图片
//=================================================
function Preview(){
  if (url.value != "http://" && url.value != "") {
    frmPreview.img.src = url.value;
    frmPreview.img2.src = url.value;
  }
  else{
    frmPreview.img.src = "images/nopic.gif";
    frmPreview.img2.src = "images/nopic.gif";
  }
  var iheight = height.value;
  var iwidth = width.value;
  if(iheight > 0){
    if(iwidth > 0){
      frmPreview.img.height=iheight;
      frmPreview.img.width=iwidth
    }
    else{
      frmPreview.img.height=iheight;
      frmPreview.img.width=iheight/frmPreview.img2.height*frmPreview.img2.width;
    }
  }
  else{
    if(iwidth>0){
      frmPreview.img.width=iwidth
      frmPreview.img.height=iwidth/frmPreview.img2.width*frmPreview.img2.height;
    }
    else{
      frmPreview.img.height=frmPreview.img2.height;
      frmPreview.img.width=frmPreview.img2.width;
    }
  }

  frmPreview.img.border=border.value;
  frmPreview.img.style.borderColor =t_bordercolor.value;
  frmPreview.img.style.filter=filter.value;
  frmPreview.img.title=alttext.value
}

// 过程名：SelectColor
// 作  用：显示颜色表.// 参  数：what  --- 要获得颜色的参数.
function SelectColor(what) {
  var dEL = document.all("t_"+what);
  var sEL = document.all("s_"+what);
  var url = "editor_selcolor.html?color="+dEL.value;
  var arr = showModalDialog(url,window,"dialogWidth:280px;dialogHeight:250px;help:no;scroll:no;status:no");
  if (arr) {
    dEL.value=arr;
    sEL.style.backgroundColor=arr;
  }
}

// 从上传的文件中选择图片.function SelectFile(){
  var arr=showModalDialog('../admin/admin_upload_list.jsp?selectFile=true&channelId=<%=channelId%>', 
    '', 'dialogWidth:820px; dialogHeight:600px; help: no; scroll: yes; status: no');
  // id$$$fileName&&&filePath
  if(arr!=null){
    var ss=arr.split('$$$');
    var fileName = ss[1];
    var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
    var imageExts = "jpg,jpeg,tif,bmp,bm,png,psd,gif";
    if (imageExts.indexOf(fileExt.toLowerCase()) == -1) {
     alert("不支持扩展名为 " + fileExt + " 的图片文件，只允许扩展名为 " + imageExts + " 的图片文件。");
     return;
    }
    upfilename.value = arr;
    url.value=ss[2];
    Preview();
  }
}

// 文件的属性, 仅设置了值, 但从未使用.var fileParams = {};

// 文件保存成功之后，返回必须的一些属性 (函数从 upfile.jsp 页面调用).function setFileOptions(options) {
  fileParams = options;
  document.all.url.value = options.url;
  var title = options.oldFileName;
  if (title != null && title.lastIndexOf('.') > 0)
    title = title.substring(0, title.lastIndexOf('.'));
  document.all.alttext.value = title;
  document.all.width.value = options.width;
  document.all.height.value = options.height;
  document.all.upfilename.value = options.id + "$$$" + options.fileName + "$$$" + options.url;
  // debug: alert('upfilename.value = ' + upfilename.value);

  // 预览.  Preview();
}
</script>

<table border=0 cellpadding=0 cellspacing=0 align=center width='95%'>
  <tr>
    <td><fieldset>
    <legend>预览图片</legend>
      <table border=0 cellpadding=0 cellspacing=5>
        <tr>
          <td align='center'><iframe id='frmPreview' width='350' height='220' frameborder='1' src='editor_imgpreview.html'></iframe></td>
        </tr>
        <tr>
          <td>地址：           <input name="url" type=text id="url" style="width:243px" onchange="javascript:Preview()" size='30' />
          <%if (isUpload) {%>
           <!--'增加房产模块用到的 ShowType=3-->
           <input type="button" name="Submit" value="..." title="从已上传文件中选择" onclick="SelectFile()">
          <% } %>
          </td>
        </tr>
      </table>
    </fieldset></td>
    <td width='80' align="center" valign="middle" rowspan="6">
      <table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'>
        <tr>
          <td height='230'></td>
        </tr>
        <tr>
          <td width='20'></td>
          <td >
           <input type="hidden" id="upfilename" value="" />
           <input type=submit value='  确定  ' id='Ok' onclick="OK()"><BR><BR>
           <input type=button value='  取消  ' onclick="window.close();">
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height=5></td>
  </tr>
  <tr>
    <td><fieldset>
    <legend>显示效果</legend>
      <table border=0 cellpadding=0 cellspacing=5>
        <tr>
          <td>说明文字：</td>
          <td colspan='5'>
           <input name="alttext" type=text id="alttext" style="width:243px" onchange="javascript:Preview()" size=38></td>
        </tr>
        <tr>
          <td nowrap=''>边框粗细：</td>
          <td>
           <input type=text id=border  name="border" size=10 value="" onKeyPress="event.returnValue=IsDigit();" onchange="javascript:Preview()"></td>
          <td width=40></td>
          <td nowrap>边框颜色：</td>
          <td>
            <table border=0 cellpadding=0 cellspacing=0>
              <tr>
                <td>
                 <input type=text id=t_bordercolor name=t_bordercolor size=7 value="" onChange="javascript:Preview()"></td>
                <td><img border=0 src="images/rect.gif" width=18 style="cursor:hand" id=s_bordercolor onClick="SelectColor('bordercolor');Preview();"> </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>特殊效果：</td>
          <td>
           <Select id=styletype style="width:72px" size=1 name="filter" onChange="javascript:Preview()">
             <option value='' selected>无</option>
             <option value='Alpha(Opacity=50)'>半透明</option>
             <option value='Alpha(Opacity=0, FinishOpacity=100, Style=1, StartX=0, StartY=0, FinishX=100, FinishY=140)'>线型透明</option>
             <option value='Alpha(Opacity=10, FinishOpacity=100, Style=2, StartX=30, StartY=30, FinishX=200, FinishY=200)'>放射透明</option>
             <option value='blur(add=1,direction=14,strength=15)'>模糊效果</option>
             <option value='blur(add=true,direction=45,strength=30)'>风动模糊</option>
             <option value='Wave(Add=0, Freq=60, LightStrength=1, Phase=0, Strength=3)'>正弦波纹</option>
             <option value='gray'>黑白照片</option>
             <option value='Chroma(Color=#FFFFFF)'>白色透明</option>
             <option value='DropShadow(Color=#999999, OffX=7, OffY=4, Positive=1)'>投射阴影</option>
             <option value='Shadow(Color=#999999, Direction=45)'>阴影</option>
             <option value='Glow(Color=#ff9900, Strength=5)'>发光</option>
             <option value='flipv'>垂直翻转</option>
             <option value='fliph'>左右翻转</option>
             <option value='grays'>降低彩色</option>
             <option value='xray'>X光照片</option>
             <option value='invert'>底片</option>
           </Select>
          </td>
          <td width=40></td>
          <td>对齐方式：</td>
          <td>
           <Select id=aligntype size=1 style="width:72px">
             <option value='' selected>默认</option>
             <option value='left'>居左</option>
             <option value='right'>居右</option>
             <option value='top'>顶部</option>
             <option value='middle'>中部</option>
             <option value='bottom'>底部</option>
             <option value='absmiddle'>绝对居中</option>
             <option value='absbottom'>绝对底部</option>
             <option value='baseline'>基线</option>
             <option value='texttop'>文本顶部</option>
           </Select>
          </td>
        </tr>
        <tr>
          <td>图片宽度：</td>
          <td>
           <input type="text" id="width" name="width" size="10" onKeyPress="event.returnValue=IsDigit();"  onChange="javascript:Preview()" maxlength="4" /></td>
          <td width=40></td>
          <td>图片高度：</td>
          <td>
           <input type="text" id="height" name="height" size="10" onKeyPress="event.returnValue=IsDigit();" maxlength="4" onChange="javascript:Preview()" /></td>
        </tr>
        <tr>
          <td>上下间距：</td>
          <td>
           <input type=text id=vspace size=10 value="" onKeyPress="event.returnValue=IsDigit();" maxlength=2 ></td>
          <td width=40></td>
          <td>左右间距：</td>
          <td>
           <input type=text id=hspace size=10 value="" onKeyPress="event.returnValue=IsDigit();" maxlength=2></td>
        </tr>
      </table>
    </fieldset></td>
  </tr>
<% if (isUpload) { %>
  <tr>
    <td><fieldset align=left>
    <legend align=left>上传本地图片</legend>
      <iframe class="TBGen" style="top:2px" id="UploadFiles" src="upload.jsp?showType=<%=showType %>&channelId=<%=channelId %>&fileType=1"
       frameborder=0 scrolling=no width="350" height="25"></iframe>
      </fieldset></td>
      </tr>
<% } %>
  <tr>
    <td height=5></td>
  </tr>
</table>

</body>
</html>
<%
/*
frmPreview - IFRAME, 用于预览图片.
url - input text, 图片地址.
upfilename - hidden.
alttext - 说明文字.
border - 边框粗细.
t_bordercolor - 边框颜色，带有一个选择颜色的按钮，其弹出选择颜色对话框.
filter - 特效.
aligntype - 对齐方式.
width - 图片宽度.
height - 图片高度.
vspace - 上下间距.
hspace - 左右间距.
UploadFiles - IFRAME, 上传图片.
upfilename - hidden, 用于保存已经上载的内容.
*/
%>