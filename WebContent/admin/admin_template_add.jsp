<%@ page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="utf-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.TemplateManage"
%><%
  // 获得网页管理所需数据。
  TemplateManage admin_data = new TemplateManage(pageContext);
  admin_data.initEditPage();
  
  String siteUrl = admin_data.getSite().getUrl();   // 'http://xxx/PubWeb/'
  String installDir = admin_data.getSite().getInstallDir();   // '/PubWeb/'
  int channelId = admin_data.getParamUtil().safeGetIntParam("channelId", 0);
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>添加/修改模板</title>
 <link href="admin_style.css" rel="stylesheet" type="text/css" />  
</head>

<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<%@include file="element_template.jsp" %>

<pub:template name="main">
 #{call admin_template_help}
 #{call edit_form }
</pub:template>



<pub:template name="debug_info">
<br/><br/><br/><br/><hr><h2>DEBUG INFO</h2>
<li>template = #{(template) }, id = #{template.id }, name = #{template.name }
<li>template.content length = #{template.content@length }
<li>&nbsp;&nbsp;.themeId=#{template.themeId}, .channelId=#{template.channelId },
   .typeId = #{template.typeId }, 
<li>theme = #{(theme) }
<li>channel = #{(channel) }
<li>request = #{(request) }
<br/><br/><br/><br/>
</pub:template>


<!-- 模板编辑的表单 -->
<pub:template name="edit_form">
<form name='form1' method='post' action='admin_template_action.jsp' >
<table width='100%' border='0' cellspacing='1' cellpadding='2' class='border'>
 <tr class='title'>
  <td height='22' align='center'><strong>#{iif(template.id != 0, " 添 加 ", " 修 改 ") } 模 板 设 置</strong></td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;&nbsp;<strong> 选择方案： </strong>
   #{call template_theme_select}   
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;&nbsp;<strong> 模板类型： </strong>
   #{call template_type_select}
  </td>
 </tr>
 <tr class='tdbg'>
  <td>&nbsp;&nbsp;<strong> 模板名称： </strong>
   <input name='templateName' type='text' id='TemplateName' value='#{template.name@html}' size='50' maxlength='50' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td align=center><b> 模 板 内 容 ↓</b>    </td>
 </tr>
 <tr class='tdbg'>
  <td valign='top' align=center id='Navigation1' style='display:'>
  <a name='#TemplateStart1'></a>
   #{call toggle_label}
   #{call function_label_toolbar}
  </td>
 </tr>
 <tr class='tdbg' id='showAlgebra'>
  <td>
   <table>
    <tr>
     <td width='20'>#{call show_label_iframe}</td>
     <td>#{call templ_content_tabel}</td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td>#{call edit_button}</td>
 </tr>
 <tr class='tdbg'>
  <td>
   <table align='left' width='200'>
    <tr id='OpenNavigation3'>
     <td>&nbsp;&nbsp;&nbsp;<IMG SRC='images/admin_open.gif' BORDER='0' ALT='' /><a href='#TemplateEnd1' onclick="OpenNavigation()">&nbsp;使用更多的标签&nbsp;</a>
     </td>
    </tr>
    <tr id='CloseNavigation3' style='display:none'>
     <td >&nbsp;&nbsp;&nbsp;<IMG SRC='images/admin_close.gif' BORDER='0' ALT='' /><a href='#TemplateEnd1' onclick="CloseNavigation()">&nbsp;关闭标签导航栏</a>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg' id=showeditor style='display:none'>
  <td valign='top'>
   <table>
    <tr>
     <td width='20'><td>
      <textarea name='EditorContent' style='display:none' ></textarea>
      <iframe id='editor' src='../editor/editor_new.jsp?channelId=#{template.channelId}&showType=1&tContentID=EditorContent&templateType=1' 
       frameborder='1' scrolling='no' width='790' height='400' ></iframe>
     </td>
    </tr>
   </table>
   <a name='#TemplateEnd1'></a>
  </td>
 </tr>
 <tr class='tdbg'>
  <td valign='top'>
   &nbsp;&nbsp;<input name='isDefault' type='checkbox' id='IsDefault' value='true' #{iif(template.isDefault,"checked","") }/> 将此模板设为默认模板
   <a name='#TemplateEnd2'></a>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='50' align='center'>
   <input name='command' type='hidden' value='save' />
   <input name='channelId' type='hidden' id='ChannelID' value='#{template.channelId}' />
   <input name='templateId' type='hidden' id='TemplateID' value='#{template.id}' />
   <input type='button' name='Submit2' value='#{iif(template.id == 0," 添加 "," 保存修改结果 ") }' onclick='return CheckForm();' />
  </td>    
 </tr>  
