<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title></title>
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<table width="100%"  border="0" cellspacing="0" cellpadding="4" align="center">
  <tr>
    <td align="center" bgcolor="#0066FF"><b><font color="#ffffff">网站管理系统--标签导航</font></b></td>
  </tr>
</table>
<table width="90%"  border="0" cellspacing="0" cellpadding="2" align="center">
  <tr>
    <td height="50" valign="top" background="images/left_tdbg_01.gif">
<style rel='stylesheet' type='text/css'>
  td {
  FONT-SIZE: 9pt; COLOR: #000000; FONT-FAMILY: 宋体,Dotum,DotumChe,Arial;line-height: 150%; 
  }
  INPUT {
  BACKGROUND-COLOR: #ffffff; 
  BORDER-BOTTOM: #666666 1px solid;
  BORDER-LEFT: #666666 1px solid;
  BORDER-RIGHT: #666666 1px solid;
  BORDER-TOP: #666666 1px solid;
  COLOR: #666666;
  HEIGHT: 18px;
  border-color: #666666 #666666 #666666 #666666; font-size: 9pt
  }
  .favMenu {
      BACKGROUND: #ffffff; COLOR: windowtext; CURSOR: hand;line-height: 150%; 
  }
  .favMenu DIV {
      WIDTH: 100%;__height: 5px;
  }
  .favMenu A {
      COLOR: windowtext; TEXT-DECORATION: none
  }
  .favMenu A:hover {
      COLOR: windowtext; TEXT-DECORATION: underline
  }
  .topFolder {
      
  }
  .topItem {

  }
  .subFolder {
      PADDING: 0px;BACKGROUND: #ffffff;
  }
  .subItem {
      PADDING: 0px;BACKGROUND: #ffffff;
  }
  .sub {
      BACKGROUND: #ffffff;DISPLAY: none; PADDING: 0px;
  }
  .sub .sub {
      BORDER: 0px;BACKGROUND: #ffffff;
  }
  .icon {
      HEIGHT: 18px; MARGIN-RIGHT: 5px; VERTICAL-ALIGN: absmiddle; WIDTH: 18px
  }
  .outer {
      BACKGROUND: #ffffff;PADDING: 0px;
  }
  .inner {
      BACKGROUND: #ffffff;PADDING: 0px;
  }
  .scrollButton {
      BACKGROUND: #ffffff; BORDER: #f6f6f6 1px solid; PADDING: 0px;
  }
  .flat {
      BACKGROUND: #ffffff; BORDER: #f6f6f6 1px solid; PADDING: 0px;
  }
</style>

