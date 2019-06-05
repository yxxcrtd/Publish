<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"
%><%@page import="com.chinaedustar.publish.*"
%><%!int channelId;
int moduleType;
int insertTemplate;
int insertTemplateType;
private void backtrackEditor(JspWriter out) throws Exception {
  if(insertTemplate == 1) {
    out.println("if(label!=null){");
    out.println("    parent.insertTemplateLabel(label);");
    out.println("}");
  } else {
    out.println("window.returnValue = label");
    out.println("window.close();");
  }
}%><%ParamUtil paramUtil = new ParamUtil(pageContext);
channelId = paramUtil.safeGetIntParam("channelId", 0);
insertTemplate = paramUtil.safeGetIntParam("insertTemplate", 0);
insertTemplateType = paramUtil.safeGetIntParam("insertTemplateType", 0);%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>网站管理系统--标签导航</title>
<style rel='stylesheet' type='text/css'>
  td {
  FONT-SIZE: 9pt; COLOR: #000000; FONT-FAMILY: 宋体,verdana,Dotum,DotumChe,Arial;line-height: 150%; 
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
      WIDTH: 100%;height: 5px;
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
</head>
<body leftmargin="0"  rightmargin="0"topmargin="0">

<!-- ******** 菜单效果开始 ******** -->
<table width="100%"  border="0" cellspacing="0" cellpadding="4" align="center">
  <tr>
    <td align="center" bgcolor="#0066FF"><b><font color="#ffffff">网站管理系统--标签导航</font></b></td>
  </tr>
</table>
<table width="90%"  border="0" cellspacing="0" cellpadding="2" align="center">
  <tr>
    <td height="50" valign="top" background="images/left_tdbg_01.gif">
    <SCRIPT type=text/javascript>

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

// 插入普通标签.
function InsertLabel(label) {
  <% backtrackEditor(out); %>
}
// 其它标签.
function InsertAdjs(type,fiflepath){
  var str="";
  switch(type){
  case "SwitchFont":
    str="<a name=StranLink href=''>切换到繁體中文</a>"
    break;
  case "Adjs":
    break;
  default:
    alert("错误参数调用！");
    break;
  }
<% if(insertTemplate == 1) { %>
  str=str+"<"+"script language=\"javascript\" src=\""+fiflepath+"\"></"+"script>"
<% } else { %>
  str=str+"<IMG alt='#[!"+"script language=\"javascript\" src=\""+fiflepath+"\"!][!/"+"script!]#'  src=\"editor/images/jscript.gif\" border=0 $>"
<% }
  if(insertTemplate == 1) {
      out.println("parent.insertTemplateLabel(str);");
  } else {
      out.println("window.returnValue =str");
  }
%>
  window.close();
}

//函数标签调用.
function FunctionLabel(url,width,height){
  try { parent.debugFunctionLabel(url, width, height); } catch(e) { }
  if (width < 340) width = 340;
  if (height < 200) height = 200;     
  if (url.indexOf("?") == -1) url += "?";
  url += ("width=" + width + "&height=" + height + "&temp=" + Math.random()); 
  var label = showModalDialog(url, "", "dialogWidth:"+width+"px; dialogHeight:"+height+"px; help: no; scroll:no; status: no"); 
  <%
    backtrackEditor(out);
  %>
}

//函数试标签.
function FunctionLabel2(name){
    var str,label
    switch(name){
    case "ShowTopUser":
        str=prompt("请输入显示注册用户列表的数量.","5"); 
        label="#{"+name+"("+str+")}"
        break;
    case "DownloadUrl":
        str=prompt("一行显示的列数","3");
            if (str!=null) {
        label = "#{"+name + "(" + str + ")}"
        }
        break;
    default:
        alert("错误参数调用！");
        break;
    }
    <%
    backtrackEditor(out);
    %>
}

//动态函数试标签.
function FunctionLabel3(name){
  str=prompt("请输入动态函数标签参数.","5"); 
  label="#{"+name+"("+str+")}"
  <%
  backtrackEditor(out);
  %>
}

//超级函数标签.
function SuperFunctionLabel (url,label,title,ModuleType,ChannelShowType,width,height){
  try { parent.debugFunctionLabel(url, width, height); } catch(e) { }
  var newUrl = url+"?channelId=<%=channelId%>&action="+label+"&title="+title+"&moduleType="+ModuleType+"&channelShowType="+ChannelShowType+"&InsertTemplate=<%=insertTemplate %>&width=" + width + "&height=" + height + "&temp=" + Math.random();
  var label = showModalDialog(newUrl, "", "dialogWidth:"+width+"px; dialogHeight:"+height+"px; help: no; scroll:yes; status: yes"); 
  <%
  backtrackEditor(out);
  %>
}      
</SCRIPT>
    
<!-- 首页 -->
<DIV class=topItem>
  <IMG class='icon' height=16 src="images/home.gif" style="HEIGHT: 16px">标签调用
</DIV>
<!-- 系统介绍 -->
<DIV class=favMenu id=aMenu>
  <!-- 通用通用标签 -->
  <DIV class=topFolder id=web><IMG id=webImg class='icon' src="images/foldericon1.gif">网站通用标签</DIV>
    <DIV class=sub id=webSub>
      <DIV class='subItem' onclick="InsertLabel('#{PageTitle}')">
        <IMG class='icon' src="images/label.gif">显示浏览器的标题栏显示页面的标题信息</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{ShowChannel}')">
        <IMG class='icon' src="images/label.gif">显示顶部频道信息</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{ShowPath}')">
        <IMG class='icon' src="images/label.gif">显示导航信息</DIV>    
      <DIV class='subItem' onclick="InsertLabel('#{ShowVote}')">
        <IMG class='icon' src="images/label.gif">显示网站调查</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{SiteName}')">
        <IMG class='icon' src="images/label.gif">显示网站名称</DIV>    
      <DIV class='subItem' onclick="InsertLabel('#{SiteUrl}')">
        <IMG class='icon' src="images/label.gif">显示网站地址</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{InstallDir}')">
        <IMG class='icon' src="images/label.gif">系统安装目录</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{ShowAdminLogin}')">
        <IMG class='icon' src="images/label.gif">显示管理登录及链接</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{Copyright}')">
        <IMG class='icon' src="images/label.gif">显示版权信息</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{Meta_Keywords}')">
        <IMG class='icon' src="images/label.gif">针对搜索引擎的关键字</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{Meta_Description}')">
        <IMG class='icon' src="images/label.gif">针对搜索引擎的说明</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{ShowSiteCountAll}')">
        <IMG class='icon' src="images/label.gif">显示网站统计</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{WebmasterName}')">
        <IMG class='icon' src="images/label.gif">显示站长姓名</DIV>
      <DIV class='subItem' onclick="FunctionLabel('label/label_ShowLogo.jsp', 540, 300)">
      <IMG class='icon' src="images/label2.gif">显示网站LOGO</DIV>
      <DIV class='subItem' onclick="FunctionLabel('label/label_ShowBanner.jsp', 520, 200)">
      <IMG class='icon' src="images/label2.gif">显示网站Banner</DIV> 
      <DIV class='subItem' onclick="InsertLabel('#{ShowBottom}')">
      <IMG class='icon' src="images/label.gif">显示网站Bottom</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{WebmasterEmail}')">
      <IMG class='icon' src="images/label.gif">显示站长Email链接</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{Skin_CSS}')">
      <IMG class='icon' src="images/label.gif">风格CSS</DIV>
    </DIV>
    <!-- 频道通用标签 -->
    <DIV class=topFolder id=ChannelCommon><IMG id=ChannelCommonImg class='icon' src="images/foldericon1.gif">频道通用标签</DIV>
    <DIV class=sub id=ChannelCommonSub>
        <DIV class='subItem' onclick="InsertLabel('#{ChannelName}')">
          <IMG class='icon' src="images/label.gif">显示频道名称</DIV>    
        <DIV class='subItem' onclick="InsertLabel('#{ChannelID}')">
          <IMG class='icon' src="images/label.gif">得到频道ID</DIV>    
        <DIV class='subItem' onclick="InsertLabel('#{ChannelDir}')">
          <IMG class='icon' src="images/label.gif">得到频道目录</DIV>    
        <DIV class='subItem' onclick="InsertLabel('#{ChannelUrl}')">
          <IMG class='icon' src="images/label.gif">频道目录路径</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{UploadDir}')">
          <IMG class='icon' src="images/label.gif">频道上传目录路径</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ChannelPicUrl}')">
          <IMG class='icon' src="images/label.gif">频道图片</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Meta_Keywords_Channel}')">
          <IMG class='icon' src="images/label.gif">针对搜索引擎的关键字</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Meta_Description_Channel}')">
          <IMG class='icon' src="images/label.gif">针对搜索引擎的说明</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ChannelItemName}')">
          <IMG class='icon' src="images/label.gif">显示频道项目名称</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowColumnMenu}')">
          <IMG class='icon' src="images/label.gif">显示栏目导航的HTML代码</DIV>
        <!-- <DIV class='subItem' onclick="InsertLabel('#{ShowColumnGuide}')"> -->
        <DIV class='subItem'><SPAN onclick="InsertLabel('#{ShowColumnGuide}')">
          <IMG class='icon' src="images/label.gif">子栏目导航</SPAN>|<SPAN 
          onclick="SuperFunctionLabel('label/super_ShowColumnGuide.jsp','ShowColumnGuide','显示子栏目导航','','',800,700)">定制</SPAN>
        </DIV> 
        <DIV class='subItem' onclick="InsertLabel('#{InstallDir}#{ChannelDir}')">
        <IMG class='icon' src="images/label.gif">频道安装目录</DIV>     
    </DIV>
    <!-- 栏目专用页标签 -->
    <DIV class=topFolder id=Channel><IMG id=ChannelImg class='icon' src="images/foldericon1.gif">栏目专用标签</DIV>
    <DIV class=sub id=ChannelSub>
        <DIV class='subItem' onclick="InsertLabel('#{ShowChildColumn}')">
        <IMG class='icon' src="images/label.gif">显示栏目的子一级栏目列表，支持循环自定义</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowAllChildColumn}')">
        <IMG class='icon' src="images/label2.gif">显示栏目的子孙栏目列表</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ParentDir}')">
        <IMG class='icon' src="images/label.gif">得到当前栏目的父目录</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ColumnDir}')">
        <IMG class='icon' src="images/label.gif">得到当前栏目的目录</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Readme}')">
        <IMG class='icon' src="images/label.gif">得到当前栏目的说明</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ColumnUrl}')">
        <IMG class='icon' src="images/label.gif">得到当前栏目的链接地址</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ColumnPicUrl}')">
        <IMG class='icon' src="images/label.gif">栏目图片</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Meta_Keywords_Column}')">
        <IMG class='icon' src="images/label.gif">针对搜索引擎的关键字</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Meta_Description_Column}')">
        <IMG class='icon' src="images/label.gif">针对搜索引擎的说明</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ColumnName}')">
        <IMG class='icon' src="images/label.gif">显示当前栏目的名称</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ColumnID}')">
        <IMG class='icon' src="images/label.gif">得到当前栏目的ID</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowChannelCount}')">
        <IMG class='icon' src="images/label.gif">显示频道统计信息</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{SpecialID}')">
        <IMG class='icon' src="images/label.gif">显示当前专题的标识</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{SpecialName}')">
        <IMG class='icon' src="images/label.gif">显示当前专题的专题名称</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{SpecialPicUrl}')">
        <IMG class='icon' src="images/label.gif">显示专题图片</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowSpecialList}')">
        <IMG class='icon' src="images/label.gif">显示指定频道专题，支持循环自定义</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowChildColumnItems}')">
        <IMG class='icon' src="images/label.gif">显示子栏目及其文章列表</DIV>
    </DIV>
    <!-- 搜索页标签 -->
    <DIV class=topFolder id=Search><IMG id=SearchImg class='icon' src="images/foldericon1.gif">搜索页标签</DIV>
    <DIV class=sub id=SearchSub>
 <!-- 全站搜索 -->
        <DIV class='subItem' onclick="InsertLabel('#{ShowSiteSearch}')">
        <IMG class='icon' src="images/label.gif">显示搜索栏</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowSiteSearchTitle}')">
        <IMG class='icon' src="images/label.gif">显示搜索项</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowSiteSearchResult}')">
        <IMG class='icon' src="images/label.gif">搜索结果</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowSiteSearchKeyword}')">
        <IMG class='icon' src="images/label.gif">搜索关键字</DIV>
 <!-- 频道内搜索 -->
        <DIV class='subItem' onclick="InsertLabel('#{ShowChannelSearch}')">
        <IMG class='icon' src="images/label.gif">显示搜索栏</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowChannelSearchTitle}')">
        <IMG class='icon' src="images/label.gif">显示搜索项</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShwoChannelSearchResult}')">
        <IMG class='icon' src="images/label.gif">搜索结果</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowChannelSearchKeyword}')">
        <IMG class='icon' src="images/label.gif">搜索关键字</DIV>
    </DIV>