</table>
</form>
</pub:template>

<!-- 编辑按钮表格 -->
<pub:template name="edit_button">
 <table>
  <tr>       
   <td width='95%'>&nbsp;&nbsp;&nbsp;&nbsp;         
    <input name='EditorAlgebra' type='button' id='EditorAlgebra' value=' 代码模式 ' 
     onclick='LoadEditorAlgebra();' onmouseover="this.style.backgroundColor='#BFDFFF'" 
     onmouseout="this.style.backgroundColor=''" />         &nbsp;
    <input name='EditorMix' type='button' id='EditorMix' value=' 混合模式 '  
     onclick='LoadEditorMix();' onmouseover="this.style.backgroundColor='#BFDFFF'" 
     onmouseout="this.style.backgroundColor=''" />         &nbsp;
    <input name='EditorEdit' type='button' id='EditorEdit' value=' 编辑模式 '  
     onclick='LoadEditorEdit();' onmouseover="this.style.backgroundColor='#BFDFFF'" 
     onmouseout="this.style.backgroundColor=''" />         &nbsp;
    <input name='Copy' type='button' id='Copy' value=' 复制代码 ' __onclick='copy();' 
     onmouseover="this.style.backgroundColor='#BFDFFF'" onmouseout="this.style.backgroundColor=''" />              &nbsp;
    <input name='Editorfullscreen' type='button' disabled='true' id='Editorfullscreen' value=' 全屏编辑 ' 
     onclick='fullscreen();' onmouseover="this.style.backgroundColor='#BFDFFF'" 
     onmouseout="this.style.backgroundColor=''" />         &nbsp;
    <input name='EditorSkin' type='button' id='EditorSkin' value=' 修改风格 ' 
     onclick="return Templateskin()"  onmouseover="this.style.backgroundColor='#BFDFFF'" 
     onmouseout="this.style.backgroundColor=''" />
   </td>
   <td align='right' width='5%'>
    <img src='../editor/images/sizeplus.gif' 
     onclick="sizeContent(5,'Content');sizeContent(5,'rollContent')" />&nbsp;
    <img src='../editor/images/sizeminus.gif' 
     onclick="sizeContent(-5,'Content');sizeContent(-5,'rollContent')">&nbsp;&nbsp;
   </td>
  </tr>
 </table>
</pub:template>


<!-- 代码模式下的内容编辑。 -->
<pub:template name="templ_content_tabel">
 <table width='100%'>
  <tr><td width='20' alignt='top'>
 <textarea id='txt_ln' name='rollContent' cols='5' rows='31' class='romNumber' 
   style="font-family:宋体,verdana" readonly='' type="_moz">1</textarea>
  </td>
  <td width='700' alignt='top'>
    <textarea name='Content' id='txt_main' rows='30' cols='117' wrap='off' 
   onkeydown='editTab()' onscroll="show_ln('txt_ln','txt_main')" 
   wrap='on' onmouseup="setContent('get',1);setContent2()" 
   style="font-family:宋体,verdana"
   class='txt_main' type="_moz">#{template.content@html }</textarea>
  </td>
  </tr>
 </table>         
 <script>
 function setRollContent() {
   var row_text = '1\n';
   for(var i=2; i<3000; i++)
     row_text += (i + '\n');
   document.getElementById('txt_ln').value = row_text;
 }
 setRollContent();
 </script>
</pub:template>


<!-- ShowLabel -->
<pub:template name="show_label_iframe">
 <table id=showLabel style='display:none'>
  <tr>
   <td>
    <iframe marginwidth=0 marginheight=0 frameborder=0 width='180' height='440' 
     src='../editor/editor_tree.jsp?channelId=#{channel.id}&moduleType=1&insertTemplate=1'></iframe>
   </td>
  </tr>
 </table>
</pub:template>