<script language="javascript">
 var selectedItem = null;
 var targetWin;

 document.onclick = handleClick;
 document.onmouseover = handleOver;
 document.onmouseout = handleOut;
 document.onmousedown = handleDown;
 document.onmouseup = handleUp;
 document.write(writeSubPadding(10));

 function handleClick() {
     el = getReal(window.event.srcElement, "tagName", "DIV");
     
     if ((el.className == "topFolder") || (el.className == "subFolder")) {
         el.sub = eval(el.id + "Sub");
         if (el.sub.style.display == null) el.sub.style.display = "none";
         if (el.sub.style.display != "block") { 
             if (el.parentElement.openedSub != null) {
                 var opener = eval(el.parentElement.openedSub + ".opener");
                 ChangeFolderImg(opener,1)
                 hide(el.parentElement.openedSub);
                 if (opener.className == "topFolder")
                     outTopItem(opener);
             }
             el.sub.style.display = "block";
             el.sub.parentElement.openedSub = el.sub.id;
             el.sub.opener = el;
             ChangeFolderImg(el,2)
         }
         else {
             hide(el.sub.id);
             ChangeFolderImg(el,1)
         }
     }    
     if ((el.className == "subItem") || (el.className == "subFolder")) {
         if (selectedItem != null)
             restoreSubItem(selectedItem);
         highlightSubItem(el);
     }
     if ((el.className == "topItem") || (el.className == "topFolder")) {
         if (selectedItem != null)
             restoreSubItem(selectedItem);
     }
     if ((el.className == "topItem") || (el.className == "subItem")) {
         if ((el.href != null) && (el.href != "")) {
             if ((el.target == null) || (el.target == "")) {
                 if (window.opener == null) {
                     if (document.all.tags("BASE").item(0) != null)
                         window.open(el.href, document.all.tags("BASE").item(0).target);
                     else 
                         window.location = el.href;                    
                 }
                 else {
                     window.opener.location =  el.href;
                 }
             }
             else {
                 window.open(el.href, el.target);
             }
         }
     }
     var tmp  = getReal(el, "className", "favMenu");
     if (tmp.className == "favMenu") fixScroll(tmp);
 }
 function handleOver() {
     var fromEl = getReal(window.event.fromElement, "tagName", "DIV");
     var toEl = getReal(window.event.toElement, "tagName", "DIV");
     if (fromEl == toEl) return;
     el = toEl;
     if ((el.className == "topFolder") || (el.className == "topItem")) overTopItem(el);
     if ((el.className == "subFolder") || (el.className == "subItem")) overSubItem(el);
     if ((el.className == "topItem") || (el.className == "subItem")) {
         if (el.href != null) {
             if (el.oldtitle == null) el.oldtitle = el.title;
             if (el.oldtitle != "")
                 el.title = el.oldtitle + "\n" + el.href;
             else
                 el.title = el.oldtitle + el.href;
         }
     }
     if (el.className == "scrollButton") overscrollButton(el);
 }
 function handleOut() {
     var fromEl = getReal(window.event.fromElement, "tagName", "DIV");
     var toEl = getReal(window.event.toElement, "tagName", "DIV");
     if (fromEl == toEl) return;
     el = fromEl;
     if ((el.className == "topFolder") || (el.className == "topItem")) outTopItem(el);
     if ((el.className == "subFolder") || (el.className == "subItem")) outSubItem(el);
     if (el.className == "scrollButton") outscrollButton(el);
 }
 function handleDown() {
     el = getReal(window.event.srcElement, "tagName", "DIV");
     if (el.className == "scrollButton") {
         downscrollButton(el);
         var mark = Math.max(el.id.indexOf("Up"), el.id.indexOf("Down"));
         var type = el.id.substr(mark);
         var menuID = el.id.substring(0,mark);
         eval("scroll" + type + "(" + menuID + ")");
     }
 }
 function handleUp() {
     el = getReal(window.event.srcElement, "tagName", "DIV");
     if (el.className == "scrollButton") {
         upscrollButton(el);
         window.clearTimeout(scrolltimer);
     }
 }
 ////////////////////// EVERYTHING IS HANDLED ////////////////////////////
 function hide(elID) {
     var el = eval(elID);
     el.style.display = "none";
     el.parentElement.openedSub = null;
 }
 function writeSubPadding(depth) {
     var str, str2, val;
     var str = "<style type='text/css'>\n";
     for (var i=0; i < depth; i++) {
         str2 = "";
         val  = 0;
         for (var j=0; j < i; j++) {
             str2 += ".sub "
             val += 18;    //子栏目左边距数值
         }
         str += str2 + ".subFolder {padding-left: " + val + "px;}\n";
         str += str2 + ".subItem   {padding-left: " + val + "px;}\n";
     }
     str += "</style>\n";
     return str;
 }
 function overTopItem(el) {
     with (el.style) {
         background   = "#f8f8f8";
         paddingBottom = "0px";
     }
 }
 function outTopItem(el) {
     if ((el.sub != null) && (el.parentElement.openedSub == el.sub.id)) { 
         with(el.style) {
             background = "#ffffff";
         }
     }
     else {
         with (el.style) {
             background = "#ffffff";
             padding = "0px";
         }
     }
 }
 function overSubItem(el) {
     el.style.background = "#F6F6F6";
     el.style.textDecoration = "underline";
 }
 function outSubItem(el) {
             el.style.background = "#ffffff";
     el.style.textDecoration = "none";
 }
 function highlightSubItem(el) {
     el.style.background = "#ffffff";
     el.style.color      = "#ff0000"; 
     selectedItem = el;
 }
 function restoreSubItem(el) {
     el.style.background = "#ffffff";
     el.style.color      = "menutext";
     selectedItem = null;
 }
 function overscrollButton(el) {
     overTopItem(el);
     el.style.padding = "0px";
 }
 function outscrollButton(el) {
     outTopItem(el);
     el.style.padding = "0px";
 }
 function downscrollButton(el) {
     with (el.style) {
         borderRight   = "0px solid buttonhighlight";
         borderLeft  = "0px solid buttonshadow";
         borderBottom    = "0px solid buttonhighlight";
         borderTop = "0px solid buttonshadow";
     }
 }
 function upscrollButton(el) {
     overTopItem(el);
     el.style.padding = "0px";
 }
 function getReal(el, type, value) {
     var temp = el;
     while ((temp != null) && (temp.tagName != "BODY")) {
         if (eval("temp." + type) == value) {
             el = temp;
             return el;
         }
         temp = temp.parentElement;
     }
     return el;
 }
 ////////////////////////////////////////////////////////////////////////////////////////
 // Fix the scrollbars
 var globalScrollContainer;    
 var overflowTimeout = 1;

 function fixScroll(el) {
     globalScrollContainer = el;
     window.setTimeout('changeOverflow(globalScrollContainer)', overflowTimeout);
 }
 function changeOverflow(el) {
     if (el.offsetHeight > el.parentElement.clientHeight)
         window.setTimeout('globalScrollContainer.parentElement.style.overflow = "auto";', overflowTimeout);
     else
         window.setTimeout('globalScrollContainer.parentElement.style.overflow = "hidden";', overflowTimeout);
 }
 function ChangeFolderImg(el,type) {
     var FolderImg = eval(el.id + "Img");
     if (type == 1) {
         FolderImg.src="images/foldericon1.gif"
     }
     else {
         FolderImg.src="images/foldericon2.gif"
     }
 }
 ////////////////////////////////////////////////////////////////////////////////////////
 // 标签调用
 //普通标签
 function insertLabel(label){
 	backtrackEditor(label);
 }
 //其它标签
 function InsertAdjs(type,fiflepath){
   alert('unsupport operation');
 }
 //函数标签调用
 function functionLabel(url, width, height){
     var label = showModalDialog(url, "", "dialogWidth:"+width+"px; dialogHeight:"+height+"px; help: no; scroll:no; status: no"); 
     backtrackEditor(label);
 }
 //函数试标签
 function FunctionLabel2(name){
     var str,label
     switch(name){
     case "ShowTopUser":
         str=prompt("请输入显示注册用户列表的数量.","5"); 
         label="{$"+name+"("+str+")}"
         break;
     case "【ArticleList_ChildClass】":
         str=prompt("循环显示文章栏目录列表：每行显示的列数","2"); 
             if (str!=null) {
         label=name+"【Cols="+str+"】{$rsClass_ClassUrl} 栏目记录集中栏目地址 {$rsClass_Readme} 说明 {$rsClass_ClassName}名称  后面请您加上您自定义的标签【/ArticleList_ChildClass】"
         }
         break;
     case "【SoftList_ChildClass】":
         str=prompt("循环显示下载栏目录列表：每行显示的列数","2"); 
             if (str!=null) {
         label=name+"【Cols="+str+"】{$rsClass_ClassUrl} 栏目记录集中栏目地址 {$rsClass_Readme} 说明 {$rsClass_ClassName}名称  后面请您加上您自定义的标签【/SoftList_ChildClass】"
         }
         break;
     case "【PhotoList_ChildClass】":
         str=prompt("循环显示图片栏目录列表：每行显示的列数","2"); 
             if (str!=null) {
         label=name+"【Cols="+str+"】{$rsClass_ClassUrl} 栏目记录集中栏目地址 {$rsClass_Readme} 说明 {$rsClass_ClassName}名称  后面请您加上您自定义的标签【/PhotoList_ChildClass】"
         }
         break;
     case "【PositionList_Content】":
         str=prompt("循环显示职位内容信息列表：每页显示的职位数","6");
             if (str!=null) {
         label = name + "【PerPageNum=" + str + "】说明：请在此加上人才招聘内容标签（除申请职位按钮标签{$SaveSupply}）【/PositionList_Content】"
         }
         break;
     case "DownloadUrl":
         str=prompt("一行显示的列数","3");
             if (str!=null) {
         label = "{$"+name + "(" + str + ")}"
         }
         break;
     default:
         alert("错误参数调用！");
         break;
     }
     backtrackEditor(label);
 }
 //动态函数试标签
 function FunctionLabel3(name) {
     str = prompt("请输入动态函数标签参数.", "5"); 
     label = "{$" + name + "(" + str + ")}"
     backtrackEditor(label);
 }
 //超级函数标签 
 function superFunctionLabel(url, label,title,ModuleType,ChannelShowType,iwidth,iheight){
     var label = showModalDialog(url + "?channelId=%ChannelID%&action="+label+"&title="+title+"&moduleType="+ModuleType+"&ChannelShowType="+ChannelShowType+"&InsertTemplate=%InsertTemplate%", 
     	 "", "dialogWidth:"+iwidth+"px; dialogHeight:"+iheight+"px; help: no; scroll:yes; status: yes"); 
     backtrackEditor(label);
 }      

 function backtrackEditor(label) {
   if (label != null) {
     try {
       parent.insertTemplateLabel(label, 1);
     } catch (e) {
       alert('backtrackEditor ex: ' + e + ', the label = ' + label);
     }
   }
   // if (InsertTemplate != 1)
   // window.returnValue = label;
   // window.close();
 }