<% if(moduleType == 1 || moduleType == 0) { %>
    <!-- 文章频道标签 -->
     <DIV class=topFolder id=Article><IMG id=ArticleImg class='icon' src="images/foldericon1.gif">文章标签</DIV>
     <DIV class=sub id=ArticleSub>
        <!-- 文章通用频道标签 -->
        <DIV class=subFolder id=subArticleChannelFunction><IMG id=subArticleChannelFunctionImg class='icon' src="images/foldericon1.gif"> 文章频道标签</DIV>
        <DIV class=sub id=subArticleChannelFunctionSub>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_ShowArticleList.jsp','ShowArticleList','文章列表函数标签','article','GetList',800,700)">
            <IMG class='icon' src="images/label3.gif">显示文章标题等信息</DIV>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_ShowPicArticle.jsp','ShowPicArticle','显示图片文章标签','article','GetPic',700,500)">
            <IMG class='icon' src="images/label3.gif">显示图片文章</DIV>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_CustomArticleList.jsp','ShowArticleList','文章自定义列表标签','article','GetArticleCustom',800,700)">
            <IMG class='icon' src="images/label3.gif">文章自定义列表标签</DIV>
        </Div>
         <!-- 文章通用频道标签结束 -->
         <!-- 文章频道内容标签 -->
         <DIV class=subFolder id=subArticleChannelContent><IMG id=subArticleChannelContentImg class='icon' src="images/foldericon1.gif"> 文章内容标签</DIV>
         <DIV class=sub id=subArticleChannelContentSub>
            <DIV class='subItem' onclick="InsertLabel('#{ArticleID}')">
            <IMG class='icon' src="images/label.gif">当前文章的ID</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ArticleProperty}')">
            <IMG class='icon' src="images/label.gif">显示当前文章的属性（热门、推荐、等级）</DIV>        
            <DIV class='subItem' onclick="InsertLabel('#{Title}')">
            <IMG class='icon' src="images/label.gif">显示文章正标题</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShortTitle}')">
            <IMG class='icon' src="images/label.gif">显示文章显示页导航处当前文章标题信息</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ArticleInfo}')">
            <IMG class='icon' src="images/label.gif">整体显示文章作者、文章来源、点击数、更新时间信息</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ArticleSubheading}')">
            <IMG class='icon' src="images/label.gif">显示文章副标题</DIV>  
            <!--  <DIV class='subItem' onclick="InsertLabel('#{ReadPoint}')">
            <IMG class='icon' src="images/label.gif">阅读点数</DIV> -->
            <DIV class='subItem' onclick="InsertLabel('#{ArticleAuthor}')">
            <IMG class='icon' src="images/label.gif">作者</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Source}')">
            <IMG class='icon' src="images/label.gif">文章来源</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Inputer}')">
            <IMG class='icon' src="images/label.gif">文章录入者</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Hits}')">
            <IMG class='icon' src="images/label.gif">点击数</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{LastModified}')">
            <IMG class='icon' src="images/label.gif">更新时间信息</DIV>    
            <DIV class='subItem' onclick="InsertLabel('#{Description}')">
            <IMG class='icon' src="images/label.gif">显示文章简介</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ArticleContent}')">
            <IMG class='icon' src="images/label.gif">显示文章的具体的内容</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{PrevArticle}')">
            <IMG class='icon' src="images/label.gif">显示上一篇文章</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{NextArticle}')">
            <IMG class='icon' src="images/label.gif">显示下一篇文章</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Editor}')">
            <IMG class='icon' src="images/label.gif">显示文章责任编辑</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ArticleAction}')">
            <IMG class='icon' src="images/label.gif">显示【发表评论】【告诉好友】【打印此文】【关闭窗口】</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{CorrelativeArticle}')">
            <IMG class='icon' src="images/label2.gif">显示相关文章</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Vote}')">
            <IMG class='icon' src="images/label2.gif">显示调查</DIV>
        </DIV>
        <!-- 文章频道内容标签结束 -->
    </DIV>
    <!-- 文章频道标签结束 -->
    <%
 }
    if (moduleType == 2 || moduleType == 0) { %>
    <!-- 下载频道标签 -->
    <DIV class=topFolder id=Soft><IMG id=SoftImg class='icon' src="images/foldericon1.gif">下载标签</DIV>
    <DIV class=sub id=SoftSub>
         <!-- 下载通用频道标签 -->
         <DIV class=subFolder id=subSoftChannelFunction><IMG id=subSoftChannelFunctionImg class='icon' src="images/foldericon1.gif"> 下载频道标签</DIV>
         <DIV class=sub id=subSoftChannelFunctionSub>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_ShowSoftList.jsp','ShowSoftList','下载列表函数标签','soft','GetList',800,700)">
            <IMG class='icon' src="images/label3.gif">显示下载项目列表</DIV>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_ShowPicSoft.jsp','ShowPicSoft','显示图片下载标签','soft','GetPic',700,500)">
            <IMG class='icon' src="images/label3.gif">显示图片下载项目</DIV>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_CustomSoftList.jsp','#{ShowSoftList}','下载自定义列表标签','soft','GetSoftCustom',720,700)">
            <IMG class='icon' src="images/label3.gif">下载项目自定义列表标签</DIV>
        </DIV>
        <!-- 下载通用频道标签结束 -->
        <!-- 下载频道内容标签 -->
        <DIV class=subFolder id=subSoftChannelContent><IMG id=subSoftChannelContentImg class='icon' src="images/foldericon1.gif"> 下载内容标签</DIV>
        <DIV class=sub id=subSoftChannelContentSub>
            <DIV class='subItem' onclick="InsertLabel('#{SoftId}')">
            <IMG class='icon' src="images/label.gif">软件ID</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Title}')">
            <IMG class='icon' src="images/label.gif">软件名称</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftVersion}')">
            <IMG class='icon' src="images/label.gif">显示软件版本</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftSize} K')">
            <IMG class='icon' src="images/label.gif">软件文件大小</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftSize_M}')">
            <IMG class='icon' src="images/label.gif">显示软件大小（以M 为单位）</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{DecompressPassword}')">
            <IMG class='icon' src="images/label.gif">解压密码</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{OperatingSystem}')">
            <IMG class='icon' src="images/label.gif">运行平台</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Hits}')">
            <IMG class='icon' src="images/label.gif">下载次数总计</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Author}')">
            <IMG class='icon' src="images/label.gif">开 发 商</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{DayHits}')">
            <IMG class='icon' src="images/label.gif">下载次数本日</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{WeekHits}')">
            <IMG class='icon' src="images/label.gif">下载次数本周</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{MonthHits}')">
            <IMG class='icon' src="images/label.gif">下载次数本月</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Stars}')">
            <IMG class='icon' src="images/label.gif">软件等级</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Source}')">
            <IMG class='icon' src="images/label.gif">软件来源</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftLink}')">
            <IMG class='icon' src="images/label.gif">显示软件的演示地址和注册地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftType}')">
            <IMG class='icon' src="images/label.gif">软件类别</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftLanguage}')">
            <IMG class='icon' src="images/label.gif">软件语言</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftProperty}')">
            <IMG class='icon' src="images/label.gif">软件属性</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{LastModified}')">
            <IMG class='icon' src="images/label.gif">更新时间</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Editor}')">
            <IMG class='icon' src="images/label.gif">软件添加审核</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Inputer}')">
            <IMG class='icon' src="images/label.gif">软件添加录入</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftPicUrl}')">
            <IMG class='icon' src="images/label.gif">显示下载图片</DIV>
            <DIV class='subItem' onclick="FunctionLabel('label/label_softPic.jsp', 240, 140)">
            <IMG class='icon' src="images/label2.gif">显示下载图片详细</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{DemoUrl}')">
            <IMG class='icon' src="images/label.gif">显示演示地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{RegUrl}')">
            <IMG class='icon' src="images/label.gif">显示注册地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SoftPoint}')">
            <IMG class='icon' src="images/label.gif">显示收费软件的下载点数</DIV>    
            <DIV class='subItem' onclick="InsertLabel('#{CopyrightType}')">
            <IMG class='icon' src="images/label.gif">授权方式</DIV>    
            <DIV class='subItem' onclick="FunctionLabel2('DownloadUrl')">
            <IMG class='icon' src="images/label2.gif">软件下载地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Description}')">
            <IMG class='icon' src="images/label.gif">软件简介</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{CorrelativeSoft}')">
            <IMG class='icon' src="images/label.gif">相关软件</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Author}')">
            <IMG class='icon' src="images/label.gif">显示软件作者</DIV>
        </DIV>
        <!-- 下载频道内容标签结束 -->
    </DIV>
    <%
    }
    if (moduleType == 3 || moduleType == 0) { %>
    <!-- 图片频道标签 -->
     <DIV class=topFolder id=Photo><IMG id=PhotoImg class='icon' src="images/foldericon1.gif">图片标签</DIV>
     <DIV class=sub id=PhotoSub>
        <!-- 图片通用频道标签 -->
        <DIV class=subFolder id=subPhotoChannelFunction><IMG id=subPhotoChannelFunctionImg class='icon' src="images/foldericon1.gif"> 图片频道标签</DIV>
        <DIV class=sub id=subPhotoChannelFunctionSub>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_ShowPhotoList.jsp','ShowPhotoList','图片列表函数标签','picture','GetList',800,700)">
            <IMG class='icon' src="images/label3.gif">显示图片列表</DIV>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_ShowPicPhoto.jsp','ShowPicPhoto','显示图片图文标签','picture','GetPic',700,550)">
            <IMG class='icon' src="images/label3.gif">显示图片</DIV>
            <DIV class='subItem' onclick="SuperFunctionLabel('label/super_CustomPhotoList.jsp','ShowPhotoList','图片自定义列表标签','picture','GetPhotoCustom',800,700)">
            <IMG class='icon' src="images/label3.gif">图片自定义列表标签</DIV>
        </DIV>
        <!-- 图片频道通用标签结束 -->
        <!-- 图片频道内容标签 -->
        <DIV class=subFolder id=subPhotoChannelContent><IMG id=subPhotoChannelContentImg class='icon' src="images/foldericon1.gif"> 图片内容标签</DIV>
        <DIV class=sub id=subPhotoChannelContentSub>
            <DIV class='subItem' onclick="InsertLabel('#{PhotoId}')">
            <IMG class='icon' src="images/label.gif">图片ID</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Title}')">
            <IMG class='icon' src="images/label.gif">显示图片名称</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Hits}')">
            <IMG class='icon' src="images/label.gif">查看次数总计</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Author}')">
            <IMG class='icon' src="images/label.gif">图片作者</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Source}')">
            <IMG class='icon' src="images/label.gif">图片来源</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Stars}')">
            <IMG class='icon' src="images/label.gif">推荐等级</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{LastModified}')">
            <IMG class='icon' src="images/label.gif">更新时间</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Editor}')">
            <IMG class='icon' src="images/label.gif">显示图片的责任编辑</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Inputer}')">
            <IMG class='icon' src="images/label.gif">显示图片录入者</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{Description}')">
            <IMG class='icon' src="images/label.gif">显示图片简介</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{PrevPhotoUrl}')">
            <IMG class='icon' src="images/label.gif">上一张图片的链接地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{NextPhotoUrl}')">
            <IMG class='icon' src="images/label.gif">下一张图片的链接地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ViewPhoto}')">
            <IMG class='icon' src="images/label.gif">图片查看框</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{PhotoUrlList}')">
            <IMG class='icon' src="images/label.gif">显示图片地址列表</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{PhotoUrl}')">
            <IMG class='icon' src="images/label.gif">图片地址列表中的第一个地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{CorrelativePhoto}')">
            <IMG class='icon' src="images/label2.gif">相关图片列表</DIV>        
            <DIV class='subItem' onclick="InsertLabel('#{PhotoDayHits}')">
            <IMG class='icon' src="images/label.gif">查看次数本日</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{PhotoWeekHits}')">
            <IMG class='icon' src="images/label.gif">查看次数本周</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{PhotoMonthHits}')">
            <IMG class='icon' src="images/label.gif">查看次数本月</DIV>    
            <DIV class='subItem' onclick="InsertLabel('#{PhotoThumb}')">
            <IMG class='icon' src="images/label.gif">显示图片缩略图</DIV>
        </DIV>
        <!-- 图片频道内容标签结束 -->
     </DIV>
    <%
    }
    if (moduleType == 4 || moduleType == 0) { %>
    <!--  留言频道函数  -->
     <DIV class=topFolder id=Guest><IMG id=GuestImg class='icon' src="images/foldericon1.gif">留言函数</DIV>
     <DIV class=sub id=GuestSub>
        <!-- 留言板通用标签 -->
        <DIV class=subFolder id=subGuestCommonFunction><IMG id=subGuestCommonFunctionImg class='icon' src="images/foldericon1.gif">留言板通用标签</DIV>
        <DIV class=sub id=subGuestCommonFunctionSub>
            <DIV class='subItem' onclick="InsertLabel('#{GetGKindList}')">
            <IMG class='icon' src="images/label.gif">显示留言类别横向导航</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GuestBook_top}')">
            <IMG class='icon' src="images/label.gif">显示顶部功能菜单</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GuestBook_Mode}')">
            <IMG class='icon' src="images/label.gif">显示留言模式（游客/ 会员模式）</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GuestBook_See}')">
            <IMG class='icon' src="images/label.gif">显示留言查看模式（留言板/ 讨论区模式）</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GuestBook_Appear}')">
            <IMG class='icon' src="images/label.gif">显示留言发表模式（审核发表/ 直接发表）</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowGueststyle}')">
            <IMG class='icon' src="images/label.gif">切换到另一种查看方式</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GuestBook_Search}')">
            <IMG class='icon' src="images/label.gif">显示留言搜索表单</DIV>
        </DIV>
        <!-- 留言板通用标签 -->
        <DIV class=subFolder id=subGuestIndexFunction><IMG id=subGuestIndexFunctionImg class='icon' src="images/foldericon1.gif">留言板通用标签</DIV>
        <DIV class=sub id=subGuestIndexFunctionSub>
            <DIV class='subItem' onclick="InsertLabel('#{GuestMain}')">
            <IMG class='icon' src="images/label.gif">显示留言列表</DIV>    
        </DIV>
        <!-- 编辑留言页标签 -->
        <DIV class=subFolder id=subGuestEditFunction><IMG id=subGuestEditFunctionImg class='icon' src="images/foldericon1.gif">编辑留言页标签</DIV>
        <DIV class=sub id=subGuestEditFunctionSub>
            <DIV class='subItem' onclick="InsertLabel('#{WriteGuest}')">
            <IMG class='icon' src="images/label.gif">签写留言</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowJS_Guest}')">
            <IMG class='icon' src="images/label.gif">留言Js验证</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{WriteTitle}')">
            <IMG class='icon' src="images/label.gif">显示留言标题</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GetGKind_Option}')">
            <IMG class='icon' src="images/label.gif">显示留言类别</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GuestFace}')">
            <IMG class='icon' src="images/label.gif">显示留言心情</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{GuestContent}')">
            <IMG class='icon' src="images/label.gif">显示留言内容</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{saveedit}')">
            <IMG class='icon' src="images/label.gif">标记是否为编辑留言</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ReplyId}')">
            <IMG class='icon' src="images/label.gif">回复主题id</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{saveeditid}')">
            <IMG class='icon' src="images/label.gif">要编辑留言的ID</DIV>
        </DIV>
        <!-- 留言回复页标签 -->
        <DIV class=subFolder id=subGuestReplyFunction><IMG id=subGuestReplyFunctionImg class='icon' src="images/foldericon1.gif">留言回复页标签</DIV>
        <DIV class=sub id=subGuestReplyFunctionSub>
            <DIV class='subItem' onclick="InsertLabel('#{ReplyGuest}')">
            <IMG class='icon' src="images/label.gif">回复留言主函数</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowJS_Guest}')">
            <IMG class='icon' src="images/label.gif">留言Js验证</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{WriteTitle}')">
            <IMG class='icon' src="images/label.gif">显示留言标题</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ReplyId}')">
            <IMG class='icon' src="images/label.gif">回复主题id</DIV>
        </DIV>
        <!-- 留言搜索页标签 -->
        <DIV class=subFolder id=subGuestSearchFunction><IMG id=subGuestSearchFunctionImg class='icon' src="images/foldericon1.gif">留言搜索页标签</DIV>
        <DIV class=sub id=subGuestSearchFunctionSub>
            <DIV class='subItem' onclick="InsertLabel('#{ResultTitle}')">
            <IMG class='icon' src="images/label.gif">搜索结果标题</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{SearchResult}')">
            <IMG class='icon' src="images/label.gif">搜索结果</DIV>
        </DIV>
     </DIV>
    <%
    }
   %>
     <!--  作者,来源 标签  -->
     <DIV class=topFolder id=Aomb><IMG id=AombImg class='icon' src="images/foldericon1.gif">作者,来源</DIV>
     <DIV class=sub id=AombSub>
         <!-- 作者 标签 -->
         <DIV class=subFolder id=Author><IMG id=AuthorImg class='icon' src="images/foldericon1.gif">作者标签</DIV>
         <DIV class=sub id=AuthorSub>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorName}')">
            <IMG class='icon' src="images/label.gif">作者姓名</DIV>            
            <DIV class='subItem' onclick="InsertLabel('#{AuthorSex}')">
            <IMG class='icon' src="images/label.gif">作者性别</DIV>       
            <DIV class='subItem' onclick="InsertLabel('#{AuthorBirthDay}')">
            <IMG class='icon' src="images/label.gif">作者生日</DIV>    
            <DIV class='subItem' onclick="InsertLabel('#{AuthorCompany}')">
            <IMG class='icon' src="images/label.gif">作者公司</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorDepartment}')">
            <IMG class='icon' src="images/label.gif">作者部门</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorAddress}')">
            <IMG class='icon' src="images/label.gif">作者地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorTel}')">
            <IMG class='icon' src="images/label.gif">作者电话</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorFax}')">
            <IMG class='icon' src="images/label.gif">作者传真</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorZipCode}')">
            <IMG class='icon' src="images/label.gif">作者邮编</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorHomePage}')">
            <IMG class='icon' src="images/label.gif">作者主页</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorEmail}')">
            <IMG class='icon' src="images/label.gif">作者邮件</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorQQ}')">
            <IMG class='icon' src="images/label.gif">作者QQ</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorType}')">
            <IMG class='icon' src="images/label.gif">作者分类</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{AuthorIntro}')">
            <IMG class='icon' src="images/label.gif">作者说明</DIV>
            <DIV class='subItem' onclick="FunctionLabel('label/label_ShowAuthorList.jsp', 420, 380)">
            <IMG class='icon' src="images/label2.gif">显示作者列表</DIV>
         </DIV>
         <!-- 来源 标签 -->
         <DIV class=subFolder id=origin><IMG id=originImg class='icon' src="images/foldericon1.gif">来源标签</DIV>
         <DIV class=sub id=originSub>
            <DIV class='subItem' onclick="InsertLabel('#{ShowPhoto}')">
            <IMG class='icon' src="images/label.gif">来源图片</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowName}')">
            <IMG class='icon' src="images/label.gif">来源姓名</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowContacterName}')">
            <IMG class='icon' src="images/label.gif">联系人</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowAddress}')">
            <IMG class='icon' src="images/label.gif">地址</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowTel}')">
            <IMG class='icon' src="images/label.gif">电话</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowFax}')">
            <IMG class='icon' src="images/label.gif">传真</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowZipCode}')">
            <IMG class='icon' src="images/label.gif">邮编</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowMail}')">
            <IMG class='icon' src="images/label.gif">信箱</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowHomePage}')">
            <IMG class='icon' src="images/label.gif">主页</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowEmail}')">
            <IMG class='icon' src="images/label.gif">邮件</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowQQ}')">
            <IMG class='icon' src="images/label.gif">QQ</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowType}')">
            <IMG class='icon' src="images/label.gif">分类</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowMemo}')">
            <IMG class='icon' src="images/label.gif">简介</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowArticleList}')">
            <IMG class='icon' src="images/label.gif">显示文章列表</DIV>
            <DIV class='subItem' onclick="InsertLabel('#{ShowCopyFromList}')">
            <IMG class='icon' src="images/label.gif">来源列表</DIV>  
         </DIV>
 </DIV>
     <!-- 会员标签 -->
     <!-- <DIV class=topFolder id=associatorItem><IMG id=associatorItemImg class='icon' src="images/foldericon1.gif">会员管理标签</DIV>
     <DIV class=sub id=associatorItemSub>
        <DIV class='subItem' onclick="InsertLabel('#{UserFace}')">
        <IMG class='icon' src="images/label.gif">会员头像</DIV>    
        <DIV class='subItem' onclick="InsertLabel('#{TrueName}')">
        <IMG class='icon' src="images/label.gif">姓名</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Sex}')">
        <IMG class='icon' src="images/label.gif">性别</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{BirthDay}')">
        <IMG class='icon' src="images/label.gif">诞辰</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Company}')">
        <IMG class='icon' src="images/label.gif">公司</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Department}')">
        <IMG class='icon' src="images/label.gif">部门</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Address}')">
        <IMG class='icon' src="images/label.gif">地址</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{HomePhone}')">
        <IMG class='icon' src="images/label.gif">电话</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Fax}')">
        <IMG class='icon' src="images/label.gif">传真</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ZipCode}')">
        <IMG class='icon' src="images/label.gif">邮编</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{HomePage}')">
        <IMG class='icon' src="images/label.gif">主页</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{Email}')">
        <IMG class='icon' src="images/label.gif">邮件</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{QQ}')">
        <IMG class='icon' src="images/label.gif">QQ</DIV>
        <DIV class='subItem' onclick="InsertLabel('#{ShowUserList}')">
        <IMG class='icon' src="images/label.gif">会员列表</DIV>
     </DIV> -->
   <!-- 公告标签 -->
     <DIV class=topFolder id=announceItem><IMG id=announceItemImg class='icon' src="images/foldericon1.gif">公告管理标签</DIV>
   <DIV class=sub id=announceItemSub>
  <DIV class='subItem' onclick="FunctionLabel('label/label_ShowAnnounce2.jsp', 240, 263)">
  <IMG class='icon' src="images/label2.gif">显示详细公告信息</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{ShowAnnounceList}')">
      <IMG class='icon' src="images/label.gif">显示公告信息列表</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceTitle}')">
      <IMG class='icon' src="images/label.gif">公告标题</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceContent}')">
      <IMG class='icon' src="images/label.gif">公告内容</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceAuthor}')">
      <IMG class='icon' src="images/label.gif">公告作者</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceCreateDate}')">
      <IMG class='icon' src="images/label.gif">公告创建日期</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceOffDate}')">
      <IMG class='icon' src="images/label.gif">公告截止日期</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceIsSelected}')">
      <IMG class='icon' src="images/label.gif">公告是否选中</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceChannelId}')">
      <IMG class='icon' src="images/label.gif">公告所属频道标识</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceShowType}')">
      <IMG class='icon' src="images/label.gif">公告的显示方式</DIV>
      <DIV class='subItem' onclick="InsertLabel('#{AnnounceOutTime}')">
      <IMG class='icon' src="images/label.gif">过期时间</DIV>
     </DIV>
     <!-- 友情链接标签 -->
     <DIV class=topFolder id=friendSiteItem><IMG id=friendSiteItemImg class='icon' src="images/foldericon1.gif">友情链接标签</DIV>
   <DIV class=sub id=friendSiteItemSub>
  <DIV class='subItem' onclick="FunctionLabel('label/label_ShowFriendSite2.jsp', 600, 415)">
  <IMG class='icon' src="images/label2.gif">显示详细友情链接信息</DIV>
     </DIV>
  </td>
 </tr>
</table>
<!-- ******** 菜单效果结束 ******** -->
<!-- 显示说明 -->
<table width='100%' height='60' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor="#EEF4FF" style='border: 1px solid #0066FF;'>
  <tr align="center">
    <td height="22" colspan="2" bgcolor='#0066FF'><font color="#FFFFFF">==&gt;&nbsp;显示说明&nbsp;&lt;==</font></td>
  </tr>
  <tr>
    <td width="9%" rowspan="3">&nbsp;</td>
    <td width="91%"><IMG class='icon' src="images/label.gif"> &gt;&gt;&gt;  普通标签 </td>
  </tr>
  <tr>
    <td><IMG class='icon' src="images/label2.gif"> &gt;&gt;&gt; 函数标签 </td>
  </tr>
  <tr>
    <td><IMG class='icon' src="images/label3.gif"> &gt;&gt;&gt; 超级函数标签 </td>
  </tr>
</table>
<!-- 显示结束 -->
</body>
</html>