<%-- 模板方案选择框 --%>
<pub:template name="template_theme_select">
 <select name='themeId' id='themeId' #{iif(template.id!=0, "disabled ","") } 
   onchange="window.location.href='admin_template_add.jsp?action=add&themeId=' + this.value + '&channelId=#{channel.id}&templateType=' + document.form1.templateType.value">
   #{foreach theme0 in theme_list }
   <option value='#{theme0.id }' #{iif(template.themeId == theme0.id, "selected", "") }>#{theme0.name}</option>
   #{/foreach }
 </select>
</pub:template>

<%-- TemplateType 选择框 --%>
<pub:template name="template_type_select">
 <select name='typeId' id='typeId' #{iif(template.id != 0, "disabled","") }>
 #{foreach type in template_type_list }
 <option value='#{type.id}' #{iif(template.typeId == type.id, "selected", "") }>#{type.title@replace("#itemName#", channel.itemName)}</option>
 #{/foreach }
 </select>
</pub:template>

<%-- 切换使用标签按钮 --%>
<pub:template name="toggle_label">
 <table align='left' width='200'>          
  <tr id='OpenNavigation1' >
   <td >&nbsp;&nbsp;&nbsp;
    <img src='images/admin_open.gif' border='0' alt='' />
    <a href='#TemplateStart1' onclick="OpenNavigation()">&nbsp;使用更多的标签&nbsp;</a>
   </td>
  </tr>          
  <tr id='CloseNavigation1' style='display:none'>
   <td >&nbsp;&nbsp;&nbsp;
    <img src='images/admin_close.gif' border='0' alt='' />
    <a href='#TemplateStart1' onclick="CloseNavigation()">&nbsp;关闭标签导航栏</a>
   </td>
  </tr>        
 </table> 
</pub:template>


<%-- 常用超级函数标签 --%>
<pub:template name="function_label_toolbar">
 <table align='left' border='0' id='CommonLabel1' cellpadding='0' cellspacing='1' width='550' height='100%' >
  <tr class='tdbg'>
   <td width='120'> 常用超级函数标签:</td>
   <td width='1' bgcolor='#ACA899'></td>
   <td width='2'></td>
   <td>
    <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowArticleList.jsp','ShowArticleList','文章列表函数标签','article','GetList',800,700,1)" >
    <img src='../editor/images/labelIco/getArticleList.gif' border='0' width='18' height='18' alt='显示文章标题等信息'></a>
    <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowPicArticle.jsp','ShowPicArticleList','显示图片文章标签','article','GetPic',700,500,1)" >
    <img src='../editor/images/labelIco/getPicArticle.gif' border='0' width='18' height='18' alt='显示图片文章'></a>
    <!-- 
    <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowSlidePicArticle.jsp','ShowSlidePicArticle','显示幻灯片文章标签','article','GetSlide',700,500,1)" >
    <img src='../editor/images/labelIco/getSlidePicArticle.gif' border='0' width='18' height='18' alt='显示幻灯片文章'></a>
     -->
    <a href="javascript:SuperFunctionLabel('../editor/label/super_CustomArticleList.jsp','CustomListLable','文章自定义列表标签','article','GetArticleCustom',720,700,1)" >
    <img src='../editor/images/labelIco/getArticleCustom.gif' border='0' width='18' height='18' alt='文章自定义列表'></a>
    </td>
 <td width='1' bgcolor='#ACA899'></td><td width='2'></td>
 <td>
 <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowSoftList.jsp','ShowSoftList','下载列表函数标签','soft','GetList',800,700,1)" >
 <img src='../editor/images/labelIco/getSoftList.gif' border='0' width='18' height='18' alt='显示软件标题'></a>
 <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowPicSoft.jsp','ShowPicSoftList','显示图片下载标签','soft','GetPic',700,500,1)" >
 <img src='../editor/images/labelIco/getPicSoft.gif' border='0' width='18' height='18' alt='显示图片下载'></a>
 <!-- 
 <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowSlidePicSoft.jsp','ShowSlidePicSoft','显示幻灯片下载标签','soft','GetSlide',700,500,1)" >
 <img src='../editor/images/labelIco/getSlidePicSoft.gif' border='0' width='18' height='18' alt='显示幻灯片下载'></a>
  -->
 <a href="javascript:SuperFunctionLabel('../editor/label/super_CustomSoftList.jsp','CustomListLable','下载自定义列表标签','soft','GetSoftCustom',720,700,1)" >
 <img src='../editor/images/labelIco/getSoftCustom.gif' border='0' width='18' height='18' alt='下载自定义列表'></a>
 </td>
 <td width='1' bgcolor='#ACA899'></td><td width='2'></td>
 <td>
 <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowPhotoList.jsp','ShowPhotoList','图片列表函数标签','picture','GetList',800,700,1)" >
 <img src='../editor/images/labelIco/getPhotoList.gif' border='0' width='18' height='18' alt='显示图片标题'></a>
 <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowPicPhoto.jsp','ShowPicPhotoList','显示图片图文标签','picture','GetPic',700,550,1)" >
 <img src='../editor/images/labelIco/getPicPhoto.gif' border='0' width='18' height='18' alt='显示图片'></a>
 <!-- 
 <a href="javascript:SuperFunctionLabel('../editor/label/super_ShowSlidePicPhoto.jsp','ShowSlidePicPhoto','显示幻灯片图片标签','picture','GetSlide',700,550,1)" >
 <img src='../editor/images/labelIco/getSlidePicPhoto.gif' border='0' width='18' height='18' alt='显示图片幻灯片'></a>
  -->
 <a href="javascript:SuperFunctionLabel('../editor/label/super_CustomPhotoList.jsp','CustomListLable','图片自定义列表标签','picture','GetPhotoCustom',720,700,1)" >
 <img src='../editor/images/labelIco/getPhotoList.gif' border='0' width='18' height='18' alt='图片自定义列表'></a>
 </td>
   <td width='1' bgcolor='#ACA899'></td>
  </tr>
 </table>