</script>

<!-- 首页 -->
<DIV class=topItem>
  <IMG class=icon height=16 src="images/home.gif" style="HEIGHT: 16px">标签调用
</DIV>
<!-- 系统介绍 -->
<DIV class=favMenu id=aMenu>
<!-- 通用标签 -->
<DIV class=topFolder id=web><IMG id=webImg class=icon src="images/foldericon1.gif">网站通用标签</DIV>
<DIV class=sub id=webSub>
 <!-- 网站通用普通标签 -->
 <DIV class=subFolder id=subwebInsert><IMG id=subwebInsertImg class=icon src="images/foldericon1.gif"> 网站通用标签</DIV>
 <DIV class=sub id=subwebInsertSub>
  <DIV class=subItem onclick="insertLabel('{$PageTitle}')"><IMG class=icon src="images/label.gif">显示浏览器的标题栏显示页面的标题信息</DIV>
  <DIV class=subItem onclick="insertLabel('{$ShowChannel}')"><IMG class=icon src="images/label.gif">显示顶部频道信息</DIV>
  <DIV class=subItem onclick="insertLabel('{$ShowPath}')"><IMG class=icon src="images/label.gif">显示导航信息</DIV>    
  <DIV class=subItem onclick="insertLabel('{$ShowVote}')"><IMG class=icon src="images/label.gif">显示网站调查</DIV>
  <DIV class=subItem onclick="insertLabel('{$SiteName}')"><IMG class=icon src="images/label.gif">显示网站名称</DIV>    
  <DIV class=subItem onclick="insertLabel('{$SiteUrl}')"><IMG class=icon src="images/label.gif">显示网站地址</DIV>
  <DIV class=subItem onclick="insertLabel('{$InstallDir}')"><IMG class=icon src="images/label.gif">系统安装目录</DIV>
  <DIV class=subItem onclick="insertLabel('{$ShowAdminLogin}')"><IMG class=icon src="images/label.gif">显示管理登录及链接</DIV>
  <DIV class=subItem onclick="insertLabel('{$Copyright}')"><IMG class=icon src="images/label.gif">显示版权信息</DIV>
  <DIV class=subItem onclick="insertLabel('{$Meta_Keywords}')"><IMG class=icon src="images/label.gif">针对搜索引擎的关键字</DIV>
  <DIV class=subItem onclick="insertLabel('{$Meta_Description}')"><IMG class=icon src="images/label.gif">针对搜索引擎的说明</DIV>
  <DIV class=subItem onclick="insertLabel('{$ShowSiteCountAll}')"><IMG class=icon src="images/label.gif">显示所有注册会员</DIV>
  <DIV class=subItem onclick="insertLabel('{$WebmasterName}')"><IMG class=icon src="images/label.gif">显示站长姓名</DIV>
  <DIV class=subItem onclick="insertLabel('{$WebmasterEmail}')"><IMG class=icon src="images/label.gif">显示站长Email链接</DIV>
  <DIV class=subItem onclick="insertLabel('{$MenuJS}')"><IMG class=icon src="images/label.gif">下拉栏目JS代码</DIV>
  <DIV class=subItem onclick="insertLabel('{$Skin_CSS}')"><IMG class=icon src="images/label.gif">风格CSS</DIV>
 </DIV>
 <!-- 网站通用函数普通标签结速标签 -->
 <DIV class=subFolder id=subwebFunction><IMG id=subwebFunctionImg class=icon src="images/foldericon1.gif"> 网站通用函数标签</DIV>
 <DIV class=sub id=subwebFunctionSub>
  <DIV class=subItem onclick="functionLabel('label/logo.htm','240','140')"><IMG class=icon src="images/label2.gif">显示网站LOGO</DIV>
  <DIV class=subItem onclick="functionLabel('label/banner.htm','240','140')"><IMG class=icon src="images/label2.gif">显示网站Banner</DIV>            
  <DIV class=subItem onclick="functionLabel2('ShowTopUser')"><IMG class=icon src="images/label2.gif">显示注册用户列表</DIV>
  <DIV class=subItem onclick="functionLabel('Lable/PE_Annouce.htm','240','140')"><IMG class=icon src="images/label2.gif">显示本站公告信息</DIV>
  <DIV class=subItem onclick="functionLabel('Lable/PE_Annouce2.htm','240','210')"><IMG class=icon src="images/label2.gif">显示详细公告信息</DIV>
  <DIV class=subItem onclick="functionLabel('Lable/PE_FSite.htm','330','260')"><IMG class=icon src="images/label2.gif">显示友情链接信息</DIV>
  <DIV class=subItem onclick="functionLabel('Lable/PE_FSite2.htm','330','510')"><IMG class=icon src="images/label2.gif">显示详细友情链接信息</DIV>
  <DIV class=subItem onclick="functionLabel('Lable/PE_ProducerList.htm','400','450')"><IMG class=icon src="images/label2.gif">显示厂商列表</DIV> 
  <DIV class=subItem onclick="functionLabel('Lable/PE_Author_ShowList.htm','400','340')"><IMG class=icon src="images/label2.gif">显示作者列表</DIV>
  <DIV class=subItem onclick="functionLabel('Lable/PE_ShowSpecialList.htm','320','300')"><IMG class=icon src="images/label2.gif">显示指定频道专题</DIV>
  <DIV class=subItem onclick="functionLabel('Lable/PE_ShowBlogList.htm','400','400')"><IMG class=icon src="images/label2.gif">显示作品集排行</DIV>
 </DIV>