</pub:template>


<pub:process_template name="main" />


<script language='JavaScript'>
<!--
var addeditorcss = false;
var addeditorcss2 = false;
var objContentSelection;  // Content 选中的范围.
function ResumeError() {
  return true;
}
// TODO: window.onerror = ResumeError;

// 扩大或缩小指定的 textarea 的显示行数.
function sizeContent(num, objname) {
  var obj = document.getElementById(objname);
  if (parseInt(obj.rows) + num >= 1) {
    obj.rows = parseInt(obj.rows) + num;
  }
  if (num > 0) {
    obj.width = "90%";
  }
}

// 复制代码.
function copy() {
  var content= document.form1.Content.value;
  document.form1.Content.value=content;
  document.form1.Content.focus();
  document.form1.Content.select();
  textRange = document.form1.Content.createTextRange();
  textRange.execCommand("Copy");
}

// 切换到代码模式.
function LoadEditorAlgebra(){
  document.form1.Content.rows=30;
  document.form1.rollContent.rows=31;
  showAlgebra.style.display="";
  showeditor.style.display="none";
  showLabel.style.display="none";
  Navigation1.style.display="";
  CommonLabel1.style.display="";
  OpenNavigation1.style.display="";
  OpenNavigation3.style.display="";
  document.form1.Editorfullscreen.disabled=true;
  document.form1.Copy.disabled=false;
  if (addeditorcss==false){
    addeditorcss=true;
    setContent('get',1)
    editor.yToolbarsCss();
    editor.showBorders();
  }else{
    setContent('get',1)
    editor.yToolbarsCss()
  }
}

// 切换到编辑模式.
function LoadEditorEdit(){
  showAlgebra.style.display="none";
  showeditor.style.display="";
  showLabel.style.display="none";
  Navigation1.style.display="none";
  CommonLabel1.style.display="none";
  OpenNavigation1.style.display="none";
  OpenNavigation3.style.display="none";
  CloseNavigation1.style.display="none";
  CloseNavigation3.style.display="none";
  document.form1.Editorfullscreen.disabled=false;
  document.form1.Copy.disabled=true;
  if (addeditorcss==false){
    addeditorcss=true;
    setContent('set',1);
    editor.yToolbarsCss();
    editor.showBorders();
  }else{
    setContent('set',1)
    editor.yToolbarsCss()
  }
}

// 切换到混合模式.
function LoadEditorMix(){
  document.form1.Content.rows=10;
  document.form1.rollContent.rows=11;
  showeditor.style.display="";
  showAlgebra.style.display="";
  showLabel.style.display="none";
  Navigation1.style.display="none";
  CommonLabel1.style.display="";
  OpenNavigation1.style.display="none";
  OpenNavigation3.style.display="none";
  document.form1.Editorfullscreen.disabled=false;
  document.form1.Copy.disabled=false;
  if (addeditorcss==false){
      addeditorcss=true;
      editor.yToolbarsCss()
      editor.showBorders()
  }else{
      editor.yToolbarsCss()
  }
}

// 使用更多的标签.
function OpenNavigation() {
  showLabel.style.display="";
  CloseNavigation1.style.display="";
  OpenNavigation1.style.display="none";
  CloseNavigation3.style.display="";
  OpenNavigation3.style.display="none";
  parent.parent.frame.cols='0,*';
}

// 关闭标签导航栏.
function CloseNavigation() {
  showLabel.style.display="none";
  OpenNavigation1.style.display="";
  CloseNavigation1.style.display="none";
  OpenNavigation3.style.display="";
  CloseNavigation3.style.display="none";
  parent.parent.frame.cols='200,*';
}

// Content.onmouseup 事件时调用.
function setContent2() {
  form1.Content.focus();
  objContentSelection = document.selection.createRange();
}

// 从 editor_tree.jsp 页面中调用.
function insertTemplateLabel(strLabel) {
  if (objContentSelection == null) {
    form1.Content.focus();
    objContentSelection = document.selection.createRange();
  }
  objContentSelection.text = strLabel;
}

// 插入超级标签.
function SuperFunctionLabel(url,label,title,ModuleType,ChannelShowType,iwidth,iheight,TemplateType){
  form1.Content.focus();
  var str = document.selection.createRange();
  var arr = showModalDialog(url+"?channelId=<%=channelId%>&Action="+label+"&Title="+title+"&ModuleType="+ModuleType+"&channelShowType="+ChannelShowType+"&InsertTemplate=1&width=" + iwidth + "&height=" + iheight, 
    "", "dialogWidth:"+iwidth+"px; dialogHeight:"+iheight+"px; help: no; scroll:yes; status: yes"); 
  if (arr != null){
      str.text = arr;
  }
}

// 转入全屏编辑.
function fullscreen() {
  window.open ("../editor/editor_fullscreen.jsp?channelId=<%=channelId%>", "", 
    "toolbar=no, menubar=no, top=0,left=0,width=1024,height=768, scrollbars=no, resizable=no,location=no, status=no");
}

// 修改风格.
function Templateskin(){
  if(confirm('您确定要转入风格设计，如果您没有保存当前操作的模板请保存模板。')){
      window.location.href = 'admin_skin.jsp?skinId=1&isDefault=-1';
  }  
}

// 同步两个 textarea 的滚动行数相同.
function show_ln(txt_ln,txt_main){
  var txt_ln  = document.getElementById(txt_ln);
  var txt_main  = document.getElementById(txt_main);
  txt_ln.scrollTop = txt_main.scrollTop;
  while(txt_ln.scrollTop != txt_main.scrollTop) {
      txt_ln.value += (i++) + '\n';
      txt_ln.scrollTop = txt_main.scrollTop;
  }
  return;
}

// Content.onkeydown 的处理.
function editTab(){
  var code, sel, tmp, r
  var tabs=''
  event.returnValue = false
  sel = event.srcElement.document.selection.createRange()
  r = event.srcElement.createTextRange()
  switch (event.keyCode){
    case (8) :
      if (!(sel.getClientRects().length > 1)){
        event.returnValue = true
        return
      }
      code = sel.text
      tmp = sel.duplicate()
      tmp.moveToPoint(r.getBoundingClientRect().left, sel.getClientRects()[0].top)
      sel.setEndPoint('startToStart', tmp)
      sel.text = sel.text.replace(/\t/gm, '')
      code = code.replace(/\t/gm, '').replace(/\r\n/g, '\r')
      r.findText(code)
      r.select()
      break
    case (9) :
      if (sel.getClientRects().length > 1){
        code = sel.text
        tmp = sel.duplicate()
        tmp.moveToPoint(r.getBoundingClientRect().left, sel.getClientRects()[0].top)
        sel.setEndPoint('startToStart', tmp)
        sel.text = '\t'+sel.text.replace(/\r\n/g, '\r\t')
        code = code.replace(/\r\n/g, '\r\t')
        r.findText(code)
        r.select()
      }else{
        sel.text = '\t'
        sel.select()
      }
      break
    case (13) :
      tmp = sel.duplicate()
      for (var i=0; tmp.text.match(/[\t]+/g) && i<tmp.text.match(/[\t]+/g)[0].length; i++) tabs += '\t'
      sel.text = '\r\n'+tabs
      sel.select()
      break
    default  :
      event.returnValue = true
      break
  }
}
//-->
</script>