</DIV>
 <!-- 频道通用标签 -->
 <DIV class=topFolder id=ChannelCommon><IMG id=ChannelCommonImg class=icon src="images/foldericon1.gif">频道通用标签</DIV>
 <DIV class=sub id=ChannelCommonSub>
     <DIV class=subItem onclick="insertLabel('{$ChannelName}')"><IMG class=icon src="images/label.gif">显示频道名称</DIV>    
     <DIV class=subItem onclick="insertLabel('{$ChannelID}')"><IMG class=icon src="images/label.gif">得到频道ID</DIV>    
     <DIV class=subItem onclick="insertLabel('{$ChannelDir}')"><IMG class=icon src="images/label.gif">得到频道目录</DIV>    
     <DIV class=subItem onclick="insertLabel('{$ChannelUrl}')"><IMG class=icon src="images/label.gif">频道目录路径</DIV>
     <DIV class=subItem onclick="insertLabel('{$UploadDir}')"><IMG class=icon src="images/label.gif">频道上传目录路径</DIV>
     <DIV class=subItem onclick="insertLabel('{$ChannelPicUrl}')"><IMG class=icon src="images/label.gif">频道图片</DIV>
     <DIV class=subItem onclick="insertLabel('{$Meta_Keywords_Channel}')"><IMG class=icon src="images/label.gif">针对搜索引擎的关键字</DIV>
     <DIV class=subItem onclick="insertLabel('{$Meta_Description_Channel}')"><IMG class=icon src="images/label.gif">针对搜索引擎的说明</DIV>
     <DIV class=subItem onclick="insertLabel('{$ChannelShortName}')"><IMG class=icon src="images/label.gif">显示频道名</DIV>    
     <DIV class=subItem onclick="functionLabel('Lable/PE_ClassNavigation.htm','260','200')"><IMG class=icon src="images/label2.gif">显示栏目导航的HTML代码</DIV>
 </DIV>
</DIV>
   </td>
  </tr>
</table>

</body>
</html>