<script language="VBScript">
Dim Strsave, Strsave2, addeditorcss3
Dim regEx, Match, Matches, StrBody,strTemp,strTemp2,strMatch,arrMatch,i
Dim Content1,Content2,Content3,Content4,TemplateContent,TemplateContent2,TemplateContent3,arrContent,EditorContent
Set regEx = New RegExp
regEx.IgnoreCase = True
regEx.Global = True
Strsave="A"
Strsave2="A"

' 检查表单是否可以提交.
Sub CheckForm()
  if document.form1.TemplateName.value="" then
    alert "模板名称不能为空！"
    document.form1.TemplateName.focus()
    exit sub
  End if
  if document.form1.Content.value="" then
    alert "模板主内容不能为空！"
    editor.HtmlEdit.focus()
    exit sub
  End if
  if Strsave="B" then setContent "get",1
  document.form1.EditorContent.value=""
  form1.rollContent.value = ""
  form1.submit
End Sub

' ?? 把IE HTML Editor 自动产生的 html 脚本进行一些格式化处理, 也许需要测试一下这些处理的效果如何.
Function ResumeBlank(ByVal Content)
  Dim strHtml,strHtml2,Num,Numtemp,Strtemp
  strHtml=Replace(Content, "<DIV", "<div")
  strHtml=Replace(strHtml, "</DIV>", vbCrLf & "</div>"& vbCrLf)
  strHtml=Replace(strHtml, "<DD>", vbCrLf & "<dd>")
  strHtml=Replace(strHtml, "<DT>", vbCrLf & "<dt>")
  strHtml=Replace(strHtml, "<DL>", vbCrLf & "<dl>")
  strHtml=Replace(strHtml, "</DD>", vbCrLf & "</dd>"& vbCrLf)
  strHtml=Replace(strHtml, "</DT>", vbCrLf & "</dt>"& vbCrLf)
  strHtml=Replace(strHtml, "</DL>", vbCrLf & "</dl>"& vbCrLf)
  strHtml=Replace(strHtml, "<TABLE", "<table")
  strHtml=Replace(strHtml, "</TABLE>", vbCrLf & "</table>"& vbCrLf)
  strHtml=Replace(strHtml, "<TBODY>", "")
  strHtml=Replace(strHtml, "</TBODY>","" & vbCrLf)
  strHtml=Replace(strHtml, "<TR", "<tr")
  strHtml=Replace(strHtml, "</TR>", vbCrLf & "</tr>"& vbCrLf)
  strHtml=Replace(strHtml, "<TD", "<td")
  strHtml=Replace(strHtml, "</TD>", "</td>")
  strHtml=Replace(strHtml, "<!--", vbCrLf & "<!--")
  strHtml=Replace(strHtml, "<SELECT",vbCrLf & "<Select")
  strHtml=Replace(strHtml, "</SELECT>",vbCrLf & "</Select>")
  strHtml=Replace(strHtml, "<OPTION",vbCrLf & "  <Option")
  strHtml=Replace(strHtml, "</OPTION>","</Option>")
  strHtml=Replace(strHtml, "<INPUT",vbCrLf & "  <Input")
  strHtml=Replace(strHtml, "<script",vbCrLf & "<script")
  strHtml=Replace(strHtml, "&amp;","&")
  arrContent = Split(strHtml,vbCrLf)
  
  For i = 0 To UBound(arrContent)
    Numtemp=false
    if Instr(arrContent(i),"<table")>0 then
      Numtemp=True
      if Strtemp<>"<table" and Strtemp <>"</table>" then
        Num=Num+2
      End if 
      Strtemp="<table"
    elseif Instr(arrContent(i),"<tr")>0 then
      Numtemp=True
      if Strtemp<>"<tr" and Strtemp<>"</tr>" then
        Num=Num+2
      End if 
      Strtemp="<tr"
    elseif Instr(arrContent(i),"<td")>0 then
      Numtemp=True
      if Strtemp<>"<td" and Strtemp<>"</td>" then
        Num=Num+2
      End if 
      Strtemp="<td"
    elseif Instr(arrContent(i),"</table>")>0 then
      Numtemp=True
      if Strtemp<>"</table>" and Strtemp<>"<table" then
        Num=Num-2
      End if 
      Strtemp="</table>"
    elseif Instr(arrContent(i),"</tr>")>0 then
      Numtemp=True
      if Strtemp<>"</tr>" and Strtemp<>"<tr" then
        Num=Num-2
      End if 
      Strtemp="</tr>"
    elseif Instr(arrContent(i),"</td>")>0 then
      Numtemp=True
      if Strtemp<>"</td>" and Strtemp<>"<td" then
        Num=Num-2
      End if 
      Strtemp="</td>"
    elseif Instr(arrContent(i),"<!--")>0 then
      Numtemp=True
    End if
    if Num< 0 then Num = 0
    if trim(arrContent(i))<>"" then
      if i=0 then
        strHtml2= string(Num," ") & arrContent(i) 
      elseif Numtemp=True then
        strHtml2= strHtml2 & vbCrLf & string(Num," ") & arrContent(i) 
      else
        strHtml2= strHtml2 & vbCrLf & arrContent(i) 
      end if
    end if
  Next
  Resumeblank = strHtml2
End function

Function setContent(zhi,TemplateType)
  if zhi="get" then
    if TemplateType=1 then
      if Strsave="A" then Exit Function
      Strsave="A"
      TemplateContent= document.form1.Content.value
      TemplateContent2= editor.HtmlEdit.document.body.innerHTML
    else
      if Strsave2="A" then Exit Function
      Strsave2="A"
      TemplateContent= document.form1.Content2.value
      TemplateContent2= editor2.HtmlEdit.document.body.innerHTML
    End if
    if TemplateContent="" then 
      alert "您删除了代码框网页，请您务必填写网页 ！"
      Exit function
    End if
    if Instr(TemplateContent,"<body>")=0 then
      regEx.Pattern = "(\<body)(.[^\<]*)(\>)"
      Set Matches = regEx.Execute(TemplateContent)
      For Each Match In Matches
        StrBody = Match.Value
      Next
      If StrBody = ""  Then
        alert "您加载的文本框没有包含 <body> 或您没有给body 参数这会使网页很难看,请最少给出 <body> ！"
        Exit function
      End If 
    Else
      StrBody="<body>" 
    End if
    arrContent = Split(TemplateContent, StrBody)
    if ubound(arrContent)=0 then 
       alert "您加载的文本框没有包含 <body> 或您没有给body 参数这会使网页很难看,请最少给出 <body> ！"
       exit function
    End if
    Content1 = arrContent(0) & StrBody
    Content2 = arrContent(1)
    regEx.Pattern = "\<IMG(.[^\<]*?)[ ]*\}[\""|\'][ ]*\>"
    Set Matches = regEx.Execute(TemplateContent2)
    For Each Match In Matches
      regEx.Pattern = "#\{([\s\S]*?)[ ]*\}"
      Set strTemp = regEx.Execute(replace(Match.Value,""" """,""""""))
      For Each Match2 In strTemp
        strTemp2 = Replace(Match2.Value, "?", """")
        strTemp2 = Replace(strTemp2,"&#13;&#10;",vbCrLf)
        strTemp2 = Replace(strTemp2,"&#9;",vbTab)
        TemplateContent2 = Replace(TemplateContent2, Match.Value, strTemp2)
      Next
    Next
    regEx.Pattern = "\<IMG(.[^\<]*?)\\$\>"
    Set Matches = regEx.Execute(TemplateContent2)
    For Each Match In Matches
      regEx.Pattern = "#\[!(.*)!\]#"
      Set strTemp = regEx.Execute(Match.Value)
      For Each Match2 In strTemp
        strTemp2 = Replace(Match2.Value, "?", "?")
        strTemp2 = Replace(Match2.Value, "&amp;", "&")
        strTemp2 = Replace(strTemp2, "#", "")
        strTemp2 = Replace(strTemp2,"&13;&10;",vbCrLf)
        strTemp2 = Replace(strTemp2,"&9;",vbTab)
        strTemp2 = Replace(strTemp2, "[!", "<")
        strTemp2 = Replace(strTemp2, "!]", ">")
        TemplateContent2 = Replace(TemplateContent2, Match.Value, strTemp2)
      Next
    Next
    TemplateContent2=Replace(TemplateContent2, "<%=siteUrl %>","#{InstallDir }")
    TemplateContent2=Replace(TemplateContent2, "<%=installDir %>","#{InstallDir }")
    TemplateContent2=Resumeblank(TemplateContent2)
    TemplateContent2=Replace(TemplateContent2,"#{InstallDir}#{rsClass_ClassUrl}","#{rsClass_ClassUrl}")
    if TemplateType =1 then
        document.form1.Content.value=Content1& vbCrLf &TemplateContent2& vbCrLf &"</body>"& vbCrLf &"</html>"
    else
        document.form1.Content2.value=Content1 & vbCrLf &TemplateContent2& vbCrLf &"</body>"& vbCrLf &"</html>"
    End if
  Else
    if TemplateType =1 then    
      if Strsave="B" then Exit Function
      Strsave="B"
      TemplateContent= document.form1.Content.value
    else 
      if Strsave2="B" then Exit Function
      Strsave2="B"
      TemplateContent= document.form1.Content2.value
    End if    
    if TemplateContent="" then 
      alert "您删除了代码框网页，请您务必填写网页 ！"
      Exit function
    End if
    if Instr(TemplateContent,"<body>")=0 then
      regEx.Pattern = "(\<body)(.[^\<]*)(\>)"
      Set Matches = regEx.Execute(TemplateContent)
      For Each Match In Matches
        StrBody = Match.Value
      Next
      If StrBody = ""  Then
        alert "您加载的文本框没有包含 <body> 或您没有给body 参数这会使网页很难看,请最少给出 <body> ！"
        Exit function
      End If 
    Else
      StrBody="<body>" 
    End if
    arrContent = Split(TemplateContent, StrBody)
    if ubound(arrContent)=0 then 
      alert "您加载的文本框没有包含 <body> 或您没有给body 参数这会使网页很难看,请最少给出 <body> ！"
      exit function
    End if
    Content1 = arrContent(0) & StrBody
    Content2 = arrContent(1)
    '图片替换JS
    regEx.Pattern = "(\<Script)([\S\s]*?)(\<\/Script\>)"
    ' regEx.Pattern = "(\<Script)(.*?)(\<\/Script\>)"
    Set Matches = regEx.Execute(Content2)
    For Each Match In Matches
      strTemp = Replace(Match.Value, "<", "[!")
      strTemp = Replace(strTemp, ">", "!]")
      strTemp = Replace(strTemp, "'", "′")
      strTemp = "<IMG alt='#" & strTemp & "#' src=""../editor/images/jscript.gif"" border=0 $>"
      Content2 = Replace(Content2, Match.Value, strTemp)
    Next
    '图片替换超级标签
    regEx.Pattern = "(#\{ShowPicArticle|#\{ShowArticleList|#\{ShowSlidePicArticle|#\{ShowPicSoft|#\{ShowSoftList|#\{ShowSlidePicSoft|#\{ShowPicPhoto|#\{ShowPhotoList|#\{ShowSlidePicPhoto|#\{ShowSearchResult)([\s\S]*?)[ ]*\}"
    Set Matches = regEx.Execute(Content2) ' 执行搜索。
    For Each Match in Matches  ' 遍历匹配集合。
      Content2 = Replace(Content2, Match.Value, Replace(Match.Value, """", "'"))
    Next
    Content2 = regEx.Replace(Content2, "<IMG src=""../editor/images/label.gif"" border=0 zzz=""$1$2}"">")

    Content2 = Replace(Content2, "{$vbCrLf}",vbCrLf)
    regEx.Pattern = "#\{InstallDir\s*\}"
    Set Matches = regEx.Execute(Content2)
    For Each Match In Matches
      Content2 = Replace(Content2, Match.Value, "<%=installDir %>")
    Next
    if TemplateType=1 then
      editor.HtmlEdit.document.body.innerHTML=Content2
      editor.showBorders()
      editor.showBorders()
    else
      editor2.HtmlEdit.document.body.innerHTML=Content2
      editor2.showBorders()
      editor2.showBorders()
    End if
  End if
End function

Function setstatus()

End Function
</script>
  
</body>
</html>